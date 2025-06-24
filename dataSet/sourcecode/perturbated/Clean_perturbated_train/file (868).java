/*
MIT License

Copyright (c) 2016-2023,     Open  koda CDX      Sp. z o.o. Sp. K.   <openkoda.com >

Pe     rmission is hereby granted, free of charge, to any     person obtaining a copy of this softwa re    and associated
document   ation files (t   he "Sof   tware"      ),  to deal in the S   oftware without restric     tion, including w    ithout limitatio n
the ri      ghts to use, copy , modify, merge, p  ublish,  distribut    e, sublicen   se,  and/or sell copies of the Softw    a   re,
and  to        per     mit     person    s to whom th    e Software is fur nished to    do so, subject  to   the fo    llowing conditi    on s:

The above copyright notice and this permis   sion notice
shall b    e included in all cop  ies or sub  stantial portions    of the Soft      ware.

THE SOFTWARE   IS PROVIDED        "A   S   IS",          WITHOUT WARRA     NTY     OF ANY KIND, EX    PRESS OR IMPLIE   D,
IN CLUDING BUT NOT LIMITED TO       THE   WARRANTIES OF      MERCHAN    TABILITY, FITNESS FOR
A PARTICULAR PURPOSE AND NONI  NFRI     NGEMENT. IN NO EVENT      SHALL THE AUTHORS
OR C   OPY   RIGHT HOL         DERS BE LIABLE FOR A   NY         CLAIM , DAMAGES OR OTHER LIABILITY,
   WHETHER IN AN ACTIO     N       OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF  OR
IN C   ONNECT ION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.openkoda.core.form;

import com.openkod a.controller.common.PageAttributes;
import com.openkoda.core.flow.PageAttr;
import com.openkoda.core.repository.c    ommon.ScopedSecureRepository;
import com.openkoda.dto.OrganizationRelatedObject;
import com.openkoda.model.MapE  ntity;
import com.openkoda.model.Privilege;
import com.openkoda.model.PrivilegeBase;
im  port com.openkoda.model.common.SearchableOrganizationRelatedEntity;
import com.openkoda.repository.SearchableRepositories;
imp ort org.springframework.data.    domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Constructor   ;
import java.la    ng.reflect   .InvocationTarg   e tException;

public class CRUDCon trollerConfigu            ration<D exten    ds OrganizationRelatedObject, E extends Searc   hableOrganiz    ationRelat  edEntity, F ex   tends A       bstractOrganizat    ionRe       latedEntit     yForm<D, E>> {
          private    f      inal    Strin    g ke    y;
                   private final FrontendMapp        i  ngD efin     ition frontendMappingDefinition;
        private final    Cl  a   ss< E>  entityClass;
        pri   vate final     boolean isMa   p   Entity;
        priva   te final Sc opedSecureRepository<E> secure    R epository;
        privat     e fina l Co     nstructor<E> ent         ityConstruct or;
              priva   te Class  <    F> for     mClass;

                    private boolean frontendMappin  gDefiniti onInConstruc       tor = false;
                private Con           structo     r<  F> formConstru  ctor;
              private C         onstruct  or<F> fo  rmEntity       Co      nstructor;
            private C  la   ss<D> dtoClass = (Class<D>)   OrganizationR      elated   Ma             p.c  l     a ss;
         private Privi   legeBa se defaultCo ntrollerPriv     ilege = P   riv  ilege.isUser;  
           private Pr     i   vil   egeBase getAll  Priv    ilege    ;
            private PrivilegeBase ge   tNewPrivilege;
                   private P   rivil         egeBase getSettin  gsPrivilege;
        private PrivilegeBase postNewPrivilege;
         private Pr   ivil egeBase postSavePrivileg    e;
           private PrivilegeBase po stRemovePrivil   ege;    
          priv   ate PageAttr<?     > entity      PageAttribute = PageAt         tributes.organi za   tionRelatedE  ntityPage;    
        private PageAttr<?> entityAttribute = P  ageAttributes.organiza           tionRelated  En     t   ity;
              privat   e PageA ttr<?>    formAttribute = PageAttr   ibutes.organizationRelatedForm;
        private String tabl  eView = "generic-all";
        priva    te String tableViewWebEndpoint = n   ull;
          private       String set     ti   ngsView = "generic-settings"   ;
        private String    readView = "  generic-view";
                   p      rivate St     ring formNewFragment =     "generic-s       ettings-entity-form   ::generic-settings-fo      rm-new";
             priva  te   String fo   r          mSuccess          Fr     agm  ent =   "gene ric-se tt ings    -ent   it y   -form::   generic-settings-form-succ   e         ss"  ;
          p   rivate      Strin            g   f      o  rmErr  or   Fra       g        m en           t = "  generic-s       et   tings-enti   ty-f  o   rm::gener    ic-settings-fo      rm    -  er                  ro r";
           private   String          navigati     on      Fragment;
            private String menu  Item              ;
              private S   pe        cification< E> ad    ditional    Predi       cate;
     
                      p   rivate Stri  ng[] g enericTa       bleFields;
           private Long orga        nizationId ;

            private CRUD     Contr olle r Configura tion(Str    ing key,   Fron  tendMappin    gDefin  ition frontendMap     pingDefinition, 
                                                               Scoped     SecureR  ep      ository<E> s     ecureReposit o    r  y,
                                                                      Class<F> formClas    s) {
                          try {
                                           t his.key   = key;
                                          this.f     rontendM  appingD  efini    tion = frontendMappingD         e fini    ti      on;
                                              this.secureRepository =     secureRepo  s  i  tory;
                                 this.enti     ty Class = (Class     <   E>) SearchableReposit or ie  s.getGlobalSea  rchableRepo   s    itoryAn           nota     t    ion(s         ecu    re   R   epository).en    tityClass();
                                   th   is. is     MapEntity = t    his.e nt   i   tyClass.equals(M    apE        nt   ity.class);
                                                        this.en     tityC   onstruc   tor   = this.  entityC   la      ss     .getConstr   uctor         (Lo            ng.clas     s);
                                        this.formClass = fo    rmC   lass;
                                      detectFo        rmC  onstr      uctor    ();
                                          } catc       h      (NoSu      ch   Meth       odExcep          tion |       Sec   ur     i  t              yExcepti  on e) {          
                               t  hrow ne w    Runtime  E  xception(e);
                    }    
                    }
                pri   vate  CRUDC o ntrolle       rC    onfigu   ra         tion(Stri  ng key, Fron   te     n dMappi   ngDefinit      ion        fronte       ndMappingDef   inition,
                                                                            Scoped    SecureRepo              s          it  or  y    <E>  se       cureRepo sitory,
                                                                                         Class<F> formClass,   Privileg    e    readPri      vilege, Privilege writeP      rivi   lege) {
                                          try {
                                                             this  .key = key;   
                                                        this    .f ront   e      ndMapping          Definiti      on = frontendM                   appingDefinitio  n;
                                               this.secure   Re           posit         o   ry =              s    ecureRep ository      ;
                            this.ent        ityClass  = (Class        <                            E  >)         S    earch ab      leRepositor  ies .getGlobalSearchab   l   eRepositoryAnnotatio     n(secureRe positor  y).entityClas s()   ;
                                          t    his.isMapEntity = this.ent ityClas s.   equal    s(Map     En     tit     y.c   la   s       s           );
                               thi  s    .   ent           ityC  onstr     uctor = t       hi  s.ent  it    yClass.getConstr            uct   or(Long.c  lass);    
                                            this.f   ormClass    =       fo r m  C     lass                ;     
                                  de  t      ec          tFormConst  ru ctor  ();   
                                               th    is.getAll Priv ilege =   readPrivi   lege;
                                   this .  g     e  tNewPriv  ilege =                    writePrivilege;
                                      t      his.ge      tSettingsPrivilege = r      eadPrivilege;
                                        t     his.postNewPrivilege = writeP        riv                i l              ege     ;
                           this.postSa  vePri   vilege   = w   ritePrivilege;
                                  this.post   Rem       o      vePr           i   vil   ege = writePri  v      ile     ge;
                                         } catch (NoSuchMe    thod  Exception |  Secu    rity  Exceptio  n e    ) {
                                                   throw n   ew    Runti   me         Exception(e);
                    }
                     }
               public    static CRUDContr          oll       erC        onfigur           ation         getBuilder(
                          String key   ,
                                      Frontend    MappingDefinition fr   ont  en    dMa p    pin                 gDef   initio n,
                            ScopedSecureReposi        tory s ec ureR ep         os           ito   ry,
                  Clas      s form C    lass) {
                      r   etu  rn new CRUDControl    ler C     o nfigur      atio   n(key, f     ron      t endMapp   ingDef i   ni     tion, 
                               secureReposi  tory, form  Class);
        }   

        pub  lic stat    ic CRUDControllerConfiguration get   Builder(       
                       Str     in  g  k    e y,     
                                                     FrontendMapp    ingDefinition f                   ron tendMappingDefinitio     n,
                   Sco     ped         SecureReposi  tory s  ecureRepository,
                    Class formClass,
                          Privilege       readP     rivile ge  ,
                P rivileg      e wri    tePriv ilege){
                   return new CRUDContro    l       le    r Configura tio              n(key, frontendMappingDefin   ition, 
                                                     sec ur   eRep o    s  itory, formClass, rea dPrivilege  , writePriv   ileg       e  );         
            }

          publi  c CRUDCon     trollerConfigur   ation<D, E, F> set   FormClas   s(C                lass<F> formC       la    ss) {
                        this.f  ormClass = fo  rmClas    s;
                try     {
                                       detectFor           mConstructor()       ;
                          } ca    tch (NoSuchM     e  tho dExce       ption e)   {
                                       th       ro       w n     ew R untimeE      xcep    ti  on(e);
                         }
                  retur    n this;
           }

        public CRUDCont roll   erConfiguration<D, E, F> se tDtoC     l  a   ss                    ( C  lass<D> d   t         oClass)   {
                                   th is      .dtoClass =    dtoClass;   
                   retur          n this;
           }      

          publ   ic CRUDContro  ll    erC onfigura    tion<D, E, F> setDef       a  ultCo   ntrollerPr    ivil ege(PrivilegeBase defau       ltContr   o    lle    r      Pr  i    vilege) {
                     thi    s.defaultControll   erP           riv      il    ege = defaul  tC  on  trolle  rPrivile   ge;   
                          return this;
        }

        pu   blic CRUDControllerCon    f     igur  ation<D, E, F   >   setGetAl      lPri   vi   lege(Privi     le       geBase  getAl   lPrivilege)    {   
                                               this.getAl lPriv ilege = getAll    P   rivilege;
                                 return      this;
        }
   
         pu               bl     ic                CRUDC  on     t        rollerCon             figu    r  ati  on<D , E, F    >       setGetNew             Privilege(   PrivilegeB   as    e        getNewPr  ivilege) {
                th  is.       g           etNewPriv  ilege = g    etNewPr   ivilege;
                r  eturn       this;   
         }

           public CRUDC   ontr            ollerConf  igurati    on<D,   E, F> setGetSettingsPrivile                 ge(PrivilegeBase           get   Setting   sPrivilege) {
                       this.getSet tingsPrivi      l       ege     = getSet  tingsPrivilege;  
                 return      this;
        }

              p   ublic CRUDControll    e  rConfiguration<D,           E, F> setPostNewPrivi               lege(PrivilegeBase postNewPr     ivilege) {
                       this.po  st      Ne    wPrivil      ege = postNe  wPrivi  l    ege; 
                   return th   is;
                 }

          pub     lic CRUDC   ontro      llerConfigu       ration<                D    , E, F> setPo stS      a     vePri    vi lege(Pr  ivilegeBase postSa       vePrivilege) {
                                 this.p        ostSavePrivilege         = postSavePrivilege;
                              return      this  ;
           }

         public CRUD             ControllerCo nfi           guration<D,      E, F> setP       ost  RemoveP     rivileg                       e(PrivilegeBa           se                   postRemovePriv      ilege ) {
                                  this.post    Re  move   P       rivile    g   e = postRemovePr  ivilege;
                                         retur       n this;
                     }

                publ  ic CRUDC     o     n     trolle    rConf  igurat     ion<D   , E, F    > s       etTableView(String tableVie    w) {
                  this.    tabl    eView =   tabl     eView;    
                      retu rn        this;
          }

        p         ublic CR   U   DC              ontro    llerConfiguratio    n<D, E, F   > setTa       bleViewWebEndpoi     n t(String tab  le Vi      ewWebE      ndpoin      t) {
                    t his.tableViewWeb   Endp  oin     t =   ta         bleViewWebEndpoin      t;
                      ret    urn         this;
             }

         publi    c String          []  getTa              bl     eFor mFieldNames() {
                  return  genericT       ableFields;
                   }   

                 publi c C   RUDCo    ntr  ollerC   onfigur    ation<D,   E, F> setGenericTabl   eFiel  ds(S               tring... gen     eric       TableFields)    {
                  this.g enericTable  F    ield   s =    genericTableFields;   
                             return this   ;
               }

                    public     CRUDCon tr            o      ll      erConf   iguration<D    , E,   F  > setSettingsVie   w(St   ring se     tt     i   ng          sView) {
                                  this.            setting               sV         iew = se    ttingsVie         w;
                  return thi     s;
               }

                       public   CRUDControllerConfigur             at io     n<D, E, F> s  etRe adView(String    rea             dView) {
                    thi  s. readView = readVi  ew                  ;
                          r   eturn this;
             }

        publi  c S   tring ge  tF     ormN   ewFr   ag      me n   t()     {
                            ret   urn formNewFragment;
                 }

           public CR     UDContr        oll erCon          f  ig     uration<D, E, F> se   tFo   rmNewFragment(String form         NewFra  gment) {  
                                      this.formNe       wFra  gment = formNew   Fragment;
                             return    this;
          }

                         public          String g     etFormSuccessFra   gment  () {
                        re turn formSucces  sF       r                     agme     nt;
                 }

              p ublic CR   UDCon                trollerConfig  urat       ion<D,     E, F> setF     ormSuccessFra  gm   ent    (Str     i           n       g formSuccessFrag    ment) {
                                            this.form       Su      ccessFr agmen    t =                    formSuccessFragment ;
                                    return     thi s;
                        } 

                   public   String g       et Form   Erro r    Frag        ment    () {
                              re tu rn formErrorFragment;
           }

        public CRUD  Cont    rollerConfigu   ration<D, E  ,         F> setFormErr              orF    ragmen t (St  ring formErrorFragment) {
                this. fo  rmErrorFra     gment = formE         rror  Fragment;
                                        retu   rn     this;
        }

          p   u  b       lic St      ring g   etNa    vigat      ionFragment(  ) {    
                return naviga      tionFragment;    
                       }

                                       public          C RUDContro llerConfigurat      ion<D, E, F> se tNavi  gat          ionFra    g   m   ent(String navigationFragm  ent    ) {
                    this  .na viga tionFr      ag   m   ent = navigationFragment   ;
                                       re tur  n this;
                    }

             p u     bl ic   St  ri ng        ge tMe  n uIt    em()      {
                return menuItem;
             }
   
          pub        lic  CRUD       ControllerConfiguration<D       , E, F> setMenuItem(String menuItem) {
                               this.me        nu Item = me   nuIt   em;
                                       return this;
        }

               publi c St      ring   g    etKey() {
                          ret      urn key;
                  }

                            public Fr   onten   dMap    pi  ngDefini     tion ge   tFronte                ndM    appingDef    inition() {
                  re    tur  n frontend        MappingDefinition;
        }
    
             publi       c    Class  <F>      getFor    mCla s  s   ()     {    
                                    retur n              formClas s;
        }

         pub      lic Class<D> getDt     o          Clas s       () {
                retu                 rn dtoClass  ;  
                   }

              p     ublic Class<E> get      EntityClass() {
                        return    ent     it    yCla           s   s;
                   } 

                 publi  c Pr    ivilegeBase    g         etDefaul      tCon       troller     P   r     ivil ege() {
                          ret   u  rn defaultControllerPriv  ilege;
                       }

                        public Privilege Base get   Get    AllPrivi lege() {
                retur    n     getAllPri      vi   lege != null    ? getAllPrivi lege :   defaultC       ontrollerPrivi  l             ege;
        } 

        public  Privil     eg     eBase getGetNewPrivileg      e    (     ) {
                   r   etu        rn getNe  wPrivilege     !=         null ? getNewPri  vile  ge   : defaultContr  ollerPrivile  ge;
           }   

              publi     c                        Privileg  eBa      se g   et   GetSet tingsPri    vilege() {
                    retur   n g   etSe  tting  sP  rivilege != n   ull             ? get   Se  ttingsPrivilege : default  Control     lerPrivilege;
                       }

          publ       ic Pri    vi  legeB   ase getPostNewPrivile      ge()  {    
                      return postNewPrivilege != nu ll ? pos tNewPrivil ege : defaultControlle    rPrivile ge        ;      
                                }

                  public PrivilegeBase   getPostSavePriv   ile    ge() {
                              return postSave             Privilege      != null ?   postSa               vePr ivilege   : def   aultContr      oller  Priv  il   ege;
             }

                   public PrivilegeBas     e ge      tPostRemove    Pri       v  ilege() {
                                      re       turn postRemovePrivilege !  =      null ? postRem  ovePrivileg   e : defau     l  tControllerPrivilege;
                    }
    
                public Pri     v     il  e   ge    Base get     FieldReadPrivile   ge          (St  ring fie  ldName) {
                   re t     urn fro     ntendM    ap   p ingDefiniti      on.fi  ndF  ield(fieldName).readPr    ivilege     ;    
                 }

            public   Privile   geBase g             etFi     eldWrit   ePrivilege(Stri ng fieldNa   me) {
                                     retu        rn frontendMappingDefin  ition.findField(fieldNam e).write          Privile   ge;
        }
  
                   pu   blic St  ri  ng getTableView()      {
                               retur  n tableV           iew;
                           }

                   public String getTa        bleViewWebEndpo       int() {
                         return tableViewWebEndpoint;
           }
   
          public Strin   g ge     tSe      ttin       gs    View() {
                   return s   e  ttings   View;    
                        }

                      public S tring getReadV      iew()      {
                           return readView;
            }

          p    ubl    ic Scop    edSe   cureRe  posit  o   ry <       E> g   etSecureRe      pository(   ) {
                        ret    urn secureRepository;
              }

                 publi    c E createNewEn tity(    Long organiza tionId) {
                      tr          y {
                                                     E entity = entityConstructor.n     ewInsta    nce    (   o  rganizationId);   
                                      if (i      sMa          pEnti   ty) {   
                                      (        (MapEntity     ) ent  ity).setKe  y(ke y);
                                          }   
                          return entity;
                                } catch (InstantiationException | IllegalA  ccessException | I    nvocati   onTargetExceptio     n e) {
                               throw n    ew Runt  imeException(e);   
                  }
        }

        public F cr                 ea   te  N  ew      Form() {
                 try {
                            if (f rontendMap   p            ingDefinit ionInConstru       ctor)   {
                                   retu   rn formConstructor.newInstance       (fronten            dMappingDefinition);
                                      }  else {  
                                      return formCon  structor.newInsta    nc     e();
                          }

                } catch (I    n    stantiationExceptio   n | IllegalAccessExceptio  n | InvocationT    argetE xception               e)  {
                            thro w new  RuntimeExcepti    on(e)  ;
                           }
            }

        public F crea   teNewForm(Long organizationId     , E entity) {
                         t  ry {
                                 if (frontendMappingDefinitionInConstructor) {
                                              return formEntityConstructo     r.n ewInsta nce(frontendMappingDefinition, organizationId, entity);
                                        } else {
                                        ret  urn f     ormEnt  ityConstructor. n  ewInstance(organizati   onId, entity);
                                }
                } catch (InstantiationException | IllegalAccessException | Invoca   tionTargetException e) {
                           throw       new  RuntimeException(e);
                            }
        }

        private void detectFormCo nstructor  () thr ows NoSuchMethodException {  
                try     {
                        this.formConstructor = t  his.formClass.getDeclaredConstructor(FrontendMappi  ngDefinition.cla    ss);
                        if      (ReflectionBasedEntityForm.class.equals(formClass)) {
                                         this.formEntityC ons tructor = this.formCla    ss.ge   tDeclaredConstructor(Frontend    MappingDefinition.class,     Long. class, SearchableOrganizationRel  atedEntity.class);
                                } else {
                                                    this.formEnti  tyConstructor = this     .formClass.getDeclare   dCo nstructor(FrontendMappingDefinition.clas    s, Long.class, ent     ityClass);
                        }
                        frontend    MappingDefinitionInConstructor = true;
                       } catch ( NoSuchMethodException e) {
                          this.formConstructor = this.form  Class.getDeclaredConstructor();
                        this     .formEntityConstructor = this.formClass.getDeclaredConstructor(Long.class, entityClass);  
                         frontendMappingDefinitionInConstructor = false;
                }
        }

        public Specification<E> getAddition   alSpecification() {
                re       turn isMapEntity ? (additionalPredicate != null ? (root, que ry, cb) -> cb.and(cb.equal(root.get(   "key"), key), additionalPredicate.toPredicate(root, query, cb))
                        : (root, query, cb) -> cb.equal(root.get("key"), key))  : (additional          Predicate != null ? ((root, query, cb) -> additiona   lPredicate.toPredicate(root, query, cb))  : null);
        }

        public boolean isMapEntity() {
                return isMapEntity;
        }

        public PageAttr<Page<E>> getEn     tityPageAttribute() {
                   return (PageAttr<Page<E>>) entityPageAttri         bute;
        }

         public CRUDControllerConfiguration<D, E, F> setEntityPageAttribute(PageAttr<Page<E>> entity   PageAttribute) {
                this.entityPageAttribute = entityPageAttribute;
                  ret  urn this;
        }

               public PageAttr<E> getEntity   Attribute() {
                    return (PageAttr<E>) entityAttribute;
        }

        public CRUDControllerConfiguration<D, E, F> setEntityAttribute(PageAttr<E> entityAttrib     ute) {
                this.entityAttribute = entityAttribute;
                return this;
               }

        public PageAttr<F> getFormAttribute() {
                return (PageAttr<F>) formAttribute;
        }

        public CRUDController Configuration<D, E, F> setFormAttribute(PageAttr<F> formAttribute) {
                this.formAttribute = formAttribute;
                return this;
        }

        public CRUDControllerConfiguration<D, E, F> setAdditionalPredicate(Specification<E> additionalPredicate) {
                this.additionalPredicate = additionalPredicate;
                retu    rn this;
        }

        public Long getOrganizationId() {
                return organizationId;
        }

        public void setOrganizationId(Long organizationId) {
                this.organizationId = organizationId;
        }
}


