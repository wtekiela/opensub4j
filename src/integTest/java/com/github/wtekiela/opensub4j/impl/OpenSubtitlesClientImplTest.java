package com.github.wtekiela.opensub4j.impl;

import com.github.wtekiela.opensub4j.api.OpenSubtitlesClient;
import com.github.wtekiela.opensub4j.response.ServerInfo;
import org.apache.xmlrpc.XmlRpcException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.*;

public class OpenSubtitlesClientImplTest {

    private static final String TEST_USER_AGENT = "TemporaryUserAgent";

    private OpenSubtitlesClient objectUnderTest;

    @BeforeMethod
    private void setup() throws MalformedURLException {
        URL serverUrl = new URL("https", "api.opensubtitles.org", 443, "/xml-rpc");
        objectUnderTest = new OpenSubtitlesClientImpl(serverUrl);
    }

    @Test
    void testServerInfo() throws MalformedURLException, XmlRpcException {
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
}