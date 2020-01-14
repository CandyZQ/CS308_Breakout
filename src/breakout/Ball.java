package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball extends Element {
    private double dx;
    private double dy;

    public Ball(int x, int y, int alpha, int speed, MovingDirection direction) {
        super(x, y, alpha, speed, direction);
    }

    public Ball(int x, int y, int speed) {
        this(x, y, 60, speed, MovingDirection.STAY);
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

    public void move(double elapsedTime) {
        instance.setX(instance.getX() + dx * elapsedTime * speed);
        instance.setY(instance.getY() + dy * elapsedTime * speed);
    }
}
