package xml;

import java.io.File;

/**
 *   Abstract class implemented by the parsers. 
 * */

public abstract class XMLParser {

	/**
	 * The XML file to be parsed.
	 */
	protected File file;

	/**
	 * Default constructor for this class.
	 * 
	 * @param filename The name of the file to be parsed.
	 */
	public XMLParser(String filename) {
		this.file = new File(filename);
	}

}
