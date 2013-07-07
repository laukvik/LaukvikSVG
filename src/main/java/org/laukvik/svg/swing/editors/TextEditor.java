package org.laukvik.svg.swing.editors;

import java.util.Vector;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextEditor extends JTextArea implements DocumentListener {

	private static final long serialVersionUID = 1L;
	private Vector<TextEditorListener> listeners;

	public TextEditor() {
		super();
		setRows( 1 );
		listeners = new Vector<TextEditorListener>();
		getDocument().addDocumentListener( this );
	}
	
	public void log( Object value ){
//		System.out.println( value );
	}
	
	public void addTextEditorListener( TextEditorListener listener ){
		listeners.add( listener );
	}
	
	public void removeTextEditorListener( TextEditorListener listener ){
		listeners.remove( listener );
	}
	
	public void setText(String t) {
		if (getText().equalsIgnoreCase( t )){
			log( "setText: ignored" + t );
		} else {
			log( "setText: " + t );
			super.setText(t);
			fireTextChanged();
		}
	}
	
	boolean busy = false;
	public void fireTextChanged(){
		if (busy){
			return;
		}
		busy = true;
		getDocument().removeDocumentListener( this );
		for (TextEditorListener l : listeners){
			l.textChanged( getText() );
		}
		getDocument().addDocumentListener( this );
		busy = false;
	}

	public void changedUpdate(DocumentEvent e) {
		log( "changedUpdate: " + e );
		fireTextChanged();
	}

	public void insertUpdate(DocumentEvent e) {
		log( "insertUpdate: " + e );
		fireTextChanged();
	}

	public void removeUpdate(DocumentEvent e) {
		log( "removeUpdate: " + e );
		fireTextChanged();
	}

}