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
 * ModelGeneratorStrategy
 */
public class ModelGeneratorStrategy implements IGeneratorStrategy {

	private static final String MODLEL_FILE = "xxx.model.ts";

	private final GeneratorOptions options;

	public ModelGeneratorStrategy(final GeneratorOptions options) {

		super();
		this.options = options;
	}

	@Override
	public LibraryType getLibraryType() {

		return LibraryType.model;
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

		try (InputStream in = this.getClass().getResourceAsStream("/" + getLibraryType().toString() + "/src/" + INDEX_FILE);
			StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			content = content.replace(IPlaceholders.FEATURE_NAME, options.getFeatureName());

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.add(destinationFile.getAbsolutePath() + "\n");
		}
	}

	@Override
	public void generateSpecialLibraryFiles(final List<String> protokoll) throws IOException {

		String libraryRootPath = GeneratorUtils.getLibraryRootPath(this);
		String librarySourcePath = libraryRootPath + "/src/lib";

		String nameModelFile = MODLEL_FILE.replace("xxx", options.getFeatureName());

		File destinationFile = new File(librarySourcePath + "/" + nameModelFile);

		if (options.isDryRun()) {

			protokoll.add(destinationFile.getAbsolutePath() + "\n");
			return;
		}

		try (InputStream in = this.getClass().getResourceAsStream("/" + getLibraryType().toString() + "/src/lib/" + MODLEL_FILE);
			StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			content = content.replace(IPlaceholders.DTO_NAME, options.getDtoName());

			FileUtils.writeStringToFile(destinationFile, content, StandardCharsets.UTF_8);
			protokoll.add(destinationFile.getAbsolutePath() + "\n");
		}
	}
}
