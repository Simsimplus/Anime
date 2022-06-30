pluginManagement {
    resolutionStrategy {
        eachPlugin {
            println(this.requested.id)
        }
    }
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
rootProject.name = "Anime"
include(":app")
