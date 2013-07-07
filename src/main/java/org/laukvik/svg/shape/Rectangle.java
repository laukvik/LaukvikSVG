package org.laukvik.svg.shape;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.laukvik.svg.unit.Unit;

/**
 * 
 * 
 * Example:
 * 
 * <rect fill="red" stroke="black" x="15" y="15" width="100" height="50"/> 
 * 
 * 
 * @author morten
 *
 */
public class Rectangle extends BasicShape implements HasFill, HasStroke {

	public static final String RECTANGLE = "rect";
	/* Width and height */
	public Unit width = new Unit(0);
	public Unit height = new Unit(0);
	/* Size of rounded corners */
	public Unit rx = new Unit(0);
	public Unit ry = new Unit(0);
	
	public Rectangle() {
		super();
	}
	
	public Rectangle( Unit x, Unit y, Unit width, Unit height ) {
		super(x, y);
		this.width = width;
		this.height = height;
	}
	
	public Rectangle( Unit x, Unit y, Unit width, Unit height, Unit rx, Unit ry) {
		super(x, y);
		this.width = width;
		this.height = height;
		this.rx = rx;
		this.ry = ry;
	}

	public void paint( Graphics g, Dimension size ){
		Graphics2D g2 = (Graphics2D) g;
		
		int x1 = toHorisontalPixels(x);
		int y1 = toVerticalPixels(y);
		
		int w1 = toHorisontalPixels(width);
		int h1 = toVerticalPixels(height);
		
		if (rx.getValue() == 0 && ry.getValue() == 0){
			setShape( new java.awt.Rectangle( x1, y1, w1, h1 ) );
		} else {
			setShape( new java.awt.geom.RoundRectangle2D.Double( x1, y1, w1, h1, toHorisontalPixels(rx)*2, toVerticalPixels(ry)*2 ) );
		}
		
		if (fill.isVisible()){
			g2.setPaint( fill );
			g2.setComposite( createAlpha( fill.opacity ) );
			g2.fill( getShape() );
		}
		if (stroke.isVisible()){
			g2.setColor( stroke.color.getValue() );
			g2.setStroke( createStroke(stroke) );
			g2.setComposite( createAlpha( stroke.opacity ) );
			g2.draw( getShape()  );
		}
		
		setHandles( x1, y1, w1, h1 );

		if (isHandlesVisible()){
			paintHandles(g);
		}
	}
	
	/**
	 * Resizes the rectangle 
	 * 
	 */
	public void resize( int direction, int amount ){
		switch(direction){
		
			case NORTH : 
				this.y = new Unit( y.getValue() - amount );
				this.height = new Unit( height.getValue() + amount );
				break;
				
			case SOUTH : 
				this.height = new Unit( height.getValue() +  amount );
				break;
				
			case EAST : 
				this.width = new Unit( width.getValue() + amount );
				break;
				
			case WEST : 
				this.x = new Unit( x.getValue() - amount );
				this.width = new Unit( width.getValue() + amount );
				break;
		}
	}

	public String getTagName() {
		return "rect";
	}

}