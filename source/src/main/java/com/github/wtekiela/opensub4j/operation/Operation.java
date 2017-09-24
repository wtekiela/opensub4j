package com.github.wtekiela.opensub4j.operation;

import com.github.wtekiela.opensub4j.response.ResponseParser;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;

public interface Operation<T> {

    T execute(XmlRpcClient client, ResponseParser parser) throws XmlRpcException;

}
