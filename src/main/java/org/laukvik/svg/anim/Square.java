package org.laukvik.svg.anim;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Square extends SVGAnimation {
	
	int y = 0;

	public Square(Long start, Long duration) {
		super(start);
		this.duration = duration;
		this.y = (int) (Math.random() * 100);
	}

	public void play( Long time, Graphics2D g ){
		int x = (int) (time /150);
		y = 50;
		int width = 50;
		int height = 50;
		if (time <= duration){
			g.fillRect( x, y, width, height );	
		}
		
	}

}