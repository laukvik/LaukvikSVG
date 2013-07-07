package org.laukvik.svg.shape;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.Vector;

import org.laukvik.svg.shape.path.Close;
import org.laukvik.svg.shape.path.PathElement;
import org.laukvik.svg.shape.path.PathParser;
import org.laukvik.svg.unit.Unit;

public class Path extends BasicShape implements HasFill, HasStroke {
	
	public static final String PATH = "path";
	public static final String D = "d";
	
	Vector <PathElement> pathElements;
	private GeneralPath p;
	
	public Path(){
		super();
		this.pathElements = new Vector<PathElement>();
	}

	public void move( Number x, Number y ){
		float deltaX = this.x.getValue() - x.floatValue();
		float deltaY = this.y.getValue() - y.floatValue();
		
		for (PathElement el : pathElements){
			el.roll( -deltaX, -deltaY );
		}
		this.x = new Unit( x );
		this.y = new Unit( y );
		emptyCache();
	}
	
	public boolean isClosed(){
		if (pathElements == null || pathElements.size() == 0){
			return false;
		}
		return pathElements.lastElement() instanceof Close;
	}
	
	public GeneralPath createCache(){
		/* Create the AWT polygon */
		GeneralPath p = new GeneralPath();
		for (PathElement el : pathElements){
			el.apply( p );
		}
		return p;
	}
	
	public void emptyCache(){
		p = null;
	}

	public void paint( Graphics g, Dimension size ){
//		if (p == null){
			p = createCache();
//		}
		/**/
		Graphics2D g2 = (Graphics2D) g;
		
		setShape( p );
		
		/* Paint the fill */
		if (!fill.color.isNone() || isClosed()){
			g2.setComposite( createAlpha( fill.opacity ) );
//			g2.setColor( fill.color.getValue() );
			g2.setPaint( fill );
			g2.fill( getShape() );
		}
		
		/* Paint the stroke */
		if (stroke.width.getValue() > 0){
			g2.setColor( stroke.color.getValue() );
			g2.setComposite( createAlpha( stroke.opacity ) );
			g2.setStroke( createStroke(stroke) );
			g2.draw( getShape() );
		}	
		
		setHandles( p.getBounds() );
		if (isHandlesVisible()){
			paintHandles(g);
		}
	}
	
	public void add( PathElement p ){
		this.pathElements.add( p );
	};
	
	public void remove( PathElement p ){
		this.pathElements.remove( p );
	};
	
	public int length(){
		return pathElements.size();
	}

	public void setElements( Vector<PathElement> pathElements ) {
		this.pathElements = pathElements;
	}
	
	public void setElements( String pathData ){
		setElements( PathParser.parsePathElements( pathData ) );
	}
	
	public void resize( int direction, int amount ){
		createCache();
		switch(direction){
		
			case NORTH : 
//				this.y = new Unit( y.getValue() + amount );
				resizeY( amount );
				break;
				
			case SOUTH : 
//				this.y = new Unit( y.getValue() - amount );
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
	
	public void resizeX( float amount ){
		float factor = amount / (p.getBounds().width);
		for (PathElement el : pathElements){
			el.resizeX( factor );
		}
	}
	
	public void resizeY( float amount ){
		float factor = amount / (p.getBounds().height );
		for (PathElement el : pathElements){
			el.resizeY( factor );
		}
	}
	
	public String listData(){
		StringBuffer buffer = new StringBuffer();
		for (PathElement el : pathElements){
			buffer.append( el.toString() + " "  );
		}
		return buffer.toString();
	}
	
}