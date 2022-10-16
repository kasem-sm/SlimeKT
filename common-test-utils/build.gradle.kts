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
	implementation(project(":core"))
	
	implementation(libs.kotlinx.coroutines.test)
	implementation(libs.google.truth)
	implementation(libs.junit)
	implementation(libs.turbine)
}
