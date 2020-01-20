package breakout.elements;

import breakout.BrickPane;
import breakout.Engine;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Brick{
    public static final int BRICK_WIDTH = Engine.BG_WIDTH / BrickPane.COL_NUM;
    public static final int BRICK_HEIGHT = 30;

    public static final int UNREMOVABLE = 9;
    public static final int POWER_UP_CHANCE = 10;

    public static final Paint COLOR_LEVEL_ONE_BRICK = Color.web("#fcdef0");
    public static final Paint COLOR_LEVEL_TWO_BRICK = Color.web("#ffb0e1");
    public static final Paint COLOR_LEVEL_THREE_BRICK = Color.web("#ff80ce");
    public static final Paint COLOR_LEVEL_FOUR_BRICK = Color.web("#cd5a91");
    public static final Paint COLOR_LEVEL_FIVE_BRICK = Color.web("#96304c");
    public static final Paint COLOR_UNREMOVABLE = Color.DARKGRAY;

    private int level;
    private Rectangle rectangle;
    private PowerUp powerUp;


    public Brick(int level, int row, int col) {
        this.level = level;
        rectangle = new Rectangle(BRICK_WIDTH - BrickPane.BRICK_GAP, BRICK_HEIGHT);
        setColor();
        setupPowerUp(row, col);
    }

    private void setupPowerUp(int row, int col) {
        int d = new Random().nextInt(POWER_UP_CHANCE);
        if (d < PowerUp.POWER_UP_NUMBER) {
            powerUp = new PowerUp(d, BRICK_WIDTH * (col + 0.3), BRICK_HEIGHT * (row + 0.3));
        } else {
            powerUp = null;
        }
    }

    public boolean takeDamage() {
        if (level == UNREMOVABLE) return false;
        level -= 1;
        return setColor();
    }

    private boolean setColor() {
        switch (level) {
            case 1:
                rectangle.setFill(COLOR_LEVEL_ONE_BRICK);
                break;
            case 2:
                rectangle.setFill(COLOR_LEVEL_TWO_BRICK);
                break;
            case 3:
                rectangle.setFill(COLOR_LEVEL_THREE_BRICK);
                break;
            case 4:
                rectangle.setFill(COLOR_LEVEL_FOUR_BRICK);
                break;
            case 5:
                rectangle.setFill(COLOR_LEVEL_FIVE_BRICK);
                break;
            case UNREMOVABLE:
                rectangle.setFill(COLOR_UNREMOVABLE);
            default:
                return shouldRemove();
        }
        return false;
    }

    public boolean shouldRemove() {
        return level <= 0;
    }

    public Rectangle getInstance() {
        return rectangle;
    }
    public double getX() {
        return rectangle.getX();
    }

    public double getY() {
        return rectangle.getY();
    }


    public void powerUpMove(double elapsedTime) {
        powerUp.move(elapsedTime);
    }

    public boolean hasPowerUp() {
        return powerUp != null;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }
}
