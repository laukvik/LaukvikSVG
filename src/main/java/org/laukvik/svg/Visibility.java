package org.laukvik.svg;

public class Visibility {
	
	public final static String VISIBILITY = "visibility";

	public final static int VISIBLE_TYPE = 1;
	public final static int HIDDEN_TYPE = 0;
	
	public final static Visibility VISIBLE = new Visibility( VISIBLE_TYPE );
	public final static Visibility HIDDEN = new Visibility( HIDDEN_TYPE );
	
	int value;
	
	public Visibility( String value ){
		if (value.equalsIgnoreCase("visible")){
			this.value = VISIBLE_TYPE;
		} else if (value.equalsIgnoreCase("hidden")){
			this.value = HIDDEN_TYPE;
		}
	}
	
	private Visibility( int value ){
		this.value = value;
	}
	
	public Visibility( boolean visible ) {
		this.value = visible ? VISIBLE_TYPE : HIDDEN_TYPE;
	}

	public boolean isVisible(){
		return value == VISIBLE_TYPE;
	}
	

	public String toString() {
		return (value == VISIBLE_TYPE) ? "visible" : "hidden";
	}

	public void setVisibility( boolean visible ) {
		this.value = (visible ? VISIBLE_TYPE : HIDDEN_TYPE);
	}
	
	public void toggle(){
		if (value == VISIBLE_TYPE){
			this.value = HIDDEN_TYPE;
		} else {
			this.value = VISIBLE_TYPE;
		}
	}
	
}