package algo;
//import modele.Request;
//import modele.CityMap;
import modele.Intersection;
import modele.Segment;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.lang.Math;

public class Pcc {
	/*private CityMap cityMap;
	private Request request;
	
	public Pcc(CityMap city , Request request ){
		cityMap = city;
		this.request = request;
	}*/
	public Pcc() {};
	
	public CompleteGraph init() {
		ArrayList<Segment> l1 = new ArrayList<>();
		Intersection inter1 = new Intersection(new Long(1), 1.0, 1.0, l1);
		Intersection inter2 = new Intersection(new Long(2), 1.0, 2.0, l1);
		Intersection inter3 = new Intersection(new Long(3), 2.0, 1.0, l1);
		Intersection inter4 = new Intersection(new Long(4), 2.0, 2.0, l1);
		Intersection inter5 = new Intersection(new Long(5), 3.0, 2.0, l1);
		Intersection inter6 = new Intersection(new Long(6), 3.0, 1.0, l1);


		Segment s12 = new Segment(1.0, "", inter1, inter2);
		Segment s24 = new Segment(2.0, "", inter2, inter4);
		Segment s21 = new Segment(1.0, "", inter2, inter1);
		Segment s13 = new Segment(5.0, "", inter1, inter3);
		Segment s34 = new Segment(1.0, "", inter3, inter4);
		Segment s43 = new Segment(1.0, "", inter4, inter3);
		Segment s45 = new Segment(2.0, "", inter4, inter5);
		Segment s36 = new Segment(10.0, "", inter3, inter6);
		Segment s32 = new Segment(10.0, "", inter3, inter2);
		Segment s56 = new Segment(5.0, "", inter5, inter6);	
		Segment s54 = new Segment(2.0, "", inter5, inter4);	
		Segment s65 = new Segment(5.0, "", inter6, inter5);		
		
		inter1.addOutboundSegment(s12);
		inter1.addOutboundSegment(s13);
		inter2.addOutboundSegment(s24);
		inter2.addOutboundSegment(s21);
		inter3.addOutboundSegment(s34);
		inter3.addOutboundSegment(s36);
		inter3.addOutboundSegment(s32);
		inter4.addOutboundSegment(s43);
		inter4.addOutboundSegment(s45);
		inter5.addOutboundSegment(s56);
		inter5.addOutboundSegment(s54);
		inter6.addOutboundSegment(s65);
		
		
		List<Intersection> startVertices = new ArrayList<Intersection>();
		startVertices.add(inter1);
		startVertices.add(inter2);
		startVertices.add(inter3);
		startVertices.add(inter4);
		startVertices.add(inter5);
		startVertices.add(inter6);


		List<Intersection> allVertices = new ArrayList<Intersection>();
		allVertices.add(inter1);
		allVertices.add(inter2);
		allVertices.add(inter3);
		allVertices.add(inter4);
		allVertices.add(inter5);
		allVertices.add(inter6);
		return computePcc(allVertices, startVertices);
	}
	
	public CompleteGraph computePcc(List<Intersection> allVertices, List<Intersection> startVertices) {
		//Pbm parce que request.get... renvoie un long (id de l'intersection)
		// au lieu de l'intersection...
		/* Commentaires pour tester l'algo
		List<Intersection> startVertices = cityMap.getIntersectionsById(request.getDeliveryLocations());
		startVertices.addAll(request.getPickUpLocations());
		startVertices.addAll(request.getStartingLocation());

		List<Intersection> allVertices = cityMap.getIntersections();
		 */
		
		//TESTS
		CompleteGraph graph = new CompleteGraph(allVertices);
		
		final int END_TEST_CYCLE = 1;
		boolean allBlackStartVertices=false;	
		HashMap<Long, IntersectionPcc> allVerticesPcc = new HashMap<Long, IntersectionPcc>();
		//HashMaps pour retrouver les voisins
		PriorityQueue<IntersectionPcc> greyVertices; // tas binaire
		HashMap<Long, Long> predecessors;//<Intersection id, Intersection id du prédecesseur  >
		
		IntersectionPcc neighbor;
		IntersectionPcc minVertex;
		
		//On fait un Dijkstra par point à visiter
		for (Intersection start  :  startVertices) {
			//Les sommets gris sont initialisés à null

			/*Début de l'algorithme classique de Dijkstra*/
			
			//Pour chaque objet Intesection on crée un objet IntersectionPcc qu'on initialise 
			//avec un cout MAX et la couleur blanche
			allVerticesPcc = new HashMap<Long, IntersectionPcc>();
			predecessors = new HashMap<Long, Long>();
			for (Intersection inter : allVertices) {
				allVerticesPcc.put( inter.getId(), new IntersectionPcc(inter, 0, Double.MAX_VALUE ));
				predecessors.put(inter.getId(), null);
			}
			
			//On colorie le point de départ en gris et on met son cout à 0.
			greyVertices = new PriorityQueue<IntersectionPcc>();
			IntersectionPcc startPcc = new IntersectionPcc (start, 1, 0.0);
			allVerticesPcc.put(startPcc.getId(), startPcc);
			greyVertices.add(startPcc);
			

			allBlackStartVertices=false;
			int i=0;
			int nbBlackStartVertices=0;
			
			while(!greyVertices.isEmpty() && !allBlackStartVertices) {			
				
				minVertex = greyVertices.poll();//On prend l'intersection grise 
											    //avec un cout minimal
				System.out.println("On regarde le sommet "+minVertex.getId());
				
				//On regarde tous les voisins "neighbor" de l'intersection "minVertex"
				for(Segment s : minVertex.getOutboundSegments()) {
					neighbor = allVerticesPcc.get(s.getDestination().getId());
					if(neighbor.getColor() == 0 || neighbor.getColor()== 1) { // blanc ou gris
						//relacher (minVertex, voisin, predecesseur, cout) : 
						System.out.println("On relache "+minVertex.getId() + " et "+neighbor.getId());

						if( minVertex.getCost() + s.getLength() < neighbor.getCost() ) {
							System.out.println("On met a jour "+minVertex.getId()+" et "+neighbor.getId());
							neighbor.setCost( minVertex.getCost() + s.getLength() );
							predecessors.put(neighbor.getId(), minVertex.getId());
						}
					}
					if(neighbor.getColor() == 0) {
						neighbor.setColor(1);
						greyVertices.add(neighbor);
					}
					allVerticesPcc.put(neighbor.getId(), neighbor);//On enregistre les modifs faites à neighbor
				}
				
				///On colorie l'intersection en noir quand elle n'a plus de voisins gris ou blancs
				minVertex.setColor(2);

				allVerticesPcc.put(minVertex.getId(), minVertex);
				//On met à jour la condition de fin
				i++;
				if(i==END_TEST_CYCLE) {//Pour ne pas tester trop souvent
					i=0;
					nbBlackStartVertices=0;
					for(Intersection sVertex : startVertices ) {
						if(allVerticesPcc.get(sVertex.getId()).getColor() == 2) {
							nbBlackStartVertices++;
						}
					}
					if(nbBlackStartVertices == startVertices.size()) {
						allBlackStartVertices = true;
					}
				}
			}
			
			//sauvegarder le résultat obtenu pour un des points de départ
			for(Long idInter : allVerticesPcc.keySet()) {
				IntersectionPcc inter = allVerticesPcc.get(idInter);
				System.out.println("En partant du sommet "+startPcc.getId()+
					" le chemin le plus court pour arriver au point "+inter.getId()+
					" a une distance de "+inter.getCost()+"\n");
				graph.updateCompleteGraph(startPcc.getId(), allVerticesPcc);
			}
		}
		
		System.out.println(graph.toString());
		return graph;
	}
	
	public static Double distance (IntersectionPcc a, IntersectionPcc b) {
		return Math.sqrt( (a.getLatitude()-b.getLatitude())*(a.getLatitude()-b.getLatitude())
			 + (a.getLongitude()-b.getLongitude())*(a.getLongitude()-b.getLongitude()) );
	}
	
	

	HashMap<Long, Long> colors;
}
