package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Group;

public class DeleteLayerAction extends SVGAction {

	private static final long serialVersionUID = 1L;
	
	public DeleteLayerAction() {
		super();
		putValue( Action.NAME, "deleteLayer" );
		putValue( Action.ACTION_COMMAND_KEY, "deleteLayer" );
		putValue( Action.SMALL_ICON, getIcon( "trash-empty.tiff" ) );
	}
	
	public void actionPerformed(ActionEvent e) {
		if (getSvgPanel() == null){
			/* Panel not specified yet */
		} else {
			Geometry g = getSvgPanel().getSelected();
			if (g == null){
				
			} else {
				Group group = g.getGroup();
				System.out.println( group );
				group.remove( g );
				getSvgPanel().fireSvgRemoved( g );
			}
		}
	}

}