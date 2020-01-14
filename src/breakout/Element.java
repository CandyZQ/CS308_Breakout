package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public abstract class Element {
    double x;
    double y;
    int alpha;
    int speed;
    Paint fill;

    MovingDirection movingDirection;

    public Element(double x, double y, int alpha, int speed, Paint fill, MovingDirection movingDirection) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.alpha = alpha;
        this.movingDirection = movingDirection;
        this.fill = fill;
        updateDirection();
    }

    public abstract void makeShape();
    public abstract Shape getInstance() throws NullPointerException;

    public void setMovingDirection(MovingDirection movingDirection) {
        this.movingDirection = movingDirection;
        updateDirection();
    }

    public abstract void updateDirection();
}
