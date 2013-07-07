package org.laukvik.svg.swing.editors.geometry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.laukvik.svg.Cursor;
import org.laukvik.svg.Visibility;
import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.SVGClass;
import org.laukvik.svg.shape.SVGID;
import org.laukvik.svg.swing.editors.TextEditor;
import org.laukvik.svg.swing.editors.TextEditorListener;
import org.laukvik.svg.unit.Pixel;
import org.laukvik.svg.unit.Unit;

public class GeometryPropertyPanel extends PropertyPanel{
	
	private static final long serialVersionUID = 1L;
	private TextEditor idTextfield, classTextfield;
	private UnitEditor spinnerX, spinnerY;
	private JCheckBox visibilityCheckBox;
	private OpacityEditor opacitySlider;
	private CursorEditor cursorComboBox;
	private Geometry geometry;

	public GeometryPropertyPanel() {
		super( "Geometry" );
		idTextfield = new TextEditor();
		idTextfield.addTextEditorListener(
			new TextEditorListener(){
				public void textChanged(String text) {
					geometry.setID( new SVGID( idTextfield.getText() ) );
					fireChanged(geometry);
				}
			}
		);
		
		classTextfield = new TextEditor();
		classTextfield.addTextEditorListener(
			new TextEditorListener(){
				public void textChanged(String text) {
					geometry.setClassCSS(  new SVGClass( classTextfield.getText() ) );
					fireChanged(geometry);
				}
			}
		);

		
		spinnerX = new UnitEditor();
		spinnerX.addUnitListener(
			new UnitListener(){
				public void unitChanged(Unit unit) {
					geometry.x = spinnerX.getUnit();
					fireChanged(geometry);
				}
			}
		);
		spinnerY = new UnitEditor();
		spinnerY.addUnitListener(
				new UnitListener(){
					public void unitChanged(Unit unit) {
						geometry.y = spinnerY.getUnit();
						fireChanged(geometry);
					}
				}
			);
		
		
		
		
		opacitySlider = new OpacityEditor();
		opacitySlider.addChangeListener(
				new ChangeListener(){
					public void stateChanged(ChangeEvent e) {
						geometry.opacity = opacitySlider.getOpacity();
						fireChanged(geometry);
					}
				}
		);
		
		
		
		visibilityCheckBox = new JCheckBox( "Visible" );
		visibilityCheckBox.addActionListener( 
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
//						System.out.println( "GeometryEditor: actionPerformed: " + geometry );
						if (geometry != null){
//							System.out.println( "GeometryEditor: setting visible to " + visibilityCheckBox.isSelected() );
							geometry.visibility = new Visibility( visibilityCheckBox.isSelected() );
							fireChanged(geometry);
						}
					}
				}
		);
		
		cursorComboBox = new CursorEditor();
		cursorComboBox.addActionListener( 
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						if (geometry != null){
							geometry.cursor = (Cursor) cursorComboBox.getSelectedItem();
							fireChanged(geometry);
						}
					}
				}
		);
		
		
		/* Add them */
        String[] labels = { "ID", "Class", "x", "y", "Visibility", "Transparency", "Cursor"  };
		JComponent [] comps = { new JScrollPane(idTextfield), new JScrollPane(classTextfield), spinnerX, spinnerY, visibilityCheckBox, opacitySlider, cursorComboBox  };
		setProperties( comps, labels );

	}

	public void selectionCleared() {
		this.geometry = null;
	}

	public void selectionChanged(Geometry... geometrys) {
		log( "GeometryPropertyPanel: " + geometry );
		if (geometrys.length == 1){
			this.geometry = geometrys[ 0 ];
			idTextfield.setText( geometry.getID().toString() );
			classTextfield.setText( geometry.getClassCSS().toString() );
			if (geometry.x == null){
				spinnerX.setUnit( new Pixel(0) );
			} else {
				spinnerX.setUnit( geometry.x );
			}
			if (geometry.y == null){
				spinnerY.setUnit( new Pixel(0) );
			} else {
				spinnerY.setUnit( geometry.y );
			}
			
			visibilityCheckBox.setSelected( geometry.visibility.isVisible() );
			opacitySlider.setOpacity( geometry.opacity );
			cursorComboBox.setSelectedCursor( geometry.cursor );
		} else {
			
		}
	}


	public void svgAdded(Geometry geometry) {
	}

	public void svgChanged(Geometry geometry) {
		selectionChanged(geometry);
	}

	public void svgRemoved(Geometry geometry) {
	}
	
}