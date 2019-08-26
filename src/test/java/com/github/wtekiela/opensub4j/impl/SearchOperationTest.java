package com.github.wtekiela.opensub4j.impl;

import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

public class SearchOperationTest {

    private static final String TOKEN = "token";
    private static final String LANG = "en";
    public static final String HASH = "hash";
    public static final String BYTESIZE = "bytesize";
    public static final String TAG = "tag";
    public static final String IMDBID = "imdbid";
    private static final String QUERY = "query";
    private static final String SEASON = "season";
    private static final String EPISODE = "episode";

    @Test
    public void testEmpty() {

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
        assertEquals(params[0], TOKEN);
        Object[] videoProperties = (Object[]) params[1];
        Map<String, String> videoParams = (Map<String, String>) videoProperties[0];
        assertEquals(videoParams.get("sublanguageid"), LANG);
        assertEquals(videoParams.size(), 1);
    }

    @Test
    public void testEmptyIfOnlyMovieHashPassed() {

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
        assertEquals(params[0], TOKEN);
        Object[] videoProperties = (Object[]) params[1];
        Map<String, String> videoParams = (Map<String, String>) videoProperties[0];
        assertEquals(videoParams.get("sublanguageid"), LANG);
        assertEquals(videoParams.size(), 1);
    }

    @Test
    public void testMovieHashAndBytes() {

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
        assertEquals(params[0], TOKEN);
        Object[] videoProperties = (Object[]) params[1];
        Map<String, String> videoParams = (Map<String, String>) videoProperties[0];
        assertEquals(videoParams.get("sublanguageid"), LANG);
        assertEquals(videoParams.get("moviehash"), HASH);
        assertEquals(videoParams.get("moviebytesize"), BYTESIZE);
        assertEquals(videoParams.size(), 3);
    }

    @Test
    public void testTag() {

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
        assertEquals(params[0], TOKEN);
        Object[] videoProperties = (Object[]) params[1];
        Map<String, String> videoParams = (Map<String, String>) videoProperties[0];
        assertEquals(videoParams.get("sublanguageid"), LANG);
        assertEquals(videoParams.get("tag"), TAG);
        assertEquals(videoParams.size(), 2);
    }

    @Test
    public void testImdbID() {

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
        assertEquals(params[0], TOKEN);
        Object[] videoProperties = (Object[]) params[1];
        Map<String, String> videoParams = (Map<String, String>) videoProperties[0];
        assertEquals(videoParams.get("sublanguageid"), LANG);
        assertEquals(videoParams.get("imdbid"), IMDBID);
        assertEquals(videoParams.size(), 2);
    }

    @Test
    public void testQuery() {

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
        assertEquals(params[0], TOKEN);
        Object[] videoProperties = (Object[]) params[1];
        Map<String, String> videoParams = (Map<String, String>) videoProperties[0];
        assertEquals(videoParams.get("sublanguageid"), LANG);
        assertEquals(videoParams.get("query"), QUERY);
        assertEquals(videoParams.size(), 2);
    }

    @Test
    public void testQueryWithSeason() {

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
        assertEquals(params[0], TOKEN);
        Object[] videoProperties = (Object[]) params[1];
        Map<String, String> videoParams = (Map<String, String>) videoProperties[0];
        assertEquals(videoParams.get("sublanguageid"), LANG);
        assertEquals(videoParams.get("query"), QUERY);
        assertEquals(videoParams.get("season"), SEASON);
        assertEquals(videoParams.size(), 3);
    }

    @Test
    public void testQueryWithSeasonAndEpisode() {

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
        assertEquals(params[0], TOKEN);
        Object[] videoProperties = (Object[]) params[1];
        Map<String, String> videoParams = (Map<String, String>) videoProperties[0];
        assertEquals(videoParams.get("sublanguageid"), LANG);
        assertEquals(videoParams.get("query"), QUERY);
        assertEquals(videoParams.get("season"), SEASON);
        assertEquals(videoParams.get("episode"), EPISODE);
        assertEquals(videoParams.size(), 4);
    }


}