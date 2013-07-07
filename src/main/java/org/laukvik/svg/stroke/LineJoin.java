package org.laukvik.svg.stroke;

public class LineJoin {

	private int type;
	
	public final static int INHERIT_TYPE = 0;
	public final static int MITER_TYPE   = java.awt.BasicStroke.JOIN_MITER;
	public final static int ROUND_TYPE   = java.awt.BasicStroke.JOIN_ROUND;
	public final static int BEVEL_TYPE   = java.awt.BasicStroke.JOIN_BEVEL;
	

	public final static LineJoin INHERIT = new LineJoin( INHERIT_TYPE );
	public final static LineJoin MITER   = new LineJoin( MITER_TYPE );
	public final static LineJoin ROUND   = new LineJoin( ROUND_TYPE );
	public final static LineJoin BEVEL   = new LineJoin( BEVEL_TYPE );
	
	public LineJoin( String type ){
		if (type.equalsIgnoreCase("miter")){
			this.type = MITER_TYPE;
		} else if (type.equalsIgnoreCase("round")){
			this.type = ROUND_TYPE;
		} else if (type.equalsIgnoreCase("bevel")){
			this.type = BEVEL_TYPE;
		} else {
			this.type = INHERIT_TYPE;
		}
	}
	
	private LineJoin( int type ){
		this.type = type;
	}
	
	public boolean isMeter(){
		return this.type == MITER_TYPE;
	}
	
	public boolean isRound(){
		return this.type == ROUND_TYPE;
	}
	
	public boolean isBevel(){
		return this.type == BEVEL_TYPE;
	}
	
	public boolean isInherit(){
		return this.type == INHERIT_TYPE;
	}

	public int getValue() {
		return type;
	}
	
	public String toString() {
		switch(type){
			case MITER_TYPE : return "miter";
			case ROUND_TYPE : return "round";
			case BEVEL_TYPE : return "bevel";
			default : return "inherit";
		}
	}
	
}