import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.get

plugins {
    id("modulator-base-logic")
    id("maven-publish")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}