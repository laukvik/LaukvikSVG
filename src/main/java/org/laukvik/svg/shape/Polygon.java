package org.laukvik.svg.shape;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.laukvik.svg.Coordinate;

public class Polygon extends Polyline implements HasFill, HasStroke{
	
	public static final String POLYGON = "polygon";
	private java.awt.Polygon poly;
	
	public Polygon() {
		super();	
	}
	
	public Polygon( String points ) {
		super( points );	
	}
	
	public Polygon( Polyline polyline ){
		this();
	}

	public void paint( Graphics g, Dimension size ){
		if (points.size() == 0){
			/* Do nada */
		} else {
			/* Create the AWT polygon */
			this.poly = new java.awt.Polygon();
			for (Coordinate p : this.points){
				poly.addPoint( (int) p.x, (int) p.y );
			}

			setShape( poly );
			
			Graphics2D g2 = (Graphics2D)g;
			
			/* Fill the polygon  */
			if (fill.isVisible()){
				g2.setPaint( fill );
				g2.setComposite( createAlpha( fill.opacity ) );
				g2.fill( getShape() );
			}
			
			
			/* Stroke the polygon */
			if (stroke.isVisible()){
				g2.setColor( stroke.color.getValue() );
				g2.setComposite( createAlpha( stroke.opacity ) );
				g2.setStroke( createStroke(stroke) );
				g2.draw( getShape() );
			}
			
			/* */
			if (isHandlesVisible()){
				setHandles( poly.getBounds() );
				paintHandles(g);
			}
			if (isPointsVisible()){
				paintPoints( g );
			}
		}
	}

	public void insert(int index, Coordinate coordinate) {
		points.insertElementAt( coordinate, index );
	}

}