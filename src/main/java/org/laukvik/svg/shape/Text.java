package org.laukvik.svg.shape;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JTextField;

import org.laukvik.svg.Opacity;
import org.laukvik.svg.SVGColor;
import org.laukvik.svg.shape.text.BaselineAlignment;
import org.laukvik.svg.shape.text.TextAnchor;
import org.laukvik.svg.unit.Pixel;
import org.laukvik.svg.unit.Unit;

/**
 * 
 * @author morten
 * @see http://www.w3.org/TR/SVG11/text.html
 */
public class Text extends BasicShape {
	
	public final static String TEXT        = "text";
	public final static String FONTSTYLE   = "font-style";
	public final static String FONTWEIGHT  = "font-weight";
	public final static String FONTFAMILY  = "font-family";
	public final static String FONTSIZE    = "font-size";
	public static final String FONTVARIANT = "font-variant";
	public static final String ANCHOR      = "text-anchor";
	public static final String EDITABLE    = "editable";
	public final static String ALIGNMENT_BASELINE = "alignment-baseline";
	
	public String text = "";
	public SVGColor color = SVGColor.black;
	public org.laukvik.svg.font.Font font = new org.laukvik.svg.font.Font();
	public TextAnchor anchor = TextAnchor.START;
	public BaselineAlignment baselineAlignment = BaselineAlignment.TOP;
	
	/* A non-standard property to introduce line wrapping */
	
	/** A non-standard property to use when line wrapping is needed  */
	public Unit width = null;
	/** Specifies the maximum height of the text before its being clipped */
	public Unit height = null;
	/** Specifies the amount of pixels required to display the whole text at the specified width */
	public Pixel requiredHeight = null;
	
	/* Features to enable editable text */
	public boolean editable = false;
	public JComponent component;
	public java.awt.Rectangle componentArea;
	
	public Text() {
		super();
	}
	
	public Text( String text ) {
		super();
		this.text = text;
	}
	
	public Text( Unit x, Unit y, String text ){
		super(x,y);
		this.text = text;
	}
	
	public Text( Unit x, Unit y, String text, TextAnchor anchor ){
		super(x,y);
		this.text = text;
		this.anchor = anchor;
	}

	public Text(Unit x, Unit y, String text, TextAnchor anchor, org.laukvik.svg.font.Font font, SVGColor color, Opacity opacity ) {
		super(x,y);
		this.text = text;
		this.anchor = anchor;
		this.font = font;
		this.fill.color = color;
		this.opacity = opacity;
	}

	public void paint( Graphics g, Dimension size ){
		Graphics2D g2 = (Graphics2D) g;
		if (font.style.isItalic() && font.weight.isBold()){
			g2.setFont( new Font( font.name, Font.ITALIC | Font.BOLD, toPixels(font.size) ) );
			
		} else if (font.style.isItalic()){
			g2.setFont( new Font( font.name, Font.ITALIC, toPixels(font.size) ) );
			
		} else if (font.weight.isBold()){
			g2.setFont( new Font( font.name, Font.BOLD, toPixels(font.size) ) );
			
		} else {
			g2.setFont( new Font( font.name, Font.PLAIN, toPixels(font.size) ) );
		} 
		
		g2.setPaint( fill );	
		g2.setComposite( createAlpha( opacity ) );
		
		int xpx = toHorisontalPixels(x);
		int ypx = toHorisontalPixels(y);
		
		if (editable){

			int w = width == null ? 100 : toHorisontalPixels( width );
			int h = height == null ? 24 : toVerticalPixels( width );
			
			JTextField field = new JTextField( text );
			getSVG().addInput( this ); 
			
			component = field;
			componentArea = new java.awt.Rectangle( xpx, ypx, w, h );

			return;
		} 
		
		/* Is wrapping turned on ?*/
		if (width == null){
			/* Don't use wrapping */
			/* Calculate required size of text */
			int swidth  = g.getFontMetrics().stringWidth( text );
			int sheight = g.getFontMetrics().getHeight();			
			setShape( new java.awt.Rectangle( xpx, ypx-sheight+g.getFontMetrics().getDescent(), swidth, sheight ) );
			
			if (anchor.isStart()){
				/* Text should start at x */
			} else if (anchor.isMiddle()){
				/* Text should be centered around x */
				xpx -= swidth / 2;
			} else if (anchor.isEnd()){
				/* Text should stop at x */
				xpx -= swidth;
			}
			
			if (baselineAlignment.isTop()){
				ypx += g.getFontMetrics().getAscent();
			} else if (baselineAlignment.isMiddle()){
				/**/
				ypx -= sheight/2;
				ypx += g.getFontMetrics().getAscent();
			} else if (baselineAlignment.isBottom()){
//				ypx += sheight;
//				ypx -= sheight;
			}
			
			g2.drawString( text, xpx, ypx );
			
			
			
			
			if (isHandlesVisible()){
				setHandles( xpx, ypx  + g.getFontMetrics().getDescent(), swidth, sheight  );
				paintHandles(g);
			}
		} else {
			
			Hashtable<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();	    
		    map.put( TextAttribute.FONT, g.getFont()  );
			
			/* Use wrapping */
			AttributedString vanGogh = new AttributedString( text.length() == 0 ? " " : text, map  );
				    
            AttributedCharacterIterator paragraph = vanGogh.getIterator();
            int paragraphStart = paragraph.getBeginIndex();
            int paragraphEnd = paragraph.getEndIndex();
            FontRenderContext frc = g2.getFontRenderContext();
            LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(paragraph, frc);
            
            // Set break width to width of Component.
            float breakWidth = toHorisontalPixels( width );
            float drawPosY = 0;
            // Set position to the index of the first character in the paragraph.
            lineMeasurer.setPosition(paragraphStart);
            
            int pixelHeight = 20000;
            
            if (height != null){
            	pixelHeight = toVerticalPixels( height );
            }
            

            // Get lines until the entire paragraph has been displayed.
            while (lineMeasurer.getPosition() < paragraphEnd) {

                // Retrieve next layout. A cleverer program would also cache
                // these layouts until the component is re-sized.
                TextLayout layout = lineMeasurer.nextLayout( breakWidth );

                // Compute pen x position. If the paragraph is right-to-left we
                // will align the TextLayouts to the right edge of the panel.
                // Note: this won't occur for the English text in this sample.
                // Note: drawPosX is always where the LEFT of the text is placed.
//                float drawPosX = layout.isLeftToRight() ? 0 : breakWidth - layout.getAdvance();

                // Move y-coordinate by the ascent of the layout.
                drawPosY += layout.getAscent();
                
//                System.out.println( "anchor: " + anchor +" " + anchor.isMiddle() );
                
                int extra = (int) ( breakWidth - (layout.getBounds().getWidth()  ) );
//                System.out.println( "height: " + height + " extra:" + extra + " drawPosY: " + drawPosY + " xpx: " + xpx + " x: " + x);
                if ((drawPosY) < pixelHeight){
                	
                	/* Draw the TextLayout */
                	if (anchor.isStart()){
                		/* Text align LEFT */
                		layout.draw(g2, xpx, ypx + drawPosY );
                		
                	} else if (anchor.isMiddle()){
                		/* Text align CENTER */
                		layout.draw(g2, xpx + extra/2 - breakWidth/2, ypx + drawPosY );
//                		System.out.println( "x: " + (xpx + extra/2 - breakWidth/2) + " y: " + (ypx + drawPosY) );
                		
                	} else if (anchor.isEnd()){
                		/* Text align RIGHT */
                		layout.draw(g2, xpx - breakWidth + extra, ypx + drawPosY );
                	}
                }

                /* Move y-coordinate in preparation for next layout */
                drawPosY += layout.getDescent() + layout.getLeading();
                
                requiredHeight = new Pixel( (int) (drawPosY + ypx) );
            }
		}
	}
	
	public void resize( int direction, int amount ){
		this.font.size = new Unit( font.size.getValue() + amount  );
	}	
	
}