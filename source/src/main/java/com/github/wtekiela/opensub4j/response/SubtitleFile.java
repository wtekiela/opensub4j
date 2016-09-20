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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class SubtitleFile {

    public static final int BUFFER_SIZE = 10240;
    public static final String DEFAULT_CHARSET = Charset.defaultCharset().name();

    private final int id;
    private final String encodedContent;
    private final Map<String, Content> contentCache = new HashMap<>();

    public SubtitleFile(int id, String encodedContent) {
        this.id = id;
        this.encodedContent = encodedContent;
    }

    public int getId() {
        return id;
    }

    public Content getContent() {
        return getContent(DEFAULT_CHARSET);
    }

    public Content getContent(String charsetName) {
        return getSubtitleFileContent(charsetName);
    }

    private Content getSubtitleFileContent(String charsetName) {
        Content content = contentCache.get(charsetName);
        if (content == null) {
            content = new ContentBuilder(charsetName).build();
            contentCache.put(charsetName, content);
        }
        return content;
    }

    public String getContentAsString(String charsetName) {
        Content content = getSubtitleFileContent(charsetName);
        return content.getContent();
    }

    @Override
    public String toString() {
        return "SubtitleFile{" +
                "id=" + id +
                '}';
    }

    public class Content {

        private final String content;
        private final String charsetName;

        public Content(String charsetName, String content) {
            this.charsetName = charsetName;
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public String getCharsetName() {
            return charsetName;
        }
    }

    private class ContentBuilder {

        private final String charsetName;

        public ContentBuilder(String charsetName) {
            this.charsetName = charsetName;
        }

        public Content build() {
            return new Content(charsetName, decode(charsetName));
        }

        private String decode(String charsetName) {
            String content = null;
            try {
                byte[] rawContent = Base64.getDecoder().decode(encodedContent);
                content = decompress(rawContent, BUFFER_SIZE, charsetName);
            } catch (IOException e) {
                // @todo add logging
                e.printStackTrace();
            }
            return content;
        }

        private String decompress(byte[] bytes, int bufferSize, String charsetName) throws IOException {
            try (GZIPInputStream is = new GZIPInputStream(new ByteArrayInputStream(bytes));
                 ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                int bytesRead;
                byte[] buffer = new byte[bufferSize];
                while ((bytesRead = is.read(buffer)) > 0) {
                    os.write(buffer, 0, bytesRead);
                }
                return os.toString(charsetName);
            }
        }
    }
}
