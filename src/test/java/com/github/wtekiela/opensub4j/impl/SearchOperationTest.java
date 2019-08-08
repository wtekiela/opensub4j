package com.github.wtekiela.opensub4j.impl;

import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

public class SearchOperationTest {

    private static final String TOKEN = "token";
    private static final String LANG = "en";

    // TODO: more tests for getParams logic

    @Test
    public void testEmpty() {

        String movieHash = "";
        String movieByteSize = "";
        String tag = "";
        String imdbid = "";
        String query = "";
        String season = "";
        String episode = "";

        SearchOperation objectUnderTest = new SearchOperation(
                TOKEN, LANG, movieHash, movieByteSize, tag, imdbid, query, season, episode
        );

        // when
        Object[] params = objectUnderTest.getParams();

        // then
        assertEquals(params[0], TOKEN);
        Object[] videoProperties = (Object[]) params[1];
        Map<String, String> videoParams = (Map<String, String>) videoProperties[0];
        assertEquals(videoParams.get("sublanguageid"), LANG);
        assertEquals(videoParams.size(), 1);
    }

}