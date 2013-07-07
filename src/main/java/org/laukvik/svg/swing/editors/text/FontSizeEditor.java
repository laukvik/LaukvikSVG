package org.laukvik.svg.swing.editors.text;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class FontSizeEditor extends JSpinner {

	private static final long serialVersionUID = 1L;
	
	public FontSizeEditor() {
		super( new SpinnerNumberModel( 11, 1, 500, 1 ) );
	}
	
	public int getFontSize(){
		return (Integer) getValue();
	}

}