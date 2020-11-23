package controller;

import modele.CityMap;
import view.Window;
import xml.XMLCityMapParser;

public class Controller {
	private CityMap cityMap;
	public CityMap getCityMap() {
		return cityMap;
	}
	private Window window;
	
	public Controller() {
		XMLCityMapParser p = new XMLCityMapParser("src/main/resources/largeMap.xml");
		cityMap = p.parse();
		window = new Window(this);
	}
	public void setCityMap(CityMap cityMap) {
		this.cityMap = cityMap;
	}

}
