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
package com.emitrom.pilot.maps.client.overlays.options;

import com.emitrom.pilot.maps.client.GMap;
import com.emitrom.pilot.util.client.core.JsObject;
import com.google.gwt.core.client.JavaScriptObject;

public abstract class AbstractOption extends JsObject {

    /**
     * Map on which to display this option.
     * 
     * @return
     */
    public native GMap getMap()/*-{
		var jso = this.@com.emitrom.pilot.util.client.core.JsObject::getJsObj()();
		var obj = jso.map;
		var toReturn = @com.emitrom.pilot.maps.client.GMap::new(Lcom/google/gwt/core/client/JavaScriptObject;)(obj);
		return toReturn;
    }-*/;

    public void setMap(GMap value) {
        setMap(value.getJsObj());
    }

    private native void setMap(JavaScriptObject value)/*-{
		var jso = this.@com.emitrom.pilot.util.client.core.JsObject::getJsObj()();
		jso.map = value;
    }-*/;

}
