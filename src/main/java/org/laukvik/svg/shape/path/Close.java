package org.laukvik.svg.shape.path;

import java.awt.geom.GeneralPath;

public class Close extends PathElement {

	public static final String ABSOLUTE = "Z";
	public static final String RELATIVE = "z";

	public Close(){
	}
	
	public void apply( GeneralPath p ){
		p.closePath();
	}
	
	public String toString() {
		return RELATIVE;
	}
	
}