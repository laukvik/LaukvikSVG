package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import org.laukvik.swing.platform.CrossPlatformUtilities;

public class PasteAction extends SVGAction {

	private static final long serialVersionUID = 1L;
	
	public PasteAction() {
		super();
		putValue( Action.NAME, "Paste" );
		putValue( Action.ACTION_COMMAND_KEY, "paste" );
//		putValue( Action.SMALL_ICON, PhotoManager.getIcon( "file/open.gif" ) );
//		putValue( Action.SHORT_DESCRIPTION, "Copies the selected item to clipboard" ); 
		putValue( Action.ACCELERATOR_KEY, CrossPlatformUtilities.getKeystroke( KeyEvent.VK_V ) );
	}
	
	public void actionPerformed(ActionEvent e) {
//		getEditor().paste();
	}

	
	
}