package org.laukvik.svg.parser;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.laukvik.svg.Opacity;
import org.laukvik.svg.SVGColor;
import org.laukvik.svg.SVGSource;
import org.laukvik.svg.shape.Image;
import org.laukvik.svg.shape.Polygon;
import org.laukvik.svg.shape.SVG;
import org.laukvik.svg.shape.SVGID;
import org.laukvik.svg.unit.Percent;
import org.laukvik.svg.unit.Pixel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Parser{

	private SVG svg;
	private Vector <ParseListener> listeners;

	public Parser(){
		listeners = new Vector<ParseListener>();
	}
	
	public void addParseListener( ParseListener listener ){
		this.listeners.add( listener );
	}
	
	public void removeParseListener( ParseListener listener ){
		this.listeners.remove( listener );
	}
	
	public void fireParseFailed( Exception e, SVGSource source ){
		for (ParseListener l : listeners){
			l.parseFailed(e,source);
		}
	}
	
	public void fireParseStarted(){
		for (ParseListener l : listeners){
			l.parseStarting();
		}
	}
	
	public void fireParseCompleted(){
		for (ParseListener l : listeners){
			l.parseComplete();
		}
	}
	
	public void fireValueChanged( int value ){
		for (ParseListener l : listeners){
			l.valueChanged( value );
		}
	}
	
	public SVG getSVG(){
		return svg;
	}
	
	public static void log( Object msg ){
//		System.out.println( Parser.class.getName() +  ": " + msg );
	}
	
	public static String getFilename( URL url ){
		String path = url.getPath();
		String filename = path.substring( path.lastIndexOf("/") );
		return filename;
	}
	

	
	public void parse( SVGSource source ) throws Exception{
		fireParseStarted();
		svg = null;
		log( "Reading java url: " + source );
		try{
			if (source.getFilename().endsWith(".gz") || source.getFilename().endsWith(".svgz")){
				parse( new GZIPInputStream( source.getInputStream() ), source );
			} else {
				parse( source.getInputStream(), source );
			}
			fireParseCompleted();
		} catch(Exception e){
			fireParseFailed(e, source);
			throw e;
		}
	}
	
	public void parse( String svg, SVGSource source ) throws Exception{
		parse( new ByteArrayInputStream( svg.getBytes() ), source );
	}

	
	public void parse( InputStream is, SVGSource source ) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        docBuilder.setEntityResolver( new EmptyDTDResolver() );
        
        Document doc = docBuilder.parse( is );
        doc.getDocumentElement().normalize();

        /* Get journal */
        Element root = doc.getDocumentElement();
        
        /* Get size */
        svg = new SVG();
        svg.source = source;

        GeometryParser.parse( svg, root );
	}

	public static BufferedImage getBufferedImage( SVGSource source, String href ) throws Exception {
		SVGSource imageHref = new SVGSource( href );
		log( "Reading buffered image: " + imageHref.toExternalForm() + " " + source );
		if (source  == null || source.isFile() ){
			return ImageIO.read( new File(href) );
		} else if (imageHref.isNone()){
			/* Relative references */
			SVGSource relative = new SVGSource( source.getAddress(), source.getType() );
			log( "Reading relative image1: " + relative.toExternalForm() );
			relative.setFilename( href );
			log( "Reading relative image2: " + relative.toExternalForm() );
			return ImageIO.read( relative.getInputStream() );
		} else {
			/* Absolute references */
			return ImageIO.read( imageHref.getInputStream() );
		}
	}

	public BufferedImage getBufferedImage( String href ) throws Exception {
		return getBufferedImage( svg.source, href );
	}
	
	public void write( SVG svg, File file ) throws Exception{
        GeometryWriter writer = new GeometryWriter( svg );
        writer.write();
        write( new DOMSource( writer.getDocument() ), new FileOutputStream( file ) );
	}
	
	public String toPlainText( SVG svg ) throws Exception{
        GeometryWriter writer = new GeometryWriter( svg );
        writer.write();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        write( new DOMSource( writer.getDocument() ), out );
        return out.toString();
	}
	
	private void write( DOMSource source, OutputStream out ) throws Exception{
		StreamResult streamResult = new StreamResult(out);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer;
		serializer = tf.newTransformer();
		serializer.setOutputProperty( OutputKeys.ENCODING, "ISO-8859-1" );
		serializer.setOutputProperty( OutputKeys.INDENT, "yes" );
		serializer.transform( source, streamResult );
	}

	public static void main( String [] args ) throws Exception{
		SVG svg = new SVG();
		svg.width = Percent.PERCENT100;
		svg.height = Percent.PERCENT100;
		svg.setTitle( "Biceps Femoris" );
		
		Image muscle = new Image( new Pixel(-300), new Pixel(-430), "femalemuscle180.jpg" );
		muscle.setID( new SVGID("muscle") );
		muscle.width = new Pixel(1063);
		muscle.height = new Pixel(1012);
		svg.add( muscle );
		
		Image skin = new Image( new Pixel(-300), new Pixel(-430), "femaleskin180.jpg" );
		skin.setID( new SVGID("skin") );
		skin.width = new Pixel(1063);
		skin.height = new Pixel(1016);
		svg.add( skin );
		
		Polygon polygon = new Polygon("546,339 556,337 562,337 567,339 575,342 584,343 592,343 593,349 590,355 588,362 587,371 588,380 590,386 591,396 590,407 587,413 579,416 562,411 545,402 534,391 532,381 533,368 534,361 539,354 540,343");
		polygon.stroke.color = SVGColor.black;
		polygon.stroke.width = new Pixel(1);
		polygon.opacity = new Opacity( 0.5f );
		polygon.fill.color = SVGColor.green;
		polygon.fill.opacity = new Opacity( 0.5f );
		svg.add( polygon );
		
		Parser parser = new Parser();
		parser.write( svg, new File("/Users/morten/Desktop/test.svg") );
	}

}