package breakout;

import breakout.directions.MovingDirection;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

import java.io.File;

public class Level {
    public static final String HEART_IMAGE = "heart.gif";

    public static final int PADDLE_OFFSET_BOTTOM = 50;
    public static final Paint PADDLE_COLOR = Color.YELLOW;
    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 10;

    public static final Paint BALL_COLOR = Color.AZURE;
    public static final int BALL_RADIUS = 8;

    public static final int BALL_SPEED_NORMAL = 400;
    public static final int BALL_SPEED_FAST = 8;
    public static final int PADDLE_SPEED_NORMAL = 300;

    public static final int BG_HEIGHT = 500;
    public static final int BG_WIDTH = 500;
    public static final Paint BACKGROUND = Color.BLACK;

    private int level = 0;

    private Ball ball;
    private BrickPane bricks;
    private Paddle paddle;

    Scene setupGame(String filename) {
        Group root = new Group();

        paddle = new Paddle(BG_WIDTH / 2 - PADDLE_WIDTH / 2, BG_HEIGHT - PADDLE_OFFSET_BOTTOM, PADDLE_SPEED_NORMAL, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_COLOR);
        ball = new Ball(BG_WIDTH / 2, BG_HEIGHT - PADDLE_OFFSET_BOTTOM, BALL_SPEED_NORMAL, BALL_RADIUS, BALL_COLOR);
        bricks = new BrickPane("." + File.separatorChar + "resources" + File.separatorChar + filename);

        root.getChildren().addAll(ball.getInstance(), paddle.getInstance(), bricks.getInstance());

        Scene scene = new Scene(root, BG_WIDTH, BG_HEIGHT, BACKGROUND);
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
        return scene;
    }

    private void handleKeyReleased(KeyCode code) {
        paddle.setMovingDirection(MovingDirection.STAY);
    }
    private void handleKeyInput (KeyCode code) {
        switch (code) {
            case RIGHT:
                paddle.setMovingDirection(MovingDirection.RIGHT);
                break;
            case LEFT:
                paddle.setMovingDirection(MovingDirection.LEFT);
                break;
            case SPACE:
                ball.setMovingDirection(MovingDirection.UPRIGHT);

            default:
                break;

        }
    }

    public void step(double elapsedTime) {
        ball.move(elapsedTime);
        paddle.move(elapsedTime, BG_WIDTH);

        // collision check
        checkPaddleCollision();
        checkBoundaryCollision();
        checkBricksCollision();

        if (isEndLevel()) {
            level += 1;
            setupGame("map_level_" + level + ".txt");
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
            ball.boundaryCollision();
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
}
