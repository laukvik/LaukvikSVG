package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.laukvik.svg.swing.Editor;

public class FileExportAction extends SVGEditorAction {

	private static final long serialVersionUID = 1L;
	
	public FileExportAction( Editor editor ) {
		super(editor);
		putValue( Action.NAME, "Export" );
		putValue( Action.ACTION_COMMAND_KEY, "fileExport" );
//		putValue( Action.SMALL_ICON, PhotoManager.getIcon( "file/open.gif" ) );
		putValue( Action.SHORT_DESCRIPTION, "Exports a file to another format" ); 
//		putValue( Action.ACCELERATOR_KEY, CrossPlatformUtilities.getKeystroke( KeyEvent.VK_O ) );
	}
	
	public void actionPerformed(ActionEvent e) {
		getEditor().handleExportFile();
	}

	
	
}