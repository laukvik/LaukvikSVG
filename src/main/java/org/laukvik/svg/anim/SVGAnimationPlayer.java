package org.laukvik.svg.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class SVGAnimationPlayer extends JFrame{

	private static final long serialVersionUID = 1L;
	Timer timer;
	int delay;
	boolean isFrameFinished;
	SVGTimeline timeline;
	Long startedMillis;
	Long timeInTimelineMillis;
	Long lastTimelineMillis;
	boolean isLoop;
	
	public SVGAnimationPlayer() {
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setSize( 500, 200 );
		setVisible( true );
		delay = 100;
		startedMillis = null;
		isLoop = true;
		isFrameFinished = true;
		timeline = new SVGTimeline();
		timer = new Timer();
		setBackground( Color.BLACK );
	}
	
	public SVGTimeline getTimeline() {
		return timeline;
	}

	
	private class ScheduledTask extends TimerTask {

		public void run() {
			repaint();
		}

	}
	
	public void paint(Graphics g) {
		if (startedMillis == null){
			super.paint(g);
		} else {
			super.paint(g);
			Graphics2D g2 = (Graphics2D)g;
			/* Calculate the time position */
			timeInTimelineMillis = System.currentTimeMillis() - startedMillis;
			g.drawString( timeInTimelineMillis+"", 50,50 );
			
			for (SVGAnimation a : timeline.getAnimations()){
				if (a.canPlay( timeInTimelineMillis )){
					a.play( timeInTimelineMillis, g2 );
				}
			}
			isFrameFinished = true;
			Long stopAt = timeline.getDuration();
			if (timeInTimelineMillis > timeline.getDuration()){
				stop();
			}
		}
	}
	
	public void setSeconds( long duration ){
		
	}
	
	public void play(){
		setTitle( "Playing..." );
		isFrameFinished = true;
		startedMillis = System.currentTimeMillis();
		lastTimelineMillis = startedMillis;
		timer.schedule( new ScheduledTask(),  0, delay );
	}
	
	public void stop(){
		setTitle( "Stopped" );
		startedMillis = null;
		timer.cancel();
	}
	
	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}
	
	/**
	 * Add Fade 0 ms to 300 ms
	 * 
	 * @param args
	 */
	public static void main( String [] args ){
		SVGAnimationPlayer player = new SVGAnimationPlayer();
		player.getTimeline().add( new Fade( 0L, 10000L ) );
		player.getTimeline().add( new Square( 0L, 2500L ) );
		player.getTimeline().add( new Square( 2500L, 2500L ) );
		player.play();
	}

}