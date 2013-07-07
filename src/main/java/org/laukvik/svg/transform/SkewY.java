package org.laukvik.svg.transform;

import java.awt.Graphics2D;

import org.laukvik.svg.unit.Unit;

public class SkewY extends TransformItem {

	public static final String SKEWY = "skewY";
	Unit y;
	
	public SkewY( Unit y ) {
		this.y = y;
	}

	public SkewY( float y ) {
		this.y = new Unit(y);
	}
	
	public SkewY duplicate(){
		return new SkewY( y );
	}

	public void paint(Graphics2D g2) {
		log( "SkewY: " + y.getPixels() );
		g2.shear( 0, y.getValue() );
	}
	
	public float toFloat() {
		return y.getValue();
	}
	
}