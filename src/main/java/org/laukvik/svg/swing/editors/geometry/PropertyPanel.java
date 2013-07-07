package org.laukvik.svg.swing.editors.geometry;

import java.awt.Component;
import java.awt.Font;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.swing.SVGChangeListener;
import org.laukvik.svg.swing.SVGLoadListener;
import org.laukvik.svg.swing.SVGEditablePanel;
import org.laukvik.svg.swing.SVGSelectionListener;

public class PropertyPanel extends JPanel implements SVGChangeListener, SVGSelectionListener, SVGLoadListener{

	private static final long serialVersionUID = 1L;
	Vector <SVGChangeListener> changeListeners;
	SVGEditablePanel panel;
	
	/* GUI Stuff */
	public int rowWidth = 300;
	public int rowHeight = 32;
	public int extraHeight = 32;
	
	public PropertyPanel( String label ) {
		super();
		setBorder( BorderFactory.createTitledBorder( label ) );
		setLayout( new SpringLayout() );
		changeListeners = new Vector<SVGChangeListener>();
	}
	
	public void log( Object msg ) {
//		System.out.println( PropertyPanel.class.getName() +  ": " + msg );
	}
	
	public void fireChanged( Geometry geometry ){
		log( "fireChanged: " + panel + " " + geometry );
		if (panel == null){
			
		} else {
			panel.fireSvgChanged( geometry );
		}
	}
	
	public void setProperties( JComponent [] comps, String [] labels ){
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		for (int x=0; x<labels.length; x++){
			add( new PropertyEditor( labels[x], comps[x] ) );
		}
	}
	
	public Font getLabelFont(){
		return new Font( getFont().getName(), Font.PLAIN, 10 );
	}
	
	public Font getEditorFont(){
		return new Font( getFont().getName(), Font.PLAIN, 10 );
	}

	public void svgChanged( Geometry geometry ){
	}
	
	public void svgLoaded(SVGEditablePanel panel) {
		this.panel = panel;
		setEnabled( true );
	}
	
	public SVGEditablePanel getSvgPanel(){
		return this.panel;
	}

	public void svgUnLoaded(SVGEditablePanel panel) {
		setEnabled( false );
	}

	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		for (Component c : getComponents()){
			c.setEnabled( false );
		}
	}
	
	/* SELECTION LISTENERS */
	
	public void selectionCleared() {
	}

	public void selectionAdded(Geometry... geometry) {
	}

	public void selectionRemoved(Geometry... geometry) {
	}

	public void selectionChanged(Geometry... geometry) {
	}

	public void svgMovedBack(Geometry geometry, int fromIndex) {
	}

	public void svgMovedFront(Geometry geometry, int fromIndex) {
	}

	public void svgAdded(Geometry... geometry) {
	}

	public void svgRemoved(Geometry... geometry) {
	}

}