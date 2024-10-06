// =====================================================
// Project: ngrx-lib-generator
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.ngrx_lib_generator;

/**
 * GeneratorException
 */
public class GeneratorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GeneratorException(final String message, final Throwable cause) {

		super(message, cause);

	}

	public GeneratorException(final String message) {

		super(message);

	}

}
