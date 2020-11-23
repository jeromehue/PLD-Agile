package tsp;

import pcc.Pcc;

public class RunTSP {
	public static void main(String[] args) {
		/*TSP tsp = new TSP1();
		for (int nbVertices = 8; nbVertices <= 16; nbVertices += 2){
			System.out.println("Graphs with "+nbVertices+" vertices:");
			Graph g = new CompleteGraph(nbVertices);
			long startTime = System.currentTimeMillis();
			tsp.searchSolution(20000, g);
			System.out.print("Solution of cost "+tsp.getSolutionCost()+" found in "
					+(System.currentTimeMillis() - startTime)+"ms : ");
			for (int i=0; i<nbVertices; i++)
				System.out.print(tsp.getSolution(i)+" ");
			System.out.println("0");
		}*/
		
		Pcc unPcc = new Pcc();
		System.out.println(unPcc.init().toString());
	}

}
