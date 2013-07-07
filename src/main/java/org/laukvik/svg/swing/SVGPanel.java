package org.laukvik.svg.swing;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import org.laukvik.svg.SVGColor;
import org.laukvik.svg.SVGSource;
import org.laukvik.svg.parser.ParseListener;
import org.laukvik.svg.parser.Parser;
import org.laukvik.svg.shape.Ellipse;
import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Group;
import org.laukvik.svg.shape.Line;
import org.laukvik.svg.shape.SVG;
import org.laukvik.svg.shape.Text;
import org.laukvik.svg.unit.Percent;
import org.laukvik.svg.unit.Unit;

public class SVGPanel extends JComponent implements ParseListener, MouseListener, MouseMotionListener{
	
	private static final long serialVersionUID = 4528126115304515535L;
	private Rectangle selectionRectangle;
	SVG svg;
	BufferedImage image;
	SVGSource source;
	Geometry mouseOver;
	SVGPopupMenu popupMenu;
	Vector <ParseListener> parseListeners;
	Vector <HyperlinkListener> linkListeners;
	Timer timer;
	ScheduledTask task;
	SVG progressSVG;

	public SVGPanel(){
		super();
		initComponents();
		loadProgressSVG();
		parseListeners = new Vector<ParseListener>();
		linkListeners = new Vector<HyperlinkListener>();
	}
	
	public SVGPanel( SVG svg ){
		this();
		setSVG( svg );
	}
	
	public SVGPanel( SVGSource source ){
		this();
		loadSVG( source );
	}
	
	/**
	 * Loads the predefined progressbar
	 * 
	 */
	private void loadProgressSVG(){
		Parser parser = new Parser();
		try {
			parser.parse( new SVGSource( "/org/laukvik/svg/symbols/progress.svg", SVGSource.JAVA ) );
			progressSVG = parser.getSVG();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addHyperlinkListener( HyperlinkListener listener ){
		this.linkListeners.add( listener );
	}
	
	public void removeHyperlinkListener( HyperlinkListener listener ){
		this.linkListeners.remove( listener );
	}
	
	public void fireHyperlinkListener( SVGSource source ){
		log( "fireHyperlinkListener: " + linkListeners.size() + " source: " + source.toExternalForm()  );
		for (HyperlinkListener l : linkListeners){
			l.clicked( source );
		}
	}
	
	public void addParseListener( ParseListener listener ){
		this.parseListeners.add( listener );
	}
	
	public void removeParseListener( ParseListener listener ){
		this.parseListeners.remove( listener );
	}
	
	public void save( String format, File file ) throws IOException{
		int width = getWidth();
		int height = getHeight();
		BufferedImage image = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
		Graphics2D g2 = image.createGraphics();
		g2.setBackground( Color.WHITE );
		g2.clearRect(0, 0, width, height);
		

		paint(g2);
		g2.dispose();
		ImageIO.write( image, format, file );
	}
	
	/*
	 * <?xml version="1.0" encoding="ISO-8859-1"?> <svg height="3.4028235E38px"
	 * width="3.4028235E38px" xmlns="http://www.w3.org/2000/svg">
	 * <title>Untitled</title> <ellipse class="" cx="27.0px" cy="27.0px"
	 * fill="rgb(0,0,0)" fill-opacity="0.0" id="" opacity="1.0" rx="124.0px"
	 * stroke="rgb(255,0,51)" stroke-dasharray="" stroke-dashoffset="0.0px"
	 * stroke-linecap="butt" stroke-opacity="1.0" stroke-width="5.0px"/> <line
	 * class="" fill="rgb(0,0,0)" fill-opacity="1.0" id="" opacity="1.0"
	 * stroke="rgb(255,0,0)" stroke-dasharray="" stroke-dashoffset="0.0px"
	 * stroke-linecap="butt" stroke-opacity="1.0" stroke-width="5.0px"
	 * x="293.0px" x2="335.0px" y="142.0px" y2="106.0px"/> </svg>
	 * 
	 */
	public SVG notFoundSymbol(){
		SVG svg = new SVG();
		
		Ellipse ellipse = new Ellipse( new Percent(50), new Percent(50), new Percent(50), new Percent(50) );
		ellipse.fill.color = SVGColor.red;
		svg.add( ellipse );
		
		Line line = new Line();
		svg.add( line );
		return svg;
	}
	
	public void initComponents(){
		setLayout( null );
		popupMenu = new SVGPopupMenu( this );
		add( popupMenu );	
		this.image = getOneChecker();
	}
	
	public final static BufferedImage getOneChecker(){
		Color dark = new Color( 200, 200, 200 );
		Color brite = Color.white;
		
		BufferedImage checker = new BufferedImage( 20, 20, BufferedImage.TYPE_INT_RGB );
		Graphics g = checker.getGraphics();
		g.setColor( brite );
		g.fillRect( 0,0,20,20 );
		g.setColor( dark );
		g.fillRect( 0,0,10,10 );
		g.setColor( dark );
		g.fillRect( 10,10,20,20 );
		return checker;
	}

	public void loadSVG( SVGSource source ){
		log( "loadSVG: " + source );
		this.source = source;
		Parser parser = new Parser();
		parser.addParseListener( this );
		try {
			parser.parse( source );
			setSVG( parser.getSVG() );
		} catch (Throwable e) {
			log( "loadSVG failed " + e );
			setSVG( new SVG() ); 
		}
		parser.removeParseListener( this );
	}
	
	public void setSVG( SVG svg ){
		this.mouseOver = null;
		removeMouseListener( this );
		this.svg = svg;
		if (svg == null){
			removeMouseMotionListener( this );
		} else {
			svg.setComponent( this );
			svgSizeChanged();
			repaint();
			addMouseMotionListener( this );
		}
		addMouseListener( this );
	}
	
	public void svgSizeChanged(){
		setSizeFromSVG();
	}
	
//	public void paintProgress( Graphics g, int percent ){
//		
//		g.clearRect( 0,0, getSize().width, getSize().height );
//		
//		Ellipse circle = (Ellipse)progressSVG.getElementById("progress");
//		circle.stroke.dashOffset = new Unit( circle.stroke.dashOffset.intValue() + 1 );
//		progressSVG.paint( g, getSize() );
//		
////		g.clearRect( 0,0, 400, 400 );
////		g.drawString("Loading " + percent + "% " + statusCounter, 20, 20 );
//	}
	
	public void paintError( Graphics g, Exception e, SVGSource source ){
		g.setFont( new Font( getFont().getName(), Font.PLAIN, 9 ) );
		String s;
		if (getLocale().getCountry().equalsIgnoreCase("no")){
			if (source == null){
				s = "Kunne ikke vise SVG";
			} else {
				s = "Klarte ikke Œ lese " + source.toExternalForm();
			}
		} else {
			if (source == null){
				s = "Could not load SVG";
			} else {
				s = "Could not load " + source.toExternalForm();
			}
		}
		s = e.getMessage();
		
		int stringWidth = g.getFontMetrics().stringWidth( s );
		int x = (getWidth() - stringWidth) /  2;
		int y = getHeight() / 2;
		g.drawString( s, x, y );
	}
	
	
	public void paint(Graphics g) {
		if (isAlreadyRunning){
			Ellipse circle = (Ellipse)progressSVG.getElementById("progress");
			circle.stroke.dashOffset = new Unit( circle.stroke.dashOffset.intValue() + 1 );
			progressSVG.paint( g, getSize() );
		} else if (svg == null){
			super.paint(g);
		} else {
//			setSizeFromSVG();
//			g.clipRect( 0,0, getWidth(), getHeight() );
			
			Dimension size = getCanvasSize();
//			g.setClip( null );
//			CheckerBoardFilter.paint(g, size, image );
			
//			g.clearRect( getWidth(), getHeight(), getWidth()-size.width, getHeight()-size.height );
			
			
			
			g.clipRect( 0,0, size.width, size.height );
			
//			CheckerBoardFilter.paint(g, size, image );
			svg.paint( g, size );		
			/* Paint Components */
			for (Text text : svg.inputs){
				text.component.setBounds( text.componentArea );
			}
			paintComponents(g);
			
			if (selectionRectangle != null){
				paintSelectionRectangle(g);
			}

		}
	}
	
	public void paintSelectionRectangle(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor( Color.BLACK );
		g2.setStroke( new BasicStroke(1) );
		g2.draw( selectionRectangle );
		g2.setComposite( AlphaComposite.getInstance( AlphaComposite.DST_ATOP, 0.7f ) );
		g2.fill( selectionRectangle );
	}
	
	public void setSizeFromSVG(){
		Dimension size = getCanvasSize();
		if (svg.width.isInfinity() && svg.height.isInfinity()){
			setPreferredSize( new Dimension(1,1) );
			setSize( size );
			
		} else if (svg.width.isInfinity()){
			setPreferredSize( new Dimension(1,size.height) );
			setSize( size );
			
		} else if (svg.height.isInfinity()){
			setPreferredSize( new Dimension(size.width,1) );
			setSize( size );
			
		} else {
			setPreferredSize( size );
			setSize( size );
			
		}

	}
	
	/**
	 * Calculate the paint area based on width/height and viewBox size
	 * 
	 * @return
	 */
	public Dimension getCanvasSize(){

		int w = 0;
		int h = 0;
		
		Container container = getParent();
//		System.out.println( container );
		
		if (svg.width.isInfinity()){
			/* Use absolute sizes */
			w = container.getBounds().width;
		} else if (svg.width.isPercent()) {
			/* Use percentage of area */
			w = svg.width.getPercent( container.getBounds().width );
		} else if (svg.width.isPX()) {
			/* Use limited size in pixels */
			w = svg.width.getPixels();
		} else {
			/* All other combinations use whole canvas */
			w = container.getBounds().width;
		}
		
		if (svg.height.isInfinity()){
			/* Use absolute sizes */
			h = container.getBounds().height;
		} else if (svg.height.isPercent()) {
			/* Use percentage of area */
			h = svg.height.getPercent( container.getBounds().height );
		} else if (svg.height.isPX()) {
			/* Use limited size in pixels */
			h = svg.height.getPixels();
		} else {
			/* All other combinations use whole canvas */
			h = container.getBounds().height;
		}

		return new Dimension( w, h );
	}
	
	public SVG getSVG() {
		return svg;
	}
	
	public void handleViewSource() {
		new SVGSourceDialog( getSVG() );
	}
	
	public void fireNoMouseOver(){
		if (this.mouseOver != null){
			this.mouseOver.fireMouseOut();
		}
		setCursor( Cursor.getDefaultCursor() );
	}
	
	public void setMouseOver(Geometry mouseOver) {
		if (this.mouseOver != null){
			this.mouseOver.fireMouseOut();
		}
		this.mouseOver = mouseOver;
		this.mouseOver.fireMouseOver();

//		setCursor( mouseOver.cursor.getCursor() );
		setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
	}
	
	public Geometry getMouseOver() {
		return mouseOver;
	}
	
	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved( MouseEvent e ) {
		Geometry match = null;
//		for (Geometry g : svg.getItems()){
//			if (g.getShape() != null && g.getShape().contains( e.getPoint() )){
//				match = g;
//			}
//		}
//		svg.listItems( e.getPoint() );
		
		Vector<Geometry> matches = svg.listItems( e.getPoint() );
		if (matches.size() > 0){
			match =  matches.get( 0 );
		}
		
		
		if (match == null){
			fireNoMouseOver();
		} else {
			setMouseOver( match );
//			log( "Mouseover: " + match );
		}
	}

	public Rectangle getSelectionRectangle() {
		return selectionRectangle;
	}

	public void setSelectionRectangle(Rectangle selectionRectangle) {
		this.selectionRectangle = selectionRectangle;
	}

	public void log( Object message ){
//		System.out.println( this.getClass().getName() + ": " + message );
	}

	/* EVENT LISTENERS */
	public void parseComplete() {
		log( "Parse done!"  );
		repaint();
		getRootPane().setCursor( Cursor.getDefaultCursor() );
		for (ParseListener l : parseListeners){
			l.parseComplete();
		}
//		stopTimer();
	}

	public void parseFailed( Exception e, SVGSource source ) {
		log( "Parse failed!"  );
		e.printStackTrace();
		paintError( getGraphics(), e, source );
		getRootPane().setCursor( Cursor.getDefaultCursor() );
		for (ParseListener l : parseListeners){
			l.parseFailed(e, source);
		}
		stopTimer();
	}

	public void parseStarting() {
		getRootPane().setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
		log( "Parsing..." );
//		paintProgress( getGraphics(), 0 );
		startTimer();
		for (ParseListener l : parseListeners){
			l.parseStarting();
		}
		
	}
	
	public void stopTimer(){
		log( "Stopping timer" );
//		if (timer != null){
//			timer.cancel();
//		}
//		if (task != null){
//			task.cancel();	
//		}
	}
	
	public void startTimer(){
		int periodMilliseconds = 50;
		log( "Starting timer with update interval: " + periodMilliseconds);
		statusCounter = 0;
		timer = new Timer();
	    timer.scheduleAtFixedRate( new ScheduledTask(), 0, periodMilliseconds ); //subsequent rate
	}

	public void valueChanged(int value) {
//		paintProgress( getGraphics(), value );
		repaint();
	}

	public void valueChanged(String value) {
	}
	
	boolean isAlreadyRunning = false;
	int statusCounter;

	
	private class ScheduledTask extends TimerTask {

		public void run() {
//			System.out.println( "ScheduledTask.run" );
			if (isAlreadyRunning){
				/* Already occupied by updating an existing task */
			} else {
				/* Not busy - do what you need to do */
//				paintProgress( getGraphics(), (int) (Math.random() * 100) );
				repaint();
				statusCounter++;
				isAlreadyRunning = false;
				
			}

		}

	}


	public void mouseClicked(MouseEvent e) {
		log("mouseClicked: " +  this.mouseOver );
		if (this.mouseOver == null){
			/* Nothing to be clicked */
		} else {
			if (this.mouseOver.getGroup() instanceof Group){
				Group g = (Group) this.mouseOver.getGroup();
				log("mouseClicked: href=" +  g.href );
				fireHyperlinkListener( new SVGSource( g.href ) );
			}
			
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {

	}
	
}