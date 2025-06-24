//    Web Common is a utility library for web applications.
//    Copyright (C) 2012 Adrián Romero Corchado.
//
//    This file is part of Web Common
//
//     Licensed under the Apache License, Version 2.0 (the "License");
//     you may not use this file except in compliance with the License.
//     You may obtain a copy of the License at
//     
//         http://www.apache.org/licenses/LICENSE-2.0
//     
//     Unless required by applicable law or agreed to in writing, software
//     distributed under the License is distributed on an "AS IS" BASIS,
//     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//     See the License for the specific language governing permissions and
//     limitations under the License.

package com.adr.web.common;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author adrian
 */
public class AppContext {

    // private static final Logger logger = Logger.getLogger(AppContext.class.getName());
    public Map<String, AppBean> beans = new HashMap<>();
    public static AppContext instance = null;

    private AppContext() {
    }

    public static void start() {
        instance = new AppContext();
    }
    
    public static void setup(AppContextConfig config) {      
        config.setup(instance);
    }

    public static AppContext getInstance() {
        return instance;
    }

    public <T> T getBean(Class<T> clazz) {
        return getBean(clazz.getName(), clazz);
    }

    public <T> T getBean(String name, Class<T> clazz) {
        AppBean<T> bean = beans.get(name);
        if (bean == null) {
            bean = new ClassLoadBean(name);
            beans.put(name, bean);
        }
        return bean.get();
    }

    public void addBean(String name, AppBean bean) {
        beans.put(name, bean);
    }

    public <T> void addBean(Class<T> name, AppBean<? extends T> bean) {
        beans.put(name.getName(), bean);
    }

    public void addBean(String name, Class clazz) {
        beans.put(name, new ClassLoadBean(clazz));
    }

    public <T> void addBean(Class<T> clazz) {
        beans.put(clazz.getName(), new ClassLoadBean(clazz));
    }
    
    public void addBean(String name, final Object o) {
        beans.put(name, new AppBean() {
            @Override
            public Object get() {
                return o;
            }
        });
    }
    
    public <T> void addBean(Class<T> clazz, final T o) {
        beans.put(clazz.getName(), new AppBean<T>() {
            @Override
            public T get() {
                return o;
            }
        });
    }    
}
