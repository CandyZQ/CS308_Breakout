package breakout.elements;

import breakout.directions.MovingDirection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The PowerUp class defines a power-up with given position and
 * {@link PowerUpType} The player retains certain special ability
 * after catching a PowerUp.
 * <p></p>
 *
 * Example code: the following code adds one life if a ball hits
 * the brick (centered at (100, 100)) of this PowerUp.
 * <p></p>
 * {@code
 *  import breakout.elements.PowerUp;
 *  import breakout.directions.MovingDirection;
 *
 *  PowerUp pu = new PowerUp(1, 100, 100);
 *  while (true) {
 *    up.move(200);
 *  }
 * }
 *
 * @author Cady Zhou
 * @version 1.1
 * @since 1.1
 * @see DynamicElement
 */

public class PowerUp extends Element{
    public static final int POWER_UP_SIZE = 50;
    public static final int POWER_UP_NUMBER = 3;
    public static final int POWER_UP_SPEED = 50;

    PowerUpType type;
    Image image;

    /**
     * Creates an instance of {@code Power} with given position and
     * {@link PowerUpType}.
     * @param t     the value represents {@code PowerUpType}
     * @param x     horizontal position of this element
     * @param y     vertical position of this element
     * @see PowerUpType
     */
    public PowerUp(int t, double x, double y) {
        super(x, y, POWER_UP_SPEED, MovingDirection.DOWN);
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
        ((ImageView) instance).setFitHeight(POWER_UP_SIZE);
        ((ImageView) instance).setFitWidth(POWER_UP_SIZE);
        ((ImageView) instance).setX(x);
        ((ImageView) instance).setY(y);
    }

    /**
     *  Updates the position of this PowerUp with time.
     * @param elapsedTime   time passed during one animation cycle
     */
    @Override
    public void move(double elapsedTime) {
        y = y + elapsedTime * POWER_UP_SPEED;
        ((ImageView) instance).setY(y);
    }

    /**
     * Gets the {@link PowerUpType}
     * @return {@link PowerUpType} of this PowerUp
     * @see PowerUpType
     */
    public PowerUpType getType() {
        return type;
    }
}
