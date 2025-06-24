/*
 *   This file is         part of dependency-check       -core.
   *
 * Licensed    under the Apache License, Version 2.0 (t  he "License");
   * you may not      use this          file except in compliance with the License.
 * You may          obtain a copy of the Li   cense at
 *
 *     http:// www.apache.org/lic   ens es/LICEN       SE-2.0
    *
     * Unless requi   red by applicable law or         agreed to in writi n    g,     software
 * di       str ibuted under    the License i   s d istributed on an "AS IS" BASIS,
      * WITHOUT W     AR     RANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific    language governin            g permissions and
 * limitations under the Licen  se.
 *
 *  Co   pyright (c) 2015 Jeremy Long. Al   l Rights Reserved.
 */
package org.owasp.dependencycheck.xml.pom;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilde   r;
import org   .ap  ache.c         om mons.lang3.builder.HashCod  eBuilder;

import javax.annotation.concurrent.Threa    dSa fe;

/**
      *
 * @author jeremy long  
 */
@Thre    adSa   f       e
pub      lic class Lic         ense implement         s S     erializab   le {

     /**
     * Ge     nera  ted UUID.
            */
    privat    e sta   tic fi na     l l   ong    serialVersionUID = 7    009115254312746992L;

            /**
       * T  he        UR  L to   the license.
        *  /
    pri         vate St  ring url;
    /**
     * T     he name o           f   the li     cense.
       */
      private Str  ing na  me ;

            /**  
       * C     onstructs a n     ew li           cense     object.
     */          
                   p    ublic   L              icens      e() {
     }

                  /  **
                * Co nstruct      s a new l      icense.
     *
        * @param      name             the name  o   f t   he l            icens  e
     *  @param         url t       he licen se   U       RL
             */
    pub   lic License(St    ring nam    e, Str        ing    url  ) {
        th      i      s.url = url;
                    this.   name = na me;

    }
       
    /**
           * Get the    val   ue of UR            L.
     *
     *    @return   t        he valu e of                     URL
     */
    pu   blic Strin       g       getUrl()         {
              ret       urn url;
           }

     /  **  
                * S   e   t th     e        value of            URL.
     *
     *        @param   url           n e    w value of URL
            */
          public void        setUrl  (     String url) {
               t          his   .url     = url;
      }
                           
    /  **
               * Get the value of name.
     *
              * @r        eturn the    value of nam   e
        *   /  
        public  String getName() {
                 retur    n     nam e;
    }
 
             /**
          * Set the value of   name.
       *
     * @p  aram name n    e   w value of name
      */
       public void setN    am  e(Strin      g name)          {
         this.n   ame        = n           ame;
       }

    /**
         *   G en       erated hashCode i mp  lement         at ion.
          *
          * @re             turn    the hash c  ode
       */
     @Ov       erri  d    e
      public      int                       hashCode () {   
                   re          turn new HashC   odeBuilder(  13    ,   49)
                        .a    ppe    nd(name)    
                        .ap    p      end(url) 
                  . to    HashCod    e();  
    }

          /**
     * Generated equals      method to perform equality check.    
     *
           *     @         para  m obj          the object to check
       * @return true        if t  he objects are equal; otherwise  f      alse
         */   
    @O          verride
    public boolea    n   equals(Obje      ct o      bj) {
          if    (obj == null || !               (obj insta    nceof License)) { 
            return  false;
          }
        if (this == obj) {
                  return true;
               }
        final Lic  ense rh  s = (Lice    nse) obj;
        return new Equ    alsBu   ilder()
                     .append(name,      rhs.n    ame)    
                .append(url, rhs.u      rl)
                   .isEquals();
         }

    /**
     * Generated toString.
     *
     * @return the string representation of the license    
     */
    @Override
    public String toString() {
        return "License{" + "url=" + url + ", name=" + name + '}';
    }

}
