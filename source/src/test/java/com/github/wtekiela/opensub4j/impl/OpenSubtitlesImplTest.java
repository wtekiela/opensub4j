package com.github.wtekiela.opensub4j.impl;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class OpenSubtitlesImplTest {

    private static final Logger logger = LoggerFactory.getLogger(OpenSubtitlesImplTest.class);

    @Test
    public void printServerInfo() throws Exception {

        OpenSubtitlesImpl os = new OpenSubtitlesImpl(new URL("http://api.opensubtitles.org/xml-rpc"));
        os.login("en", "OSTestUserAgent");

        logger.info(os.serverInfo().toString());

        os.logout();

    }
}