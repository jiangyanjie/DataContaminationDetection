//  -*- Mode:   Java -*-
//
     // _StartupAddressExample.java

/*
+---------------------     ------- BEGIN LICENSE BLOCK --------------------  -----      --+
        |                                                                                                                                                                                                                                         |
| Ve             rsion: MP                     L     1.1         /GP  L 2.0          /LGP    L     2.1                                                                                                  |       
|                                                                                                                                         |        
          |                  The c on tents of this      file       are                   subject      to the         Moz illa Pu   blic       Lic            ens e                                          |
| Vers  ion   1.1 (   the "Licen  se"); you may not use     this fi      le             exce pt in                             |
     | c  o    mpli     ance wi    th the Licens  e. Yo      u     may obtain a         copy     of t         he     L   icense  at                           |
    | http://www   .  mozi     lla.org/M      PL/                                                                                                   |
|                                                                                                                                                                               |
|    Softwar     e d   istribu                      ted u n der th  e L  icens                  e            i       s di strib        ute       d  on an    "AS           IS" bas    i  s             ,    |
  | W   ITH       OU T   WARR   AN   T     Y OF ANY KIND, eit her e  xpress or impl     ied      . S   ee the Li   cens       e   |       
      |    fo   r       the               spec     i       fic   langu   age    g  o       v  er ni           ng                           r   ig   hts an    d    l imi                         ta tions under               the              |
  | Li           ce        nse.                                                                                                                                                                                              |
|                                                                                                                                                      |
| T    he Origi   na    l   Code is     the             S        TEL LA Pr   og    ramming Lang    ua      ge.                                             |          
|                                                                                                                                                                             |
| Th        e     Initial D  eveloper         of the Origina l    Code is                                               |
| UNIVER   S    I  TY OF SOU    THERN   CALIFORNIA       ,      I   NF   ORMATION SCIEN                       CES IN    S TITUTE                                      |
| 4676 A   dmira   lty   W   ay, Marina De    l R        ey,    California  9       0292,   U .S.      A.                        |
|                                                                                                                         |
| P      or   tions cr       ea t         ed by the Initi  al Develop   er a  re Copyri    gh  t (   C) 2003-2012       |     
| the Ini tial D      evel  oper.  A  ll Rights Re       se     rv  ed  .                                         |
|                                                                                                  |
|        Cont        ribu   tor(s):                                                                                       |
|                                                                                               |
| Alter    natively , the c        on   te     nts of    this file may be used    under the terms of    |
| ei   ther the GNU Gen   eral Pu     blic Li    cense Version 2 o         r la   ter (the " GPL"), or       |
| t   he GNU Lesser General Public Licen   se   Version 2.1 o       r l    ater (the  "L   GPL"),         |
| in which case      the provision s of the GPL or the LGPL are applicable instead |
| of those above. If y        ou wi     sh to allow use   of your version    of    t   his         file only     |
| u nde r the terms of e ither the GPL or the LGPL, and not to allow others to        |
    | use       your version of this file u   nder the terms o f the MPL, indi       cate y     our    |
| d             ecision       by deleting the provis ions above an     d repla  ce them with the notice |
|   and other provisions required by the GPL or t   he LGPL. If yo  u do not delete |
| the provisions above, a recipient m   ay use      yo  ur version of this    file   under  |
| the terms of any one of the MPL,        the GPL or  the LGPL.                            |
|                                                                                           |
+----------------------------  END LICENSE BLOCK    ----------- ------    ------      ------+
*/

package edu.isi.webtool s.examples.     addressexample;

import edu.isi.stella.javalib.Native;
import edu.i  si.stel   la.javalib.StellaSpecialVariable;
import e      du.isi.webtools.objects  .xml_objects.*;
im    port edu.isi.stella.*;

public class _StartupAddressExample {  
  static void HELP_STARTUP_ADDRESS_EXAMPLE1()     {
    {
      Addre ssExample.SGT_ADDRESS_EXAMPLE_getAddressFromName = ((Surrogate)(GeneralizedSymbol.internRi   gidSymb     olWrtModule("getAddres sFromName", null, 1)));
      AddressExample.SYM_  ADDRESS_EXAMPLE_nameElement = ((Symbol)(Ge           neralizedSymbol.internRi  gidSymbolWrtModule("na meElement",       null, 0)));
         AddressExampl e   .SGT_ADDRESS_EXAMPLE_return = ((Surrogate)(General   izedSymbol.internRigidSymbolWrtModule("return", null, 1)));
      AddressExample.SYM_ADDRESS_EXAMPLE_streetNum = ((Symbol)(GeneralizedSymbol.internRigidSymbolWrt  Module("streetNum", null, 0)));
      AddressExample.SYM_ADDRESS_EXAMPLE_streetName = ((Symbol)(GeneralizedSymbol.internRigidSymbo    lWrtModu le("streetN   ame",    nul     l, 0))      );
      AddressExample.SYM_ADDRESS_EXAMPLE_city =    ((     Symbol)(GeneralizedSym    bol.internRigidSym bolWrtModule("city", null, 0)));     
          A ddressExample.SYM_ADDRESS_EXA    MPLE_state = ((Symbol)(GeneralizedSymbol.internRigidSymbolWrtModule("sta  te", null, 0)))  ;
         AddressExample.SYM_ADDRESS_EXAMPLE_z     ip = ((Symbol)(Gen  eralizedSymbol.internRigidSymbolWrtM   odule("zip", null,    0)));   
      AddressE     xample.SYM_ADDRESS_EXAMPLE_phone = ((Sy   mbol)(GeneralizedSymbol.internRigidSymbolWr  tModule("phone", null, 0)));
      AddressExample.SYM_XSI_type = ((  Symbol)(GeneralizedSym  bol.      internRi gid       Symbo    lWrtModule("type", Stella     .getStellaModule("/STELLA/XML-OBJECTS/XSI", true)    , 0)      ));
      AddressExample.SGT_   ADDRESS_EXAMPLE_getAddressFromNameResponse = ((S    urrogate)(Generalize  dSymbol.internRigidSymbol WrtModule("getAddressFromNameResponse", n   ull, 1)));
      AddressExample.SYM_ADDRESS_EXAMPLE_return = ((Symbol)(GeneralizedSymbol.internRigidSymbolW    rtModule("return"    , null, 0)));
      AddressExample.SGT_ADDRESS_EXAMPLE_nameToLookup = ((Surrogate)(GeneralizedSymb  ol.internRigidSymbolWrtModule("nameToLookup", null, 1)));  
      AddressExample.SGT_ADDRESS_EXAMPLE_Address =   ((Surrogate)(GeneralizedSym   bol.internRigidSymb    olWrtModule("Address", null, 1)));
          Add  ressExample.S   GT_ADDRESS_EXAMPLE_StreetNum = ((Surrogate)(GeneralizedSy   mbol.internRigidSymbol  Wr    tMod  ule("StreetNum", nul l, 1)));
      AddressExample .SGT_ADDRESS_EXAMPLE_StreetName = ((Surrogate)(GeneralizedSymbol.intern Rig       idSymbol WrtModule("StreetName",        null, 1)));
      AddressEx     ample.SGT_ADD      RESS_EXAMPLE_City = ((Surrogate)(GeneralizedSymbol.inter   nRigidSymbolWrtModule("City", null, 1)));
      AddressExample.  SGT_ADDRES    S_EXAMPLE_State = ((Surrogate)(GeneralizedSymbol.internRigidSymbolWrtModu     le("State", null, 1)));
      AddressExample.SGT_ADDRESS_EXAM   PLE_Zip = ((Surrogate)(GeneralizedSymbol.internRigidSymb         olWrtModule("Zip",     null, 1)));
            AddressE    x     ample.SGT_ADDRESS_EXAMPLE_P   honeNumbe   r = ((Surrogate)(GeneralizedSym    bol.internRigidSymbolWrtModule("PhoneNumber", null,       1)));
        AddressExample.SYM_ADDRESS_EXAMPLE_areaCode = ((Symbol)(GeneralizedSymbol.int    ernRigidSymbolW   rtModule("areaCode", nu     ll, 0)));
      Ad dressExample.SYM_ADDRESS_EXAMPLE_exchange = ((Symbol)(GeneralizedSymbol.internRigidSymbolWrtModule("exchange", null, 0)));
          AddressExample.SYM_ADDRESS_EXAMPLE_number = ((Symbol)   (GeneralizedSy     mbol.internRigidSymbolWrtMo  dule("     n um     ber",   null, 0)));
      Add ressExam    ple.SGT_ADDRESS_EXAMPLE_AreaCode = ((Surrogat    e)(GeneralizedSymbol.internRigidSymbolWrt    Module("AreaCode", null, 1)));
      AddressEx   ample.SGT_ADDRESS_EXAMPLE_Excha   nge = ((Surrog  ate)(GeneralizedSymbol  .   internRigidSymbolWrtModule("Exchange", null,   1)));
      AddressExample.SGT_ADDR  ESS_EXAMPLE_Number = ((Surrogate)(Gen               eralizedSy    mbol.int       ernRigidSymbolWrtModule(   "Number", null,   1)));
      AddressExample.SGT_ADDRESS_EXAMPLE_streetNum = ((Surrogate)(GeneralizedSymbol.int   er      nRigidSy mbolWrtModule("stre     etNum", null, 1)));
         Addres   sExample.SGT_ADDRESS_EXAMPLE_str    eetName = ((Surrogate)(GeneralizedSymbol.intern      RigidSymb     olWrtM o    dule("streetName", null, 1)));
           Ad   dress     Example.SGT_A    DDRESS_           E   XAMPLE_ci  ty = ((Surroga  te)(GeneralizedSymbol.internRigidSymbolWrtModule("city", null  , 1)));  
      Add   ressExam   ple.SGT_ADDRESS_EXAMPLE_state = ((Surrogate)(GeneralizedSymbol.intern   Rigi  dSymb    olWrtModul  e("state", null, 1)));
      AddressExampl   e.SGT_ADDRESS_       EXAMPLE_zip = ((Surrogate)(GeneralizedSymbol.intern    RigidSymbolWrtModul  e("zip",  n   ull, 1)));
      AddressExample.SGT_ADDRESS_EXAM    PLE_phoneNumber = ((Sur    rogate)(GeneralizedSymbol.internRigidSymbolWrtModule("phoneNumber", null, 1)));
      AddressExample.   SGT_ADDRESS_EXAMPL     E_areaCode = ((Surroga       te)(GeneralizedSymbol.internRigidSymbolWrtMo  dule("areaCode", null, 1   )));
      AddressExample.SGT    _ADDR        ESS_EXAMPLE_exch   ang   e =     ((Surrogate)(G     eneralizedSymbol.internRigid  SymbolWrtModu  le("exchange", null, 1)));
      Ad dressExample.SGT_ADDRESS_EXAMPLE_numb er = ((Surrogat   e)(G     eneraliz   edSymbol.internRigid SymbolWrtModule("number", null, 1)));
       AddressExample.KWD_M ETHOD        = ((Keyword)(GeneralizedSymbol.internRigi   dSymbolWrtModule("METHOD", null, 2)));
      A ddressExample.KWD_POS     T = ((Keyword)(GeneralizedSymbol        .internRigidSymbolWr  tModule("POST", null, 2)));
            AddressExample.KWD_CONTENT = (    (Ke   yword    )(GeneralizedSym     bol.inter nRigidSymbolWrtModule("CONT     ENT"  , nu  ll, 2)));
      Add  ressExample      .KWD_HEADERS = ((Keyword)(Gen   eralize  dSymbol.internRigidSy   mbolWrtM        odule("HEADERS", null, 2)));
        AddressExample.SGT_SO         AP_ENV_Fault = ((Sur      rogate)(GeneralizedSymbol.internRigidSymbolWrtModule("Fault", Stella.getStella   Mo    du   le("/ST   ELLA/XML-OBJECTS/SOA P-ENV", true), 1)));
      AddressExample.SYM_ADDRESS_EXAMPLE_STARTUP_          ADDRESS_EXAMPL   E = ((Symbo l)(GeneralizedS  ymbol.inte             rnRigidSymbolWrtModule("STARTUP-ADDR  ES S-EXAMPLE", null, 0   )));
     }
     }

  pub    lic static void STARTUP_ADDRESS_EXAMPLE() {
    { Object OLD_$MODULE$_     000 = Stella.$  MODULE           $  .get() ;
                Object OLD_$CONTEXT$_000 = Stella.$CONTEXT$.get(    );

      try {
           Native.setSpecial(Stella.$ MODULE$,      Stella.getStellaModule("/S          TELLA/XML-OBJECTS/ADDRESS-EXAMPLE", Stella   .$STARTUP_TIME_PHAS   E$ > 1));
        Native.setSpecial(Stella.$CONTEXT$, ((Modu   le)(Stella.$MODULE$.g  et())));
               if (Stella.currentStartupTimePhaseP(2)) {
           _StartupAddressExample.HELP_STARTUP_ADDRESS_EXAMPLE1(); 
        }
        if (Stella.currentStartupTimePhaseP(5)) {
          { Stella_Class renamed_Class =    Stella.defineClassFromStringifiedSource("getAddressFr       omName", "( DEFCLASS getAddressFromN    ame (XMLObject) :PUBLIC-SLOTS ((nameElement :TYPE nameToLookup)))");

            renamed_Class.classCon   structorCode = Na        tive.find_java_method("edu.isi.webtool    s.examples.a  ddressexample.getAddressF     romName   ", "new_getAddressFromName", ne  w java.lang.       Class [] {});
                 renamed_Class.classSlotAccessorCo    de = Native.find_java_method("edu.isi.webtools.examples.addressexample.ge               tAdd   ressFromName", "access_g       etAddr  essFromName_  Slot_V     alue", new java. lang.Class [] {Native.find_j   ava_class("edu.isi.webtools.ex     amples.a  ddressexample.getAddressFromName"),      Native.find_java_class("e         du.isi.stella.Symbol"), Native.find_java_cla    ss("ed u.isi.st ella.Stella_Object"), java.lang.Boolean.TYP  E});      
           }
          { Stella_  Cl        ass renamed_Cla     ss = Stella.defin  eClassFromStringifiedSource("return", "  (DEFC LASS return (XMLObject) :  PUBLIC-SLOTS ((str      eetNum :TYPE stree tNum) (streetName      :TYPE streetName) (city :TYPE city) (state :TYPE state) (zip :   TY   PE zip) (phone :TYPE phoneNumber ) (/STELLA/XML-OBJECTS/  XSI/type    :TYPE STRING :INITIALLY \"ADDRESS-DEMO:address\")))")  ;

            renamed_Class.classConstructorCode = Native.find_jav  a_meth   od("edu.isi.webtools.examples.addressexample.S  tella_return", "new_return", n    ew      java.lang.Class [] {});
            rena       m    ed_Class.c        lassSlotAccessorCode = Native.find_java_method("edu.isi.webtools.     examples.addressexample   .Stella_return", "access_return_     Slot    _Value", new jav    a.lang.Class     [   ] {Na    tive.find_ja      va_class("edu.isi.webtools.examples.addressexample.Stella_return"), N   ative.find_java_class("edu.i    si  .st     ella.Symbol"), Native.find_java_class("ed    u.isi.stella     .Stella_Object "), java.     lan    g.Boolean.TYPE });
          }
          { Stella_Class ren     amed_Clas         s = Ste lla.defineClassFromStringifiedSource("getAd  dressFromNameResponse", "(DEFCLASS getAddressF  rom     N   ameResponse (XMLObject) :PUBLI    C-SLOTS ((return :T YPE /STELL  A/XML-OBJECTS/A  DDRESS-DEMO/return)))");

                 rena   med_Clas s.cla   ssConstru          ctorCode = Native.find_java_m  ethod("edu.isi.   webtools.example    s.addressexample.getAddressFrom    NameResponse",    "new_getAdd   ressFromNameResponse", ne     w java.lang.Class [] {});
                renamed_    Cl     ass.class SlotAccessorCo       de = Na    tive.fin d_java_method("edu.i si.webtools.exa     mpl   es.addr    essexamp    le.getAddressFromN   ame        Resp      onse",  "access_getAddressFrom   NameResp      o  nse_Slot_Value", new j       ava.lang.Cl  ass [] {Native.find_java_class("edu.isi.we  bto     ols.   examples.addressexa  mple.getAddressFromNameRespons e"), Native.find_java_class("edu.isi.stella.Sy    mbol"), Native.find_java_class("edu.isi.stella.Ste   lla_Object"),      java.lang.Boolean.TYPE});
             }
          { Stella_C        lass renamed_Class       =       Stella.defineClassFrom   StringifiedSource("nameToLookup", "(DEFCLASS nameToLookup (XMLObject) :     PUBLIC-SLOTS ((/S    TELLA/XML -OBJECTS/XSI/type :TYPE STRING)))");

            renam      ed_Class.classCon     structorCode = Native.fin    d_java_me  t hod("edu.isi.webtools.   examples.addressexample.nameToLoo kup",   "new_name    ToL   ookup", new java.lang.Cla   ss [] {});
                renam  ed_Class.clas          sSl  otAccess     orCo    de =   Nat  ive.find_java_method("edu.isi.webtools.examples.addressexample.nameToLookup", "acces   s_nameT  oLook     up_Slot_   Valu    e", new j    ava.lan    g.Class [] {N    ative.find_java_class("edu.isi.web     tools.ex      ampl es.address  example.nameToLookup")    , Native .find_java_class("edu.isi.stella.Symbol"), Native.find_java_class("edu.isi.stella.Stella_Object")     ,   java.l  ang.Boolean.   TYPE});
             }
              {       Stella_Class renamed_Clas    s = Stella.defineClassFromStringifiedSour   ce("Add    ress"  , "(DEFCLASS Address (XMLObject) :PUBLIC-SLOTS ((streetNum :TYPE StreetNum)  (s   treetName :TYPE         Str eetName) (city :TYPE City) (s  tate :TYPE S   tate) (zi  p :TYPE Zip) (phone :TYPE PhoneNumber)))")   ;

                  renamed_Cla    ss.classConstru    ctorCode =      Native   .find_java_met    hod("edu.isi.we     btools.exam    ples       .addressexample.Address", "new_Address", new j   ava.   lang.Class [] {});
              renamed_Class.classSlotAcce   ssorCode = Native.fin   d_java_method("edu.isi.webtools.examples.a   ddressexample      .Ad   dress", "ac  cess_Addres    s_Slot_Value" , new j   ava.lang.Class [] {Native.find_java_ class("edu.isi.webt    o   ols.examples.addressexample.Address"),   Native.find  _java_class("ed  u.isi .stella.Symbol"), Native.find_java   _class("edu.isi  .stella.S    tel  la_Obje   ct"), ja    va.lang.Boolean.TYPE});
           }
          { Stella_Cl       ass renamed_Class = Stella.d          efineClassFromStr     ingifiedSo   urce("Stree    tNum", "(DEFCLASS StreetNum (XMLO        bject))");  

                renamed_Class.classConstructorCode = Nat  ive.find_ja    va_method("edu.isi.webtools.exa     mples.addressexample.StreetNum", "new_StreetNum", ne   w java.la    ng.Class [] {});
          }
             { Stella_Class renam      ed_Class = Stell  a.defineClassFromStr ing     ifiedSource("St   reetName",   "(   DEFCLASS   StreetName (XML  Object))");

               renamed_Class.cla       ssConstruc  torCode = N     ative   .f     ind_java_  m    ethod("edu.isi.w ebtools.example       s.addressexam      ple.StreetNa        me",    "new_StreetName"   , new java.    lang.Class [] {});
                 }
              { Stella_Clas     s   ren  amed_Class = Stella.defineClassFr   omStringifiedSour  c   e("City", "(DEFCLASS City (XMLObject))");

               r  enamed_Class.classConstructorCode =  Native.find_ja  va_method   ("edu.isi.webtools.examples.address  example.City",    "new_City",   new java.lang.Class [       ] {});
          }
             { Ste   lla_Class renamed _Class = S tella.defineClassFromStringifiedSource("Sta     te", "(DEFCLASS State (X MLObject))")    ;

               renamed_Class.classConstructorCode =       Native.find_java_method("edu.isi.webtools.examples.addressexample    .State", "ne     w_State", new j     a  va.lang.Cl    ass     [] {});
             }
           { Stella_Cl  ass renamed_Class = Stella.defineClass  FromStringifiedSour ce("Zip", "(DEFCLA    SS Z   ip (XMLO   bjec t))");

                  renamed_Cla   ss.classConstruc    torCode = Native.find_java_method("edu.isi.webtools.examples  .addressexample.Zip", "new_Zip", new java.lang.Class [] {});
          }
              { Stell a _Class renamed_Class =          Stell  a.def   ineC    lassFromStringifiedSource("   PhoneNumber", "(DE FCLASS PhoneNumbe     r (  XMLObject) :PUBLIC-SLOTS ((areaCode :TY     PE   AreaCode) (exchange      :TYPE Exchange) (nu  mber :TYPE  N   umber)))");

            rena    med_Class.classConstructorCode = N ative.find_java_method("       ed    u.isi.we   btools.examples.addressexample.PhoneNumber", "new_PhoneNumber", ne     w java.lang.Class [] {});
               ren          amed_Class .   cl assSlotAccessorCode    =            Native.fin     d_java_method("edu.isi.webtoo   ls.examples.add   ressexample.PhoneNumber", "access_PhoneNumber   _Slot_Value", new java.lang.Class [] {Native.find_java_   c   lass("edu.isi.webtool s.examples.   addressexample.PhoneNumber"),    Native.f  ind_java_c  lass("edu.isi.st    ella.Symbol"), Native.find_java_class("edu.is    i.stella.Stella_Object"), java.l    ang.Boolean.TYPE});
            }
              {   Stella_Class rena med_Class = Stella.defineClassFr  omStringifiedSource("AreaCode", "(DEFCLASS    AreaCode (XMLObjec  t))");

            renamed_Class.classConstruct  o  rCode = Nati     ve.f  ind_java_me   thod("e   du.is i.  webtools.exa      mples.addressex ample.Area   Code",   "new_ AreaCode", new java.lang.Class [] {});
             }
          { St   ella_Class renamed_Class = Stella.defineClassFromString ifiedSource("Exchange   ", "(DE     FCL  ASS Exchange (XMLOb  ject   ))");

            renamed_Class.classConstructorCo      de = Na  tive.f      ind_jav  a_method("edu.isi.webtools.exa mples.address example     .Exchange"     , "new_Exchange", new j     ava.lang.Cl  ass [] {});
          }
          { Stella_Class r  enam    ed_Class = Stella.def  ineClassF   romS         tringif   ie       dSource( "Nu    mber",       "(D EFCLASS N   umber (XMLObject))");

               renamed_Class.classC     onstructorC     ode = Native.find_java_method     ("edu.isi.webtools.examples.addressex  ample.Number", "new_Numb  er", n    ew java.la   ng.Class [] {});
            }
             { Stella_Class renamed_      Cla  ss = Stell  a.defineClassFr omString     ifiedSource("streetNu          m", "(DEFCLASS streetN   um (XMLObject) :P   UBLIC-SLOTS ( (/STELLA/XML-OBJECTS/XSI/type :TYPE S     TRING :INITIALL   Y \"XSD:int\  ")))");

                  renamed_Class.classConstructorCode = N     ative.find_java_method("ed u.isi. webtools.exampl  es.  addresse  xample.s      treetNum         ", "n        e   w_s      treetNum", new java.lang.Cl   ass [] {});
            renamed_Cla      ss.classSlotAccessor   Code = Native.find_java_method("edu.isi.webtools.exampl es.addressexample.str   eetNum", "access_streetNum_Slo      t_V   alue  ", new java.lang. Class [] {Native.find _java_class("edu.is   i.webtools.ex   ample    s.addres   s ex ample. s        treetNu m"), Native.find_java_class("edu.isi.stella.Sy  mbol"), Native.f      ind_java_class("edu.isi.  stella.Stel  la_Object"), java.lang.Boolean.TYPE});
            }
           { Stella_Class renamed_Class = St     ella.defineClassFromStringifi edSource("streetNam    e", "(DE  FCLAS   S streetName (   XMLO    bject) :PUBL    IC-SLOTS ((/STELLA/XML          -OBJECTS/XSI/type :TYPE STRING :INITIALLY \"XSD:string\")))") ;

               renamed_Clas      s.classConstructo  rCode = Native.find_java_method("edu.isi.webtools.examples.addressexample.streetName", "new_streetName", new java.lang .Class [] {});
             r    ena   med_Class.classSlotAccessorCode = Native.find_java_method("edu.isi. webtools.examples.addressexample.streetName", "access_streetName   _Slot_Value"  ,     new java.lang.Class [] {Native.find_java_class("edu.isi.webtools.examples.addressexample.streetName"), Native.find_java_class("edu.isi.st  ella.Symb  ol"), Nativ e   .find_java_class("edu.is  i.stella  .Stella_Object"), java.lang.Boo  lean.TYPE});
             }
             { Stella_Class renamed_C  lass   = Stella.de   fi neClassFromStri    ngifi     ed         Source(    "city", "(D     EFCLASS city (XMLObject) :PUBLIC-SLOTS ((/STELLA/XML-OBJECTS/XSI/type :TYPE ST RING :INITIALLY \"XSD:string\")))")    ;

                    renamed_   Class.classConstructorCode = Nati   ve.find_ja         va_method("edu.isi.webtools.examples.addressexample.city", "new_c  ity", new java.lang.Class [] {});
                renamed_Class.cl assSlotAccessorCo  de        = Nativ e.find_java_method("edu.is i.webtools.examp les.addressexample.city", "access_city_Slot_Value      ", new java       .lang.Class [] {Native.find_java_cl     ass("edu.isi.webtools.examples.addressexample.city"), Nativ   e.find_java_class ("edu.i      si.stella.Symbol"), Native.find_  java_class("edu.isi.stella.Stella_Object"),    java.l  ang.Boolean.TYPE});
                  }
          { Stella_Class renamed_ Class = Stella.defineClassFromStringifiedSo   urce("sta   te",    "(DEFCLASS state (XMLObject)  :PUBLIC-  SLOTS  ((/STELLA/  XML-OBJEC    TS/XSI/type :TYPE STRING :INI  TI ALLY     \"XS           D:stri   ng\")))");

            renamed_C  lass.classConstructorCode = Native.find_java_method("edu.isi.webtools.e   xamples.address      e   xample.sta       te", "  ne   w_state", new java.lang.Class [] {});
            renamed_Cla  ss  .classSlotAccessorCode =       Native.       fi   nd_ja    va_met   hod("edu.isi.webt ools.example  s.addressexample.state", "access_state_Slot_Val  ue", new java.lang.Class [] {Native.find_j ava_class("edu.isi.webtools.exampl   es.addre    ssexampl       e.state"), Native.find_j    ava_clas     s("edu.isi.  stella     .Sy   mbol"), Native.find_java_class("edu.isi.stella.Stel  la_Object"), ja       va.lang.Boolean.TYPE});    
          }
                      { Stella_Class renamed_Class = Stella.defineCla  ssFromStringifiedSo      urce("zip", "(DEFCLASS zip (XML Object) :PUBLIC-SLOTS ((/STELLA/XML-OBJECTS/XSI/type :TYPE STRING :I    NIT  IALLY \"XSD:int\") ))");

               renamed_Class.classConstructorCode = Native.find_java_method("edu.isi.webtools.examples.addressexample.zip", "  new_zip", new        java.lang.Class [] {   });
            renamed_Class.classSlotAccessorCode = Native.find_  java_method("edu.isi.webtools.examp   les.addressexamp      le.zip", "access_zip_Slot_Value", new java.lang.Class [] {Nat       ive.find_java_class("edu.isi.webtools.examples.addresse  xample.zip" ), Native.find_java_class("edu.  isi.stella.Symbol"), Na tive.find_java_cl   ass(    "edu.isi.stella.St    ella_Ob     ject"), java.lang.Boolean.TYPE});
              }
          { Stella    _Class renamed_Class = Stella.defineClassFromStringifiedSou         r   ce("phoneNumber", "(DEFCLASS       ph  oneNumber (XMLObject) :PUBL  IC-SLOTS ((areaCode :TYPE areaCode)     (exchange :T  YPE exchange) (number :TYPE   number) (/STELLA  /XML-OBJECTS/XSI/type :TYPE STRIN   G :INITIALLY \"ADDRESS-DEMO:phone\")))");

            renamed_Class.cla ssC    onst   ructorCode = Native.find_java_method("edu.isi.webtools.examples.a    ddressexample.phoneNumber", "new_ph  on eNumber", new java.lang   .Class [] {});
            renamed_Class.classS      lotAccessorCode = Native.fin d_ja   va_method("edu.is       i.webtoo        ls.examp les.addressexam   ple.phoneNumber", "access_     phon    e Number_Slot_Value",   new java.lang.Class     [] {Native.find_java_class("edu.isi.webtools.exa    mpl      es.addressexample.p         honeNumber"),   Native.find_java_clas       s("edu.i si.stell      a.Symbol"), Na   t   ive.     find_java_class("edu.isi.stella.Stell  a_Object"), java.lang.Boole  an.TYPE});
                     }
          { Ste  lla_Class renamed_Cl ass = Stella.d    efineClassFromStrin    gifiedSource("areaCode", "(DEFC  LASS areaCode (XMLObject) :PUBLIC-SLOTS ((/STELLA/XM   L-OBJEC  TS/XSI/  type :TYPE      STRING  :INITIALLY \"XSD:i   nt\")))"); 

            renamed_Class.classC  onstructorCode     = Native.find_java_method("edu.isi.webtools.ex amples.addressexample.areaCode", "new_ar                     eaCode", new java.lang.Class [] {});
            renamed_Class.classSlotAccessorC    ode = Native.find_j    ava_method("edu.isi.webto  ols.examp  les.addressexample.areaCo de", "access _areaCode_Slot_Value", new java.lang.Class [] {Native.find_java_cla  s     s("edu.isi.we   btools.examples .addressexample.areaCode"), Native.find_java_class("edu.isi.stella.Symbol"),    Native.find_java_c       lass("        edu.i  s   i.st  ella.Stella_Object"), java.lang.Bool    ean.TYPE});
          }
          { Stella_Class renamed_Class = Stella.defi neClassFromSt  ringi      f   iedSource("excha  nge",    "(DEFC   LASS exchange (XML   Object) :PUBLIC-SLOTS ((/STELLA/XML-OBJECTS/XSI/type :TYPE STRING :I NITIALLY \"XSD:strin  g\")))     ");

                     renamed _Class.classCon     structorCode = Native.find_java_method(   "edu.isi.webtools.examples.addressexample.exchange", "new_exchange", new java.lan     g.Class [] {});
              renamed_Class.classSlotAccessorCode   = Native.find_java_method("edu.isi.webtools.examples.     addressex  ample.exchange", "a   ccess_exchange_Sl   ot_Value", new java.lang.Class [] {Native.     find_java_class("edu.isi.w    ebtools.examples.addressexample.exchange"), Native.find_java_class("ed     u.isi.         stella.S     ymbol"), Na       tive.find_ja    va_class("edu.isi.stel  la.Ste  lla_Object"   )    , java.lang.Boole  an.TYPE});
           }
               { Stella_Class renamed_Class = Stella.defineClassFr   omStringifiedS  ource("numb  er", "(DEFCLASS number (XMLObject) :PUBLIC-SLOTS  ((/  STELLA/XML-OBJECTS/XSI/type :TYPE STR  ING :INITIALLY \"XSD:string   \")))");

             renamed_Class.classConstructorCode = Nat ive.find_java    _  method("edu .isi.webtools.examples.addressexample.number", "new_number", new   java.lang.Class [] {});
            renamed_Cl    ass.classSlotAcce     ssorCode =     Native.  find_java_   meth   od("edu.isi.webtools.examples.address    example.numbe    r", "     access_number   _Slot_Value", new java.lang.Class      [] {Native.find_java_class(    "edu.isi.w ebtools.examples.addressexample.num      ber"), N       ative.find_java_class("edu.isi.stella.Symbol"), Native.find_java_      class("edu.isi.stella.Stella_Object"), java.lang.Boolean.TYPE});
          }
        }
        if (Stella.currentStartupTi     mePhaseP(6)) {
          Stella.finalizeClasses();
        }
        if (S tella.    currentStartupTimePhaseP(7)) {
          Stella.defineFunctionObject("getAddressFromName", "(DEFUN (getAddressFromNa   me g     et  AddressFromNameResponse) ((name nameToL  ookup)))", Native.find_java_method("edu.isi.webtools.examples.addressexample.nameToLookup", "getAddressFromName", new java.lang.Class [] {Native.find_java_class("edu .isi.webtools.exa     mp    les.addressexample.na meToLookup")}), null);
          Stella.defineFunctionObject("MAKE -REQUEST-OB    JECT1", "(DEFUN (MAKE-REQU  EST-OBJECT1 getAddressFromName) ())", Native.find_java_method("edu.isi.webtools.examples.addressexample.AddressExample", "MAKE_REQUEST_OBJECT1", new java  .lang  .Class [] {}), null);
          Stella.defineFunctionObject("ADDRESS-TEST1", "(DEFUN ADDRESS-TEST1 ())",        Native.find_j    ava_method("edu.isi.webtoo   ls.examples   .addre  ssexample.AddressExample", "ADDRESS_TEST1", new java.l ang.Class [] {}), null)   ;
                 Stella.defineFunctionObject("ADDRESS-TEST2", "(DEFUN ADDRESS-TEST2 ())", Native.find_java_method("edu.isi.webtools.examples.addressexample.AddressExample", "ADDRESS_TEST2    ", new java.lang.Cl   ass [] {}), null);
          Stella.defineFunctionObject("GET-APACHE-RESPONS E1", "(DEFUN GET-APACHE-RESPONSE1 ())", Nativ   e.find_java_met     hod("ed      u.isi.webtools.examples.addressexample.Add      ressExample", "GET_APACHE_RESPONSE1", new java.lang.Class [] {}), null);
             Stella.defineFunctionObject("GET-APACHE-RESPONSE2", "(DEFUN     GET-APACHE-RESPONSE2 ())", Native.find_java_method("edu.isi.webtools.examples.addressexample.Addr essExa mple", " GET_APACHE_RESPONSE2" , new java.lang.Class [] {}),  null);
          Stella.defineFunctionObject("STARTUP-ADDRESS-EXAMPLE", "(     DEFUN STARTUP-ADDRESS-EXAMPLE () :PUBLIC? TRUE)", Native.find_java_method("edu.isi.webtools.examples.addressexample._StartupAddressExample", "STARTUP_ADDRESS_EXAMPLE",   new java.lang.Class [] {}), null);
          { MethodSlot function = Symbol.lookupFunction(AddressExample.SYM_ADDRESS_EXAMPLE_STARTUP_ADDRESS_EXAMPLE);

            KeyValueLis   t.setDynamicSlotValue(function.dyna micSlots, edu.isi.    we btools.examples.sample.Sample.SYM_STELLA_METHOD_STARTUP_CLASSNAME, StringWrapper.wrapString("_StartupAddressExample    "), Stel  la.NULL_STRING   _WRAPPER);
          }
          }
        if (Stella.currentStartupTimePhaseP(8)) {
          Stella.finalizeSlots();
          Stella.cleanupUnfinalizedClasses        ();
        }
        if (Stella.currentStartupTimePhaseP(9)) {
          Stel     la_Object.inModule(((StringWrapper)(Stella_Object.copyConsTree(StringWrapper.wra     pString("ADDRESS-EXAMPLE")))));
          XmlObjects.$NAMESPACE_PREFIX_URI_TABLE$.insertAt(StringWrapper.wrapString("ADDRESS-EXAMPLE"), StringWrapper.wrapString("urn:AddressFetcher"));
          XmlObjects.$NAMESPACE_URI_PREFIX_TABLE$.insertAt(StringWrapper.wrapString("urn:AddressFetcher"), StringWrapper.wrapString("ADDRESS-EXAMPLE"));
          XmlObjects.$INVISIBLE_NAMESPACES_ON_OUTPUT$ = Cons.cons(StringWrapper.wrapString("ADDRESS-EXAMPLE"), XmlObjects.$INVISIBLE_NAMESPACES_ON_OUTPUT$);
          Stella.defineStellaGlobalVariableFromStringifiedSource("(DEFGLOBAL *ADDRESS-REQUEST* STRING \"<?xml ver  sion='1.0' encoding='UTF-8'?>\n<SOAP-ENV:Envelope xmlns:SOAP-ENV=\\\"http://schemas.xmlsoap.org/soap/envelope/\\\" xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\" xmlns:xsd=\\\"http://www.w3.org/2001/XMLSchema\\\">\n<SOAP-ENV:Body>\n<ns1:getAddressFromName xmlns:ns1=\\\"urn:AddressFetcher\\\" SOAP-ENV:encodingStyle=\\\"http://schemas.xmlsoap.org/soap/encoding/\\\">\n<ns1:nameToLookup xsi:type=\ \\"xsd:string\\\">John B. Good</ns1:nameToLookup>\n</ns1:getAddressFromName>\n</SOAP-ENV:Body>\n</SOAP-ENV:Envelope>\n\")");
        }

      } finally {
        Stella.$CONTEXT$.set(OLD_$CONTEXT$_000);
        Stella.$MODULE$.set(OLD_$MODULE$_000);
      }
    }
  }

}
