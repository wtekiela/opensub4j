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

import com.github.wtekiela.opensub4j.response.SubtitleInfo;

class SubtitleInfoBuilder extends AbstractResponseObjectBuilder<SubtitleInfo> {

    private Map queryParameters;
    private String IDMovie;
    private String IDMovieImdb;
    private String IDSubMovieFile;
    private String IDSubtitle;
    private String IDSubtitleFile;
    private String ISO639;
    private String languageName;
    private String matchedBy;
    private String movieByteSize;
    private String movieFPS;
    private String movieHash;
    private String movieImdbRating;
    private String movieKind;
    private String movieName;
    private String movieNameEng;
    private String movieReleaseName;
    private String movieTimeMS;
    private String movieYear;
    private String queryNumber;
    private String seriesEpisode;
    private String seriesIMDBParent;
    private String seriesSeason;
    private String subActualCD;
    private String subAddDate;
    private String subAuthorComment;
    private String subBad;
    private String subComments;
    private String subDownloadLink;
    private String subDownloadsCnt;
    private String subEncoding;
    private String subFeatured;
    private String subFileName;
    private String subFormat;
    private String subHD;
    private String subHash;
    private String subHearingImpaired;
    private String subLanguageID;
    private String subRating;
    private String subSize;
    private String subSumCD;
    private String subtitlesLink;
    private String userID;
    private String userNickName;
    private String userRank;
    private String zipDownloadLink;

    public void setQueryParameters(Map queryParameters) {
        this.queryParameters = queryParameters;
    }

    public void setIDMovie(String IDMovie) {
        this.IDMovie = IDMovie;
    }

    public void setIDMovieImdb(String IDMovieImdb) {
        this.IDMovieImdb = IDMovieImdb;
    }

    public void setIDSubMovieFile(String IDSubMovieFile) {
        this.IDSubMovieFile = IDSubMovieFile;
    }

    public void setIDSubtitle(String IDSubtitle) {
        this.IDSubtitle = IDSubtitle;
    }

    public void setIDSubtitleFile(String IDSubtitleFile) {
        this.IDSubtitleFile = IDSubtitleFile;
    }

    public void setISO639(String ISO639) {
        this.ISO639 = ISO639;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public void setMatchedBy(String matchedBy) {
        this.matchedBy = matchedBy;
    }

    public void setMovieByteSize(String movieByteSize) {
        this.movieByteSize = movieByteSize;
    }

    public void setMovieFPS(String movieFPS) {
        this.movieFPS = movieFPS;
    }

    public void setMovieHash(String movieHash) {
        this.movieHash = movieHash;
    }

    public void setMovieImdbRating(String movieImdbRating) {
        this.movieImdbRating = movieImdbRating;
    }

    public void setMovieKind(String movieKind) {
        this.movieKind = movieKind;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setMovieNameEng(String movieNameEng) {
        this.movieNameEng = movieNameEng;
    }

    public void setMovieReleaseName(String movieReleaseName) {
        this.movieReleaseName = movieReleaseName;
    }

    public void setMovieTimeMS(String movieTimeMS) {
        this.movieTimeMS = movieTimeMS;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }

    public void setQueryNumber(String queryNumber) {
        this.queryNumber = queryNumber;
    }

    public void setSeriesEpisode(String seriesEpisode) {
        this.seriesEpisode = seriesEpisode;
    }

    public void setSeriesIMDBParent(String seriesIMDBParent) {
        this.seriesIMDBParent = seriesIMDBParent;
    }

    public void setSeriesSeason(String seriesSeason) {
        this.seriesSeason = seriesSeason;
    }

    public void setSubActualCD(String subActualCD) {
        this.subActualCD = subActualCD;
    }

    public void setSubAddDate(String subAddDate) {
        this.subAddDate = subAddDate;
    }

    public void setSubAuthorComment(String subAuthorComment) {
        this.subAuthorComment = subAuthorComment;
    }

    public void setSubBad(String subBad) {
        this.subBad = subBad;
    }

    public void setSubComments(String subComments) {
        this.subComments = subComments;
    }

    public void setSubDownloadLink(String subDownloadLink) {
        this.subDownloadLink = subDownloadLink;
    }

    public void setSubDownloadsCnt(String subDownloadsCnt) {
        this.subDownloadsCnt = subDownloadsCnt;
    }

    public void setSubEncoding(String subEncoding) {
        this.subEncoding = subEncoding;
    }

    public void setSubFeatured(String subFeatured) {
        this.subFeatured = subFeatured;
    }

    public void setSubFileName(String subFileName) {
        this.subFileName = subFileName;
    }

    public void setSubFormat(String subFormat) {
        this.subFormat = subFormat;
    }

    public void setSubHD(String subHD) {
        this.subHD = subHD;
    }

    public void setSubHash(String subHash) {
        this.subHash = subHash;
    }

    public void setSubHearingImpaired(String subHearingImpaired) {
        this.subHearingImpaired = subHearingImpaired;
    }

    public void setSubLanguageID(String subLanguageID) {
        this.subLanguageID = subLanguageID;
    }

    public void setSubRating(String subRating) {
        this.subRating = subRating;
    }

    public void setSubSize(String subSize) {
        this.subSize = subSize;
    }

    public void setSubSumCD(String subSumCD) {
        this.subSumCD = subSumCD;
    }

    public void setSubtitlesLink(String subtitlesLink) {
        this.subtitlesLink = subtitlesLink;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }

    public void setZipDownloadLink(String zipDownloadLink) {
        this.zipDownloadLink = zipDownloadLink;
    }

    public SubtitleInfo build() {
        int id = Integer.valueOf(IDSubtitle);
        int fileId = Integer.valueOf(IDSubtitleFile);
        int downloadsCount = Integer.valueOf(subDownloadsCnt);
        return new SubtitleInfo(id, fileId, languageName, subFileName, subFormat, downloadsCount, subtitlesLink, subDownloadLink, zipDownloadLink, subEncoding);
    }
}
