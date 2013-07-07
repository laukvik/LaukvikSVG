package org.laukvik.svg.fill;

import java.awt.Color;
import java.awt.PaintContext;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

import org.laukvik.awt.RoundGradientPaint;
import org.laukvik.svg.unit.Unit;

/**
 * 
 * @author morten
 * 
 * @see http://www.w3.org/TR/SVG11/pservers.html#RadialGradients
 */
public class RadialGradient extends Gradient {
	
	public static final String RADIALGRADIENT = "radialGradient";

	/** cx, cy, r define the largest (i.e., outermost) circle for the radial gradient. 
	 * The gradient will be drawn such that the 100% gradient stop is mapped to the perimeter 
	 * of this largest (i.e., outermost) circle. If the attribute is not specified, the effect 
	 * is as if a value of "50%" were specified.*/
	public Unit cx = new Unit( 50, Unit.PERCENT );
	public Unit cy = new Unit( 50, Unit.PERCENT );
	
	public Unit r = new Unit( 100, Unit.PERCENT );
	
	
	/** fx, fy define the focal point for the radial gradient. The gradient will be drawn such 
	 * that the 0% gradient stop is mapped to (fx, fy). If attribute fx is not specified, fx 
	 * will coincide with cx. */
	public Unit fx, fy;

	public RadialGradient() {
	}
	
	public RadialGradient( String gradient ){
		//url(#MyGradient)
	}

	public PaintContext createContext(ColorModel cm, Rectangle rect, Rectangle2D r2d, AffineTransform xform, RenderingHints hints) {

		Color start = getStops().get( 0 ).color.getValue();
		Color end = getStops().get( 1 ).color.getValue();
		
		int rx = getSVG().toHorisontalPixels(r)/4;
		int ry = getSVG().toVerticalPixels(r)/4;
		
		RoundGradientPaint paint = new RoundGradientPaint( 
				getSVG().toHorisontalPixels(fx), 
				getSVG().toVerticalPixels(fy), 
				start, 
				new Point(rx,ry), 
				end 
			);
		
		
		return paint.createContext(cm, rect, r2d, xform, hints);
	}

	public int getTransparency() {
		return 0;
	}
	
}