package com.demo.app;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
	private boolean exit = false;
	private boolean testMode = false;
	private String configFilePath = "/config.properties";
	private static int debugLevel = 0;

	public MainApp(String name) {
		this.name = name;
	}

	/**
	 * Main processing entry point tfor this program.
	 * 
	 * @param args the array of arguments coming from the Java Command Line
	 *             Interface.
	 */
	public void run(String[] args) {
		initialize(args);
		process();
		dispose();
	}

	/**
	 * Initialize the program with CLI arguemnts, and read the Configuration file.
	 * 
	 * @param args the array of arguments coming from the Java Command Line
	 *             Interface.
	 */
	public void initialize(String[] args) {
		log(MainApp.class, "INFO", "Initializing %s ...", name);
		Map<String, String> maps = Arrays.asList(args).stream().map(e -> e.split("="))
				.collect(Collectors.toMap(e -> e[0], e -> e[1]));
		extractConfigAttributes(maps);
		for (String arg : args) {
			debug(MainApp.class, "arg:%s", arg);
		}
		loadConfigurationFrom(configFilePath);
		// overload loaded values with CLI arguments ones
		extractConfigAttributes(maps);
	}

	/**
	 * Read Properties file as configuration file to feed the internals parameters
	 * 
	 * @param configFilePath a path to a JAR internal configuration file.
	 */
	private void loadConfigurationFrom(String configFilePath) {
		try {
			info(MainApp.class, "Load configuration from file %s", configFilePath);
			config.load(MainApp.class.getResourceAsStream(configFilePath));
			Map<String, String> maps = config.entrySet().stream()
					.collect(Collectors.toMap(e -> (String) e.getKey(), e -> (String) e.getValue()));
			extractConfigAttributes(maps);
		} catch (IOException e) {
			error(MainApp.class, "Unable to read Configuration file");
		}
	}

	/**
	 * Extract values for internal configuration from the map.
	 * 
	 * @param map a Map of key/values to used to feed the internal configuration
	 *            attributes.
	 */
	private void extractConfigAttributes(Map<String, String> map) {
		map.forEach((k, v) -> {
			switch (k) {
				case "c", "config", "confiFilePath" -> {
					configFilePath = v;
					info(MainApp.class, "configuration : configuration file path set to %s", configFilePath);
				}
				case "d", "debug", "app.debug.level" -> {
					MainApp.setDebugLevel(Integer.parseInt(v));
					info(MainApp.class, "configuration : debug level set to %d", debugLevel);

				}
				case "n", "name", "app.name" -> {
					name = v;
					info(MainApp.class, "configuration : Application name  %s", name);
				}
				case "x", "exit", "app.exit" -> {
					exit = Boolean.parseBoolean(v);
					info(MainApp.class, "configuration : auto exit %s", exit);
				}
				case "test", "tm", "app.test.mode" -> {
					testMode = Boolean.parseBoolean(v);
					info(MainApp.class, "configuration : start with test mode %s", testMode);
				}
				default -> {
					warn(MainApp.class, "Unknown configuration argument %s=%s", k, v);
				}
			}
		});

	}

	/**
	 * Main processing of the program.
	 */
	protected void process() {
		info(MainApp.class, "Processing...");
		do {
			// Todo Code what is needed.
		} while (!(exit || testMode));
		info(MainApp.class, "Process done.");
	}

	/**
	 * Release internal resources.
	 */
	protected void dispose() {
		info(MainApp.class, "Releasing resources...");
		info(MainApp.class, "Release done.");

	}

	/**
	 * The main entry point for this program.
	 * 
	 * @param args the array of arguments coming from the Java Command Line
	 *             Interface.
	 */
	public static void main(String[] args) {
		MainApp app = new MainApp("testapp");
		app.run(args);
	}

	protected static void debug(Class<?> className, String message, Object... args) {
		log(className, "DEBUG", message, args);
	}

	protected static void info(Class<?> className, String message, Object... args) {
		log(className, "INFO", message, args);
	}

	protected static void warn(Class<?> className, String message, Object... args) {
		log(className, "WARN", message, args);
	}

	protected static void error(Class<?> className, String message, Object... args) {
		log(className, "ERROR", message, args);
	}

	protected static void fatal(Class<?> className, String message, Object... args) {
		log(className, "FATAL", message, args);
	}

	private static void log(Class<?> className, String level, String message, Object... args) {
		ZonedDateTime ldt = ZonedDateTime.now(ZoneId.systemDefault());
		if ("ERROR,FATAL".contains(level)) {
			System.err.printf("%s | %s | %s | %s%n",
					DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ldt),
					className.getCanonicalName(),
					level,
					String.format(message, args));
		}
		if ("DEBUG".contains(level) && debugLevel == 0) {
			return;
		}
		System.out.printf("%s | %s | %s | %s%n",
				DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ldt),
				className.getCanonicalName(),
				level,
				String.format(message, args));
	}

	public static synchronized void setDebugLevel(int dl) {
		debugLevel = dl;
	}
}
