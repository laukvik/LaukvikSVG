package org.laukvik.svg.swing;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class SVGFileFilter extends FileFilter {


	public boolean accept(File f) {
		if (f == null){
			return false;
		} else if (f.isDirectory()){
			return false;
		} else if (f.getName().endsWith(".svg")){
			return true;
		} else if (f.getName().endsWith(".svgz")){
			return true;
		} else if (f.getName().endsWith(".xml")){
			return true;
		}
		return false;
	}

	public String getDescription() {
		return "SVG files (svg, svgz, xml)";
	}

}
