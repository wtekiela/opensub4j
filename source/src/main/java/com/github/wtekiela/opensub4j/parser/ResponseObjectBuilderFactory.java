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

import java.util.List;

import com.github.wtekiela.opensub4j.response.LoginToken;
import com.github.wtekiela.opensub4j.response.MovieInfo;
import com.github.wtekiela.opensub4j.response.Reply;
import com.github.wtekiela.opensub4j.response.ServerInfo;
import com.github.wtekiela.opensub4j.response.SubtitleFile;
import com.github.wtekiela.opensub4j.response.SubtitleInfo;

public class ResponseObjectBuilderFactory {

    public ResponseObjectBuilder<LoginToken> loginTokenBuilder() {
        return new LoginTokenBuilder();
    }

    public ResponseObjectBuilder<Reply> replyBuilder() {
        return new ReplyBuilder();
    }

    public ResponseObjectBuilder<ServerInfo> serverInfoBuilder() {
        return new ServerInfoBuilder();
    }

    public ResponseObjectBuilder<List<SubtitleInfo>> subtitleInfoListBuilder(ResponseParser parser) {
        return new ResponseObjectListBuilder<>(parser, SubtitleInfoBuilder.class);
    }

    public ResponseObjectBuilder<List<SubtitleFile>> subtitleFileListBuilder(ResponseParser parser) {
        return new ResponseObjectListBuilder<>(parser, SubtitleFileBuilder.class);
    }

    public ResponseObjectBuilder<List<MovieInfo>> movieInfoListBuilder(ResponseParser parser) {
        return new ResponseObjectListBuilder<>(parser, MovieInfoBuilder.class);
    }
}
