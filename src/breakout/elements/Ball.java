package breakout.elements;

import breakout.Engine;
import breakout.directions.CollisionDirection;
import breakout.directions.MovingDirection;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * This Ball class defines the ball with specified position, speed,
 * and moving information. This ball moves and bounces following physics
 * laws. By default, this ball sits in the middle of the horizontal axis,
 * on top of the paddle.
 * <p></p>
 *
 * Example code: the following code creates a pink-colored Ball with
 * radius 5, starting at (0,0) and moving towards right at normal speed.
 * <p></p>
 * {@code
 *  import breakout.elements.Ball;
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
public class Ball extends DynamicElement {
    public static final int BALL_RADIUS = 8;
    public static final Paint BALL_COLOR = Color.BLACK;

    public static final int BALL_SPEED_NORMAL = 400;
    public static final int BALL_SPEED_INCREMENT = 600;
    public static final int BALL_SPEED_FAST = 800;
    public static final int BALL_SPEED_UP_CYCLE = 200;

    private double dx;
    private double dy;
    private double radius;
    private double initialAngle;

    private CollisionDirection collisionDirection;

    /**
     * Creates a new instance of Ball with default setting.
     */
    public Ball() {
        this(Engine.BG_WIDTH / 2.0,
                Engine.BG_HEIGHT - Paddle.PADDLE_OFFSET_BOTTOM,
                30,
                BALL_SPEED_NORMAL,
                BALL_RADIUS,
                BALL_COLOR,
                MovingDirection.STAY);
    }

    /**
     * Creates a new instance of Ball with the given position,
     * fill,and moving information.
     * @param x                 horizontal position of this ball
     * @param y                 vertical position of this ball
     * @param angle             angle relative to the vertical line
     * @param speed             speed of this ball
     * @param radius            radius of this ball
     * @param fill              a {@link Paint} represents color of
     *                          this ball
     * @param movingDirection   moving direction of this ball
     */

    public Ball(double x, double y, double angle, int speed, double radius,
                Paint fill, MovingDirection movingDirection) {
        super(x, y - radius, angle, speed, fill, movingDirection);

        this.initialAngle = angle;
        this.radius = radius;
        collisionDirection = CollisionDirection.NO_COLLISION;
        makeShape();
    }

    /**
     * Assigns {@link Element#instance} a new instance of {@link Circle}
     * with given position and color.
     * @see DynamicElement#makeShape
     */
    @Override
    protected void makeShape() {
        instance = new Circle(x, y, radius);
        ((Circle) instance).setFill(fill);
    }

    /**
     * Updates ball speed on both horizontal and vertical axes.
     */
    @Override
    protected void updateAxesSpeed() {
        dx = Math.sin(Math.toRadians(Math.abs(angle)));
        dy = Math.cos(Math.toRadians(Math.abs(angle)));

        switch (movingDirection) {
            case UPRIGHT:
                dy = -dy;
                break;
            case UPLEFT:
                dx = -dx;
                dy = -dy;
                break;
            case DOWNLEFT:
                dx = -dx;
                break;
            case DOWNRIGHT:
                // Do not have to change
                break;
            case STAY:
                dx = 0;
                dy = 0;
                break;
            default:
                break;
        }
    }

    /**
     * Updates the position of this Ball according to its speed on
     * horizontal and vertical axes.
     * @param elapsedTime   time passed during one animation cycle
     */
    @Override
    public void move(double elapsedTime) {
        x = ((Circle) instance).getCenterX() + dx * elapsedTime * speed;
        y = ((Circle) instance).getCenterY() + dy * elapsedTime * speed;
        ((Circle) instance).setCenterX(x);
        ((Circle) instance).setCenterY(y);
    }

    /**
     * Changes moving direction after a collision.
     */
    public void changeDirection() {
        switch (collisionDirection) {
            case LEFTTORIGHT:
                movingDirection = movingDirection == MovingDirection.UPLEFT ? MovingDirection.UPRIGHT : MovingDirection.DOWNRIGHT;
                break;
            case UPTODOWN:
                movingDirection = movingDirection == MovingDirection.UPRIGHT ? MovingDirection.DOWNRIGHT : MovingDirection.DOWNLEFT;
                break;
            case RIGHTTOLEFT:
                movingDirection = movingDirection == MovingDirection.UPRIGHT ? MovingDirection.UPLEFT : MovingDirection.DOWNLEFT;
                break;
            case DOWNTOUP:
                movingDirection = movingDirection == MovingDirection.DOWNLEFT ? MovingDirection.UPLEFT : MovingDirection.UPRIGHT;
                break;
            default:
                break;
        }
        updateAxesSpeed();
        collisionDirection = CollisionDirection.NO_COLLISION;
    }

    /**
     * Checks if this ball hits a boundary and needs to perform bouncing.
     * @return true if this ball hits any boundary
     */
    public boolean isHitBoundary() {
        return ((x - radius < 0) || (x + radius > Engine.BG_WIDTH) ||
                (y - radius < 0)) || (y + radius > Engine.BG_HEIGHT); //TODO: delete the last condition
    }

    /**
     * Checks if speed is BALL_SPEED_FAST.
     * @return true if speed is BALL_SPEED_FAST
     */
    public boolean isSpeedFast() {
        return speed == BALL_SPEED_FAST;
    }

    /**
     * Checks if speed is BALL_SPEED_INCREMENT.
     * @return true if speed is BALL_SPEED_INCREMENT
     */
    public boolean isSpeedIncremented() {
        return speed == BALL_SPEED_INCREMENT;
    }

    /**
     * Gets the initial angle of this ball.
     * @return  a double represents the initial angle
     */
    public double getInitialAngle() {
        return initialAngle;
    }

    /**
     * Gets the radius of this ball.
     * @return  a double represents radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets collision direction after a collision.
     * @param collisionDirection the updated collision direction
     */
    public void setCollision(CollisionDirection collisionDirection) {
        this.collisionDirection = collisionDirection;
    }

    /**
     * Sets speed to BALL_SPEED_FAST.
     */
    public void setBallSpeedFast() {
        setSpeed(BALL_SPEED_FAST);
    }

    /**
     * Sets speed to BALL_SPEED_NORMAL.
     */
    public void setBallSpeedNormal() {
        setSpeed(BALL_SPEED_NORMAL);
    }

    /**
     * Sets speed to BALL_SPEED_INCREMENT.
     */
    public void setBallSpeedIncrement() {
        setSpeed(BALL_SPEED_INCREMENT);
    }
}
