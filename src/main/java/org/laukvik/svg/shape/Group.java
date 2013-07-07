package org.laukvik.svg.shape;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Collection;
import java.util.Vector;

/**
 * 
 * 
 * 
 * <g transform="translate(-10,-20) scale(2) rotate(45) translate(5,10)">
 * 
 * @author Morten Laukvik
 * 
 * @see http://www.w3.org/TR/SVG/coords.html#TransformAttribute
 */
public class Group extends BasicShape {
	
	public static final String SVG = "svg";
	public static final String GROUP = "g";
	public static final String A = "a";
	public Vector <Geometry> items;
	public static final String XLINK_HREF = "xlink:href";
	public String href;
	
	public Group(){
		super(null,null);
		items = new Vector<Geometry>();
	}
	
	public Vector<Geometry> listItems( Point point ) {
		return listItems( point.x, point.y );
	}
	
	public Vector<Geometry> listItems( int x, int y ){
		Vector<Geometry> matches = new Vector<Geometry>();
		for (Geometry g : listItems()){
			if (g.getShape() != null && g.getShape().contains( x, y )){
				matches.add( g );
			} else {
			}
		}
		return matches;
	}
	
	public Collection<? extends Geometry> listItems(){
		Vector<Geometry> all = new Vector<Geometry>();
		for (Geometry g : items){
			if (g instanceof Group){
				Group group = (Group)g;
				all.addAll( group.listItems() );
			} else {
				all.add( g );
			}
		}
		return all;
	}
	
	public void setHref(String href) {
		this.href = href;
	}
	
	public String getHref() {
		return href;
	}
	
	public void moveFront( Geometry geometry  ){
		int orgIndex = geometry.indexOf();
		if (orgIndex == -1){
			/* Not a part of group! */
			throw new IllegalArgumentException("Not a part of this group!");
		} else {
			if (orgIndex < items.size()-1){
//				System.out.println( "MoveFront: from " + orgIndex + " to " + orgIndex+1 + " size=" + items.size() );
				items.remove( geometry );
				items.insertElementAt( geometry, orgIndex+1 );	
			} else {
				/* Already at top */
				Group newGroup = geometry.getGroup().getGroup();
				Group oldGroup = geometry.getGroup();
				oldGroup.remove( geometry );
				newGroup.add( geometry );
//				System.out.println( "MoveFront: not moving from " + orgIndex + " size=" + items.size() );
			}
		}
	}
	
	public void moveBehind( Geometry geometry ){
		int orgIndex = geometry.indexOf();
		if (orgIndex == -1){
			/* Not a part of group! */
			throw new IllegalArgumentException("Not a part of this group!");
		} else {
			if (orgIndex == 0){
				/* Already at top */
				
			} else {
//				if (items.elementAt( orgIndex+1 ) instanceof Group){
//					/* When next position is a group - move into that */
//					Group g = (Group) items.elementAt( orgIndex+1 );
//					items.remove( geometry );
//					g.add(geometry);
//				} else {
					items.remove( geometry );
					items.insertElementAt( geometry, orgIndex-1 );	
//				}

			}
		}
	}
	
	public Geometry add( Geometry geometry ){
//		log( this + " add: " + geometry  );
		geometry.setGroup( this );
		items.add( geometry );
		if (geometry instanceof Image){
			Image image = (Image) geometry;
			image.loadImage();
		}
		
		return geometry;
	}
	
	public Geometry add( Geometry geometry, int index ){
		items.insertElementAt( geometry, index);
		geometry.setGroup( this );
		return geometry;
	}
	
	public Geometry remove( Geometry geometry ){
		geometry.setGroup( null );
		items.remove( geometry );
		return geometry;
	}
	
	public Vector<Geometry> getItems() {
		return items;
	}

	public void paint(Graphics g, Dimension size ) {
		if (visibility.isVisible()){
//			transform.paint( g );
			for (Geometry item : items){
				if (item.visibility.isVisible()){
					setOpacity( opacity, g );
//					item.transform.paint( g );
					item.paint( g, size );
				}
			}
		} else {
			/* Set to invisible */
		}
	}

}