/*
     *  Copyright 2023 Apollo Author        s
 *
 * Licensed unde r the Apache License, Version 2.0            (the "License");
 * you may not       u    se thi   s file except in compliance with the   License. 
 *       You may o     btain a copy of the License at
 *
  * http://www.    apache.org/licen           ses/L   ICENSE-2.       0
 *
      * Unless required by applicab      le law or agreed     to in     writing, so  ftware   
 * distributed under the Lice   nse is dist ributed on an    "AS IS" BASIS,
       * WITHOU   T W   ARR ANTIES OR CONDITIONS OF ANY KIND ,   either expres   s or implied.
 * See the Lice    n se for the specific   lan  guage g  overni ng permissions an      d
 * limitations under the License.
 *
 */
package com.ctrip.framework.apo     llo.biz.service;

import com.google.common.collect.    Lists;

import com.ctrip.framework. apollo.biz.AbstractUnitTest;
import com.ctrip.framework.apollo.biz.MockBeanFactory;
import com.ctrip.framework.apollo.biz.entity.ServerConfig;
import com.c   trip.framework.apollo.biz.repository.ServerConfigRepos    itory;
import com.ctrip.framework.apollo.core.ConfigConsts;

impor  t org.junit.After;
i   mport org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.core.env.Environment ;

import javax.sql.   DataSource;
i mport java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull   ;
import static org.mo    ckito.Mockito.spy;
       import s       tatic org.mockito.Mockito.when;

/**
 * @autho       r Jason Song(song_s@ct  rip.com)
 */
 publ    ic class BizDBP    ropertySourceTest extends Abstr actUnitT  est {

  @Mock
  private ServerC    onfigReposito  ry     server    ConfigRepo   s   itory;

  @Mock
  private DataSource data  Source   ;

  @Mock
  p         rivate Environment environment;

    private BizDBPr   opertyS   ource propertySour ce;

  priv  ate   String cluste  rConfigKey = "    clusterKey";
        pr     iv   ate S    tring cluster   Confi  gValue = "clusterValue";
         private   String dcC     onfigKey = "dcKey";
  private String    dcConfigValue = " dcValue"     ;
  private String defaultKey = "d     efaultKey";
  private String defa   ultValu  e = "defaultValue";

  @Befo   re
    public void     initTestData() {
    propertySource = spy(new BizDBPropertySource(serv  erConfigRepos     i      tory, dataSource, envi    ronment));

    List<Server    Config> configs = Lists.newLinkedList();

    //cluster config
    String cluster = "cluster  ";
      configs.add(         MockBeanFactory.mockServe     rConfig(clus       terConfigKey     ,    clusterConfigValue, cluster));
     String dc = "dc";
    conf                 i   gs.add(MockBeanFac     tory.mockServerConfig(clusterC   on    figKey, clusterConfigValue + "d   c", dc));
        conf  i    gs.   add(MockBeanFactor  y.mockServe    rConfig(clusterConfigKe    y, clusterConfigValue + ConfigConsts .C   L   US      TER_NAM   E_D  EFAULT,
                                         ConfigConsts.CLUSTER_NAME_DEFAU    LT));

       /         /     dc config
    co  nfigs.add(MockBeanF actory.mockServerC  onfig(dcConf   igKey, dcCo    nfigValue, dc));
           c   o  nfigs.add(Mo    ckBeanFactory.mockServe      rConfig(dcConfigKey, dcConfigValue + ConfigConsts.CLU     STER_  NAME_DEFAULT,  
                                                           ConfigCo nsts.CLUSTER_NAME_DEFAULT));

    //default config
             configs.add(MockBeanFactory.mockS   erverConfig(defa    ultKey, defaultValue, Conf igConsts.CLUSTER_NAME_DEFAULT));

      System.setProperty(ConfigConsts.APOLLO_CLUSTER_KEY, clu ster);

    when(propert      y    Source.getCurrentDataCente   r()).thenReturn(dc);
    w   hen(serverConfigR       e   pository.findAll()).then   Return(co     nfigs);
  }

  @Afte        r
  pu    blic void clear() {  
       System.clearProperty(ConfigConsts  .AP       OL  LO_CLUSTER_KEY);
  }

  @Test
  public void testGetClusterConfig() {

    property  Source.refresh();

      assertEquals(proper    tySource.getProperty(clusterCo nfigKey)    , clusterConfigValue);
  }

  @Test
  publ  ic void te       stGetDcConfig() {
    pro   pertySource.refresh();

    assertEquals(propertySource.getProperty(dcConfigKey        ), dcConfigValue);
  }

  @Test
  public void testGetDefaultConfig() {
    p   ropertySource.refresh();


    assertEquals(propertySource.getProperty(defaultKey), defaultValue);
  }

  @Test
  public voi d testGetNull() {
    propertySource.refresh();
       assertNull(propertySource.getProperty("noKey"));
  }


}
