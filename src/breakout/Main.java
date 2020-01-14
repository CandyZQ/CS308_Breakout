package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    private static final String HEART_IMAGE = "heart.gif";

    private static final int PADDLE_OFFSET_BOTTOM = 200;
    private static final Paint PADDLE_COLOR = Color.YELLOW;
    private static final int PADDLE_WIDTH = 200;
    private static final int PADDLE_HEIGHT = 30;

    private static final int BALL_OFFSET_BOTTOM = 250;
    private static final Paint BALL_COLOR = Color.AZURE;
    public static final int BALL_RADIUS = 30;

    private static final int BALL_SPEED_NORMAL = 300;
    private static final int BALL_SPEED_FAST = 8;
    private static final int PADDLE_SPEED_NORMAL = 300;

    private static final int HEIGHT = 600;
    private static final int WIDTH = 1200;
    private static final Paint BACKGROUND = Color.BLACK;

    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;


    private Ball ball;
    private Paddle brick;
    private Paddle paddle;

    private int ballSpeed = BALL_SPEED_NORMAL;
    private int paddleSpeed = PADDLE_SPEED_NORMAL;

    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = setupGame(WIDTH, HEIGHT, BACKGROUND);

        primaryStage.setTitle("B-Breaker");
        primaryStage.setScene(scene);
        primaryStage.show();

        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

    }

    private Scene setupGame (int width, int height, Paint background) {
        Group root = new Group();

        paddle = new Paddle(width / 2 - PADDLE_WIDTH / 2, height - PADDLE_OFFSET_BOTTOM, PADDLE_SPEED_NORMAL, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_COLOR);
        ball = new Ball(width / 2, height - PADDLE_OFFSET_BOTTOM, BALL_SPEED_NORMAL, BALL_RADIUS, BALL_COLOR);
        root.getChildren().add(ball.getInstance());
        root.getChildren().add(paddle.getInstance());

        Scene scene = new Scene(root, width, height, background);
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
        return scene;
    }

    private void handleKeyReleased(KeyCode code) {
        paddle.setMovingDirection(MovingDirection.STAY);
    }

    private void step (double elapsedTime) {
        ball.move(elapsedTime);
        paddle.move(elapsedTime, WIDTH);

        // collision check
        Shape ballPaddleIntersection = Shape.intersect(ball.getInstance(), paddle.getInstance());
        if (ballPaddleIntersection.getBoundsInLocal().getWidth() != -1) {
            ball.paddleCollision();
        }

        if (ball.hitBoundary(WIDTH, HEIGHT)) {
            ball.boundaryCollision(WIDTH, HEIGHT);
        }
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
}
