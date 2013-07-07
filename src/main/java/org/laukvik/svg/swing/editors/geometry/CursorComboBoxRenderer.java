package org.laukvik.svg.swing.editors.geometry;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.laukvik.svg.Cursor;

public class CursorComboBoxRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = 1L;
	
	public CursorComboBoxRenderer() {
	}

	public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ){
		Cursor c = (Cursor) value;
		
		setCursor( c.getCursor() );
		setText( c.toString() );
		if (isSelected) {
			setBackground( list.getSelectionBackground() );
			setForeground( list.getSelectionForeground() );
		} else {
			setBackground( list.getBackground() );
			setForeground( list.getForeground() );
		}
		return this;
	}

}