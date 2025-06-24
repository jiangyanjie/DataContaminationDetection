package     org.omg.DynamicAny;


/**
* org/omg/DynamicAny/_DynArrayStub.  java .
*      Generat   ed by    the IDL-t     o-Java compil  er (portable),    version "3.2"
* from c:/re/workspace/8-2-build-windows-amd64-cygwin/jdk8u131/8869/corba/src/share/classes/org/omg/DynamicAny/DynamicA   n  y.idl
* Wednesday, March 15, 2 017 1:25:04 A    M      PDT
*/

  
/**
    *  DynA  rray objects support the m   ani   pulatio n of I    DL a    rrays.
         * Note that the dimension  of the array i     s con    tained in the Ty  peCode which        is acces   sib   le
    *   through the type attrib  ute. It ca     n a     lso b      e obtained by calling th   e component_count operation.
    */
public class _DynArrayStub extends org.    omg.CORBA.p  ortable .ObjectImpl implements org.omg.DynamicAny.DynAr   ray
{
  final publ   i        c static ja va.lang.Class _o  p    sClas     s = DynArrayOperation    s.class;



   /**
        * Ret     urns     the elements o f the DynArray.
        */
  public org.omg.CORBA.A     ny[] get_elements   ()
  {
        o         rg.omg.CORB  A.portable.ServantObject $so = _s     er vant_pr   einvoke    ("g    et_elem en   ts", _opsCl  ass  );
               DynA            rrayO      pera        tions     $sel     f   = (       DynArrayOperat     ions) $s o.servant;

      tr y               {
                    return $ self.ge       t_  el    ements () ;
                 } f    inally   {    
               _servant_p        ostinvoke    ($so);
      }
  } //   get_  elements


  /**
                         * Sets th   e DynArray to contain the     passed elements.
        *
         * @exception TypeMismatch if    one or mo   re element   s have a type t  hat is inconsistent with the DynArrays TypeCo    de
        * @exception In   validValue if the sequence d    oes not contain the same number of elements as th     e array dimension
          */
  public void set_elements (or   g.omg.CORBA.A ny[] v  alue) throws org.omg.DynamicAn y.DynAnyPackage.Typ         eM ismatch, org      .omg.DynamicAny.   D            y nAnyPa  ck    age.In   val     idVa   lue
  {
                   or     g.omg.CORBA.portable.S      ervantObj          ect $so = _    serva   nt_preinvoke ("se   t_elements", _op  sClass);
              DynAr     rayOperati o   ns  $self = (Dyn Arra   yOperation  s) $so.serva   nt;      
    
      try {
            $self.set_elements (   value);
      } f     in     ally {
              _ser  vant_postinvoke ($so);
      }
  } //       s  e t_  elements


  /**
        * Returns the elements of the DynAr  ray a s DynAnys.
        */
  public org.omg.Dynam     icAny.DynAny    [] get _el  em   ents_as_dyn  _any    ()
   {
                               org.omg. CORBA.port able.S ervantOb ject $so   = _servan      t_preinvoke ("get  _ele   ments    _as_dyn_an y", _opsCla   ss);
      D    ynArrayOper    ations  $self = (DynArrayOperat      i ons    ) $so    .s     ervant;    
   
      try {
         return $self.get_elements_      as_dyn_any ();
      } finally   {
              _ser vant_postinvoke ($so);
        }     
  } // get_elements_as_    dyn_ any


      /*      *
          * Sets         the DynArray       t   o   con tain the passed ele ments.
        *         
        * @exception TypeMismatch if one         or more elements have a type that is inc onsist en t     with the D  ynA       rrays TypeC     ode
        *   @exception InvalidValue if the sequence does not     contain the             same num  ber of ele   ments     as the a   rray di    men    sio n
            */
  public void set_elements_as_dyn_any      (org.o                 mg.DynamicAny.DynAny[] va     lue) t           hrows org.omg.DynamicAny.DynAn    yPackage.TypeMismatch, org.omg.Dynami    cAny.DynAnyPackage.Inv  al        idV             alue 
  {
                          org.omg.CORBA.portable.ServantO        bj   ect $so = _servant_preinvok       e ("se   t    _eleme    nts_as_dyn_    any",    _ops   C  lass);
           DynArra   yOp    er  atio    ns  $self = (DynArrayOp             erations) $so.servant    ; 

      try             {
         $self     .set_elements_as_ dyn _any (value);
          } finally {
                   _  s erva   nt_postinvo   ke        (    $s   o);
      }
   } // s     et   _elemen    ts     _as_dyn_any


  /**
        * Returns the T  y peCode assoc          i ated   with this DynAny object.
                  * A D      ynAny       object is cr   eated with a TypeCode val ue         assi   gned t     o it   .
         * T    his Type     Code val   ue determines the ty   pe    of the value handled thr  ough th   e DynA           ny obj          ect.
                      * Note that the            Type     Code as  sociated w   it       h a D   yn   A ny       object    is initialized at the time the
                         * Dy nAny is cr      eat ed and   cannot be cha       n  ged     duri     ng lifetime of the      DynAny ob       ject.
        *
        * @re turn The         TypeCode associated wi     th this         Dy   n        Any ob ject
                */
     public org.omg.CORBA.Typ     eCode type ()                         
              {
             org.omg.C      ORBA.    portable.   Serva   nt            Object $so = _      serv     ant_preinvo  ke ("type", _opsClass);
          D   y   nArray   Operation  s    $self = (DynArray  Operati  o      ns) $so.serv    a         nt;
    
        try {
                 return  $se l       f   .    type ()   ;
                } fi  nally {
          _servant_p ostinvoke ($so);  
         }
  } // type


     /**
                               * Initial     i   zes t      h   e value    associated w        ith a DynAny object with t he      value
             *   assoc  iated      wit         h another DynAny object.
            * The curr  ent p   o   sition of th    e target Dyn   Any is set to zero for values th  at hav        e compo        nents
                 *   and to   -1 for values th     at do not hav            e compo   ne     nts.
                        *
        * @param  dyn_any
             * @exc  ept     ion Typ e    Mismatch if the type of the passed DynAny  is not equivalent to the typ        e of target     DynAny
        */            
  public void ass      ign (or g.omg.D       ynam    icAny.DynAny dyn_a      ny) throws org.om  g.DynamicAny.DynAnyP    acka ge.TypeMism     atch
   {
           org.omg.C           O    RBA.portable.ServantO    bject $so        = _servant_preinv    oke   ("assign", _opsCla ss);
             DynArrayOperatio      ns  $self = ( DynArrayOperat     ions) $    so.servant    ;

      try {
         $self.assign (dyn_       any);
      } finally {
                       _ser   v     ant_postinvoke (    $so    )         ;  
        }
      } // ass  ign


     /**
            * Initializes       the    value ass ociated w  ith   a   DynAny       object with t   he         v    alue contained in an any    .
           * The current p        osition                    o  f  the target    DynAny is set    to zer o for value   s t          ha     t have co      mponents
                  * and to -1 for va lu es that do not have compon    ent      s         .
           *
                      * @excep    tion   Ty  pe  Mismatch if the t   ype of the pa    ssed Any is not equ       ivalent     to the type  of ta   rget D    ynAny
                * @exception Invalid   V alu    e if the passe      d Any d   oes not contain a lega       l value (such as a n    ull s   tring)
                   */
  public void from         _any  (org.   omg.C     O RBA.Any    va    l  ue) throws org    .omg.DynamicAny.DynAnyPa      ckage.Ty     peMismatch, org.o                 mg.  DynamicAny.        D       ynAnyPa  ckage.InvalidValu   e
  {
      or  g.omg.C          ORBA.por                        table.Serva ntObj ect $so   = _   servan  t_preinvoke ("fr        om_a    ny", _opsClas     s);
      DynA  rra  yOperations       $self     = (      DynArrayOperation   s) $   s  o.servant;
                 
        try {
         $      s     e           lf.f    rom_any (va lue);
      } final  ly           {
                 _servant_post  i nv   oke (    $s o);
                      }          
  } // fro   m_any       


  /**
            * Crea tes an any             value from a D   ynAny object .
            * A co p y of       the Typ             eCode associated wi    th the DynA   ny   obj   e          ct is  assigne      d to the   result     i  ng          any   .
         *      The value associate   d w       ith t   he DynAny obje  c t is copied       in  to       th    e any.
                         *
                 * @return a     new Any object w  i   th     t   he same     value and TypeC ode         
             */
  pub   l      ic org.omg.CO  RBA.Any to_any    ()
     {
      org.omg.COR  BA   .     po  rtable.S  ervan   tObject     $so = _servant   _prei  nvoke ("t   o_any", _opsC       lass);
                Dy  nArrayOperations  $sel    f = (DynArrayOperati   ons) $s    o  .servant      ;

           try   {
             return $self.to_any (    );
      } finally {
                     _se    rv  ant_postinvok e ($so     )    ;
           }
  }      // to_any


      /**
        * Comp      are   s             two             DynAny values for equ  al  ity.
        * Two DynAny     values are equal if their     TypeC    od  es are equivalent and, r      ecursively, a ll         component D     ynAnys
        * have     eq      ua      l valu    es.
               * The current position of t    he two DynAnys b     eing compared has no eff   ect on the resu    lt of equa l.
          *
                * @return true of      t  he Dyn         Anys are    equal,          false otherwise
        *    /       
  public boolean equal (org.omg.Dyna     micAny.D ynAny dyn _any)  
  {
           org.omg.CORBA.p ortable.Serv       ant     Object $so = _s  ervan  t_pre     in   vok  e ("e qual", _opsC  lass);
        DynArrayOperatio   n             s      $self = (Dy  nA  rra   yOperations  ) $so.servant;

      try {
               return $  self.equal (dyn_a      ny);
                    } finally {
          _serva    nt_postinv  o ke ($so);
           }
    } // equal


  /**
        * Destroy  s a DynAny ob   ject      .
            *      This operation frees any resourc  es used to    re    p      resen   t the data   v  a         lue associated with a   DynAny o       bj   ect.
          * It     m  ust be invoked on refer  ences obta  ined       fro          m one of  the      cre   a    tio     n oper  ations on the ORB int          erf  ace
        * or o   n a referen  ce return       ed by D      ynAny.copy()     to     avoid resourc       e   leaks.
              * Invoking destro   y on com      ponent DynAny objects (for exa     mp       le , on obj    ects returned by the
        * curre   nt_component   operati  on) does no      thing.  
        * Destruction of a       DynAny object             im     plies de    struction o            f    al     l   DynAn   y objects obtained       from  it.
            * T   hat         i s, refe rences to componen      t s of a destroyed Dyn       Any become invalid.
                 * Invocati o   ns o        n such referen     ces raise OBJECT_NOT_EXIST.
            * It is poss  ible to m   anip  u     late a component      of a DynA ny      b    e        yond         the life t        ime of the     DynAny
              * f  rom which the c  o  mponent was obtai     ned b      y making    a copy of   the c     omponent w     ith the copy oper   ation
                 * before des        troyi   ng the DynAny fro  m  wh   ich the component w    as      obtaine d.
               *   /
  public       void destroy (   )
  {                
      or g.omg.CO     RBA.port  abl e.ServantObject     $so = _servant_prein  voke ("d est  roy", _opsClas s); 
           DynArra  yOperation   s  $self =      (DynArray      Operations) $so.serv        ant;      

      try {
         $se         lf.destroy      ();
         } finally {
                           _s  ervan  t_p   ost    invo  ke ($so);
                 }
        } // destroy
  

  /**
           * Creates a ne      w Dy  nAny object whose     value is a deep copy     of     the DynAny o   n which it is invoked.
         * The             operat    ion is p  olymorp   hic, tha  t is, invoking it on one     of the    types derived from D    ynAny,
        * such a  s  DynStruc t, creates the d    erived type but ret    urns its reference as  the D     y  nAny base type.
             *
                 *  @return a deep copy of the   DynAny object
               *  /
      public org           .omg.Dynami    cAny.DynAny copy ()
  {
      org.omg.CORBA.portable.ServantObject  $     s    o = _s  ervant_pre      inv  o                  k e ("copy",    _opsClass);
           DynA   rrayO  perations  $sel  f  = (DynArrayOperations)   $so.  servant;  
     
         try {
             ret  urn $self.copy ();
      } finally {
          _ servant_postinvoke     ($so);
      }
  } // copy
          

  /**
                  * Inser   ts a boolean value into the DynAny.
         *
            * @exception     Inva     lidValu   e    if this DynAny has components but has a cur   rent position of -1
        * @exception TypeMismatch if call     ed    on a   DynAny whose current c       o   mp  onent itself ha    s components
           */
  public v     oi       d insert_boolean (boolea n value) thro          ws    org.   omg.DynamicAn y.DynAnyPac k      age.Typ    eMismatch, org.omg  .Dyna   micAny.Dy nAnyPac   kage.Inv  alidValue
  {
      org.omg.  CO  R     BA.port able.Se  r   vantObject $so = _ servant_p   reinvo    ke   ("   inse  rt     _boolean", _opsClass);
       DynArrayOperations  $s   elf =    (DynArrayOperations    ) $s  o.servant;

      try {
         $self.inse   rt   _bo   olean (value);  
           } f        in      a                  lly {     
                             _serv      ant_postinvoke ($so); 
        }
  }                     // insert_boolean


  /**
        * Inserts a byte     value        into the DynAny.  The IDL octet data type is mapped to the Ja va byte data type.    
                 *
        * @exception     InvalidVal ue if  this DynAny has components but has a curr ent    position of -1
        * @exception T   ypeMismatch if called on a DynAny whose current component itself has compo  nents
        */
     public       void i       nsert_    octet (byte value  ) throw   s   org.omg       .DynamicAny.DynAnyPackag e.Ty        peMismatch, org.omg.D yn       amicAny.DynAnyPackag      e.InvalidValue
      {      
                org.omg.CORBA.portable.ServantObject $so            = _ se    rvant_preinvoke ("insert_oct    et", _ opsClass);
        DynArrayOperation         s  $self = (DynA    rrayOp    erat ions) $so.servant;

        try {
                        $      self.insert_octet      (v    alue);
      } fi    na lly {
          _s ervant_  postinvoke ($so);
           }
  }      // insert_octet


  /*        *
                     * Ins    e    rts a char   value into t he DynAny.
        *
        * @e     xception InvalidValue if this DynAny has components but has a current   positi    on of      -1
           * @exceptio  n    Typ   eMismatc   h if       cal led on a DynAny whose cu  rrent compone   nt itself has components
        */
  public void insert_cha   r (char v    alue) throws org.omg.Dy    namicAny .       DynAnyPacka    ge.TypeMism         atch, org.omg.     DynamicA   ny.Dyn    AnyPackage   .InvalidVal ue
      {
      o           r     g.    omg.C    ORBA.portable.Se  rvantObject $s    o = _ser         vant    _preinvoke ("insert_cha       r",       _o       ps     Class)      ;
      DynArr         ayOperations    $self = (DynArrayOperations) $so.servant;
      
        try      {
             $sel  f .i     nsert_char (valu    e);
               }      finally {
               _serv   ant   _post    invoke ($so);
      }
  } //  insert_char


  /**
             * Inserts a short val         u     e      into th  e Dy      nA     ny.
        *
           * @exception Inv     alidValue if this                         DynAny has components           but h as a cur     rent pos         iti    on  of -1
        * @excep      tion TypeMism atch if called on a D    yn    Any   whose current com  ponent its  elf has co       mponents
            */
  public void insert_short   (sh ort va   lue) throws  or  g.o   mg   .Dynamic    An  y.   DynAnyPackage.Ty    peM   is    match, org.   omg.DynamicAny.D     ynAn yPackage.I n validValue
      {
         org.omg.CORBA.portable.Serv    antObj     e            ct $  so = _serv   ant    _pre      invo    ke ("in    sert_sho rt", _opsC      la    ss);      
            DynArrayOperations  $self   = (DynAr rayOperations) $so.servant;

      try {
            $self.insert_short     (value);
          } fin  ally    {
          _serva           n            t_ post  i     nv  oke ($so);   
      }
  }  /   / insert_sh    ort
    

       /**
           * Inserts a     sh              ort    value into t         he   Dyn Any. The IDL ushort data type is          mappe         d to the  Java short data        type.
                  *
                 *   @exception InvalidValue if t      his     DynAny has com   ponent  s but has a curre  nt position of -1
                *       @excepti   o   n  TypeMismatch if called on            a DynAny whose current component it    s      e   lf has             comp   on   ents
             */
  public    void insert_ushort (short      v  alue)      throws org.omg.DynamicAny.      DynAnyPacka ge.T    ypeM    ismatch, org.omg.DynamicAny.DynAnyPackage.I nval idV      al   ue         
  {             
             org.om     g.C   ORBA.portable. ServantObject $so = _s   ervant   _preinvo    ke ("insert_ush    ort    ", _opsClass);
        DynAr  rayOperat    ions  $se   lf = (Dy             nArr   ayOpe  rat      ions) $so    .     se   rva   nt;   

                 t  ry {
          $self.insert_ush   or   t (value     );
           } finally {
             _s     ervant_postinvo          ke ($so);
      }
  } // ins   ert_ushort  

 
  /**
        * In     ser  ts an     int   eger value into the D     y     nAny. The IDL long data type   is mapped t  o the Ja   va int data type.  
          *
          *        @excep  tion InvalidValue if th    is DynAny    has compon e             nts but h as a current position of       -1
           * @exception    TypeMisma   tch if called on a DynAny wh  ose   current             co   mponen   t       itself has componen      ts   
              *   /
    publi         c     voi    d insert_long (int value) thro  ws org     .omg.Dy     nam       icAny.DynAnyPackage.TypeMismat  ch, org.o     mg.DynamicAny.DynAn   yPackage   .InvalidValue
  {
      org.omg.CORBA   .portable.ServantObject $so = _   servan      t_preinvok  e ("insert_lon    g", _opsClass   );
        DynArrayOperations   $self = (DynA   rrayOperations) $so   .servan   t;

         try {
         $self.insert_long (value);
      } final    ly {    
             _ servant_postinvoke ($so);
        }
       } // i  nsert_long

     
  /**
        *  In    serts an integer value  into the DynA     ny. The IDL ul     ong da      ta type   is mapped     to the   Java int d ata t    ype.
          *
        * @exc   eption InvalidValue if        t  his Dyn        Any has compon          ents  but has a current  positio n of -1
              *             @e    xce  ptio n     TypeMismatch i  f ca     lled on a DynAny whose current component itself h          as co      m      po  nents
             *     /
          public void inse     rt_ul   ong (  int value) th       rows org.     omg.Dyn    a        micAny.DynAnyPackage.TypeMis    match, o rg.omg.DynamicAny.DynAnyPackage.In  validValue
     {
           org.omg.CORBA.po           rtable.S  ervantObject $so = _serv   ant_preinvoke ("i  nsert_u  long", _               opsClass);
            DynArrayOperations        $self = (DynArrayOperat  ions)   $ so.        servant;

          try { 
         $sel    f.insert_ ulong (value);
      } fina  lly {
                   _servant_   postinvoke ($so);
      }
  } //  i  n           sert  _ulong


  /* *
         * Insert           s   a     flo      at value into           the DynAn y.
        *
                 *   @excep      tion                 InvalidValu     e    if this DynAny has  c     o  mpone   nts but has a current posi   tion of -1
        * @exception T ypeMismatch if called   on a DynAny wh            ose curre   nt c  omponent itself ha     s com    pone    nt   s
        */
  public void inse    rt_float (float value) throws o  rg.omg.Dynami     c      Any.DynAnyPackag     e.TypeMismatch, org.omg.Dynamic         Any.DynAnyPackage.Inva        lidV         alue
  {
      o     rg.omg.CORBA  .por  table.Se   rvantOb ject $  so = _se              rvant_preinv     oke ("in          sert_flo    at",    _opsClass);
         DynArrayOpe    rations         $        self = (DynArrayOperati         ons) $so. se   rvant;    
    
      try {
            $self.insert   _float (v   alue       )   ;
           } finally {
              _     se                rvant_postinvoke ($so);   
        }
     } // insert_float
     

  /**
         * Inserts a double value into the    DynAny.     
           *  
           * @  exception InvalidValue if this Dy        nAny has components bu    t has a current pos   ition of -1
        * @exc        eption T    ypeMismatc    h if cal   led o        n a DynAny wh     ose current component       itself  has co          mpo     n  e     nts
        */     
     public void      insert_double (double val ue) throws org.omg.Dy    namicA  n y.DynAnyPack   age   .TypeM   is match, org.  omg.D  ynamicAny.DynAnyPac   kage.InvalidValue
  {
      org.omg.CORBA.          portable.Ser vantO           bj    ect $so    = _se    rva    n       t_p   reinvoke ("insert_double",       _opsClass)  ;
      Dyn  ArrayOpera   t    ion s  $self = (DynArrayOperations)   $so.servant;

      try    {
         $self        .insert_double (value);
      } finally {
          _servant_postinvoke     (   $     so);
                       }
    } //        ins  ert_  double


  /**
        * Inserts a st    ring value into the DynA  ny.
         *    Both bounded and unbounded s     trings are inse  r     te    d using this    met   hod.
        *
              * @exception     Invalid   Value if th   is   DynAny has components    but has a current positi   on of -1
               * @ ex     ception In        validValue if the string inserted is lo  nger than the bound of a boun    ded str      ing
         * @exception TypeMismatch if ca      lled on a D     ynAny whose curre       nt          component itself has comp     onents
                  */
  public void   in       sert_string (String value) thro  ws org.om   g.DynamicAny.DynAnyPack    ag  e.  Type   Mism       atch   ,               org.omg.DynamicAny.Dyn     AnyPackag e.Invalid          Value
  {      
       org   .omg.CORBA.  portable.Servant   Object $so = _serva       nt_prein   voke (          "insert_string", _opsCla    ss      );     
      DynArrayO peration   s  $self =      (DynArrayOperations  ) $so.s  ervant;      

          try       {
                          $self.in     sert_s       tring (valu e);
       } finally {  
           _s      ervant_postinvoke     ($so);
      }
  }         / / insert_st  rin    g

    
    /**
        * In            serts    a reference to             a CO      RBA object          into the DynAny.
               *
         * @exce   ptio      n InvalidValu   e if this DynA    ny has     comp       one    nts but has a current posi   tion of -1
        * @exception TypeMismatch if called on a DynAny who        se   curre  nt compone     n         t it  self has compo nents
           */
  public void insert_reference         (or      g.omg.    CORBA.Object val  ue) thr   ows org.omg      .Dynamic Any.DynAnyPacka  ge.TypeMi        sma      tch, org.    omg.DynamicAny.D  ynAnyP            ackage.InvalidValue
  {
             org.omg.COR  BA .portabl      e.ServantObject $so =    _servant_preinvoke ("insert_reference", _opsC           lass);
      DynArrayOperations  $se    l   f =    ( D  ynArrayOperations) $so.servant;

      try {
            $self.insert_reference (value);
              }  fi   nall    y {
                 _servant_posti    nvoke   ($so);
      }
  }     //     insert_referen ce


  /* *
        * Inser   ts    a TypeCo  de object int      o the DynAny.
        *
        * @exception InvalidValue if this  Dy nAny has    com ponents bu       t has a cu    rrent position of -1
           *    @exception TypeMismat          ch if             called on   a DynAny whos   e current component          itself has    compon   ents     
        */
  public void in   sert  _typecode (or  g    .om   g.COR   BA      .TypeCode value) t      hrows org.omg.DynamicAny.Dy   nAnyPackage.TypeMismatch, org.omg.   Dynam    icAn  y.DynAn                   yPackage. InvalidVa    lue
    {
      org.omg.CO    RBA.p        ort     able.ServantObje      ct   $so = _serv     ant _preinvoke ("insert_typecode",     _      opsC       las     s);
              DynAr   rayO    pe        rations  $self = (DynArrayO  perations) $s   o.servant;

                     try {
         $   self.       inse  rt_   typecode   (value);
          }     final  ly {
               _servant_postinvoke    ($s  o);
         } 
  } // insert_type   code


  /**
             * Inserts a long    value into the Dyn       An    y. T    he I    DL l  ong lon    g data type i    s map   ped  to    the Ja     va long da   ta type.
        *
             * @excepti     on InvalidV  alue if this Dyn  Any has com    ponents but h   as a curre      nt position of -1    
                 * @exception Typ  eMisma    tch if called on a Dyn      An          y whose current comp   one        nt i    tself has co m       ponents
        *      /
   public   void insert_longlong      (       lon    g value) throws   org.omg.DynamicAny.DynAnyPackage.Typ       eMi smatch, org.omg.  DynamicAny.DynAnyPackage.InvalidValue
  {
                org.omg.CO        RBA.      porta ble.Serva  nt  Object      $so = _             servan   t_preinv  oke ("insert    _longlong",    _ops   Class);
            DynArr  ayOpe   r atio  ns  $sel    f = (DynArrayOp  e  ration    s    ) $so.servant;

             tr                y         {
         $self.in      sert_  l       onglong (val        ue);
       } finally {
                      _se    rvant_     postinvoke   ($so) ;  
      }
  } //   insert_longl ong

    
  /**
                     * Ins   erts       a long value int  o t       he DynAny.
        * The ID     L unsigned lo    ng long data type is mapped     to      the Ja  va long data type.
                         *
        * @excepti on Inval     i          dVa  lue if this DynAny h  as components         but     has a curre nt posi      tion o    f -1
               *  @     exception TypeMismatch if called on a DynAn   y whose current c      omponent   itsel    f has         components
                 */
  public voi    d insert      _ulonglo     n     g (long value) th      rows           org.omg.Dyna   mic  A      ny  .DynAnyPackage.TypeMisma  tch, org  .  omg    .Dynamic An    y   .Dy   nAnyPackage.InvalidValue
  {     
         or       g.om g.C       ORBA.portable.ServantObject   $so =         _servant_preinvoke ("insert_ulongl     ong", _opsClass    );
                      D   ynArr  ayO  perati   on  s  $sel f = (D ynArrayOp     erations) $so.servant;

       t    ry {
                   $self.insert_ulonglong (value);
      } finally {
            _servant_p    ostin      voke (    $so);    
      }
      }   // in  se    rt_ulo       nglong


     /**    
        * Inserts a char v          alue into the Dy  nAny.  The   IDL        wchar  data ty    pe is mapped to the              Java char      data type.
            *
        * @   exception Invalid    Value i    f   t        his DynAny has     components but h   as     a curr        ent position    of -1  
         * @exception T  ypeM               ismatc    h if called on a DynAny w  hose current   component itself has com  ponents
         */ 
             public vo    id     in  se rt_wch     ar (char value) throws org.omg.DynamicAny.DynA    nyPackage  .TypeMismatch, org      .  om    g.DynamicAny.Dyn      AnyPackage.Invali  dValu           e
  {
      org.omg     .CORB       A.portable.Se   rv       antOb       je    ct $s o   =  _serva      nt_prein      voke      ("insert_wchar"     ,     _opsClass);
      DynArrayOpera tions     $self =    (DynArrayOperations) $so.  s   ervant;

           try {
                              $self.     in     sert_wchar (valu e     );
      }  finally {
          _servant_pos  ti      n      voke ($so);
         }
  }      // in sert_wc     har 


  /**
                        *   Ins er ts      a  s   tring value int   o th    e Dyn Any.
        * B     oth bound  ed and unbou      nded strings are inserted using     this met  hod.
        *
               * @   exception  Invalid     V   alue if this DynAny has components but has a  current position of  -      1
        * @exc  eption In             validValue    if the stri ng inserte  d is longer than the             bound             o  f a bound    ed string
             */
  public voi  d insert_wstring (String value) thro ws     org    .o mg.DynamicA      ny.DynAn   yPackage. Typ       eMismatch  , org.omg.Dynam  icAny.DynAnyPac  kage.   In         validVal     ue 
    {
                         org.omg.CORBA            .por table.Ser    vantObj    e  c  t     $so = _serva   nt_preinvoke (    "    inser       t_wstring", _op  sCla  ss); 
       DynA  rrayOper          ations  $self = (DynArrayOperations) $so.   serv    ant;

      try {
            $sel  f.insert_     wstring (valu   e);
      } finally {
          _servant_post    invoke ($so);
           }
  } //    inser   t_wstring


  /**
             * I  nserts an Any value into the An y represented by this Dyn  Any.
        *
        * @ex ception InvalidValu    e if this DynAn     y has comp     o         nents    but        has a current position of      -1
             * @except    ion TypeMismatch                      if ca lled on a  DynAny whose curre   nt  com    ponen   t itself has components
           */
  p    ub  lic void  ins ert _any     (org    .omg.COR    BA.Any value) throws org.  omg.DynamicAny.DynAnyPackage.Typ   eMis   match, org.  omg.Dynamic   Any   .DynAnyPack age.InvalidValue
  {
      or g.    o  m g.CO     RBA.portable.Serva ntObject             $s     o      = _servant_p   reinvoke ("insert_any", _o  psC   lass);
         DynArrayOperations   $self   = (DynA     r     rayOperations) $so.s erva nt;

      try {
         $self.insert_any (v a lue);
      }  final    ly     {
                _servant_postinvoke ($s  o);
      }
     }             // in sert_any


  /**
           *         Inse   rt  s th        e Any value c  ontained in t   he paramete r Dyn     Any into the Any repr  esented by thi          s DynAny.
        *
            * @exception InvalidValue if this D        y   nAny has c    omponents but ha s a   curr     ent posi  ti     on of -1
                   * @excep t       ion TypeM   ismatch   if called on      a             DynAny who   se current     compone nt   it  self     has compone nts
                  */
   public void insert_d                 yn_any (org.omg.Dyna m     icAn    y.Dy nAny value) throw        s    org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org   .omg.Dy  namicA    ny  .D  ynAnyPackag   e.Inva      l     idValue
     { 
       org.  omg.CORBA.portable.Ser vantObject $s       o = _serva nt   _p reinvoke ("   insert_dyn_any"     , _opsClass);
        DynArrayOpera  tio       n  s      $self =       (DynArrayO  perations) $so.servant;
   
       t ry {
            $self.insert   _dyn_any (value);
          } final    ly {
          _serv ant_postinvo    ke       ($    so);
                                   }
  } // ins ert_d   yn_any    
          

  /**
        * Inserts a reference to a Ser  ializable obj   ect into this Dy     nAny.
        * The ID L Va  lueBase type  is mapp   e    d to the Java Serializ    abl    e   type.
        *
            * @exception I      nvalidVal  u   e   if     this DynA    n     y has components     but has     a current positi    on of -1
            * @excepti    on T   yp            eMismatch if        called     on a DynAny whose         current             com           ponent i    tself       h   as components
        */
            public void i    nser     t_val (ja va.io.Serial i   zable value)     th   row     s    org.omg.DynamicAny.Dy   nA   n         yPac   ka         ge.   TypeMismatch,   org.omg             .DynamicA    ny.DynAnyP   ackage.InvalidValue
  {
      org.omg.CORBA.portable.ServantObject $so    =      _servant_preinvok   e ("ins ert_val", _opsClass)   ;
               D        ynAr   rayOperation  s       $self = (D ynAr    rayO      pe  rations) $so.servant;

       try {
         $s   elf.ins                 ert_    val (va  lue);
         } f  inally   {
                _servant_postinvoke ($so);
        }
  } // insert_val
  

              /**
          * E xtracts    the bool ean value f        rom this DynAny.
        *
            * @excepti   on TypeMism        atch if the  a  ccessed component in the    DynAny is o   f a type
                 * that is    not equivalent to the  requ    ested type.
            * @exce   ption Typ   eMismatch     if      calle d on     a DynAny whose curr ent comp onent itself h   as components
           *    @exception I      nvalidVal  ue if this   DynA     ny has comp  onents but has a c   urrent posit    ion   of -1
                    */    
  pu  bl ic boolean get_boolean () th   rows   org.om         g.Dy       namicAny.DynAny    P   ackage.Ty   p           eMismatch, org.omg.DynamicAny.DynAnyPac  kage.Invali dValue
  {
         org.omg.CORBA.portabl     e.Ser  vantO     bject $s      o      = _servant   _preinvoke   ("g    e t_boolean"   , _ops    Class);
      Dyn       ArrayOperat        io    ns    $self = (DynArrayOperati  o      ns)      $so.   servant;

      try {
                       return $self.get_b   o    olean ();
        } fin  al   ly {
                   _servant_postinvoke ($so);
        }
  } // g et_boolean


   /**
                * Extrac     ts the byte value from   t    h       is DynAny. The IDL              octet da      ta t    ype is mapped to t    he J      av a byte data type.
            *
        * @exc    ep     tion TypeMismatch     if the accessed     compone  nt in the DynAny      i   s of a t  ype   
           * that i   s not  equivalent to the requested typ    e.
        * @e    x      ception Typ eMismatch            if c  al  led              on a DynAny whose    curr ent com      ponent itse lf has compo   nents   
        * @excepti  on   InvalidValue if this DynAny has components  but has a current pos ition of -1  
         */
  public byte get_octet (  ) thr    ows org.omg. D        ynami   cAny       .D   ynAnyPackage.T     ypeMismatc             h, org.omg.Dyn  amicAny.DynAnyPackage.   InvalidValue
  {
           o               rg.omg.CO RBA.portable.ServantObject $so = _servant_preinvoke ("get_octet"   , _opsClass);
              Dyn   Ar  r     ayO peratio  n s      $self = (DynArrayOp        erat      ion  s)       $so.servant;

         try {
              return   $self.get_o      ctet ();
      } finally {
           _serv ant_postinvoke ($so);
      }
  }     // get  _   octet


     /    **
        * Ex   tra     cts the c     har val         ue from this DynA ny.
          *    
                    * @exception TypeMismatch if the a cces  sed component in the Dyn  Any is of    a    type
        * that    is n  ot e   qu  i  valent to the reque   sted type.
               * @ex  ception Ty  peMi   smatch if called on a      DynAny wh   ose current co     m  ponent     it                 self has c   omp      onent    s
          * @exception InvalidValue        if this DynAny has components but ha         s a current    position of       -1
        */
  public char get_char    () throws o rg.omg.DynamicAn  y.DynAnyPackage.TypeMi    sma  tc     h  , org.omg   .Dynamic        Any.DynAnyPacka  ge.InvalidValue
  {
      org.omg.CORBA   .porta    ble.S   ervant          O   bject $so =     _ser       vant_prei    nvoke ("g  et_c har", _o   psClass);
              DynArrayOperations     $s      elf = (Dyn     A    rrayOpera    tions)    $so.s     ervant;

         try {   
                          retur       n $self.ge   t_char (   );
      } fi  na lly {
          _servant_p     ostinv oke ($s  o);
      }
  } //    get    _char


  /**
               * Extr    a                cts the sh or   t value from this DynAny.
                     *
        * @e   xception TypeMismatch       if th  e      acc    essed   component in t  he D   ynAny is o     f a t  ype
                   * that is not equ   ivalent to the requ     ested type.
         * @e    xce    pt       io    n Ty peMismatch if called o    n      a DynAn      y whose current component itself has co   m    po    nents
            * @  exception Inv  alidVal   ue if this              D    yn     Any has co    mponent s but has a current positio    n of -1
               */
  public s hort get_sho    rt () throws org.omg.Dyna  mi cAny.DynAnyP  acka  ge.TypeMismatch,   org.omg.Dynam  icAny.Dyn  Any  Pack    age  .InvalidValue
            {
              org.o   mg.CORBA       .por  table.Servan       tObjec   t     $so = _servant_pr   e invoke      ("get_s        hort",  _op         sC   lass);
       D     ynArrayOperation     s  $self = (Dyn      Ar   rayOpera        tions) $   so      .se  rvant;   

      try {
            return $se    lf.get_short ();    
         } finally  {
          _servant_p    os   tinvoke ($so);
      }
  } // get_short
   

  /**
           * Ext    racts the sho     rt value from this DynAny      . The IDL    u   short data type is ma                      pped to th  e Java short          d     at   a t  ype.
             *
                   * @exception    TypeMismatch    if t           he    accessed       comp    onen t in the DynAny is of a type
        * tha t is not e  quivalent to   the re   quested     typ   e.
             * @exceptio n Ty     peMismatch if       called on a DynAny whose curr      ent component it    self ha         s com     ponents
                  * @ex     ception Inval   i     dValu    e if this D  y  nAny h    a  s components       but          has a current pos         ition of -1
        *    /
    pu    blic short get  _ushort () thr  ows org.omg.DynamicA ny.Dyn    AnyPackage.   T             ypeMi  sma tch, o  rg.o   mg.      Dyna   mic  Any.DynAnyPackage.InvalidVa  lue
  {
      org    .omg.CO RBA.porta        ble.S   ervantObject        $so = _servant_pr     einvoke ("get_ushor    t", _   op     sClass);
      DynArrayOperations  $self     = (D    ynArra  yOperations) $so      .servant;

      try   {
                      return $self  .get_ushort ();
      } finally {
                         _servant_postinvo    ke ($so);  
                 }
  } // get_ushort


  /  **
        * Extracts     the in      teger val ue from this DynAny   . T          he IDL long data type       is mapped to the Java     int data type.
          *
        * @  e    xcepti on     Type    Mismatch if the accessed   component in the DynAny is of a typ   e
            *         tha  t is not       equiv     alent to the requested t    ype.
                   *    @exception    TypeMismat     c h if  called on a DynAny whos      e curren t   component       itself has comp  onen   ts
                 * @ex       ception InvalidValue if this DynAny     has         components but has a curren    t  p  osition of -     1
        */
  public int get_long ( ) thro ws   org.   omg.DynamicAny.DynAnyPack      age.TypeM i smatch, org.omg.Dy  namicAny.DynAnyPa   cka ge.I   n    v al       id   Value
     {
          org.omg.CORBA.portable.Serv antObject      $so  = _serva   nt    _preinvok  e ("get   _long", _opsClas     s);
       D     ynA  rray           O     pe rations  $self = (         Dy       nAr   r a   yOperatio    ns      ) $so.servant;
 
      tr    y {
            retu    rn $self.get_long ();
          } finally {      
                  _servant_pos  tinvoke          ($so);
      }
  } /  / g     et   _long


  /**
                * E      xtrac   ts t he    in  teger    value from t his DynAny   .  The IDL ulong data ty  pe is       map     p ed to th   e        Ja      va int data typ  e.   
        *     
                *  @ exception TypeMismatch      if   the ac   cessed         component i     n th e DynAny is o   f a        type
            * t   hat is     no  t   equiv      alent to the requ  es     ted type.
        *    @exception Ty   p    eMis    ma tch if call   ed o     n a DynAny w     hose curre nt comp     one nt itself has components
        * @exception InvalidValue if this DynAny has components but has a current    position of -1
              *  /
     public int get_ulong   () thro             ws         org.omg.D    ynamicA ny.D ynA       nyPac       kage.          TypeMismatch, or  g.omg.Dynam  icAny.DynA  nyPackage.InvalidVa    lue
  {
      org.   omg.C   OR  BA    .portabl             e   .ServantObject $s o = _serv ant_pre     invoke ("get_ulong", _opsClass);
       DynArrayOpera        tions  $s    elf = (DynArrayOpera    tio         ns)  $so.servant;

        try {
              return $self.get_ulong     ();
         }             final    ly {
              _servant_post     inv    oke ($so);
      }
  } // get_ulong


  /    **
        *          Extract     s t   he flo       at va    lu e fro  m      this    DynAny.                
               *
        * @except   ion TypeMismatch if t  he a       cce          sse d compone  nt i        n           the DynAny is of a ty           pe
        *     t     hat is not equivalent to th    e requeste        d type.
                   * @   exception TypeMism        atch if        called on a Dy    nAny    whose  current component                 itself has components
                    * @     except     ion     In    validVa       lue if   th     is Dyn  Any has component   s but ha  s a c  urrent      position of -1
        */
  public float get_float () th  rows org.omg.Dynam icAny.DynAn   yPack    a        ge  .Typ  eMisma     tch, or         g.omg.     DynamicAny.DynAnyPackag  e.InvalidValue
   {
      org.omg.CO  RBA.p   or    ta      ble.ServantObj  ec t  $so = _servan       t_preinvoke ("get_float"    , _opsC    lass);
      D  ynArrayO        perations  $self = (DynA rrayOp  erations) $so.      serva  nt;

      try {
         re     turn $sel    f.get_float           (     );
      } finally        {  
                 _      servant_post i  nvoke        ($so   )   ;
          }
      } //     ge t_fl  oat


  /**   
                                 * Extra cts the doubl  e value from         th is Dy    nA   ny.
             *
        * @exception Typ    eMi     s     mat         ch if the ac   ce      ssed c    om   ponent in the DynA ny is         of a type
        * that is not equivalent to the re           quested type    .
                 * @excep   tion T ypeMismatch if ca   lled    on a DynAny    whose curr    ent com        po    nent itself has components
              * @e  x       ception InvalidVa      lue i  f   this DynAny has components but has a current p  osition of -1
        */
  pub   lic double g et_  double ()   throws org.omg.DynamicAny.DynAnyPackage.TypeM   ismatch, org.omg      .Dyn     am     icAny.DynAnyPackage    .I    nval    idValue
  {
      o r g.omg.CORBA.portable.Serva ntObj     ect $so =     _se    rvant_preinv oke             ("get_double", _opsCla  ss);
          DynArr        ayOperatio    ns         $self = (D     y     nArray   Operat   ions) $so.servant;

              t   ry {
              re  turn        $    self.ge     t_d     oub     le ()  ;
          } finally  {
                          _servant_               postinvoke     ($so);
      }
   } // get_double


      /**
                 * Ex  tracts the str    ing value    from   this DynAny.
        *  B    oth bounded a  nd unbou  nded strings    ar         e ex  tracted using th       is method.
           *   
        * @ex    ception TypeMismatch if the accessed compone     nt in the DynAny is of a typ       e
            * that         is not equivalent           to the requested  typ   e.
              * @exception  TypeM  ism       atch if  called on a DynAny whose current component itself has components
                   * @      exc             eption   In   validValue           if this DynAny h   as compo  nents but has a c   ur            rent p            osition of -1
        */
  public String get_string ()       th         rows org.    omg.Dy namicAny.  Dy nAnyPackage.Type  Mismatch, org.omg.D   ynamicAny.DynAnyPackage.InvalidValue
  {
         org.omg.   CORBA.por    table.ServantObj   e    ct $s      o = _               serv a    nt_   pr einvoke ("g     e   t_string",   _opsClass)  ;
               DynArrayOp       erations  $s    el      f     = (       D   ynA     rrayOpe     rations)       $so.serv            ant;
       
          try {
         return $self.ge          t_string     ();
      }    finally     {
          _servant_postinvo     ke ($so);
      }
  } // get_string


  /**       
         * Ext    r  acts the r    ef  erenc   e to        a   CORBA Object     from this Dyn   Any.
             *
          * @exception     TypeMismatc   h    if the accessed component i   n the DynAny   is    of a type
          * t   hat is            not equivalent to the req         u   est   ed t  yp   e.   
        * @exception TypeMismatch if call   ed o     n     a DynAn    y whose     c    urrent component   i   tself has com   ponents
        * @ex  ception InvalidValue if        thi    s DynAny has components but has a cu r             re  nt po     sition     of -1
              */
  public org.     o  mg.CORBA.Object get_referenc  e () throws org.omg.DynamicAny.DynAnyPackage.TypeMi smatch, org.o mg.Dyn   amicA   ny.DynAn                        yP            ac   kage.Inva  lid     Value
    {
                org.  om  g.CORBA.portable. ServantOb    ject $so = _servan         t_preinvoke ("get_reference", _ops Class);
                        DynArrayOp       erations  $self = (DynArrayOper ations) $so.ser  v     ant;
     
      try {
         return $ self.ge t_reference      ();
      } finally {
            _servant_   postinv   oke ($so);
      }
  } // get   _reference


  /*     *    
        * Extr     acts   the           T    yp    eCode obje   ct     f     rom this DynAny.
         *       
          * @excep    ti       on TypeMism   atch i  f t   he accessed componen    t in the DynA  ny    is o  f a     type
        * that   is not   equivalent to          the req  u ested       type.
                    *       @except  ion TypeMi     smat  ch if      calle           d on a DynAn  y   whos  e curren  t component   itself         has components
            * @exceptio     n InvalidValue i  f       this DynAny has   compon             ents     bu  t has a     current p      ositio        n          of      -1
        */
        p                ublic o    rg.omg.CORBA.TypeCode g    et_type    code     () thro               ws o  rg.omg   .Dynami  cAny.DynA   nyPackage.TypeMismatch, org.om        g.DynamicAny.   DynAnyP  a    ck  age.InvalidVa  lue
         {
      org.    omg.CORB A.portable.ServantObject $so = _serv     ant_pr  einvoke (    "get_typeco      de", _opsClass    );
      D     ynArrayOpe    rat   i       on   s  $self = (DynArrayOper  ations)             $so.se r  vant ;
    
      try {
             return $self.get_t         y     pecode     (      );    
      } finally {
           _servant_postinvoke ($so);
                 }
        } // get       _typecod    e


  /*  *
        * Ex   tract   s the long        value from this D     ynAny. T   he        IDL long long dat   a type is mapped to the J        ava l   ong data type.
              *
          *      @exception     Typ         eMismatch if the accessed   componen   t in the    Dyn    Any is of a  type
        * tha   t is not equiv      alent to      t he reques    ted type.
        * @ excepti     on TypeM     ismatch if c    alled on a DynAny        whose current co  mponent itsel f has components
         * @ex      ceptio  n Invalid        Value   if th  is D     ynAny has c     omp   onents but has a cu  rrent position of -1
        */
  public                   long get_longlong ()  th   row s org.omg.DynamicAny.DynAnyPackage.TypeM   ismatch, org.omg.Dyn amicAny.DynAnyPackage.InvalidValue
  {
             org.omg.CORBA.portable.ServantObject $so = _ser  va      nt_preinvoke ("g      e  t_longlong", _o psC   la      ss);
      DynArrayOpera     tions  $  self = (DynArrayOper    ations) $s        o.s          ervant;

            try {
         ret    urn $self  .get      _longlong ();
         }     finally {
          _servant_postinv   oke ($so);
           }   
    }       // get_longlong


  /**
        * Extra     cts the lon         g value f  rom this DynAny.
        * The IDL unsigned long long da  ta typ        e is mapped t       o the J ava long data type.
                *
                 * @exception TypeMismatch if   the a   ccessed com   po  nent in the DynAny i    s of a type
        *  that is not equiva     lent to the requested type.   
        * @exception TypeM   ismatch if           called    on        a DynAny whose        current component    itsel f    has comp  one   nts
        * @except  io    n Inval idValu         e if this DynAny has components but has a curren t position of -1
        */
  public long get_ulon        glong () throws org.omg  .DynamicAny.DynAny    Package.TypeMismatch, org    .om   g.DynamicAny.DynAnyPackag    e.InvalidVa        lue
  {
      org.omg.CORBA.portabl    e.ServantObject $so   = _servant_pre       inv       ok     e ("get_ul onglong", _     opsClass);
      DynArra    yO     perations  $self = (DynArrayOperations) $so.servant;

           try  {
                    retu    rn      $self.          get_ulo          nglon g    () ;
          } fina lly {
          _serv    ant_postinvoke    ($so);
      }
  } // get_ulonglong
   

  /**
        * Ex       tracts the        long  value from this DynAny. The IDL wchar data     t  ype is mapped to the Java char     data type.   
        *
        * @exception Type     M      ismatch   if the accessed com  ponent in the D                ynAny is    of a type
         * that is not equivalent to th    e re  quested type.
             * @excep t    ion T      ypeMismatch if call    ed  on a DynAny whos      e   current component itself has   c  omp   onents
            * @exceptio  n Invali  dValue i f this DynAny    has compone    n  ts but has  a cur  rent position of -1
        */
  public cha   r g  et_wchar  () throw  s org.omg.Dyna  micAny.DynAnyPackage.TypeMismatch, org.o mg    .DynamicA    ny.D  ynAnyPackage.In  validValue     
  {
      org.omg.CORBA.p  ortable.ServantOb  ject $so = _servant_preinvok     e ("get_wchar", _  op    sClass);
      DynArrayO   perations  $self      = ( DynArrayOperati      ons) $so.servan     t ;  

       try {
         r   etu     r     n $self.get_wchar     ();
      } fi       nally {
          _servant_p       ostin  voke ($so);
            }
  } / / get_wchar


  /**
        * E   xtracts the strin g value from thi   s DynAny.
             * Both b  o        unded a   nd unbounded strings are    extracted using this method.
        *
        *  @exception TypeMismatch if the accessed component in the DynAny is of a type
          *  that     is not equiva  lent     to the requested ty  p    e.
             * @exc eption TypeMismatch if called on a DynAny whose c             urrent compo nent itself has     components
        */
              public String get_wstr  ing () throws org.omg.DynamicAny.DynAnyPackage.TypeMisma   tch, org.omg.Dy     n  amicAn   y.D  y    nAnyPackage.Invalid    V    alue
  {
      org.o   mg.C  ORBA.porta ble.ServantObject $so = _serva           nt_preinvoke ("    get_wstr    ing", _op    sClass);
      DynArrayOperat  ions  $self    =  (DynA    rrayO    perations) $so.servant;

            tr              y    {
         return $self.get_wstring ();
              } finally {
               _servant_postinvoke ($so);
      }
  } // get_wstring


  /**
              * Extracts an A   ny va lue c ontained in the Any repres   ented by th  is DynAn y.
        *
        * @exception Ty  peMismatch if the accessed component in the DynAny is of a typ      e
        *       that       i     s not equivalent to the requested type.
        * @exception   Type  Mismatc     h i    f c      alled on a DynAny whose    c urr      ent c   omponent itself has comp   onen   ts
               * @exceptio n InvalidValue if this   DynAny has component   s but has a current positi  on o   f -1
           */
    public org.o      mg.CORBA.Any   get_any  () throws org.omg.Dynami    cAny.DynAnyPackage.TypeMism     atch, org.omg.Dynamic  Any.D      ynAnyPackage.Invali        dValue
  {
      or   g.omg.CORBA.portable.S  er      vantObject $so      = _servant_preinvoke      ("ge  t_any", _opsClass);
              DynArrayOper  ations   $self = (         DynArrayOperations) $so.servant;

                 try {
          re         turn $self.get_any ();
        } finally {
          _servant_post  in   voke ($so);
      }
  } // get_any


  /**
        * Extracts the Any value c     ontained in the     Any      represented by this DynAny and returns it wrapp      ed
        * into    a new DynAny.
          *
        * @exception TypeMismatch if the accessed     component in the   DynAny is of a type         
        * that is not equi valent to the r   equested t  ype.
              * @exception TypeMisma      tch    if c   alled on a DynAny whose  cur  rent componen t itself has components  
        * @excepti  on Inval  idValue if this DynAny has components but has a current position of -1
           */
  public org.om      g.DynamicAny.DynAny get_dyn  _any        () throw  s org.omg.DynamicAny.DynAnyPackage.          TypeMismatch, org.omg.DynamicAny.DynAnyP ackage.Inval    idValue    
  {
      org.omg.CORBA.port    able.S  ervantObjec  t $so = _serv an  t_preinvoke ("get_dyn_any", _opsClas  s);
      Dyn    ArrayOperat    ions  $self = (DynArrayOperatio       n  s)   $s    o.servant;

      try {
         return $self.get_dyn_any ();
           } finally {
          _ser   vant_postinvoke (   $so);
      }
  } // get_dyn_any

     
    /**
        * Extracts a Serializable object from t    his DynAny.
        * The IDL ValueBase type     is mapped to the Java Serializable type.
        *
        *   @exception T ypeMismatch    if the acc   essed component in the DynAny is   of a    type
        * that is     not equ   ivalent to the re  queste   d type.
             * @exce   ption TypeMismat   ch if called on a D  yn    Any whose c  urrent    component itself has components
        * @exception InvalidValue    if this DynAny has components but has a current position of -1
                    */
  public java.io.Serializa     ble get_val () t  hrows  org.omg.Dynamic Any.DynAnyPackage.TypeM ismatch, org.omg.Dynami   cAny.DynAnyPackage.InvalidValue  
  {
      org.omg.CORBA.portable.ServantObjec       t $so = _    servan  t_preinvoke ("get_va    l", _opsClass);
      DynArrayOperations  $self = (DynArrayOperations   ) $s        o.servant;

      try     {
            return $self.get     _val ();
      } finally {
               _servant_postin    v oke ( $so);
      }
  } // get_val


  /**
        * Sets the current position to index. The current position is indexed 0 to n-1, that is,
        *    index zero correspon   ds to the first component. The operation returns true if  the resulting
        * current position indic    ates a component of   the DynAny and false   if      index in d    icates
        * a position that does  not correspond to a comp   onent.
        * Calling seek with a negative index is legal. It     sets the current position to -1 to ind   icat       e
        * no component and returns false. Passing a non-negative index value   for a D  ynAny that does not     
            * have a component at the corresponding position sets          the c ur      rent posi tion to -1 and return    s false.
            */    
  publ  ic boolean seek (int index)
     {
      org.omg.CORBA.p   ortable.ServantObject $  so = _servant_preinvo  ke ("seek", _opsClass);
      DynArrayOperations  $self = (DynArrayOperations) $       so.servant;

      try {
            return $self.seek (index);
      } fi   nally {  
               _servant_postinvoke ($so);
      }
  }      // seek


  /**
        * Is equiva       lent to seek(0).
           */
  public void rewin        d ()
  {    
      org.omg.CORBA.portable.S  e rvantObject $so = _servant_preinvoke ("rewind"    , _opsClass);
      D   ynArrayOperat     ions  $self = (DynArrayOperations) $so.servant;

      try {
         $self.rewind ();
      } finally {
          _servant_pos   tinvoke ($so);
           }
  } // rewin     d


  /**
        * Advances the curren    t position  to the next component.
          * The operation returns true while the resulting current position indic     ates  a com       ponent, false ot   herwise.
        * A false return v  alue leaves the current      position at -1.
        * Invoking next on a DynAny without components leaves the current position at -1 and      re      t         urns false.
             *   /
  public boolean next ()
  {
      org.omg.CORBA.portable.ServantObjec   t     $so = _   servant_prei    nvoke ("next", _opsClass);
        DynA  rrayOperations  $self = (DynArray Operations) $s   o.servant;

      tr  y {
             return $self.next ();
      } finally {
            _servant_postinvoke ($so);
      }
  } // next


  /**
        * Returns the    number of   c   omponents of a DynAny.
        * For a DynAny without components, it    returns zero.
        * The operation only counts the components at the top level.
        * For example, if   component_count is invoked on a DynStruct with a single member,
               * the return value        is 1, irrespective of the     type of the member.
           * <UL>
          * <LI>For sequences, the operation returns the current number of elements.
              * <   LI>For structures, exceptions, and value types, the operation returns the number of members. 
             * <LI>For arrays, the operation returns the       number of elements.
        *      <LI>For unions,  the operation returns 2 if the discriminator indi   cates that a named   memb er is active,
        * otherwise, it returns 1.
           * <LI>For DynFixed and DynEnum, the operation returns zero.
          * </UL>
        */
  public int component_count ()
  {
      org.omg.CORBA.portable.ServantObject $so = _servant_preinvo    ke ("comp   onent_count", _opsClass);
      DynArrayOperations  $self = (DynArrayOperations) $so.servant;

      try {
         return $self.component_count ();
      } finally {
          _servant_postinvoke ($so);
      }
  } // componen   t_count


  /**
        * Returns the DynAny for the component at the current position.
        * It d  oes not advance the current posi tion, so repeated calls to current_component
        * without an    intervening call to rewind, next, o   r seek return the same component.
        * The returned DynAny object reference can b   e used to get/set the value of the current component.
        * If the current component represents a complex type, the returned reference can be narrowed
        * based on the TypeCode to get the interface corresponding   to the to the complex type.
        * Calling current_co       mponent on a DynAny that cannot have components,
        * such as a DynEn um or    an empty exception, raises TypeMisma    tch.
        * Calling current_component on a DynAny whose current position is -1 returns a nil reference.
        * The iteration operations, together with current_component, can be used
        * to dynamically compose an any value. After creating a dynamic any, such as a DynStruct,
        * current_component and next can be used to initialize all the compo    nents of the value.
        * Once the dy  namic value is completely in      itialized,     to_any cre      ates the corresponding any value.
        *
        * @excepti  on TypeMismatch If called on a DynAny that cannot        have components,
        * such as a DynEnum or an empty exc        eption
        */
  public org.omg.DynamicAny.DynAny current_component () throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch
  {
      org.omg.CORBA.portable.ServantObject $so = _servant_preinvoke ("current_c   omponent", _opsClass);
      DynArrayOperations  $self = (DynArrayOperations) $so.servant;

      try {
         return $self.current_component ();
      } finally {
          _servant_po         stinvoke ($so);
      }
  } // current_component

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/DynamicAny/DynArray:1.0", 
    "IDL:omg.org/DynamicAny/DynAny:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
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
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _DynArrayStub
