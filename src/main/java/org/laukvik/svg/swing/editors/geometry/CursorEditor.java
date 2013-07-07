package org.laukvik.svg.swing.editors.geometry;

import javax.swing.JComboBox;

import org.laukvik.svg.Cursor;

public class CursorEditor extends JComboBox {

	private static final long serialVersionUID = 1L;
	
	public CursorEditor() {
		super( Cursor.AVAILABLE_CURSORS );
//		setRenderer( new CursorComboBoxRenderer() );
		setMaximumRowCount( 20 );
	}
	
	public void setSelectedCursor( Cursor cursor ){
		for (Cursor c : Cursor.AVAILABLE_CURSORS){
			if (c.getName().equalsIgnoreCase( cursor.getName() )){
				setSelectedItem( cursor );		
			}
		}
	}

}
