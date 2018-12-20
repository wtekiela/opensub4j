package com.github.wtekiela.opensub4j.impl;

import com.github.wtekiela.opensub4j.response.MovieInfo;
import com.github.wtekiela.opensub4j.response.ServerInfo;
import com.github.wtekiela.opensub4j.response.SubtitleFile;
import com.github.wtekiela.opensub4j.response.SubtitleInfo;
import org.apache.xmlrpc.XmlRpcException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

public class OpenSubtitlesClientImplIntegTest {

    private static final String TEST_USER_AGENT = "TemporaryUserAgent";
    private static final String TEST_LANG_2 = "en";
    private static final String TEST_LANG_3 = "eng";

    // https://www.imdb.com/title/tt0109830/?ref_=nv_sr_2
    private static final String IMDB_ID_FORREST_GUMP = "0109830";

    // Forrest Gump_1994_DVDrip_aviM720_en.srt
    // http://www.opensubtitles.org/en/subtitles/3429018/sid-hjd1kIEN-LzDwLbgka2ZkDKFscf/forrest-gump-en
    private static final int TEST_SUBTITLE_FILE_ID = 1952039423;

    private OpenSubtitlesClientImpl objectUnderTest;
    private boolean loggedIn;

    @BeforeMethod
    private void setup() throws MalformedURLException {
        URL serverUrl = new URL("https", "api.opensubtitles.org", 443, "/xml-rpc");
        objectUnderTest = new OpenSubtitlesClientImpl(serverUrl);
    }

    @AfterMethod
    private void teardown() throws XmlRpcException {
        if (loggedIn) {
            loggedIn = false;
            objectUnderTest.logout();
        }
    }

    @Test
    void testServerInfo() throws XmlRpcException {
        // given

        // when
        ServerInfo serverInfo = objectUnderTest.serverInfo();
        // then
        assertNotNull(serverInfo);
        assertEquals(serverInfo.getWebsiteURL(), "http://www.opensubtitles.org");
        assertEquals(serverInfo.getXmlRpcURL(), "http://api.opensubtitles.org/xml-rpc");
        assertEquals(serverInfo.getXmlRpcVersion(), "0.1");
        assertTrue(serverInfo.getLoggedInUsersNo() > 0);
    }

    @Test
    void testLogIn() throws XmlRpcException {
        // given

        // when
        login();

        // then
        assertTrue(objectUnderTest.isLoggedIn());
    }

    private void login() throws XmlRpcException {
        objectUnderTest.login(TEST_LANG_2, TEST_USER_AGENT);
        loggedIn = true;
    }

    @Test
    void testLogout() throws XmlRpcException {
        // given
        objectUnderTest.login(TEST_LANG_2, TEST_USER_AGENT);

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
        objectUnderTest.noop();

        // then
        // does not fail
    }

    @Test
    void testSearchSubtitlesByImdbId() throws XmlRpcException {
        // given
        login();

        // when
        List<SubtitleInfo> subtitleInfos = objectUnderTest.searchSubtitles(TEST_LANG_3, IMDB_ID_FORREST_GUMP);

        // then
        assertFalse(subtitleInfos.isEmpty());
        subtitleInfos.forEach(subtitle -> assertEquals(subtitle.getLanguage(), "English"));
    }

    @Test
    void testDownloadSubtitles() throws XmlRpcException {
        // given
        login();

        // when
        List<SubtitleFile> subtitleFiles = objectUnderTest.downloadSubtitles(TEST_SUBTITLE_FILE_ID);

        // then
        assertEquals(subtitleFiles.size(), 1);
        SubtitleFile file = subtitleFiles.get(0);
        assertEquals(file.getId(), TEST_SUBTITLE_FILE_ID);
    }

    @Test
    void testSearchMoviesOnImdb() throws XmlRpcException {
        // given
        login();

        // when
        List<MovieInfo> movies = objectUnderTest.searchMoviesOnImdb("Forrest Gump");

        // then
        assertFalse(movies.isEmpty());
    }
}