package breakout;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Brick{
    public static final int BRICK_WIDTH = Main.BG_WIDTH / BrickPane.COL_NUM;
    public static final int BRICK_HEIGHT = 30;

    public static final int UNREMOVABLE = 9;

    public static final Paint COLOR_LEVEL_ONE_BRICK = Color.MISTYROSE;
    public static final Paint COLOR_LEVEL_TWO_BRICK = Color.LIGHTPINK;
    public static final Paint COLOR_LEVEL_THREE_BRICK = Color.PINK;
    public static final Paint COLOR_LEVEL_FOUR_BRICK = Color.PALEVIOLETRED;
    public static final Paint COLOR_LEVEL_FIVE_BRICK = Color.SALMON;
    public static final Paint COLOR_UNREMOVABLE = Color.DARKGRAY;

    private int level;
    private Rectangle rectangle;


    public Brick(int level) {
        this.level = level;
        rectangle = new Rectangle(BRICK_WIDTH - BrickPane.BRICK_GAP, BRICK_HEIGHT);
        setColor();
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


}
