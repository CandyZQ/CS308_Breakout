package breakout;

import javafx.scene.image.ImageView;

public class Paddle extends Element {
    private int dx;
    private int da;

    public Paddle(int x, int y, int angle, int speed, MovingDirection direction) {
        super(x, y, angle, speed, direction);

    }

    public Paddle(int x, int y, int speed) {
        this(x, y, 0, speed, MovingDirection.STAY);
    }

    @Override
    public void updateDirection() {
        switch (direction) {
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

    public void move(double elapsedTime) {
        instance.setX(instance.getX() + dx * elapsedTime * speed);
    }
}
