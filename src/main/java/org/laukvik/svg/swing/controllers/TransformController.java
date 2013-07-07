package org.laukvik.svg.swing.controllers;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.swing.SVGEditablePanel;

public class TransformController implements DrawingController {
	
	SVGEditablePanel panel;
//	private Editor editor;
	Point start;
	int direction = Geometry.UNKNOWN_DIRECTION;
	
	public TransformController(){
	}
	
	public void setSvgPanel(SVGEditablePanel panel){
		this.panel = panel;
		if (panel != null){
			panel.getSelected().setHandlesVisible( true );
			panel.repaint();
		}
	}
	
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		if (start == null){
			log( "Start is NULL!" );
		} else {
			Point amount = null;
			/**
			 * Alt som blir st¿rre er pluss. Alt som blir mindre er minus
			 * 
			 * 
			 * 
			 * 
			 */
			switch(direction){
				case Geometry.NORTH_WEST : amount = new Point( start.x-e.getPoint().x, start.y-e.getPoint().y ); break;
				case Geometry.NORTH :      amount = new Point( 0, start.y-e.getPoint().y ); break;
				
				case Geometry.NORTH_EAST : amount = new Point( e.getPoint().x-start.x, start.y-e.getPoint().y ); break;
				case Geometry.EAST :       amount = new Point( e.getPoint().x-start.x, 0 ); break;
				
				case Geometry.SOUTH_EAST : amount = new Point( e.getPoint().x-start.x, e.getPoint().y-start.y ); break;
				case Geometry.SOUTH :      amount = new Point( 0, e.getPoint().y-start.y ); break;
				
				case Geometry.SOUTH_WEST : amount = new Point( start.x-e.getPoint().x, e.getPoint().y-start.y ); break;
				case Geometry.WEST :       amount = new Point( start.x-e.getPoint().x, 0 ); break;
			}
			
			panel.getSelected().resize( direction, amount );
			panel.fireSvgChanged( panel.getSelected() );
			start = e.getPoint();	
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		if (panel!= null && panel.getSelected() != null && e.getPoint() != null){
			Point p = panel.getSelected().getHandle( e.getPoint() );
			if (p == null){
				panel.setCursor( Cursor.getDefaultCursor() );
			} else {
				int dir = panel.getSelected().getHandleDirection( p );
				panel.setCursor( getCursor( dir ) );
			}
		}
	}
	
	public Cursor getCursor( int dir  ){
		switch(dir){
			case Geometry.NORTH_WEST : return new Cursor( Cursor.NW_RESIZE_CURSOR );
			case Geometry.NORTH :      return new Cursor( Cursor.N_RESIZE_CURSOR );
			
			case Geometry.NORTH_EAST : return new Cursor( Cursor.NE_RESIZE_CURSOR );
			case Geometry.EAST :       return new Cursor( Cursor.E_RESIZE_CURSOR );
			
			case Geometry.SOUTH_EAST : return new Cursor( Cursor.SE_RESIZE_CURSOR );
			case Geometry.SOUTH :      return new Cursor( Cursor.S_RESIZE_CURSOR );
			
			case Geometry.SOUTH_WEST : return new Cursor( Cursor.SW_RESIZE_CURSOR );
			case Geometry.WEST :       return new Cursor( Cursor.W_RESIZE_CURSOR );
			default : return Cursor.getDefaultCursor();
		}
	}

	public void mousePressed(MouseEvent e) {
		start = panel.getSelected().getHandle( e.getPoint() );
		log( "mousePressed: handle=" + start + " mouse=" + e.getPoint() );
		if (start == null){
			direction = Geometry.UNKNOWN_DIRECTION;
		} else {
			direction = panel.getSelected().getHandleDirection( start );
		}		
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void log( Object msg ) {
		System.out.println( this.getClass().getName() + ": " + msg );
	}
	
}