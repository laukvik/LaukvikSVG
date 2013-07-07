package org.laukvik.svg.swing.editors;

import java.awt.Dimension;

import javax.swing.JComponent;
import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.SVG;
import org.laukvik.svg.swing.editors.geometry.PropertyPanel;
import org.laukvik.svg.swing.editors.geometry.UnitEditor;
import org.laukvik.svg.swing.editors.geometry.UnitListener;
import org.laukvik.svg.unit.Unit;

public class SVGPropertyPanel extends PropertyPanel {

	private static final long serialVersionUID = 1L;
	
	private SVG svg;
	private UnitEditor width, height;
	private TextEditor titleEditor;
	private ViewBoxEditor viewBox;

	public SVGPropertyPanel(){
		super( "SVG" );

		width = new UnitEditor();
		width.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
					svg.width = width.getUnit();
					fireChanged(svg);
				}
			}
		);
		
		height = new UnitEditor();
		height.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
					svg.height = height.getUnit();
					fireChanged(svg);
				}
			}
		);
		
		titleEditor = new TextEditor();
		titleEditor.addTextEditorListener(
			new TextEditorListener(){
				public void textChanged(String text) {
					svg.setTitle( text );
				}
			}
		);
		
		viewBox = new ViewBoxEditor();
		
        String[] labels = { "Title", "Width", "Height", "ViewBox" };
		JComponent [] comps = { titleEditor, width, height, viewBox };
		setProperties( comps, labels );
		setMaximumSize( new Dimension(rowWidth,300) );
	}

	public void selectionCleared() {
		this.svg = null;
	}

	public void selectionChanged(Geometry... geometrys) {
		if (geometrys.length == 1 && geometrys[0] instanceof SVG){
			this.svg = (SVG) geometrys[0];
			width.setUnit( svg.width );
			height.setUnit( svg.height );
			titleEditor.setText( svg.getTitle() );
			viewBox.setViewBox( svg.viewBox );
		} else { 
			this.svg = null;
		}
	}

	public void svgAdded(Geometry geometry) {
	}

	public void svgChanged(Geometry geometry) {
	}

	public void svgRemoved(Geometry geometry) {
	}

}
