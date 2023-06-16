@file:Suppress("UnstableApiUsage")

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("kyawlinnthant.application")
    id("kyawlinnthant.compose")
    id("kyawlinnthant.hilt")
    id("kyawlinnthant.firebase")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.kyawlinnthant.onetouch"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.kyawlinnthant.onetouch"
        versionCode = 1
        versionName = "0.0.1" //X.Y.Z ( Major.Minor.Patch)
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.splashscreen)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.bundles.androidx.core)
    implementation(libs.bundles.firebase)
    implementation(libs.coil)
    debugImplementation(libs.bundles.androidx.compose.debug)
    implementation(libs.kotlinx.serialization)
    implementation(libs.bundles.network)
    implementation(libs.bundles.room)
    implementation(libs.bundles.datastore)
    implementation(libs.hilt.navigation)
    kapt(libs.room.compiler)
    annotationProcessor(libs.room.compiler)

    testImplementation(libs.test.unit.junit)
    androidTestImplementation(libs.test.android.junit)
    androidTestImplementation(libs.test.android.espresso)
    androidTestImplementation(libs.test.android.compose.junit)
}