package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Group;

public class RotateCwAction extends SVGAction {

	private static final long serialVersionUID = 1L;
	
	public RotateCwAction() {
		super();
		putValue( Action.NAME, "Rotate" );
		putValue( Action.ACTION_COMMAND_KEY, "rotateCW" );
		putValue( Action.SMALL_ICON, getIcon( "tools_rotate-CW.tiff" ) );
		putValue( Action.SHORT_DESCRIPTION, "Rotates 90 degrees clockwise" ); 
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