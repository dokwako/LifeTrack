// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.4.0")
        classpath("com.google.gms:google-services:4.4.1")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
        classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.9.22-1.0.17")
    }
}

plugins {
    id("com.android.application") version "8.4.0" apply false
    id("com.android.library") version "8.4.0" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

val buildToolsVersion by extra("35.0.1")