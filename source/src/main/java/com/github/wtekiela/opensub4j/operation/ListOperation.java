package com.github.wtekiela.opensub4j.operation;

import com.github.wtekiela.opensub4j.response.ResponseParser;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class ListOperation<T> implements Operation<List<T>> {

    @Override
    public List<T> execute(XmlRpcClient client, ResponseParser parser) throws XmlRpcException {
        Object response = client.execute(getMethodName(), getParams());
        try {
            return parser.bindList(getResponseClass(), (Map) response);
        } catch (InstantiationException | IllegalAccessException e) {
            return new ArrayList<>();
        }
    }

    abstract String getMethodName();

    abstract Object[] getParams();

    abstract Class<T> getResponseClass();
}
