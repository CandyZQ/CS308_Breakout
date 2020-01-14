package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Element {
    private int dx;
    private int da;
    private int width;
    private int height;

    private Rectangle instance;

    public Paddle(int x, int y, int angle, int speed, int width, int height, Paint fill, MovingDirection direction) {
        super(x, y, angle, speed, fill, direction);
        this.width = width;
        this.height = height;
        makeShape();
    }

    public Paddle(int x, int y, int speed, int width, int height, Paint fill) {
        this(x, y, 0, speed, width, height, fill, MovingDirection.STAY);
    }

    @Override
    public void updateDirection() {
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

    public void move(double elapsedTime) {
        instance.setX(instance.getX() + dx * elapsedTime * speed);
    }
}
