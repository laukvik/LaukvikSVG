package org.laukvik.svg.font;

public class FontVariant {
	
	public final static String TYPE_INHERIT   = "inherit";
	public final static String TYPE_NORMAL    = "normal";
	public final static String TYPE_SMALLCAPS = "small-caps";
	
	public final static String [] VALUES = { TYPE_INHERIT, TYPE_NORMAL, TYPE_SMALLCAPS };
	
	public final static FontVariant NORMAL = new FontVariant( TYPE_NORMAL );
	public final static FontVariant SMALLCAPS = new FontVariant( TYPE_SMALLCAPS );
	
	private String style;
	
	public FontVariant() {
		this.style = TYPE_NORMAL;
	}
	
	public FontVariant( String variant ){
		this.style = TYPE_NORMAL;
		for (String s : VALUES){
			if (style.equalsIgnoreCase( s )){
				this.style = variant;
				return;
			}
		}
	}

	public boolean isSmallCaps(){
		return this.style.equalsIgnoreCase( TYPE_SMALLCAPS );
	}
	
	public boolean isNormal(){
		return this.style.equalsIgnoreCase( TYPE_NORMAL );
	}
	
	public boolean isInherit(){
		return this.style.equalsIgnoreCase( TYPE_INHERIT );
	}
	
}