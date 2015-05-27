/**
 * Copyright (c) 2015 Wojciech Tekiela
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

    private final int loggedInUsersNo;
    private final int onlineProgramUsersNo;
    private final int onlineTotalUsersNo;
    private final String application;
    private final String contact;
    private final int moviesAKANo;
    private final int moviesTotalNo;
    private final int subsDownloadsNo;
    private final int subsFileNo;
    private final int subsLangsNo;
    private final int usersMaxAllTime;
    private final int usersRegistered;
    private final String websiteURL;
    private final String xmlRpcURL;
    private final double xmlRpcVersion;

    public ServerInfo(int loggedInUsersNo, int onlineProgramUsersNo, int onlineTotalUsersNo, String application,
                      String contact, int moviesAKANo, int moviesTotalNo, int subsDownloadsNo, int subsFileNo,
                      int subsLangsNo, int usersMaxAllTime, int usersRegistered, String websiteURL, String xmlRpcURL,
                      double xmlRpcVersion) {
        this.loggedInUsersNo = loggedInUsersNo;
        this.onlineProgramUsersNo = onlineProgramUsersNo;
        this.onlineTotalUsersNo = onlineTotalUsersNo;
        this.application = application;
        this.contact = contact;
        this.moviesAKANo = moviesAKANo;
        this.moviesTotalNo = moviesTotalNo;
        this.subsDownloadsNo = subsDownloadsNo;
        this.subsFileNo = subsFileNo;
        this.subsLangsNo = subsLangsNo;
        this.usersMaxAllTime = usersMaxAllTime;
        this.usersRegistered = usersRegistered;
        this.websiteURL = websiteURL;
        this.xmlRpcURL = xmlRpcURL;
        this.xmlRpcVersion = xmlRpcVersion;
    }

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

    public int getMoviesAKANo() {
        return moviesAKANo;
    }

    public int getMoviesTotalNo() {
        return moviesTotalNo;
    }

    public int getSubsDownloadsNo() {
        return subsDownloadsNo;
    }

    public int getSubsFileNo() {
        return subsFileNo;
    }

    public int getSubsLangsNo() {
        return subsLangsNo;
    }

    public int getUsersMaxAllTime() {
        return usersMaxAllTime;
    }

    public int getUsersRegistered() {
        return usersRegistered;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public String getXmlRpcURL() {
        return xmlRpcURL;
    }

    public double getXmlRpcVersion() {
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
