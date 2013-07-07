package org.laukvik.svg.shape;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.Vector;
import org.laukvik.svg.Coordinate;
import org.laukvik.svg.Cursor;
import org.laukvik.svg.Opacity;
import org.laukvik.svg.Visibility;
import org.laukvik.svg.stroke.Stroke;
import org.laukvik.svg.transform.Transform;
import org.laukvik.svg.unit.Unit;

public class Geometry extends Element{
	
	/** 0 = total see through and 100% = no see through */
	public Opacity opacity = Opacity.OPAQUE;
	public Unit x = new Unit(0);
	public Unit y = new Unit(0);
	public Description description;
	public Transform transform = new Transform();
	public Cursor cursor = Cursor.DEFAULT;
	public Visibility visibility = Visibility.VISIBLE;
	private boolean showHandles = false;
	private Point [] handles = new Point[8];
	/** The radius of each handle */
	private final static int HANDLE_SIZE = 3;
	private boolean showPoints = false;
	
	public final static int UNKNOWN_DIRECTION = -1;
	/* Corners */
	public final static int NORTH_WEST = 0;
	public final static int NORTH_EAST = 1;
	public final static int SOUTH_EAST = 2;
	public final static int SOUTH_WEST = 3;
	/* Directions */
	public final static int NORTH      = 4;
	public final static int EAST       = 5;
	public final static int SOUTH      = 6;
	public final static int WEST       = 7;
	
	private Vector <MouseFocusListener>  mouseFocusListeners = new Vector<MouseFocusListener>();
	private java.awt.Shape shape;
	
	
	public Geometry(){
	}
	
	public Geometry( Unit x, Unit y ){
		this.x = x;
		this.y = y;
	}
	
	public int indexOf(){
		return getGroup().getItems().indexOf( this );
	}
	
	public void setHandlesVisible(boolean showHandles) {
		this.showHandles = showHandles;
	}
	
	public boolean isHandlesVisible() {
		return showHandles;
	}
	
	/**
	 * Returns an array of all points which can be used for resizing/moving 
	 * 
	 * @return
	 */
	public Coordinate [] listPoints(){
		return new Coordinate[ 0 ];
	}
	
	/**
	 * Checks whether the specified point is a resize point you can
	 * use to transform the object
	 * 
	 * @param point
	 * @return
	 */
	public boolean isHandle( Point point ){
		Point p = getHandle( point );
		return p != null;
	}
	
	/**
	 * Returns the direction for the specified handle
	 * 
	 * @param point
	 * @return
	 */
	public int getHandleDirection( Point point ){
		if (point == listHandles()[ 0 ]){
			return NORTH_WEST;
		}
		if (point == listHandles()[ 1 ]){
			return NORTH_EAST;
		}
		if (point == listHandles()[ 2 ]){
			return SOUTH_EAST;
		}
		if (point == listHandles()[ 3 ]){
			return SOUTH_WEST;
		}
		
		if (point == listHandles()[ 4 ]){
			return NORTH;
		}
		if (point == listHandles()[ 5 ]){
			return EAST;
		}
		if (point == listHandles()[ 6 ]){
			return SOUTH;
		}
		if (point == listHandles()[ 7 ]){
			return WEST;
		}
		return UNKNOWN_DIRECTION;
	}
	
	public void setHandle( int direction, Point point ){
		handles[ direction ] = point;
	}
	
	public Point getHandle( int direction ){
		return handles[ direction ];
	}
	
	public void setHandles( int x, int y, int width, int height ){
		handles = new Point [] { 
			new Point(x, y),  new Point(x+width, y), new Point(x+width, y+height), new Point(x, y+height), // Corners
			new Point(x+width/2, y),  new Point(x+width, y+height/2), new Point(x+width/2, y+height), new Point(x, y+height/2)  // Directions
		};
	}
	
	public void setHandles( java.awt.Rectangle rect ){
		setHandles( rect.x, rect.y, rect.width, rect.height );
	}

	/**
	 * Returns the handle point for the specified point
	 * 
	 * @param point
	 * @return
	 */
	public Point getHandle(Point point) {
		if (handles == null){
			return null;
		}
		for (Point p : handles){
			if (p == null){
				/* Handles not set yet */
			} else {
				if (point.x >= p.x-HANDLE_SIZE && point.x <= p.x+HANDLE_SIZE){
					if (point.y >= p.y-HANDLE_SIZE && point.y <= p.y+HANDLE_SIZE){
						return p;
					}
				}	
			}

		}
		return null;
	}
	
	/**
	 * Paints a resize/move handle
	 * 
	 * @param g
	 * @param x
	 * @param y
	 */
	public void paintHandle( Graphics g, int x, int y ){
		g.drawOval( x-HANDLE_SIZE, y-HANDLE_SIZE, HANDLE_SIZE*2, HANDLE_SIZE*2 );
	}
	
	/**
	 * Paints all handles
	 * 
	 * @param g
	 */
	public void paintHandles( Graphics g ){
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke( new BasicStroke( 1 ) );
		g2.setColor( Color.BLACK );
		for (Point p : handles){
			paintHandle( g, p.x, p.y );
		}
	}
	
	public void paintPoints( Graphics g ){
		for (Coordinate p : listPoints()){
			paintHandle(g, (int) p.x, (int) p.y );
		}
	}
	
	public Point [] listHandles(){
		return handles;
	}
	
	public synchronized void setHandles( Point [] points ){
		this.handles = points;
	}
	
	public void paint( Graphics g, Dimension size ){
	}
	
	public int toPixels( Unit unit ){
		return group.toPixels(unit);
	}
	
	public int toHorisontalPixels( Unit unit ){
		return group.toHorisontalPixels( unit );
	}
	
	public int toVerticalPixels( Unit unit ){
		return group.toVerticalPixels( unit );
	}
	
	public void setPointsVisible(boolean showPoints) {
		this.showPoints = showPoints;
	}
	
	public boolean isPointsVisible() {
		return showPoints;
	}
	
	
	
	/**
	 * BasicStroke()
	 * BasicStroke(float width) 
	 * BasicStroke(float width, int cap, int join)
	 * BasicStroke(float width, int cap, int join, float miterlimit)
	 * BasicStroke(float width, int cap, int join, float miterlimit, float[] dash, float dash_phase)
	 * 
	 * @param stroke
	 * @return
	 */
	public java.awt.Stroke createStroke( Stroke stroke ) {
		if (stroke.dashArray.isEmpty()){
			return new BasicStroke( stroke.width.getValue(), stroke.lineCap.getValue(), stroke.lineJoin.getValue(), stroke.miterLimit.getValue() );
		} else {
			int offset = toPixels( stroke.dashOffset );
			return new BasicStroke( stroke.width.getValue(), stroke.lineCap.getValue(), stroke.lineJoin.getValue(), stroke.miterLimit.getValue(), stroke.dashArray.list(), offset );
		}
	}
	
	/**
	 * 100-10% / 100 = 90/100 = 0.9
	 * 
	 * 
	 * @param p
	 * @return
	 */
	public AlphaComposite createAlpha( Opacity opacity ){
		float n = opacity.toFloat();
		return AlphaComposite.getInstance( AlphaComposite.SRC_OVER, n );
	}
	
	public AlphaComposite createNoAlpha( Opacity opacity ){
		return AlphaComposite.Clear;
	}
	
	/**
	 * 
	 * @param opacity
	 * @param g
	 */
	public void setOpacity( Opacity opacity, Graphics g ){
		((Graphics2D)g).setComposite( createAlpha( opacity ) );
	}
	
	public AffineTransform createTransform( Transform transform ){
		AffineTransform toCenterAt = new AffineTransform();
		return toCenterAt;
	}

	/**
	 * Resizes the object a specific amount of pixels in the specified direction
	 * 
	 * rect.resize( NORTH_EAST, new Point(2,6) )
	 * rect.resize( NORTH, 5 )
	 * 
	 * @param direction
	 * @param point
	 */
	public void resize( int direction, int amount ){
	}
	
	public void resize( int direction, Point amount ){
		/* North */
		if (direction == NORTH_WEST || direction == NORTH || direction == NORTH_EAST){
			resize( NORTH, amount.y );
		} else 
		/* South */
		if (direction == SOUTH_WEST || direction == SOUTH || direction == SOUTH_EAST){
			resize( SOUTH, amount.y );
		}
		/* East */
		if (direction == NORTH_EAST|| direction == EAST || direction == SOUTH_EAST){
			resize( EAST, amount.x );
		} else 
		/* West */
		if (direction == NORTH_WEST || direction == WEST || direction == SOUTH_WEST){
			resize( WEST, amount.x );
		}	
	}
	
	/* 
	 * 
	 * 
	 * 
	 * EVENT HANDLING 
	 * 
	 * 
	 * 
	 * 
	 * */
	
	public void addMouseFocusListener( MouseFocusListener listener ){
		mouseFocusListeners.add( listener );
	}
	
	public void removeMouseFocusListener( MouseFocusListener listener ){
		mouseFocusListeners.remove( listener );
	}
	
	public void fireMouseOver(){
		for (MouseFocusListener l : mouseFocusListeners){
			l.mouseOver( this );
		}
	}
	
	public void fireMouseOut(){
		for (MouseFocusListener l : mouseFocusListeners){
			l.mouseOut( this );
		}
	}
	
	public java.awt.Shape getShape(){
		return shape;
	}
	
	public void setShape(java.awt.Shape shape) {
		this.shape = shape;
	}

	/*  GEOMETRY STUFF  */
	
	public void move( Number x, Number y ){
		this.x = new Unit( x.floatValue() );
		this.y = new Unit( y.floatValue() );
	}
	
	public void flipHoristonal(){
	}
	
	public void flipVertical(){
	}
	
	public void rotate( int degrees ){
	}
	
}