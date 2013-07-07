package org.laukvik.svg.shape.path;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class VLineTo extends PathElement {
	
	public static final String ABSOLUTE = "V";
	public static final String RELATIVE = "v";

	public float y;
	public boolean absolute;
	
	public String toString() {
		return (absolute ? ABSOLUTE : RELATIVE) + y;
	}
	
	public VLineTo( float y){
		this( y, false );
	}
	
	public VLineTo( float y, boolean absolute ){
		this.y = y;
		this.absolute = absolute;
	}
	
	public void roll( float x, float y ){
		if (absolute){
			this.y += y;
		}
		
	}
	
	public void apply(GeneralPath p) {
		Point2D point = p.getCurrentPoint();
		int x = (int) point.getX();
		if (absolute){
			p.lineTo( x , y ); 
		} else {
			p.lineTo( x , y+(int) point.getY() ); 
		}
		
	}

	public void resizeX( float amount ){
	}

	public void resizeY( float amount ){
		y +=  y * amount;
	}
	
}