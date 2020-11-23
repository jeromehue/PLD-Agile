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
		Segment s1 = new Segment(1.0, "", new Long(1), new Long(2));
		Segment s2 = new Segment(2.0, "", new Long(2), new Long(4));
		Segment s3 = new Segment(3.0, "", new Long(1), new Long(3));
		Segment s4 = new Segment(4.0, "", new Long(3), new Long(4));
		Segment s5 = new Segment(5.0, "", new Long(4), new Long(5));
		Segment s6 = new Segment(6.0, "", new Long(3), new Long(6));
		Segment s7 = new Segment(7.0, "", new Long(5), new Long(6));
		
		ArrayList<Segment> l1 = new ArrayList<>();
		l1.add(s1);
		l1.add(s3);
		ArrayList<Segment> l2 = new ArrayList<>();
		l2.add(s1);
		l2.add(s2);
		ArrayList<Segment> l3 = new ArrayList<>();
		l3.add(s4);
		l3.add(s3);
		l3.add(s6);
		ArrayList<Segment> l4 = new ArrayList<>();
		l4.add(s2);
		l4.add(s5);
		l4.add(s4);
		ArrayList<Segment> l5 = new ArrayList<>();
		l5.add(s5);
		l5.add(s7);
		ArrayList<Segment> l6 = new ArrayList<>();
		l6.add(s7);
		l6.add(s6);
		
		Intersection inter1 = new Intersection(new Long(1), 1.0, 1.0, l1);
		Intersection inter2 = new Intersection(new Long(2), 1.0, 2.0, l2);
		Intersection inter3 = new Intersection(new Long(3), 2.0, 1.0, l3);
		Intersection inter4 = new Intersection(new Long(4), 2.0, 2.0, l4);
		Intersection inter5 = new Intersection(new Long(5), 3.0, 2.0, l5);
		Intersection inter6 = new Intersection(new Long(6), 3.0, 1.0, l6);
		
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
		PriorityQueue<Long> greyVertexes;// !!!! Que les ids ???
		HashMap<Long, Long> predecesors;//<Intersection id, Intersection id du prédecesseur  >
		
		IntersectionPcc neighbor;
		IntersectionPcc minVertex;
		
		//On fait un Dijkstra par point à visiter
		for (Intersection start  :  startVertexes) {
			//Les sommets gris sont initialisés à null

			/*Début de l'algorithme classique de Dijkstra*/
			
			//Pour chaque objet Intesection on crée un objet IntersectionPcc qu'on initialise avec un cout MAX
			// et un couleur blanche
			allVertexesPcc = new HashMap<Long, IntersectionPcc>();
			predecesors = new HashMap<Long, Long>();
			for (Intersection inter : allVertexes) {
				allVertexesPcc.put( inter.getId(), new IntersectionPcc(inter, 0, Double.MAX_VALUE ));
				predecesors.put(inter.getId(), null);
			}
			
			//On colorie le point de départ en gris et on met son cout à 0.
			greyVertexes = new PriorityQueue<Long>();
			IntersectionPcc startPcc = new IntersectionPcc (start, 1, 0.0);
			startPcc = allVertexesPcc.put(startPcc.getId(), startPcc);
			greyVertexes.add(startPcc.getId());
			
			while(!greyVertexes.isEmpty()) {
				minVertex = allVertexesPcc.get(greyVertexes.peek());//On prend l'intersection grise 
																//avec un cout minimal
				System.out.println("On regarde le sommet "+minVertex.getId());
				
				//On regarde tous les voisins "neighbor" de l'intersection "minVertex"
				for(Segment s : minVertex.getOutboundSegments()) {
					neighbor = allVertexesPcc.get(s.getDestination());
					if(neighbor.getColor() == 0 || neighbor.getColor()== 1) {//Si le voisin est blanc ou gris
						//relacher (minVertex, voisin, predecesseur, cout) : 
						if( minVertex.getCost() + s.getLength() < neighbor.getCost() ) {
							System.out.println("On relache "+minVertex.getId()+" et "+neighbor.getId());
							neighbor.setCost( minVertex.getCost() + s.getLength() );
							predecesors.put(neighbor.getId(), minVertex.getId());
						}
					}
					if(neighbor.getColor() == 0) {
						neighbor.setColor(1);
						greyVertexes.add(neighbor.getId());
					}
					allVertexesPcc.put(neighbor.getId(), neighbor);//On enregistre les modifs faites à neighbor
				}
				
				greyVertexes.remove(minVertex.getId());//On enlève l'intersection quand elle n'a plus de voisins
												//gris ou blancs -> (on la colorie en boir)
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
