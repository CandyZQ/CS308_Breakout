package breakout;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class Element {
    int x;
    int y;
    int alpha;
    int speed;
    Paint fill;

    MovingDirection direction;

    public Element(int x, int y, int alpha, int speed, Paint fill, MovingDirection direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.alpha = alpha;
        this.direction = direction;
        this.fill = fill;
        updateDirection();
    }

    public abstract void makeShape();
    public abstract Shape getInstance() throws NullPointerException;

    public void setDirection(MovingDirection direction) {
        this.direction = direction;
        updateDirection();
    }

    public abstract void updateDirection();
}
