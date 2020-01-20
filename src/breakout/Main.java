package breakout;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the main class that runs the game.
 *
 * @author Cady Zhou
 * @version 1.1
 * @since 1.1
 * @see Engine
 */
public class Main extends Application {
    Engine engine;

    /**
     * Launch the game.
     */
    public static void main (String[] args) {
        launch(args);
    }

    /**
     * Creates an instance of Engine and starts the game.
     * @param primaryStage  the main {@link Stage}
     */
    @Override
    public void start(Stage primaryStage) {
        engine = new Engine(primaryStage);
        engine.createSplashScreen();
    }
}
