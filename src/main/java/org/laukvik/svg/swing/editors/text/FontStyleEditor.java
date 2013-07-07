package org.laukvik.svg.swing.editors.text;

import javax.swing.JCheckBox;

import org.laukvik.svg.font.FontStyle;

public class FontStyleEditor extends JCheckBox {

	private static final long serialVersionUID = 1L;

	public FontStyleEditor() {
		super();
	}
	
	public void setFontStyle( FontStyle fontStyle ){
		setSelected( fontStyle.isItalic() );
	}

	public FontStyle getFontStyle() {
		return isSelected() ? FontStyle.ITALIC : FontStyle.NORMAL;
	}
	
}