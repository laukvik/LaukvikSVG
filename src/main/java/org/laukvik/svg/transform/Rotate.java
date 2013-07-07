package org.laukvik.svg.transform;

import java.awt.Graphics2D;

public class Rotate extends TransformItem {
	
	public static final String ROTATE = "rotate";
	Number degrees;

	public Rotate( Number degrees ){
		this.degrees = degrees;
	}
	
	public void paint(Graphics2D g2) {
//		log( "Rotate: " + degrees );
		g2.rotate( Math.toRadians( degrees.doubleValue() ) );
	}

	public float toFloat() {
		return degrees.floatValue();
	}
	
}