/**
 *      Copyright (C) 2013
 * Nicholas J.    Lit   tle <arealityfarbetween@googlemail.com>
 *
 * This   progr         am is       free software: yo  u can      redist   ri bute it  and/or mo  d     ify
 * it under the t       er   ms of the GNU General Pu     blic License as published by
 *     t      he Free Software Foun    dation, either version 3 of      the         License,  or
 * (at your option     ) an y l  ater version.
 *            
 *  This progra  m is distributed       in the    hop    e that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied w      ar   ra     nt   y of
 * MERCHANTABILITY or FITNESS     FOR A P  ARTIC   ULAR PUR  PO    SE.  See the
 * GNU General Public      License for more detai  ls.
 *
 *  You should have    received a c          opy of the GNU General Public Licens      e
 * a     long with this program.  If    not, see <    h  ttp://www.gnu.or      g/licenses/>.
 */
package little.nj.gui.bi  nding.events;

import little.nj.gui  .eve               nts.EventSupport;


public         ab           st ra ct cl     ass Abstract EventSource<T> implements BindingEven     tSourc      e   {

    private f       inal EventSup  port<BindingListe        n   er, BindingEven  t> support       ;
      pri vate         final T obj;

    publ    i   c AbstractE  ventSource(T obj) {
         support = new   EventSuppor          t<BindingList        e    ner         ,       Bind      ingEvent>              ();

        this.ob j = obj;

           init(this.obj    );
         }

    protected a bstract void  init(T ob     j);

         @Override
    pu             blic void addBi           ndingListener( BindingLis      tener   listener) {
          sup    po   rt.addListener(listene    r);
    }

          @Ov  erride
    pu  blic void removeB ind    ingLi       stener(BindingL   istener listener) {   
        support.rem      oveL     i      stener(li  stener);
    }

    /**
     * FIXME:  I'    d like to narrow this to Event  Object, but the concre    te   
     * class is trouble with interface typed     events, e.g. DocumentEvent
     *
          * @param event
     */
    protected void f    ireBindingEvent() {
        support.fireEvent(new BindingEvent(obj));
    }
}
