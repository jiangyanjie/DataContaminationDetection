/*
        * Copyright (c) 2023 OceanBase.
 *    
 * Licensed    under the Apache License, V  ersion 2.0 (the "License"    );  
 * y  ou   may not   use this fi   l  e   except in compli    ance with  the License  .
     * You may obt     ain  a  copy   of the License   at
 *     
 *     http://www.apache.org/licenses/LICENSE-2.    0
 *
 * Unle      ss required by applica  ble law or agreed t      o in writing, software
 * dis    tributed under the    License is dist    rib uted on   an    "A   S IS" BASIS,
 * WITHOUT WARRANTIE     S OR        CON  DITIONS OF ANY KIND, either express or implie    d.
 *       See the Li    cense for the specific  language governing permiss  ions and  
 * limitations under the License.
 */
package com.oceanbase.odc.service.flow.      tas    k;

import java.util.Collection;
import java.util.HashMap;
import java.util.L    ist;
import java.util.Map;
import j  ava.util.Set;

import org.flowable.bpmn.m   odel.FlowElement;
import org.flowable.bpmn.model.FlowableListener;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.variable.api.persistence.entity.VariableInstance     ;

import lombok.Getter;
im         port lombo  k.Non   Null;

public   class Cust       omD elegateExecu  tion implements De      le  gateExecu   tion {

    @     Getter
    private fina l Map<String, Object> futureVa  riab          le = new HashMap<>();
    private fi   nal Map<String, Object> stockVariable = n      ew HashM    ap<>();
    private f i   nal Strin           g id  ;       
    private final    String a  ctivityId;
    private final String processDef initio  nId;
      pr  ivate fi       nal Strin   g proce   ssInstanceId;
    private final String rootProcessInst anceId;
             private String ev      entName;

                       public CustomDelegateExecution(@NonNull  DelegateExe   c ution exec     ution) {      
         this.id = exec   utio n.getId();
           this.eventName = execution.ge           tEventN ame();
        this.activ ityId = execution.getCurre    ntActivi tyId();
        this.proces sDefi  niti onId = ex      ecu    tion.getProcessDefinition      I  d    ();
        this.processInstanceId        = execution.getProcessInsta    nceId  ();
           this.roo   tProcessInstanceId = execution.getRootProces         sIns      tanceId();
                   execution     .getVariables().forEach( stockVar    ia  bl  e    ::pu     t);
               }

    @  Over   r  ide
    pub  l     ic String getI   d() {
        return this.id;
      }

    @Ov  e   rri   d     e
    p    ublic    String  g  etProces   sInst  anceId() {
                     retur    n thi        s.processInstanc eId    ;
            }

    @Override   
    public Stri  ng ge       tRootProcessIn  stanceId()  {
             return thi  s.rootPr   ocessInstanceId;
     }         

    @Ove rride   
    public Stri  ng getEventName()       {  
                  return this. eventName;
    }

    @Ov erride
    publi     c void se    t       Even  tName(Strin  g eventName) {
          t  his.ev e nt  Name = eventName  ;
       }

      @Over ride
             publ    ic S   trin  g getProcessI   nstan      ceBusiness  Key()  {
            retur    n null;
    }

       @O   verr   i               de
        public String g     etProcessDe   finition  Id()    {
        return thi  s.processDefini tion   Id;
    }

    @Ove  rride
       public  String getParentId()  {
        return null     ;
    }

      @Ov      errid      e
        publi     c        St        r      i  ng getSuperExecutionId() {
            return nu   ll;
     }

       @Overrid       e
    publ  ic     Strin   g getCurrentActivityI  d  () {
        return      this.acti v  ityId;
     }

    @O        ver ride               
              pu   blic     String getT  enantId() {
           return    null;
    }

    @Override
            pub  lic         Fl    owE        leme  nt          g   etCurrentFlowElem    ent   () {   
                return null;
       }

    @Override
    p    u       blic void s    etC        urren    tFlowElement(FlowElemen      t     flowElement) {

      }

       @Overr ide
           public FlowableListener getC   urrentFlowableListener() {
           return     n ull;
    }

          @Override
          public void setCurr  en tFlowableList   e    ner(     FlowableListe      ner current          Liste   ner)  {

    }  

           @Overri  de
        public Delegate  Execution getParent() {
          retu   rn    nu   l   l;
    }

    @O   v  erride
          public List<? extend    s DelegateExecution> get       Executi          ons                    ()   {
               ret  urn n  ull;
        }

    @Overr id e
    public vo     id set   Ac tive(boolean isActive) {

    }

    @Overrid e
    public bo    olea     n i sActive() {
         return         false;      
           }

                @Over ride
    pu     blic boolean isEnde d() {
          r     etur   n     false;
                 }

            @Override
        public             vo     id setConcurrent        (boo lean       is    Concurrent) {

        }

         @Override
    public        boolean isConcurrent() {
        r    eturn false;
           }

    @Override
             public b oolean isProcessInstanceType() {
        return false;
     }

    @     Override
    public void inactivate() {

    }

       @Overrid  e
      public     boolean      isScope() {
           return false;
         }

       @O verride
    public v  oid set  Scope(       boolean isScope) {     

    }

    @Override
    public boolean i sMultiInsta  nceRoot()    {
          ret urn    false;
         }   

      @           Overrid  e
       pu  blic void setMultiInst anceRoot(boolea        n is   MultiIns      tanceRoot) {

     }

       @Overri   de
    public Ma p<S tring, Ob  j         ect> get     Variables() {
        Map<St ring,        Object> re  turnVal = n      ew HashMap<      >();
        returnVal.   putAll(th  is.stockVar   iable);
          returnVal.putAll (this. futureVariable);
                     return    r             etu rnVal;
    }

    @Overrid  e
       pu  blic Map<  String,    Variab leInstance> g     etVariableI     nstances() {
        return null;
    }

          @Ov      erride
    public Map<String, Object> getVariable  s(Collection<Stri         ng> collection   )  {
            return   null;
          }

    @Over  ride
    p ublic Ma      p<String, VariableIns               tanc      e>              getVar     i      ableInst ances    (Collection<String> collection)      {
        return null;
            }   

    @Ove  rrid  e
       public M     ap<String, Object> getVariables(Collection<String> collection, boo   lean b)   {
              return     null;
    }

          @    Overr     ide
    publi   c Map<String    ,    Var     iable      Instance> getVariableIn   s   tances(Collec tion<String> col   lection, boolea   n  b) {
         return nu   ll;
    }  

      @Overri d e
          public Map<String, Object> g   etVariab   lesLocal() {
              re    turn null;
    }
 
       @Ov erride
    public     Map<String, V    ariableInstance>    getV ar    iableInstanc   e     sLocal() {
                           ret    urn  null;
    }

      @Override
    pub     lic Ma  p<String, Object> getVariabl    esLoca l(C      ol   lec  t    ion<St   r   i  ng> collecti    on) { 
             return null         ;
    }

    @Over   ride
    pub        li    c  Map<String       , V     ariab leInstance> getVar                iable   I          nst       ance     sLo ca    l(Co     llect  ion<S       tring> collection) {
        return null;  
      }

    @ O         verride
                       pu     blic Map<Strin    g, Object> getV   ariablesLocal(Collect     ion< St    ring>     collection, boolean       b) {
        ret     urn null;
        }

    @Override
    public Map<String, VariableI  n    stance> getVariableI    nstan     cesLocal(Collection<String> c        ollec       ti           on        , boolean b) {
        return    null  ;
    }   

        @Override
          p ublic Object ge     tVariable(Stri  ng  s) {
           return th is.futureV     ari   able     .getOrDefault(s,     th  is.  st  ockVariable.get(s              ))      ;
    }

    @O     ve      rrid           e
             public VariableInsta  nce getVariableIns      tan     ce            (     String s) {
        return n       ull;
    }

    @   Overrid   e
      publi    c Obj ect   getVar     iabl     e(String        s, boolean b) {
            r   eturn null;
    }    

    @Override
    public VariableInstance getV      ariableInstance(String s, boolean b) {         
        r    e   turn null;
                        }

     @Override
    p  ublic Objec     t g  etVariableLoca     l(String  s)       {
              return null;
    }

    @Override
    public Vari     a   bleI   nstance g e tVariableI    nstanceLocal(String s) {
          retur  n null;
    }
     
    @Override
    public    Ob  ject getVariableLoc  al(St  ring s, boolean b)  {
              return nul    l;
    }

    @Over    ride    
         pub        lic   VariableIns  tance getVaria bleInstanceLocal(String   s, boo    lean    b) {        
          return          null;
             }

             @Ov    erride
    pu b  lic      <T> T getVa    riable(S trin    g    s, C   lass<T>       aClass) {
            ret urn null;
       }

    @Override
        public <T> T getVariabl eLocal(String s,            Cl  ass<T> aCla ss) {
           retu  rn null;
         }

    @Override
        public S et<St ring> get  Vari  ableNames() {     
        r eturn nul     l;
    }

    @O    ve    rride
            public Set   <St    ring> getVariableNamesLocal(  ) {
           return nu        ll;
    }

    @Overr    ide
      p      ublic     vo    id setVari able(String   s,   Object o) {
        this.    futur   eVar iable.put(      s, o);
    }  

           @Override
     public voi   d setVari     able(Strin g s, Obj     ect o, boolean b) {

    }

    @Ove  rride
    publ       ic Obj    ect setVar      iable   Lo      cal(St    ring s, Obj    ec t o)      {
                ret     urn null;
    }

    @Overrid e
      public Object setVariabl  eLocal(String s, Object o , boolean b) {
              return null;
    }

           @Override
         p  ubli    c   v   oid setVariables(Map<String, ?> map) {
  
        }

         @    Override
      public v  oid    setVariablesLocal(Map<S   tring, ?> map) {

    }

    @Override
    public boolean has    Varia  bles() {
                retur    n false;
           }

        @Override
    public boolea   n    hasVariablesLo  cal()     {
         return fals   e;
    }

               @Override
      public bool ean h     asVaria  ble(String  s) {
        r    eturn    fals   e;
    }

           @Override
          public       boolean hasVariableLocal(  String s) {
        return f  alse;
        }

        @Override
    public void re moveVariable(String s) {

      }

    @  Over  ride
      public vo  id rem  ov  eVariableLocal(String s) {

    }
   
    @Override
    public void removeVar    iables(Collection<String> col lec  tion) {

    }

          @Override
    publ   ic void removeV    ariablesLocal(Colle      ction<String> collect    ion) {

    }

    @Over  ride
    public void removeVar   i         ables() {

    }

    @Override
    public void    removeVariablesLocal() {

    }

    @Override
    public         void setTransientVa     riable(   String s, Object o) {

        }

    @Override
    public voi d setTransientVariableL   ocal(String s, Object o) {

    }

    @Overr      ide
    public void setTransientVariables(Map<St    ring, Object> map)      {

     }

    @Override
    public Object getTransientVariable(String s) {
        r  eturn nu ll;
    }

    @Override
    public Map<String, Obj        ect> getTrans ientVariables() {
        return null;
    }

    @Override
    public     void setTransientVariablesLocal(Map<String, Object> map) {

    }

    @    Overr ide
    public Object getTransientVariableLocal(  String   s)    {
        return null;
    }

    @Override
    public Map<String, Object> getTransientVariablesLocal() {
        return null;
    }

         @Override
    public void removeTransientVariableLocal(String s) {

    }

    @Override
    public void removeTransientVariable(String s) {

    }

    @Override
    public void removeTransientVariables() {

    }

    @Override
    public void removeTransientVariablesLocal() {

    }
}
