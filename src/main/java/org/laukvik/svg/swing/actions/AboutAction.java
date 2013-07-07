package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.laukvik.svg.swing.Editor;

public class AboutAction extends SVGEditorAction {

	private static final long serialVersionUID = 1L;
	
	public AboutAction( Editor editor ) {
		super(editor);
		putValue( Action.NAME, "About" );
		putValue( Action.ACTION_COMMAND_KEY, "about" );
	}
	
	public void actionPerformed(ActionEvent e) {
		getEditor().about();
	}

	
	
}