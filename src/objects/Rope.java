package objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rope {
	private DoublePoint anchor1;
	private DoublePoint anchor2;
	private DoublePoint anchor3;
	private double length;
	private double gUnit;
	private Physics ob1;
	private Physics ob2;
	private Pulley pulley;
	
	public Rope(double x, double y){
		
	}
	
	/**
	 * Constructs a new Rope attached to two Physics objects
	 * @param ob1
	 * @param pulley
	 */
	public Rope(Physics ob1, Pulley pulley){
		this.ob1 = ob1;
		this.pulley = pulley;
		
		this.anchor1 = ob1.getPosition();
		this.anchor2 = pulley.getPosition();
		
		setLength2();
	}
	
	/**
	 * Constructs a new Rope attached to a pulley and an object
	 * @param ob1
	 * @param pulley
	 * @param ob2
	 */
	public Rope(Physics ob1, Pulley pulley, Physics ob2){
		this.ob1 = ob1;
		this.pulley = pulley;
		this.ob2 = ob2;
		
		this.anchor1 = ob1.getPosition();
		this.anchor2 = pulley.getPosition();
		this.anchor3 = ob2.getPosition();
		
		setLength3();
	}
	
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public Rope(double x1, double y1, double x2, double y2){
		
		anchor1.x = x1;
		anchor1.y = y1;
		anchor2.x = x2;
		anchor2.y = y2;
		
		setLength2();

	}
	
	/**
	 * 
	 * @param anchor1
	 * @param anchor2
	 */
	public Rope(DoublePoint anchor1, DoublePoint anchor2){
		
		this.anchor1 = anchor1;
		this.anchor2 = anchor2;
	}
	
	public void setLength3(){
		length = Math.sqrt(Math.pow(anchor1.x - anchor2.x, 2) + Math.pow(anchor1.y - anchor2.y, 2));
		length +=  Math.sqrt(Math.pow(anchor3.x - anchor2.x, 2) + Math.pow(anchor3.y - anchor2.y, 2));
	}
	
	public void setLength2(){
		
		length = Math.sqrt(Math.pow(anchor1.x - anchor2.x, 2) + Math.pow(anchor1.y - anchor2.y, 2));
		//System.out.println(anchor1.x - anchor2.x);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void move(double x, double y){
		double dx = x - anchor1.x;
		double dy = y - anchor1.y;
		double distance = Math.sqrt(Math.pow((anchor1.x + dx) - anchor2.x, 2.0) + Math.pow((anchor1.y + dy) - anchor2.y, 2.0));
		
		if (distance >= length){
			anchor2.x += dx;
			anchor2.y += dy;
		}
		//System.out.println(" d: " + distance + " l: " + length);
	}
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void pulleyMove(double x, double y){
		double dx = x - anchor1.x;
		double dy = y - anchor1.y;
		double distance = Math.sqrt(Math.pow((anchor1.x + dx) - anchor2.x, 2.0) + Math.pow((anchor1.y + dy) - anchor2.y, 2.0));
		double distance2 = length - distance;
		
		if (distance <= length && distance >= 0.3){
			anchor3.y = anchor2.y + distance2;
		}
		
	}
	

	public DoublePoint getAnchor1() {
		return anchor1;
	}

	public void setAnchor1(DoublePoint anchor1) {
		this.anchor1 = anchor1;
	}

	public DoublePoint getAnchor2() {
		return anchor2;
	}

	public void setAnchor2(DoublePoint anchor2) {
		this.anchor2 = anchor2;
	}

	public DoublePoint getAnchor3() {
		// TODO Auto-generated method stub
		return anchor3;
	}
	
	public void setAnchor3(DoublePoint anchor3){
		this.anchor3 = anchor3;
		
	}
	
	
}

	