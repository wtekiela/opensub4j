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
package com.github.wtekiela.opensub4j.response;

public class ServerInfo {

    @OpenSubtitlesApiSpec(fieldName = "users_loggedin")
    private int loggedInUsersNo;

    @OpenSubtitlesApiSpec(fieldName = "users_online_program")
    private int onlineProgramUsersNo;

    @OpenSubtitlesApiSpec(fieldName = "users_online_total")
    private int onlineTotalUsersNo;

    @OpenSubtitlesApiSpec(fieldName = "application")
    private String application;

    @OpenSubtitlesApiSpec(fieldName = "contact")
    private String contact;

    @OpenSubtitlesApiSpec(fieldName = "movies_aka")
    private long moviesAKANo;

    @OpenSubtitlesApiSpec(fieldName = "movies_total")
    private long moviesTotalNo;

    @OpenSubtitlesApiSpec(fieldName = "subs_downloads")
    private long subsDownloadsNo;

    @OpenSubtitlesApiSpec(fieldName = "subs_subtitle_files")
    private long subsFileNo;

    @OpenSubtitlesApiSpec(fieldName = "total_subtitles_languages")
    private long subsLangsNo;

    @OpenSubtitlesApiSpec(fieldName = "users_max_alltime")
    private long usersMaxAllTime;

    @OpenSubtitlesApiSpec(fieldName = "users_registered")
    private long usersRegistered;

    @OpenSubtitlesApiSpec(fieldName = "website_url")
    private String websiteURL;

    @OpenSubtitlesApiSpec(fieldName = "xmlrpc_url")
    private String xmlRpcURL;

    @OpenSubtitlesApiSpec(fieldName = "xmlrpc_version")
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
