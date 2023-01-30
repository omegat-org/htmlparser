# HTML parser (fork) for java

This is fork project for OmegaT project dependency
to guarantee providing source history.
We dumped code from CVS until v1.6-20060610.

Published version is v1.5.
Current snapshot version is v1.6-20230129-SNAPSHOT.


## Use the library (snapshot) in your project

### Gradle build.gradle

```groovy
repositories {
    maven { url "https://s01.oss.sonatype.org/content/repositories/snapshots/"}
}

dependencies {
    implementation 'org.omegat:htmlparser:1.6-20230129-SNAPSHOT'
}
```

### Maven pom.xml

```xml
<repository>
  <id>sonatype.snapshots</id>
  <name>Sonatype OSSRH Snapshot Repository</name>
  <url>https://s01.oss.sonatype.org/content/repositories/snapshots/</url>
  <releases>
    <enabled>false</enabled>
  </releases>
  <snapshots>
    <enabled>true</enabled>
  </snapshots>
</repository>
```

```xml
<dependency>
    <groupId>org.omegat</groupId>
    <artifactId>htmlparser</artifactId>
    <version>1.6-20230129-SNAPSHOT</version>
</dependency>
```


## Changes from v1.6-20060610

- Support building with Java 8 and 11.
- Breach sourceforge links from javadoc for privacy
- Migrate to Gradle build system
  - Support OSSRH/MavenCentral publish
  - Build on Java 8
  - Signatory support
  - Separate apps build
  - SourceJar release
- Fix several javadoc errors

## License

htmlparser v1.5 and v1.6 is licensed under LGPL 2.1 or later.

## Origin

An origin of the project is https://htmlparser.sourceforge.net/
CVS repository is described at http://htmlparser.cvs.sourceforge.net/htmlparser/
