// =====================================================
// Project: ngrx-lib-generator
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.ngrx_lib_generator.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import de.egladil.web.ngrx_lib_generator.GeneratorOptions;
import de.egladil.web.ngrx_lib_generator.IGeneratorStrategy;
import de.egladil.web.ngrx_lib_generator.LibraryType;
import de.egladil.web.ngrx_lib_generator.utils.GeneratorUtils;
import de.egladil.web.ngrx_lib_generator.utils.IPlaceholders;

/**
 * DataGeneratorStrategy
 */
public class DataGeneratorStrategy implements IGeneratorStrategy {

	private static final String HTTP_SERVICE_FILE = "xxx-http.service.ts";

	private static final String ACTIONS_FILE = "xxx.actions.ts";

	private static final String REDUCER_FILE = "xxx.reducer.ts";

	private static final String SELECTORS_FILE = "xxx.selectors.ts";

	private static final String EFFECTS_FILE = "xxx.effects.ts";

	private final GeneratorOptions options;

	public DataGeneratorStrategy(final GeneratorOptions options) {

		super();
		this.options = options;
	}

	@Override
	public LibraryType getLibraryType() {

		return LibraryType.data;
	}

	@Override
	public GeneratorOptions getOptions() {

		return options;
	}

	@Override
	public void generateIndexFile(final List<String> protokoll) throws IOException {

		String libraryRootPath = GeneratorUtils.getLibraryRootPath(this);
		String librarySourcePath = libraryRootPath + "/src";

		File destinationFile = new File(librarySourcePath + "/" + INDEX_FILE);

		if (options.isDryRun()) {

			protokoll.add(destinationFile.getAbsolutePath() + "\n");
			return;
		}

		try (
			InputStream in = this.getClass()
				.getResourceAsStream("/" + getLibraryType().toString() + "/src/" + INDEX_FILE);
			StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			content = content.replaceAll(IPlaceholders.FEATURE_NAME_CAMEL_CASE,
				GeneratorUtils.transformToCamelCase(options.getFeatureName()));
			content = content.replaceAll(IPlaceholders.FEATURE_NAME, options.getFeatureName());

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.add(destinationFile.getAbsolutePath() + "\n");
		}

	}

	@Override
	public void generateSpecialLibraryFiles(final List<String> protokoll) throws IOException {

		String libraryRootPath = GeneratorUtils.getLibraryRootPath(this);
		String librarySourcePath = libraryRootPath + "/src/lib";
		String httpServicePath = librarySourcePath + "/" + HTTP_SERVICE_FILE.replace("xxx", options.getFeatureName());

		String ngrxDirPath = librarySourcePath + "/+state";
		String actionsPath = ngrxDirPath + "/" + ACTIONS_FILE.replace("xxx", options.getFeatureName());
		String reducerPath = ngrxDirPath + "/" + REDUCER_FILE.replace("xxx", options.getFeatureName());
		String selectorsPath = ngrxDirPath + "/" + SELECTORS_FILE.replace("xxx", options.getFeatureName());
		String effectsPath = ngrxDirPath + "/" + EFFECTS_FILE.replace("xxx", options.getFeatureName());

		if (options.isDryRun()) {

			protokoll.add(actionsPath + "\n");
			protokoll.add(reducerPath + "\n");
			protokoll.add(selectorsPath + "\n");
			protokoll.add(effectsPath + "\n");
			protokoll.add(httpServicePath + "\n");
			return;

		}

		this.generateActions(actionsPath, protokoll);
		this.generateReducer(reducerPath, protokoll);
		this.generateSelectors(selectorsPath, protokoll);
		this.generateEffects(effectsPath, protokoll);
		this.generateHttpService(httpServicePath, protokoll);

	}

	void generateActions(final String pathDestinationFile, final List<String> protokoll) throws IOException {

		try (
			InputStream in = this.getClass()
				.getResourceAsStream("/" + getLibraryType().toString() + "/src/lib/+state/" + ACTIONS_FILE);
			StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			content = content.replaceAll(IPlaceholders.DTO_NAME, options.getDtoName());
			content = content.replace(IPlaceholders.LIB_ALIAS_PREFIX, options.getLibAliasPrefix());
			content = content.replaceAll(IPlaceholders.FEATURE_NAME, options.getFeatureName());

			// System.out.println(content);

			File destinationFile = new File(pathDestinationFile);

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.add(destinationFile.getAbsolutePath() + "\n");
		}
	}

	void generateReducer(final String pathDestinationFile, final List<String> protokoll) throws IOException {

		try (
			InputStream in = this.getClass()
				.getResourceAsStream("/" + getLibraryType().toString() + "/src/lib/+state/" + REDUCER_FILE);
			StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			content = content.replaceAll(IPlaceholders.DTO_NAME, options.getDtoName());
			content = content.replace(IPlaceholders.LIB_ALIAS_PREFIX, options.getLibAliasPrefix());
			content = content.replaceAll(IPlaceholders.FEATURE_NAME_CAMEL_CASE,
				GeneratorUtils.transformToCamelCase(options.getFeatureName()));
			content = content.replaceAll(IPlaceholders.FEATURE_NAME, options.getFeatureName());

			File destinationFile = new File(pathDestinationFile);

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.add(destinationFile.getAbsolutePath() + "\n");
		}
	}

	void generateSelectors(final String pathDestinationFile, final List<String> protokoll) throws IOException {

		try (
			InputStream in = this.getClass()
				.getResourceAsStream("/" + getLibraryType().toString() + "/src/lib/+state/" + SELECTORS_FILE);
			StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			content = content.replaceAll(IPlaceholders.FEATURE_NAME_CAMEL_CASE,
				GeneratorUtils.transformToCamelCase(options.getFeatureName()));
			content = content.replaceAll(IPlaceholders.FEATURE_NAME, options.getFeatureName());

			File destinationFile = new File(pathDestinationFile);

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.add(destinationFile.getAbsolutePath() + "\n");
		}
	}

	void generateEffects(final String pathDestinationFile, final List<String> protokoll) throws IOException {

		try (
			InputStream in = this.getClass()
				.getResourceAsStream("/" + getLibraryType().toString() + "/src/lib/+state/" + EFFECTS_FILE);
			StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			content = content.replaceAll(IPlaceholders.FEATURE_NAME_CAMEL_CASE,
				GeneratorUtils.transformToCamelCase(options.getFeatureName()));
			content = content.replaceAll(IPlaceholders.FEATURE_NAME, options.getFeatureName());

			File destinationFile = new File(pathDestinationFile);

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.add(destinationFile.getAbsolutePath() + "\n");
		}
	}

	void generateHttpService(final String pathDestinationFile, final List<String> protokoll) throws IOException {

		try (
			InputStream in = this.getClass()
				.getResourceAsStream("/" + getLibraryType().toString() + "/src/lib/" + HTTP_SERVICE_FILE);
			StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			content = content.replaceAll(IPlaceholders.FEATURE_NAME_CAMEL_CASE,
				GeneratorUtils.transformToCamelCase(options.getFeatureName()));
			content = content.replaceAll(IPlaceholders.FEATURE_NAME, options.getFeatureName());
			content = content.replaceAll(IPlaceholders.DTO_NAME, options.getDtoName());
			content = content.replaceAll(IPlaceholders.LIB_ALIAS_PREFIX, options.getLibAliasPrefix());

			File destinationFile = new File(pathDestinationFile);

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.add(destinationFile.getAbsolutePath() + "\n");
		}
	}
}
