package org.laukvik.svg.swing.actions;

import javax.swing.Action;

import org.laukvik.svg.swing.controllers.TransformController;

public class TransformAction extends ControllerAction {

	private static final long serialVersionUID = 1L;
	
	public TransformAction() {
		super( new TransformController() );
		putValue( Action.NAME, "Transform" );
		putValue( Action.ACTION_COMMAND_KEY, "transform" );
		putValue( Action.SMALL_ICON, getIcon( "transform.gif" ) );
		putValue( Action.SHORT_DESCRIPTION, "Enables transformation" ); 
	}

}