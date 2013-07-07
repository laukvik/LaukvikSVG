package org.laukvik.svg.shape;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import org.laukvik.svg.SVGColor;
import org.laukvik.svg.fill.Fill;
import org.laukvik.svg.stroke.Stroke;
import org.laukvik.svg.unit.Unit;

public class Circle extends BasicShape implements HasFill, HasStroke{
	
	public static final String CIRCLE = "circle";
	public Unit radius = new Unit(0);
	
	public Circle() {
		super();
	}

	public Circle( Unit x, Unit y, Unit radius ) {
		super(x, y);
		this.radius = radius;
	}
	
	public Circle( Unit x, Unit y, Unit radius, SVGColor color ) {
		super(x, y);
		this.radius = radius;
		this.fill.color = color;
	}
	
	public Circle( Unit x, Unit y, Unit radius, Fill fill, Stroke stroke ) {
		super(x, y);
		this.radius = radius;
		this.fill = fill;
		this.stroke = stroke;
	}

	public void paint( Graphics g, Dimension size ){
		int rpx = toPixels(radius);
		
		int x1 = toHorisontalPixels(x);
		int y1 = toVerticalPixels(y);
		
		setHandles( x1 - rpx, y1 - rpx, rpx*2, rpx*2 );
		
		Graphics2D g2 = (Graphics2D) g;
		
		/* Java paints circle from upper left corner of circle 
		 * while SVG paints circle from the center of circle */
		setShape( new Ellipse2D.Float( x1 - rpx , y1 - rpx, rpx*2, rpx*2 ) );
		
		if (fill.isVisible()){
			g2.setPaint( fill );
			g2.setComposite( createAlpha( fill.opacity ) );
			g2.fill( getShape() );
		}
		if (stroke.isVisible()){
			g2.setColor( stroke.color.getValue() );
			g2.setStroke( createStroke(stroke) );
			g2.setComposite( createAlpha( stroke.opacity ) );
			g2.draw( getShape() );
		}
		
		if (isHandlesVisible()){
			paintHandles(g);
		}
	}
	
	public void resize( int direction, int amount ){
		this.radius = new Unit( radius.getValue() + amount  );
	}

}