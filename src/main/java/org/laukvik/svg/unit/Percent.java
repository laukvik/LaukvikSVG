package org.laukvik.svg.unit;

public class Percent extends Unit{

	private Number value;
	
	public Percent( String value ){
		super( parseValue(value) );
	}
	
	public Percent( float value ){
		super( value, Unit.PERCENT );
	}
	
	private static float parseValue( String value ){
		float n = 0;
		if (value == null || value.trim().length() == 0){
			n = 0; 
		} else if (value.endsWith("%")){
			n = Integer.parseInt( value.substring(0, value.length()-1 ) );
		} else {
			n = Float.parseFloat( value );
		}
		return n;
	}
	
	public int toInt() {
		return value.intValue();
	}
	
	public String toString(){
		return value + "%";
	}
	
	public float toFloat(){
		return value.floatValue();
	}
	
}