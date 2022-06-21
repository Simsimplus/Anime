package io.simsim.anime.utils.gradle

import io.simsim.anime.BuildConfig

/**
 * app debuggable
 */
val isDebugMode by lazy {
    BuildConfig.DEBUG
}

/**
 * app version code
 */
val versionCode by lazy {
    BuildConfig.VERSION_CODE
}

/**
 * app version name
 */
val versionName by lazy {
    BuildConfig.VERSION_NAME
}

/**
 * app build type
 */
val buildType by lazy {
    BuildConfig.BUILD_TYPE
}
