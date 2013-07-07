package org.laukvik.svg.transform;

import java.awt.Graphics2D;

public class Matrix extends TransformItem {

	public static final String MATRIX = "matrix";
	public float a, b, c, d, e, f;
	
	public Matrix( String matrix ) {
		if (matrix == null || matrix.length() == 0){
			setValues(0,0,0,0,0,0);
		} else {
			String [] arr = matrix.split( "\\s" );
			if (arr.length == 6){
				this.a = Float.parseFloat( arr[ 0 ] );
				this.b = Float.parseFloat( arr[ 1 ] );
				this.c = Float.parseFloat( arr[ 2 ] );
				this.d = Float.parseFloat( arr[ 3 ] );
				this.e = Float.parseFloat( arr[ 4 ] );
				this.f = Float.parseFloat( arr[ 5 ] );
				setValues( a,b,c, d,e,f );
			} else {
				setValues(0,0,0,0,0,0);
			}
		}
	}

	public Matrix( float a, float b, float c, float d, float e, float f) {
		setValues( a,b,c, d,e,f );
	}
	
	public void setValues( float a, float b, float c, float d, float e, float f ){
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
	}

	public Matrix duplicate(){
		return new Matrix( a,b,c,d,e,f );
	}

	public void paint(Graphics2D g2) {
//		log( "SkewX: " + x.getPixels() );
//		g2.shear( x.getValue(), 0 );
	}
	
}