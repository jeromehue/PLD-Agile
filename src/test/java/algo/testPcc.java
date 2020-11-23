package algo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pcc.Pcc;
import tsp.CompleteGraph;

class testPcc {

	@Test
	void test() {
		Pcc pcc = new Pcc();
		CompleteGraph graph = pcc.init();
		System.out.println(graph.toString());

		for(int i = 0 ; i < graph.getNbVertices() ; ++i) {
			for(int j = 0 ; j < graph.getNbVertices() ; ++j) {
				if(i == j) {
					assertTrue(graph.getCost(i, j) == 0.0);
				} else {
					assertTrue(graph.getCost(i, j) != 0.0);
				}
			}
		}
	}
}
