package org.laukvik.svg.swing.editors.circle;

import javax.swing.JComponent;

import org.laukvik.svg.shape.Ellipse;
import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.swing.editors.geometry.PropertyPanel;
import org.laukvik.svg.swing.editors.geometry.UnitEditor;
import org.laukvik.svg.swing.editors.geometry.UnitListener;
import org.laukvik.svg.unit.Unit;

public class EllipsePropertyPanel extends PropertyPanel {
	
	private static final long serialVersionUID = 1L;
	private Ellipse ellipse;
	private UnitEditor radius, radiusY;

	public EllipsePropertyPanel() {
		super( "Ellipse" );
		radius = new UnitEditor();
		radius.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
					ellipse.radiusx = radius.getUnit();
					fireChanged(ellipse);
				}
			}
		);
		radiusY = new UnitEditor();
		radiusY.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
					ellipse.radiusy = radiusY.getUnit();
					fireChanged(ellipse);
				}
			}
		);
		
        String[] labels = { "RadiusX", "RadiusY" };
		JComponent [] comps = { radius, radiusY };
		setProperties( comps, labels );
	}

	public void selectionCleared() {
		this.ellipse = null;
	}

	public void selectionChanged(Geometry... geometrys) {
		if (geometrys.length == 1 && geometrys[0] instanceof Ellipse){
			this.ellipse = (Ellipse) geometrys[0];
			radius.setUnit( ellipse.radiusx );
			radiusY.setUnit( ellipse.radiusy );
		} else { 
			this.ellipse = null;
		}
	}

	public void svgAdded(Geometry geometry) {
	}

	public void svgChanged(Geometry geometry) {
	}

	public void svgRemoved(Geometry geometry) {
	}

}