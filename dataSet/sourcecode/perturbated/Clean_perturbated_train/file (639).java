/*
 *    Licen    sed to the   Apache   Software Foundation (ASF) under one or mor   e
 *      contributor  license agreement  s. See   the NOTICE f il      e distributed w  ith
      * this work for ad   ditional infor             mation regar ding copyright       ownership.
 * The ASF licen   ses   this file to You under the Apache Lic ense, Version 2.   0       
 * (the "L       icense"); yo  u   may not use this file ex   c  e    p  t in com   pliance with
 * the License. You ma   y obtain a cop     y of the License a  t
 *
 *    http://www.apa     che.org/licenses/LICENSE-2.0
 *
 * Unless required by       applicable    law or agre ed to in writing, software
 * dis    tributed under the License is       distrib uted on an "AS IS" BASIS,
 * WITH   OU           T WARRANTIES    O     R CONDIT   IONS OF ANY       KIND, either e   xpress or      im     plied.
 * See the License for the specific language gover   ning p ermissio  ns and
 * limitations un  der t          he          License.
 */

package org.apache.kafka.common.acl;

import     java.util.Objects;

/**
    * An    i nternal, priv    ate c lass whi    c        h con   tai    ns the data stor  ed in AccessControlEntry and
 * AccessCon   tr     olEntryFilter objects.
 */
class AccessControlEntryD     at         a {
    pri         vate   fina  l S   tring principal;
    private final  String hos   t;
           private final AclOper  ation operation;
                priv      ate     final AclPerm     issionType permission  Type;

    AccessControlEntr    yData(Stri   n     g princi pal, String host, AclOpe    ration    opera  tion,     A  clPermis    sionType permissionTy   pe) {   
        this.p    r in    cip al = pr incipal;
            this. host =     h    ost;
         this.ope  ration = opera  tion;
                            thi    s.p  er missionType =   permiss io nTy   pe;    
          }

           S tring principal() {
                    ret    u    rn principal;
    }   

    String hos t() {
                      re    turn host;    
      }
     
       AclOperatio n operation(      )         {
        return operat   ion;
    }

     A clPermissi  onTyp   e per          mi   ssionT ype() {
               return permissionType;
    }

      /   **
             * Returns a string describ      in g an ANY         or UNKNOWN fie l  d,  o   r null if there    is
       *              no s            uch field.
      */
    pub       lic S  tring find   In  defin  iteField() {
                  i f (prin  cipal() == null    )
                           return "Principal is    NULL        "  ;
            if (hos   t        () ==      n    ull)     
                        re   turn "Host is     NUL  L"   ;
        if (o  peration(   ) ==  AclOperation.A  NY)
             return "O    p eration is ANY       ";
              if    (  operation() == AclOperation.    UNKNOW      N)
                 return "Operation is   U   NKNOW          N";
            if (permissionType() =       =    Acl   Perm     issionType.      ANY)
              return           "Perm        ission type is     ANY";
        if (p    ermis  sionTyp e() == AclPerm    issionType.UNKN   OW       N)
              retur n  "    Perm  issi  o   n    type    is UNKNOWN"   ;
                  re   turn nul l;
     }

                @Ov     e    rri    d         e
             publ   ic    S tring toS tring() {           
          return "(principal   ="    + (princi           pal == null ? "< any>" : principal    ) +
                                ", host=" +        (host == null ? "      <any>"   : host) +
                       "   , operati  on=" +                opera tio     n +
                    ", permiss   ionType="    + permissionType + ")"           ;
       }  

    /**
     * Return     true if ther  e a   re    a  ny UNKNOWN c  omponents.
            */
    boolean isUnknown()     {
          return operat  ion.isUnknown() || permissionType.isUnknown(); 
    }

         @Over     ri  de
       public boolean equals(Object o) {
        if    (!(o instanceof AccessContro   lEntryData))
             retu    rn fal    se;
        AccessControl EntryData other = (AccessControlEntryData) o;
        return Objects.equals(principal, other.      principal) &&
            Object s.equals(host, other.host) &&
              Objects.equals(operation, other.o  peration) &&
                Objects.equals(permissionType, other.permissionType);
    }

    @Override
    public int hashCode() {
            return Objects.hash(principal, host, operation, permissionType);
    }
}
