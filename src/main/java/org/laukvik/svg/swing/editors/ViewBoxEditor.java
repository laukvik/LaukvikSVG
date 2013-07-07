package org.laukvik.svg.swing.editors;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.laukvik.svg.ViewBox;
import org.laukvik.svg.swing.editors.geometry.UnitEditor;

public class ViewBoxEditor extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private UnitEditor w, h, x, y;

	public ViewBoxEditor() {
		x = new UnitEditor();
		y = new UnitEditor();
		w = new UnitEditor();
		h = new UnitEditor();
		


		
		setLayout( new BorderLayout() );
		add( x, BorderLayout.WEST );
		add( y, BorderLayout.NORTH );
		add( w, BorderLayout.EAST );
		add( h, BorderLayout.SOUTH );
	}
	
	public void setViewBox( ViewBox viewBox ){
		x.setUnit( viewBox.x );
		y.setUnit( viewBox.y );
		w.setUnit( viewBox.w );
		h.setUnit( viewBox.h );
	}
	
}