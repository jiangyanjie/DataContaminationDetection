package org.omg.DynamicAny;


/**
*    org/omg/DynamicAny/_DynUnionStub.j        ava .
* Genera   ted by  the IDL-to-Java comp  iler (portable),     version "3   .2"
* from c:/re/workspace/8-2-build-windows-amd64-cygwin/jdk8u131/8869/corba/src/share/classes/org/omg/DynamicAny/DynamicA   ny.idl
*     Wedne      sda    y   , March 15, 2017 1:25   :04 AM PDT
*/


/*    *
    * DynUn   ion obj  ects support the man   ipulation of        IDL unions.
    * A u   nion c            an have only    two valid current positions:
        * <       UL >
      * <LI>zer   o, which denotes the discrimin         ator
    * <LI  >one, which denotes the acti   ve    memb er
      * </UL>
    * The component_coun    t value  for a union depends o       n the cu  rrent     discriminator:
      * it is 2 for a union    whose disc     rimina     tor indicates a named me mber, and 1 otherwise.
     */     
pub lic class _DynUnionStu    b extends org.omg.C   ORBA.porta    ble.ObjectImpl imple ments org.omg.DynamicAny.DynUnion
{
           final public static java.lang.Class   _o psClass = DynUnio nOpera     tions.class;



     /* *
         * Ret     urns the current discriminator v      alue.     
        */
  public org.omg.DynamicAny.DynAny get   _discriminator (  )
  {
                                 org.omg.CORB     A.portable.Servant             O   bject     $so = _serva   nt_preinvoke   ("get_discrimina    tor", _opsClass);
           DynUnionOperations  $self = (D      ynU   nio  nOper atio ns) $s  o.se         rva nt;

       try {
         re       turn $s         elf.get_d    iscrimi  nator ()          ;
      } finally {
          _s     erv    an t_   postinvoke     ($so);    
      }
  } // get_dis  criminat       or
   

  /**
                    *  Sets the di      scriminato  r      of the DynUnion to the specified value.
        * S   etting the dis       crimina  tor to          a value that is      consistent with  the cu             rrent ly ac     ti    ve union mem ber
          *      does no    t affect the curre  ntly active mem    ber. Setting the d  iscriminator to  a    v   alue tha t is inconsistent
                            * with the cu     rrently    active member deacti    vates the member    and    act  ivates the m  emb er that is       consistent
          * wi    th the new discr  imi    nat        or value (if there is a me mber     for th          at value) by init ia        l  izi  ng th             e memb     er
        * to its de fault v   a  lue. 
                   * Setting t   he discrimina tor    of a u   nion set   s the current pos    ition to 0 if the di s criminator value
          * indicates a non-existent un  ion    memb    er (ha  s_no_acti ve_membe r returns tr    ue in this case).
              * Ot  herwis  e, if th   e discrimina    tor val  ue       indic      ates a      na  med union    member, t h  e    curr  ent position is se t to 1
        * (ha s_n      o_active_      member r etu  rns false     and co      mponent_count   returns 2     in      this case).
                        *
               * @exception Typ  eMismatch if the TypeCod   e of the   parameter is       not eq  uivalent to the       TypeCode
          *              of the un ion's discriminator
        */
  public    void set_d        iscrimina   tor (org.omg.Dy    namicAny.DynA     ny    d) throws org   .omg.D     ynamicAny.DynAnyPackage.Type   Mismat ch
  {        
           o        rg.o mg.C ORBA.portable.ServantObject $so = _servant_preinvoke ("set_d     iscriminator", _   opsCla   ss);
      Dy     nUni onOperati   ons  $self = (DynUni       on  Operatio   ns) $so.servant;

      tr         y   {
         $sel f.se      t   _  discrimin   ator (d     );
           } f  inally      {
                 _servant_post   invoke ($so)  ;
      }
    } // set_discriminato       r


  /**
        * Sets the discriminator to a v      al ue that is      c      onsi          s       ten  t with the v      a   lue of the default case of a union   .   
                  *     It sets   the curr        ent                positi     o   n   to zero a  nd      causes com    ponent_count to       ret      urn 2.
        *
        * @exception TypeMismatch if the union         d   oes not have    an explicit default c     ase
          */
      p   ublic void   set_to_d  ef ault_membe  r () thro ws or  g  .omg.  DynamicAn   y.DynAnyPa      ck       a    ge.TypeMismat   ch
  {
        org.omg.  CORBA.portable.Ser vantObje   ct $so = _servant_p  rei      nvoke (    "  set_to_d efault_member"     , _opsClass );
              DynU nio     nOperations       $   self = (DynUnionOperations) $s    o.servan    t;

      try     { 
                                      $s elf.set_to_def  au     lt    _member    ();
         } fin   al      ly {
                     _se    rv     ant_postinvo ke    ($so);
         }
  } // se    t_ to_default_mem ber


  /**
        * Sets the discriminator to a value that does n   ot               correspond to  any of the                    unions case la b    els.
             * It sets the current position to zero    and cau    ses com  ponent_c   ount to re   turn        1     .
         *
         * @exception T   yp         eMismatch if the uni     on has an ex p  licit defaul     t case or if it uses the                 en   tir   e          range
        *            of discriminator value  s for expl   ic          it cas  e     labels
        */
  public void se    t_t   o_no_a       ctive_member () throws     org.o  mg.DynamicAny.DynAnyPackage.Ty   peMismatch
  {
      org.omg.CORBA.portable.ServantObject $so = _servant_preinvoke ("    set_  to_no_active_member", _opsClass);
              DynUn   ionOperat   ions       $     self = (DynUnionOperations) $s  o        .serva  nt;    
 
      try {
          $  self.set_to_     no      _active_member ();
         } finally {
          _serva  nt_postinvoke ($  so);
      }
  } // set_to_n     o _active_memb   er


  /**
               * Returns tr     ue     if th e unio          n has no active me  m be r, that   is       , the unions     value consists solel     y   
          * of its  discrim    inator be   cause      the discr  imin       ator       has a value       that is not liste d   as an exp      licit ca     s  e labe    l.
         *      Calling this       operation   on a un   ion that has a de     faul t case retu           rns f   alse.
          * Calling this ope     r    ation on  a     union        that uses    the entire range of discriminato     r valu  e    s
        * for explicit case l  abels retu     rns fal     se.
        */    
     public b    oolean has_no  _active_mem    ber ()
  {
          org.    o      mg.C   ORBA.portable.Servan            t   Ob j  e  ct $so = _  s     erva    n  t_prein voke ("has_no_active_member",    _opsClass);
      DynUnionOperations  $self =                   (DynUn  ionOp  erations) $so.s    ervant;

      try {
         r      eturn $self.has   _  no    _active_member            ();
                   } finally {
            _serva  nt _pos  tinvok   e   ($so);
      }
  } //       has_no_a   ctive_      me mber 

   
   / **
        * Returns       the TCKind value of t  he discrimina       to   r   s       Type            Co      d  e.
        */
  publi  c or g.omg.C       O    RBA.TCKind discriminator_ki nd ()
  {
               org.om   g.COR       BA.portable.Ser vantObject $so = _ser   vant_preinvoke   ("discriminator_kin   d ", _opsClass );
      DynUnionOpera    tions  $self =   (DynUnionOpe  rati    ons)     $s  o.servant;

          tr   y { 
              return $self.d     iscrimi   nator_kind ();
         } finally {
                        _s     ervant_postinvo  ke ($so);
              } 
  } //     discrim         inator_kin  d


  /**
          * Return  s  the TCKind va      lu  e of the  currentl  y     active members TypeCode. 
        *
        * @exception Invali dValue    if the         union does not have a currently active   memb er
            *  /    
  public org.omg.      CORB    A.TCKind member_kind   () t  hrows org    .omg.DynamicA         ny.Dyn   AnyPackage.     Inva         lidValue
       {
          org.omg .CORBA      .portable.Serv antO   bje   ct $so = _serva   nt_preinv  oke ("  member_ki   nd",         _opsClass);
         DynU    nio   nOperatio    n           s  $   self = (Dyn  Union Ope    rations) $so.servant;   

               try {
         retu     rn $self.member_kind ();
            }    finally {
            _servant_post  invoke     ($           so);
           }
  }     // member_kin          d


  /**
          * Retur  ns    the currently active mem   ber.      Note th    at the  returned r        efer   enc e rem   ains valid        only
        * for as lo    ng as th  e currently a  c   tive member does not change. Using the returned       refe    rence 
                                            * beyond t   he life tim    e of the cur    ren  tly activ  e member r          aises OB J      E        CT _NOT_EXI  ST. 
              *
          * @exception Inva    lidValue if     the   unio  n has no      active member
         */
  publi  c org.       omg.Dyna       micAny.DynAny member () throws org.       omg        .Dy  namicAny.DynAnyPa     ckage.InvalidValue
  {
              org.omg.CORBA.portable.ServantObj   ect $so =    _servant_preinvo    ke (    "    member",        _     opsC      las       s)      ;
      DynUnio       nO  perat   ions  $self =    (Dy    nUnionO perat    ions) $so.            ser     vant;

      tr   y   {
          return $self.member    ()             ;     
         }    finally {
                 _s   er     vant_p    ostinvoke ($so);
         }   
  } // member


  /**
           * Returns the name    of the cu r  rentl   y active member. If the unions     TypeC  o   d                e   does not conta    in
                   *     a mem ber nam e fo  r th      e   currently        a    ctive member, the   o    p   er    ation returns an empty s tring.
           *
           *     @      ex  ce   ption InvalidValue     if    the union ha s no active member   
        *    /     
  public String member_name ()    throws org.omg.Dynam     icAny.DynAn  yPackage.InvalidValue
  {   
           org.omg.CORBA.portable.S    ervantOb  ject $s         o = _ser    vant_preinvoke ("member_na  me"           , _ops  Class);
            DynUnion    O  perations  $     self      =        (DynU  nionOpe    rat   ions   ) $so.s ervant;
    
                   try {   
         return $self   .member_name ();          
          } finally {
                  _s  ervant_postinvoke               (   $so);
      }
         } // member_name  


  /**
               *    Retu   rns the TypeCode  as   sociated with t  his DynAn     y o  bject.
              * A DynAn  y     obje       ct    i    s created    with a TypeCode value assigned to it.
            * T   h   is TypeCo de                 va lue d  et   er mines the t   ype of the va lu            e handled t      hro        ugh the DynAny object.
          * Note that      t   he TypeCo   de associated      wit  h a DynAny obje      ct is init           ialized at the   time      the
                  * DynAny is   crea     ted a     nd cannot           be changed during l  if  e  t   ime of the D    ynA    ny objec             t.
              *
             * @return The TypeCode associated with th        is DynA   ny   object   
                */
         public org.omg .CORBA.TypeC      ode       ty       pe ()
  {
      org    .omg.CORBA.portable.   Ser   va   ntObject  $so = _servant_pr   einvoke ("type", _opsClass)          ;
                     DynUnio  nOpe r  ations   $self =    (DynUnion      O    perati      ons) $so.servant;

      try {
                      return $  self     .t    ype ();
      }        finally {
          _   ser vant_    p     ostinvoke   ($so);
               }
  } //    t  ype
    

  /    **
        * Initi  al         izes the value associated with    a      DynAn     y obje ct  wit    h th e value
          * associated with ano the r DynAny       obj    ect.
        * Th e current position of   the targ  et DynAny is s       et to zero f    or values tha      t ha        ve components
            *      and to -     1 for v alues   that do no     t have components.
        *
           * @param dyn_   any
        *  @exception TypeMismat     c  h if the type    of th    e pass     ed DynAn  y is     not equi  va   le  nt t  o the type of t      arget DynAny
                  */
  public void assign (org .    om      g.D  ynamicA      n y.DynAny    dyn    _any) thro ws       org.omg.DynamicAn   y.D   ynAn           y    Pa     ck   age.Typ     e Mismatch              
  {
      org.omg.CORBA.po  rtable.ServantObject $    so   =  _servant_preinvoke ("assign      ", _opsClass);
      Dyn    Union        Opera   tio        ns  $self = ( DynUnionOperations) $so.serva   nt;

      tr   y {
                  $self.assign (dyn  _any);
         } finally         {
              _servant_postinvok e ($so);
         }
       } // assi gn


  /**
        * Initial       izes the   value associat  ed     w   ith      a DynAny       object with the va      lue cont  ain     ed in an any.
           * Th    e   cu    r rent        posi  tion of   the target Dy  nA ny is     set to zero for va  lues that   ha ve     com   ponents
                            * and      to    -   1 for values that do     not    have       components.   
          *
             * @exc  epti   o            n TypeMismatch      if the type     of    th   e pas   sed Any is    not e    quivalent to the type of     tar    get DynAny
        * @exception I  nvalid   Value if the passed Any does not co nt  ain       a  l egal value    (such   as a null   string)
              */
  public void from_any (org     .omg.CO                   RBA.Any value) throws       or       g.omg.DynamicAny.DynAnyP ackage.Typ         eMismatc   h, org.omg.DynamicA  ny.D   ynAnyPa   ckage.Inval  idVa l   ue
  {
         org.omg.CORBA.    portable.Serva   ntObject $so = _serv ant_       prei  nvoke (      "f    rom     _any ", _opsC   lass);
       Dyn   UnionOperatio  ns   $    self   = (DynU          ni onOperatio    ns)     $so.servant  ;

                  try {
               $self.from_any (value      );
      } fi   nally {
          _servan t_post   invo  ke ($     so     );
              }
    } // from_any


            /       **
        *       Creat  es  an a   ny valu     e from a    D  ynAny    object.
          * A copy o    f     the TypeCode associated with   the DynAny  obje    ct is ass      igned to the resul   t    in                 g an       y.
                  * T    he v  alue associ   ated w     i       th      the DynAny object        is copied i     nto the any.
          *
                        * @return      a new An       y ob je  ct  with          the same value and T   ypeCode
           *  /
       public org.omg.CORBA.Any to_any ()
  {
             org.om    g.CORBA.po  rt  able.Se    rvantO bject   $so =  _ser   va  nt_preinvoke ("to_an         y", _opsClas           s)       ;
        DynUnion  Operations  $self       = (DynUnionOperatio     ns) $so    .ser     vant;

      try {
            return $self.to  _an  y ();
      } f  inal    ly {
             _serv    ant_po   s tinvoke    ($so);
      }   
     } // to_any


  /*  *
        * Compares     two       DynAny values for equality.
        * Two Dyn  An  y values         are equal if    their     TypeCodes are equivalent and, recur         sively   , all      c       om  po                nent    DynAnys
           * have equal values.
           * The current  position of the two  DynAnys      bei   ng com   pare   d     ha           s no              eff  ect on  the result of equal  .
                  *
               * @re   turn true of the D     yn   Any   s are equa   l, false ot her    wise
                    */
      p     u   blic boole  an equ    al    (org.omg.   Dy   namicAny.D     ynAny dyn_a   ny)
  {
       org .  omg.CORBA.por  table.S  ervantOb  ject $so   = _    servan   t_p reinvoke ("equal", _     opsClas         s)                ;
      Dyn  UnionOpe    rations      $self = (DynUnionOp   er ations) $so.serv  ant;

      tr       y {
               retu    rn $self.equal (dyn_a  ny);
        } finally {
          _se  rv      ant_p  osti  nvoke ($so); 
      }
  } // equ    al

    
  /**
              * Destroys a            DynAny object.        
             *    Th   is o   per   ation frees  a   ny resources used t        o repres   ent the data value a   ssocia ted wi  th a   DynAny object.
        * It must       be in   v          oked         on       references ob    t ained from     one of       the  cre        ation operat    ion s on th      e OR    B inter        fac  e
           * or on a ref          erence r  eturn  ed     by D     ynAny.copy() to    a v         oid  resource leaks.
                  *     Invoking de  str oy on component       DynAny objects (for example, on objects returned by the
             * current_c   omponent   op        e     ration)    do          es nothin  g     .
        * Destruction of a D       ynAny object implies dest       ruction of all      D    ynAny objects obtained f   rom it.
        * That   is,    refere n      ces t  o componen   ts of a destroyed DynAny become inva    lid.
               * Invocat   ions on such references raise        OBJECT_  NOT_EXIST     .
                * It   is possible to     manipulate a compon  ent of a DynAny beyond      the l   ife time     of the         DynAny
        * from which the component wa   s     ob    tai  ned by making a copy o    f  the component        with t          he copy opera    tion
        * before destroying th       e DynAny f  rom which the   component w     as obtained.
             */ 
  p ublic  void      destroy ()
  {
                 or      g.omg.CORBA.portab   le.Se rvantObject $so =       _serva     nt_preinvoke    ("destroy", _opsClas    s     );
      DynU   nionOpera    tions  $s         elf = (DynUnio  nOperati    ons)    $    so.servant;

      try {
           $self.destr  oy ();
         } fi       nally {
           _   servant_postin     voke ($so);
         }             
  } // destroy


  /**
        * Creates    a new DynAny object whose    value is a d    eep copy of the DynAny on whic h   it is in  voked.
            * The operat     ion is polymorphic, that is, invoking i  t     on one of    the typ    es d erived from DynAny,
            * such as DynStruct  , cre       ates th   e derive   d type but ret urn     s its ref   erenc     e as the Dyn   Any bas           e type.
          *   
           * @return   a deep copy  of th    e DynAny  o                bject
          */
               p   ublic     org.omg.Dyn  amic     An y.DynAny c  op  y ()  
  {
      org.omg.CORBA   .porta    ble.ServantObject $so = _s er vant _preinvoke ("copy", _op   sClass);
           DynUnio      nOperati ons  $self = (DynUnionO    p    erations) $so.servant;

      try     {
              return $sel  f.copy ();
           } f  inally {
                        _serva      nt_posti   n   voke                 ($so);
               }
  }            // cop   y


  /**
                  * Inserts a bo    olean valu  e into      t  he DynAny.
        *
                 * @ex   ception Invali  dValue if    this DynAny h  a       s components but has  a current position of -1
             *    @except      ion TypeMismatch if c      a    lled on a D     ynAny whose current component itself has component s
            */
  public void   ins   ert_bool       ea   n    (boolean value) throws org        .omg.DynamicAny.DynAnyPackage.TypeMismatch ,       org. o    mg.D   ynamicAn                 y  .DynAn    yPackage.In    validValue    
  {
        org    .omg.CO    RBA.portable.ServantObje   ct     $so = _serv      an  t_pr    einv     oke    ("insert_bool       ean", _o     psCl     ass);
         DynUnion  Opera   tions  $self = (DynUnionO     pe   rations) $  so.ser       vant;
    
      try {
             $self.inse  rt_bool         ean (value);
        } finall      y       {
                        _    ser     vant_pos   ti    nvoke ($so);
      }
  } // in        sert_bool           ean


  /**
        * In serts a   byte value into the DynAny. The IDL octet data type is mapped  to the Java byte data typ         e.
                *
        * @exception I      nvalidVal        ue if this     DynAny h       as components bu   t has a current pos  ition of -1
        *    @exception Type     Mis      match      if calle d on a  DynAny whose cur  r   ent com ponen       t  it         sel    f has     c  omponents    
                   */
  p    ublic    vo  id insert      _octet (b      yte value) throws org.omg.DynamicAny.D  ynAnyPack        a     ge.TypeM     ismatch, org.omg   .D  ynamicAny.DynAnyPackage.Invalid   Value
  {
            o  r  g.omg.  CO   RBA.portable.ServantObject $so = _servant_pre  invoke   ("insert_     octet", _o  psClas    s);
            DynUnionOperations  $self = (DynUnionO           perations    )      $so.servant;    

      try {   
                        $self.insert     _octet (value);
      } finally {
           _servant_postinv      o  ke ($so    );
            }
    } /      /    insert_octet


  /*    *
        * Inserts a        char value into the Dyn           Any.
        *
                  * @exce ption InvalidV  alue if t   his DynAny     has comp       onents but has a c  urre   nt positi         on of -1
         * @exception   TypeMismatch if  called    on a        Dyn       Any whose current component itself has components
        *   /
  pu   blic void insert_cha    r (c   har  value    )    throw    s org    .omg.DynamicAny.   DynAny Pack age.TypeMismatch, org.omg.  D  y   nami       cAny.DynAnyPackage.InvalidVa  lue
  {
      org.omg.CORBA.portabl   e.ServantObje       ct $ so = _servant_prei   nvoke ("insert_char                 ", _opsClass);
             DynU nionO    perations    $self = (Dyn    UnionOp    erations) $s   o.servant;

      tr         y {
                   $s el     f.insert_char (value);
      } f   i    nal     ly {     
          _servant_post        invoke (     $so);
            }
       } // insert_ char    

      
  /**
                *     In  serts a       short  value i   nto th  e DynA    n     y.
             *
              * @excepti  o    n Inva l idValue if    this Dy  nAny has compon  ents but ha     s   a current positio   n of -1
                    * @  ex cep        tion TypeMis  match         if cal   led       o   n a DynAny whos e cur ren   t component itsel   f ha  s components
               */
      publ     i     c void inser         t_short (short value) throws or     g.omg.D      ynamicAny.DynAnyPackage.TypeMis      mat     ch, org.omg.D   ynami  cA   ny.Dy nAnyPackage   .Inval   idVa     lue
  {
      org.omg.CO     RBA.portable.ServantObj   ect $so = _   serva   nt _pr   einvok    e ("insert_short  " , _opsClass);
      Dy  n          UnionOpe rati    on   s  $   self = (Dyn      UnionOp  eratio   ns) $so.servant;
 
         try    {
         $s    el  f.i    n  sert_s         hor  t (value);
      } finally     {
          _s     ervan    t_postinvoke ($so   );
             }
  } /     /    inse rt  _short


     /**
          * Ins    erts a short value into the D ynAny.   The IDL ushort data ty pe is mapped to the   Java sh  ort    data    type.
             *
        * @exception   InvalidValue if this D  y   nAny h    as components but has a current position of -1
        * @exception TypeMismatch i      f called on      a DynAny     whose current component itself has components
        */
  pu blic void    insert_ush    ort (short val     ue  ) throw     s org.o   mg.DynamicAny.Dy    nAnyPackage.TypeMismatch,    org.omg.Dynami         cAn  y.DynAny           Package .InvalidValue
  {
      org.om  g.COR  BA.         porta  ble.ServantObject $so = _serva  nt_prei       nvoke ("ins        ert_ush    ort", _opsClass);
         DynUnionOpe      ratio        ns  $  self = (DynUnionOper ations) $s o.servan   t;

      try   {
              $self.insert_ushort (va  lue);
         } f inally    {
                _s ervant_postinvoke (  $so);
                    }
  } // inse    rt_us   hor   t


  /**
              * Inserts an       intege   r value into the DynA    ny. The IDL long d ata ty      pe i   s m   apped to              the Java            int data type.
        *
               * @exception Invalid     Value   if this  Dy nAny has components but    has a cur    rent po   siti   o           n of -1     
              * @exception      TypeMi smatch if cal            led on a D       y  nAny whose curren   t component   it   se      l   f   ha      s components
        */
  publ       ic void insert_l  ong (           int value) throws org   .omg.DynamicAny.DynAnyPackag   e.TypeMismatch, or g.omg.DynamicAn     y.D    ynAnyPackage.In   validValue
       {    
      org.om    g.CORBA.po r      table.Serv   antObjec       t     $so =   _servant_p     reinvoke ("          inse     rt_lon  g", _opsCl  ass)     ;
                           DynUnio     nOperations    $se   lf = (DynUn    ionOperati   ons) $so.s      ervant;

          try {
             $self.i nsert_long (value);
      } finally {
              _serva   nt_ postinvoke   ($so);
      }
  } //  inse rt_long


  /**
              * In  serts             an integer value in      to the DynAny. The IDL           ulong da  ta t   ype is    ma     pped to the Java int data ty    pe.
          *
        * @exception InvalidValue if this DynAny has  com    p   onents but has a current position of -1
        * @exc    eption TypeMismat  ch if     called on a DynA  ny whose current compone     n   t itself has      componen   ts    
           */
  public voi     d insert_ulong     (int value) thro      w    s   org            .omg    .Dynami        cA   ny   .DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.Invali   dV    a                   lue  
  {
      org.omg.     CORBA.p   orta  ble   .ServantO    bject $so = _servant_prein     voke ("insert_ul    ong",     _opsClass);
      DynUnionO  p   erations  $self = (DynUni    onOperations)     $so     .ser    vant;

          try {
           $s      elf.insert_ul  ong (value);
       } finally {
                      _se   rva nt_postinv    oke ($so);   
         } 
   } // inse  rt_u     long


  /**
             * In  se    rts a float value into the DynAny.
                   *
        * @exception InvalidValue if this     D   ynAny h    as         components but ha      s a current posi       tion   of  -1
                    *   @exception TypeM      ismatch if cal  l                ed on a   DynAny who     se curr        ent com   pon  ent i ts    e    lf has components    
          */
  public vo   id    in  se   rt_float (float value) th   rows org.omg.DynamicAny.DynAnyPac   k  age.Type Mis   match, org.omg.DynamicAny.DynAnyPackage.InvalidVa lue
  {
        org.  omg.CORBA.portable.Se          rv   an  tObject $so = _ser    vant_   p    re   invoke ("insert_float", _ops   C     lass);
         Dy   nUnionOperations     $se    lf =    (Dy  nUnionOperati   ons)          $ so.servant;
  
      try {
            $self.inse      rt_float (   value);
        } fin    ally {
            _servant_postinvoke ($so);
      }
  } /       /  inse       r    t_float 


         /**
                    * Inserts a double value into    the DynAn   y.
        *
        *    @exceptio  n I      nvali d     Val   ue if th   is DynAny has components but h  a    s a current position of -1
        * @exception TypeMism     atch if        called  o  n a Dyn Any whose    current co          mponent itself has comp   onents
        */
  pu         blic vo   id  insert_do      uble (double value) throws org.   omg.D ynamicAny.DynAnyPac          kage.TypeMismatch, org.omg.DynamicAn   y.DynAnyPackage.InvalidValue            
  {
      org.omg.CORB       A.porta   b      le.ServantObject $so = _serv   ant_preinvoke ("insert_double", _o         psCla   ss);
          D  ynUnionOperations  $s   elf = (DynUnionO   perations) $so.servant;
    
      try    {
           $self.insert_double (value);
         }         finally {
            _se    r   vant_postin    voke ($s    o);
                       }
    }  //   insert_double


  /*   *
           * Inserts       a string   value into the DynAny.
                   * Both bounded    and  unbounde   d strings are inserte   d using this method.
                  *
              * @except    ion InvalidValu      e if      this DynA ny has comp onent   s      but has a current     posit  ion  of -1
        *  @excepti on I   nval            idVal       ue if th  e string inse     rted is longer than the bound of      a bounded stri ng
            * @except ion TypeMismat   ch if cal  led on a DynAny     whose current compo nent itself has  com   po   nents
        */
  public void       insert  _stri   ng (Stri         ng value         ) t  hrows org  .        omg.Dynam    icAny.DynAny  Package.TypeMismatch, or g.om   g.DynamicAny.DynAnyPackage  .InvalidValue
  {
      org.omg.CORBA.portable.ServantObje   ct   $so    = _servant_pre   invoke ("insert           _string" ,      _op    sClass);
         DynU nionOpe  rati    ons  $self = (DynUnionOperations) $so.servan  t  ;

             try {
         $self.insert_string (val  ue);
      }    finally {
           _servant_p      osti         nvoke ($so);
       }
  } //   i      nsert_stri     ng


  /**
        * Inserts a reference                   to    a CO  RBA object i  nto the     Dy nAny. 
          *
             *      @exception InvalidV  alue    if   this        DynAny has comp  onents but has a current p      osit  ion      of -1
        * @exceptio      n TypeMismatch if called on a DynAn     y whose curren t co  mponent itsel      f      has components
        */
       publ  ic       void        insert_r     eferenc   e (org.omg.CORBA.Object value) thro   ws org.omg.DynamicAny.DynAnyPa ckage.TypeMismatch, o   rg.omg.D    ynamicAny.D     ynA    n             yPackage.InvalidValue
  {    
      org.       o   mg.   CORBA.portable.Serva    ntObj   ec    t $so = _servant_       prei nvok   e ("inser    t_reference"    , _ opsClass);
            D    ynU        nionOpera       tion     s  $self =   (DynUnionOperations) $so.s  ervant;

            try {
            $self  .insert_reference (value);
          }  finally {
                 _servant_postinvoke (   $so);   
           }
  } // inser      t_ref  erence


      /    **
         * Inserts    a  Typ    eCode object into t  he D  ynAny.
                 *
             *         @exception Inval  idValue   if    this DynAny        has comp           onent  s   but has a current p   osition of          -1
             * @excep      tion Type   Mismatch if c          alled on a DynAny w   hose current component itself      has co mponent    s
                 */
       pub     lic void insert_typecode     (org.o   mg.CORBA.  Type    Code value) throws org.omg.     D ynamicAny.DynAnyP ac  kage .TypeMismatch,   o      rg.om  g.Dynamic  Any.Dyn   AnyPackage.InvalidValue
     {
      org.omg.CORBA.portable.ServantObject $so =                   _servant_p  reinvoke ("  i       ns    ert_typec ode", _opsClass);
        Dy        nUnionO  perations  $s elf =                (Dyn   UnionOperat   ions     ) $so.servant;

              try {
               $self.i     nsert_      typeco  d    e (valu       e);
        }     finally {
          _se       rvant_postinvok  e (       $so);
            }
                       } // insert    _type        code


  /**
               * Insert s a long value i  nto the DynAny. The IDL lon   g    long data type is mapped to the Java long data type.
              *
              * @exception Inval  idValue if this D    ynAny has componen      ts bu  t has a current position o   f -1
        *    @except    ion T  ypeM    is    match if calle       d on a DynA   ny whose    current compon     e   nt itself ha  s com    ponents
            */
  publ     ic voi d insert_long long (long value)   throws org.omg.DynamicAny  .DynAnyPackage .T yp    eM           ismatch, org.omg    .  DynamicA   ny.D  y  n  AnyPackage.InvalidV  alue
  {
         org.omg.      CORBA.por table.Ser  v  antO   bject $so = _    servant_preinvoke ("insert_longlong   ", _opsCl   a      ss);
              DynU            nionO  perations  $self =  (DynU   nionO     perations) $so.serva  nt;

           try {
              $self.i      ns        ert_longlo   ng (value);
      } finally {
          _ser     vant_p    o     stinvoke   (  $so);  
           }
       } /  / ins    e  rt_longlong


   /         **
                     * Inserts a long    value in     to  the Dyn   Any.
                * T  he IDL unsigned       long long      da        ta    type is mapped         to the Java long data type.
         *
                        * @e    x  ception In   va   lidValue    if t  his        DynAny      has components but has a current p         osition of -1
        * @exception  TypeMismatch i   f c    alled on a Dyn     Any whose        curre     n       t c omponent itself has compone  nts
               */
  public void ins        ert_ ulonglong           (l   ong value) thr  o   ws org.omg.DynamicAny.DynAnyPackage.  TypeM   ismatch, org.omg.Dy   namicAny.            DynAn  yPacka                  ge.InvalidValu   e
     {
      or  g   .omg.     CORBA.por   ta b le.   ServantObjec      t $so   =     _servant_preinvoke ("in   sert_       u longlong" , _op      sC  l  ass);
           Dy     nUnionOperat ions  $s   elf = (DynUni    onOperations) $so.servant     ;
     
                  try       {
            $self.inse    rt_ulongl   o  ng (v alue);
           } finally {
          _servant_postinv    oke ($so);
              }
      } // insert_ulonglon g


    /**
        * Inserts a char       value into t      he DynAny. The I       DL wchar     data type is mapped to the Java ch   ar d   ata typ   e.
        *
            *  @exception       I nvalidValue if th     is DynAny h   as compone     nt            s but        has a curr ent    position     of -1
        * @exce         pt     ion TypeMismatch i      f cal   led on a DynA     ny who   se    current component itself      has co mponents
              */
    public void insert_wchar (c   ha   r val ue) throws org.omg.DynamicAny.DynA    nyPackage.TypeMismatch, org.omg.Dynam icAny   .DynAnyPackage .Inva     li      dVal     u  e
  {
                org.omg.COR  BA.portable.Se       rvantObje  ct $so   = _servant_preinvoke ("insert_    wcha     r",        _opsCl     ass);
      DynUni onOpera  tions   $self = (DynUnionO   perations) $so         .servan    t;

      try   {
              $self.insert_wchar    (valu   e     );        
        } fi        na       lly { 
             _servant_postinvoke ($so);
         }
     } // insert_wc       har        

     
            /**
        * Inser ts a      string va    lu  e into the D   ynAny.
                   * Both bounded an    d unboun           ded strin      gs are     inser     ted u       sing this metho     d.
        *
               * @exception InvalidValue if  this          DynAny has compo    nent   s     but has a current position of -1
         * @exception InvalidV  alue   if the string      ins erted is lon ger than the bound    of      a bou nded strin    g
             */
  publi   c  void insert_ws  tring (Str    ing v alue)   throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, or g.omg        .D ynamicAny.Dyn     Any  Package   .InvalidValue
  {
         org.omg.CORB        A.portable.S   erv    ant       Ob     ject $so = _servant_preinvoke ("insert_wstring",    _opsClass);
      DynUn  ionOperations  $self          = (Dy  nUni  onOperations) $so.serva nt;

      try {
                        $self.inser     t_ws   tring (value);
          } final   ly {
          _servant_po stinvoke (   $so);
      }
  } // insert       _ws      tring


  /**
          *  Inserts an             An    y valu   e i           nto the Any     r epresented    b      y   this DynAny.
          *
              * @except   ion       Invalid Valu e if this DynAny    ha     s          components      but has a     current position o      f -1
        * @exceptio   n TypeMi     sma  t      ch if called on       a D  ynAny whos        e current com        pon  ent itself   has c   om   ponents
        */        
    publi  c v oid insert_any    (org.omg.CORBA.Any           value) throws org.omg.Dyn   amicAny.DynAnyPack  age.TypeMismatch, org.omg.D     ynami  cA              ny.Dy  nAnyPackage.In    val    idVa   lue     
  {
              org.omg.        CORB        A  .por  table.ServantOb   jec    t $      so = _   servan  t_pr  ein    voke ("      insert_a  ny", _ opsClass)    ;
      DynUnionOperations    $self =  (D  yn    UnionOperations)    $so      .servant;
  
        try {
           $sel    f.insert_any (va  lu   e);   
      } finally { 
             _serva   nt_postinvoke  ($so);
      }
  } // i    nsert_any


     /**
               * Insert    s the       Any val     ue   contained in the    p     aramet    er     DynAny into the Any represented by    this DynAny.
                     *
               * @exception Invali  dValu                     e if this DynAny has compo    nents but has a  cur           rent position    of -1
         * @exceptio  n TypeMismatch   if c  alled on a D     ynAny whose curre       nt component itself has components
           */
  public void insert_dy   n_any (org.omg.Dy   namicAny.             DynAny va       l ue) throw  s org     .omg.Dynami      cAny.DynAnyPac    kage.Type     Mismatch, o rg. omg.DynamicAny.Dy    nAnyPackage.InvalidV    alue
  {
             org.omg.CORBA.p   ortable.S    ervantObj   ect $so = _ser   vant_preinvoke     ("i   nsert   _dyn_any"   , _opsClass);
                D     ynUnion   Operations  $sel f     = (DynUnionOperations) $so.serva   nt;

          try {
         $self.inser t_dyn_any (va      l       ue);
       } finally   {
              _servant_postinv  oke ($so);
        }
     } // ins     e      rt_dyn_any


  /**
        * Inserts      a r   eference        to      a Serializable obje   ct into this DynAny.
          * The IDL Valu    eBase type is   mapped to     the J    ava Se   rializable type.
                *
                *     @exceptio     n InvalidValue i    f this DynA      ny has   compo   n  ents but has a             current p   osition of -1
              * @exception TypeMismatch if     call  ed    on a D  ynAny   whos  e   current component it   self has compo   nents
        */
  public vo id ins  ert_val (java.io.Se        rializable v    alue) throws org.om    g.Dynam    icAny     .DynAny  Pac      kage.TypeMismatch,    or           g.omg.Dyna           m i  cAny.DynAnyPac       kage  .Invalid   Value
  {
          org.omg.CORBA.por  table.Serv  antObject $so = _servant_preinvoke ("insert_val",   _ops     Class);    
      DynUni onOperations  $s         e  lf = (  DynUnio     nOperations)     $s  o.   servant;
  
          try {
           $self.in       sert_val (valu   e);
        } fina   lly {
             _s  ervant_postinvok       e ($so);
      }
   }    /    / inse    rt_val


  /*        *
                                 * Extr     acts the boolean value from     t   his DynAny.
                *   
             * @exception          T    ypeM   ismatch if the       a   ccess     ed componen      t in the Dyn  Any is    of a t    ype
              * that is not equival    ent         to th        e reque   st    ed        type.
             *    @exception TypeMi    smatch if c   alled on        a DynAny whos  e curr  ent componen   t its    elf   has  compon     ents
        * @exception InvalidValu         e if this DynAny has co     mponen  ts but h   as a cu       r  rent po        s      itio  n of -1
              */
  public boolean g   et_boolean    () throws o     rg.omg.D  yn amicAn   y   .DynAn           yPackage.    Type  Mismatch, org.om  g.Dyna           micAny.DynAn     yPacka    ge. InvalidValu    e
  {
      org .omg.CORBA.port     able.Se   rvan         t  Object $so    = _serv         ant_preinvok   e ("get_boolean"   , _op   sClas s)         ;
         DynUnionOperations  $self = (DynUnionOperations) $    so.  servant; 

       tr y  {
           re    tu   rn $   self.         get_b oolean ();
      } fina           lly {
          _servant_ po  stinvo ke ($so);
      }
  }      // get_b      o    olean


  /**
                                      * Extra     c    ts the   b    yt    e value from this   DynAny. The   IDL octet data type is mapped to the Java b yte data    type.
             *
        * @        exception     TypeMis   match if the   accessed component in   the DynAny is of a type
        * tha       t is n           ot equivalent to the    requested t    ype.
            * @except   ion Ty    p     eMis  match if called on a DynAn   y w                         hos e    curren     t compo     nent itself has components
        * @ex ception InvalidValu  e if   this DynAny ha  s componen  ts but     has    a current position of -1
        */
  pu    b  li c byte   get_oc       tet ()    throws org.omg.DynamicAny.DynA    nyPackag e. TypeMisma    tch,         o r  g.o    mg.     DynamicAny.    Dyn   A      nyPackage.Inval   idValue  
  {
           org.omg.CORBA.por   t   able.Serv   an      tObject $ so   = _servant_preinvoke ("get_   oc tet", _o p         sClass);    
      Dy     nUnionO  perat    ions  $se    lf = (Dy      nUnion    Operations) $   so.servant;

      try {
                     re    turn $sel  f.   g        et_octet ();
      } fi  nally {
               _serva nt_postinvoke ($so       );   
      }
  } /  /   get_octet  


  /*     *
        * Extracts the   char valu  e from this Dyn   Any.
        *
        * @exception TypeMismatch if the accessed componen    t      in the DynAny is          o   f a type
        * that is n    ot equi   valent to the r   eques  ted type.
        * @excepti   on TypeMismatch if     c     alled on a DynAny whose    current          compone       nt    itse  lf h     as components
        *  @exception InvalidValue  if t           his  Dy     nAny ha       s components but h a s     a current po   sition of -1
            */
  public    char   get_char     () throws org.          omg.Dynam icAny.  DynAny  Package.TypeMis match, org.omg.Dyn   amicAny.DynAnyPackage.I   nvalidValue
  {
      org.omg.CORBA.portable.Serva  nt    Object $so = _servant_preinvoke ("    get_char", _opsClass);       
      DynUnionOperations  $self =      (     DynUn ionOper    atio    ns)      $so.servant;

              try {
          r eturn $self.get_char        ();
      }   fin    a   lly {
          _servant_postinvoke (   $so);
      }
  }         // get_char


  /**
             *     Extrac ts the short value from this DynAny.
             *
        *    @ex  cepti   on TypeMismatch if the acces s ed           component   in the  DynAny is of     a type
            *          that is        not equivalent          to the requested          typ             e.
          *   @exception TypeMismatch if called on a DynAny whose       curr  ent component itself h       as c  ompon       ents
            *  @exception In validV       alue if this DynAny has com  pon ents but has      a   current posit  ion of -1
               */
  p  ublic s            hort get_sh ort ()    throw s o   rg.omg.Dynamic  Any.DynAnyPacka     g   e.TypeM     ism  atch, org.omg.Dy namicAn     y.DynAny    Package     .Inv     alidValue
  {   
      org.omg. CORBA.portable.ServantObject     $so = _    servant _pre   invoke ("get_s    hort", _opsClas  s);      
            Dy           nUnionOperations  $s elf = ( Dyn    Unio   nOperations) $so.servant  ;

             try {
         retu r n $sel        f.get_s       hort  ();
           } fina   lly {       
          _se    rvan    t_p  ost    invoke ($          so);
      }
  } // get   _    short


  /**
         * Ext racts     th         e short value f         rom this DynA n   y. The IDL    ushort data type i       s        m     appe   d to the Java short data typ       e.
              *
                       * @except         io   n Typ eM   is   m atch if the accessed com    po   nent i  n the DynA                    n   y     is of a t   ype
           * that is not equivalent t o the reque     st      e                    d     type.
        * @exc   eption TypeMismatch if called  o  n a DynAny wh  ose current component itself has    c omp    onent       s
                 * @exception InvalidValue i    f thi s DynAny        has compone          nts but has a current position  of    -1
                */
       public short get_ushort (  ) t   hrows    org.          om    g     .Dyna micAn     y.DynAnyPackag               e.TypeM       ismatch, org.omg .D ynam        icAny.DynAny   Package.InvalidValue
  {
               org.omg.CORBA.port         a     ble     .ServantObject $so = _servant_p     re     invoke ("get_ushort", _op   sClass);
         DynUni      onOpe ratio      ns  $self = (DynUnionOperations) $s        o.servant;

      try {
            return $self.ge      t_ushort ();
           } finally {
              _servan t_postinvok       e              ($      so);
      }
           } / / get_u   short


   /**
        * Extr             acts th      e inte  ger value from this DynAny.   The IDL long data    type is m       apped to th e    Ja va in     t  d  a    t   a type.
         *
             * @exception        Typ eMismatch if the       a   ccess      ed component in t he DynAn     y is of   a       typ                  e
        * that is n   ot equivalent to the        re      que sted type.
         * @except        ion          TypeMism      atch if called on    a DynAny whose curr   ent co  mpon   e   nt itself           has components
                     *       @e      xception InvalidValue if this Dy    nAny has co     mponents but has a     curre    nt pos i   tion of     -1
        */
  p    ublic int get_long  () throws org.omg        .  Dyn      amicAny.   DynAnyPac   k   a  ge.TypeMismatch, org   .omg.Dynam   icAny.DynAnyPackage.InvalidValue
  {
      org.o  mg.CO     RBA .portable.S   ervantOb          j       ect       $so = _servant_preinvoke     ("get_    long",       _opsClass);
           DynUnionOpera tio  ns   $self    = (Dy nU   nionOperations)           $so.servant ;

       try {
           return $self.get_long     ();
      } final  ly {
            _s     ervant_po        stinvoke ($so);  
                              }
  } // get_ l             on    g


  /**
             * Extra    cts th e intege     r valu              e from    th     is D ynAny. The IDL ul ong da    ta typ       e is mapped to    the        Java int data type.            
                   *
                       * @e xcepti       on     TypeMism    a    tc  h        if   the    a cces       sed component in the DynAny is      of a typ     e 
               * that is   not equivalen          t  to the reque  sted type.
        * @excep tion TypeMismatch    if called on      a Dyn   An     y whose current c       om ponent  itself ha  s   componen    t   s
          * @exceptio    n In v         a     lidV alue if this D    ynAn y h   as co   mponents b ut has a   curr      ent  posi    t  ion of     -1
          */
  public int get_ulong  () th     r    o   ws org.om   g        .DynamicAny        .Dyn      AnyPackage.TypeMi   sm       atch, org.omg        .Dyna  mi   cAny.DynAnyPackage.Invali         dValue
          {
      org.omg.CORBA.portable.ServantObject $so          = _serv   ant    _preinvok    e (         "get_ul   ong", _opsClass);
      DynUnionO       perations  $self = (D  ynUnio      nOper  ations) $so.servant;

             try {
         retur     n $self  .get _ulo             ng ();
      } final      ly {
            _ser    vant_postin     voke (     $so   )  ;
        }
  } // get_ulong


     /           **
          * Extra  cts the float value fr    o  m this DynA        ny.
        *
            * @exception TypeMismatc h if t     he ac        cess      ed component in the     DynAny     is          of a t     ype
          *      that is n     ot   equivalent to   t      he requested typ    e.
          * @           exc  eption     TypeMismat  ch if called on a      Dyn  Any whos     e current     com   ponen   t itself has c      om  pon   ents
        * @excepti   on In val  idValue if this DynAny has   componen      ts but has a current po    sitio   n of -1
                   */   
  p        ublic    fl oat get_fl      oat (   ) throws or   g.  o     mg.Dyna micAny.Dyn    AnyPackage.Ty             peMismatch  , org      .omg.Dyna  micAny.DynAnyPa         ckage.  Invali     dValue
  {
      org.omg.CORBA.port    able.S    erv antObject $so = _servant_prei     nvoke ("   get_float", _opsC    lass    );
      DynUnionOperations      $self = ( DynU nionOperations) $        s   o  .servant;

      try {
           ret        urn $  sel  f.g e       t_flo at ();
         } finally {
                     _serva  n    t_postinvoke ($so );
           }
     } // get_  float

    
  /**
           * E    xtracts th    e do uble value from this DynAny.
        *
        *       @except    ion     TypeMismatch if the access  ed c  omponent in t  he DynAny i  s      of a type
              *   that is not equivalent to the r    eque          sted  ty   pe.
          * @exception T  ypeMismatch if call   ed on a  Dy  nAny whose cu  r      r       ent co   mponent    itse   lf has compo       nents
              * @excep   tion    InvalidValue i       f this Dy  nAny h as components but has a curre   nt posit  ion of -1
            */
  public       double get  _double () throws org.omg.Dyn  amicAny.DynAnyPack    age.TypeMismatch,          or   g.omg.Dynam    icAny.DynAnyPackage    .I    nvalidValue
  {
        org.omg.CO    RBA.po     rta     ble.ServantObject $ so    = _s  ervant_preinvoke    ("ge t_doub le", _opsCl  as       s);
                 DynUn    i    onOperations  $s     elf = (DynUni     o  nOp  erati  ons) $so.s  ervan  t;

          try {
         re  tur  n $self.get_d    ou  ble (  );
        } fi       na         lly {
          _servant_po   stinvoke   ($so);
      }
     }            // get_double


       /**
                 * Extracts the  strin     g va  lue fr   om this DynAny.   
            * Both          bounded and unbou   nded  strings    are        extr   acted u   sin           g         this method. 
                  *
           * @e     xc eption TypeMi s        m   atch if the a  ccessed  com        ponent in the DynAny is                of a type
        * tha    t is not    equivalent to th  e request      ed type.
        * @ exception      T     ypeMisma           t   c  h if called on      a DynAny   w  hose current compo nent  i   tself has components
        * @ex  ception InvalidValue if this DynA            ny has component    s but has a current positio   n of -1
            */
  public String ge     t_st   ring        ()   t   hr ows    org.omg  .Dy nami    cAny    .         DynAnyPac         kage.T  ypeMismatch, org.omg.DynamicAny.DynAny     Pack age.In  validValue
  {
            org.omg    .CO  RBA    .portabl   e.ServantObject    $so = _servant_preinvok e ("get_string", _opsClass);
      D     ynUnionOpera    tion    s       $self =  (D      ynU       nionOper ation s) $so     .ser      v      ant;

        try {
              return $self.get_str    ing ();
      } final     ly {
                         _s             ervant_po     s   t      invo   ke ($   so);
           } 
  }     // get_string


  /**
             * Extracts the r       eference to a CORBA    Object from this   DynAny  .
        *
          * @exc    eption TypeM   ismatch if th  e      accessed com   ponent in the DynAn      y is of      a  type
                * th     at is not equi  valent to the requested type.
           * @exce  p      tion TypeMismatch if ca    lle  d o     n a DynAny whose current component itself   has comp      onents
        * @exception Inval idValue if    this Dy  nAny has          comp     onents         but has a    current positi     on of -1
         */
  p ublic or  g.omg.CORBA.Ob     ject get_referenc     e () t        hrows org.omg       .Dyna  m       icAny.DynAnyPackage       .TypeMismat  ch,    org  .om     g.Dynami  cAny.DynA      nyPackag      e.InvalidValu      e
  {       
      org.omg.CORBA.po   rta    ble.ServantObject $so = _servant_preinvoke ("get_      referen           ce", _opsClass);
             Dyn      Unio       nOp          e   r  ations  $self = (DynUn  ionOperatio       ns) $so     .s  erv   ant;

      tr  y {
              ret  urn $self.      get_reference        ();
      } finally {
          _ser vant_postinvoke   ($so);
         }
       } //          get   _ref    erence


  /**
        * Extracts the TypeCode      obje  ct from this DynAny.
        *   
         *    @exception Type   Mismatch if the a   ccessed compon  en  t in the DynAny is o   f  a ty     pe
        * that is not equivale  nt to the re     quested type.
        * @exception T   ypeMismatch if called on  a           DynAny who  se cu    r     rent compon    ent itself      has comp       onents
        * @exception InvalidV    alu  e if thi     s     DynAny has com  pone       nts but has a current posit  ion of -1
        */
  public o    rg.omg.CORBA.TypeCode get_typecode () throw  s org.     omg.D      ynamicA   ny.DynAn    yPackage.Type    Mi   sm  atch,  org.omg.DynamicAny.DynAn    yP     ackage.Invali  dVa     lue
  {
      org.omg.CORBA  .portable    .Se     rvantObject $so =      _s    ervant_preinvoke ("get_typecode",   _opsClass);
      DynUnionOperations  $s      e lf = (DynUnio           nOperation    s) $so .s   ervant  ;

                 try   {
              r     eturn   $sel     f.  get_typecode ();
      } fin    ally     {
                  _se    rvant_po  stinvoke ($so);
       }
  } / / get_ty       pecode


  /    **
          * Ex     tracts the long   valu        e from    this Dyn    Any. The IDL         long long data type      is mapped to the Java long     data type.
              *
         * @exception TypeMis       m      atch if the accessed comp onent in the       DynAny is           of a     t  ype
           * that  is not equivalent to the requested type.
           * @except   ion TypeMismatch if      cal   led on   a DynAny whose cu  rrent component itself has component   s
        * @exception   InvalidValue if      th  is DynAny h  as compone  nt s but has a      current positio n of -1
        */
  pub       l ic   lo     ng get_lo       nglong () thro   ws org.omg.Dy  namicAny  .   DynAn   yPackage.TypeMismatch, org.omg.DynamicAn    y.D     ynAn  yPackag   e .InvalidVa   lu   e
  {
      org.omg.CO         RBA.portable.Serva    ntObject $so = _servant_prein           vo   ke ("get_              longlong", _o  psCl    ass);           
      DynUnionOper ations  $  se    lf = (DynUnionOperations)      $so.serva  nt;

         try {
         re   turn $self.         get_lon   glong ();
      } fi      nally {
                        _servant_p      ostinvoke    ($so);
      }
   } //        get_longlong


  /**
                 * Extracts the long val  ue from     t     his  DynAny.
        * T he IDL unsigned lon              g long da     ta type is mapped to th   e Java   l  o   ng data type.
        *
        * @exception  TypeMismatch if the accessed co    mponent     in    t         h       e DynA        ny is of a type
        * that is not equivale        nt to the r    equested t   ype.
             * @exce      ption TypeMismatch      if called o  n a DynAny      whose current compone nt itsel   f    has components
        * @exception InvalidValue if th   is    DynAny  has components but        has a current position of -    1
               */
  public long get_  ulonglong () throws org.omg.DynamicAny.DynAnyPackage.Ty        pe Mismatch, org.omg.DynamicA ny.DynAnyPackage.InvalidVa     lue
  {
          org.omg.CORBA.portable.Servant     Object $so = _serv   ant_prein voke ("get_ulonglong",   _     o       psClas    s);
      DynUni  onOperations  $self = (DynU   nionOpe     ra  tion    s) $so.servant;

           try {
         retu    rn $self     .get_ulo    nglong ( );
        } final ly {
             _servant_    po   s         tinvoke ($so);
        }
  } // ge    t_ul onglong


  /   **
        * Extracts the long v a  l  u    e from this D  ynAny. The IDL wchar dat          a type is mapped to           the Java char data type         .
        *
          * @exc   eption Ty  peMismat   ch i     f the ac   cess    ed comp        onent i     n the    DynAny is of a type
        * that is not e  quivalent to the requested type.   
        * @exception TypeMis match if cal    led on a DynAny wh  ose current component i  tself has components
          * @exception InvalidValu          e if   this DynAny has components but h as a curre    nt    position of -1
         */
  pu blic char get_   wcha      r        () throws  org.omg.D   y     namicA    n    y.DynAnyPackage.TypeMismatch,                 org.omg.DynamicAny.              DynAnyPa  ck      age.Inval   idValue
  {
       org.omg.CORBA.portable.Servant   Object $so = _servant_preinvoke ("get_wchar", _o   psClas  s);
      Dyn     UnionOp eratio  ns  $se     lf = (DynUnionOperations) $so.servant;

      tr     y {
          return $self.get_wc ha r ();
          }        finally {
          _servant_postinvoke ($so);
       }
    } // get_wcha      r


  /**
        * Extra   c    ts the strin    g      va   lue from thi   s DynAn    y.
                              * Both     bounded and unbounded strings are extract        ed using th   is method.
          *
        * @exception TypeMismatch if the accessed component in the D      ynAny is of  a typ     e
          * that is no t equiv  alent t  o the requested  type.
        * @ except  ion Type Mismatch if cal   led  on  a DynAny whose curr  ent component itsel   f has components
        */
  public String get_wstring    () th    rows org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org   .omg.DynamicAny.DynAny   Package.   InvalidValue
       {
      org.omg.CORBA.portabl           e.       ServantObject $so = _servant_prei  nvoke ("get_wstring", _opsClass);
      D    ynUnionOperatio  ns  $self = (D  ynUnionOpe     rations) $so  .servant;

      try {
         re        turn $self.ge t_wstring ();
           }  finally {
          _servant_postinvoke (   $so);
         }
  } // get_wstring


  /**
        * Extr     acts an Any value contained in the     A     ny  represented by th  is DynAny.
        *
        * @exc   eption TypeMis     match     if the accessed component in the DynAny i s of a t ype
        * that is not equivalent to the requested type.
               * @excepti    on TypeMismatch if called on a DynAny      who    se current c        omponent itself has components
        * @ex ception InvalidValue if this DynAny has compo     nents but has a cu      rrent position of -1
          */
  p ublic org.omg.CO  RBA.Any get_          any () thro ws org.om    g.DynamicAny.DynAnyPackage.T    ypeM    i   smatch, org.omg.DynamicAny.Dy  nAnyPackage     .Inval  idValue
  {
      o rg.omg.CORBA.portable.Servan tObject $so = _servant_p reinvoke ("get_any", _opsClass);
         DynUnionOpe  rations      $self = (DynUnionOp    erations) $so.servant;

       try     {
         return $self.ge     t_   any ();
      }      finall      y {      
          _servant_postinvoke ($so);
      }
  } //         get_any


  /**
        * Extra   cts the Any     value contained in the Any      represented by this DynAny and r   eturns it wrapped
          * into a new DynAny.  
        *
          * @exception TypeMismatch if the accessed component in the DynAny is of a type
        * that is not equi     val    ent  to the requested type.
           * @exception TypeM  ismatch if called on a DynAny whos   e current compone       nt itse   lf has c  omponents
        * @exception InvalidValue if t     his   DynAny has components but has a current positi  on of -1
        */
  public org.omg.Dyna   micAny.DynAny get_dyn   _an y () throws org.omg.Dy namicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.Dyn      AnyPackage.InvalidValue
  {
      org.  omg.CORBA.portable.    ServantObject $so = _servant_preinvoke ("get_dyn_any", _opsClass);
      DynU      nio nO   perations  $self =    (DynUnionOperations) $so.servant;

      try {
          return $     self.get_dyn_any ();
        } finally {
                 _servant_postinvoke ($so);
                   }
  } // get_d   yn_any


    /**
        * Extracts      a Serializable object from   this DynAny.
         * The I      DL ValueBase type  is mapp   ed to the Java  Serializable type.
        *
        * @exception Type      Mismatch if the      accessed component in the DynAny i   s of a type
        * that  is not equivale  nt to the requested type.
        * @exception TypeMismatc   h if called on a DynAny whos    e    curre      n    t comp      onen      t itself has components
           * @excepti    on InvalidValue if this DynAny has compo nents but   has a current position of -1
            */
       public jav         a.io.Ser    ializab le get_val () throws org.om   g.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue
  {
        org.omg.CORBA.portabl  e.Se  rvantObject $so = _servant_preinvoke ("get_val", _opsClass);
              DynUnionOp   erations  $self = (DynUnionOperatio    ns) $so.servant;

          try {
               return $self.get_val ();
      } finally {
          _servant_postin      vo       ke ($so);
      }
  } // get_val


  /**
        * Sets t     he current position to index. The current pos   ition is indexed 0 to n-1, tha   t is,
        * index zero corresponds to the first component. The operation returns true if the resulting
          * current position indicates a component of the DynAny and false i   f in    dex indicates
        * a position that does not correspon  d to a component.
        * Calling seek with a negative inde   x is legal. It sets the cu rrent position to -1 to indicate
           * no  c     omponent and returns f      alse. Passing a non-negative index    value for a DynAny that does not
        * have a component at the corresponding position sets   the current position to -1 and returns false.
           *  /
  public boolean     seek (int in dex)
     {
         org.omg.CORBA.    portable.ServantObject $so = _servant_preinvoke ("seek", _opsClass);
      DynUnionOperations  $self = (DynU     nion  Opera   tions) $so.servant;

      try {
         return $se   lf.seek (index);
      } finally {
          _servant_po  stinv      o   ke ($so);
      }
  } // seek

   
  /**
        * Is equivalent to    seek(0).
        */
  public void rewind ()
  {
      org.omg.CORBA.portable.ServantObj  ect $so = _servant_prei        nvoke ("rewind",          _opsClass);
           DynUnionOperations  $self = (DynUnionOp    erations) $s  o.servant;

      try {
              $se   lf.rewind ();
      } fin   ally {
          _servant_postinvoke ($so);
      }
  } //  rewind


  /**
        * Advances the current position to the next component.
        * The operation returns true while    the resulting curren   t position indicates a component, false otherwise.
        * A false return value leaves the     cu  rrent position at  -1.
        * Invoking next on a DynAny without components leaves the current posi    tion at -1    and ret  urns false.
           */
  public boolean ne  xt ()
  {
      org.omg.CORBA.portable.ServantObject $so = _servant_preinvoke ("next", _opsClass);
      DynUnionOperations  $ self = (D    ynUnionOperations) $so.servant;

      try {
         retu    rn $self.next ();
      } finally {
          _servant  _postinvoke ($so);
      }
  } // next


  /**
        * Returns the number of components of a DynAny.
        * For a DynAn    y without     components, it returns ze   ro.
        * The operation   only counts the compone   nts     at the top level.
        * For example, if component_count is invoked on a DynStruct with a  single member ,
        * the return value i  s 1, irrespective of  the ty pe of the member.
        * <UL>
        * <LI>For sequences, the operation returns the current number of elements.
                    * <LI>For structures, exceptions, and value t     ypes, the operation returns the number of members.
        * <LI>For arra  ys, t   he operat        ion returns the number of elements.
        * <LI>For unions, the op   eration returns 2 i  f t       he discriminator indicates that a named member is active,
        * otherwise, it returns 1.
        * <LI>For DynFixed and DynEnum, t he operation returns z   ero.
        * </UL>
        */
  public int component_count ()
  {
      org.omg.CORBA.portable.ServantObject $   so = _servant_preinvoke ("component_count", _opsClass);
      DynUnionOperations  $self = (DynUnionOperations) $so.ser    van t;

      try {
         return $self.component_count ();
      } finally {
          _servant_postinvoke ($so);
      }
  } // component_count


  /**
        * Retur   ns the DynAny for the compone    nt at the curren      t position.
        * It does not advance the current position, so repeated calls to current_compone nt
        * without an intervening call to rewind, next, or seek return the same component.
        * The returned DynAny object reference can be used to get/set the value of the   current component.
        * If the curre  nt component repr  esents a complex type, the returned reference can be narrowed
        *  based on the TypeCode to get the interface corresponding to the to the complex type.
        * Calling current_component on a Dyn   Any that cannot have components,
        * such as a DynE   num or an  empty exceptio   n, raises TypeMismatch.
        * Calling curren   t_component on a DynAny whose current position is -1 returns a nil reference.
             * The iteration operations, together with current_component, can be used
        * to dynamically compose a n any value. After creating a dynamic any, such as a     DynStruct,
        * current_component and next can be used      to initialize all the components of the value.
        * Once the dynamic value is completely initialized, to_any     creates the corresponding any value.
        *
        * @exception Ty   peMismatch If called on a DynAny that cann    ot have components,
        * such as a DynEnum or an empty exception
        */
  public org.omg.DynamicAny.DynAny current_component () throws org.omg.DynamicAny.DynAnyPackage.TypeMi  smatch
  {
      org.omg.CORBA.portable.ServantObject $so = _servant_preinvoke ("current_component", _opsClass);
      DynUnionOperations  $self = (DynUn    ionOperations) $so.servant;

      try {
         return $self.current_component ();
      } finally {
          _servant_postinvoke ($so);
      }
  } // current_component

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:omg.org/DynamicAny/DynUnion:1.0", 
    "IDL:omg.org/DynamicAny/DynAny:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws       java.io.IOException
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
} // class _DynUnionStub
