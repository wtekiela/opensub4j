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
package com.github.wtekiela.opensub4j.response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubtitleFile {

    public static final int BUFFER_SIZE = 10240;
    public static final String DEFAULT_CHARSET = Charset.defaultCharset().name();
    private static final Logger LOGGER = LoggerFactory.getLogger(SubtitleFile.class);
    private final Map<String, Content> contentCache = new ConcurrentHashMap<>();
    @OpenSubtitlesApiSpec(fieldName = "idsubtitlefile")
    private int id;
    @OpenSubtitlesApiSpec(fieldName = "data")
    private String encodedContent;

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
        return contentCache.computeIfAbsent(charsetName, cn -> new ContentBuilder(cn).build());
    }

    public String getContentAsString(String charsetName) {
        Content content = getSubtitleFileContent(charsetName);
        return content.getContent();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubtitleFile that = (SubtitleFile) o;
        return id == that.id;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SubtitleFile.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .toString();
    }

    public class Content {

        private final String decodedContent;
        private final String charsetName;

        Content(String charsetName, String content) {
            this.charsetName = charsetName;
            this.decodedContent = content;
        }

        public String getContent() {
            return decodedContent;
        }

        public String getCharsetName() {
            return charsetName;
        }
    }

    private class ContentBuilder {

        private final String charsetName;

        ContentBuilder(String charsetName) {
            this.charsetName = charsetName;
        }

        Content build() {
            return new Content(charsetName, decode(charsetName));
        }

        private String decode(String charsetName) {
            String content = null;
            try {
                byte[] rawContent = Base64.getDecoder().decode(encodedContent);
                content = decompress(rawContent, BUFFER_SIZE, charsetName);
            } catch (IOException e) {
                LOGGER.error("Error while decompressing content", e);
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
