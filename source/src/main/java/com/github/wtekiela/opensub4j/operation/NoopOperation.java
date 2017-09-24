package com.github.wtekiela.opensub4j.operation;

public class NoopOperation extends AbstractOperation {

    @Override
    String getMethodName() {
        return "NoOperation";
    }

    @Override
    Object[] getParams() {
        return new Object[0];
    }

    @Override
    Object getResponseObject() {
        return null;
    }
}
