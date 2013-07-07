package org.laukvik.svg;


public class Coordinate {

	public float x;
	public float y;
	
	public Coordinate( float x, float y ){
		this.x = x;
		this.y = y;
	}
	
	public Coordinate( int x, int y ){
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return x + "," + y;
	}
	
}