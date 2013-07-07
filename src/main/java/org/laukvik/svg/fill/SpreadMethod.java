package org.laukvik.svg.fill;

public class SpreadMethod {

	public final static int PAD_TYPE = 0;
	public final static int REFLECT_TYPE = 1;
	public final static int REPEAT_TYPE = 2;
	
	public static SpreadMethod PAD = new SpreadMethod(PAD_TYPE);
	public static SpreadMethod REFLECT = new SpreadMethod(REFLECT_TYPE);
	public static SpreadMethod REPEAT = new SpreadMethod(REPEAT_TYPE);
	
	private int type;
	
	private SpreadMethod( int type ){
		this.type = type;
	}
	
	public SpreadMethod( String value ){
		if (value == null){
			/* */
			this.type = PAD_TYPE;
		} else if (value.equalsIgnoreCase("pad")){
			this.type = PAD_TYPE;
		} else if (value.equalsIgnoreCase("reflect")){
			this.type = REFLECT_TYPE;
		} else if (value.equalsIgnoreCase("repeat")){
			this.type = REPEAT_TYPE;
		} else {
			this.type = PAD_TYPE;
		}
	}
	
	public boolean isPad(){
		return type == PAD_TYPE;
	}
	
	public boolean isReflect(){
		return type == REFLECT_TYPE;
	}
	
	public boolean isRepeat(){
		return type == REPEAT_TYPE;
	}
	
}