// =====================================================
// Project: ngrx-lib-generator
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.ngrx_lib_generator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.ngrx_lib_generator.utils.GeneratorUtils;
import de.egladil.web.ngrx_lib_generator.utils.IPlaceholders;

/**
 * Generator
 */
public class Generator {

	private static final Logger LOGGER = LoggerFactory.getLogger(Generator.class);

	private final List<String> protokoll = new ArrayList<>();

	/**
	 * Generiert die Standard-ngrx-Library-Templates model, data und api.
	 *
	 * @param  options
	 *                            GeneratorOptions
	 * @return                    List das ist der report, der anschließend auf der Konsole ausgegeben wetrden kann.
	 * @throws GeneratorException
	 */
	public List<String> generateDefaultNgrxLibs(final GeneratorOptions options) throws GeneratorException {

		IGeneratorStrategy.getAllStrategies(options).forEach(s -> {

			this.generateSpecialLib(s);
		});

		if (options.isDryRun()) {

			protokoll
				.add("manuell muessen anschliessend in tsconfig.base.ts noch folgende library aliase hinzugefuegt werden:\n");

		} else {

			protokoll.add("=== ACHTUNG: bitte jetzt manuell in tsconfig.base.ts noch folgende library aliase hinzufuegen:\n");

		}

		IGeneratorStrategy.getAllStrategies(options).forEach(s -> {

			protokoll.add(GeneratorUtils.getTsConfigBaseEntry(s));
		});

		return protokoll;
	}

	/**
	 * @param  strategy
	 * @return          String absoluter Pfad zum root path der Library.
	 */
	String generateSpecialLib(final IGeneratorStrategy strategy) {

		protokoll.add(">>>>> library " + strategy.getLibraryType().toString() + " <<<<< \n");

		if (strategy.getOptions().isDryRun()) {

			protokoll.add("folgende Files werden generiert: \n");

		} else {

			protokoll.add("folgende Files wurden generiert: \n");
		}

		String libRootPath = GeneratorUtils.getLibraryRootPath(strategy);
		String libSourcePath = libRootPath + "/src";
		String libLibPath = libSourcePath + "/lib";

		Map<String, String> commonConfigFiles = new HashMap<>();

		for (String filename : IGeneratorStrategy.TS_CONFIG_FILES) {

			commonConfigFiles.put("/common-config-files/" + filename, libRootPath + "/" + filename);
		}
		commonConfigFiles.put("/common-config-files/src/test-setup.ts", libSourcePath + "/test-setup.ts");

		try {

			// gesamten Unterverzeichnisbaum erzeugen.
			FileUtils.forceMkdir(new File(libLibPath));

			if (!strategy.getOptions().isDryRun()) {

				LOGGER.debug("Verzeichnis {} erzeugt", libSourcePath);
			}

			for (String key : commonConfigFiles.keySet()) {

				String outFilePath = commonConfigFiles.get(key);
				this.replaceCdUpAndSave(key, outFilePath, strategy);
				LOGGER.debug("{} nach {} kopiert", key, outFilePath);
			}

			generateEslintConfig(strategy);
			generateProjectJson(strategy);
			generateJestConfigFile(strategy);

			strategy.generateIndexFile(protokoll);
			strategy.generateSpecialLibraryFiles(protokoll);

			return libRootPath;

		} catch (IOException e) {

			throw new GeneratorException("Irgendwas mit dem Pfad zum outputDir ist falsch: " + e.getMessage(), e);

		}

	}

	private void replaceCdUpAndSave(final String classpathPath, final String outfilePath, final IGeneratorStrategy strategy) throws IOException {

		File destinationFile = new File(outfilePath);

		if (strategy.getOptions().isDryRun()) {

			protokoll.add(destinationFile.getAbsolutePath() + "\n");
			return;
		}

		String valueCdUp = GeneratorUtils.getReplacementForCdUp(strategy.getOptions());

		try (InputStream in = getClass().getResourceAsStream(classpathPath); StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			content = content.replaceAll(IPlaceholders.CD_UP, valueCdUp);

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.add(destinationFile.getAbsolutePath() + "\n");
		}
	}

	private void generateEslintConfig(final IGeneratorStrategy strategy) throws IOException {

		File destinationFile = new File(GeneratorUtils.getLibraryRootPath(strategy) + "/" + IGeneratorStrategy.ESLINT_CONFIG_FILE);

		if (strategy.getOptions().isDryRun()) {

			protokoll.add(destinationFile.getAbsolutePath() + "\n");
			return;
		}

		try (InputStream in = this.getClass().getResourceAsStream("/common-config-files/" + IGeneratorStrategy.ESLINT_CONFIG_FILE);
			StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String projectFeatureName = getProjectFeatureName(strategy);
			LOGGER.debug("projectFeatureName: {}", projectFeatureName);
			String content = sw.toString();
			content = content.replaceAll(IPlaceholders.ANGULAR_SELECTOR_PREFIX, strategy.getOptions().getAngularSelectorPrefix());
			content = content.replaceAll(IPlaceholders.CD_UP, GeneratorUtils.getReplacementForCdUp(strategy.getOptions()));

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.add(destinationFile.getAbsolutePath() + "\n");
		}
	}

	private void generateProjectJson(final IGeneratorStrategy strategy) throws IOException {

		GeneratorOptions options = strategy.getOptions();

		File destinationFile = new File(GeneratorUtils.getLibraryRootPath(strategy) + "/" + IGeneratorStrategy.PROJECT_CONFIG_FILE);
		String libName = getProjectFeatureName(strategy);
		LOGGER.debug("libName: {}", libName);

		if (options.isDryRun()) {

			protokoll.add(destinationFile.getAbsolutePath() + "\n");
			return;
		}

		try (
			InputStream in = this.getClass()
				.getResourceAsStream("/" + strategy.getLibraryType().toString() + "/" + IGeneratorStrategy.PROJECT_CONFIG_FILE);
			StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);
			String content = sw.toString();
			content = content.replaceAll(IPlaceholders.FEATURE_NAME, options.getFeatureName());
			content = content.replace(IPlaceholders.LIB_NAME, libName);
			content = content.replaceAll(IPlaceholders.FEATURE_ROOT_PATH, options.getFeatureParentPath());
			content = content.replaceAll(IPlaceholders.CD_UP, GeneratorUtils.getReplacementForCdUp(options));

			List<String> tagList = new ArrayList<>(options.getTags());
			if (!tagList.contains("type:" + strategy.getLibraryType().toString()))
				tagList.add("type:" + strategy.getLibraryType().toString());

			String tags = GeneratorUtils.getReplacementForTags(tagList);

			content = content.replace(IPlaceholders.TAGS, tags);

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.add(destinationFile.getAbsolutePath() + "\n");
		}
	}

	private void generateJestConfigFile(final IGeneratorStrategy strategy) throws IOException {

		File destinationFile = new File(GeneratorUtils.getLibraryRootPath(strategy) + "/" + IGeneratorStrategy.JEST_CONFIG_FILE);
		String libName = getProjectFeatureName(strategy);
		LOGGER.debug("libName: {}", libName);

		GeneratorOptions options = strategy.getOptions();

		if (options.isDryRun()) {

			protokoll.add(destinationFile.getAbsolutePath() + "\n");
			return;
		}

		try (
			InputStream in = this.getClass()
				.getResourceAsStream("/" + strategy.getLibraryType().toString() + "/" + IGeneratorStrategy.JEST_CONFIG_FILE);
			StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String projectFeatureName = getProjectFeatureName(strategy);
			LOGGER.debug("projectFeatureName: {}", projectFeatureName);
			String content = sw.toString();
			content = content.replace(IPlaceholders.LIB_NAME, libName);
			content = content.replaceAll(IPlaceholders.FEATURE_ROOT_PATH, options.getFeatureParentPath());
			content = content.replace(IPlaceholders.FEATURE_NAME, options.getFeatureName());
			content = content.replaceAll(IPlaceholders.CD_UP, GeneratorUtils.getReplacementForCdUp(options));

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.add(destinationFile.getAbsolutePath() + "\n");
		}
	}

	/**
	 * @return
	 */
	private String getProjectFeatureName(final IGeneratorStrategy strategy) {

		return GeneratorUtils.getProjectFeatureNamePrefix(strategy.getOptions()) + "-" + strategy.getLibraryType().toString();
	}

	List<String> getProtokoll() {

		return protokoll;
	}
}
