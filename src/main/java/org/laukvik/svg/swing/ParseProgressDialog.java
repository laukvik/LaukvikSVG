package org.laukvik.svg.swing;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JProgressBar;

import org.laukvik.svg.SVGSource;
import org.laukvik.svg.parser.ParseListener;

public class ParseProgressDialog extends Dialog implements ParseListener {

	private static final long serialVersionUID = 1L;
	private JProgressBar bar;
	private Dimension size = new Dimension( 200, 25 );

	public ParseProgressDialog( Frame owner, String title ){
		super( owner, title, true );
		setLayout( new FlowLayout() );
		setSize( 220, 40 );
		setResizable( false );
		setUndecorated( true );
		
		bar = new JProgressBar( 0, 100 );
		bar.setMinimumSize( size );
		bar.setPreferredSize( size );
		bar.setMaximumSize( size );
		bar.setIndeterminate( true );
		bar.setStringPainted( true );
		bar.setString( title );
		add( bar );
		setLocationRelativeTo( null );
	}
	
	public void setMinMax( int min, int max){
		bar.setMinimum( min );
		bar.setMaximum( max );
	}
	
	public void showDialog(){
		setVisible( true );
	}
	
	public void setText( String message ){
		bar.setString( message );
	}
	
	public void closeDialog(){
		setVisible( false );
		dispose();
	}

	public void valueChanged( String message ) {
		bar.setString( message );
	}

	public void valueChanged(int value) {
		bar.setValue( value );
	}

	public void parseComplete() {
	}

	public void parseStarting() {
	}

	public void parseFailed(Exception e, SVGSource source) {
	}
	
}