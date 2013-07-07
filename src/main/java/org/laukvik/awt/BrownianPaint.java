package org.laukvik.awt;

//$Header: /usr/local/cvsrep/LaukvikSVG/src/org/laukvik/awt/BrownianPaint.java,v 1.1 2010/05/02 12:01:45 mortenlaukvik Exp $
//
// BrownianPaint
//
// A demonstration of a user-defined java.awt.Paint (sub-) class.
// This Paint implements a 2 dimensional fractional brownian motion texture.
// The algorithm is taken from the BRL-CAD sources (files libbn/noise.c
// and liboptical/sh_camo.c).
// See www.brl-cad.org

import java.awt.Color;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

class BrownianPaint implements Paint {
	
	static AffineTransform defaultXForm = AffineTransform.getScaleInstance(	0.05, 0.05);
	private double h;
	private double lacunarity;
	private double octaves;
	// Colors a and b stored in component form.
	float[] colorA;
	float[] colorB;
	private AffineTransform xform;
	// Spectral weights, computed once for each instance and used many
	// times by getRaster.
	private float[] weight;
	private AffineTransform lacunarScale;

	public BrownianPaint(Color aArg, Color bArg, double hArg,
			double lacunarityArg, double octavesArg) {
		h = hArg;
		lacunarity = lacunarityArg;
		octaves = octavesArg;
		colorA = aArg.getComponents(null);
		colorB = bArg.getComponents(null);
		xform = defaultXForm;
		weight = SpectralWeight(h, lacunarity, octaves);
		lacunarScale = AffineTransform.getScaleInstance(lacunarity, lacunarity);
	}

	public BrownianPaint(Color aArg, Color bArg, double hArg,
			double lacunarityArg, double octavesArg, AffineTransform xformArg) {
		this(aArg, bArg, hArg, lacunarityArg, octavesArg);
		if (xformArg != null) {
			xform = xformArg;
		}
	}

	static float[] SpectralWeight(double h, double lacunarity, double octaves) {
		float[] weight = new float[(int) Math.ceil(octaves)];
		double frequency = 1.0;

		for (int i = 0; i < octaves; ++i) {
			weight[i] = (float) Math.pow(frequency, -h);
			frequency *= lacunarity;
		}
		return weight;
	}

	public PaintContext createContext(ColorModel cm, Rectangle deviceBounds,
			Rectangle2D userBounds, AffineTransform xform, RenderingHints hints) {
		return new Context(cm, xform);
	}

	public int getTransparency() {
		return OPAQUE;
	}

	public class Context implements PaintContext {

		public Context(ColorModel cm_, AffineTransform xform_) {
		}

		public void dispose() {
		}

		public ColorModel getColorModel() {
			return ColorModel.getRGBdefault();
		}

		// getRaster makes heavy use of the enclosing BrownianPaint instance
		public Raster getRaster(int xOffset, int yOffset, int w, int h) {
			WritableRaster raster = getColorModel()
					.createCompatibleWritableRaster(w, h);

			// Some temporary arrays, allocated outside the loops.
			float[] color = new float[4];
			// 2D point stored in array format for convenience of using
			// AffineTransform.transform(float[], _)
			float[] p = new float[2];

			// Row major traversal, x coordinate changes fastest.
			for (int j = 0; j < h; ++j) {
				for (int i = 0; i < w; ++i) {
					p[0] = i + xOffset;
					p[1] = j + yOffset;

					xform.transform(p, 0, p, 0, 1);

					// The inner loop accumulates a Perlin noise value for each
					// "octave".  Octave is a slight misnomer since each one
					// encompasses a range equal to the lacunarity which isn't
					// necessarily 2.
					float t = 0;
					double z = 2.718; // Essentially arbitrary.

					int o; // Declared outside the loop, so that we can use it later.
					for (o = 0; o < octaves; ++o) {
						t += (float) ImprovedNoise.noise(p[0], p[1], z)
								* weight[o];
						lacunarScale.transform(p, 0, p, 0, 1);
					}
					double remainder = octaves - (int) octaves;
					if (remainder != 0) {
						t += (float) ImprovedNoise.noise(p[0], p[1], z)
								* weight[o] * remainder;
					}
					colorise(t, color);
					raster.setPixel(i, j, color);
				}
			}
			return raster;
		}

		void colorise(float t, float[] color) {
			// t is centred on 0.0 with (conceptually) infinite range.
			// Scale and clamp to [0,1].
			t = (t + 1) / 2;
			if (t > 1.0) {
				t = 1.0f;
			}
			if (t < 0.0) {
				t = 0.0f;
			}

			for (int b = 0; b < 4; ++b) {
				color[b] = lerp(t, colorA[b], colorB[b]);
				// The multiplication assumes the default RGB model, 8 bits
				// per band (channel).
				color[b] *= 0xff;
			}
			return;
		}

		float lerp(float t, float a, float b) {
			return a + t * (b - a);
		}
	}
}