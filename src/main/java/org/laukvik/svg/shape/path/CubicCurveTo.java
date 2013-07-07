package org.laukvik.svg.shape.path;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import org.laukvik.svg.Coordinate;

public class CubicCurveTo extends PathElement {
	
	public final static String ABSOLUTE = "C";
	public final static String RELATIVE = "c";

	public final static String ABSOLUTE_SMOOTH = "S";
	public final static String RELATIVE_SMOOTH = "s";
	
	public float x;
	public float y;
	public float x2;
	public float y2;
	public float x3;
	public float y3;
	public boolean absolute;
	public boolean smooth;
	
	
	public String toString() {
		return (absolute ? ABSOLUTE : RELATIVE) + x+","+y + " " + x2+","+y2 + " " + x3+","+y3;
	}
	
	public CubicCurveTo( String values ){
		Coordinate [] points = PathParser.listPoints( values );
		if (points.length == 3){
			Coordinate p1 = points[ 0 ];
			Coordinate p2 = points[ 1 ];
			Coordinate p3 = points[ 2 ];
			this.x = p1.x;
			this.y = p1.y;
			this.x2 = p2.x;
			this.y2 = p2.x;
			this.x3 = p3.x;
			this.y3 = p3.x;			
		} else if (points.length == 2){
			Coordinate p1 = points[ 0 ];
			Coordinate p2 = points[ 1 ];
			this.x = p1.x;
			this.y = p1.y;
			this.x2 = p2.x;
			this.y2 = p2.x;
			this.x3 = p1.x;
			this.y3 = p1.x;	
		}
	}
	
	public CubicCurveTo( float x, float y, float x2, float y2, float x3, float y3 ){
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
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
			Point2D point = p.getCurrentPoint();
			if (point == null){
				p.moveTo(0,0);
			}
			p.curveTo( x, y, x2, y2, x3, y3 );
		} else {
			Point2D point = p.getCurrentPoint();
//			if (point != null){
				float rx = (float) point.getX();
				float ry = (float) point.getY();
				p.curveTo( x+rx, y+ry, x2+rx, y2+ry, x3+rx, y3+ry );
//			}
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
	
	public static void main( String [] args ){
		System.out.println( new CubicCurveTo("-0.04199,0.04297,0.01367,0.16553,0.125,0.27393") );
	}
	
}