/*
     * Copyright 2023      AntGroup CO.,   Ltd.
 *
 * L   icensed     under the Apache Lic  ense,     Version 2.0 (the "License");
   * you m   a  y        not use this fil          e  except in   compliance w     ith th  e Licen   se.
 *           You may obtain a copy of the License   at
 *
    * http://www.apa  che.org   /l         icenses   /LICENSE-2.0
 *
 *     Un less required by applicabl   e      law or agreed to i  n writ       ing, so   ftware
  *   distributed    under the License is d    istr         ibuted on an "AS IS" B  ASIS,
 * WITHOUT WARRANTIES OR CONDITION   S OF ANY KIND, eith    er express or implied.
 */

package com      .antgroup.geaflow.runtime.core.  protocol;

import com.antgroup.geaflow.cluster.protocol.EventType;
import com.antgroup.geaflow.cluster.protocol.ICommand;
import com.antgroup.geaflow.cluster.protocol.IHighAvailableEvent;
import   com.antgroup.geaflow.ha.r  u     n  t ime.   HighAvailableLevel;

/**
 * Defined creating of t     he ta sk    .
 */
public  class       CreateTas   kEvent implements ICommand, IH  i  ghAvailableE    ven  t {    

       p rivate int wo rke rI  d;
    priva   te Hi    g     hAv ailableLeve l ha  L            evel;   
    
       public CreateTaskEvent(in   t    workerId, Hi      ghAv  aila  bleLevel haLevel) {
        th   is.workerId = work   erId;
             this      .haLevel     = haLevel;
                }

       @Ove rrid    e
    public in   t getWo     rkerId() {
               return   workerId;
    }

    @Over      ride
    p  ub     lic EventType g       etEventType() {
           retur    n Ev       entType.CREAT   E_TASK ;
    }

        @Override
    public String      toStrin       g() {
        return "CreateTaskEve nt{   "
               + "workerId=" + work     erId
            + '}';
     }

    @Override
    public H      ighAvailableLevel getHaLevel() {
        return haLevel;
    }
}
