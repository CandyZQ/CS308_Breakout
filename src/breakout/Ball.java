package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball extends Element {
    private int dx;
    private int dy;

    public Ball(int x, int y, int alpha, int speed, MovingDirection direction) {
        super(x, y, alpha, speed, direction);
    }

    public Ball(int x, int y, int speed) {
        this(x, y, 60, speed, MovingDirection.UPRIGHT);
    }

    public void updateDirection() {
        dx = (int) Math.cos(alpha);
        dy = (int) Math.sin(alpha);

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
            default:
                break;
        }
    }

    public void move(double elapsedTime) {
        instance.setX(instance.getX() + dx * elapsedTime * speed);
        instance.setY(instance.getY() + dy * elapsedTime * speed);
    }
}
