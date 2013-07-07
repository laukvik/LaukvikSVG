package org.laukvik.svg.swing.controllers;

import java.awt.Point;
import java.awt.event.MouseEvent;

import org.laukvik.svg.swing.SVGEditablePanel;

public class MoveController implements DrawingController {
	
	SVGEditablePanel panel;
	Point start;
	Point org;
	
	public MoveController(){
	}
	
	public void setSvgPanel( SVGEditablePanel panel ) {
		this.panel = panel;
	}

	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * Org:     0px
	 * Klikk:   50px
	 * Drag:    40px;
	 * 
	 * Sum:     Org + (Klikk-Drag) 
	 * 
	 */
	public void mouseDragged(MouseEvent e) {
		log( "mouseDragged " + e.getX() + "x" +  e.getY() );
		Number x = org.x - (start.x - e.getPoint().x);
		Number y = org.y - (start.y - e.getPoint().y);
		
		panel.getSelected().move( x, y );
		panel.fireSvgChanged( panel.getSelected() );
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
		org = new Point( (int)panel.getSelected().x.getValue(), (int)panel.getSelected().y.getValue() );
	}

	public void mouseReleased(MouseEvent e) {
		start = null;
		org = null;
	}

	public void log( Object msg ) {
//		System.out.println( MoveController.class.getName() + ": " + msg );
	}
	
}