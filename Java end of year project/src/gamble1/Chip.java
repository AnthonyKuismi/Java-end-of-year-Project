package gamble1;

import java.awt.Color;
import java.awt.Graphics;

public class Chip {
	private int x= 1210;
	private int y  = 240;
	private int price = 100;
	private int size = 30;
	private boolean shouldFollowMouse;
	public Chip() {
		
	}
	public void draw(Graphics g) {
		g.setColor(Color.PINK);
		g.fillOval(x+size/2, y-size/2, size, size);
	}
	public void update(int mouseX, int mouseY) {
		if(Ball.isMoving()) {
			shouldFollowMouse = false;
		} else {
			this.x = mouseX-size;
			this.y = mouseY-size;
		}

	}
	public boolean didGetClickedOn(int x, int y) {
		if(Math.abs(this.x - x) < 50 && Math.abs(this.y - y) < 50 && !Ball.isMoving()) {
			shouldFollowMouse = true;
			return true;
		}
		return false;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void drop() {
		shouldFollowMouse = false;
	}
	public int getGambleTile() {
		if(x<950) {
			if(y> 450) {
				return 5;
			}else if (y< 300) {
				return 1;
			}else {
				return 3;
			}
		}else {
			if(y> 450) {
				return 6;
			}else if (y< 300) {
				return 2;
			}else {
				return 4;
			}
		}
	}
	
}



