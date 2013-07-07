package org.laukvik.svg.shape;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import org.laukvik.svg.unit.Unit;

public class Ellipse extends BasicShape implements HasFill, HasStroke{
	
	public final static String ELLIPSE = "ellipse";

	public Unit radiusx = new Unit(0);
	public Unit radiusy = new Unit(0);
	
	public Ellipse( Unit x, Unit y, Unit radiusx, Unit radiusy ) {
		super(x, y);
		this.radiusx = radiusx;
		this.radiusy = radiusy;
	}
	
	public Ellipse() {
		super();
	}

	public void paint( Graphics g, Dimension size ){
		int xpx = toPixels(x);
		int ypx = toPixels(y);
		int rx = toPixels(radiusx);
		int ry = toPixels(radiusy);
		

		
		setHandles( xpx-rx, ypx-ry, rx*2, ry*2 );
		
		/* Java paints circle from upper left corner of circle 
		 * while SVG paints circle from the center of circle */
		int rpx = toPixels(radiusx);
		int rpy = toPixels(radiusy);
		
		setShape( new Ellipse2D.Float( xpx - rpx , ypx - rpy, rx*2, ry*2 ) );
		
		Graphics2D g2 = (Graphics2D) g;
		if (fill.isVisible()){
			g2.setPaint( fill );
			g2.setComposite( createAlpha( fill.opacity ) );
			g2.fill( getShape() );
		}
		
		if (stroke.width.getValue() > 0){
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
		this.radiusx = new Unit( radiusx.getValue() + amount  );
		this.radiusy = new Unit( radiusy.getValue() + amount  );
	}

}