package org.laukvik.svg.swing;

import java.util.Vector;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Rectangle;
import org.laukvik.svg.shape.SVG;

public class SVGController {

	private SVG svg;
	private Vector<SVGChangeListener> listeners;
	
	public SVGController( SVG svg, int a ){
		this.svg = svg;
		this.listeners = new Vector<SVGChangeListener>();
	}
	
	public SVGController( int a){
		this.listeners = new Vector<SVGChangeListener>();
	}
	
 	public void addListener( SVGChangeListener listener ){
 		this.listeners.add( listener );
	}
 	
 	public void removeListener( SVGChangeListener listener ){
 		this.listeners.remove( listener );
	}
	
	public void  fireGeometryAdded( Geometry geometry ){
		for (SVGChangeListener l : listeners){
			l.svgAdded(geometry);
		}
	}
	
	public void  fireGeometryRemoved( Geometry geometry ){
		for (SVGChangeListener l : listeners){
			l.svgRemoved(geometry);
		}
	}
	
	public void  fireGeometryChanged( Geometry geometry ){
		for (SVGChangeListener l : listeners){
			l.svgChanged(geometry);
		}
	}
	
	public void setSVG( SVG svg ) {
		this.svg = svg;
	}
	
	public SVG getSVG() {
		return svg;
	}

	public void add( Rectangle r ){
		svg.add( r );
	}
	
}