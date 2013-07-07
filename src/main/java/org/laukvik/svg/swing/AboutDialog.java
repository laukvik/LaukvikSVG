package org.laukvik.svg.swing;

import java.awt.Dimension;

import javax.swing.JDialog;

import org.laukvik.svg.SVGSource;

public class AboutDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public AboutDialog(){
		add( new SVGPanel( new SVGSource( "java:///org/laukvik/svg/about.svg" ) ) );
		setAlwaysOnTop( true );
		setSize( new Dimension(500,300) );
		setResizable( false );
		setModal( true );
	}
	
}