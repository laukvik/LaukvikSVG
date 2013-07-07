package org.laukvik.svg.swing.editors.image;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import org.laukvik.svg.swing.SVGImageFilter;
import org.laukvik.svg.swing.editors.TextEditor;
import org.laukvik.svg.swing.editors.TextEditorListener;

public class ImageURLEditor extends JPanel implements TextEditorListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private TextEditor urlEditor;
	private JButton button;
	private Vector <ImageUrlListener> listeners;

	public ImageURLEditor() {
		super();
		initComponents();
	}
	
	public void initComponents(){
		setBorder( null );
		listeners = new Vector<ImageUrlListener>();
		urlEditor = new TextEditor();
		urlEditor.setRows( 1 );
		urlEditor.setLineWrap( false );
		urlEditor.setPreferredSize( new Dimension(40,16) );
		urlEditor.setMaximumSize( new Dimension(40,32) );
		urlEditor.setBorder( null );
		
		button = new JButton("...");
		button.setMaximumSize( new Dimension(40,16) );
		button.addActionListener( this );
		setLayout( new BorderLayout() );
		add( urlEditor, BorderLayout.CENTER  );
		add( button, BorderLayout.EAST  );
		
		urlEditor.addTextEditorListener( this );
	}
	
	public void setFont(Font font) {
		super.setFont(font);
		if (urlEditor != null){ // Avoid exception when look and feel is installed
			urlEditor.setFont( font );
			button.setFont( font );
		}
	}
	
	public void addImageUrlListener( ImageUrlListener imageUrlListener ){
		listeners.add( imageUrlListener );
	}
	
	public void removeImageUrlListener( ImageUrlListener imageUrlListener ){
		listeners.remove( imageUrlListener );
	}

	public void setURL( String url ) {
		urlEditor.setText( url );
	}

	public void textChanged(String text) {
		setURL( text );
		urlEditor.setCaretPosition( 0 );
		fireChanged();
	}
	
	public void fireChanged(){
		for (ImageUrlListener l : listeners){
			l.imageUrlChanged( urlEditor.getText() );
		}
	}

	public void actionPerformed( ActionEvent e ) {
		JFileChooser chooser = new JFileChooser( urlEditor.getText() );
		chooser.setFileFilter( new SVGImageFilter() );
		int returnVal = chooser.showOpenDialog( this );
		if (returnVal == JFileChooser.APPROVE_OPTION){
			File file = chooser.getSelectedFile();
			if (file != null){
				setURL( file.getAbsolutePath() );
			}
		}
	}
	
}