package org.laukvik.svg.swing.editors.text;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Text;
import org.laukvik.svg.shape.text.BaselineAlignment;
import org.laukvik.svg.shape.text.TextAnchor;
import org.laukvik.svg.swing.editors.TextEditor;
import org.laukvik.svg.swing.editors.TextEditorListener;
import org.laukvik.svg.swing.editors.geometry.PropertyPanel;
import org.laukvik.svg.swing.editors.geometry.UnitEditor;
import org.laukvik.svg.swing.editors.geometry.UnitListener;
import org.laukvik.svg.unit.Pixel;
import org.laukvik.svg.unit.Unit;

public class TextPropertyPanel extends PropertyPanel {
	
	private static final long serialVersionUID = 1L;
	private Text text;
	private TextEditor textArea;
	private UnitEditor textWidth, textHeight;
	private FontFamilyEditor textFontFamily;
	private UnitEditor textFontSize;
	private TextAnchorEditor textAlign;
	private BaselineAlignmentEditor textValign;
	private FontWeightEditor textFontWeight;
	private FontStyleEditor textFontStyle;
	private FontVariantEditor textFontVariant;

	public TextPropertyPanel() {
		super( "Text" );
		textArea = new TextEditor();
		textArea.setRows( 5 );
		textArea.addTextEditorListener(
				new TextEditorListener(){
					public void textChanged(String text2) {	
							text.text = text2;
							fireChanged(text);
					}
				}
		);
		
		
		JScrollPane textScroll = new JScrollPane( textArea );

		
		textWidth = new UnitEditor();
		textWidth.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {

						text.width = textWidth.getUnit();
						fireChanged(text);
					
				}
			}
		);
		
		textHeight = new UnitEditor();
		textHeight.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
				
						text.height = textHeight.getUnit();
						fireChanged(text);
					
				}
			}
		);
		
		textFontFamily = new FontFamilyEditor();
		textFontFamily.setMaximumSize( new Dimension(200, Integer.MAX_VALUE) );
		textFontFamily.addActionListener(
			new ActionListener(){
				public void actionPerformed( ActionEvent e ){
					
						text.font.name = (String) textFontFamily.getSelectedItem();
						fireChanged(text);
					
					
				}
			}
		);
		

		
		textFontSize = new UnitEditor();
		textFontSize.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
					
						text.font.size = textFontSize.getUnit();
						fireChanged(text);
					
				}
			}
		);
		
		textAlign = new TextAnchorEditor();
		textAlign.addTextAlignListener(
			new TextAnchorListener(){
				public void textAlignChanged(TextAnchor anchor) {
					
						text.anchor = textAlign.getTextAlign();
						fireChanged(text);
					
				}
			}
		);
		
		textValign = new BaselineAlignmentEditor();
		textValign.addTextAlignListener(
			new BaselineAlignmentListener(){
				public void textValignChanged( BaselineAlignment baselineAlignment) {

						text.baselineAlignment = textValign.getTextVerticalAlign();
						fireChanged(text);
					
				}
			}
		);
		textFontWeight = new FontWeightEditor();
		textFontWeight.addChangeListener(
			new ChangeListener(){
				public void stateChanged(ChangeEvent e) {
					
						text.font.weight = textFontWeight.getFontWeight();
						fireChanged(text);
					
				}				
			}
		);
		
		
		textFontStyle = new FontStyleEditor();
		textFontStyle.addChangeListener(
			new ChangeListener(){
				public void stateChanged(ChangeEvent e) {
					
						text.font.style = textFontStyle.getFontStyle();
						fireChanged(text);
					
				}				
			}
		);
		
		textFontVariant = new FontVariantEditor();
		textFontVariant.addChangeListener(
			new ChangeListener(){
				public void stateChanged(ChangeEvent e) {
				
						text.font.variant = textFontVariant.getFontVariant();
						fireChanged(text);
					
				}				
			}
		);
		
        String[] labels = { "Text", "Width", "Height", "Font", "Size", "Align", "Valign", "Bold", "Italic", "SmallCaps"  };
		JComponent [] comps = { textScroll, textWidth, textHeight, textFontFamily, textFontSize, textAlign, textValign, textFontWeight, textFontStyle, textFontVariant };
		setProperties( comps, labels );
	
	}

	public void selectionCleared() {
		this.text = null;
	}

	public void selectionChanged(Geometry... geometrys) {
		if (geometrys.length == 1 && geometrys[0] instanceof Text){
			this.text = (Text) geometrys[0];
			textArea.setText( text.text );
			textFontSize.setUnit( text.font.size );
			textFontFamily.setSelectedItem( text.font.name );
			if (text.width == null){
				textWidth.setUnit( new Pixel(0) );
			} else {
				textWidth.setUnit( text.width );
			}
			if (text.height == null){
				textHeight.setUnit( new Pixel(0) );
			} else {
				textHeight.setUnit( text.height );
			}
			
			
			textAlign.setTextAlign( text.anchor );
			textValign.setTextValign( text.baselineAlignment );
			textFontWeight.setFontWeight( text.font.weight );
		} else { 
			this.text = null;
		}
	}

}