package de.egladil.web.ngrx_lib_generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Hello world!
 */
public class GeneratorApp {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorApp.class);

	@Parameter(names = { "--length", "-l" })
	int length;

	@Parameter(names = { "--pattern", "-p" })
	int pattern;

	@Parameter(names = { "--workdir", "-w" }, description = "Pfad zum nx-workspace")
	String workdir;

	private GeneratorOptions options;

	public static void main(final String[] args) {

		GeneratorApp generatorApp = new GeneratorApp();

		if (args.length == 0) {

			generatorApp.runInInteractiveMode();
		} else {

			generatorApp.runInAutomaticMode(args);
		}
	}

	/**
	 *
	 */
	private void runInInteractiveMode() {

		this.options = new GeneratorOptions();

	}

	/**
	 * @param args
	 */
	private void runInAutomaticMode(final String[] args) {

		JCommander.newBuilder()
			.addObject(this)
			.build()
			.parse(args);

		this.options = new GeneratorOptions();
		this.run();
	}

	/**
	 *
	 */
	private void run() {

		System.out.println(this.options.toString());

		try {

		} catch (GeneratorException e) {

			LOGGER.error("Beim Generieren ist leider ein Fehler aufgetreten: " + e.getMessage());
			LOGGER.debug(e.getMessage(), e);

		}
	}
}
