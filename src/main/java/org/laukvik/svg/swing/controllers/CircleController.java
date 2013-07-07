package org.laukvik.svg.swing.controllers;

import java.awt.Point;
import java.awt.event.MouseEvent;

import org.laukvik.svg.shape.Ellipse;
import org.laukvik.svg.shape.Group;
import org.laukvik.svg.swing.SVGEditablePanel;
import org.laukvik.svg.unit.Unit;

public class CircleController implements DrawingController {
	
	SVGEditablePanel panel;
	Ellipse r;
	Point start;
	
	public CircleController(){
	}
	
	public void setSvgPanel(SVGEditablePanel panel) {
		this.panel = panel;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		
		
		if (e.isShiftDown()){
			r.radiusx  = new Unit( e.getX() - start.getX() );
			r.radiusy  = new Unit( e.getX() - start.getX() );
		} else {
			r.radiusx  = new Unit( e.getX() - start.getX() );
			r.radiusy  = new Unit( e.getY() - start.getY() );
		}
		

		
		
		log( "mouseDragged " + e.getX() + "x" +  e.getY() + " ( " + r.x + " " +  r.y + " , " + r.radiusx + " " + r.radiusy + " )");
		
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
		r = new Ellipse( new Unit(e.getX()), new Unit(e.getY()), new Unit(1), new Unit(1) );
		panel.applyPencilFeatures( r );
		
		if (panel.getSelected() == null){
			log( "No geometry selected!" );
		} else {
			
			Group group = null;
			if (panel.getSelected() instanceof Group){
//				((Group) panel.getSelected()).add( r );
//				panel.fireSvgAdded( r );
//				panel.setSelected( r );
				group = (Group) panel.getSelected();
			} else {
				group = panel.getSelected().getGroup();
			}
			group.add( r );
			panel.fireSvgAdded( r );
			panel.setSelected( r );
		}

	}

	public void mouseReleased(MouseEvent e) {
		log( "mouseReleased: " + e.getX() + "x" +  e.getY() );
		r = null;
	}

	public void log( Object msg ) {
//		System.out.println( CircleController.class.getName() + ": " + msg );
	}
	
}