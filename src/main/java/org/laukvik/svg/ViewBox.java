package org.laukvik.svg;

import org.laukvik.svg.unit.Unit;

/**
 * 
 * @author morten
 * @see http://www.w3.org/TR/SVG11/coords.html#ViewBoxAttribute
 */
public class ViewBox {

	public Unit x = Unit.ZERO;
	public Unit y = Unit.ZERO; 
	public Unit w = Unit.INFINITY; 
	public Unit h = Unit.INFINITY;
	
	public ViewBox( String viewBox ){
		String [] arr = viewBox.split( " " );
		if (arr.length > 0){
			this.x = new Unit( arr[0] );
		}
		if (arr.length > 1){
			this.y = new Unit( arr[1] );
		}
		if (arr.length > 2){
			this.w = new Unit( arr[2] );
		}
		if (arr.length > 3){
			this.h = new Unit( arr[3] );
		}
	}
	
	public ViewBox( Unit x, Unit y, Unit w, Unit h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public ViewBox(){
	}
	
}