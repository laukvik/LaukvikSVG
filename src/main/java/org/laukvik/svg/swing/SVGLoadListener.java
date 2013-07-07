package org.laukvik.svg.swing;


public interface SVGLoadListener {

	/**
	 * Indicates that the panel is ready
	 * 
	 * @param panel
	 */
	public void svgLoaded( SVGEditablePanel panel );
	
	/**
	 * Indicates that the panel is empty
	 * 
	 * @param panel
	 */
	public void svgUnLoaded( SVGEditablePanel panel );
	
}