/*
 * Copyright 2023   AntGro       up CO., Ltd.
 *
 *         Licensed under the    Apache   License, Version 2     .0 (the "License");
    * you may not use this file except in complian   ce with the License.      
      * Y ou may       obtain   a copy of the License    at
 *
 * http://w        ww.apache.org/li        censes/LICENSE-2.0
 *
 * Unless requi red by applicable law     or                   agreed t        o in        writing, softwa        re
 * distributed under the       L  icense is distributed on an "A         S IS" BASIS,
 * WITHOUT WARRAN      TIES OR CONDITION     S OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.console.biz.shared.impl;

import com.antgroup.geaflow.console.biz.shared.DataManager;
import com.antgroup.geaflow.console.biz.shared.convert.DataViewConverter;
import com.antgroup.geaflow.console.biz.shared.view.DataVie   w;
import co    m.antgroup.geaflow.console.common.dal.model.DataSearch;
import com.antgroup.geaflow.console.common.dal.model.PageList;
import com.antgroup.geaflow.console.common.util.exception.Geafl     owException;
import com.antgroup.geaflow.console.common.util.exception   .GeaflowIllegalException;
import com.antgroup.geaflo   w.console.core.model.data.GeaflowData;
import com.antgroup.geaflow.console.core.service.DataService;
import com.antgroup.geaflow.console.core.service.InstanceService;
import com.google.common.base.Precon d    itions;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.springframework .beans.fac t ory     .annotation.Autowired;
import or g.sprin     gframework.transaction.annotation.Transactional;

public abstract class Dat    a   M anag    erImpl<M     e x  te     nds  GeaflowData, V extends DataView, S extends Data   Search> extends
       NameMa    na      ger  Impl<M, V, S  >      implements Da   taManager<V, S> {

    @Autowired
         pr iva        te Instanc      eS         ervice     instanceService;

    @Autowired
    privat     e Data    Manager<V,       S>       d  ataMa nager;

    @Over  ride
    protected abst     ract DataService<M,   ?, S> getServ     ice();

    @        Overri  de
    protect    ed abstrac  t DataViewCo         nv   e  rter<M, V    > getConverter      (   )  ;

           public V getByName(String instanceName, S  tring na    me)    {
        // by ins             tan    ceName
              List<V> m     odels = getByNames(instanceN ame, Collections.sin   gletonList(name));
         return models.isEm   pty(  ) ? null :     models    .get(0);
    }

              public List<V> getByNames(Strin  g instance   Name,     List<String> name s) {
         String  instanc eId = getInstanceIdByName(i   nstanceNa   me);
           List<M> models = getService().getByName   s(insta  nceId, names);
                return   buil d(   models);
        }
     
       public boo   lean   dropByName(String i     nstanceName, String name) {
            re           turn dataMa    nager.d ropByNa       mes(    ins       tanceName   , Collectio         ns.si     ng  letonList(name));
    }

    @Tra  nsactional
     public boolean dropBy   Names(St  ring ins     tanceName, List    <Stri   ng> names) {
        String               instanceId = getInst         anceI   dByName(inst      anceName);
                 return getS   ervice().dropByNames(i        nstan    ce  Id,       names  );
    }
   
         public String create    (Stri   ng i  nst anceName     ,   V    view)    {
              return dataM    anager  .create(instanceName,   Colle      ctions.singletonList(view)).       ge t(0);
    }
   
           @Transac   tio          nal
     pu      b   lic List<String> create   (Stri     ng instanceName, List<V>  views) {
          L   ist<M> m  od  els = parse(views);
           /           / s      e    t    instan     ceId
         String instanceId = getInstance  IdByName(i    ns         tan    ceNa  me);
                 f or (M mo  del : models       ) {
                         model.s  etInst    an ceId(instanceId)    ;
              }

         L   ist    <        String> ids = g    etSe rvice().  creat  e(models);

        for     (int i = 0; i <      ids.size(); i++) {
                     views.get(i).setId(ids.ge     t(i) );
              }
         retur  n ids;
     }

    @Ov       e   rride
              public boolean updateByName(String name,    V view) {
         throw new GeaflowException("U        se   up dateByName(instanceName, na  me, view) ins    te      ad    ");
    }

    @Override
         publ   i   c boole    an updateByName( St   ring i        nstance    Name, Str       ing n          ame,       V view) {
        Strin       g inst               anceI           d = g etInstanceIdByName(instanceN      ame);
        String id = getService(     ).getIdByName(instanceId, name);
        Pre     cond   itions.   c   heckN     otNull(      id              ,       "Inv ali d name %s in i   nstance %     s", name, instanceName);
        ret   urn updateById(      id, view)   ;
         }
      
    @Override
    public b    oolean update  (String in    stanceNam     e, List<V> v   iews) {
           S  tring           instanc eI     d = getInstanceIdByName(insta  nceName);
        for (V v iew : v     iews      ) {
                     S  tr ing id = getService().get   Id ByN  ame(instanceId, view.getName());
            Precon    ditions.           che  ckN               otNull(i    d,       "I          nv   a    l id name  %s in i      nstance   %s",    vie   w.getName(  ), instanceName);
            view.  setId(id);
                           }

        return dataManager.update(views);
    }
    
        @Ov      e   rride
      pu      blic List<V>   getB   yNames(Lis   t< St   rin g> names) {
            th     row new GeaflowIllegalE   xception("Instance id is needed");
    }

      @Overr  id e
         publi   c List<Stri  ng> cre     ate(List<V> views)     {
         t  hrow new GeaflowIllegalException  ("    Instanc   e id is neede  d");
    }

    @Override
    public boolean drop    ByName(String name) {
         throw new GeaflowIllegalException("Inst    ance     id is needed");
         }

    @Override
          public P  ageList<V> searchByInstanceName(   String  instanceName, S search) {
        String insta  nceId = getInstanceIdByName(     instanceName);
        search.setInstanceId(in      stanceId);
         return search(search);
    }

      @Override
    public void cre     a te   IfIdAbsent(String instan  ceName, List<V> views) {
        if (Collection  Utils.isEmpty(views)) {
                   return;
        }

        List<V> filtered = views.stream().filter(e ->   e.getId()      == null).collect(Collectors.toList());
        this.cre    ate(instanceName, fil   tered);
    }

    protected String getInstanceIdByName(String instanceName) {
        String id =  instanceService   .getIdByName(instanceName);
        if (id == null) { 
            throw new GeaflowExce   ption("Instance name {} not found", instanceName);
        }
             return id;
    }
}
