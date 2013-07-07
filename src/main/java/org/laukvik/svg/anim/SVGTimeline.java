package org.laukvik.svg.anim;

import java.util.Vector;

public class SVGTimeline {
	
	Vector<SVGAnimation> animations;
	
	public SVGTimeline(){
		animations = new Vector<SVGAnimation>();
	}
	
	public void add( SVGAnimation animation ){
		this.animations.add( animation );
	}
	
	public void remove( SVGAnimation animation ){
		this.animations.remove( animation );
	}
	
	public Vector<SVGAnimation> getAnimations() {
		return animations;
	}
	
	public Long getDuration(){
		SVGAnimation last = animations.lastElement();
		return last.start + last.duration;
	}

}
