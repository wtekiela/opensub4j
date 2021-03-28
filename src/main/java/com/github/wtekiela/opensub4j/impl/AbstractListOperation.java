/**
 * Copyright (c) 2021 Wojciech Tekiela
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
package com.github.wtekiela.opensub4j.impl;

import com.github.wtekiela.opensub4j.response.ListResponse;
import java.util.Map;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;

abstract class AbstractListOperation<T> implements Operation<ListResponse<T>> {

    @Override
    public ListResponse<T> execute(XmlRpcClient client, ResponseParser parser) throws XmlRpcException {
        Object response = client.execute(getMethodName(), getParams());
        return parser.bind(new ListResponse<>(), getListElementFactory(), (Map) response);
    }

    abstract String getMethodName();

    abstract Object[] getParams();

    abstract ElementFactory<T> getListElementFactory();

    interface ElementFactory<T> {

        T newInstance();

    }
}
