package org.laukvik.svg.swing.actions;

import javax.swing.Action;

import org.laukvik.svg.swing.controllers.FreedrawController;

public class DrawAction extends ControllerAction {

	private static final long serialVersionUID = 1L;
	
	public DrawAction() {
		super( new FreedrawController() );
		putValue( Action.NAME, "freedraw" );
		putValue( Action.ACTION_COMMAND_KEY, "freedraw" );
		putValue( Action.SMALL_ICON, getIcon( "shapedraw.tiff" ) );
		putValue( Action.SHORT_DESCRIPTION, "Creates a line" ); 
	}

}