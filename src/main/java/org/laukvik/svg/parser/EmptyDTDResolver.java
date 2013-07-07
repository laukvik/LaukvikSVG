package org.laukvik.svg.parser;

import java.io.IOException;
import java.io.StringReader;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class EmptyDTDResolver implements EntityResolver {

	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        if (systemId.endsWith(".dtd"))
        {
            StringReader stringInput = new StringReader(" ");
            return new InputSource(stringInput);
        }
        else
        {
            return null;    // default behavior
        }
	}

	
	
}