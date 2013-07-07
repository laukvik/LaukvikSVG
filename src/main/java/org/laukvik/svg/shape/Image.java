package org.laukvik.svg.shape;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.laukvik.svg.SVGSource;
import org.laukvik.svg.parser.Parser;
import org.laukvik.svg.unit.Pixel;
import org.laukvik.svg.unit.Unit;

public class Image extends BasicShape {
	
	public static final String IMAGE = "image";
	public static final String XLINK_HREF = "xlink:href";
	public String href;
	public BufferedImage buffer = null;
	public Image render = null;
	public Unit width = new Unit(0);
	public Unit height  = new Unit(0);
	public Unit physicalWidth = new Unit(0);
	public Unit physicalHeight = new Unit(0);

	public Image( Unit x, Unit y, String href ) {
		super(x, y);
		setHref( href );
	}

	public Image(){
		super();
	}	

	public void loadImage() {
		if (href == null || href.length() == 0){
			
		} else {
			load( href );	
		}
	}

	public void setHref( String href ) {
		this.href = href;
		/* In an SVG is present, load the image */
		if (getSVG() != null){
			load( href );
		}
	}

	private void load( String href ){
		log( "Loading image: " + href   );
		
		SVG svg = getSVG();
		
		if (svg.source == null){ 
			load( new File( href ) );
			
		} else if (svg.source.isFile()){
			File file = new File( svg.source.getFile().getParentFile(), href );
			load( file );
			
		} else if (svg.source.isURL()){
			try {
				load( new URL( href ) );
			} catch (MalformedURLException e) {
				log( e );
			}
		} else if (svg.source.isJava()){
			String javaPath = svg.source.getFolder() + "/" + href;
//			javaPath = javaPath.replaceAll("/", ".");
//			javaPath = "org.laukvik.trainer.svg.femalemuscle180.jpg";
			
//			System.out.println( "Image: folder=" + svg.source.getFolder() );
//			System.out.println( "Image: href=" + href );
//			System.out.println( "Image: javaPath=" + javaPath );
//			
			SVGSource javaSource = new SVGSource( javaPath, SVGSource.JAVA );
			try {
				setBuffer( ImageIO.read( javaSource.getInputStream() ) );
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			log( "Could not lod image!" );
		}
	}
	
	private void load( File file ){
		log( "Load file into image: " + file.getAbsolutePath() );
//		this.href = file.getAbsolutePath();	
		try {
			setBuffer( Parser.getBufferedImage( null, file.getAbsolutePath() ) );
		} catch (Exception e) {
			log( e );
		}
	}
	
	private void load( URL url ){
		log( "Load url into image: " + url.toExternalForm() );
//		this.href = url.toExternalForm();
		try {
			setBuffer( Parser.getBufferedImage( null, url.toExternalForm() ) );
		} catch (Exception e) {
			log( e );
		}
	}
	
	public void log( Object o ){
//		System.out.println( Image.class.getName() + ": " + o );
	}
	
	private void setBuffer( BufferedImage buffer ){
		this.buffer = buffer;
		this.width = new Pixel( buffer.getWidth() );
		this.height = new Pixel( buffer.getHeight() );
		this.physicalWidth = new Pixel( buffer.getWidth() );
		this.physicalHeight = new Pixel( buffer.getHeight() );
	}

	public void paint( Graphics g, Dimension size ){
		
		int x1 = toHorisontalPixels(x);
		int y1 = toVerticalPixels(y);
		
		int w1 = toHorisontalPixels(width);
		int h1 = toVerticalPixels(height);
		
		setHandles( x1, y1, w1, h1 );
		setShape( new java.awt.Rectangle( x1, y1, w1, h1 ) );
		
		if (buffer == null ||  w1 == 0 || h1 == 0){
			
		} else {
			Graphics2D g2 = (Graphics2D)g;
			g2.setComposite( createAlpha( opacity ) );
			
			
			if (width.isSame( physicalWidth ) && height.isSame(physicalHeight)){
				g.drawImage( buffer, x1, y1, getFakeImageObserver() );
			} else {
				System.out.println( "Scaling image: " + w1 + "x" + h1 + " ( " + width + " " + height + " )" );
				g.drawImage( buffer.getScaledInstance( w1, h1, java.awt.Image.SCALE_SMOOTH ), x1, y1, getFakeImageObserver() );

			}	
		}
		if (isHandlesVisible()){
			paintHandles(g);
		}
	}
	
	public final static Component getFakeImageObserver(){
		return new Component(){
			private static final long serialVersionUID = 1L;
		};
	}

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
	
}