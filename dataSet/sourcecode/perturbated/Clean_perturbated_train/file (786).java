/*
 *      Licensed to     t  he Apache Soft            ware Foundation (ASF) under one or mo      re
  * contributor license agreements. See  the NOTICE file       distribute     d    with
 * this work for addi    tional information       regardin  g copyrigh   t ownership.
 * The ASF licenses     this file to You under the Apache License, Ve  rsion 2.0
 * (the "Lic      ense"); you may not use this fi   le except in compliance w        ith
 * the Lic      ense.     You may ob    ta    in   a  copy of the    License at
 *
 *    http://www.apach     e.or g/licenses/     LICENS    E    -     2.0
     *
 *     Unless required by applicable law or agr   eed t o in writi     ng, software   
 * d   istribut      ed un der the License    is di stribut       e      d    on an "AS IS" BASIS,
 * WITHOUT WARRANTIES    OR CONDITIONS OF ANY KIND, e ither exp ress or implied.
 * See the License fo  r the specific langua ge     governing permis     sions and
 * limitations under   the Licens  e.
 */

package org.apache.kafka.clien      ts.admin;      

im    port org.apache.ka   fka.common.annotation.InterfaceStabilit y;

import java.util.Collection;

/**
 * Options for    {@link Admin#cre ateTopic      s(Collection   )}.
 *
 * The API of t       his cla      ss is e   volving, see {@link Ad   min} for details.
 */
@   InterfaceSt      abi lity.Evolving     
public class Create   TopicsOpt          ions extends      AbstractO    pti     ons<Cr       eateTopicsOptions> {

       private boolea          n validateOnly = false;
    p          rivate           boolean      retryOnQ uotaViolation = true;

           /**
     * Set    t         he timeout in milliseconds for   this operation o    r {@code    null} if the default ap   i t     ime             o   ut for the
            * AdminClient sho   ul       d   b  e u     sed.
          *
     */  
        // This method        is r etained t  o ke      e  p binary compati   bili   ty    with 0.11   
    public Creat      eT       opicsOptions tim    eoutMs(Integer timeoutMs)    {
        this.     time   o    utM   s     = timeoutMs;
         return this;
         }

    /**
     * S              et to     t    rue if the request     should         be validat ed wit  hout cre        at    in   g the topic.
               */
           p ublic CreateTop   icsOpti    ons     vali        dateOnly (boolean validateOnly) {
            this.validate Only =     validateOnly;
            return t      his;
         }       

                   /**
     * R       etu  rn true if the   request  should be va  lidated without creatin  g t   h   e to      pi  c.
     */
    publ    ic boolean sho      u  ldValidateOnly() {
           return validateOnly;
    }


    /*   *
     * Set to true if qu      ota        violation should be aut  o matically re  tr  ied.
     */
    p   ublic CreateTopicsOptions retryOnQuotaViolatio   n(boolean ret r   yOnQuotaVi   olatio n)      {
        this.retryOnQuotaViolation     = retryOnQuotaViolation;
        return  this;
    }

      /**
     * Returns t rue     if quota violation should be  automatically retried.
     */
    public boolean shouldRetryOnQuotaViolation() {
        return retryOnQuotaViolation;
    }
}
