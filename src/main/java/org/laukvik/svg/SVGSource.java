package org.laukvik.svg;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * SVG
 * 
 * @author morten
 *
 */
public class SVGSource{

	public final static int NONE = 0;
	public final static int FILE = 1;
	public final static int URL  = 2;
	public final static int JAVA = 3;
	
	private String address;
	private int type;
	
	/**
	 * 
	 * 
	 * @param address
	 * @param type
	 */
	public SVGSource( String address, int type ){
		if (type == FILE || type == URL || type == JAVA){
			this.type = type;
			this.address = address;
		} else {
			this.type = NONE;
			this.address = address;
		}
	}
	
	public SVGSource( URL url ){
		this.address = url.toExternalForm().substring( 7 );
		this.type = URL;
	}
	
	public SVGSource( File file ){
		this.address = file.getAbsolutePath();
		this.type = FILE;
	}
	
	public SVGSource( String url ){
		if (url.startsWith("java://")){
			this.address = url.substring( 7 );
			this.type = JAVA;
		} else if (url.startsWith("http://")){
			this.address = url.substring( 7 );
			this.type = URL;
		} else if (url.startsWith("file://")){
			this.address = url.substring( 7 );
			this.type = FILE;
		} else {
			this.address = url;
			this.type = FILE;
		}
	}
	
	public int getType() {
		return type;
	}
	
	public boolean isFile(){
		return type == FILE;
	}
	
	public boolean isJava(){
		return type == JAVA;
	}

	public boolean isURL(){
		return type == URL;
	}
	
	public boolean isNone(){
		return type == NONE;
	}
	
	public InputStream getInputStream() throws Exception{
//		System.out.println( "JavaURL: " + this.toExternalForm() );
		if (type == JAVA){
			return this.getClass().getResourceAsStream( address );
		} else if (type == URL){
			URL url = new URL( "http://" + address );
			return url.openStream();
		} else {
			return new FileInputStream( address );
		}
	}
	
	private String getProtocol( int type ){
		switch (type){
			case FILE : return "file://";
			case URL  : return "http://";
			case JAVA : return "java://";
			case NONE : return "";
			default : return null;
		}
	}

	public String toExternalForm(){
		return getProtocol(type) + "" + address;
	}
	
	public String getFolder(){
		return address.substring( 0, address.lastIndexOf("/") );
	}
	
	public String getFilename(){
		return address.substring( address.lastIndexOf("/") + 1 );
	}

	public void setFilename( String filename ) {
		this.address = getFolder() + "/" + filename;
	}

	public String getAddress() {
		return address;
	}

	public File getFile() {
		return new File( address );
	}
	
	public File getRelativeFile( String filename ){
		return new File(  getFolder(), filename );
	}
	
}