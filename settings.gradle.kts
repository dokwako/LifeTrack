pluginManagement {
    repositories {
        google()
        mavenCentral()
//        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }
}

//enableFeaturePreview("VERSION_CATALOGS")

rootProject.name = "LifeTrack"
include(":app")