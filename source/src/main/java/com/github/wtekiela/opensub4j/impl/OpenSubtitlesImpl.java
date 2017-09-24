/**
 * Copyright (c) 2016 Wojciech Tekiela
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.github.wtekiela.opensub4j.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.wtekiela.opensub4j.response.*;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;

import com.github.wtekiela.opensub4j.api.OpenSubtitles;
import com.github.wtekiela.opensub4j.file.FileHashCalculator;
import com.github.wtekiela.opensub4j.file.OpenSubtitlesFileHashCalculator;

public class OpenSubtitlesImpl implements OpenSubtitles {

    private static final String ANONYMOUS = "";

    private final XmlRpcClient client;
    private final ResponseParser parser;
    private final FileHashCalculator hashCalculator;

    private LoginToken loginToken;

    public OpenSubtitlesImpl(URL serverUrl) {
        this.client = new RetriableXmlRpcClient(serverUrl);
        this.parser = new ResponseParser();
        this.hashCalculator = new OpenSubtitlesFileHashCalculator();
    }

    @Override
    public ServerInfo serverInfo() throws XmlRpcException {
        Object[] params = {};
        Object response = client.execute("ServerInfo", params);

        // note: no response status in server info

        if ((response instanceof Map)) {
            try {
                return parser.bind(new ServerInfo(), (Map) response);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            //throw new Exception("Unable to get server info, malformed response");
            return null;
        }
    }

    @Override
    public void login(String lang, String useragent) throws XmlRpcException {
        login(ANONYMOUS, ANONYMOUS, lang, useragent);
    }

    @Override
    public void login(String user, String pass, String lang, String useragent) throws XmlRpcException {
        if (loginToken != null) {
            throw new IllegalStateException("Already logged in! Please log out first.");
        }

        Object[] params = {user, pass, lang, useragent};
        Object response = client.execute("LogIn", params);

        try {
            loginToken = parser.bind(new LoginToken(), (Map) response);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void logout() throws XmlRpcException {
        ensureLoggedIn();

        Object[] params = {loginToken.getToken()};
        Object response = client.execute("LogOut", params);

        loginToken = null;
    }

    private void ensureLoggedIn() {
        if (loginToken == null) {
            throw new IllegalStateException("Not logged in!");
        }
    }

    @Override
    public void noop() throws XmlRpcException {
        ensureLoggedIn();

        Object[] params = {loginToken.getToken()};
        Object response = client.execute("NoOperation", params);
    }

    @Override
    public List<SubtitleInfo> searchSubtitles(String lang, File file) throws IOException, XmlRpcException {
        ensureLoggedIn();

        if (file == null) {
            throw new IllegalArgumentException("File cannot be null!");
        }

        String hash = hashCalculator.calculateHash(file);
        long size = file.length();

        return searchSubtitles(lang, hash, String.valueOf(size));
    }

    @Override
    public List<SubtitleInfo> searchSubtitles(String lang, String hash, String movieByteSize)
            throws XmlRpcException {
        return searchSubtitles(lang, hash, movieByteSize, null, null, null, null, null);
    }

    @Override
    public List<SubtitleInfo> searchSubtitles(String lang, String movieHash, String movieByteSize, String imdbid,
                                              String query, String season, String episode, String tag)
            throws XmlRpcException {
        ensureLoggedIn();

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
        Object[] params = {loginToken.getToken(), videoParams};
        Object response = client.execute("SearchSubtitles", params);

        try {
            return parser.bindList(SubtitleInfo.class, (Map) response);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SubtitleInfo> searchSubtitles(String lang, String imdbId) throws XmlRpcException {
        return searchSubtitles(lang, null, null, imdbId, null, null, null, null);
    }

    @Override
    public List<SubtitleInfo> searchSubtitles(String lang, String query, String season, String episode)
            throws XmlRpcException {
        return searchSubtitles(lang, null, null, null, query, season, episode, null);
    }

    @Override
    public List<SubtitleFile> downloadSubtitles(int subtitleFileID) throws XmlRpcException {
        ensureLoggedIn();

        Object[] subtitleFileIDs = {subtitleFileID};
        Object[] params = {loginToken.getToken(), subtitleFileIDs};
        Object response = client.execute("DownloadSubtitles", params);

        try {
            return parser.bindList(SubtitleFile.class, (Map) response);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MovieInfo> searchMoviesOnImdb(String query) throws XmlRpcException {
        ensureLoggedIn();

        Object[] params = {loginToken.getToken(), query};
        Object response = client.execute("SearchMoviesOnIMDB", params);

        try {
            return parser.bindList(MovieInfo.class, (Map) response);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

}
