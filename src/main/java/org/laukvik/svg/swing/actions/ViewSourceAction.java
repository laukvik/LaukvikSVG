package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.laukvik.svg.swing.Editor;

public class ViewSourceAction extends SVGEditorAction {

	private static final long serialVersionUID = 1L;
	
	public ViewSourceAction( Editor editor ) {
		super(editor);
		putValue( Action.NAME, "View source" );
		putValue( Action.ACTION_COMMAND_KEY, "viewSource" );
	}
	
	public void actionPerformed(ActionEvent e) {
		getEditor().handleViewSource();
	}
	
}