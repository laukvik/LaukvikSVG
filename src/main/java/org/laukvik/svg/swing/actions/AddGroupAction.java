package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Group;

public class AddGroupAction extends SVGAction {

	private static final long serialVersionUID = 1L;
	
	public AddGroupAction() {
		super();
		putValue( Action.NAME, "addGroup" );
		putValue( Action.ACTION_COMMAND_KEY, "addGroup" );
		putValue( Action.SMALL_ICON, getIcon( "export.tiff" ) );
	}
	
	public void actionPerformed(ActionEvent e) {
		if (getSvgPanel() == null){
			
		} else {
			Geometry g = getSvgPanel().getSelected();
			Group group = g.getGroup();
			if (group == null){
				
			} else {
				int index = g.indexOf();
				getSvgPanel().fireSvgAdded( group.add( new Group(), index+1 ) );
			}	
		}
	}

}