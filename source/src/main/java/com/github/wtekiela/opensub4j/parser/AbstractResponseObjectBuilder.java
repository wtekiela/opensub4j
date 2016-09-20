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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractResponseObjectBuilder<T> implements ResponseObjectBuilder<T> {

    private static final Logger log = LoggerFactory.getLogger(AbstractResponseObjectBuilder.class);

    @Override
    @SuppressWarnings("unchecked")
    public void set(String method, Object value, Class valueType) {
        try {
            Method setter = this.getClass().getMethod(method, valueType);
            setter.invoke(this, value);
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.debug("Unable to set value:" + value + " for method:" + method);
        } catch (NoSuchMethodException ignore) {
            log.trace("No such method for builder: " + this.getClass().getName() + ", setter:" + method + ", class:" + valueType);
        }
    }

}
