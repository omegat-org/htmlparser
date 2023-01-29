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

tasks.jar {
    manifest {
        attributes("Main-Class" to "org.htmlparser.Parser")
        attributes("org/htmlparser/Parser.class", "Java-Bean" to "True")
        attributes("org/htmlparser/beans/StringBean.class", "Java-Bean" to "True")
        attributes("org/htmlparser/beans/HTMLTextBean.class", "Java-Bean" to "True")
        attributes("org/htmlparser/beans/LinkBean.class", "Java-Bean" to "True")
        attributes("org/htmlparser/beans/HTMLLinkBean.class", "Java-Bean" to "True")
    }
}

project(":htmllexer") {
    apply(plugin="application")
    application.applicationName = "htmllexer"
    application.mainClass.set("org.htmlparser.lexer.Lexer")
    sourceSets {
        main {
            java {
                srcDir("src/src")
                include(listOf(
                        "org/htmlparser/lexer/*",
                        "org/htmlparser/nodes/*.class",
                        "org/htmlparser/Attribute.class",
                        "org/htmlparser/Node.class",
                        "org/htmlparser/NodeFactory.class",
                        "org/htmlparser/NodeFilter.class",
                        "org/htmlparser/Remark.class",
                        "org/htmlparser/Tag.class",
                        "org/htmlparser/Text.class",
                        "org/htmlparser/scanners/Scanner.class",
                        "org/htmlparser/scanners/TagScanner.class",
                        "org/htmlparser/http/ConnectionManager.class",
                        "org/htmlparser/http/ConnectionMonitor.class",
                        "org/htmlparser/http/Cookie.class",
                        "org/htmlparser/util/ParserException.class",
                        "org/htmlparser/util/ChainedException.class",
                        "org/htmlparser/util/NodeList*.class",
                        "org/htmlparser/util/NodeIterator.class",
                        "org/htmlparser/util/SimpleNodeIterator.class",
                        "org/htmlparser/util/EncodingChangeException.class",
                        "org/htmlparser/util/sort/**/*.class",
                        "org/htmlparser/visitors/NodeVisitor.class"
                ))
            }
        }
    }
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
