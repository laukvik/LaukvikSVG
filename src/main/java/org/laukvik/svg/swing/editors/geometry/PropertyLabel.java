package org.laukvik.svg.swing.editors.geometry;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class PropertyLabel extends JLabel {

	private static final long serialVersionUID = 1L;
	
	public PropertyLabel( String label ){
		super( label, JLabel.TRAILING );
		setForeground( Color.BLUE );
		setFont( new Font( getFont().getName(), Font.PLAIN, 10 ) );
//		setPreferredSize( new Dimension( 100, 20 ) );
	}

}
