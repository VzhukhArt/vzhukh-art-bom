plugins {
    id("java-platform")
    id("maven-publish")
}

group = "com.dm.net.vzukh"
version = findProperty("projVersion")?.toString() ?: "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project
val githubUsername: String by project
val githubPassword: String by project

dependencies {
    constraints {
        api(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
        api(enforcedPlatform("${quarkusPlatformGroupId}:quarkus-camel-bom:${quarkusPlatformVersion}"))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["javaPlatform"])
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/${System.getenv("GITHUB_REPOSITORY") ?: "VzhukhArt/messages-model"}")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: githubUsername
                password = System.getenv("GITHUB_TOKEN") ?: githubPassword
            }
        }
    }
}
