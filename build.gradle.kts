buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:${io.simsim.buildsrc.Libs.Version.hiltVersion}")
        classpath("com.diffplug.spotless:spotless-plugin-gradle:${io.simsim.buildsrc.Libs.Version.spotlessVersion}")
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.2.0" apply false
    id("com.android.library") version "7.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.6.21" apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
subprojects {
    apply(plugin = "com.diffplug.spotless")
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")
            ktlint().userData(
                mapOf(
                    "disabled_rules" to "no-wildcard-imports"
                )
            )
        }
    }
}