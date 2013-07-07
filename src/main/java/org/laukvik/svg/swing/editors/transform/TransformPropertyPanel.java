package org.laukvik.svg.swing.editors.transform;

import javax.swing.JComponent;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.swing.editors.geometry.PropertyPanel;
import org.laukvik.svg.swing.editors.geometry.UnitEditor;
import org.laukvik.svg.swing.editors.geometry.UnitListener;
import org.laukvik.svg.unit.Pixel;
import org.laukvik.svg.unit.Unit;

/**
 * Example
 * 
 * <g transform="translate(-10,-20) scale(2) rotate(45) translate(5,10)">
 * 
 * translate
    | scale
    | rotate
    | skewX
    | skewY
 * 
 * @author morten
 * @see http://www.w3.org/TR/SVG/coords.html#TransformAttribute
 * @see http://www.carto.net/papers/svg/samples/matrix.shtml
 * 
 */
public class TransformPropertyPanel extends PropertyPanel {
	
	private static final long serialVersionUID = 1L;
	private Geometry geo;
	private UnitEditor scale, scaleY, rotate, skewX, skewY;
	private MatrixTransformEditor matrix;

	public TransformPropertyPanel() {
		super( "Transform" );
		scale = new UnitEditor();
		scale.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
//					geometry.transform.setScale( unit );
					fireChanged(geo);
				}
			}
		);
		scaleY = new UnitEditor();
		scaleY.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
//					geometry.transform.setScale( unit );
					fireChanged(geo);
				}
			}
		);
		rotate = new UnitEditor();
		rotate.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
//					geometry.transform.setScale( unit );
					fireChanged(geo);
				}
			}
		);
		skewX = new UnitEditor();
		skewX.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
//					geometry.transform.setScale( unit );
					fireChanged(geo);
				}
			}
		);
		skewY = new UnitEditor();
		skewY.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
//					geometry.transform.setScale( unit );
					fireChanged(geo);
				}
			}
		);
		matrix = new MatrixTransformEditor();
		
        String[] labels = { "ScaleX", "ScaleY", "Rotate", "SkewX", "SkewY", "Matrix" };
		JComponent [] comps = { scale, scaleY, rotate, skewX, skewY, matrix };
		setProperties( comps, labels );
	}

	public void selectionCleared() {
		this.geo = null;
	}

	public void selectionChanged(Geometry... geometrys) {
		if (geometrys.length == 1){
			Geometry g = geometrys[0];
			scale.setUnit( new Unit( g.transform.getScale().toFloatX() ) );
			scaleY.setUnit( new Unit( g.transform.getScale().toFloatY() ) );
			rotate.setUnit( new Unit( g.transform.getRotate().toFloat() ) );
			skewX.setUnit( new Unit( g.transform.getSkewX().toFloat() ) );
			skewY.setUnit( new Unit( g.transform.getSkewY().toFloat() ) );
			matrix.setMatrix( g.transform.getMatrix() );
		} else {
			this.geo = null;
		}
	}

	public void svgAdded(Geometry geometry) {
	}

	public void svgChanged(Geometry geometry) {
	}

	public void svgRemoved(Geometry geometry) {
	}

}