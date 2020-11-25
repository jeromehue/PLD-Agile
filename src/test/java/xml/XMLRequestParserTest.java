package xml;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import modele.CityMap;

@TestInstance(Lifecycle.PER_CLASS)
class XMLRequestParserTest {

	private CityMap smallMap;
	private CityMap mediumMap;
	private CityMap largeMap;
	
	@BeforeAll
	void setup() {
		System.out.println("setup()");
		XMLCityMapParser p;
		p = new XMLCityMapParser("src/main/resources/smallMap.xml");
		this.smallMap = p.parse();
		p = new XMLCityMapParser("src/main/resources/mediumMap.xml");
		this.mediumMap = p.parse();
		p = new XMLCityMapParser("src/main/resources/largeMap.xml");
		this.largeMap = p.parse();
	}

	@Test
	@DisplayName("Loading small requests")
	void smallRequestTest() {
		XMLRequestParser p;
		p = new XMLRequestParser("src/main/resources/requestsSmall1.xml", this.smallMap);
		p.parse();
	}

	@Test
	@DisplayName("Loading medium requests")
	void mediumRequestTest() {
		XMLRequestParser p;
		p = new XMLRequestParser("src/main/resources/requestsMedium3.xml", this.mediumMap);
		p.parse();
	}

	@Test
	@DisplayName("Loading large requests")
	void largeRequestTest() {
		XMLRequestParser p;
		p = new XMLRequestParser("src/main/resources/requestsLarge7.xml", this.largeMap);
		p.parse();
	}

	@Test
	@DisplayName("Loading requests with unknown intersections")
	void missingLocations() throws InvalidRequestException {
		assertThrows(InvalidRequestException.class, () -> {
			XMLRequestParser p;
			p = new XMLRequestParser("src/main/resources/requestsLarge7.xml", this.smallMap);
			p.parse();
	    });
	}

}
