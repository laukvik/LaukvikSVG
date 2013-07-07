package org.laukvik.svg.swing.actions;

import javax.swing.Action;

public class ColorAction extends SVGAction {

	private static final long serialVersionUID = 1L;
	
	public ColorAction() {
		super();
		putValue( Action.NAME, "Color" );
		putValue( Action.ACTION_COMMAND_KEY, "color" );
//		putValue( Action.SMALL_ICON, getIcon( "selection.tiff" ) );
	}

}