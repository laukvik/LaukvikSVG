package org.laukvik.svg.swing;

import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Group;
import org.laukvik.svg.shape.SVG;

public class SVGTreeModel implements TreeModel {
	
	private Vector<TreeModelListener> listeners;
	private SVG svg;
	
	public SVGTreeModel( SVG svg ){
		this.listeners = new Vector<TreeModelListener>();
		this.svg = svg;
	}
	
	public SVGTreeModel(){
		this.listeners = new Vector<TreeModelListener>();
	}
	
	public void setSvg(SVG svg) {
		this.svg = svg;
	}
	
	public SVG getSvg() {
		return svg;
	}

	public void addTreeModelListener(TreeModelListener l) {
		listeners.add( l );
	}

	public void removeTreeModelListener(TreeModelListener l) {
		listeners.remove( l );
	}
	
	public Vector <Geometry> list( Group group ){
		Vector <Geometry> items = new Vector<Geometry>();
		for (Geometry g : group.getItems()){
			items.add( 0, g );
		}
		return items;
	}
	
	public Object getChild( Object parent, int index ) {
		Group group = (Group) parent;
		return list( group ).get(index);
	}

	public int getChildCount(Object parent) {
		if (parent instanceof Group){
			Group group = (Group) parent;
			return list( group ).size();
		} else {
			return 0;
		}
	}

	public int getIndexOfChild(Object parent, Object child) {
		Group group = (Group) parent;
		return list( group ).indexOf( child );
	}

	public Object getRoot() {
		return svg;
	}

	public boolean isLeaf(Object node) {
		return !(node instanceof Group);
	}


	public void valueForPathChanged(TreePath path, Object newValue) {
		for (TreeModelListener l : listeners){
			l.treeStructureChanged( new TreeModelEvent( this, path ) );
		}
	}

}