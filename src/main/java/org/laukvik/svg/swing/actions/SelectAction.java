package org.laukvik.svg.swing.actions;

import javax.swing.Action;

import org.laukvik.svg.swing.controllers.SelectController;

public class SelectAction extends ControllerAction {

	private static final long serialVersionUID = 1L;
	
	public SelectAction() {
		super( new SelectController() );
		putValue( Action.NAME, "Select" );
		putValue( Action.ACTION_COMMAND_KEY, "select" );
		putValue( Action.SMALL_ICON, getIcon( "selection.tiff" ) );
	}

}