package org.laukvik.svg.swing.controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.laukvik.svg.swing.SVGEditablePanel;

/**
 * 
 * 
 * @author morten
 *
 */
public interface DrawingController extends MouseListener, MouseMotionListener{

	public void mouseClicked(MouseEvent e);
	public void mouseEntered(MouseEvent e);
	public void mouseExited(MouseEvent e);
	public void mousePressed(MouseEvent e);
	public void mouseReleased(MouseEvent e);
	public void mouseDragged(MouseEvent e);
	public void mouseMoved(MouseEvent e);

	public void setSvgPanel( SVGEditablePanel panel );
	
}