package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;

import org.laukvik.svg.swing.Editor;

public class MagnifierAction extends SVGEditorAction {

	private static final long serialVersionUID = 1L;
	
	public MagnifierAction( Editor editor ) {
		super(editor);
		putValue( Action.NAME, "Magnifier" );
		putValue( Action.ACTION_COMMAND_KEY, "magnifier" );
	}
	
	public void actionPerformed(ActionEvent e) {
		getEditor().handleMagnifier();
		Object o = e.getSource();
		if (o instanceof JCheckBoxMenuItem){
			JCheckBoxMenuItem checkbox = (JCheckBoxMenuItem) o;
			checkbox.setSelected( getEditor().isMagnifierVisible() );
		}
	}
	
}