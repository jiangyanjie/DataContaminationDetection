/*
      *     Copy     right  (c) 1997,   2000, O    racle and/or its affiliates    . All rights reserved.
 * ORACLE PROPRIETARY/CONF  IDENTIAL   .        U      se is subject      to lic   ense terms.
 *
     *
 *
 *  
     *
 *       
 *   
 *
 *
 *
 *
   *
 *
 *
 *
 *
    *
      *
 *
 *
 */
/*
 * File: ./org/omg/CosNaming/_N amingContextImp   lBase.java
  * From  : name          service       .i  dl
 * Da t          e: Tue Aug   1  1 03:12:0     9 1998
    *   By: i      dltojava Ja va I  DL 1.2      Aug 11 1998 02:00:18
 *      @deprecated Deprecated     in JDK 1.4.
 */

package org.omg    .CosNaming;
public abstract      class _NamingC        ontextImpl Base   extends org.omg.CORBA.DynamicImplementation implements org.om  g.CosNaming.NamingContext {
       //   C  o     nstructor
     public        _   NamingContex    tImp   lBas     e() {
        super( );
        }
    // Type strings for this     c      lass and its superclases
    private static final Strin  g _type_ids[] = {
        "I     DL:om        g.org/CosNaming/NamingContext:1  .0"
              };

      public String[] _ids() { ret urn (Str   ing  []) _type_ ids.clon    e(); }

    private      static java.util.Dictionary _methods = ne    w java.util.Hashta    ble();     
    static {
                                        _methods.p      ut("bind  ", new java.l   ang.In    teger(0));
          _methods   .put("bind_con  text", ne  w    java.lang.I           nteger(1));
                _me  thods .put(  "re  bin     d", new java.lang.Integer(2));
          _meth    o  ds.put("rebind_context", new    java.lang.  Integer(3));
                   _methods    .pu   t("resolve   ",      new java.lang.Integer (4))    ;
                      _method s.put("u  nbind", new jav    a.lang    .   Integer    (5));
        _methods.put("list", new    java.la   ng.Integ  er(6));   
        _methods.pu  t("new_context", new java.       la       ng.Integ   er  (  7));
        _methods.put("bi   nd_new_context", new jav  a. la ng.Integer(8));
                _m    ethods.put("destr oy", new java.lang.Integ    er(9));
      }
       // D    SI D   ispa  tch call
    pu    blic v    o     id invoke(org.omg.CORBA.ServerR eque   st      r) {
                  switch (((j ava.lang.In     teger) _methods    .ge       t(r.op _name())).      intV alu   e()) {
        case 0:                      // org .omg.        CosNaming.NamingC   onte  xt.bind    
                        {
                    org.omg.CORBA.     NVList     _     list = _orb().create_   list(        0 );  
                org.o   mg.CO    RBA.          Any _n = _orb(         ).cr        e     ate  _a    n   y();
                                     _n.ty   pe(org.omg.CosNa  ming.             NameHelper.type());
                                       _l i         s             t.add_val  u   e       ("n", _n, o   rg.omg.CORBA.ARG_IN.v alue);
                                   org.omg     .  CORBA.Any _obj = _orb             ().c   reat        e_any()   ;
                                     _o    bj.type(org.omg.CORB   A.ORB.init        (   ).get_pr       i  miti       ve_tc(org.o m        g.C  OR      BA.        TCK         ind.tk_ob      jref));
                                      _l   ist.a   dd_value("obj",          _obj,   org.omg.CORB      A  .ARG   _IN            .   valu   e);
                            r.params        (_list);
                  org.o       mg.     CosNaming.Na meComponent[] n;
                                               n = org.omg.CosN  am           ing.N             ameH         el         per.ex      t   rac       t(_n);
                       or             g.om    g              .CORBA    .Object     o    bj   ;    
                                  obj                    =   _  obj.extract_Object();
                                                 tr    y {
                                     this.bi  nd     (n, ob          j)   ;
                                          }            
                       catch (o    rg   .om             g.C      os Naming.NamingContextPa  ckag   e.NotFo und e0) {
                                      org.om              g.    CORBA.Any      _except = _orb()        .crea    t    e_any    ();
                                   org  .omg.CosNaming.Nami ngC  ontextP  ackage.N  ot     Fou ndHel   per.insert(_except, e0);
                                                    r  .excep t   (  _exce      pt);
                                       return;
                             }   
                       catch (org    .omg.Co   sNaming.NamingContextPackage.Cann   otProceed e 1) {
                              org.o      mg.CO      RBA.Any _except = _orb().cre     ate_an   y()     ;
                              org.   o      mg.CosNaming.Nami  n  gContextPackage.C   ann     otP r     oce  edH            elper.inse   rt(_e     xc        ept,   e1);
                                         r.e   xcept(_except     ) ;  
                     retu       rn;
                      }
                  c   a   tc       h (org   .omg  .                 CosNa  ming      .Namin  g  ContextPack   age  .InvalidNa    me     e2) {
                       o  r           g.omg     .CORBA.An   y _    except = _ orb(    ).                      creat        e  _any();
                                  org.o  mg.C          os  Naming   .Na    mingContextPackage.Invali    dNameHelper.insert(_e x cept, e2) ;
                                       r.ex    c ept(       _except       );
                          return;
                       }
                                      ca  tc h      (o   rg   .om   g.CosN  ami    ng.Namin  gC   on   textPackag   e.AlreadyBound e3)       {
                                             o  rg.om        g.COR              BA.Any   _exce    pt      =         _o    rb().  cre a te_any();
                               org.o      mg.CosNam        ing  .       Namin          gC       ontextP     ackage.AlreadyBoundHel    p    er.i   n sert(_except, e3     );
                                      r.except(               _ex cept) ;
                            re  turn;
                   }
                               org.omg.CO R          BA.Any   __return     = _orb   ().cre     ate_ an  y();
                    _   _    return.                                 type(   _orb()   .      get_pri   mitive_tc(org.omg   .     CORBA.         TCKind.t  k_void));
                           r.resul      t(__return);      
                       }
                  brea  k;
              case 1   : // org.o   mg.Co s          N   am    in g.Nam  i   ngCo   ntext.b         ind_context
                 {
                org.      omg.COR        BA.NVList _list = _orb().     c    r      e    ate_li  st(0);
                                 org.om       g.CORB  A.Any _n      =       _orb   ().cre                        ate   _any()   ;      
                                         _n.type(org.omg.CosNaming.Name    Helper.     ty pe());
                    _l  ist.add_value(                         "n", _n, o       rg.omg.C  O            RBA.ARG_IN.val  ue);
                             or g. omg.COR           BA             .A  ny   _nc                = _orb().create_any();
                    _nc.t     y p     e(org.omg.Cos         N  aming.NamingC  ontextHelper.t   ype());
                            _li   st.add _v   alue("nc", _nc   ,  org.o m g.  COR          BA.AR                           G_IN.val   ue)       ;
                            r.  par     ams(_lis      t);
                         o       rg.omg.CosN     am ing    .NameCo mp     on    e    nt[] n;
                          n = org.om g.C   osNaming.Name   Helper.e  xt    rac t      (       _n)     ;
                          or g.omg.CosNaming   .N  amin                        gCont                ext n          c;
                                        nc = org  .     om    g      .CosNaming.  Na  mingContex t        Helper.extract(_nc);
                        try     {
                                     this.b     ind_context(n, nc);
                         }  
                                         cat    ch            (       org.o     mg.CosNa      mi      n   g.NamingContextPackage.NotF   ound e0)  {
                                                   or   g.omg.CO   RBA.Any _e     xcept =   _     orb().cr eate_any       ();
                    org.omg. CosNamin      g.Nam   ing    C o  ntextPackage.NotFoundHe  lper.    inser    t  (_     except, e0);   
                                                r.exce      pt(_except)   ;  
                                                   r  et     urn;
                                 }
                      catch (o rg.om    g.CosNaming.N  amin   g  ContextPack    age.Cann      otPr    oc   e  ed e1) {
                                 org     .om g.CO      R         B  A.A  ny    _except = _orb       ().crea   te    _any();
                                     org.omg.Co           sNami  ng.NamingCont        e x    tPackage.Cannot  P  r     oc  ee   dHe     l pe  r.insert(_except,     e1);
                         r          .e   xcept         (   _except);
                                        return;
                       }
                          c   atch      (org.omg.CosN  amin     g.NamingCo             ntextPacka        ge.InvalidName e2)    {
                                             or  g.omg.CORBA        .Any        _except = _orb().create_any();
                               or  g.    om   g.C       os       N          aming.NamingCon  textPackage.InvalidNameHelper.in      sert(_ex   ce pt, e     2 );
                                                            r.excep   t(_          except)   ;
                      return;
                                   }
                       catc   h (org. om g.Co    sNam  ing.Nami     ngContextPackage.AlreadyBound  e3) {
                      org.om     g.CORBA.Any  _except   = _orb().cr    eate_ any()  ;
                                           org.omg.          CosNaming.NamingContextP   ackage.A  lrea      dyBound       Helper.       insert(_ex  cept, e3);   
                        r.          except(_except);
                        r          eturn;    
                                }
                                 or      g    .omg.CORBA.Any __    return = _or     b().     cre     ate_a     ny();
                                        _  _return.type(     _o             rb().    get_      primit      ive    _tc(org.omg.COR   BA.TCKi   nd.tk_void       ));
                               r.res     ult(__             return);   
                     }
                  b      r              eak;
                case 2: /  / org.omg.CosNaming.NamingCo  n   t   ext.      rebind
                            {
                        org.omg   .CORBA.NVList _list =    _or       b().    create_    list(0);
                           org.omg.CORBA   .A  n     y _n = _o   rb().create_any();
                              _n.type(org             .    omg.CosNa  ming.Name  Helpe   r.type());
                                    _list.a      dd_value("n",     _n   , org.omg.CORBA.ARG_IN.val      ue)    ;
                     org.omg.CORBA       .Any _obj         = _   o  rb()    .create_a  ny      ();
                                      _obj.type(or  g                 .omg.     CORBA.OR    B.in   it()  .get_primit       ive      _tc(o rg.omg.C ORBA.TCKind .    tk_o       bjr  ef));
                        _list   .add_v    al   u   e   ("obj    " ,                 _ obj, org.om  g.CORBA.   ARG_IN.value);
                             r.params(_list);
                         org    .omg         .CosN    a       ming.NameC         ompone nt[     ] n;
                      n     =   org.omg.CosNaming.N  ame   Hel  per.extrac t(_        n);
                or    g.omg.C      OR    BA.Ob ject o      bj  ;
                              ob j = _o  bj.extract               _Object(    );  
                  try {    
                    thi s.rebind(n,     obj        );
                       }
                         cat         ch        (org.omg.CosNaming.Na m  i ngContextPac kag  e.NotFo  un       d  e0)     {
                                or    g  .omg.COR     BA      .Any _except =    _o            r         b()  .create_a  ny();
                                  org.omg.CosN          aming.Naming   ContextPa   cka     ge.Not   FoundHel      per .insert(_exc   e pt , e        0);
                                   r.except(_ex ce  p  t);
                                                     ret urn;  
                                                }  
                                catc   h  (org.o   mg.Cos  Namin          g.NamingContex   tP  ackage.CannotProceed e    1) {
                             o  rg.o     mg.CORBA.Any    _ex    c       e     pt = _orb().create    _a       ny();
                             org.o       mg                 .Co sNa      mi                       ng.  NamingContextPackage.CannotProc eed    He    l     p    er. inse     rt(_exc ept   ,                e1);
                            r.    except(_exc    ept   );
                         r    etu    r  n;
                                                  }    
                                    catch (    org.omg.CosNaming.NamingContextPackag  e.I    nval idName e2) {
                                    org.omg.CO       RBA.Any _except = _orb().creat e _any(); 
                                org.om     g.C  osNam       ing.Namin    gCo ntex       tPa cka      ge.   InvalidNameH     e   lpe        r.in ser   t(_excep t,     e2)    ;
                                   r.ex  cept(_         e x   c       ept)     ;
                            re        tur      n;
                              }
                                  org.omg.CORBA.Any _    _return   = _orb()    .creat     e_an   y    ();
                          __return.type(_or  b ().ge        t_      primiti          ve_tc( org   .o  mg    .CORBA  .TCKin  d    .tk   _vo     id));
                       r     .result(__ret    ur     n) ;
              }
                break;
                cas   e     3:     /          /     org.omg.  C        osNa    min   g.N           amingContext.r     e           bind_con        te  xt
                  {      
                org     .    o      mg.  CO RBA.N VL       ist _list            = _orb().create_ lis          t(0)  ;
                    o     rg.omg.   CORBA.Any _n  = _o   rb()  .create_any();
                    _n.ty     p    e(     o   rg.omg        .CosNaming.Na        meHelper.t       ype());
                            _list.add_value              ("n", _n, org.omg     .CORB   A.A         R G_I  N.valu     e);
                              org.omg.CORBA.An         y _nc    = _orb().create_any();     
                              _    n    c.ty        pe(org.om g.CosNamin  g.           Nam    in    gCon     te xt                     H                elper.     t      y      pe());
                _l     is   t   .a  dd_value("n  c",   _nc, org.   om    g.      CORBA.ARG_IN   .value );
                              r.par   ams(_list   );
                           o rg      .omg  .CosNaming.Name  Co     mp       on     ent[]  n;  
                n = or     g.omg.CosNaming.      NameHel       per.extract(_ n);
                       or    g.omg.CosN                 aming.Nam ingContext nc;            
                              nc = org.  omg.CosNaming.Naming ContextHelpe    r.ext         ra     ct(_nc);
                         try {
                           this  .rebind_context(n, nc);
                         }
                                      catch  (org.o    mg.CosNam              ing.NamingCont  extPa     ckag    e.NotFound e0) {         
                                   or      g.omg. CORB     A.Any _except = _o rb   () .create   _any(     );
                                       org.omg  .CosNaming.Naming     Conte       x     tPac      kag      e.NotF    oundHel per.ins ert(_   e    x  ce        pt, e0);
                     r.exce    pt(_except);
                                   retur  n;
                 }
                                                    ca       tch (org.  om    g.CosNam      in      g.NamingCon   t     extPack         a ge.Cannot       P    r oceed e1)     {  
                                           or    g.o  m      g.C              OR BA.Any _e         xcept = _or   b().creat e_any();
                                     o    rg.omg.C     osNam  ing.Na             ming       Co     ntextPa  c    kage.Ca nnotP      roceedH   elper.ins      ert    (_except, e 1);       
                         r. exc           ep t(_e    xcept)   ;     
                                   retu     rn;   
                                        }
                    catch (org.omg    .   CosNami       ng.Nami    ngContextPac           kage.InvalidName e2) {
                                org     .omg.CO         RBA.Any _   except = _orb().create_any();
                           org.   omg.  CosNaming.  Na  mingContex tPa    c  kage.Inv     alidNa    meHelpe   r.insert  (    _except,   e2    );
                                     r.except(   _exc ept);
                                                           re t      urn;
                                    }
                                  org             .  omg.CORBA.Any __          return =    _orb().create_any();     
                __return.type(       _orb().get_primitiv    e_    tc(org.omg.CORBA.TCKind.tk_void));
                       r.re       sult    (    __return     );
                  }
                    b     reak;
                ca  se 4:     /  / o        rg.omg.    Co   sNami ng  .Nam    ingContext.resolve
                     {
                  org.omg. CORB   A.        NVList _li     st =             _orb()  .create_l  ist(0    );
                  org.omg.CO       RBA   .  Any _n    = _orb().creat         e_an     y();
                                                 _n.type(org.  omg.CosNaming.        NameHelper    .type());
                               _list.add_value("n", _n, org.omg.C O      RB      A.ARG_I     N        .value);
                    r.params(_lis t);
                                   or    g.omg   . Co  s    N  ami  ng.Na    meCo      mponent     []     n;
                      n = org   .o      mg.Co        sNaming.Na        meHelper.   ext   ract    (_n)  ;
                  org.omg.CO    RBA.Ob je  ct __      _result;
                           try {
                           ___re  sult      = this.r      esol        ve(n);    
                              }
                        catch      (org.omg.Co       sNaming .NamingCont        extP   ac  kag     e     .NotFound           e0 ) {
                     org.   o      mg.CORBA.Any _exce   pt =    _orb(). create_any(   );
                                       org    .omg.Co   sNaming.NamingConte    xtPack    age.NotFound Helper   .i nsert(_      except           , e0);      
                              r.  excep  t(_except);
                                        re  tur   n;
                                }
                       c      atch (org.om          g.Co sN aming.N       am i        ngContextPa  c   kage.  C   ann   otProceed e1) {
                                            o  rg.omg.   CO R          BA.Any _excep            t =   _orb().create_a n          y();
                                  org.omg.C  osNaming    .NamingContextPac      kage    .  CannotProceedH  elper.insert   (_except, e1) ;
                                    r.except(    _except   )          ;
                         return;
                      }
                                      catc   h    (o  rg.omg.C  os  Naming.NamingCont    extPacka       ge.Invali  dName e2 ) {
                           org.  omg.CORBA        .Any _except      = _orb().cr  eate_any();
                              or     g.    omg.CosNaming.Nam      ingCon    tex  tPackage.Inva       lidNameHelper.insert(_except, e   2);
                                        r.exc     ept(_except);
                       retur   n;
                  }
                          org.omg.CORBA. Any __     result = _orb().create_any();
                          __result.insert_Object     (  ___res  ult);    
                       r  .result (_     _r   esult);
                          }
                            break;
               case 5: // org.omg.CosNaming.NamingContex  t.unbind
                           {
                           o r     g.omg.CORBA.NVLis   t      _list  = _orb().create_list(0);   
                         org. omg.COR      BA.       Any _  n = _orb().create_any();                
                             _n.type(     or         g   .omg.C   osNa             ming.NameHelper.type());
                     _l ist.add_v alue("n", _       n,       org.omg.CORBA.ARG_IN.valu  e);  
                      r    . params(_li      st);
                                 or   g.om g.CosNamin            g.Na   meC ompone      n     t[] n;
                n = org.omg.C   osNaming.NameHelper.extract(_n         );
                     try {
                             t        his.unbind(n);
                            }
                    catch (org.omg         .CosNaming.Nam    ingC    on   textPackag          e.NotFo       und e0) {
                           org.    o  mg.      COR    BA.A   ny _except = _orb  ().creat e_any();
                              org.omg.Co sNam  ing.NamingContextPackage    .      NotFou    ndHe lper.insert(_except,    e0);
                           r.except(_exce      pt);
                              r  et       urn;
                    }
                      catch  (org.o      m  g.CosNaming .    NamingCon   textPackage.Cann    otPr     oceed e1)  {
                         org.omg.COR  BA.Any    _excep       t              = _orb().        crea te _any()  ;  
                                             org.omg.CosNam     ing.NamingCon    textPac  kage    .CannotPr      oceedHelper.insert(  _except             , e1);
                             r.exc   ept(_except);
                      ret   urn ;
                }
                  catch (o       rg.o   mg. CosNaming.NamingCo   ntextPacka  ge .Inval  idName e2)   {
                                org.o   m       g.CORBA.An   y _e   xcept = _orb().cr      eate_a ny    ();
                        org    .omg.CosNami  ng.NamingContext   Packag   e.In validNa     meHelper.insert(_except,     e2);
                          r.except(_except);
                    return;
                            }
                   org.o    mg.CORBA.Any   __return = _orb().cre    ate_any();
                 __return.type(_orb().get_primitive_tc(org.omg.CORBA.TCKind.tk_void    ));
                                        r.result(__    ret    urn);
            }
                      break;
        c    ase 6:       // org.om  g.CosNaming.    N       amingContext.list
                     {
                   org.omg.CORBA.   NVLis t _list = _orb().create_list(0);
                           org.    omg.CORBA.Any _ho      w_many = _orb(     ).crea              te_a  ny();
                      _how_m    any.type(org .omg.CORBA.ORB.ini    t().get_primitive_tc(      org    .omg.CORB   A   .T     C  Kind.tk_ulong))      ;
                 _li  st.add_v  alue("how_man   y     ", _how_many, org.omg.CORBA   .ARG_IN.value);
                org.omg.CORB A.Any     _bl = _o   rb().cr   eate_any();
                   _bl.type(org.   omg.CosNaming.    Bin     din    gL     istHelper.type   ());
                  _   lis       t.add_value  ("bl", _bl, org.omg.    CORBA.A    RG_OUT.value  );
                                org.omg.CORB       A.Any _bi =        _orb().crea    te_any();
                _bi.type(org.omg.CosNaming.Bi   ndingIteratorHelper.type(  ));
                  _  list.       add_v    a    lue ("bi ", _bi,   org.omg.COR   BA.ARG_O  UT.value);
                         r.params(     _list);
                         int ho    w   _many;
                     how_man    y   = _how _ma     ny.ex tr   act_ul          ong   ();
                                            org.omg.CosNaming.Bin        dingListHo    l der bl ;
                                      bl = new org.omg.CosNaming.Bindi    ng ListHolder();
                    org.omg.CosNaming.Bind   in     g IteratorHolder bi;
                b i = new org.omg.C osNaming.BindingIt  eratorHolder();
                 this.list(   how_many, bl, b  i);
                         org.om          g.Co     sNamin     g.Bindin    gLis    tHelper.insert(_bl, bl.valu   e);
                    o  rg.omg.CosN  aming.BindingIteratorHelper.insert(_bi, bi.value)        ;
                         org.o mg.CORBA.Any __    return = _orb().create_any();
                     __return.type(_orb().get_primitive_tc(org.o    mg.CORBA.TCKind.tk_void));
                       r  .result(__re       turn);
                  }
            break;
        case 7: // org.omg.CosNami  ng.        NamingContext.n        ew_con    text  
            {
                             org.omg   .CORBA.NVList _list = _orb().create_   list(0);
                r.  par  ams(_list);
                     or       g.omg.CosNami  ng.Namin     gC     ontex  t ___res      ult;
                      ___result = this.new_context();
                        org.omg.CORBA.Any __r  esult = _orb().create_any();
                     org.omg.CosNaming.NamingContextHelper.insert(__res     ult, ___       result);
                r.r  esult   (__result);
            }
                br     ea   k ;
        case 8: // org.omg.CosNaming.  Nam       ing  Context. b         ind_new_context
            {
                org.omg.CORBA.NVList _list   = _orb().create_list(0);
                     org.o   mg.     CORBA.Any _n = _orb().cre        ate_  an  y();
                            _n.type(org.om g.CosNaming.NameHelper.t        ype());
                    _list.add_value("n", _n, org.omg.  CORBA.ARG_IN.va      lue);
                r    .params(_list);
                    org.omg.C      osNaming.Name  Component[]  n;
                   n = org.omg.CosNaming.Na  meHelper.ex   tract  (_n);
                org.omg.CosNaming.   NamingC   ontext ___r  esult;
                  try {
                     ___result = t   his.bind_new_context(n)    ;
                }
                catch (   org.om g.C   osNamin   g.N amingCo   ntextPackage.NotFound e0) {
                    org.omg.CORBA.Any _except = _orb().c  reate_an  y();
                           org  .omg.CosNaming.NamingContextPackage.NotFoundHelper.insert(_except, e0);
                     r.except(_except);
                    return;
                      }
                     catc h (org.omg.   CosNaming.NamingContextPackage.AlreadyBound e1) {  
                       org.omg.CORBA.Any _ex    cept = _orb().create  _any();
                    org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.insert(_except, e1);
                         r.exc    ept(_except);
                    re   turn;
                         }
                catch (org.omg.C   osNaming.NamingC   ont extPackage.CannotProceed e2) {
                          org.omg.CORBA.Any _except = _o  rb().create_any();
                    org.omg.CosNaming.NamingContextPackage.CannotProceedHel per.insert(_except, e2);
                    r.except(_except);
                    return;
                 }
                catch (org.omg.CosNaming.NamingContextPackage.Inv   alidName e3) {
                           org.omg.CORBA.Any _except = _orb().create   _any();
                    org.omg.CosNaming.NamingContextPackage.InvalidNam   eHelper.insert(_except, e3);
                    r.ex  cept(_except);
                    return;
                 }
                org.omg.CORBA.Any __result = _orb().create_any();
                     org.omg.CosNaming.NamingContextHelper.insert(__result, ___result);
                     r.result(__result);
            }
            break;
        case 9: // org.omg.CosNaming.NamingContext.destroy
            {
                    org.omg.CORBA.NVList _list = _orb().create_list(0);
                r.params(_list);
                try {
                    this.destroy();
                }
                catch (org.omg.CosNaming.NamingContextPackage.NotEm   pty e0) {
                    org.omg.CORBA.Any _except = _orb().create_any();
                    org.omg.CosNaming.NamingContextPackage.NotEmptyHelper.insert(_except, e0);
                    r.except(_except);
                    return;
                }
                org.omg.CORBA.Any __return = _orb().create_any();
                __return.type(_orb().get_primitive_tc(org.omg.CORBA.TCKind.tk_void));
                r.result(__return);
            }
            break;
        default:
            throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
        }
    }
}
