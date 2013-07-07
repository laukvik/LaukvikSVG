package org.laukvik.svg.transform;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import org.laukvik.svg.unit.Unit;

/**
 * matrix(<a> <b> <c> <d> <e> <f>), which specifies a transformation in the form of a transformation matrix of six values. matrix(a,b,c,d,e,f) is equivalent to applying the transformation matrix [a b c d e f].
 * translate(<tx> [<ty>]), which specifies a translation by tx and ty. If <ty> is not provided, it is assumed to be zero.
 * scale(<sx> [<sy>]), which specifies a scale operation by sx and sy. If <sy> is not provided, it is assumed to be equal to <sx>.
 * rotate(<rotate-angle> [<cx> <cy>]), which specifies a rotation by <rotate-angle> degrees about a given point.
 * If optional parameters <cx> and <cy> are not supplied, the rotate is about the origin of the current user coordinate system. The operation corresponds to the matrix [cos(a) sin(a) -sin(a) cos(a) 0 0].
 * If optional parameters <cx> and <cy> are supplied, the rotate is about the point (<cx>, <cy>). The operation represents the equivalent of the following specification: translate(<cx>, <cy>) rotate(<rotate-angle>) translate(-<cx>, -<cy>).
 * skewX(<skew-angle>), which specifies a skew transformation along the x-axis.
 * skewY(<skew-angle>), which specifies a skew transformation along the y-axis.
 * 
 * @author morten
 * @see http://www.w3.org/TR/SVG/coords.html#TransformAttribute
 *
 */
public class Transform {
	
	public static final String TRANSFORM = "transform";
	Vector<TransformItem> items;
	
	Rotate rotate;
	Translate translate;
	Scale scale;
	SkewX skewX;
	SkewY skewY;
	Matrix matrix;

	public Transform(){
		this.items = new Vector<TransformItem>();
	}
	
	public Transform( String transformData ){
		this.items = new Vector<TransformItem>();
		String [] arr = transformData.split( "\\)" );
		for (String s : arr){
			String [] keyValue = s.split( "\\(" );
			String key = keyValue[ 0 ].trim();
			String value = keyValue[ 1 ];
//			log( "Transform: " + key + "=" + value );
			if (key.equalsIgnoreCase( Translate.TRANSLATE )){
				String [] points = value.split(",");
				Translate l = new Translate( new Unit(points[0]), new Unit(points[1]) );
				items.add( l );
			} else if (key.equalsIgnoreCase( Rotate.ROTATE )) {
				Rotate rotate = new Rotate( Double.parseDouble( value ) );
				items.add( rotate );
			} else if (key.equalsIgnoreCase( Scale.SCALE )){
	
				String [] points = value.split(",");
				if (points.length == 2){
					Scale scale = new Scale( Float.parseFloat( points[0]), Float.parseFloat( points[1] ) );
					items.add( scale );
				} else {
					Scale scale = new Scale( Float.parseFloat( points[0] ) );
					items.add( scale );
				}
			}
		}
	}
	
	public Transform duplicate(){
		Transform t = new Transform();
		for(TransformItem item : items){
			t.add( item );
		}
		return t;
	}
	
	public void add( TransformItem item ){
		this.items.add( item );
	}
	
	public void remove( TransformItem item ){
		this.items.remove( item );
	}
	
	public void paint(Graphics g){
		AffineTransform tx = new AffineTransform();
		Graphics2D g2 = (Graphics2D) g;
		g2.setTransform( tx );
		for (TransformItem item : items){
			item.paint( g2 );
		}
	}
	
	public Rotate getRotate(){
		return new Rotate(55);
	}
	
	public Translate getTranslate(){
		return new Translate(55,12);
	}
	
	public Scale getScale(){
		return new Scale(55,12);
	}
	
	public SkewX getSkewX(){
		return new SkewX( 5 );
	}
	
	public SkewY getSkewY(){
		return new SkewY( 5 );
	}
	
	public Matrix getMatrix(){
		return new Matrix( 1,1,1, 4,5,6 );
	}

}