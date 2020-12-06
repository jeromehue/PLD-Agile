package controller;

import modele.Way;

import javax.swing.JButton;

//import algo.Pcc;
import modele.Intersection;
import modele.Tour;
//import modele.CityMap;
//import modele.Request;
import view.Window;

public class OrderModificationState implements State {

	
	public void clickOnStep(Window w, Way wa, JButton button, Tour t) {
		System.out.println("[Order Modification State] Modify order of tour in controller");
		System.out.println("[Order Modification State] Intersection to be modify" + wa.getDeparture());
		
		// Testing purposes
		int newIndex = w.displaySelectOrderDialog();
		Intersection intersection = wa.getDeparture();
		//Tour t = w.graphicalView.getTour(); 
		//CityMap cityMap = w.graphicalView.getCityMap();
		//Request request = w.graphicalView.getRequest();
		//Pcc shortestPathComputer = new Pcc(cityMap , request);
		//Tour newTour = shortestPathComputer.changeOrder( t,  intersection,  newIndex);
		System.out.println("Tour : " + t + ",intersection : " +intersection + ",newIndex :" + newIndex);
		
	}
}
