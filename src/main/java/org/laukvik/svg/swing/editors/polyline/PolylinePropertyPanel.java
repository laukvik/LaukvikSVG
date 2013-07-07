package org.laukvik.svg.swing.editors.polyline;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Polyline;
import org.laukvik.svg.swing.editors.TextEditor;
import org.laukvik.svg.swing.editors.TextEditorListener;
import org.laukvik.svg.swing.editors.geometry.PropertyPanel;

public class PolylinePropertyPanel extends PropertyPanel{
	
	private static final long serialVersionUID = 1L;
	private Polyline polyline;
	private TextEditor textArea;

	public PolylinePropertyPanel() {
		super( "Polyline" );
		textArea = new TextEditor();
		textArea.setLineWrap( true );
		textArea.setRows( 5 );
		textArea.addTextEditorListener(
				new TextEditorListener(){
					public void textChanged(String text2) {	
						polyline.setPoints( text2 );
						fireChanged(polyline);
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
		this.polyline = null;
	}

	public void selectionChanged(Geometry... geometrys) {
		if (geometrys.length == 1 && geometrys[0] instanceof Polyline){
			this.polyline = (Polyline) geometrys[0];
			textArea.setText( polyline.listPointsData() );
		} else {
			this.polyline = null;
		}
	}

}