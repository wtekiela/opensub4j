package com.github.wtekiela.opensub4j.impl;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchOperationTest {

    private static final String TOKEN = "token";
    private static final String LANG = "en";
    private static final String HASH = "hash";
    private static final String BYTESIZE = "bytesize";
    private static final String TAG = "tag";
    private static final String IMDBID = "imdbid";
    private static final String QUERY = "query";
    private static final String SEASON = "season";
    private static final String EPISODE = "episode";

    @Test
    void testEmpty() {

        // given
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
        assertEquals(TOKEN, params[0]);
        Object[] videoProperties = (Object[]) params[1];
        Map<String, String> videoParams = (Map<String, String>) videoProperties[0];
        assertEquals(LANG, videoParams.get("sublanguageid"));
        assertEquals(1, videoParams.size());
    }

    @Test
    void testEmptyIfOnlyMovieHashPassed() {

        // given
        String movieHash = HASH;
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
        assertEquals(TOKEN, params[0]);
        Object[] videoProperties = (Object[]) params[1];
        Map<String, String> videoParams = (Map<String, String>) videoProperties[0];
        assertEquals(LANG, videoParams.get("sublanguageid"));
        assertEquals(1, videoParams.size());
    }

    @Test
    void testMovieHashAndBytes() {

        // given
        String movieHash = HASH;
        String movieByteSize = BYTESIZE;
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
        assertEquals(TOKEN, params[0]);
        Object[] videoProperties = (Object[]) params[1];
        Map<String, String> videoParams = (Map<String, String>) videoProperties[0];
        assertEquals(LANG, videoParams.get("sublanguageid"));
        assertEquals(HASH, videoParams.get("moviehash"));
        assertEquals(BYTESIZE, videoParams.get("moviebytesize"));
        assertEquals(3, videoParams.size());
    }

    @Test
    void testTag() {

        // given
        String movieHash = "";
        String movieByteSize = "";
        String tag = TAG;
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
        assertEquals(TOKEN, params[0]);
        Object[] videoProperties = (Object[]) params[1];
        Map<String, String> videoParams = (Map<String, String>) videoProperties[0];
        assertEquals(LANG, videoParams.get("sublanguageid"));
        assertEquals(TAG, videoParams.get("tag"));
        assertEquals(2, videoParams.size());
    }

    @Test
    void testImdbID() {

        // given
        String movieHash = "";
        String movieByteSize = "";
        String tag = "";
        String imdbid = IMDBID;
        String query = "";
        String season = "";
        String episode = "";

        SearchOperation objectUnderTest = new SearchOperation(
                TOKEN, LANG, movieHash, movieByteSize, tag, imdbid, query, season, episode
        );

        // when
        Object[] params = objectUnderTest.getParams();

        // then
        assertEquals(TOKEN, params[0]);
        Object[] videoProperties = (Object[]) params[1];
        Map<String, String> videoParams = (Map<String, String>) videoProperties[0];
        assertEquals(LANG, videoParams.get("sublanguageid"));
        assertEquals(IMDBID, videoParams.get("imdbid"));
        assertEquals(2, videoParams.size());
    }

    @Test
    void testQuery() {

        // given
        String movieHash = "";
        String movieByteSize = "";
        String tag = "";
        String imdbid = "";
        String query = QUERY;
        String season = "";
        String episode = "";

        SearchOperation objectUnderTest = new SearchOperation(
                TOKEN, LANG, movieHash, movieByteSize, tag, imdbid, query, season, episode
        );

        // when
        Object[] params = objectUnderTest.getParams();

        // then
        assertEquals(TOKEN, params[0]);
        Object[] videoProperties = (Object[]) params[1];
        Map<String, String> videoParams = (Map<String, String>) videoProperties[0];
        assertEquals(LANG, videoParams.get("sublanguageid"));
        assertEquals(QUERY, videoParams.get("query"));
        assertEquals(2, videoParams.size());
    }

    @Test
    void testQueryWithSeason() {

        // given
        String movieHash = "";
        String movieByteSize = "";
        String tag = "";
        String imdbid = "";
        String query = QUERY;
        String season = SEASON;
        String episode = "";

        SearchOperation objectUnderTest = new SearchOperation(
                TOKEN, LANG, movieHash, movieByteSize, tag, imdbid, query, season, episode
        );

        // when
        Object[] params = objectUnderTest.getParams();

        // then
        assertEquals(TOKEN, params[0]);
        Object[] videoProperties = (Object[]) params[1];
        Map<String, String> videoParams = (Map<String, String>) videoProperties[0];
        assertEquals(LANG, videoParams.get("sublanguageid"));
        assertEquals(QUERY, videoParams.get("query"));
        assertEquals(SEASON, videoParams.get("season"));
        assertEquals(3, videoParams.size());
    }

    @Test
    void testQueryWithSeasonAndEpisode() {

        // given
        String movieHash = "";
        String movieByteSize = "";
        String tag = "";
        String imdbid = "";
        String query = QUERY;
        String season = SEASON;
        String episode = EPISODE;

        SearchOperation objectUnderTest = new SearchOperation(
                TOKEN, LANG, movieHash, movieByteSize, tag, imdbid, query, season, episode
        );

        // when
        Object[] params = objectUnderTest.getParams();

        // then
        assertEquals(TOKEN, params[0]);
        Object[] videoProperties = (Object[]) params[1];
        Map<String, String> videoParams = (Map<String, String>) videoProperties[0];
        assertEquals(LANG, videoParams.get("sublanguageid"));
        assertEquals(QUERY, videoParams.get("query"));
        assertEquals(SEASON, videoParams.get("season"));
        assertEquals(EPISODE, videoParams.get("episode"));
        assertEquals(4, videoParams.size());
    }


}