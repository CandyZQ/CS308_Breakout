package breakout;

import breakout.directions.MovingDirection;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Element {
    public static final int PADDLE_OFFSET_BOTTOM = 50;
    public static final Paint PADDLE_COLOR = Color.YELLOW;
    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 10;
    public static final int PADDLE_SPEED_NORMAL = 300;

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

    public Paddle() {
        this(Level.BG_WIDTH / 2 - PADDLE_WIDTH / 2, Level.BG_HEIGHT - PADDLE_OFFSET_BOTTOM, 0, PADDLE_SPEED_NORMAL, PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_COLOR, MovingDirection.STAY);
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
