package org.laukvik.svg.swing.editors.transform;

import javax.swing.JSpinner;

public class MatrixSpinner extends JSpinner {

	private static final long serialVersionUID = 1L;

	public MatrixSpinner() {
		super( new MatrixSpinnerModel() );
	}
	
}