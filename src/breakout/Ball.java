package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Ball extends Element {
    private double dx;
    private double dy;
    private int radius;

    private Circle instance;

    public Ball(int x, int y, int alpha, int speed, int radius, Paint fill, MovingDirection direction) {
        super(x, y, alpha, speed, fill, direction);
        this.radius = radius;
        makeShape();
    }

    public Ball(int x, int y, int speed, int radius, Paint fill) {
        this(x, y, 60, speed, radius, fill, MovingDirection.STAY);
    }

    @Override
    public void makeShape() {
        instance = new Circle(x, y, radius);
        instance.setFill(fill);
    }

    @Override
    public void updateDirection() {
        dx = Math.cos(Math.toRadians(alpha));
        dy = Math.sin(Math.toRadians(alpha));

        switch (direction) {
            case UPRIGHT:
                dy = -dy;
                break;
            case UPLEFT:
                dx = -dx;
                dy = -dy;
                break;
            case DOWNLEFT:
                dx = -dx;
            case DOWNRIGHT:
                // Do not have to change
                break;
            case STAY:
                dx = 0;
                dy = 0;
            default:
                break;
        }
    }

    @Override
    public Circle getInstance() throws NullPointerException {
        if (instance == null) {
            throw new NullPointerException("Instance has not been created");
        }
        return instance;
    }

    public void move(double elapsedTime) {
        instance.setCenterX(instance.getCenterX() + dx * elapsedTime * speed);
        instance.setCenterY(instance.getCenterY() + dy * elapsedTime * speed);
    }

    public void paddleCollision() {
        direction = direction == MovingDirection.DOWNLEFT ? MovingDirection.UPRIGHT : MovingDirection.UPLEFT;
        updateDirection();
    }

    public boolean hitBoundary(int width, int height) {
        return (x - radius < 0) || (x + radius > width) || (y + radius > height);
    }

    public void boundaryCollision() {

    }
}
