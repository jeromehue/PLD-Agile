package pcc;
import modele.Request;
import modele.CityMap;
import modele.Intersection;
import modele.Segment;
import tsp.CompleteGraph;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.lang.Math;

public class pcc {
	private CityMap cityMap;
	private Request request;
	
	public pcc(CityMap city , Request request ){
		cityMap = city;
	}
	public pcc() {};
	
	public CompleteGraph calculPcc() {
		//Pbm parce que request.get... renvoie un long (id de l'intersection)
		// au lieu de l'intersection...
		/* Commentaires pour tester l'algo
		List<Intersection> startVertexes = cityMap.getIntersectionsById(request.getDeliveryLocations());
		startVertexes.addAll(request.getPickUpLocations());
		startVertexes.addAll(request.getStartingLocation());

		List<Intersection> allVertexes = cityMap.getIntersections();
		 */
		
		//TESTS
		ArrayList<Segment> l1 = new ArrayList<>();
		Intersection inter1 = new Intersection(new Long(1), 1.0, 1.0, l1);
		Intersection inter2 = new Intersection(new Long(2), 1.0, 2.0, l1);
		Intersection inter3 = new Intersection(new Long(3), 2.0, 1.0, l1);
		Intersection inter4 = new Intersection(new Long(4), 2.0, 2.0, l1);
		Intersection inter5 = new Intersection(new Long(5), 3.0, 2.0, l1);
		Intersection inter6 = new Intersection(new Long(6), 3.0, 1.0, l1);
		Segment s12 = new Segment(1.0, "", inter1, inter2);
		Segment s24 = new Segment(2.0, "", inter2, inter4);
		Segment s13 = new Segment(5.0, "", inter1, inter3);
		Segment s34 = new Segment(1.0, "", inter3, inter4);
		Segment s43 = new Segment(1.0, "", inter4, inter3);
		Segment s45 = new Segment(2.0, "", inter4, inter5);
		Segment s36 = new Segment(10.0, "", inter3, inter6);
		Segment s56 = new Segment(5.0, "", inter5, inter6);	
		Segment s65 = new Segment(5.0, "", inter6, inter5);		
		
		inter1.addOutboundSegment(s12);
		inter1.addOutboundSegment(s13);
		inter2.addOutboundSegment(s24);
		inter3.addOutboundSegment(s34);
		inter3.addOutboundSegment(s36);
		inter4.addOutboundSegment(s43);
		inter4.addOutboundSegment(s45);
		inter5.addOutboundSegment(s56);
		inter6.addOutboundSegment(s65);
		
		
		List<Intersection> startVertexes = new ArrayList<Intersection>();
		startVertexes.add(inter1);
		List<Intersection> allVertexes = new ArrayList<Intersection>();
		allVertexes.add(inter1);
		allVertexes.add(inter2);
		allVertexes.add(inter3);
		allVertexes.add(inter4);
		allVertexes.add(inter5);
		allVertexes.add(inter6);
			
		HashMap<Long, IntersectionPcc> allVertexesPcc;//HashMaps pour retrouver les voisins
		PriorityQueue<IntersectionPcc> greyVertexes; // tas binaire
		HashMap<Long, Long> predecessors;//<Intersection id, Intersection id du prédecesseur  >
		
		IntersectionPcc neighbor;
		IntersectionPcc minVertex;
		
		//On fait un Dijkstra par point à visiter
		for (Intersection start  :  startVertexes) {
			//Les sommets gris sont initialisés à null

			/*Début de l'algorithme classique de Dijkstra*/
			
			//Pour chaque objet Intesection on crée un objet IntersectionPcc qu'on initialise 
			//avec un cout MAX et la couleur blanche
			allVertexesPcc = new HashMap<Long, IntersectionPcc>();
			predecessors = new HashMap<Long, Long>();
			for (Intersection inter : allVertexes) {
				allVertexesPcc.put( inter.getId(), new IntersectionPcc(inter, 0, Double.MAX_VALUE ));
				predecessors.put(inter.getId(), null);
			}
			
			//On colorie le point de départ en gris et on met son cout à 0.
			greyVertexes = new PriorityQueue<IntersectionPcc>();
			IntersectionPcc startPcc = new IntersectionPcc (start, 1, 0.0);
			allVertexesPcc.put(startPcc.getId(), startPcc);
			greyVertexes.add(startPcc);
			
			while(!greyVertexes.isEmpty()) {
				minVertex = greyVertexes.poll();//On prend l'intersection grise 
																//avec un cout minimal
				System.out.println("On regarde le sommet "+minVertex.getId());
				
				//On regarde tous les voisins "neighbor" de l'intersection "minVertex"
				for(Segment s : minVertex.getOutboundSegments()) {
					neighbor = allVertexesPcc.get(s.getDestination().getId());
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
						greyVertexes.add(neighbor);
					}
					allVertexesPcc.put(neighbor.getId(), neighbor);//On enregistre les modifs faites à neighbor
				}
				
				///On colorie l'intersection en noir quand elle n'a plus de voisins gris ou blancs
				minVertex.setColor(2);
				allVertexesPcc.put(minVertex.getId(), minVertex);
			}
			//sauvegarder le résultat obtenu pour un des points de départ
			for(Long idInter : allVertexesPcc.keySet()) {
				IntersectionPcc inter = allVertexesPcc.get(idInter);
				System.out.println("En partant du sommet "+startPcc.getId()+
					" le chemin le plus court pour arriver au point "+inter.getId()+
					" a une distance de "+inter.getCost()+"\n");
			}
		}	
		return new CompleteGraph(2);
	}
	
	public static Double distance (IntersectionPcc a, IntersectionPcc b) {
		return Math.sqrt( (a.getLatitude()-b.getLatitude())*(a.getLatitude()-b.getLatitude())
			 + (a.getLongitude()-b.getLongitude())*(a.getLongitude()-b.getLongitude()) );
	}
	
	

	HashMap<Long, Long> colors;
}
