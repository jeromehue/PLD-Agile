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
		XMLCityMapParser p;
		p = new XMLCityMapParser("src/main/resources/smallMap.xml");
		try {
			this.smallMap = p.parse();
		} catch (Exception e) {
			System.err.println("Error while parsing map");
			fail();
		}	
		p = new XMLCityMapParser("src/main/resources/mediumMap.xml");

		try {
			this.mediumMap = p.parse();
		} catch (Exception e) {
			System.err.println("Error while parsing map");
			fail();
		}			p = new XMLCityMapParser("src/main/resources/largeMap.xml");

		try {
			this.largeMap = p.parse();
		} catch (Exception e) {
			System.err.println("Error while parsing map");
			fail();
		}
	}


	@Test
	@DisplayName("Loading small requests")
	void smallRequestTest() throws InvalidRequestException  {
		XMLRequestParser p;
		p = new XMLRequestParser("src/main/resources/requestsSmall1.xml", this.smallMap);
		try {
			p.parse();
		} catch(Exception e) {
			System.err.println("Error while parsing requests");
			fail();
		}
		
	}

	@Test
	@DisplayName("Loading medium requests")
	void mediumRequestTest() throws InvalidRequestException  {
		XMLRequestParser p;
		p = new XMLRequestParser("src/main/resources/requestsMedium3.xml", this.mediumMap);
		try {
			p.parse();
		} catch(Exception e) {
			System.err.println("Error while parsing requests");
			fail();
		}
	}

	@Test
	@DisplayName("Loading large requests")
	void largeRequestTest() throws InvalidRequestException {
		XMLRequestParser p;
		p = new XMLRequestParser("src/main/resources/requestsLarge7.xml", this.largeMap);
		try {
			p.parse();
		} catch(Exception e) {
			System.err.println("Error while parsing requests");
			fail();
		}
	}

	@Test
	@DisplayName("Loading requests with unknown intersections")
	void missingLocations() {
		XMLRequestParser p;
		p = new XMLRequestParser("src/main/resources/requestsLarge7.xml", this.smallMap);
		assertThrows(InvalidRequestException.class, () -> {
			p.parse();
	    });
	}

}
