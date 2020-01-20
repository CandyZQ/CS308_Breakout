package breakout.bricks;

import breakout.Engine;
import breakout.elements.PowerUp;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.Random;

/**
 * This Brick class defines a single Brick that has its size defined
 * by size of the application window and column number of bricks. Missing
 * or inaccurate values can fail this brick to be created with the correct
 * size. A Brick remains at the same position during the game. The
 * position is defined in {@link BrickPane} with {@code row} and
 * {@code col}.
 * <p></p>
 * A Brick also has a {@link #level}, indicating the number of time a Ball
 * hit needed to eliminate this brick. After each hit, level is subtracted
 * by one. This change will be reflected with its color.
 * <p></p>
 *
 * Example code: the following code creates a level 5 brick at (0,0) of
 * BrickPane.
 * <p></p>
 * {@code
 *  import breakout.bricks.Brick;
 *
 *  Brick b = new Brick(5, 0, 0);
 * }
 *
 * @author Cady Zhou
 * @version 1.1
 * @since 1.1
 * @see BrickPane
 */
public class Brick {
    public static final int BRICK_WIDTH = Engine.BG_WIDTH / BrickPane.COL_NUM;
    public static final int BRICK_HEIGHT = 30;

    public static final Paint COLOR_LEVEL_ONE_BRICK = Color.web("#fcdef0");
    public static final Paint COLOR_LEVEL_TWO_BRICK = Color.web("#ffb0e1");
    public static final Paint COLOR_LEVEL_THREE_BRICK = Color.web("#ff80ce");
    public static final Paint COLOR_LEVEL_FOUR_BRICK = Color.web("#cd5a91");
    public static final Paint COLOR_LEVEL_FIVE_BRICK = Color.web("#96304c");
    public static final Paint COLOR_UNREMOVABLE = Color.web("#212121");

    // unremovable brick has level UNREMOVABLE
    public static final int UNREMOVABLE = 9;
    // # of PowerUp type / POWER_UP_CHANCE = the probability the player gets a power-up
    public static final int POWER_UP_CHANCE = 3;

    private int level;
    private Rectangle rectangle;
    private PowerUp powerUp;

    /**
     * Creates a new instance of Ball with given {@code level} and
     * position on {@link BrickPane}.
     * @param level     the level of this brick
     * @param row       the horizontal position on {@code BrickPane}
     * @param col       the vertical position on {@code BrickPane}
     * @see BrickPane
     */
    public Brick(int level, int row, int col) {
        this.level = level;
        rectangle = new Rectangle(BRICK_WIDTH - BrickPane.BRICK_GAP, BRICK_HEIGHT);
        setColor();
        setupPowerUp(row, col);
    }

    /**
     * Sets color of this brick.
     * @return true if {@code level} is 0 and the brick should disappear
     */
    private boolean setColor() {
        switch (level) {
            case 1:
                rectangle.setFill(COLOR_LEVEL_ONE_BRICK);
                break;
            case 2:
                rectangle.setFill(COLOR_LEVEL_TWO_BRICK);
                break;
            case 3:
                rectangle.setFill(COLOR_LEVEL_THREE_BRICK);
                break;
            case 4:
                rectangle.setFill(COLOR_LEVEL_FOUR_BRICK);
                break;
            case 5:
                rectangle.setFill(COLOR_LEVEL_FIVE_BRICK);
                break;
            case UNREMOVABLE:
                rectangle.setFill(COLOR_UNREMOVABLE);
            default:
                return shouldRemove();
        }
        return false;
    }

    private boolean shouldRemove() {
        return level <= 0;
    }

    /**
     * Determines if this brick has a {@link PowerUp}. Creates an instance
     * if has one. {@code row} and {@code #col} are used to determine the
     * initial position of the {@link PowerUp}.
     * @param row       the horizontal position on {@code BrickPane}
     * @param col       the vertical position on {@code BrickPane}
     * @see PowerUp
     */
    private void setupPowerUp(int row, int col) {
        int d = new Random().nextInt(POWER_UP_CHANCE);
        if (d < PowerUp.POWER_UP_NUMBER) {
            powerUp = new PowerUp(d, BRICK_WIDTH * (col + 0.3), BRICK_HEIGHT * (row + 0.3));
        } else {
            powerUp = null;
        }
    }

    /**
     * Changes states of this brick if hit by a ball.
     * @return  true if this brick should disappear
     */
    public boolean beingHit() {
        if (level == UNREMOVABLE) {
            return false;
        }
        level -= 1;
        return setColor();
    }

    /**
     * Checks if the brick has a {@link PowerUp}
     * @return true if the brick has a {@link PowerUp}
     */
    public boolean hasPowerUp() {
        return powerUp != null;
    }

    /**
     * Gets the x position of this brick.
     * @return  a double represents x position
     */
    public double getX() {
        return rectangle.getX();
    }

    /**
     * Gets the y position of this brick.
     * @return  a double represents y position
     */
    public double getY() {
        return rectangle.getY();
    }

    /**
     * Gets {@link PowerUp} of this brick if it has one.
     * @return  the {@link PowerUp} of this brick
     */
    public PowerUp getPowerUp() throws NullPointerException{
        if (!hasPowerUp()) {
            throw new NullPointerException("This brick does not have a power-up");
        }
        return powerUp;
    }

    /**
     * Gets this instance of brick
     * @return                      a rectangle represents this brick
     * @throws NullPointerException If this brick is not initialized
     */
    public Rectangle getInstance() throws NullPointerException{
        if (rectangle == null) {
            throw new NullPointerException("Brick instance has not been created");
        }
        return rectangle;
    }
}
