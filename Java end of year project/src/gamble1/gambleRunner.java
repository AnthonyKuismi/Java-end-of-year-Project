
package gamble1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class gambleRunner extends JPanel implements ActionListener,KeyListener,MouseListener{
	/**
	 * 
	 */
	public static double gain = 0;
	public static float gainAlpha = 1;
	public static int displayTimer = 0;
	private static final long serialVersionUID = 5043904950045134587L;
	public Timer tm = new Timer(25, this);
	public static final int WIDTH = 1366;
	public static final int HEIGHT = 768;
	public boolean siez = false;
	public Random r = new Random();
	public Font font1 = new Font("Marker Felt", Font.BOLD, 200);
	public Font font2 = new Font("Marker Felt", Font.BOLD, 30);
	public Font font3 = new Font("Marker Felt", Font.BOLD, 20);
	private boolean titleScreen = true;
	public static int i=0;
	public static int reverse=0;
	public static int update = 0;
	public int offsetX = 0;
	public int offsetY = 0;
	public double money = 1000;
	public double betAmount = 100;
	public boolean chipMove = false;
	public Font font0 = new Font("Marker Felt", Font.BOLD, 40);
	public static Ball b = new Ball(450,350);
	public Chip c = new Chip();
	public boolean gamble = false;
	public static boolean hasFiredOld = false;
	public boolean noResultYet = false;
	public gambleRunner() {
		tm.start();
		
		addMouseListener(this);
		setFocusable(true);
		addKeyListener(this);
		setFocusTraversalKeysEnabled(false);
	}
	public static void main(String[] args) {
		gambleRunner f = new gambleRunner();
		JFrame jf = new JFrame();	
		
		jf.setTitle("Gambling Ring");
		jf.setSize(WIDTH,HEIGHT);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(f);
	}

	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int c = e.getExtendedKeyCode();
		switch(c) {
		//button to start
		case KeyEvent.VK_SPACE:
			titleScreen = false;
			break;
		//button to gamble
		case KeyEvent.VK_ENTER:
			if(!titleScreen)
				gambleMoney();
			break;
		case KeyEvent.VK_UP:
			if(betAmount + money/20 > money && !b.isMoving()) {
				betAmount = money;
			}else if(!b.isMoving()){
				betAmount += money/20;
				betAmount = ((int)(betAmount*100))/100.0;
				if(money == 0) {
					betAmount = 0;
				}
			}		
			break;
		case KeyEvent.VK_DOWN:
			if(betAmount - money/20 <= 0 && !b.isMoving()) {
				betAmount = 1;
			} else if(!b.isMoving()){
				betAmount -= money/20;
				betAmount = ((int)(betAmount*100))/100.0;
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
				
	}
	public void paintComponent(Graphics g) {
		if(titleScreen) {
			if(i>27) {
				i=0;
				reverse++;
			}
			g.setFont(font1);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			drawTitleBackground(g, offsetX, offsetY);
			if(update<=10) {
				offsetX = WIDTH/75;
				offsetY = HEIGHT/75;
			} else if(update<=20) {
				offsetX = 2*WIDTH/75;
				offsetY = 2*HEIGHT/75;
			} else if(update<=30) {
				offsetX = 3*WIDTH/75;
				offsetY = 3*HEIGHT/75;
			} else if(update<=40) {
				offsetX = 4*WIDTH/75;
				offsetY = 4*HEIGHT/75;
			} else {
				offsetX = 0;
				offsetY = 0;
				if(update>=51) {
					update = 0;
				}
			}
			update+=5;
			drawSpam(g);
			drawXtoStart(g, i, reverse);
			i++;

			

			
		} else {
			g.setColor(new Color(255,255,255));
			paintBackground(g);	
			try {
				b.update();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			b.draw(g);
			
			if (chipMove) {
				Point p = MouseInfo.getPointerInfo().getLocation();
				c.update(p.x, p.y);
			}
			c.draw(g);
			g.setFont(font2);
			g.drawString("Bet Type: #" + c.getGambleTile(), 670, 100);
			g.drawString("Bet Amount: " + betAmount, 900, 100);
			g.drawString("Money: "+ money, 900,50);
			g.setColor(new Color(255,0,0));
			if(money < betAmount && !b.isMoving() && !noResultYet) {
				g.drawString("Not enough money to gamble!", 530, 50);
			}
			g.setFont(font3);
			g.setColor(new Color(255,255,255));
			if(money==1000&& c.getX() == 1210 && c.getY() == 240) {
				g.setFont(font2);
				g.drawString("INFO", (WIDTH/2)+520, (HEIGHT/2)-296);
				g.setFont(font3);
				g.drawString("Use the up and down arrow", (WIDTH/2)+456, (HEIGHT/2)-240);
				g.drawString("keys to change bet amount.", (WIDTH/2)+460, (HEIGHT/2)-215);
				g.drawString("Press ENTER to gamble!", (WIDTH/2)+470, (HEIGHT/2)-190);
				g.drawString("Click and move the chip", (WIDTH/2)+470, (HEIGHT/2)-70);
				g.drawString("to change the bet type!", (WIDTH/2)+470, (HEIGHT/2)-45);
				g.setFont(font2);
				g.drawString("↑", (WIDTH/2)+543, (HEIGHT/2)-100);
			}
			g.setFont(font2);
			if(b.isMoving()) {
				int rgb = (int)(Math.random()*230)+25;
				int rgb2 = (int)(Math.random()*230)+25;
				int rgb3 = (int)(Math.random()*230)+25;
				Color color = new Color(rgb, rgb2, rgb3);
				g.setColor(color);
				g.drawString("BET LOCKED IN!", 60, 110);
				g.setColor(new Color(255,255,255));
			}
			if(hasFiredOld != b.hasFired && !b.isMoving() && noResultYet) {
				b.hasFired = hasFiredOld;
				noResultYet = false;
				calculateResult();
				displayTimer=0;
				gainAlpha=1;

			}
			if(gain!=0) {
				displayTimer++;
				if(displayTimer<100) {
					gainAlpha-=0.01;
					if(gain>0) {
						g.setColor(new Color(0,1,0,gainAlpha));
						g.drawString("+$" + gain, 100, 120-displayTimer);
					} else {
						g.setColor(new Color(1,0,0,gainAlpha));
						g.drawString("-$" + -gain, 100, 120-displayTimer);
					}
				}
			}
			

		}






	}
	
	public void calculateResult() {
		//Odd Numbers (1-13) 
				//Even Numbers (2-14)
				//0 (only 0)
				//1,2,3
				//7,13
				//All primes 2-13
		switch(c.getGambleTile()) {
		case 1:
			if((b.getTileNum()%2==1)) {
				money += betAmount*2;
				gain = betAmount*2;
			} else {
				gain = -betAmount;
			}
			break;
		case 2:
			if((b.getTileNum()%2==0)) {
				money += betAmount*2;
				gain = betAmount*2;
			}else {
				gain = -betAmount;
			}
			break;
		case 3:
			if((b.getTileNum()==0)) {
				money += betAmount*15;
				gain = betAmount*15;
			}else {
				gain = -betAmount;
			}
			break;
		case 4:
			if((b.getTileNum()==1 || b.getTileNum()==2|| b.getTileNum()==3)) {
				money += betAmount*5;
				gain = betAmount*5;
			}else {
				gain = -betAmount;
			}
			break;
		case 5:
			if((b.getTileNum()==7 || b.getTileNum()==13)) {
				money += betAmount*10;
				gain = betAmount*10;
			}else {
				gain = -betAmount;
			}
			break;
		case 6:
			if((b.getTileNum()==2 || b.getTileNum()==3|| b.getTileNum()==5 ||  b.getTileNum()==7 || b.getTileNum()==11|| b.getTileNum()==13)) {
				money += betAmount*3;
				gain = betAmount*3;
			}else {
				gain = -betAmount;
			}
			break;
		
		}


		
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
		revalidate();
	}
	public void paintBackground(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for (int i = 0; i < 15; i++) {
			g.setColor(Color.RED);
			if(i%2 == 0) {
				g.setColor(Color.WHITE);
			}
			if(i==14) {
				g.setColor(Color.GREEN);
			}
			
			g.fillArc(200, 100, 500, 500, i*24, 22);
			g.setColor(Color.WHITE);
			g.setFont(font2);
			g.drawString(i + "", (int)(270*Math.cos((i*24+12)*Math.PI/180)+435), (int)(270*Math.sin((i*24+12)*Math.PI/180)+365));
		}
		g.setColor(Color.BLACK);
		g.fillOval(300, 210, 300, 300);
		
		g.setColor(Color.WHITE);
		g.fillRect(800, 150, 300, 450);
		g.setColor(Color.BLACK);
		g.fillRect(950, 145, 10, 500);
		g.fillRect(800, 300, 300, 10);
		g.fillRect(800, 450, 300, 10);
		g.setColor(Color.GRAY);
		g.setFont(font3);
		g.drawString("1: Odd Numbers", 800,225);
		g.drawString("3: Only 0", 800,375);
		g.drawString("5: 7 and 13", 800,525);
		g.drawString("2: Even Numbers", 960,225);
		g.drawString("4: 1,2,3", 960,375);
		g.drawString("6: Only Primes", 960,525);
		//Odd Numbers (1-13) 
		//Even Numbers (2-14)
		//0 (only 0)
		//1,2,3
		//7,13
		//5 Random Numbers 

		
	}
	public void drawSpam(Graphics g) {
		int rgb = (int)(Math.random()*230)+25;
		int rgb2 = (int)(Math.random()*230)+25;
		int rgb3 = (int)(Math.random()*230)+25;
		Color color = new Color(rgb, rgb2, rgb3);
		g.setColor(color);
		g.drawString("GAMBLE!", (WIDTH/2)-350, (HEIGHT/2)+10);
	}
	public void drawXtoStart(Graphics g, int count, int isReverse) {	
		if(isReverse%2==1) {
			Color color = new Color(255, 255, 255, 215-count*5);
			g.setColor(color);
		} else {
			Color color = new Color(255, 255, 255, 80+count*5);
			g.setColor(color);
		}
		g.setFont(font0);
		g.drawString("Press [Space] to Start", (WIDTH/2)-150, (HEIGHT/2)+80);
	}
	public void drawTitleBackground(Graphics g, int offsetX, int offsetY) {
		int useHeight = HEIGHT/15;
		int useWidth = WIDTH/15;
		int x = 0;
		int y = 0;
		g.setColor(getBackground());
		for(int i=-1;i<=15;i++) {
			x = i*useWidth;
			for(int j=-1;j<=15;j++) {
				y = j*useHeight;
				g.drawRect(x+offsetX, y+offsetY, (int) (Math.random()*50)+10, 1);
			}

			
		}
	}
	//method that happens when you spin the wheel 
	public void gambleMoney() {
		if (money >= betAmount && !b.isMoving() && money > 0) {

			money-=betAmount;
			b.spin();
			noResultYet = true;


			
			money = ((int)(money*1000))/1000.0;
			
		} 
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (chipMove == false) {
			int x = e.getX();
			int y = e.getY();
			
			chipMove = c.didGetClickedOn(x, y);
		}else {
			chipMove = false;
		}
		
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
		




}

