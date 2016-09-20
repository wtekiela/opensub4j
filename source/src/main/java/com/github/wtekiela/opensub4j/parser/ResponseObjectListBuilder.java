/**
 * Copyright (c) 2016 Wojciech Tekiela
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
package com.github.wtekiela.opensub4j.parser;

import java.util.LinkedList;
import java.util.List;

class ResponseObjectListBuilder<T, S extends ResponseObjectBuilder<T>> extends AbstractResponseObjectBuilder<List<T>> {

    private final ResponseParser parser;
    private final Class<S> clazz;

    private Object[] data = {};

    public ResponseObjectListBuilder(ResponseParser parser, Class<S> itemBuilderClass) {
        this.parser = parser;
        this.clazz = itemBuilderClass;
    }

    public void setData(Object[] data) {
        this.data = data;
    }

    @Override
    public List<T> build() {
        List<T> items = new LinkedList<>();
        for (Object obj : data) {
            T item = null;
            try {
                item = parser.parse(clazz.newInstance(), obj);
            } catch (InstantiationException | IllegalAccessException e) {
                // @todo handle exception
                e.printStackTrace();
            }
            items.add(item);
        }
        return items;
    }

}
