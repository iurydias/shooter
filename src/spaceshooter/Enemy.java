package spaceshooter;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;

public class Enemy {

    Image image;
    int x;
    int y;
    boolean isAlive = true;
    double theta;
    int enemytype;
    int enemylife;
    int movement;
    int initialpy;
    Random rn = new Random();
    int j;
    int max = 2;
    int min = 1;
    int diff = max - min;
    // public Enemy() {
    //  if (image == null) {
    //      image = new ImageIcon(this.getClass().getResource("enemy1.png")).getImage();
    //  }
    //  enemytype = 1;
    // enemylife = 1;
    // setLocation();
    //}
    public Enemy(int i) {
        if (image == null) {
            if (i == 1) {
                image = new ImageIcon(this.getClass().getResource("enemy1.png")).getImage();
                enemytype = 1;
                enemylife = 1;
            } else if (i == 2) {
                image = new ImageIcon(this.getClass().getResource("enemy2.png")).getImage();
                enemytype = 2;
                enemylife = 1;
            } else if (i == 3) {
                image = new ImageIcon(this.getClass().getResource("enemy3.png")).getImage();
                enemytype = 3;
                enemylife = 3;
            } else if (i == 4) {
                image = new ImageIcon(this.getClass().getResource("enemy4.png")).getImage();
                enemytype = 4;
                enemylife = 5;
            }
        }
        setLocation();
    }

    public Image getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }

    public void setLocation() {
        int i, j, n, m;
        // i = (int) (Math.random() * 2 + 1);
        j = (int) (Math.random() * 2 + 1);
        // if (i == 1) {
        x = (int) (Math.random() * 500 + 900);
        // } else {
        //  x = (int) (Math.random() * 220 + 811);
        //}
        if (j == 1) {
            y = (int) (Math.random() * 300 + 1);
            if (enemytype == 3 || enemytype == 4) {
                j = rn.nextInt(diff + 1);
                j += min;
                movement = j;
                if (enemytype == 4) {
                    initialpy = y;
                }
            }
        } else if (j > 1.5) {
            y = (int) (Math.random() * 115 + 496);
            if (enemytype == 3 || enemytype == 4) {
                j = rn.nextInt(diff + 1);
                j += min;
                movement = j;
                if (enemytype == 4) {
                    initialpy = y;
                }
            }
        } else {
            y = (int) (Math.random() * 255 + 300);
            if (enemytype == 3 || enemytype == 4) {
                j = rn.nextInt(diff + 1);
                j += min;
                movement = j;
                if (enemytype == 4) {
                    initialpy = y;
                }
            }
        }

//		n = ((int) (Math.random() * 245));
//		m = ((int) (Math.random() * 140));
//		i = ((int) (Math.random() * 4)) + 1;
//		j = ((int) (Math.random() * 4)) + 1;
//
//		if (i == 2 && j == 2 || i == 2 && j == 3 || i == 3 && j == 2 || i == 3
//				&& j == 3) {
//
//			int l = ((int) (Math.random()));
//
//			if (l == 0) {
//
//				l = ((int) (Math.random()));
//				if (l == 0)
//					i = 1;
//				else
//					i = 4;
//
//			} else {
//
//				l = ((int) (Math.random()));
//				if (l == 0)
//					j = 1;
//				else
//					j = 4;
//
//			}
//		}
//
//		x = n * i;
//		y = m * j;
    }

}
