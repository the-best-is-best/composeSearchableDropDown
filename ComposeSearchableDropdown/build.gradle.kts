plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("signing")
    // id ("com.vanniktech.maven.publish") version "0.27.0"
}
apply(plugin = "maven-publish")
apply(plugin = "signing")

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


afterEvaluate {
    tasks.withType<PublishToMavenLocal> {
        // Make 'publishReleasePublicationToMavenLocal' depend on 'assembleRelease'
        dependsOn("assembleRelease")
    }
    publishing {

        publications.create<MavenPublication>("release") {
            groupId = "io.github.the-best-is-best"
            artifactId = "ComposeSearchableDropdown"
            version = "2.0.1"
            from(components["release"])



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
                    username = "the-best-is-best"
                    password = "Mesho@5005153171997"
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

    }



signing {
    useGpgCmd()
    sign(publishing.publications)
}
android {
    namespace = "com.tbib.composequill"

    compileSdk = 34
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.ui:ui:1.6.7")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material:material-icons-extended:1.6.7")
    implementation("androidx.test.ext:junit-ktx:1.1.5")

}