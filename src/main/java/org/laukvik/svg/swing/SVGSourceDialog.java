package org.laukvik.svg.swing;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import org.laukvik.svg.parser.Parser;
import org.laukvik.svg.shape.SVG;

public class SVGSourceDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JEditorPane editorPane;

	public SVGSourceDialog(SVG svg) {
		super();
		setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
		Parser parser = new Parser();
		String code = "error";
		try {
			code = parser.toPlainText( svg );
		} catch (Exception e) {
			e.printStackTrace();
		}
		editorPane = new JEditorPane( "text/xml", code );
		editorPane.setEditable( false );
		add( new JScrollPane( editorPane ) );
		setSize( 400, 400 );
		setVisible( true );
	}

}