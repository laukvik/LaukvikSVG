package org.laukvik.svg.stroke;

import java.awt.BasicStroke;

public class LineCap {

	private int type;

	public final static int INHERIT_TYPE = 0;
	public final static int BUTT_TYPE    = BasicStroke.CAP_BUTT;
	public final static int ROUND_TYPE   = BasicStroke.CAP_ROUND;
	public final static int SQUARE_TYPE  = BasicStroke.CAP_SQUARE;

	public final static LineCap INHERIT = new LineCap( INHERIT_TYPE );
	public final static LineCap BUTT    = new LineCap( BUTT_TYPE );
	public final static LineCap ROUND   = new LineCap( ROUND_TYPE );
	public final static LineCap SQUARE  = new LineCap( SQUARE_TYPE );
	
	public LineCap(){
		this.type = BUTT_TYPE;
	}
	
	public LineCap( String type ){
		if (type.equalsIgnoreCase("butt")){
			this.type = BUTT_TYPE;
		} else if (type.equalsIgnoreCase("round")){
			this.type = ROUND_TYPE;
		} else if (type.equalsIgnoreCase("square")){
			this.type = SQUARE_TYPE;
		} else {
			this.type = INHERIT_TYPE;
		}
	}
	
	private LineCap( int type ){
		this.type = type;
	}
	
	public int getValue(){
		return type;
	}
	
	public boolean isButt(){
		return this.type == BUTT_TYPE;
	}
	
	public boolean isRound(){
		return this.type == ROUND_TYPE;
	}
	
	public boolean isSquare(){
		return this.type == SQUARE_TYPE;
	}
	
	public boolean isInherit(){
		return this.type == INHERIT_TYPE;
	}
	

	public String toString() {
		switch(type){
			case ROUND_TYPE : return "round";
			case BUTT_TYPE : return "butt";
			case SQUARE_TYPE : return "square";
			default : return "round";
		}
	}
	
}