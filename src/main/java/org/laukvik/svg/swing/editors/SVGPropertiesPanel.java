package org.laukvik.svg.swing.editors;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.laukvik.svg.shape.Circle;
import org.laukvik.svg.shape.Ellipse;
import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Image;
import org.laukvik.svg.shape.Line;
import org.laukvik.svg.shape.Path;
import org.laukvik.svg.shape.Polygon;
import org.laukvik.svg.shape.Polyline;
import org.laukvik.svg.shape.Rectangle;
import org.laukvik.svg.shape.SVG;
import org.laukvik.svg.shape.Text;
import org.laukvik.svg.swing.Editor;
import org.laukvik.svg.swing.SVGSelectionListener;
import org.laukvik.svg.swing.editors.circle.CirclePropertyPanel;
import org.laukvik.svg.swing.editors.circle.EllipsePropertyPanel;
import org.laukvik.svg.swing.editors.fill.FillPropertyPanel;
import org.laukvik.svg.swing.editors.geometry.GeometryPropertyPanel;
import org.laukvik.svg.swing.editors.geometry.PropertyPanel;
import org.laukvik.svg.swing.editors.image.ImagePropertyPanel;
import org.laukvik.svg.swing.editors.line.LinePropertyPanel;
import org.laukvik.svg.swing.editors.path.PathPropertyPanel;
import org.laukvik.svg.swing.editors.polygon.PolygonPropertyPanel;
import org.laukvik.svg.swing.editors.polyline.PolylinePropertyPanel;
import org.laukvik.svg.swing.editors.rectangle.RectanglePropertyPanel;
import org.laukvik.svg.swing.editors.stroke.StrokePropertyPanel;
import org.laukvik.svg.swing.editors.text.TextPropertyPanel;
import org.laukvik.svg.swing.editors.transform.TransformPropertyPanel;

public class SVGPropertiesPanel extends JPanel implements SVGSelectionListener{

	private static final long serialVersionUID = 1L;
	
	/* GUI Stuff */
	private int rowWidth = 300;

	private SVGPropertyPanel svgPanel;
	private GeometryPropertyPanel geometryPanel;
	private StrokePropertyPanel strokePanel;
	private FillPropertyPanel fillPanel;
	private RectanglePropertyPanel rectPanel;
	private ImagePropertyPanel imagePanel;
	private TextPropertyPanel textPanel;
	private CirclePropertyPanel circlePanel;
	private EllipsePropertyPanel ellipsePanel;
	private PolygonPropertyPanel polygonPanel;
	private PolylinePropertyPanel polylinePanel;
	private LinePropertyPanel linePanel;
	private PathPropertyPanel pathPanel;
	private TransformPropertyPanel transformPanel;

	/**
	 * 
	 * 
	 * @param editorApp
	 */
	public SVGPropertiesPanel( Editor editorApp ){
		super();
		initComponent();
	}
	
	public void initComponent(){
		setBorder( null );
		setMaximumSize( new Dimension(rowWidth,10000) );
		setMinimumSize( new Dimension(rowWidth,0) );
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		
		svgPanel = new SVGPropertyPanel();
		circlePanel = new CirclePropertyPanel();
		ellipsePanel = new EllipsePropertyPanel();
		polygonPanel = new PolygonPropertyPanel();
		polylinePanel = new PolylinePropertyPanel();
		linePanel = new LinePropertyPanel();
		pathPanel = new PathPropertyPanel();
		rectPanel = new RectanglePropertyPanel();
		geometryPanel = new GeometryPropertyPanel();
        strokePanel = new StrokePropertyPanel();
		fillPanel = new FillPropertyPanel();
		imagePanel = new ImagePropertyPanel();
		textPanel = new TextPropertyPanel();
		transformPanel = new TransformPropertyPanel();
	}
	
	public void log( Object message ){
		System.out.println( SVGPropertiesPanel.class.getName()  + ": " + message );
	}
	
	public PropertyPanel [] listPanels( Geometry geometry ) {
		PropertyPanel[] panels = new PropertyPanel[ 0 ];
		if (geometry instanceof Rectangle){
			panels = listRectanglePanels();
			
		} else if (geometry instanceof Image){
			panels = listImagePanels();
			
		} else if (geometry instanceof Text){
			panels = listTextPanels();
			
		} else if (geometry instanceof Ellipse){
			panels = listEllipsePanels();
			
		} else if (geometry instanceof Circle){
			panels = listCirclePanels();
			
		} else if (geometry instanceof Polygon){
			panels = listPolygonPanels();
			
		} else if (geometry instanceof Polyline){
			panels = listPolylinePanels();
			
		} else if (geometry instanceof Line){
			panels = listLinePanels();
			
		} else if (geometry instanceof Path){
			panels = listPathPanels();
			
		} else if (geometry instanceof SVG){
			panels = listSVGPanels();
			
		} else {
			panels = listGeometryPanels();
		}
		return panels;
	}
	
	public PropertyPanel [] listSVGPanels() {
		return new PropertyPanel[] { svgPanel };
	}
	
	public PropertyPanel [] listGeometryPanels() {
		return new PropertyPanel[] { strokePanel, fillPanel, geometryPanel, transformPanel };
	}
	
	public PropertyPanel [] listPathPanels() {
		return new PropertyPanel[] { pathPanel, strokePanel, fillPanel, geometryPanel, transformPanel };
	}
	
	public PropertyPanel [] listLinePanels() {
		return new PropertyPanel[] { linePanel, strokePanel, fillPanel, geometryPanel, transformPanel };
	}
	
	public PropertyPanel [] listPolygonPanels() {
		return new PropertyPanel[] { polygonPanel, strokePanel, fillPanel, geometryPanel, transformPanel };
	}
	
	public PropertyPanel [] listPolylinePanels() {
		return new PropertyPanel[] { polylinePanel, strokePanel, fillPanel, geometryPanel, transformPanel };
	}
	
	public PropertyPanel [] listEllipsePanels() {
		return new PropertyPanel[] { ellipsePanel, strokePanel, fillPanel, geometryPanel, transformPanel };
	}
	
	public PropertyPanel [] listCirclePanels() {
		return new PropertyPanel[] { circlePanel, strokePanel, fillPanel, geometryPanel, transformPanel };
	}
	
	public PropertyPanel [] listTextPanels() {
		return new PropertyPanel[] { textPanel, strokePanel, fillPanel, geometryPanel, transformPanel };
	}
	
	public PropertyPanel [] listImagePanels() {
		return new PropertyPanel[] { imagePanel, geometryPanel, transformPanel };
	}

	public PropertyPanel [] listRectanglePanels() {
		return new PropertyPanel[] { rectPanel, strokePanel, fillPanel, geometryPanel, transformPanel };
	}
	
	public PropertyPanel [] listPanels() {
		return new PropertyPanel[] { geometryPanel, strokePanel, fillPanel, rectPanel, imagePanel, textPanel, circlePanel, polygonPanel,
				polylinePanel, ellipsePanel, linePanel, pathPanel, transformPanel, svgPanel };
	}

	public void selectionAdded(Geometry... geometry) {
	}

	public void selectionChanged(Geometry... geometrys) {
		PropertyPanel[] panels;
		if (geometrys.length == 1){
			panels = listPanels( geometrys[0] );
		} else {
			panels = new PropertyPanel[ 0 ];
		}
		setVisible( false );
		removeAll();
//		invalidate();

		for (PropertyPanel pp : panels){
			add( pp );
		}
//		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
//		getParent().invalidate();
		invalidate();
//		repaint();
		setVisible( true );
	}

	public void selectionCleared() {
	}

	public void selectionRemoved(Geometry... geometry) {
	}
	
}