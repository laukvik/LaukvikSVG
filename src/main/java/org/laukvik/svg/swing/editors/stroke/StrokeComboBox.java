package org.laukvik.svg.swing.editors.stroke;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.laukvik.svg.stroke.Stroke;
import org.laukvik.svg.swing.SVGEditablePanel;

public class StrokeComboBox extends JComboBox {

	private static final long serialVersionUID = 1L;
	private Stroke [] strokes;
	private int width = 75;
	SVGEditablePanel panel;

	public StrokeComboBox() {
		super( );
		setOpaque( true );
		setMaximumSize( new Dimension( width,30) );
		setMaximumRowCount( 20 );
		strokes = new Stroke [ 0 ];
		setStrokes( strokes );
		setRenderer( new StrokeComboBoxRenderer( new Dimension(100,24) ) );
		addActionListener( this );
	}
	
	public void setStrokes( Stroke [] strokes ){
		setModel( new DefaultComboBoxModel( strokes ) );
	}

	public void setSvgPanel(SVGEditablePanel panel) {
		this.panel = panel;
		setStrokes( panel.listAvailableStrokes() );
	}

	public void actionPerformed(ActionEvent e) {
		if (panel == null){
			
		} else {
			panel.getPencil().setStroke( (Stroke) getSelectedItem() );
		}
	}
	
}