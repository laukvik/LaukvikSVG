package org.laukvik.svg.font;

import javax.swing.UIManager;

import org.laukvik.svg.unit.Unit;

public class Font {

	public FontVariant variant = new FontVariant();
	public Unit size;
	public String name;
	public FontStyle style = FontStyle.getNormal();
	public FontWeight weight = FontWeight.getNormal();
	
	public Font(){
		this.name = UIManager.getFont("Label.font").getFontName();
		this.size = new Unit( UIManager.getFont("Label.font").getSize() );
	}

	public Font( String name, Unit size ) {
		this.name = name;
		this.size = size;
	}
	
	public Font( Unit size ) {
		this.name = UIManager.getFont("Label.font").getFontName();
		this.size = size;
	}

}