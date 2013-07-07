package org.laukvik.svg.swing.editors.image;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Image;
import org.laukvik.svg.swing.editors.geometry.PropertyPanel;
import org.laukvik.svg.swing.editors.geometry.UnitEditor;
import org.laukvik.svg.swing.editors.geometry.UnitListener;
import org.laukvik.svg.unit.Unit;

public class ImagePropertyPanel extends PropertyPanel {
	
	private static final long serialVersionUID = 1L;
	private Image img;
	private ImageURLEditor imageHref;
	private UnitEditor imageWidth, imageHeight, physicalWidth, physicalHeight;

	public ImagePropertyPanel() {
		super( "Image" );
		
		imageWidth = new UnitEditor();
		imageHeight = new UnitEditor();
		imageHref = new ImageURLEditor();
		
		physicalWidth = new UnitEditor();
		physicalWidth.setEnabled( false );
		physicalHeight = new UnitEditor();
		physicalHeight.setEnabled( false );
		
		imageWidth.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
						img.width = imageWidth.getUnit();
						fireChanged(img);
				}
			}
		);
		imageHeight.addUnitListener(
			new UnitListener(){
				public void unitChanged( Unit unit ) {
					img.height = imageHeight.getUnit();
					fireChanged(img);
				}
			}
		);
		imageHref.addImageUrlListener(
			new ImageUrlListener(){
				public void imageUrlChanged(String text) {
//					img.load( text, img.getSVG() );
					img.setHref( text );
					imageWidth.setUnit( img.width );
					imageHeight.setUnit( img.height );
					physicalWidth.setUnit( img.physicalWidth );
					physicalHeight.setUnit( img.physicalHeight );
					fireChanged(img);
				}
			}
		);
		
        String[] labels = { "File", "Width", "Height", "Physical Height", "Physical Width"  };
		JComponent [] comps = { new JScrollPane(imageHref), imageWidth, imageHeight, physicalWidth, physicalHeight };
		setProperties( comps, labels );
		
	}

	public void selectionCleared() {
		this.img = null;
	}

	public void selectionChanged(Geometry... geometrys) {
		if (geometrys.length == 1 && geometrys[0] instanceof Image){
			this.img = (Image) geometrys[0];
			imageWidth.setUnit( img.width );
			imageHeight.setUnit( img.height );
			imageHref.setURL( img.href );
			physicalWidth.setUnit( img.physicalWidth );
			physicalHeight.setUnit( img.physicalHeight );
		} else { 
			this.img = null;
		}
	}
	
}