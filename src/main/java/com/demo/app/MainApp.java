package com.demo.app;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Main Application class.
 * 
 * @author Frédéric Delorme<frederic.delorme@ik.me>
 * @since 1.0.0
 */
public class MainApp {

	ResourceBundle i18n = ResourceBundle.getBundle("i18n.messages", Locale.ROOT);
	Properties config = new Properties();

	private String name = "";

	public MainApp(String name) {
		this.name = name;
	}

	public void run(String[] args) {
		initialize(args);
		process();
		dispose();
	}

	public void initialize(String[] args) {
		log(MainApp.class, "INFO", "Initializing...");
		for (String arg : args) {
			log(MainApp.class, "DEBUG", "arg:%s", arg);
		}
		try {
			config.load(MainApp.class.getResourceAsStream("/config.properties"));
		} catch (IOException e) {
			log(MainApp.class, "ERROR", "Unable to read Configuration file");
		}
	}

	public void process() {
		log(MainApp.class, "INFO", "Processing...");
	}

	public void dispose() {
		log(MainApp.class, "INFO", "Releasing resources...");
	}

	public static void main(String[] args) {
		MainApp app = new MainApp("testapp");
		app.run(args);
	}

	public static void log(Class<?> className, String level, String message, Object... args) {
		ZonedDateTime ldt = ZonedDateTime.now(ZoneId.systemDefault());
		System.out.printf("%s | %s | %s | %s%n",
				DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ldt),
				className.getSimpleName(),
				level,
				String.format(message, args));
	}

}
