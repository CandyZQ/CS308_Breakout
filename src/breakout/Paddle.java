package breakout;

import breakout.directions.MovingDirection;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Element {
    private int dx;
    private int da;
    private double width;
    private double height;

    private Rectangle instance;

    public Paddle(double x, double y, int angle, int speed, double width, double height, Paint fill, MovingDirection direction) {
        super(x, y, angle, speed, fill, direction);
        this.width = width;
        this.height = height;
        makeShape();
    }

    public Paddle(double x, double y, int speed, double width, double height, Paint fill) {
        this(x, y, 0, speed, width, height, fill, MovingDirection.STAY);
    }

    @Override
    public void updateMovingDirection() {
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

    @Override
    public void makeShape() {
        instance = new Rectangle(x, y, width, height);
        instance.setFill(fill);
    }

    @Override
    public Rectangle getInstance() throws NullPointerException {
        if (instance == null) {
            throw new NullPointerException("Instance has not been created");
        }
        return instance;
    }

    public void move(double elapsedTime, int sceneWidth) {
        x = instance.getX() + dx * elapsedTime * speed;
        x = x + width > sceneWidth ? sceneWidth - width: x;
        x = x < 0 ? 0 : x;
        instance.setX(x);
    }

    public double getHeight() {
        return height;
    }
}
