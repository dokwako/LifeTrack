// Top-level build file where you can add configuration options common to all sub-projects/modules.
 plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
//     id("com.google.dagger.hilt.android") version "2.57" apply false

}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.android.gradle.plugin)
        classpath(libs.google.services)
//        classpath("com.android.tools.build:gradle:8.3.0") // or your current AGP version
//        classpath("com.google.gms:google-services:4.4.3")  // Firebase plugin
    }
}
val buildToolsVersion by extra("35.0.1")
