package org.laukvik.svg.unit;

/**
 * 
 * The supported length unit identifiers are: em, ex, px, pt, pc, cm, mm, in, and percentages.
 * 
 * @author morten
 *
 */
public class Unit{
	
	public final static String NAME_CM 		= "cm";
	public final static String NAME_PX 		= "px";
	public final static String NAME_PERCENT	= "%";
	public final static String NAME_EM		= "em";
	public final static String NAME_EX		= "ex";
	public final static String NAME_PT		= "pt";
	public final static String NAME_PC		= "pc";
	public final static String NAME_MM		= "mm";
	public final static String NAME_IN		= "in";
	public final static String [] NAME_UNITS = { NAME_CM, NAME_PX, NAME_PERCENT };
	
	public final static int CM 		= 1;
	public final static int PX 		= 0;
	public final static int PERCENT = 2;
	public final static int MM		= 3;
	public final static int M		= 4;
	public final static int [] UNITS = { PX, CM, PERCENT };
	
	final static int PIXELS_PR_CM   = 100;
	final static int PIXELS_PR_INCH = 100;
	
	private float number;
	private int unit;
	
	public static Unit ZERO = new Unit( 0, PX );
	public static Unit PERCENT100 = new Unit( 100, PERCENT );
	public static Unit INFINITY   = new Unit( Float.MAX_VALUE, PX );

	public Unit( String unit ){
		if (unit == null || unit.length() == 0){
			this.number = 0;
			this.unit = PX;
		} else {
			if (unit.endsWith( NAME_CM )){
				this.unit = CM;
				this.number = Float.parseFloat( unit.substring( 0, unit.length()-2 ) );
			} else if (unit.endsWith( NAME_PERCENT )){
				this.unit = PERCENT;
				this.number = Float.parseFloat( unit.substring( 0, unit.length()-1 ) );
			} else if (unit.endsWith( NAME_PX )){
				this.unit = PX;
				this.number = Float.parseFloat( unit.substring( 0, unit.length()-2 ) );
			} else {
				this.unit = PX;
				this.number = Float.parseFloat( unit );
			}
		}
	}
	
	public Unit( Number number ){
		this( number.floatValue(), PX );
	}
	
	public Unit( Number number, int unit ){
		this.number = number.floatValue();
		this.unit = unit;
	}
	
	public String getName( int unit ){
		switch (unit){
			case CM : return NAME_CM;
			case PX : return NAME_PX;
			case PERCENT : return NAME_PERCENT;
			default: return null;
		}
	}

	public float getValue(){
		return number;
	}
	
	public String toString(){
		if (isInfinity()){
			return "100%";
		} else if (isPX()){
			return (int) number + "";	
		} else {
			return number + "" + getName( unit );	
		}
	}
	
	public boolean isCM(){
		return this.unit == CM;
	}
	
	public boolean isPX(){
		return this.unit == PX;
	}
	
	public boolean isPercent(){
		return this.unit == PERCENT;
	}
	
	public boolean is100Percent(){
		return this.unit == PERCENT && number == 100;
	}

	public boolean isSame( Unit anotherUnit ) {
		return (this.unit == anotherUnit.unit && this.number == anotherUnit.number);
	}

	/**
	 * Returns the percent amount of the given number
	 * 
	 * @param width
	 * @return
	 */
	public int getPercent( float value ) {
		if (!isPercent()){
			throw new IllegalArgumentException( "Unit not a percentage!" );
		}
		int r = (int) (value * number / 100 );
//		System.out.println( "%" + number + " of " + width + " = " + r );
		return r;
	}

	public int getPixels() {
		if (!isPX()){
			throw new IllegalArgumentException( "Unit not a pixel!" );
		}
		return (int) number;
	}

	public int intValue() {
		return (int) number;
	}

	public boolean isInfinity() {
		return number == Float.MAX_VALUE;
	}

	public String getName() {
		return getName( unit );
	}

	public int getUnitType() {
		return unit;
	}
	
}