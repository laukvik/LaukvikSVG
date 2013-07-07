package org.laukvik.awt;

import java.awt.Graphics;
import java.awt.Shape;
import java.util.Observable;
import java.util.Vector;

/**
 * BasicGradientPainter is an abstract base class that
 * handles the management of GradientSegment management
 * for other GradientPainter classes.
 * <p>
 * A gradient can be a simple gradient, defined by a
 * start and end color, or it can be a multi-part
 * gradient composed of several GradientSegment objects.
 * <p>
 * This class also extends Observable.  Whenever the
 * stored gradients vector changes, the observers will
 * be notified.
 *
 * @see GradientSegment
 * @see GradientPainter
 * @see LinearGradientPainter
 */
@SuppressWarnings("unchecked")
public abstract class BasicGradientPainter extends Observable
    implements java.io.Serializable, java.util.Observer
{
    /**
     * The default painting mode -- 0.
     * All implementations of this class must support a
     * usable mode with a value of 0.
     */
    public static final int DEFAULT_MODE = 0;

    
	protected Vector segments;
    protected BasicGradientPainter master = null;

    /**
     * Create a new BasicGradientPainter with 0 segments.
     */
    public BasicGradientPainter() {
        segments = new Vector();
    }

    /**
     * Create a new BasicGradientPainter, initialized with
     * a copy of the gradient segments of the supplied painter.
     *
     * @param src Painter whose segments are to be copied
     */
    public BasicGradientPainter(BasicGradientPainter src) {
	this();
	for(int i = 0; i < src.countSegments(); i++) {
	    segments.addElement(src.getSegment(i).clone());
	}
    }

    /**
     * Share our gradient segment list with another gradient
     * painter.  The other one becomes the 'master' and this one
     * becomes the 'slave'.  All modifications to the segment list
     * should be done in the master.
     */
    public void slaveTo(BasicGradientPainter m) {
	if (m == null) 
	    throw new IllegalArgumentException("Master cannot be null");
	master = m;
	segments = master.segments;
	master.addObserver(this);
    }

    /**
     * Observe our master if we have one.  When the master
     * changes, then we notify our observers too.
     */
    public void update(Observable o, Object x) {
	if (o == master) {
	    gradientsChanged();
	}
    }

    /**
     * Paint a gradient into a given Shape using
     * a given Graphics, using the given mode.  This
     * would normally be called to do the simplest
     * kind of painting into a Canvas or other AWT
     * Component.  Subclasses may (and should) provide
     * more sophisticated paint() methods.
     *
     * @param g Graphics to paint into
     * @param s Shape to fill (usually a Rectangle)
     * @param mode Gradient generation mode (defined per subclass)
     */
    public abstract void paint(Graphics g, Shape s, int mode);

    /**
     * Take any necessary action if the gradients
     * have changed.  This implementation notifies
     * our observers that our gradients have changed
     * and thus they might want to repaint.
     * All the gradient-setting and -removing
     * methods call it, so subclasses that need to
     * react (possibly by invalidating a cache) can
     * do so by overriding this method.  Note that
     * if a subclass does override this method, it
     * should still call super.gradientsChanged() to
     * take care of notifying our observers.
     */
    public void gradientsChanged() { 
	setChanged();
	notifyObservers();
    }


    /**
     * Split the specified GradientSegment no. i
     * at its midpoint.  If the value i is out of
     * bounds, then we silently do nothing.
     */
    public synchronized void splitSegment(int i) {
        if (segments == null || i < 0 || i >= segments.size()) return;
	GradientSegment original = (GradientSegment)(segments.elementAt(i));
	GradientSegment replacements[] = original.split();
	segments.removeElementAt(i);
	segments.insertElementAt(replacements[1], i);
	segments.insertElementAt(replacements[0], i);
	gradientsChanged();
    }

    /**
     * Flip the specified segment, exchange its
     * start and end color values.
     */
    public synchronized void flipSegment(int i) {
        if (segments == null || i < 0 || i >= segments.size()) return;
	GradientSegment gs = (GradientSegment)(segments.elementAt(i));
	gs.flip();
	gradientsChanged();
    }

    /**
     * Swap segments i and i-1,
     * preserving the offset of the other segments.
     * 
     * @return true if a swap actually took place
     */
    public synchronized boolean swapAt(int i) {
        if (segments == null || i <= 0 || i >= segments.size()) return false;
	GradientSegment gs = (GradientSegment)(segments.elementAt(i));
	GradientSegment gsd = (GradientSegment)(segments.elementAt(i-1));
	double s1, e1, s2, e2, size, sized;
	size = gs.getRangeEnd() - gs.getRangeStart();
	sized = gsd.getRangeEnd() - gsd.getRangeStart();
	s1 = gsd.getRangeStart();
	s2 = s1 + size;
	e1 = s2;
	e2 = s2 + sized;
	gs.setRange((float)s1, (float)e1);
	gsd.setRange((float)s2, (float)e2);
	segments.setElementAt(gs, i-1);
	segments.setElementAt(gsd, i);
	gradientsChanged();
	return true;
    }

    /** 
     * Get the GradientSegment no. i, returns null
     * if i is out of bounds.
     */
    public synchronized GradientSegment getSegment(int i) {
        if (segments == null || i < 0 || i >= segments.size()) 
            return null;
        else {
            return (GradientSegment)(segments.elementAt(i));
	}
    }

    /** 
     * Remove the GradientSegment no. i, return null
     * and remove nothing if i is out of bounds.
     */
    public synchronized GradientSegment removeSegment(int i) {
        if (segments == null || i < 0 || i >= segments.size()) 
            return null;
        else {
            GradientSegment ret;
            ret = (GradientSegment)(segments.elementAt(i));
            segments.removeElementAt(i);
	    gradientsChanged();
            return ret;
        }
    }

    /** 
     * Replace the GradientSegment no. i.
     * Throws ArrayIndexOutOfBoundsException if i is
     * out of bounds.
     */
    public synchronized void setSegment(int i, GradientSegment gs) {
        segments.setElementAt(gs, i);
	gradientsChanged();
    }

    /** 
     * Append another GradientSegment to the sequence
     * used by the painter.
     */
    public synchronized void addSegment(GradientSegment gs) {
        segments.addElement(gs);
	gradientsChanged();
    }

    /**
     * Return the count of how many segments this
     * painter is currently holding.
     */
    public synchronized int countSegments() { return segments.size(); }


    /**
     * A simple representation of this GradientPainter as a string.
     */
    public String toString() {
	return getClass().getName() + "[" + countSegments() + " segments]";
    }

    /**
     * A representation of this GradientPainter as a code fragment
     * creating a GradientPainter named toPainter.  Note that 
     * the printed classname is always "GradientPainter" even if
     * the instance is actually something else (gack!).
     */
    public synchronized String toCodeFragment(String toPainter) {
	StringBuffer sb = new StringBuffer();
	if (toPainter == null) toPainter = "painter1";

	sb.append("  GradientPainter ");
	sb.append(toPainter);
	sb.append(" = new ");
	sb.append("GradientPainter");
	sb.append("();\n  ");
	for(int i = 0; i < countSegments(); i++) {
	    sb.append(getSegment(i).toCodeFragment(toPainter));
	    sb.append("\n  ");
	}
	sb.append("\n");
	return sb.toString();
    }
}
