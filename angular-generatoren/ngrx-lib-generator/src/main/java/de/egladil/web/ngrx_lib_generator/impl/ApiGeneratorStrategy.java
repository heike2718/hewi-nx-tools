// =====================================================
// Project: ngrx-lib-generator
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.ngrx_lib_generator.impl;

import java.io.IOException;
import java.util.List;

import de.egladil.web.ngrx_lib_generator.GeneratorOptions;
import de.egladil.web.ngrx_lib_generator.IGeneratorStrategy;
import de.egladil.web.ngrx_lib_generator.LibraryType;

/**
 * ApiGeneratorStrategy
 */
public class ApiGeneratorStrategy implements IGeneratorStrategy {

	private final GeneratorOptions options;

	public ApiGeneratorStrategy(final GeneratorOptions options) {

		this.options = options;
	}

	@Override
	public LibraryType getLibraryType() {

		return LibraryType.api;
	}

	@Override
	public GeneratorOptions getOptions() {

		return this.options;
	}

	@Override
	public void generateIndexFile(final List<String> protokoll) throws IOException {

	}

	@Override
	public void generateSpecialLibraryFiles(final List<String> protokoll) throws IOException {

	}

}
