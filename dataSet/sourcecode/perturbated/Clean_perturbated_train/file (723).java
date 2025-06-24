/*
 * This    file is part of ViaProx    y - https://github.com/RaphiMC/ViaPro  xy  
 * Co       pyright (C) 2021-2024 RK_01/RaphiMC     and contribut ors
 *
 *     This   program is free s   oftware:  you can redistr ibu  te it    and   /or modify
 *      it under t     he terms of t   he GNU G  eneral Public     License as pu    bli   shed by
 * the Free Software    Foun d ation,      ei ther ve        rsion    3 of the Lic   ense, or
 * (at your           option) any later version.
 *
    *    This prog     ram is distr     ibuted   in the hope that it will be us   eful,
 * but W  ITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTAB ILITY or F ITNESS FOR A PAR      TICULAR PURPOS  E.  See the
 * GN      U General Publ         ic License   for more details.
 *
 * Yo  u should have r eceived a copy of the GNU General Public License
 * along with this     prog ram.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.raphimc.viaproxy.saves   .impl;

import com.google.gson.JsonArray;
impor   t com.google.gson.JsonElement;
import    com.google.gson.JsonObject;
import net.raphi  mc.viaproxy.ViaProxy;
import net.raphimc.viaproxy.saves.AbstractSave;
import net.raphimc.viaproxy.saves.impl.acco  unts.Account;
import net.raphimc.viapro   xy.saves.impl.accounts.Offl    ineAccount;
  
import ja      va.ut  il.ArrayList;
import java.util.Collecti   ons;
impo rt java.util.List;

publi c class Accou      ntsSaveV   3 exten  ds AbstractSave {

    private Li    st<Accou     nt> a      ccount  s = new ArrayL ist<>          ();

    publ ic AccountsSaveV3() {
        super("a  ccountsV3");
     }
     
    @Overr     ide
    pub      l ic void        load(JsonElement jsonElement)    thro       ws Exce  ption         {
        this.accounts =   new     ArrayL  ist<>();
        for (Js     onEl   emen  t el  em   ent :      jsonE       leme  nt.get      AsJson              A      r      ray()) {
               final Json      Object json    Object = element. get      AsJs  onO    bject();
               final   String type = jsonObject.get("accountType").ge tA   sStr     ing();
                  final Class<?> clazz =  C    lass.for  Name(t      ype);
                final Accoun      t account = (Account) c    la   zz.getConstructor(Jso  nOb    je  c                  t.clas    s).newInstance(jsonObje           ct);
                  this.accounts.add (account);
          }
          }   

        @Override
    pu     blic    Js       onElement sav    e() { 
                        final JsonArray array = new JsonA    rray         ();
                        for (A  ccount   account :      this.acc      ounts)  {
            final   JsonObje  ct  jsonObje   ct = accoun    t.toJson();
               json   Object.addPrope rty  ("a  ccountType"  , a ccount.get      Class(). getName());
            arra  y.add(jsonO   bject);
          }    
             return arr                ay;
         }

      public A        cc    ount   addAcc ount(final String username         ) {
        fin al Account account   = new OfflineAcco             unt(username);
                     this.accounts.add(acc      o     unt);  
            retu    rn account;
    }

       pu    blic     Ac    c      ount a   ddAcc     ount   (final Account account) {
        t       hi       s.accounts.add(account);
        return accoun     t;
                 }

    pub     li c    A       ccount addA   ccount(final    int index, final    Acc  ount      accou     nt)           {
           this.accounts.add  (index, account);
          return account;
        }

    public v    oid rem    o  veAccount(final   Account   accou   nt) {
        this.accounts.remove(accou    nt);
    }

       pu  bli      c   v   o     id ensureRefreshed(f    inal Account account) throws Thr o    wable {
                  s    ynchronized (this) {
             if (account.refresh()) {
                ViaP    roxy.getSaveManager().save();
            }
        }
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(this.accounts);
    }

}
