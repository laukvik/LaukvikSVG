package org.laukvik.svg.shape.text;

/**
 * 
 * @author morten
 * @see http://www.w3.org/TR/SVG/text.html#AlignmentProperties
 */
public class TextAnchor {

	// 	start | middle | end |

//	public final static int INHERIT_TYPE = 0;
	public final static int START_TYPE = 1;
	public final static int MIDDLE_TYPE = 2;
	public final static int END_TYPE = 3;
	
//	public final static TextAnchor INHERIT = new TextAnchor( INHERIT_TYPE );
	public final static TextAnchor START = new TextAnchor( START_TYPE );
	public final static TextAnchor MIDDLE = new TextAnchor( MIDDLE_TYPE );
	public final static TextAnchor END = new TextAnchor( END_TYPE );

	private int rule;

	private TextAnchor( int rule ){
		this.rule = rule;
	}
	
	public TextAnchor( String rule ){
		if (rule.equalsIgnoreCase( "start" )){
			this.rule = START_TYPE;
		} else if (rule.equalsIgnoreCase( "middle" )){
			this.rule = MIDDLE_TYPE;
		} else if (rule.equalsIgnoreCase( "end" )){
			this.rule = END_TYPE;
		} else {
			this.rule = START_TYPE;
		}
	}
	
	public String toString(){
		switch(rule){
			case START_TYPE : return "start";
			case MIDDLE_TYPE : return "middle";
			case END_TYPE : return "end";
			default : return "unknown";
		}
	}
	
//	public boolean isInherit(){
//		return this.rule == INHERIT_TYPE;
//	}
	
	public boolean isStart(){
		return this.rule == START_TYPE;
	}
	
	public boolean isMiddle(){
		return this.rule == MIDDLE_TYPE;
	}
	
	public boolean isEnd(){
		return this.rule == END_TYPE;
	}
}