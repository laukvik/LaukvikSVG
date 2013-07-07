package org.laukvik.svg.swing;

import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.JButton;

public class LayerButton extends JButton {

	private static final long serialVersionUID = 1L;
	private Dimension size = new Dimension(32,32);
	
	public LayerButton( Action a ){
		super(a);
		setText( null );
		setSize( size );
		setPreferredSize( size );
		setMinimumSize( size );
		setMaximumSize( size );
	}

}