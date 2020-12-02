package algo;
import modele.CityMap;
//import modele.Request;
//import modele.CityMap;
import modele.Intersection;
import modele.Request;
import modele.Segment;
import modele.Tour;
import modele.Way;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import java.util.HashMap;
import java.time.LocalTime;

public class Pcc {
	private List<Intersection> allVertices;
	private List<Intersection> pickUpVertices;
	private List<Intersection> deliveryVertices;
	private Intersection start;
	//private List<Segment> edges;
	private HashMap<Long, HashMap<Long, Segment>> savePredecessors;
	HashMap<Long, IntersectionPcc> allVerticesPcc;
	Request request;
	private double bikeVelocity = 4;//en m.s-1 (=14,4 km/h)
	private double lengthAB;//Length from point A to B, computed in getRoads
	
	public Pcc() {};
	
	public Pcc(CityMap city, Request request) {
		allVertices = city.getIntersections();
		pickUpVertices = request.getPickUpLocations();
		deliveryVertices = request.getDeliveryLocations();
		start = request.getStartingLocation();
		savePredecessors = new HashMap<Long, HashMap<Long, Segment>>();//< idStartVertex, <idCurrentVertex, idLastVertex> >
		//edges = city.getSegments();
		this.request=request;
	}
	
	public CompleteGraph computePcc() {
		
		ArrayList<Intersection> startVertices = new ArrayList<>();
		startVertices.add(start);
		startVertices.addAll(pickUpVertices);
		startVertices.addAll(deliveryVertices);
		
		CompleteGraph graph = new CompleteGraph(startVertices);
		
		final int END_TEST_CYCLE = 1;
		boolean allBlackStartVertices=false;	
		allVerticesPcc = new HashMap<Long, IntersectionPcc>();
		//HashMaps pour retrouver les voisins
		PriorityQueue<IntersectionPcc> greyVertices; // tas binaire
		// < Intersection id, segment qui relie le prédecesseur à l'intersection  >
		HashMap<Long, Segment> predecessors;
		
		
		IntersectionPcc neighbor;
		IntersectionPcc minVertex;
		
		//On fait un Dijkstra par point à visiter
		for (Intersection start : startVertices) {
			//Les sommets gris sont initialisés à null

			/*Début de l'algorithme classique de Dijkstra*/
			
			//Pour chaque objet Intesection on crée un objet IntersectionPcc qu'on initialise 
			//avec un cout MAX et la couleur blanche
			allVerticesPcc = new HashMap<Long, IntersectionPcc>();
			predecessors = new HashMap<Long, Segment>();
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
				//System.out.println("On regarde le sommet "+minVertex.getId());
				
				//On regarde tous les voisins "neighbor" de l'intersection "minVertex"
				for(Segment s : minVertex.getOutboundSegments()) {
					neighbor = allVerticesPcc.get(s.getDestination().getId());
					if(neighbor.getColor() == 0 || neighbor.getColor()== 1) { // blanc ou gris
						//relacher (minVertex, voisin, predecesseur, cout) : 
						//System.out.println("On relache "+minVertex.getId() + " et "+neighbor.getId());

						if( minVertex.getCost() + s.getLength() < neighbor.getCost() ) {
							//System.out.println("On met a jour "+minVertex.getId()+" et "+neighbor.getId());
							neighbor.setCost( minVertex.getCost() + s.getLength() );
							predecessors.put(neighbor.getId(), s);
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
			
			//sauvegarder le résultat obtenu pour le point de départ
			graph.updateCompleteGraph(startPcc.getId(), allVerticesPcc, startVertices);
			// puis sauvegarder une HashMap des predecessors
			savePredecessors.put(start.getId(), predecessors);
		}
		
		System.out.println(graph.toString());
		return graph;
	}
	

	public List<Segment> getRoads(Intersection start, Intersection finish){
		ArrayList<Segment> segmentsList =  new ArrayList<Segment>();
		HashMap<Long, Segment> predecessors = savePredecessors.get(start.getId());
		Long currentPoint = finish.getId();
		Segment path = predecessors.get(currentPoint);
		lengthAB=0.0;
		
		do {
			segmentsList.add(0, path);
			lengthAB += path.getLength();
			
			currentPoint = path.getOrigin().getId();
			path = predecessors.get(currentPoint);
		}while(path != null);
		
		
		Intersection passage = segmentsList.get(0).getOrigin();

		System.out.println("Affichage de trajet : ");
		System.out.print("(" + passage.getLatitude() + " ; " + passage.getLongitude() + ")");
		System.out.print(" de la rue " + segmentsList.get(0).getName());
		System.out.print(" -> ");

		passage = segmentsList.get(segmentsList.size() - 1).getDestination();
		
		System.out.print("(" + passage.getLatitude() + " ; " + passage.getLongitude() + ")");
		System.out.println(" à la rue " + segmentsList.get(segmentsList.size() - 1).getName());
		System.out.print("Longueur totale :");
		System.out.println(lengthAB);
		
		return segmentsList;
	}
	
	public Integer getDuration() {
		return (int) (lengthAB/bikeVelocity) ;
	}
	
	public Tour computeGooodTSPTour(){
		CompleteGraph graph = computePcc();
		System.out.println("[PCC.computeTour] taille graphe : "+graph.getNbVertices());
		TSP1 tsp = new TSP1(graph, request);
		tsp.init();
		long startTime = System.currentTimeMillis();
		tsp.searchSolution(40000);
		System.out.print("Solution of cost "+tsp.getSolutionCost()+" found in "
				+(System.currentTimeMillis() - startTime)+"ms : ");
		
		Intersection inter;
		Long idInter;
		List<Intersection> goodInterList = new ArrayList<Intersection>();
		
		for (int i=0; i<graph.getNbVertices(); i++) {
			idInter = graph.getIdFromIndex(tsp.getSolution(i));
			inter = allVerticesPcc.get(idInter);
			goodInterList.add(inter);
		}
		
		return computeTour(goodInterList);
	}
	
	public Tour computeTour(List<Intersection> interList) {
		List<Way> wayList = new ArrayList<>();
		LocalTime tourStartingTime = request.getStartingTime();
		Integer totalWayDuration = 0;
		
		Integer stayingStartDuration; //différence entre startArrival et startDeparture
		LocalTime arrivalAtStart; //Arrivée au start
		LocalTime departureFromStart; // départ du start
		LocalTime arrivalAtFinish; // arrivée à finish
		Integer wayDuration;
		Way way;
		
		for(int i=0; i<interList.size(); i++) {
			List<Segment> list = new ArrayList<>();
			Intersection start;
			Intersection finish;
			start = interList.get(i);
			if( (i+1)<interList.size() ) {
				finish = interList.get(i+1);
			} else {
				finish = interList.get(0);
			}
			
			list = getRoads(start, finish);
			wayDuration = getDuration();

			totalWayDuration += wayDuration;

			if(i==0) {
				arrivalAtStart = tourStartingTime;
				departureFromStart = tourStartingTime;
				
			} else {
				stayingStartDuration = request.getDurationPickUpDelivery(start.getId());
				arrivalAtStart = tourStartingTime.plusSeconds(totalWayDuration-wayDuration);
				departureFromStart = arrivalAtStart.plusSeconds(stayingStartDuration);
				totalWayDuration += stayingStartDuration;
			}
	
			arrivalAtFinish = departureFromStart.plusSeconds(wayDuration);
			way = new Way(list, arrivalAtStart, departureFromStart, arrivalAtFinish, start, finish );
			wayList.add(way);
		}
		
		Tour tour = new Tour(request.getStartingLocation(), request, wayList);
		
		return tour;
	}
	

	public Tour changeOrder (Tour tour, Intersection intersection, int newIndex){
		
		return null;
	}
	
	public Tour addRequest (Tour tour, Intersection pickup, Intersection delivery) {
		return null;
	}
	
	public Tour deleteIntersection (Tour tour, Intersection intersection) {
		List<Intersection> list = new ArrayList<Intersection> ();
		for (Way w : tour.getwaysList()) {
			if(w.getDeparture().getId() != intersection.getId()) {
				list.add(w.getDeparture());
			}
		}
		return computeTour(list);
	}
/*=======
	public void setComputeTour(Tour t){
		CompleteGraph graph = computePcc();
		System.out.println("[PCC.computeTour] taille graphe : "+graph.getNbVertices());
		TSP1 tsp = new TSP1(graph, request);
		//tsp.addGraph(graph);
		tsp.init();
		
		
		List<Way> wayList = new ArrayList<>();
		LocalTime tourStartingTime = request.getStartingTime();
		Integer totalWayDuration = 0;
		
		Integer stayingStartDuration; //différence entre startArrival et startDeparture
		LocalTime arrivalAtStart; //Arrivée au start
		LocalTime departureFromStart; // départ du start
		LocalTime arrivalAtFinish; // arrivée à finish
		Integer wayDuration;

		Intersection start;
		Intersection finish;
		Way way;
		
		for (int i=0; i<graph.getNbVertices(); i++) {
			
			//On récupère les id puis les intersections entre deux points
			Long idStart = graph.getIdFromIndex(i);
			Long idFinish;
			//Last point comes  back to start point.
			if( (i+1) < graph.getNbVertices()) {
				idFinish = graph.getIdFromIndex(i+1);
			}
			else {
				idFinish = graph.getIdFromIndex(0);
			}
			start = allVerticesPcc.get(idStart);
			finish = allVerticesPcc.get(idFinish);
			
			List<Segment> list = getRoads(start, finish);
			wayDuration = getDuration();
			totalWayDuration += wayDuration;

			if(i==0) {
				arrivalAtStart = tourStartingTime;
				departureFromStart = tourStartingTime;
				
			} else {
				stayingStartDuration = request.getDurationPickUpDelivery(start.getId());
				arrivalAtStart = tourStartingTime.plusSeconds(totalWayDuration-wayDuration);
				departureFromStart = arrivalAtStart.plusSeconds(stayingStartDuration);
				totalWayDuration += stayingStartDuration;
			}
	
			arrivalAtFinish = departureFromStart.plusSeconds(wayDuration);
			way = new Way(list, arrivalAtStart, departureFromStart, arrivalAtFinish, start, finish );
			wayList.add(way);
		}
		
		
		t.setTour(request.getStartingLocation(), request, wayList);
		
	}
	
>>>>>>> 42716bffd4b058d1579c3e274cc8a1b5444e5bef*/
	
	public Double getBikeVelocity() {
		return bikeVelocity;
	}
	public void setBikeVelocity(double velocity) {
		bikeVelocity = velocity;
	}
	
	//------------------------------------------------------

/*	public CompleteGraph initTest() {
		ArrayList<Segment> l1 = new ArrayList<>();
		Intersection inter1 = new Intersection(new Long(1), 1.0, 1.0, l1);
		Intersection inter2 = new Intersection(new Long(2), 1.0, 2.0, l1);
		Intersection inter3 = new Intersection(new Long(3), 2.0, 1.0, l1);
		Intersection inter4 = new Intersection(new Long(4), 2.0, 2.0, l1);
		Intersection inter5 = new Intersection(new Long(5), 3.0, 2.0, l1);
		Intersection inter6 = new Intersection(new Long(6), 3.0, 1.0, l1);


		Segment s12 = new Segment(1.0, "rue 12", inter1, inter2);
		Segment s24 = new Segment(2.0, "rue 24", inter2, inter4);
		Segment s21 = new Segment(1.0, "rue 21", inter2, inter1);
		Segment s13 = new Segment(5.0, "rue 13", inter1, inter3);
		Segment s34 = new Segment(1.0, "rue 34", inter3, inter4);
		Segment s43 = new Segment(1.0, "rue 43", inter4, inter3);
		Segment s45 = new Segment(2.0, "rue 45", inter4, inter5);
		Segment s36 = new Segment(10.0, "rue 36", inter3, inter6);
		Segment s32 = new Segment(10.0, "rue 32", inter3, inter2);
		Segment s56 = new Segment(5.0, "rue 56", inter5, inter6);	
		Segment s54 = new Segment(2.0, "rue 54", inter5, inter4);	
		Segment s65 = new Segment(5.0, "rue 65", inter6, inter5);		
		Segment s15 = new Segment(6.0, "rue 15", inter1, inter5);		
		Segment s51 = new Segment(7.0, "rue 51", inter5, inter1);		
		
		inter1.addOutboundSegment(s12);
		inter1.addOutboundSegment(s13);
		inter1.addOutboundSegment(s15);
		inter2.addOutboundSegment(s24);
		inter2.addOutboundSegment(s21);
		inter3.addOutboundSegment(s34);
		inter3.addOutboundSegment(s36);
		inter3.addOutboundSegment(s32);
		inter4.addOutboundSegment(s43);
		inter4.addOutboundSegment(s45);
		inter5.addOutboundSegment(s51);
		inter5.addOutboundSegment(s56);
		inter5.addOutboundSegment(s54);
		inter6.addOutboundSegment(s65);
		
		
		List<Intersection> startVertices = new ArrayList<Intersection>();
		startVertices.add(inter1);
		startVertices.add(inter2);
		startVertices.add(inter3);
		//startVertices.add(inter4);
		//startVertices.add(inter5);
		//startVertices.add(inter6);


		List<Intersection> allVertices = new ArrayList<Intersection>();
		allVertices.add(inter1);
		allVertices.add(inter2);
		allVertices.add(inter3);
		allVertices.add(inter4);
		allVertices.add(inter5);
		allVertices.add(inter6);
		return testComputePcc(allVertices, startVertices);
	}*/
	
	/*public CompleteGraph testComputePcc(List<Intersection> allVertices, List<Intersection> startVertices) {
		//Pbm parce que request.get... renvoie un long (id de l'intersection)
		// au lieu de l'intersection...
		// Commentaires pour tester l'algo
		//List<Intersection> startVertices = cityMap.getIntersectionsById(request.getDeliveryLocations());
		//startVertices.addAll(request.getPickUpLocations());
		//startVertices.addAll(request.getStartingLocation());

		//List<Intersection> allVertices = cityMap.getIntersections();
		 
		
		//TESTS
		CompleteGraph graph = new CompleteGraph(startVertices);
		
		final int END_TEST_CYCLE = 1;
		boolean allBlackStartVertices=false;	
		allVerticesPcc = new HashMap<Long, IntersectionPcc>();
		//HashMaps pour retrouver les voisins
		PriorityQueue<IntersectionPcc> greyVertices; // tas binaire
		HashMap<Long, Long> predecessors;//<Intersection id, Intersection id du prédecesseur  >
		
		IntersectionPcc neighbor;
		IntersectionPcc minVertex;
		
		//On fait un Dijkstra par point à visiter
		for (Intersection start  :  startVertices) {
			//Les sommets gris sont initialisés à null

			//Début de l'algorithme classique de Dijkstra
			
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
				//System.out.println("On regarde le sommet "+minVertex.getId());
				
				//On regarde tous les voisins "neighbor" de l'intersection "minVertex"
				for(Segment s : minVertex.getOutboundSegments()) {
					neighbor = allVerticesPcc.get(s.getDestination().getId());
					if(neighbor.getColor() == 0 || neighbor.getColor()== 1) { // blanc ou gris
						//relacher (minVertex, voisin, predecesseur, cout) : 
						//System.out.println("On relache "+minVertex.getId() + " et "+neighbor.getId());

						if( minVertex.getCost() + s.getLength() < neighbor.getCost() ) {
							//System.out.println("On met a jour "+minVertex.getId()+" et "+neighbor.getId());
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
			
			//sauvegarder le résultat obtenu pour le point de départ
			graph.updateCompleteGraph(startPcc.getId(), allVerticesPcc, startVertices);
		
			/*System.out.println("Le chemin a l'envers du point 6 au sommet de départ "+ start.getId()+"est : ");
			Long currentPoint = new Long(6);
			do {
				System.out.print(currentPoint+" - ");
				currentPoint = predecessors.get(currentPoint);
			}while(currentPoint != null);
			System.out.println();
			//On enregistre une HashMap predecessors par point de départ
			 */
			//savePredecessors.put(start.getId(), predecessors);
	/*
		}
		
		System.out.println(graph.toString());
		return graph;
	}*/
	
	
	
	
	/*public static Double distance (IntersectionPcc a, IntersectionPcc b) {
		return Math.sqrt( (a.getLatitude()-b.getLatitude())*(a.getLatitude()-b.getLatitude())
			 + (a.getLongitude()-b.getLongitude())*(a.getLongitude()-b.getLongitude()) );
	}*/
}
