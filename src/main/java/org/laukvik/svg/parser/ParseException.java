package org.laukvik.svg.parser;

import org.w3c.dom.Node;

public class ParseException extends Exception {

	private static final long serialVersionUID = 1L;
	private Node n;

	public ParseException(Node n) {
		super("Could not parse node: " + n );
		this.n = n;
	}
	
	public Node getNode(){
		return n;
	}
	
}