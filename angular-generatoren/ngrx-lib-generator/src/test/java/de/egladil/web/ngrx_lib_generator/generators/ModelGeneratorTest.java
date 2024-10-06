// =====================================================
// Project: ngrx-lib-generator
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.ngrx_lib_generator.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import de.egladil.web.ngrx_lib_generator.GeneratorOptions;
import de.egladil.web.ngrx_lib_generator.GeneratorResult;

/**
 * ModelGeneratorTest
 */
public class ModelGeneratorTest {

	@Test
	void shouldGenerateTheModelLib_onlyDryRun() {

		// Arrange
		String userHome = System.getProperty("user.home");
		String workDir = userHome + "/tmp/" + UUID.randomUUID().toString();
		// String workDir = "/home/heike/git/authenticationprovider/frontend/auth-app-profil-app-ws";

		GeneratorOptions options = createGeneratorOptions(workDir);

		// Act
		GeneratorResult result = new ModelGenerator(options).generateLib();

		// Assert
		assertNotNull(result);

		System.out.println(result.getReport());
		String path = result.getLibRootPath();
		assertEquals(workDir + "/apps/profil-app/src/app/auth/model", path);
		assertTrue(new File(path).isDirectory());
	}

	@Test
	void shouldGenerateTheModelLib() throws Exception {

		// Arrange
		String userHome = System.getProperty("user.home");
		String workDir = userHome + "/tmp/" + UUID.randomUUID().toString();
		// String workDir = "/home/heike/git/authenticationprovider/frontend/auth-app-profil-app-ws";

		GeneratorOptions options = createGeneratorOptions(workDir);

		// Act
		GeneratorResult result = new ModelGenerator(options).generateLib();

		// Assert
		assertNotNull(result);

		System.out.println(result.getReport());

		String path = result.getLibRootPath();
		assertEquals(workDir + "/apps/profil-app/src/app/auth/model", path);
		assertTrue(new File(path).isDirectory());

		assertEquals("\"@profil-app/auth/model\": [\"apps/profil-app/src/app/auth/model/src/index.ts\"],",
			result.getTsConfigBaseLibDeclaration());

		// /home/heike/tmp/d901decd-e35c-402a-b0f0-dc19502e480a/apps/profil-app/src/app/auth/model
		File jestConfigFile = new File(path + "/jest.config.ts");
		assertTrue(jestConfigFile.isFile());
		assertTrue(jestConfigFile.canRead());

		try (InputStream in = new FileInputStream(jestConfigFile); StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			assertTrue(content.contains("profil-app-src-app-auth-model"));
			assertTrue(content.contains("'../../../../../../coverage/apps/profil-app/src/app/auth/model'"));
			assertTrue(content.contains("'../../../../../../jest.preset.js'"));

		}

		File projectJson = new File(path + "/project.json");

		assertTrue(projectJson.isFile());
		assertTrue(projectJson.canRead());

		try (InputStream in = new FileInputStream(projectJson); StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			assertTrue(content.contains("\"$schema\": \"../../../../../../node_modules/nx/schemas/project-schema.json\""));
			assertTrue(content.contains("\"jestConfig\": \"apps/profil-app/src/app/auth/model/jest.config.ts\""));
			assertTrue(content.contains("\"name\": \"profil-app-src-app-auth-model\""));
			assertTrue(content.contains("\"tags\": [\"domain:profil\", \"type:model\"]"));
			assertTrue(content.contains("\"sourceRoot\": \"apps/profil-app/src/app/auth/model/src\""));
		}

		File esLintConfig = new File(path + "/eslint.config.js");
		assertTrue(esLintConfig.isFile());
		assertTrue(esLintConfig.canRead());

		try (InputStream in = new FileInputStream(esLintConfig); StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			assertTrue(content.contains("const baseConfig = require('../../../../../../eslint.config.js');"));

			int first = content.indexOf("prefix: 'auth'");
			assertTrue(first > 0);

			content = content.substring(first + "prefix: 'auth'".length());
			int second = content.indexOf("prefix: 'auth'");
			assertTrue(second > 0);
		}

		File tsconfig = new File(path + "/tsconfig.json");
		assertTrue(tsconfig.isFile());
		assertTrue(tsconfig.canRead());

		try (InputStream in = new FileInputStream(tsconfig); StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			assertTrue(content.contains("\"extends\": \"../../../../../../tsconfig.base.json\","));
		}

		File tsconfigLib = new File(path + "/tsconfig.lib.json");
		assertTrue(tsconfigLib.isFile());
		assertTrue(tsconfigLib.canRead());

		try (InputStream in = new FileInputStream(tsconfigLib); StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			assertTrue(content.contains("\"outDir\": \"../../../../../../dist/out-tsc\","));
		}

		File tsconfigSpec = new File(path + "/tsconfig.spec.json");
		assertTrue(tsconfigSpec.isFile());
		assertTrue(tsconfigSpec.canRead());

		try (InputStream in = new FileInputStream(tsconfigSpec); StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			assertTrue(content.contains("\"outDir\": \"../../../../../../dist/out-tsc\","));
		}

		File testSetup = new File(path + "/src/test-setup.ts");
		assertTrue(testSetup.isFile());
		assertTrue(testSetup.canRead());

		File index = new File(path + "/src/index.ts");
		assertTrue(index.isFile());
		assertTrue(index.canRead());

		try (InputStream in = new FileInputStream(index); StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			assertTrue(content.contains("export * from './lib/auth.model';"));
		}

		File model = new File(path + "/src/lib/auth.model.ts");
		assertTrue(model.isFile());
		assertTrue(model.canRead());

		try (InputStream in = new FileInputStream(model); StringWriter sw = new StringWriter()) {

			IOUtils.copy(in, sw, StandardCharsets.UTF_8);

			String content = sw.toString();
			assertTrue(content.contains("// put your DTOs and other state model objects into this file"));
			assertTrue(content.contains("export interface UserDto {"));
		}
	}

	private GeneratorOptions createGeneratorOptions(final String workDir) {

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
