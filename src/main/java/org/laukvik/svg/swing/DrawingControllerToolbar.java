package org.laukvik.svg.swing;

import javax.swing.JLabel;
import javax.swing.JToolBar;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Group;
import org.laukvik.svg.shape.SVG;
import org.laukvik.svg.swing.actions.CircleAction;
import org.laukvik.svg.swing.actions.ColorAction;
import org.laukvik.svg.swing.actions.DrawAction;
import org.laukvik.svg.swing.actions.GroupAction;
import org.laukvik.svg.swing.actions.LineAction;
import org.laukvik.svg.swing.actions.MoveAction;
import org.laukvik.svg.swing.actions.PolygonAction;
import org.laukvik.svg.swing.actions.RectangleAction;
import org.laukvik.svg.swing.actions.RotateCcwAction;
import org.laukvik.svg.swing.actions.RotateCwAction;
import org.laukvik.svg.swing.actions.SVGAction;
import org.laukvik.svg.swing.actions.SelectAction;
import org.laukvik.svg.swing.actions.TextAction;
import org.laukvik.svg.swing.actions.TransformAction;
import org.laukvik.svg.swing.actions.UnGroupAction;
import org.laukvik.svg.swing.controllers.CircleController;
import org.laukvik.svg.swing.controllers.DrawingController;
import org.laukvik.svg.swing.controllers.FreedrawController;
import org.laukvik.svg.swing.controllers.LineController;
import org.laukvik.svg.swing.controllers.MoveController;
import org.laukvik.svg.swing.controllers.PolygonController;
import org.laukvik.svg.swing.controllers.RectangleController;
import org.laukvik.svg.swing.controllers.SelectController;
import org.laukvik.svg.swing.controllers.TextController;
import org.laukvik.svg.swing.controllers.TransformController;
import org.laukvik.svg.swing.editors.fill.FillComboBox;
import org.laukvik.svg.swing.editors.stroke.StrokeComboBox;

public class DrawingControllerToolbar extends JToolBar implements SVGChangeListener, SVGControllerListener, SVGLoadListener, SVGSelectionListener{

	private static final long serialVersionUID = 1L;
	ColorButton color;
	DrawingControllerToolbarButton select, transform, rectangle, circle, polygon, text, move, line, draw;
	SVGAction colorAction, selectAction, transformAction, rectangleAction, circleAction, polygonAction, textAction, moveAction, lineAction, drawAction;
	
	DrawingControllerToolbarButton rotateCW, rotateCCW, group, ungroup;
	SVGAction rotateCwAction, rotateCcwAction, groupAction, unGroupAction;
	
	StrokeComboBox strokeComboBox;
	FillComboBox fillComboBox;
	
	SVGEditablePanel panel;

	public DrawingControllerToolbar(){
		super();
		initComponents();
	}
	
	public void log( Object msg ) {
		System.out.println( DrawingControllerToolbar.class.getName() + ": " + msg );
	}
	
	public void initComponents(){
		colorAction = new ColorAction();
		selectAction = new SelectAction();
		transformAction = new TransformAction();
		rectangleAction = new RectangleAction();
		circleAction = new CircleAction();
		polygonAction = new PolygonAction();
		textAction = new TextAction();
		moveAction = new MoveAction();
		lineAction = new LineAction();
		drawAction = new DrawAction();
		groupAction = new GroupAction();
		unGroupAction = new UnGroupAction();
		
		color = new ColorButton( colorAction ); 
		select = new DrawingControllerToolbarButton( selectAction );
		transform = new DrawingControllerToolbarButton( transformAction );
		rectangle = new DrawingControllerToolbarButton( rectangleAction );
		circle = new DrawingControllerToolbarButton( circleAction );
		polygon = new DrawingControllerToolbarButton( polygonAction );
		text = new DrawingControllerToolbarButton( textAction );
		move = new DrawingControllerToolbarButton( moveAction );
		line = new DrawingControllerToolbarButton( lineAction );
		draw = new DrawingControllerToolbarButton( drawAction );
		group = new DrawingControllerToolbarButton( groupAction );
		ungroup = new DrawingControllerToolbarButton( unGroupAction );
		
		add( color );
		
		addSeparator();
		
		add( select );
		
		add( line );
		add( rectangle );
		add( circle );
		add( polygon );
		add( draw );
		add( text );
		add( transform );
		add( move );
		
		
		addSeparator();
		
		rotateCwAction = new RotateCwAction();
		rotateCcwAction = new RotateCcwAction();
		rotateCW = new DrawingControllerToolbarButton( rotateCwAction );
		rotateCCW = new DrawingControllerToolbarButton( rotateCcwAction );
		
		add( rotateCW );
		add( rotateCCW );
		
		addSeparator();
		
		add( group );
		add( ungroup );
		
		addSeparator();
		
		strokeComboBox = new StrokeComboBox();
		fillComboBox = new FillComboBox();

		add( new JLabel("Fill") );
		add( fillComboBox );
		
		add( new JLabel("Stroke") );
		add( strokeComboBox );
	}

	public void svgChanged(Geometry geometry) {
	}

	public void svgDeselected() {
	}

	public void svgLoaded(SVGEditablePanel panel) {
		this.panel = panel;
		strokeComboBox.setSvgPanel( panel );
		fillComboBox.setSvgPanel( panel );
		colorAction.setSvgPanel( panel );
		color.setSvgPanel( panel );
		selectAction.setSvgPanel( panel );
		transformAction.setSvgPanel( panel );
		rectangleAction.setSvgPanel( panel );
		circleAction.setSvgPanel( panel );
		polygonAction.setSvgPanel( panel );
		textAction.setSvgPanel( panel );
		moveAction.setSvgPanel( panel );
		lineAction.setSvgPanel( panel );
		drawAction.setSvgPanel( panel );
		
		groupAction.setSvgPanel( panel );
		unGroupAction.setSvgPanel( panel );
	}

	public void svgUnLoaded(SVGEditablePanel panel) {
	}

	public void svgControllerChanged( DrawingController controller ) {
		log( "svgControllerChanged" );
		if (!(controller instanceof SelectController)){
			panel.setSelectionRectangle( null );
			panel.repaint();
		}
		select.setSelected( (controller instanceof SelectController) );
		transform.setSelected( (controller instanceof TransformController) );
		rectangle.setSelected( (controller instanceof RectangleController) );
		circle.setSelected( (controller instanceof CircleController) );
		polygon.setSelected( (controller instanceof PolygonController) );
		text.setSelected( (controller instanceof TextController) );
		move.setSelected( (controller instanceof MoveController) );	
		line.setSelected( (controller instanceof LineController) );	
		draw.setSelected( (controller instanceof FreedrawController) );	
	}

	public void svgMovedBack(Geometry geometry, int fromIndex) {
	}

	public void svgMovedFront(Geometry geometry, int fromIndex) {
	}

	public void selectionAdded(Geometry... geometry) {
	}

	public void selectionChanged(Geometry... geometry) {
		if (geometry == null || geometry.length == 0){
			group.setEnabled( false ); 
			ungroup.setEnabled( false );
		} else if (geometry.length == 1){
			ungroup.setEnabled( (geometry[0] instanceof Group && (geometry[0] instanceof SVG) == false) );
			group.setEnabled( false );
		} else {
			group.setEnabled( true );
			ungroup.setEnabled( false );
		}
	}

	public void selectionCleared() {
		group.setEnabled( false ); 
		ungroup.setEnabled( false ); 
	}

	public void selectionRemoved(Geometry... geometry) {
	}

	public void svgAdded(Geometry... geometry) {
		// TODO Auto-generated method stub
		
	}

	public void svgRemoved(Geometry... geometry) {
		// TODO Auto-generated method stub
		
	}
	
}