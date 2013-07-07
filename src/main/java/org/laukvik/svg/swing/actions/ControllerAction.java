package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;

import org.laukvik.svg.swing.controllers.DrawingController;

public class ControllerAction extends SVGAction {

	private static final long serialVersionUID = 1L;
	private DrawingController controller;

	public ControllerAction( DrawingController controller ) {
		super();
		setController( controller );
	}
	
	public void log( Object message ){
		System.out.println( ControllerAction.class.getName() + ": " + message ); 
	}
	
	public void setController(DrawingController controller) {
		this.controller = controller;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (getSvgPanel() == null){
			log( "Panel not found. Cant change controller!" );
		} else {
			log( "Found panel. Changing controller: " + controller );
			getSvgPanel().setDrawingController( controller );
		}
	}

}