plugins {
    id("java")
    id("java-library")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of("8")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Logging
    compileOnly("org.slf4j:slf4j-api:2.0.13")

    // Annotations
    compileOnly("org.jetbrains:annotations:24.0.0")

    // Unit-testing engine
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}