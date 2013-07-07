package org.laukvik.svg.swing.editors.text;

import javax.swing.JComboBox;

import org.laukvik.svg.stroke.LineJoin;

public class LineJoinEditor extends JComboBox {

	private static final long serialVersionUID = 1L;

	public LineJoinEditor(){
		super( new Object [] { LineJoin.BEVEL, LineJoin.MITER, LineJoin.ROUND } );
	}
	
	public void setSelectedItem( Object anObject ) {
		if (anObject instanceof LineJoin){
			LineJoin lc = (LineJoin) anObject;
			if (lc.isBevel()){
				super.setSelectedItem( LineJoin.BEVEL );
			} 
			if (lc.isMeter()){
				super.setSelectedItem( LineJoin.MITER);
			} 
			if (lc.isRound()){
				super.setSelectedItem( LineJoin.ROUND );
			} 
		}
	}
	
}