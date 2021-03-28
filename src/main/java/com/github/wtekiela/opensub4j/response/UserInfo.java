/**
 * Copyright (c) 2020 Wojciech Tekiela
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

public class UserInfo {

    @OpenSubtitlesApiSpec(fieldName = "UserRank")
    private String userRank;

    @OpenSubtitlesApiSpec(fieldName = "UserNickName")
    private String userNickName;

    @OpenSubtitlesApiSpec(fieldName = "UploadCnt")
    private int uploadCount;

    @OpenSubtitlesApiSpec(fieldName = "DownloadCnt")
    private int downloadCount;

    @OpenSubtitlesApiSpec(fieldName = "IsVIP")
    private boolean isVip;

    @OpenSubtitlesApiSpec(fieldName = "UserWebLanguage")
    private String userWebLanguage;

    @OpenSubtitlesApiSpec(fieldName = "UserPreferedLanguages")
    private String userPreferedLanguages;

    @OpenSubtitlesApiSpec(fieldName = "IDUser")
    private String userId;

    public String getUserRank() {
        return userRank;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public int getUploadCount() {
        return uploadCount;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public boolean isVip() {
        return isVip;
    }

    public String getUserWebLanguage() {
        return userWebLanguage;
    }

    public String getUserPreferedLanguages() {
        return userPreferedLanguages;
    }

    public String getUserId() {
        return userId;
    }
}
