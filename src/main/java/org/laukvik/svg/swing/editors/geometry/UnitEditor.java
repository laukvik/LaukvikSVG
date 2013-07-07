package org.laukvik.svg.swing.editors.geometry;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.laukvik.svg.unit.Percent;
import org.laukvik.svg.unit.Pixel;
import org.laukvik.svg.unit.Unit;

public class UnitEditor extends JPanel implements ChangeListener, ItemListener {

	private static final long serialVersionUID = 1L;
	private JSpinner valueSpinner;
	private UnitComboBox typeCombo;
	private Vector<UnitListener> listeners;
	
	public UnitEditor(){
		this( new Pixel(0) );
	}
	
	public UnitEditor( Unit unit ){
		super();
		listeners = new Vector<UnitListener>();
		initComponents();
		setUnit( unit );
	}
	
	public void setFont(Font font) {
		super.setFont(font);
		if (valueSpinner != null){ // Avoid exception when look and feel is installed
			valueSpinner.setFont( font );
			typeCombo.setFont( font );
		}
	}
	
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		valueSpinner.setEnabled(enabled);
		typeCombo.setEnabled(enabled);
	}
	
	public void initComponents(){
		setLayout( new BorderLayout() );
		valueSpinner = new JSpinner();
		typeCombo = new UnitComboBox();
		add( valueSpinner, BorderLayout.CENTER );
		add( typeCombo, BorderLayout.EAST );
		setMinimumSize( new Dimension(70,24) );
		
		valueSpinner.addChangeListener( this );
		typeCombo.addItemListener( this );
	}
	
	public void setUnit( Unit unit ) {
		valueSpinner.removeChangeListener( this );
		typeCombo.removeItemListener( this );
		if (unit.isInfinity() || unit.is100Percent()){
			valueSpinner.setValue( 100 );
			typeCombo.setSelectedItem( new Percent(0) );
		} else {
			valueSpinner.setValue( unit.getValue() );
			typeCombo.setSelectedItem( unit );
		}
		valueSpinner.addChangeListener( this );
		typeCombo.addItemListener( this );
	}
	
	public Unit getUnit() {
		Unit u = typeCombo.getUnit();
		Number n = (Number) valueSpinner.getValue();
		return new Unit( n.floatValue(), u.getUnitType()  );
	}

	public void addUnitListener(UnitListener unitListener) {
		listeners.add( unitListener );
	}
	
	public void removeUnitListener(UnitListener unitListener) {
		listeners.remove( unitListener );
	}

	/**
	 * Spinner changed
	 * 
	 */
	public void stateChanged(ChangeEvent e) {
		fireChanged();
	}

	/**
	 * Type changed
	 */
	public void itemStateChanged(ItemEvent e) {
		fireChanged();
	}
	
	public void fireChanged(){
		Unit u = getUnit();
		for (UnitListener l : listeners){
			l.unitChanged( u );
		}
	}

}