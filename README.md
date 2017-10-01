# Java library for opensubtitles.org

OpenSub4j is an open source Java library for opensubtites. It provides an object-oriented abstraction over XML-RPC opensubtitles.org API.

## Installation

Simply add the dependency to gradle/maven, either to the latest release:

```
dependencies {
    compile 'com.github.wtekiela:opensub4j:0.1.2'
}
```

or to the latest build:

```
dependencies {
    compile 'com.github.wtekiela:opensub4j:0.2.0-SNAPSHOT'
}
```

## Building from sources

To build the library from sources, you just need to invoke:
```
./gradlew assemble
```

Note: Java JDK 8 is required.