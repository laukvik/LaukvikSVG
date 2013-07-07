package org.laukvik.svg.swing;

import java.awt.Dimension;
import javax.swing.JButton;
import org.laukvik.svg.swing.actions.SVGAction;

public class DrawingControllerToolbarButton extends JButton {

	private static final long serialVersionUID = 1L;
	private Dimension size = new Dimension(36,36);

	public DrawingControllerToolbarButton( SVGAction action ){
		super( action );
		setText( "" );
		setSize( size );
		setPreferredSize( size );
		setMinimumSize( size );
		setMaximumSize( size );
	}
	
}