package org.laukvik.svg;

import java.awt.Color;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.util.HashMap;

/**
 * 1). green
 * 2). #ffaa22
 * 3). rgb(0,0,0)
 * 4). #600
 * 
 * @author morten
 *
 */
public class SVGColor implements Paint, Cloneable{

	private static final long serialVersionUID = 1L;
	private Color color = Color.BLACK;

	public final static SVGColor aliceblue = new SVGColor(240, 248, 255);
	public final static SVGColor antiquewhite = new SVGColor(250, 235, 215);
	public final static SVGColor aqua = new SVGColor( 0, 255, 255);
	public final static SVGColor aquamarine = new SVGColor(127, 255, 212);
	public final static SVGColor azure = new SVGColor(240, 255, 255);
	public final static SVGColor beige = new SVGColor(245, 245, 220);
	public final static SVGColor bisque = new SVGColor(255, 228, 196);
	public final static SVGColor black = new SVGColor( 0, 0, 0);
	public final static SVGColor blanchedalmond = new SVGColor(255, 235, 205);
	public final static SVGColor blue = new SVGColor( 0, 0, 255);
	public final static SVGColor blueviolet = new SVGColor(138, 43, 226);
	public final static SVGColor brown = new SVGColor(165, 42, 42);
	public final static SVGColor burlywood = new SVGColor(222, 184, 135);
	public final static SVGColor cadetblue = new SVGColor( 95, 158, 160);
	public final static SVGColor chartreuse = new SVGColor(127, 255, 0);
	public final static SVGColor chocolate = new SVGColor(210, 105, 30);
	public final static SVGColor coral = new SVGColor(255, 127, 80);
	public final static SVGColor cornflowerblue = new SVGColor(100, 149, 237);
	public final static SVGColor cornsilk = new SVGColor(255, 248, 220);
	public final static SVGColor crimson = new SVGColor(220, 20, 60);
	public final static SVGColor cyan = new SVGColor( 0, 255, 255);
	public final static SVGColor darkblue = new SVGColor( 0, 0, 139);
	public final static SVGColor darkcyan = new SVGColor( 0, 139, 139);
	public final static SVGColor darkgoldenrod = new SVGColor(184, 134, 11);
	public final static SVGColor darkgray = new SVGColor(169, 169, 169);
	public final static SVGColor darkgreen = new SVGColor( 0, 100, 0);
	public final static SVGColor darkgrey = new SVGColor(169, 169, 169);
	public final static SVGColor darkkhaki = new SVGColor(189, 183, 107);
	public final static SVGColor darkmagenta = new SVGColor(139, 0, 139);
	public final static SVGColor darkolivegreen = new SVGColor( 85, 107, 47);
	public final static SVGColor darkorange = new SVGColor(255, 140, 0);
	public final static SVGColor darkorchid = new SVGColor(153, 50, 204);
	public final static SVGColor darkred = new SVGColor(139, 0, 0);
	public final static SVGColor darksalmon = new SVGColor(233, 150, 122);
	public final static SVGColor darkseagreen = new SVGColor(143, 188, 143);
	public final static SVGColor darkslateblue = new SVGColor( 72, 61, 139);
	public final static SVGColor darkslategray = new SVGColor( 47, 79, 79);
	public final static SVGColor darkslategrey = new SVGColor( 47, 79, 79);
	public final static SVGColor darkturquoise = new SVGColor( 0, 206, 209);
	public final static SVGColor darkviolet = new SVGColor(148, 0, 211);
	public final static SVGColor deeppink = new SVGColor(255, 20, 147);
	public final static SVGColor deepskyblue = new SVGColor( 0, 191, 255);
	public final static SVGColor dimgray = new SVGColor(105, 105, 105);
	public final static SVGColor dimgrey = new SVGColor(105, 105, 105);
	public final static SVGColor dodgerblue = new SVGColor( 30, 144, 255);
	public final static SVGColor firebrick = new SVGColor(178, 34, 34);
	public final static SVGColor floralwhite = new SVGColor(255, 250, 240);
	public final static SVGColor forestgreen = new SVGColor( 34, 139, 34);
	public final static SVGColor fuchsia = new SVGColor(255, 0, 255);
	public final static SVGColor gainsboro = new SVGColor(220, 220, 220);
	public final static SVGColor ghostwhite = new SVGColor(248, 248, 255);
	public final static SVGColor gold = new SVGColor(255, 215, 0);
	public final static SVGColor goldenrod = new SVGColor(218, 165, 32);
	public final static SVGColor gray = new SVGColor(128, 128, 128);
	public final static SVGColor grey = new SVGColor(128, 128, 128);
	public final static SVGColor green = new SVGColor( 0, 128, 0);
	public final static SVGColor greenyellow = new SVGColor(173, 255, 47);
	public final static SVGColor honeydew = new SVGColor(240, 255, 240);
	public final static SVGColor hotpink = new SVGColor(255, 105, 180);
	public final static SVGColor indianred = new SVGColor(205, 92, 92);
	public final static SVGColor indigo = new SVGColor( 75, 0, 130);
	public final static SVGColor ivory = new SVGColor(255, 255, 240);
	public final static SVGColor khaki = new SVGColor(240, 230, 140);
	public final static SVGColor lavender = new SVGColor(230, 230, 250);
	public final static SVGColor lavenderblush = new SVGColor(255, 240, 245);
	public final static SVGColor lawngreen = new SVGColor(124, 252, 0);
	public final static SVGColor lemonchiffon = new SVGColor(255, 250, 205);
	public final static SVGColor lightblue = new SVGColor(173, 216, 230);
	public final static SVGColor lightcoral = new SVGColor(240, 128, 128);
	public final static SVGColor lightcyan = new SVGColor(224, 255, 255);
	public final static SVGColor lightgoldenrodyellow = new SVGColor(250, 250, 210);
	public final static SVGColor lightgray = new SVGColor(211, 211, 211);
	public final static SVGColor lightgreen = new SVGColor(144, 238, 144);
	public final static SVGColor lightgrey = new SVGColor(211, 211, 211);
	public final static SVGColor lightpink = new SVGColor(255, 182, 193);
	public final static SVGColor lightsalmon = new SVGColor(255, 160, 122);
	public final static SVGColor lightseagreen = new SVGColor( 32, 178, 170);
	public final static SVGColor lightskyblue = new SVGColor(135, 206, 250);
	public final static SVGColor lightslategray = new SVGColor(119, 136, 153);
	public final static SVGColor lightslategrey = new SVGColor(119, 136, 153);
	public final static SVGColor lightsteelblue = new SVGColor(176, 196, 222);
	public final static SVGColor lightyellow = new SVGColor(255, 255, 224);
	public final static SVGColor lime = new SVGColor( 0, 255, 0);
	public final static SVGColor limegreen = new SVGColor( 50, 205, 50);
	public final static SVGColor linen = new SVGColor(250, 240, 230);
	public final static SVGColor magenta = new SVGColor(255, 0, 255);
	public final static SVGColor maroon = new SVGColor(128, 0, 0);
	public final static SVGColor mediumaquamarine = new SVGColor(102, 205, 170);
	public final static SVGColor mediumblue = new SVGColor( 0, 0, 205);
	public final static SVGColor mediumorchid = new SVGColor(186, 85, 211);
	public final static SVGColor mediumpurple = new SVGColor(147, 112, 219);
	public final static SVGColor mediumseagreen = new SVGColor( 60, 179, 113);
	public final static SVGColor mediumslateblue = new SVGColor(123, 104, 238);
	public final static SVGColor mediumspringgreen = new SVGColor( 0, 250, 154);
	public final static SVGColor mediumturquoise = new SVGColor( 72, 209, 204);
	public final static SVGColor mediumvioletred = new SVGColor(199, 21, 133);
	public final static SVGColor midnightblue = new SVGColor( 25, 25, 112);
	public final static SVGColor mintcream = new SVGColor(245, 255, 250);
	public final static SVGColor mistyrose = new SVGColor(255, 228, 225);
	public final static SVGColor moccasin = new SVGColor(255, 228, 181);
	public final static SVGColor navajowhite = new SVGColor(255, 222, 173);
	public final static SVGColor navy = new SVGColor( 0, 0, 128);
	public final static SVGColor oldlace = new SVGColor(253, 245, 230);
	public final static SVGColor olive = new SVGColor(128, 128, 0);
	public final static SVGColor olivedrab = new SVGColor(107, 142, 35);
	public final static SVGColor orange = new SVGColor(255, 165, 0);
	public final static SVGColor orangered = new SVGColor(255, 69, 0);
	public final static SVGColor orchid = new SVGColor(218, 112, 214);
	public final static SVGColor palegoldenrod = new SVGColor(238, 232, 170);
	public final static SVGColor palegreen = new SVGColor(152, 251, 152);
	public final static SVGColor paleturquoise = new SVGColor(175, 238, 238);
	public final static SVGColor palevioletred = new SVGColor(219, 112, 147);
	public final static SVGColor papayawhip = new SVGColor(255, 239, 213);
	public final static SVGColor peachpuff = new SVGColor(255, 218, 185);
	public final static SVGColor peru = new SVGColor(205, 133, 63);
	public final static SVGColor pink = new SVGColor(255, 192, 203);
	public final static SVGColor plum = new SVGColor(221, 160, 221);
	public final static SVGColor powderblue = new SVGColor(176, 224, 230);
	public final static SVGColor purple = new SVGColor(128, 0, 128);
	public final static SVGColor red = new SVGColor(255, 0, 0);
	public final static SVGColor rosybrown = new SVGColor(188, 143, 143);
	public final static SVGColor royalblue = new SVGColor( 65, 105, 225);
	public final static SVGColor saddlebrown = new SVGColor(139, 69, 19);
	public final static SVGColor salmon = new SVGColor(250, 128, 114);
	public final static SVGColor sandybrown = new SVGColor(244, 164, 96);
	public final static SVGColor seagreen = new SVGColor( 46, 139, 87);
	public final static SVGColor seashell = new SVGColor(255, 245, 238);
	public final static SVGColor sienna = new SVGColor(160, 82, 45);
	public final static SVGColor silver = new SVGColor(192, 192, 192);
	public final static SVGColor skyblue = new SVGColor(135, 206, 235);
	public final static SVGColor slateblue = new SVGColor(106, 90, 205);
	public final static SVGColor slategray = new SVGColor(112, 128, 144);
	public final static SVGColor slategrey = new SVGColor(112, 128, 144);
	public final static SVGColor snow = new SVGColor(255, 250, 250);
	public final static SVGColor springgreen = new SVGColor( 0, 255, 127);
	public final static SVGColor steelblue = new SVGColor( 70, 130, 180);
	public final static SVGColor tan = new SVGColor(210, 180, 140);
	public final static SVGColor teal = new SVGColor( 0, 128, 128);
	public final static SVGColor thistle = new SVGColor(216, 191, 216);
	public final static SVGColor tomato = new SVGColor(255, 99, 71);
	public final static SVGColor turquoise = new SVGColor( 64, 224, 208);
	public final static SVGColor violet = new SVGColor(238, 130, 238);
	public final static SVGColor wheat = new SVGColor(245, 222, 179);
	public final static SVGColor white = new SVGColor(255, 255, 255);
	public final static SVGColor whitesmoke = new SVGColor(245, 245, 245);
	public final static SVGColor yellow = new SVGColor(255, 255, 0);
	public final static SVGColor yellowgreen = new SVGColor(154, 205, 50);
	
	private boolean isNone = false;
	
	public SVGColor( String color ){
		if (color.equalsIgnoreCase( "none" )){
			this.isNone = true;
		} else {
			this.color = parse( color );
		}
	}
	
	public SVGColor( int r, int g, int b ){
		this.color = new Color( r, g, b );
	}
	
	public SVGColor( Color color ) {
		this.color = color;
	}

	public boolean isNone() {
		return isNone;
	}
	
	public Color getValue(){
		return color;
	}
	
	private static Color parseRGB( String color ){
		if (color.startsWith("rgb(")){
			String rgb = color.substring( 4, color.length()-1 );
			String [] values = rgb.split(",");
			int r = Integer.parseInt( values[0] );
			int g = Integer.parseInt( values[1] );
			int b = Integer.parseInt( values[2] );
			return new Color( r, g, b );
			
		} else if (color.startsWith("#")){
			
			if (color.length() == 4){
				/* Three digits - e.g #600 */
				int r = Integer.parseInt( color.substring(1,2)+color.substring(1,2), 16 );
				int g = Integer.parseInt( color.substring(2,3)+color.substring(2,3), 16 );
				int b = Integer.parseInt( color.substring(3,4)+color.substring(3,4), 16 );
				return new Color( r, g, b );
				
			} else if (color.length() == 7){
				/* Six digits */
				int rgb = Integer.parseInt( color.substring(1).toUpperCase(), 16 );
				return new Color( rgb );
			}
		} else if (color.equalsIgnoreCase("none")){
			return null;
			
		} else {
			int rgb = Integer.parseInt( color.toUpperCase(), 16 );
			return new Color( rgb );
		}
		return null;
	}
	
	
	private final static Color parse( String color ){
		if (color == null || color.length() == 0){
			return null;
		}
		
		try{
			return parseRGB( color );
		} catch(Exception e){
			


		HashMap<String, Color> map = new HashMap<String, Color>();
		map.put( "aliceblue", new Color(240, 248, 255) );
		map.put( "antiquewhite", new Color(250, 235, 215) );
		map.put( "aqua", new Color( 0, 255, 255) );
		map.put( "aquamarine", new Color(127, 255, 212) );
		map.put( "azure", new Color(240, 255, 255) );
		map.put( "beige", new Color(245, 245, 220) );
		map.put( "bisque", new Color(255, 228, 196) );
		map.put( "black", new Color( 0, 0, 0) );
		map.put( "blanchedalmond", new Color(255, 235, 205) );
		map.put( "blue", new Color( 0, 0, 255) );
		map.put( "blueviolet", new Color(138, 43, 226) );
		map.put( "brown", new Color(165, 42, 42) );
		map.put( "burlywood", new Color(222, 184, 135) );
		map.put( "cadetblue", new Color( 95, 158, 160) );
		map.put( "chartreuse", new Color(127, 255, 0) );
		map.put( "chocolate", new Color(210, 105, 30) );
		map.put( "coral", new Color(255, 127, 80) );
		map.put( "cornflowerblue", new Color(100, 149, 237) );
		map.put( "cornsilk", new Color(255, 248, 220) );
		map.put( "crimson", new Color(220, 20, 60) );
		map.put( "cyan", new Color( 0, 255, 255) );
		map.put( "darkblue", new Color( 0, 0, 139) );
		map.put( "darkcyan", new Color( 0, 139, 139) );
		map.put( "darkgoldenrod", new Color(184, 134, 11) );
		map.put( "darkgray", new Color(169, 169, 169) );
		map.put( "darkgreen", new Color( 0, 100, 0) );
		map.put( "darkgrey", new Color(169, 169, 169) );
		map.put( "darkkhaki", new Color(189, 183, 107) );
		map.put( "darkmagenta", new Color(139, 0, 139) );
		map.put( "darkolivegreen", new Color( 85, 107, 47) );
		map.put( "darkorange", new Color(255, 140, 0) );
		map.put( "darkorchid", new Color(153, 50, 204) );
		map.put( "darkred", new Color(139, 0, 0) );
		map.put( "darksalmon", new Color(233, 150, 122) );
		map.put( "darkseagreen", new Color(143, 188, 143) );
		map.put( "darkslateblue", new Color( 72, 61, 139) );
		map.put( "darkslategray", new Color( 47, 79, 79) );
		map.put( "darkslategrey", new Color( 47, 79, 79) );
		map.put( "darkturquoise", new Color( 0, 206, 209) );
		map.put( "darkviolet", new Color(148, 0, 211) );
		map.put( "deeppink", new Color(255, 20, 147) );
		map.put( "deepskyblue", new Color( 0, 191, 255) );
		map.put( "dimgray", new Color(105, 105, 105) );
		map.put( "dimgrey", new Color(105, 105, 105) );
		map.put( "dodgerblue", new Color( 30, 144, 255) );
		map.put( "firebrick", new Color(178, 34, 34) );
		map.put( "floralwhite", new Color(255, 250, 240) );
		map.put( "forestgreen", new Color( 34, 139, 34) );
		map.put( "fuchsia", new Color(255, 0, 255) );
		map.put( "gainsboro", new Color(220, 220, 220) );
		map.put( "ghostwhite", new Color(248, 248, 255) );
		map.put( "gold", new Color(255, 215, 0) );
		map.put( "goldenrod", new Color(218, 165, 32) );
		map.put( "gray", new Color(128, 128, 128) );
		map.put( "grey", new Color(128, 128, 128) );
		map.put( "green", new Color( 0, 128, 0) );
		map.put( "greenyellow", new Color(173, 255, 47) );
		map.put( "honeydew", new Color(240, 255, 240) );
		map.put( "hotpink", new Color(255, 105, 180) );
		map.put( "indianred", new Color(205, 92, 92) );
		map.put( "indigo", new Color( 75, 0, 130) );
		map.put( "ivory", new Color(255, 255, 240) );
		map.put( "khaki", new Color(240, 230, 140) );
		map.put( "lavender", new Color(230, 230, 250) );
		map.put( "lavenderblush", new Color(255, 240, 245) );
		map.put( "lawngreen", new Color(124, 252, 0) );
		map.put( "lemonchiffon", new Color(255, 250, 205) );
		map.put( "lightblue", new Color(173, 216, 230) );
		map.put( "lightcoral", new Color(240, 128, 128) );
		map.put( "lightcyan", new Color(224, 255, 255) );
		map.put( "lightgoldenrodyellow", new Color(250, 250, 210) );
		map.put( "lightgray", new Color(211, 211, 211) );
		map.put( "lightgreen", new Color(144, 238, 144) );
		map.put( "lightgrey", new Color(211, 211, 211) );
		map.put( "lightpink", new Color(255, 182, 193) );
		map.put( "lightsalmon", new Color(255, 160, 122) );
		map.put( "lightseagreen", new Color( 32, 178, 170) );
		map.put( "lightskyblue", new Color(135, 206, 250) );
		map.put( "lightslategray", new Color(119, 136, 153) );
		map.put( "lightslategrey", new Color(119, 136, 153) );
		map.put( "lightsteelblue", new Color(176, 196, 222) );
		map.put( "lightyellow", new Color(255, 255, 224) );
		map.put( "lime", new Color( 0, 255, 0) );
		map.put( "limegreen", new Color( 50, 205, 50) );
		map.put( "linen", new Color(250, 240, 230) );
		map.put( "magenta", new Color(255, 0, 255) );
		map.put( "maroon", new Color(128, 0, 0) );
		map.put( "mediumaquamarine", new Color(102, 205, 170) );
		map.put( "mediumblue", new Color( 0, 0, 205) );
		map.put( "mediumorchid", new Color(186, 85, 211) );
		map.put( "mediumpurple", new Color(147, 112, 219) );
		map.put( "mediumseagreen", new Color( 60, 179, 113) );
		map.put( "mediumslateblue", new Color(123, 104, 238) );
		map.put( "mediumspringgreen", new Color( 0, 250, 154) );
		map.put( "mediumturquoise", new Color( 72, 209, 204) );
		map.put( "mediumvioletred", new Color(199, 21, 133) );
		map.put( "midnightblue", new Color( 25, 25, 112) );
		map.put( "mintcream", new Color(245, 255, 250) );
		map.put( "mistyrose", new Color(255, 228, 225) );
		map.put( "moccasin", new Color(255, 228, 181) );
		map.put( "navajowhite", new Color(255, 222, 173) );
		map.put( "navy", new Color( 0, 0, 128) );
		map.put( "oldlace", new Color(253, 245, 230) );
		map.put( "olive", new Color(128, 128, 0) );
		map.put( "olivedrab", new Color(107, 142, 35) );
		map.put( "orange", new Color(255, 165, 0) );
		map.put( "orangered", new Color(255, 69, 0) );
		map.put( "orchid", new Color(218, 112, 214) );
		map.put( "palegoldenrod", new Color(238, 232, 170) );
		map.put( "palegreen", new Color(152, 251, 152) );
		map.put( "paleturquoise", new Color(175, 238, 238) );
		map.put( "palevioletred", new Color(219, 112, 147) );
		map.put( "papayawhip", new Color(255, 239, 213) );
		map.put( "peachpuff", new Color(255, 218, 185) );
		map.put( "peru", new Color(205, 133, 63) );
		map.put( "pink", new Color(255, 192, 203) );
		map.put( "plum", new Color(221, 160, 221) );
		map.put( "powderblue", new Color(176, 224, 230) );
		map.put( "purple", new Color(128, 0, 128) );
		map.put( "red", new Color(255, 0, 0) );
		map.put( "rosybrown", new Color(188, 143, 143) );
		map.put( "royalblue", new Color( 65, 105, 225) );
		map.put( "saddlebrown", new Color(139, 69, 19) );
		map.put( "salmon", new Color(250, 128, 114) );
		map.put( "sandybrown", new Color(244, 164, 96) );
		map.put( "seagreen", new Color( 46, 139, 87) );
		map.put( "seashell", new Color(255, 245, 238) );
		map.put( "sienna", new Color(160, 82, 45) );
		map.put( "silver", new Color(192, 192, 192) );
		map.put( "skyblue", new Color(135, 206, 235) );
		map.put( "slateblue", new Color(106, 90, 205) );
		map.put( "slategray", new Color(112, 128, 144) );
		map.put( "slategrey", new Color(112, 128, 144) );
		map.put( "snow", new Color(255, 250, 250) );
		map.put( "springgreen", new Color( 0, 255, 127) );
		map.put( "steelblue", new Color( 70, 130, 180) );
		map.put( "tan", new Color(210, 180, 140) );
		map.put( "teal", new Color( 0, 128, 128) );
		map.put( "thistle", new Color(216, 191, 216) );
		map.put( "tomato", new Color(255, 99, 71) );
		map.put( "turquoise", new Color( 64, 224, 208) );
		map.put( "violet", new Color(238, 130, 238) );
		map.put( "wheat", new Color(245, 222, 179) );
		map.put( "white", new Color(255, 255, 255) );
		map.put( "whitesmoke", new Color(245, 245, 245) );
		map.put( "yellow", new Color(255, 255, 0) );
		map.put( "yellowgreen", new Color(154, 205, 50) );
		return map.get( color.toLowerCase() );
		
		}
	}
	
	 public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	public String toString() {
		return "rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")";
	}

	public PaintContext createContext(ColorModel cm, Rectangle deviceBounds,Rectangle2D userBounds, AffineTransform xform, RenderingHints hints) {
		return color.createContext(cm, deviceBounds, userBounds, xform, hints);
	}

	public int getTransparency() {
		return color.getTransparency();
	}
	
	public static void main( String [] args ){
		System.out.println( SVGColor.parse( "#eb8080" ) );
	}
	
}