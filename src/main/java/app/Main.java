package app;

import controller.Controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class, should be run to start the program
 */
public class Main {

	/**
	 * The logger instance, used to log relevant information to the console.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	/**
	 * The main method, starting point of Deliver'IF. Creates a Controller instance.
	 * 
	 * @param args arguments (ignored)
	 */
	public static void main(String[] args) {
		logger.info("Creating controller");
		Locale.setDefault(new Locale("en", "US"));
		new Controller();
	}
}
