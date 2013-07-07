package org.laukvik.svg.swing;

import java.util.Vector;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.laukvik.svg.shape.Element;
import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.SVG;

public class SVGTree extends JTree implements TreeSelectionListener, SVGChangeListener, SVGLoadListener {

	private static final long serialVersionUID = 1L;
	SVGEditablePanel panel;
	
	public SVGTree(){
		super();
//		setBackground( new Color(212,218,227) );
		setCellRenderer( new SVGTreeRenderer() );
		getSelectionModel().addTreeSelectionListener( this );
		getSelectionModel().setSelectionMode(  TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION );
		
	}
	
	public SVGTree( SVG svg ){
		this();
		SVGTreeModel model = new SVGTreeModel(svg);
		setModel( model );
	}
	
	public SVGTree( TreeModel model ){
		this();
		setModel( model );
	}
	
	public TreePath getPath( Geometry geometry ){
		return new TreePath( geometry.listPath() );
	}

	public void svgAdded( Geometry... geometrys ) {
//		for (Geometry g : geometrys){
//			Element [] os = g.listPath();
//			System.out.print( "Found path: " +  g.getTagName() + "=" + os.length + " objects= " );
//			for (Element o : os){
//				System.out.print( os + ", " );	
//			}
//			System.out.println();
//		}
//		setModel( new SVGTreeModel( panel.getSVG()) );
		
//		if (geometry.getGroup() == null){
//			TreePath path = new TreePath( new Object [] { geometry  } );
//
//			getModel().valueForPathChanged( path, geometry );
//		} else {
//			TreePath path = new TreePath( new Object [] { geometry.getGroup() } );
//
//			getModel().valueForPathChanged( path, geometry );
//			
//			TreePath path2 = new TreePath( new Object [] { geometry.getGroup(), geometry } );
//			setSelectionPath( path2 );
//		}
		setModel( new SVGTreeModel( panel.getSVG()) );
	}
	


	public void svgChanged(Geometry geometry) {
		treeDidChange();
	}

	public void svgRemoved(Geometry... geometry) {
		setModel( new SVGTreeModel( panel.getSVG()) );
	}
	
	public void log( Object message ){
//		System.out.println( SVGTree.class.getName()  + ": " + message );
	}

	public void valueChanged(TreeSelectionEvent e) {
		/* Clear all previously selected items */
		if (getSelectionCount() == 0){
			log( "Clearing all selected..." );
			panel.clearSelection();
		} else if (getSelectionCount() == 1){
			Geometry g = (Geometry) e.getNewLeadSelectionPath().getLastPathComponent();
			log( "Setting selection: " + g );
			panel.setSelected( g );
		} else if (e.isAddedPath()){
			/* Since there is no method of getting a list of objects which were selected
			 * in the TreeSelectionEvent - just get all items in the Tree which were 
			 * selected and set them as selected */
			Vector<Geometry> items = new Vector<Geometry>();
			for (TreePath tp : getSelectionPaths()){
				log( "added: " + tp.getLastPathComponent() );
				items.add( (Geometry) tp.getLastPathComponent() );
			}
			Geometry [] gems = new Geometry[ items.size() ];
			items.toArray( gems );
			panel.setSelected( gems );			
		} else {
			Geometry g = (Geometry) e.getOldLeadSelectionPath().getLastPathComponent();
			log( "Removing from selection: " + g );
			panel.removeSelection( g );
		}
	}

	public void svgLoaded(SVGEditablePanel panel) {
		this.panel = panel;
	}

	public void svgUnLoaded(SVGEditablePanel panel) {
		this.panel = null;
	}

	public void svgMovedBack(Geometry geometry, int fromIndex) {
		TreePath path = new TreePath( new Object [] { geometry.getGroup() } );
		getModel().valueForPathChanged( path, geometry );
		
		TreePath path2 = new TreePath( new Object [] { geometry.getGroup(), geometry } );
		setSelectionPath( path2 );
	}

	public void svgMovedFront(Geometry geometry, int fromIndex) {
		TreePath path = new TreePath( new Object [] { geometry.getGroup() } );
		getModel().valueForPathChanged( path, geometry );
		
		TreePath path2 = new TreePath( new Object [] { geometry.getGroup(), geometry } );
		setSelectionPath( path2 );
	}

}