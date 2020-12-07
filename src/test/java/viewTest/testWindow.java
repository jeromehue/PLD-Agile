package viewTest;

import controller.Controller;
import modele.Tour;
import view.Window;

import org.junit.jupiter.api.Test;

public class testWindow {
	
	@Test
	void test() {
		Controller c = new Controller();
		Tour t = new Tour();
		Window w = new Window(c, t);
	}
}
