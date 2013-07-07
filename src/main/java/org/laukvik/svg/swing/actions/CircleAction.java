package org.laukvik.svg.swing.actions;

import javax.swing.Action;

import org.laukvik.svg.swing.controllers.CircleController;

public class CircleAction extends ControllerAction {

	private static final long serialVersionUID = 1L;
	
	public CircleAction() {
		super( new CircleController() );
		putValue( Action.NAME, "Circle" );
		putValue( Action.ACTION_COMMAND_KEY, "createCircle" );
		putValue( Action.SMALL_ICON, getIcon( "oval.tiff" ) );
		putValue( Action.SHORT_DESCRIPTION, "Creates a circle" ); 
	}

}