package breakout;

import breakout.directions.MovingDirection;
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
        updateMovingDirection();
    }

    public abstract void makeShape();
    public abstract Shape getInstance() throws NullPointerException;

    public void setMovingDirection(MovingDirection movingDirection) {
        this.movingDirection = movingDirection;
        updateMovingDirection();
    }

    public abstract void updateMovingDirection();
}
