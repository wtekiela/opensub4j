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
package com.github.wtekiela.opensub4j.parser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

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
        String content = getContent();

        Integer id = Integer.valueOf(this.idsubtitlefile);

        return new SubtitleFile(id, content);
    }

    private String getContent() {
        String content = null;
        try {
            content = decompress(decode(data), 10240);
        } catch (IOException e) {
            // @todo add logging
            e.printStackTrace();
        }
        return content == null ? data : content;
    }

    private static String decompress(byte[] bytes, int bufferSize) throws IOException {
        try (GZIPInputStream is = new GZIPInputStream(new ByteArrayInputStream(bytes));
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            int bytesRead;
            byte[] buffer = new byte[bufferSize];
            while ((bytesRead = is.read(buffer)) > 0) {
                os.write(buffer, 0, bytesRead);
            }
            return os.toString();
        }
    }

    private static byte[] decode(String s) {
        return Base64.getDecoder().decode(s);
    }
}
