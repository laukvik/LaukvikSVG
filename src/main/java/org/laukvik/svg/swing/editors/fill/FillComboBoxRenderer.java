package org.laukvik.svg.swing.editors.fill;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.laukvik.svg.stroke.Stroke;

public class FillComboBoxRenderer extends FillPanel implements ListCellRenderer {

	private static final long serialVersionUID = 1L;
	Dimension size;
	
	public FillComboBoxRenderer( Dimension size ){
		this.size = size;
	}

	public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ){
		Stroke stroke = (Stroke) value;
		setSize( size );
		setStroke( stroke );
		

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