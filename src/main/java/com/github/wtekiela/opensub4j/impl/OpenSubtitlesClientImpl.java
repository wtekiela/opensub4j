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

import com.github.wtekiela.opensub4j.api.FileHashCalculator;
import com.github.wtekiela.opensub4j.api.OpenSubtitlesClient;
import com.github.wtekiela.opensub4j.response.ListResponse;
import com.github.wtekiela.opensub4j.response.LoginResponse;
import com.github.wtekiela.opensub4j.response.MovieInfo;
import com.github.wtekiela.opensub4j.response.Response;
import com.github.wtekiela.opensub4j.response.ResponseStatus;
import com.github.wtekiela.opensub4j.response.ServerInfo;
import com.github.wtekiela.opensub4j.response.SubtitleFile;
import com.github.wtekiela.opensub4j.response.SubtitleInfo;
import com.github.wtekiela.opensub4j.xmlrpc.client.RetryableXmlRpcClient;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfig;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class OpenSubtitlesClientImpl implements OpenSubtitlesClient {

    private final XmlRpcClient xmlRpcClient;
    private final ResponseParser responseParser;
    private final FileHashCalculator fileHashCalculator;

    private LoginResponse loginResponse;

    /**
     * Client for opensubtitles.org xml-rpc API
     *
     * @param serverUrl API URL
     */
    public OpenSubtitlesClientImpl(URL serverUrl) {
        XmlRpcClientConfigImpl xmlRpcClientConfig = new XmlRpcClientConfigImpl();
        xmlRpcClientConfig.setServerURL(serverUrl);
        xmlRpcClientConfig.setEnabledForExtensions(true);
        xmlRpcClientConfig.setGzipCompressing(true);
        xmlRpcClientConfig.setGzipRequesting(true);

        this.xmlRpcClient = new RetryableXmlRpcClient(xmlRpcClientConfig);
        this.responseParser = new ResponseParser();
        this.fileHashCalculator = new OpenSubtitlesFileHashCalculator();
    }

    /**
     * Client for opensubtitles.org xml-rpc API
     *
     * @param serverUrl   API URL
     * @param maxAttempts maximum number of retries for each request before throwing an exception
     * @param interval    interval between retries
     */
    public OpenSubtitlesClientImpl(URL serverUrl, int maxAttempts, int interval) {
        XmlRpcClientConfigImpl xmlRpcClientConfig = new XmlRpcClientConfigImpl();
        xmlRpcClientConfig.setServerURL(serverUrl);

        this.xmlRpcClient = new RetryableXmlRpcClient(xmlRpcClientConfig, maxAttempts, interval);
        this.responseParser = new ResponseParser();
        this.fileHashCalculator = new OpenSubtitlesFileHashCalculator();
    }

    /**
     * Client for opensubtitles.org xml-rpc API
     *
     * @param xmlRpcClientConfig configuration for {@link XmlRpcClient}
     */
    public OpenSubtitlesClientImpl(XmlRpcClientConfig xmlRpcClientConfig) {
        this(new RetryableXmlRpcClient(xmlRpcClientConfig), new ResponseParser(),
            new OpenSubtitlesFileHashCalculator());
    }

    /**
     * Client for opensubtitles.org xml-rpc API
     *
     * @param xmlRpcClientConfig configuration for {@link XmlRpcClient}
     * @param maxAttempts        maximum number of retries for each request before throwing an exception
     * @param interval           interval between retries
     */
    public OpenSubtitlesClientImpl(XmlRpcClientConfig xmlRpcClientConfig, int maxAttempts, int interval) {
        this(new RetryableXmlRpcClient(xmlRpcClientConfig, maxAttempts, interval), new ResponseParser(),
            new OpenSubtitlesFileHashCalculator());
    }

    OpenSubtitlesClientImpl(XmlRpcClient xmlRpcClient, ResponseParser responseParser,
                            FileHashCalculator fileHashCalculator) {
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
    public synchronized LoginResponse login(String user, String pass, String lang, String useragent) throws XmlRpcException {
        ensureNotLoggedIn();
        LoginResponse response =
            new LogInOperation(user, pass, lang, useragent).execute(xmlRpcClient, responseParser);
        if (ResponseStatus.OK.equals(response.getStatus())) {
            this.loginResponse = response;
        }
        return response;
    }

    @Override
    public synchronized void logout() throws XmlRpcException {
        ensureLoggedIn();
        new LogOutOperation(loginResponse.getToken()).execute(xmlRpcClient, responseParser);
        loginResponse = null;
    }

    @Override
    public boolean isLoggedIn() {
        return loginResponse != null;
    }

    @Override
    public Response noop() throws XmlRpcException {
        ensureLoggedIn();
        return new NoopOperation(loginResponse.getToken()).execute(xmlRpcClient, responseParser);
    }

    @Override
    public ListResponse<SubtitleInfo> searchSubtitles(String lang, File file) throws IOException, XmlRpcException {
        ensureLoggedIn();
        String hash = fileHashCalculator.calculateHash(file);
        long size = file.length();
        return searchSubtitles(lang, hash, String.valueOf(size));
    }

    @Override
    public ListResponse<SubtitleInfo> searchSubtitles(String lang, String hash, String movieByteSize)
        throws XmlRpcException {
        return searchSubtitles(lang, hash, movieByteSize, null, null, null, null, null);
    }

    @Override
    public ListResponse<SubtitleInfo> searchSubtitles(String lang, String imdbId) throws XmlRpcException {
        return searchSubtitles(lang, null, null, imdbId, null, null, null, null);
    }

    @Override
    public ListResponse<SubtitleInfo> searchSubtitles(String lang, String query, String season, String episode)
        throws XmlRpcException {
        return searchSubtitles(lang, null, null, null, query, season, episode, null);
    }

    @Override
    public ListResponse<SubtitleInfo> searchSubtitles(String lang, String movieHash, String movieByteSize,
                                                      String imdbid,
                                                      String query, String season, String episode,
                                                      String tag) throws XmlRpcException {
        ensureLoggedIn();
        return new SearchOperation(loginResponse.getToken(), lang, movieHash, movieByteSize, tag, imdbid, query, season,
            episode)
            .execute(xmlRpcClient, responseParser);
    }

    @Override
    public ListResponse<SubtitleFile> downloadSubtitles(int subtitleFileID) throws XmlRpcException {
        ensureLoggedIn();
        return new DownloadSubtitlesOperation(loginResponse.getToken(), subtitleFileID)
            .execute(xmlRpcClient, responseParser);
    }

    @Override
    public ListResponse<MovieInfo> searchMoviesOnImdb(String query) throws XmlRpcException {
        ensureLoggedIn();
        return new ImdbSearchOperation(loginResponse.getToken(), query)
            .execute(xmlRpcClient, responseParser);
    }

    private void ensureNotLoggedIn() {
        if (loginResponse != null) {
            throw new IllegalStateException("Already logged in! Please log out first.");
        }
    }

    private void ensureLoggedIn() {
        if (loginResponse == null) {
            throw new IllegalStateException("Not logged in!");
        }
    }

}
