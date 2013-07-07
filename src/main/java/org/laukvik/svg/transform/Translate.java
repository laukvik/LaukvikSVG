package org.laukvik.svg.transform;

import java.awt.Graphics2D;

import org.laukvik.svg.unit.Unit;

public class Translate extends TransformItem {

	public static final String TRANSLATE = "translate";
	Unit x;
	Unit y;
	
	public Translate( Unit x, Unit y) {
		this.x = x;
		this.y = y;
	}

	public Translate( float x, float y ) {
		this.x = new Unit(x);
		this.y = new Unit(y);
	}

	public void paint(Graphics2D g2) {
		log( "Translate: " + x + " x " + y );
		g2.translate( x.getValue(), y.getValue() );
	}
	
}