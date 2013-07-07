package org.laukvik.svg.swing;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.laukvik.svg.SVGSource;
import org.laukvik.svg.parser.ParseListener;
import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Group;

public class Viewer extends JFrame implements ParseListener, HyperlinkListener {

	private static final long serialVersionUID = 1L;
	SVGPanel panel;
	JScrollPane scroll;

	public Viewer(){
		setSize( 400, 400 );
		panel = new SVGPanel();
		panel.addParseListener( this );
		panel.addHyperlinkListener( this );
		JScrollPane scroll = new JScrollPane(panel);
		scroll.setBorder( null );
		setLayout( new BorderLayout() );
		add( scroll ); 
		setVisible( true );
	}

	public void loadSVG( String url2 ) throws Exception{
		panel.loadSVG( new SVGSource( url2 ) );
	}
	
	public static void main(String[] args) throws Exception {
		Viewer viewer = new Viewer();
//		viewer.loadSVG("java:///org/laukvik/svg/about.svg");
//		viewer.loadSVG("java:///org/laukvik/trainer/svg/pectoralis_major.svg");
		//viewer.loadSVG("java:///org/laukvik/trainer/svg/deltoideus_m.svg");
		viewer.loadSVG("http://www.carto.net/papers/svg/eu/oecd_map.svg");
//		viewer.loadSVG("http://upload.wikimedia.org/wikipedia/commons/d/d1/Europe-fr.svg");
//		viewer.loadSVG("http://upload.wikimedia.org/wikipedia/commons/1/16/Norway_counties.svg");
//		viewer.loadSVG("http://upload.wikimedia.org/wikipedia/commons/3/35/Tux.svg");
//		viewer.loadSVG("/Users/morten/external_link.svg");
//		viewer.loadSVG("http://boinc.berkeley.edu/w/images/2/2c/Broom_icon.svg");
		
		
	}

	public void parseComplete() {
//		panel.setSizeFromSVG();
		setSize( panel.getSize() );
	}

	public void parseFailed(Exception e, SVGSource source) {
	}

	public void parseStarting() {
	}

	public void valueChanged(int value) {
	}

	public void valueChanged(String value) {
	}

	public void clicked( SVGSource source) {
		System.out.println( "Clicked on " + source );
		panel.loadSVG( source );
	}

}