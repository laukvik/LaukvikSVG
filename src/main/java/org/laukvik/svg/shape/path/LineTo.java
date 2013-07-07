package org.laukvik.svg.shape.path;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * 
 * @author morten
 * @see http://www.w3.org/TR/SVG/paths.html#PathDataLinetoCommands
 */
public class LineTo extends PathElement {

	public final static String ABSOLUTE = "L";
	public final static String RELATIVE = "l";
	public float x;
	public float y;
	public boolean absolute;
	
	public String toString() {
		return (absolute ? ABSOLUTE : RELATIVE) + x + "," + y;
	}
	
	public LineTo( float x, float y ){
		this( x, y, false );
	}
	
	public LineTo( float x, float y, boolean absolute ){
		this.x = x;
		this.y = y;
		this.absolute = absolute;
	}
	
	public void roll( float x, float y ){
		if (absolute){
			this.x += (int) x;
			this.y += (int) y;	
		}
	}
	
	public void apply(GeneralPath p) {
		Point2D point = p.getCurrentPoint();
		if (absolute){
			p.lineTo( x, y );
		} else {
			if (point != null){
				p.lineTo( x + (int) point.getX(), y + (int)point.getY() );
			}
		}
	}
	
	public void resizeX( float amount ){
		x = x +  x * amount;
	}

	public void resizeY( float amount ){
		y = y + y * amount;
	}
	
}