package org.laukvik.svg.swing.actions;

import javax.swing.Action;

import org.laukvik.svg.swing.controllers.PolygonController;

public class PolygonAction extends ControllerAction {

	private static final long serialVersionUID = 1L;
	
	public PolygonAction() {
		super( new PolygonController() );
		putValue( Action.NAME, "Polygon" );
		putValue( Action.ACTION_COMMAND_KEY, "createPolygon" );
		putValue( Action.SMALL_ICON, getIcon( "polygon.tiff" ) );
		putValue( Action.SHORT_DESCRIPTION, "Creates a polygon" ); 
	}

}