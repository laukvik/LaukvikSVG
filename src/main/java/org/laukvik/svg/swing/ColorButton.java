package org.laukvik.svg.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JColorChooser;

import org.laukvik.svg.SVGColor;
import org.laukvik.svg.swing.actions.ColorAction;
import org.laukvik.svg.swing.actions.SVGAction;

public class ColorButton extends DrawingControllerToolbarButton implements MouseMotionListener, ActionListener {

	private static final long serialVersionUID = 1L;
	
	private Rectangle back, fore;
	
	final int TYPE_NOTHING = -1;
	final int TYPE_FORE    = 0;
	final int TYPE_BACK    = 1;
	final int TYPE_ARROW   = 2;
	private int type = TYPE_NOTHING;
	
	private Rectangle arrow;
	private PencilFeatures pencil;
	
	public ColorButton( SVGAction colorAction ) {
		super( new ColorAction() );
		fore = new Rectangle( 5,5, 15, 15 );
		back = new Rectangle( 15,15, 15, 15 );
		arrow = new Rectangle( 25,5, 5, 5 );
		addMouseMotionListener( this );
		addActionListener( this );
	}
	
	public void setSvgPanel(SVGEditablePanel panel) {
		this.pencil = panel.getPencil();
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		

		
		/* Background */
		g2.setColor( pencil.getBackground().getValue() );
		g2.fill( back );
		g2.setColor( Color.BLACK );
		g2.draw( back );
		
		/* Foreground */
		g2.setColor( pencil.getForeground().getValue() );
		g2.fill( fore );
		g2.setColor( Color.BLACK );
		g2.draw( fore );
		
		/* Switch */
		g2.setColor( Color.WHITE );
		g2.fill( arrow );
		g2.setColor( Color.BLACK );
		g2.draw( arrow );
	}
	
	public void actionPerformed(ActionEvent e) {
		if (type == TYPE_FORE){
			Color tmp = JColorChooser.showDialog( this, "", pencil.getForeground().getValue() );
			if (tmp != null){
				pencil.setForeground(new SVGColor( tmp ));
			}
		} else if (type == TYPE_BACK){
			Color tmp = JColorChooser.showDialog( this, "", pencil.getBackground().getValue() );
			if (tmp != null){
				pencil.setBackground(new SVGColor( tmp ));
			}
		} else if (type == TYPE_ARROW){
			Color tmp1 = pencil.getBackground().getValue();
			Color tmp2 = pencil.getForeground().getValue();
			pencil.setBackground(new SVGColor( tmp2 ));
			pencil.setForeground(new SVGColor( tmp1 ));
		}
		repaint();
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		if (fore.contains(  e.getX(), e.getY() )){
			type = TYPE_FORE;
		} else if (back.contains(  e.getX(), e.getY() )){
			type = TYPE_BACK;
		} else if (arrow.contains(  e.getX(), e.getY() )){
			type = TYPE_ARROW;
		} else {
			type = TYPE_NOTHING;
		}
	}
	
}