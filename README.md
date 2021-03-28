# Java library for opensubtitles.org

OpenSub4j is an open source Java library for opensubtites. It provides an object-oriented abstraction over XML-RPC opensubtitles.org API.

## Badges

| Branch |    |
|--------|----|
| master | [![Build Status](https://travis-ci.org/wtekiela/opensub4j.svg?branch=master)](https://travis-ci.org/wtekiela/opensub4j) [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=wtekiela_opensub4j&metric=ncloc)](https://sonarcloud.io/dashboard?id=wtekiela_opensub4j) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=wtekiela_opensub4j&metric=coverage)](https://sonarcloud.io/dashboard?id=wtekiela_opensub4j) [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=wtekiela_opensub4j&metric=bugs)](https://sonarcloud.io/dashboard?id=wtekiela_opensub4j) [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=wtekiela_opensub4j&metric=code_smells)](https://sonarcloud.io/dashboard?id=wtekiela_opensub4j) |
| 0.4    | [![Build Status](https://travis-ci.org/wtekiela/opensub4j.svg?branch=0.4)](https://travis-ci.org/wtekiela/opensub4j) |
| 0.3    | [![Build Status](https://travis-ci.org/wtekiela/opensub4j.svg?branch=0.3)](https://travis-ci.org/wtekiela/opensub4j) |
| 0.2    | [![Build Status](https://travis-ci.org/wtekiela/opensub4j.svg?branch=0.2)](https://travis-ci.org/wtekiela/opensub4j) |
| 0.1    | [![Build Status](https://travis-ci.org/wtekiela/opensub4j.svg?branch=0.1)](https://travis-ci.org/wtekiela/opensub4j) |

## Installation

Simply add the dependency to gradle/maven to the latest release:

```
dependencies {
    compile 'com.github.wtekiela:opensub4j:0.4.0'
}
```

## Usage (version 0.4.X)

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
LoginResponse response = osClient.login("username", "password", "en", "TemporaryUserAgent");

// checking login status
assert response.getStatus() == ResponseStatus.OK;
assert osClient.isLoggedIn() == true;

// user information
UserInfo userInfo = response.getUserInfo();

// logging out
osClient.logout();
```

### Search

```
// searching for subtitles matching a file
ListResponse<SubtitleInfo> response = osClient.searchSubtitles("eng", new File("/path/to/file.mkv"));
List<SubtitleInfo> subtitles = response.getData();

// searching by imdb id
ListResponse<SubtitleInfo> response = osClient.searchSubtitles("eng", "movie IMDB id");
List<SubtitleInfo> subtitles = response.getData();

// searching by string query + season/episode
ListResponse<SubtitleInfo> response = osClient.searchSubtitles("eng", "Friends", "1", "1");
List<SubtitleInfo> subtitles = response.getData();
```

### Downloading subtitles

```
ListResponse<SubtitleFile> response = osClient.downloadSubtitles(subtitleInfo.getId());
List<SubtitleFile> subtitleFiles = response.getData();
```

## Error handling

Each method interfacing with XML-RPC api returns a response that carries HTTP status and duration of the call.

```
Response response = osClient.serverInfo();
double duration = response.getSeconds();
ResponseStatus status = response.getStatus();
int httpCode = status.getCode();
String message = status.getMessage();
```

Defined `ResponseStatus` contants:
```
    // 2xx
    public static final ResponseStatus OK = new ResponseStatus(200, "OK");
    public static final ResponseStatus PARTIAL_CONTENT = new ResponseStatus(206, "Partial content");
    // 3xx
    public static final ResponseStatus MOVED = new ResponseStatus(301, "Moved (host)");
    // 4xx
    public static final ResponseStatus UNAUTHORIZED = new ResponseStatus(401, "Unauthorized");
    public static final ResponseStatus SUBTITLE_INVALID_FORMAT = new ResponseStatus(402, "Subtitles has invalid format");
    public static final ResponseStatus SUBTITLE_HASH_NOT_SAME = new ResponseStatus(403, "SubHashes (content and sent subhash) are not same!");
    public static final ResponseStatus SUBTITLE_INVALID_LANGUAGE = new ResponseStatus(404, "Subtitles has invalid language!");
    public static final ResponseStatus NOT_ALL_MANDATORY_PARAMS_SPECIFIED = new ResponseStatus(405, "Not all mandatory parameters was specified");
    public static final ResponseStatus NO_SESSION = new ResponseStatus(406, "No session");
    public static final ResponseStatus DOWNLOAD_LIMIT_REACHED = new ResponseStatus(407, "Download limit reached");
    public static final ResponseStatus INVALID_PARAMETERS = new ResponseStatus(408, "Invalid parameters");
    public static final ResponseStatus METHOD_NOT_FOUND = new ResponseStatus(409, "Method not found");
    public static final ResponseStatus OTHER_UNKNOWN_ERROR = new ResponseStatus(410, "Other or unknown error");
    public static final ResponseStatus INVALID_USER_AGENT = new ResponseStatus(411, "Empty or invalid useragent");
    public static final ResponseStatus S_INVALID_FORMAT = new ResponseStatus(412, "%s has invalid format (reason)");
    public static final ResponseStatus INVALID_IMDB_ID = new ResponseStatus(413, "Invalid ImdbID");
    public static final ResponseStatus UNKNOWN_USER_AGENT = new ResponseStatus(414, "Unknown User Agent");
    public static final ResponseStatus DISABLED_USER_AGENT = new ResponseStatus(415, "Disabled user agent");
    public static final ResponseStatus INTERNAL_SUBTITLE_VALIDATION_FAILED = new ResponseStatus(416, "Internal subtitle validation failed");
    // 5XX
    public static final ResponseStatus SERVICE_UNAVAILABLE = new ResponseStatus(503, "Service Unavailable");
    public static final ResponseStatus MAINTENANCE = new ResponseStatus(506, "Server under maintenance");
```

## Logging

Library uses slf4j logging facade, so you can use any logging implementation that slf4j supports. For more information please refer to [slf4j documentation](http://www.slf4j.org/manual.html).

![](http://www.slf4j.org/images/concrete-bindings.png)

## Building from sources

To build the library from sources, you just need to invoke:
```
./gradlew assemble
```
