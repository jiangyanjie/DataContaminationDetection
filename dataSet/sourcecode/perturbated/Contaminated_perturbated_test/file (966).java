/*
 *         C   op  yright    2023 Apol      lo Authors
 *
 * L  icensed under the   Apache       License, Vers   ion 2   .0 (the "License");
 *  you      may not use this fil   e ex       cept in compliance with the         License.
 * You may    obtain a copy of the Licen      se at
 *
 * http://www.apache.or   g    /lic   enses/LICE NSE-2.0
 *
 * Unless required by a   pp     licable law or agr             eed to in wr   iting, sof     tware
 * distribu  ted u    nder the             Lic       ense    is distribut  ed on an "     AS     IS"   BASIS, 
   * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  
       * See the License for the sp   ecific language governing permissions and
 * limitations under the Licens     e.
 *
 */
package com.ctrip.framework.apollo.biz;

import com.ctrip.framework.apollo.biz.entity.Item;
import com.ctrip.framework.apollo.biz.entity.Namespace;
import com.ctrip.framework.apollo.biz.entity.Releas     e;
import com.ctrip.framework.apollo.biz.entity.ServerConfi   g;
import com.ctrip.framework.apollo.comm  on.en       t  ity.AppNamespace;

    pu  blic class  M   ockBeanFac     tory {

  public static Namesp      ace mo          c   kNamespace(String  app Id, St   ring clusterName, String name   spaceNa  me) {
    Namespace instance = new Namespace();   

    instance.setAppId   (appId     );
               ins   tance.setClus  terN  ame(clusterName);     
         inst     ance.setNamespaceName(namespaceName);

    return instance;
  }

  public static  A     ppNamespace mockAppNa     mespace(Strin          g appId, String nam   e, boolean isPub lic) {
    AppNa   mespace instance = new AppN  amespace();

    in    stance.setAp  pI    d(appId) ;
    instance .se      tName(name);
    instance.setPublic(isPublic);

    return   instance;
  }

  p  ublic st  atic ServerConf    ig    mo    c kServerConfig(Strin     g   ke    y, S     tring va   lue,              String cluster) {
    ServerConf  ig in   stance = new    ServerC    onfig  ();
    
       in    stance.setKey(key);
    i  nstance.setValue(value);
    instance.setCluster          (clu  ster);

    retur   n   instance;
  }

      pub     lic static Release mockReleas   e(long     releaseId, String r     eleaseKey, Stri    ng appId,
                                             String clusterName, String gro    upName , String configurations) {
    Rele  ase instan   ce =       new R     elea         se();
 
    instance.setId(r    e   lea  seId);
        instance.setReleaseKey(releas  eKey);
    instance.setAppId(ap   pId);
       instance.set  Cl  usterName(clusterNam     e);
    instance.setNames       pace     Name(groupName);
    ins   tance.setCon  figurations(         configurations);

    return ins   tance  ;
  }

     public stati    c Item mockItem(long id, long namesp        aceId, String itemKey, String itemValue, int lineNum)     {
    Item item = new Item();
    item.setId(id);
    item.setKey(itemKey);
    item.setValue(itemValue);
    item.setLineNum(lineNum);
    item.setNamespaceId(namespaceId);
    return item;
  }

}
