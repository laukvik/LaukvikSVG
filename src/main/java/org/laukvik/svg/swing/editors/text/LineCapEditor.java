package org.laukvik.svg.swing.editors.text;

import javax.swing.JComboBox;

import org.laukvik.svg.stroke.LineCap;

public class LineCapEditor extends JComboBox {

	private static final long serialVersionUID = 1L;

	public LineCapEditor(){
		super( new Object [] { LineCap.BUTT, LineCap.ROUND, LineCap.SQUARE } );
	}
	
	public void setSelectedItem( Object anObject ) {
		if (anObject instanceof LineCap){
			LineCap lc = (LineCap) anObject;
//			System.out.println( "LineCapEditor: " + lc );
			if (lc.getValue() == LineCap.BUTT_TYPE){
				super.setSelectedItem( LineCap.BUTT );
			} 
			if (lc.getValue() == LineCap.ROUND_TYPE){
				super.setSelectedItem( LineCap.ROUND );
			} 
			if (lc.getValue() == LineCap.SQUARE_TYPE){
				super.setSelectedItem( LineCap.SQUARE );
			} 
		} else {
//			System.err.println( "LineCapEditor: ????"  );
		}
	}
	
}