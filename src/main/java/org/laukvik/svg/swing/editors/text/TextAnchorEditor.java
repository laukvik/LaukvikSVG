package org.laukvik.svg.swing.editors.text;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import org.laukvik.svg.shape.text.TextAnchor;

public class TextAnchorEditor extends JComponent implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Vector<TextAnchorListener> listeners;
	private JRadioButton left, middle, right;
	
	public TextAnchorEditor() {
		super();
		listeners = new Vector<TextAnchorListener>();
		initComponents();
	}
	
	public void setFont(Font font) {
		super.setFont(font);
		if (left != null){ // Avoid exception when look and feel is installed
			left.setFont( font );
			middle.setFont( font );
			right.setFont( font );
		}
	}
	
	public void initComponents(){
		setLayout( new FlowLayout( FlowLayout.LEFT ) );
		left = new JRadioButton("Left",true);
		middle = new JRadioButton("Middle");
		right = new JRadioButton("Right");
		
		add( left );
		add( middle );
		add( right );
		
		left.addActionListener( this );
		middle.addActionListener( this );
		right.addActionListener( this );
	}
	
	public TextAnchor getTextAlign(){
		if (left.isSelected()){
			return TextAnchor.START;
		} else if (middle.isSelected()){
			return TextAnchor.MIDDLE;
		} else {
			return TextAnchor.END;
		}
	}

	public void setTextAlign( TextAnchor anchor ) {
		left.removeActionListener( this );
		middle.removeActionListener( this );
		right.removeActionListener( this );
		
		left.setSelected( anchor.isStart() );
		middle.setSelected( anchor.isMiddle() );
		right.setSelected( anchor.isEnd() );
		
		left.addActionListener( this );
		middle.addActionListener( this );
		right.addActionListener( this );
	}
	
	public void fireValueChanged( TextAnchor anchor ){
		for (TextAnchorListener l : listeners){
			l.textAlignChanged( anchor );
		}
	}

	public void addTextAlignListener( TextAnchorListener listener ){
		listeners.add( listener );
	}

	public void removeTextAlignListener( TextAnchorListener listener ){
		listeners.remove( listener );
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == left){
			setTextAlign( TextAnchor.START );
		} else if (e.getSource() == middle){
			setTextAlign( TextAnchor.MIDDLE );
		} else {
			setTextAlign( TextAnchor.END );
		}
		fireValueChanged( getTextAlign() );
	}
	
}