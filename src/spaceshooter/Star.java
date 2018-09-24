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
    int Xmax = 900;
    int Ymax = 700;
    //int Xmax2 = 900;
    //int Xmin2 = 890;
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
    
    public int getX() {
    	return x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public void setTam(int sit) {
    	if (sit == 1) { 
    		width = 8;
    		height = 8;
    	}else {
    		width = 5;
    		height = 5;
    	}
    }

    public void draw(Graphics2D g2d, double width, double height) {	
    	g2d.drawOval(300, 300, (int)width, (int)height);
    	g2d.setColor(Color.WHITE);
    }
    
    public Rectangle getBounds() {
        return new Rectangle((x), (y), (width), (height));
    }
    
    public void setLocation() {
    	y = rn.nextInt(Ydiff + 1);
    	x = 900;
    			
    }

}
