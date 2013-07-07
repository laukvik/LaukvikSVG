package org.laukvik.svg.swing.editors.polygon;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Polygon;
import org.laukvik.svg.swing.editors.TextEditor;
import org.laukvik.svg.swing.editors.TextEditorListener;
import org.laukvik.svg.swing.editors.geometry.PropertyPanel;

public class PolygonPropertyPanel extends PropertyPanel {
	
	private static final long serialVersionUID = 1L;
	private Polygon polygon;
	private TextEditor textArea;

	public PolygonPropertyPanel() {
		super( "Polygon" );
		textArea = new TextEditor();
		textArea.setLineWrap( true );
		textArea.setRows( 5 );
		textArea.addTextEditorListener(
				new TextEditorListener(){
					public void textChanged(String text2) {	
						polygon.setPoints( text2 );
						fireChanged(polygon);
					}
				}
		);
		
		
		JScrollPane textScroll = new JScrollPane( textArea );
        String[] labels = { "Points"  };
		JComponent [] comps = { textScroll  };
		/**/
		setProperties( comps, labels );

	}

	public void selectionCleared() {
		this.polygon = null;
	}

	public void selectionChanged(Geometry... geometrys) {
		if (geometrys.length == 1 && geometrys[0] instanceof Polygon){
			this.polygon = (Polygon) geometrys[0];
			textArea.setText( polygon.listPointsData() );
		} else { 
			this.polygon = null;
		}
	}

}
