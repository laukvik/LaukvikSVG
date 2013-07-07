package org.laukvik.svg.shape.text;

/**
 * 
 * @author morten
 * @see http://www.w3.org/TR/SVG/text.html#AlignmentProperties
 */
public class BaselineAlignment {

	public final static int TOP_TYPE = 1;
	public final static int MIDDLE_TYPE = 2;
	public final static int BOTTOM_TYPE = 3;
	
	public final static BaselineAlignment TOP = new BaselineAlignment( TOP_TYPE );
	public final static BaselineAlignment MIDDLE = new BaselineAlignment( MIDDLE_TYPE );
	public final static BaselineAlignment BOTTOM = new BaselineAlignment( BOTTOM_TYPE );

	private int rule;

	private BaselineAlignment( int rule ){
		this.rule = rule;
	}
	
	public BaselineAlignment( String rule ){
		if (rule.equalsIgnoreCase( "top" )){
			this.rule = TOP_TYPE;
		} else if (rule.equalsIgnoreCase( "middle" )){
			this.rule = MIDDLE_TYPE;
		} else if (rule.equalsIgnoreCase( "bottom" )){
			this.rule = BOTTOM_TYPE;
		} else {
			this.rule = TOP_TYPE;
		}
	}
	
	public String toString(){
		switch(rule){
			case TOP_TYPE : return "start";
			case MIDDLE_TYPE : return "middle";
			case BOTTOM_TYPE : return "end";
			default : return "unknown";
		}
	}
	
	public boolean isTop(){
		return this.rule == TOP_TYPE;
	}
	
	public boolean isMiddle(){
		return this.rule == MIDDLE_TYPE;
	}
	
	public boolean isBottom(){
		return this.rule == BOTTOM_TYPE;
	}
}