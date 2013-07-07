package org.laukvik.svg.shape;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import javax.swing.JComponent;

import org.laukvik.svg.SVGColor;
import org.laukvik.svg.SVGSource;
import org.laukvik.svg.ViewBox;
import org.laukvik.svg.defs.Definition;
import org.laukvik.svg.unit.Unit;

/**
 * 
 * 
 * 
 * @author morten
 * @see http://www.w3.org/TR/SVG
 *
 */
public class SVG extends Group{
	
	public final static int PIXELS_PR_CM = 32;
	
	public Unit width;
	public Unit height;
	public ViewBox viewBox = new ViewBox();
	private String title = "Untitled";
	
	private Dimension renderSize = new Dimension(0,0);
	public SVGSource source;
	private Vector<Definition> definitions = new Vector<Definition>();
	private JComponent component;
	/**/
//	public JComponent draw;
//	private java.awt.Rectangle rect;
//	private HashMap<SVGID, ?> components = new HashMap();
	/**/
	public Vector <Text> inputs = new Vector<Text>();

	public SVG(){
		super();
		this.width  = Unit.INFINITY;
		this.height = Unit.INFINITY;
		this.group = null;
		this.items = new Vector<Geometry>();
		this.fill.color = SVGColor.black;
	}
	
	public void addInput( Text text ){
		if (text.component != null){
			this.component.add( text.component );
			inputs.add( text );	
		}
	}
	
	public void clearInput(){
//		this.component.removeAll();
//		inputs.removeAllElements();
	}
	
	public JComponent getComponent() {
		return component;
	}
	
	public void setComponent(JComponent component) {
		this.component = component;
	}
	
	public void addDefinition( Definition... definition ){
		for (Definition d : definition){
			this.definitions.add( d );
		}
	}
	
	public void removeDefinition( Definition definition ){
		this.definitions.remove( definition );
	}
	
	public Vector<Definition> getDefinitions() {
		return definitions;
	}
	
	/**
	 * Returns the definition with the specified ID 
	 * 
	 * @param id
	 * @return
	 */
	public Definition getDefinitionById( String id ){
		for (Definition d : definitions){
//			System.out.println( "Definition: " +  d.id + " " + id  );
			if (d.id.isID(id)){
				return d;
			}
		}
		return null;
	}

	public Definition getDefinitionById(SVGID definitionID ) {
		return getDefinitionById( definitionID.getValue() );
	}
	
	public Element getElementById( String id ){
		return getElementById( this, id );
	}
	
	private Element getElementById( Group group, String id ){
		if (group.id.isID( id )){
			return group;
		}
		for (Geometry g : group.getItems()){
			if (g.id.isID( id )){
				return g;
			}
			if (g instanceof Group){
				Group sub = (Group) g;
				return getElementById( sub, id);
			}
		}
		return null;
	}
	
//	public void paint( JComponent component, Dimension size ){
//		this.component = component;
//		paint( component.getGraphics(), size );
//		this.component.paintComponents( component.getGraphics() );
//	}

	public void paint( Graphics g, Dimension size ) {
		clearInput();
//		log( "Painting canvas: " + size.width + "x" + size.height );
		this.renderSize = size;
//		g.setColor( fill );
//		g.clearRect( 0,0,size.width,size.height );
		/* Create a 2d object for scaling purposes */
		Graphics2D g2 = (Graphics2D) g;
		/* Setup anti-aliasing for high quality */
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		/* Calculate scaling factors */
		float scaleX = 1;
		float scaleY = 1;
		
		if (viewBox.w.isInfinity()){
			/* No viewbox is set */
			scaleX = 1;
		} else {
			scaleX = size.width / viewBox.w.getValue();
		}
		
		if (viewBox.h.isInfinity()){
			/* No viewbox is set */
			scaleY = 1;
		} else {
			scaleY = size.height / viewBox.h.getValue();
		}

//		log( "Scale: " + scaleX + " - " + scaleY );
		g2.scale( scaleX, scaleY );
		AffineTransform affine = new AffineTransform();
		affine.scale( scaleX, scaleY );
		affine.rotate( Math.toRadians( 0 ) );

		

		/* Setup the viewbox - the zoomable area */
//		log( "ViewBox: " + viewBox.x + " " + viewBox.y + "   " + viewBox.w + " " + viewBox.h );
		g.translate( -viewBox.x.getPixels(), -viewBox.y.getPixels() );
		
		
		// This makes it able to draw onto other componetns
		if (viewBox.w.isPX() && viewBox.h.isPX()){
//			g.setClip( viewBox.x.getPixels(), viewBox.y.getPixels(), viewBox.w.getPixels(), viewBox.h.getPixels() );
//			g.setClip( 0, 0, size.width, size.height ); 
		}	
		
		for (Geometry item : items){
//			g2.setTransform( affine ); // This makes the paint job to go mad!
			item.paint( g, size );
		}
	}
	
	public int toPixels( Unit unit, int max ){
		if (unit.isPX()){
			return (int) unit.getValue();
		}
		if (unit.isPercent()){
			return (int) ((max * unit.getValue()) / 100f);
		} else {
			return (int) unit.getValue();
		}
	}
	
	public int toHorisontalPixels( Unit unit ){
		return toPixels( unit, renderSize.width );
	}
	
	public int toVerticalPixels( Unit unit ){
		return toPixels( unit, renderSize.height );
	}
	
	public int toPixels( Unit unit ){
		if (unit.isPX()){
			return (int) unit.getValue();
			
		} else if (unit.isCM()){
			return (int) (unit.getValue() * PIXELS_PR_CM);
			
		} else if (unit.isPercent()){
			return (int) unit.getValue();
			
		} else {
			return (int) unit.getValue();
		}
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public Group createGroup( Geometry... geometrys ) {
		/* Create new group */
		Group group = new Group();
		/* Iterate through all items reversed so they end up in the right order*/
		for (int x=geometrys.length-1; x>-1; x--){
			Geometry g = geometrys[ x ];
			/* Remove from its parents */
			Group parent = g.getGroup();
			parent.remove( g );
			/* Add to new group */
			group.add( g );
		}
		add( group );
		return group;
	}

	public Geometry[] splitGroup( Group group ) {
		/* Get the groups parent */
		Group parent = group.getGroup();
		
		/* Create an array of members to avoid ConcurrentModificationException */
		Geometry [] members = new Geometry[ parent.getItems().size() ];
		parent.getItems().toArray( members );
		
		/* Remove items from existing group */
		group.items.removeAllElements();
		
		/**/

		for (Geometry g : members){
			/* Move'm into parent group */
			parent.items.add( g );
		}
		



		Geometry [] gems = new Geometry[ parent.getItems().size() ];
		parent.getItems().toArray( gems );
		return gems;
	}

}