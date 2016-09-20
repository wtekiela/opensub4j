/**
 * Copyright (c) 2016 Wojciech Tekiela
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
package com.github.wtekiela.opensub4j.api;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.xmlrpc.XmlRpcException;

import com.github.wtekiela.opensub4j.response.MovieInfo;
import com.github.wtekiela.opensub4j.response.ServerInfo;
import com.github.wtekiela.opensub4j.response.SubtitleFile;
import com.github.wtekiela.opensub4j.response.SubtitleInfo;

/**
 * opensubtitles.org XML-RPC API client
 */
public interface OpenSubtitles {

    /**
     * Retrieves basic information about the server. It could be used for ping or telling server info to client, no
     * valid UserAgent is needed.
     *
     * @return server information
     *
     * @throws org.apache.xmlrpc.XmlRpcException When exception occurs during XML-RPC call
     */
    ServerInfo serverInfo() throws XmlRpcException;

    /**
     * Login as anonymous user. Logging in is required before talking to the OSDb server.
     *
     * @param lang      ISO639 2 letter language code
     * @param useragent UserAgent registered with OpenSubtitles
     *
     * @throws org.apache.xmlrpc.XmlRpcException When exception occurs during XML-RPC call
     */
    void login(String lang, String useragent) throws XmlRpcException;

    /**
     * Login given user, set interface language and initiate session. Logging in is required before talking to the OSDb
     * server.
     *
     * Login user {user} identified by password {pass} communicating in language {lang} and working in client
     * application {useragent}. This function should be always called when starting communication with OSDb server to
     * identify user, specify application and start a new session (either registered user or anonymous).
     *
     * If user has no account, blank username and password should be used.
     *
     * @param user      username
     * @param pass      password
     * @param lang      ISO639 2 letter language code
     * @param useragent UserAgent registered with OpenSubtitles
     *
     * @throws org.apache.xmlrpc.XmlRpcException When exception occurs during XML-RPC call
     */
    void login(String user, String pass, String lang, String useragent) throws XmlRpcException;

    /**
     * Logout the user and end the session. This method should always be called just before exiting the application.
     *
     * @throws org.apache.xmlrpc.XmlRpcException When exception occurs during XML-RPC call
     */
    void logout() throws XmlRpcException;

    /**
     * This method is used to keep the session alive while client application is idling. Should be called every 15
     * minutes between XML-RPC requests (in case user is idle or client application is not currently communicating with
     * OSDb server) to keep the connection alive while client application is still running. It can be also used to check
     * if given session is still active.
     *
     * @throws org.apache.xmlrpc.XmlRpcException When exception occurs during XML-RPC call
     */
    void noop() throws XmlRpcException;

    /**
     * Search for subtitle files matching your videos using a movie file. If {lang} is empty or contains the string
     * 'all' - search is performed for all languages.
     *
     * @param lang ISO639-3 language code
     * @param file Movie file
     *
     * @return Information about found subtitles
     *
     * @throws org.apache.xmlrpc.XmlRpcException When exception occurs during XML-RPC call
     * @throws java.io.IOException               If the file does not exist, is a directory rather than a regular file
     *                                           or other I/O error occurs
     */
    List<SubtitleInfo> searchSubtitles(String lang, File file) throws IOException, XmlRpcException;

    /**
     * Search for subtitle files by computed file hash and size of a video file. If {lang} is empty or contains the
     * string 'all' - search is performed for all languages.
     *
     * @param lang ISO639-3 language code
     * @param hash Calculated video file hash
     * @param size Size of a video file in bytes
     *
     * @return Information about found subtitles
     *
     * @throws org.apache.xmlrpc.XmlRpcException When exception occurs during XML-RPC call
     */
    List<SubtitleInfo> searchSubtitles(String lang, String hash, String size) throws XmlRpcException;

    /**
     * Search for subtitle files matching your videos using IMDB ids. If {lang} is empty or contains the string 'all' -
     * search is performed for all languages. Please note that some fields (IDSubMovieFile, MovieHash, MovieByteSize,
     * MovieTimeMS) are missing in output when using this method.
     *
     * @param lang   ISO639-3 language code
     * @param imdbId IMDB movie ID
     *
     * @return Information about found subtitles
     *
     * @throws org.apache.xmlrpc.XmlRpcException When exception occurs during XML-RPC call
     */
    List<SubtitleInfo> searchSubtitles(String lang, String imdbId) throws XmlRpcException;

    /**
     * Search for subtitle files using full text search.
     *
     * @param lang    ISO639-3 language code
     * @param query   Query for the full text search engine
     * @param season  Season number
     * @param episode Episode number
     *
     * @return Information about found subtitles
     *
     * @throws org.apache.xmlrpc.XmlRpcException When exception occurs during XML-RPC call
     */
    List<SubtitleInfo> searchSubtitles(String lang, String query, String season, String episode)
            throws XmlRpcException;

    /**
     * Search for subtitle files. If you define movie hash and size, then the rest of the parameters are ignored. If you
     * define tag - query, season and episode are ignored. Full text search can be performed by using query, season and
     * episode parameters.
     *
     * @param lang    ISO639-3 language code
     * @param hash    Calculated video file hash
     * @param size    Size of a video file in bytes
     * @param imdbid  IMDB movie ID
     * @param query   Query for the full text search engine
     * @param season  Season number
     * @param episode Episode number
     * @param tag     Index of movie filename, subtitle filename or release name
     *
     * @return Information about found subtitles
     *
     * @throws org.apache.xmlrpc.XmlRpcException When exception occurs during XML-RPC call
     */
    List<SubtitleInfo> searchSubtitles(String lang, String hash, String size, String imdbid,
                                       String query, String season, String episode, String tag)
            throws XmlRpcException;

    /**
     * Download given subtitle file
     *
     * @param subtitleFileID ID of the subtitle file to download
     *
     * @return Subtitle files
     *
     * @throws org.apache.xmlrpc.XmlRpcException When exception occurs during XML-RPC call
     */
    List<SubtitleFile> downloadSubtitles(int subtitleFileID) throws XmlRpcException;

    /**
     * Searches for movies matching given movie title {query}. Returns list of movies data found on IMDb.com and in
     * internal server movie database. Manually added movies can be identified by ID starting at 10000000.
     *
     * @param query Query string
     *
     * @return Information about found movies
     *
     * @throws org.apache.xmlrpc.XmlRpcException When exception occurs during XML-RPC call
     */
    List<MovieInfo> searchMoviesOnImdb(String query) throws XmlRpcException;

}
