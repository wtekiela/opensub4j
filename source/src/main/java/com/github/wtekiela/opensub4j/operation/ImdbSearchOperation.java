package com.github.wtekiela.opensub4j.operation;

import com.github.wtekiela.opensub4j.response.MovieInfo;

public class ImdbSearchOperation extends ListOperation<MovieInfo> {

    private final String loginToken;
    private final String query;

    public ImdbSearchOperation(String loginToken, String query) {
        this.loginToken = loginToken;
        this.query = query;
    }

    @Override
    String getMethodName() {
        return "SearchMoviesOnIMDB";
    }

    @Override
    Object[] getParams() {
        return new Object[]{loginToken, query};
    }

    @Override
    Class<MovieInfo> getResponseClass() {
        return MovieInfo.class;
    }

}
