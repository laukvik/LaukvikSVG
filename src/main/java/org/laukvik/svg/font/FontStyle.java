package org.laukvik.svg.font;

public class FontStyle {

	public final static String TYPE_INHERIT = "inherit";
	public final static String TYPE_NORMAL  = "normal";
	public final static String TYPE_ITALIC   = "italic";
	public final static String TYPE_OBLIQUE  = "oblique";
	
	public final static String [] VALUES = { TYPE_INHERIT, TYPE_NORMAL, TYPE_ITALIC, TYPE_OBLIQUE };
	
	public final static FontStyle NORMAL  = new FontStyle( TYPE_NORMAL );
	public final static FontStyle ITALIC  = new FontStyle( TYPE_ITALIC );
	
	private String style;
	
	/**
	 * inherit italic normal oblique
	 * 
	 */
	public FontStyle( String style ){
		this.style = TYPE_NORMAL;
		for (String s : VALUES){
			if (style.equalsIgnoreCase( s )){
				this.style = style;
				return;
			}
		}
	}
	
	public FontStyle(){
		this.style = TYPE_NORMAL;
	}
	
	public String toString() {
		return this.style;
	}
	
	public String getValue(){
		return style;
	}
	
	public boolean isItalic(){
		return this.style.equalsIgnoreCase( TYPE_ITALIC );
	}

	public static FontStyle getNormal() {
		return new FontStyle(TYPE_NORMAL);
	}
	
}