package app;

import controller.Controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		logger.info("Creating controller");
		Locale.setDefault(new Locale("en", "US"));
		new Controller();
	}
}
