package org.laukvik.svg.fill;

import org.laukvik.svg.Opacity;
import org.laukvik.svg.SVGColor;
import org.laukvik.svg.unit.Unit;

public class Stop {

	public Unit offset = new Unit(0);
	public SVGColor color = SVGColor.black;
	public Opacity opacity = new Opacity( Opacity.OPAQUE_TYPE );
	
	public Stop( Unit offset, SVGColor color, Opacity opacity ){
		this.offset = offset;
		this.color = color;
		this.opacity = opacity;
	}
	
	public Stop( Unit offset, SVGColor color ){
		this.offset = offset;
		this.color = color;
	}
	
	public Stop(){
	}
	
}