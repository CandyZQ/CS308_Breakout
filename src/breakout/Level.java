package breakout;

import breakout.directions.MovingDirection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;

public class Level {

    public static final int TEXT_LEFT_PADDING = 20;
    private static final int TEXT_BUTTON_PADDING = 20;

    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private int level = 1;
    private int life = 3;

    boolean hasStarted;

    private Ball ball;
    private BrickPane bricks;
    private Paddle paddle;
    private Stage primaryStage;
    private Text text;
    private Group root;

    public Level(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void createNewLevel() {
        Scene scene = setupGame("map_level_" + level + ".txt");
        primaryStage.setTitle("B-Breaker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void addFrame() {
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));

        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    public Scene setupGame(String filename) {
        root = new Group();

        paddle = new Paddle();
        ball = new Ball();
        bricks = new BrickPane("." + File.separatorChar + "resources" + File.separatorChar + filename);
//        text = new Text(TEXT_LEFT_PADDING, Main.BG_HEIGHT - TEXT_BUTTON_PADDING,
//                "Current Level: " + level + "          Lives Left: " + life);
//        text.setFont(new Font(20));

        root.getChildren().addAll(ball.getInstance(), paddle.getInstance(), bricks.getInstance());
        updateStatus();

        Scene scene = new Scene(root, Main.BG_WIDTH, Main.BG_HEIGHT, Main.BACKGROUND);
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
        return scene;
    }

    private void updateStatus() {
        if (text != null) {
            root.getChildren().remove(text);
        }
        text = new Text(TEXT_LEFT_PADDING, Main.BG_HEIGHT - TEXT_BUTTON_PADDING,
                "Current Level: " + level + "          Lives Left: " + life);
        text.setFont(new Font(20));

        root.getChildren().add(text);
    }

    public void step(double elapsedTime) {
        ball.move(elapsedTime);
        paddle.move(elapsedTime, Main.BG_WIDTH);

        // collision check
        checkPaddleCollision();
        checkBoundaryCollision();
        checkBricksCollision();

        if (isEndLevel()) {
            level += 1;
            hasStarted = false;
            createNewLevel();
        }
    }

    public boolean isEndLevel() {
        for (int r = 0; r < BrickPane.ROW_NUM; r++) {
            for (int c = 0; c < BrickPane.COL_NUM; c++) {
                Brick brick = bricks.getBricks()[r][c];
                if (brick != null) return false;
            }
        }
        return true;
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

            // cheat key - life
            case A:
                life = 99;
                break;

            default:
                break;
        }
    }


}
