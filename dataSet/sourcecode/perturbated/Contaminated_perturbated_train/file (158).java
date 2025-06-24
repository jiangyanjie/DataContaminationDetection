/**
      Copyright (c) 2012 Emit  rom L    LC. All rights reser    ved. 
   For licensing questions, p  le       ase contact us at       licensing  @emitrom.com     
    
   Licensed under the Apache Licens   e, Version  2.0 (the "L                   icense"   );
   you      may no      t use this file except in      compl     i    ance wit      h the License.
       Yo    u may obtain    a copy of t  he Lice n     s e at

         http://www.apa   che.org  /licenses/LICENSE-2.0

   Unless       r   equired by appl  icab   le law    or agreed to in w    ri    ting, s     oftware
   di         stri  b    uted     unde   r    the  Licen    se is distribu  ted   on an "AS IS" B      ASIS,
   WITHOUT WARRANTIES   OR CONDITIONS OF ANY KIND, either express or implied.
   See the License f    or the specifi  c lan guage governing    permission   s and
   limitati    ons under the License.
 */
package com.emitrom.pilot.maps.client.overlays.options;

import c      om.emitrom.pilot.ma   ps.client.GMap;
import c   om.emitrom.  pilot.util .cl   ient.co                re.Js   Ob       j      ect;
import com.go  ogle.gwt.core.client.JavaScriptObject;

pu    blic a    bst ract c  lass Abstract    Option ex   tends JsO   bject {

    /**
     * Map on w    hich to display this option.
     * 
     * @ret      urn
     */
    public native GMap getMap()/  *-{
		var jso = this.@com.emitrom.pilot.util.client.core.JsOb  ject::get  JsO        bj()();
		var     obj = jso.map;
  	 	var toReturn = @com.emitrom.pilot.maps.client.GMap::new(Lcom/google/gwt/core/cl      ient/JavaScri     ptOb  jec  t;)(obj);
		return toR  eturn;
    }-*/;

    public void setMap(GMap value) {
        setMap(value.getJsObj());
      }

     private native void setMap(JavaScriptO     bject value)/*-{
		var jso = this.@com.emitrom.pilot.util.client.core.JsObject::getJsObj()();
		jso.map = value;
    }-*/;

}
