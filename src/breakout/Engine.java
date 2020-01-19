package breakout;

import breakout.directions.MovingDirection;
import breakout.directions.RotationDirection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
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
    boolean finalScreenStage;

    private Ball ball;
    private BrickPane bricks;
    private Paddle paddle;

    private Stage primaryStage;
    private Text text;
    private Group root;
    private Scene scene;
    private List<PowerUp> powerUps;

    public Engine(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.finalScreenStage = false;
        powerUps = new ArrayList<>();
        root = new Group();
    }

    public void createSplashScreen() {
        Text title = new Text(BG_WIDTH / 2 - TITLE_PADDING, BG_HEIGHT / 2, "B-Breaker");
        title.setFont(new Font(30));
        title.setTextAlignment(TextAlignment.RIGHT);

        Text text = new Text(BG_WIDTH / 2 - TITLE_PADDING - TEXT_PADDING, BG_HEIGHT / 2 + 50, "Press any key to start");
        text.setFont(new Font(20));
        text.setTextAlignment(TextAlignment.RIGHT);

        root.getChildren().addAll(title, text, new Ball().getInstance(), new Paddle(level).getInstance(),
                new BrickPane("." + File.separatorChar + "resources" + File.separatorChar + "map_level_0.txt").getInstance());

        scene = new Scene(root, BG_WIDTH, BG_HEIGHT, BACKGROUND);
        scene.addEventFilter(KeyEvent.ANY, keyEvent -> {
            createNewLevel();
            addFrame();
        });

        setStage();
    }

    public void createNewLevel() {
        finalScreenStage = false;
        setupGame("map_level_" + level + ".txt");
    }

    public void addFrame() {
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));

        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void setStage() {
        primaryStage.setTitle("B-Breaker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setupGame(String filename) {
        root = new Group();

        paddle = new Paddle(level);
        ball = new Ball();
        bricks = new BrickPane("." + File.separatorChar + "resources" + File.separatorChar + filename);
        root.getChildren().addAll(ball.getInstance(), paddle.getInstance(), bricks.getInstance());
        updateStatus();

        setUpLevelScene();
        setStage();
    }

    public void setPowerUpScene() {
        root = new Group();
        root.getChildren().addAll(ball.getInstance(), paddle.getInstance(), bricks.getInstance());

        for (PowerUp p: powerUps) {
            root.getChildren().add(p.getInstance());
        }
        updateStatus();
        setUpLevelScene();
        setStage();
    }

    private void setUpLevelScene() {
        scene = new Scene(root, BG_WIDTH, BG_HEIGHT, BACKGROUND);
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
    }

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

        if (!finalScreenStage) {
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

            if (isDead()) {
                createFinalScreen("Lose");
            }

            if (isWin()) {
                createFinalScreen("Win");
            }
        }
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

    private void createFinalScreen(String message) {
        finalScreenStage = true;

        Text text = new Text(BG_WIDTH / 2 - TITLE_PADDING, BG_HEIGHT / 2 + 50, "You " + message + " !");
        text.setFont(new Font(30));
        text.setTextAlignment(TextAlignment.RIGHT);

        root = new Group();
        root.getChildren().addAll(text, new Ball().getInstance(), new Paddle(level).getInstance());

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

    public boolean isDead() {
        return life <= 0;
    }

    private void checkBoundaryCollision() {
        if (ball.hitBoundary()) {
            if (!ball.boundaryCollision()) {
                life -= 1;
                updateStatus();
            }
        }
    }

    private void checkPaddleCollision() {
        Shape ballPaddleIntersection = Shape.intersect(ball.getInstance(), paddle.getInstance());
        if (ballPaddleIntersection.getBoundsInLocal().getWidth() != -1) {
            ball.paddleCollision(paddle.getAngle(), paddle.getX());
        }
    }

    private void checkBricksCollision() {
        for (int r = 0; r < BrickPane.ROW_NUM; r++) {
            for (int c = 0; c < BrickPane.COL_NUM; c++) {
                Brick brick = bricks.getBricks()[r][c];
                if (brick != null) {
                    Shape ballBrickIntersection = Shape.intersect(ball.getInstance(), brick.getInstance());
                    if (ballBrickIntersection.getBoundsInLocal().getWidth() != -1) {

                        ball.brickCollision(brick);
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
