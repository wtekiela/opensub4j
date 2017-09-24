package com.github.wtekiela.opensub4j.operation;

import com.github.wtekiela.opensub4j.response.LoginToken;

public class LogInOperation extends AbstractOperation<LoginToken> {

    private final String user;
    private final String pass;
    private final String lang;
    private final String useragent;

    public LogInOperation(String user, String pass, String lang, String useragent) {
        this.user = user;
        this.pass = pass;
        this.lang = lang;
        this.useragent = useragent;
    }

    @Override
    String getMethodName() {
        return "LogIn";
    }

    @Override
    Object[] getParams() {
        return new Object[]{user, pass, lang, useragent};
    }

    @Override
    LoginToken getResponseObject() {
        return new LoginToken();
    }

}
