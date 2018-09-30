package spaceshooter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ResultPanel {
	
	boolean[] activated = new boolean[2];
	Point[] point = new Point[2];
	Image image;
	int button_w = 150;
	int button_h = 50;
	CanvasPanelImage game;
	Rectangle[] rectangle = new Rectangle[2];
	boolean clicked;
	boolean won = false;
	boolean painted = false;
	Random random;
	Integer score;
	

	public ResultPanel(CanvasPanelImage game) {
		for (int i = 0; i < 2; i++) {
			activated[i] = false;
		}
		for (int i = 0; i < 2; i++) {
			point[i] = new Point(375, 200 + 81*i);
			rectangle[i] = new Rectangle(point[i], new Dimension(button_w,
					button_h));
		}
			image = new ImageIcon(this.getClass().getResource("button.jpg")).getImage();
	

		this.game = game;
		
		random = new Random(10000);
		score = 0;
		

	}

	public void draw(Graphics2D g2d) {
		g2d.drawImage(game.backGround, 0, 0, null);
		g2d.drawImage(game.opacity, 0, 0, null);
		if(won){
			g2d.setColor(Color.GREEN);
			g2d.setFont(game.font);
			g2d.drawString("You WON!", 320, 100);
			g2d.setColor(Color.WHITE);
			g2d.setFont(game.font);
			//if(!painted) score = random.nextInt(10000);
			g2d.drawString(String.valueOf(game.score), 400, 160);
		}else{
			g2d.setColor(Color.RED);
			g2d.setFont(game.font);
			g2d.drawString("You LOST!", 320, 100);
			g2d.setColor(Color.WHITE);
			g2d.setFont(game.font);
			g2d.drawString(String.valueOf(game.score), 400, 160);
		}

		for (int i = 0; i < 2; i++) {
			if (activated[i]) {
				g2d.drawImage(image, point[i].x, point[i].y, null);
			}
		}
		
		g2d.setColor(Color.white);
		g2d.drawString("Menu", 375, 235);
		g2d.drawString("Exit", 375, 316);

	}

	public Rectangle getBounds(int i) {
		return rectangle[i];
	}

}
