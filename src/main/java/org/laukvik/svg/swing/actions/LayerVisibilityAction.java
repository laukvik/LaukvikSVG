package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.laukvik.svg.shape.Geometry;

public class LayerVisibilityAction extends SVGAction {

	private static final long serialVersionUID = 1L;
	
	public LayerVisibilityAction() {
		super();
		putValue( Action.NAME, "visible" );
		putValue( Action.ACTION_COMMAND_KEY, "visible" );
//		putValue( Action.SMALL_ICON, getIcon( "up.gif" ) );
	}
	
	public void actionPerformed(ActionEvent e) {
		if (getSvgPanel() == null){
			
		} else {
			Geometry g = getSvgPanel().getSelected();
			if (g == null){
				
			} else {
				g.visibility.toggle();
				getSvgPanel().fireSvgAdded( g );
			}	
		}
	}

	
	
}