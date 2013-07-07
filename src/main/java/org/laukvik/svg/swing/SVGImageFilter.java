package org.laukvik.svg.swing;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class SVGImageFilter extends FileFilter {

	public boolean accept(File f) {
		if (f == null){
			return false;
		} else if (f.isDirectory()){
			return false;
		} else if (f.getName().endsWith(".jpg")){
			return true;
		} else if (f.getName().endsWith(".gif")){
			return true;
		} else if (f.getName().endsWith(".png")){
			return true;
		} else if (f.getName().endsWith(".tiff")){
			return true;
		} else if (f.getName().endsWith(".tif")){
			return true;
		} else if (f.getName().endsWith(".tga")){
			return true;
		}
		return false;
	}

	public String getDescription() {
		return "SVG files (jpg, gif, png, tiff)";
	}

}
