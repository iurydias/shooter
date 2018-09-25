package spaceshooter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class MenuPanel {

	String projectName = "SPACE SHOOTER";
	String myName = "By: Daniel, Iury and Vinicius";
	String about = "About";
	String help = "Help";
	String exit = "Exit";
	String play = "Play";
	String beginner = "Beginner";
	String intermediate = "Intermediate";
	String advance = "Advance";
	boolean[] activated = new boolean[7];
	Point[] point = new Point[7];
	Image image;
	int button_w = 150;
	int button_h = 50;
	CanvasPanelImage game;
	Rectangle[] rectangle = new Rectangle[7];
	boolean clicked;

	public MenuPanel(CanvasPanelImage game) {
		for (int i = 0; i < 7; i++) {
			activated[i] = false;
		}
		for (int i = 0; i < 4; i++) {
			point[i] = new Point(60 + i * 270, 200);
			rectangle[i] = new Rectangle(point[i], new Dimension(button_w,
					button_h));
		}

		for (int i = 0; i < 3; i++) {
			point[i + 4] = new Point(60, 281 + 81 * i);
			rectangle[i + 4] = new Rectangle(point[i+4], new Dimension(button_w,
					button_h));

		}

			image = new ImageIcon(this.getClass().getResource("button.jpg")).getImage();
		

		this.game = game;

	}

	public void draw(Graphics2D g2d) {
		g2d.drawImage(game.backGround, 0, 0, null);
		g2d.drawImage(game.opacity, 0, 0, null);
		g2d.setColor(Color.BLUE);
		g2d.setFont(game.font);
		g2d.drawString("SPACE SHOOTER", 250, 100);
		g2d.setColor(Color.WHITE);
                Font font = new Font(Font.MONOSPACED, Font.BOLD, 32);
		g2d.setFont(font);
		g2d.drawString("By: Daniel, Iury and Vinicius", 280, 600);
                g2d.setFont(game.font);
		for (int i = 0; i < 4; i++) {
			if (activated[i]) {
				g2d.drawImage(image, point[i].x, point[i].y, null);
			}
		}
		if (clicked) {
			g2d.drawImage(image, point[0].x, point[1].y, null);

			for (int i = 4; i < 7; i++) {
				if(activated[i])
				g2d.drawImage(image, point[i].x, point[i].y, null);
			}
			
			g2d.setColor(Color.WHITE);
			g2d.drawString("Easy", 93, 316);
			g2d.drawString("Normal", 80, 397);
			g2d.drawString("Hard", 93, 478);

		}
		g2d.setColor(Color.white);
		g2d.drawString("Play", 90, 235);
		g2d.drawString("Help", 365, 235);
		g2d.drawString("About", 630, 235);
		g2d.drawString("Exit", 905, 235);

	}

	public Rectangle getBounds(int i) {
		return rectangle[i];
	}

}
