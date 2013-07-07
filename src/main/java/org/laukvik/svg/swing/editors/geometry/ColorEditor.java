package org.laukvik.svg.swing.editors.geometry;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;

public class ColorEditor extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	Color color;

	public ColorEditor(){
		super();
		setBorder( BorderFactory.createEtchedBorder() );
		setOpaque( true );
		setColor( Color.black );
		addActionListener( this );
	}
	
	public void setColor(Color color) {
		this.color = color;
		setBackground( color );
		setText( color.getRed()  + "x" + color.getGreen() + "x" + color.getBlue() );
		
		double rgb = color.getRed() + color.getGreen() + color.getBlue(); 
		if (rgb > 128){
			setForeground( Color.WHITE );
		} else {
			setForeground( Color.BLACK );
		}
	}
	
	public Color getColor() {
		return color;
	}

	public void actionPerformed(ActionEvent e) {
//		JColorChooser cc = new JColorChooser();

		Color newColor = JColorChooser.showDialog( this, "", color );
		if (newColor == null){
			
		} else {
			setColor( newColor );
		}
	}	
	
}