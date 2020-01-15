package breakout;


import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Brick{
    public static final int BRICK_WIDTH = Main.BG_WIDTH / BrickPane.COL_NUM;
    public static final int BRICK_HEIGHT = 20;

    public static final int UNREMOVABLE = 9;

    public static final Paint COLOR_LEVEL_ONE_BRICK = Color.MISTYROSE;
    public static final Paint COLOR_LEVEL_TWO_BRICK = Color.LIGHTPINK;
    public static final Paint COLOR_LEVEL_THREE_BRICK = Color.PINK;
    public static final Paint COLOR_LEVEL_FOUR_BRICK = Color.PALEVIOLETRED;
    public static final Paint COLOR_LEVEL_FIVE_BRICK = Color.SALMON;
    public static final Paint COLOR_UNREMOVABLE = Color.DARKGRAY;
    public static final Paint COLOR_NO_BRICK = Color.TRANSPARENT;

    private int level;
    private Rectangle rectangle;

    public Brick(int level) {
        this.level = level;
        rectangle = new Rectangle(BRICK_WIDTH, BRICK_HEIGHT);
        setColor();
    }


    public void makeDamage() {
        if (level == UNREMOVABLE) return;
        level -= 1;
        setColor();
    }

    private boolean setColor() {
        switch (level) {
            case 0:
                return shouldRemove();
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
                break;
        }
        return false;
    }

    public boolean shouldRemove() {
        return level == 0;
    }

    public Rectangle getInstance() {
        return rectangle;
    }

    public Paint getColor() {
        return rectangle.getFill();
    }
}
