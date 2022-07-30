plugins {
	id("java-library")
	id("org.jetbrains.kotlin.jvm")
}

java {
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8
}

configurations {
	create("test")
}

tasks.register<Jar>("testArchive") {
	archiveBaseName.set("common-test-utils")
	from(project.the<SourceSetContainer>()["test"].output)
}

artifacts {
	add("test", tasks["testArchive"])
}

dependencies {
	// Test Libs
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
	implementation("com.google.truth:truth:1.1.3")
	implementation("junit:junit:4.13.2")

	implementation(project(":core"))

	implementation("app.cash.turbine:turbine:0.8.0")
}
