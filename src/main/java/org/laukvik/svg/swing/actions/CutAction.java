package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import org.laukvik.swing.platform.CrossPlatformUtilities;

public class CutAction extends SVGAction {

	private static final long serialVersionUID = 1L;
	
	public CutAction() {
		super();
		putValue( Action.NAME, "Cut" );
		putValue( Action.ACTION_COMMAND_KEY, "cut" );
//		putValue( Action.SMALL_ICON, PhotoManager.getIcon( "file/open.gif" ) );
//		putValue( Action.SHORT_DESCRIPTION, "Copies the selected item to clipboard" ); 
		putValue( Action.ACCELERATOR_KEY, CrossPlatformUtilities.getKeystroke( KeyEvent.VK_X ) );
	}
	
	public void actionPerformed(ActionEvent e) {
//		getEditor().cut();
		
	}	
	
}