package org.laukvik.svg.transform;

import java.awt.Graphics2D;

public class Scale extends TransformItem {

	public static final String SCALE = "scale";
	float x;
	float y;
	float both = 1;
	boolean isBoth;
	
	public Scale( float value ) {
		this.both = value;
		this.x  = value;
		this.y = value;
		this.isBoth = true;
	}

	public Scale( float x, float y ) {
		this.x = x;
		this.y = y;
		this.isBoth = false;
	}
	
	public Scale duplicate(){
		return new Scale( x, y );
	}

	public void paint(Graphics2D g2) {
		g2.scale( x , y  );
		log( "Scale: " + x  + " x " + y  );
		g2.scale( x , y  );
	}
	
	public boolean isBoth(){
		return isBoth;
	}

	public float toFloat() {
		if (!isBoth){
			throw new IllegalArgumentException("Scale is not both!");
		}
		return both;
	}
	
	public float toFloatX() {
		return x;
	}
	
	public float toFloatY() {
		return x;
	}
	
	public String toString() {
		return "scale(" + x + "," + y + ")";
	}
	
}