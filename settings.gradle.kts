pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
      //  maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}

rootProject.name = "exmple"
include(":app")
include(":ComposeSearchableDropdown")
