package spaceshooter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Random;
import javax.swing.ImageIcon;


public class Star {
	Random rn = new Random();
    Image image;
    int x, y, width = 5, height = 5 ;
    int j;
    int Xmax = 890;
    int Ymax = 700;
    int Xmax2 = 900;
    int Xmin2 = 890;
    int min = 1;
    int Xdiff = Xmax - min;
    int Ydiff = Ymax - min;
    public Star(){
    	x = rn.nextInt(Xdiff + 1);
    	y =	rn.nextInt(Ydiff + 1);
    }
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void draw(Graphics2D g2d, double width, double height) {	
    	g2d.drawOval(300, 300, (int)width, (int)height);
    	g2d.setColor(Color.WHITE);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
//    public void setLocation() {
//        int i, j, n, m;
//        if (j == 1) {
//            y = (int) (Math.random() * 300 + 1);
//            if (enemytype == 3 || enemytype == 4) {
//                j = rn.nextInt(diff + 1);
//                j += min;
//                movement = j;
//                if (enemytype == 4) {
//                    initialpy = y;
//                }
//            }
//        } else if (j > 1.5) {
//            y = (int) (Math.random() * 115 + 496);
//            if (enemytype == 3 || enemytype == 4) {
//                j = rn.nextInt(diff + 1);
//                j += min;
//                movement = j;
//                if (enemytype == 4) {
//                    initialpy = y;
//                }
//            }
//        } else {
//            y = (int) (Math.random() * 255 + 300);
//            if (enemytype == 3 || enemytype == 4) {
//                j = rn.nextInt(diff + 1);
//                j += min;
//                movement = j;
//                if (enemytype == 4) {
//                    initialpy = y;
//                }
//            }
//        }
//
//    }

}
