package org.laukvik.svg.swing.editors.circle;

import javax.swing.JComponent;

import org.laukvik.svg.shape.Circle;
import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.swing.editors.geometry.PropertyPanel;
import org.laukvik.svg.swing.editors.geometry.UnitEditor;
import org.laukvik.svg.swing.editors.geometry.UnitListener;
import org.laukvik.svg.unit.Unit;

public class CirclePropertyPanel extends PropertyPanel {
	
	private static final long serialVersionUID = 1L;
	private Circle circle;
	private UnitEditor radius;

	public CirclePropertyPanel() {
		super( "Circle" );
		radius = new UnitEditor();
		radius.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
					circle.radius = radius.getUnit();
					fireChanged(circle);
				}
			}
		);
		
        String[] labels = { "Radius" };
		JComponent [] comps = { radius };
		setProperties( comps, labels );
	}

	public void selectionCleared() {
		this.circle = null;
	}

	public void selectionChanged(Geometry... geometrys) {
		if (geometrys.length == 1 && geometrys[0] instanceof Circle){
			this.circle = (Circle) geometrys[0];
			radius.setUnit( circle.radius );
		} else { 
			this.circle = null;
		}
	}

	public void svgAdded(Geometry geometry) {
	}

	public void svgChanged(Geometry geometry) {
	}

	public void svgRemoved(Geometry geometry) {
	}

}