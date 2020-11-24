package controller;

import modele.CityMap;
import modele.Request;
import view.Window;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

public class InitState implements State {

	
	@Override
	public void loadMap(Controller c,Window w) {
		String path = w.createDialogBoxToGetFilePath();
		if(path != null) 
		{
			System.out.println("Affichage de la carte normalement");
			XMLCityMapParser p = new XMLCityMapParser(path);
			CityMap cityMap = p.parse();
			w.graphicalView.setCityMap(cityMap);
		}
		else 
		{
			System.out.println("Echec de l'obtention du chemin avec la boite de dialogue");
		}
	}
	
	@Override
	public void loadRequest(Controller c,Window w) {
		String path = w.createDialogBoxToGetFilePath();
		if(path != null) 
		{
			System.out.println("Affichage de la requête normalement");
			XMLRequestParser p = new XMLRequestParser(path);
			Request request = p.parse();
			w.graphicalView.setRequest(request);
		}
		else 
		{
			System.out.println("Echec de l'obtention du chemin avec la boite de dialogue");
		}
		
	}
	
}
