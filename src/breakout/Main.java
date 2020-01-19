package breakout;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;


/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    Level currentLevel;

    public static final int BG_HEIGHT = 500;
    public static final int BG_WIDTH = 500;
    public static final Paint BACKGROUND = Color.WHITE;

    public static final int TITLE_PADDING = 60;
    public static final int TEXT_PADDING = 30;

    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        currentLevel = new Level(primaryStage);
        primaryStage.setTitle("B-Breaker");
        primaryStage.setScene(createSplashScreen());
        primaryStage.show();
    }

    private Scene createSplashScreen() {
        Group root = new Group();

        Text title = new Text(BG_WIDTH / 2 - TITLE_PADDING, BG_HEIGHT / 2, "B-Breaker");
        title.setFont(new Font(30));
        title.setTextAlignment(TextAlignment.RIGHT);

        Text text = new Text(BG_WIDTH / 2 - TITLE_PADDING - TEXT_PADDING, BG_HEIGHT / 2 + 50, "Press any key to start");
        text.setFont(new Font(20));
        text.setTextAlignment(TextAlignment.RIGHT);


        root.getChildren().addAll(title, text, new Ball().getInstance(), new Paddle().getInstance(),
                new BrickPane("." + File.separatorChar + "resources" + File.separatorChar + "map_level_0.txt").getInstance());

        Scene scene = new Scene(root, BG_WIDTH, BG_HEIGHT, BACKGROUND);
        scene.addEventFilter(KeyEvent.ANY, keyEvent -> {
            currentLevel.createNewLevel();
            currentLevel.addFrame();
        });
        return scene;
    }



}
