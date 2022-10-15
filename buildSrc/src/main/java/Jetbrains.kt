object Jetbrains {
    private const val serializationCoreVersion = "1.4.0"

    // Serialization
    const val serializationCore =
        "org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationCoreVersion"

    // Coroutines
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.4"

    // Ktor
    private const val ktorVersion = "2.1.2"
    const val ktorJvm = "io.ktor:ktor-http-jvm:$ktorVersion"
    const val ktorUtils = "io.ktor:ktor-utils-jvm:$ktorVersion"
    const val ktorClientCore = "io.ktor:ktor-client-core-jvm:$ktorVersion"
}
