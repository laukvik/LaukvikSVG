package org.laukvik.svg.parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.laukvik.svg.Cursor;
import org.laukvik.svg.Opacity;
import org.laukvik.svg.shape.BasicShape;
import org.laukvik.svg.shape.Circle;
import org.laukvik.svg.shape.Ellipse;
import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Group;
import org.laukvik.svg.shape.Image;
import org.laukvik.svg.shape.Line;
import org.laukvik.svg.shape.Path;
import org.laukvik.svg.shape.Polygon;
import org.laukvik.svg.shape.Polyline;
import org.laukvik.svg.shape.Rectangle;
import org.laukvik.svg.shape.SVG;
import org.laukvik.svg.shape.Text;
import org.laukvik.svg.stroke.LineJoin;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

public class GeometryWriter {
	
	private Document document;
	private SVG svg;
	private Element svgElement;
	
	public GeometryWriter( SVG svg ) throws ParserConfigurationException, TransformerException{
		this.svg = svg;
		// Find the implementation
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		DOMImplementation impl = builder.getDOMImplementation();

		// Create the document
		DocumentType svgDOCTYPE = impl.createDocumentType( "svg", "-//W3C//DTD SVG 1.0//EN", "http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd");
		document = impl.createDocument( "http://www.w3.org/2000/svg", "svg", svgDOCTYPE );

		
		// Fill the document
		svgElement = document.getDocumentElement();

	}
	
	public void write(){
		write( svg, svgElement );
	}

	public Document getDocument() {
		return document;
	}
	
	private void write( Group group, Element element ) {
		if (group == null){
			System.err.println( "GeometryWriter: group can't be null!" );
		}
		if (element == null){
			System.err.println( "GeometryWriter: element can't be null!" );
		}
		if (group instanceof SVG){
			Element title = document.createElement( "title" );
			title.setTextContent( ((SVG)group).getTitle() );
			svgElement.appendChild( title );
			
			setAttribute( "width", svg.width, element );
			setAttribute( "height", svg.height, element );
		}

		

		for (Geometry g : group.getItems()){
			log( "Found item: " + g );
			/* Create new element for Geometry */
			Element childElement = null;
			if (g instanceof Image){
				childElement = document.createElementNS("http://www.w3.org/2000/svg","image");
			} else {
				childElement = document.createElement( g.getTagName() );
			}
			
			element.appendChild( childElement );
			
			/* */
			if (g instanceof Geometry){
				Geometry geo = (Geometry) g;
				
				if (g instanceof Ellipse || g instanceof Circle|| g instanceof Line){

				} else {
					setAttribute( "x", geo.x, childElement );
					setAttribute( "y", geo.y, childElement );
				}

				
				setAttribute( "id", geo.getID(), childElement );
				
				if (geo.getClassCSS().isEmpty()){
					setAttribute( "class", geo.getClassCSS(), childElement );
				}
				
				
				setAttribute( "opacity", geo.opacity, childElement );
				if (!geo.visibility.isVisible()){
					setAttribute( "visibility", geo.visibility, childElement );
				}
				setAttribute( "cursor", geo.cursor, childElement, Cursor.DEFAULT );
//				setAttribute( "transform", geo.transform, childElement );
				
			}
			
			/* */
			if (g instanceof BasicShape){
				BasicShape bs = (BasicShape) g;
				setAttribute( "stroke", bs.stroke.color, childElement );
				setAttribute( "stroke-width", bs.stroke.width, childElement );
				setAttribute( "stroke-opacity", bs.stroke.opacity, childElement );
				setAttribute( "stroke-dasharray", bs.stroke.dashArray, childElement );
				setAttribute( "stroke-dashoffset", bs.stroke.dashOffset, childElement );
				setAttribute( "stroke-linecap", bs.stroke.lineCap, childElement );
				setAttribute( "stroke-linejoin", bs.stroke.lineJoin, childElement, LineJoin.MITER );
				if (g instanceof Polyline && (g instanceof Polygon == false)){
					setAttribute( "fill", "none", childElement );
				} else {
					setAttribute( "fill", bs.fill.color, childElement );
					setAttribute( "fill-opacity", bs.fill.opacity, childElement, Opacity.OPAQUE );	
				}
				
			}
			
			/* */
			if (g instanceof Rectangle){
				Rectangle r = (Rectangle) g;
				setAttribute( "width", r.width, childElement );
				setAttribute( "height", r.height, childElement );
				setAttribute( "rx", r.rx, childElement );
				setAttribute( "ry", r.ry, childElement );
				
			}
			
			/* */
			if (g instanceof Image){
				Image img = (Image) g;
				setAttribute( "href", img.href, childElement );
				setAttribute( "width", img.width, childElement );
				setAttribute( "height", img.height, childElement );
				Attr attr = document.createAttributeNS( "http://www.w3.org/1999/xlink", "href" );
				attr.setPrefix("xlink");
				attr.setNodeValue( img.href );
				childElement.setAttributeNodeNS( attr );
			}
			
			if (g instanceof Polyline){
				Polyline poly = (Polyline) g;
				setAttribute( "points", poly.listPointsData(), childElement );
			}
			
			/**
			 * <ellipse ry="100" rx="100" cx="300" cy="500"></ellipse>
			 */
			if (g instanceof Ellipse){
				Ellipse ellipse = (Ellipse) g;
				setAttribute( "cx", ellipse.x, childElement );
				setAttribute( "cy", ellipse.y, childElement );
				setAttribute( "rx", ellipse.radiusx, childElement );
				setAttribute( "ry", ellipse.radiusy, childElement );
			}
			
			if (g instanceof Line){
				Line line = (Line) g;
				setAttribute( "x1", line.x, childElement );
				setAttribute( "y1", line.y, childElement );
				setAttribute( "x2", line.x2, childElement );
				setAttribute( "y2", line.y2, childElement );
			}
			
			/**
			 * <circle r="50" cx="200" cy="200"></circle>
			 */
			if (g instanceof Circle){
				Circle circle = (Circle) g;
				setAttribute( "r", circle.radius, childElement );
				setAttribute( "cx", circle.x, childElement );
				setAttribute( "cy", circle.y, childElement );
			}
			
			if (g instanceof Path){
				Path path = (Path) g;
				setAttribute( "points", path.listData(), childElement );
			}
			
			/* 
			 * font-size="12px" font-family="Arial" font-style="italic"
			 *  
			 **/
			if (g instanceof Text){
				Text text = (Text) g;
				
				setAttribute( "font-size", text.font.size, childElement );
				setAttribute( "font-family", text.font.name, childElement );
				setAttribute( "font-style", text.font.style, childElement );
				
				
				
				setAttribute( "text-anchor", text.anchor, childElement );
				setAttribute( "alignment-baseline", text.baselineAlignment, childElement );
				
				childElement.setTextContent(  text.text );
				
				/* NON-STANDARD SUPPORT FOR WRAPPING */
				if (text.width != null && text.width.getValue() > 0){
					setAttribute( "width", text.width, childElement );
				}
				if (text.height != null && text.height.getValue() > 0){
					setAttribute( "height", text.height, childElement );
				}
				
				

			}
			
			/* Iterate inwards if item is a group */
			if (g instanceof Group){
				Group childGroup = (Group) g;
				write( childGroup, childElement );
			}
			
			
		}
	}
	
	private void setAttribute( String name, Object value, Element element ){
		setAttribute( name, value, element, null );
	}
	
	/**
	 * Writes XML attributes when value is not null or the default value
	 */
	private void setAttribute( String name, Object value, Element element, Object defaultValue ){
		if (value == null || value == defaultValue){
			
		} else {
			element.setAttribute( name, value.toString() );
		}
	}

	public static void log( Object message ){
//		System.out.println( GeometryWriter.class.getName() + ": " + message );
	}
	
}