package breakout;

import javafx.scene.image.ImageView;

public class Element {
    int x;
    int y;
    int alpha;
    int speed;

    MovingDirection direction;
    ElementOperations operation;

    ImageView instance;

    public Element(int x, int y, int alpha, int speed, MovingDirection direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.alpha = alpha;
        this.direction = direction;
        instance = null;

        operation = new ElementOperations();
        updateDirection();
    }

    public ImageView getInstance(String pic) {
        instance = operation.setupElement(pic, x, y);
        return instance;
    }

    public ImageView getInstance() throws NullPointerException {
        if (instance == null) {
            throw new NullPointerException("Instance has not been created");
        }
        return instance;
    }


    public void updateDirection() {
    }

    public void setDirection(MovingDirection direction) {
        this.direction = direction;
    }
}
