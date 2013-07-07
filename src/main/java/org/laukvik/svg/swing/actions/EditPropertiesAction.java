package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.laukvik.svg.swing.Editor;

public class EditPropertiesAction extends SVGAction {

	private static final long serialVersionUID = 1L;
	
	public EditPropertiesAction() {
		super();
		putValue( Action.NAME, "Properties" );
		putValue( Action.ACTION_COMMAND_KEY, "Properties" );
//		putValue( Action.SMALL_ICON, PhotoManager.getIcon( "file/open.gif" ) );
//		putValue( Action.SHORT_DESCRIPTION, "Opens a file" ); 
//		putValue( Action.ACCELERATOR_KEY, CrossPlatformUtilities.getKeystroke( KeyEvent.VK_O ) );
	}
	
	public void actionPerformed(ActionEvent e) {
//		getEditor().handleOpenProperties();
	}

	
	
}