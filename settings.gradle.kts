pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootDir.resolve("gradle.properties").copyTo(
    target = rootDir.resolve("buildSrc").resolve("gradle.properties"),
    overwrite = true
)

@kotlin.Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Slime"
include(":app")
include(":core")
include(":common-ui")
include(":ui-core")

include(":features")

// Feature Article
include(":features:article")
include(":features:article:datasource")
include(":features:article:domain")
include(":features:article:domain:model")
include(":features:article:domain:interactors")
include(":features:article:common-article-ui")
include(":features:article:markdown")
include(":features:article:worker")
include(":features:article:widget")
include(":features:article:datasource-impl")

// Feature Topic
include(":features:topic")
include(":features:topic:common-topic-ui")
include(":features:topic:domain")
include(":features:topic:domain:model")
include(":features:topic:datasource")
include(":features:topic:domain:interactors")
include(":features:topic:worker")
include(":features:topic:datasource-impl")

// UI Libraries
include(":ui-article-detail")
include(":ui-home")
include(":ui-explore")
include(":ui-subscribe-topic")
include(":ui-profile")
include(":ui-article-list")
include(":ui-auth")

// Feature Auth
include(":features:authentication")
include(":features:authentication:domain:model")
include(":features:authentication:datasource")
include(":features:authentication:domain:interactors")
include(":features:authentication:datasource-impl")

include(":data")
include(":navigation")
