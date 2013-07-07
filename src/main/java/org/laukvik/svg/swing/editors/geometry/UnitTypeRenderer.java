package org.laukvik.svg.swing.editors.geometry;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.laukvik.svg.unit.Unit;

public class UnitTypeRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = 1L;

	public UnitTypeRenderer(){
		super();
		setOpaque(true);
	}
	
	public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
		
		if (isSelected) {
			setBackground( list.getSelectionBackground() );
			setForeground( list.getSelectionForeground() );
		} else {
			setBackground( list.getBackground() );
			setForeground( list.getForeground() );
		}
		Unit u = (Unit) value;
		setText( " " + u.getName() + " " );
		return this;
	}

}
