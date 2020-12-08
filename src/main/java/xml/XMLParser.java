package xml;

import java.io.File;

/**
 *   Abstract class implemented by the parsers. 
 * */

public abstract class XMLParser {


	protected File file;

	public XMLParser(String filename) {
		this.file = new File(filename);
	}

}
