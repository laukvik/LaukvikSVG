package org.laukvik.svg.fill;

public class FillRule {
	
	public final static int INHERIT_TYPE = 0;
	public final static int NONZERO_TYPE = 1;
	public final static int EVENODD_TYPE = 2;
	
	public final static FillRule INHERIT = new FillRule( INHERIT_TYPE );
	public final static FillRule NONZERO = new FillRule( NONZERO_TYPE );
	public final static FillRule EVENODD = new FillRule( EVENODD_TYPE );

	private int rule;

	private FillRule( int rule ){
		this.rule = rule;
	}
	
	public FillRule( String rule ){
		if (rule.equalsIgnoreCase( "evenodd" )){
			this.rule = EVENODD_TYPE;
		} else if (rule.equalsIgnoreCase( "nonzero" )){
			this.rule = NONZERO_TYPE;
		} else {
			this.rule = INHERIT_TYPE;
		}
	}
	
	public boolean isInherit(){
		return this.rule == INHERIT_TYPE;
	}
	
	public boolean isNonZero(){
		return this.rule == INHERIT_TYPE;
	}
	
	public boolean isEvenOdd(){
		return this.rule == INHERIT_TYPE;
	}
	
}