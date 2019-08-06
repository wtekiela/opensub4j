/**
 * Copyright (c) 2019 Wojciech Tekiela
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

import com.github.wtekiela.opensub4j.api.OpenSubtitlesClient;
import com.github.wtekiela.opensub4j.response.*;
import com.github.wtekiela.opensub4j.xmlrpc.client.RetriableXmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;

import com.github.wtekiela.opensub4j.api.FileHashCalculator;
import org.apache.xmlrpc.client.XmlRpcClientConfig;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class OpenSubtitlesClientImpl implements OpenSubtitlesClient {

    private final XmlRpcClient xmlRpcClient;
    private final ResponseParser responseParser;
    private final FileHashCalculator fileHashCalculator;

    private LoginToken loginToken;

    /**
     * Client for opensubtitles.org xml-rpc API
     *
     * @param serverUrl API URL
     */
    public OpenSubtitlesClientImpl(URL serverUrl) {
        XmlRpcClientConfigImpl xmlRpcClientConfig = new XmlRpcClientConfigImpl();
        xmlRpcClientConfig.setServerURL(serverUrl);

        this.xmlRpcClient = new RetriableXmlRpcClient(xmlRpcClientConfig);
        this.responseParser = new ResponseParser();
        this.fileHashCalculator = new OpenSubtitlesFileHashCalculator();
    }

    /**
     * Client for opensubtitles.org xml-rpc API
     *
     * @param serverUrl API URL
     * @param maxAttempts maximum number of retries for each request before throwing an exception
     * @param interval interval between retries
     */
    public OpenSubtitlesClientImpl(URL serverUrl, int maxAttempts, int interval) {
        XmlRpcClientConfigImpl xmlRpcClientConfig = new XmlRpcClientConfigImpl();
        xmlRpcClientConfig.setServerURL(serverUrl);

        this.xmlRpcClient = new RetriableXmlRpcClient(xmlRpcClientConfig, maxAttempts, interval);
        this.responseParser = new ResponseParser();
        this.fileHashCalculator = new OpenSubtitlesFileHashCalculator();
    }

    /**
     * Client for opensubtitles.org xml-rpc API
     *
     * @param xmlRpcClientConfig configuration for {@link XmlRpcClient}
     */
    public OpenSubtitlesClientImpl(XmlRpcClientConfig xmlRpcClientConfig) {
        this(new RetriableXmlRpcClient(xmlRpcClientConfig), new ResponseParser(), new OpenSubtitlesFileHashCalculator());
    }

    /**
     * Client for opensubtitles.org xml-rpc API
     *
     * @param xmlRpcClientConfig configuration for {@link XmlRpcClient}
     * @param maxAttempts maximum number of retries for each request before throwing an exception
     * @param interval interval between retries
     */
    public OpenSubtitlesClientImpl(XmlRpcClientConfig xmlRpcClientConfig, int maxAttempts, int interval) {
        this(new RetriableXmlRpcClient(xmlRpcClientConfig, maxAttempts, interval), new ResponseParser(), new OpenSubtitlesFileHashCalculator());
    }

    OpenSubtitlesClientImpl(XmlRpcClient xmlRpcClient, ResponseParser responseParser, FileHashCalculator fileHashCalculator) {
        this.xmlRpcClient = xmlRpcClient;
        this.responseParser = responseParser;
        this.fileHashCalculator = fileHashCalculator;
    }

    @Override
    public ServerInfo serverInfo() throws XmlRpcException {
        // note: no response status in server info
        Operation<ServerInfo> operation = new ServerInfoOperation();
        return operation.execute(xmlRpcClient, responseParser);
    }

    @Override
    public void login(String lang, String useragent) throws XmlRpcException {
        login("", "", lang, useragent);
    }

    @Override
    public void login(String user, String pass, String lang, String useragent) throws XmlRpcException {
        ensureNotLoggedIn();
        loginToken = new LogInOperation(user, pass, lang, useragent).execute(xmlRpcClient, responseParser);
    }

    private void ensureNotLoggedIn() {
        if (loginToken != null) {
            throw new IllegalStateException("Already logged in! Please log out first.");
        }
    }

    @Override
    public void logout() throws XmlRpcException {
        ensureLoggedIn();
        new LogOutOperation(loginToken.getToken()).execute(xmlRpcClient, responseParser);
        loginToken = null;
    }

    private void ensureLoggedIn() {
        if (loginToken == null) {
            throw new IllegalStateException("Not logged in!");
        }
    }

    @Override
    public boolean isLoggedIn() {
        return loginToken != null;
    }

    @Override
    public void noop() throws XmlRpcException {
        ensureLoggedIn();
        new NoopOperation(loginToken.getToken()).execute(xmlRpcClient, responseParser);
    }

    @Override
    public List<SubtitleInfo> searchSubtitles(String lang, File file) throws IOException, XmlRpcException {
        ensureLoggedIn();
        String hash = fileHashCalculator.calculateHash(file);
        long size = file.length();
        return searchSubtitles(lang, hash, String.valueOf(size));
    }

    @Override
    public List<SubtitleInfo> searchSubtitles(String lang, String hash, String movieByteSize) throws XmlRpcException {
        return searchSubtitles(lang, hash, movieByteSize, null, null, null, null, null);
    }

    @Override
    public List<SubtitleInfo> searchSubtitles(String lang, String imdbId) throws XmlRpcException {
        return searchSubtitles(lang, null, null, imdbId, null, null, null, null);
    }

    @Override
    public List<SubtitleInfo> searchSubtitles(String lang, String query, String season, String episode) throws XmlRpcException {
        return searchSubtitles(lang, null, null, null, query, season, episode, null);
    }

    @Override
    public List<SubtitleInfo> searchSubtitles(String lang, String movieHash, String movieByteSize,
                                              String imdbid,
                                              String query, String season, String episode,
                                              String tag) throws XmlRpcException {
        ensureLoggedIn();
        return new SearchOperation(loginToken.getToken(), lang, movieHash, movieByteSize, tag, imdbid, query, season, episode)
                .execute(xmlRpcClient, responseParser);
    }

    @Override
    public List<SubtitleFile> downloadSubtitles(int subtitleFileID) throws XmlRpcException {
        ensureLoggedIn();
        return new DownloadSubtitlesOperation(loginToken.getToken(), subtitleFileID)
                .execute(xmlRpcClient, responseParser);
    }

    @Override
    public List<MovieInfo> searchMoviesOnImdb(String query) throws XmlRpcException {
        ensureLoggedIn();
        return new ImdbSearchOperation(loginToken.getToken(), query)
                .execute(xmlRpcClient, responseParser);
    }

}
