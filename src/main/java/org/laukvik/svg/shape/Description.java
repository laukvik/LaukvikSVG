package org.laukvik.svg.shape;

public class Description extends Element {

	private String text;
	
	public Description( String text ){
		super();
		if (text == null){
			this.text = text;
		} else {
			this.text = text.trim();
		}
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
}