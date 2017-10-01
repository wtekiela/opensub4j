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
package com.github.wtekiela.opensub4j.response;

public class ServerInfo {

    @OpenSubtitlesApi(fieldName = "users_loggedin")
    private int loggedInUsersNo;

    @OpenSubtitlesApi(fieldName = "users_online_program")
    private int onlineProgramUsersNo;

    @OpenSubtitlesApi(fieldName = "users_online_total")
    private int onlineTotalUsersNo;

    @OpenSubtitlesApi(fieldName = "application")
    private String application;

    @OpenSubtitlesApi(fieldName = "contact")
    private String contact;

    @OpenSubtitlesApi(fieldName = "movies_aka")
    private long moviesAKANo;

    @OpenSubtitlesApi(fieldName = "movies_total")
    private long moviesTotalNo;

    @OpenSubtitlesApi(fieldName = "subs_downloads")
    private long subsDownloadsNo;

    @OpenSubtitlesApi(fieldName = "subs_subtitle_files")
    private long subsFileNo;

    @OpenSubtitlesApi(fieldName = "total_subtitles_languages")
    private long subsLangsNo;

    @OpenSubtitlesApi(fieldName = "users_max_alltime")
    private long usersMaxAllTime;

    @OpenSubtitlesApi(fieldName = "users_registered")
    private long usersRegistered;

    @OpenSubtitlesApi(fieldName = "website_url")
    private String websiteURL;

    @OpenSubtitlesApi(fieldName = "xmlrpc_url")
    private String xmlRpcURL;

    @OpenSubtitlesApi(fieldName = "xmlrpc_version")
    private String xmlRpcVersion;

    public int getLoggedInUsersNo() {
        return loggedInUsersNo;
    }

    public int getOnlineProgramUsersNo() {
        return onlineProgramUsersNo;
    }

    public int getOnlineTotalUsersNo() {
        return onlineTotalUsersNo;
    }

    public String getApplication() {
        return application;
    }

    public String getContact() {
        return contact;
    }

    public long getMoviesAKANo() {
        return moviesAKANo;
    }

    public long getMoviesTotalNo() {
        return moviesTotalNo;
    }

    public long getSubsDownloadsNo() {
        return subsDownloadsNo;
    }

    public long getSubsFileNo() {
        return subsFileNo;
    }

    public long getSubsLangsNo() {
        return subsLangsNo;
    }

    public long getUsersMaxAllTime() {
        return usersMaxAllTime;
    }

    public long getUsersRegistered() {
        return usersRegistered;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public String getXmlRpcURL() {
        return xmlRpcURL;
    }

    public String getXmlRpcVersion() {
        return xmlRpcVersion;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "loggedInUsersNo=" + loggedInUsersNo +
                ", onlineProgramUsersNo=" + onlineProgramUsersNo +
                ", onlineTotalUsersNo=" + onlineTotalUsersNo +
                ", application='" + application + '\'' +
                ", contact='" + contact + '\'' +
                ", moviesAKANo=" + moviesAKANo +
                ", moviesTotalNo=" + moviesTotalNo +
                ", subsDownloadsNo=" + subsDownloadsNo +
                ", subsFileNo=" + subsFileNo +
                ", subsLangsNo=" + subsLangsNo +
                ", usersMaxAllTime=" + usersMaxAllTime +
                ", usersRegistered=" + usersRegistered +
                ", websiteURL='" + websiteURL + '\'' +
                ", xmlRpcURL='" + xmlRpcURL + '\'' +
                ", xmlRpcVersion=" + xmlRpcVersion +
                '}';
    }
}
