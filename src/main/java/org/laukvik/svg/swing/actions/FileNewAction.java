package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import org.laukvik.svg.swing.Editor;
import org.laukvik.swing.platform.CrossPlatformUtilities;

public class FileNewAction extends SVGEditorAction {

	private static final long serialVersionUID = 1L;
	
	public FileNewAction( Editor editor ) {
		super(editor);
		putValue( Action.NAME, "New file" );
		putValue( Action.ACTION_COMMAND_KEY, "fileNew" );
//		putValue( Action.SMALL_ICON, PhotoManager.getIcon( "file/open.gif" ) );
		putValue( Action.SHORT_DESCRIPTION, "Creates an new file" ); 
		putValue( Action.ACCELERATOR_KEY, CrossPlatformUtilities.getKeystroke( KeyEvent.VK_N ) );
	}
	
	public void actionPerformed(ActionEvent e) {
		getEditor().handleNewFile();
	}

	
	
}