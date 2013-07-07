package org.laukvik.svg.anim;

import java.awt.Graphics2D;

public abstract class SVGAnimation {

	Long start;
	Long duration;
	
	public SVGAnimation( Long start ){
		this.start = start;
	}

	public abstract void play( Long time, Graphics2D g );

	public boolean canPlay( Long timeInTimelineMillis ) {
		return (timeInTimelineMillis > start && timeInTimelineMillis < (start + duration));
	}

}