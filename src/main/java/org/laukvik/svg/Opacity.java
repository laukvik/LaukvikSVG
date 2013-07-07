package org.laukvik.svg;

import org.laukvik.svg.unit.Percent;

/**
 * 
 * 
 * @author morten
 * @see http://www.w3.org/TR/SVG/masking.html#ObjectAndGroupOpacityProperties
 */
public class Opacity{
	
	public final static float TRANSPARENT_TYPE = 0;
	public final static float OPAQUE_TYPE = 1;
	public final static Opacity TRANSPARENT = new Opacity( TRANSPARENT_TYPE );
	public final static Opacity OPAQUE = new Opacity( OPAQUE_TYPE );
	private float value = OPAQUE_TYPE;

	public Opacity( float opacity ){
		setValue( opacity );
	}
	
	public Opacity( Percent opacity ){
		this( opacity.toFloat() / 100 );
	}
	
	public Opacity( String opacity ){
		if (opacity == null || opacity.length() == 0){
			
		} else if (opacity.endsWith("%")){
//			System.out.println( "Opacity%: " + new Percent( opacity ) );
			setValue( Float.parseFloat( opacity.substring(0, opacity.length()-1 ) ) );
		} else {
			setValue( Float.parseFloat( opacity ) );
		}
	}
	
	public void setValue( Percent opacity ){
		setValue( opacity.toFloat() / 100 );
	}
	
	public void setValue( float opacity ){
		if (opacity > 1){
			this.value = 1;
		} else if(opacity <0){
			this.value = 0;
		} else {
			this.value = opacity;
		}
	}

	public float toFloat() {
		return value;
	}
	
	public String toString() {
		return value + "";
	}
}