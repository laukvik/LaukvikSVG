package org.laukvik.svg.stroke;

import java.util.Vector;

public class DashArray {

	private String dashArray;
	
	public DashArray( String dashArray ){
		this.dashArray = dashArray;
	}

	public DashArray(){
		this.dashArray = "";
	}

	public String toString() {
		return dashArray;
	}
	
	public boolean isEmpty(){
		return dashArray.length() == 0;
	}
	
	public float [] list(){
		Vector <Float> items = new Vector<Float>();
		
		String [] floats = dashArray.split( "," );
		
		for (String s : floats){
			try{
				float f = Float.parseFloat( s );
				items.add( f );
			} catch(Exception e){
				
			}
		}
		
		float [] arr = new float[ items.size() ];

		
		for (int x=0; x<arr.length; x++){
			arr[ x ] = items.get( x );
		}

		return arr;
	}
	
}