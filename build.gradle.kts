// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}

buildscript {
    dependencies {
        // Add the dependency for the Crashlytics Gradle plugin
        classpath ("com.google.firebase:firebase-crashlytics-gradle:2.9.5")
    }
}