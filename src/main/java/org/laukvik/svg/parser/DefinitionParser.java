package org.laukvik.svg.parser;

import java.util.Vector;

import org.laukvik.svg.Opacity;
import org.laukvik.svg.SVGColor;
import org.laukvik.svg.defs.Definition;
import org.laukvik.svg.fill.LinearGradient;
import org.laukvik.svg.fill.RadialGradient;
import org.laukvik.svg.fill.SpreadMethod;
import org.laukvik.svg.fill.Stop;
import org.laukvik.svg.shape.SVGID;
import org.laukvik.svg.unit.Unit;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DefinitionParser {
	
	public static RadialGradient parseRadialGradient( Node n ){
		RadialGradient lg = new RadialGradient();
		
		/* Get its attributes */
		int count = n.getAttributes().getLength();
		for (int z=0; z<count; z++){
			Node attribute = n.getAttributes().item( z );
			/* Get attribute name and value*/
			String attr = attribute.getNodeName();
			String value = attribute.getNodeValue();
			
			if (attr.equalsIgnoreCase( "id" )){
				lg.id = new SVGID( value );
			} else if (attr.equalsIgnoreCase( "cx" )){
				lg.cx = new Unit( value );
			} else if (attr.equalsIgnoreCase( "cy" )){
				lg.cy = new Unit( value );
			} else if (attr.equalsIgnoreCase( "fx" )){
				lg.fx = new Unit( value );
			} else if (attr.equalsIgnoreCase( "fy" )){
				lg.fy = new Unit( value );
			} else if (attr.equalsIgnoreCase( "r" )){
				lg.r = new Unit( value );
			}

		}
		/* <stop offset="0%" stop-color="red" stop-opacity="0"/> */
		NodeList stopNodes = n.getChildNodes();
        for (int index=0; index<stopNodes.getLength(); index++){
        	Node stopNode = stopNodes.item( index );
    		String nodeName = stopNode.getNodeName();
    		if (nodeName.equalsIgnoreCase("stop")){
    			Stop stop = new Stop();
    			

    			/* Get its attributes */
    			int max = stopNode.getAttributes().getLength();
    			for (int y=0; y<max; y++){
    				Node attribute = stopNode.getAttributes().item( y );
    				/* Get attribute name and value*/
    				String attr = attribute.getNodeName();
    				String value = attribute.getNodeValue();
    				
    				if (attr.equalsIgnoreCase( "offset" )){
    					stop.offset = new Unit( value );
    				} else if (attr.equalsIgnoreCase( "stop-color" )){
    					stop.color = new SVGColor( value );
    				} else if (attr.equalsIgnoreCase( "stop-opacity" )){
    					stop.opacity = new Opacity( value );
    				} else if (attr.equalsIgnoreCase( "style" )){
    					
    				}

    			}
    			
    			
    			
    			lg.add( stop );
    		}
        }

		return lg;
	}
	
	public static LinearGradient parseLinearGradient( Node n ){
		LinearGradient lg = new LinearGradient( new Unit(0), new Unit(00), new Unit(00), Unit.PERCENT100 );
		
		/* Get its attributes */
		int count = n.getAttributes().getLength();
		for (int z=0; z<count; z++){
			Node attribute = n.getAttributes().item( z );
			/* Get attribute name and value*/
			String attr = attribute.getNodeName();
			String value = attribute.getNodeValue();
			
			if (attr.equalsIgnoreCase( "id" )){
				lg.id = new SVGID( value );
				
			} else if (attr.equalsIgnoreCase( "x1" )){
				lg.x1 = new Unit( value );
			} else if (attr.equalsIgnoreCase( "x2" )){
				lg.x2 = new Unit( value );
			} else if (attr.equalsIgnoreCase( "y1" )){
				lg.y1 = new Unit( value );
			} else if (attr.equalsIgnoreCase( "y2" )){
				lg.y2 = new Unit( value );
			} else if (attr.equalsIgnoreCase( "spreadMethod" )){
				lg.spreadMethod = new SpreadMethod( value );
			}

		}
		/* <stop offset="0%" stop-color="red" stop-opacity="0"/> */
		NodeList stopNodes = n.getChildNodes();
        for (int index=0; index<stopNodes.getLength(); index++){
        	Node stopNode = stopNodes.item( index );
    		String nodeName = stopNode.getNodeName();
    		if (nodeName.equalsIgnoreCase("stop")){
    			Stop stop = new Stop();
    			

    			/* Get its attributes */
    			int max = stopNode.getAttributes().getLength();
    			for (int y=0; y<max; y++){
    				Node attribute = stopNode.getAttributes().item( y );
    				/* Get attribute name and value*/
    				String attr = attribute.getNodeName();
    				String value = attribute.getNodeValue();
    				
    				if (attr.equalsIgnoreCase( "offset" )){
    					stop.offset = new Unit( value );
    				} else if (attr.equalsIgnoreCase( "stop-color" )){
    					stop.color = new SVGColor( value );
    				} else if (attr.equalsIgnoreCase( "stop-opacity" )){
    					stop.opacity = new Opacity( value );
    				} else if (attr.equalsIgnoreCase( "style" )){
    					
    				}

    			}
    			
    			
    			
    			lg.add( stop );
    		}
        }

		return lg;
	}
	
	public static Definition [] parse( Node definitionNode ){
		Vector <Definition> defs = new Vector<Definition>();
		
		/* Iterate through all child nodes */
		NodeList definitionList = definitionNode.getChildNodes();
        for (int index=0; index<definitionList.getLength(); index++){
        	Node n = definitionList.item( index );
    		String nodeName = n.getNodeName();
    		
    		/* Create new object based on node name */
    		if (nodeName.equalsIgnoreCase( LinearGradient.LINEARGRADIENT )){
    			defs.add( parseLinearGradient(n) );
    		} else if (nodeName.equalsIgnoreCase( RadialGradient.RADIALGRADIENT )){
    			defs.add( parseRadialGradient(n) );
    		} 
        }
		
		/* Convert to array */
		Definition [] ds = new Definition[ defs.size() ];
		defs.toArray( ds );
		return ds;
	}
	
	public static void log( Object message ){
		System.out.println( DefinitionParser.class.getName() + ": " + message );
	}
	
	public static void error( Object message ){
		System.err.println( message );
	}	

}
