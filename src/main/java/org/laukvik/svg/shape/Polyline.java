package org.laukvik.svg.shape;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

import org.laukvik.svg.Coordinate;
import org.laukvik.svg.shape.path.PathParser;
import org.laukvik.svg.unit.Unit;

public class Polyline extends BasicShape implements HasStroke {
	
	public static final String POLYLINE = "polyline";
	public static final String POINTS = "points";
	Vector <Coordinate> points;
	private java.awt.geom.GeneralPath polyline;

	public Polyline(){
		super();
		this.points = new Vector<Coordinate>();
	}
	
	public Polyline( String points ) {
		this();
		setPoints( points );
	}
	
	public Polyline( Point point ) {
		this();
		this.x = new Unit( point.x );
		this.y = new Unit( point.y );
		add( point );
	}
	
	public Vector<Coordinate> getPoints() {
		return points;
	}
	
	/**
	 * Lists the points data as a SPACE separated list
	 * 
	 * @return
	 */
	public String listPointsData(){
		StringBuffer buffer = new StringBuffer();
		int x=0;
		for (Coordinate p : points){
			if (x > 0){
				buffer.append( " " );
			}
			buffer.append( p.toString() );
			x++;
		}
		return buffer.toString();
	}
	
	public void move( Number x, Number y ){
		float deltaX = this.x.getValue() - x.floatValue();
		float deltaY = this.y.getValue() - y.floatValue();
		for (Coordinate p : points){
			p.x -= deltaX;
			p.y -= deltaY;
		}
		this.x = new Unit( x );
		this.y = new Unit( y );
	}
	
	public void setPoints( String points ){
		this.points = new Vector<Coordinate>();
		Coordinate [] ps = PathParser.listPoints( points );
		for (Coordinate p : ps){
			this.points.add( p );
		}
	}
	
	public void paint( Graphics g, Dimension size ){
		if (points.size() == 0){
			/* Do nada */
		} else {
			this.polyline = new java.awt.geom.GeneralPath();
			int count = 0;
			for (Coordinate p : this.points){
				if (count == 0){
					polyline.moveTo( (int) p.x, (int) p.y );
				} else {
					polyline.lineTo( (int) p.x, (int) p.y );
				}
				count++;

			}
			
			setShape( polyline );
			
			Graphics2D g2 = (Graphics2D) g;
			g2.setComposite( createAlpha( stroke.opacity ) );
			g2.setPaint( stroke.color );
			g2.setStroke( createStroke(stroke) );
//			Coordinate old = null;
//			for (Coordinate p : points){
//				if (old != null){
//					g.drawLine( (int) old.x, (int) old.y, (int) p.x, (int) p.y );
//				}
//				old = p;
//			}
			g2.draw( getShape() );
			
			/* */
			if (isHandlesVisible()){
				setHandles( polyline.getBounds() );
				paintHandles(g);
			} 
			if (isPointsVisible()){
				paintPoints( g );
			}
		}
	}
	
	public Coordinate [] listPoints(){
		Coordinate [] ps = new Coordinate[ points.size() ];
		points.toArray( ps );
		return ps;
	}
	
	public void add( Point p ){
		this.points.add( new Coordinate( p.x, p.y ) );
	};
	
	public void add( Coordinate p ){
		this.points.add( p );
	};
	
	public void remove( Coordinate p ){
		this.points.remove( p );
	};
	
	public int length(){
		return points.size();
	}

	public void resize( int direction, int amount ){
		
		switch(direction){
		
			case NORTH : 
				this.y = new Unit( y.getValue() - amount );
				resizeY( amount );
				break;
				
			case SOUTH : 
				resizeY( amount );
				break;
				
			case EAST : 
				resizeX( amount );
				break;
				
			case WEST : 
				this.x = new Unit( x.getValue() - amount );
				resizeX( amount );
				break;
		}
	}

	/**
	 * 
	 * 
	 * 
	 * @param amount
	 */
	public void resizeY( float amount ){
//		this.polyline = new java.awt.Polygon();
//		for (Coordinate p : this.points){
//			polyline.addPoint( (int) p.x, (int) p.y );
//		}
//		float factor = amount / (polyline.getBounds().height + amount*2);
//		int startY = polyline.getBounds().y;
//		int origoY = polyline.getBounds().height / 2 + startY;
//
//		for (Coordinate p : points){
//			float deltaY = p.y - origoY;
//			p.y += deltaY * factor*2;
//		}
	}
	
	public void resizeX( float amount ){
//		this.polyline = new java.awt.Polygon();
//		for (Coordinate p : this.points){
//			polyline.addPoint( (int) p.x, (int) p.y );
//		}
//		/* Calculate factor to increase with */
//		float factor = amount / (polyline.getBounds().width + amount*2);
////		System.out.println( "Width: " + polyline.getBounds().width + " amount: " + amount + " factor: " + factor );
//		int startX = polyline.getBounds().x;
//		int origoX = polyline.getBounds().width / 2 + startX;
//
//		for (Coordinate p : points){
//			float deltaX = p.x - origoX;
//			p.x += deltaX * factor*2;
//		}
	}

}