package org.laukvik.svg.swing.editors.transform;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import org.laukvik.svg.transform.Matrix;

public class MatrixTransformEditor extends JPanel {

	private static final long serialVersionUID = 1L;
	private MatrixSpinner spina, spinb, spinc, spind, spine, spinf;

	public MatrixTransformEditor( float a, float b, float c, float d, float e, float f ){
		super();
		setLayout( new GridLayout( 2, 3 ) );
		
		spina = new MatrixSpinner();
		spinb = new MatrixSpinner();
		spinc = new MatrixSpinner();
		spind = new MatrixSpinner();
		spine = new MatrixSpinner();
		spinf = new MatrixSpinner();
		
		MatrixSpinner [] spinners = new MatrixSpinner[]{ spina, spinb, spinc, spind, spine, spinf };
		
		for (JSpinner spin : spinners){
			add( spin );
		}

	}
	
	public MatrixTransformEditor(){
		this( 0,0,0,0,0,0 );
	}

	public void setFont(Font font) {
		super.setFont(font);
		if (spinf != null){
			spina.setFont(font);
			spinb.setFont(font);
			spinc.setFont(font);
			spind.setFont(font);
			spine.setFont(font);
			spinf.setFont(font);
		}
	}

	public void setMatrix( Matrix matrix ){
		spina.setValue( matrix.a );
		spinb.setValue( matrix.b );
		spinc.setValue( matrix.c );
		spind.setValue( matrix.d );
		spine.setValue( matrix.e );
		spinf.setValue( matrix.f );
	}
	
}