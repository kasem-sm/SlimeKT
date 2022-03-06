plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // Test Libs
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    implementation("com.google.truth:truth:1.1.3")
    implementation("junit:junit:4.13.2")
}