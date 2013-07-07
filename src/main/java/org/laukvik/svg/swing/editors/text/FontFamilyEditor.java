package org.laukvik.svg.swing.editors.text;

import java.awt.GraphicsEnvironment;
import javax.swing.JComboBox;

public class FontFamilyEditor extends JComboBox {

	private static final long serialVersionUID = 1L;
	
	public FontFamilyEditor() {
		super( GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames() );
		setMaximumRowCount( 20 );
	}

}