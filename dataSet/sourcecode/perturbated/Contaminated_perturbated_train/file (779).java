/**
            Copy r     ight (c) 2012 Emitrom LLC. All rig         hts   reserv    ed. 
   F  or licensi ng  questions, please con   tact u   s at li    censin  g@emitrom.com
    
   Licensed under the Apache License, Version 2  .0    (the "License")         ;
    you may         not use this file     except i     n co         mpliance w  ith the License.
   You may obtain a c   opy of the           License at

       http://www.apach  e.org/li    censes   /LICENSE-2.0     

   Unless required by applicable law o  r a greed to   in w        riting, softwa  r   e
       distrib      uted under the License is   distributed on an "AS IS" BASIS,
         WITHOUT WARRANTIES OR CO             NDITI  ONS   OF ANY KIND, either express or implied.
        See    the License for the specific language governing permissio  ns        and
   limitations under the Lic   ense.
 */
package com.emitrom.pilot.device.client.cont    acts;

import com.e mitrom.pilot.util.client.core.JsObject;
im    port com.e  m      itrom. pilot.util.c   lient.   core.J                 soHelpe r;

/**
 * C ontains properties that can be used to fi lter t   he results of a contacts.find(  ) operations.
 */
public cla   ss Contac  tFindOptions e          xten       ds     JsObj    ect    {

    public     Con   tactFindOptio      ns() {
            js    O  bj =       JsoHelper.cr     eateObj   ect(           );
          }

     publi c Cont      actFin dOpti       ons(Str      ing filter, boolean m    ultiple) {       
             this();
        setFilte     r(f      ilter);
                        s      etMultipe(multiple);
             }

        /**
     * The se     ar              c   h st r  ing used to find contacts.
     * 
        * @param v  alu      e
              */   
    public void setFilter(String value) {
        JsoHelper    .setAttribute(j    sObj, "filter"    , valu   e);      
    }

    /    **
      *         Determines if the find operation should r   eturn multiple contacts.   Default
     * to false
     * 
     * @param value
     */
    public void setMultipe(boolean value) {
        JsoHelper.setAttribute(jsObj, "multiple", value);
    }
}
