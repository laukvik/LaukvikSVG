package org.laukvik.svg.swing.editors.stroke;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.laukvik.svg.SVGColor;
import org.laukvik.svg.shape.BasicShape;
import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.stroke.LineCap;
import org.laukvik.svg.stroke.LineJoin;
import org.laukvik.svg.swing.editors.TextEditorListener;
import org.laukvik.svg.swing.editors.geometry.ColorEditor;
import org.laukvik.svg.swing.editors.geometry.OpacityEditor;
import org.laukvik.svg.swing.editors.geometry.PropertyPanel;
import org.laukvik.svg.swing.editors.geometry.UnitEditor;
import org.laukvik.svg.swing.editors.geometry.UnitListener;
import org.laukvik.svg.swing.editors.text.DashArrayEditor;
import org.laukvik.svg.swing.editors.text.LineCapEditor;
import org.laukvik.svg.swing.editors.text.LineJoinEditor;
import org.laukvik.svg.unit.Unit;

public class StrokePropertyPanel extends PropertyPanel{
	
	private static final long serialVersionUID = 1L;
	private StrokeWidthEditor strokeWidth;
	private LineCapEditor strokeLineCap;
	private LineJoinEditor strokeLineJoin;
	private UnitEditor strokeMiterLimit;
	private OpacityEditor strokeOpacity;
	private ColorEditor strokeColor;
	private DashArrayEditor strokeDashArray;
	private UnitEditor strokeDashOffset;
	private BasicShape bs;

	public StrokePropertyPanel() {
		super( "Stroke" );
		strokeWidth = new StrokeWidthEditor();
		strokeWidth.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
					bs.stroke.width = strokeWidth.getUnit();
					fireChanged(bs);
				}
			}
		);
		strokeOpacity = new OpacityEditor();
		strokeOpacity.addChangeListener(
				new ChangeListener(){
					public void stateChanged(ChangeEvent e) {
						bs.stroke.opacity = strokeOpacity.getOpacity();
						fireChanged(bs);
					}
				}
		);
		
		strokeColor = new ColorEditor();
		strokeColor.addChangeListener(
			new ChangeListener(){
				public void stateChanged(ChangeEvent e) {
					bs.stroke.color = new SVGColor( strokeColor.getColor() );
					fireChanged(bs);
				}
			}
		);
		
		strokeLineCap = new LineCapEditor();
		strokeLineCap.addActionListener( 
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						bs.stroke.lineCap = (LineCap) strokeLineCap.getSelectedItem();
						fireChanged(bs);
					}
				}
		);
		
		strokeLineJoin = new LineJoinEditor();
		strokeLineJoin.addActionListener( 
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						bs.stroke.lineJoin = (LineJoin) strokeLineJoin.getSelectedItem();
						fireChanged(bs);
					}
				}
		);
		
		strokeMiterLimit = new UnitEditor();
		strokeMiterLimit.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
					bs.stroke.miterLimit = strokeMiterLimit.getUnit();
					fireChanged(bs);
				}
			}
		);
	
		strokeDashArray = new DashArrayEditor();
		strokeDashArray.addTextEditorListener(
			new TextEditorListener(){
				public void textChanged(String text) {
					bs.stroke.dashArray = strokeDashArray.getDashArray();
					fireChanged(bs);
				}
				
			}
		);
		
		strokeDashOffset = new UnitEditor();
		strokeDashOffset.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
					bs.stroke.dashOffset = strokeDashOffset.getUnit();
					fireChanged(bs);
				}
			}
		);

		
        String[] labels = { "Width", "Transparency", "Color", "LineCap", "LineJoin", "MiterLimit", "DashArray", "DashOffset"  };
		JComponent [] comps = { strokeWidth, strokeOpacity, strokeColor, strokeLineCap, strokeLineJoin, strokeMiterLimit, new JScrollPane(strokeDashArray), strokeDashOffset };
		setProperties( comps, labels );
	}




	public void selectionCleared() {
		this.bs = null;
	}

	public void selectionChanged( Geometry... geometrys) {
		if (geometrys.length == 1 && geometrys[ 0 ] instanceof BasicShape){
			this.bs = (BasicShape) geometrys[ 0 ];
			strokeWidth.setUnit( bs.stroke.width );
			strokeOpacity.setOpacity( bs.stroke.opacity  );
			strokeColor.setColor( bs.stroke.color.getValue() );
			strokeLineCap.setSelectedItem( bs.stroke.lineCap );
			strokeMiterLimit.setUnit( bs.stroke.miterLimit );
			strokeDashArray.setDashArray( bs.stroke.dashArray );
			strokeDashOffset.setUnit( bs.stroke.dashOffset );
		} else {
			this.bs = null;
		}
	}


}