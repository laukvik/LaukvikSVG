package org.laukvik.svg.swing.editors.geometry;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JSlider;
import org.laukvik.svg.Opacity;

public class OpacityEditor extends JSlider implements MouseWheelListener {

	private static final long serialVersionUID = 1L;

	public OpacityEditor() {
		super( 0, 100, 100 );
		addMouseWheelListener( this );
	}
	
	public void setOpacity( Opacity opacity ){
		setValue( (int) (opacity.toFloat() * 100) );
	}
	
	public Opacity getOpacity(){
		return new Opacity( getValue() / 100f  );
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
//		System.out.println( "mouseWheelMoved: " + e.getScrollAmount() );
		setValue( getValue() + e.getWheelRotation() );
	}
	
}