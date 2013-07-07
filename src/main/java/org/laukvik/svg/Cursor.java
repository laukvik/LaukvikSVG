package org.laukvik.svg;

/**
 * 
 * @author morten
 * @see http://www.w3.org/TR/SVG/interact.html#Cursors
 */
public class Cursor {
	
	public static final String CURSOR = "cursor";

	/**
	 * auto | crosshair | default | pointer | move | e-resize | ne-resize | nw-resize | n-resize | 
	 * se-resize | sw-resize | s-resize | w-resize| text | wait | help ] ] | inherit
	 */

	private int type = java.awt.Cursor.DEFAULT_CURSOR;
	
	public static Cursor DEFAULT = new Cursor( java.awt.Cursor.DEFAULT_CURSOR );
	public static Cursor CROSSHAIR = new Cursor( java.awt.Cursor.CROSSHAIR_CURSOR );
	public static Cursor POINTER = new Cursor( java.awt.Cursor.HAND_CURSOR );
	public static Cursor MOVE = new Cursor( java.awt.Cursor.MOVE_CURSOR );
	public static Cursor E_RESIZE = new Cursor( java.awt.Cursor.E_RESIZE_CURSOR );
	public static Cursor NE_RESIZE = new Cursor( java.awt.Cursor.NE_RESIZE_CURSOR );
	public static Cursor NW_RESIZE = new Cursor( java.awt.Cursor.NW_RESIZE_CURSOR );
	public static Cursor N_RESIZE = new Cursor( java.awt.Cursor.N_RESIZE_CURSOR );
	
	public static Cursor SE_RESIZE = new Cursor( java.awt.Cursor.SE_RESIZE_CURSOR );
	public static Cursor SW_RESIZE = new Cursor( java.awt.Cursor.SW_RESIZE_CURSOR );
	public static Cursor S_RESIZE = new Cursor( java.awt.Cursor.S_RESIZE_CURSOR );
	public static Cursor W_RESIZE = new Cursor( java.awt.Cursor.W_RESIZE_CURSOR );
	public static Cursor TEXT = new Cursor( java.awt.Cursor.TEXT_CURSOR );
	public static Cursor WAIT = new Cursor( java.awt.Cursor.WAIT_CURSOR );
	public static Cursor HELP = new Cursor( java.awt.Cursor.DEFAULT_CURSOR );
	
	public final static Cursor [] AVAILABLE_CURSORS = { DEFAULT, CROSSHAIR, POINTER, MOVE, E_RESIZE, NE_RESIZE, NW_RESIZE, NW_RESIZE, N_RESIZE,
		SE_RESIZE, SW_RESIZE, S_RESIZE, S_RESIZE, W_RESIZE, TEXT, WAIT, HELP }; 

	public Cursor( int type ){
		this.type = type;
	}
	
	public Cursor( String type ){
		if (type == null){
			this.type = java.awt.Cursor.DEFAULT_CURSOR;
		} else if (type.equalsIgnoreCase("pointer")){
			this.type = java.awt.Cursor.HAND_CURSOR;
		} else if (type.equalsIgnoreCase("move")){
			this.type = java.awt.Cursor.MOVE_CURSOR;
		} else if (type.equalsIgnoreCase("e-resize")){
			this.type = java.awt.Cursor.E_RESIZE_CURSOR;
		} else if (type.equalsIgnoreCase("ne-resize")){
			this.type = java.awt.Cursor.NE_RESIZE_CURSOR;
		} else if (type.equalsIgnoreCase("nw-resize")){
			this.type = java.awt.Cursor.NW_RESIZE_CURSOR;
		} else if (type.equalsIgnoreCase("n-resize")){
			this.type = java.awt.Cursor.N_RESIZE_CURSOR;
		} else if (type.equalsIgnoreCase("se-resize")){
			this.type = java.awt.Cursor.SE_RESIZE_CURSOR;
			
		} else if (type.equalsIgnoreCase("sw-resize")){
			this.type = java.awt.Cursor.SW_RESIZE_CURSOR;
		} else if (type.equalsIgnoreCase("s-resize")){
			this.type = java.awt.Cursor.S_RESIZE_CURSOR;
		} else if (type.equalsIgnoreCase("w-resize")){
			this.type = java.awt.Cursor.W_RESIZE_CURSOR;
		} else if (type.equalsIgnoreCase("text")){
			this.type = java.awt.Cursor.TEXT_CURSOR;
		} else if (type.equalsIgnoreCase("wait")){
			this.type = java.awt.Cursor.WAIT_CURSOR;
		} else if (type.equalsIgnoreCase("help")){
			this.type = java.awt.Cursor.DEFAULT_CURSOR;
		}
	}
	
	public java.awt.Cursor getCursor(){
		return new java.awt.Cursor( type );
	}
	
	public String toString() {
		return new java.awt.Cursor( type ).getName();
	}

	public String getName() {
		return new java.awt.Cursor( type ).getName();
	}
	
}