package org.laukvik.svg.swing;

import org.laukvik.svg.SVGColor;
import org.laukvik.svg.stroke.Stroke;

public class PencilFeatures {
	
	private SVGColor background, foreground;
	public Stroke stroke;

	public PencilFeatures() {
		stroke = new Stroke();
		background = SVGColor.white;
		foreground = SVGColor.black;
	}
	
	public void setBackground(SVGColor background) {
		this.background = background;
	}
	
	public void setForeground(SVGColor foreground) {
		this.foreground = foreground;
	}
	
	public SVGColor getBackground() {
		return new SVGColor( background.getValue() );
	}
	
	public SVGColor getForeground() {
		return new SVGColor( foreground.getValue() );
	}
	
	public Stroke getStroke() {
		return stroke;
	}

	public void setStroke( Stroke stroke ) {
		this.stroke = stroke;
	}

	
}
