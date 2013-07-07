package org.laukvik.svg.swing.actions;

import javax.swing.Action;

import org.laukvik.svg.swing.controllers.LineController;

public class LineAction extends ControllerAction {

	private static final long serialVersionUID = 1L;
	
	public LineAction() {
		super( new LineController() );
		putValue( Action.NAME, "Line" );
		putValue( Action.ACTION_COMMAND_KEY, "line" );
		putValue( Action.SMALL_ICON, getIcon( "line.tiff" ) );
		putValue( Action.SHORT_DESCRIPTION, "Creates a line" ); 
	}

}