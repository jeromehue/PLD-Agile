package xml;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import modele.Request;

class XMLRequestParserTest {

	@Test
	void test() {
		
		System.out.println("Début du test");
		XMLRequestParser p = new XMLRequestParser("src/main/resources/requestsSmall1.xml");
		Request r = p.parse();
		System.out.println(r);
		fail("Not yet implemented");
	}

}
