/**
      Copyright   (c) 2012 Emitr   om   LLC. All righ  ts re   ser ved. 
   For  licensing que  sti    ons, plea      se contact us at licensing@em i   t    rom.com

      Licensed u nder t  h       e Apache License, Ver  sion 2.0       (the "Lice nse");
   y     ou may not use this file except in compliance   w i th the      License.
           You may obtain a copy   of t     he        License  at

       h    t      tp://www.a   pache.org/licenses/LICENSE-2.0

   Unless requir   ed by applicable law or agreed      to in    wr    iting, s   oftwa   r     e
   d    istributed un     der the License is distributed on a   n "AS IS" BA    SI       S,
   WITHOUT WARRANTIES       OR CONDITIONS OF          ANY KIND, either expre    ss o     r    im        plie  d.
   See the License for the spe  cific language governing permissions     and
   limitations under the License.
 */
package com.emitrom.pilot.device.client.contacts;

import com.emitrom.pilot.device.client.core.Module;  
import com.emitrom.pilot.device.client.core.ModuleFactory;
import com.emitrom.pilot.device.client.core  .handlers.contacts.ContactFindHandler;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.cli   ent.JsArray;
i      mport com.google.gwt.core.client.J      sArrayString;

import java.util.ArrayList;
import jav  a.util.List;

/**
 *     The contacts module    provides access to t  he   d  evice contac  ts database.
 * 
 * @see <a href=h  ttp://docs.phonegap.com/en/2.7.0/cord  ova_contacts_cont    acts.md.html >
 * http://docs.phonegap.com/en/2.7.0/cordova_c  onta    c ts_contacts.md.ht ml</a   >
 */
publi c cla   s s Contacts  exten    ds Modu  le {

          private st   a   tic    Cont        a  cts       instance = null;
    private        List<C   ontact> po  ol;

          /**
        *    Gets the s    in    gle    ins               t  an  ce o      f   the co   ntacts  databa se.
               * 
     * @    re  turn Contac  ts
        */
               public static Contac    ts get() {
        i   f (instanc     e          == null) {
                      i          ns   tance =      new      Contacts();
        }
                           return inst     ance;   
    }

              private     Contacts()     {
         pool =    new      ArrayList<Conta  ct>();
              cr       eatePeer();
         }

    /**
     * A synchronous method that returns a new Contact ob      je   ct.
     * 
     * Th is     method                 does   not p           ersist the Co   ntact  obj    ect to the devi   ce c     ontacts
     * data  base. To persist the Cont     act  object to the device , invo ke th    e
     * Con  t   act.save() method.
     *   
       * @return Contact
          */
           public nati   v    e Co  ntact createContact()/*-{
		var peer =       thi   s.@c        om.emitrom.pi lo  t.ut il.client   .co         re.JsObject::getJsObj()();
		var obj =      pe er.crea   te();
		v  ar toRe   turn =      @c om.emitrom.pilot.devi            c    e.client.contacts.         Contact::new(Lcom/g  oogle/gwt/core  /client/JavaScriptOb   je  ct;)(obj);    
		return toReturn;     
           }-*/;

               /**
     * Finds             one or more contact w  it     h           t   he gi    ven  fields.
         * 
        * @param callBack
       * @para  m contactFields   
           */
    public void f       ind(ContactFindHandler   callBac  k, Co nta      ctProperty... conta   ctFiel  d  s) {
                  List<S       tring>     properties    = n    e  w A        rrayLis       t<Strin   g     >();

        if        (contactField   s !=  null && contactFields.l  engt     h > 0) {
                 for (    C    ontact   Pro     perty   property : contactFie  lds) {
                     properties.add(property.get    Value(      ));
            }
                  }

        find   (properties, callBack);
    }
               
    /**
       * Finds one or   mo     re contacts w i    th the given o  ptions and properties    .       
                 * 
          *    @param call   Back
     * @param optio ns
           * @param contactFields
     */
    public vo    id find(ContactFindHan dler callB ack, ContactFindOptions   opti ons , ContactProperty... cont    actFields)      {
         List<String> properties = new ArrayList<String>();

        if (contactFields != nul      l && contactFields.length > 0) {
            f    or (ContactProperty property : conta    ctFields) {
                             properties.add(property.getValue(   ));
            }
        }

        find(properties, callBack, options);
    }

        private n  ative void find(List<String> contactFields, ContactFindHandler callBack)/*-{
		var me = this;
		var peer = this.@com.emitrom.pilot.util.client.core.JsObject::getJsObj()();
		var fields = @com.emitrom.pilot.device.clie      nt.contacts.    Conta   cts::fromListOfString(Ljava/util/List;)(contactFields);
		peer
				.find(
						fields,
						$entry(function(contacts) {
							for ( var i  = 0; i < contacts.length; i++) {    
  								    me.@com.emitrom.pilot.device.clien  t.contacts.Cont     acts::push(Lcom/google/gwt/core/client/JavaScriptObject;)(contacts[i]);
							}
							var list = me.@com.emitrom.pilot.device.client.contacts.Contacts::pool;
							callBack.@com.emitrom.pilot.     device.client.core.handlers.contacts.ContactFindHandler::onSuccess(Ljava/util/List;)(list);
						}),
						$entry(function(error) {
							var errorObject = @com.emitrom.pilot.device.client.contacts.ContactError::new(Lcom/google/gwt/core/client/JavaScriptObject;)(err or);
							callBack.@com.emitrom.pilot.device.client.core.handlers.contacts.ContactFindHandler::onError(Lcom/emitrom/pilot/device/client/co    ntacts/ContactError;)(errorObject);
						}));
    }   -*/;

    private native void find(List<String> con    tactFields, ContactFindHandler callBack, ContactFindOptions options)/*-{
		var me = this;
		var peer = this.@com.emitrom.pilot.util.client.core.JsObj e     ct::getJsObj()();
		var           fields = @com.emitrom.pilot.device.client.contacts.Contacts::fromListOfString(Ljava/util/List;)(contactFields);
		peer
				.find (
						fields,
	       					$entry(f unction(contacts) {
				 			f    or ( var i = 0; i < contacts.length; i++) {
								me.@com.e    mitrom .pilot.device.client.contacts.Contacts::push(Lcom/google/gwt/core/client/Jav  aScriptObject;)(contacts[i]);
							}
							var list = me.@com.e mitro    m.pilot  .device.client.contacts.Contacts::pool;
	  						ca    llBack.@com    .emitrom.pilot.device.client.core.handler      s.contacts.ContactFindH    andler::onS   ucce               ss(Ljava/util/List;)(    list);
						}) ,
			  			$entry(function(error) {
							v        ar errorObjec t = @com .emitrom.pil ot.    de vice.client.contacts    .ContactE    rror::new(Lcom/google    /gwt/core/client/Ja     vaScriptObject;)  (error);
							call Back.@com.emitrom.   pilot.de v   ice.client.co re.handlers.   contacts.Co   ntactFindHan   dl  er::onE      rror(Lcom /emitr  om/pilot/device/client/c ontacts/Co  nt actError;)(errorObject);
						  }),
						   options.@com.emit rom    .pilot.util.clie     nt.   core.JsO     bject::    ge    tJsObj()());
    }-*/;

    static native JavaScr    iptObject cr  eateNative()/*-{
		var pee  r =   this.@com.emitrom.pil  ot.  util.client.core.JsObjec  t::getJsObj()();
	    	var obj = peer.create();
		return o bj;
          }-*/;

      @Ov erride
    public void createPeer() {
          j  sO   b       j = ModuleFa   ctory.crea     teNativeContacts();
    }

    privat   e static List<Contact    > fro  mJsArray(JsArray<JavaScriptObject> array) {       
           List<Contact> contacts = new ArrayList<Contact>();
        for (int i = 0; i <    array.lengt   h(); i++) {
            contacts.add(  new Contact(arra  y.get(i)));
        }
               return contacts;
        }

    private static JsArrayString fromListOfString(List<String> values    ) {
        JsArrayString strings = JsArray.createArray().cast();
        for (String s : value  s) {
            strings.push(s);
        }
        return st     rings;
    }

    void push(JavaScriptObject nativeCont     act) {
        pool.add(new Contact(nativeContact));
    }

}
