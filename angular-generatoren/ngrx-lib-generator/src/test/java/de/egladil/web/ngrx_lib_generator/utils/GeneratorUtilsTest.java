// =====================================================
// Project: ngrx-lib-generator
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.ngrx_lib_generator.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import de.egladil.web.ngrx_lib_generator.GeneratorOptions;
import de.egladil.web.ngrx_lib_generator.utils.GeneratorUtils;

/**
 * GeneratorUtilsTest
 */
public class GeneratorUtilsTest {

	@Test
	void should_getReplacementForCdUp_work() {

		// Arrange
		GeneratorOptions options = new GeneratorOptions().withFeatureParentPath("apps/profil-app/src/app");

		// Act + Asssert
		assertEquals("../../../../../../", GeneratorUtils.getReplacementForCdUp(options));

	}

	@Test
	void should_getProjectFeatureNamePrefix_work_whenApps() {

		// Arrange
		GeneratorOptions options = new GeneratorOptions().withFeatureParentPath("apps/profil-app/src/app").withFeatureName("auth");

		// Act + Asssert
		assertEquals("profil-app-src-app-auth", GeneratorUtils.getProjectFeatureNamePrefix(options));

	}

	@Test
	void should_getProjectFeatureNamePrefix_work_whenLibs() {

		// Arrange
		GeneratorOptions options = new GeneratorOptions().withFeatureParentPath("libs").withFeatureName("users");

		// Act + Asssert
		assertEquals("users", GeneratorUtils.getProjectFeatureNamePrefix(options));

	}

	@Test
	void should_getReplacementForTags_work_withNull() {

		// Act + Assert
		assertEquals("[]", GeneratorUtils.getReplacementForTags(null));
	}

	@Test
	void should_getReplacementForTags_work_withEmptyList() {

		// Act + Assert
		assertEquals("[]", GeneratorUtils.getReplacementForTags(new ArrayList<>()));
	}

	@Test
	void should_getReplacementForTags_work_withOneTag() {

		// Act + Assert
		assertEquals("[\"domain:profil\"]",
			GeneratorUtils.getReplacementForTags(Arrays.asList(new String[] { "domain:profil" })));
	}

	@Test
	void should_getReplacementForTags_work_withTwoTags() {

		// Act + Assert
		String result = GeneratorUtils.getReplacementForTags(Arrays.asList(new String[] { "domain:profil", "type:model" }));

		assertEquals("[\"domain:profil\", \"type:model\"]",
			result);
	}

}
