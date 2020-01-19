package breakout;

import breakout.directions.MovingDirection;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public abstract class Element {
    double x;
    double y;
    double angle;
    int speed;
    Paint fill;

    MovingDirection movingDirection;

    public Element(double x, double y, double angle, int speed, Paint fill, MovingDirection movingDirection) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.angle = angle;
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
    public abstract void move(double elapsedTime);
}
