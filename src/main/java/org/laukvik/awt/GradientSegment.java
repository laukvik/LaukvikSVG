package org.laukvik.awt;

import java.awt.Color;

/**
 * A portion of a gradient, from a Color c1 to 
 * a Color c2, in either RGB space or HSB space.
 * Every gradient segment is defined as a particular
 * portion of a gradient, where every gradient goes
 * from 0.0 to 1.0.  By using multiple GradientSegments,
 * you can use a GradientPainter to get very fancy 
 * effects.
 * <p>
 * This is for Java 1.1.  Java 2 already has
 * gradient painting support.
 */

public class GradientSegment implements java.io.Serializable, Cloneable{
	
	private static final long serialVersionUID = 1L;
	protected Color c1c;
    protected Color c2c;
    protected float c1p[];
    protected float c2p[];
    protected float start;
    protected float end;
    protected boolean useHSB;

    /**
     * Create a simple GradientSegment in RGB space,
     * from color c1 to c2 on range 0.0 to 1.0.
     */
    public GradientSegment(Color c1, Color c2) {
        this(c1, c2, 0.0F, 1.0F, false);
    }

    /** 
     * Create a GradientSegment from a Color c1 to
     * a Color 2, over the specified range s to e.
     *
     * @param s Start of range, s>=0.0, s<1.0
     * @param e End of range, e>0.0, e<=1.0, e>s
     */
    public GradientSegment(Color c1, Color c2, double s, double e, boolean isHSB)
    {
	this(c1, c2, (float)s, (float)e, isHSB);
    }

    /**
     * Split a segment into two segments at its midpoint, the segments
     * will be contiguous, so the overall gradient would not be
     * affected by replacing the original segment with this pair.
     * 
     * @return An array of 2 GradientSegments
     */
    public GradientSegment [] split() {
	GradientSegment ret[] = new GradientSegment[2];
	double midpoint = (end - start)/2.0 + start;
	Color midcolor = new Color(getColorInt(midpoint));
	ret[0] = new GradientSegment(c1c, midcolor, start, midpoint, useHSB);
	ret[1] = new GradientSegment(midcolor, c2c, midpoint, end, useHSB);
	return ret;
    }

    /**
     * Create a GradientSegment from Color c1 to
     * c2, over the specified range s to e.  
     */
    public GradientSegment(Color c1, Color c2, float s, float e, boolean isHSB)
    {
        if (s < 0.0F || s >= 1.0F ||
            e <= 0.0F || e > 1.0F || s >= e) {
            throw new IllegalArgumentException("Bad start/end params");
        }
        start = s;
        end = e;
        c1c = c1;
        c2c = c2;
        c1p = new float[3];
        c2p = new float[3];
        useHSB = isHSB;
	prepareColors();
    }

    protected void prepareColors() {
        if (useHSB) {
            Color.RGBtoHSB(c1c.getRed(),c1c.getGreen(),c1c.getBlue(),c1p);
            Color.RGBtoHSB(c2c.getRed(),c2c.getGreen(),c2c.getBlue(),c2p);
        }
        else {
            getRGBColorComponents(c1c, c1p);
            getRGBColorComponents(c2c, c2p);
        }
    }

    protected void getRGBColorComponents(Color c, float[] f) {
        f[0] = (float)(c.getRed() / (float)255);
        f[1] = (float)(c.getGreen() / (float)255);
        f[2] = (float)(c.getBlue() / (float)255);
    }

    /** Retrieve the start color */
    public Color getColor1() { return c1c; }

    /** Retrieve the end color */
    public Color getColor2() { return c2c; }

    /** Set the start color */
    public void setColor1(Color c) { c1c = c; prepareColors(); }

    /** Set the end color */
    public void setColor2(Color c) { c2c = c; prepareColors(); }

    /** Retrieve the start of the range */
    public float getRangeStart() { return start; }

    /** Retrieve the end of the range */
    public float getRangeEnd() { return end; }

    /** Set the start and end values */
    public void setRange(float s, float e) {
        if (s < 0.0F || s >= 1.0F ||
            e <= 0.0F || e > 1.0F || s >= e) {
            throw new IllegalArgumentException("Bad start/end params");
        }
        start = s;
        end = e;
    }

    /** 
     * Set the HSB/RGB flag
     *
     * @param isHSB Set to true to use HSB, false for RGB
     */
    public void setUseHSB(boolean isHSB) {
	useHSB = isHSB;
	prepareColors();
    }

    /**
     * Retrieve the HSB/RGB flag.
     */
    public boolean getUseHSB() { return useHSB; }

    /**
     * Get a Color corresponding to the value on
     * the range 0.0 to 1.0.  If the value given
     * is outside the range that this segment is
     * responsible for, then null is returned.
     */
    public Color getColor(float v) {
        double pr;
        if (v < start || v > end) return null;

        pr = (v - start)/(end - start);
        float f0 = (float)(c1p[0] + (c2p[0] - c1p[0]) * pr);
        float f1 = (float)(c1p[1] + (c2p[1] - c1p[1]) * pr);
        float f2 = (float)(c1p[2] + (c2p[2] - c1p[2]) * pr);
        if (useHSB) {
            return Color.getHSBColor(f0,f1,f2);
        }
        else {
            return new Color(f0,f1,f2);
        }
    }

    /**
     * Flip the colors of this segment.
     */
    public synchronized void flip() {
	Color tmp = c1c;
	c1c = c2c;
	c2c = tmp;
	prepareColors();
	return;
    }

    /**
     * Get a Color as an int with format 0x00RRGGBB.
     * This is the fastest method, but also the 
     * one that is hardest to use for drawing.  If the
     * value v does not fall in this segment, then we
     * return 0.  (Normally, we could never return 0
     * because we have to return an opaque color with
     * alpha=FF.)
     *
     * @param v gradient value on the range [0,1]
     * @return a color value, or 0.
     * @see java.awt.Color
     */
    public final int getColorInt(double v) {
        double pr;
        if (v < start || v > end) return 0;

        pr = (v - start)/(end - start);
        float f0 = (float)(c1p[0] + (c2p[0] - c1p[0]) * pr);
        float f1 = (float)(c1p[1] + (c2p[1] - c1p[1]) * pr);
        float f2 = (float)(c1p[2] + (c2p[2] - c1p[2]) * pr);
        if (useHSB) {
            return (Color.HSBtoRGB(f0,f1,f2));
        }
        else {
	    int ret = ((int)((f0 * 255) + 0.5) & 0x00ff);
	    ret = (ret << 8) | (int)((f1 * 255) + 0.5) & 0x00ff;
	    ret = (ret << 8) | (int)((f2 * 255) + 0.5) & 0x00ff;
	    return (ret | 0xff000000);
        }
    }  


    /**
     * Get a Color corresponding to the value on
     * the range 0.0 to 1.0 and write the RGB
     * components into the given float array.
     * Note that this form is much faster than
     * the other form of getColor for RGB, but
     * no faster for HSB computations.  This
     * method returns true if the output array
     * r[] has been filled in and should be painted,
     * and false otherwise.
     */
    public boolean getColor(float v, float [] r) {
        double pr;
        if (v < start || v > end) return false;

        pr = (v - start)/(end - start);
        float f0 = (float)(c1p[0] + (c2p[0] - c1p[0]) * pr);
        float f1 = (float)(c1p[1] + (c2p[1] - c1p[1]) * pr);
        float f2 = (float)(c1p[2] + (c2p[2] - c1p[2]) * pr);
        if (useHSB) {
            Color cx = Color.getHSBColor(f0,f1,f2);
            getRGBColorComponents(cx, r);
        }
        else {
            r[0] = f0;
            r[1] = f1;
            r[2] = f2;
        }
        return true;
    }

    /**
     * Render this GradientSegment as a String.
     */
    public String toString() {
	StringBuffer sb = new StringBuffer();
	sb.append("GradientSegment[");
	sb.append(c1c.toString());
	sb.append(",");
	sb.append(c2c.toString());
	sb.append(",");
	sb.append(start);
	sb.append(",");
	sb.append(end);
	sb.append(",");
	sb.append(useHSB);
	sb.append("]");
	return sb.toString();
    }

    /**
     * Render this GradientSegment as a short, informative string.
     */
    public String toShortString() {
	StringBuffer sb = new StringBuffer();
	sb.append("[");
	sb.append(c1c.getRed());
	sb.append(",");
	sb.append(c1c.getGreen());
	sb.append(",");
	sb.append(c1c.getBlue());
	sb.append(" > ");
	sb.append(c2c.getRed());
	sb.append(",");
	sb.append(c2c.getGreen());
	sb.append(",");
	sb.append(c2c.getBlue());
	sb.append("]");
	if (useHSB) sb.append("hsb"); else sb.append("rgb");
	return sb.toString();
    }

    /**
     * Render this GradientSegment as a code fragment that 
     * is a call to the 5-arg constructor.
     * If the addToPainter string is not null, then return
     * a longer code fragment that adds this segment to the
     * named painter, like this:
     *
     * <pre>painter1.addSegment(new GradientSegment(new Color(-519),new Color(-1),0.0, 0.5, false));</pre>
     *
     * @param addToPainter name of a notional GradientPainter variable
     */
    public String toCodeFragment(String addToPainter) {
	StringBuffer sb = new StringBuffer();
	if (addToPainter != null) {
	    sb.append(addToPainter);
	    sb.append(".addSegment(");
	}
	sb.append("new GradientSegment(");
	sb.append("new Color(");
	sb.append(c1c.getRed());
	sb.append(",");
	sb.append(c1c.getGreen());
	sb.append(",");
	sb.append(c1c.getBlue());
	sb.append("), new Color(");
	sb.append(c2c.getRed());
	sb.append(",");
	sb.append(c2c.getGreen());
	sb.append(",");
	sb.append(c2c.getBlue());
	sb.append("), ");
	sb.append(start);
	sb.append(",");
	sb.append(end);
	sb.append(",");
	sb.append(useHSB ? "true" : "false" );
	sb.append(")");
	if (addToPainter != null) {
	    sb.append(");");
	}
	return sb.toString();
    }

    /**
     * Create a new GradientSegment that is the same as this one.
     */
    public synchronized Object clone() {
	return new GradientSegment(c1c, c2c, start, end, useHSB);
    }

}

