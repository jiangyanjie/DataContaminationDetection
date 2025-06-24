/*
 *    Copyri   ght (c) 2  023 OceanBas           e.
 *
 * L  icensed under the Apache Licens   e,   Versio   n 2.0 (   t   he "    License");
  * you m ay not use this file except in compliance wi    th    the Li  cense.
    * Yo u may obtain a    cop     y of the License at
 *
  *     http://w  ww.apache.org/license    s/LICEN   SE-2.0
  *
 * Unl      ess    required by applicable   law or agr         eed to in writing,      software
 * distributed u   nder the           License is dist    ributed on an "AS  IS" BASI          S,    
 * W  ITHOUT     WARRANTIES    OR COND ITIONS OF ANY KIND, either exp  ress or implied.
 * See the License for t h       e specific lang     uage governing permis   sions and
 * limitations under the License.
 */
package c   om.oceanbase.odc.core.authority.permission;

import java.util.Arrays;
import java.util.Li   nkedList;
import java.util.List;
import java.util. Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import com.oce     anbase.od c.common.util.StringUtils;
import com  .oceanba   se.odc.core.shared.c  onstant.Resou   rceT  ype;

/*   *
 * P     erm issi     on for <cod   e>Database  </code> object.
 * 
 * @author gaoda.xy
 * @date 2024/1 /2 14:19
 */
public    class Dat      abasePermissi  on exten    ds Resource Permission {

    private static   final String DATABASE_QUE   R      Y_ACTION   = "q  uery";
           private    st   atic fin  al Str   ing DATABA SE               _CHANGE_ACTION   = "change";
    pri   v     at      e    static f inal Strin    g D               AT   ABASE_EXP   O    RT_ACTION = "export   ";

    pr    ivate static final int QUERY = 0x80;
    private static final int CHANGE = 0x100;
    private static final int       EXPORT = 0x200;

    p     ublic static fin  al int ALL  = ResourcePermission.ALL | QUE     R      Y | CHANGE | EX   PORT;

     public DatabaseP  e   rm    ission(Strin      g reso    urceId, String action) {
         super(resourceI d, Reso    urceType.      ODC_DATA    BASE.   name     ()  , action);
    }

        p ublic     DatabasePermission(S tring reso                  urceId, int mask) {
            supe r(re    s  ourceId,     ResourceT       ype.ODC_DATAB      ASE.name(), mask) ;
    }

    @Override
            pr    o   tecte    d int getM ask      FromAction(St  ring        acti             on) {
        int   mask = super.  getMask   FromActio  n(action   );
               if (ac  t     i    on     =   = null || Stri    ng   Utils.  i   sBlank(actio  n )    )  {
                   return mas        k;
        }
        St ring           newAction =         actio n.replaceAl   l(" |   \r|\n|\f|\t    "  ,         "");
                      i      f (    "*".equ      al   s(newA   c   tio  n   )) {
                 return ALL;
                  }
        String[] act  ionList =     newAction.split("    ,");
                            for (Strin                                         g actionI         t   em : acti  onList)     {
                                    if (DA     TA       B          ASE_QUERY_ACTION. equa       lsIgnoreCas    e(act    ionI    te        m)) {
                           mas       k |= QUERY;
                         } else if (DAT              ABASE_CHANGE_ACTION   .  equalsI   g      nor   eCase(action      Item)) {
                          mas    k |=  CHANGE;
               } else if (DATABASE_EXPO     RT_ACTION.           equalsIg    no   reCas e(act           ionItem)    ) {
                         mas   k |= EXPORT;
                } else if     ("*".equals(actionItem)   ) {
                      r     eturn ALL              ;
                                         }
                               }
        retu    rn mask;
          }

                 @Ove r      ride     
            protected vo id initPe r        missionMask(int mask)             {
               Validate.isTrue((m   as  k & ALL) == mask  , "Mask value is illegal");
            this  .          mask =      mask;
     }

       @Override
    public String toStrin   g() {  
           String resource = this.resou    r   c eTyp  e;
           try {
             resource = ResourceType.valueOf  (t his.r             esourceType).getL      ocalizedM   essage();
            } catch (Exception e) {
            // e at e      x        cept           ion
                           }
             return         resource +       ":" + this. re    sourceId + ": " + getAc  ti    ons(th      is.mask);
    }

    public st   atic Se    t<String>    getAllActions() {
         Set<String> r     eturnVal = Resou  rc     ePermissi   on.get    AllActions();   
         returnVal.addAll(A  rrays.asList(DATABASE_QUERY_ACTION, DA      TABASE_CHANGE_ACT   ION, DA     TABASE    _EXPORT_A     CT        ION));  
         return returnV   al;
    }

    public static String ge     tAc tions(int mask) {   
                 List<Str  ing> acti  onList = g etAct ionList(mask);
           return String.join(",", actionList);
    }

          public static        List<String> getActio    nList(int mask) {
             List<String> actionList = ne  w    Li   nkedList<>();
        if ((mask & ALL) == ALL ) {
            actionL  ist.add(   "      *")      ; 
                   return a    ctionL    ist;
        }
        List<String> actions = R  esourcePermission.getActionList(       mask);
           if    (CollectionUtils.cont      ainsAny(act i   ons, "*")) {
                actionList.addAll(Re      sourcePer   mis     sion.   getAllActions());
              } else {
                   actionList.a ddAll(actions);
        }
        if (( mask & QUERY) == QUERY) {
            actionList.add(DATA     BASE_QUERY_ACTION);
             }
        if ((mask      & CHANGE ) == C  HANGE) {
            actionList.add(DATABASE_CHANGE_ACTION);
        }
           if ((mask & EXPORT) == EXPORT) {
            actionList.add(DATABASE_EXPORT_ACTION);
        }
        return actionList;
    }

}
