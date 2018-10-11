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
			} else if (i == 5) {
				image = new ImageIcon(this.getClass().getResource("boss2.gif")).getImage();
				enemytype = 5;
				enemylife = 10;
                                movement = 1;
				setLocation(true);
			}
                        else if (i == 6) {
				image = new ImageIcon(this.getClass().getResource("enemy5.png")).getImage();
				enemytype = 6;
				enemylife = 6;
			}
		}
		if (i != 5) {
			setLocation(false);
		}
	}

	public Image getImage() {
		return image;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}

	public void setLocation(Boolean boss) {
		if (boss) {
			x = 900;
			y = 200;
		} else {
			int i, j, n, m;
			j = (int) (Math.random() * 2 + 1);
			x = (int) (Math.random() * 500 + 900);

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
		}

	}

}
