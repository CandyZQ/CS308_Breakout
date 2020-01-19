package breakout;

import breakout.directions.CollisionDirection;
import breakout.directions.MovingDirection;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball extends Element {
    public static final Paint BALL_COLOR = Color.BLACK;
    public static final int BALL_RADIUS = 8;

    public static final int BALL_SPEED_NORMAL = 400;
    public static final int BALL_SPEED_INCREMENT = 600;
    public static final int BALL_SPEED_FAST = 800;
    public static final int BALL_SPEED_UP_CYCLE = 200;

    private double dx;
    private double dy;
    private double radius;

    private Circle instance;
    private CollisionDirection collisionDirection;
    private double initialAngle;

    public Ball(double x, double y, double angle, int speed, double radius, Paint fill, MovingDirection direction) {
        super(x, y - radius, angle, speed, fill, direction);
        initialAngle = angle;
        this.radius = radius;
        makeShape();
        collisionDirection = CollisionDirection.NO_COLLISION;
    }

    public Ball() {
        this((double) Engine.BG_WIDTH / 2, Engine.BG_HEIGHT - Paddle.PADDLE_OFFSET_BOTTOM, 30, BALL_SPEED_NORMAL, BALL_RADIUS, BALL_COLOR, MovingDirection.STAY);
    }

    @Override
    public void makeShape() {
        instance = new Circle(x, y, radius);
        instance.setFill(fill);
    }

    @Override
    public void updateMovingDirection() {
        dx = Math.sin(Math.toRadians(Math.abs(angle)));
        dy = Math.cos(Math.toRadians(Math.abs(angle)));

        switch (movingDirection) {
            case UPRIGHT:
                dy = -dy;
                break;
            case UPLEFT:
                dx = -dx;
                dy = -dy;
                break;
            case DOWNLEFT:
                dx = -dx;
                break;
            case DOWNRIGHT:
                // Do not have to change
                break;
            case STAY:
                dx = 0;
                dy = 0;
                break;
            default:
                break;
        }
//        System.out.println("dx: " + dx);
//        System.out.println("dy: " + dy);
    }

    @Override
    public Circle getInstance() throws NullPointerException {
        if (instance == null) {
            throw new NullPointerException("Instance has not been created");
        }
        return instance;
    }

    @Override
    public void move(double elapsedTime) {
        x = instance.getCenterX() + dx * elapsedTime * speed;
        y = instance.getCenterY() + dy * elapsedTime * speed;
        instance.setCenterX(x);
        instance.setCenterY(y);
    }

    public void paddleCollision(double alpha, double paddleX) {
        if (x <= (paddleX + Paddle.PADDLE_WIDTH / 4.0) || x >= (paddleX + Paddle.PADDLE_WIDTH / 4.0 * 3)) {
            speed = BALL_SPEED_INCREMENT;
        }
        collisionDirection = CollisionDirection.DOWNTOUP;
        angle = movingDirection == MovingDirection.DOWNRIGHT ? initialAngle + alpha : initialAngle - alpha;
        changeDirection();
    }

    public boolean hitBoundary() {
//        System.out.println("x: " + x);
//        System.out.println("y: " + y);
        return ((x - radius < 0) || (x + radius > Engine.BG_WIDTH) || (y - radius < 0)) || (y + radius > Engine.BG_HEIGHT); //TODO: delete the last condition
    }

    public void changeDirection() {
        switch (collisionDirection) {
            case LEFTTORIGHT:
//                System.out.println("LEFTTORIGHT");
//                System.out.println("Moving direction: " + movingDirection);
//                System.out.println("Angle: " + angle);
                movingDirection = movingDirection == MovingDirection.UPLEFT ? MovingDirection.UPRIGHT : MovingDirection.DOWNRIGHT;
                break;
            case UPTODOWN:
                movingDirection = movingDirection == MovingDirection.UPRIGHT ? MovingDirection.DOWNRIGHT : MovingDirection.DOWNLEFT;
                break;
            case RIGHTTOLEFT:
//                System.out.println("RIGHTOTLEFT");
//                System.out.println("Moving direction: " + movingDirection);
//                System.out.println("Angle: " + angle);
                movingDirection = movingDirection == MovingDirection.UPRIGHT ? MovingDirection.UPLEFT : MovingDirection.DOWNLEFT;
                break;
            case DOWNTOUP:
                movingDirection = movingDirection == MovingDirection.DOWNLEFT ? MovingDirection.UPLEFT : MovingDirection.UPRIGHT;
                break;
            default:
                break;
        }
        updateMovingDirection();
        collisionDirection = CollisionDirection.NO_COLLISION;
    }

    // return true if collide a boundary, false if dead
    public boolean boundaryCollision() {
        if (x - radius < 0) {
            collisionDirection = CollisionDirection.LEFTTORIGHT;
        } else if (x + radius > Engine.BG_WIDTH) {
            collisionDirection = CollisionDirection.RIGHTTOLEFT;
        } else if (y - radius < 0) {
            collisionDirection = CollisionDirection.UPTODOWN;
        } else if (y + radius > Engine.BG_HEIGHT) {
            collisionDirection = CollisionDirection.DOWNTOUP; // TODO: delete this!
            changeDirection();
            return false;
        } else {
            throw new IllegalStateException("There is no collision!");
        }
        changeDirection();
        return true;
    }

    public void brickCollision(Brick brick) {
        if (isSpeedIncreamented()) setBallSpeedNormal();
        if (y + radius <= brick.getY()) {
            collisionDirection = CollisionDirection.DOWNTOUP;
        } else if (y - radius >= brick.getY() + Brick.BRICK_HEIGHT) {
            collisionDirection = CollisionDirection.UPTODOWN;
        } else if (x < brick.getX()) {
            collisionDirection = CollisionDirection.RIGHTTOLEFT;
        } else if (x > brick.getX() + Brick.BRICK_WIDTH) {
            collisionDirection = CollisionDirection.LEFTTORIGHT;
        } else {
            collisionDirection = CollisionDirection.LEFTTORIGHT;
            System.out.println("Warning: Invalid Brick Collision!");
        }
        changeDirection();
    }

    public void setBallSpeedFast() {
        speed = BALL_SPEED_FAST;
    }

    public void setBallSpeedNormal() {
        speed = BALL_SPEED_NORMAL;
    }

    public boolean isSpeedFast() {
        return speed == BALL_SPEED_FAST;
    }

    public boolean isSpeedIncreamented() {
        return speed == BALL_SPEED_INCREMENT;
    }
}
