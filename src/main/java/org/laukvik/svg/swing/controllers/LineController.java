package org.laukvik.svg.swing.controllers;

import java.awt.Point;
import java.awt.event.MouseEvent;

import org.laukvik.svg.SVGColor;
import org.laukvik.svg.shape.Group;
import org.laukvik.svg.shape.Line;
import org.laukvik.svg.swing.SVGEditablePanel;
import org.laukvik.svg.unit.Unit;

public class LineController implements DrawingController {
	
	SVGEditablePanel panel;
	Point start;
	Line line;
	
	public LineController(){
	}
	
	public void setSvgPanel(SVGEditablePanel panel) {
		this.panel = panel;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		line.x2 = new Unit( e.getX() );
		line.y2 = new Unit( e.getY() );
		panel.fireSvgChanged( line );
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
			start = e.getPoint();
			this.line = new Line( new Unit(e.getX()), new Unit(e.getY()), new Unit(e.getX()+1), new Unit(e.getY()+1), SVGColor.black  );
			panel.applyPencilFeatures( line );
			
			if (panel.getSelected() instanceof Group){
				((Group) panel.getSelected()).add( line );
				panel.fireSvgAdded( line );
				panel.setSelected( line );
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		log( "mouseReleased: " + e.getX() + "x" +  e.getY() );
		line.x2 = new Unit( e.getX() );
		line.y2 = new Unit( e.getY() );
		panel.fireSvgChanged( line );
		line = null;
	}

	public void log( Object msg ) {
		System.out.println( LineController.class.getName() +  ": " + msg );
	}
	
}