package org.laukvik.svg.fill;

import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

import org.laukvik.svg.defs.Definition;
import org.laukvik.svg.shape.SVG;

public class SVGPaint extends Definition implements Paint{
	
	private SVG svg;
	
	public SVGPaint() {
	}
	
	public void setSVG(SVG svg) {
		this.svg = svg;
	}
	
	public SVG getSVG() {
		return svg;
	}

	public PaintContext createContext(ColorModel cm, Rectangle deviceBounds,Rectangle2D userBounds, AffineTransform xform, RenderingHints hints) {
		return null;
	}

	public int getTransparency() {
		return 0;
	}

}