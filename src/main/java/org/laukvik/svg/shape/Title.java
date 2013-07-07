package org.laukvik.svg.shape;

public class Title extends Element{

	private String title;
	public final static String TITLE = "title";
	
	public Title( String title ){
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
}