package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import org.laukvik.svg.swing.Editor;
import org.laukvik.swing.platform.CrossPlatformUtilities;

public class FileSaveAction extends SVGEditorAction {

	private static final long serialVersionUID = 1L;
	
	public FileSaveAction( Editor editor ) {
		super(editor);
		putValue( Action.NAME, "Save" );
		putValue( Action.ACTION_COMMAND_KEY, "fileSave" );
//		putValue( Action.SMALL_ICON, PhotoManager.getIcon( "file/open.gif" ) );
		putValue( Action.SHORT_DESCRIPTION, "Saves a file" ); 
		putValue( Action.ACCELERATOR_KEY, CrossPlatformUtilities.getKeystroke( KeyEvent.VK_S ) );
	}
	
	public void actionPerformed(ActionEvent e) {
		getEditor().handleFileSave();
	}

	
	
}