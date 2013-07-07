package org.laukvik.svg.unit;

public class Pixel extends Unit {

	public Pixel( int pixels ){
		super( pixels, PX );
	}
	
	public Pixel( String value ){
		super( Float.parseFloat( value ), PX );
	}
	
}