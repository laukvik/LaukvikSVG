package org.laukvik.svg.swing.controllers;

import java.awt.Point;
import java.awt.event.MouseEvent;

import org.laukvik.svg.shape.Group;
import org.laukvik.svg.shape.Rectangle;
import org.laukvik.svg.swing.SVGEditablePanel;
import org.laukvik.svg.unit.Unit;

public class RectangleController implements DrawingController {
	
	SVGEditablePanel panel;
	Rectangle r;
	Point start;
	
	public RectangleController(){
	}
	
	public void setSvgPanel(SVGEditablePanel panel) {
		this.panel = panel;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		r.width  = new Unit( e.getX() - start.getX() );
		r.height = new Unit( e.getY() - start.getY() );
		
		log( "mouseDragged " + e.getX() + "x" +  e.getY() + " ( " + r.x + " " +  r.y + " , " + r.width + " " +  r.height + " )");
		
		panel.fireSvgChanged( r );
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		log( "mousePressed: " + e.getX() + "x" +  e.getY() );
		start = e.getPoint();
		r = new Rectangle( new Unit(e.getX()), new Unit(e.getY()), new Unit(1), new Unit(1) );
		panel.applyPencilFeatures( r );
		
		if (panel == null || panel.getSelected() == null){
			log( "No geometry selected!" );
		} else {
			if (panel.getSelected() instanceof Group){
				((Group) panel.getSelected()).add( r );
				panel.fireSvgAdded( r );
				panel.setSelected( r );
			}
		}

	}

	public void mouseReleased(MouseEvent e) {
		log( "mouseReleased: " + e.getX() + "x" +  e.getY() );
		r = null;
	}

	public void log( Object msg ) {
//		System.out.println( RectangleController.class.getName() + ": " + msg );
	}
	
}