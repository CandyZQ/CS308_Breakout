package breakout;

import breakout.bricks.Brick;
import breakout.bricks.BrickPane;
import breakout.directions.MovingDirection;
import breakout.directions.RotationDirection;
import breakout.elements.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is the main Engine that controls the game flow.
 * All state changes of game element should be made with {@link Controller}
 * <p></p>
 *
 * Example code:
 * <p></p>
 * {@code
 *  import breakout.Engine;
 *    Engine engine = new Engine(primaryStage);
 *    engine.createSplashScreen();
 * }
 *
 * @author Cady Zhou
 * @version 1.1
 * @since 1.1
 * @see Controller
 */
public class Engine {
    public static final int BG_HEIGHT = 500;
    public static final int BG_WIDTH = 500;
    public static final Paint BACKGROUND = Color.WHITE;

    public static final int TITLE_PADDING = 60;
    public static final int TEXT_PADDING = 30;
    public static final int TEXT_LEFT_PADDING = 20;
    private static final int TEXT_BUTTON_PADDING = 20;

    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    public static final int NUM_LEVEL = 5;

    private int level = 1;
    private int life = 3;
    private int ballSpeedFastCycle = 0;

    boolean hasStarted;
    boolean isFinalScreen;

    private Ball ball;
    private BrickPane bricks;
    private Paddle paddle;
    private List<PowerUp> powerUps;
    private Text text;

    private Stage primaryStage;
    private Scene scene;
    private Group root;

    private Controller controller;

    /**
     * Creates an instance of Engine of runs the game
     * @param primaryStage  the main {@link Stage}
     */
    public Engine(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.isFinalScreen = false;

        root = new Group();
        controller = new Controller();
        powerUps = new ArrayList<>();
    }

    /**
     * Creates a splash screen that provides game keys. Should be the
     * first screen of the game.
     */
    public void createSplashScreen() {
        Text title = new Text(BG_WIDTH / 2 - TITLE_PADDING, BG_HEIGHT / 2, "B-Breaker");
        title.setFont(new Font(30));
        title.setTextAlignment(TextAlignment.RIGHT);

        Text text = new Text(BG_WIDTH / 2 - TITLE_PADDING - TEXT_PADDING, BG_HEIGHT / 2 + 50,
                "Press any key to start\nA/D: Rotate Paddle\nSpace: Shoot Ball");
        text.setFont(new Font(20));
        text.setTextAlignment(TextAlignment.CENTER);

        root.getChildren().addAll(title,
                text,
                new Ball().getInstance(),
                new Paddle(level).getInstance(),
                new BrickPane("." + File.separatorChar + "resources" + File.separatorChar + "map_level_0.txt").getInstance());

        scene = new Scene(root, BG_WIDTH, BG_HEIGHT, BACKGROUND);
        scene.addEventFilter(KeyEvent.ANY, keyEvent -> {
            createNewLevel();
            addFrame();
        });

        setStage();
    }

    /**
     * Creates a new scene with given level.
     */
    private void createNewLevel() {
        isFinalScreen = false;
        powerUps = new ArrayList<>();

        addLevelElements("map_level_" + level + ".txt");
        updateStatus();
        setUpLevelScene();
        setStage();
    }

    /**
     * Add elements with given level to {@link #root}
     * @param filename  a txt file of brick configuration
     */
    private void addLevelElements(String filename) {
        root = new Group();

        paddle = new Paddle(level);
        ball = new Ball();
        bricks = new BrickPane("." + File.separatorChar + "resources" + File.separatorChar + filename);
        root.getChildren().addAll(ball.getInstance(), paddle.getInstance(), bricks.getInstance());
    }

    /**
     * Add current level and lives left (Status) to {@link #root}.
     */
    private void updateStatus() {
        if (text != null) {
            root.getChildren().remove(text);
        }
        text = new Text(TEXT_LEFT_PADDING, BG_HEIGHT - TEXT_BUTTON_PADDING,
                "Current Level: " + level +
                        "                                      Lives Left: " + life);
        text.setFont(new Font(20));
        root.getChildren().add(text);
    }

    private void setUpLevelScene() {
        scene = new Scene(root, BG_WIDTH, BG_HEIGHT, BACKGROUND);
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
    }

    private void setStage() {
        primaryStage.setTitle("B-Breaker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addFrame() {
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));

        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step(double elapsedTime) {
        ball.move(elapsedTime);
        paddle.move(elapsedTime);

        for (PowerUp p: powerUps) {
            p.move(elapsedTime);
        }

        if (ball.isSpeedFast()) {
            ballSpeedFastCycle += 1;
            if (ballSpeedFastCycle == Ball.BALL_SPEED_UP_CYCLE) {
                ball.setBallSpeedNormal();
            }
        }

        if (!isFinalScreen) {
            // collision check
            checkBoundaryCollision();
            checkPaddleCollision();
            checkBricksCollision();
            checkPowerUpCollision();

            // check end-level/game status
            if (isEndLevel()) {
                level += 1;
                hasStarted = false;
                createNewLevel();
            }
            if (isLose()) {
                createFinalScene("Lose");
            }
            if (isWin()) {
                createFinalScene("Win");
            }
        }
    }

    private void setPowerUpScene() {
        root = new Group();
        root.getChildren().addAll(ball.getInstance(),
                paddle.getInstance(),
                bricks.getInstance());

        for (PowerUp p: powerUps) {
            root.getChildren().add(p.getInstance());
        }
        updateStatus();
        setUpLevelScene();
        setStage();
    }

    private void checkPowerUpCollision() {
        for (Iterator<PowerUp> itr = powerUps.iterator(); itr.hasNext();) {
            PowerUp p = itr.next();
            if (p.getInstance().getBoundsInParent().intersects(paddle.getInstance().getBoundsInParent())) {
                handleEffect(p.getType());
                itr.remove();
                setPowerUpScene();
            }
        }
    }

    private void handleEffect(PowerUpType type) {
        switch (type) {
            case LIFE:
                life += 1;
                updateStatus();
                break;
            case BALL_SPEEDUP:
                ball.setBallSpeedFast();
                break;
            case BOMB:
                boolean end = false;
                for (int r = 0; r < BrickPane.ROW_NUM; r++) {
                    for (int c = 0; c < BrickPane.COL_NUM; c++) {
                        if (bricks.getBricks()[r][c] != null) {
                            end = true;
                            bricks.checkPowerUp(r, c);
                            bricks.updateBrickStatus(r, c);
                            break;
                        }
                    }
                    if (end) break;
                    }
                break;
            default:
                break;
        }
    }

    private void createFinalScene(String message) {
        isFinalScreen = true;

        Text text = new Text(BG_WIDTH / 2 - TITLE_PADDING, BG_HEIGHT / 2 + 50, "You " + message + " !");
        text.setFont(new Font(30));
        text.setTextAlignment(TextAlignment.RIGHT);

        root = new Group();
        root.getChildren().addAll(text,
                new Ball().getInstance(),
                new Paddle(level).getInstance());

        scene = new Scene(root, BG_WIDTH, BG_HEIGHT, BACKGROUND);
        scene.setOnKeyPressed(e -> handCheatKeyInput(e.getCode()));
        setStage();
    }

    private boolean isEndLevel() {
        for (int r = 0; r < BrickPane.ROW_NUM; r++) {
            for (int c = 0; c < BrickPane.COL_NUM; c++) {
                Brick brick = bricks.getBricks()[r][c];
                if (brick != null) return false;
            }
        }
        return true;
    }

    private boolean isWin() {
        return level > NUM_LEVEL;
    }

    public boolean isLose() {
        return life <= 0;
    }

    private void checkBoundaryCollision() {
        if (ball.isHitBoundary()) {
            if (!controller.ballBoundaryCollision(ball)) {
                life -= 1;
                setUpDeadScene();
            }
        }
    }

    private void setUpDeadScene() {
        isFinalScreen = false;
        powerUps = new ArrayList<>();
        root = new Group();

        ball = new Ball();
        paddle = new Paddle(level);
        root.getChildren().addAll(ball.getInstance(),
                paddle.getInstance(),
                bricks.getInstance());

        updateStatus();
        setUpLevelScene();
        setStage();
        hasStarted = false;
    }

    private void checkPaddleCollision() {
        Shape ballPaddleIntersection = Shape.intersect((Shape) ball.getInstance(), (Shape) paddle.getInstance());
        if (ballPaddleIntersection.getBoundsInLocal().getWidth() != -1) {
            controller.ballPaddleCollision(ball, paddle);
        }
    }

    private void checkBricksCollision() {
        for (int r = 0; r < BrickPane.ROW_NUM; r++) {
            for (int c = 0; c < BrickPane.COL_NUM; c++) {
                Brick brick = bricks.getBricks()[r][c];
                if (brick != null) {
                    Shape ballBrickIntersection = Shape.intersect((Shape) ball.getInstance(), brick.getInstance());
                    if (ballBrickIntersection.getBoundsInLocal().getWidth() != -1) {

                        controller.ballBrickCollision(ball, brick);
                        PowerUp powerUp = bricks.checkPowerUp(r, c);
                        if (powerUp != null) {
                            powerUps.add(powerUp);
                            setPowerUpScene();
                        }
                        bricks.updateBrickStatus(r, c);
                    }
                }
            }
        }
    }

    private void handleKeyReleased(KeyCode code) {
        paddle.setMovingDirection(MovingDirection.STAY);
        paddle.setRotationDirection(RotationDirection.FLOATING);
    }

    private void handleKeyInput (KeyCode code) {
        if (hasStarted) {
            switch (code) {
                case RIGHT:
                    paddle.setMovingDirection(MovingDirection.RIGHT);
                    break;
                case LEFT:
                    paddle.setMovingDirection(MovingDirection.LEFT);
                    break;
                case A:
                    paddle.setRotationDirection(RotationDirection.CCW);
                    break;
                case D:
                    paddle.setRotationDirection(RotationDirection.CC);
                    break;
                default:
                    break;
            }
        }

        if (code == KeyCode.SPACE) {
            hasStarted = true;
            ball.setMovingDirection(MovingDirection.UPRIGHT);
        }
        handCheatKeyInput(code);
    }

    private void handCheatKeyInput(KeyCode code) {
        switch (code) {
            // cheat keys - level
            case DIGIT1:
                level = 1;
                hasStarted = false;
                createNewLevel();
                break;
            case DIGIT2:
                level = 2;
                hasStarted = false;
                createNewLevel();
                break;
            case DIGIT3:
                level = 3;
                hasStarted = false;
                createNewLevel();
                break;
            case DIGIT4:
                level = 4;
                hasStarted = false;
                createNewLevel();
                break;
            case DIGIT5:
                level = 5;
                hasStarted = false;
                createNewLevel();
                break;
            case R:
                hasStarted = false;
                life = 3;
                createNewLevel();
                break;

                // cheat key - life
            case M:
                life = 99;
                updateStatus();
                break;
            default:
                break;
        }

    }
}
