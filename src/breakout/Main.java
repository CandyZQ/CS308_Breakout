package breakout;

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
    private static final int BALL_SPEED_NORMAL = 5;
    private static final int BALL_SPEED_FAST = 8;
    private static final int PADDLE_SPEED_NORMAL = 8;
    private static final int HEIGHT = 600;
    private static final int WIDTH = 1200;
    private static final Paint BACKGROUND = Color.BLACK;

    private ImageView ball;
    private ImageView brick;
    private ImageView paddle;

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
    }

    private Scene setupGame (int width, int height, Paint background) {
        Group root = new Group();
        StackPane pane = new StackPane();

        paddle = setupElement(PADDLE_IMAGE, width / 2, height - PADDLE_OFFSET_BOTTOM);
        ball = setupElement(BALL_IMAGE, width / 2, height - BALL_OFFSET_BOTTOM);
        root.getChildren().add(ball);
        root.getChildren().add(paddle);

        Scene scene = new Scene(root, width, height, background);
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }

    private ImageView setupElement(String picture, int x, int y) {
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(picture));
        ImageView imageView = new ImageView(image);
        imageView.setX(x);
        imageView.setY(y);
        return imageView;
    }


    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
         switch (code) {
             case RIGHT:
                 paddle.setX(paddle.getX() + paddleSpeed);
                 break;
             case LEFT:
                 paddle.setX(paddle.getX() - paddleSpeed);
                 break;
             default:
                 break;

         }
    }
}
