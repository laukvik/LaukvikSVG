package org.laukvik.svg.swing.actions;

import javax.swing.Action;

import org.laukvik.svg.swing.controllers.RectangleController;

public class RectangleAction extends ControllerAction {

	private static final long serialVersionUID = 1L;

	public RectangleAction() {
		super( new RectangleController() );
		putValue( Action.NAME, "Rectangle" );
		putValue( Action.ACTION_COMMAND_KEY, "createRectangle" );
		putValue( Action.SMALL_ICON, getIcon( "rectangle.tiff" ) );
		putValue( Action.SHORT_DESCRIPTION, "Creates a rectangle" ); 
	}

}