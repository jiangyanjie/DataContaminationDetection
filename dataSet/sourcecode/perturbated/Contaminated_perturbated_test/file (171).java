
package   org.opencyc.parser;

////      Internal Imports
i mport org.opencyc.api.*;
import    org.opencyc.cycobject   .*;

//// Exter     nal Imports
import java.io.*;
imp    ort java.uti      l.*;

     /**
 * <P>CycLPars    erUtil   is desig        ned    to be the main    entr     y point into parsi     ng
 *  CycL expressions.
 *
 * @version $Id: CycLP    arserUtil.java 138070       2 012-01-10 1    9:46:08Z sbrown $            
        * @auth  or     Tony Brusseau
 *
 * <p  >Copyright 2001 Cycorp, Inc., l   icens   e is o    pen source GNU LGPL.
 * <p><a href="http://www.opencyc.org/li  cense.txt">the license</a>
 * <p><a href=  "ht    tp://www.opencyc.org">www.ope  ncyc.org</a>
 * <p><a href    ="h    ttp://www.sour  cefo        rge.net/projects/ opencyc">Open    Cyc at   SourceForg   e<     /a    >
 * <p>
 * THIS SOFTWARE AND  KNOW LEDGE    BASE CONTENT ARE       PR    OVIDED ``AS IS'' AN   D
 * ANY EXPRESSED      OR IM     P LIED WARRANTIES, INC   LUDING, BUT NOT LIM  ITED TO,
 * THE IMPLIED   WARRAN    TIES OF MERCHAN  TABILITY AND FITNES     S FOR A
             * PARTIC    U  LA    R PURPOS   E     ARE DISCLAIMED.  IN NO         EVENT SH  ALL       THE OPENCYC
 * ORGANIZATION OR ITS CONTRIBU TORS BE LIABL       E FOR ANY  DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL    ,    EXEMPL   ARY, OR CONSEQUENTIAL  DAMAGES
 * (INCLUDING, BUT NOT L IMITE    D            TO, PRO      CUREMENT OF SUBSTITUTE GOO      DS OR
 * SERVICES; LOSS OF USE, DATA   , OR PROFITS; O      R BUSINESS INTE RRUPTION)
 * H      OWEVE   R CAUSED AND ON ANY THEORY OF LIABILITY,  W    HETHER      IN CONTRACT,   
 *    ST     RI CT LIABILITY, OR TOR  T (INCLUDIN   G NEGLIGENCE  O     R   OTHERWISE)
 * ARISING IN ANY WAY OUT O    F      THE       U   SE OF THIS     SOFTWARE AND KNOW   LEDGE
 * BASE CONTENT, EVEN  IF      ADVISED OF THE POS SI   BILITY OF SUCH  DAMA   GE.
 *   /
      public class CycLParserUtil {
  
    //// Constructors
      
  /** Cre    ates a new i  nsta    nce of CycLParserUtil. */
    priva   te Cyc      LParserU       ti    l() {}
  
  //// Public Area
  
  pub  li  c static Object  p    ars  eCycLTerm(Str   ing toParse,              boolean testForEOF, Cy  cAccess access) 
  throws ParseException, IOException, CycApiExcept   ion,      CycApiServerSideException, 
  InvalidConst    antNameException, InvalidConstantGuidException,    
  UnsupportedV  ocabularyException, TokenMgrError {
    r   eturn parseCycLTerm(new S tringReader(toParse), testForEOF,  access);
  }
  
    public static Objec     t parseCycLT   erm(Reader reader,    b    o      o        lean   testForEOF, CycAccess access) 
  throws ParseException, IOExce   ption, CycApiException, Cyc   ApiServer SideException, 
      InvalidConstan  tNameEx    c    eption,  InvalidConstantGuidEx        ception, 
  Unsupported       Vocabula  ryExcept       ion, TokenMgrError {
    CycLPar ser parser = n   ew CycLPars  er(reader, access);
      return c omplet  eConst  ants(parser.t        erm (testForEOF), a   ccess);
  }
  
  publ   ic static CycList parseCycLTermLi   st(String toParse, boolean testForE     OF, CycAccess access) 
  throws Par  seException, IOExce   p    tion, CycApiException, CycApiServerSideException, 
  InvalidConstantNam  eExceptio      n, InvalidConstant G     ui  dExcepti     on, 
  Unsuppo rtedVocabulary   E  xception   , TokenMgrError {
    return parseCycLTermList(new StringReader(toParse), testForEOF, access  );
  }
  
  public static CycList parseCycLTerm   List(Reader reader, boolean  testForEOF, CycAccess     a     ccess   ) 
  throws ParseException, IOException, CycApiE    xcep   tion, CycApiServerS  ideException, 
  InvalidConstantNameException, InvalidConstantGuid   Exception, 
  Unsuppo      r  tedV  ocabular      yExcep    tion, TokenMgrError {
    C   ycLParser parser = new CycLParser(reader, access);
    return (Cy  cList)completeCon stants(parser.t  ermList(testForEOF), access);
  }
  
  pub    lic static CycF     ormulaSentence parseCycLSent   ence(String toParse,  boo lean testForEOF,  CycAccess access)
     throws   Pa    rseException, IOException, CycApiException, CycApiServerSide Exception,   
  InvalidConstantNameException,   InvalidConst antGuidException, 
  UnsupportedVocabularyException, To        kenMgrErr  or {
    retu rn p  arse  CycLSentence(new StringReader  (toPar  se), testForEOF, access    );
  }
  
  public static CycFormula  Sentence par    seCycLSentenc    e(Re ader reader, boolean testFo   rEOF, Cy   cAccess acces    s)
  throws ParseException, I     OException, CycApiExcept     ion, CycApiServerSideException, 
  Inva  lid  Constant NameException, InvalidConstantGuidException,        
  UnsupportedVocabularyExce  p     tion, TokenMgrError {
    CycLParser   parser = new Cyc       LParser(reader, access);
    return   new    CycForm   ulaSe    ntence((Cy    cList)completeConstants(parser.sentence(test     ForEOF), access));
  }
      
  public static String parseCycLString(String toParse, boolean testForEOF, C     ycAccess access) 
  throws ParseExcep  tion,  IOException, Cy  cApiException, CycApiServerSideExcepti   o   n, 
  TokenMgrE rror {
    return parseCycLStrin   g(new StringReader(toParse), te     s     tForEOF, access);
  }
     
  p  ublic stat  ic St    ring p  ar  s   eCycLString  (R   eader reader,  boolean testFor  EOF, CycAccess access) 
  thr   o      ws Par      seException, IOE      xception, CycA    piEx  ception,      CycApiServerSideException,   
       TokenMgrError{
    CycLParser parser = new CycLPa    rser(reade  r, access);
    return parser.s   tring( testForEOF);
  }
  
  public static Number parseCy      cLNumber(String toParse, b    oolean testForEO      F    , Cyc    Access    access)   
  throws ParseException, IOException, CycApiExceptio  n, CycApiServerSideException, 
  T  ok       enMgrError {
    return parseCycLNumber(new    Str       ingReader(toPar  se), testForEOF, access);
  }
  
  public        static Number parseCycLNumber(Reader reader,  boolean testForEOF, CycAccess access) 
  throws ParseExcep tion, IOException, CycApiExcep  tion, CycApiServer   SideException, 
  TokenMgrError {
         CycLParser parser = new      CycLParser(reader, access);
    ret   urn parser.number(testForEOF);
  }
  
  pub  lic static CycConstant par     se            CycLConsta    nt(Str       ing toParse, boolean testForEOF, Cyc  Access access) 
  throws ParseException, IO     Exception, CycApiExc  eption, CycApiServe rSideException, 
  InvalidConstantNameEx ception,  InvalidCo   nstantGuidException, 
    Unsu   pportedVocabularyException, To   kenMgrError {
    return parse     CycLCo     nstant     (new StringRead   er(toParse), testForEOF, access);
    }
  
  pub     li   c static CycConstant        parseCycLConstant(Re   ad  er r     eader    , boolean tes    tForEOF, CycAcc ess access) 
  throws Parse   Exception  , IOExcept  io  n, CycApiExce   ption, CycApi   ServerSideException, 
      I      nvalidConstantName     Excep    tion, InvalidConstantGuidException   , 
  UnsupportedVoc  abularyE   xception, TokenMg    rError {
    CycLParse     r    pars    er = new CycLParser(reader, access);
    return (CycConstant)completeConstants(p  arser.con    stant(testForEOF), access);
  }
  
  public static CycVariable parseCycLVariable(    String   toParse, boolean testFor  EO    F, CycAccess access) 
  throws ParseExceptio     n, IOExcep     ti   on, CycApiExcepti    on, Cyc  ApiServ    erSideException, 
  Tok enMgrEr     ror {
    return parseCy cLVariable(new St   ringReade  r(   toParse),       testForEOF, access);
  }
  
  public       static CycVa   riable parse     C ycLVariable(Reader reader, boolean tes       tForE    OF   , CycAccess acce   ss         ) 
  throws    ParseException, IOExcep   tion, CycApiException, Cyc      ApiServerSideException   , 
  TokenMgrError {
    CycLPar   ser parser = new CycLParser(reader, acc          e   ss);
    return parse    r.variable     (testForEOF);
  }       
  
  publ ic static Object parseCycLDenotationalTerm(Strin       g toParse, boolean testForEO  F,   CycAcces s access) 
  throws ParseExcepti   on, IOException, CycApiEx   c  eption, CycApiServer  S     ideException, 
  InvalidConstantNameException, InvalidConstantGui  dException, 
             U  nsup          portedVocabularyException, TokenMgrError      {
     return parseCycLDenotationalTerm(n   ew StringReader(toParse), test     ForEOF, acc   ess);
  }
  
  pu  blic static Object parseCy    cLDenotationalTerm(Reader reader, boolean testForEOF, Cy  cAc   ce    ss access) 
  t    hrows ParseException, IOException, CycApiException, CycApiServerSideException, 
  InvalidConstantNameException, InvalidConstantGuidException, 
  UnsupportedVocab ularyE  xception,   T  okenMgrError {
          Cyc       LParser parser = new CycLParser(read er, acces         s)    ;
        retu  rn (  Object)completeCo    nstant     s(pars   e   r.denotationalTerm(testForEOF), a   ccess);   
  }

  public static CycFort  parseCycLFORT(String toParse, boolean testFo       rEOF, Cy cAccess acces   s)
  throws ParseException, IOE    xcept  ion, CycApiException, CycApiServer       SideException     ,
  InvalidConstantN am  e   Exception, InvalidConstantGuidException,
  Unsuppor     t   edVocabu   laryEx   ception, TokenMgrError {
               return    pa        rseCycLFORT(new StringReader(toParse), testForEOF, a  ccess);
        }

  public static CycFort         parseCycLFORT(Reade   r reader, boolean te   s    tForEOF,   CycAccess access)
    throws ParseEx    ceptio  n, IOExcep             tion,   CycApiExce   ption, CycApiServerSideException,
  In   va     lidCo   nstantNameEx   cept   ion, Inva  lidC    onstantGuid    Excep    tion          ,
      U   nsupp  ortedV    ocabularyExce     ption, Tok  enMgrError {  
     Object resul     t = parse     CycLDenotation      alTerm(    reader, t    e    stF    orEOF, acc ess);
    if       (r    esult instanceof CycList) {
             Object result2 = ac cess.getHLCycT     erm(DefaultCycObject.cyclify(resul   t))      ;
          if (!(result2 ins   tanceo          f Cy       cNart))       {
        throw new RuntimeException("U nknown fort: " + re        sult);
              }
          r   eturn (CycNar      t)res    ult2;
    } else if (result insta nceo  f CycConstant)   {
      return (Cy  cC onstant    )result;
    }    else if (result in stance  of Cyc  Nart) {  
      ret  urn (Cyc  Nart)result; 
    }
    throw new R     untimeExcepti   on("    Unable to fi  nd appro    priate FORT.");
  }

  /**   Takes a CycL     formula      re    presented in CycLis    t for     m and replaces all the subcomponents
   * that are  NARTs    in   the KB wi     th Cy   cNart  objects  .    All other CycL objects are ret urned unc   ha    nged.
           * Note, this fun   ct ion calls a non-api l  evel SubL call,      therefore it may   err  or when    called
   * against Op en  Cyc but will    be accessible from Re    searchCyc. It   has t         he advantag    e over "toHL"
   * becuase nartS       ubs     titute   does not do       other sort     s of can  n   oic    al   izations           like re   ordering arguments  
   * and s  uch. Note: thi     s isn't a p     arsing functio         n and pa   ssing      a string  of a formula w   ill just
   *  return the string un  changed. N  ote: NARTs sho  uld be an implementa  tion   detail of the
   * i nf     erence e    ngine, however  , there ar   e quite a few expections   wher   e api method    s
   * behave diffe               rently whether they are used or not.
   **/
     pub               lic static Object       n  art Su    b     stitute(Object cycl    Object, Cyc   Access       access)    
  t hrows IOException {     
    if (!DefaultC  ycObject.isCycLObject(cyclObje     ct)) {
       throw new RuntimeException(De f   aultC                   ycObject.c   yclify(cyclOb    je  ct) +    " is not    a valid Cyc obje   ct.");
           }
    if (!(cyclObject instanceo    f CycObject)) { // @todo need a test       th at sees if    t        he CycO     bject
                                                      // contains any CycLists any fa     st fail if not
            return cyclObject;
    }
    re     turn acc   ess.converseObject("     (nar  t-substi tute    '" + DefaultCy   cObjec     t.cyclifyWithEsca peChars(cycl  Object, true) + ")");
  }

   /** Takes a      CycL f   ormula rep r    esente       d in C   ycList     form and replaces all the subcomponents
   * t     hat are NARTs in the KB with Cyc     Na    rt objects as wel l as doing arguments   reor der  ing     and
     * variab   le renaming and  conversion. All    oth       er Cy      cL objects are returned unchanged.
   *   Note    : this isn't a par   sing function and pas           sing a string of a form    ula w  ill just
   * return the stri       ng u     nc     h     a nged. No  te: NARTs and HL con structs should be an implement     ation
   *       detail of the inference engi ne, however, there are quite a few expections    where api methods
   * behave differently w     hether they are used or not.
   **/  
  public static Object toHL(Object cycl     Object, CycAccess   access)  
      thro  ws IOExcept   ion {
    if (!Def     aultCycOb ject.    isCycLObject(cyclOb   ject)) {
       thro w new        Ru   ntimeException(Def aultCycO           bjec   t.c     ycli  fy(cy  clObject) + "    is not a  valid Cyc ob ject.")   ;
    }
    if ( !(cyclObj    ec    t instanceof     CycObject)) {
      return       cyclObject;
    }
         return access.converse Object("(cano     nicalize-term          '" + De    fa ultCycObject.cyclifyWi    thEscape         C   hars(cyclOb  ject, true) + ")")   ;
  }
     
        public static Object parseCycLNonAtomicDenotationalTerm(String toParse, boolean testForEOF, CycAccess access) 
  throws Pa     r    seException, IOException, CycApiExc      eption, CycApiServerSide   Exception     , 
  Inval      idConsta      ntNameExcept   ion,     InvalidConstan tG   ui    dE   xception, 
  Un     supported     Voc     abulary        Exception,       TokenMg     rError {
    re  turn parseCyc    L     NonAtomicDenotationalTerm(new                StringRead   er(    to     Parse), testForEOF, access);
  }
  
  p u blic static Obj  ect        parseCycLNon              Atomi cDenotationalTe      rm( Reader reader   , boolean testForEOF, C     ycAcce ss ac cess) 
  throws ParseExc  eption , IO   E    xcept ion, CycAp   iException, CycApiServerSideException, 
  Inv   alidConstant     Na      meException, InvalidConstant  GuidException, 
        Un      supportedVocabu  laryException, TokenMgrError {
      CycLPar      ser parser = new Cyc  LParser(reader,   access)     ;
    return       (Object)completeC          ons   tants(parser.   nonAtomicDe    notationalTerm  (testForEOF),         access);
  }

        public static Object c            ompleteConstant   s(Object ob    j, C    ycAccess acces    s) 
    throws I OE   xception,  
             C        ycApiExcept     io   n, 
           CycApiServerSideException, 
           InvalidConst        antNameExcepti  on, 
             Inv ali  d     C   onstan   tGuidExcep tion, 
           Unsuppo    rtedVocabularyException {    
    Lis   t al    lConstants = Defaul     tCycObject.g  etReferencedConstants(obj);
    if ((allConstants == n  ull)      || (allConstant s.size()    =     = 0))      { return o    bj; }   
       Cy     cList incomplete   ConstantsW  i     thNames = null;
          CycList i   ncompleteC    onstantsWithGuids =     null;
    // Fi    nd i           ncomplete constants
    for ( Iterator iter   = allConstants.itera   tor();    iter.hasNext(); ) {
      CycConst     ant    curConst = (CycCons     tant   )iter.   n   ext();
      i f ((cu      rC     onst.name == null) &&   (curConst  .guid == null)) {
        thr    ow new  Illegal   A  rgu mentEx     cept   ion("Can't    deal   with compl etely       bare            constants.") ;
         }
           if   (cur            Const   .nam    e           == null)   {
        if (       incompleteCo nstan  tsWithG    uids =   = n  ull)       {       incompleteCo nst  antsWithGuids = new    CycList(); }   
        incomplet  eCons       tantsWithGuids.add(cu    rConst);
       }
       if ( c  urConst.guid      == null) {
           if         (incompleteConsta ntsWithNames == null)                  { incompleteConsta ntsWith   N    a   m  es = new CycL ist( ); }
        i   ncompleteCo      nstantsWithName         s.add(curConst);
      }
       }
    // Find       invalid co   nstant names fr   om the list of incomplete consta       nts
      List cycCons tants = access.findConstants  ForName     s(  i    nco   mplet    eConstant   sWithNames);
    if ((cyc Con  stants != null) && (inco m     pleteConsta         ntsWithNames != null)) {
                 InvalidConstantNameException icne =     n   ull;   
         for     (Iterator iter = cyc Constant      s.it erator(), 
              ol dIter = incompleteConstants  With  Names.iterator(); iter.hasNext(); ) {     
        Object     c     urConstant = iter.nex      t(   );
             Cy           cC  onstan   t oldConstant    = (Cy    cConstant)oldI   ter.next(    );
               if (!(curC     on sta    nt ins      tanceof Cyc Consta    nt   )) {
           if (ic ne == null) { icne =       ne        w InvalidConstantNa      meException(        ); }
                        icn   e.a    ddInvalidCon        stan  tName(oldCo      ns   tant.getNam  e());
            } else         {
          oldConstant.  setGuid((    (CycConstant)     curC      onstant).      getGuid());
          CycObjectFactory.addCycCon   s       tantCache( oldConstant);
           }
       }
        if (icne != nul l) {            t  hrow i  cne  ; }
           }
    //Find         invlaid  GUIDs from the       list of in  complete cons    tants
        c      yc  Constants      =     access.f  indCo nst     a    ntsFor   Guids(incompleteConstants      WithGuids);
    if ((cycConstants != nu    ll) && (incompleteCo     ns tantsWithGuids     ! =       nu   ll       )) {
      InvalidConstan  tGuidException icge = n  ull;
      for (Iter ator iter =   cycConstan      ts.iterato   r(), 
          oldIter    = incompleteCo nstantsWithGuid   s.it  e ra  t                  or(    ); iter     .h asNext(); ) {
        Object   c  urConstant = iter.ne     xt();
               CycC  onst  ant o      ld    Con    stant     = (CycConstant)oldIter.next();
        if (!   (cu  rConstant       ins         tanceof CycCon  stant)) {
          i    f (ic   ge == null) { icge =         new InvalidCo nstantGuid  Exception(); }
          icge.addInv           alidConstantGuid(oldCo     nst     ant.getGuid());
        }    else   {
          ol       dC    onst   ant.setNa   me(((CycConst ant)cur Constant).getName()); 
            CycObjectFac     tory.addCycConstantCache(oldConstant);
        }
      }
      if (icge != null) { throw icge;   }
    }
       //Find unsuppor  ted   constants
    for ( Iterator iter = allConstants.  itera    tor(); iter.hasNext(); ){
         C  ycConstant curConst = (CycCo   ns  tant)iter. next();
       if (  access.sublQuo  te     FnConst.guid.equals(curCons  t.getGu  id())) {
             th       row     new UnsupportedVocabularyExcep      tion(access.su    blQuoteFnConst);
         }
      if (ac    c      ess.expandSubLFnConst.guid. equ     a      ls(     curC onst.getGuid())) {
              throw new Unsu         pportedVo    cabularyException(access.expandSubLFnCon  st   );
      }
    }
         re    tu rn obj;
  }
  
  //// Protected Area
  
  protected static    void sanityCheck()   {
    try {
      CycAccess access = new CycAccess(T   EST_    C   YC_MACHINE, TE    ST_C  YC_PORT        );
      Objec    t   obj      = parseCycLConsta   nt("Dog", true, access); 
      System.out.println("Go   t result: "   + obj);
      obj = parseCycLConstant("#G\"bd590573-9c29-11b1-9dad-c379636f7270\"", true,         access);
      System.out.println("Got result: " + obj);
      try {
        obj = parseCycLC onstant("D    ogqweqr", true, access);
             System.out.println("Got result: " + o   bj);   
      } catch        (E xception e) { e.printStackTrace(System.out); }
      try {
         obj =    parseCycLCons     tant("#G\"bd590573-9c29-11b1-9dad-c379636f7279\"", true  ,  access);
           System.out.println("Got result: "      + obj);
      } cat ch (Exception e) { e.prin  tStackTrace(System.out); }
        try {
               obj = parseCycLFORT("(FruitFn App  leTree)", true, access);
        System    .out.println("Got FORT: " + obj);
        } catch (Exception e) { e.printStackTrace(System.out)   ; }
      try {
        obj = parseCycLDenotationalTer   m("(FruitFn AppleT    ree)", true, access);
        System.out.println("Got FOR    T: " + obj + " of type: " + obj.getCla ss());
        obj = nartSubstitute(obj,  access);
          System.out.println("Got FORT: " + o    bj  + " of type: " + obj.getClass());
        obj = toHL(obj, access);
                  System.out.println("Got FORT: " + obj + " of type: " + obj.getClass());
        obj = nartSubstitute("\"" , access);
        System. out.println(" Got single double quote string a   s: " + obj);
        obj = toHL("\"", access);
          System.out.println("Got single double quote string as: " + obj);
      } catch (Ex ception e) { e.printStackTrace(System.out); }
    } catch (Exception e   ) {
      e.printStackTrace();
      Syste  m.out.println("F   inished abnormally");
      System.exit(-1);
    }
  }
  
  ///  / Private Area
  
  //    // Internal Reader
  
  private static String TEST_CYC_MACHINE = "localhost";
  
  private static int TEST_CYC_PORT = 3660;
  
  //// Main
  
  p  ublic static void main(String[] args) {
    System.out.println("Starting");
    try {
      sanityCheck();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Finished abnormally");
      System.exit(-1);
    }
    System.out.println("Finished");
    System.exit(0);
  }
  
}
