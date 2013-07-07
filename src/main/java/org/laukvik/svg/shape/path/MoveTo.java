package org.laukvik.svg.shape.path;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


/**
 * 
 * @author morten
 * 
 * @see http://www.w3.org/TR/SVG/paths.html#PathDataCubicBezierCommands
 */
public class MoveTo extends PathElement {
	
	public final static String ABSOLUTE = "M";
	public final static String RELATIVE = "m";

	public float x;
	public float y;
	public boolean absolute;
	
	public String toString() {
		return (absolute ? ABSOLUTE : RELATIVE) + x+","+y;
	}
	
	public MoveTo( float x, float y ){
		this( x, y, false );
	}
	
	public MoveTo( float x, float y, boolean absolute ){
		this.x = x;
		this.y = y;
		this.absolute = absolute;
	}
	
	public MoveTo( String values ){
		String [] xy = values.trim().split( ",| " );
//		System.out.println( values + " size: " + values.length() );
//		if (xy[0].length() == 0){
//			this.x = 0;
//		} else {
//			this.x = Float.parseFloat( xy[0] );
//		}
		

		this.x = Float.parseFloat( xy[0] );
		this.y = Float.parseFloat( xy[1] );
		this.absolute = true;
	}
	
	public void roll( float x, float y ){
		if (absolute){
			this.x += x;
			this.y += y;	
		}
	}
	
	public void apply(GeneralPath p) {
		Point2D point = p.getCurrentPoint();
		if (absolute){
			p.moveTo( x, y );
		} else {
			if (point == null){
				p.moveTo( x, y );
			} else {
				p.moveTo( x+(int) point.getX(), y+(int) point.getY() );
			}
		}
	}
	
	public void resizeX( float amount ){
		x +=  x * amount;
	}

	public void resizeY( float amount ){
		y +=  y * amount;
	}

}