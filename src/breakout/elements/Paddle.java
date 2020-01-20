package breakout.elements;

import breakout.Engine;
import breakout.directions.MovingDirection;
import breakout.directions.RotationDirection;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

/**
 * The Paddle class defines a paddle with given position, fill and
 * movement information. By default, this paddle sits in the middle
 * of the horizontal axis.
 * <p></p>
 * This Paddle increases its speed at higher level. It also wraps from
 * one side of the screen to the other when reaches the edge. A ball
 * accelerates until it hits a brick if bouncing within 1/4 of either end.
 * <p></p>
 *
 * Example code: the following code creates a pink-colored ball with
 * radius 5, starting at (0,0) and moving towards right at normal speed.
 * <p></p>
 * {@code
 *  import breakout.elements.Paddle;
 *  import javafx.scene.paint.Color;
 *  import breakout.directions.MovingDirection;
 *
 *  Ball b = new Ball(0, 0, 90, BALL_SPEED_NORMAL, 5, Color.PINK, MovingDirection.RIGHT);
 *  while (true) {
 *    b.move(200);
 *  }
 * }
 *
 * @author Cady Zhou
 * @version 1.1
 * @since 1.1
 * @see DynamicElement
 */

public class Paddle extends DynamicElement {
    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 10;
    public static final int PADDLE_OFFSET_BOTTOM = 80;
    public static final Paint PADDLE_COLOR = Color.CORAL;

    public static final int PADDLE_ROTATION_SPEED = 100;
    public static final int PADDLE_SPEED_LEVEL_INCREMENT = 30;
    public static final int PADDLE_SPEED_NORMAL = 300;

    public static final double MAX_ANGLE = 40;
    public static final int PADDLE_SPEED_MAX = 250;

    private double dx;
    private double da;
    private double width;
    private double height;

    private Rotate rotate;

    /**
     * Creates a new instance of Paddle with default setting.
     * @param level
     */
    public Paddle(int level) {
        this(Engine.BG_WIDTH / 2 - PADDLE_WIDTH / 2,
                Engine.BG_HEIGHT - PADDLE_OFFSET_BOTTOM,
                0,
                PADDLE_SPEED_NORMAL + level * PADDLE_SPEED_LEVEL_INCREMENT,
                PADDLE_COLOR,
                MovingDirection.STAY);
    }


    /**
     * Creates a new instance of Ball with the given position, fill,
     * and movement information.
     * @param x                 horizontal position of this ball
     * @param y                 vertical position of this ball
     * @param angle             angle relative to the vertical line
     * @param speed             speed of this ball
     * @param fill              a {@link Paint} represents color of
     *                          this ball
     * @param movingDirection   moving direction of this ball
     */
    public Paddle(double x, double y, double angle, int speed,
                  Paint fill, MovingDirection movingDirection) {
        super(x, y, angle, speed, fill, movingDirection);
        this.speed = Math.min(PADDLE_SPEED_MAX, speed);
        this.width = PADDLE_WIDTH;
        this.height = PADDLE_HEIGHT;
        this.rotate = new Rotate();
        makeShape();
    }

    @Override
    protected void makeShape() {
        instance = new Rectangle(x, y, width, height);
        ((Rectangle) instance).setFill(fill);
    }


    @Override
    protected void updateAxesSpeed() {
        switch (movingDirection) {
            case RIGHT:
                dx = 1;
                break;
            case LEFT:
                dx = -1;
                break;
            case STAY:
                dx = 0;
            default:
                break;
        }
    }

    /**
     * Updates the position and rotation angle of this paddle
     * according to its speed on horizontal and polar axes.
     * @param elapsedTime   time passed during one animation cycle
     */
    @Override
    public void move(double elapsedTime) {
        // wraps over screen
        x = x + dx * elapsedTime * speed;
        x = x > Engine.BG_WIDTH ? 0 - width: x;
        x = x + width < 0 ? Engine.BG_WIDTH - width : x;
        ((Rectangle) instance).setX(x);

        // rotation
        instance.getTransforms().remove(rotate);
        angle = angle + da * elapsedTime * PADDLE_ROTATION_SPEED;
        if (Math.abs(angle) > MAX_ANGLE) {
            angle = angle > 0 ? MAX_ANGLE : -MAX_ANGLE;
        }

        rotate.setAngle(angle);
        rotate.setPivotX(x + (double) PADDLE_WIDTH / 2);
        rotate.setPivotY(y + PADDLE_HEIGHT);
        instance.getTransforms().add(rotate);
    }

    /**
     * Sets updated rotation direction to paddle
     * @param rotationDirection the rotation direction of this paddle
     */
    public void setRotationDirection(RotationDirection rotationDirection) {
        switch (rotationDirection) {
            case CC:
                da = 1;
                break;
            case CCW:
                da = -1;
                break;
            case FLOATING:
                da = 0;
                break;
            default:
                break;
        }
    }
}
