package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Group;

public class MoveDownAction extends SVGAction {

	private static final long serialVersionUID = 1L;
	
	public MoveDownAction() {
		super();
		putValue( Action.NAME, "Backwards" );
		putValue( Action.ACTION_COMMAND_KEY, "moveLayerDown" );
		putValue( Action.SMALL_ICON, getIcon( "ct_tb_arrow-down.tiff" ) );
		putValue( Action.SHORT_DESCRIPTION, "Moves the selected layer down" );
	}
	
	public void actionPerformed(ActionEvent e) {
		if (getSvgPanel() == null){
			
		} else {
			Geometry selected = getSvgPanel().getSelected();
			if (selected == null){
				
			} else {
				Group g = selected.getGroup();
				int fromIndex = selected.indexOf();
				g.moveBehind( selected ); 
				getSvgPanel().fireSvgMovedBack( selected, fromIndex );
			}
		}
	}

}