# Java library for opensubtitles.org

OpenSub4j is an open source Java library for opensubtites. It provides an object-oriented abstraction over XML-RPC opensubtitles.org API.

## Installation

Simply add the dependency to gradle/maven to the latest release:

```
dependencies {
    compile 'com.github.wtekiela:opensub4j:0.2.3'
}
```

## Usage (version 0.2.X)

### Creating the client

Client can be created either by passing the URL object:
```
URL serverUrl = new URL("https", "api.opensubtitles.org", 443, "/xml-rpc");
OpenSubtitlesClient osClient = new OpenSubtitlesClientImpl(serverUrl);
```

or, for more granular control over the xml-rpc connection, by passing the XmlRpcClientConfig object:
```
URL serverUrl = new URL("https", "api.opensubtitles.org", 443, "/xml-rpc");

XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
config.setServerURL(serverUrl);
config.setConnectionTimeout(100);
config.setReplyTimeout(100);
config.setGzipCompressing(true);

OpenSubtitlesClient client = new OpenSubtitlesClientImpl(config);
```

### Getting server info

```
ServerInfo serverInfo = osClient.serverInfo();
```

### Authentication

```
// logging in
Response response = osClient.login("username", "password", "en", "TemporaryUserAgent");

// checking login status
response.getStatus();
osClient.isLoggedIn();

// logging out
osClient.logout();
```

### Search

```
// searching for subtitles matching a file
List<SubtitleInfo> subtitles = osClient.searchSubtitles("eng", new File("/path/to/file.mkv"));

// searching by imdb id
List<SubtitleInfo> subtitles = osClient.searchSubtitles("eng", "movie IMDB id");

// searching by string query + season/episode
List<SubtitleInfo> subtitles = osClient.searchSubtitles("eng", "Friends", "1", "1");
```

### Downloading subtitles

```
List<SubtitleFile> subtitleFiles = osClient.downloadSubtitles(subtitleInfo.getId());
```

## Logging

Library uses slf4j logging facade, it can be bound to any logger implementation that has bindings to slf4j. 

For simple logging:
```
runtime 'org.slf4j:slf4j-simple:1.7.+'
``` 

For binding with log4j:
```
runtime 'org.slf4j:slf4j-log4j12:1.7.+'
```

For more information please refer to slf4j documentation.

## Building from sources

To build the library from sources, you just need to invoke:
```
./gradlew assemble
```

Note: Java JDK 8 is required.
