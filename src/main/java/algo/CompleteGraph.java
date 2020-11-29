package algo;

import java.util.List;
import java.util.HashMap;

import modele.Intersection;

public class CompleteGraph implements Graph {
	private int nbVertices;
	private double[][] costsMatrix;
	private HashMap<Long, Integer> index; // Id d'intersection, index dans le tableau de couts
	
	/**
	 * Create a complete directed graph such that each edge has a weight within [MIN_COST,MAX_COST]
	 * @param nbVertices
	 */
	public CompleteGraph(List<Intersection> startVertices){
		this.nbVertices = startVertices.size();
		index = new HashMap<Long, Integer>();
		
		this.costsMatrix = new double[nbVertices][nbVertices];
		for(int i = 0 ; i < nbVertices ; ++i) {
			Intersection inter = startVertices.get(i);
			index.put(inter.getId(), i); // initialisation de index

			for(int j = 0 ; j < nbVertices ; ++j) {
				costsMatrix[i][j] = 0.0;
			}
		}
	}
	
	public void updateCompleteGraph(Long startId, HashMap<Long, IntersectionPcc> costs, List<Intersection> start) {
		Integer startIndex = index.get(startId);
		for(Intersection startIntersection : start) {
			costsMatrix[startIndex][index.get(startIntersection.getId())] = costs.get(startIntersection.getId()).getCost();
		}
	}

	@Override
	public int getNbVertices() {
		return nbVertices;
	}

	@Override
	public double getCost(int i, int j) {
		if (i<0 || i>=nbVertices || j<0 || j>=nbVertices)
			return -1;
		return costsMatrix[i][j];
	}

	@Override
	public boolean isArc(int i, int j) {
		if (i<0 || i>=nbVertices || j<0 || j>=nbVertices)
			return false;
		return i != j;
	}
	
	@Override
	public String toString() {
		String ret = "";
		for(int i = 0 ; i < nbVertices ; ++i) {
			ret += "Cout pour aller de " + i + " a :\n";
			for(int j = 0 ; j < nbVertices ; ++j) {
				if(costsMatrix[i][j] == Double.MAX_VALUE) {
					ret += j + " = Unreachable\n";
				} else {
					ret += j + " = " + costsMatrix[i][j] + "\n";
				}
			}
		}
		return ret;
	}

}
