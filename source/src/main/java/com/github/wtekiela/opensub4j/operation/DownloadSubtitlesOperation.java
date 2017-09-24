package com.github.wtekiela.opensub4j.operation;

import com.github.wtekiela.opensub4j.response.SubtitleFile;

public class DownloadSubtitlesOperation extends ListOperation<SubtitleFile> {

    private final String loginToken;
    private final int subtitleFileID;

    public DownloadSubtitlesOperation(String loginToken, int subtitleFileID) {
        this.loginToken = loginToken;
        this.subtitleFileID = subtitleFileID;
    }

    @Override
    String getMethodName() {
        return "DownloadSubtitles";
    }

    @Override
    Object[] getParams() {
        Object[] subtitleFileIDs = {subtitleFileID};
        Object[] params = {loginToken, subtitleFileIDs};
        return params;
    }

    @Override
    Class<SubtitleFile> getResponseClass() {
        return SubtitleFile.class;
    }

}
