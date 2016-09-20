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
package com.github.wtekiela.opensub4j.parser;

import java.util.Map;

import com.github.wtekiela.opensub4j.response.ServerInfo;

class ServerInfoBuilder extends AbstractResponseObjectBuilder<ServerInfo> {

    private Integer users_loggedin;
    private Integer users_online_program;
    private Integer users_online_total;
    private Map download_limits;
    private Map last_update_strings;
    private String application;
    private String contact;
    private String movies_aka;
    private String movies_total;
    private String subs_downloads;
    private String subs_subtitle_files;
    private String total_subtitles_languages;
    private String users_max_alltime;
    private String users_registered;
    private String website_url;
    private String xmlrpc_url;
    private String xmlrpc_version;

    public void setUsers_loggedin(Integer users_loggedin) {
        this.users_loggedin = users_loggedin;
    }

    public void setUsers_online_program(Integer users_online_program) {
        this.users_online_program = users_online_program;
    }

    public void setUsers_online_total(Integer users_online_total) {
        this.users_online_total = users_online_total;
    }

    public void setDownload_limits(Map download_limits) {
        this.download_limits = download_limits;
    }

    public void setLast_update_strings(Map last_update_strings) {
        this.last_update_strings = last_update_strings;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setMovies_aka(String movies_aka) {
        this.movies_aka = movies_aka;
    }

    public void setMovies_total(String movies_total) {
        this.movies_total = movies_total;
    }

    public void setSubs_downloads(String subs_downloads) {
        this.subs_downloads = subs_downloads;
    }

    public void setSubs_subtitle_files(String subs_subtitle_files) {
        this.subs_subtitle_files = subs_subtitle_files;
    }

    public void setTotal_subtitles_languages(String total_subtitles_languages) {
        this.total_subtitles_languages = total_subtitles_languages;
    }

    public void setUsers_max_alltime(String users_max_alltime) {
        this.users_max_alltime = users_max_alltime;
    }

    public void setUsers_registered(String users_registered) {
        this.users_registered = users_registered;
    }

    public void setWebsite_url(String website_url) {
        this.website_url = website_url;
    }

    public void setXmlrpc_url(String xmlrpc_url) {
        this.xmlrpc_url = xmlrpc_url;
    }

    public void setXmlrpc_version(String xmlrpc_version) {
        this.xmlrpc_version = xmlrpc_version;
    }

    public ServerInfo build() {
        return new ServerInfo(users_loggedin, users_online_program, users_online_total, application, contact,
                Long.valueOf(movies_aka), Long.valueOf(movies_total), Long.valueOf(subs_downloads),
                Long.valueOf(subs_subtitle_files), Long.valueOf(total_subtitles_languages),
                Long.valueOf(users_max_alltime), Long.valueOf(users_registered),
                website_url, xmlrpc_url, Double.valueOf(xmlrpc_version));
    }

}
