package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import org.laukvik.svg.swing.Editor;
import org.laukvik.swing.platform.CrossPlatformUtilities;

public class OpenURLAction extends SVGEditorAction {

	private static final long serialVersionUID = 1L;
	
	public OpenURLAction( Editor editor ) {
		super(editor);
		putValue( Action.NAME, "Open URL" );
		putValue( Action.ACTION_COMMAND_KEY, "urlOpen" );
//		putValue( Action.SMALL_ICON, PhotoManager.getIcon( "file/open.gif" ) );
		putValue( Action.SHORT_DESCRIPTION, "Opens a URL" ); 
		putValue( Action.ACCELERATOR_KEY, CrossPlatformUtilities.getKeystroke( KeyEvent.VK_L ) );
	}
	
	public void actionPerformed(ActionEvent e) {
		getEditor().handleOpenURL();
	}

	
	
}