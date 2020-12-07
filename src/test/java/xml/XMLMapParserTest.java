package xml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

class XMLMapParserTest {

	@Test
	@DisplayName("Loading a small map")
	void smallMapTest() {
		XMLCityMapParser p;
		p = new XMLCityMapParser("src/main/resources/smallMap.xml");
		try {
			p.parse();
		} catch (Exception e) {
			System.err.println("Error while parsing map");
			fail();
		}
	}

	@Test
	@DisplayName("Loading a medium map")
	void mediumMapTest() {
		XMLCityMapParser p;
		p = new XMLCityMapParser("src/main/resources/mediumMap.xml");
		try {
			p.parse();
		} catch (Exception e) {
			System.err.println("Error while parsing map");
			fail();
		}
	}

	@Test
	@DisplayName("Loading a large map")
	void largeMapTest() {
		XMLCityMapParser p;
		p = new XMLCityMapParser("src/main/resources/largeMap.xml");
		try {
			p.parse();
		} catch (Exception e) {
			System.err.println("Error while parsing map");
			fail();
		}
	}

}
