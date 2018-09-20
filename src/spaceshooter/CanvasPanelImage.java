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
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @authors Iury, Vinicius and Daniel
 */
public class CanvasPanelImage extends JPanel implements Runnable {
    int score = 0;
    Font font = new Font(Font.MONOSPACED, Font.BOLD, 50);
    Image backGround;
    Image opacity;
    int maxEnemy = 4;
    int minEnemy = 1;
    int diffEnemy = maxEnemy - minEnemy;
    int maxMissile = 10;
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
    int numberOfHearts = 3;
    int remainingHearts = numberOfHearts;
    int numberOfSortMissiles = 10;
     int numberOfMissiles = 3;
    Random rn = new Random();
    int j;

    Missile[] missiles;
    Missile[] sortmissiles;
    Thread thread;
    Enemy[] enemy;
    HealthBar[] hearts;
    Bullet[] bullets1;
    Bullet[] bullets2;
    Bullet[] enemyBullets;

    MouseDetector mouse;
    Player player;

    public CanvasPanelImage() {
        setDoubleBuffered(true);
        setFocusable(true);
        load();
        new Thread(this).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        //backGround = new ImageIcon(this.getClass().getResource("background1.jpg")).getImage();
        g2d.drawImage(backGround, 0, 0, null);
        player.draw(g2d, theta);
        //carregando images de inimigos
        for (int v = 0; v < numberOfEnemies; v++) {
            if (enemy[v].isAlive) {
                g2d.drawImage(enemy[v].getImage(), enemy[v].x,
                        enemy[v].y, null);

                // enemy[v].draw(g2d);
            }
        }
        //carregando sorteadamente o numero de misseis
        for (int v = 0; v < numberOfSortMissiles; v++) {
            if (sortmissiles[v].fired) {
                g2d.drawImage(sortmissiles[v].getImage(), sortmissiles[v].x,
                        sortmissiles[v].y, null);
            }
        }
        for (int i = 0; i < numberOfHearts; i++) {
            g2d.drawImage(hearts[i].getImage(), hearts[i].x + 40, 30, null);
        }
        for (int i = 0; i < numberOfMissiles; i++) {
            g2d.drawImage(missiles[i].getImage(), missiles[i].x + 700, 30, null);
        }
        g2d.setFont(font);
         g2d.drawString(String.valueOf(score), 420, 70);
        
        for (int i = 0; i < numberOfBullets; i++) {
            if (bullets1[i].fired) {
                g2d.drawImage(bullets1[i].getImage(), bullets1[i].x,
                        bullets1[i].y, null);
            }
        }
        for (int i = 0; i < numberOfBullets; i++) {
            if (bullets2[i].fired) {
                g2d.drawImage(bullets2[i].getImage(), bullets2[i].x,
                        bullets2[i].y, null);
            }
        }
        for (int i = 0; i < numberOfEnemyBullets; i++) {
            if (enemyBullets[i].fired) {
                g2d.drawImage(enemyBullets[i].getImage(), enemyBullets[i].x,
                        enemyBullets[i].y, null);
            }
        }

    }

    private void load() {
        addKeyListener(new KeyboardAdapter());
        //double n;
        mouse = new MouseDetector(this);
        player = new Player();
        // setBackground(Color.BLACK);
        remainingHearts = numberOfHearts;
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
        for (int i = 0; i < numberOfSortMissiles ; i++) {
            sortmissiles[i] = new Missile(1);
        }
        
        bullets2 = new Bullet[numberOfBullets];
        for (int i = 0; i < numberOfBullets; i++) {
            bullets2[i] = new Bullet(2);
        }
        enemyBullets = new Bullet[numberOfEnemyBullets];
        for (int i = 0; i < numberOfEnemyBullets; i++) {
            enemyBullets[i] = new Bullet(5);
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

        double btime, dtime = 0;
        btime = System.currentTimeMillis();
        while (true) {
            update(dtime / 1000);
            repaint();
            try {
                thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            dtime = (System.currentTimeMillis() - btime);
            btime = System.currentTimeMillis();
        }
    }

    private void update(double dt) {
        // System.out.println(player.y);
        for (int i = 0; i < numberOfEnemies; i++) {
            System.out.println(enemy[i].y);
            // enemy[i].y = enemy[i].y
            //       - (int) (2 * Math.sin(enemy[i].theta));
            if (enemy[i].enemytype == 1 || enemy[i].enemytype == 2) {
                enemy[i].x = enemy[i].x - (int) (100 * dt);
            } else if (enemy[i].enemytype == 3) {
                if (enemy[i].movement == 1) {
                    enemy[i].x = enemy[i].x - (int) (100 * dt);
                    enemy[i].y = enemy[i].y + (int) (100 * dt);
                } else if (enemy[i].movement == 2) {
                    enemy[i].x = enemy[i].x - (int) (100 * dt);
                    enemy[i].y = enemy[i].y - (int) (100 * dt);
                }
            } else if (enemy[i].enemytype == 4) {
                if (enemy[i].movement == 1) {
                    enemy[i].x = enemy[i].x - (int) (100 * dt);
                    enemy[i].y = enemy[i].y + (int) (100 * dt);
                } else if (enemy[i].movement == 2) {
                    enemy[i].x = enemy[i].x - (int) (100 * dt);
                    enemy[i].y = enemy[i].y - (int) (100 * dt);
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
        for (int i = 0; i < numberOfBullets; i++) {
            if (bullets1[i].fired) {
                for (int v = 0; v < numberOfEnemies; v++) {
                    if (enemy[v].isAlive) {
                        if (enemy[v].getBounds().intersects(
                                bullets1[i].getBounds())) {
                            enemy[v].enemylife--;
                            if (enemy[v].enemylife == 0) {
                                if (enemy[v].enemytype == 1){
                                    score += 10;
                                }else if (enemy[v].enemytype == 2){
                                    score += 50;
                                }else if (enemy[v].enemytype == 3){
                                    score += 100;
                                }else if (enemy[v].enemytype == 4){
                                    score += 200;
                                }
                                enemy[v].isAlive = false;
                                countEnemy--;
                                j = rn.nextInt(diffMissile + 1);
                                j += minMissile;
                                if (j == 2) {
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

                    bullets1[i].x = bullets1[i].x + (int) (1000 * dt);
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
                    enemyBullets[i].x = enemyBullets[i].x - (int) (1000 * dt);
                }
            }
        }

        for (int i = 0; i < numberOfBullets; i++) {
            if (bullets2[i].fired) {
                for (int v = 0; v < numberOfEnemies; v++) {
                    if (enemy[v].isAlive) {
                        if (enemy[v].getBounds().intersects(
                                bullets2[i].getBounds())) {
                             if (enemy[v].enemytype == 1){
                                    score += 10;
                                }else if (enemy[v].enemytype == 2){
                                    score += 50;
                                }else if (enemy[v].enemytype == 3){
                                    score += 100;
                                }else if (enemy[v].enemytype == 4){
                                    score += 200;
                                }
                            enemy[v].isAlive = false;
                            countEnemy--;
                            bullets2[i].fired = false;
                            j = rn.nextInt(diffEnemy + 1);
                            j += minEnemy;
                            if (j == 4) {
                                addMissile(enemy[v]);
                            }
                        }
                    }
                }
                if (bullets2[i].x < -100 || bullets2[i].x > 1180
                        || bullets2[i].y < -100 || bullets2[i].y > 760) {
                    bullets2[i].fired = false;
                } else {
                    // bullets[i].y = bullets[i].y
                    //   + (int) (speedOfBullets * bullets[i].sin);

                    bullets2[i].x = bullets2[i].x + (int) (1000 * dt);
                }
            }
        }

        if (timeForBullet % seconds == 0) {
            int rand = (int) (Math.random() * numberOfEnemies);
            if (enemy[rand].isAlive) {
                addEnemyBullet(enemy[rand]);
            }
        }
        if (key_states[KeyEvent.VK_UP]) {
            player.y = player.y - (500 * dt);
        }
        if (key_states[KeyEvent.VK_DOWN]) {
            player.y = player.y + (500 * dt);
        }
        if (key_states[KeyEvent.VK_LEFT]) {
            player.x = player.x - (500 * dt);
        }
        if (key_states[KeyEvent.VK_RIGHT]) {
            player.x = player.x + (500 * dt);
        }
        timeForBullet++;
    }

    public void addBullet(int x) {
        if (x == 1) {
            if (gun == 1) {
                for (int i = 0; i < numberOfBullets; i++) {
                    if (!bullets1[i].fired) {
                        bullets1[i].x = (int) player.x + 50;
                        bullets1[i].y = (int) player.y + 60;
                        bullets1[i].fired = true;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < numberOfBullets; i++) {
                    if (!bullets2[i].fired) {
                        bullets2[i].x = (int) player.x + 50;
                        bullets2[i].y = (int) player.y + 60;
                        bullets2[i].fired = true;
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
        for (int i = 0; i < numberOfMissiles; i++) {
            if (!sortmissiles[i].fired) {
                sortmissiles[i].x = sEnemy.x + 25;
                sortmissiles[i].y = sEnemy.y + 25;
                sortmissiles[i].fired = true;
                break;
            }
        }
    }
}
