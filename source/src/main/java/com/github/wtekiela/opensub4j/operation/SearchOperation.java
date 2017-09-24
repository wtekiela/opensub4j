package com.github.wtekiela.opensub4j.operation;

import com.github.wtekiela.opensub4j.response.SubtitleInfo;

import java.util.HashMap;
import java.util.Map;

public class SearchOperation extends ListOperation<SubtitleInfo> {

    private final String loginToken;
    private final String lang;
    private final String movieHash;
    private final String movieByteSize;
    private final String tag;
    private final String imdbid;
    private final String query;
    private final String season;
    private final String episode;

    public SearchOperation(String loginToken, String lang, String movieHash, String movieByteSize, String tag, String imdbid, String query, String season, String episode) {
        this.loginToken = loginToken;
        this.lang = lang;
        this.movieHash = movieHash;
        this.movieByteSize = movieByteSize;
        this.tag = tag;
        this.imdbid = imdbid;
        this.query = query;
        this.season = season;
        this.episode = episode;
    }

    @Override
    String getMethodName() {
        return "SearchSubtitles";
    }

    @Override
    Object[] getParams() {
        Map<String, String> videoProperties = new HashMap<>();
        videoProperties.put("sublanguageid", lang);

        if (movieHash != null && !movieHash.isEmpty() && movieByteSize != null && !movieByteSize.isEmpty()) {
            videoProperties.put("moviehash", movieHash);
            videoProperties.put("moviebytesize", movieByteSize);
        } else if (tag != null && !imdbid.isEmpty()) {
            // Tag is index of movie filename or subtitle file fieldName, or release fieldName -
            // currently we index more than 40.000.000 of tags.
            videoProperties.put("tag", tag);
        } else if (imdbid != null && !imdbid.isEmpty()) {
            videoProperties.put("imdbid", imdbid);
        } else if (query != null && !query.isEmpty()) {
            videoProperties.put("query", query);
            if (season != null && !season.isEmpty()) {
                videoProperties.put("season", season);
            }
            if (episode != null && !episode.isEmpty()) {
                videoProperties.put("episode", episode);
            }
        }

        Object[] videoParams = {videoProperties};
        Object[] params = {loginToken, videoParams};
        return params;
    }

    @Override
    Class<SubtitleInfo> getResponseClass() {
        return SubtitleInfo.class;
    }
}
