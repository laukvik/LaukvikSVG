package org.laukvik.svg.swing;

import java.util.Vector;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.laukvik.svg.shape.SVG;

public class SVGTableModel implements TableModel {
	
	private Vector<TableModelListener> listeners;
	private SVG svg = new SVG();
	
	public SVGTableModel(){
		this.listeners = new Vector<TableModelListener>();
	}
	
	public void setSVG(SVG svg) {
		this.svg = svg;
	}
	
	public SVG getSVG() {
		return svg;
	}
	
	public void addTableModelListener(TableModelListener l) {
		listeners.add( l );
	}

	public void removeTableModelListener(TableModelListener l) {
		listeners.remove( l );
	}
	
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		return 1;
	}

	public String getColumnName(int columnIndex) {
		return "Element";
	}

	public int getRowCount() {
		
		return svg.getItems().size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return svg.getItems().get( rowIndex ).getClass().getName();
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}


	public void setValueAt(Object value, int rowIndex, int columnIndex) {
	}

}
