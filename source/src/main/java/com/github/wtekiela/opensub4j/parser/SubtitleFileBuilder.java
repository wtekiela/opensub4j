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

import com.github.wtekiela.opensub4j.response.SubtitleFile;

class SubtitleFileBuilder extends AbstractResponseObjectBuilder<SubtitleFile> {

    private String data;
    private String idsubtitlefile;

    public void setData(String data) {
        this.data = data;
    }

    public void setIdsubtitlefile(String idsubtitlefile) {
        this.idsubtitlefile = idsubtitlefile;
    }

    @Override
    public SubtitleFile build() {
        Integer id = Integer.valueOf(this.idsubtitlefile);
        return new SubtitleFile(id, data);
    }
}
