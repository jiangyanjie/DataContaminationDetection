/**
   Copyright (c) 2012 Emitrom LLC. All rights reserved. 
   For licensing questions, please contact us at licensing@emitrom.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.emitrom.pilot.device.client.contacts;

import com.emitrom.pilot.util.client.core.JsObject;
import com.emitrom.pilot.util.client.core.JsoHelper;

/**
 * Contains properties that can be used to filter the results of a contacts.find() operations.
 */
public class ContactFindOptions extends JsObject {

    public ContactFindOptions() {
        jsObj = JsoHelper.createObject();
    }

    public ContactFindOptions(String filter, boolean multiple) {
        this();
        setFilter(filter);
        setMultipe(multiple);
    }

    /**
     * The search string used to find contacts.
     * 
     * @param value
     */
    public void setFilter(String value) {
        JsoHelper.setAttribute(jsObj, "filter", value);
    }

    /**
     * Determines if the find operation should return multiple contacts. Default
     * to false
     * 
     * @param value
     */
    public void setMultipe(boolean value) {
        JsoHelper.setAttribute(jsObj, "multiple", value);
    }
}
