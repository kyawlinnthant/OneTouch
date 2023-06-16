plugins {
    `kotlin-dsl`
}

group = "com.kyawlinnthant.onetouch.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}
gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "kyawlinnthant.application"
            implementationClass = "ApplicationPlugin"
        }
        register("androidApplicationCompose") {
            id = "kyawlinnthant.compose"
            implementationClass = "ComposePlugin"
        }
        register("androidDaggerHilt") {
            id = "kyawlinnthant.hilt"
            implementationClass = "HiltPlugin"
        }
        register("androidFirebase"){
            id = "kyawlinnthant.firebase"
            implementationClass = "FirebasePlugin"
        }
    }
}