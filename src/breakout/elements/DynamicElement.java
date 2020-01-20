package breakout.elements;

import breakout.directions.MovingDirection;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

/**
 * Dynamic Element is the base class for all game elements moving
 * at an angle and changes direction while moving. In addition
 * to state information stores in {@link Element}, this class
 * has additional states:
 *  <ul>
 *      <li>The angle of movement</li>
 *      <li>The color of this dynamic element</li>
 *  </ul>
 * <p></p>
 * This class assumes that {@link Element} creates a {@link Shape}
 * as its {@link Element#instance}. {@link Paint} is thus used to define
 * the color of this dynamic element.
 * @author Cady Zhou
 * @version 1.1
 * @since 1.1
 * @see Element
 */
public abstract class DynamicElement extends Element {
    double angle;
    Paint fill;

    /**
     * Creates a new instance of Dynamic Element.
     */
    public DynamicElement() {
        super();
    }

    /**
     * Creates a new instance of Dynamic Element with the given position,
     * fill,and moving information.
     * @param x                 horizontal position of this element
     * @param y                 vertical position of this element
     * @param angle             angle relative to the vertical line
     * @param speed             speed of this element
     * @param fill              a {@link Paint} represents color of
     *                          this element
     * @param movingDirection   moving direction of this element
     */
    public DynamicElement(double x, double y, double angle, int speed, Paint fill, MovingDirection movingDirection) {
        super(x, y, speed, movingDirection);

        this.angle = angle;
        this.fill = fill;
        updateAxesSpeed();
    }

    /**
     * Sets moving direction of this element.
     * @param movingDirection new moving direction of this element
     */

    public void setMovingDirection(MovingDirection movingDirection) {
        this.movingDirection = movingDirection;
        updateAxesSpeed();
    }

    /**
     * Creates a new instance {@link Shape} and assigns to
     * {@link Element#instance}. All available states, including
     * position, movement and color, are assigned if applicable. This
     * method is typically called in a constructor of a child class.
     */
    public abstract void makeShape();

    /**
     * Updates speed of this {@code instance} on all axes (for example,
     * dx and dy). This method is typically called after a direction change.
     * @see #setMovingDirection(MovingDirection) 
     */
    public abstract void updateAxesSpeed();
}
