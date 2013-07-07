package org.laukvik.svg.swing;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.laukvik.svg.swing.actions.AboutAction;
import org.laukvik.svg.swing.actions.CopyAction;
import org.laukvik.svg.swing.actions.CutAction;
import org.laukvik.svg.swing.actions.EditPropertiesAction;
import org.laukvik.svg.swing.actions.FileExportAction;
import org.laukvik.svg.swing.actions.FileNewAction;
import org.laukvik.svg.swing.actions.FileOpenAction;
import org.laukvik.svg.swing.actions.FileOpenRecentAction;
import org.laukvik.svg.swing.actions.FileSaveAction;
import org.laukvik.svg.swing.actions.FileSaveAsAction;
import org.laukvik.svg.swing.actions.MagnifierAction;
import org.laukvik.svg.swing.actions.OpenURLAction;
import org.laukvik.svg.swing.actions.PasteAction;
import org.laukvik.svg.swing.actions.SVGAction;
import org.laukvik.svg.swing.actions.ViewSourceAction;
import org.laukvik.swing.recent.RecentFiles;
import org.laukvik.swing.recent.RecentListener;

public class SVGMenuBar extends JMenuBar implements RecentListener {

	private static final long serialVersionUID = 1L;
	private JMenu recent;
	private RecentFiles recentList;
	private Editor editor;
	
	JMenuItem cutItem, copyItem, pasteItem;
	private SVGAction cutAction, copyAction, pasteAction;
	
	public SVGMenuBar( Editor editor ){
		super();
		this.editor = editor;
		recentList = new RecentFiles( Editor.class );
		
		JMenu file = new JMenu("File");
		file.add( new JMenuItem(new FileNewAction(editor)) );
		file.add( new JMenuItem(new FileOpenAction(editor)) );

		
		recent = new JMenu("Open recent...");
		int max = recentList.getItems().size();
		for (int x=0; x<max; x++){
			FileOpenRecentAction openRecent = new FileOpenRecentAction(editor,recentList.getItems().get( x ));
			recent.add( openRecent );
		}
		
		file.add( recent );
		file.add( new JMenuItem(new OpenURLAction(editor)) );
		
		
		
		file.addSeparator();
		file.add( new JMenuItem(new FileSaveAction(editor)) );
		file.add( new JMenuItem(new FileSaveAsAction(editor)) );
		file.addSeparator();
		file.add( new JMenuItem(new FileExportAction(editor)) );
		
		cutAction = new CutAction();
		copyAction = new CopyAction();
		pasteAction = new PasteAction();
		
		JMenu edit = new JMenu("Edit");
		cutItem = edit.add( new JMenuItem( cutAction ) );
		copyItem = edit.add( new JMenuItem( copyAction ) );
		pasteItem = edit.add( new JMenuItem( pasteAction ) );
		
		edit.addSeparator();
		edit.add( new JMenuItem(new EditPropertiesAction()) );
		
		
		JMenu view = new JMenu("View");
		view.add( new JMenuItem(new ViewSourceAction(editor)) );
		view.add( new JCheckBoxMenuItem(new MagnifierAction(editor)) );
		
		JMenu about = new JMenu("About");
		about.add( new JMenuItem(new AboutAction(editor)) ); 
		add( file );
		add( edit );
		add( view );
		add( about );
	}

	public void fileLoaded(String filename) {
		recentList.add(filename);
		recentChanged();
	}

	public void fileSaved(String filename) {
		recentList.add(filename);
		recentChanged();
	}
	
	public void recentChanged(){
		recent.removeAll();
		int max = recentList.getItems().size();
		for (int x=0; x<max; x++){
			FileOpenRecentAction openRecent = new FileOpenRecentAction(editor,recentList.getItems().get( x ));
			recent.add( openRecent );
		}
	}
	
}