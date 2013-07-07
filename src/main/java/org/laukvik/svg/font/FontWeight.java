package org.laukvik.svg.font;

public class FontWeight {
	
	/**/
	public final static String TYPE_INHERIT = "inherit";
	public final static String TYPE_NORMAL  = "normal";
	public final static String TYPE_BOLD    = "bold";
	public final static String TYPE_BOLDER  = "bolder";
	public final static String TYPE_LIGHTER = "lighter";
	
	public final static String [] VALUES = { TYPE_INHERIT, TYPE_NORMAL, TYPE_BOLD, TYPE_BOLDER, TYPE_LIGHTER };
	
	public final static FontWeight NORMAL = new FontWeight( TYPE_NORMAL );
	public final static FontWeight BOLD = new FontWeight( TYPE_BOLD );

	/**/
	private String weight;

	/**
	 * 
	 */
	public FontWeight(){
		this.weight = TYPE_NORMAL;
	}
	
	/**
	 * inherit bold normal bolder lighter 100-900
	 * 
	 */
	public FontWeight( String weight ){
		this.weight = TYPE_NORMAL;
		for (String s : VALUES){
			if (weight.equalsIgnoreCase( s )){
				this.weight = weight;
				return;
			}
		}
	}
	
	public String toString() {
		return this.weight;
	}
	
	public String getValue(){
		return weight;
	}
	
	public boolean isBold(){
		return this.weight.equalsIgnoreCase( TYPE_BOLD );
	}

	public static FontWeight getNormal() {
		return new FontWeight( TYPE_NORMAL );
	}
	
}