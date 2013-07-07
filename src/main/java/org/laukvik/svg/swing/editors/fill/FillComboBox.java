package org.laukvik.svg.swing.editors.fill;

import java.awt.Dimension;

import javax.swing.JComboBox;

import org.laukvik.svg.swing.SVGEditablePanel;

public class FillComboBox extends JComboBox {

	private static final long serialVersionUID = 1L;

	public FillComboBox() {
		super();
		setMaximumSize( new Dimension(75,30) );
	}

	public void setSvgPanel(SVGEditablePanel panel) {
		// TODO Auto-generated method stub
		
	}
	
}