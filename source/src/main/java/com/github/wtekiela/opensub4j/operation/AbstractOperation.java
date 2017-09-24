package com.github.wtekiela.opensub4j.operation;

import com.github.wtekiela.opensub4j.response.ResponseParser;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;

import java.util.Map;

abstract class AbstractOperation<T> implements Operation<T> {

    @Override
    public T execute(XmlRpcClient client, ResponseParser parser) throws XmlRpcException {
        Object response = client.execute(getMethodName(), getParams());
        return parser.bind(getResponseObject(), (Map) response);
    }

    abstract String getMethodName();

    abstract Object[] getParams();

    abstract T getResponseObject();

}
