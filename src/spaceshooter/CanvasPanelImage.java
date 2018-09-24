/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceshooter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @authors Iury, Vinicius and Daniel
 */
public class CanvasPanelImage extends JPanel implements Runnable {
    int turbotime;
    int thunderx = 900,
            thundery = 900;
    int turbo = 1;
    JFrame frame = new JFrame("Space Shooter");
    GameWindowHandler window;
    boolean inited = false;
    MenuPanel menuPanel;
    boolean mainPanel;
    boolean gameOver;
    boolean resultPanel;
    volatile boolean play;
    ResultPanel result;
    int score = 0;
    Font font = new Font(Font.MONOSPACED, Font.BOLD, 50);
    Image backGround;
    Image opacity;
    int maxEnemy = 4;
    int minEnemy = 1;
    int diffEnemy = maxEnemy - minEnemy;
    int maxMissile = 2;
    int minMissile = 1;
    int diffMissile = maxMissile - minMissile;
    private boolean[] key_states = new boolean[256];
    int speedOfBullets = 20;
    int timeForBullet = 1;
    int seconds = 90;
    double theta;
    double px = 0, py = 0;
    int numberOfEnemies = 15;
    int countEnemy = numberOfEnemies;
    int numberOfBullets = 25;
    int speedOfEnemies = 3;
    int numberOfEnemyBullets = 15;
    int gun = 1;
    int numberOfHearts = 8;
    int numberOfStars = 200;
    int remainingHearts = numberOfHearts;
    int numberOfSortMissiles = 10;
    int numberOfMissiles = 3;
    int remainingMissiles = numberOfMissiles;
    int numberOfMissilesToShot = 3;
    int control = 0;
    Random rn = new Random();

    Missile[] missiles;
    Missile[] sortmissiles;
    Thread thread;
    Enemy[] enemy;
    HealthBar[] hearts;
    Bullet[] bullets1;
    Bullet[] shotmissiles;
    Bullet[] enemyBullets;
    MouseDetector mouse;
    Star[] stars;
    Player player;

    private long diff, start = System.currentTimeMillis();

    public CanvasPanelImage() {
        setSize(900, 700);
        frame.setSize(900, 700);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        setFocusable(true);
        frame.setLocationRelativeTo(null);
        setBackground(Color.BLACK);
        frame.add(this);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (inited) {
            if (mainPanel) {
                menuPanel.draw(g2d);
            } else if (!gameOver) {
                g2d.drawImage(backGround, 0, 0, null);
                //carregando estrelas
                for(int i = 0; i< numberOfStars; i++) {
                	if(i<=150) {
                		g2d.setColor(Color.WHITE);
                		g2d.fillOval(stars[i].x, stars[i].y, stars[i].tam, stars[i].tam);
                	}else if(i<=180) {
                		g2d.setColor(Color.YELLOW);
                		g2d.fillOval(stars[i].x, stars[i].y, stars[i].tam, stars[i].tam);
                	}else {
                		g2d.setColor(Color.BLUE);
                    	g2d.fillOval(stars[i].x, stars[i].y, stars[i].tam, stars[i].tam);
                	}
                	
                }
                player.draw(g2d, theta);
                //carregando images de inimigos
                for (int v = 0; v < numberOfEnemies; v++) {
                    if (enemy[v].isAlive) {
                        g2d.drawImage(enemy[v].getImage(), enemy[v].x,
                                enemy[v].y, null);
                    }
                }
                //carregando as imagens dos misseis extras
                for (int v = 0; v < numberOfSortMissiles; v++) {
                    if (sortmissiles[v].fired) {
                        g2d.drawImage(sortmissiles[v].getImage(), sortmissiles[v].x,
                                sortmissiles[v].y, null);
                    }
                }
                //carregando as imagens das vidas
                for (int i = 0; i < numberOfHearts; i++) {
                    g2d.drawImage(hearts[i].getImage(), hearts[i].x + 40, 30, null);
                }
                //carregando as imagens dos misseis
                for (int i = 0; i < numberOfMissiles; i++) {
                    g2d.drawImage(missiles[i].getImage(), missiles[i].x + 700, 30, null);
                }
                g2d.setFont(font);
                g2d.drawString(String.valueOf(score), 420, 70);
                g2d.drawImage(player.getThunder(), thunderx, thundery, 90, 80, null);
                //carregando as imagens das balas
                for (int i = 0; i < numberOfBullets; i++) {
                    if (bullets1[i].fired) {
                        g2d.drawImage(bullets1[i].getImage(), bullets1[i].x,
                                bullets1[i].y, null);
                    }
                }
                //carregando as imagens das balas
                for (int i = 0; i < numberOfMissilesToShot; i++) {
                    if (shotmissiles[i].fired) {
                        g2d.drawImage(shotmissiles[i].getImage(), shotmissiles[i].x,
                                shotmissiles[i].y, null);
                    }
                }
                //carregando as imagens dos inimigos
                for (int i = 0; i < numberOfEnemyBullets; i++) {
                    if (enemyBullets[i].fired) {
                        g2d.drawImage(enemyBullets[i].getImage(), enemyBullets[i].x,
                                enemyBullets[i].y, null);
                    }
                }
            } else if (resultPanel) {
                result.draw(g2d);
            }
        }
    }

    private void init() {
        addKeyListener(new KeyboardAdapter());
        //double n;
        mouse = new MouseDetector(this);
        player = new Player();
        gameOver = true;
        mainPanel = true;
        resultPanel = false;
        inited = true;
        menuPanel = new MenuPanel(this);
        result = new ResultPanel(this);
        window = new GameWindowHandler(this);
        // setBackground(Color.BLACK);
        hearts = new HealthBar[numberOfHearts];
        for (int i = 0; i < numberOfHearts; i++) {
            hearts[i] = new HealthBar();
            hearts[i].x = 40 * i;
        }
        missiles = new Missile[numberOfMissiles];
        for (int i = 0; i < numberOfMissiles; i++) {
            missiles[i] = new Missile(1);
            missiles[i].x = 40 * i;
        }

        thread = new Thread(this);
        enemy = new Enemy[numberOfEnemies];
        for (int i = 0; i < numberOfEnemies; i++) {
            Random rn = new Random();
            int j = rn.nextInt(diffEnemy + 1);
            j += minEnemy;
            if (j == 1) {
                enemy[i] = new Enemy(j);
            } else if (j == 2) {
                enemy[i] = new Enemy(j);
            } else if (j == 3) {
                enemy[i] = new Enemy(j);
                //j = rn.nextInt(diff + 1);
                //j += min;
                //if (j == 1 || j == 2) {
                //    enemy[i].movement = 1;
                //} else if (j == 3 || j == 4) {
                //    enemy[i].movement = 2;
                //}
            } else if (j == 4) {
                enemy[i] = new Enemy(j);
            }
        }
        bullets1 = new Bullet[numberOfBullets];
        for (int i = 0; i < numberOfBullets; i++) {
            bullets1[i] = new Bullet(210, 200, 0);
        }
        sortmissiles = new Missile[numberOfSortMissiles];
        for (int i = 0; i < numberOfSortMissiles; i++) {
            sortmissiles[i] = new Missile(1);
        }

        shotmissiles = new Bullet[numberOfBullets];
        for (int i = 0; i < numberOfBullets; i++) {
            shotmissiles[i] = new Bullet(2);
        }
        enemyBullets = new Bullet[numberOfEnemyBullets];
        for (int i = 0; i < numberOfEnemyBullets; i++) {
            enemyBullets[i] = new Bullet(5);
        }
        stars = new Star[numberOfStars];
        for (int i = 0; i < numberOfStars; i++) {
       		stars[i] = new Star();
       }
    }

    private class KeyboardAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            key_states[e.getKeyCode()] = false;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            key_states[e.getKeyCode()] = true;
        }
    }

    public void run() {
        init();
        while (true) {

            main();

            while (!gameOver) {
                play();
            }

            result();
        }
    }

    public void play() {
        // System.out.println(player.y);
        while (play) {
        	for(int i = 0; i< numberOfStars; i++) {
        		switch(stars[i].tam){
        		    case 1:
        		    	stars[i].x -= 1;
        		    	break;
        		    case 2:
        		    	stars[i].x -= 2;
        		    	break;
        		    case 3:
        		    	stars[i].x -= 3;
        		    	break;
        		    case 4:
        		    	stars[i].x -= 4;
        		    	break;
        		    default:
        		    	stars[i].x -= 5;
        		    	break;
        		}
        		if (stars[i].x <= 0) {
        			stars[i].setLocation();
        		}
                 if (key_states[KeyEvent.VK_RIGHT] && player.x < 835) {
             		switch(stars[i].tam){
        		    case 1:
        		    	stars[i].x -= 2;
        		    	break;
        		    case 2:
        		    	stars[i].x -= 3;
        		    	break;
        		    case 3:
        		    	stars[i].x -= 4;
        		    	break;
        		    case 4:
        		    	stars[i].x -= 5;
        		    	break;
        		    default:
        		    	stars[i].x -= 6;
        		    	break;
        		}
                }
        	}
        	
            if (player.isAlive) {
                for (int i = 0; i < numberOfEnemies; i++) {
                    // enemy[i].y = enemy[i].y
                    //       - (int) (2 * Math.sin(enemy[i].theta));
                    if (enemy[i].enemytype == 1 || enemy[i].enemytype == 2) {
                        enemy[i].x = enemy[i].x - 1;
                    } else if (enemy[i].enemytype == 3) {
                        if (enemy[i].movement == 1) {
                            enemy[i].x = enemy[i].x - 1;
                            enemy[i].y = enemy[i].y + 1;
                        } else if (enemy[i].movement == 2) {
                            enemy[i].x = enemy[i].x - 1;
                            enemy[i].y = enemy[i].y - 1;
                        }
                    } else if (enemy[i].enemytype == 4) {
                        if (enemy[i].movement == 1) {
                            enemy[i].x = enemy[i].x - 1;
                            enemy[i].y = enemy[i].y + 1;
                        } else if (enemy[i].movement == 2) {
                            enemy[i].x = enemy[i].x - 1;
                            enemy[i].y = enemy[i].y - 1;
                        }
                        if (enemy[i].y > enemy[i].initialpy + 50) {
                            if (enemy[i].movement == 1) {
                                enemy[i].movement = 2;
                            } else {
                                enemy[i].movement = 1;
                            }
                        }
                        if (enemy[i].y < enemy[i].initialpy - 50) {
                            if (enemy[i].movement == 1) {
                                enemy[i].movement = 2;
                            } else {
                                enemy[i].movement = 1;
                            }
                        }
                    }
                    if (enemy[i].x < -100 || enemy[i].y < -100 || enemy[i].y > 700) {
                        enemy[i].setLocation();
                    }
                    if (enemy[i].isAlive
                            && remainingHearts != 0
                            && enemy[i].getBounds().intersects(
                                    player.getBounds())) {
                        enemy[i].isAlive = false;
                        countEnemy--;
                        hearts[--remainingHearts].setImage();
                    }
                }
            }
            for (int i = 0; i < numberOfBullets; i++) {
                if (bullets1[i].fired) {
                    for (int v = 0; v < numberOfEnemies; v++) {
                        if (enemy[v].isAlive) {
                            if (enemy[v].getBounds().intersects(
                                    bullets1[i].getBounds())) {
                                enemy[v].enemylife--;
                                if (enemy[v].enemylife == 0) {
                                    if (enemy[v].enemytype == 1) {
                                        score += 10;
                                    } else if (enemy[v].enemytype == 2) {
                                        score += 50;
                                    } else if (enemy[v].enemytype == 3) {
                                        score += 100;
                                    } else if (enemy[v].enemytype == 4) {
                                        score += 200;
                                    }
                                    enemy[v].isAlive = false;
                                    countEnemy--;
                                    int j;
                                    j = rn.nextInt(diffMissile + 1);
                                    j += minMissile;
                                    System.out.println(j);
                                    if (j == 1) {
                                        addMissile(enemy[v]);
                                    }
                                }
                                bullets1[i].fired = false;
                            }
                        }
                    }
                    if (bullets1[i].x < -100 || bullets1[i].x > 1180
                            || bullets1[i].y < -100 || bullets1[i].y > 760) {
                        bullets1[i].fired = false;
                    } else {
                        // bullets[i].y = bullets[i].y
                        //   + (int) (speedOfBullets * bullets[i].sin);

                        bullets1[i].x = bullets1[i].x + 1 * 10;
                    }
                }
            }
            
         
            for (int i = 0; i < numberOfEnemyBullets; i++) {
                if (enemyBullets[i].fired) {
                    if (player.getBounds().intersects(
                            enemyBullets[i].getBounds())) {
                        hearts[--remainingHearts].setImage();
                        enemyBullets[i].fired = false;
                    }
                    if (enemyBullets[i].x < -200 || enemyBullets[i].x > 1300
                            || enemyBullets[i].y < -200 || enemyBullets[i].y > 1000) {
                        enemyBullets[i].fired = false;
                    } else {
                        enemyBullets[i].x = enemyBullets[i].x - 1 * 10;
                    }
                }
            }
            for (int i = 0; i < numberOfSortMissiles; i++) {
                if (player.getBounds().intersects(
                        sortmissiles[i].getBounds())) {
                    sortmissiles[i].fired = false;
                    sortmissiles[i].x = 900;
                    sortmissiles[i].y = 900;
                    for (int l = 0; l < numberOfMissilesToShot; l++) {
                        if (shotmissiles[l].fired == true) {
                            shotmissiles[l].fired = false;
                            missiles[remainingMissiles].setHealth();
                            ++remainingMissiles;
                            break;
                        }
                    }

                }
            }

            for (int i = 0; i < numberOfMissilesToShot; i++) {
                if (shotmissiles[i].fired) {
                    for (int v = 0; v < numberOfEnemies; v++) {
                        if (enemy[v].isAlive) {
                            if (enemy[v].getBounds().intersects(
                                    shotmissiles[i].getBounds())) {
                                if (enemy[v].enemytype == 1) {
                                    score += 10;
                                } else if (enemy[v].enemytype == 2) {
                                    score += 50;
                                } else if (enemy[v].enemytype == 3) {
                                    score += 100;
                                } else if (enemy[v].enemytype == 4) {
                                    score += 200;
                                }
                                enemy[v].isAlive = false;
                                countEnemy--;
                                int j;
                                j = rn.nextInt(diffMissile + 1);
                                j += minMissile;
                                System.out.println(j);
                                if (j == 1) {
                                    addMissile(enemy[v]);
                                }
                                shotmissiles[i].x = 900;
                                shotmissiles[i].y = 900;

                            }
                        }
                    }
                    shotmissiles[i].x = shotmissiles[i].x + 1 * 10;
                }
            }

            if (timeForBullet % seconds == 0) {
                int rand = (int) (Math.random() * numberOfEnemies);
                if (enemy[rand].isAlive) {
                    addEnemyBullet(enemy[rand]);
                }
                turbotime++;
            }
            if (turbotime == 6){
                thunderx = 150;
                thundery = 3;
                turbo = 5;
            }
            if (remainingHearts == 0) {
                result.won = false;
                play = false;
                gameOver = true;
                resultPanel = true;
            }
            if (countEnemy == 0) {
                result.won = true;
                play = false;
                gameOver = true;
                resultPanel = true;
            }
            if (key_states[KeyEvent.VK_UP] && player.y > 0) {
                player.y = player.y - turbo * 5;
            }
            if (key_states[KeyEvent.VK_DOWN] && player.y < 635) {
                player.y = player.y + turbo * 5;
            }
            if (key_states[KeyEvent.VK_LEFT] && player.x > 0 ){
                player.x = player.x - turbo * 5;
            }
            if (key_states[KeyEvent.VK_RIGHT] && player.x < 835) {
                player.x = player.x + turbo * 5;
                
            }
            repaint();
            sleep(60);
            timeForBullet++;
        }
    }

    public void addBullet(int x) {
        if (x == 1) {
            if (gun == 1) {
                for (int i = 0; i < numberOfBullets; i++) {
                    if (!bullets1[i].fired) {
                        bullets1[i].x = (int) player.x + 50;
                        bullets1[i].y = (int) player.y + 10;
                        bullets1[i].fired = true;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < numberOfBullets; i++) {
                    if (!shotmissiles[i].fired) {
                        shotmissiles[i].x = (int) player.x + 50;
                        shotmissiles[i].y = (int) player.y + 10;
                        shotmissiles[i].fired = true;
                        break;
                    }
                }
            }
        }
    }

    public void addEnemyBullet(Enemy sEnemy) {
        for (int i = 0; i < numberOfEnemyBullets; i++) {
            if (!bullets1[i].fired && (sEnemy.enemytype == 2 || sEnemy.enemytype == 4)) {
                enemyBullets[i].x = sEnemy.x + 25;
                enemyBullets[i].y = sEnemy.y + 25;
                enemyBullets[i].fired = true;
                break;
            }
        }
    }

    public void addMissile(Enemy sEnemy) {
        for (int i = 0; i < numberOfSortMissiles; i++) {
            if (!sortmissiles[i].fired) {
                sortmissiles[i].x = sEnemy.x + 25;
                sortmissiles[i].y = sEnemy.y + 25;
                sortmissiles[i].fired = true;
                break;
            }
        }
    }

    public void result() {
        result.painted = false;
        mouse.pClicked.x = 0;
        mouse.pClicked.y = 0;
        while (resultPanel) {

            for (int i = 0; i < 2; i++) {
                if (result.rectangle[i].contains(mouse.pMoved)) {
                    result.activated[i] = true;
                } else {
                    result.activated[i] = false;
                }
            }

            if (result.rectangle[1].contains(mouse.pClicked)) {
                System.exit(0);
            }
            if (result.rectangle[0].contains(mouse.pClicked)) {
                mouse.pClicked.x = 0;
                mouse.pClicked.y = 0;
                resultPanel = false;
                mainPanel = true;
            }
            sleep(60);
            repaint();
            result.painted = true;

        }
    }

    public void main() {

        while (mainPanel) {

            for (int i = 0; i < 7; i++) {
                if (menuPanel.rectangle[i].contains(mouse.pMoved)) {
                    menuPanel.activated[i] = true;
                } else {
                    menuPanel.activated[i] = false;
                }
            }

            if (menuPanel.rectangle[0].contains(mouse.pClicked)) {
                menuPanel.clicked = true;
            } else {
                if (!(menuPanel.rectangle[4].contains(mouse.pClicked)
                        || menuPanel.rectangle[5].contains(mouse.pClicked) || menuPanel.rectangle[6]
                        .contains(mouse.pClicked))) {
                    menuPanel.clicked = false;
                }

            }

            if (menuPanel.rectangle[3].contains(mouse.pClicked)) {
                System.exit(0);
            }
            if (menuPanel.rectangle[2].contains(mouse.pClicked)) {
                mouse.pClicked.x = 0;
                mouse.pClicked.y = 0;
                new AboutWindow();
            }
            if (menuPanel.rectangle[1].contains(mouse.pClicked)) {
                mouse.pClicked.x = 0;
                mouse.pClicked.y = 0;
                new HelpWindow();
            }

            if (menuPanel.clicked
                    && menuPanel.rectangle[4].contains(mouse.pClicked)) {
                menuPanel.clicked = false;
                numberOfEnemies = 8;
                numberOfHearts = 3;
                remainingHearts = 3;
                remainingMissiles = numberOfMissiles;
                numberOfMissilesToShot = 3;
                seconds = 90;
                score = 0;
                countEnemy = numberOfEnemies;
                reset();
                mainPanel = false;
                play = true;
                gameOver = false;
            }
            if (menuPanel.clicked
                    && menuPanel.rectangle[5].contains(mouse.pClicked)) {
                menuPanel.clicked = false;
                numberOfEnemies = 13;
                numberOfHearts = 3;
                remainingHearts = 3;
                remainingMissiles = numberOfMissiles;
                numberOfMissilesToShot = 3;
                seconds = 70;
                score = 0;
                countEnemy = numberOfEnemies;
                reset();
                mainPanel = false;
                play = true;
                gameOver = false;
            }
            if (menuPanel.clicked
                    && menuPanel.rectangle[6].contains(mouse.pClicked)) {
                menuPanel.clicked = false;
                numberOfEnemies = 20;
                numberOfHearts = 3;
                remainingHearts = 3;
                remainingMissiles = numberOfMissiles;
                numberOfMissilesToShot = 3;
                seconds = 60;
                score = 0;
                countEnemy = numberOfEnemies;
                reset();
                mainPanel = false;
                play = true;
                gameOver = false;
            }
            repaint();
            sleep(60);

        }
    }

    public void reset() {

        for (int i = 0; i < numberOfHearts; i++) {
            hearts[i].setHealth();
        }
        for (int i = 0; i < numberOfSortMissiles; i++) {
            sortmissiles[i].fired = false;
        }

        for (int i = 0; i < numberOfEnemies; i++) {
            enemy[i].isAlive = true;
            enemy[i].setLocation();
            if (enemy[i].enemytype == 1 || enemy[i].enemytype == 2) {
                enemy[i].enemylife = 1;
            } else if (enemy[i].enemytype == 3) {
                enemy[i].enemylife = 3;
            } else {
                enemy[i].enemylife = 5;
            }
        }

        for (int i = 0; i < numberOfBullets; i++) {
            bullets1[i].fired = false;
        }
        for (int i = 0; i < numberOfMissilesToShot; i++) {
            shotmissiles[i].fired = false;
        }
        for (int i = 0; i < numberOfMissiles; i++) {
            missiles[i].setHealth();
        }
        for (int i = 0; i < numberOfSortMissiles; i++) {
            sortmissiles[i].fired = false;
        }
        player.x = 1080 / 2 - 50;
        player.y = 340 - 40;

    }

    public static void main(String[] args) {
        new Thread(new CanvasPanelImage()).start();
    }

    public void sleep(int fps) {
        if (fps > 0) {
            diff = System.currentTimeMillis() - start;
            long targetDelay = 1000 / fps;
            if (diff < targetDelay) {
                try {
                    Thread.sleep(targetDelay - diff);
                } catch (InterruptedException e) {
                }
            }
            start = System.currentTimeMillis();
        }
    }
    
}
