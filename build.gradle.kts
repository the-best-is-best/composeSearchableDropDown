plugins {
    alias(libs.plugins.multiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.compose).apply(false)
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.androidKotlinMultiplatformLibrary) apply false
    id("com.vanniktech.maven.publish") version "0.35.0"
}

