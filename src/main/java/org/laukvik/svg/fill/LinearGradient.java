package org.laukvik.svg.fill;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

import org.laukvik.svg.unit.Unit;

/**
 * 
 * @author morten
 * @see http://www.w3.org/TR/SVG11/pservers.html#LinearGradients
 */
public class LinearGradient extends Gradient {

	public static final String LINEARGRADIENT = "linearGradient";
	public Unit x1;
	public Unit y1;
	public Unit x2;
	public Unit y2;
	
	public LinearGradient( Unit x1, Unit y1, Unit x2, Unit y2 ){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public LinearGradient(){
	}
	
	public PaintContext createContext(ColorModel cm, Rectangle r, Rectangle2D r2d, AffineTransform xform, RenderingHints hints) {

		Color start = getStops().get( 0 ).color.getValue();
		Color end = getStops().get( 1 ).color.getValue();
	
		GradientPaint gradient = new GradientPaint( 
				getSVG().toHorisontalPixels(x1),
				getSVG().toVerticalPixels(y1), 
				start,
				getSVG().toHorisontalPixels(x2),
				getSVG().toVerticalPixels(y2), 
				end, 
				spreadMethod.isRepeat() 
			);
		return gradient.createContext(cm, r, r2d, xform, hints);
	}

	public int getTransparency() {
		return 0;
	}
	
}