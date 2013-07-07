package org.laukvik.svg.shape.path;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class QuadCurveTo extends PathElement {
	
	public static final String ABSOLUTE = "Q";
	public static final String RELATIVE = "q";
	
	
	public final static String ABSOLUTE_SMOOTH = "T";
	public final static String RELATIVE_SMOOTH = "t";

	public float x;
	public float y;
	public float x2;
	public float y2;
	public boolean absolute;
	public boolean smooth;
	
	public String toString() {
		return (absolute ? ABSOLUTE : RELATIVE) + x+","+y + " " + x2+","+y2;
	}
	
	public QuadCurveTo( float x, float y, float x2, float y2 ){
		this( x,y, x2,y2, false );
	}
	
	public QuadCurveTo( float x, float y, float x2, float y2, boolean absolute ){
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
		this.absolute = absolute;
	}
	
	public void roll( float x, float y ){
		if (absolute){
			this.x += x;
			this.y += y;
			this.x2 += x;
			this.y2 += y;	
		}
	}
	
	public void apply(GeneralPath p) {
		
		if (absolute){
			p.quadTo( x, y, x2, y2  );
		} else {
			Point2D point = p.getCurrentPoint();
			if (point != null){
				float rx = (float) point.getX();
				float ry = (float) point.getY();
				p.quadTo( x+rx, y+ry, x2+rx, y2+ry );
			}
		}
	}
	
	public void resizeX( float amount ){
		x +=  x * amount;
		x2 +=  x2 * amount;
	}

	public void resizeY( float amount ){
		y +=  y * amount;
		y2 += y2 * amount;
	}
	
}