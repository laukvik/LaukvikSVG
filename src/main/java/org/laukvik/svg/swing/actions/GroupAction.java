package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.laukvik.svg.shape.Group;
import org.laukvik.svg.swing.controllers.TextController;

public class GroupAction extends ControllerAction {

	private static final long serialVersionUID = 1L;
	
	public GroupAction() {
		super( new TextController() );
		putValue( Action.NAME, "Group" );
		putValue( Action.ACTION_COMMAND_KEY, "group" );
		putValue( Action.SMALL_ICON, getIcon( "group.tiff" ) );
		putValue( Action.SHORT_DESCRIPTION, "Creates a new group of the selected items" ); 
	}
	
	public void actionPerformed(ActionEvent e) {
		if (getSvgPanel() == null){
			
		} else {
			Group group = (Group) getSvgPanel().getSVG().createGroup( getSvgPanel().getSelection() );
			getSvgPanel().fireSvgAdded( group );
		}
	}

}