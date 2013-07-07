package org.laukvik.svg.parser;

import org.laukvik.svg.SVGSource;

public interface ParseListener{

	public void valueChanged( int value );
	public void valueChanged( String value );
	public void parseStarting();
	public void parseComplete();
	public void parseFailed( Exception e, SVGSource source );
	
}