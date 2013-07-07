package org.laukvik.svg.swing.editors.path;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Path;
import org.laukvik.svg.swing.editors.TextEditor;
import org.laukvik.svg.swing.editors.TextEditorListener;
import org.laukvik.svg.swing.editors.geometry.PropertyPanel;

public class PathPropertyPanel extends PropertyPanel {
	
	private static final long serialVersionUID = 1L;
	private Path path;
	private TextEditor textEditorPath;

	public PathPropertyPanel() {
		super( "Path" );
		textEditorPath = new TextEditor();
		textEditorPath.setLineWrap( true );
		textEditorPath.setRows( 5 );
		textEditorPath.addTextEditorListener(
				new TextEditorListener(){
					public void textChanged(String text2) {	
						path.setElements( text2 );
						fireChanged(path);
					}
				}
		);
		
		
		JScrollPane textScroll = new JScrollPane( textEditorPath );
        String[] labels = { "Points"  };
		JComponent [] comps = { textScroll  };
		/**/
		setProperties( comps, labels );

	}
	
	public void selectionCleared() {
		this.path = null;
	}

	public void selectionChanged(Geometry... geometrys) {
		if (geometrys.length == 1 && geometrys[0] instanceof Path){
			this.path = (Path) geometrys[0];
			textEditorPath.setText( path.listData() );
		} else { 
			this.path = null;
		}
	}

}