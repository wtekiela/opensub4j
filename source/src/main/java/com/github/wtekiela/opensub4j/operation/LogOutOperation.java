package com.github.wtekiela.opensub4j.operation;

public class LogOutOperation extends AbstractOperation {

    private final String loginToken;

    public LogOutOperation(String loginToken) {
        this.loginToken = loginToken;
    }

    @Override
    String getMethodName() {
        return "LogOut";
    }

    @Override
    Object[] getParams() {
        return new Object[]{loginToken};
    }

    @Override
    Object getResponseObject() {
        return null;
    }

}
