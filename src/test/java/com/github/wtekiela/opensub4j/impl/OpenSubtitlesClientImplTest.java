package com.github.wtekiela.opensub4j.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpenSubtitlesClientImplTest {

    private OpenSubtitlesClientImpl objectUnderTest;

    @BeforeEach
    private void setup() {
        objectUnderTest = new OpenSubtitlesClientImpl(null, null, null);
    }

    @Test
    void testLogoutFailsIfNotLoggedIn() {
        Assertions.assertThrows(IllegalStateException.class, () -> objectUnderTest.logout());
    }

    @Test
    void testNoopFailsIfNotLoggedIn() {
        Assertions.assertThrows(IllegalStateException.class, () -> objectUnderTest.noop());
    }

}