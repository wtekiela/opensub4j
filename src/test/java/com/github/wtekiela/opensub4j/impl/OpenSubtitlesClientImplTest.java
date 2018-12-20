package com.github.wtekiela.opensub4j.impl;

import org.apache.xmlrpc.XmlRpcException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OpenSubtitlesClientImplTest {

    private OpenSubtitlesClientImpl objectUnderTest;

    @BeforeMethod
    private void setup() {
        objectUnderTest = new OpenSubtitlesClientImpl(null, null, null);
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    void testLogoutFailsIfNotLoggedIn() throws XmlRpcException {
        objectUnderTest.logout();
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    void testNoopFailsIfNotLoggedIn() throws XmlRpcException {
        objectUnderTest.noop();
    }

}