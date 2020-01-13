package breakout;


import javafx.scene.image.ImageView;

public class Ball {
    private int x;
    private int y;
    private int alpha;
    private int speed;

    private int dx;
    private int dy;

    private MovingDirection direction;
    private ElementOperations ballOperation;

    public Ball(int x, int y, int alpha, int speed, MovingDirection direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.alpha = alpha;
        this.direction = direction;

        ballOperation = new ElementOperations();
        updateDirection();
    }

    public Ball(int x, int y, int speed) {
        this(x, y, 60, speed, MovingDirection.UPRIGHT);
    }


    public ImageView getInstance(String pic) {
        return ballOperation.setupElement(pic, x, y);
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

    public void setDirection(MovingDirection direction) {
        this.direction = direction;
    }
}
