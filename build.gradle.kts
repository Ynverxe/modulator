plugins {
    id("java")
    id("maven-publish")
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
    // Annotations
    compileOnly("org.jetbrains:annotations:24.0.0")

    // Unit-testing engine
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}