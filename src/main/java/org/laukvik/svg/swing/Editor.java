package org.laukvik.svg.swing;

import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.laukvik.svg.SVGSource;
import org.laukvik.svg.parser.Parser;
import org.laukvik.svg.shape.Geometry;
import org.laukvik.svg.shape.Image;
import org.laukvik.svg.shape.SVG;
import org.laukvik.svg.swing.actions.SVGAction;
import org.laukvik.svg.swing.editors.SVGPropertiesDialog;
import org.laukvik.svg.swing.editors.SVGPropertiesPanel;
import org.laukvik.svg.swing.editors.geometry.PropertyPanel;
import org.laukvik.svg.unit.Pixel;
import org.laukvik.swing.platform.CrossPlatformUtilities;
import org.laukvik.swing.platform.osx.OSXAdapter;
import org.laukvik.swing.transparency.MagnifyingDialog;

/**
 * TODO - Text need to support outline TODO - Add support for linking TODO - Add
 * support for CM TODO - Lage image caching av bilder som er skalert TODO - Both
 * stroke/fill combo needs red line when not selected TODO - Moving groups must
 * work TODO - Stroke needs to be duplicated and not share stroke TODO - Show
 * paintable area with different colors
 *
 * TODO - Controllers needs to work when no group is selected TODO - Magnifier
 * must work TODO - Addd right click TODO - Selection tool must work TODO - Muse
 * guidelines er forsvunnet TODO - Create 10 strokes with 10 fills TODO - Lage
 * stilig progressbar med SVG TODO - Lage not found symbol TODO - Lage undo
 *
 * @author morten
 *
 */
public class Editor extends JFrame implements DropTargetListener, SVGChangeListener, WindowListener {

    private static final long serialVersionUID = 1L;
    private SVGEditablePanel panel;
    private SVGTree tree;
    private SVGTreeModel treeModel;
    private SVGScrollPane scroll;
    private SVGMenuBar menuBar;
    private DrawingControllerToolbar controllerToolbar;
    private SVGPropertiesPanel svgPropertiesPanel;
    private MagnifyingDialog magnifier;
    private LayerToolbar layerToolbar;
    private final int LOADING = -1;
    private final int CHANGED = 1;
    private final int UNCHANGED = 0;
    private int changeStatus = 0;

    public Editor() {
        super();
        registerForMacOSXEvents();
        initComponents();
        handleNewFile();
    }

    public void initComponents() {
        addWindowListener(this);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        menuBar = new SVGMenuBar(this);
        setJMenuBar(menuBar);
        controllerToolbar = new DrawingControllerToolbar();
        controllerToolbar.setFloatable(false);
        panel = new SVGEditablePanel();
        scroll = new SVGScrollPane(panel);
        tree = new SVGTree(new SVGTreeModel());

        svgPropertiesPanel = new SVGPropertiesPanel(this);
        JScrollPane propScroll = new JScrollPane(svgPropertiesPanel);
        propScroll.setBorder(null);
        propScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        layerToolbar = new LayerToolbar(this);

        JPanel treeAndtoolbar = new JPanel(new BorderLayout());
        treeAndtoolbar.add(layerToolbar, BorderLayout.NORTH);
        treeAndtoolbar.add(new JScrollPane(tree), BorderLayout.CENTER);

        JSplitPane splitTreeAndProperties = new JSplitPane(JSplitPane.VERTICAL_SPLIT, treeAndtoolbar, propScroll);
        splitTreeAndProperties.setBorder(null);
        splitTreeAndProperties.setDividerLocation(200);
        splitTreeAndProperties.setOneTouchExpandable(true);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, splitTreeAndProperties);
        split.setEnabled(false);
        split.setOneTouchExpandable(true);
        split.setResizeWeight(1);
        split.setBorder(null);

        ((JComponent) split.getLeftComponent()).setBorder(null);
        setLayout(new BorderLayout());
        add(split, BorderLayout.CENTER);
        add(controllerToolbar, BorderLayout.SOUTH);

        split.setDividerLocation(750);

        new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this, true, null);


        /* Make all properties panel listen for svg events */
        panel.addSelectionListener(svgPropertiesPanel);
        panel.addSelectionListener(layerToolbar);
        panel.addLoadListener(layerToolbar);
        panel.addControllerListener(controllerToolbar);
        panel.addLoadListener(controllerToolbar);
        panel.addSelectionListener(controllerToolbar);
        panel.addChangeListener(tree);
        panel.addLoadListener(tree);
        for (PropertyPanel pp : svgPropertiesPanel.listPanels()) {
            panel.addChangeListener(pp);
            panel.addLoadListener(pp);
            panel.addSelectionListener(pp);
        }
        panel.addChangeListener(this);

        setSize(1100, 800);
//		setLocationRelativeTo( null );
        setLocation(0, 0);
        setVisible(true);
    }

    public Icon getIcon() {
        return SVGAction.getIcon("paint32x32.png");
    }

    public boolean confirmNotSaved() {
        String msg = "Do you want to save your changes?";
        if (isChanged()) {
            int n = JOptionPane.showConfirmDialog(this, msg, "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, getIcon());
            if (n == JOptionPane.CANCEL_OPTION) {
                return false;
            } else if (n == JOptionPane.YES_OPTION) {
                int saved = handleSaveFileAS();
                if (saved == JFileChooser.CANCEL_OPTION) {
                    return false;
                }
            }
        }
        return true;
    }

    private void loadSVG(SVG svg) {




        setIsLoading();
        treeModel = new SVGTreeModel(svg);
        tree.setModel(treeModel);
        panel.setSVG(svg);

        /* Make sure SVG element is selected */
        tree.setSelectionRow(0);
        setIsUnchanged();
    }

    public void setWindowTitle() {
        SVG svg = panel.getSVG();
        if (svg.source == null) {
            setTitle((isChanged() ? "* " : "") + "Untitled");
        } else {
            setTitle((isChanged() ? "* " : "") + (svg.getTitle() == null ? "Untitled" : svg.getTitle()) + " - " + svg.source.toExternalForm());
        }
    }

    public void loadSVG(final SVGSource url) throws Exception {
        final ParseProgressDialog progress = new ParseProgressDialog(this, "LoadSVG");
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Parser r = new Parser();
                    r.addParseListener(progress);
                    r.parse(url);
                    loadSVG(r.getSVG());
                } catch (Exception e) {
                    progress.setText(e.getMessage());
                } finally {
                    progress.closeDialog();
                    setVisible(true);
                }
            }
        });

        t.start();
        progress.showDialog();
    }

    public void handleOpenURL() {
        if (confirmNotSaved()) {
            String url = JOptionPane.showInputDialog(this, "", "Open URL", JOptionPane.PLAIN_MESSAGE);
            if (url == null) {
            } else {
                try {
                    loadSVG(new SVGSource(url));
                    menuBar.fileLoaded(url);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Open URL", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void handleFileSave() {
        if (panel.getSVG().source == null || panel.getSVG().source.isFile() == false) {
            handleSaveFileAS();
        } else {
            String filename = "";
            menuBar.fileSaved(filename);
            Parser parser = new Parser();
            try {
                parser.write(panel.getSVG(), panel.getSVG().source.getFile());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int handleSaveFileAS() {
        final JFileChooser fc = new JFileChooser();
        //In response to a button click:
        int returnVal = fc.showSaveDialog(this);


        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Parser parser = new Parser();
            try {
                parser.write(panel.getSVG(), fc.getSelectedFile());
                menuBar.fileSaved(fc.getSelectedFile().getAbsolutePath());
                panel.getSVG().source = new SVGSource(fc.getSelectedFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnVal;
    }

    public int handleExportFile() {
        final JFileChooser fc = new JFileChooser();
        fc.resetChoosableFileFilters();
        fc.addChoosableFileFilter(new ExtensionFileFilter("JPEG", new String[]{".jpg"}));
        fc.addChoosableFileFilter(new ExtensionFileFilter("PNG", new String[]{".png"}));
        //In response to a button click:
        int returnVal = fc.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                ExtensionFileFilter eff = (ExtensionFileFilter) fc.getFileFilter();
                String format = eff.getExtension();
                if (file.getName().endsWith(format)) {
                } else {
                    file = new File(file.getParentFile(), file.getName() + "" + format);
                }
                panel.save(format.substring(1), file);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Open URL", JOptionPane.ERROR_MESSAGE);
            }
        }
        return returnVal;
    }

    public void handleOpenFile(String filename) {
        if (confirmNotSaved()) {
        }
    }

    public void handleOpenFile() {
        if (confirmNotSaved()) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new SVGFileFilter());
            chooser.showOpenDialog(this);
            File file = chooser.getSelectedFile();
            if (file == null) {
            } else {
                try {
                    loadSVG(new SVGSource(file));
                    menuBar.fileLoaded(file.getCanonicalPath());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Open File", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void handleOpenFile(File[] files) {
        SVGFileFilter svgFilter = new SVGFileFilter();
        SVGImageFilter imageFilter = new SVGImageFilter();

        for (File file : files) {
            if (svgFilter.accept(file)) {
                if (confirmNotSaved()) {

                    try {
                        loadSVG(new SVGSource(file));
                    } catch (Exception e) {
                        showError(e);
                    }
                    return;

                }

            }
            if (imageFilter.accept(file)) {
                importImageToSVG(file);
            }
        }
    }

    public void about() {
        AboutDialog dialog = new AboutDialog();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public void preferences() {
    }

    public void handleQuit() {
        if (confirmNotSaved()) {
            System.exit(0);
        }
    }

    public void dragEnter(DropTargetDragEvent dtde) {
    }

    public void dragExit(DropTargetEvent dte) {
    }

    public void dragOver(DropTargetDragEvent dtde) {
    }

    @SuppressWarnings("unchecked")
    public void drop(DropTargetDropEvent dtde) {
        if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            dtde.acceptDrop(dtde.getDropAction());
            Transferable t = dtde.getTransferable();
            try {
                List<File> fileList = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
                File[] files = new File[fileList.size()];
                fileList.toArray(files);
                handleOpenFile(files);
            } catch (Exception e) {
                showError(e);
            }

        } else {
            dtde.rejectDrop();
            showError("File not supported!");
        }
    }

    public void showError(Exception e) {
    }

    public void showError(String message) {
    }

    /**
     * Import an image file to the SVG canvas
     *
     * @param file
     */
    public void importImageToSVG(File file) {
        log("importImageToSVG: " + file);
        try {
            Image img = new Image(new Pixel(0), new Pixel(0), file.getAbsolutePath());
            panel.getSVG().add(img);
            panel.fireSvgAdded(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    public void handleNewFile() {
        if (confirmNotSaved()) {
            loadSVG(new SVG());
        }
    }

    public void handleOpenProperties() {
        SVGPropertiesDialog dialog = new SVGPropertiesDialog();
        dialog.openDialog(panel.getSVG());
        if (dialog.acceptPressed()) {
            panel.getSVG().width = dialog.getSvgWidth();
            panel.getSVG().height = dialog.getSvgHeight();
            panel.getSVG().setTitle(dialog.getSvgTitle());
        } else {
        }
    }

    public void log(Object msg) {
//		System.out.println( Editor.class.getName() + ": " + msg );
    }

    public void handleViewSource() {
        new SVGSourceDialog(panel.getSVG());
    }

    /**
     * Generic registration with the Mac OS X application menu Checks the
     * platform, then attempts to register with the Apple EAWT See
     * OSXAdapter.java to see how this is done without directly referencing any
     * Apple APIs
     */
    public void registerForMacOSXEvents() {
        if (CrossPlatformUtilities.isMacOSX()) {
            try {
                // Generate and register the OSXAdapter, passing it a hash of all the methods we wish to
                // use as delegates for various com.apple.eawt.ApplicationListener methods
                OSXAdapter.setQuitHandler(this, getClass().getDeclaredMethod("handleQuit", (Class[]) null));
                OSXAdapter.setAboutHandler(this, getClass().getDeclaredMethod("about", (Class[]) null));
                OSXAdapter.setPreferencesHandler(this, getClass().getDeclaredMethod("preferences", (Class[]) null));
                OSXAdapter.setFileHandler(this, getClass().getDeclaredMethod("handleOpenFile", new Class[]{String.class}));
            } catch (Exception e) {
                System.err.println("Error while loading the OSXAdapter:");
                e.printStackTrace();
            }
        }
    }

    public void setIsChanged() {
        changeStatus = CHANGED;
        setWindowTitle();
    }

    public void setIsLoading() {
        changeStatus = LOADING;
    }

    public void setIsUnchanged() {
        changeStatus = UNCHANGED;
        setWindowTitle();
    }

    public boolean isLoading() {
        return changeStatus == LOADING;
    }

    public boolean isChanged() {
        return changeStatus == CHANGED;
    }

    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CrossPlatformUtilities.setCrossPlatformProperties();
                @SuppressWarnings("unused")
                Editor editor = new Editor();
//				try {
//					editor.loadSVG( new SVGSource( "java:///org/laukvik/trainer/svg/pectoralis_major.xml" ) );
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
            }
        });
    }

    /**
     *
     */
    public void handleMagnifier() {
        if (magnifier == null) {
            magnifier = new MagnifyingDialog(panel);
        } else {
            magnifier.dispose();
            magnifier = null;
        }
    }

    public boolean isMagnifierVisible() {
        return magnifier != null;
    }

    public void svgChanged(Geometry geometry) {
        if (isLoading()) {
        } else {
            setIsChanged();
        }
    }

    public void svgLoaded(SVGEditablePanel panel) {
    }

    public void svgUnLoaded(SVGEditablePanel panel) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        handleQuit();
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }

    public void svgMovedBack(Geometry geometry, int fromIndex) {
    }

    public void svgMovedFront(Geometry geometry, int fromIndex) {
    }

    public void svgAdded(Geometry... geometry) {
        // TODO Auto-generated method stub
    }

    public void svgRemoved(Geometry... geometry) {
        // TODO Auto-generated method stub
    }
}
