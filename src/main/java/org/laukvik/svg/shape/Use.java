package org.laukvik.svg.shape;

public class Use extends BasicShape {
	
	private SVGID xlink;

	public Use() {
		super();
	}
	
	public void setXlink(SVGID xlink) {
		this.xlink = xlink;
	}
	
	public SVGID getXlink() {
		return xlink;
	}
	
}