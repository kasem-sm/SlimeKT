val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val koinVersion: String by project
val kmongoVersion: String by project

plugins {
	application
	kotlin("jvm") version "1.7.10"
	id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
	id("com.github.johnrengelman.shadow") version "7.1.2"
	id("com.diffplug.spotless") version "6.12.0"
}

group = "slime.com"
version = "0.0.2"
application {
	mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("io.ktor:ktor-server-core:$ktorVersion")
	implementation("io.ktor:ktor-websockets:$ktorVersion")
	implementation("io.ktor:ktor-serialization:$ktorVersion")
	implementation("io.ktor:ktor-server-host-common:$ktorVersion")
	implementation("io.ktor:ktor-gson:$ktorVersion")
	implementation("io.ktor:ktor-auth:$ktorVersion")
	implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
	implementation("io.ktor:ktor-server-netty:$ktorVersion")
	implementation("ch.qos.logback:logback-classic:$logbackVersion")

	// KMongo
	implementation("org.litote.kmongo:kmongo:$kmongoVersion")
	implementation("org.litote.kmongo:kmongo-coroutine:$kmongoVersion")

	// Koin core
	implementation("io.insert-koin:koin-core:$koinVersion")
	implementation("io.insert-koin:koin-ktor:$koinVersion")
	implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

	// Test
	testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
	implementation(kotlin("stdlib-jdk8"))
}

tasks {
	named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
		manifest {
			attributes["Main-Class"] = application.mainClass
		}
	}
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
	kotlin {
		ktlint()
		licenseHeaderFile(project.rootProject.file("spotless/copyright.kt"))
	}
	kotlinGradle {
		target("*.gradle.kts")
		ktlint()
		indentWithTabs()
		trimTrailingWhitespace()
	}
}
