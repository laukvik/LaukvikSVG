package org.laukvik.svg.shape;

public class SVGClass {

	public static final String SVGCLASS = "class";
	private String value = "";
	
	public SVGClass(){
	}
	
	public SVGClass( String value ){
		if (value == null){
			this.value = "";
		} else {
			this.value = value.trim();
		}
	}
	
	public String [] listClassNames(){
		return this.value.split( " " );
	}

	public String toString() {
		return value;
	}

	public boolean isEmpty() {
		return value.length() == 0;
	}
	
}