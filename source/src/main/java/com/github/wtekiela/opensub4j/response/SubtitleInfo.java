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
package com.github.wtekiela.opensub4j.response;

public class SubtitleInfo {

    private final int id;
    private final int subtitleFileId;
    private final int downloadsNo;
    private final String language;
    private final String fileName;
    private final String format;
    private final String osLink;
    private final String downloadLink;
    private final String zipDownloadLink;
    private final String encoding;

    public SubtitleInfo(int id, int subtitleFileId, String language, String fileName, String format, int downloadsNo,
                        String osLink, String downloadLink, String zipDownloadLink, String encoding) {
        this.id = id;
        this.subtitleFileId = subtitleFileId;
        this.language = language;
        this.fileName = fileName;
        this.format = format;
        this.downloadsNo = downloadsNo;
        this.osLink = osLink;
        this.downloadLink = downloadLink;
        this.zipDownloadLink = zipDownloadLink;
        this.encoding = encoding;
    }

    public int getId() {
        return id;
    }

    public int getSubtitleFileId() {
        return subtitleFileId;
    }

    public int getDownloadsNo() {
        return downloadsNo;
    }

    public String getLanguage() {
        return language;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFormat() {
        return format;
    }

    public String getOsLink() {
        return osLink;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public String getZipDownloadLink() {
        return zipDownloadLink;
    }

    public String getEncoding() {
        return encoding;
    }

    @Override
    public String toString() {
        return "SubtitleInfo{" +
                "id=" + id +
                ", subtitleFileId=" + subtitleFileId +
                ", downloadsNo=" + downloadsNo +
                ", language='" + language + '\'' +
                ", fileName='" + fileName + '\'' +
                ", format='" + format + '\'' +
                ", osLink='" + osLink + '\'' +
                ", downloadLink='" + downloadLink + '\'' +
                ", zipDownloadLink='" + zipDownloadLink + '\'' +
                '}';
    }
}
