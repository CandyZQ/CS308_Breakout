package breakout;

import javafx.scene.image.ImageView;

public class Paddle {
    private int x;
    private int y;
    private int angle;
    private int speed;

    private int dx;
    private int da;

    private MovingDirection direction;
    private ElementOperations paddleOperation;

    public Paddle(int x, int y, int angle, int speed, MovingDirection direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.angle = angle;
        this.direction = direction;

        paddleOperation = new ElementOperations();
        updateDirection();
    }

    public Paddle(int x, int y, int speed) {
        this(x, y, 0, speed, MovingDirection.RIGHT);
    }

    public ImageView getInstance(String pic) {
        return paddleOperation.setupElement(pic, x, y);
    }

    public void updateDirection() {
    }

    public void setDirection(MovingDirection direction) {
        this.direction = direction;
    }
}
