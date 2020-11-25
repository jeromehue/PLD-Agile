package algoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import modele.Intersection;
import modele.Segment;

import org.junit.jupiter.api.Test;

import algo.CompleteGraph;
import algo.Pcc;

class testPcc {

	@Test
	void test() {
		Pcc pcc = new Pcc();
		CompleteGraph graph = pcc.init();
		Intersection inter1 = new Intersection(new Long(1), 0.0, 0.0, new ArrayList<Segment>() ); 
		Intersection inter6 = new Intersection(new Long(6), 0.0, 0.0, new ArrayList<Segment>() ); 
		pcc.getRoads(inter1, inter6);
		//System.out.println(graph.toString());

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
