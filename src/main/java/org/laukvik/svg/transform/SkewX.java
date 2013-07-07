package org.laukvik.svg.transform;

import java.awt.Graphics2D;

import org.laukvik.svg.unit.Unit;

public class SkewX extends TransformItem {

	public static final String SKEWX = "skewX";
	Unit x;
	
	public SkewX( Unit x ) {
		this.x = x;
	}

	public SkewX( float x ) {
		this.x = new Unit(x);
	}
	
	public SkewX duplicate(){
		return new SkewX( x );
	}

	public void paint(Graphics2D g2) {
		log( "SkewX: " + x.getPixels() );
		g2.shear( x.getValue(), 0 );
	}

	public float toFloat() {
		return x.getValue();
	}
	
}