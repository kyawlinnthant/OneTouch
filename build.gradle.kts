buildscript {
    dependencies {
        classpath("com.google.gms:google-services:${libs.versions.google.services.get()}")
    }
}
@Suppress("DSL_SCOPE_VIOLATION")// Remove when fixed https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.android.kotlin).apply(false)
    alias(libs.plugins.kotlin.serialization).apply(false)
    alias(libs.plugins.hilt).apply(false)
}
//Workaround for "Expecting an expression" build error
println("")