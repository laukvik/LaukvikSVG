package org.laukvik.svg.shape;

import java.util.Vector;

public class Element {
	
	Group group;
	SVGID id = new SVGID();
	SVGClass svgClass = new SVGClass();
	SVGStyle style = new SVGStyle();

	public Element(){
	}

	public SVGID getID() {
		return id;
	}

	public void setID(SVGID id) {
		this.id = id;
	}

	public SVGClass getClassCSS() {
		return svgClass;
	}

	public void setClassCSS(SVGClass svgClass) {
		this.svgClass = svgClass;
	}

	public SVGStyle getStyle() {
		return style;
	}

	public void setStyle(SVGStyle style) {
		this.style = style;
	}

	public SVG getSVG(){
		log( "getSVG: " + group + " " + this );
		if (group == null){
			return null;
		}
		if (group instanceof SVG){
			return (SVG) group;
		}
		
		Group parent = group;
		while (parent.getGroup() != null){
			log( "getSVG: " + parent.getGroup() );
			if (parent instanceof SVG){
				return (SVG) parent;
			}
			parent = parent.getGroup();
		}
		return null;
	}
	
	/**
	 * Returns an array of all objects all the way back to SVG 
	 * 
	 * @return
	 */
	public Element [] listPath(){
//		System.out.println( "listPath: " + this );
		if (group == null){
			return new Element[]{ this };
		}
		Vector <Object> items = new Vector<Object>();
		items.add( this );
		Group parent = group;
		boolean foundSVG = false;
		while (!foundSVG){
//			System.out.println( "listPath.getSVG: " + parent.getGroup() );
			if (parent == null){
				foundSVG = true;
			} else  if( parent instanceof SVG){
				items.add( parent );
				foundSVG = true;
			} else {
				items.add( parent );
				parent = parent.getGroup();	
			}
		}
//		System.out.println( "listPath.Size: " + items.size() );
		Element [] els = new Element[ items.size() ];
		items.toArray( els );
		return els;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	public Group getGroup(){
		return group;
	}
	
	public void log( Object message ){
//		System.out.println( message );
	}
	
	public void error( Object message ){
		System.err.println( message );
	}	

	/**
	 * Returns the name of the element
	 * 
	 * @return
	 */
	public String getTagName() {
		return getClass().getSimpleName().toLowerCase();
	}
	
}