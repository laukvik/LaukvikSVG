package org.laukvik.svg.swing.editors.fill;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.laukvik.svg.SVGColor;
import org.laukvik.svg.shape.BasicShape;
import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.swing.editors.geometry.ColorEditor;
import org.laukvik.svg.swing.editors.geometry.OpacityEditor;
import org.laukvik.svg.swing.editors.geometry.PropertyPanel;

public class FillPropertyPanel extends PropertyPanel{
	
	private static final long serialVersionUID = 1L;
	private BasicShape bs;
	private ColorEditor fillColor;
	private OpacityEditor fillOpacity;

	public FillPropertyPanel() {
		super( "Fill" );
		fillColor = new ColorEditor();
		fillColor.addChangeListener(
				new ChangeListener(){
					public void stateChanged(ChangeEvent e) {

							bs.fill.color = new SVGColor( fillColor.getColor() );
							fireChanged( bs );
						
					}
				}
			);
		fillOpacity = new OpacityEditor();
		fillOpacity.addChangeListener(
				new ChangeListener(){
					public void stateChanged(ChangeEvent e) {

							bs.fill.opacity = fillOpacity.getOpacity();
							fireChanged(bs);
						
					}
				}
		);
		
        String[] labels = { "Color", "Transparency"  };
		JComponent [] comps = { fillColor, fillOpacity };
		setProperties( comps, labels );      

	}

	public void selectionCleared() {
		this.bs = null;
	}

	public void selectionChanged(Geometry... geometrys) {
		if (geometrys.length == 1 && geometrys[0] instanceof BasicShape){
			BasicShape bs = (BasicShape) geometrys[0];
			this.bs = bs;
			fillColor.setColor( bs.fill.color.getValue() );
			fillOpacity.setOpacity( bs.fill.opacity );
		} else { 
			this.bs = null;
		}
	}

	public void svgAdded(Geometry geometry) {
	}

	public void svgChanged(Geometry geometry) {
	}

	public void svgRemoved(Geometry geometry) {
	}
	
}