/**
 * Copyright (c) 2017 Wojciech Tekiela
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.github.wtekiela.opensub4j.operation.*;
import com.github.wtekiela.opensub4j.response.*;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;

import com.github.wtekiela.opensub4j.api.OpenSubtitles;
import com.github.wtekiela.opensub4j.file.FileHashCalculator;
import com.github.wtekiela.opensub4j.file.OpenSubtitlesFileHashCalculator;

public class OpenSubtitlesImpl implements OpenSubtitles {

    private final XmlRpcClient client;
    private final ResponseParser parser;
    private final FileHashCalculator hashCalculator;

    private LoginToken loginToken;

    public OpenSubtitlesImpl(URL serverUrl) {
        this(new RetriableXmlRpcClient(serverUrl), new ResponseParser(), new OpenSubtitlesFileHashCalculator());
    }

    OpenSubtitlesImpl(XmlRpcClient client, ResponseParser parser, FileHashCalculator hashCalculator) {
        this.client = client;
        this.parser = parser;
        this.hashCalculator = hashCalculator;
    }

    @Override
    public ServerInfo serverInfo() throws XmlRpcException {
        // note: no response status in server info
        Operation<ServerInfo> operation = new ServerInfoOperation();
        return operation.execute(client, parser);
    }

    @Override
    public void login(String lang, String useragent) throws XmlRpcException {
        login("", "", lang, useragent);
    }

    @Override
    public void login(String user, String pass, String lang, String useragent) throws XmlRpcException {
        ensureNotLoggedIn();
        loginToken = new LogInOperation(user, pass, lang, useragent).execute(client, parser);
    }

    private void ensureNotLoggedIn() {
        if (loginToken != null) {
            throw new IllegalStateException("Already logged in! Please log out first.");
        }
    }

    @Override
    public void logout() throws XmlRpcException {
        ensureLoggedIn();
        new LogOutOperation(loginToken.getToken()).execute(client, parser);
    }

    private void ensureLoggedIn() {
        if (loginToken == null) {
            throw new IllegalStateException("Not logged in!");
        }
    }

    @Override
    public void noop() throws XmlRpcException {
        ensureLoggedIn();
        new NoopOperation().execute(client, parser);
    }

    @Override
    public List<SubtitleInfo> searchSubtitles(String lang, File file) throws IOException, XmlRpcException {
        ensureLoggedIn();
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
    public List<SubtitleInfo> searchSubtitles(String lang, String imdbId) throws XmlRpcException {
        return searchSubtitles(lang, null, null, imdbId, null, null, null, null);
    }

    @Override
    public List<SubtitleInfo> searchSubtitles(String lang, String query, String season, String episode)
            throws XmlRpcException {
        return searchSubtitles(lang, null, null, null, query, season, episode, null);
    }

    @Override
    public List<SubtitleInfo> searchSubtitles(String lang, String movieHash, String movieByteSize, String imdbid,
                                              String query, String season, String episode, String tag) throws XmlRpcException {
        ensureLoggedIn();
        return new SearchOperation(loginToken.getToken(), lang, movieHash, movieByteSize, tag, imdbid, query, season, episode)
                .execute(client, parser);
    }

    @Override
    public List<SubtitleFile> downloadSubtitles(int subtitleFileID) throws XmlRpcException {
        ensureLoggedIn();
        return new DownloadSubtitlesOperation(loginToken.getToken(), subtitleFileID)
                .execute(client, parser);
    }

    @Override
    public List<MovieInfo> searchMoviesOnImdb(String query) throws XmlRpcException {
        ensureLoggedIn();
        return new ImdbSearchOperation(loginToken.getToken(), query)
                .execute(client, parser);
    }

}
