package xml;

import java.io.File;

public abstract class XMLParser {

	File file;

	public XMLParser(String filename) {
		this.file = new File(filename);
	}

}
