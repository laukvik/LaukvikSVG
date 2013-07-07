package org.laukvik.svg.swing.controllers;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import org.laukvik.svg.swing.SVGEditablePanel;

public class SelectController implements DrawingController {
	
	SVGEditablePanel panel;
	Point start;
	
	public SelectController(){
	}
	
	public void setSvgPanel(SVGEditablePanel panel) {
		this.panel = panel;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		log( "mouseDragged: " + e.getX() + "x" +  e.getY() );
		if (panel == null){
			/* Can't do anything without a panel */
		} else {

			int x = (start.x > e.getX()) ? e.getX() : start.x;
			int y = (start.y > e.getY()) ? e.getY() : start.y;
			
			int width = ((e.getX()-start.x) > 0) ? e.getX()-start.x : start.x - e.getX();
			int height = ((e.getY()-start.y) > 0) ? e.getY()-start.y : start.y - e.getY();
			
			panel.setSelectionRectangle( new Rectangle( x,y, width,height ) );

			panel.repaint();
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
		start = e.getPoint();
	}

	public void mouseReleased(MouseEvent e) {
		log( "mouseReleased: " + e.getX() + "x" +  e.getY() );
		panel.setSelectionRectangle( null );
		panel.repaint();
	}

	public void log( Object msg ) {
//		System.out.println( SelectController.class.getName() +  ": " + msg );
	}
	
}