package org.laukvik.svg.shape.path;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class HLineTo extends PathElement {
	
	public static final String ABSOLUTE = "H";
	public static final String RELATIVE = "h";

	public int x;
	public boolean absolute;
	
	public String toString() {
		return (absolute ? ABSOLUTE : RELATIVE) + x;
	}
	
	public HLineTo( int x ){
		this( x, false );
	}
	
	public HLineTo( int x, boolean absolute ){
		this.x = x;
		this.absolute = absolute;
	}
	
	public void roll( float x, float y ){
		if (absolute){
			this.x += (int) x;
		}
	}
	
	public void apply(GeneralPath p) {
		Point2D point = p.getCurrentPoint();
		int y = (int) point.getY();
		if (absolute){
			p.lineTo( x , y ); 
		} else {
			p.lineTo( x+(int) point.getX(), y ); 
		}
	}
	
	public void resizeX( float amount ){
		x +=  x * amount;
	}

	public void resizeY( float amount ){
	}
	
}