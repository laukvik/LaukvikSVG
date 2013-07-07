package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.laukvik.svg.SVGSource;
import org.laukvik.svg.swing.Editor;

public class FileOpenRecentAction extends SVGEditorAction {

	private static final long serialVersionUID = 1L;
	String filename;
	
	public FileOpenRecentAction( Editor editor, String filename ) {
		super(editor);
		this.filename = filename;
		putValue( Action.NAME, filename );
		putValue( Action.ACTION_COMMAND_KEY, "fileOpen" );
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			getEditor().loadSVG( new SVGSource( filename ) );
		} catch (Exception e1) {
		}
	}
	
}