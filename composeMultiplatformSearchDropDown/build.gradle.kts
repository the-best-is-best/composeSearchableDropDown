import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform.getCurrentOperatingSystem
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import java.util.Properties

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.compatibility)
    alias(libs.plugins.android.library)
    id("maven-publish")
    id("signing")
}

buildscript {
    dependencies {
        val dokkaVersion = libs.versions.dokka.get()
        classpath("org.jetbrains.dokka:dokka-base:$dokkaVersion")
    }
}

apply(plugin = "maven-publish")
apply(plugin = "signing")


tasks.withType<PublishToMavenRepository> {
    val isMac = getCurrentOperatingSystem().isMacOsX
    onlyIf {
        isMac.also {
            if (!isMac) logger.error(
                """
                    Publishing the library requires macOS to be able to generate iOS artifacts.
                    Run the task on a mac or use the project GitHub workflows for publication and release.
                """
            )
        }
    }
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml.flatMap(DokkaTask::outputDirectory))
    archiveClassifier = "javadoc"
}

tasks.dokkaHtml {
    // outputDirectory = layout.buildDirectory.get().resolve("dokka")
    offlineMode = false
    moduleName = "composeMultiplatformSearchDropDown.html"

    // See the buildscript block above and also
    // https://github.com/Kotlin/dokka/issues/2406
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {

        separateInheritedMembers = true



        dokkaSourceSets {

                configureEach {
                    reportUndocumented = true
                    noAndroidSdkLink = false
                    noStdlibLink = false
                    noJdkLink = false
                    jdkVersion = libs.versions.java.get().toInt()

                    // sourceLink {
                    //     // Unix based directory relative path to the root of the project (where you execute gradle respectively).
                    //     // localDirectory.set(file("src/main/kotlin"))
                    //     // URL showing where the source code can be accessed through the web browser
                    //     // remoteUrl = uri("https://github.com/mahozad/${project.name}/blob/main/${project.name}/src/main/kotlin").toURL()
                    //     // Suffix which is used to append the line number to the URL. Use #L for GitHub
                    //     remoteLineSuffix = "#L"
                    // }
                }

        }
    }
}



publishing {

    publications.withType<MavenPublication> {
        groupId = "io.github.the-best-is-best"
        artifactId = "ComposeSearchableDropdown"
        artifact(javadocJar) // Required a workaround. See below
        version = "2.0.2"
      //  from(components["kotlin"])



        //  artifact("$buildDir/outputs/aar/ComposeQuill-release.aar")
        //artifact("$buildDir/libs/ComposeQuill-release.jar")
        // Provide artifacts information required by Maven Central
        pom {
            name.set("Compose Searchable Dropdown")
            description.set("A Jetpack Compose Android Library to create a searchable dropdown.")
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
    repositories {
//            maven {
//                name = "OSSRH-snapshots"
//                url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
//                credentials {
//                    username = System.getenv("MAVEN_NAME")
//                    password = System.getenv("MAVEN_TOKEN")
//                }
//            }

        maven {
            name = "OSSRH"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
              
            }
        }
//            maven {
//                name = "LocalMaven"
//                url = uri("$buildDir/maven")
//                   }
//                maven {
//                    name = "GitHubPackages"
//                    url = uri("https://github.com/the-best-is-best/composeSearchableDropDown")
//                    credentials {
//                        username = "the-best-is-best"
//                        password =
//                            System.getenv("BUILD_MAVEN")
//                    }
        //      }
    }
}




kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                    freeCompilerArgs.add("-Xjdk-release=${JavaVersion.VERSION_1_8}")
                }
            }
        }
        //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant {
            sourceSetTree.set(KotlinSourceSetTree.test)
            dependencies {
                debugImplementation(libs.androidx.testManifest)
                implementation(libs.androidx.junit4)
            }
        }
    }
    jvm()

    @OptIn(ExperimentalWasmDsl::class)
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
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }

        androidMain.dependencies {
            implementation(compose.uiTooling)
            implementation(libs.androidx.activityCompose)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }

        iosMain.dependencies {
        }

    }
}

android {
    namespace = "io.github.compose_searchable_dropdown"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
      }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        //enables a Compose tooling support in the AndroidStudio
        compose = true
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
