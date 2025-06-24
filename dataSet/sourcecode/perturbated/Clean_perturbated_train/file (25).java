package      org.omg.CosNaming;


/**
* org/omg/CosNaming/_NamingContextStub.java .
* Gene     rated by the IDL-to-Java compiler (portable), versi    on "3.2"
* from c:/re/workspace/8-2-build-windows-amd64-cygwin/jdk8u131/8869/corba/src/share/classes/org/omg/CosNaming/nameservice.idl
* Wednes     day,  Ma  r   ch 15, 2017 1:25:04 AM PDT
*/


/*   * 
 *     A naming contex    t is an object  that c     o      ntains    a  set of name bi      n   dings in 
 * which each name i  s unique. Different na   mes can be       bound to    an   object  
 * in the same o r diff    eren   t contexts at the  sam   e time. <p>
 * 
 * Se       e <a href="http://www.omg.org/technology/documents/  formal/naming_service.htm">
 * CORBA COS 
    * N   aming Specificatio    n.</a>
 */ 
pub    lic class _NamingContextStub ext      ends org.omg.CORB A.portable.Ob  jectImpl i  m     plements org.omg.CosNaming.Nami  ngContext
{


  /**
   * Cr  eates a bindi  ng of a name   and  an    ob    ject in      the nami   ng   context.
 *   Naming contexts tha      t a   re boun    d using bind do not participate     in name
 * resol    uti       on when compound n  ames are pass     e d to b  e reso    lved. 
 * 
 * @para     m n Name of the obj    ect   <p>
 * 
 * @param obj The Object to bind with the given name<p>
  * 
 * @exception org.omg.CosNaming.NamingConte   xtPackage.NotFound Indicates
 *    the name do  es not identify a bindi  ng.<p>
 * 
 * @exceptio        n or    g.omg.CosNaming.NamingContextPackag    e.CannotPro   ceed 
 * Indic       ates tha  t the impleme n    tation has    given   u  p for some     reason.   
 * The cli    ent, ho      wever, may be able to continue  the operati       on
 * at the returned naming context.<p>
 *   
 * @exception org.omg. CosNaming.Nami    ngContextPackage.InvalidName 
   * Indicates that the name is invalid. <p>
 *
 * @exc   ep   tion org.omg.CosN  aming.NamingContextPackage.AlreadyBound 
 * Indicates an obj    ect is already bo    und to the specified name      .<p> 
 */
  public void bind (org.omg.CosNaming.NameComponen   t[] n, org.omg.C   ORBA  .Object obj) t         hrows org.omg.CosNaming.Namin     gContext  Package.NotFou       nd, or        g.omg.CosNaming.NamingCon     textPa ckage.Can          no         tProceed, org.omg. CosNaming.NamingContextPackage      .InvalidName,        o       rg.omg  .Co                sNaming.NamingContextPackage   .Alread    yBound  
      {
              org.omg.  CORBA .     po        r  t          able  .InputS  trea m $in = null;
                             try {
                                    or    g.om           g.C    ORBA   .port             able.Out  putS                   tream $  o   ut = _request   (   "    bind", true);
                      org.omg.Cos  Naming.NameHelper  .write       ($out, n);
                             or g.    omg.CORB A.ObjectHelper.wr      ite   ($  out, o bj      );
                                     $in =    _invoke    (       $out);
                                ret      u     rn;
                    } catch (org.omg.COR BA.p        ortable.Appli        cationE  xception  $ex) {
                  $in = $ex.   getInputSt  ream   ();
                       String _id = $ex      .getId (     );
                   if     (_    id.e quals ("I  DL:omg.org/CosNaming/N      amingCon  text/NotFoun      d:1.0 ") )
                            th     ro w or   g.omg  .Co     sNaming.Nam      ingContextPackage.      NotF    oundHelper.read ($in);
                           e   lse if   (_i  d.equals ("IDL:om       g.org/C    osNamin         g/Namin gC o   ntext/CannotProc eed:1.0"))
                           throw org.omg.   Cos     Naming   .Namin  gContextPackag    e.CannotProceedHelper.r    ead ($   in);
                        else         if (     _id.equals     ("IDL:omg.or   g/CosNa  ming/Nami  ngCon text/In    validName         : 1.0"))
                             throw org.omg.Co    sNaming  .Nam    ingCon tex     tPa          ck   ag   e.I         nvalidNameHelper. read ($in);   
                          else i  f  (_id.equa           ls ("IDL:omg.org/C      osNaming/NamingContext/  AlreadyBound :1.0"))
                       t      hrow or  g.omg      .C         osNaming.NamingCo   ntextPack    age            .  Alr   ea       d   yBou   ndHelper.read ($in);
                     else
                            th row new      org.omg.CORBA.MA      RSHAL (_id);
               } ca      t   c      h   (org      .omg.CO    RBA.port  able.Rema   r    s      halException $rm) {
                  bind (n    , ob j         );
                 } finally {
                  _releaseReply ($in);
            }
    } /    / bind
 

  /**
 *    Names an    object that is a n aming context. Naming co     ntexts that
 * are bound using bind_context() participate in name resolution 
 * wh   en com    poun   d   n         am es are passed to be resolved.
 * 
     * @param n Name of the object <p>
 * 
 * @param nc Nam         ingCon    t  ec t object  to bind with the given name <p>
     * 
 *    @excep  tion  org.omg.  Cos  Naming.NamingC   ontextPackage.No t     Found I   ndi    cates the name does no     t identify a  bind     ing  .<p>
 * 
 *      @exception o  rg.omg.CosNamin    g .Naming      ContextPa   ckag e.Cann  otProceed Indicates that th    e implementation has  
 * given up for some reason    .  The cli    ent, h owever, may be abl      e to 
 * continue the operation at the ret  ur       ned naming context.<p>
     *    
 * @ex    c  epti on     org.omg.CosNa         ming.N    aming   ContextPackage.Inva       lidName I ndicates     that    the name is invalid. <p>
 *
 * @exception org.o    mg.Co  sNami        n           g.NamingConte   xtPackage.AlreadyBou   nd Indicates an object is already 
 * bou      nd  to the specified name.< p>
 */
  public v    oi d    bind_context (org.omg.CosNaming.NameC  om    po   nent[] n, org.omg.CosNaming.Nami     n  gCont         ex        t    nc) t  h rows   o  rg   . omg             .C   osNam       ing.NamingContex    tPackage.N  otF    oun d           , org .omg.CosNaming .NamingCo ntextPackage       .Cann   o      t      Proceed, o    rg.omg.      CosNaming.NamingC   on    te xtPacka ge.Inv    alidName, org.omg.CosN    aming.Na  mi  ngContextPac   kage.AlreadyBound
         {
                       org.       omg.CORBA   .p          ortable.Inpu    tStream $in = null  ;
               try {
                        org.omg.    CORBA.portable.Outp    utStream   $out   = _requ   est ("bi     nd_context", true)   ;
                          or    g.omg.C  os   Naming.NameHelper .wr   ite ($o  ut, n);
                                 org.om  g  .CosNaming.NamingContex   tHelper.write     ($  out, nc)  ;  
                  $in = _invoke    ($o ut);
                re tu     rn;
            } catch (org.omg.CORBA.portable.A pplicat   ionException $ex) {   
                $in = $ex.getInputStre        a   m ();
                        String _id  = $ex.getI  d ();
                   if  (_id.equals (          "IDL   :omg.     or   g/Cos   Naming/NamingConte     xt/NotFound:1         .0"  )  )
                                    thro w org.om      g   .C   osNaming   .Nam  ingContextPa   ckage.NotF      oundHelper.r ead ($ in) ;
                else if (_id.e  qual     s ("IDL:om   g.org/CosNaming   /NamingC    ontext/CannotProceed:1.0"))
                                                      throw org.omg.CosNaming.N       a mingContextPack       age   .         CannotProce e dHelper.read ($in  );
                               e          lse i    f (_id.equal s (   "IDL:o    mg.org/CosNaming/Nami      ngConte   xt/InvalidNa         me:1.0"))      
                          thr         ow   org.omg.CosNa ming.N            amin        gContextPackage.Inva  lidNameHelper.read ($in)  ;
                else if     (_id  .       equals       ("IDL:omg.  o rg/C        os N  aming/NamingConte    xt/  A  lready    Bound:1.0"))
                              thro  w   org.omg.CosNam  ing.Na   m  ingContextPackage.Al     readyBou     n    dHel   per.re      ad ($in);
                 else   
                              throw new org.omg.COR     BA   .MARS        H    AL (_id);
                             }           cat    ch (org.omg.  CORBA.portable.Rem     arshalEx    ception $rm) {
                             bin  d           _con t  ext (n,     nc         );
            } fi        nally {
                      _releaseReply      ($in     );
            }
  }  // bind_conte   xt


  /**
 * Cre     ates a binding       of a name and a                            n object in the naming context
 *    ev    en i f the      name    is alread   y boun d in the context  . Naming conte      xts 
 * that   are bound u     sin    g rebind do not participa      te in name r    eso    l ution       
 * when compound names are passed to   b  e resolved.
     * 
 * @pa     ram  n Nam     e                of the object             <    p>
 *     
 * @pa  r a  m obj          The Obj    e         ct to rebind wit h     the g  i        ven name <p>
 * 
 * @exc  eption org   .om  g.C       os          Naming.Nam         in   gContextPackag e.NotF  ound Indicates th  e    name does not ide   nt ify a bi    nding.<p>
   * 
  * @exc      eption    org.omg.CosNaming   .NamingContextPackage.CannotProceed Ind     ica         tes     that t    he implem     entatio   n          has
 * given u  p for so     me reason. The client, h  owe          ver, may         be abl    e   t   o   
 * continue the      operation at t          he r eturned         naming     c         ontext.<p>
 * 
 * @exce     ption or    g.omg   .CosNaming.Na     mingCo  nte               x       tPackage.InvalidName Indicates that the  name is invalid. <p>
 *  /
  pub     lic void rebind (or  g.om  g.CosNaming.Nam       eCo   mpo ne nt[] n, org.o mg.C  ORBA.Objec  t obj) thro                 ws org            .omg.CosNaming.NamingContextPackag e.NotFou  nd, org .omg.CosNaming.NamingCon textP    acka   ge.CannotProceed, org.omg.CosNaming.NamingCo   ntext  Pac  kage.Invali       dNa          me
  {
               org.omg.COR BA  .por  table.    InputStream    $in = null     ;
             try {
                         or  g.omg.CORB   A.po    rta    ble.OutputStream $out = _req       u e      st ("rebind",    true);
                            org.omg.Cos  Nam  ing.NameHelper.write   ($out, n);
                        org      .omg.CORBA       .Object   Helper.wri te ($out,     obj);
                      $in =     _     invoke  ($ou     t);
                       return;
              }     catch   (org.omg.C  OR      BA.po     rtable.    Application Except             ion $ex) {
                                     $in      =  $ex. ge   tInputStrea  m (   );
                         Stri  ng _id = $ex.getId    ();
                      if (_id.     equals ("IDL:  omg.org/CosNami ng/NamingC   ontext/NotFound:1.0"))
                        throw org.omg.CosNam      ing.NamingContextPackage.NotFou     n    dH        el           per.read ($in);
                    els e        if (_   id.equ    als ("IDL   :  omg.o      r  g/Cos   Namin       g/  NamingContext/       CannotProceed:   1.0"))
                    thro    w org      .o    m      g.Co sNamin            g.NamingContex  tPackage .       CannotProceedHelper    .rea      d ($in);
                                    else i      f (_id.equ   als      ("ID  L:omg.org/CosN        aming/NamingContext/InvalidN ame   :1.0"))
                             th   row    org.omg.CosNaming.Namin   gContextP   ackage.In vali   dNam   e Helper.read ($in);
                        else
                         throw new        org.omg.CORBA.MARSHAL (_i    d   ) ;
            } catch (o      rg.omg.C ORBA.portable  .Rem        arshalException $rm) {
                   r       ebind (n, obj        );
                    } finally { 
                            _rele aseReply   ($ in); 
               }
  } // rebind


  /*   * 
                    *            Create        s a b        inding of a name and a       naming       context in the naming
 * context even if the    name is   already b   ound           in the c   ontext. Namin    g 
 * contexts that are    bound using re bind_context()     participate            i  n name 
    * resolutio      n     when compound           nam   es are passed to be re solved.    
 * 
 * @param   n  Name of the object <  p>
 * 
 *     @param nc     Namin      gCon     tect   object to rebind   with the giv     en na                       me <p>
 * 
    * @except  ion org.omg.CosNa ming.   NamingContextPackage.NotFound Indicates the          name does not ide    nti fy    a bin ding.<p>
    * 
 * @exceptio    n org.omg.CosNamin   g.Nami      ngContextP  ackag    e.CannotProceed Indica      tes that  the implementation has
 *  given up for some reason   . The c l ien  t, howev  er, may be  able     to 
     *      c   ontinue     t he operation at the ret      urn  ed    nam   ing cont    ex    t.<p>
     * 
 * @ excepti    on org.om  g.CosN    aming.Nam   ingContextPackage.Inva            lidName Indicate    s that the       name  is   in  valid  . <p> 
         */
       public void rebind    _context (o       rg.  om       g.Co       sNaming.NameComponent[] n, org.omg.CosNam    ing.Naming    Context nc)    th  rows org.omg.Cos Nam     in    g.     NamingContextPa   ckage.NotFound, org.omg.CosNaming.N   aming  Co nte  xtPacka      ge.CannotP   roceed ,      org.omg.    CosNaming.Nami              ngContex tPackage.InvalidNam  e
       {
                         org.o              mg.CORBA.portabl  e.Input    Stream $in = nu   ll;
                try {
                   org.omg.CORB      A.portable.OutputStream $ou  t = _reques    t ("  rebind_context", t            rue);
                or      g     .om    g.CosNam             ing.N     ameHelper.write ($out, n);
                        o rg.omg.CosNaming.Nami     ngContextHe lper.write ($out,     nc)   ;
                          $      in = _in     voke ($out     ) ;
                     retu  r  n;
               } catch (org.omg.CORBA.portable.ApplicationException $  ex) {
                       $in = $ex.g     etInputStream ();
                               String _id      = $e  x.  getId ();
                if (_     i    d. equals ("IDL:omg.org/CosNaming/NamingContext/No   tFound:1.0"))   
                                 thro w org .    o    mg.CosNaming.Nam  ingContex    t          Package.N      otFoundHelper       .read ($in);
                else i     f (_id.equals ("I    DL:omg.org/    CosNaming/NamingCo nte    xt/Cann      otProceed:1.0"))
                            throw org.om      g.CosNaming.Nam ingCon             textPackage.CannotP    roceedHelper.read ($     in);
                            e      lse if (_i    d.equals    ("IDL:omg.org/CosNaming/Namin gC     ont    ext/InvalidName: 1.0"))
                                 throw org.omg.CosNaming.NamingContextPackage.InvalidNam    eHe          lper.read ($in);
                     else
                        throw new org.omg.COR      BA.MARSHAL (_         id);
                  } ca tch (org.omg.CORBA.porta     ble.RemarshalE   xception $rm) { 
                                          r  e     bin     d_context (n, nc        );   
                          } finally {
                  _rele     aseRepl    y    ($in);
                        } 
  } // rebind_context


  /  **         
 * The resolve o     perati   on is the process o    f    r     et      riev   ing an object
    * bound     t    o a  name in a given c       ontext. The give      n name must           exactly 
 * match t he    bound nam e. The naming s      ervice does n   ot        return   the ty pe 
 * of t     he obj  ect. Clients    a   r       e responsible for "na  rrowing"      the object 
 * to the approp      riate           type. That       is, clie     n     ts typica    lly cast the     returned 
 * object from Objec       t to    a more       specialized interfa           ce.
 *          
 * @param n Nam   e of     th            e     object <p  > 
 * 
 * @exc eption     org.omg.CosNaming.Nam          i    ngContextPackage  .  N     otFou nd    Indicate     s th    e name              doe     s not identify a binding.<p>
 *           
 * @e  xc e   pti      on org.omg.CosN          amin  g.Namin  gCo      nt   ex  tPackage.     CannotProceed Indicates tha   t the implementatio      n h     as
 *      given up for some   reason. The client, however,      may     be able to 
 * continue the ope   rat  io    n at the returned naming c     ontext.<p>
  * 
 * @e     xception o   rg.omg.   CosNa    m  ing.Naming   Conte  xtPa   cka g         e.InvalidN     ame I     ndicates th  at t    h e name    is    invali   d. <p>
 */
  public or     g.omg.CORBA.Object resolve   (org.omg.CosNaming.   N ameCo                             mponent    [] n) throws org.omg   .C  o    sNaming  .Namin  gC ontextPackage.NotFo und, org.omg     .CosNam   ing.NamingContextPackage.Canno tProceed,           org.o      mg.CosNaming .NamingC ontextPackage.In       va   lidName
  {
                               or g.omg.CO  RBA.por table.    In  putStream                $in = null;
                 try {
                              org.o      mg.CORBA.port able.OutputStream        $out  = _request ("re   solv  e", true);
                       org. omg.Cos     Namin g.NameHelper   .write   ($out,   n);
                          $in = _         in    voke ( $out      );
                org.     omg.C      ORBA.      Object $result = org          .o   mg. CORBA.Objec   tHelper    .read ($   i    n        );
                   return  $resul   t; 
                } catch (  org.omg    .CORBA.portable.Application    Excep   tion $ex    ) {
                       $in = $ex.get  InputStream ();
                     S   tri      ng _id = $ex.     ge   tId ();
                           if    ( _id.e   quals ("IDL:omg.org       /      Cos      Nam  ing    /Naming        C   ontext/NotFound:1.0"))
                            th row org.o  mg.Cos        Naming.NamingCo nte xtPac          kage    .NotFoundHelpe   r.read ($i  n);
                     el           s   e i  f    (_id.equal      s ("IDL      :om g.org/CosNa m  ing   /Naming  Context/Cann otProceed:1.0"))
                                                    throw org.om    g.CosNa    min g    .NamingContextPac   kage.        Cann   otP roceedHelper.read ($in);
                          else if (_id.equals ("IDL:omg.org/CosNaming/NamingCon       text/InvalidName:1.0"))
                         throw org.o mg.C osNam    in       g.Nami   ngCo   ntextPackage.InvalidName       Helper.read     ($in    );
                       else
                                                 t     hr  ow n       ew o   rg.omg       .CO   RBA.MARSHAL (_id);
              }          c     atch (org.     omg.CORB  A.portable.Remar       shalE   xception $rm    ) {   
                         return   resolve (n           );
              } f  inally {
                           _releaseReply ($   in);
                   }
    } // resolve


  /**    
 * T    he unbind op    eration re   moves a name binding fro m a    c     ontext.
 * 
 *         @par    a     m n   Name            o  f the object <p>
 * 
 * @exception org.om  g.CosNamin g.NamingCo  n     textP    ac  ka     ge.NotFound Indica   tes  the nam          e does not id               entif   y a binding.<p>
 *   
 * @exception org.om   g        .C   osNam        ing.N  a   min   gContextPackage.Ca   nnot Proceed Indicat  es that the implement ation h   as
 * given up  for some reason. The client, h    owever, may be  ab    le to 
 * continue    the op   eration at t    he r eturned naming context.<p>
 *                 
 * @excepti   on  org.omg   .CosNa  min        g.Na           mingContextPa   ckag      e. Inv   alidName I   n dicat  es that      the      name is inv      alid. <p>
 *        /
  public void un   bind (org      .om  g  .CosNaming.Name  Comp  onent[]                n) throws or   g.omg.CosNaming.N    amingContextPa   cka  ge  .NotFoun    d, org  .o mg.Co  sNa    ming.NamingContextPackage.Can  n      otProceed, or  g.omg .CosNaming.Na  min  gContex      tPackage.Inva    li  dName
  {
                org.omg.COR    BA.portable.    Inp   utStream $in = null;
               try    {
                org       .omg.CORBA.    porta bl e.OutputStream $ou    t =    _requ    est    ("unbind",   tru e);
                        org.omg.Co     sNaming.N    ameHelper.w r  ite ($out,       n);
                 $i        n = _invok e ($   ou     t);
                 ret    ur    n;
            } ca  t           ch       (org.omg.C       ORBA.port     able.Appli     catio  nExc   ep tio n $e  x) {
                                    $in = $ex.getInput S      tream ();
                         Str  i ng _id  = $ex.   getId ();
                          if (_id.e    quals ("ID         L:omg.org/CosNaming  /NamingCont ext/NotFound:1.0"))
                    throw org.omg     .CosNaming.Nam  ingContextPackage.NotF   ound  Help                       er.read (          $in  );
                         else if (_id.   equals ("ID       L:om  g.org   /CosNaming/N amingContext/C  anno tPro  c        eed:1.  0"   )      )
                                        thro       w org.o     m g.CosNaming. Nami  ngContextPackage.CannotP  r        oceedHelpe  r.r        ead ($in);
                els  e i f     (_i   d.        eq    ual   s ("IDL:o  mg.org/  Co  sN    aming/NamingCont   e        xt/InvalidName:1.0"   ))
                    th   row org.omg.CosNaming.NamingC   ont  e         xtP          ackage.I  nvali dNameHelp      e     r.read (   $in        );
                          else
                           throw new org  .omg.CO    RB  A  .MARS  HAL (_id);
                 } catch   (org.omg.CORB A.port     able.Re   marsha     lExc     eption $ rm) {
                       unbind (n            );
                }         finally {
                         _releaseRep   ly ($in);
                  }
  } // unbind

     
     /**
        * The     l  ist opera    tion al   lows a clie      nt to iterate throug       h a         set of
 *    bindings          in a    naming context   . <p>
 *    
 * The     list   operatio n  r       et          ur      ns at most the reque  sted number of
 * bindin   g   s in  BindingLi    st   b  l. 
 *   <u l>
 * <li>If the   na     m        i  ng cont  ext c      ontains addi tional 
 * binding   s, the list oper   ation returns a BindingIterator with the 
         * ad    ditional bin  din     gs.              
        * <l     i   >If the naming context does not contain a      dd  itiona      l 
 * bindings,     the bindi       ng ite   rator is a nil obj      ect refer    ence.
 *      < /u l>
 * 
 * @param   how_ma    ny     the maxi     m          um number   of bindings to re     turn <p>
 *    
 * @param bl     the r eturned list of bindin  gs <p>
 *       
 * @para      m           bi the returned binding iter   ator <p      >
     */
  public   void li           st (int how_many ,     o  r    g.omg.C         o     sNa    ming.     BindingListHolder bl, or     g.omg  .CosNa     m    ing.BindingIteratorHolder bi)
  {
                          o  rg.om   g.COR    BA.port   able.Input   Strea m $in = nul   l;
               try {
                         org   .omg.CORBA.port       able.OutputSt   ream $out = _reques t ("list"      , t  r ue);
                          $out.wr    ite_ulong (how_many     );  
                             $in = _inv  oke ($o ut);
                       b   l.valu    e = o  rg.omg.CosNaming   .BindingListHel    per.r            e   ad ($in);
                        bi.value =   org.omg.CosNaming.Bin    dingIteratorHelpe    r.read ($in);
                         return;
                    } ca tch (org.omg.CORB  A        .portable    .ApplicationException $ex)       {
                      $     in = $ex. getInputStream (      );
                       String _id = $ex .getId ();
                 throw n    ew      org.omg   .CORBA.M      ARSHAL (_id);
              } catc     h (org.omg.CORBA.portable.RemarshalExcepti  on $r m) {           
                list (h ow_many, bl, bi            );
            } fi   nally {
                 _releaseRe      p  ly   ($in);
                          }
    } // list


  /**
 * This oper   ation returns a     n    aming cont     e  xt implemented by the sam    e   
 * n  ami  ng   s erver as the context on           which the operation was invoked.          
 * Th    e    new contex     t is not bou     nd to    any name.
 */
  public org.omg  .CosNaming.NamingCon    text ne w    _context ()
  {
               org   .omg.CORBA.po  rtable.    InputStream    $in = nul   l;
                          try {
                     org  .omg    .     CORBA.portable.O       utputStr     eam $out =              _req   uest ("n     ew_conte  xt",  true      );
                          $   in = _i n  vok   e ($o   u       t);
                  org.omg.C  osNaming.NamingContext $result = org.omg.CosNaming.Na   mingContextHelper.read ($in);
                return          $r   esult;
            } catch (org.om  g.C  ORBA.       port  able.Appli           catio   nExceptio           n $ex) {
                $in    = $ex.ge    tInputStream ();
                   S  trin     g _id    = $ex.getId (); 
                throw new org.omg.CORBA.M ARSHAL (_id);
                   } catch (org.omg.CORBA.portab le. Rema rshalExcep  tion $rm) {
                    retur        n new_co   ntext   (         )   ;
                        } finally {
                          _releaseRep  ly ( $in);
                   }
  } // new_context


  /    **
 * This operation creates    a new context and binds it to    t   he name
 * supplied as   an a      rgum           e  nt. The newly-created context is implement ed   
 * by the same naming server as the context in which it wa    s b   ound (tha t 
 * is, the nami ng serv   er that  implements the context den  oted by the 
 *   nam  e a     rgument e      xclu   d    ing  the last component).
 * 
 * @param n Name o  f the object <p>
 * 
 *      @exception org.o   mg.Cos   N    amin   g.NamingContextPackage.NotFo    und Indicates the name does     not identify a bindi    ng.<p     >
 * 
 * @exception org.omg.CosNaming.NamingContextPackage.AlreadyBound Indicates an ob  ject is already 
 * bound to      the specified  n           ame.<p>
 * 
 * @     exception org    .    omg.CosNaming.Namin    gCon  tex  tPackage.CannotProceed Indica  tes tha        t th  e implemen  tation has
 * given up for some reason    . The client, however, may be   able to 
 * c    ontinue the operation           at the returned naming context.<p>
 * 
 * @   exception org.omg .CosNami      ng.NamingContextPackage. InvalidName Indicates      t  hat th        e n ame is invalid. <p>
       */
  public org.omg.CosNaming.NamingContext bind_new_conte    xt (or   g.omg.CosNaming.NameCompone       nt[] n)   throws org.omg.CosNaming.NamingContextPackage.    NotFound, org.omg.CosNaming.N  amingC ontextPackage.AlreadyBou     nd, org.omg.CosNaming.NamingContextPackage.CannotProceed,  org.omg.CosNa      ming.NamingContextPackage  .Inv      alidName
  {
            org.omg.CORBA.po     rtable.InputStr     eam $in = null;
                         try {
                org.o mg.CORBA.porta    bl      e.OutputStream   $out = _request ("  bind_new_  conte     xt", true);
                      org.omg.C      os    Naming.NameHe  lper.write ($out, n);
                $in = _invoke ($out);
                   org.  omg.CosNaming.NamingC              ontext $r es   u   lt = org.  omg.CosNaming.NamingContextHelper.read ($in    )    ;
                     retu  rn $result;
               } catch (    org.omg.CORBA.portable.ApplicationExcepti     on $ex) {
                  $in = $ex.getInputStream ();
                 String _id = $ex.getId ();
                      if (_id.e   quals ("IDL:omg.org   /CosNaming/Nami    ngContext/NotFound:1.0"   ))
                                throw o rg.           omg      .CosNaming.N  amingConte  xtPack     age.   NotFou   ndHe    lper.read ($ in     );
                else if (_id.equals ("IDL:omg.org/CosNaming/NamingContext/AlreadyBound:1.0"))
                           t    hrow org      .  omg.CosNaming.NamingContextPack age   .Alre   adyBoundHelper.rea    d ($in);
                el  se if (_id.equals ("IDL:omg.org/CosNaming/NamingContext /CannotProceed:1.0"))            
                        throw     org.omg.CosNaming.NamingCont  extPackage.CannotProceedHelper.re    ad (      $in);
                else if (_id.equals ("IDL:omg.org/CosNaming/NamingContext/InvalidName:1.0"))
                    throw org.om g.CosNami       ng.NamingContextPackage.InvalidNameHelper.read ($in);
                   else
                    throw new org.omg.CORBA.MARSHA       L (_id);
            } catch (org.omg.CORBA.portable.Remarsha  lExc   ept  ion $rm) {
                  return bind_new_context (n        );
            } finally {
                        _releaseReply ($in);
            }
  } // bind  _new_context


  /** 
 * The destroy opera    tion de    letes a naming context. If the naming 
 * context contains bindings, the    NotEmpt         y exception is raised.
 * 
 * @exceptio  n org.omg.Co     sNaming.NamingCont extPackage.    NotEmpty Indicates that the N     aming Context contains bindings.
 */
  public voi d destroy (   ) t       hrows org.omg.CosNaming.NamingContextPackage.NotEm pty
  {
            org.omg.    CORBA.portable.InputStream       $in = nu  ll;
            try {
                     org.omg.CORBA.p  ortable.OutputStream $out = _request ("destroy", true);
                $in = _invoke ($out);
                return;
               } catch (org.omg.CORBA.portable.ApplicationExcep    tio   n $ex) {
                     $in = $ex  .getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:omg.org/CosNaming/NamingContext/NotEmpty:1.0"))
                    thro  w org.omg.CosNaming.NamingContextPackage.NotEmptyHelpe r.read ($in);
                    els        e
                         throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.Remarsh     alException $rm) {
                    destroy (        );
            } finally {
                _releaseReply ($in);
                }
  } // destroy

  /   / Type-specific CORBA::Object operations
  private static String[] __    ids = {
    "IDL:omg.org/CosNaming/NamingContext:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF (   );
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
      String[] args = nu      ll;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _NamingContextStub
