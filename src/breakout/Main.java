package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    Level currentLevel;

    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        currentLevel = new Level();

        Scene scene = currentLevel.setupGame("map_level_1.txt");
        primaryStage.setTitle("B-Breaker");
        primaryStage.setScene(scene);
        primaryStage.show();

        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> currentLevel.step(SECOND_DELAY));

        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);

        animation.getKeyFrames().add(frame);
        animation.play();

    }

}
