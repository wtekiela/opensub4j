/**
 * Copyright (c) 2021 Wojciech Tekiela
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.github.wtekiela.opensub4j.impl;

import com.github.wtekiela.opensub4j.response.SubtitleInfo;
import java.util.HashMap;
import java.util.Map;

class SearchOperation extends AbstractListOperation<SubtitleInfo> {

    private final String loginToken;
    private final String lang;
    private final String movieHash;
    private final String movieByteSize;
    private final String tag;
    private final String imdbid;
    private final String query;
    private final String season;
    private final String episode;

    @SuppressWarnings("squid:S00107")
    SearchOperation(String loginToken, String lang, String movieHash, String movieByteSize, String tag, String imdbid,
                    String query, String season, String episode) {
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
    ElementFactory<SubtitleInfo> getListElementFactory() {
        return SubtitleInfo::new;
    }

    @Override
    Object[] getParams() {
        Map<String, String> videoProperties = new HashMap<>();
        videoProperties.put("sublanguageid", lang);

        if (shouldSearchByMovieHash()) {
            videoProperties.put("moviehash", movieHash);
            videoProperties.put("moviebytesize", movieByteSize);
        } else if (shouldSearchByTag()) {
            // Tag is index of movie filename or subtitle file fieldName, or release fieldName -
            // currently we index more than 40.000.000 of tags.
            videoProperties.put("tag", tag);
        } else if (shouldSearchByImdbId()) {
            videoProperties.put("imdbid", imdbid);
            //if imdb id is for a series
            addSeasonAndEpisodeInfo(videoProperties);
        } else if (shouldSearchByQuery()) {
            videoProperties.put("query", query);
            addSeasonAndEpisodeInfo(videoProperties);
        }

        Object[] videoParams = {videoProperties};
        return new Object[] {loginToken, videoParams};
    }

    private void addSeasonAndEpisodeInfo(Map<String, String> videoProperties) {
        if (season != null && !season.isEmpty()) {
            videoProperties.put("season", season);
        }
        if (episode != null && !episode.isEmpty()) {
            videoProperties.put("episode", episode);
        }
    }

    private boolean shouldSearchByQuery() {
        return query != null && !query.isEmpty();
    }

    private boolean shouldSearchByImdbId() {
        return imdbid != null && !imdbid.isEmpty();
    }

    private boolean shouldSearchByTag() {
        return tag != null && !tag.isEmpty();
    }

    private boolean shouldSearchByMovieHash() {
        return movieHash != null && !movieHash.isEmpty() && movieByteSize != null && !movieByteSize.isEmpty();
    }
}
