// =====================================================
// Project: ngrx-lib-generator
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.ngrx_lib_generator;

import java.util.Arrays;

import de.egladil.web.ngrx_lib_generator.GeneratorOptions;

/**
 * TestUtils
 */
public class TestUtils {

	public static GeneratorOptions createGeneratorOptions(final String workDir) {

		return new GeneratorOptions()
			.withDryRun(false)
			.withDtoName("UserDto")
			.withFeatureName("auth")
			.withFeatureParentPath("apps/profil-app/src/app")
			.withLibAliasPrefix("@profil-app")
			.withAngularSelectorPrefix("auth")
			.withTags(Arrays.asList(new String[] { "domain:profil" }))
			.withNxWorkspaceDir(workDir);
	}
}
