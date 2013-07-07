package org.laukvik.svg.shape;

import org.laukvik.svg.fill.Fill;
import org.laukvik.svg.stroke.Stroke;
import org.laukvik.svg.unit.Unit;

public class BasicShape extends Geometry implements HasFill, HasStroke {
	
	public Stroke stroke = new Stroke();
	public Fill fill = new Fill();

	public BasicShape( Unit x, Unit y ) {
		super( x, y );
	}
	
	public BasicShape() {
		super();
	}

	public Stroke getStroke() {
		return stroke;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	public Fill getFill() {
		return fill;
	}

	public void setFill(Fill fill) {
		this.fill = fill;
	}

}