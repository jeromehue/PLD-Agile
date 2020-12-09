package algo;

import modele.CityMap;
import modele.Intersection;
import modele.Request;
import modele.Segment;
import modele.Tour;
import modele.Way;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.Main;

import java.util.HashMap;
import java.time.LocalTime;

/**
 * This class computes and stores the shortest ways between pickUp and delivery
 * points.
 * 
 * @author H4414
 *
 */

public class Pcc {
	private static final Logger logger = LoggerFactory.getLogger(Pcc.class);	
	/**
	 * All the intersections gotten from CityMap.
	 */
	private HashMap<Long, Intersection> allVertices;

	/**
	 * Intersections where delivery driver picks up package.
	 */
	private List<Intersection> pickUpVertices;
	
	/**
	 * Intersections where delivery driver delivers package.
	 */
	private List<Intersection> deliveryVertices;
	
	/**
	 * Intersection where delivery driver begins his tour.
	 */
	private Intersection start;
	
	/**
	 * Save how to reach each pickUp and delivery from any Intersection of interest
	 * (=the other pickUp and delivery).
	 */
	private HashMap<Long, HashMap<Long, Segment>> savePredecessors;
	
	/**
	 * The request containing the steps given by the user.
	 */
	private Request request;
	
	/**
	 * Bike velocity in m.s-1 (4m.s-1 = 14,4km/h).
	 */
	private double bikeVelocity = 4;
	
	/**
	 * Length from point A to B, computed in getRoads.
	 */
	private double lengthAB;

	/**
	 * Empty constructor.
	 */
	public Pcc() {};

	/**
	 * Initialization with a City Map which contains the intersections and segments.
	 * The request contains the pickup and delivery Intersections.
	 * 
	 * @param city
	 * 		Objects who contains intersections and segments to make a map.
	 * @param request
	 * 		Objects who contains specific intersections and informations to create a tour.
	 */
	public Pcc(CityMap city, Request request) {
		allVertices = new HashMap<Long, Intersection>();
		for ( Intersection inter : city.getIntersections() ) {
			allVertices.put(inter.getId(), inter);
		}
		
		pickUpVertices = request.getPickUpLocations();
		deliveryVertices = request.getDeliveryLocations();
		start = request.getStartingLocation();
		// HashMap< idStartVertex, <idCurrentVertex, idLastVertex> >
		savePredecessors = new HashMap<Long, HashMap<Long, Segment>>();
		this.request = request;
	}

	/**
	 * Computes lowest costs between pickup and delivery points. Stores the
	 * predecessors to be able to travel from a point to another. Returns a Complete
	 * graph to compute an optimized tour.
	 * 
	 * @return
	 * 		Object CompleteGraph with costs to go from an intersection to another.
	 */
	public CompleteGraph computePcc() {

		ArrayList<Intersection> startVertices = new ArrayList<Intersection>();
		HashMap<Long, IntersectionPcc> allVerticesPcc = new HashMap<Long, IntersectionPcc>();

		startVertices.add(start);
		startVertices.addAll(pickUpVertices);
		startVertices.addAll(deliveryVertices);
		
		CompleteGraph graph = new CompleteGraph(startVertices);
		
		for (Intersection start : startVertices) {
			allVerticesPcc.clear();
			for (HashMap.Entry<Long, Intersection> inter : allVertices.entrySet()) {				
				allVerticesPcc.put( inter.getKey(), new IntersectionPcc(inter.getValue(), 0, Double.MAX_VALUE) );
			}
						
			doDijkstra(startVertices, start, allVerticesPcc);
			graph.updateCompleteGraph(start.getId(), allVerticesPcc, startVertices);
		}
		
		return graph;
	}
	
	/**
	 * Computes the Dijkstra algorithm between the <code>start</code> 
	 * vertex and all vertices of <code>startVertices</code>
	 * The results are stored in <code>allVerticesPcc</code> 
	 * and <code>savePredecessors</code>.
	 * 
	 * @param startVertices The list of Intersection needed to compute the algorithm.
	 * @param start The Intersection from which we search the shortest paths to all
	 * other Intersections.
	 * @param verticesPcc HashMap linking an intersection's id to its instance
	 * of IntersectionPcc, needed for this algorithm.
	 */
	public void doDijkstra(ArrayList<Intersection> startVertices, Intersection start, 
			HashMap<Long, IntersectionPcc> verticesPcc) {
		
		PriorityQueue<IntersectionPcc> greyVertices = new PriorityQueue<IntersectionPcc>();
		HashMap<Long, Segment> predecessors=new HashMap<Long, Segment>();
		Integer nbBlackStartVertices = 0;
		IntersectionPcc startPcc = new IntersectionPcc (start, 1, 0.0);
		
		greyVertices.add(startPcc);
		verticesPcc.put(start.getId(), startPcc);
		
		while (!greyVertices.isEmpty()) {			
			
			IntersectionPcc minVertex = greyVertices.poll();
			
			for (Segment s : minVertex.getOutboundSegments()) {
				IntersectionPcc neighbor = verticesPcc.get( s.getDestination().getId() );
				
				if ( ((neighbor.getColor() == 0) || (neighbor.getColor()== 1))
						&& (minVertex.getCost() + s.getLength() < neighbor.getCost()) ) {
					neighbor.setCost( minVertex.getCost() + s.getLength() );
					predecessors.put(neighbor.getId(), s);
				}
				if (neighbor.getColor() == 0) {
					neighbor.setColor(1);
					greyVertices.add(neighbor);
				}
			}
			
			minVertex.setColor(2);
			
			// checking if the loop has to end
			nbBlackStartVertices = 0;
			for (Intersection startVertex : startVertices) {
				if (verticesPcc.get( startVertex.getId() ).getColor() == 2) {
					nbBlackStartVertices++;
				}
			}
			if (nbBlackStartVertices == startVertices.size()) {
				break; // we've got our full complete graph
			}
		}
		
		savePredecessors.put(start.getId(), predecessors);
		verticesPcc.put(startPcc.getId(), startPcc);
   	}
			

	/**
	 * Returns a list of Segment which allows to go from the intersections start to
	 * finish using the shortest way computed by the Dijkstra algorithm.
	 * 
	 * @param start
	 * 		Beginning of the list of segments.
	 * @param finish
	 * 		Beginning of the list of segments.
	 * @return
	 * 		A list of segments which represent the shortest way to go from start to finish.
	 */
	public List<Segment> getRoads(Intersection start, Intersection finish) {
		ArrayList<Segment> segmentsList = new ArrayList<Segment>();
		HashMap<Long, Segment> predecessors = savePredecessors.get(start.getId());
		Long currentPoint = finish.getId();
		Segment path = predecessors.get(currentPoint);
		lengthAB=0.0;
		
		while(path != null) {

			segmentsList.add(0, path);
						
			lengthAB += path.getLength();

			currentPoint = path.getOrigin().getId();

			path = predecessors.get(currentPoint);				
		}

		return segmentsList;
	}

	/**
	 * 
	 * @return
	 * 		An integer which represents the number of seconds needed to travel
	 *  the list of segments computed in getRoads.
	 */
	public Integer getDuration() {
		return (int) (lengthAB / bikeVelocity);
	}

	/**
	 * 
	 * @return An optimized tour computed with the TSP algorithm.
	 */
	public Tour computeGooodTSPTour() {
		CompleteGraph graph = computePcc();
		logger.info("[PCC.computeTour] taille graphe : {}" ,graph.getNbVertices());
		// TODO: remove 1000 and set a real max discrepancy

		TSP1 tsp = new TSP1(graph, request, 100);

		long startTime = System.currentTimeMillis();
		tsp.searchSolution(4000);
		logger.info("Solution of cost {} found in {} ms :" , tsp.getSolutionCost(),
			 (System.currentTimeMillis() - startTime) );

		Intersection inter;
		Long idInter;
		System.out.println("okay TSP");
		List<Intersection> goodInterList = new ArrayList<Intersection>();

		for (int i = 0; i < graph.getNbVertices(); i++) {
			idInter = graph.getIdFromIndex(tsp.getSolution(i));
			inter = allVertices.get(idInter);
			goodInterList.add(inter);
		}

		List<Way> waysList = computeWaysList(goodInterList);
		return new Tour(request.getStartingLocation(), request, waysList);

	}

	/**
	 * Return a list of ways from a list of intersections. The list of intersections
	 * comes from an order of visit computed by the TSP algorithm or from the user's
	 * modifications.
	 * 
	 * @param interList
	 * 		List of intersections representing the tour.
	 * @return
	 * 		A list of ways which contain all the segments to run
	 *  the tour and some informations about time spent on each step.
	 */
	public List<Way> computeWaysList(List<Intersection> interList) {
		List<Way> wayList = new ArrayList<Way>();
		LocalTime tourStartingTime = request.getStartingTime();
		Integer totalWayDuration = 0;

		Integer stayingStartDuration;
		LocalTime arrivalAtStart;
		LocalTime departureFromStart;
		LocalTime arrivalAtFinish;
		Integer wayDuration;
		Way way;

		for (int i = 0; i < interList.size(); i++) {
			List<Segment> list = new ArrayList<Segment>();
			Intersection start = interList.get(i);
			Intersection finish;
			
			if ((i + 1) < interList.size()) {
				finish = interList.get(i + 1);
			} else {
				finish = interList.get(0);
			}

			list = getRoads(start, finish);
			wayDuration = getDuration();

			totalWayDuration += wayDuration;

			if (i == 0) {
				arrivalAtStart = tourStartingTime;
				departureFromStart = tourStartingTime;

			} else {
				stayingStartDuration = request.getDurationPickUpDelivery(start.getId());
				arrivalAtStart = tourStartingTime.plusSeconds((long) totalWayDuration - wayDuration);
				departureFromStart = arrivalAtStart.plusSeconds(stayingStartDuration);
				totalWayDuration += stayingStartDuration;
			}

			arrivalAtFinish = departureFromStart.plusSeconds(wayDuration);
			way = new Way(list, arrivalAtStart, departureFromStart, arrivalAtFinish, start, finish);
			wayList.add(way);
		}

		return wayList;
	}
	
	/**
	 * Enables the modifying of the position of an intersection - 
	 * it's not possible to change the position of the starting point.
	 * 
	 * @param tour
	 * 		Contains the current order between pick up and delivery points.
	 * @param intersection
	 * 		The intersection to change.
	 * @param shift
	 * 		Integer which represents the shift in the order. Can be positive or negative.
	 * @return
	 * 		A new Tour updated.
	 */
	public Tour changeOrder(Tour tour, Intersection intersection, int shift) {
		if (intersection.getId().equals(request.getStartingLocation().getId())) {
			return tour;
		}

		List<Intersection> list = new ArrayList<Intersection>();
		int oldIndex = 0;
		int i = 0;
		for (Way w : tour.getWaysList()) {
			if (!w.getDeparture().getId().equals(intersection.getId())) {
				list.add(w.getDeparture());
			} else {
				intersection = w.getDeparture();
				oldIndex = i;
			}
			i++;
		}

		// TODO
		// Verify shift is consistent with oldIndex and tour.getWaysList().size
		if (oldIndex + shift > 0 && oldIndex + shift < tour.getWaysList().size()) {
			list.add(oldIndex + shift, intersection);
		} else {
			return tour;
		}

		tour.setWaysList(computeWaysList(list));

		tour.updateIsPositionConsistent(intersection.getId());
		if (request.hasDelivery(intersection.getId())) {
			tour.updateIsPositionConsistent(request.getDeliveryFromPickUp(intersection.getId()));
		}

		return tour;
	}
	
	/**
	 * Enable to add a request to the tour.
	 * It adds a pickup and a delivery point at a specific index.
	 *  
	 * @param tour
	 * 		Contains the current order between pick up and delivery points.
	 * @param pickup
	 * 		The pickup intersection to add.
	 * @param delivery
	 * 		The delivery intersection to add.
	 * @param pickUpDuration
	 * 		Time spent in seconds at pick up point.
	 * @param deliveryDuration
	 * 		Time spent in seconds at delivery point.
	 * @param pickupIndex
	 * 		Index where the pick up point will be added.
	 * @param deliveryIndex
	 * 		Index where the delivery point will be added.
	 * @return
	 * 		A new Tour updated.
	 */

	public Tour addRequest(Tour tour, Intersection pickup, Intersection delivery, Integer pickUpDuration,
			Integer deliveryDuration, Integer pickupIndex, Integer deliveryIndex) {
		
		request.addRequest(pickup, delivery, pickUpDuration, deliveryDuration);
		this.computePcc();

		List<Intersection> list = new ArrayList<Intersection>();
		for (Way w : tour.getWaysList()) {
			list.add(w.getDeparture());
		}
		list.add(pickupIndex, pickup);
		list.add(deliveryIndex, delivery);
				
		tour.setRequest(request);
		tour.setWaysList(computeWaysList(list));
		tour.updateIsPositionConsistent(delivery.getId());
		tour.updateIsPositionConsistent(pickup.getId());

		return tour;
	}
	
	/**
	 * Enables to delete a step from the tour.
	 * 
	 * @param tour
	 * 		Contains the current order between pick up and delivery points.
	 * @param intersection
	 * 		The step to delete. 
	 * @return
	 * 		A new Tour updated.
	 */
	public Tour deleteStep(Tour tour, Intersection intersection) {
		if (intersection.getId().equals(request.getStartingLocation().getId())) {
			return tour;
		}

		List<Intersection> list = new ArrayList<Intersection>();

		for (Way w : tour.getWaysList()) {
			if (!w.getDeparture().getId().equals(intersection.getId())) {
				list.add(w.getDeparture());
			}
		}

		tour.setWaysList(computeWaysList(list));

		tour.updateIsPositionConsistent(intersection.getId());
		if (request.isPickUp(intersection.getId())) {
			tour.updateIsPositionConsistent(request.getDeliveryFromPickUp(intersection.getId()));
			request.getPickUpLocations().remove(intersection);
		}
		else {
			request.getDeliveryLocations().remove(intersection);
		}
		tour.setRequest(request);
		
		return tour;
	}
	
	/**
	 * Getter of bikeVelocity.
	 * 
	 * @return The bike Velocity in m.s-1.
	 */
	public Double getBikeVelocity() {
		return bikeVelocity;
	}
	
	/**
	 * Setter of bikeVelocity.
	 * 
	 * @param velocity
	 * 		The new bike velocity in m.s-1.
	 */
	public void setBikeVelocity(double velocity) {
		bikeVelocity = velocity;
	}
}
