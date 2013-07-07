package org.laukvik.svg.shape;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import org.laukvik.svg.Opacity;
import org.laukvik.svg.SVGColor;
import org.laukvik.svg.unit.Unit;

public class Line extends BasicShape implements HasStroke {

	public static final String LINE = "line";
	public Unit x2 = new Unit(0);
	public Unit y2 = new Unit(0);

	public Line() {
		super();
	}
	
	public Line( Unit x1, Unit y1, Unit x2, Unit y2, SVGColor color, Unit width, Opacity opacity ) {
		this.x = x1;
		this.y = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.stroke.color = color;
		this.stroke.width = width;
		this.stroke.opacity = opacity;
	}
	
	public Line( Unit x1, Unit y1, Unit x2, Unit y2, SVGColor color  ) {
		this.x = x1;
		this.y = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.stroke.color = color;
	}
	
	public Line( Unit x1, Unit y1, Unit x2, Unit y2 ) {
		this.x = x1;
		this.y = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public Line( int x1, int y1, int x2, int y2, SVGColor color, int width ) {
		this.x = new Unit( x1 );
		this.y = new Unit( y1 );
		this.x2 = new Unit( x2 );
		this.y2 = new Unit( y2 );
		this.stroke.color = color;
		this.stroke.width = new Unit( width );
		this.stroke.opacity = Opacity.OPAQUE;
	}

	public void move( Number x, Number y ){
		float deltaX = this.x.getValue() - x.floatValue();
		float deltaY = this.y.getValue() - y.floatValue();
		super.move( x, y );
		this.x2 = new Unit( x2.getValue() - deltaX );
		this.y2 = new Unit( y2.getValue() - deltaY );
	}

	public void paint( Graphics g, Dimension size ){
		
		int xpx = toHorisontalPixels(x);
		int ypx = toVerticalPixels(y);
		
		int x2px = toHorisontalPixels(x2);
		int y2px = toVerticalPixels(y2);
		
		Line2D.Float line = new Line2D.Float( xpx,  ypx, x2px, y2px );
		setHandles( line.getBounds() );
		setShape( line );
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint( stroke.color );
		g2.setStroke( createStroke(stroke) );
		g2.setComposite( createAlpha( stroke.opacity ) );
		g2.draw( line );
		
		if (isHandlesVisible()){
			paintHandles(g);
		}
	}
	
	public void resize( int direction, int amount ){
		switch(direction){
		
			case NORTH : 
				this.y = new Unit( y.getValue() - amount );
				this.y2 = new Unit( y2.getValue() + amount );
				break;
				
			case SOUTH : 
				this.y2 = new Unit( y2.getValue() +  amount );
				break;
				
			case EAST : 
				this.x2 = new Unit( x2.getValue() + amount );
				break;
				
			case WEST : 
				this.x = new Unit( x.getValue() - amount );
				this.x2 = new Unit( x2.getValue() + amount );
				break;
		}
	}
	
}