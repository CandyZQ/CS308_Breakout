package breakout.elements;

import breakout.directions.MovingDirection;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

/**
 * Element is an abstract base class used for all game elements.
 * An Element object encapsulate the state information required to
 * define the position and movement of this element. This state
 * information includes:
 * <ul>
 *     <li>The x position</li>
 *     <li>The y position</li>
 *     <li>The speed this elements moves</li>
 *     <li>The direction of movement</li>
 * </ul>
 * <p></p>
 * Objects that extend this class should move during game. Otherwise,
 * simply creates a {@link Node} object.
 * <p></p>
 * This element object has a {@link Node}, which can be
 * implemented as any specific {@link Node} (for example,
 * {@link ImageView}). This instance should be added to a {@link Group}
 * during {@link Scene} creation.
 * @author Cady Zhou
 * @version 1.1
 * @since 1.1
 * @see Node
 */
public abstract class Element {
    double x;
    double y;
    double speed;
    MovingDirection movingDirection;
    Node instance;

    /**
     * Creates a new instance of Element.
     */
    public Element() {
    }

    /**
     * Creates a new instance of Element with the given position
     * and moving information.
     * @param x                 horizontal position of this element
     * @param y                 vertical position of this element
     * @param speed             speed of this element
     * @param movingDirection   moving direction of this element
     */
    public Element(double x, double y, double speed, MovingDirection movingDirection) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.movingDirection = movingDirection;
    }

    /**
     * Gets the node that can be added as a child of {@code Scene}
     * @return                      a node of this element
     * @throws NullPointerException If this instance is not initialized
     */
    public Node getInstance() throws NullPointerException {
        return instance;
    }

    /**
     * Defines movement of this element during {@code elapsedTime}.
     * This method updates any element state changes within a short
     * amount of time, usually called by an event handler of {@code KeyFrame}.
     * <p>
     * Note, {@code elapsedTime} is used to ensure constant speed
     * achieved across machines.
     * @param elapsedTime   time passed during one animation cycle
     */
    public abstract void move(double elapsedTime);

}

