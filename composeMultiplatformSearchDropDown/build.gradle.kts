import org.jetbrains.compose.desktop.application.dsl.TargetFormat


plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    id("maven-publish")
    id("signing")
    id("com.vanniktech.maven.publish")
}


mavenPublishing {
    coordinates("io.github.the-best-is-best", "ComposeSearchableDropdown", "2.3.2")

    publishToMavenCentral( true)
    signAllPublications()

    pom {
        name.set("Compose Searchable Dropdown")
        description.set("A Compose multiplatform Library to create a searchable dropdown.")
        url.set("https://github.com/the-best-is-best/composeSearchableDropDown")
        licenses {
            license {
                name.set("Apache-2.0")
                url.set("https://opensource.org/licenses/Apache-2.0")
            }
        }
        issueManagement {
            system.set("Github")
            url.set("https://github.com/the-best-is-best/composeSearchableDropDown/issues")
        }
        scm {
            connection.set("https://github.com/the-best-is-best/composeSearchableDropDown.git")
            url.set("https://github.com/the-best-is-best/composeSearchableDropDown")
        }
        developers {
            developer {
                id.set("MichelleRaouf")
                name.set("Michelle Raouf")
                email.set("eng.michelle.raouf@gmail.com")
            }
        }
    }

}


kotlin {
    jvmToolchain(17)
    jvm()

    js {
        browser()
        binaries.executable()
    }

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        nodejs()
        browser()
        binaries.executable()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeSearchableDropdown"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.runtime)
            implementation(libs.foundation)
            implementation(libs.material3)
            implementation(libs.material.icons.extended)
            implementation(libs.ui.tooling.preview)
        }
//
//        commonTest.dependencies {
//            implementation(kotlin("test"))
//            @OptIn(ExperimentalComposeLibrary::class)
//            implementation(compose.uiTest)
//        }

        androidMain.dependencies {
            implementation(libs.ui.tooling)
            implementation(libs.androidx.activityCompose)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }

        iosMain.dependencies {
        }

    }

    android {

        namespace = "io.github.compose_searchable_dropdown"
        compileSdk = 36
        minSdk = 23

    }
}


signing {
    useGpgCmd()
    sign(publishing.publications)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "io.github.compose_searchable_dropdown.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}
tasks.withType(AbstractPublishToMaven::class).configureEach {
    dependsOn(tasks.withType(Sign::class))
}
