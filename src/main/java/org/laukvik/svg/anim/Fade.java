package org.laukvik.svg.anim;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Fade extends SVGAnimation {


	public Fade(Long start, Long duration) {
		super(start);
		this.duration = duration;
	}

	public void play( Long time, Graphics2D g ){
		if (time > duration){
			/* Do nothing */
		} else {
			
			float alpha = (time.floatValue() / duration.floatValue());
			System.out.println( alpha );
			if (alpha > 1){
				alpha = 1;
			} else if(alpha < 0){
				alpha = 0;
			}
			String str = time + "/" + duration + "=" + alpha;
  			g.drawString(str, 20, 80 );
  			g.setPaint( Color.WHITE );
			g.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, alpha ) );	
		}
	}

}