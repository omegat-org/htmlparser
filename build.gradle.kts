plugins {
    application
    `java-library`
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

group = "org.omegat"
version = "1.6"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
    withSourcesJar()
    withJavadocJar()
}

sourceSets {
    main {
        java {
            srcDir("src/src")
        }
    }
    test {
        java {
            srcDir("src/tests")
        }
    }
}

repositories {
    mavenCentral()
}

val javaHome = System.getProperty("java.home")

dependencies {
    // Test dependencies
    testImplementation(files("${javaHome}/../lib/tools.jar"))
    testImplementation("junit:junit:3.8.2")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set("htmlparser")
                description.set("html parser library")
                url.set("https://github.com/omegat-org/htmlparser")
                licenses {
                    license {
                        name.set("The GNU Lesser General Public License, Version 2.1")
                        url.set("https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("miurahr")
                        name.set("Hiroshi Miura")
                        email.set("miurahr@linux.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/omegat-org/htmlparser.git")
                    developerConnection.set("scm:git:git://github.com/omegat-org/htmlparser.git")
                    url.set("https://github.com/omegat-org/htmlparser")
                }
            }
        }
    }
}

val signKey = listOf("signingKey", "signing.keyId", "signing.gnupg.keyName").find {project.hasProperty(it)}
signing {
    when (signKey) {
        "signingKey" -> {
            val signingKey: String? by project
            val signingPassword: String? by project
            useInMemoryPgpKeys(signingKey, signingPassword)
        }
        "signing.keyId" -> {
            val keyId: String? by project
            val password: String? by project
            val secretKeyRingFile: String? by project // e.g. gpg --export-secret-keys > secring.gpg
            useInMemoryPgpKeys(keyId, password, secretKeyRingFile)
        }
        "signing.gnupg.keyName" -> {
            useGpgCmd()
        }
    }
    sign(publishing.publications["mavenJava"])
}

nexusPublishing {
    repositories{
        sonatype()
    }
}
