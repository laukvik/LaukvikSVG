package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.laukvik.svg.swing.Editor;

public class FileSaveAsAction extends SVGEditorAction {

	private static final long serialVersionUID = 1L;
	
	public FileSaveAsAction( Editor editor ) {
		super(editor);
		putValue( Action.NAME, "Save as" );
		putValue( Action.ACTION_COMMAND_KEY, "fileSaveAs" );
//		putValue( Action.SMALL_ICON, PhotoManager.getIcon( "file/open.gif" ) );
		putValue( Action.SHORT_DESCRIPTION, "Saves a file" ); 
//		putValue( Action.ACCELERATOR_KEY, CrossPlatformUtilities.getKeystroke( KeyEvent.VK_O ) );
	}
	
	public void actionPerformed(ActionEvent e) {
		getEditor().handleSaveFileAS();
	}

	
	
}