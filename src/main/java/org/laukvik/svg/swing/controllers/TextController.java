package org.laukvik.svg.swing.controllers;

import java.awt.Point;
import java.awt.event.MouseEvent;

import org.laukvik.svg.shape.Group;
import org.laukvik.svg.shape.SVG;
import org.laukvik.svg.shape.Text;
import org.laukvik.svg.swing.SVGEditablePanel;
import org.laukvik.svg.unit.Unit;

public class TextController implements DrawingController {
	
	SVGEditablePanel panel;
	Point start;
	
	public TextController(){
	}
	
	public void setSvgPanel(SVGEditablePanel panel) {
		this.panel = panel;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
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
			Text text = new Text( new Unit(e.getX()), new Unit(e.getY()), "Hello world" );
			panel.applyPencilFeatures( text );
			
			
			if (panel.getSelected() instanceof Group){
				((Group) panel.getSelected()).add( text );
				panel.fireSvgAdded( text );
				panel.setSelected( text );
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		log( "mouseReleased: " + e.getX() + "x" +  e.getY() );
	}

	public void log( Object msg ) {
		System.out.println( TextController.class.getName() +  ": " + msg );
	}
	
}