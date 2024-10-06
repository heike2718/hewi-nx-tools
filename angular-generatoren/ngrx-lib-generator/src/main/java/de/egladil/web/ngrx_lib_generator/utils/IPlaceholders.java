// =====================================================
// Project: ngrx-lib-generator
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.ngrx_lib_generator.utils;

/**
 * IPlaceholders
 */
public interface IPlaceholders {

	String MESSAGE_FORMAT_PATTERN_LIB_ALIAS = "\"{0}\": " + "[" + "\"{1}\"],";

	String CD_UP = "#cd-up#";

	/**
	 * Aus options.prefix
	 */
	String ANGULAR_SELECTOR_PREFIX = "#angular-selector-prefix#";

	/**
	 * Das ist der Name des features im Store.<br>
	 * <br>
	 * Beispiel: auth
	 */
	String FEATURE_NAME = "#feature-name#";

	/**
	 * Lib-Name in den Config-Files project.json und jest.config.ts<br>
	 * <br>
	 * Beispiel: profil-app-src-app-auth-model
	 */
	String LIB_NAME = "#lib-name#";

	/**
	 * Das ist der relative Pfad im nx-Workspace, unter dem die 3 zu generierenden Libs api, data und model liegen sollen. <br>
	 * <br>
	 * Beispiel: apps/profil-app/src/app/auth
	 */
	String FEATURE_ROOT_PATH = "#feature-root-path#";

	/**
	 * Platzhalter für die Tags.
	 */
	String TAGS = "#tags#";

	String DTO_NAME = "#dto-name#";
}
