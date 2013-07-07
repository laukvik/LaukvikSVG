package org.laukvik.awt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;

/**
 * GradientPainter is a class that accepts parameters
 * defining a gradient, and can also paint the gradient
 * into a Shape (actually, into the Shape's bounding
 * box), and can also fill the gradient into an Image or
 * just an array of pixel values.
 * <p>
 * This implementation can only paint several kinds of
 * of gradients: linear gradients at any angle, conical
 * gradients at any angle, square gradients at any angle,
 * and radial gradients.
 * The algorithm is fairly simple: 
 * <ol>
 * <li>for the given shape, get the bounding box as a rectangle (W,H)
 * <li>create a pixel array of (WxH)
 * <li>For each pixel in that array:
 * <ol>
 * <li>Translate the point by (-W/2,-H/2)
 * <li>Rotate the point by the given angle 
 * <li>Compute the color at that point in the simple version of the
 * gradient
 * <li>Put the color into the array.
 * </ol>
 * <li>Create an Image from the pixel array using MemoryImageSource
 * <li>Draw the image using Graphics.drawImage
 * </ol>
 * <p>
 * The image is cached for later use; each GradientPainter
 * object will cache exactly one Image. [This way, if you use a
 * separate GradientPainter for each region you wish to paint
 * then the first paint call will be slow but subsequent ones will
 * be very fast, at the cost of hogging more memory for caching.]
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
 * Then you would do step 3 repeatedly.
 * <p>
 * Internally, the GradientPainter holds its list of
 * segments on a Vector and uses them in sequence.
 * It is the responsibility of the caller to ensure
 * that the segments link up correctly.  Regions of
 * the range [0.0..1.0] that have noo segment 
 * corresponding to them will be painted with the
 * background color.
 * Regions that overlap will be painted with the
 * lowest-numbered segment that applies to them.
 * <p>
 * This class supports Java 1.1 and Java 2.  Under Java 1.1
 * the Shape to paint may be a Rectangle or a Polygon or
 * any other shape supported by java.awt.Graphics.setClip(),
 * [which is usually just Rectangle; the code uses a hideous
 * kludge to auto-recognize this piece of Java1.1-lameness
 * and work around it by making parts of the image transparent,
 * ack!].  Under Java 2, it supports any Shape that can be
 * used to clip with a Graphics2D, include Ellipse2D and
 * GeneralPath.  Eventually, I hope to support the full 
 * Java2D Paint interface with a separate FancyGradientPaint 
 * class.
 * <p>
 * Objects of class GradientPainter serve as Observables,
 * so that other objects interested in knowing when the
 * gradient has changed (and therefore needs to be repainted)
 * can register themselves as Observers.
 *
 * @see GradientSegment
 * @see java.awt.Shape
 */
public class GradientPainter extends BasicGradientPainter implements ImageObserver, java.io.Serializable, Paint{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Linear gradient, by default painted from along the x
     * axis left to right.  Use a different angle to get
     * a gradient along some other axis.  For simple
     * vertical or horizontal gradients, use LinearGradientPainter
     * instead.
     *
     * @see LinearGradientPainter
     */
    public static final int LINEAR_GRADIENT = 0;

    /**
     * A conical gradient pointing, by default, to the
     * center right.  The extent value does not matter 
     * for conical gradients.
     */
    public static final int CONICAL_GRADIENT = 1;

    /**
     * A radial gradient with the center at the
     * center of the Shape bounding box.  The angle
     * value does not matter for radial gradients.
     */
    public static final int RADIAL_GRADIENT = 2;

    /**
     * A square gradient with the center at the 
     * center of the Shape bounding box.  At this time,
     * we can only do squares; maybe someday we will
     * also have rectangular gradients.
     */
    public static final int SQUARE_GRADIENT = 3;


    // member variables
    protected Color  bg;
    protected int    defAngle;
    protected int    defExtent;
    protected Point  defCenter;

    // cache-related member variables (not serialized)
    protected transient Image  cacheImage;
    protected transient Rectangle cacheBaseRect;
    protected transient int    cacheAngle;
    protected transient Point  cacheBasePoint;
    protected transient int    cacheMode;
    protected transient int    cacheExtent;
    protected transient int    cacheDefRGB;
    protected transient Polygon cacheHackClipShape;
    
    // special clipping hack member variable
    protected transient Polygon  hackClipShape;


    /**
     * Create a simple GradientPainter with 1 simple
     * RGB segment.
     */
    public GradientPainter(Color c1, Color c2) {
	this();
        addSegment(new GradientSegment(c1,c2));
    }

    /**
     * Create a GradientPainter with the same segments
     * as the supplied BasicGradientPainter.  This is
     * basically a copy constructor.
     */
    public GradientPainter(BasicGradientPainter src) {
	super(src);
	cacheImage = null;
	cacheBaseRect = null;
	cacheBasePoint = null;
	hackClipShape = null;
	bg = Color.black;
	defAngle = 0;
	defExtent = 0;
	defCenter = null;
	if (src instanceof GradientPainter) {
	    bg = ((GradientPainter)src).getBackground();
	    defAngle = ((GradientPainter)src).defAngle;
	    defExtent = ((GradientPainter)src).defExtent;
	    defCenter = ((GradientPainter)src).defCenter;
	}
    }

    /**
     * Create a new GradientPainter with 0 segments.
     */
    public GradientPainter() {
	super();
	cacheImage = null;
	cacheBaseRect = null;
	cacheBasePoint = null;
	hackClipShape = null;
	bg = Color.black;
	defAngle = 0;
	defExtent = 0;
	defCenter = null;
    }

    /**
     * Set the default background color; this is the color that will
     * be used where no segment applies.
     */
    public void setBackground(Color c) {
	bg = c;
    }

    /**
     * Retrieve the default background color.
     */
    public Color getBackground() { return bg; }
    

    /**
     * Paint a gradient into a given Shape using
     * a given Graphics.  Note that you can use
     * any Shape, including a Rectangle or a Polygon.
     * If Shape is null, then uses the current clip
     * shape of the Graphics.  Mode must be one of
     * the constants LINEAR_GRADIENT, CONICAL_GRADIENT,
     * RADIAL_GRADIENT, or SQUARE_GRADIENT.
     *
     */
    public synchronized void paint(Graphics g, Shape s, int mode) {
        if (s == null) {
            s = g.getClip();
        }
        Rectangle r = s.getBounds();

	Point p;
	if (defCenter == null) 
	    p = new Point(r.width/2 + r.x, r.height/2 + r.y);
	else 
	    p = defCenter;

	int ext;
	if (defExtent > 0) ext = defExtent;
	else {
	    if (mode == LINEAR_GRADIENT) {
		ext = r.width;
	    }
	    else if (mode == SQUARE_GRADIENT) {
		ext = Math.max(r.width, r.height);
		ext = ext/2;
	    }
	    else {
		double extd = Math.sqrt((r.width * r.width) +
					(r.height * r.height));
		ext = (int)(Math.round(extd/2.0));
	    }
	}
        paint(g, r, p, ext, defAngle, mode);
        return;
    }

    /**
     * Set default values for the angle, normally 0.
     * This will be used when the simple 3-argument version of 
     * paint() is called.   If the new default is different
     * from the previous one, then the observers are notified.
     *
     * @param angle The default angle to use, in degrees
     */
    public synchronized void setDefaultAngle(int angle) {
	if (angle != defAngle) {
	    defAngle = angle;
	    setChanged();
	    notifyObservers();
	}
    }

    /**
     * Retreive the default angle.
     */
    public synchronized int getDefaultAngle() {
	return defAngle;
    }

    /**
     * Set default values for the extent, which is normally
     * auto-computed from the size of the paint region.
     * This will be used when the simple 3-argument version of 
     * paint() is called.   If the new default is different
     * from the previous one, then the observers are notified.
     * Pass 0 to return to the default behavior of autocomputing
     * the extent from the shape.
     *
     * @param angle The default extent to use, >1
     */
    public synchronized void setDefaultExtent(int extent) {
	if (extent != defExtent) {
	    if (extent < 0) 
		throw new IllegalArgumentException("Default extent must be >=0");
	    defExtent = extent;
	    setChanged();
	    notifyObservers();
	}
    }


    /**
     * Retreive the default angle.
     */
    public synchronized int getDefaultExtent() {
	return defExtent;
    }


    /**
     * Set default center point for the gradient drawing.
     * This will be used when the simple 3-argument version of 
     * paint() is called.   If the new default is different
     * from the previous one, then the observers are notified.
     * If no default center is set, or the default is set to
     * null, then a new center point will be autocomputed from
     * the shape at each call of the 3-argument version of 
     * paint().
     *
     * @param pt Default center point to use (absolute!)
     */
    public synchronized void setDefaultCenter(Point pt) {
	if (pt != null && pt.equals(defCenter)) return;
	defCenter = pt;
	setChanged();
	notifyObservers();
    }

    /**
     * Retreive the default center point.
     */
    public synchronized Point getDefaultCenter() {
	return defCenter;
    }


    /**
     * Paint a gradient into a given Shape
     * area using a given Graphics.  The center Point
     * should normally be within the bounds of the Shape s.
     * The extent is the overall size of the gradient;
     * For a radial or square gradient, it is usually
     * the radius of the Shape s, for a linear gradient it
     * is usually the size of the shape along the given
     * angle, for a conical gradient extent doesn't matter.
     * Note that the extent must be large enough so that
     * the gradient covers the whole shape even if the
     * center is offset.  If the center is null, then it
     * will be set to the center of the Shape's bounding box.
     * The angle must be in degrees; 0 degrees is pointing
     * horizontally to the right.  Mode must be one of
     * the constants LINEAR_GRADIENT, CONICAL_GRADIENT,
     * RADIAL_GRADIENT, or SQUARE_GRADIENT.
     * 
     * @param g Graphics into which to paint
     * @param s Shape of the area to paint (usually rectangular)
     * @param center The center of the gradient (or null)
     * @param extent Size of the gradient computation (>1)
     * @param angle Angle of the gradient computation
     * @param mode Kind of gradient to paint (default: Linear)
     */
    public synchronized void paint(Graphics g, Shape s, Point center, 
				   int extent, int angle, int mode) 
    {
	Rectangle r = s.getBounds();
	Rectangle clip = g.getClipBounds();
	if (clip != null) {
	    if (!(clip.intersects(r))) return;
	}
	if (center == null) {
	    center = new Point(r.width/2 + r.x, r.height/2 + r.y);
	}
	Rectangle baserect = new Rectangle(0, 0, r.width, r.height);
	Point basepoint = new Point(center.x - r.x, center.y - r.y);
	
	hackClipShape = null;
	// bizarre kludge to permit filled Polygons in JDK 1.1
	Shape cache = g.getClip();
	try { g.setClip(s); }
	catch (IllegalArgumentException e) {
	    if (s instanceof Polygon) {
		hackClipShape = (Polygon)s;
		g.setClip(r);
	    }
	    else throw e;
	}

	Image img = computeGradientImage(Toolkit.getDefaultToolkit(),
					 baserect, basepoint,
					 extent, angle, mode, bg.getRGB());
	if (extent <= 1) 
	    throw new IllegalArgumentException("Extent must be > 1");
	g.drawImage(img, r.x, r.y, this);
	g.setClip(cache);
	return;
    }


    /**
     * Get an image with the requested gradient in it.  
     * This method is used by the paint() method, and it may also
     * be called directly.  It has a lot of parameters.
     *
     * @param tk The AWT toolkit to use, typically from Component.getToolkit()
     * @param baserect A rectangle at origin 0,0 - gives the size of the image
     * @param center The centerpoint of the gradient (from origin 0,0)
     * @param ext Extent of the gradient, usually related to size of image
     * @param angle Angle of the gradient in degrees
     * @param mode  Kind of gradient to paint
     * @param defrgb Color to use where the gradient doesnt paint
     */
    public synchronized Image computeGradientImage(Toolkit tk,
						   Rectangle baserect,
						   Point basepoint,
						   int ext, int angle,
						   int mode, int defrgb)
    {
	int w, h;
	w = baserect.width;
	h = baserect.height;
	int pixels[];

	// step 0 - check for valid cache
	if (mode == RADIAL_GRADIENT) angle = 0;
	if (cacheImage != null && cacheBaseRect != null &&
	    baserect.equals(cacheBaseRect) && basepoint.equals(cacheBasePoint) &&
	    cacheAngle == angle && cacheExtent == ext && cacheMode == mode &&
	    cacheDefRGB == defrgb && 
	    (hackClipShape == cacheHackClipShape || 
	     hackClipShape != null && hackClipShape.equals(cacheHackClipShape)))
	    {
		return cacheImage;
	    }
	    

	// step 1 - initialize pixel array
	Image im;
	pixels = new int[w * h];
	for(int i = 0; i < pixels.length; i++) {
	    pixels[i] = defrgb;
	}

	// step 2 - fill in the pixel array
	computeGradientPixels(w, h, pixels, basepoint, ext, angle, mode, defrgb);

	// step 3 - create the Image and cache it
	MemoryImageSource mis = new MemoryImageSource(w, h, pixels, 0, w);
	im = tk.createImage(mis);
	cacheImage = im;
	cacheBaseRect = baserect;
	cacheBasePoint = basepoint;
	cacheAngle = angle;
	cacheExtent = ext;
	cacheMode = mode;
	cacheDefRGB = defrgb;
	cacheHackClipShape = hackClipShape;

	// step 4 - return the image
        return im;
    }

    // coordinate transform values for communication between methods
    protected transient double   xrx, xry, yrx, yry;  // rotation
    protected transient double   tx, ty;		  // translation
    protected transient double   tw, th;	// size
    protected transient double   exf;
    protected transient int      glim;
    protected transient int      tmode;	// gradient mode

    /**
     * Fill the pixel array for a given gradient.  This is the
     * most primitive of the public methods, but it can be used
     * to support the Java2 Raster/DataBuffer/Paint architecture.
     * Note that, when you would call this from elsewhere, you
     *
     * @param w Width of the raster to fill in with pixels
     * @param h Height of the raster to fill in with pixels
     * @param pixels Array of ints, w*h in size
     * @param basepoint Base point for the clipping region (usually null)
     * @param ext Extent of the gradient (very important, must be >1)
     * @param angle Angle of the gradient in degrees (ignored for Radial)
     * @param mode Gradient mode, linear, radial, conical, square
     * @param defrgb raw sRGB int color to use for unpainted areas
     *
     */
    public synchronized void computeGradientPixels(int w, int h, int pixels[],
						   Point basepoint,
						   int ext, int angle,
						   int mode, int defrgb)
    {
	int i, x, y, pixel;
	int txi = 0;
	int tyi = 0;
	tw = w;
        th = h;
	if (hackClipShape != null) {
	    txi = hackClipShape.getBounds().x;
	    tyi = hackClipShape.getBounds().y;
	}
	tx = basepoint.x;
	ty = basepoint.y;
	exf = ext;
	double arad = (angle / 180.0) * Math.PI;
	double xp, yp;
	yry = xrx = Math.cos(arad);
	xry = -Math.sin(arad);
	yrx =  Math.sin(arad);
	glim = segments.size();
	tmode = mode;
	for(i = 0, y = 0; y < h; y++) {
	    yp = y + 0.5;
	    for(x = 0; x < w; x++) {
		xp = x + 0.5;
		pixel = computeColorFor(xp,yp);
		pixels[i] = ((pixel == 0)?(defrgb):(pixel));
		// bizarre kludge to permit filled Polygons in JDK 1.1
		if (hackClipShape != null) {
		    if (!(hackClipShape.contains(x + txi, y + tyi))) {
			pixels[i] = 0; // fully transparent
		    }
		}
		i++;
	    }
	}
	return;
    }


    /**
     * Compute the color for a given point and return it.
     * Most of the data gets passed in as protected member
     * variables, for speed.
     */
    protected final int computeColorFor(double x, double y) {
	double v, av;
	// x and y are in the image coordinate space
	double tmpx = x - tx;
	double tmpy = y - ty;
	x = tmpx * xrx + tmpy * xry;
	y = tmpx * yrx + tmpy * yry;
	// x and y are now in the transformed coordinate space
	switch (tmode) {
	case CONICAL_GRADIENT:
	    av = Math.abs(Math.atan2(y,x));
	    v = av/Math.PI;
	    break;

	case RADIAL_GRADIENT:
	    av = Math.sqrt(x*x + y*y);
	    v = av / exf;
	    break;

	case SQUARE_GRADIENT:
	    av = Math.max(Math.abs(x), Math.abs(y));
	    v = av / exf;
	    break;


	default: // LINEAR_GRADIENT
	    av = x / exf;
	    v = av + 0.5;
	    break;
	}

	// okay, now we have v
	int ret = -1;
	int cp = 0;
	if (v < 0.0) v = 0.0;
	else if (v > 1.0) v = 1.0;
	for(int j = 0; j < glim; j++) {
	    cp = ((GradientSegment)(segments.elementAt(j))).getColorInt(v);
	    if (cp != 0) { ret = cp; break; }
	}
	return ret;
    }


    /**
     * Invalidate the cached image, if any, because the
     * GradientSegments have been changed somehow.  You
     * must call this if you've changed one of the
     * segments by calling any of the GradientSegment setXXX
     * methods.  This implementation calls BasicGradientPainter's
     * gradientsChanged() method too, so that we take care of
     * notifying any observers.
     */
    public synchronized void gradientsChanged() {
	cacheImage = null;
	cacheBaseRect = null;
	super.gradientsChanged();
    }	


    /**
     * Do-nothing version of imageUpdate to allow this object to be
     * used as a lazy imageObserver
     */
    public boolean imageUpdate(Image im, int info, int x, int y, int w, int h) {
	return !((info & ImageObserver.ALLBITS) != 0);
    }

	public PaintContext createContext(ColorModel cm, Rectangle deviceBounds,
			Rectangle2D userBounds, AffineTransform xform, RenderingHints hints) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getTransparency() {
		// TODO Auto-generated method stub
		return 0;
	}

}