package breakout;

import breakout.directions.MovingDirection;
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

import java.awt.*;
import java.io.File;

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

    boolean hasStarted;
    boolean finalScreenStage;

    private Ball ball;
    private BrickPane bricks;
    private Paddle paddle;

    private Stage primaryStage;
    private Text text;
    private Group root;
    private Scene scene;

    public Engine(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.finalScreenStage = false;
        root = new Group();
    }

    public void createSplashScreen() {
        Text title = new Text(BG_WIDTH / 2 - TITLE_PADDING, BG_HEIGHT / 2, "B-Breaker");
        title.setFont(new Font(30));
        title.setTextAlignment(TextAlignment.RIGHT);

        Text text = new Text(BG_WIDTH / 2 - TITLE_PADDING - TEXT_PADDING, BG_HEIGHT / 2 + 50, "Press any key to start");
        text.setFont(new Font(20));
        text.setTextAlignment(TextAlignment.RIGHT);

        root.getChildren().addAll(title, text, new Ball().getInstance(), new Paddle().getInstance(),
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
        setStage();
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

        paddle = new Paddle();
        ball = new Ball();
        bricks = new BrickPane("." + File.separatorChar + "resources" + File.separatorChar + filename);
        root.getChildren().addAll(ball.getInstance(), paddle.getInstance(), bricks.getInstance());
        updateStatus();

        scene = new Scene(root, BG_WIDTH, BG_HEIGHT, BACKGROUND);
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
    }

    private void updateStatus() {
        if (text != null) {
            root.getChildren().remove(text);
        }
        text = new Text(TEXT_LEFT_PADDING, BG_HEIGHT - TEXT_BUTTON_PADDING,
                "Current Level: " + level + "          Lives Left: " + life);
        text.setFont(new Font(20));

        root.getChildren().add(text);
    }

    private void step(double elapsedTime) {
        ball.move(elapsedTime);
        paddle.move(elapsedTime, BG_WIDTH);

        if (!finalScreenStage) {
            // collision check
            checkPaddleCollision();
            checkBoundaryCollision();
            checkBricksCollision();

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

    private void createFinalScreen(String message) {
        finalScreenStage = true;

        Text text = new Text(BG_WIDTH / 2 - TITLE_PADDING, BG_HEIGHT / 2 + 50, "You " + message + " !");
        text.setFont(new Font(30));
        text.setTextAlignment(TextAlignment.RIGHT);

        root = new Group();
        root.getChildren().addAll(text, new Ball().getInstance(), new Paddle().getInstance());

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
        return level == NUM_LEVEL;
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
            ball.paddleCollision();
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
                        bricks.updateBrickStatus(r, c);
                    }
                }
            }
        }
    }
    private void handleKeyReleased(KeyCode code) {
        paddle.setMovingDirection(MovingDirection.STAY);
    }

    private void handleKeyInput (KeyCode code) {
        switch (code) {
            case RIGHT:
                if (hasStarted) {
                    paddle.setMovingDirection(MovingDirection.RIGHT);
                }
                break;
            case LEFT:
                if (hasStarted) {
                    paddle.setMovingDirection(MovingDirection.LEFT);
                }
                break;
            case SPACE:
                hasStarted = true;
                ball.setMovingDirection(MovingDirection.UPRIGHT);
                break;
            default:
                break;
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
