package org.laukvik.svg.fill;

import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

import org.laukvik.svg.Opacity;
import org.laukvik.svg.SVGColor;
import org.laukvik.svg.defs.Definition;
import org.laukvik.svg.shape.SVG;
import org.laukvik.svg.shape.SVGID;

public class Fill implements Paint{
	
	public final static String FILL = "fill";
	
	public SVGColor color = SVGColor.black;
	public Opacity opacity = Opacity.OPAQUE;
	public FillRule rule = FillRule.INHERIT;
	public SVGID definitionID;
	
	private SVGPaint paint;
	
	public Fill(){

	}
	
	public Fill( String fill ){
	}

	public boolean isVisible() {
		return !color.isNone() || paint != null;
	}

	public void setDefinitionID( SVGID id, SVG svg ) {
		Definition def = svg.getDefinitionById( id );
		if (def instanceof SVGPaint){
			this.paint = (SVGPaint) def;
			this.paint.setSVG( svg );
		}
	}

	public SVGID getDefinitionID() {
		return definitionID;
	}

	public PaintContext createContext(ColorModel cm, Rectangle deviceBounds,Rectangle2D userBounds, AffineTransform xform, RenderingHints hints) {
		if (paint == null){
			return color.createContext(cm, deviceBounds, userBounds, xform, hints);
		} else {
			return paint.createContext(cm, deviceBounds, userBounds, xform, hints);
		}
	}

	public int getTransparency() {
		if (paint == null){
			return color.getTransparency();
		} else {
			return paint.getTransparency();
		}
	}

}