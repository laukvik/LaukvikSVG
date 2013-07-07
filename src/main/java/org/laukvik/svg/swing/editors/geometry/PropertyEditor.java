package org.laukvik.svg.swing.editors.geometry;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JComponent;

public class PropertyEditor extends JComponent{

	private static final long serialVersionUID = 9068845689186356582L;
	private PropertyLabel label;
	private JComponent editor;
	private Dimension size = new Dimension( 70, 32 );
	private Dimension labelMinsize = new Dimension( 70, 32 );
	private Dimension csize = new Dimension( 130, 320 );
	
	public PropertyEditor( String label, JComponent editor ){
		super();
		setLayout( new BorderLayout() );
		this.label = new PropertyLabel( label );
		this.label.setPreferredSize( size );
		this.label.setMinimumSize( labelMinsize );
		this.editor = editor;
		this.editor.setMaximumSize( csize );
		
		this.editor.setFont( getEditorFont() );
		
		add( this.label, BorderLayout.WEST );
		add( this.editor, BorderLayout.CENTER );
	}
	
	public Font getEditorFont(){
		return new Font( "Verdana", Font.PLAIN, 10 );
	}
	
}