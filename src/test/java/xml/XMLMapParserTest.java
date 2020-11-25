package xml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class XMLMapParserTest {

	@Test
	@DisplayName("Loading a small map")
	void smallMapTest() {
		XMLCityMapParser p;
		p = new XMLCityMapParser("src/main/resources/smallMap.xml");
		p.parse();
	}
	
	@Test
	@DisplayName("Loading a medium map")
	void mediumMapTest() {
		XMLCityMapParser p;
		p = new XMLCityMapParser("src/main/resources/mediumMap.xml");
		p.parse();
	}
	@Test
	@DisplayName("Loading a large map")
	void largeMapTest() {
		XMLCityMapParser p;
		p = new XMLCityMapParser("src/main/resources/largeMap.xml");
		p.parse();
	}


}
