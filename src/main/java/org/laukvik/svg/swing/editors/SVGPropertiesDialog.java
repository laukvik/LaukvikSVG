package org.laukvik.svg.swing.editors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import org.laukvik.svg.shape.SVG;
import org.laukvik.svg.swing.editors.geometry.UnitEditor;
import org.laukvik.svg.unit.Pixel;
import org.laukvik.svg.unit.Unit;

public class SVGPropertiesDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	JTextField title;
	UnitEditor width;
	UnitEditor height;
	int status = -1;

	public SVGPropertiesDialog() {
		setResizable( false );
		
		JPanel titlePanel = new JPanel( new BorderLayout() );
		titlePanel.setBorder( BorderFactory.createTitledBorder("Title") );
		title = new JTextField();
		titlePanel.add( title );
		
		JPanel dimensionPanel = new JPanel();

		width = new UnitEditor( new Pixel(0) );
		height = new UnitEditor( new Pixel(0) );
		
		/* Add them */
        String[] labels = {  "Width", "Height" };
		JComponent [] comps = { width, height };

		dimensionPanel = new JPanel( new SpringLayout() );
		dimensionPanel.setBorder( BorderFactory.createTitledBorder("Dimension") );
		
		int numPairs = labels.length;
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel( labels[i], JLabel.TRAILING );
            dimensionPanel.add( l );
            l.setLabelFor( comps[ i ] );
            dimensionPanel.add( comps[ i ] );
        }
        SpringUtilities.makeCompactGrid( dimensionPanel,
                numPairs, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
        dimensionPanel.setMaximumSize( new Dimension( 200, numPairs*24 + 32) );
        
        
		// Add in the Ok and Cancel buttons for our dialog box
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				closeAndSave();
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				closeAndCancel();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add( okButton );
		buttonPanel.add( cancelButton );
		
		JPanel contentPanel = new JPanel( new BorderLayout() );
		contentPanel.setBorder( BorderFactory.createEmptyBorder( 20,20,20,20 ));
		contentPanel.add( titlePanel, BorderLayout.NORTH  );
		contentPanel.add( dimensionPanel, BorderLayout.CENTER  );
		
		setLayout( new BorderLayout() );
		add( contentPanel, BorderLayout.CENTER  );
		add( buttonPanel, BorderLayout.SOUTH );
		
		setSize( 400, 290 );
	}
	
	public void openDialog( SVG svg ){
		status = -1;
		width.setUnit( svg.width );
		height.setUnit( svg.height );
		title.setText( svg.getTitle() );
		setLocationRelativeTo( null );
		setModal( true );
		setVisible( true );
	}
	
	public Unit getSvgWidth(){
		return width.getUnit();
	}
	
	public Unit getSvgHeight(){
		return height.getUnit();
	}
	
	public String getSvgTitle(){
		return title.getText();
	}
	
	
	public void closeAndSave(){
		status = 1;
		dispose();
	}
	
	public void closeAndCancel(){
		status = 0;
		dispose();
	}

	public boolean acceptPressed() {
		return status == 1;
	}
	
}