// =====================================================
// Project: ngrx-lib-generator
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.ngrx_lib_generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.egladil.web.ngrx_lib_generator.impl.DataGeneratorStrategy;
import de.egladil.web.ngrx_lib_generator.impl.ModelGeneratorStrategy;

/**
 * IGeneratorStrategy
 */
public interface IGeneratorStrategy {

	String INDEX_FILE = "index.ts";

	String ESLINT_CONFIG_FILE = "eslint.config.js";

	String JEST_CONFIG_FILE = "jest.config.ts";

	String PROJECT_CONFIG_FILE = "project.json";

	List<String> TS_CONFIG_FILES = Arrays
		.asList(new String[] { "tsconfig.json", "tsconfig.lib.json", "tsconfig.spec.json" });

	/**
	 * Gibt den Typ des Generators zurück.
	 *
	 * @return
	 */
	LibraryType getLibraryType();

	/**
	 * Die Optionen, mit denen generiert werden soll.
	 *
	 * @return
	 */
	GeneratorOptions getOptions();

	/**
	 * @param protokoll
	 *                  StringBuffer
	 */
	void generateIndexFile(List<String> protokoll) throws IOException;

	/**
	 * Generiert die libraryspezifischen Files.
	 *
	 * @param protokoll
	 */
	void generateSpecialLibraryFiles(List<String> protokoll) throws IOException;

	/**
	 * Liste aller Strategien, damit sie sequenziell generieren können.
	 *
	 * @param  options
	 *                 GeneratorOptions
	 * @return         List
	 */
	static List<IGeneratorStrategy> getAllStrategies(final GeneratorOptions options) {

		List<IGeneratorStrategy> result = new ArrayList<>();
		result.add(new ModelGeneratorStrategy(options));
		result.add(new DataGeneratorStrategy(options));
		return result;

	}

}
