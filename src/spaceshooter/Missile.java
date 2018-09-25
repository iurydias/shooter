package spaceshooter;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Missile {

    Image image;
    Image image1;
    Image image2;
    int x;
    int y;
    boolean fired = false;
    public Missile(int i) {
        if (image1 == null) {
            image1 = new ImageIcon(this.getClass().getResource("gui_missile.png")).getImage();
             image = image1;
        }
    }

    public Image getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }
     public void setImage() {
        image = image2;
    }

    public void setHealth() {
        image = image1;
    }

}
