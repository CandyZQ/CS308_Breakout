package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ElementOperations {

    public ImageView setupElement(String picture, int x, int y) {
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(picture));
        ImageView imageView = new ImageView(image);
        imageView.setX(x);
        imageView.setY(y);
        return imageView;
    }


}
