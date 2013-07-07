package org.laukvik.svg.swing.editors.stroke;

import java.awt.Color;
import org.laukvik.svg.SVGColor;
import org.laukvik.svg.shape.Line;
import org.laukvik.svg.shape.Rectangle;
import org.laukvik.svg.shape.SVG;
import org.laukvik.svg.stroke.Stroke;
import org.laukvik.svg.swing.SVGPanel;
import org.laukvik.svg.unit.Pixel;

public class StrokePanel extends SVGPanel {

	private static final long serialVersionUID = 1L;
	SVG svg;
	Line line;
	Rectangle r;

	public StrokePanel(){
		super();
		svg = new SVG();
		svg.width = new Pixel( 75 );
		svg.height = new Pixel( 24 );
		r = new Rectangle( new Pixel(0), new Pixel(0), new Pixel(75), new Pixel(24) );

		r.stroke.width = new Pixel( 0 );
		
		svg.add( r );
		line = new Line( new Pixel(0),new Pixel(12), new Pixel(75),new Pixel(12) );
		
		svg.add( line );
		setSVG( svg );
	}
	
	public void setStroke( Stroke stroke ){
		line.y = new Pixel( getHeight()/2 );
		line.y2 = new Pixel( getHeight()/2 );
		line.stroke = stroke;
		repaint();
	}

	public void setBackground(Color bg) {
		r.fill.color = new SVGColor( bg );
		super.setBackground(bg);
	}

	public void setForeground(Color fg) {
		super.setForeground(fg);
	}
	
}