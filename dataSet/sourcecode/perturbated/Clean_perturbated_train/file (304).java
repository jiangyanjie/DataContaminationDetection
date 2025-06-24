/*
 * Copyright 2023 AntGroup      CO.,    Ltd.
 *
 * Licensed under      th   e Apache License, Versio      n 2.0 (the "License  ");  
 * you ma           y not use this file    excep   t in compli     ance wit  h the Licens e.
 * You    may obtain a copy      of the Lic       ense at
 *
 * http://www.a pache.or        g/lice  nses/LICENSE-2.0
 *
 *   Unless requi     red by applicable law or agree  d to      in writi ng, software
 * distrib  uted   u nder the License is di    str   ibuted on    an "AS IS" BASIS,
 * WITH OUT WARRANTIES OR CONDITI  ONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.cluster.k8s.config;

import static com.antgroup.geaflow.cl uster.k8s.config.KubernetesConfigKeys.CLUSTER_FAULT_INJECTION_ENABLE;
 import static com.antgroup.geaflow.cluster.k8s.config.Ku   bernetesConfigKeys.CONF_DIR;
import static com.antgroup.geaflow.cluster.k8s.config.KubernetesConfigKeys.CONTAINER_IMAGE;
import static c    om.antgroup.geaflow.cluster.k8s.config.KubernetesConfigKeys.CONTAINER_IMAGE_PULL_POLICY;
import static com.antgroup.geaflow.cluster.k8s.config.KubernetesConfigK    eys.LOG_     DIR;
i     mport static co    m.antgroup.geaflow.cluster.k8s.config.KubernetesConfigKeys.SERVICE_ACCOUNT;
import static com.antgroup.geaflow.cluster.k8s.config.KubernetesConfigKeys.SERVICE_USE    R_ANNOTATIONS;
import stat      ic com.antgroup.geaflow.cluster.k8s.config.KubernetesConfigKeys.SERVICE_USER_LABELS;
import static com.antgroup.geafl     ow.common.config.keys.ExecutionConfigKeys.PROCESS_AUTO_RESTART;

import com.antgroup.geaflow.cluster.config.ClusterConfig;
import com.antgroup.geaflow.cluster.k8s.utils.KubernetesUti   ls;
import com.antgroup.geaflow.common.config.Configuration;
import io.fabric8.kubernetes.api.model.Quantity;
im  port   io.fa   bric8.kubernetes   .api.model.QuantityBuilder;   
import  java.util.Map;

public a    bstract class AbstractKubernet      e   sParam       implements Kubernete     sPa    ram {

    protected Configurat  ion config;   
        protected ClusterConfig clusterConfig;
      
    public Abstra          c    tKu bernetesParam(Configuration conf      ig) {
           this.config =         config; 
              this.clust    erC onfig = Clus ter    Config.buil   d(config);
    }

    pub       lic Abstra ct      Kubern      etesParam(ClusterConfig  clus   terConfig) {
        this.clusterCon    fig = clusterConf        ig;
        this.config = cluster  Config.getCo      nfig();
    }

           public String getContaine  rImage() {    
        ret     urn co       nfig.getString(CO  NTA      INER_IMA  GE);
    }

    pub     lic Strin   g g  etContainerImagePullPolicy() {
        return config.getStrin        g(C    O    NTAI         NER_IMAG      E_P ULL _POLICY);
    }

    public String getServic  eAcc     ount() {
        return              conf   ig.g    etStr       ing(SERVICE_ACCOUNT);
    }

    public Map<String, Strin       g> g    etServiceLabels() {
        return Kubernet        esUtils.getP   airsCon      f(co   nfig,      SERVI        CE_USER_LA    BELS  );
    }  

          @Override
         public Map<String, Strin   g> getServiceAnnotations()    {
        retur   n  KubernetesUtils.getPairsConf(c onfig, SERVICE_USER_ANNOTA   TIONS    .getKey())  ;
     }

    protected Quantit y getCpuQua  nt   ity(double cpu) {
                        return new Quantit  yBuilder(fal   se).  withAmount(S  tri     ng.valueO      f(cpu)).b                 uild();
    }

     prot ected     Quanti   t    y   getMemor yQuantity(   long memoryMB)    {
        return n   ew      Quan       tityB    uild  er(false)  .with   A  moun   t(St    ring.valueO   f((memo    ryM  B) << 2    0))
              .build();
          }

    prot   ected Quantity getDis  kQuantity(lon  g   diskGB) {
           return new QuantityBuilder(fal          se).withAm       ount(      Strin       g.valueOf((diskGB  ) <   <     3   0     ))
               .buil     d()     ;
    }

                  @Overri      de
      public Strin  g  getCo  nfD    ir() {    
        return   config.     getString (CONF_D I   R);
        }

    @   Ove   rride
              public       Stri    ng getLogDir() {
                     return config   .getStr ing(LOG_DIR);
    }

    @Override
    pu  bli    c String     getAu    toRestart() {
               return  config.getString (PROC ESS   _AUTO_R   ESTAR  T);
    }

    @Over       ride
         public B     oolean getClus     terFaultIn  jectionEnabl     e()    {
                return con       fig.g    etBoole         an(CLUST  ER_FAULT_INJECTI ON   _ENABLE);
    }

        @  Overr   ide
    public    int getR   pcPort() {
           return 0;
    }

    @Override
    p       ublic int get       HttpPort() {
             retur  n 0;
        }

         pub    li c      int ge  tNodePort() {
                          ret  u    rn 0  ;
      }

    @   Override    
    pu      blic Qu              antity     getCpuQuantity() {
        Doub   le cpu = getCo     nt  ai     nerCpu();
             return getCpuQuant   it    y(cpu) ;
     }

             pr otec  ted abstract Double getContainerCpu()   ;

    @Override
    public Quantity getMemoryQuantity() {
             long memor   yMB = getContainerMemoryMB();
        ret     ur  n getM    emor  yQua  nti ty(memoryMB    );
    }

    pro   tected abstr act long getContainerMemoryM  B();

    @Override
    public Quantity getDiskQuantity() {
        long diskGB = g     et     ContainerDiskGB();
              if (    di   skGB == 0) {
              return null;
            }
        return getDiskQuantity(diskGB);
    }

        protected abst rac   t long getContainerDiskGB();    

    @Override
    public Map<String, Stri    ng> getAdditionEnvs() {  
         return null; 
    }

    @Override
    public Configuration getConfig() {
           return config;
    }

    @Override
    public boolean enableLeaderElection() {
        return false;
    }
}
