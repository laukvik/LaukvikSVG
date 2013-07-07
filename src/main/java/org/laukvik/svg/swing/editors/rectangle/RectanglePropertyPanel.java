package org.laukvik.svg.swing.editors.rectangle;

import javax.swing.JComponent;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Rectangle;
import org.laukvik.svg.swing.editors.geometry.PropertyPanel;
import org.laukvik.svg.swing.editors.geometry.UnitEditor;
import org.laukvik.svg.swing.editors.geometry.UnitListener;
import org.laukvik.svg.unit.Unit;

public class RectanglePropertyPanel extends PropertyPanel {
	
	private static final long serialVersionUID = 1L;
	private Rectangle geometry;
	private UnitEditor rX, rY, rWidth, rHeight;

	public RectanglePropertyPanel() {
		super( "Rectangle" );
		rX = new UnitEditor();
		rY = new UnitEditor();
		rWidth = new UnitEditor();
		rHeight = new UnitEditor();
		
		rX.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ){
					geometry.rx = unit;
					fireChanged(geometry);
				}
			}
		);
		rY.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ){
					geometry.ry = unit;
					fireChanged(geometry);
				}
			}
		);
		
		rWidth.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ){
					geometry.width = unit;
					fireChanged(geometry);
				}
			}
		);
		rHeight.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ){
					geometry.height = unit;
					fireChanged(geometry);
				}
			}
		);
		
        String[] labels = { "CornerX", "CornerY", "Width", "Height" };
		JComponent [] comps = { rX, rY, rWidth, rHeight };
		setProperties( comps, labels );

	}

	public void selectionCleared() {
		this.geometry = null;
	}

	public void selectionChanged(Geometry... geometrys) {
		if (geometrys.length == 1 && geometrys[0] instanceof Rectangle){
			geometry = (Rectangle) geometrys[0];
			rX.setUnit( geometry.rx );
			rY.setUnit( geometry.ry );
			rWidth.setUnit( geometry.width );
			rHeight.setUnit( geometry.height );
		} else {
			this.geometry = null;
		}
	}

}