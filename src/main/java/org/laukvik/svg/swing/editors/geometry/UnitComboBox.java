package org.laukvik.svg.swing.editors.geometry;

import javax.swing.JComboBox;
import org.laukvik.svg.unit.*;

public class UnitComboBox extends JComboBox {

	private static final long serialVersionUID = 1L;
	private static Object [] types = { new Pixel(0), new Percent(0), new CM(0) };

	public UnitComboBox(){
		super( types );
		setSelectedIndex( 0 );
		setRenderer( new UnitTypeRenderer() );
	}
	
	public void setSelectedItem( Object unit ) {
		if (unit instanceof Unit){
			Unit u = (Unit) unit;
			if (u.isPercent()){
				super.setSelectedItem( types[1] );

			} else if (u.isCM()){
				super.setSelectedItem( types[2] );
				
			} else {
				super.setSelectedItem( types[0] );
			}			
		} else {
			/* Do nada - only unit is allowed */
			throw new IllegalArgumentException( "Only unit is allowed!" );
		}
	}
	
	public Unit getUnit(){
		Unit u = (Unit) getSelectedItem(); 
//		System.out.println( "UnitComboBox: " +  u );
		return u;
	}

}