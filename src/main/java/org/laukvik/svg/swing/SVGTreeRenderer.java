package org.laukvik.svg.swing;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.laukvik.svg.SVGSource;
import org.laukvik.svg.shape.Group;
import org.laukvik.svg.shape.HasFill;
import org.laukvik.svg.shape.Image;
import org.laukvik.svg.shape.Polygon;
import org.laukvik.svg.shape.Text;

public class SVGTreeRenderer extends DefaultTreeCellRenderer{

	private static final long serialVersionUID = 1L;

	public SVGTreeRenderer(){
		super();
	}
	
	public Component getTreeCellRendererComponent( JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row,	boolean hasFocus) {
		super.getTreeCellRendererComponent( tree, value, sel, expanded, leaf, row, hasFocus);
		if (value instanceof Group){
			Group group = (Group) value;
			setText( value.getClass().getSimpleName() + " (" +  group.getItems().size() + ")" );
			
		} else if (value instanceof HasFill){
			HasFill filler = (HasFill) value;
			setForeground( filler.getFill().color.getValue() );
			setText( value.getClass().getSimpleName() + "" );
			
		} else {
			setText( value.getClass().getSimpleName() );
		}
		if (value instanceof Polygon){
			Polygon poly = (Polygon) value;
			setText( getText() + " (" + poly.length() + ")" );
		} else if (value instanceof Image){
			Image image = (Image) value;
			SVGSource s = new SVGSource( image.href );
			
			setText( s.getFilename() + " (" + image.width.intValue() + "x" + image.height.intValue() + ")" );
		} else if (value instanceof Text){
			Text text = (Text) value;
			setText( text.text );
		}
		return this;
	}

}