package org.laukvik.svg.swing;

import org.laukvik.svg.shape.Geometry;

public interface SVGChangeListener {

	public void svgAdded( Geometry... geometry );
	public void svgRemoved( Geometry... geometry );
	public void svgChanged( Geometry geometry );
	public void svgMovedBack( Geometry geometry, int fromIndex );
	public void svgMovedFront( Geometry geometry, int fromIndex );
	
}