package org.laukvik.svg.swing.actions;

import javax.swing.Action;

import org.laukvik.svg.swing.controllers.MoveController;

public class MoveAction extends ControllerAction {

	private static final long serialVersionUID = 1L;
	
	public MoveAction() {
		super( new MoveController() );
		putValue( Action.NAME, "Move" );
		putValue( Action.ACTION_COMMAND_KEY, "move" );
		putValue( Action.SMALL_ICON, getIcon( "move.gif" ) );
	}

}