package breakout;

import breakout.directions.CollisionDirection;
import breakout.directions.MovingDirection;
import breakout.elements.Ball;
import breakout.bricks.Brick;
import breakout.elements.Paddle;

/**
 * This class is the controller that makes calculation and
 * state change when two or more game elements are involved.
 * Parameters for all methods should be game elements.
 * <p></p>
 *
 * Example code: the following code changes necessary fields afater
 * a ball collides a paddle.
 * <p></p>
 * {@code
 *  import breakout.Controller;
 *  import breakout.elements.*;
 *    Ball b = new Ball();
 *    Paddle p = new Paddle();
 *    Controller controller = new Controller();
 *
 *    if (b collides p) {
 *        controller.ballPaddleCollision(b, p);
 *    }
 * }
 *
 * @author Cady Zhou
 * @version 1.1
 * @since 1.1
 */
public class Controller {

    /**
     * Updates changed states, including speed, angle, and moving
     * direction of the ball, after a collision between the ball and
     * the paddle.
     * @param ball      the ball appearing on current scene
     * @param paddle    the ball appearing on current scene
     */
    public void ballPaddleCollision(Ball ball, Paddle paddle) {
        double alpha = paddle.getAngle();

        // If ball is at 1/4 on each side, speed up
        if (ball.getX() <= (paddle.getX() + Paddle.PADDLE_WIDTH / 4.0)
                || ball.getX() >= (paddle.getX() + Paddle.PADDLE_WIDTH / 4.0 * 3)) {
            ball.setBallSpeedIncrement();
        }

        ball.setCollision(CollisionDirection.DOWNTOUP);
        ball.setAngle(
                ball.getMovingDirection() == MovingDirection.DOWNRIGHT ?
                ball.getInitialAngle() + alpha : ball.getInitialAngle() - alpha);
        ball.changeDirection();
    }

    /**
     * Update changed states of this ball after colliding with a brick.
     * @param ball      the ball appearing ont he current scene
     * @param brick     the brick that collides with the ball
     */
    public void ballBrickCollision(Ball ball, Brick brick) {
        if (ball.isSpeedIncremented()) {
            ball.setBallSpeedNormal();
        }

        CollisionDirection cd;
        if (ball.getY() + ball.getRadius() <= brick.getY()) {
            cd = CollisionDirection.DOWNTOUP;
        } else if (ball.getY() - ball.getRadius()
                >= brick.getY() + Brick.BRICK_HEIGHT) {
            cd = CollisionDirection.UPTODOWN;
        } else if (ball.getX() < brick.getX()) {
            cd = CollisionDirection.RIGHTTOLEFT;
        } else if (ball.getX() > brick.getX() + Brick.BRICK_WIDTH) {
            cd = CollisionDirection.LEFTTORIGHT;
        } else {
            cd = CollisionDirection.LEFTTORIGHT;
            System.out.println("Warning: Invalid Brick Collision!");
        }

        ball.setCollision(cd);
        ball.changeDirection();
    }

    /**
     * Checks if this ball hits a boundary or is dead. If hits a boundary,
     * changes collision direction.
     * @return true if hits top/left/right boundary, false if dead
     */
    public boolean ballBoundaryCollision(Ball ball) {
        CollisionDirection cd;

        if (ball.getX() - ball.getRadius() < 0) {
             cd = CollisionDirection.LEFTTORIGHT;
        } else if (ball.getX() + ball.getRadius() > Engine.BG_WIDTH) {
            cd = CollisionDirection.RIGHTTOLEFT;
        } else if (ball.getY() - ball.getRadius() < 0) {
            cd = CollisionDirection.UPTODOWN;
        } else if (ball.getY() + ball.getRadius() > Engine.BG_HEIGHT) {
            return false;
        } else {
            throw new IllegalStateException("There is no collision!");
        }

        ball.setCollision(cd);
        ball.changeDirection();
        return true;
    }
}
