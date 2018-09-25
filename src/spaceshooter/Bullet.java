package spaceshooter;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Bullet {

    Image image;
    int x;
    int y;
    boolean fired = false;
    double theta;

    public Bullet(int x, int y, double theta) {
        if (image == null) {
            image = new ImageIcon(this.getClass().getResource("bullet2.png")).getImage();
        }

    }

    public Bullet(int i) {
        if (image == null) {
            image = new ImageIcon(this.getClass().getResource("bullet3.png")).getImage();
        }
    }

    public Image getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }

}
