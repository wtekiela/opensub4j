package org.opensub4j.parser;

import org.opensub4j.response.MovieInfo;

public class MovieInfoBuilder implements ResponseObjectBuilder<MovieInfo> {

    String id;
    String title;
    String from_redis;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFrom_redis(String from_redis) {
        this.from_redis = from_redis;
    }

    @Override
    public MovieInfo build() {
        return new MovieInfo(Integer.valueOf(id), title);
    }

}
