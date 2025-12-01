// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    // build.gradle (Project)

        // Make sure the KSP version matches your Kotlin version!
        // Check releases here: https://github.com/google/ksp/releases
        id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
    

}