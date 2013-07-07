package org.laukvik.svg.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

import org.laukvik.svg.swing.actions.ViewSourceAction;

public class SVGPopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	private SVGPanel svgPanel;
	
	public SVGPopupMenu( SVGPanel svgPanel ) {
		super();
		this.svgPanel = svgPanel;
		add( "Properties" );
		
//		add( new ViewSourceAction(svgPanel) );
		
		
		MouseListener popupListener = new PopupListener();
		svgPanel.addMouseListener( popupListener );
	}
	
	class PopupListener extends MouseAdapter {
	    public void mousePressed(MouseEvent e) {
	        maybeShowPopup(e);
	    }

	    public void mouseReleased(MouseEvent e) {
	        maybeShowPopup(e);
	    }

	    private void maybeShowPopup(MouseEvent e) {
	        if (e.isPopupTrigger()) {
	        	removeAll();
	        	add( "About " + svgPanel.getSVG().getTitle() );
	        	show( e.getComponent(), e.getX(), e.getY() );
	        }
	    }
	}
	
}