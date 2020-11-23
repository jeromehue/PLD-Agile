package controller;

import view.Window;

public class InitState implements State {

	
	@Override
	public void loadMap(Window w) {
		System.out.println("Affichage de la carte normalement");
	}
	
}
