package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;


/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    private static final String BALL_IMAGE = "heart.gif";
    private static final String HEART_IMAGE = "heart.gif";
    private static final String PADDLE_IMAGE = "paddle.png";

    private static final int PADDLE_OFFSET_BOTTOM = 200;
    private static final int BALL_OFFSET_BOTTOM = 250;

    private static final int BALL_SPEED_NORMAL = 100;
    private static final int BALL_SPEED_FAST = 8;
    private static final int PADDLE_SPEED_NORMAL = 100;
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

        paddle = new Paddle(width / 2, height - PADDLE_OFFSET_BOTTOM, PADDLE_SPEED_NORMAL);
        ball = new Ball(width / 2, height - BALL_OFFSET_BOTTOM, BALL_SPEED_NORMAL);
        root.getChildren().add(ball.getInstance(BALL_IMAGE));
        root.getChildren().add(paddle.getInstance(PADDLE_IMAGE));

        Scene scene = new Scene(root, width, height, background);
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
        return scene;
    }

    private void handleKeyReleased(KeyCode code) {
        paddle.setDirection(MovingDirection.STAY);
    }

    private void step (double elapsedTime) {
        ball.move(elapsedTime);
        paddle.move(elapsedTime);

//        // with images can only check bounding box
//        if (ball.getInstance().getBoundsInParent().intersects(brick.getInstance().getBoundsInParent())) {
//            myGrower.setFill(HIGHLIGHT);
//        }
//        else {
//            myGrower.setFill(GROWER_COLOR);
//        }
    }

    private void handleKeyInput (KeyCode code) {
         switch (code) {
             case RIGHT:
                 paddle.setDirection(MovingDirection.RIGHT);
                 break;
             case LEFT:
                 paddle.setDirection(MovingDirection.LEFT);
                 break;
             default:
                 break;

         }
    }
}
