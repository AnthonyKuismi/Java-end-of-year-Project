package gamble1;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Ball {
	private double x;
	private double y;
	private double radians_acc = 0.985;
	private double radians_vel = 0;
	private double radians = 0;
	private int radius = 200;
	private Random r = new Random();
	private int size = 40;
	private double tileNum;
	private int centerx;
	private int centery;
	public static boolean hasFired = false;
	private static boolean isMoving = false;
	private ArrayList<Double> prevx = new ArrayList<Double>();
	private ArrayList<Double> prevy = new ArrayList<Double>();
	public Ball(int centerx, int centery) {
		this.centerx = centerx;
		this.centery = centery;
		this.x = centerx + radius;
		this.y = centery;
	}
	//applies a random angular velocity
	public void spin() {
		radians_vel = -r.nextDouble()-0.5;

	}
	public void update() throws InterruptedException {
		radians_vel *= radians_acc;
		radians+=radians_vel;
		tileNum = (int)(-((radians*180/Math.PI)%360)/24);
		prevx.add(x);
		prevy.add(y);
		if (prevx.size()>20) {
			prevx.remove(0);
			prevy.remove(0);
		}
		if (radians_vel >-0.003) {
			radians_vel = 0;
			isMoving = false;
			hasFired = true;
			
		}else {
			isMoving = true;
		}
		
		x = radius*Math.cos(radians)+centerx;
		
		y = -radius*Math.sin(radians)+centery;
		
		x+=(Math.random()*5)-(Math.random()*5);
		y+=(Math.random()*5)-(Math.random()*5);
		
		
	}
	public double getVel() {
		return radians_vel;
	}
	
	public void draw(Graphics g) {	
		for(int i = 0; i< prevx.size(); i ++) {
			Color c = new Color(50,50,150,i*7);
			g.setColor(c);
			g.fillOval((int)(prevx.get(i)-size/2), (int)(prevy.get(i)-size/2), size, size);
		}
		g.setColor(Color.BLUE);
		g.fillOval((int)(x-size/2), (int)(y-size/2), size, size);
		g.setColor(Color.WHITE);
		
		//g.setColor(Color.RED);
		//g.fillOval(centerx-size/2, centery-size/2, size, size);

	}
	public static void hasFired() {

	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getTileNum() {
		return tileNum;
	}
	public void setTileNum(double tileNum) {
		this.tileNum = tileNum;
	}
	public static boolean isMoving() {
		return isMoving;
	}
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
}




