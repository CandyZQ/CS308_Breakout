package breakout;

import breakout.directions.MovingDirection;
import javafx.scene.Node;

/**
 * Element is an abstract base class used for all game elements.
 * An element object encapsulate the state information required to
 * define the position and movement of this element. This state
 * information includes:
 * <ul>
 *     <li>The x position</li>
 *     <li>The y position</li>
 *     <li>The speed this elements moves</li>
 *     <li>The direction of movement</li>
 * </ul>
 * <p>
 * Objects that extend this class should move during game. Otherwise,
 * simply creates a {@code Node} object.
 * <p>
 * This element object has a {@code javafx.scene.Node}, which can be
 * implemented as any specific {@code Node} (for example,
 * {@code ImageView}). This instance should be added to a {@code Group}
 * during {@code Scene} creation.
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
     * Sole constructor. (For invocation by subclass constructors,
     * typically implicit)
     */
    public Element() {
    }

    /**
     * Constructor. Initialize state information
     * @param x                 a double for x position of this element
     * @param y                 a double for y position of this element
     * @param speed             a double for speed of this element
     * @param movingDirection   the moving direction of this element
     */
    public Element(double x, double y, double speed, MovingDirection movingDirection) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.movingDirection = movingDirection;
    }

    /**
     * Returns the node that can be added as a child of {@code Scene}
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
     * @param elapsedTime   a double represents time passed for one animation
     */
    public abstract void move(double elapsedTime);

}

