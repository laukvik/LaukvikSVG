package org.laukvik.svg.swing.editors.text;

import org.laukvik.svg.stroke.DashArray;
import org.laukvik.svg.swing.editors.TextEditor;

public class DashArrayEditor extends TextEditor {

	private static final long serialVersionUID = 1L;

	public DashArrayEditor() {
		super();
		setRows( 3 );
	}

	public DashArray getDashArray() {
		return new DashArray( getText() );
	}

	public void setDashArray( DashArray dashArray ) {
		setText( dashArray.toString() );
	}
	
}
