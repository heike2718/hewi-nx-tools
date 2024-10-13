// =====================================================
// Project: ngrx-lib-generator
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.ngrx_lib_generator.utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.egladil.web.ngrx_lib_generator.GeneratorOptions;
import de.egladil.web.ngrx_lib_generator.IGeneratorStrategy;

/**
 * GeneratorUtils
 */
public class GeneratorUtils {

	/**
	 *
	 */
	private static final String DIR_UP = "../";

	/**
	 * Wandelt den relativen Pfad für das Feature in einen Minus-saparierten String um. Dieser muss noch mit -api, -model, -data
	 * gesuffixed werden und ist danach das replacement für {feature-name}
	 *
	 * @param  options
	 * @return         String
	 */
	public static String getProjectFeatureNamePrefix(final GeneratorOptions options) {

		String featurePath = options.getFeatureParentPath();
		String[] featurePathTokens = featurePath.split("/");
		List<String> featurePathList = new ArrayList<>(Arrays.asList(featurePathTokens));
		featurePathList.add(options.getFeatureName());

		List<String> filteredList = featurePathList.stream().filter(p -> !"apps".equals(p) && !"libs".equals(p)).toList();

		return String.join("-", filteredList);

	}

	/**
	 * Die config-Files enthalten jeweils ../../ usw. um im Verzeichnisbaun zum Root-Pfad des Workspaces zu navigieren. Dies gibt
	 * die Anzahl.
	 *
	 * @return
	 */
	public static String getReplacementForCdUp(final GeneratorOptions options) {

		String featurePath = options.getFeatureParentPath();
		String[] featurePathTokens = featurePath.split("/");

		StringBuffer sb = new StringBuffer();

		// feature-parent-path und dort das Unterverzeichnis mit dem Namen featureName + Name des types (api, data, model)
		for (int i = 0; i < featurePathTokens.length + 2; i++) {

			sb.append(DIR_UP);
		}

		return sb.toString();

	}

	public static String getReplacementForTags(final List<String> tags) {

		if (tags == null || tags.isEmpty()) {

			return "[]";
		}

		StringBuffer sb = new StringBuffer();
		sb.append("[");

		int count = 0;

		for (String tag : tags) {

			sb.append("\"");
			sb.append(tag);
			sb.append("\"");

			if (count < tags.size() - 1) {

				sb.append(", ");
			}
			count++;
		}

		sb.append("]");
		return sb.toString();
	}

	public static String transformToCamelCase(final String input) {

		String[] parts = input.split("[_\\s]+");

		StringBuffer camelCaseString = new StringBuffer();

		for (String part : parts) {

			// Capitalize the first letter and add the rest in lowercase
			if (part.length() > 0) {

				camelCaseString.append(part.substring(0, 1).toUpperCase())
					.append(part.substring(1).toLowerCase());
			}
		}

		return camelCaseString.toString();

	}

	public static String getLibraryRootPath(final IGeneratorStrategy strategy) {

		GeneratorOptions options = strategy.getOptions();

		return options.getNxWorkspaceDir() + "/" + options.getFeatureParentPath() + "/" + options.getFeatureName() + "/"
			+ strategy.getLibraryType().toString();
	}

	public static String getTsConfigBaseEntry(final IGeneratorStrategy strategy) {

		GeneratorOptions options = strategy.getOptions();

		String libAlias = options.getLibAliasPrefix() + "/" + options.getFeatureName() + "/" + strategy.getLibraryType().toString();
		String path = options.getFeatureParentPath() + "/" + options.getFeatureName() + "/" + strategy.getLibraryType().toString()
			+ "/src/index.ts";
		return MessageFormat.format(IPlaceholders.MESSAGE_FORMAT_PATTERN_LIB_ALIAS, new Object[] { libAlias, path });
	}
}
