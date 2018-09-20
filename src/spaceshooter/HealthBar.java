package spaceshooter;

import java.awt.Image;
import javax.swing.ImageIcon;

public class HealthBar {

    Image image1;
    Image image2;
    Image image;
    int x;
    int y;
    boolean health = false;

    public HealthBar() {
        if (image1 == null) {
            image1 = new ImageIcon(this.getClass().getResource("gui_spaceship.png")).getImage();
            image = image1;
        }

    }

    public Image getImage() {
        return image;
    }

    public void setImage() {
        image = image2;
    }

    public void setHealth() {
        image = image1;
    }

}
