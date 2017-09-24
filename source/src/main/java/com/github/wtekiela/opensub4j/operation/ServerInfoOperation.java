package com.github.wtekiela.opensub4j.operation;

import com.github.wtekiela.opensub4j.response.ServerInfo;

public class ServerInfoOperation extends AbstractOperation<ServerInfo> {

    @Override
    String getMethodName() {
        return "ServerInfo";
    }

    @Override
    Object[] getParams() {
        return new Object[0];
    }

    @Override
    ServerInfo getResponseObject() {
        return new ServerInfo();
    }

}
