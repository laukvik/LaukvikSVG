package org.laukvik.svg.parser;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.laukvik.svg.Cursor;
import org.laukvik.svg.Opacity;
import org.laukvik.svg.SVGColor;
import org.laukvik.svg.SVGSource;
import org.laukvik.svg.ViewBox;
import org.laukvik.svg.defs.Definition;
import org.laukvik.svg.fill.Fill;
import org.laukvik.svg.fill.FillRule;
import org.laukvik.svg.font.FontStyle;
import org.laukvik.svg.font.FontVariant;
import org.laukvik.svg.font.FontWeight;
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
import org.laukvik.svg.shape.SVGClass;
import org.laukvik.svg.shape.SVGID;
import org.laukvik.svg.shape.SVGStyle;
import org.laukvik.svg.shape.Text;
import org.laukvik.svg.shape.Title;
import org.laukvik.svg.shape.text.BaselineAlignment;
import org.laukvik.svg.shape.text.TextAnchor;
import org.laukvik.svg.stroke.DashArray;
import org.laukvik.svg.stroke.LineCap;
import org.laukvik.svg.stroke.LineJoin;
import org.laukvik.svg.transform.Transform;
import org.laukvik.svg.unit.Unit;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GeometryParser {

	public static Geometry parse( Group group, Node node ){
		/* Create object */
		Geometry geometry = null;
		
		/* Get tag name */
		String nodeName = node.getNodeName();
		
		/* Create new object based on node name */
		if (nodeName.equalsIgnoreCase( SVG.SVG )){
			geometry = group;
			((SVG)group).setGroup(group);
			
		} else if (nodeName.equalsIgnoreCase( Group.GROUP ) || nodeName.equalsIgnoreCase( Group.A )){
			geometry = new Group(  );
			geometry.setGroup(group);
				
		} else if (nodeName.equalsIgnoreCase( Rectangle.RECTANGLE )){
			geometry = new Rectangle();
			
		} else if (nodeName.equalsIgnoreCase( Polygon.POLYGON )){
			geometry = new Polygon();
			
		} else if (nodeName.equalsIgnoreCase( Polyline.POLYLINE )){
			geometry = new Polyline();
			
		} else if (nodeName.equalsIgnoreCase( Circle.CIRCLE )){
			geometry = new Circle();
			
		} else if (nodeName.equalsIgnoreCase( Ellipse.ELLIPSE )){
			geometry = new Ellipse();
			
		} else if (nodeName.equalsIgnoreCase( Path.PATH )){
			geometry = new Path();
			
		} else if (nodeName.equalsIgnoreCase( Image.IMAGE )){
			geometry = new Image();
			geometry.setGroup(group); // Required during loading of image
			
		} else if (nodeName.equalsIgnoreCase( Line.LINE )){
			geometry = new Line();
			
		} else if (nodeName.equalsIgnoreCase( Text.TEXT )){
			geometry = new Text( node.getTextContent() );
			
		} else if (nodeName.equalsIgnoreCase( Title.TITLE )){
			
			if (group instanceof SVG){
				((SVG)group).setTitle( node.getTextContent() );
			}
			
		} else if (nodeName.equalsIgnoreCase( "defs" )){
			
			Definition [] defs = DefinitionParser.parse( node );
			if (group instanceof SVG){
				((SVG)group).addDefinition( defs );
			} else if (group.getSVG() != null){
				group.getSVG().addDefinition( defs );  
			}
			
			
		} else {
			
		}

		if (geometry == null){
			/* Do nothing */
			return null;
		} else {
			geometry.setGroup(group);
			
			/* Add inherited values */
			if (geometry instanceof BasicShape){
				BasicShape bs = ((BasicShape) geometry);
				bs.fill.color     = group.fill.color; 
				bs.fill.opacity   = group.fill.opacity; 
				bs.fill.rule      = group.fill.rule; 
				
				bs.stroke.color   = group.stroke.color;
				bs.stroke.width   = group.stroke.width;
				bs.stroke.opacity = group.stroke.opacity;
				
				bs.transform      = group.transform.duplicate();
				
			}
			
			/* Get its attributes */
			int count = node.getAttributes().getLength();
			for (int index=0; index<count; index++){
				Node attribute = node.getAttributes().item( index );
				/* Get attribute name and value*/
				String attr = attribute.getNodeName();
				String value = attribute.getNodeValue();
				
				/* Add attributes from the style attribute */
				if (attr.equalsIgnoreCase( SVGStyle.STYLE )){
					SVGStyle style = new SVGStyle( value );
					String [] styles = style.listStyles();
					for (String s : styles ){
						String [] keyValue = s.split(":");
						if (keyValue.length == 2){
							/* Add attributes */
							applyAttribute( geometry, keyValue[0].trim(), keyValue[1] );
						}
					}
				} else {
					/* Add attributes from XML attributes */
					applyAttribute( geometry, attr, value );
				}
			}
			
			/* Iterate through all child nodes */
			NodeList svgNodeList = node.getChildNodes();
	        for (int index=0; index<svgNodeList.getLength(); index++){
	        	Node n = svgNodeList.item( index );
				if (geometry instanceof Group){
					Geometry child = GeometryParser.parse( (Group) geometry, n );
					if (child != null){
						((Group)geometry).add( child );
					}
				} else {
					
				}
	        }
			return geometry;
		}		
	}
	
	public static BufferedImage getBufferedImage( SVGSource source, String href ) throws Exception {
		SVGSource imageHref = new SVGSource( href );
//		log( "Reading buffered image: " + imageHref.toExternalForm() + " " + source );
		if (imageHref.isNone()){
			/* Relative references */
			SVGSource relative = new SVGSource( source.getAddress(), source.getType() );
//			log( "Reading relative image1: " + relative.toExternalForm() );
			relative.setFilename( href );
//			log( "Reading relative image2: " + relative.toExternalForm() );
			return ImageIO.read( relative.getInputStream() );
		} else {
			/* Absolute references */
			return ImageIO.read( imageHref.getInputStream() );
		}
	}
	
	public static void applyAttribute( Geometry geometry, String attr, String value ){
		/**/
//		log( attr + "=" + value );

		if (attr.equalsIgnoreCase("x")){
			geometry.x = new Unit( value );
			log( "x: " + geometry.x );
			
		} else if (attr.equalsIgnoreCase("y")){
			geometry.y = new Unit( value );
			log( "y: " + geometry.y );
			
		} else if (attr.equalsIgnoreCase("x1")){
			
			if (geometry instanceof Line){
				((Line)geometry).x = new Unit( value );
				log( "x1: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("y1")){
			
			if (geometry instanceof Line){
				((Line)geometry).y = new Unit( value );
				log( "y1: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("x2")){
			
			if (geometry instanceof Line){
				((Line)geometry).x2 = new Unit( value );
				log( "x2: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("y2")){
			
			if (geometry instanceof Line){
				((Line)geometry).y2 = new Unit( value );
				log( "y2: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("width")){
			
			if (geometry instanceof SVG){
				((SVG)geometry).width = new Unit( value );
				log( "svgwidth: " + ((SVG)geometry).width );
			} else if (geometry instanceof Rectangle){
				((Rectangle)geometry).width = new Unit( value );
				log( "width: " + value );
			} else if (geometry instanceof Image){
				((Image)geometry).width = new Unit( value );
				log( "width: " + value );
			} else if (geometry instanceof Text){
				((Text)geometry).width = new Unit( value );
				log( "width: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("height")){
			
			if (geometry instanceof SVG){
				((SVG)geometry).height = new Unit( value );
				log( "svgheight: " + ((SVG)geometry).height );
			} else if (geometry instanceof Rectangle){
				((Rectangle)geometry).height = new Unit( value );
				log( "height: " + value );
			} else if (geometry instanceof Image){
				((Image)geometry).height = new Unit( value );
				log( "height: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("opacity")){
			geometry.opacity = new Opacity( value );
			log( "opacity: " + geometry.opacity.toFloat()  + " " + geometry.getClass().getName() );
			
		} else if (attr.equalsIgnoreCase("color")){

			if (geometry instanceof BasicShape){
				((BasicShape)geometry).fill.color = new SVGColor( value );
				log( "stroke-linejoin: " + value );
			}
			
		} else if (attr.equalsIgnoreCase( SVGID.ID )){

			geometry.setID( new SVGID( value ) );
			log( "id: " + value );
			
		} else if (attr.equalsIgnoreCase( SVGClass.SVGCLASS )){

			geometry.setClassCSS( new SVGClass( value ) );
			log( "class: " + value );
			
		} else if (attr.equalsIgnoreCase( Image.XLINK_HREF ) || attr.equalsIgnoreCase( Group.XLINK_HREF )){

			if (geometry instanceof Image){
				Image image = (Image)geometry;
				try {
					image.setHref( value );
//					image.load( value, geometry.getSVG() );
				} catch (Exception e) {
					e.printStackTrace();
				}
				log( "xlink:href: " + value );
			} else if (geometry instanceof Group){
				Group g = (Group)geometry;
				g.setHref( value );
				log( "xlink:href: " + value );
				System.out.println( "Found link in a " + value );
			}
			
		} else if (attr.equalsIgnoreCase("viewBox")){

			if (geometry instanceof SVG){
				((SVG)geometry).viewBox = new ViewBox( value );
				log( "viewBox: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("stroke-width")){

			if (geometry instanceof BasicShape){
				((BasicShape)geometry).stroke.width = new Unit( value );
				log( "stroke-width: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("stroke-opacity")){

			if (geometry instanceof BasicShape){
				((BasicShape)geometry).stroke.opacity = new Opacity( value );
				log( "stroke-opacity: " + value );
			}
			
		} else if (attr.equalsIgnoreCase( Fill.FILL)){
			
			/* fill: color; fill: url(#orange_red2) */
			if (geometry instanceof BasicShape){
				BasicShape bs = ((BasicShape)geometry);
				
				if (value.indexOf("url") > -1){
					log( "fill: geometry=" + geometry.getSVG()  );
					if (geometry instanceof SVG){
						bs.fill.setDefinitionID( new SVGID( value ), (SVG) geometry );
					} else {
//						System.out.println( "Fil: " + value );
						bs.fill.setDefinitionID( new SVGID( value ), geometry.getSVG() );
					}
					log( "fill: " + bs.fill.getDefinitionID()  ); 
					
				} else {
					bs.fill.color = new SVGColor( value );
					log( "fill: " + bs.fill.color );
				}

			}
			
		} else if (attr.equalsIgnoreCase("fill-opacity")){

			if (geometry instanceof BasicShape){
				((BasicShape)geometry).fill.opacity = new Opacity( value ); 
				log( "fill-opacity: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("fill-rule")){

			if (geometry instanceof BasicShape){
				((BasicShape)geometry).fill.rule = new FillRule( value ); 
				log( "fill-rule: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("points")){

			if (geometry instanceof Polyline){
				((Polyline)geometry).setPoints( value );
				log( "points: " + value );
				
			} else if (geometry instanceof Polygon){
				((Polygon)geometry).setPoints( value );
				log( "points: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("preserveAspectRatio")){

		} else if (attr.equalsIgnoreCase("stroke-miterlimit")){

			if (geometry instanceof BasicShape){
				((BasicShape)geometry).stroke.miterLimit = new Unit( value );
				log( "fill-rule: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("stroke")){

			if (geometry instanceof BasicShape){
				((BasicShape)geometry).stroke.color = new SVGColor( value );
				log( "stroke: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("stroke-dasharray")){

			if (geometry instanceof BasicShape){
				((BasicShape)geometry).stroke.dashArray = new DashArray( value );
				log( "stroke-dasharray: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("stroke-dashoffset")){

			if (geometry instanceof BasicShape){
				((BasicShape)geometry).stroke.dashOffset = new Unit( value );
				log( "stroke-dashoffset: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("stroke-linecap")){

			if (geometry instanceof BasicShape){
				((BasicShape)geometry).stroke.lineCap = new LineCap( value );
				log( "stroke-linecap: " + value );
			}
		
		} else if (attr.equalsIgnoreCase("stroke-linejoin")){

			if (geometry instanceof BasicShape){
				((BasicShape)geometry).stroke.lineJoin = new LineJoin( value );
				log( "stroke-linejoin: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("rx")){

			if (geometry instanceof Rectangle){
				((Rectangle)geometry).rx = new Unit( value );
				log( "rx: " + value );
			} else if (geometry instanceof Ellipse){
				((Ellipse)geometry).radiusx = new Unit( value );
				log( "rx: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("ry")){

			if (geometry instanceof Rectangle){
				((Rectangle)geometry).ry = new Unit( value );
				log( "ry: " + value );
			} else if (geometry instanceof Ellipse){
				((Ellipse)geometry).radiusy = new Unit( value );
				log( "ry: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("r")){

			if (geometry instanceof Circle){
				((Circle)geometry).radius = new Unit( value );
				log( "r: " + value );
			}
			
			
		} else if (attr.equalsIgnoreCase("cx")){

			if (geometry instanceof Ellipse){
				((Ellipse)geometry).x = new Unit( value );
				log( "cx: " + value );
				
			} else if (geometry instanceof Circle){
				((Circle)geometry).x = new Unit( value );
				log( "cx: " + value );
			}
			
		} else if (attr.equalsIgnoreCase("cy")){

			if (geometry instanceof Ellipse){
				((Ellipse)geometry).y = new Unit( value );
				log( "cy: " + value );
				
			} else if (geometry instanceof Circle){
				((Circle)geometry).y = new Unit( value );
				log( "cy: " + value );
			}
			
		} else if (attr.equalsIgnoreCase( Polyline.POINTS )){

			if (geometry instanceof Polyline){
				((Polyline)geometry).setPoints( value );
				log( "points: " + value );
			} else if (geometry instanceof Polygon){
				((Polygon)geometry).setPoints( value );
				log( "points: " + value );
			}
			
		} else if (attr.equalsIgnoreCase( Path.D )){

			if (geometry instanceof Path){
				((Path)geometry).setElements( value );
				log( "d: " + value );
			}
			
		} else if (attr.equalsIgnoreCase( Text.FONTSIZE )){

			if (geometry instanceof Text){
				((Text)geometry).font.size = new Unit( value );
				log( "font-size: " + value );
			}
			
		} else if (attr.equalsIgnoreCase( Text.FONTFAMILY )){

			if (geometry instanceof Text){
				((Text)geometry).font.name = value;
				log( "font-family: " + value );
			}
			
		} else if (attr.equalsIgnoreCase( Text.FONTWEIGHT )){

			if (geometry instanceof Text){
				((Text)geometry).font.weight = new FontWeight( value );
				log( "font-weight: " + value );
			}
			
		} else if (attr.equalsIgnoreCase( Text.FONTSTYLE )){

			if (geometry instanceof Text){
				((Text)geometry).font.style = new FontStyle( value );
				log( Text.FONTSTYLE +": " + value );
			}
			
		} else if (attr.equalsIgnoreCase( Text.FONTVARIANT )){

			if (geometry instanceof Text){
				((Text)geometry).font.variant = new FontVariant( value );
				log( Text.FONTVARIANT +": " + value );
			}
			
		} else if (attr.equalsIgnoreCase( Text.ANCHOR )){

			if (geometry instanceof Text){
				((Text)geometry).anchor = new TextAnchor( value );
				log( Text.ANCHOR +": " + value );
			}
			
		} else if (attr.equalsIgnoreCase( Transform.TRANSFORM )){

			geometry.transform = new Transform( value );
			log( "transform: " + value );		
			
		} else if (attr.equalsIgnoreCase( Cursor.CURSOR )){

			geometry.cursor = new Cursor( value );
			log( "cursor: " + value );	
			
		} else if (attr.equalsIgnoreCase( Text.ALIGNMENT_BASELINE )){

			if (geometry instanceof Text){
				((Text)geometry).baselineAlignment = new BaselineAlignment( value );
				log( "baselineAlignment" +": " + value );
			}
			
		} else if (attr.equalsIgnoreCase( Text.EDITABLE )){

			if (geometry instanceof Text){
				((Text)geometry).editable = value.equalsIgnoreCase("true");
				log( "editable" +": " + ((Text)geometry).editable );
			}
			
		} else {
			error( "Unknown attribute: " + attr + "="+ value );
		}
	}
	
	public static void log( Object msg ){
//		System.out.println( "GeometryParser: " + msg );
	}
	
	public static void error( Object message ){
//		System.err.println( message );
	}	
}