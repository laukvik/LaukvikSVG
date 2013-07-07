package org.laukvik.svg.swing.editors.text;

import javax.swing.JCheckBox;

import org.laukvik.svg.font.FontVariant;

public class FontVariantEditor extends JCheckBox {

	private static final long serialVersionUID = 1L;

	public FontVariantEditor() {
		super();
	}
	
	public void setFontVariant( FontVariant fontVariant ){
		setSelected( fontVariant.isSmallCaps() );
	}

	public FontVariant getFontVariant() {
		return isSelected() ? FontVariant.SMALLCAPS : FontVariant.NORMAL;
	}
	
}