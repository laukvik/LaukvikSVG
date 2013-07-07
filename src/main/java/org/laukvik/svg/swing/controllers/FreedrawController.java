package org.laukvik.svg.swing.controllers;

import java.awt.event.MouseEvent;

import org.laukvik.svg.shape.Group;
import org.laukvik.svg.shape.Polyline;
import org.laukvik.svg.swing.SVGEditablePanel;

public class FreedrawController implements DrawingController {
	
	SVGEditablePanel panel;
	Polyline polyline;
	
	public FreedrawController(){
	}
	
	public void setSvgPanel(SVGEditablePanel panel) {
		this.panel = panel;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		if (panel == null || polyline == null){
//			log("mouseMoved: " + panel + " "  + polyline );
		} else {
//			log("mouseMoved: " + e.getPoint() );
			polyline.add( e.getPoint() );
			panel.fireSvgChanged( polyline );
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		log( "mousePressed: " + e.getX() + "x" +  e.getY() );
		if (panel == null || panel.getSelected() == null){
			/* Can't do anything without a panel or a selected item */
		} else {
			this.polyline = new Polyline( e.getPoint() );
			panel.applyPencilFeatures( polyline );
			
			if (panel.getSelected() instanceof Group){
				((Group) panel.getSelected()).add( polyline );
				panel.fireSvgAdded( polyline );
				panel.setSelected( polyline );
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		log( "mouseReleased: " + e.getX() + "x" +  e.getY() );
		this.polyline = null;
	}

	public void log( Object msg ) {
		System.out.println( FreedrawController.class.getName() +  ": " + msg );
	}
	
}