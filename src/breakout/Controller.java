package breakout;

import breakout.directions.CollisionDirection;
import breakout.directions.MovingDirection;
import breakout.elements.Ball;
import breakout.elements.Brick;
import breakout.elements.Paddle;

public class Controller {

    /**
     * Updates changed states, including speed, angle, and moving
     * direction of the ball, after a collision between the ball and
     * the paddle.
     * @param ball      the ball appearing on current scene
     * @param alpha     the angle of paddle
     * @param paddleX   the x position of paddle
     */
    public void ballPaddleCollision(Ball ball, double alpha, double paddleX) {
        // If ball is at 1/4 on each side, speed up
        if (ball.getX() <= (paddleX + Paddle.PADDLE_WIDTH / 4.0) || ball.getX() >= (paddleX + Paddle.PADDLE_WIDTH / 4.0 * 3)) {
            ball.setBallSpeedIncrement();
        }

        ball.setCollision(CollisionDirection.DOWNTOUP);
        ball.setAngle(
                ball.getMovingDirection() == MovingDirection.DOWNRIGHT ?
                ball.getInitialAngle() + alpha : ball.getInitialAngle() - alpha);
        ball.changeDirection();
    }

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
        boolean hit = true;

        if (ball.getX() - ball.getRadius() < 0) {
             cd = CollisionDirection.LEFTTORIGHT;
        } else if (ball.getX() + ball.getRadius() > Engine.BG_WIDTH) {
            cd = CollisionDirection.RIGHTTOLEFT;
        } else if (ball.getY() - ball.getRadius() < 0) {
            cd = CollisionDirection.UPTODOWN;
        } else if (ball.getY() + ball.getRadius() > Engine.BG_HEIGHT) {
            cd = CollisionDirection.DOWNTOUP; // TODO: delete this!
            ball.setCollision(cd);
            ball.changeDirection();
            hit = false;
        } else {
            throw new IllegalStateException("There is no collision!");
        }

        ball.setCollision(cd);
        ball.changeDirection();
        return hit;
    }
}
