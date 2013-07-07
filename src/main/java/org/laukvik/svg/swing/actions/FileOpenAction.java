package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import org.laukvik.svg.swing.Editor;
import org.laukvik.swing.platform.CrossPlatformUtilities;

public class FileOpenAction extends SVGEditorAction {

	private static final long serialVersionUID = 1L;
	
	public FileOpenAction( Editor editor ) {
		super(editor);
		putValue( Action.NAME, "Open" );
		putValue( Action.ACTION_COMMAND_KEY, "fileOpen" );
//		putValue( Action.SMALL_ICON, PhotoManager.getIcon( "file/open.gif" ) );
		putValue( Action.SHORT_DESCRIPTION, "Opens a file" ); 
		putValue( Action.ACCELERATOR_KEY, CrossPlatformUtilities.getKeystroke( KeyEvent.VK_O ) );
	}
	
	public void actionPerformed(ActionEvent e) {
		getEditor().handleOpenFile();
	}

	
	
}