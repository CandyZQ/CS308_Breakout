package breakout;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class PowerUp {
    public static final int POWER_UP_SIZE = 50;
    public static final int POWER_UP_NUMBER = 3;
    public static final int POWER_UP_SPEED = 50;

    private double x;
    private double y;

    PowerUpType type;
    ImageView instance;
    Image image;

    public PowerUp(int t, double x, double y) {
        this.x = x;
        this.y = y;
        type = PowerUpType.values()[t];
        assignImage();
        makePowerUp();
    }

    private void assignImage() throws IllegalArgumentException{
        String filename = "";
        switch (type) {
            case BOMB:
                filename = "bomb.gif";
                break;
            case LIFE:
                filename = "heart.gif";
                break;
            case BALL_SPEEDUP:
                filename = "lightening.gif";
                break;
            default:
                throw new IllegalArgumentException("The Power Up type does not have a corresponding image");
        }
        image = new Image(this.getClass().getClassLoader().getResourceAsStream(filename));
    }

    private void makePowerUp() {
        instance = new ImageView(image);
        instance.setFitHeight(POWER_UP_SIZE);
        instance.setFitWidth(POWER_UP_SIZE);
        instance.setX(x);
        instance.setY(y);
    }

    public void move(double elapsedTime) {
        y = y + elapsedTime * POWER_UP_SPEED;
        instance.setY(y);
    }

    public ImageView getInstance() {
//        System.out.println("Type: " + type);
//        System.out.println("x: " + instance.getX());
//        System.out.println("y: " + instance.getY());
        return instance;
    }

    public PowerUpType getType() {
        return type;
    }
}
