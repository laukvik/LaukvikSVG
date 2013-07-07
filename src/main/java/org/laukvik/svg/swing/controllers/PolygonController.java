package org.laukvik.svg.swing.controllers;

import java.awt.event.MouseEvent;

import org.laukvik.svg.Coordinate;
import org.laukvik.svg.Cursor;
import org.laukvik.svg.shape.Group;
import org.laukvik.svg.shape.Polygon;
import org.laukvik.svg.shape.Polyline;
import org.laukvik.svg.swing.SVGEditablePanel;

public class PolygonController implements DrawingController {
	
	SVGEditablePanel panel;
	Coordinate start;
	Coordinate [] points;
	int index = -1;
	
	public PolygonController(){
	}
	
	public void setSvgPanel(SVGEditablePanel panel) {
		this.panel = panel;
	}

	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	
	public void mouseDragged(MouseEvent e) {
		log( "mouseDragged " + e.getX() + "x" +  e.getY() );
		if (start == null){
			
		} else {
			start.x = e.getX();
			start.y = e.getY();
			panel.fireSvgChanged( panel.getSelected() );
		}
	}

	public void mouseMoved(MouseEvent e) {
		if (panel.getSelected() instanceof Polygon){
			
		} else if (panel.getSelected() instanceof Polyline){
			Polyline polyline = (Polyline) panel.getSelected();

			Coordinate c = polyline.getPoints().get(  polyline.getPoints().size()-1 );
			c.x = e.getPoint().x;
			c.y = e.getPoint().y;

			panel.fireSvgChanged( panel.getSelected() );

			return;
		}
		
		/* Change mouse */
		if (points != null){
			for (Coordinate p : points){
				if (e.getX() > p.x-3 && e.getX() < p.x+3){
					if (e.getY() > p.y-3 && e.getY() < p.y+3){
						panel.setCursor( Cursor.MOVE );
						return;
					}
				}
			}
		}

		panel.setCursor( Cursor.DEFAULT );
	}

	public void mousePressed(MouseEvent e) {
		if (panel == null){
			return;
		}
		if (panel.getSelected() instanceof Polygon){

			start = null;
			int x=0;
			/* Search for points we clicked on */
			for (Coordinate p : points){
				if (e.getX() > p.x-3 && e.getX() < p.x+3){
					if (e.getY() > p.y-3 && e.getY() < p.y+3){
						start = p;
						index = x;
					}
				}
				x++;
			}
			/* Couldn't find any points - check if we clicked on the path */
			if (start == null){
				for (int index=1; index<points.length; index++){
					Coordinate p1 = points[ index-1 ];
					Coordinate p2 = points[ index ];
					if (e.getX() > p1.x && e.getX() < p2.x || e.getX() > p2.x && e.getX() < p1.x){
						if (e.getY() > p1.y && e.getY() < p2.y || e.getY() > p2.y && e.getY() < p1.y){
							log("Insert at: " + index + " "  + p1.x+"x"+p1.y + " " + p2.x+"x"+p2.y );
							if (panel.getSelected() instanceof Polygon){
								Polygon poly = (Polygon)panel.getSelected();
								poly.insert( index, new Coordinate( e.getX(), e.getY() ) );
								points = panel.getSelected().listPoints();
								panel.applyPencilFeatures( poly );
								panel.fireSvgChanged( panel.getSelected() );
							}
						}
					}
				}
			}
		
		} else if (panel.getSelected() instanceof Polyline){
			/* Presses that occures when the line is not closed */
			Polyline polyline = (Polyline) panel.getSelected();
			
			/* Check if user clicks on the first point and tries to close it */
			boolean userClickedOnFirstPoint = false;
			if (polyline.length() > 2){
				Coordinate p = polyline.getPoints().firstElement();
				if (e.getX() > p.x-3 && e.getX() < p.x+3){
					if (e.getY() > p.y-3 && e.getY() < p.y+3){
						userClickedOnFirstPoint = true;
					}
				}
			}

			log( "mousePressed: firstPoint: " + userClickedOnFirstPoint );
			if (userClickedOnFirstPoint){
				Polygon polygon = new Polygon( polyline.listPointsData() );
				
				Group group = panel.getSelected().getGroup();
				int index = group.getItems().indexOf( polyline );
				if (index != -1){
					group.getItems().set( index, polygon );
					
					panel.fireSvgRemoved( polyline );
					panel.applyPencilFeatures( polygon );
					panel.fireSvgAdded( polygon );
					panel.setSelected( polygon );
					
				}
			} else {
				polyline.add( e.getPoint() );
				panel.applyPencilFeatures( polyline );
				panel.fireSvgChanged( polyline );
			}
			


		} else if (panel.getSelected() instanceof Group){
			/* Add polyline when clicking on a group */
			Polyline poly = new Polyline( e.getPoint() );
			/* Add one more point so the polyline starts with two points where the second
			 * point is the point to be dragged around */
			poly.add( e.getPoint() );
			
			Group group = (Group) panel.getSelected();
			group.add( poly );
			
			panel.applyPencilFeatures( poly );
			
			panel.fireSvgAdded( poly );
			panel.setSelected( poly );
		} 

	}

	public void log( Object msg ) {
		System.out.println( this.getClass().getName() +  ": " + msg );
	}
	
}