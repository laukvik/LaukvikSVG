package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import org.laukvik.svg.swing.Editor;
import org.laukvik.svg.swing.SVGEditablePanel;

public class SVGAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private SVGEditablePanel panel;
	
	public SVGAction(){
		super();
	}
	
	public void actionPerformed(ActionEvent e) {
	}
	
	public static Icon getIcon( String filename) {
		BufferedImage img;
		if (filename.endsWith(".tiff")){
			try {
				img = ImageIO.read( Editor.class.getResource("icons/tifs/" + filename) );
				return new javax.swing.ImageIcon( img );
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new javax.swing.ImageIcon( Editor.class.getResource("icons/" + filename));
	}
	
	public void setSvgPanel(SVGEditablePanel panel) {
		this.panel = panel;
	}
	
	public SVGEditablePanel getSvgPanel() {
		return panel;
	}
	
}