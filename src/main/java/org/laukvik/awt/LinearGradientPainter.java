package org.laukvik.awt;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

/**
 * GradientPainter is a class that accepts parameters
 * defining a gradient, and can also paint the gradient
 * into a Shape (actually, into the Shape's bounding
 * box).  
 * <p>
 * This implementation can only paint two simple kinds
 * of gradients: linear gradients horizontal and vertical.
 * This is because the current implementation uses a
 * very stupid but easy approach of drawing a bunch of
 * 1-pixel lines in different colors.  This is easy and
 * fast for small areas and avoids using gobs of memory
 * for large areas, but it is totally impractical for
 * more sophisticated or complicated gradients.  [Note:
 * for fancies gradients, you must use an algorithms 
 * that renders pixels into some kind of buffer, computing
 * the RGB of each pixel based on it's position in the
 * gradient.  That's easy for some gradient shapes but
 * complicated for others.  It also requires a buffer
 * big enough to hold all the pixels and computation
 * enough to set all the pixels.]
 * <p>
 * A gradient can be a simple gradient, defined by a
 * start and end color, or it can be a multi-part
 * gradient composed of several GradientSegments.
 * <p>
 * The usual sequence for using this class is:
 * <ol>
 * <li>Create the GradientPainter
 * <li>Add one or more segments
 * <li>Paint a gradient into a Shape
 * </ol>
 * Then you would do step 3 repeatedly, probably from
 * the paint() method of an AWT component subclass.
 * <p>
 * Internally, the GradientPainter holds its list of
 * segments on a Vector and uses them in sequence.
 * It is the responsibility of the caller to ensure
 * that the segments link up correctly.  Regions of
 * the range [0.0..1.0] that have no segment 
 * corresponding to them will not be painted. 
 * Regions that overlap will be painted with the
 * lowest-numbered segment that applies to them.
 *
 * @see GradientSegment
 * @see GradientPainter
 */
public class LinearGradientPainter extends BasicGradientPainter implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Linear gradient paint from left to right.
     */
    public static final int LEFT_TO_RIGHT = 0;

    /**
     * Linear gradient paint from left to right.
     */
    public static final int RIGHT_TO_LEFT = 180;

    /**
     * Linear gradient paint from top to bottom
     */
    public static final int TOP_TO_BOTTOM = 90;

    /**
     * Linear gradient paint from bottom to top
     */
    public static final int BOTTOM_TO_TOP = 270;


    /**
     * Create a simple GradientPainter with 1 simple
     * RGB segment.
     */
    public LinearGradientPainter(Color c1, Color c2) {
	super();
        addSegment(new GradientSegment(c1,c2));
    }

    /**
     * Create a new GradientPainter with 0 segments.
     */
    public LinearGradientPainter() {
	super();
    }

    /**
     * Create a new LinearGradientPainter with a copy of
     * the segments of some other gradient painter
     */
    public LinearGradientPainter(BasicGradientPainter src) {
	super(src);
    }

    /**
     * Paint a gradient into a given Shape using
     * a given Graphics.  Note that you can use
     * any Shape, including a Rectangle or a Polygon.
     * If Shape is null, then uses the current clip
     * shape of the Graphics.  Mode must be one of
     * the constants LEFT_TO_RIGHT, RIGHT_TO_LEFT,
     * TOP_TO_BOTTOM, or BOTTOM_TO_TOP.
     */
    public synchronized void paint(Graphics g, Shape s, int mode) {
        Shape cache = null;
        if (s == null) {
            s = g.getClip();
        }
        else {
            cache = g.getClip();
            g.setClip(s);
        }
        Rectangle r = s.getBounds();
        paint(g, r.x, r.y, r.width, r.height, mode);
        if (cache != null) g.setClip(cache);
        return;
    }

    /**
     * Paint a gradient into a given rectangular
     * area using a given Graphics.  Mode must be one of
     * the constants LEFT_TO_RIGHT, RIGHT_TO_LEFT,
     * TOP_TO_BOTTOM, or BOTTOM_TO_TOP.
     */
    public synchronized void paint(Graphics g,
                                   int x,int y,int w,int h, 
                                   int mode) 
    {
        int bval, fval;
        int min, max;
        int incr;
        boolean horiz;
        switch (mode) {
        case LEFT_TO_RIGHT:
            bval = x; fval = x + w;
            min = y; max = y + h;
            incr = 1; horiz = false;
            break;
        case RIGHT_TO_LEFT:
            bval = x + w; fval = x;
            min = y; max = y + h;
            incr = -1; horiz = false;
            break;
        case TOP_TO_BOTTOM:
            bval = y; fval = y + h;
            min = x; max = x + w;
            incr = 1; horiz = true;
            break;
        case BOTTOM_TO_TOP:
            bval = y + h; fval = y;
            min = x; max = x + w;
            incr = -1; horiz = true;
            break;
        default:
            throw new IllegalArgumentException("Bad gradient mode");
        }
        paintSimpleLinear(g, bval, fval, min, max, incr, horiz);
        return;
    }


    protected void paintSimpleLinear(Graphics g, int bval, int fval,
                                     int min, int max, int incr, boolean h)
    {
        Color ccache = g.getColor();
        Color cp;
        int i, j, lim, ext;
        int val;
        float v;
        lim = countSegments();
        ext = Math.abs(fval - bval);
        for(i = 0, val = bval; i <= ext; val += incr, i++) {
            v = (float)((float)i / (float)ext);
            for(cp = null, j = 0; j < lim; j++) {
                cp = ((GradientSegment)(segments.elementAt(j))).getColor(v);
                if (cp != null) break;
            }
            if (cp != null) {
                g.setColor(cp);
                if (h) g.drawLine(min, val, max, val);
                else g.drawLine(val, min, val, max);
            }
        }
        g.setColor(ccache);
        return;
    }
}

