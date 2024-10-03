"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.addNgRxToPackageJson = addNgRxToPackageJson;
const devkit_1 = require("@nx/devkit");
const semver_1 = require("semver");
const version_utils_1 = require("../../utils/version-utils");
function addNgRxToPackageJson(tree, options) {
    const jasmineMarblesVersion = (0, semver_1.gte)(options.rxjsVersion, '7.0.0')
        ? '~0.9.1'
        : '~0.8.3';
    const ngrxVersion = (0, version_utils_1.versions)(tree).ngrxVersion;
    process.env.npm_config_legacy_peer_deps ??= 'true';
    return (0, devkit_1.addDependenciesToPackageJson)(tree, {
        '@ngrx/store': ngrxVersion,
        '@ngrx/effects': ngrxVersion,
        '@ngrx/entity': ngrxVersion,
        '@ngrx/router-store': ngrxVersion,
        '@ngrx/component-store': ngrxVersion,
    }, {
        '@ngrx/schematics': ngrxVersion,
        '@ngrx/store-devtools': ngrxVersion,
        'jasmine-marbles': jasmineMarblesVersion,
    });
}
