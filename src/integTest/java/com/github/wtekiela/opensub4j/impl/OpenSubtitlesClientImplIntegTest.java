package com.github.wtekiela.opensub4j.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.wtekiela.opensub4j.api.OpenSubtitlesClient;
import com.github.wtekiela.opensub4j.response.ListResponse;
import com.github.wtekiela.opensub4j.response.MovieInfo;
import com.github.wtekiela.opensub4j.response.Response;
import com.github.wtekiela.opensub4j.response.ResponseStatus;
import com.github.wtekiela.opensub4j.response.ServerInfo;
import com.github.wtekiela.opensub4j.response.SubtitleFile;
import com.github.wtekiela.opensub4j.response.SubtitleInfo;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpenSubtitlesClientImplIntegTest {

    private static final String TEST_USER_AGENT = "TemporaryUserAgent";

    private static final String DEFAULT_USERNAME = "";
    private static final String DEFAULT_PASSWORD = "";

    private static final String TEST_LANG_2 = "en";
    private static final String TEST_LANG_3 = "eng";

    // https://www.imdb.com/title/tt0109830/?ref_=nv_sr_2
    private static final String IMDB_ID_FORREST_GUMP = "0109830";

    // Forrest Gump_1994_DVDrip_aviM720_en.srt
    // http://www.opensubtitles.org/en/subtitles/3429018/sid-hjd1kIEN-LzDwLbgka2ZkDKFscf/forrest-gump-en
    private static final int TEST_SUBTITLE_FILE_ID = 1952039423;


    private URL testServerUrl;

    private OpenSubtitlesClientImpl objectUnderTest;

    OpenSubtitlesClientImplIntegTest() throws MalformedURLException {
        testServerUrl = new URL("https", "api.opensubtitles.org", 443, "/xml-rpc");
    }

    @BeforeEach
    private void setup() {
        objectUnderTest = new OpenSubtitlesClientImpl(testServerUrl);
    }

    @AfterEach
    private void teardown() throws XmlRpcException {
        if (objectUnderTest.isLoggedIn()) {
            objectUnderTest.logout();
        }
    }

    @Test
    void testCustomXmlRpcClientConfig() {
        // given
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(testServerUrl);
        config.setConnectionTimeout(1);
        config.setReplyTimeout(1);

        OpenSubtitlesClient client = new OpenSubtitlesClientImpl(config, 1, 2);

        // when
        assertThrows(XmlRpcException.class, client::serverInfo);

        // then
        // expects exception
    }

    @Test
    void testServerInfo() throws XmlRpcException {
        // given

        // when
        ServerInfo serverInfo = objectUnderTest.serverInfo();
        // then
        assertNotNull(serverInfo);
        assertTrue(serverInfo.getSeconds() > 0);
        assertEquals("http://www.opensubtitles.org", serverInfo.getWebsiteURL());
        assertEquals("http://api.opensubtitles.org/xml-rpc", serverInfo.getXmlRpcURL());
        assertEquals("0.1", serverInfo.getXmlRpcVersion());
        assertTrue(serverInfo.getLoggedInUsersNo() > 0);
    }

    @Test
    void testLogIn() throws XmlRpcException {
        // given

        // when
        Response response = login();

        // then
        assertTrue(objectUnderTest.isLoggedIn());
        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    private Response login() throws XmlRpcException {
        String username = System.getenv("OS_USER");
        if (username == null || username.isEmpty()) {
            username = DEFAULT_USERNAME;
        }
        String password = System.getenv("OS_PASS");
        if (password == null || password.isEmpty()) {
            password = DEFAULT_PASSWORD;
        }
        return objectUnderTest.login(username, password, TEST_LANG_2, TEST_USER_AGENT);
    }

    @Test
    void testLogInWithImproperUserAgent() throws XmlRpcException {
        // given

        // when
        Response response = objectUnderTest.login("myID", "andPassword", "en",
            "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");

        // then
        assertFalse(objectUnderTest.isLoggedIn());
        assertEquals(ResponseStatus.UNKNOWN_USER_AGENT, response.getStatus());
    }

    @Test
    void testLogInTwice() throws XmlRpcException {
        // given
        login();

        // when
        assertThrows(IllegalStateException.class, this::login);

        // then
        // IllegalStateException
    }

    @Test
    void testLogout() throws XmlRpcException {
        // given
        login();

        // when
        objectUnderTest.logout();

        // then
        assertFalse(objectUnderTest.isLoggedIn());
    }

    @Test
    void testNoop() throws XmlRpcException {
        // given
        login();

        // when
        Response response = objectUnderTest.noop();

        // then
        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @Test
    void testSearchSubtitlesByImdbId() throws XmlRpcException {
        // given
        login();

        // when
        ListResponse<SubtitleInfo> subtitleInfos = objectUnderTest.searchSubtitles(TEST_LANG_3, IMDB_ID_FORREST_GUMP);

        // then
        assertFalse(subtitleInfos.getData().isEmpty());
        subtitleInfos.getData().forEach(subtitle -> assertEquals("English", subtitle.getLanguage()));
    }

    @Test
    void testDownloadSubtitles() throws XmlRpcException {
        // given
        login();

        // when
        ListResponse<SubtitleFile> subtitleFiles = objectUnderTest.downloadSubtitles(TEST_SUBTITLE_FILE_ID);

        // then
        assertEquals(1, subtitleFiles.getData().size());
        SubtitleFile file = subtitleFiles.getData().get(0);
        assertEquals(TEST_SUBTITLE_FILE_ID, file.getId());
    }

    @Test
    void testDownloadSubtitlesForImproperId() throws XmlRpcException {
        // given
        login();

        // when
        ListResponse<SubtitleFile> subtitleFiles = objectUnderTest.downloadSubtitles(-1);

        // then
        assertTrue(subtitleFiles.getData().isEmpty());
        assertEquals(ResponseStatus.PARTIAL_CONTENT, subtitleFiles.getStatus());
    }

    @Test
    void testSearchMoviesOnImdb() throws XmlRpcException {
        // given
        login();

        // when
        ListResponse<MovieInfo> movies = objectUnderTest.searchMoviesOnImdb("Forrest Gump");

        // then
        assertFalse(movies.getData().isEmpty());
    }
}