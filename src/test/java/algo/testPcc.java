package algo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pcc.Pcc;

class testPcc {

	@Test
	void test() {
		Pcc pcc = new Pcc();
		System.out.println(pcc.init().toString());
		assertTrue(pcc.init().toString() != null && pcc.init().toString() != "");
	}

}
