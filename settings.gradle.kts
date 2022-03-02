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

// Feature Article
include(":article")
include(":article:datasource")
include(":article:domain")
include(":article:domain:model")
include(":article:domain:interactors")
include(":article:common-article-ui")
include(":article:markdown")
include(":article:worker")
include(":article:widget")
include(":article:datasource-impl")

// Feature Topic
include(":topic")
include(":topic:common-topic-ui")
include(":topic:domain")
include(":topic:domain:model")
include(":topic:datasource")
include(":topic:domain:interactors")
include(":topic:worker")
include(":topic:datasource-impl")

// UI Libraries
include(":ui-article-detail")
include(":ui-home")
include(":ui-explore")
include(":ui-subscribe-topic")
include(":ui-profile")
include(":ui-article-list")
include(":ui-auth")

// Feature Auth
include(":authentication")
include(":authentication:domain:model")
include(":authentication:datasource")
include(":authentication:domain:interactors")
include(":authentication:datasource-impl")

include(":data")
include(":navigation")
