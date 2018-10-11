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
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @authors Iury, Vinicius and Daniel
 */
public class CanvasPanelImage extends JPanel implements Runnable {
	int closer = Integer.MAX_VALUE;
	Boolean hitted = false;
	boolean lop = false;
	long hittedTime = 0;
	boolean ert = false, verif = false;
	int closerposition;
	int damage = 1;
	int difficult = 0;
	int nextLevel = 0;
	int easyNumberEnemies = 6;
	int normalNumberEnemies = 12;
	int hardNumberEnemies = 18;
	int[] tipoEnemy;
	long secondbefore = 0;
	int bonusScore = 0;
	int turbotime;
	int thunderx = 900, thundery = 900;
	int turbo = 1;
	boolean thunder = false;
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
	int maxMissile = 10;
	int minMissile = 1;
	int diffMissile = maxMissile - minMissile;
	private boolean[] key_states = new boolean[256];
	int speedOfBullets = 20;
	int timeForBullet = 1;
	int seconds = 60;
	double theta;
	double px = 0, py = 0;
	int numberOfEnemies = 160;
	int countEnemy = numberOfEnemies;
	int numberOfBullets = 25;
	int speedOfEnemies = 0;
	int numberOfEnemyBullets = 20;
	int gun = 1;
	int numberOfHearts = 18;
	int numberOfStars = 200;
	int remainingHearts = numberOfHearts;
	int numberOfSortMissiles = 10;
	int numberOfMissiles = 3;
	int remainingMissiles = numberOfMissiles;
	int numberOfMissilesToShot = 3;
	int strx = 900, stry = 900;
	Random rn = new Random();
	long startTime, secondss;
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
	Graphics2D g2d;
	int bossdx = 2, bossdy = 2;

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

				// carregando estrelas
				for (int i = 0; i < numberOfStars; i++) {
					if (i <= 150) {
						g2d.setColor(Color.WHITE);
						g2d.fillOval(stars[i].x, stars[i].y, stars[i].tam, stars[i].tam);
					} else if (i <= 180) {
						g2d.setColor(Color.YELLOW);
						g2d.fillOval(stars[i].x, stars[i].y, stars[i].tam, stars[i].tam);
					} else {
						g2d.setColor(Color.BLUE);
						g2d.fillOval(stars[i].x, stars[i].y, stars[i].tam, stars[i].tam);
					}

				}
				player.draw(g2d, theta);
				g2d.setColor(Color.YELLOW);
				g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
				g2d.drawString("PRESS SPACE", strx, stry);

				// carregando images de inimigos
				for (int v = 0; v < numberOfEnemies; v++) {
					if (enemy[v].isAlive) {
						g2d.drawImage(enemy[v].getImage(), enemy[v].x, enemy[v].y, null);
					}
				}
				// carregando as imagens dos misseis extras
				for (int v = 0; v < numberOfSortMissiles; v++) {
					if (sortmissiles[v].fired) {
						g2d.drawImage(sortmissiles[v].getImage(), sortmissiles[v].x, sortmissiles[v].y, null);
					}
				}
				// carregando as imagens das vidas
				for (int i = 0; i < numberOfHearts; i++) {
					g2d.drawImage(hearts[i].getImage(), hearts[i].x + 40, 30, null);
				}
				// carregando as imagens dos misseis
				for (int i = 0; i < numberOfMissiles; i++) {
					g2d.drawImage(missiles[i].getImage(), missiles[i].x + 700, 30, null);
				}
				g2d.setFont(font);
				g2d.drawString(String.valueOf(score), 420, 70);
				g2d.drawImage(player.getThunder(), thunderx, thundery, 80, 70, null);
				// carregando as imagens das balas
				for (int i = 0; i < numberOfBullets; i++) {
					if (bullets1[i].fired) {
						g2d.drawImage(bullets1[i].getImage(), bullets1[i].x, bullets1[i].y, null);
					}
				}
				// carregando as imagens das balas
				for (int i = 0; i < numberOfMissilesToShot; i++) {
					if (shotmissiles[i].fired) {
						g2d.drawImage(shotmissiles[i].getImage(), shotmissiles[i].x, shotmissiles[i].y, null);
						// 140, 60
					}
				}
				// carregando as imagens dos inimigos
				for (int i = 0; i < numberOfEnemyBullets; i++) {
					if (enemyBullets[i].fired) {
						g2d.drawImage(enemyBullets[i].getImage(), enemyBullets[i].x, enemyBullets[i].y, null);
					}
				}
				g2d.setFont(font);
				if (nextLevel == 5) {
					g2d.drawString("STAGE:BOSS", 40, 650);

				} else {
					g2d.drawString("STAGE:" + String.valueOf(nextLevel + 1), 40, 650);
				}
			} else if (resultPanel) {
				result.draw(g2d);
			}
		}
	}

	private void init() {
		addKeyListener(new KeyboardAdapter());
		// double n;
		startTime = System.currentTimeMillis();
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
			shotmissiles[i] = new Bullet(1);
		}
		enemyBullets = new Bullet[numberOfEnemyBullets];
		for (int i = 0; i < numberOfEnemyBullets; i++) {
			enemyBullets[i] = new Bullet(1);
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
			secondss = (System.currentTimeMillis() - startTime) / 1000l;
			for (int i = 0; i < numberOfStars; i++) {
				switch (stars[i].tam) {
				case 1:
					stars[i].x -= turbo * 1;
					break;
				case 2:
					stars[i].x -= turbo * 2;
					break;
				case 3:
					stars[i].x -= turbo * 3;
					break;
				case 4:
					stars[i].x -= turbo * 4;
					break;
				default:
					stars[i].x -= turbo * 5;
					break;
				}
				if (stars[i].x <= 0) {
					stars[i].setLocation();
				}
				if (key_states[KeyEvent.VK_D] && player.x < 835) {
					switch (stars[i].tam) {
					case 1:
						stars[i].x -= turbo * 2;
						break;
					case 2:
						stars[i].x -= turbo * 3;
						break;
					case 3:
						stars[i].x -= turbo * 4;
						break;
					case 4:
						stars[i].x -= turbo * 5;
						break;
					default:
						stars[i].x -= turbo * 6;
						break;
					}
				}
			}

			if (player.isAlive) {
				for (int i = 0; i < numberOfEnemies; i++) {

					// enemy[i].y = enemy[i].y
					// - (int) (2 * Math.sin(enemy[i].theta));
					if (enemy[i].enemytype == 1 || enemy[i].enemytype == 2)  {
						enemy[i].x = enemy[i].x - 1 - turbo - speedOfEnemies;
					} else if (enemy[i].enemytype == 3) {
						if (enemy[i].movement == 1) {
							enemy[i].x = enemy[i].x - 2 * turbo - 2 * speedOfEnemies;
							enemy[i].y = enemy[i].y + turbo + speedOfEnemies;
						} else if (enemy[i].movement == 2) {
							enemy[i].x = enemy[i].x - 2 * turbo - 2 * speedOfEnemies;
							enemy[i].y = enemy[i].y - turbo - speedOfEnemies;
						}
					} else if (enemy[i].enemytype == 4) {
						if (enemy[i].movement == 1) {
							enemy[i].x = enemy[i].x - 2 * turbo - 2 * speedOfEnemies;
							enemy[i].y = enemy[i].y + turbo + speedOfEnemies;
						} else if (enemy[i].movement == 2) {
							enemy[i].x = enemy[i].x - 2 * turbo - 2 * speedOfEnemies;
							enemy[i].y = enemy[i].y - turbo - speedOfEnemies;
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
                                    } if (enemy[i].enemytype == 5){
                                        if (enemy[i].movement == 1) {
                                            enemy[i].x = enemy[i].x - 1 - turbo - speedOfEnemies;
                                        }else if (enemy[i].movement == 2){
                                            enemy[i].x = enemy[i].x + turbo + speedOfEnemies;
					    enemy[i].y = enemy[i].y - turbo - speedOfEnemies;
                                        }else if (enemy[i].movement == 3){
                                             enemy[i].x = enemy[i].x + turbo + speedOfEnemies;
					    enemy[i].y = enemy[i].y + turbo + speedOfEnemies;
                                        }else if (enemy[i].movement == 4){
                                             enemy[i].x = enemy[i].x - turbo - speedOfEnemies;
					    enemy[i].y = enemy[i].y + turbo + speedOfEnemies;
                                        }else if (enemy[i].movement == 5){
                                             enemy[i].x = enemy[i].x - turbo - speedOfEnemies;
					    enemy[i].y = enemy[i].y - turbo - speedOfEnemies;
                                        }
                                        
                                        if(enemy[i].x == 0 && enemy[i].movement == 1){
                                            enemy[i].movement = 2;
                                        }
                                        if(enemy[i].x == 0 && enemy[i].movement == 5){
                                            enemy[i].movement = 2;
                                        }
                                        if(enemy[i].x == 0 && enemy[i].movement == 4){
                                            enemy[i].movement = 3;
                                        }
                                        if(enemy[i].y == 0 && enemy[i].movement == 2){
                                            enemy[i].movement = 3;
                                        }
                                         if(enemy[i].y == 0 && enemy[i].movement == 5){
                                            enemy[i].movement = 4;
                                        }
                                        if(enemy[i].x > 700 && enemy[i].movement == 3){
                                            enemy[i].movement = 4;
                                        }
                                         if(enemy[i].y > 500 && enemy[i].movement == 3){
                                            enemy[i].movement = 4;
                                        }
                                        if(enemy[i].y > 500 && enemy[i].movement == 4){
                                            enemy[i].movement = 5;
                                        }
                                        System.out.println(enemy[i].x);
                                        System.out.println(enemy[i].y);
                                    }
                                    if (enemy[i].x < -200 || enemy[i].y < -100 || enemy[i].y > 700) {
                                        if (enemy[i].enemytype != 5) {
                                            enemy[i].setLocation(false);
                                        } else {
                                         			enemy[i].setLocation(true);
						}
					}
					if (enemy[i].isAlive && remainingHearts != 0
							&& enemy[i].getBounds().intersects(player.getBounds())) {
						if (!hitted) {
							enemy[i].isAlive = false;
							countEnemy--;
							hearts[--remainingHearts].setImage();
							hitted = true;
							hittedTime = System.currentTimeMillis();
							player.x = 250 / 2 - 50;
							player.y = 340 - 40;
						}
					}
				}
			}
			for (int i = 0; i < numberOfBullets; i++) {
				if (bullets1[i].fired) {
					for (int v = 0; v < numberOfEnemies; v++) {
						if (enemy[v].isAlive) {
							if (enemy[v].getBounds().intersects(bullets1[i].getBounds())) {
								enemy[v].enemylife = enemy[v].enemylife - damage;
								if (enemy[v].enemylife <= 0) {
									if (enemy[v].enemytype == 1) {
										score += 10 + bonusScore;
									} else if (enemy[v].enemytype == 2) {
										score += 50 + bonusScore;
									} else if (enemy[v].enemytype == 3) {
										score += 100 + bonusScore;
									} else if (enemy[v].enemytype == 4) {
										score += 200 + bonusScore;
									} else if (enemy[v].enemytype == 5) {
										score += 1000 + bonusScore;
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
					if (bullets1[i].x < -100 || bullets1[i].x > 900 || bullets1[i].y < -100 || bullets1[i].y > 760) {
						bullets1[i].fired = false;
					} else {
						// bullets[i].y = bullets[i].y
						// + (int) (speedOfBullets * bullets[i].sin);

						bullets1[i].x = bullets1[i].x + 2 * 10;
					}
				}
			}

			for (int i = 0; i < numberOfEnemyBullets; i++) {
				if (enemyBullets[i].fired) {
					if (player.getBounds().intersects(enemyBullets[i].getBounds())) {
						if (!hitted) {
							hearts[--remainingHearts].setImage();
							enemyBullets[i].fired = false;
							hitted = true;
							hittedTime = System.currentTimeMillis();
							player.x = 250 / 2 - 50;
							player.y = 340 - 40;
						}
					}
					if (enemyBullets[i].x < -200 || enemyBullets[i].x > 1300 || enemyBullets[i].y < -200
							|| enemyBullets[i].y > 1000) {
						enemyBullets[i].fired = false;
					} else {
						enemyBullets[i].x = enemyBullets[i].x - 1 * 10;
					}
				}
			}
			for (int i = 0; i < numberOfSortMissiles; i++) {
				if (player.getBounds().intersects(sortmissiles[i].getBounds())) {
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
			if (ert==true) {
            for (int i = 0; i < numberOfEnemies; i++) {
              	 if (enemy[i].x < closer && enemy[i].x >= player.x && enemy[i].x < 900 && enemy[i].y > 0 && enemy[i].y < 700  && enemy[i].isAlive){
                      closer = enemy[i].x;
                      closerposition = i;
                      lop = true;
                   }
               }if(lop == true) {
            	   verif = true;
            	   lop = false;
            	   addBullet(1);
            	   missiles[--remainingMissiles].setImage();
               }
			}
            ert = false;
            int closer = Integer.MAX_VALUE;
            if(verif == true) {
            	
			for (int i = 0; i < numberOfMissilesToShot; i++) {
				
				if (shotmissiles[i].fired) {
					
					if (shotmissiles[i].x > 0 && shotmissiles[i].x < 900 && shotmissiles[i].y > 0 && shotmissiles[i].y < 635) {
						shotmissiles[i].theta = Math.atan2(shotmissiles[i].y - (enemy[closerposition].y + 40),
						shotmissiles[i].x - (enemy[closerposition].x + 50));
						shotmissiles[i].y = shotmissiles[i].y - (int) (20 * Math.sin(shotmissiles[i].theta));
						shotmissiles[i].x = shotmissiles[i].x - (int) (20 * Math.cos(shotmissiles[i].theta));
						
						
					}
					
					for (int v = 0; v < numberOfEnemies; v++) {
						if (enemy[v].isAlive) {
							if (enemy[v].getBounds().intersects(shotmissiles[i].getBounds())) {
								if (enemy[v].enemytype == 1) {
									score += 10;
									verif = false;
								} else if (enemy[v].enemytype == 2) {
									score += 50;
									verif = false;
								} else if (enemy[v].enemytype == 3) {
									score += 100;
									verif = false;
								} else if (enemy[v].enemytype == 4) {
									score += 200;
									verif = false;
								} else if (enemy[v].enemytype == 5) {
									score += 1000;
									verif = false;
								}
								enemy[v].isAlive = false;
								enemy[v].x = 2000;
								enemy[v].y = 2000;
								countEnemy--;
								int j;
								j = rn.nextInt(diffMissile + 1);
								j += minMissile;
								System.out.println(j);
								if (j == 1) {
									addMissile(enemy[v]);
								}
								shotmissiles[i].x = 2000;
								shotmissiles[i].y = 2000;
								// shotmissiles[i].fired = false;

							}
						}
					}

					// shotmissiles[i].x = shotmissiles[i].x + 1 * 10;
				}
				
			}
			
            }
			if (timeForBullet % seconds == 0) {
				int rand = (int) (Math.random() * numberOfEnemies);
				if (enemy[rand].isAlive) {
					addEnemyBullet(enemy[rand]);
				}
				// if (thunder == false){
				// turbotime++;
				// }
			}

			if (secondbefore != secondss) {
				if (secondss % 12 == 0) {
					if (thunder == false) {
						thunderx = 150;
						thundery = 15;
						thunder = true;
						strx = 210;
						stry = 60;
					} else if (thunder == true && turbo == 5) {
						thunderx = 900;
						thundery = 900;
						turbo = 1;
						damage = 1;
						bonusScore = 0;
						thunder = false;
						player.setBackSpaceship();
					}
				}
			}
			secondbefore = secondss;
			// if (timeForBullet % seconds == 0 && thunder == true) {
			// turbotime--;
			// }
			// if (turbotime == 15){
			// thunderx = 150;
			// thundery = 15;
			// thunder = true;
			// }
			// if (turbotime == 0){
			// thunderx = 900;
			// thundery = 900;
			// turbo = 1;
			// thunder = false;
			// }
			if (key_states[KeyEvent.VK_SPACE] && thunder == true) {
				damage = 5;
				bonusScore = 100;
				turbo = 5;
				strx = 900;
				stry = 900;
				secondss = 0;
                                secondbefore = 0;
				player.setTurboSpaceship();
			}
			if (remainingHearts == 0) {
				result.won = false;
				play = false;
				gameOver = true;
				resultPanel = true;
			}

			if (countEnemy == 0) {
				nextLevel++;
				if (difficult > 0) {
					if (nextLevel == 1) {
						remainingHearts = 3;
						reset(1, false);
					}
					else if (nextLevel == 2) {
						remainingHearts = 3;
						reset(2, false);
					}
					else if (nextLevel == 3) {
						remainingHearts = 3;
						reset(3, false);
					}
					else if (nextLevel == 4) {
						remainingHearts = 3;
						reset(4, false);
					} else if (nextLevel == 5) {
						remainingHearts = 3;
						reset(5, false);
						difficult = 0;
					}

				}

				else {
					result.won = true;
					play = false;
					gameOver = true;
					resultPanel = true;
				}

			}

			if (hitted) {
				if (System.currentTimeMillis() - hittedTime > 2000) {
					hitted = false;
				}
				if (hitted) {
					//fazer a nave piscar player.blink();
				
				}
			
			}
			if (key_states[KeyEvent.VK_W] && player.y > 0) {
				player.y = player.y - 5;
			}
			if (key_states[KeyEvent.VK_S] && player.y < 635) {
				player.y = player.y + 5;
			}
			if (key_states[KeyEvent.VK_A] && player.x > 0) {
				player.x = player.x - 5;
			}
			if (key_states[KeyEvent.VK_D] && player.x < 835) {
				player.x = player.x + 5;

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
				if (!(menuPanel.rectangle[4].contains(mouse.pClicked) || menuPanel.rectangle[5].contains(mouse.pClicked)
						|| menuPanel.rectangle[6].contains(mouse.pClicked))) {
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

			if (menuPanel.clicked && menuPanel.rectangle[4].contains(mouse.pClicked)) {
				menuPanel.clicked = false;
				speedOfEnemies = 0;
				difficult = 1;
				numberOfEnemies = easyNumberEnemies;
				numberOfHearts = 3;
				remainingHearts = numberOfHearts;
				numberOfMissiles = 3;
				remainingMissiles = numberOfMissiles;
				numberOfMissilesToShot = 3;
				seconds = 90;
				score = 0;
				countEnemy = numberOfEnemies;
				reset(0, true);
				mainPanel = false;
				play = true;
				gameOver = false;

			}
			if (menuPanel.clicked && menuPanel.rectangle[5].contains(mouse.pClicked)) {
				menuPanel.clicked = false;
				speedOfEnemies = 1;
				difficult = 2;
				numberOfEnemies = normalNumberEnemies;
				numberOfHearts = 3;
				remainingHearts = numberOfHearts;
				numberOfMissiles = 3;
				remainingMissiles = numberOfMissiles;
				numberOfMissilesToShot = 3;
				seconds = 70;
				score = 0;
				countEnemy = numberOfEnemies;
				reset(0, true);
				mainPanel = false;
				play = true;
				gameOver = false;

			}
			if (menuPanel.clicked && menuPanel.rectangle[6].contains(mouse.pClicked)) {
				menuPanel.clicked = false;
				speedOfEnemies = 2;
				difficult = 3;
				numberOfEnemies = hardNumberEnemies;
				numberOfHearts = 3;
				remainingHearts = numberOfHearts;
				numberOfMissiles = 3;
				remainingMissiles = numberOfMissiles;
				numberOfMissilesToShot = 3;
				seconds = 60;
				score = 0;
				countEnemy = numberOfEnemies;
				reset(0, true);
				mainPanel = false;
				play = true;
				gameOver = false;

			}
			repaint();
			sleep(60);

		}
	}

	public void newLevel(int numberOfEnemies, int level) {
		countEnemy = numberOfEnemies;
		enemy = new Enemy[numberOfEnemies];
		tipoEnemy = new int[numberOfEnemies];
		if (level == 0) {
			int j;
			for (j = 0; j < numberOfEnemies; j++) {
				tipoEnemy[j] = 1;
			}

		} else if (level == 1) {
			int j;
			for (j = 0; j < numberOfEnemies / 2; j++) {
				tipoEnemy[j] = 1;
			}

			for (j = j + 0; j < numberOfEnemies; j++) {
				tipoEnemy[j] = 2;
			}

		} else if (level == 2) {
			int j;
			for (j = 0; j < numberOfEnemies / 2; j++) {
				tipoEnemy[j] = 2;
			}

			for (j = j + 0; j < numberOfEnemies; j++) {
				tipoEnemy[j] = 3;
			}
		} else if (level == 3) {
			int j;
			for (j = 0; j < numberOfEnemies / 2; j++) {
				tipoEnemy[j] = 3;
			}

			for (j = j + 0; j < numberOfEnemies; j++) {
				tipoEnemy[j] = 4;
			}

		} else if (level == 4) {
			int j;
			for (j = 0; j < numberOfEnemies; j++) {
				tipoEnemy[j] = 4;
			}
		} else if (level == 5) {
			tipoEnemy[0] = 5;
			int j;
			for (j = 1; j < numberOfEnemies; j++) {
				tipoEnemy[j] = 4;
			}

		}
		for (int i = 0; i < numberOfEnemies; i++) {

			if (tipoEnemy[i] == 1) {
				enemy[i] = new Enemy(tipoEnemy[i]);
			} else if (tipoEnemy[i] == 2) {
				enemy[i] = new Enemy(tipoEnemy[i]);
			} else if (tipoEnemy[i] == 3) {
				enemy[i] = new Enemy(tipoEnemy[i]);
				// j = rn.nextInt(diff + 1);
				// j += min;
				// if (j == 1 || j == 2) {
				// enemy[i].movement = 1;
				// } else if (j == 3 || j == 4) {
				// enemy[i].movement = 2;
				// }
			} else if (tipoEnemy[i] == 4) {
				enemy[i] = new Enemy(tipoEnemy[i]);
			} else if (tipoEnemy[i] == 5) {
				enemy[i] = new Enemy(tipoEnemy[i]);
			}
		}

	}

	public void reset(int level, boolean first) {
		newLevel(numberOfEnemies, level);

		for (int i = 0; i < numberOfEnemies; i++) {
			enemy[i].isAlive = true;
			if (enemy[i].enemytype != 5) {
				enemy[i].setLocation(false);
			} else {
				enemy[i].setLocation(true);
			}

			if (enemy[i].enemytype == 1 || enemy[i].enemytype == 2) {
				enemy[i].enemylife = 1;
			} else if (enemy[i].enemytype == 3) {
				enemy[i].enemylife = 3;
			} else if (enemy[i].enemytype == 4) {
				enemy[i].enemylife = 5;
			} else {
				enemy[i].enemylife = 50;
			}

		}
		for (int i = 0; i < numberOfHearts; i++) {
			hearts[i].setHealth();
		}

		if (first == true) {
			// resolver o bug que come�ava o novo nivel com turbo
			thunderx = 900;
			thundery = 900;
			turbo = 1;
			damage = 1;
			bonusScore = 0;
			thunder = false;
			player.setBackSpaceship();
			// -------------------------------------------------
			for (int i = 0; i < numberOfBullets; i++) {
				bullets1[i].fired = false;
			}

			for (int i = 0; i < numberOfSortMissiles; i++) {
				sortmissiles[i].fired = false;
			}
			for (int i = 0; i < numberOfMissilesToShot; i++) {
				shotmissiles[i].fired = false;
			}
			for (int i = 0; i < numberOfSortMissiles; i++) {
				sortmissiles[i].fired = false;
			}

			for (int i = 0; i < numberOfMissiles; i++) {
				missiles[i].setHealth();
			}
			player.x = 250 / 2 - 50;
			player.y = 340 - 40;
			nextLevel = 0;
		}
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
