package org.laukvik.svg.swing;

import org.laukvik.svg.shape.Geometry;

public interface SVGSelectionListener {
	
	public void selectionChanged( Geometry... geometry );
	public void selectionCleared();

	public void selectionAdded( Geometry... geometry );
	public void selectionRemoved( Geometry... geometry );

}