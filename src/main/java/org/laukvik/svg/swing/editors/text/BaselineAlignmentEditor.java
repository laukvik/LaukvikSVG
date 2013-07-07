package org.laukvik.svg.swing.editors.text;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JRadioButton;

import org.laukvik.svg.shape.text.BaselineAlignment;

public class BaselineAlignmentEditor extends JComponent implements ActionListener{

	private static final long serialVersionUID = 1L;
	private Vector<BaselineAlignmentListener> listeners;
	private JRadioButton top, middle, bottom;
	
	public BaselineAlignmentEditor() {
		super();
		listeners = new Vector<BaselineAlignmentListener>();
		initComponents();
	}
	
	public void setFont(Font font) {
		super.setFont(font);
		if (top != null){ // Avoid exception when look and feel is installed
			top.setFont( font );
			middle.setFont( font );
			bottom.setFont( font );
		}
	}
	
	public void initComponents(){
		setLayout( new FlowLayout( FlowLayout.LEFT ) );
		top = new JRadioButton("Top",true);
		middle = new JRadioButton("Middle");
		bottom = new JRadioButton("Bottom");
		
		add( top );
		add( middle );
		add( bottom );
		
		top.addActionListener( this );
		middle.addActionListener( this );
		bottom.addActionListener( this );
	}

	public void setTextValign( BaselineAlignment alignment ) {
		top.removeActionListener( this );
		middle.removeActionListener( this );
		bottom.removeActionListener( this );
		
		top.setSelected( alignment.isTop() );
		middle.setSelected( alignment.isMiddle() );
		bottom.setSelected( alignment.isBottom() );
		
		top.addActionListener( this );
		middle.addActionListener( this );
		bottom.addActionListener( this );
	}
	
	public void fireValueChanged( BaselineAlignment alignment ){
		for (BaselineAlignmentListener l : listeners){
			l.textValignChanged( alignment );
		}
	}

	public void addTextAlignListener( BaselineAlignmentListener listener ){
		listeners.add( listener );
	}

	public void removeTextAlignListener( BaselineAlignmentListener listener ){
		listeners.remove( listener );
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == top){
			setTextValign( BaselineAlignment.TOP );
		} else if (e.getSource() == middle){
			setTextValign( BaselineAlignment.MIDDLE );
		} else {
			setTextValign(BaselineAlignment.BOTTOM );
		}
		fireValueChanged( getTextVerticalAlign() );
	}

	public BaselineAlignment getTextVerticalAlign() {
		if (top.isSelected()){
			return BaselineAlignment.TOP;
		} else if (middle.isSelected()){
			return BaselineAlignment.MIDDLE;
		} else {
			return BaselineAlignment.BOTTOM;
		}
	}
	
}