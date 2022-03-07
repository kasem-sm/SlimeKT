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
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    api("com.google.truth:truth:1.1.3")
    api("junit:junit:4.13.2")
    api("app.cash.turbine:turbine:0.7.0")
    api("io.mockk:mockk:1.12.3")
}
