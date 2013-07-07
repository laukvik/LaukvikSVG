package org.laukvik.svg.fill;

import java.util.Vector;

public class Gradient extends SVGPaint{
	
	private Vector <Stop> stops = new Vector<Stop>();
	public SpreadMethod spreadMethod = SpreadMethod.REPEAT;

	public Gradient(){
	}

	public Gradient( String gradient ){
		//url(#MyGradient)
	}
	
	public void add( Stop stop ){
		stops.add( stop );
	}
	
	public Vector<Stop> getStops() {
		return stops;
	}
	
}