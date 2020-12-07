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

/**
 * This class computes and stores the shortest ways between pickUp and delivery points. 
 * 
 * @author H4414
 *
 */

public class Pcc {
	/**
	 * All the intersections get from CityMap
	 */
	private List<Intersection> allVertices;
	private List<Intersection> pickUpVertices;
	private List<Intersection> deliveryVertices;
	private Intersection start;
	/**
	 * Save how to reach each pickUp and delivery from any Intersection of interset (=the other pickUp and delivery) 
	 */
	private HashMap<Long, HashMap<Long, Segment>> savePredecessors;
	HashMap<Long, IntersectionPcc> allVerticesPcc;
	Request request;
	/**
	 * Bike velocity in m.s-1 (4m.s-1 = 14,4km/h)
	 */
	private double bikeVelocity = 4;
	/**
	 * Length from point A to B, computed in getRoads
	 */
	private double lengthAB; 
	
	/**
	 * Empty constructor
	 */
	public Pcc() {};
	
	/**
	 * Initialization with a City Map which contains the intersections and segments.
	 *  The request contains the pickup and delivery Intersections.
	 * 
	 * @param city
	 * @param request
	 */
	public Pcc(CityMap city, Request request) {
		allVertices = city.getIntersections();
		pickUpVertices = request.getPickUpLocations();
		deliveryVertices = request.getDeliveryLocations();
		start = request.getStartingLocation();
		// HashMap< idStartVertex, <idCurrentVertex, idLastVertex> >
		savePredecessors = new HashMap<Long, HashMap<Long, Segment>>();
		this.request=request;
	}
	
	/**
	 * Computes lowest costs between pickup and delivery points.
	 * Stores the predecessors to be able to travel from a point to another.
	 * Returns a Complete graph to compute a optimized tour.
	 * 
	 * @return
	 */
	public CompleteGraph computePcc() {
		
		ArrayList<Intersection> startVertices = new ArrayList<>();
		startVertices.add(start);
		startVertices.addAll(pickUpVertices);
		startVertices.addAll(deliveryVertices);
		CompleteGraph graph = new CompleteGraph(startVertices);
		
		final int END_TEST_CYCLE = 1;
		boolean allBlackStartVertices=false;	
		//HashMap pour retrouver les voisins
		allVerticesPcc = new HashMap<Long, IntersectionPcc>();
		PriorityQueue<IntersectionPcc> greyVertices; // tas binaire
		// HashMap<Intersection id, segment qui relie le prédecesseur à l'intersection >
		HashMap<Long, Segment> predecessors;
		
		
		IntersectionPcc neighbor;
		IntersectionPcc minVertex;
		
		
		//On fait un Dijkstra par point à visiter
		for (Intersection start : startVertices) {
			//Les sommets gris sont initialisés à null

			//Début de l'algorithme classique de Dijkstra
			
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
				
				minVertex = greyVertices.poll();//On prend l'intersection grise de cout minimal
				
				//On regarde tous les voisins "neighbor" de l'intersection "minVertex"
				for (Segment s : minVertex.getOutboundSegments()) {
					neighbor = allVerticesPcc.get(s.getDestination().getId());
					if (neighbor.getColor() == 0 || neighbor.getColor()== 1) { // blanc ou gris
						//relacher (minVertex, voisin, predecesseur, cout) : 

						if ( minVertex.getCost() + s.getLength() < neighbor.getCost() ) {
							neighbor.setCost( minVertex.getCost() + s.getLength() );
							predecessors.put(neighbor.getId(), s);
						}
					}
					if (neighbor.getColor() == 0) {
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
				if (i==END_TEST_CYCLE) {//Pour ne pas tester trop souvent
					i=0;
					nbBlackStartVertices=0;
					for (Intersection sVertex : startVertices ) {
						if (allVerticesPcc.get(sVertex.getId()).getColor() == 2) {
							nbBlackStartVertices++;
						}
					}
					if (nbBlackStartVertices == startVertices.size()) {
						allBlackStartVertices = true;
					}
				}
			}
			
			//sauvegarder le résultat obtenu pour le point de départ
			graph.updateCompleteGraph(startPcc.getId(), allVerticesPcc, startVertices);
			// puis sauvegarder une HashMap des predecessors
			savePredecessors.put(start.getId(), predecessors);
		}
		
		//System.out.println(graph.toString());
		return graph;
	}

	/**
	 * Returns a list of Segment which allows to go from the intersection start to finish using the shortest way.
	 * @param start
	 * @param finish
	 * @return
	 */
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
		
		
		//Intersection passage = segmentsList.get(0).getOrigin();

		/*
		System.out.println("Affichage de trajet : ");
		System.out.print("(" + passage.getLatitude() + " ; " + passage.getLongitude() + ")");
		System.out.print(" de la rue " + segmentsList.get(0).getName());
		System.out.print(" -> ");

		passage = segmentsList.get(segmentsList.size() - 1).getDestination();
		
		System.out.print("(" + passage.getLatitude() + " ; " + passage.getLongitude() + ")");
		System.out.println(" à la rue " + segmentsList.get(segmentsList.size() - 1).getName());
		System.out.print("Longueur totale :");
		System.out.println(lengthAB);*/
		
		return segmentsList;
	}
	
	/**
	 * Returns duration in seconds to travel the list of segments computed in getRoads
	 * @return
	 */
	public Integer getDuration() {
		return (int) (lengthAB/bikeVelocity) ;
	}
	
	/**
	 * Return a optimized tour computed with the TSP algorithm
	 * @return a Tour
	 */
	public Tour computeGooodTSPTour() {
		CompleteGraph graph = computePcc();
		System.out.println("[PCC.computeTour] taille graphe : "+graph.getNbVertices());
		// TODO: remove 1000 and set a real max discrepancy

		TSP1 tsp = new TSP1(graph, request, 80);

		tsp.init();
		System.out.println("okay TSP init");

		long startTime = System.currentTimeMillis();
		tsp.searchSolution(400000);
		System.out.print("Solution of cost "+tsp.getSolutionCost()+" found in "
				+(System.currentTimeMillis() - startTime)+"ms : ");
		
		Intersection inter;
		Long idInter;
		System.out.println("okay TSP");
		List<Intersection> goodInterList = new ArrayList<Intersection>();
		
		for (int i=0; i<graph.getNbVertices(); i++) {
			idInter = graph.getIdFromIndex(tsp.getSolution(i));
			inter = allVerticesPcc.get(idInter);
			goodInterList.add(inter);
		}
		
		List<Way> waysList = computeWaysList(goodInterList);
		return new Tour(request.getStartingLocation(), request, waysList);
		
	}
	
	/**
	 * Return a list of ways from a list of intersections.
	 * The list of intersections come from a orderof visit computed by the TSP algo or from a user modification.
	 * 
	 * @param interList
	 * @return
	 */
	public List<Way> computeWaysList(List<Intersection> interList) {
		List<Way> wayList = new ArrayList<>();
		LocalTime tourStartingTime = request.getStartingTime();
		Integer totalWayDuration = 0;
		
		Integer stayingStartDuration; //différence entre startArrival et startDeparture
		LocalTime arrivalAtStart; //Arrivée au start
		LocalTime departureFromStart; // départ du start
		LocalTime arrivalAtFinish; // arrivée à finish
		Integer wayDuration;
		Way way;
		
		for (int i=0; i<interList.size(); i++) {
			List<Segment> list = new ArrayList<>();
			Intersection start;
			Intersection finish;
			start = interList.get(i);
			if ( (i+1)<interList.size() ) {
				finish = interList.get(i+1);
			} else {
				finish = interList.get(0);
			}
			
			list = getRoads(start, finish);
			wayDuration = getDuration();

			totalWayDuration += wayDuration;

			if (i==0) {
				arrivalAtStart = tourStartingTime;
				departureFromStart = tourStartingTime;
				
			} else {
				stayingStartDuration = request.getDurationPickUpDelivery(start.getId());
				arrivalAtStart = tourStartingTime.plusSeconds((long) totalWayDuration - wayDuration);
				departureFromStart = arrivalAtStart.plusSeconds(stayingStartDuration);
				totalWayDuration += stayingStartDuration;
			}
	
			arrivalAtFinish = departureFromStart.plusSeconds(wayDuration);
			way = new Way(list, arrivalAtStart, departureFromStart, arrivalAtFinish, start, finish );
			wayList.add(way);
		}
		
		return wayList;
	}
	
	public Tour changeOrder (Tour tour, Intersection intersection, int shift){
		if (intersection.getId().equals(request.getStartingLocation().getId())) {
			return tour;
		}
		
		List<Intersection> list = new ArrayList<Intersection> ();
		int oldIndex=0;
		int i=0;
		for (Way w : tour.getWaysList()) {
			if (!w.getDeparture().getId().equals(intersection.getId())) {
				list.add(w.getDeparture());
			}
			else {
				intersection = w.getDeparture();
				oldIndex=i;
			}
			i++;
		}
		
		//TODO
		//Verify shift is consistent with oldIndex and tour.getWaysList().size
		if (oldIndex+shift>0 && oldIndex+shift<tour.getWaysList().size()) {
			list.add(oldIndex+shift, intersection);
		}
		else {
			return tour;
		}
		
		tour.setWaysList( computeWaysList(list) );
		
		tour.updateIsPositionConsistent(intersection.getId());
		if (request.hasDelivery(intersection.getId())) {
			tour.updateIsPositionConsistent(request.getDeliveryFromPickUp(intersection.getId()));			
		}
		
		return tour;
	}
	
	public Tour addRequest (Tour tour, Intersection pickup, Intersection delivery, Integer pickUpDuration, Integer deliveryDuration,
							Integer pickupIndex, Integer deliveryIndex) {		
		
		IntersectionPcc interD = allVerticesPcc.get(delivery.getId());
		IntersectionPcc interP = allVerticesPcc.get(pickup.getId());
		delivery = new Intersection(interD.getId(), interD.getLatitude(), interD.getLongitude(), interD.getOutboundSegments() );
		pickup = new Intersection(interP.getId(), interP.getLatitude(), interP.getLongitude(), interP.getOutboundSegments() );
		this.deliveryVertices.add(delivery);
		this.pickUpVertices.add(pickup);
		this.computePcc();

		request.addRequest(pickup, delivery, pickUpDuration, deliveryDuration);
		
		List<Intersection> list = new ArrayList<Intersection>();
		for (Way w : tour.getWaysList()) {			
			list.add(w.getDeparture());
		}
		list.add(pickupIndex, pickup);
		list.add(deliveryIndex, delivery);

		List<Way> ways = computeWaysList(list); 
		
		tour.setRequest(request);
		tour.setWaysList(ways);
		tour.updateIsPositionConsistent(delivery.getId());
		tour.updateIsPositionConsistent(pickup.getId());
		
		
		
		return tour;
	}
	
	public Tour deleteIntersection (Tour tour, Intersection intersection) {
		if (intersection.getId().equals(request.getStartingLocation().getId())) {
			return tour;
		}
		
		List<Intersection> list = new ArrayList<Intersection> ();

		for (Way w : tour.getWaysList()) {
			if (!w.getDeparture().getId().equals(intersection.getId())) {
				list.add(w.getDeparture());
			} else {				
				//TODO
				//Save deleted intersection into a list in Tour to have the possibility to put it back
			}
		}
		
		tour.setWaysList(computeWaysList(list));
		
		tour.updateIsPositionConsistent(intersection.getId());
		if (request.isPickUp(intersection.getId())) {
			tour.updateIsPositionConsistent(request.getDeliveryFromPickUp(intersection.getId()));			
		}
		
		return tour;
	}
	
	public Double getBikeVelocity() {
		return bikeVelocity;
	}
	
	public void setBikeVelocity(double velocity) {
		bikeVelocity = velocity;
	}
}
