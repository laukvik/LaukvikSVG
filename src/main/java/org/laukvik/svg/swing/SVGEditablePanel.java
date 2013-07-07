package org.laukvik.svg.swing;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.util.Vector;

import org.laukvik.svg.SVGColor;
import org.laukvik.svg.SVGSource;
import org.laukvik.svg.SVGTransferable;
import org.laukvik.svg.shape.BasicShape;
import org.laukvik.svg.shape.Element;
import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Group;
import org.laukvik.svg.shape.SVG;
import org.laukvik.svg.stroke.DashArray;
import org.laukvik.svg.stroke.Stroke;
import org.laukvik.svg.swing.controllers.DrawingController;
import org.laukvik.svg.unit.Pixel;

public class SVGEditablePanel extends SVGPanel implements ClipboardOwner {

	private static final long serialVersionUID = 1L;

	private Vector <SVGChangeListener> changeListeners;
	private Vector <SVGSelectionListener> selectionListeners;
	private Vector <SVGControllerListener> controllerListeners;
	private Vector <SVGLoadListener> loadListeners;
	private DrawingController drawingController;
	
	private Vector <Geometry> selected;
	private PencilFeatures pencil;
	
	public SVGEditablePanel( SVG svg ){
		super( svg );
		initListeners();
	}
	
	public SVGEditablePanel(){
		super();
		initListeners();
	}
	
	public SVGEditablePanel( SVGSource source ){
		super( source );
		initListeners();
	}
	
	public void applyPencilFeatures( Geometry geometry ){
		if (geometry instanceof BasicShape){
			BasicShape bs = (BasicShape) geometry;
			bs.stroke = (Stroke) getPencil().stroke.clone();
			bs.stroke.color = (SVGColor) getPencil().getForeground().clone();
			bs.fill.color = (SVGColor) getPencil().getBackground().clone();
		}
	}
	
	public void initListeners(){
		changeListeners = new Vector<SVGChangeListener>();
		selectionListeners = new Vector<SVGSelectionListener>();
		controllerListeners = new Vector<SVGControllerListener>();
		loadListeners = new Vector<SVGLoadListener>();
		selected = new Vector<Geometry>();
		pencil = new PencilFeatures();
	}

	public void setSVG( SVG svg ) {
		fireSvgUnLoaded(); 
		super.setSVG(svg);
		fireSvgLoaded();
	}
	
	public PencilFeatures getPencil(){
		return pencil;
	}
	
	public Stroke [] listAvailableStrokes(){
		Stroke [] strokes = new Stroke [ 9 ];
		
		strokes[ 0 ] = new Stroke();
		strokes[ 0 ].width = new Pixel( 1 );
		strokes[ 0 ].dashArray = new DashArray( "" );
		
		strokes[ 1 ] = new Stroke();
		strokes[ 1 ].width = new Pixel( 1 );
		strokes[ 1 ].dashArray = new DashArray( "1" );
		
		strokes[ 2 ] = new Stroke();
		strokes[ 2 ].width = new Pixel( 1 );
		strokes[ 2 ].dashArray = new DashArray( "2" );
		
		strokes[ 3 ] = new Stroke();
		strokes[ 3 ].width = new Pixel( 1 );
		strokes[ 3 ].dashArray = new DashArray( "3" );
		
		strokes[ 4 ] = new Stroke();
		strokes[ 4 ].width = new Pixel( 1 );
		strokes[ 4 ].dashArray = new DashArray( "4" );
		
		strokes[ 5 ] = new Stroke();
		strokes[ 5 ].width = new Pixel( 1 );
		strokes[ 5 ].dashArray = new DashArray( "6" );
		
		strokes[ 6 ] = new Stroke();
		strokes[ 6 ].width = new Pixel( 1 );
		strokes[ 6 ].dashArray = new DashArray( "10" );
		
		strokes[ 7 ] = new Stroke();
		strokes[ 7 ].width = new Pixel( 1 );
		strokes[ 7 ].dashArray = new DashArray( "15" );
		
		strokes[ 8 ] = new Stroke();
		strokes[ 8 ].width = new Pixel( 1 );
		strokes[ 8 ].dashArray = new DashArray( "20" );

		return strokes;
	}
	
	public void setDrawingController(DrawingController drawingController) {
		/* Remove old controllers */
		if (this.drawingController != null){
			this.drawingController.setSvgPanel( null );
			removeMouseListener( this.drawingController );
			removeMouseMotionListener( this.drawingController );
		}

		/* Set new controller */
		this.drawingController = drawingController;
		/* Add mouse listeners for new controller */
		addMouseListener( this.drawingController );
		addMouseMotionListener( this.drawingController );
		this.drawingController.setSvgPanel( this );
		/* Fire changes in controller */
		fireSvgControllerChanged();
	}
	
	public DrawingController getDrawingController() {
		return drawingController;
	}
	
	public void delete(){
		if (getSelected() != null){
			Geometry removedGeometry = getSelected();
			svg.remove( removedGeometry );
			fireSvgRemoved( removedGeometry );
		}
	}

	public void cut() {
		if (getSelected() != null){
			copy();
			delete();
		}
	}

	public void copy() {
		if (getSelected() != null){
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents( new SVGTransferable( getSelected() ), this );	
		}
	}

	public void lostOwnership(Clipboard clipboard, Transferable contents) {
	}

	public void paste() {
		if (getSelected() == null){
			 /* Can't paste when nothing is selected */
		} else {
			if (Toolkit.getDefaultToolkit().getSystemClipboard().isDataFlavorAvailable( SVGTransferable.SVG_FLAVOR )){
				try {
					Element el = (Element) Toolkit.getDefaultToolkit().getSystemClipboard().getData( SVGTransferable.SVG_FLAVOR );
					log( "Pasting: " + el );
					Group group = getSelected().getGroup();
					fireSvgAdded( group.add( (Geometry) el  ) );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		}
	}
	



	
	/*  CONTROLLER LISTENERS **/

	public void addControllerListener( SVGControllerListener listener ){
		log( "addControllerListener: " + listener  );
		controllerListeners.add( listener );
	}
	
	public void removeControllerListener( SVGControllerListener listener ){
		log( "removeControllerListener: " + listener  );
		controllerListeners.remove( listener );
	}
	
	public void fireSvgControllerChanged(){
		log( "fireSvgControllerChanged: "  );
		for (SVGControllerListener l : controllerListeners){
			l.svgControllerChanged( drawingController );
		}
	}
	
	/*  CHANGE LISTENERS */
	
	public void addChangeListener( SVGChangeListener listener ){
		log( "addChangeListener: " + listener  );
		changeListeners.add( listener );
	}
	
	public void removeChangeListener( SVGChangeListener listener ){
		log( "removeChangeListener: " + listener  );
		changeListeners.remove( listener );
	}
	
	public void fireSvgAdded( Geometry... geometry  ){
		log( "fireSvgAdded: " + geometry  );
		repaint();
		for (SVGChangeListener l : changeListeners){
			l.svgAdded( geometry );
		}
	}
	
	public void fireSvgChanged( Geometry geometry  ){
		log( "fireSvgChanged: " + geometry  );
		repaint();
		for (SVGChangeListener l : changeListeners){
			l.svgChanged( geometry );
		}
	}
	
	public void fireSvgRemoved( Geometry geometry  ){
		log( "fireSvgRemoved: " + geometry  );
		repaint();
		for (SVGChangeListener l : changeListeners){
			l.svgRemoved(geometry);
		}
	}
	
	public void fireSvgMovedBack( Geometry selected, int fromIndex ) {
		log( "fireSvgMovedBack: " + selected  );
		repaint();
		for (SVGChangeListener l : changeListeners){
			l.svgMovedBack( selected, fromIndex );
		}
	}

	public void fireSvgMovedFront( Geometry selected, int fromIndex ) {
		log( "fireSvgMovedFront: " + selected  );
		repaint();
		for (SVGChangeListener l : changeListeners){
			l.svgMovedFront( selected, fromIndex );
		}
	}
	
	/*  SELECTION LISTENERS */
	
	public void addSelectionListener( SVGSelectionListener listener ){
		log( "addSelectionListener: " + listener  );
		selectionListeners.add( listener );
	}
	
	public void removeSelectionListener( SVGSelectionListener listener ){
		log( "removeSelectionListener: " + listener  );
		selectionListeners.remove( listener );
	}
	
	public void clearSelection(){
		this.selected.removeAllElements();
		fireSelectionCleared();
		fireSelectionChanged();
	}
	
	public void addSelection( Geometry... geometry ){
		for (Geometry g: geometry){
			this.selected.add( g );
		}
		fireAddedSelection( geometry );
		fireSelectionChanged();
	}
	
	public void removeSelection( Geometry... geometry ){
		for (Geometry g: geometry){
			this.selected.remove( g );
		}
		fireRemovedSelection( geometry );
		fireSelectionChanged();
	}
	
	public void setSelected( Geometry... geometry ) {
		
		this.selected.removeAllElements();
		for (Geometry g: geometry){
			this.selected.add( g );
		}
		fireSelectionChanged();
	}
	
	public boolean isSelected( Geometry geometry ){
		return selected.contains( geometry );
	}
	
	/** 
	 * @deprecated
	 * 
	 * TODO - REMOVE THIS
	 *  */
	public Geometry getSelected(){
		if (selected.size() == 0){
			return null;
		} else {
			return selected.get( 0 );
		}
	}
	
	public Geometry [] getSelection(){
		Geometry [] g = new Geometry[ selected.size() ];
		return this.selected.toArray( g );
	}
	
	private void fireAddedSelection( Geometry... geometry  ){
		log( "fireAddedSelection: " + geometry  );
		for (SVGSelectionListener l : selectionListeners){
			l.selectionAdded( geometry );
		}
	}
	
	private void fireRemovedSelection( Geometry... geometry  ){
		log( "fireRemovedSelection: " + geometry  );
		for (SVGSelectionListener l : selectionListeners){
			l.selectionRemoved( geometry );
		}
	}
	
	private void fireSelectionChanged(){
		Geometry [] selection = getSelection();
		log( "fireSelectionChanged: lenght="  + selection.length  );
		for (SVGSelectionListener l : selectionListeners){
			l.selectionChanged( selection );
		}
	}
	
	private void fireSelectionCleared(){
		log( "fireSelectionCleared: "  );
		for (SVGSelectionListener l : selectionListeners){
			l.selectionCleared();
		}
	}
	
	/* LOAD LISTENERS */
	
	public void addLoadListener( SVGLoadListener listener ){
		log( "addLoadListener: " + listener  );
		loadListeners.add( listener );
	}
	
	public void removeLoadListener( SVGLoadListener listener ){
		log( "removeLoadListener: " + listener  );
		loadListeners.remove( listener );
	}
	
	public void fireSvgLoaded(){
		log( "fireSvgLoaded: "  );
		for (SVGLoadListener l : loadListeners){
			l.svgLoaded( this );
		}
	}
	
	public void fireSvgUnLoaded(){
		log( "fireSvgUnLoaded: "  );
		for (SVGLoadListener l : loadListeners){
			l.svgUnLoaded( this );
		}
	}

	public void setCursor( org.laukvik.svg.Cursor cursor ) {
		setCursor( cursor.getCursor() );
	}
}