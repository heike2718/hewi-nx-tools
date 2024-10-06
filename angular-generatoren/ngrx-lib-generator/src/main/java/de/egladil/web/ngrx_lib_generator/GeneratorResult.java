// =====================================================
// Project: ngrx-lib-generator
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.ngrx_lib_generator;

/**
 * GeneratorResult
 */
public class GeneratorResult {

	private String libRootPath;

	private String report;

	private String tsConfigBaseLibDeclaration;

	public String getLibRootPath() {

		return libRootPath;
	}

	public void setLibRootPath(final String path) {

		this.libRootPath = path;
	}

	public String getReport() {

		return report;
	}

	public void setReport(final String report) {

		this.report = report;
	}

	public String getTsConfigBaseLibDeclaration() {

		return tsConfigBaseLibDeclaration;
	}

	public void setTsConfigBaseLibDeclaration(final String libDeclaration) {

		this.tsConfigBaseLibDeclaration = libDeclaration;
	}

}
