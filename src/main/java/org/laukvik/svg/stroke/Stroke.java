package org.laukvik.svg.stroke;

import org.laukvik.svg.Opacity;
import org.laukvik.svg.SVGColor;
import org.laukvik.svg.unit.Pixel;
import org.laukvik.svg.unit.Unit;

/**
 * 
 * @author morten
 * @see http://java.sun.com/developer/JDCTechTips/2003/tt0520.html
 */
public class Stroke implements Cloneable{

	public SVGColor color = SVGColor.black;
	public LineJoin lineJoin = LineJoin.MITER;
	public LineCap lineCap = new LineCap();
	public Opacity opacity = Opacity.OPAQUE;
	public Unit width = new Pixel(1);
	public Unit miterLimit = new Unit(4);
	public DashArray dashArray = new DashArray();
	public Unit dashOffset = new Unit(0);
	
	public Stroke(){
	}

	/**
	 * Returns true if Geometry needs to paint the stroke
	 * 
	 * @return
	 */
	public boolean isVisible() {
		if (color.isNone()){
			return false;
		} else if (width.getValue() == 0){
			return false;
		} else {
			return true;
		}
	}

	 public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
	
}