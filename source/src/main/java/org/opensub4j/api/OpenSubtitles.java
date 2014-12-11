/**
 *    Copyright (c) 2014 Wojciech Tekiela
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.opensub4j.api;

import org.apache.xmlrpc.XmlRpcException;
import org.opensub4j.response.ServerInfo;
import org.opensub4j.response.SubtitleFile;
import org.opensub4j.response.SubtitleInfo;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
     * @throws org.apache.xmlrpc.XmlRpcException
     */
    public ServerInfo serverInfo() throws XmlRpcException;

    /**
     * Login as anonymous user. Logging in is required before talking to the server.
     *
     * @param lang      ISO639 2 letter language code
     * @param useragent UserAgent registered with OpenSubtitles
     *
     * @throws org.apache.xmlrpc.XmlRpcException
     */
    public void login(String lang, String useragent) throws XmlRpcException;

    /**
     * Login given user, set interface language and initiate session. Logging in is required before talking to the
     * server.
     *
     * @param user      username
     * @param pass      password
     * @param lang      ISO639 2 letter language code
     * @param useragent UserAgent registered with OpenSubtitles
     *
     * @throws org.apache.xmlrpc.XmlRpcException
     */
    public void login(String user, String pass, String lang, String useragent) throws XmlRpcException;

    /**
     * Logout the user and end the session. This method should always be called just before exiting the application.
     *
     * @throws org.apache.xmlrpc.XmlRpcException
     */
    public void logout() throws XmlRpcException;

    /**
     * This method is used to keep the session alive while client application is idling. Should be called every 15
     * minutes between XML-RPC requests (in case user is idle or client application is not currently communicating with
     * OSDb server) to keep the connection alive while client application is still running. It can be also used to check
     * if given session is still active.
     *
     * @throws org.apache.xmlrpc.XmlRpcException
     */
    public void noop() throws XmlRpcException;

    /**
     * Search for subtitle files matching your videos using a movie file.
     *
     * @param lang ISO639-3 language code
     * @param file Movie file
     *
     * @return Information about found subtitles
     *
     * @throws org.apache.xmlrpc.XmlRpcException
     * @throws java.io.IOException
     */
    public List<SubtitleInfo> searchSubtitles(String lang, File file) throws IOException, XmlRpcException;

    public List<SubtitleFile> downloadSubtitles(int subtitleFileID) throws XmlRpcException;

}
