package org.laukvik.awt;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;

/**
 * A subclass of Canvas that automatically maintains a
 * gradient-painted background using a BasicGradientPainter
 * object.
 */

public class GradientCanvas extends Canvas implements Observer {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected BasicGradientPainter gradient;
    protected int defaultMode;
    protected int marginWidth;
    protected Color borderColor;
    
    /**
     * Create a GradientCanvas that renders using the given
     * BasicGradientPainter in its default mode.
     */
    public GradientCanvas(BasicGradientPainter p) {
	gradient = p;
	defaultMode = BasicGradientPainter.DEFAULT_MODE;
	gradient.addObserver(this);
    }

    /**
     * Set up fancy-looking margins.  Use this in conjunction
     * with setBackground().  If mwidth > 0 then a 3DRect will be
     * traced at the edges of the canvas, with mwidth margin 
     * inside it.  If bcolor is not null, then a 1-pixel line
     * of the given color will be draw.  Then, finally, the
     * gradient will be drawn insides all of that.
     */
    public void setMargins(int mwidth, Color bcolor) {
	marginWidth = mwidth;
	borderColor = bcolor;
    }

    /** 
     * Set the default gradient paint mode to be used, >= 0.
     */
    public synchronized void setMode(int m) {
	if (m != defaultMode) {
	    defaultMode = m;
	    repaint();
	}
    }

    /**
     * Retreive the gradient being used as our background
     */
    public synchronized BasicGradientPainter getGradient() {
	return gradient;
    }

    /**
     * Set the gradient to be used as our background.
     */
    public synchronized void setGradient(BasicGradientPainter p) {
	if (p == gradient) return;
	if (gradient != null) gradient.deleteObserver(this);
	gradient = p;
	gradient.addObserver(this);
    }

    /**
     * Overridden version of paint() that paints the gradient
     * into the background.  Subclasses should call super.paint(g)
     * first, before doing their own painting.
     */
    public void paint(Graphics g) {
	Rectangle r = getBounds();
	r.x = 0;
	r.y = 0;
	if (marginWidth > 0) {
	    g.setColor(getBackground());
	    g.draw3DRect(r.x, r.y, r.width - 1, r.height - 1, false);
	    r.width -= 2 + 2*marginWidth;
	    r.height -= 2 + 2*marginWidth;
	    r.x = 1 + marginWidth;
	    r.y = 1 + marginWidth;
	}
	if (borderColor != null) {
	    g.setColor(borderColor);
	    g.drawRect(r.x, r.y, r.width-1, r.height-1);
	    r.x += 1;
	    r.y += 1;
	    r.width -= 2;
	    r.height -= 2;
	}
	if (gradient != null) {
	    gradient.paint(g, r, defaultMode);
	}
	g.setClip(null);
    }

    /**
     * Each instance of GradientCanvas acts 
     * as an Observer of its own gradient, so that
     * it can repaint when the gradient changes.
     */
    public void update(Observable g, Object x) {
	if (g == gradient) repaint();
    }
}


