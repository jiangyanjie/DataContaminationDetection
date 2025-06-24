package    com.tencent.supersonic.headless.server.persistence.dataobject;

import     java.util.ArrayList;
import java.util.Date;
import      java.util.List;

pub lic class DatabaseDOExample {      
        /         **
       * s2_dat abase
     */
    protec    ted String orderBy   Cla     us   e;

       /**
            *      s  2_data  ba       se 
        */
      protected boolean distinct ;
   
       /**
     * s    2_da       tabase
           */
    pr      otect   ed List<Cr iteria> oredC      r    it         eria;

    /**
     * s2_  database
     */
    p rotect    e      d      I              nt eger limitStart;

       /**
     * s2_database
     */
    protected Int     eger             limitE             nd;

     /**     
        *
     * @mbg.ge     nerat   ed
      */
         public Da taba     s       eDOE  xa    m   ple() {
           oredCrit        eria = new ArrayList<Crit   er          ia>();  
    }  

    /**
         *
                  *         @mb   g.ge   nerated
     */
             public void s  etOrd        erByClause(St   ring orderByCla  use) {
          this.orderByClause = orde  rByCla    use   ;
    }

    /**
     *   
          *   @  m      bg.generated
     */
    pu  b   l  i             c Stri   ng    ge   tOrderByClause() {
           return orde  rByC lause   ;
       }

     /**
     *
       * @    m      bg.gene        rated              
            */
     public void setD is   ti  nct(   boolean di               stin    ct) {
          this.dis   ti  nct = distinct    ;
       }

    /**
        *
     *    @mbg    .generat    ed 
            */
           pu    blic b              ool    ean isD          istinct(    ) {
                         re  turn distinct;
      }

    /**
            *
      *       @m  bg.gen  er     at e     d
     */
    public List<Criteria> ge  tOr  ed   Crite  ria() {
              re     turn or ed Crit    eria;
                            }

    /**
         *
                       * @mb     g.genera   ted
     */
    pub      lic void or(Crite   ria cr  i teria) {   
          o redC   r iteria     .add(    cri    teria);
    }

    /**
       *
              *              @mbg. generated       
          */
    public Criteria or( ) {
                       Crit      e  ri   a cr  i teria       = cre        ateCriteriaInternal()   ;
         o   r   edCriteria.ad                     d(    criteria)      ;
           r       eturn criteri a;
    }

    /**
     *
        * @mbg. gen    er    ated
     */            
       publi  c Criteri     a              crea          teCriteria() {
            C     ri  te   ria criteria = cr   eateC  riteriaInternal();
                    if (ored     Cri   teri           a.s   i      ze() ==        0)  {
             ored  Crit    eria.ad     d(   crit  eria);
        }
               re  turn criteria    ;
       }

                /**
      *
      * @  mbg.generat                  ed
     */
              protected  Cri               teria creat  eCriteriaIn terna    l(  ) {
               Cr   it   eria crit  eria =         new Cri teri a(  );
             return c     rit           eria        ;
    }

        /**
            *
     * @mbg.generated
     */
    pub lic    void clear    () {
        oredCrit        e  ria.clea       r();
                         orderByClaus e = null ;
        dist    i nct = false;
    }

    /**
                          *
                 * @mbg.gene         rated    
     */
    public void     setLimitS tart    (Integ     e    r lim  it   Start) {
                  t   his.l          im                    itStart=   limi    tStart;
    }

       /**
     *
     *         @m  bg                              .generated
     */
    public In         teger getLimitS       tart(  )    {
              return limitSta rt;
    }

           /**
     *
           *               @m bg.generat     ed
         */  
        public                  void setLimitEnd(Integer limitEnd) {
                 this.limi  tEnd=limitEnd                 ;
     }

    /**
     *
        * @mb g.genera    ted
     */
        p   ub lic Int eger g         e  tLi  mitEnd() {
                     r              eturn limi    t E       nd;
              }

       /   ** 
          *    s2_ databas  e      null 
         */
                            pr ot     ec        ted a     bst      ract  static cl    as    s    Generat ed Criteria {
        prote    cted Li    s   t<C  rit   erion> cri te          ria;
    
         p  rotec    ted     Gen    eratedCr     it     eria()               {
                             super();
                    cri  teria =  new Arr  ayList<   Crit       erio   n>();
             }

        publi c bool   ean isVal   id() {
                   return criteria.size() >      0;
                    }

                  p     ublic       List<Criterion> getAll  Criter  ia() {
            return criter        i     a;
             }

             publi   c Lis            t<Criteri  on> getCriteri       a() {
                return cr iteria;
              }

         protect   ed     vo  id   addCriterion      (String condition) {    
                                  i     f (cond ition ==   null)     {
                      thro      w new RuntimeExcept   ion("Value for condition can   n ot be null");
              }
             criteria  .add(new Criterion(c   ondition ));
                  }      

               protected       v oid a     ddC    riterion           (Str ing c    ond    i      tion, Obj     ect va  lue,     String    property) {
                      if  (val    ue   == null)   {
                                    throw new Runtim    eExc ept  ion("      Val    ue  for            " + property +   " cannot be null");
                     }
                 cr       iteria   .add(new Criteri       on (condition,             value)      );
        }

                                         protec     ted            void       add Cri             terion(String condit     i                on, Object va                 lue1, Object valu   e2    ,  Str  ing propert           y) {
                                   if    (value         1 ==        nul       l || valu  e      2 ==    null) {
                  th      row new     RuntimeException(   "Between  values    for " + property + " cannot be   null");
              }     
              criteria  .ad  d(new Crit           erio    n (condi ti   on, value1, v   a      lue2));
                      }

              pub       lic Criteria and    IdIsNull() {
            addCriterion(         "id is       n      ull");
                   return     (Criteria) this;
         }

          public Criteria andIdI      sNotNull()    {    
                  ad      dCrite rion(          "id is    not nul    l");
                 retu     rn   (Cr     iteria)   this;
                    }

                    p  ubl      i    c Criteri     a andI   dE  qua lTo(Long value) {
            addCrit   erion("id =",        value  , "id");
                 retur   n (Cri   teri a)    this;
        }

                       public Crite         ria andIdNotE          qualTo(L o      ng   va    l  ue) {
                 addCrit    erion("id <>",      valu e,    "id   ");
                   ret   urn        (Cr   iteria) t      his;
                }

        pub  lic  Criteria    andIdG   re      ate      rT   han    (Long   v   a lue)   {
                  ad       dCr         i        terion("id       >"      , valu e, "id") ;
              retur   n (   Criter      i       a) this;
          }     

        public Cri         teria a     ndI     dGreaterThanOr  EqualTo                         (L     ong       valu     e)    {
                add  C       rite        rion(" id    >=",     va   lue,            "i   d" )   ;
                   return (Criteria ) this;            
              }

        publi    c Criteria andIdLessThan(Long valu  e) {
                 addCri         t   e        rion                (  "id <", valu          e,  "id     ");
                       return      (Criteria) this;
           }

          public Criteria andIdLessThanO    rEqu      alTo(Long value) {
                   addCriterion("id <=", v alu     e, "id");
                    r   eturn (                     Cr            iteria ) th   is;
           }
                 
                public Criteria andIdIn(List< Long> valu    es) {
                  addCriterion  ("    id in"    ,          values, "i d  ");          
                return (Criteri    a) this;
                        }

            publ   i   c Criteria      andIdNotIn(List<Long      >         values   )       {
             a   d dCriterion  ("id not i    n",           val  ues, "i d");
                  return (Criteria    ) this;
        }

                               pub  lic Cr  iteri a and               IdBetwee n(Long va    lue1, Long val  ue2) {
            a                    d    dCrite      rion("id between", v  al        ue1    , va     lue2    ,         "id");
                            return (Criteria                       ) this;
         }

                     public       Criteria andIdNo   t     B    etween(Long value1,   Lon g v   al  ue2) {
                  ad       dCriteri        on("    id not between  ",             value1, v   alue2     , "id");     
                    return (C  riter     ia) t h  is;
                             }
  
          p  ublic    Criteria andNameIsNu    ll() {
              addCr                       iterion("nam    e i  s null")    ;
               re turn      (Cri t      eria) this;
            }
   
        public Crit    eria a          ndNameIsNotNull() {
                  addCriterion("name         is  not n         ull");
                         return (Criter    ia) this      ;
                     }
  
                              public Cri   t         er    ia andN      ameEqualTo(S   tring value)           {
                 ad  dCriterion( "name =",                   value, "name                    ");
                  r     eturn (Criteria)      this;
                    }

             p    ub  li  c   Criteria a  ndNameNot   EqualT         o(String value) {
                      addCriterion("name <>",      value,        "name"    );
            re       t  urn (Criteria) thi         s                                  ;
        }
      
                publi   c  C     riteria andNameGr      ea        terThan(String value)       {
                      addCriter ion("   name >       " ,       value, "name");
               return  (Criteria) this;
            }

        public              Criteria andNameG r  e    aterTha nOrE       qualT       o(String  value) {
                    addCri      terion("na     me >        ="    , value, "n    ame");
                ret   ur              n (C                    rit eria) this;
                           }

                pu   blic Criteria andNameLessThan(String value)     {
                 addCrite     rion("name <", va  lue,       "name");
               re      turn (Criteria) this;
        }    

        public Criter       ia a  ndNameLe         ssTh          a    nOrEqualTo(String value) {
                 addCriteri   on("     nam e <=",  valu     e, "name"             );
                 r            etur   n   (Cr     ite   r   ia) t   his;
               }

        public Crite  ria      andNam   e                Like(String  v     alue) {
            add          Criterion("n   a                            me li    ke", value,     "nam   e");
                    r      et  urn (Cri                  ter       ia) this;
        }

           pu     bli    c       Criteri    a   andName  NotLi        ke(Str   ing value ) {
                   a               d  dCriterion("name n     o        t like"  , value,    "na    me");
                return    (          Cr      it         e   ria) this;
         }   

               public Criteria  andNameIn(List<String> valu es)    {
                               addCriterion("    n   ame in", v   alues, "n    ame");
             ret   urn     (Criteria)  this;
                       }

                public C  ri  teria andNam e  NotIn(Lis       t<Strin    g> valu     es) {
            addCrit erion  ("  name n       ot i         n", values, "n   ame  ");
                r   etur   n  ( Criteria)            th is;
             }

        public Cr         iteria an     dNameBet  ween(Str   ing valu    e1, Str      ing                       value   2) {
                     addCrit  erion  ("name      between"     , value1, va   lue    2,   "name"); 
            retur n   (Cr      iteria)   t  his;
              }

                     pu     bl  ic C                riteri   a andNameNo    t   Between(Str         ing value1, String value2) {    
                 addCriter  ion("name not between",    v  alue1  , value2, "name"); 
                     return (C  rit   eria)         th  is;
               }

        public Criter  ia           a    ndDescriptionIsNull() {     
                   addCriterion("d e     scrip  tion  is nu  ll");
                     return (Crit  eria ) this;
           }

               public    Cri    te ria                     andDescri    ptionIsNot   Nu l                   l(    )    {
                add      Cri       terion(     "description     is not null");
            re    turn     (Crit eria)         this;
        }

        public Criteria  and  Descripti    onE          qualTo   (St     ring value) {
                              addCr   i terion("des  cripti   on =", value,   "     descr   iption");
             return (Crit        eria) this           ;
              }

        public Cri       teria andDescription  NotEqual   To(Strin   g valu   e) {
                          addCri  t  erion("description <>", va      lue, " d  escrip   t         ion");
                  re  turn (       Criteria) this;
        }

              pu  bli  c Criteria         andDescriptionG       reaterThan(String   v   alue) {
                 addCriterio    n   (        "descr  iption                    >", val    u   e, "descriptio         n");
                         ret urn (Criteria) this;
            }   

             publi c Criteria an   dDe       s       c    ripti  onGreat  erThanOrE     qualT   o(St    r  ing value) {
                    addCriterion("descrip tion >=         ", value, "des          cription"      );
                                 return (Criteria) this;
           }

        public Cr   iteria andDe   scriptionLes sTh        an(S tring value)               {
               addCr         iteri           on("descr          iption <",    value, "d escriptio    n");
                 return (Criteria)                this;
         }

        public Criteria           andDescriptionL      essThanOrEq  ualTo  (String  value) {
            addCriterion("de    scri         p tio     n            <=", value, "descr ipti  on");
                    return (Criter ia)  t                 his;
                  }

          pub   lic Criteri       a   and   D  e      sc    riptionLike(Stri  ng val    ue) {
              addCrite ri               on   ("description like",    value, "description");    
            return (Crite  ria  ) this;
        }

                publi c        C    riteri  a andDescriptionNo t    Li  k    e(String value)     {
            addCriterion("descri pti   on not   like", value   , "description");
                 return (Criter        ia)      th   is;
                }

          public Criter   ia andDes        crip tion   In(List<String> value     s)   {
                   ad   dCriterion(       "    de   scription i         n", values    , "d    escription      ")     ;
                    return     (Crit  e    ria) this;
        }

          public Criter   ia andDe  scri   pti      onNotIn(    Li  st <Strin      g>                 va    lu  es)        {
                             addCriterion(            " description not in", va        lues,   "description");
                   retu   rn (   Crite     ria      ) this;
           }

         publi      c Crit   eria andDescript  ionBetween(Str     ing               value1, String valu   e2) {
                    addCriterion("   description be   tween     ", va     lue     1,          value2, "descripti      on")            ; 
                   return   (Cr  i     te         r  i      a) thi           s;
           }
 
                 publ        ic Crite  ria andDescri  p tio        nNot   Be  tween(String valu    e1, String value2) {
                     addCriterion("   desc   ription not b  etw een", valu e1, value2,   "description");       
                re     turn (Cri  teria      ) this           ;
              }

            public C               rite      ria andVe     rsionIsNul     l()      {  
                 addCriterion("v   e   rs     ion  is nu   ll");
                        return            (   Cri teria) this;
        }

              pub lic C  r            iter    ia        andVersionIsNotNu    l   l            ( )            {
               addCr  iter   ion(  "v     ersion i   s n    ot null");     
                             return (Cr iteria  ) th     is;
         }
              
            public  C        riteria and    Ver s     ionE     qualTo   (S tr   ing value) {     
                                             addCrite    rion("  v        ersion =", value,    "ve   rsion");
                             re turn (Criteria) this;
                   }

             pu   blic Criteria andVersionNotEqualTo(S        trin  g value) {
                 ad dCrit       e   rion(    "versi  on <>   ", v  alu                     e, "version  ");
              return (Cri    teria)     th           is;        
        }

              public Criteri      a     a                  ndVersionG reater Than(St  rin     g val  ue) {
                            addCr     it       erion("version >   "     , v         alue, "version");
                       return    (Criteria) thi     s; 
                     }  

        publi c Crit  e                ri   a andVersionGrea  ter       ThanOr E  qualTo(       Str      ing value) {     
              addCrite          rion("version >=",       v alue, " version");
                  return          (Criter  ia) this; 
         }     

            p ubl   ic    Criteria andVersionLessTh           an(    String   value)  {
               addCriterion("versi         on <", valu  e , "          version");
                retur      n     (Cr iteria) this;
        }

        pub      l   ic Criteria and              Ve           rsio            nLess    Than  OrEqu    alTo(  String value)     {
             addCriterion("versio   n <=", value     , "version"    );
                   ret   urn (Crit  eria) this ;
          }

          pu  bli  c   Cr      iteria and      Vers     ionLike(String val    ue) { 
               a   ddCriterion("v   ers   ion like", value,      "version "); 
                       return        (Crite    ria)    th   is;     
                 }

               publ ic Cr    iteria       andVers   ionNotLike(Str   ing       val   ue) {
            addCriterion(    "version not like",      v   alue,   "versi on");       
                           return      (Crite  ria)   this;
             }

          pub    l ic C   riteria andVersi      onIn(   List<    St  ring>    values) {
                    a ddC     riterion ("ve   rsion in",    val      ue   s, "v     ersion");
               return (Cri    ter        ia) this;    
                       }

              public Cri              t             eria   and   Ve   rsionNot    In(List<S    tring>    values) {
                add  Criterion("version not in  "   , v          alues, "version");  
                                        ret urn (Crite  ria) this;
        }

        publi     c       Criteria andVersi  o      n    Be  twee   n(Stri   ng va    l           ue1, Strin             g   valu e2) {
                         addCr    iterion("version             betw          een", value1, value2, "version");
               return (Crit  eri    a) this;
        }

        pub li    c Criteria a       ndV  ersionNotBe t     ween(String valu e   1, Str              ing value2)     {
            addCrit      e rion("    vers              ion not betw    ee      n",        value1, value2, "ve rsio  n");
                      re tur   n     (Criter    ia   )    this;
              }

            public Criteri       a and TypeIsNull  (   ) {
                         ad    dCrit         erion("typ    e     is null")        ;
               re  turn (Criteria ) this;
                  }

        public C    r   i    te    ri    a andTypeIsNotNull() {
             a    ddC     rite  rion  ("type is not nu ll");
               re      tu rn (   Crit eria) this;
            }

                      public C  riteria andTyp           eEq     ualTo(String value)       {
            addCriterion("    ty  pe =",      va         lue, "type");
                        return (Cri         ter   ia)  thi   s    ;
                         }
  
                           public Cr iteria and   Ty  peN  ot      Equal     To(Strin  g value) {
            addCriterion("type <>          ", valu     e,  "type  ");
            return (Criteri    a)       th    is;
          }

                pub   l ic      C riteria andType      Gre       aterThan(Strin    g value) {
                       add Criterion("type >", v alue, "typ       e");
               return (Cr   ite    ria) this;
             }

          public Cri teria and   TypeGreate    rThanOrEqualTo(Str  ing     value) {
            addCri    t          e    ri    on("ty pe >= ", value, "ty  pe");
                        return (Criteria) this;
                }

          public Criteria  a        ndT     ypeLessThan(  S         t ring value ) {
               a      ddCriterion("type <",    value, "typ e");
            ret    urn    (Cr iteria)      t           his;      
        }

            public C    riteria andTypeLessTh  an     OrEqu  alTo(String  value) {
                         ad dCriterion("type <=   ",   va    lue,    "  ty pe");
              return (C    riteria     ) t  his;
        }

             public Cr iteria     andTypeLike(String v   alue     )    {
              addCrit         erion("ty      pe like", va     l       ue, "type");
              return (Criteria) th   is;
           }   

            public Criteria andType Not      Like(Str  i  ng   valu e)         {
                 addCri terion("typ e not like  ",  value,   "type");
            return (C  riteri   a) this;
           }

        p ublic C    riteri   a an   dT         ypeIn(List<Str    ing> values) {
               addCri  terion ("t      ype in",      valu          es, "type      ");
                                       r      etu            rn (Cr        iteria) this;
        }

          pub         lic Criteria              andTy   peNot     In(List          <              String> v   alu  e      s) {
                          addCriterion("type not in       "   , values   , "type");
            re t      ur            n (C   riteria) thi    s;
        }

          pub  lic       Crit er         ia andTypeBet ween(String   value1,   String va    l  ue2)  {
                         addCriterion("type be     tween", value  1, value2    , "type");
                return (C    riteria)   this;
              }

        pu     blic Criteria  andTy peNotBetween(Stri    ng val   ue1, Strin    g value2)         {
                         addCr       iterion      ("t   ype not between", va    lue1, value2, "type");  
                   retur   n  (Criteria)  t     h   is;
        }

           public C     rit   eria a    ndCreat  edA  tI   sN  ull()     {
             add  Criterion("created_at is null");   
                        ret      urn  (Crit                eria) t   hi   s;      
                }

             publ     ic    Criteria andCreatedAtIsNotNull() {
                add     Cr i    terion("created _at    is   not n    ul   l      ")     ;
                           r         e     turn    (Criteria) this      ;
        }

                  pub   lic Criteria and    Cr    eat edAtEqualTo(Dat e val       ue) {
                              addCrit  erion("created_at =", value, "cr  eat          edAt");   
                   return (Cr       iteria) this;
           }  

        pub  lic C   riteria andCreatedAtNotEq         ualTo(Date va     lu    e) {
                      addCrite  rion("cr  e     ated_a   t <>", value, "createdAt");
            return (Crit  eri   a) this;
            }

        pub   lic Cri   teria a  ndCr   eat   edA   tGr       eaterThan(Date v  al    ue) {
                 addCri  terion("create  d     _at >", va  lue, "createdAt");
               return (Criteri a  )      this;
                           }

         p      ublic Criteria andCre  atedAtGreaterThanOrEqualTo(Date v      alue  ) {
                addCriteri  on("create      d_at >  =",   value,    "c  r  eatedAt");
                retu     rn (Criteria) t  his;
          }

                 public Criteri  a andCreatedAtLessTh     a      n(Date                      value)    {
                      addCr   iterion("created   _at <", value,  "creat     edAt");
                                   r       etur  n      (  C  r i teria) this  ;
                }
   
          p  ublic Criteria an          dCreatedAtLessThan    OrEqua lTo(D ate v           alue) {
                   add       Criter    ion("created_a    t    <=", va l  ue        , "cr     eated   A     t  " );
              re    turn (Criteria) this; 
            }

              public C  riteria andCreate      d        At In     (List<Da  t    e> valu es) {
                       addCri   terion("cr     eated_at in", valu  es   ,   "created    At"   );
                 retur n (   Crit    eria) this;
              }
         
            p    ublic Criter ia an   d      Cre atedAtNotIn(List<Date> value s) {
                 addCri    teri       on("cre    ated_a t not    in",   values, "createdAt"   );
                              r     etu       rn  (Criteria)   this;
           }

         p        u  blic Criteria andCre atedAtBetwee  n   (Date       val     ue1, D     ate value2) {
                          addCr it    erion(   "create   d_at          be    tw   een      ", value1, val       ue2,  "cr   eatedAt")  ;
                               return (  Cri     teria)      t h   is;
             }

                  public Cr iteria a    ndCreatedAtNo    tBetwe    en(Dat e value1, Date v alue2)        {            
                  a ddCriter   ion     (  "created_at not between   ",     valu e1,      va   lue2,   "createdAt");
                               return (Crite  ria) thi   s;
        }

              publi   c  Criteria andC   reatedByIsNull()        {
                     addCr   iterion("created_ by  is       n    u     ll"   );
                    ret    urn (Crite  ri   a) this;
        }

            public Cr  iteria a        ndCr  eate           dByIsNotNull  ( ) {
                               addCriterion("created_by is not null");
                 ret  urn (            Cri       teria) this;
          }   

        public Cr      i teria andCreatedByE    q      ualTo(Str   ing value) {
                         a ddCriterion("cr  ea        ted_by =", value,       "createdBy"   );
                    return (Cri      t  eria) this  ;
            }

                      public Cri   t eria    and           Cr     eat  edByN   otEq   ualTo(String va    lu   e)     {
                  addCriteri    on("cre  ate   d_by <>", v  a  lue, "c   reatedBy");
                         r          eturn (Cri teria        ) this; 
        }

          publ   ic Criteria andCreatedByGre      a  t erThan(Str    ing value) {
            addCri  terion("created   _             by >"  , value, "create   dBy  ");  
            return (    Crit        eria) thi  s;
        }

            p      ublic  Criter    ia           an       dCreate   dB     yGreaterThanOrE  qua    lTo(  String value)    {
                             addCriteri         on("created_by >=", va   lue, "cr     eatedBy");
              r    eturn (Cr   it    eria)       t his;
               }

        pu bli          c Criteria an dCreatedByLessThan(Strin     g value) {
                  ad  dCriterion("c         reated_by <"     ,   value   , "created   By");
                  re   turn (   Criteri  a) this;
            }

           public    Crit  eria andCreat  ed  ByLessTha nOrEqualTo(String   v     alue)  {
                       addCriterion("created_b y <="  , value      ,           "c   re   atedBy");
              return (Criteria) th     is;   
                }   

           public Criteria a    ndCreatedByLike(String       value) {
                add Crit erio     n     (  "creat  ed_by like", val    ue,   "       cre  a      tedBy")       ;
               return (Crit  eria) this;
                     }

        pub lic Criter      ia a ndCrea tedByNotL   ike(St      r   in    g val         ue) {
                add  Cr   i  terion("creat  ed_    by   not like",         value, "crea tedBy");
                     return (Crite      ria) t his;
          }

             public Criteria an       dCreatedByIn     (List<S             tr   ing   > va          lues)      {
             addCriter  ion(               "  cr eated_by in"  , values     , "createdBy")   ;
                               return (     Criteria) thi       s;
           }

        public Criteria andCr    eatedByNotI   n(Lis    t <Str i     ng> values) {   
                   a ddCr          iter    ion("created_b      y not in",        values, "creat     edBy");
                   r eturn             (Cr      i      ter ia) th    is;
                           }

            pu          blic Criter        ia andC     reat edByBe  tw een(  String valu   e1, S    tring v  alue2) {
                     addCrit    eri   on("cre    ated_by    between", value1, value2,   "create       d   B    y");
                     r               e    t  urn (Crit eria) th        is;
                             }
    
                   public Criter   ia andCreatedByNotB    etween(Strin       g value1, Stri ng value2) {
                    addCriterion("cr      eated_by not betwe    en", value1, value2,  "  cr   eate      dBy    "); 
                    retu rn (     Criteria) t   hi   s;  
        }

                   public   Crite         ria    a  ndUpdatedA    tI   sNull() {
                         a      d    d Criterion("upd  a           ted_at      is     null"     );
                return   (Criteria)     this   ; 
             }

                         public Crite      ria andUpdatedAtIsNo tNull() {
                                addCriteri        o         n   ("         updated_at is n    ot null        ");
                               return (Criteria)       th      is;
        }

              public Criter    ia     a     ndUpdatedAtEqualTo(Date v   alue)  {
                      a      ddCriterion(" up   dated_a t =",  val      ue, "updat      edAt "                 );
            ret      urn   (Cri  teria    ) this; 
                 }   

                    public Crit  eri  a             andUpda      tedAtNotEqualTo(  D   ate value) {
                        addCri     te  rion    ("upd     a  ted  _at <>"   , v     alue, "upda    tedAt");
                   r eturn (Crite  ria)     this;
          }    

        public Criter ia a  n   dUpdatedAtGreate rThan(   Dat       e   value  ) {
                      addCrit      e ri o      n("updated_    at >", val      ue,   "updated         A t");   
               return (C     r  iteri a)                       this;
                       }
           
        public   Cr           it   er ia andUpdate   dAtGreaterTha     nOrEqualTo(Date value)    {
                   a               ddCrite  rio  n("updated_at         >=   ", value           , "updated At  ");
                   return ( Crit  eria   ) th     is;
          }

        public Cr i   teria      a       nd     U      pdatedAtL   e  ssThan(D     ate value) {
                ad     dCriterion("u     pdated_at <", val       ue       , "updatedAt");
                 re turn (C    rite ria) this;
        }  

        pu     b   li c Criteria an   dUpdat   ed          AtLes sTh        anOrEqualTo(Da te    value)   {
                addCriter    ion("      up  d     ated_at < =", value,  "updatedAt")   ;
               return (Cri        teria) this;
        }

                          p  ubli   c C  rite   ria andUpda               tedAtIn(List<   Date> values) {
                                  a    ddCriterion(    "updated_at in"  , values, "updatedAt");
                retu   rn  (Criteria) this;
                 }

        public Crit   e  ria     andUpdatedAtNotIn(List<Da   te       > values) {     
                 add Criter   ion("            up  dated_at   not in",           values   ,            "updatedAt   ");
               re turn        ( C   riteria) this;    
        }

            public        Cri   te ria andUpdated A    tBetween(Da   te valu    e1, Date     va  lue2              ) {    
                        addCriterion    ("updated_at between", valu    e1, valu   e2, "update  dAt");
            return (Cri   teria)    t    his;
              }

        pu   blic Criteria andUpdat       edAt    NotBetween(Dat e va  lu    e1, Date value2) {
                     addCriterion("u    p            d  ated_        at    not    bet  w    een",    value1, value2, "u pda     te    dA  t     ");
               retur  n (Criteria)   this;
          }

             publ     i    c Criter        ia a  ndUpd  atedByIsNull() {         
            ad  dCr   iterion("    updated_by is nu        ll");
                         retu    rn (Criteria) this;
          }

                     public Criteria andUpdatedByIsNo   tNull()     {
                       ad   dCriter   ion(" upd ated_by is         not null");
            ret   ur      n (Cri         teria) th    is;
           }

            public Criteria a   n        dUpdate dByE     qu  alT      o(Stri      ng value)       {
             addCriterion("updated_by  =",       value , "updatedBy");
                      r   eturn (       Criteria) this;
            }

              public   Criteria     andUp    datedByNotEqualTo(   Stri  n               g    value)      {
            ad       dCrite rion("u  pdated_by <>", value, "upda   tedBy    ");
                  return (Criteria) this;
         }

        publi      c Cri teria andUpdat    edByGreaterThan(Strin  g value) {
            addCrite    rion("upd    ated_by >", v al   ue, "updatedBy");
                       re      turn (Criteria)   this;
                  }

        pub lic Criteria andUpdatedByGrea   terTha  nOrEqualTo(String value) {
               a    ddCriterion(     "updated_by >=", value, "updatedBy");
                  return (Criteria) th       is          ;
        } 

         pu     bli  c C   riteria andUp   date   dByLessT         han(String value) {
                    addCriterion("up dated_by <", value, "up  datedBy");
             return  (Criteri  a) thi      s;
                     }

        public C riteria andU  pdatedByLessT   hanOrEqu    alTo(String value)  {
            addCriterion("upd      ated_by <=", value,   "u    pdate dBy");
                return (Criteria) this  ;
            }

             public Cri  teria   andUpdatedByLike(Str   ing val ue) {
                   ad      dCriterion   ("       upd  ated_by like", va     lue, "up    dat     edBy");
             ret       urn (Criteria) this;
        }

                public Criteria a   nd    UpdatedByNotLike(String value) {
                  add     Criterion  (  "updated_by not like", value, "updatedBy    ");
            ret  urn  (Criteria) this;
                   }

                      public Criteria andUpdatedB    yI   n(L    ist        <String> val ues) {
               a ddCrite      rion("update             d_by in", values, "updatedBy");  
                       return (Criteria  ) this;
           }

        publ   ic Criteria andUpdatedByNotIn(List<    String> valu  es) {
                    addCriterion("u pdated_by not in", values, "updatedBy  ");
                           return (C  rit   eria) thi   s;
        }

             publi c Criteria andU  pdatedByB       etween(String v  alue1,     String va    lue2) {
                addC        rit erion("u  pdate     d_by between", value1, v  alue2, "updatedBy");
                return (Criter    ia) thi     s;
           }

        public Criteria an      dUpdat   edBy  NotBetw     een(String value1, String value2) {
                               addCriterion("  updated_by not between", value1, value2, "updatedBy");
             return (             Criteria)    this;
           }

              public  Criter   ia andAdminIsN      ull() {    
              a       ddC rit erion("admin is null");
                   return  (Crite ria) this;
        }

        public    Crite r ia andAdmin   IsNo        tNull() {
            addCriterion("ad  min is not null");
                    return  (C riteria) this;
         }

             public Cr       iteria andAdm inEqualTo    (String value) {  
               addCriter   i  on    ("adm    in =", value, "admin   ");
            return (Criteria) this;
        }

             public Criteria   andAdminNot  Equal     To(String value)           {
                    addCr iteri   on("    admin <>", val    ue, "admin");
            return (  C     r  iteri a) th             is;
          }

                   publ  ic Cri  t eria andAdminGreaterThan(String value) {
            addCriterio    n("admin       >",    valu    e, "admin         ");
                return     (Criteria) this;
                }

        publi   c Criteria andAdminGreate   rThanOrEqualTo(Str  ing value) {
               addCriterion("admin >="     , value, "admin");      
            return (Cr       iter        ia) this;
        }

        public Crit eria andAdminLessThan(S  trin      g v  alue) {
              a    ddCriterion("adm   in <", value, "admin");
              return (Criteria) this;
        }
   
           public Cr     iteria andAdminLe  ssThanO   rEqualTo(Stri     ng value) {
             a   ddCrite   rion     ("admin <=",    value, "admi   n");
              return (Criteria) this;
        }

        pu        blic Criteria     a        ndAdm      inLike(String v  alue) {
            addCriterion("admin     like", value     , "admin");   
                    retu    rn (Criteria) t   his;
        }

        publi  c Criteria andAdminNotLi     ke(String value) {
                 addCriter   ion("admin    not like", value , "      ad  min");
            return  (Criteria) this;
        }

           public Criteria andAdminIn(Lis        t<String>   values) {
            addCriterion("admin in", val ues, "admin");
              re   turn (Cr        iteria)  this;
        }

        public Criteri    a andAdminNotIn(List<  String> va  lu es) { 
            addCriterion("admin  not in", values, "admin");
                return (Criteria) this;
        }

        public Cr  ite      ria and AdminB   etween    (S    tring value1, St     ring value2) {
                  addCriterion("admin b etween", value1, value2, "adm   in");
                    return (Criteria) this;
        }

        public Criteria   andAdminNotBetwe      en(Str    ing value1,          Strin    g value2) {
            addCriterion("   admin not between", value1, value2, "admin");
            return (Criteria) this;
        }

        public C        rite   ria andViewerIsNull() {
              ad      dCriterion    ("view  er is null");
                    r   eturn (Criteria) this;
                   }

        public Cr   ite    ria andViewerIsNotNull() {
            addCr  iterion   ("viewer is not null  ");
            return (Criteria) this;
        }

        p     ublic Criteria andViewerEqualTo(Strin   g value) {
            a  ddCriterion("viewer =", va   l     ue, "viewer");  
            r    etur n (Criteria) this;
           }      

        public Criteria a  ndView   erNotEqualTo(String value) {
            addCriterion(           "viewer <>", va    lue, "viewer");
            return (Criteria) t      his;
        }

        public Cri  teria andViewerGre  aterThan(String val ue) {
              addCriterion("viewer   >", value, " viewer");
              return (Criteria) this;
           }

            pu     b   lic Crite     r   ia   andViewerGreaterThanOrEqualTo(String value) {
            addCriterion("viewer >=", value, "viewer");
            return (Criteria) this;
          }

        p    ublic Criteria andViewerLes              s              Than(String value) {
            addCriterion("  viewer <", value, "viewe  r")    ;
            retu   rn (    Criteria) this;
        }

        publ    ic Criteria andView    erLe  ssThanOrEqualTo(String value) {
               addCriterion("viewer <=", value, "viewer");
              return (Criteria) this;
        }

        public Criteria andViewerLike(String v       alue) {
            addC   riterion("viewer like", value, "vi   ewer");
              return ( Criteria) thi      s;
         }

        public         Criteria a ndViewerNotLike(String value) {
            addCriterion("viewer not like", value, "viewer");
            return (Criteria) this;
        }

        public Criteria andViewerIn(List<String> values) {
            addCri   terion("v  iewer in",  values, "viewer");
            r    eturn (Criteria) this;
        }

        public Criteria andViewerNotIn(List<String> values) {
            addCriterion("viewer not in", values, "viewer");
             return (Criteria)     this;
        }

        public Criteri  a andViewerBetween(String value1, String value2) {
               addCr      iterion("viewer between   ", value1, value2, "viewer");
            return (Criteria) this;
        }

        public Criteria andV        iewerNotBetween(String value     1,      Str     ing value2)    {
            addC   riterion(    "viewe  r not bet ween", value1, value2     , "viewe    r");
            return (Criteria) this; 
        }
    }

      /**
     * s2_database
     */
    public static class Criteria exte   nds GeneratedCr    iteria {

        protected Criteria() {
            super();
         }
    }

    /* *
     * s2_database null
     */
    public static class Criterion {
        private String condition;    

        private Object value;

        private Object s   econdValue;

        pr  ivate boolean noValue;

        private boolean sin   gleValue;

        priv ate boolean betweenValue;

        pr ivate boolean listValue;

         private S      tring type  Handler;

        public String getCondition() {
            return conditi  on;
           }
   
        public Objec   t getValue()     {
            retu     rn value;
        }

        public Object getSecondValue() {
                 r             eturn secondValue;
        }

             public boolean is    NoValue()      {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        publi    c boolea n isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public Str  ing getTypeHandler() {
            return    typeHandler;
        }

            protected Criterion(String condition) {
            sup        er();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String conditio   n, Object valu     e, String typeHandler) {
            super();
            this.condi tio   n = condition;
            thi     s.value = value;
            this  .typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue  = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criter   ion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}