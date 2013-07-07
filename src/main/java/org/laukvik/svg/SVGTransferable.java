package org.laukvik.svg;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.laukvik.svg.shape.Element;

public class SVGTransferable implements Transferable {
	
	public final static DataFlavor SVG_FLAVOR = new DataFlavor( Element.class, "SVG" );
	Element [] element;

	public SVGTransferable( Element... element ) {
		this.element = element;
	}

	public Object getTransferData( DataFlavor flavor ) throws UnsupportedFlavorException, IOException {
		return element;
	}

	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor [] { SVG_FLAVOR };
	}

	public boolean isDataFlavorSupported( DataFlavor flavor) {
		return flavor.equals( SVG_FLAVOR );
	}

}
