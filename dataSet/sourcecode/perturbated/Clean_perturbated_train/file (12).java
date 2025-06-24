package    org.omg.DynamicAny;


/*     *
* org/omg/DynamicAny/_DynStructStub.java  .
* Gen   erated by the IDL-to-J    ava compiler (portable), ver  sion "3.   2"
* from c:/re/workspace/8-2-build-windows-amd64-cygwin/jdk8u131/8869/corba/src/share/classes/org/omg/DynamicAny/DynamicAny.idl
* Wednesday    , March   1  5, 2  0 17 1:25:04  AM PDT  
*/


/**
    * DynSt   ruct ob    jects su  pp  ort th e mani     pulation of IDL      struct and exceptio       n values.
    * Members of th      e exceptions      are   handled    in the same way     as m embers of a struc t.
    */
public class _DynStructStub extends org.omg.CORBA.portable.ObjectImpl i             mplements     o    rg.o  mg.DynamicAny.Dy   nStruct
{
  fi nal public static java.   lang.Class        _opsClass = DynStructOperations.cla     ss;



  /*   *
        * Returns the na  me of     the member at the cur     ren  t pos    i   tion.
             * This operati             on may return        an empty string si nc        e the   Ty        peCode of the va   lue being
        * manipula      ted m   ay not        con tain the   names of members    .
        *
          * @exception TypeMism     a tch if the DynStru ct represents an empty excepti  on.   
          * @exception  Invalid   Value if the curren   t   posit     ion does not indicat  e a mem  ber
          */
  p ublic String current_member_name () t hrows org.omg.DynamicAny.DynAnyP ackag   e.Type     Mismatch, org   .   omg.    DynamicAny  .DynAn   yPackage.InvalidValue
  {
      or     g.omg.CORBA.portable.ServantObject $so =    _servant_preinvoke ("current_member _n    a  me", _opsClass);
      Dyn   StructO    perat  ions     $sel    f = ( DynStructOpe    ration    s)    $so.serva     nt;
   
      tr  y {
         return $se        lf   .c   ur rent_member         _name ();
        } finally    {    
          _se    rvant_postinvoke ($so);
              }
  } // current_member_nam       e


     /  **
        * Return   s the TCKind associat  ed with    the member at the curre  nt position.
        *
           * @exception       TypeMi    smatch if     t  he DynStr uct r    epresents an empty exc  e     pti on.
                  * @exception InvalidValue if the current position does not indicate a             member
          */    
  public org.omg.CORBA.TCKind current_m     embe      r_ki   nd () throws org.omg         .D ynami cAny.DynAnyP        ackag    e.TypeMis   match, org.omg.DynamicAny.DynAnyPackage.   InvalidValu e  
     {   
               org.omg.CORBA.portable.Se       rvan       t    Object $s  o =     _se      rvant_preinvoke   ("c urrent_  member_kind", _       opsCla       ss);
                   Dyn    Struct  Operat  ions  $self = (Dy   nSt      ruc    tOperatio    ns)    $so.serv    ant;

      try {
                retu   rn $self.current_member_k     ind ();
      } fi   nally     {
          _ser      vant_postinvoke ($s  o);
        }
  } // current_me  mb          er_          kind
    
   
  /**
                    *     Returns a s     equence of NameValuePa      irs des   cribing th                 e n    ame and the value of   each member
                     * in the str         uct as      sociate   d with a D ynStru    ct      object.
           * T   he sequence conta   ins members in the sa   me order as the   declaration order of members
        * as i ndicat    ed       by t          h  e D  ynStruct's TypeCode.     The current position is not         affected.
        *      The me   mber names in the     return       ed sequence will be empty strings if the DynStruc   t's TypeCode
        * does not   cont ain      m         e  mb er names.
           */
  public   org.omg.Dynamic       Any.NameValuePair[]      get_members (              )
  {
      org.omg.C  ORBA.portable.Se    r  v   ant  Object    $s    o =   _serv    ant_preinvoke ("g     et_m   em  bers   ", _ops        Class);
      DynStruc         tOperat    ions  $self = (DynSt       r   uctOper      atio   ns) $so.servan      t;

      try {
           ret urn $sel    f.ge   t_members  ();
          }   f  inally {
                _ser      vant_postinvoke   ($so);
      }
  } // ge   t_me           mb   e    rs


   /**
        *        Initiali   zes t   he stru     ct    data value associated with a D  ynStruct object f  rom   a sequence of NameValuePai         rs.               
          * The op      e  ration se t     s the current posi  tion       to z    ero if       the  passed sequ ences h   as    non-zero le  ngth. Otherwi  se,
                   * if an empty sequence   is pass ed,     the curren   t posit    ion i  s set to -1.
             *      <P>M  embers          mus    t appe ar i   n  the      N   ameValu       eP   airs     in t        he ord er         in which       they appear         in the IDL specification
        * of     the struct as indic  ated   by the DynStruct's Ty   peCode or they mu        st be empty stri     ngs.
          * The o   peration mak  es no att     em   pt t    o assign m  ember values base         d  o   n member names.
            *          
        *         @exception Type      Mis  match if    the me                mber names supplied in the passed  sequen   ce do no     t match      the
                 *                 corresponding member name in the    DynStruct  's TypeC     ode and  they are not e mpty        stri ngs
                     *     @exception           I  nva    lidV  al  ue if the p   assed sequ   ence has a num  ber of  elements t hat disagre    es
           *                   with the number of members as indicated b  y the DynStr      uc t      's         TypeCode
         */
  publi  c void set_members (org.omg.DynamicAny.NameVal  uePair[] value  ) thro    w    s org    .omg.Dynam         icAny.DynAnyPackage.Type     Mismatch, org.om    g.  Dyna micAny.            DynAny     Package.InvalidValue
  {
           org.omg.CORBA.portable     .       ServantObject $so = _servan t_prein    voke (  "set_   members",          _opsClass);
        DynStructOperations       $            self = (DynStruct    Op erations) $    s        o.servant;
   
      tr  y {
               $self.se   t_members    (value);
        } f   inally {
          _servant_        postinvo          ke ($so);
      }
    }    //    s             et_memb ers
    

  /**   
              * Ret urns a  s        equence of NameDynAnyPair                s  descri bing the name and the    value of ea   ch   member
              *     in the str   uct       associated       with a DynStruct obje  ct.
           * The  seq      uence contains m     embers in      the     s     ame   order        as the decla    ration order of members
         * as     i     ndi     cated by the D   ynStruct's TypeC          ode  . The   current position is no   t     a    ffected.
                 * The member na   m      es in    the r  e            tu  rned sequence  will be   empty    strings i   f the DynStruc        t's Typ eCode
              * does no     t      contain  member           names.
            *      /
  pub       lic org.omg.DynamicAny.NameDynAnyPair[] get_members_as_dyn_     any ()           
  {
      o  rg      .om        g.CORBA.portable.Se     rv  antObject $so = _servan   t_preinvoke ("get_members_as_dy n_any  ", _opsClass       );
      DynStructOp  erati    ons  $self  = (DynStructO       peratio    ns) $so.servant;

      try {
          r  eturn $self.g     et_m      em       bers_          as_dyn_any ();
      } fin ally {
          _se rvant_postinvoke ( $so    );
              }
  } // g   et_memb        ers_as   _dyn_any


     / **
        * Initi a    l   izes the struct data valu     e      assoc  iated with a DynStruct obj    e      ct from       a sequenc   e of NameDynAnyPairs.
        * The operation sets t h        e current pos          ition to      ze    ro if the passed sequences has  non-zero        length.     Oth   erwise,
        * if an emp        t      y sequenc e is  passed, the c         urrent position is set to -1.
        * <P>Members must app   e   ar in the N     ameDyn   AnyPa     irs in   the order in which   they appear in   th  e IDL specifi    c    a  tion
        * of t  he s     truct      as in           d   icated by    t  he DynStruct             's Typ  eC      o   de or they must be    empty str  ings.    
         * The operat  ion m      akes no att  emp   t to a  ssign mem      b   e       r  v   al   ues   bas    ed on   membe r names.    
        *
        * @        exception TypeMismatch if    the member names supplied in the passed sequence do          not        match the
           *            c            orrespondi  ng memb er name in th e   DynStruct's TypeCode and th      ey   a      re n    ot empty strings
        * @exception Invalid  Va  lu  e      if the passed sequence has a    numb   er o   f    elem ents th   a      t disagrees
           *                 with the numbe r of mem    bers as ind     icated by the DynStruct  's TypeCode
        */
     public void set_mem   bers_as  _dyn  _any   (org.omg.Dynami           cAny.NameDynAnyPair[] value) throws org.omg.Dy     nam  icAny.Dy  nAnyPackage.Typ      eMism atch, org.omg.Dynami         cAny.Dy nAnyPack           a    ge.Inv  alidValue
    {
                    org.omg.    CORBA.portable.Serv antObject $so = _ser     vant    _pre        invoke   ("set_          members       _as_d   y  n_any", _ops         Clas                     s       )      ;
      DynS truc    tOper     ation    s  $self = (DynStru ctOpera t  ions) $so.s    erva  nt;

       tr  y {
           $self.set_mem    bers_as_dyn_any    (value    );
      } f     inally {  
                      _ser   va   nt_p     ostinvoke ($so); 
      }
     }           // set_  members_as_dyn_any


  /**
        * Ret       u         rns th    e Typ eCode ass  ociate  d with t   his DynAny object.
              *                  A    Dyn      Any obj  ect is created with a TypeCode value    assigned to it.  
              * Th  is TypeCod  e   value det      e    rm ines   the type o    f the v   alue han dled    through the DynAny object    .
        * Note that t         he TypeCo  de a ssocia ted   with a DynAny o    bject      is init  iali     zed at th      e ti    me the
        * D       ynAny i   s created and cannot be  changed during lif       eti  me o             f th  e D      ynA                ny object.    
                     *
         *       @retu    rn Th     e TypeCode associ   ated with    this Dy     nAny o   bject    
        *   /
  public org.  omg.CORB  A.        Ty peCode type ()
  {
      org.omg.    C  ORBA.             portab   le.ServantObje    ct $so = _ser      vant_prei    nvoke            ("ty      pe",   _opsClass   );
       Dyn   StructO perations  $se       lf =                (DynSt  ruc    tOperations) $so.servan  t;
   
              try {
         return $self.t   ype ();
       }     finall    y {
                         _se    rvant_postinvoke ($so);
          }
  } //      type


  /**
         *    I  ni   t   i   alize     s the value associated with    a  DynAny object with the va   lue    
              * associa   ted with an other Dy          nAny          object.
          *     The current position       o       f       the      target DynAny is set   to zero f      or        values t  hat have compone  nts
         * and to -1 for  values that do not have    com    pone  n   ts.
        *
          * @par     am        dyn_any
        *      @e          xce   ption        Ty       peMismatch if the t    ype o    f the passed DynAny is not equivalent to the type of t    arget DynAn  y
                                                */
     public void a     ssi    gn (org.omg.Dyna      micAny.DynA   ny dyn  _any) t    hrows org.         omg.DynamicAny.DynAnyPackage.TypeMismatch
  {
      org.om    g.CORB A.po   rtable.ServantObjec    t $so = _servant_preinvoke ("as  sign",   _opsC        lass);
           DynStructOperatio  ns  $self =   (DynStructOperations    ) $    so.    servant;

      try {
                     $self.assign ( dyn_any);
       }        f    inally {
               _servant_postinvo  ke ($       so);
              }
  } // assign


      /**
              * In  i   tia      lizes      th       e valu   e associated   w                  ith a Dyn     Any    o      bject w   ith the value contained in an any     .
         * The curr         ent position of the   target DynA     ny is    set to zero for values that hav e components
                 * and to             -1 for v     al   ues that do not h ave components.
        *
        * @   exception TypeMismatch if th    e type of the passe    d Any     is not equivalent to the type of        target DynAny   
            *     @     except  ion            InvalidValue if the p assed Any does    not contain a lega   l v  alue       (such as       a null string)
         */
  pub   lic      void from_   any (org.omg.   CORBA.Any v     alue)         throw         s org.omg.DynamicAny.DynAny  P         ac  ka  ge.TypeMismatch, or       g  .omg  .DynamicAny.DynAnyPacka     ge.InvalidV    alue
  {
             or      g.o  mg.C              ORBA.portable      .ServantOb  ject $so = _s   ervant    _preinv   oke ("from_any" , _o psCl    ass);
        Dy n       StructO   pe                     rations   $s    elf = (DynStructOperations)   $so.serv ant;

          try {
            $self.from_any     (value);            
          } finally {    
                               _serva    nt_postinvo     ke     ($so);
       }
  } // fr     om_any

    
  /**
              * Cre    ates an any       val      ue fr       om a D       ynAny object.
          * A    copy of the Ty   peCode ass oc   iat    ed with the DynA     ny object    is a ssigned to    t      he   resulting any.
        * T     he valu e associated w     ith  the Dyn    Any obj  ect is copied into the any .
                *
                 *   @return a new Any object with       t h e same     v   alue and  T  ypeCode            
        */
       public org.omg.CORBA.Any    to_any ()    
       {
           org.    o  mg.CORBA.portable.Servant    Ob         ject $so = _servant_preinvo  ke ("to_any", _opsClass);     
                     DynStructOperations    $se    lf = (DynStructOperations) $so.serv  ant;

       t       ry {
              return $se l f.to_any     (    );
           } fina        lly { 
          _servant_  post     invoke   ($   so);
      } 
  } // to_any

  
  /**
                  * Compare  s two DynA    ny va      lues for      equality.
          *   Two DynAny values are equal if their TypeCod   es    are equ  ivalent a  nd, recursively        , all component DynAnys
             * hav   e  equal value         s.  
          * The current position of the two DynAny   s b       ei       ng compared has     no e  ffect on the re      sult of      equ     al.
                   *
        *   @r     eturn t       rue of   the Dy   nAny       s are equa l,  false otherw      ise
            */
      public boole    an         e     qual (org   .omg.DynamicAny.DynAny dyn_a      n y)
  {
           org      .omg.CORBA.p    ortable.Serva ntObject $so      = _servant_prein    vo  ke ("equal", _ops        Cla         ss);
      D ynStructOp eration s   $self  = (DynStructOpera  tions) $so.servant;

             try {      
            r  eturn $se     lf.equa  l (dyn_an  y   );
          } finall    y {
            _serva  nt_postinvoke ($so);
      }
  } //                 equ    al

         
  /**
        * Dest   roys       a DynAny object.
         * Thi  s oper   ation frees a  ny r  esources use d    to repre     sent       th   e data value  a  ssoci   ated w        ith a DynAny object.
        * It must be      invok   ed on references obtained from            one of the      c reation     opera     ti   ons on the O          RB interface
         * or on a      reference returned by DynAny.copy() to avoid resourc e   leaks.
          * Invoking              destroy on   comp  onent               Dy   nAny ob jects      (fo     r       exam ple, on objects   ret urned b   y th    e
        * curre   nt_component operation)   doe s no    thing.
              * Destruction of a Dyn   Any o   b       ject      impl  ies d     estruction of all DynAny        object     s o  b      taine       d from it.  
         * Tha   t is,     re   ferences to components of       a de  st royed D yn     Any b           ecome invali   d.      
                    * Invocations      on such references ra          ise  OBJECT_N   OT_EXIST.
                  *  It i    s poss     ible to manipulate a co     mponent o      f a DynAny beyo  nd the life time of the DynAny
           * from w  hich the component   was obtained by ma k    ing a copy  of the com    ponent with   the copy    op      eration
        * b   efore destroying the Dyn   Any from   which the co     mp    onent was obta       ined.
        */
    public vo  i  d destro   y          ()
  {
      org.omg.CORBA.portable.     ServantObject $so = _servant_preinvoke ( "destroy", _opsClass);        
      DynSt  ru    c  t      Ope           r   ations  $self = (Dyn  S   tructOperations) $so .servan   t;

      try {
            $sel     f.     des          troy ();
           } fina     lly    {
                 _serv      ant_postinvoke   ($so)      ;
      }
     } / / destroy


  /**
          * Cre      ates a new D   ynAny obj   ect whose value is a deep copy   o         f the DynAn     y o  n which it       is invoked.
              * T  he operation i     s polymorphic, that is,       invoking it on    o ne of the types  d erived from DynAny,
        * such as Dy    nS   truct  , creates the derived type but returns its            ref      erence as    the D        ynA         ny base ty     pe.
        *
            * @return    a    d    eep    copy of the DynAn   y object
        *  /
  public org.omg.Dy    na     micAny.   DynAny copy ()
  {
        org.omg  .CORBA.   portable.ServantObject $so = _        s     ervant_preinvoke (   "copy", _opsClas   s);
            DynStructOperations  $self = (DynStructOperations)        $       so            .servant;

      try {
         retu rn   $self.co   py ();
      }     finall            y {
              _serva        nt_postinvoke ($so   );
      }
  } // copy


  /**
           * Inserts a b    o olean  value into the DynAny.
               *
          * @excep    t       ion I    nvalidValue i      f this DynAny has components     but   has a  cu   rrent position of -1
              * @e         xception       TypeMisma tch if called    on a Dyn   Any         whose cur  rent        com      ponent       i    tself has compo    nents     
              *   /  
  public     void i       nsert_boole     an (b     ool       ean    va  lue) th   rows org.omg.D        ynamicAny.DynAny   Packag e.T    ypeMismatch, org.omg.D       yn            amic   Any.       Dyn AnyPack   age.In    validValue
    {
                 org  .omg.CORBA.portable.ServantObje ct $so      =   _servant_  prein   voke ("ins   ert_boo    lean"   , _opsCla    ss);      
       DynS  tru  ctOpe   ra      tions  $sel  f =        (DynStr uctOperations) $so   .servant    ;    

      try {
         $self.insert_boo    le              an (value);
      } finally {
                _servant_postinvo    ke ($so);
      }
  }     / / insert_boolean


  /**
             * Insert  s a     byte value     into the DynAny. The    IDL octet data type is ma          pped to t      he Java by t       e d  ata type.
          *
            * @exception InvalidValue         if this D     y  nAny has components        but    has a curr        ent p   osition of -1
         * @ex   ception TypeMismatch i f called on  a DynAny wh   ose     current component itself has c omponents
        */
         p ublic v    oid inser  t_octet (by     te v      alue) t    hrow  s      org.o   m       g.DynamicAny.Dyn     An      yPackage.TypeMismatch, org.omg.DynamicAny.DynA nyPa   ckage.InvalidValue
  {
      org.omg.CORBA.portable     .ServantObject $so = _servant_preinvoke ("in    sert_octet",      _ops    Clas s);
       DynStruc  tOperatio    ns   $self = (DynStructOperation   s)   $so .s      ervant;

       try {
          $sel    f.insert       _octet       (value)    ;
            } fin  ally {    
              _servant_postinv   oke ($so);
      }   
     } // in          sert_octet


   /**
        * I  nser   ts a char value into the DynAny     .
          *
             * @except   ion Inva    lidValue if     this DynA     ny has components but has      a current position of   -1
         * @e    xception  TypeMismatch if calle  d on a DynAny   whose current compon     ent itself has compo       nen     ts
           */
          public void insert_     char    (char va lu    e) throws org.omg.DynamicAny.DynAnyPack       age.T      ypeMismatch   , o   rg.omg.Dynamic  A  ny.DynAnyPacka     g   e.            I nvali    dValue     
  {
         org.omg.CORBA.portable.ServantO      bject $so = _servant_preinvoke (" inser   t_char", _opsCla   ss            );
      DynStructOpe   rations  $self   = (DynStruc        tO    perat      io    ns)       $so.s   e          rvant;         

            try {    
         $self.i  nsert_ cha           r (value);
      } fi           n        ally   {
          _servant_postinvoke ($so)   ;
         }
     } //            in      se   rt_char


  /*     *   
              * Inserts a  sho rt value into the DynAny.   
         *
                    * @e  xcep    t          ion InvalidValue if this DynAny has com    po       nents but has a cu  rre n     t posi               tion of -1
             * @exception TypeMismatch if ca  lled on a DynAny   whose current component itself has components    
              */
  public v    oid insert_short (short va  lue)      throws        org.omg.Dy    nam    ic Any.DynAnyPackag  e.TypeMismatch, org.omg.Dyna     micAny.DynAnyPa  ckage.InvalidVa lue
  {
      org   .omg.CORBA.portable.    Serva    ntObject $so = _     servant_pr    e        i      nvoke  ("in     sert_short    ", _  opsClass)   ;
             DynStru  ctOperations  $sel       f = (DynStructOp  erations)      $so    .servant  ;
     
             try    {
                     $self.ins  ert_short (value);
        } fi    nal  ly {
           _servant_postinvok  e ( $so);
       }
     } // ins     ert_  short


  /**
          * In    ser  ts     a short value into the D          ynAny. The IDL ushort d  ata type is map      ped to the Jav              a short   data type.
        *
           * @e     xc    eption I      nvalidV  a   l ue if this    DynAny has components but ha    s a      curr  ent position of -1   
          *  @excepti  on TypeMismatch i   f   cal    le d               on a DynAny wh    ose current component itself h   as compone   nts
          */
  public       v   oid inser       t_ushort  (short val   ue)         throws       org.omg.DynamicA       ny.DynAnyPa     c   kag e.TypeMismatc   h,       org.omg.Dynam    icAny.Dy     nAnyPackage.      In    val   idVa  lue
  {
      org.o   m   g.CO   RBA.port     able    .Ser     vantObjec                 t $so =    _servant_p reinvo   ke  ("insert_ush       ort", _opsCla  ss);
               Dy       nStructOperations   $s    e     lf = (DynStructOperati      ons) $so.servant;

          try {
               $self.insert_ushort (valu  e);
          } final   ly {
           _  servant_p     ostinvoke ($so);
            }
   } // insert_ushort     


  /**
        * I      nser  ts a  n i     nteger value      into th e                  DynAny    . The         IDL l   o    ng data type is       mapped to   the J   ava int data type.         
          *
            *   @   exception InvalidValue if this D ynAny   ha s compo  nents but h   as a current         position of -1
                  *  @ex  cepti     on TypeMismatch  if called on a DynAny who  se c    urrent    component     itse   lf     ha   s components
                  *     /
  public v o  id insert_long (i     nt value)   throws or                    g.      omg.Dy  n   a  micA          ny   .D      ynAnyPackag e.TypeMismatch, o rg.omg.DynamicAny.DynAnyPack    age.Invalid   Value
  {
       org   .om   g.CORB     A.porta   ble.ServantObject $so = _servant     _pre    invoke ("in   sert_long", _o   psC    la   ss);
       DynStructOperations  $self = (DynStructOper ations) $so.s  erv    ant;

      t   ry {
            $self.insert_long (value);
      } finally {
             _serva    nt_postin  voke ($so);
                }
  } // insert_long


  /**
        * Insert     s an integer value into the DynAny. The  IDL ulong data type is mapped to   the Java int data type.
             *
        * @exception InvalidValue if th     is Dyn   An y has c   om    p       onents but h as    a curren  t po sition o   f -1
          * @exception TypeMismatch   if ca     lled on     a DynAny whose   cu   rrent component itself has   components 
        */     
                           publ    ic void inser         t_ulong (  int val ue) throws org.          omg.Dyn     amicAny.DynAnyPac  kage.TypeMismat      ch, org.omg.DynamicAny.DynAnyPack   ag e.  In   valid    Value
        {
            org. omg.CORBA  .por  tabl    e.Ser    vantObjec  t     $so =   _serva    nt_pr  einv     oke ("in   sert_ulong"    ,      _o    psClass);
          DynStructOp erat    ions  $self = (DynS  tructOperat    ions)          $so.servant ;

            try    {
          $sel    f.insert_u  long (value);
        } f   ina       ll y {
          _ser        va  nt_po   sti          nvoke ($so);
      }
  } // insert_ulong


  /**
        * I    nserts a float value into th  e DynAny.
        *
                * @exce  ptio   n Inval         idV  alue if this DynAny has  compon  ents but has a      current position of -1
                * @except     io   n Ty     p    eMi smatch     if called    on a     DynAny whose current compo  nent      its e  lf  has components
        */
   public void     inse  rt_float (float va  lue)   thr o             ws  org.om g.DynamicAny.DynA    nyP ackage     .   TypeMisma    tch, org.om                  g     .Dyn amicAny.DynAnyPackage.InvalidValue
  {
      org.omg.CORBA.portab  le.ServantObject $s   o = _    servant                  _preinv      oke ("inse rt_       float",  _opsClass );
       DynStructOp   er    atio         ns       $self = (     DynS   tructOperat  ions) $so.serv ant;

      try { 
                    $sel   f.insert_flo   at (value)     ;
            } finally {
          _servant_pos     tinvoke ($so);
      }
  } // insert_  float


       /**
                 * Inserts a     double v      alu    e in t    o the D      ynA     ny.
          *
         * @  ex ception InvalidV       alue i      f  th      is Dy     nA          ny ha   s componen    ts b ut h    as a current posit      ion      of -1
          * @exception TypeMis       match if called on     a Dy  n    Any whose c    urr    en    t c   omponent itself has compo  nents
        */
  publi c void insert_d  ouble   (  doubl   e   value) th      rows  org.om   g.DynamicAny.D        ynAnyPackage.T       ypeMismatch,   org.o     mg  .DynamicAny .D  yn   AnyPacka   ge.InvalidValue
   {  
      org.omg.CORBA. por tabl    e  .ServantObject $   so = _servan    t_preinvoke ("insert      _double", _ops    Class)   ;
      DynStructOpe rati    ons  $self   = (DynStructOperation       s) $    s    o.servant          ;
 
         try {
         $self.insert_doub     le (v    alue);
      } finally {
              _  servan  t_    post   invoke ($so  );
            }
  } // insert_double


  /**
          * Inserts a string value into the DynAny.
          *    Both bounded and unbounded strin   gs are   inse rted using this me  t          hod.
            *
        * @exception In   val idValu e if this     DynAny has co    mponents but has a cur    rent positio  n of -1
                   * @exception Inva      lidValue if the    string insert    ed is longer   tha     n the   bound of a bounded string
            *      @exce      p  tion TypeMismatc     h if called on a DynAny whose c     urr   ent com pon    ent itsel    f has components
        */
  public void   insert_str       i  ng (String value) throw    s or g.omg        .Dyna  mic  Any.DynAnyPackage.TypeMismatch, org.omg.  DynamicAn  y.DynAnyP  a         ckag   e.InvalidValue
  {
      org.    om  g.CORBA.portable.Ser     vantObject $so = _se     rvant_preinvoke ("insert_stri   ng", _opsClass)  ;
      DynStr   uctOperations  $s      elf  = (DynStr    uctOperat ions) $  so.servant;

      tr  y {       
                 $self.insert_string (  value)   ;
         } finally {           
          _s  er   v  ant_posti   nvoke ($ so)  ;
       }
  } // i nsert_    string


  /**          
                * Inserts a reference to a CORBA obje  ct i  nto  the DynAny.
        *
         * @exceptio   n InvalidValue if this DynAny has compon    ents but has a current position of           -1
        * @ex     ception Type  Mismatch i   f  called    on a              D ynAny       whose cur    rent    componen                          t        i     ts elf      has com p   onen   ts
            * /
        public v      oid  insert_ref  erence (org.om   g.CORBA.Object value) t      hrows org.omg.DynamicAny.DynAny P     ackag  e.TypeMismatch,      org. omg.Dy       na    micAny.Dyn         AnyPackage.In     validVal   ue
   {
      org.omg     .CORBA.portable.Servant  Objec  t $s  o =   _servant_preinvoke ("insert_reference     ",   _opsCl     ass);
      DynStruc t     Operations  $      self = (DynStr   u  ctOperat          ions) $so.ser  vant;

                t  r  y {
               $self.inser   t_re      ference (value  );
      } finally {
                _servant_p   ostin     vo   ke ($  so);
         }    
            } // i     nsert_reference


  /**
        * In serts a                TypeC       ode       object into the DynAny.
        *
        * @exceptio n Invalid  Value if    this DynAny      has co mp        onents bu  t has a current posi     ti on o   f -1
                     *      @exception TypeM    ismatc  h     if called on  a DynAny whose cu      rrent compo     nent itself has c o  mponen   ts      
        */
           pub    lic v    oid inse    rt_typecod e (org.omg.CORBA.Typ    eCode value) throws org    .omg.DynamicAny                 .      Dyn      An  yPacka    ge.Typ     eMismatch,    org.omg.Dynam       icAn      y.D  ynAnyPackage.Inva  lidValue
     {
           org.omg  .CORBA .por     table.Servant    Object $s    o = _    s e           rvant_pre     i   nvok   e ("insert_typecode"   , _ops   Class);
      DynStructOp  erations    $self    = (DynStruct Operati                ons) $so.servant;  
       
        try     {
             $self.insert_typecode (value) ;
      } finally {
                _ser   vant_post  invoke ($       s       o    );
      }
  } // insert_typec   ode


  /**
        * I     nserts a long valu  e into the Dy            nAny. The IDL l  ong long data t ype    is map ped to the J      ava lon g data         type.
            *
        * @ex       c   eption InvalidValue i f this Dy       nAny has com   p    onents but has a curre     nt position           o   f     -    1
        * @e   xception         TypeM        ismatch if called on a DynAny       who    se current component its    elf ha   s  compo        nents
          */     
  public voi   d inse   rt_lon       glong (lo ng value) throws        org  .omg.Dynami  cAn   y.D  y       nAnyPa        cka  ge.TypeMismatch, org    .omg.DynamicAn      y.      Dy nAnyPackage.Invali   dValu  e
  {
      org.omg.    CORBA.   port  able.ServantObject $so = _servant_preinv            oke ("inse         rt_lo    nglo    ng",      _opsCl   a    ss) ;
      DynStructOperations      $self = (DynStructOperations)     $so.s  erv     ant;

       try {   
         $self.insert_longlong (valu  e);
      } fi    nally {
           _servant_postinv     oke ($so);
            }
  } //  inse   r   t_l   onglong  
  

       /**      
           * Inserts a l o   ng value into the DynA ny.
        * Th  e   IDL unsigned    long long   data type is map       ped to the Java       long data t            ype.
        *
               * @e       xce    ption InvalidVal   ue if thi   s DynAny has   c    ompone  nts   but h   as a current positio    n of -1 
        * @exception TypeMismatch if called       on a DynAny     whose curr       e  nt component it    self has components
          */
  publ   i  c void  insert  _ulong   long (l  ong value) throws org.o   mg.Dynami      cAny.DynAnyP   ackage.TypeMisma  tch, org.omg.Dy  namicAn y.DynAnyPacka    ge.InvalidValue
  {
      org.omg.CORBA.porta    ble.ServantO     bject $so = _serv    an t_preinv  oke ("ins   ert_ulongl      ong", _ opsCla   ss);
         DynS tructOper   ations  $self = (DynStru ctOperations) $so.ser     vant;

                try {
           $self        .insert_ulonglo  ng (value);
         } finally {
          _ser   vant_po   st    invoke ($so)  ;
           }
  } /      /         insert_ulonglong


     /**
            * Insert    s  a     cha        r value into the D    ynAny. The IDL   wchar data type is mapped to the Java cha  r       d     at    a      type.
            *
        * @exception I   nvalidValue if   this      DynAny    has c      omponents b      ut has      a current p os  ition of -1
        * @ex ception   Type  Mismat   ch if called on a DynAny w   hose             current c  omponent    itself has co    mpo  nents
                         */
  public     void            inser t_wcha   r (char value) throws        org.o    m  g.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.Inval    idVa lue
  {
      org.omg.CO  RBA.portabl   e.ServantObj      ect $s     o = _servant_preinvoke ("insert_wc       har    ", _opsCl     ass);
      DynStru   ctOperations  $s    e  lf = (D ynStructO    peratio    ns) $so.servant; 

      try {
         $self.insert_wcha r (value);
                             } fin   al  ly {
                     _servant_postinvoke ($so)   ;
      }
  } //    in    sert_wchar


  /  **            
         * Inserts a str  ing v        alue into the Dy   nA   ny.
             *     Both bounde   d a              n  d unbounded st   rings are inserted         using this method.   
        *
            * @exce   ption Invali  dValue i    f thi     s   DynAny has c      omponents but ha  s a curr  en  t po        sition   o    f -1
          * @exception Inva    lidValue    if    t  he string inserted is longer             than   the boun            d of a bo                unded string
               */
  public void          inse    rt_     wstring (String value) throws org.omg.D    ynamicAny.D  ynA nyPack    ag  e.TypeMismatch,    or     g.omg.Dyn     am  icAny.DynAnyPackage.InvalidV    al  ue      
  {
      org.omg.CORBA.portable.   Serv              antObjec t $      so = _se     rvant      _preinvoke       ("in sert_wstring", _opsClass);
      DynStructOperatio    ns  $self = (    DynStructOperations) $so.servant;

      try {
          $self.i      nse    rt_w    s      t      ring (valu e);
      } finally      {
                 _ser     vant_p    ostinvo   ke ($ so);
      }
  }       // i         nsert_wstring
         

  /**
              * Inserts an Any value into the Any r    epresented by this DynAny.
        *  
        *     @exception I  nval   idValue if thi  s DynA ny has       co     mponents but has a current   positio    n of -1  
        * @exce    ption Ty   peM           ismatc       h if calle           d      on a  Dyn  Any who   se   cu  rrent component i  tself ha       s     compone  nts
        */  
       public v   o      id  inser  t_any         (org.om  g.CORBA.Any val  ue     )              throw              s org.omg.DynamicAny.DynA   ny                      Package.TypeMismat   ch, org.omg.Dynamic      Any.DynAnyP    ack ag        e.Invalid    Value
  {
      o    r  g.omg.CO   RB   A.p      orta    ble.S  e       rvantObject $so    =   _se  r    van            t_preinvoke ("in            sert_any", _ops   Class);
      Dy nStruc   tOp erat    i      ons  $self = (Dyn       StructOpera  tions) $s   o.serv     ant;

             try {
         $self.   insert_a    ny (value);
      } finally {
                             _s  ervant_postinvoke ($so);
           }
                     } //      insert_any


  /**
          * In  serts    the Any value conta         ined in the paramete    r DynA  ny   in    to the          Any repr esented   by      this D ynAny.  
        *       
            * @exception InvalidValue if     this DynAny has components but h as a current p   osition of -1
        *        @   exc  eptio     n T     ypeMismatch if called on        a    Dyn  Any whose current comp  onent itself has  compone    nts
          */
  public   vo       id insert_dy n_any (org.                        omg.DynamicAny.DynAny value) throws   org.omg.DynamicAny.DynAnyPac    kage .Ty peMisma  tch, o          rg.omg.Dy    namicA   ny.DynAnyPa   ckage.InvalidValue
  {
        org.omg.COR    BA.portab     le .S     ervan   tOb       ject $so =   _servant_pr   einvoke ("insert_dyn   _any", _opsClass); 
           DynS  tr   uc tOperat     ions      $self          = (DynS  tructOperations     ) $so    .    se   rv   ant;

      t   ry {
                     $   self.  insert_dyn_   an         y (value);
      }    finall     y {
            _servant_posti            nvoke ($so);
      }
  }  // insert           _  dyn   _any

  
  /**
         * Inse  r    ts a refe    rence to     a Serializ   a  ble object into this DynA      ny.
            * T he IDL V    al   ueBase type is mapp  ed to    th      e J     ava Serializabl   e type   .
              *
           *    @exception In v   alidValue i      f this DynAny has componen   ts but has a curre     nt    position of     -1
                *    @exception TypeMis   match if called on a DynAny whos     e   curre  nt        com   ponent it    self  has c      omponents
        *  /
  public   void i   ns   ert_va l (java.io.Serializa  ble v    alue  ) throws org.   omg. Dyna   mic    Any.Dy      nAnyPackage    .TypeM  is    match, org.o   mg.Dyna   mi    cAny.DynA    nyPacka      ge.InvalidVa   lue
  {
      org.omg.CORBA.portable.Servan  tOb ject $so          = _ serv     ant_prei nv       oke      (" inser  t_val", _opsCla  ss);
         DynStructOperations  $self = (DynStructOperations) $so.serv        ant;

                  try {
         $self.insert_val (value);
             } finally {
          _serva nt_p  os       tinv      oke ($so );
      }
      } // insert_v             a  l


     /    **
                * Extracts the boolean value f  rom this Dy   nAny.
              *
        * @exception TypeMismat   ch   if the acces    sed     comp       one     nt in the Dy     nAny is of a type    
         * that is       not equivalent to the requested  type   .
           * @exception Ty    peMis      match if called on a   DynAny w      hose curr     e nt componen  t itself has components
         *  @exceptio  n InvalidV   alue if this D  ynAny has components but has a     curr   e      nt positio    n of       -1
                   */
  p ublic boolean get_bool    ean ()          throws      org.omg.Dynam      icAny.D    ynAnyPack       age.T      ypeMismatch, org.omg  .Dynam    icAny.DynAnyPack              age.InvalidV   alue
  {
       org.omg.CORBA.      portable.ServantObject $so = _servant_    p  reinvoke ("get_boolean", _o  psClass);
       DynS  tructOpera    t ions  $ self = (DynS       truc      t     Oper     ations) $so.servant;  

            try {
                return $self.ge      t_boolea      n ();
          }      finally  {
              _s   ervant_postinvoke        ($so);
        }
  } // get_boolean


  /**
            * Extracts the    byte value from this Dy nAny. T     he   IDL octet da   ta                       t  ype is map  ped to the Jav       a byte data  type.
                      *
        *        @exc   ep    tion T     ypeMism   atch if the accessed componen          t in the DynAny      is of a type
                * th      at is not equ  ivalen     t to the requested ty        pe.
              * @exception Ty   peMismatch           if calle    d on a Dyn  Any  whose current component   itse  l    f h  as    com   p  o nen  t      s
               * @exce     p   tion Inv    alidValue if     this DynAny has com   ponent       s but   has a current position of -1     
          */
  public                       b      yte get_octet () throws o   rg.omg.Dy  namicAny.DynAnyPackage.Ty            peMism   atch , org.omg.DynamicAny.DynA    nyPackage.I   nvalidValue       
  {
      or        g.omg    .CORBA.   portable.ServantObj           ect $so = _servant_preinvoke    ("get_octet  ", _   opsClass);
      DynStructOperations  $self = (DynStruct Operations)     $so.serva       nt;

      t  ry {
         re              turn $se    lf.get_octet ();
              } finally {
               _servant_p ostinv  oke ($so);
      }
  }    // get_oc    tet


  / **
         * Extracts    t   he char value from th    is  Dy      nAny.
                  *
        * @ex      ception Typ e    Mi smatc        h     i   f the    acces      sed component  in   the DynA     ny     is of   a type
        * that is not equivalen    t to the requested type.    
           * @excep  tio   n Typ eMismatch     i       f called on a   DynAny who    se current    component itself has       com       ponen ts
              * @exception InvalidValue    if th is DynAny h    a    s components    but    has a cu    rrent position of  -1
             */
      public char get _char        () throws    org.omg.Dyn          amicAny.   Dy    nA    nyPac      kage           .TypeMismatch, org.omg.D    ynamicAny.Dyn    AnyP  ackage.Inval  idVa      lu  e            
     {
      o    rg.omg.CORBA.portable.ServantObjec t $so = _s  e  rvant_preinvoke ("      get_c      har", _opsClass         );
      DynStruct  O perations  $sel f = (    DynS    t      ructOper    at   ions) $s                 o.servant;        
    
          try {
         r      eturn     $self.get_char ()  ;
              } finall     y {
              _ser  vant_postinvoke ($so);
                         }             
  } //   get_char


       /**
         *    Extracts      the  sho  r    t value from this DynAny     .
                      *
        *     @exception Type  Mismatch if   the acc    ess ed componen   t     in the Dy nAny is o  f a ty  pe
        * th         at is not equivalent to      the r    equested typ  e.
        * @exception TypeMis match if called  on a DynAny whose current compon      ent itself h    a     s compo    nents
        * @exceptio     n Inv    ali   dVal    ue if this DynAny has comp on   en  ts but has a curren      t position      of -1     
        */ 
      p   ubl   ic      sho        rt g            et_short ()   thr  ows org.omg.Dy   namicAny.Dyn     AnyPack          age.Typ eMismatch, org.o     mg.DynamicAny.D  ynAnyPacka ge.InvalidValue   
  {
         org.om        g.CORBA.por  table.Se      rvantObjec   t $so = _servant_pr  einvoke ("get_sh   ort     ", _opsClass);
         DynStructOperations  $self = (DynS tructOperations) $so.s    ervant;

             try {
            ret urn $     self.        ge      t_short ();
                } f inally {
                 _servant_postinv    oke ($so);
       }
  } // ge   t_short


  /**  
                  * Extra  cts the sh     ort   value       from     th       is DynAny. The IDL ushort da           ta ty  pe is  mapp         ed to the Java short dat    a typ   e  .
                *
             * @excep   t          ion Ty         p   eMi   smatch if the accessed component in    t   he         Dyn    Any is of a type
          * that is        not equ  ivalent to the r           equested ty       p   e.
        * @excepti        on T  ypeMismatc          h if call   ed on a DynAny  whose current compone nt itself              ha     s  compo    nents
        * @exc    eptio    n Inv   alidValu   e if t his DynAny      has components but has a     current pos   ition of -1
           */
          public short   get_usho  rt ( ) throws org.omg.DynamicA    ny.DynAnyP   ackage.Ty  peMismatch,    org.omg.Dy    namicAny.DynA          ny     Pa   ckage.Inv   alid         Valu      e
  {
                    org.o    mg.CORB   A.portable.Se   rvan   tObject         $  so = _se rva  n t_pr     e     invo    ke ("ge   t_ushor     t", _op    sClass)         ;  
                DynSt       ructOp     era  tions   $self    = (DynStruc      tO      perations) $so.ser    vant;

         try {
            return $se       lf.get_ush  ort ();
      } fi  n  a lly {
          _ser       v    ant_post  invoke ($s    o);
       }
  } //   get_ushort


  /**
          * Ex tracts the inte   ger value from this DynAny. The IDL long   data type is mapped to      the Java int   data type.
            *
        * @exception TypeMis    match if the accesse   d component       in the DynA    ny is of a type
        * th    a   t     is   not e       quivalent     to the     requeste   d type.
               * @exception TypeM     ismatch if called on a DynA   ny whos e  current c  omponent i  t         s  elf has components
            * @excep    tio         n   InvalidValue if t         his DynAny has      compo  nents but has a cu  rr   e nt    position of -1
            *   /
  p          ub          lic int get_long () t h rows org.omg.DynamicAny.DynAnyP         ac     kage.TypeMi smat   ch, org.omg.D  ynamicA n   y.DynA nyP  ackage.In       vali     d  Value
  {
      org.omg.C      ORBA.p     ortab  le.ServantObject   $so = _se  rva nt_preinvoke ("get_long", _o  psCla  s      s);
      DynS  tru   ct     Operations  $s     elf = (DynStructOpera       tions) $so.serva  nt;

         try {
         return $se    lf.get_long ();     
      }  finally {
              _serv ant_pos   tinvoke ($so) ;
      }
  } /    / get_long


           /**
        * Extracts the inte   ge  r     value fr  om this DynAny. Th  e     I         DL    ulo   ng data type is mapped to the Java int data typ  e.
            *
              * @exception    Typ     eMismatch          if the a          ccessed      c      o mponent in the DynAny is of a type
           *    that is no   t     equivalent to the requested t    ype.      
        * @exception TypeMismatch if   called o   n a D          ynA ny who   se c urrent com     pon       ent i   tself has components
        * @exception InvalidValue i    f this DynA ny has componen    ts but ha        s       a c   urre  nt p  osi  tion of -1
        */            
   public int get_ul  ong () throw        s or          g.o        m   g.DynamicAny.DynAn  yPack        age.  TypeMismatch,      or         g.omg.DynamicAny.DynAny   Package.   InvalidValue
  {
          org.omg.COR              BA.portab  le   .Serv     antOb  je  ct $so =        _servant_preinv    oke       ("ge     t_ulong" , _opsClass);
        DynSt                 ructOperations  $self =     (D         ynSt ructOperations)             $so.   servan t;

                 t  ry    {
            re            tu   rn $s   e  lf   .get_ul    ong (); 
      } finally  {
          _servan   t_postinvoke ($so)    ;   
             }      
                   } // get_ulong 


  /**
            * Extracts        the f   loat v     a    lu   e from t  his     DynAny            .
            *
        * @e  xce    ption TypeMismat  ch if  the access   ed component in the Dy    nAny is of   a      type
        * that is n   ot equ           iva    lent   t   o      the    requested type.     
         * @except      ion TypeMismatch      if called on a              D ynAn     y whose current    c   ompone nt it   self has components
        * @exception InvalidVa        lue if this   DynAny has               components but    has a current positi  on o  f     -   1
         */
  pu   b li c float    get _f      loat () throws org   .omg.D ynamicAny.Dy    nA    n      yPackage.    Type    Mi       smatch, or  g.omg.Dy      namicAny.     DynAnyP ackage.   Invali           dValue
     {
          org.omg.CORBA.port    able.Ser        vantOb  jec   t $so =    _ser        van     t    _prein   voke ("get_float",    _o  ps  Clas   s);
            D   ynStructOperat   ions  $s  elf    =      (DynSt   ructO    per  atio ns) $so.servant;

                 try    {
         return                          $sel   f.get_float ();
             }   finally {    
              _serva       nt_po  stinvo     ke            ($so);
        }
  } // get_float


  /**
        * Extr   ac      ts     the do  u bl  e value from this DynAny.
        *        
        * @exception TypeMismatch  if the accessed co mponent in the DynA    ny i     s               of a typ    e
               * that is not equivale  nt to the requeste          d type.
            * @exception   TypeMismatch if ca   lled on a          Dyn    Any whose curr     ent compone        nt itself has comp onents
                   * @excep  tion Invalid  V   alue if t    his Dy     nAny has   components but  ha    s  a cu           r    rent posit       ion         of -1
        */
  public double get_dou  ble      (  ) t       hrows org.omg.D    y    n   amicAny.Dyn AnyPack    age.TypeMismatch, or    g.omg.Dy  na   micAn    y.DynAnyPackag      e. InvalidValue
   {
              org     .omg.CORB  A.portable.Servant Object $so   =    _se      rvant_       prein       voke ("get _do   ub  le"    , _o   ps    Clas  s);
         Dy     nSt     ructOper    ation    s  $self = (     Dyn StructOpe rations) $so.servant;

            try {
           return $self.get_double ();
        } f   inally {   
             _serv     ant_postinvoke      ($so);
        }
  }   // g et_doub   le


  /**
        * Extrac     ts the string  value from t         his Dyn       Any.
        * Both bounded       a     nd un     bou   nded s   trings a      re extract e   d usin     g  this     m   eth  od.
        *
           * @exception TypeMismatch if th    e a    ccessed  component in the   DynAny is of a type
        * that is    not equivalent to th  e    requested type.
             * @exceptio    n TypeMismatch    i f c   a    lled     on a     D    ynAny whose curre   nt  comp      on     ent i  tself has components
        * @excepti   on    Invali   d V       alue if this DynAny has      compone  nts but  h  as a cu      r  rent pos  ition of -1
        */
     publi     c St   ring get_string () throws org.omg.DynamicAny.DynAnyPackage.TypeMi   smatch, org.omg.DynamicAn   y.DynAnyPack    age.Inval       idValue
  {
            org.  omg.CORBA  .portable.ServantObjec  t $so = _servant_p  reinvoke ("get_s    tring", _op         s Cla   ss);
          Dyn     S       tructOperati  ons  $self = (D ynStructOperations     ) $so.s ervant;

          try {
         return $self.get_string ();
           } finally {
           _  servant_posti  nvoke ($so  );
      }
  } // get_stri    ng


      /** 
                  *  Ex        tr acts t           he  reference to a CORBA     Obj        ect from this Dyn     Any.
               *
             * @exception TypeMismatch i   f the a     ccessed   component in th e  DynAny    is of a      type
               *       that is  not equivalen     t    to  the request  ed type.
                 * @exceptio n   TypeMismatch if c   alled on         a        DynAn        y whose   cu  rrent   component    its el   f has co  mponent        s  
           * @exception I nvalidValue if this Dy    nAny has compon    ents but has a  curre       nt position of -1
        */
  publi      c org.om g.CORBA.Object get_refe  rence () thro      ws org.omg.Dyn     amicAn    y    .DynAn             yPackage.TypeMi   smatch , org.omg      .DynamicAny.DynAnyPa   ckage.Inva lidVa  lue
  {  
      org.omg   .CORB  A.portable.ServantObjec   t $so = _servan   t_preinvoke ("get_re  fer  ence", _opsCl  ass);
      DynStr   u  ctOperations  $self = (DynStru   ctOperations    ) $s    o.s    er   vant     ;

        try {
                  r       eturn    $self.g           et_reference (   );
           } finally {
               _servant_postinvoke ( $so);
         }
  } // g     e       t_refere  nce


  /**
           *   Extracts       the TypeCod      e ob jec  t from thi s DynAny.
                 *
            *     @exception TypeMismatch     if        the accessed component in the DynAny is of a typ    e
        * that       is not equi     valent to t    he requ        ested type.
                 * @ex    ce   ption TypeMismatch if   called o   n a Dyn          Any whose c     u rren  t com  ponent itself has c   omponents
            *   @exception InvalidValue if this DynAny has com   ponent           s but has a c  urrent position of -1
        */
  publi   c org.omg.CORBA.TypeCo      de ge    t_typecode () throws or g.omg.DynamicA  ny.DynAnyPackage.TypeM    ismatch, org.omg.Dyn     amicAny.DynAnyPackage.   I nvalidValue
  {
               org   .om    g.CORBA   .   portab  le.ServantObject $  so =   _servant_preinvoke ("g  et_type code", _opsC  lass);
          DynStructOperations  $self = (DynStructOper      ati        ons)   $so.  s          ervan t;

          try {
             return $se lf .get  _typecode ();
      } finally {
             _ser   vant_postin  vo           ke ($so);
      }
  } //     get_typec   o          de


  /**
        * Extracts the l       on   g value    fro  m th     is DynAny. Th  e IDL long long data type is m        ap  ped to the Java long data ty   pe.
                *
            * @exce      ption TypeMismatch if the accessed compone        nt in the DynAny      is of a type
          *    that is not   equ    ivalent to the reque  sted type.
        *           @exce   ption TypeMismatch         if calle     d on      a DynAn  y wh  ose cu   rrent component itself has components
               * @e  xce  ption    Inv al      idValue if this       DynAny has com   ponents bu      t h    as a current position of -1
        */
  public long get_long      lo    ng () throws org.omg.Dynamic An    y.DynAnyPackage    .TypeMismatch     , org .omg.DynamicAny.DynAnyPack   age.I   nvalidValue
  {
           org.omg.CORBA.portable.    Ser   v  antObjec   t $so = _servant_preinvoke (    "     g       et_longlong", _opsClass);
        D  ynStructOperati  ons  $      se  lf = (DynStr   uctOper     ati   on       s)       $so.s       ervant;

      try {
         re   turn $self.get_longlon    g ();
      } finally {
          _            servant_postinvoke     ($so)  ;
      }
     } // get_long    long


  /**  
                  * Extracts     the long value from this     DynAny.
        * The      IDL unsigned long long data type    is mapped to the Java long   data     type.
                     *
        * @exce  ption Type    Mis  match if    t    h    e accessed component in    the DynAny is of a t  ype
        * that is not equivalent to the requested type.
        * @exception TypeMismat    ch if calle    d on a DynAny whose current component itself has compone    nts
        * @   excep   tion Inv  alidValue if     this DynAn     y    has componen  ts but has a    current positio    n of -    1
        */
  public lo     n    g get_ulonglong () throws o   rg.omg.DynamicAny.DynAny   Packa  g e.TypeMismat           ch, or g.omg.DynamicAny.D   ynAnyPackage.Inva   l   id Value
         {
      org.omg.CORBA.po      r   table.Se  rvantObject $so = _servant  _preinvoke (    "  get_ulong  long", _op     sClass);
      Dyn   StructOperat  ions  $self = (DynStruc  tOperations) $so.servant;

      try {
         return $self.get_ulo nglong ();
      } finally {
               _se  rvant_postinvoke (   $so);
      }
  } // get_ulo  n          glo   ng


  /**
          * Extracts the long value from thi    s DynAny. The IDL wchar data typ   e i       s ma                      pped to the    Java char data type   .
        *
        * @e             xception TypeMisma    tch if the acc  essed c    omponent in the         DynAny      is of a t   ype
          *       t   hat is   not equivalent t  o the re   quested type.
        * @exception TypeMi    smatch if called on a D            ynAny       w  ho  se cu     rrent component     itself has       components
        * @ exception Inval    idValue if   this Dyn      Any has components but has a current position of -1
        */
  public char get_w    char ()     throws          org.omg  .Dynam  icAny .     DynAny  P    ac     kage.Ty           peMismatch, org.  omg.DynamicAny.Dy  nAnyPackage.InvalidValue
  {
          org.om  g.CORBA.porta     ble.ServantOb   ject $   s   o       = _servant_preinvoke  ("get_wchar"  , _opsClass);
      DynStructOp    erations  $self = (DynStructOperations) $so            .serv         ant;    

      tr  y          {
             return $self.get_wchar     ( );
      } fin ally       {
              _ser      vant_pos         tinv   oke (  $so)  ;
      }
  } // get_wchar


  /**
        * Extr   acts th    e st  ring valu   e from this D  ynAny.
        * Both bounded  and unboun      ded  strings are extracted using   this me    thod.
            *
        * @exception TypeMismatch if   the          accessed component in the    DynAny is    of  a type
        * that is not equivalent to the   requested type.
          *   @exception TypeMismatch if called on a DynAny   whos  e     current c omponent     it   self h    as comp    onents          
         */
  public String get_wstring () throws org.o     mg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.Invalid Value
  {
       org.omg.CORBA.portable.Serva   ntObject $  so = _s er  vant_       preinvoke ("g   et   _wst     ring", _opsClass);
          Dy             nStructOperat  ions  $self = (DynStructO     peratio    ns) $s    o.servant;

      try      {
         retur    n    $self  .get_wstring    ();
      } fi      nally {
              _servant_postinvok       e ($so);     
            }
  } // get_ wstring


  /**
        * Extracts an Any v    alue contained in     the Any repre      sented by this DynAny.
        *
            * @e          xception TypeMismatch if the accessed component in the DynAny is of a type
        * th   at is not equivalent to the      req  uested ty     pe.
        * @exc eption TypeMismatch if called on a DynA      n  y whose curren   t component itself has  components
        * @exception InvalidValue if this DynA     ny    has components but has a current position of      -1
        */
  pu     blic or  g.omg.CORBA.  Any get_any () throws org   .omg.DynamicAny.DynAnyPacka  ge.TypeMis    match, org      .omg.DynamicAny.DynAnyPackage.InvalidValue
  {
      org.omg.CORBA.portable.S   ervantObject $so = _servant_preinvok     e (      "get_any", _opsClass);
      DynS   truc  tOperations  $self = (DynStructOperations) $so. servant;

      try {
         return $self.get_any ();
      } f   inal   ly {
          _serva  nt_postinvoke      ($so);
      }
  } //    get_any


     /**
           *     Extracts t  h     e Any value containe   d in the Any represented by this DynAny and retu  rns it wrapped
          * into a new DynAny.
        *
        * @e  xception T    ypeMismatch if      the  accessed componen t in the DynAny is of a    ty   pe
        * that is n    ot equivalent to the req         uested type.
           * @exception TypeMis       match if called on a Dy    nAny whose c   urrent compone   n    t itself has compone   nts
           * @exception InvalidValue if this Dy   nAny has components but has            a current     position of -1
        */
  public org.omg.Dynamic Any.DynAny g   et_dy    n_any () throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg   .    DynamicAny.DynAnyPackage.InvalidValue
  {
                 org.omg.CO     RBA.portable.ServantObject $so = _servant_preinvoke ("get_dyn_a      ny", _opsClass);
      DynStructOperations  $self = (DynStructOperations) $so.servant;

      try    {
         return $self.get_dyn _any ();
       } finally {
             _servant_postinv  oke ($so);
      }
  } // get_dyn_any


  /**
        * Extract   s  a Se   rializable object from this DynAny.
        * The IDL ValueBase type is mapped to the Java Serializable type.
        *
        * @    except  ion TypeMismatch if th       e accessed component in the Dyn  Any is of a type
        * t hat is not eq  uivalent to the  requested type.
              * @     exception TypeMismatch if c alled on a DynAny whose current component itsel      f has components
        * @exception InvalidVa lue if this DynAny has c  ompone   nts but has     a current position of -1
                */
  public java.io.Serializable get_val () throws org.omg.Dyn    amicAny.DynAny   Package.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue
  {
      org.omg .CORBA.portable.ServantObject $so = _servant_preinvoke ("get_val", _opsC        lass);
      DynStructOperations  $   self = (DynStructOp   erations) $so.servant;

      try {
         return $se    lf.get_val ();
      } finally {
          _servant_post     invoke ($so);
        }
      } // get_   val


  /**
        * Sets the current position to index   . T he    current posit        ion is indexed 0 t o n-1, that is,
        * index zero corresponds to the first component. The o  peration returns t     rue if th    e resultin  g
           * cu    rrent position indicates a co    mponent of the DynAn   y and false if index indicates
          * a  posi tion that does      not correspond to a compo nent.
           * Calling see    k with a n egative index is leg   al. It sets the current position to     -1 to indicate
        * no component and returns fals    e.   Passing  a non-negative index value for a DynAny that does not
        * have a component at the corresponding position set     s the current positio   n to -1 and returns fals e   .
        */
  public boolean seek (int index)
  {
      org.omg.CORBA.portable.    ServantObject $so = _servant_preinv    oke ("see   k", _opsClass);
      DynStru ct Operations     $self = (Dyn   StructOperations) $so.servant;

      try {
         return $ self.seek (index);
      } finall    y {
                _servant_postinvoke ($so);
       }
  } // seek


  /**
        * Is   equivalent to seek     (0).
           */
  public void rewind ()
  {
      org.omg.CORBA.portable.S     ervan     tObj  ect  $so = _servant_preinvoke ("rewind", _opsClass);
      DynStructOperations  $self = (DynStructOperations) $so.servant;

      try {
            $self    .rewind ();
       } finall  y {
          _servant_post    invoke ($so);
       }
  } // rewi   nd


  /**
        * Advances the current position to the next component.     
        * The ope    ration retur  ns true while the resulting current position indicates a component, false otherwise.
        * A false return value leaves the  current position at  -1.
        * Invoking next on a DynAny without components leaves the current  position at -1 and returns f     alse.
        */
  public bool   ean ne   xt ()
  {
      org.omg.CORBA.portable.ServantObject $so = _servant_preinvoke ("next", _opsClass);
        DynStructOperations  $self = (DynStructOperations) $so.s ervant;

      try {      
         return $self.next ();
      } finally {
          _servant_postinvoke ($so);
      }
     } // next


  /**
           * Returns the number of components o f a DynAny.
        * For a DynAny without  compone   nts, it returns zero.
        * The operation only counts the components at the top level.
        * For example, if component_c   ount is invoked on a DynStruct with a single member,
        * the return value is 1, irrespective of the   type of the member.
        * <UL>
            * <LI>For sequences, the o   pera  tion returns  the current number of elements.
        * <LI>For structures, exceptions, and value types, the operation returns th   e    number of m   embers.
        * <LI>For arrays, the operation returns the nu  mber of elements.
        * <LI>For unions, the operation retur ns 2 if the discriminator indicates that a named member is ac    tive,
        * otherwise, it returns 1.
        * <LI>For DynFixed and DynEnum, the operation returns zero.
        * </UL>
        */
  public int componen t_count ()
  {
      org.o  mg.CORBA.portable.ServantObject $so = _servant  _preinvoke ("component_count", _opsClass);
      DynStructOperations  $self = (DynStructOperations) $so.servant;

      try {
         return $self.component_count (   );
      } finally {
            _servant_postinvoke ($so);
      }
  } // component_count


  /**
        * Returns the DynAny for the component at the current position.
        * It does not advance the   current position, so repeated calls to current_component
        * without an int  erv    ening call to             rewind, next, or seek return the same compone  nt.
        * The returned DynAny object reference can be used to get/set the value of th e current component.
        * If     the current component represents a com    plex type, the returned reference   can be narrowed
        * based   on the TypeCode to get the interface corresponding to the to the complex type.
        * Calling current_component on a DynAny that cannot have components,
        * such as a Dy   nEnum or an empty exception, raises TypeMismatch.
        * Calling   current_component on a DynAny whose current position is -1 returns a      nil reference.
        * The iteration operations, together with current_component, can be used
                * to dynamically compose an any valu       e. After creating a dynamic any, such as a DynStruct,
        * current_component and next can be used to initialize all the components of the value.
        * Once the dynamic value is completely initialized, to_any creates the corresponding any value.
        *
        * @exception TypeMismatch If called on a DynAny that cannot have components,
        * such as a DynEnum or an empty exception
        */
  public org.omg.DynamicAny.DynAny current_component () throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch
  {
      org.omg.CORBA.portable.ServantObject $so = _servant_preinvoke ("current_component", _opsClass);
      DynStructOperations  $self = (DynStructOperations) $so.servant;

      try {
         return $self.current_component ();
      } finally {
          _servant_postinvoke ($so);
      }
  } // current_component

  // Type-specific CORBA::Object operations
  private static String[] __ids =  {
    "IDL:omg.org/DynamicAny/DynStruct:1.0", 
    "IDL:omg.org/DynamicAny/DynAny:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  pri  vate void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Ob  ject obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try   {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _DynStructStub
