package viewTest;

import controller.Controller;
import modele.Tour;
import view.Window;

public class testWindow {
	

	void test() {
		Controller c = new Controller();
		Tour t = new Tour();
		Window w = new Window(c, t);
	}
}
