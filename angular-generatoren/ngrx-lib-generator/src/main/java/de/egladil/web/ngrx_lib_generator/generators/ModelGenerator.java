// =====================================================
// Project: ngrx-lib-generator
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.ngrx_lib_generator.generators;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egladil.web.ngrx_lib_generator.GeneratorException;
import de.egladil.web.ngrx_lib_generator.GeneratorOptions;
import de.egladil.web.ngrx_lib_generator.GeneratorResult;
import de.egladil.web.ngrx_lib_generator.utils.GeneratorUtils;
import de.egladil.web.ngrx_lib_generator.utils.IPlaceholders;

/**
 * ModelGenerator
 */
public class ModelGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModelGenerator.class);

	private static final List<String> TS_CONFIG_FILES = Arrays
		.asList(new String[] { "tsconfig.json", "tsconfig.lib.json", "tsconfig.spec.json" });

	private static final String PROJECT_CONFIG_FILE = "project.json";

	private static final String JEST_CONFIG_FILE = "jest.config.ts";

	private static final String ESLINT_CONFIG_FILE = "eslint.config.js";

	private static final String INDEX_FILE = "index.ts";

	private static final String MODLEL_FILE = "xxx.model.ts";

	private StringBuffer protokoll;

	GeneratorResult generatorResult;

	private final GeneratorOptions options;

	public ModelGenerator(final GeneratorOptions options) {

		super();
		this.options = options;
	}

	public GeneratorResult generateLib() throws GeneratorException {

		generatorResult = new GeneratorResult();

		protokoll = new StringBuffer().append(">>>>> library model <<<<< \n");

		if (options.isDryRun()) {

			protokoll.append("folgende Files werden generiert: \n");
		} else {

			protokoll.append("folgende Files wurden generiert: \n");
		}

		String libRootPath = getModelRootPath();
		String modelSourcePath = libRootPath + "/src";
		String modelLibPath = modelSourcePath + "/lib";

		Map<String, String> nxConfigFilesWithoutPlaceholders = new HashMap<>();

		for (String filename : TS_CONFIG_FILES) {

			nxConfigFilesWithoutPlaceholders.put("/model/" + filename, libRootPath + "/" + filename);
		}
		nxConfigFilesWithoutPlaceholders.put("/model/src/test-setup.ts", modelSourcePath + "/test-setup.ts");

		try {

			// gesamten Unterverzeichnisbaum erzeugen.
			FileUtils.forceMkdir(new File(modelLibPath));

			if (!options.isDryRun()) {

				LOGGER.debug("Verzeichnis {} erzeugt", modelSourcePath);
			}

			for (String key : nxConfigFilesWithoutPlaceholders.keySet()) {

				String outFilePath = nxConfigFilesWithoutPlaceholders.get(key);
				this.replaceCdUpAndSave(key, outFilePath);
				LOGGER.debug("{} nach {} kopiert", key, outFilePath);
			}

			generateEslintConfig();
			generateProjectJson();
			generateJestConfigFile();
			generateIndexFile();
			generateModelFile();

		} catch (IOException e) {

			throw new GeneratorException("Irgendwas mit dem Pfad zum outputDir ist falsch: " + e.getMessage(), e);

		}

		generatorResult.setLibRootPath(libRootPath);
		generatorResult.setReport(protokoll.toString());
		return generatorResult;

	}

	private void generateProjectJson() throws IOException {

		File destinationFile = new File(getModelRootPath() + "/" + PROJECT_CONFIG_FILE);
		String libName = GeneratorUtils.getProjectFeatureNamePrefix(options) + "-model";
		LOGGER.debug("libName: {}", libName);

		if (options.isDryRun()) {

			protokoll.append(destinationFile.getAbsolutePath() + "\n");
			return;
		}

		try (InputStream in = this.getClass().getResourceAsStream("/model/" + PROJECT_CONFIG_FILE);
			StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);
			String content = sw.toString();
			content = content.replaceAll(IPlaceholders.FEATURE_NAME, options.getFeatureName());
			content = content.replace(IPlaceholders.LIB_NAME, libName);
			content = content.replaceAll(IPlaceholders.FEATURE_ROOT_PATH, options.getFeatureParentPath());
			content = content.replaceAll(IPlaceholders.CD_UP, GeneratorUtils.getReplacementForCdUp(options));

			List<String> tagList = new ArrayList<>(options.getTags());
			if (!tagList.contains("type:model"))
				tagList.add("type:model");

			String tags = GeneratorUtils.getReplacementForTags(tagList);

			content = content.replace(IPlaceholders.TAGS, tags);

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.append(destinationFile.getAbsolutePath() + "\n");
		}
	}

	private void generateJestConfigFile() throws IOException {

		File destinationFile = new File(getModelRootPath() + "/" + JEST_CONFIG_FILE);
		String libName = GeneratorUtils.getProjectFeatureNamePrefix(options) + "-model";
		LOGGER.debug("libName: {}", libName);

		if (options.isDryRun()) {

			protokoll.append(destinationFile.getAbsolutePath() + "\n");
			return;
		}

		try (InputStream in = this.getClass().getResourceAsStream("/model/" + JEST_CONFIG_FILE);
			StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String projectFeatureName = GeneratorUtils.getProjectFeatureNamePrefix(options) + "-model";
			LOGGER.debug("projectFeatureName: {}", projectFeatureName);
			String content = sw.toString();
			content = content.replace(IPlaceholders.LIB_NAME, libName);
			content = content.replaceAll(IPlaceholders.FEATURE_ROOT_PATH, options.getFeatureParentPath());
			content = content.replace(IPlaceholders.FEATURE_NAME, options.getFeatureName());
			content = content.replaceAll(IPlaceholders.CD_UP, GeneratorUtils.getReplacementForCdUp(options));

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.append(destinationFile.getAbsolutePath() + "\n");
		}
	}

	private void generateEslintConfig() throws IOException {

		File destinationFile = new File(getModelRootPath() + "/" + ESLINT_CONFIG_FILE);

		if (options.isDryRun()) {

			protokoll.append(destinationFile.getAbsolutePath() + "\n");
			return;
		}

		try (InputStream in = this.getClass().getResourceAsStream("/model/" + ESLINT_CONFIG_FILE);
			StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String projectFeatureName = GeneratorUtils.getProjectFeatureNamePrefix(options) + "-model";
			LOGGER.debug("projectFeatureName: {}", projectFeatureName);
			String content = sw.toString();
			content = content.replaceAll(IPlaceholders.ANGULAR_SELECTOR_PREFIX, options.getAngularSelectorPrefix());
			content = content.replaceAll(IPlaceholders.CD_UP, GeneratorUtils.getReplacementForCdUp(options));

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.append(destinationFile.getAbsolutePath() + "\n");
		}
	}

	private void generateIndexFile() throws IOException {

		String modelRootPath = getModelRootPath();
		String modelSourcePath = modelRootPath + "/src";

		File destinationFile = new File(modelSourcePath + "/" + INDEX_FILE);

		if (options.isDryRun()) {

			protokoll.append(destinationFile.getAbsolutePath() + "\n");
			return;
		}

		try (InputStream in = this.getClass().getResourceAsStream("/model/src/" + INDEX_FILE);
			StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			content = content.replace(IPlaceholders.FEATURE_NAME, options.getFeatureName());

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.append(destinationFile.getAbsolutePath() + "\n");
			generatorResult.setTsConfigBaseLibDeclaration(this.getTsConfigBaseEntry());
		}
	}

	private void generateModelFile() throws IOException {

		String modelRootPath = getModelRootPath();
		String modelSourcePath = modelRootPath + "/src/lib";

		String nameModelFile = MODLEL_FILE.replace("xxx", options.getFeatureName());

		File destinationFile = new File(modelSourcePath + "/" + nameModelFile);

		if (options.isDryRun()) {

			protokoll.append(destinationFile.getAbsolutePath() + "\n");
			return;
		}

		try (InputStream in = this.getClass().getResourceAsStream("/model/src/lib/" + MODLEL_FILE);
			StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			content = content.replace(IPlaceholders.DTO_NAME, options.getDtoName());

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.append(destinationFile.getAbsolutePath() + "\n");
			generatorResult.setTsConfigBaseLibDeclaration(this.getTsConfigBaseEntry());
		}
	}

	private void replaceCdUpAndSave(final String classpathPath, final String outfilePath) throws IOException {

		File destinationFile = new File(outfilePath);

		if (options.isDryRun()) {

			protokoll.append(destinationFile.getAbsolutePath() + "\n");
			return;
		}

		String valueCdUp = GeneratorUtils.getReplacementForCdUp(options);

		try (InputStream in = getClass().getResourceAsStream(classpathPath); StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			content = content.replaceAll(IPlaceholders.CD_UP, valueCdUp);

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.append(destinationFile.getAbsolutePath() + "\n");
		}
	}

	String getModelRootPath() {

		return options.getNxWorkspaceDir() + "/" + options.getFeatureParentPath() + "/" + options.getFeatureName() + "/model";
	}

	/**
	 * @return
	 */
	String getTsConfigBaseEntry() {

		String path = options.getFeatureParentPath() + "/" + options.getFeatureName() + "/model/src/index.ts";
		String libAlias = options.getLibAliasPrefix() + "/" + options.getFeatureName() + "/model";
		return MessageFormat.format(IPlaceholders.MESSAGE_FORMAT_PATTERN_LIB_ALIAS, new Object[] { libAlias, path });
	}
}
