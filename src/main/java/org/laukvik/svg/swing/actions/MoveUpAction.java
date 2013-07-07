package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Group;

public class MoveUpAction extends SVGAction {

	private static final long serialVersionUID = 1L;
	
	public MoveUpAction() {
		super();
		putValue( Action.NAME, "Forward" );
		putValue( Action.ACTION_COMMAND_KEY, "moveLayerUp" );
		putValue( Action.SMALL_ICON, getIcon( "ct_tb_arrow-up.tiff" ) );
		putValue( Action.SHORT_DESCRIPTION, "Moves the selected layer up" );
	}
	
	public void actionPerformed(ActionEvent e) {
		if (getSvgPanel() == null){
			
		} else {
			Geometry selected = getSvgPanel().getSelected();
			if (selected == null){
				
			} else {
				int fromIndex = selected.indexOf();
				Group g = selected.getGroup();
				g.moveFront( selected );
				getSvgPanel().fireSvgMovedFront( selected, fromIndex );
			}
		}
	}
	
}