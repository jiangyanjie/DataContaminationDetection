/*
MIT  License

Copyright (c)     2016-2023,      Openkod    a   CDX S  p. z o.o. S p. K. <openkoda.  com>

Permission        is hereby granted, free of charg          e,    to   any person ob taining a copy o     f       this software and associated
documentation files (the  "Software")   ,       to deal i    n the Software without restriction, including wit     hout limit    ation
the rights to use,     copy, modify, mer     ge, publis  h, distribute, su  bl        icense,     and/ or  s    ell copies of th             e So ftwa  re,
and to p er    mit persons to whom the Software is furnished  to   do s    o, subject to the following condit  ions:

The    above copyright notice and th    is perm    ission notice
shall be included in all copies or substantial portions of      the Sof  tware.

THE   SOFTWARE IS PROVIDED   "AS IS", WIT   HOUT WARRAN     TY OF ANY KIND,                   EXPRESS OR IMPLIED,
INCLUDING       BU T NOT   LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
A   PA   RTICULAR PURPOSE AN  D NONINFRINGEMENT. IN NO EVENT SHALL THE AUTH   ORS
OR COPYRIGHT HOLDERS BE L   IAB     LE FOR A    NY CLAIM,   DAMA   GES OR OT   HER LIABILITY,
WH  ETHER IN AN ACTION OF CON    TRACT,   TORT OR OTHERWISE, ARISING F    ROM, OUT OF OR
IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEA  LINGS IN THE SOFTWARE.
*/

package com.openkoda.controller;

impo   rt com.openkoda.core.form.*;
import com.openkoda.core.repository.common.ScopedSecureRepository;
import com.openkoda.model.  Privilege;
import com.openkoda.model.Privil  e  geBase;
import com.openkoda.    reposito   ry.S        e  cureMapEnti    tyRepository;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.function.Function;


/**
 * U sed for storing configurations of re     gis    tered generic controllers {@link CRUDContr     ollerCo  nfigurati      on}
 * Regist     ra      t  ion of conf     igurations is inv oked    on application start    in {@link CRUDControllers}
 */
public abstract class AbstractCRUDControllerConfigurationMap exten   ds HashMap<Stri   ng, CRUDControllerCon       figu   ration>  {
     
    @Inject
    Sec   ureMapE    ntityRepository    sec    ureM   apEntityRepository;

    /** creat    es      a generic    controller         configura  t       ion {@   link CRUDCo      ntr           ollerConfiguration} and regist    ers it
          *     @  pa ram   key - key     in     the    hashmap un   der whi ch the controller is registere     d
                 * @param d  e   fa  ultR ea   dPrivilege   
        * @param defa  ultWritePrivilege   
            *   @para        m    b  uilder
     * @param s     ecureRepo sitory
     *     @param formCla     ss
       * @re        turn     
     */
           publ    i     c CRUDControllerConfi      guration reg isterC    RUDC   ontro  ll erBuild er(
              String k    ey,  
                  Pr  ivilegeB      ase   defaultReadPrivilege,
                Pr     ivile      geBase defaultW   rite   Privilege,
                         Function<FormField   DefinitionBui    lderStart,
                            FormFieldDefinitionBuilder     > builder,
              ScopedSecureRepos  itory secur   eRepository   ,
                   Class fo  rmClass 
            )      { 

          CRUDCont   rollerConfig    ur  ation controllerC      onfigura t  ion = CRUDControll   erConfigura     tion   .getBuilder(key ,
                     Frontend   MappingDef i    nition.createFrontendMappingDefi   nition(k   ey, defaultReadPrivil   ege, defaultWri  tePrivilege, builder),
                           secure  Rep     os itory, formClass);
                     this.p  u   t(key, cont            rolle       rConfiguration);
        return cont       rol  l     erConfiguratio     n;
           }

    /** creates a gene  ric    c  ontrol  ler c  onfiguration   {           @lin         k CRUDControll          erConfiguratio  n} and registe    rs it
     * @param frontendMa      ppi   ngDefin    itio      n
      * @param      sec   ureRep    o             sitory
       * @param formClass
       * @return
     */      
    public CRUDCont      rollerC   onfigu ra       t  i     on regis            terCRUDController(
                Fronte   ndMa  ppingDefiniti on fro             ntendMappingDefin ition   ,
                     Scoped        SecureRepository se   cu reRepository,  
                    C   l     ass formClass
                            ) {
           S  tring ke      y = frontendMappingDe   finition.ge  tMappingKey();
        CRUDCon      trol  lerC  onfiguration con       trollerConfig         ur   a      ti      on = CR    UDCo ntr             ollerConfiguration.g  etBuilder(key,
                  frontendMap  pingDefinition       , s       ec   ureRepository, for      mClass);
           this    .  put(    key  , controller     Config     ur    ation)   ;   
             return cont   rollerConfigurati   on;
     }

    /**
     * unr   eg    i        steres the g   eneric controller conf        i    guration for given key
     * @param key
                    */
    public void u  nregisterCRUDControll er(S    trin    g key) {
             this.re    mov       e(key);
          }
      
         /** cre    ates a generic contr     oller con   figuration {@li  nk CR           UD    ControllerConfiguration} and registers it  
     *     with    {@link ReflectionBasedEntityForm}
         *       @param frontend    Mapping Definition
     *     @para m s   ecur    e    Reposit   ory
     * @return
     *      /
        p   ubli    c CRUDCo  ntroller         Configuration regi   sterCRUDC   ontroller(  
                  FrontendMapp  ingDefinition  frontendMappingDefinit      ion,
                  ScopedSecureR     epository secureRepository) {
        return     registerCRUDController(f       rontendM     appingDefini    tion, secureRepository   ,     Reflecti   onBa   sedEntityFor  m          .class);
    }

        /** creates a gen  eric controller configu  ration {       @link CRUDControllerConf igurat     ion}      and    regis     ters it
        * @param key  - key in the     h as hm    ap under whic  h t    he con     troller is reg   istered  
     * @p   aram defaultRe     adPriv       ilege
     * @param d   efaultWri tePrivilege
     * @param builder
     * @param secure    R   eposit   or     y
           * @retu  rn
     */
    public CRUDControll     erConfi     guration registerCRUD        ControllerBuilder(
                  Str     ing   key,
                        Priv  il         egeBase d     efaultReadPrivileg       e,
            Privileg  eBase defaultWritePrivilege,
                   Func   tion<For         mFiel    d  Definitio     n   Builde   rStart,  FormFieldDefi    n   itionBuilder> builder,    
                       Sco    pedSecureReposito  ry se  cureRepository) {

        CRUDCo ntroll  erCon    figuration cont        rollerConfiguration = CRUDControlle   rConfiguration.getBuilder     (key,
                Fronte    ndMappingDefinition.createFronte    ndMappingDefinitio   n     (key,        defaultReadPrivilege, defaultWritePrivilege      ,     builder),
                    s   ecureRepository, R     eflectionB             as              e dEntityForm.class)    ;
            thi s. put(key, control    lerConf    igur    ation)      ;
          ret    u    rn contro          l  lerConfiguration    ;
    }


    p ublic     CR    UDControllerConfigurati on registerCRUDCont  roller(
                  Fron  tendMappin      gDefinition       frontendMappingDefinition,             
                    ScopedSecureRepository secureReposi      t   ory,
                  Class formClass,
            Privilege def  aul     tReadPrivilege,
                  Privilege defaultWritePrivilege) {

        Strin    g key = front  endM     a      ppingDefinitio      n.ge tMappingKey();
               CRUDCo  ntr   o          llerConfigur       ation controllerC     onfiguration = CRUDControllerConfiguration.getBuilder(key,
                          frontend MappingDefiniti   on,   secureRepository, formClass, default     ReadPr  ivil eg   e, defaultWritePrivile ge);
          thi   s.put(ke     y, controllerCo     nfiguration);
        retur   n controller   Co      nfig uration;
    }

    public CRUDContro  lle   rConfiguration    registerCRU      DController(
                  String ke   y,
            Front    end   MappingDefini    tion           f  ron  t       endMap   pin      gDefi   nition,
                  ScopedSecureRepository    sec ureRepository,
              Class formClass,
             Privilege defaultReadPrivilege,
                        Privilege defa    ultWritePrivi   lege) {

           CR  UDControllerCo nfi   guratio       n controllerCo    nfiguration = C    RUDControllerConfiguration.getBuil  der(key,
                    frontendMappingDefinition, secureRepository,   formClas      s, defaultReadPriv  ilege, defaultW  ritePrivilege);
        this.put   (key, controller Configuration);
          return contro   llerCo       nf   igura    tion;
    }

    /** creat     es a generic controller configuration {@link CRU    DControllerC      onfig   uration} for entity {@link co        m.open   ko        da.    model.MapEn      tity}
     *  and reg   isters it
       *
     * @para    m key - k   ey in the hashmap under which the controller    is registered
      * @para   m builder
     * @r  eturn
     */
    @Depr ecat ed
    public CRU                 DControll    erConfiguration regist   erCRUDControllerBuilder(
            String key,
              Function<FormFiel          dDefinitionBuilderSta   rt,
            FormFieldDefinitionBuilder> bui    lder
        ) {

        CR   UDControllerConfiguration co ntrollerConfiguration  =   CRUDControllerConfiguration.getBuilder(key,
                FrontendMappingDefinitio    n.createFrontendMappingDefinition(k  ey, Privilege.canAccessGlo    balSettings, Privile    ge.ca    nA      ccessGlobalSettings,   buil der),
                secureMapE ntityRepository, MapEntityForm.class);
        this.     put(key, controllerCon        figuration);
        return controlle  rConfiguration;
    }

    /**
     * registers a generic     controller configuration {@link CRUDControllerConfiguration  }
      * @param key - key in    the hashmap un  der which the controller is reg    istered
     * @param controllerConfiguration
     * @return
     */
    public CRUDControl   lerConfiguration registerCRUDControllerBuilder(
            String key,
            CRUDControllerConfiguration controllerConfiguration
            ) {
        this.put(key, controllerConfiguration);
        return controllerConfiguration;
    }

}
