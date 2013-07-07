package org.laukvik.svg.swing;

import javax.swing.JButton;
import javax.swing.JToolBar;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.SVG;
import org.laukvik.svg.swing.actions.AddGroupAction;
import org.laukvik.svg.swing.actions.DeleteLayerAction;
import org.laukvik.svg.swing.actions.LayerVisibilityAction;
import org.laukvik.svg.swing.actions.MoveDownAction;
import org.laukvik.svg.swing.actions.MoveUpAction;
import org.laukvik.svg.swing.actions.SVGAction;

public class LayerToolbar extends JToolBar implements SVGSelectionListener, SVGLoadListener{

	private static final long serialVersionUID = 1L;
	private JButton up, down, delete, visible, group;
	private SVGAction moveDownAction, moveUpAction, deleteAction, groupAction, visibilityAction;

	public LayerToolbar( Editor editor ) {
		super();
		setBorder( null );
		setFloatable( false );
		
		moveUpAction = new MoveUpAction();
		moveDownAction = new MoveDownAction();
		deleteAction = new DeleteLayerAction();
		groupAction = new AddGroupAction();
		visibilityAction = new LayerVisibilityAction();
		
		up = new LayerButton( moveUpAction );
		down = new LayerButton( moveDownAction );
		delete = new LayerButton( deleteAction );
		visible = new LayerButton( visibilityAction );
		group = new LayerButton( groupAction );
		
		add( visible );
		add( up );
		add( down );
		addSeparator();
		add( group );
		add( delete );
	}
	
	public void log( Object message ){
//		System.out.println( LayerToolbar.class.getName() + ": " + message );
	}

	public void selectionCleared() {
		visible.setEnabled( false );
		up.setEnabled( false );
		down.setEnabled( false );
		delete.setEnabled( false );
		group.setEnabled( true );
	}

	public void selectionChanged(Geometry... geometri) {
		log( "svgSelected: " + geometri.length );
		if (geometri.length == 1){
			Geometry geometry = geometri[ 0 ];
			
			up.setEnabled( !(geometry instanceof SVG) );
			down.setEnabled( !(geometry instanceof SVG) );
			
		} else {
			up.setEnabled( false );
			down.setEnabled( false );
		}
		
		
		
		boolean hasSVG = false;
		for (Geometry g : geometri){
			if (g instanceof SVG){
				hasSVG = true;
			}
		}
		delete.setEnabled( !hasSVG );
		group.setEnabled( true );
		visible.setEnabled( !hasSVG );
		
	}

	public void svgLoaded(SVGEditablePanel panel) {
		moveUpAction.setSvgPanel( panel );
		moveDownAction.setSvgPanel( panel );
		deleteAction.setSvgPanel( panel );
		groupAction.setSvgPanel( panel );
		visibilityAction.setSvgPanel( panel );
	}

	public void svgUnLoaded(SVGEditablePanel panel) {
		moveUpAction.setSvgPanel( null );
		moveDownAction.setSvgPanel( null );
		deleteAction.setSvgPanel( null );
		groupAction.setSvgPanel( null );
		visibilityAction.setSvgPanel( null );
	}

	public void selectionAdded(Geometry... geometry) {
		// TODO Auto-generated method stub
		
	}

	public void selectionRemoved(Geometry... geometry) {
		// TODO Auto-generated method stub
		
	}
	
}