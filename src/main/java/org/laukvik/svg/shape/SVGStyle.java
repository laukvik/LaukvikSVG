package org.laukvik.svg.shape;

public class SVGStyle {

	public static final String STYLE = "style";
	private String value = "";
	
	public SVGStyle(){
	}
	
	public SVGStyle( String value ){
		if (value == null){
			this.value = "";
		} else {
			this.value = value;
		}
	}
	
	public String [] listStyles(){
		return this.value.split( ";" );
	}
	
}