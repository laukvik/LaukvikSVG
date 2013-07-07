package org.laukvik.svg.swing.editors.line;

import javax.swing.JComponent;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Line;
import org.laukvik.svg.swing.editors.geometry.PropertyPanel;
import org.laukvik.svg.swing.editors.geometry.UnitEditor;
import org.laukvik.svg.swing.editors.geometry.UnitListener;
import org.laukvik.svg.unit.Unit;

public class LinePropertyPanel extends PropertyPanel{
	
	private static final long serialVersionUID = 1L;
	private Line line;
	private UnitEditor radius, radiusY;

	public LinePropertyPanel() {
		super( "Line" );
		radius = new UnitEditor();
		radius.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
					line.x2 = radius.getUnit();
					fireChanged(line);
				}
			}
		);
		radiusY = new UnitEditor();
		radiusY.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
					line.y2 = radiusY.getUnit();
					fireChanged(line);
				}
			}
		);
		
        String[] labels = { "DestX", "DestY" };
		JComponent [] comps = { radius, radiusY };
		setProperties( comps, labels );
	}

	public void selectionCleared() {
		this.line = null;
	}

	public void selectionChanged(Geometry... geometrys) {
		if (geometrys.length == 1 && geometrys[0] instanceof Line){
			this.line = (Line) geometrys[0];
			radius.setUnit( line.x2 );
			radiusY.setUnit( line.y2 );
		} else { 
			this.line = null;
		}
	}

}
