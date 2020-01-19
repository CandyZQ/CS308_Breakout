package breakout;

import breakout.directions.MovingDirection;
import breakout.directions.RotationDirection;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Paddle extends Element {
    public static final int PADDLE_OFFSET_BOTTOM = 80;
    public static final Paint PADDLE_COLOR = Color.CORAL;

    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 10;
    public static final int PADDLE_ROTATION_SPEED = 100;
    public static final int PADDLE_SPEED_NORMAL = 300;
    public static final int PADDLE_SPEED_LEVEL_INCREMENT = 30;

    public static final double MAX_ANGLE = 40;
    public static final int PADDLE_SPEED_MAX = 250;

    private int dx;
    private int da;
    private double width;
    private double height;

    private Rectangle instance;
    private Rotate rotate;

    public Paddle(double x, double y, double angle, int speed, double width, double height, Paint fill, MovingDirection direction) {
        super(x, y, angle, speed, fill, direction);
        this.speed = Math.min(PADDLE_SPEED_MAX, speed);
        this.width = width;
        this.height = height;
        rotate = new Rotate();
        makeShape();
    }

    public Paddle(int level) {
        this(Engine.BG_WIDTH / 2 - PADDLE_WIDTH / 2, Engine.BG_HEIGHT - PADDLE_OFFSET_BOTTOM, 0,
                PADDLE_SPEED_NORMAL + level * PADDLE_SPEED_LEVEL_INCREMENT, PADDLE_WIDTH,
                PADDLE_HEIGHT, PADDLE_COLOR, MovingDirection.STAY);
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

    public void setRotationDirection(RotationDirection rotationDirection) {
        switch (rotationDirection) {
            case CC:
                da = 1;
                break;
            case CCW:
                da = -1;
                break;
            case FLOATING:
                da = 0;
                break;
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

    @Override
    public void move(double elapsedTime) {
        // movement
        x = instance.getX() + dx * elapsedTime * speed;
        x = x > Engine.BG_WIDTH ? 0 - width: x;
        x = x + width < 0 ? Engine.BG_WIDTH - width : x;
        instance.setX(x);

        instance.getTransforms().remove(rotate);
        // rotation
        angle = angle + da * elapsedTime * PADDLE_ROTATION_SPEED;
        if (Math.abs(angle) > MAX_ANGLE) {
            angle = angle > 0 ? MAX_ANGLE : -MAX_ANGLE;
        }
        rotate.setAngle(angle);
        rotate.setPivotX(x + (double) PADDLE_WIDTH / 2);
        rotate.setPivotY(y + PADDLE_HEIGHT);
        instance.getTransforms().add(rotate);
    }

    public double getAngle() {
        return angle;
    }

    public double getX() {
        return x;
    }
}
