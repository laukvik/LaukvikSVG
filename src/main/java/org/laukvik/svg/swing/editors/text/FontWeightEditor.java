package org.laukvik.svg.swing.editors.text;

import javax.swing.JCheckBox;

import org.laukvik.svg.font.FontWeight;

public class FontWeightEditor extends JCheckBox {

	private static final long serialVersionUID = 1L;

	public FontWeightEditor() {
		super();
	}
	
	public FontWeight getFontWeight(){
		return isSelected() ? FontWeight.BOLD : FontWeight.NORMAL;
	}

	public void setFontWeight( FontWeight weight ) {
		setSelected( weight.isBold() );
	}

}