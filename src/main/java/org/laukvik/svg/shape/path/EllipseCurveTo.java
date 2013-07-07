package org.laukvik.svg.shape.path;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class EllipseCurveTo extends PathElement {
	
	public static final String ABSOLUTE = "A";
	public static final String RELATIVE = "a";

	public float x;
	public float y;
	public float x2;
	public float y2;
	public float x3;
	public float y3;
	public boolean absolute = false;
	
	public String toString() {
		return (absolute ? ABSOLUTE : RELATIVE) + x+","+y + " " + x2+","+y2 + " " + x3+","+y3;
	}
	
	public EllipseCurveTo( float x, float y, float x2, float y2, float x3, float y3 ){
		this.x = x;
		this.y = y;
		
		this.x2 = x3;
		this.y2 = y3;
		
		this.x3 = x3;
		this.y3 = y3;
	}
	
	public void roll( float x, float y ){	
		if (absolute){
			this.x += x;
			this.y += y;
			this.x2 += x;
			this.y2 += y;
			this.x3 += x;
			this.y3 += y;
		}
	}
	
	public void apply( GeneralPath p ){
		if (absolute){
			p.curveTo( x, y, x2, y2, x3, y3 );
		} else {
			Point2D point = p.getCurrentPoint();
			if (point != null){
				float rx = (float) point.getX();
				float ry = (float) point.getY();
				p.curveTo( x+rx, y+ry, x2+rx, y2+ry, x3+rx, y3+ry );
			}
		}
	}
	
	public void resizeX( float amount ){
		x +=  x * amount;
		x2 +=  x2 * amount;
		x3 +=  x3 * amount;
	}

	public void resizeY( float amount ){
		y +=  y * amount;
		y2 += y2 * amount;
		y3 += y3 * amount;
	}
	
}