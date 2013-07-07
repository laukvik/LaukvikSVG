package org.laukvik.svg.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Group;
import org.laukvik.svg.swing.SVGEditablePanel;
import org.laukvik.svg.swing.controllers.TextController;

public class UnGroupAction extends ControllerAction {

	private static final long serialVersionUID = 1L;
	
	public UnGroupAction() {
		super( new TextController() );
		putValue( Action.NAME, "UnGroup" );
		putValue( Action.ACTION_COMMAND_KEY, "ungroup" );
		putValue( Action.SMALL_ICON, getIcon( "ungroup.tiff" ) );
		putValue( Action.SHORT_DESCRIPTION, "<html><b>Ungroup</b><br>Moves the items in this group into its parent and removes the group</html>" ); 
	}

	public void actionPerformed(ActionEvent e) {
		if (getSvgPanel() == null){
			
		} else {
			if (getSvgPanel().getSelected() instanceof Group){
				Group group = (Group) getSvgPanel().getSelected();
				
				Geometry [] gems = getSvgPanel().getSVG().splitGroup( group );
				SVGEditablePanel editable = (SVGEditablePanel) getSvgPanel(); 
				editable.clearSelection();
				getSvgPanel().fireSvgRemoved( group );
				getSvgPanel().fireSvgAdded( gems );
			}
		}
	}
	
}