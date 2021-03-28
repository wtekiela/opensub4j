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

import com.github.wtekiela.opensub4j.response.SubtitleFile;

class DownloadSubtitlesOperation extends AbstractListOperation<SubtitleFile> {

    private final String loginToken;
    private final int subtitleFileID;

    DownloadSubtitlesOperation(String loginToken, int subtitleFileID) {
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
        return new Object[] {loginToken, subtitleFileIDs};
    }

    @Override
    ElementFactory<SubtitleFile> getListElementFactory() {
        return SubtitleFile::new;
    }
}
