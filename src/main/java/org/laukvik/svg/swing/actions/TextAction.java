package org.laukvik.svg.swing.actions;

import javax.swing.Action;

import org.laukvik.svg.swing.controllers.TextController;

public class TextAction extends ControllerAction {

	private static final long serialVersionUID = 1L;
	
	public TextAction() {
		super( new TextController() );
		putValue( Action.NAME, "Text" );
		putValue( Action.ACTION_COMMAND_KEY, "createText" );
		putValue( Action.SMALL_ICON, getIcon( "stb_text_tool.tiff" ) );
		putValue( Action.SHORT_DESCRIPTION, "Creates a text" ); 
	}

}