// =====================================================
// Project: ngrx-lib-generator
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.ngrx_lib_generator;

import java.util.List;

/**
 * GeneratorOptions
 */
public class GeneratorOptions {

	private String nxWorkspaceDir;

	private String featureParentPath;

	private String featureName;

	private List<String> tags;

	private String dtoName;

	private String libAliasPrefix;

	private String angularSelectorPrefix;

	private boolean dryRun;

	@Override
	public String toString() {

		return "GeneratorOptions [nxWorkspaceDir=" + nxWorkspaceDir + ", featureParentPath=" + featureParentPath + ", featureName="
			+ featureName + ", tags="
			+ tags + ", dtoName=" + dtoName + ", libAliasPrefix=" + libAliasPrefix + "]";
	}

	/**
	 * Absoluter Pfad zum Verzeichnis mit dem nx-Workspace. Dies ist das Root-Verzeichnis, auf das sich die relativen Pfade
	 * beziehen.<br>
	 * <br>
	 * Beispiel: /home/heike/git/authenticationprovider/frontend/auth-app-profil-app-ws
	 *
	 * @return String
	 */
	public String getNxWorkspaceDir() {

		return nxWorkspaceDir;
	}

	public void setNxWorkspaceDir(final String nxWorkspaceDir) {

		this.nxWorkspaceDir = nxWorkspaceDir;
	}

	public GeneratorOptions withNxWorkspaceDir(final String nxWorkspaceDir) {

		this.nxWorkspaceDir = nxWorkspaceDir;
		return this;
	}

	/**
	 * Root-Path des features. Pfad Elternverzeichnisses für dieses Feature relativ zum nx-workspace-dir. Das erste Unterverteichnis
	 * ist ein Verzeichnis namens featureName.<br>
	 * <br>
	 * Beispiel: apps/profil-app/src/app/auth
	 *
	 * @return String
	 */
	public String getFeatureParentPath() {

		return featureParentPath;
	}

	public void setFeatureParentPath(final String featureParentPath) {

		this.featureParentPath = featureParentPath;
	}

	public GeneratorOptions withFeatureParentPath(final String featureParentPath) {

		this.featureParentPath = featureParentPath;
		return this;
	}

	/**
	 * Name des Features im Store. Input für Ersetzung von IPlaceholders.FEATURE_NAME in den config-Files<br>
	 * <br>
	 * Beispiel: auth
	 *
	 * @return
	 */
	public String getFeatureName() {

		return featureName;
	}

	public void setFeatureName(final String featureName) {

		this.featureName = featureName;
	}

	public GeneratorOptions withFeatureName(final String featureName) {

		this.featureName = featureName;
		return this;
	}

	public List<String> getTags() {

		return tags;
	}

	public void setTags(final List<String> tags) {

		this.tags = tags;
	}

	public GeneratorOptions withTags(final List<String> tags) {

		this.tags = tags;
		return this;
	}

	public String getDtoName() {

		return dtoName;
	}

	public void setDtoName(final String dtoName) {

		this.dtoName = dtoName;
	}

	public GeneratorOptions withDtoName(final String dtoName) {

		this.dtoName = dtoName;
		return this;
	}

	public String getLibAliasPrefix() {

		return libAliasPrefix;
	}

	/**
	 * Das ist der Präfix, der in ts.config.base.json in der paths-section für die neue lib gesetzt wird.<br>
	 * <br>
	 * Beispiel: der (at)profil-app Teil von (at)profil-app/auth/model
	 *
	 * @param libPrefix
	 */
	public void setLibAliasPrefix(final String libPrefix) {

		this.libAliasPrefix = libPrefix;
	}

	public GeneratorOptions withLibAliasPrefix(final String libPrefix) {

		this.libAliasPrefix = libPrefix;
		return this;
	}

	public boolean isDryRun() {

		return dryRun;
	}

	public void setDryRun(final boolean dryRun) {

		this.dryRun = dryRun;
	}

	public GeneratorOptions withDryRun(final boolean dryRun) {

		this.dryRun = dryRun;
		return this;
	}

	/**
	 * Der Präfix für selectors in Angular components. Default ist 'app'.
	 *
	 * @return
	 */
	public String getAngularSelectorPrefix() {

		return angularSelectorPrefix;
	}

	public void setAngularSelectorPrefix(final String angularSelectorPrefix) {

		this.angularSelectorPrefix = angularSelectorPrefix;
	}

	public GeneratorOptions withAngularSelectorPrefix(final String angularSelectorPrefix) {

		this.angularSelectorPrefix = angularSelectorPrefix;
		return this;
	}

}
