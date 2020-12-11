package xml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
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

	@Test
	@DisplayName("Loading a small map with missing longitude")
	void missingIntersection() {
		XMLCityMapParser p;
		p = new XMLCityMapParser("src/test/resources/smallMap_missing_longitude.xml");
		assertThrows(NumberFormatException.class, () -> {
			p.parse();
		});
	}

}
