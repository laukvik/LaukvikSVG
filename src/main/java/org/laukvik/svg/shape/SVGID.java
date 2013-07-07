package org.laukvik.svg.shape;

public class SVGID {

	public static final String ID = "id";
	private String id = "";
//	public SVG svg;
	
	public SVGID(){
	}
	
//	public SVGID( String id, SVG svg ){
//		this( id );
//		if (svg == null){
//			throw new IllegalArgumentException("SVG can't be null!");
//		}
//		this.svg = svg;
//	}
	
	public SVGID( String value ) {
		if (id == null){
		} else {
			this.id = extractID( value );
		}
	}
	
	private String extractID( String value ){
		return value.replaceAll("url\\(", "").replaceAll( "\\)", "" ).replace("#", "").trim();
	}

	public String getValue(){
		return id;
	}
	
	public boolean isID( String id ){
		return this.id.equalsIgnoreCase( id );
	}
	
	public String toString(){
		return id;
	}
	
}