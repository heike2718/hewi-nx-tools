// =====================================================
// Project: ngrx-lib-generator
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.ngrx_lib_generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.egladil.web.ngrx_lib_generator.impl.DataGeneratorStrategy;
import de.egladil.web.ngrx_lib_generator.impl.ModelGeneratorStrategy;
import de.egladil.web.ngrx_lib_generator.utils.GeneratorUtils;

/**
 * DataGeneratorTest
 */
public class DataGeneratorTest {

	@Nested
	class ModelGeneratorStrategyTest {

		@Test
		void shouldGenerateTheModelLib_onlyDryRun() {

			// Arrange
			Generator generator = new Generator();
			assertTrue(generator.getProtokoll().isEmpty());

			String userHome = System.getProperty("user.home");
			String workDir = userHome + "/tmp/" + UUID.randomUUID().toString();
			// String workDir = "/home/heike/git/authenticationprovider/frontend/auth-app-profil-app-ws";

			GeneratorOptions options = TestUtils.createGeneratorOptions(workDir).withDryRun(true);

			// Act
			String libRootPth = generator.generateSpecialLib(new ModelGeneratorStrategy(options));

			// Assert
			List<String> protokoll = generator.getProtokoll();
			assertNotNull(protokoll);

			System.out.println(protokoll);
			assertEquals(workDir + "/apps/profil-app/src/app/auth/model", libRootPth);
			assertTrue(new File(libRootPth).isDirectory());
		}

		@Test
		void shouldGenerateTheModelLib() throws Exception {

			// Arrange
			Generator generator = new Generator();
			assertTrue(generator.getProtokoll().isEmpty());

			String userHome = System.getProperty("user.home");
			String workDir = userHome + "/tmp/" + UUID.randomUUID().toString();
			// String workDir = "/home/heike/git/authenticationprovider/frontend/auth-app-profil-app-ws";

			GeneratorOptions options = TestUtils.createGeneratorOptions(workDir);
			ModelGeneratorStrategy strategy = new ModelGeneratorStrategy(options);

			// Act
			String path = generator.generateSpecialLib(strategy);

			// Assert
			List<String> protokoll = generator.getProtokoll();
			assertNotNull(protokoll);

			System.out.println(protokoll);

			assertEquals(workDir + "/apps/profil-app/src/app/auth/model", path);
			assertTrue(new File(path).isDirectory());

			assertEquals("\"@profil-app/auth/model\": [\"apps/profil-app/src/app/auth/model/src/index.ts\"],",
				GeneratorUtils.getTsConfigBaseEntry(strategy));

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
	}

	@Nested
	class DataGeneratorStrategyTest {

		@Test
		void shouldGenerateTheDataLib_onlyDryRun() {

			// Arrange
			Generator generator = new Generator();
			assertTrue(generator.getProtokoll().isEmpty());

			String userHome = System.getProperty("user.home");
			String workDir = userHome + "/tmp/" + UUID.randomUUID().toString();
			// String workDir = "/home/heike/git/authenticationprovider/frontend/auth-app-profil-app-ws";

			GeneratorOptions options = TestUtils.createGeneratorOptions(workDir).withDryRun(true);

			// Act
			String libRootPth = generator.generateSpecialLib(new DataGeneratorStrategy(options));

			// Assert
			List<String> protokoll = generator.getProtokoll();
			assertNotNull(protokoll);

			// System.out.println(protokoll);
			assertEquals(workDir + "/apps/profil-app/src/app/auth/data", libRootPth);
			assertTrue(new File(libRootPth).isDirectory());
		}

		@Test
		void shouldGenerateTheDataLib() throws Exception {

			// Arrange
			Generator generator = new Generator();
			assertTrue(generator.getProtokoll().isEmpty());

			String userHome = System.getProperty("user.home");
			String workDir = userHome + "/tmp/" + UUID.randomUUID().toString();
			// String workDir = "/home/heike/git/authenticationprovider/frontend/auth-app-profil-app-ws";

			GeneratorOptions options = TestUtils.createGeneratorOptions(workDir);
			IGeneratorStrategy strategy = new DataGeneratorStrategy(options);

			// Act
			String path = generator.generateSpecialLib(strategy);

			// Assert
			List<String> protokoll = generator.getProtokoll();
			assertNotNull(protokoll);

			// System.out.println(protokoll);

			assertEquals(workDir + "/apps/profil-app/src/app/auth/data", path);
			assertTrue(new File(path).isDirectory());

			String tsConfigBaseEntry = GeneratorUtils.getTsConfigBaseEntry(strategy);

			System.out.println("===> tsConfigBaseEntry=" + tsConfigBaseEntry);

			assertEquals("\"@profil-app/auth/data\": [\"apps/profil-app/src/app/auth/data/src/index.ts\"],",
				tsConfigBaseEntry);

			// /home/heike/tmp/d901decd-e35c-402a-b0f0-dc19502e480a/apps/profil-app/src/app/auth/model
			File jestConfigFile = new File(path + "/jest.config.ts");
			assertTrue(jestConfigFile.isFile());
			assertTrue(jestConfigFile.canRead());

			try (InputStream in = new FileInputStream(jestConfigFile); StringWriter sw = new StringWriter()) {

				IOUtils.copy(in, sw, StandardCharsets.UTF_8);

				String content = sw.toString();
				assertTrue(content.contains("profil-app-src-app-auth-data"));
				assertTrue(content.contains("'../../../../../../coverage/apps/profil-app/src/app/auth/data'"));
				assertTrue(content.contains("'../../../../../../jest.preset.js'"));

			}

			File projectJson = new File(path + "/project.json");

			assertTrue(projectJson.isFile());
			assertTrue(projectJson.canRead());

			try (InputStream in = new FileInputStream(projectJson); StringWriter sw = new StringWriter()) {

				IOUtils.copy(in, sw, StandardCharsets.UTF_8);

				String content = sw.toString();
				assertTrue(content.contains("\"$schema\": \"../../../../../../node_modules/nx/schemas/project-schema.json\""));
				assertTrue(content.contains("\"jestConfig\": \"apps/profil-app/src/app/auth/data/jest.config.ts\""));
				assertTrue(content.contains("\"name\": \"profil-app-src-app-auth-data\""));
				assertTrue(content.contains("\"tags\": [\"domain:profil\", \"type:data\"]"));
				assertTrue(content.contains("\"sourceRoot\": \"apps/profil-app/src/app/auth/data/src\""));
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
				assertTrue(content.contains("export { authActions } from './lib/+state/auth.actions';"));
				assertTrue(content.contains("export { fromAuth } from './lib/+state/auth.selectors';"));
				assertTrue(content.contains("// exportieren, damit der dataProvider in die api kann."));
				assertTrue(content.contains("export { authFeature } from './lib/+state/auth.reducer';"));
				assertTrue(content.contains("export { AuthEffects } from './lib/+state/auth.effects';"));
			}

			File actions = new File(path + "/src/lib/+state/auth.actions.ts");
			assertTrue(actions.isFile());
			assertTrue(actions.canRead());

			try (InputStream in = new FileInputStream(actions); StringWriter sw = new StringWriter()) {

				IOUtils.copy(in, sw, StandardCharsets.UTF_8);

				String content = sw.toString();
				assertTrue(content.contains("import { createActionGroup, emptyProps, props } from '@ngrx/store';"));
				assertTrue(content.contains("import { UserDto } from '@profil-app/auth/model';"));
				assertTrue(content.contains("export const authActions = createActionGroup({"));
				assertTrue(content.contains("source: 'auth',"));
				assertTrue(content.contains("events: {"));
				assertTrue(content.contains("'LOAD_ALL_DTOS': emptyProps(),"));
				assertTrue(content.contains("'ALL_DTOS_LOADED': props<{ dtos: UserDto[] }>()"));
			}

			File reducer = new File(path + "/src/lib/+state/auth.reducer.ts");
			assertTrue(reducer.isFile());
			assertTrue(reducer.canRead());

			try (InputStream in = new FileInputStream(reducer); StringWriter sw = new StringWriter()) {

				IOUtils.copy(in, sw, StandardCharsets.UTF_8);

				String content = sw.toString();

				assertTrue(content.contains("import { createFeature, createReducer, on } from '@ngrx/store';"));
				assertTrue(content.contains("import { UserDto } from '@profil-app/auth/model';"));
				assertTrue(content.contains("import { authActions } from './auth.actions';"));
				assertTrue(content.contains("export interface AuthState {"));
				assertTrue(content.contains("readonly loaded: boolean;"));
				assertTrue(content.contains("readonly dtos: UserDto[];"));
				assertTrue(content.contains("const initialAuthState: AuthState = {"));
				assertTrue(content.contains("loaded: false,"));
				assertTrue(content.contains("dtos: [],"));
				assertTrue(content.contains("export const authFeature = createFeature({"));
				assertTrue(content.contains("name: 'auth',"));
				assertTrue(content.contains("reducer: createReducer("));
				assertTrue(content.contains("initialAuthState,"));
				assertTrue(content.contains("on(authActions.aLL_DTOS_LOADED, (state, action) => {"));
				assertTrue(content.contains("return {"));
				assertTrue(content.contains("...state,"));
				assertTrue(content.contains("dtos: action.dtos"));
				assertTrue(content.contains("loaded: true"));
			}

			File selectors = new File(path + "/src/lib/+state/auth.selectors.ts");
			assertTrue(selectors.isFile());
			assertTrue(selectors.canRead());

			try (InputStream in = new FileInputStream(selectors); StringWriter sw = new StringWriter()) {

				IOUtils.copy(in, sw, StandardCharsets.UTF_8);

				String content = sw.toString();

				assertTrue(content.contains("import { createSelector } from '@ngrx/store';"));
				assertTrue(content.contains("import { authFeature } from './auth.reducer';"));
				assertTrue(content.contains("const { selectAuthState: selectAuthState} = authFeature;"));
				assertTrue(content.contains("const isLoaded = createSelector("));
				assertTrue(content.contains("state => state.loaded"));
				assertTrue(content.contains("const dtos = createSelector("));
				assertTrue(content.contains("state => state.dtos"));
				assertTrue(content.contains("export const fromAuth = {"));
				assertTrue(content.contains("isLoaded,"));
				assertTrue(content.contains("dtos,"));

				int first = content.indexOf("selectAuthState,");
				assertTrue(first > 0);

				int second = content.indexOf("selectAuthState,", first + "selectAuthState,".length());
				assertTrue(second > 0);
			}

			File effects = new File(path + "/src/lib/+state/auth.effects.ts");
			assertTrue(effects.isFile());
			assertTrue(effects.canRead());

			try (InputStream in = new FileInputStream(effects); StringWriter sw = new StringWriter()) {

				IOUtils.copy(in, sw, StandardCharsets.UTF_8);

				String content = sw.toString();

				// System.out.println(content);

				assertTrue(content.contains("import { Injectable, inject } from '@angular/core';"));
				assertTrue(content.contains("import { Actions, createEffect, ofType } from '@ngrx/effects';"));
				assertTrue(content.contains("import { AuthHttpService } from '../auth-http.service';"));
				assertTrue(content.contains("import { authActions } from './auth.actions';"));
				assertTrue(content.contains("import { map, switchMap } from 'rxjs';"));
				assertTrue(content.contains("@Injectable({"));
				assertTrue(content.contains("providedIn: 'root'"));
				assertTrue(content.contains("export class AuthEffects {"));
				assertTrue(content.contains("#actions = inject(Actions);"));
				assertTrue(content.contains("#authHttpService = inject(AuthHttpService);"));
				assertTrue(content.contains("loadDtos$ = createEffect(() => {"));
				assertTrue(content.contains("return this.#actions.pipe("));
				assertTrue(content.contains("ofType(authActions.lOAD_ALL_DTOS),"));
				assertTrue(content
					.contains("switchMap((action) => this.#authHttpService.loadDtos()),"));
				assertTrue(content.contains("map((dtos) => authActions.aLL_DTOS_LOADED({ dtos }))"));
			}

			File httpService = new File(path + "/src/lib/auth-http.service.ts");
			assertTrue(httpService.isFile());
			assertTrue(httpService.canRead());

			try (InputStream in = new FileInputStream(httpService); StringWriter sw = new StringWriter()) {

				IOUtils.copy(in, sw, StandardCharsets.UTF_8);

				String content = sw.toString();
				assertTrue(content.contains("import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';"));
				assertTrue(content.contains("import { Injectable, inject } from '@angular/core';"));
				assertTrue(content.contains("import { UserDto } from '@profil-app/auth/model';"));
				assertTrue(content.contains("import { Observable } from 'rxjs';"));
				assertTrue(content.contains("@Injectable({"));
				assertTrue(content.contains("providedIn: 'root'"));
				assertTrue(content.contains("export class AuthHttpService {"));
				assertTrue(content.contains("#http = inject(HttpClient);"));
				assertTrue(content.contains("#url = '/api/auth';"));
				assertTrue(content.contains("loadDtos(): Observable<UserDto[]> {"));
				assertTrue(content.contains("const url = this.#url + '/v1';"));
				assertTrue(content.contains("const headers = new HttpHeaders().set('Accept', 'application/json');"));
				assertTrue(content.contains("return this.#http.get<UserDto[]>(url, { headers });"));
			}
		}
	}
}
