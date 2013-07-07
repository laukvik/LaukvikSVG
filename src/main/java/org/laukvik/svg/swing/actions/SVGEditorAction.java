package org.laukvik.svg.swing.actions;

import org.laukvik.svg.swing.Editor;

public class SVGEditorAction extends SVGAction {

	private static final long serialVersionUID = 1L;
	Editor editor;

	public SVGEditorAction( Editor editor ) {
		super();
		this.editor = editor;
	}
	
	public Editor getEditor() {
		return editor;
	}
	
}