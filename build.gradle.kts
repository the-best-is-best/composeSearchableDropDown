plugins {
    alias(libs.plugins.multiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.compose).apply(false)
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.kotlin.compatibility) apply false
    alias(libs.plugins.android.library) apply false
}

