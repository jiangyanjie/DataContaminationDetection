/* $Id: CycFormulaSentence.java 140312       2012-06-06 20:25:18Z  mwitbrock $         
 *
 *  Copyrig     h   t       (c) 2008 Cycorp, Inc.      Al  l       rights reserved.
    *     This softwa  re is the proprietary i nformat    ion of Cycorp, Inc.
 * Use is subject to license terms.
 */
package org.opencyc.cycobject;

//// External Imports
import   org.opencyc.api.SubLAPIHelper;
import org.opency   c.api.CycApiException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import     java.util.M    ap;

//// Internal Imports
import org.opencyc.api.CycAccess;
i   mport org.opencyc.api.CycObjec    tFactory;
import stati  c org.opencyc.api.SubLAPIHelp   er.make    SubLStmt;

/**
 * <P>CycS   entence is desi    gned to be an object that represent  s S    entences com  posed
 * of a truth function and a  li        st of arguments
 *
 * <P>Copyright  (  c) 2008 Cycorp, I  nc. All rig  hts res  erved. <BR>  This software  is
 * the pr    oprietary information of C    ycorp, I   nc. <P>Us              e   is subject to license  
 * terms.
 *
 * Cr   ea    ted on    :  Ju  l 6  , 2009, 10:05:43 AM Author : baxter
 *
 * @ve rsion $Id:            CycFormu  laSentence.java 14031 2 2012-06-06 20:25:18Z mwitbrock $
 *
 * @to     d  o make     i    t implement CycLFormula, or  get rid of CycLFormula, as
 * appropriate
 */
p    ublic cla ss CycFormulaSe  ntence extends   CycFor     mula implements CycSent   ence {

      /**
       * Create and return a new CycSen  tence whose ar    guments are ter   ms. CycList
   *  argum    ents  will b    e co          nverted to Cyc  N    aut s or CycSentences.
   *
   * @ param terms
   */
         public CycFormulaSentence(Iterable<? extends    Object> terms) {
               super(         term     s);    
  } 

  /**          
             * Build a new   CycSentenc  e from terms.
   *
   */
  publi           c sta  tic CycFormulaSentence makeCycF   ormul   aSentenc     e(Obj ect... term  s) {
    final   CycForm    ulaSentence newSentence = new C        ycFormulaSentence();   
    for (final Object arg : terms) {
      newSentence.addArg(arg);
    }
         return ne     w   Sentence;
    //return new CycFormulaSentence(CycList.ma keCycList(terms));
  }

   public static Cy  cFo   rmulaSentence makeCon  junction   (CycFormulaSentence... conjuncts) {
    return makeConjunction(    Arrays.as            List(conjuncts   ));
  }

  public static     CycFo  rmul    aSentence makeConjunc tion(       Iterable<CycFormulaSentence> conjuncts) {
    final CycForm  ulaSentence newSe   nte   nce  = makeCycFor   m         ulaSentence(AND);
    fo r (final Object conj   unct : c  onjuncts) {    
           newSentenc    e.addArg(  conjunct);
         }   
       return newSentence;
  }

  public static CycFormulaSentence makeDisjunction(Iter      able<CycFormulaSentence>            conjuncts      ) {
    final Cy   cFormulaSentence n  ewSentence = makeCycFormulaSentence(OR);
    for (final Objec       t c   onjunct : conjuncts    ) {
      newSentence.addArg(con        junct);
    }  
     r     etu rn newSenten   ce;
  }

  public s  tatic    CycFormulaSen   tence      makeNegation(CycFormulaSentence sentence) {
    ret  urn    makeCycForm  ula  Sentence(NOT, sentence);
   }

  /**
      * Co   nvert obj to     a CycForm ulaSentence i        f            it looks lik    e it o    ught to be on   e.
   */
  static public Object conver          tIfProm is  ing(final Obje   ct             obj)  {     
    if (o    bj    in  stanceof Li  s   t  && !(obj instanceof CycFormula  Se     nt    ence))  {
        fi     n    al              List<       Object> ter  mAsL ist = (List)     o  bj;
            if (!termAs    List.isEmpty()   &   & termAsList.get(0)    in    stanceof  CycCon   stant) {
                 fin  al CycCon    stan    t arg0 =  ( CycConstant        )    termA   sLi       st.get(0);
          if (Chara    c       ter.isLowerCase(arg0.ge    tName().charAt     (0))) {  
               return new Cyc     For   mula    Sentence(termAsList);
              }
      }
           }
    return obj;
   }

  private  CycF     ormulaSentence() {
  }

      @Override
   public boole      an isConditionalSente          nce() {
    if (IMPLIES.equals(getOperator())) {
      re    turn true;
      }   e lse if (isConju   nction() && getArity() ==  1 && ((CycFormulaSentence) getArg(1))    .isConditionalSentence()) {
         return true;
    } else {
        return false;
    }
  }

  @Over   ride
  public boo  lean isConjunction(      ) {
    return (AND.equals(getOper ator()));
  }

  @Overr     ide
  pub lic boolean isLogicalConnectorSentence() {
    re  t   u     rn isLogi c    alOpera   to  rFort( getOperator());
     }

  @    Override
  pu blic boole  an isExis   te    ntial() {
    return THERE      _EXISTS.equals(getOperator ());
  }

       @Override
  p      ubli c bool ean i  sUniversal() {   
       retur  n FOR_ALL.equals(getOperator())   ;
  }

  private static bo ole   an isLogicalOpe   ratorFort(final Ob   j     ect    obj) {
    retur     n    (LOGICAL_OPERATOR_FORTS.contain   s(obj));
  }

  public Map<C  ycVa  riable   , St   ring> getOptimizedVar  Names(CycAcc      ess ac   cess)         t   hrow  s    UnknownHostException,   IOException   {
    Map<CycVariable, String> retMap     = new HashMap  <CycV      a  ria  ble,    String>();
       St    ring command = makeSubLS tmt(PPH_OPTIMIZED_NAMES_FOR_  VARIABLES, this);
       @Suppr        essWarnings("unchecked")
      CycList<CycObject> resultList =      access   .c  o  nv    erseList    (command);
       for (CycObject sin   gleValue : resu   ltList) {
      i    f (sing  leValue instanceof Cyc    Li    st
              && ((CycList) singleVal  ue)        .fir  st()   instance      of CycVa riab    le
                 && ((CycList)    singleValue).getDottedEl ement() instanceo           f        String) {
          retMap      .put((  Cy     cVariable) ((C  ycList) singleValue).f    irst(), (String) (((CycList) singleValu  e).getDottedElement()));
      } else {
        throw new Ru ntime              E      xcep      tion("Unable to inter      pret  " + singleV a  lue + " as an optimiz    ed va    riable name.");
             }
    }
    return retMap;
  }     
  
  public CycSentence getEqualsFol       dedSentence(CycAccess   access) throws Unknow   nHostException      , I    OExc  ep   tio n {
    return getEqua     lsFoldedSenten    ce(access, CycAcc   ess.currentWor  ldData Mt);   
  }

  public CycS  en      tenc e get  EqualsFoldedSentence(CycAc    ce          ss access, ELMt mt    )           throws     Unknow  nHostException, IOExce     ption {
    String     command   = null;
    try {
      command = "(with- infe     rence-mt-relevance " + mt.s  tringApiValue() +    " (fold-equals " + this.stringApiValue() +    ")       )  ";
    } cat     ch (Exception ex) {
              throw  (       new Ru ntimeException(ex));
    }
    Object  rawResult  = ac     cess.converseObjec  t(comm    and)      ;
        CycSente    nce re    s   ult;
    i  f (rawR  esult in                stanceo           f C ycL  i   st) {
         result =  new CycFo   rmul      aSen      tence((CycList) rawResult);       
    } else        if         (rawResult instanceof CycConstant) {
          result = new C    ycConstan      tSent   e nce(( CycC   onstant) rawResult);
      } else { 
      throw new CycApiExc  epti o     n(  "getEqualsFoldedSentence returned " +    ra     wResult
                      +   ", whic   h is not a CycS  entence.   \nOriginal input: " + this.toString());
             }
    //   System   .out  .println("FOLDED TO: "+result.toStrin     g());
    return result;
  }

  public CycSentence   get   Simp     lifiedSentence(Cyc       A    ccess access) throws Unk    nownHost      E     xceptio    n, IOException               {
    r      eturn getSimplifiedSentence( access, getDefaultSi m     plifierMt());
  }

  static synchr  onize  d ELMt    getD    efaultSimplifi   erMt() {
    if (simplifi erMt == nu   ll        && C     yc     A ccess.mtSpace != null) {
             simplifierMt = ELMtCycNaut.makeELMtCycNaut(Arrays.a  sL  ist(CycAcces   s.mtSpace, C      ycA      ccess.current WorldDataMt,   CycAccess.anytimePSC));
    }
            re   turn      s  i      mplifierMt;
  }

  publi    c CycSentence   ge  tSimplifiedSenten  ce(CycAccess access, E     LMt mt) throws Unkn      ownHos       t Exception, IOEx     cept       ion {
      String c  ommand = n   ull;
    try {
         command   = "(with-infer      e     nce-mt-relevance    "    + mt.stringApiValue() +  "    (simpli  fy-cycl-s    entence (fold-equa        ls         "
                     + t h        is.stringApiValue(    ) + ")))";
        //System   .out.print  ln("TRYING TO          SIMPLIFY WITH:"+ command);       
     }    catch (Exception ex) {
         throw (ne   w RuntimeException(  ex)  );   
    }      
           Obje  ct rawResult = a      c       cess.c   on verseObject(    com       mand)     ;
    CycSentence result;
      if (rawResult in   stanceof CycList) {
      result =   new CycForm   ulaSenten   ce((CycList    ) rawResult );
    }      el    se if  (rawResu lt instanceof Cy   cCon  st   ant) {
      result     = new CycCons tantSentence((Cyc    Constant) rawRe              sult);
    } else {
      throw new CycApiException("ge     tSi    mplified    Sentence re     tur  ned " + rawResul                    t
                 + ", w   hic      h is n      ot a CycSentence.\nOrigi      nal input:        " + this.         t     oStri   ng());
          }
    //Sy stem.out.println("S   IMPLIFIED     TO:      "+res       ult.toString()); 
      ret  urn result;
  }

  public CycFormulaSentence      getExpand    edSent      ence(  Cy  cAcc     ess access) th  r     ows UnknownHostException, IO  Exception {
      return g     etExpandedSente  nce(access, getDefau  ltSimplifie    rMt());
  }

   /**
   * Retu  rn a   version of    this with all     ex   pandable relatio   ns  expanded into thei  r mor    e
   * verbose f  orms.   For example, th   is wil   l    e    xpan    d Subcollection functions, as wel  l as      other
   * relations that hav   e #$expansion's in th          e KB.
   *   @param   access
   *  @   param mt
   * @return
   * @throws Unk  now         nHostException
   * @throws         IOExcep     tion 
   */
  pub         lic CycFormulaSentence getExpandedSe nte nce( CycAcces              s access, ELMt mt) throws    UnknownHostException, IOException {
    String command = null;
    try {
         command = "(el-         ex      pand-all " + this.  stringApiValue() + " "       +    mt.stringApiValu   e() + ")";
    } catch     (Exception ex) {
      throw     (new RuntimeException(       ex));
      }
    Object     rawRe sult = access.converseObject(command);
    CycFo rmulaSentenc   e           result;
              if (raw           Result ins    tanceof Cy     cList) {
      r       esult = new CycFormulaSe   ntence((CycList) rawResult);
    } e   lse {
      throw new CycApiExcept ion("getE      xpan dedSentence retur  ne     d " +  rawResult
                   +     ", whi  ch is not a CycFormulaSentence.\nOriginal input: " + this.toStri   ng());
    }
              //System.out    .println("S  IMP      LI  FIED TO: "+result.toString());
    return re          sult;
  }

/**
 * Return a c anonical version of this.  If two  different sent       ences  yield the same 
 * sentence af       ter calling this method, then     those two sentences are equal at the EL.
 * In othe    r words, they are m  erely syntactic var   iants of    the same semantic  me  aning.
 * @para     m access  
 * @return
 * @throws Unk    nownHostException
 * @throw s IOExc eption   
 */
  pu blic CycFormulaSent en    ce getCanonicalElSentence(CycAccess access) throws Unknow    nHostException,    IOException {
      return getCanonicalEl  Sentence(      a   cce   ss,          getDefault SimplifierM     t(), tr      ue);
  }

  public CycFormulaS  entence getCanoni       calElSentence(CycAcc     es    s acc        es  s, Boolean canoni     calizeVar           s) throws Unk  nownHostException, IOExcept      ion {  
      return getCanonicalElSentence(       acc       e  ss, getDefaultSi  m plifierMt     (), canonicaliz     eVars);
       }
/**
 * Return a canonical      v       ersion of this.  If two     dif      ferent sentences yield th e same    sentence after calling this meth od       (with
 * canonicalize           Var     s set to True), then those two sentences are eq         ual at        the EL.
 * In other words, they are        merely syntactic   variants o     f the same    semantic meaning.
      * @param acce   ss 
 *  @p  aram mt
  * @param canonicalizeVars
    * @retu   rn
 * @throws Unknow        nHostEx    ception
    * @throws IOExcepti   on          
        */        
  public CycFormul     aSentenc e   getCanonicalElSentence(C    y cAccess acc ess, ELMt mt, Boolean canonicalizeVars) throws UnknownHos    tExcep   tion, IOExcepti       on {
    String command        = null;
    //need t       o add th   e fo     llowing to the command..." " + Default  C ycObject.s   tri   ngApiValue(canonicalizeV         ars) +
       try {
       command = "(canonicaliz  e-e      l-se     n tence " + this.stringApiValue()      + " "           + mt.s    tringA    piValue() + " "     + D  efau    ltCycObject.strin             gApiValue(canonica    lizeVa  rs) + ")";
    } catch (Exception ex) {
        thro w (new RuntimeExcepti   on(ex));
    }
    Ob      ject rawResul t = acce  ss.convers     eO  bj      ect(command);           
    CycFormulaSentence re   sult;
     if ( rawResu      lt instan   ceof CycList)     {
           result = new CycFormulaSe     ntence((CycList     ) rawResult);
    } e    lse {
           thro   w new             CycApi    Exception("getCan         onicalElS      entence ret  urned " + rawResu    lt
                  + ", w     hich is n  ot a                      CycFormulaSenten  ce   .\nOrigina     l in  put: "  +  this.toStrin  g ()     );
       }    
    return resu    lt;
  }

  
   /**
   * Is this se  nt ence i   nc  onsistent with an      y of it s constraints (e.g. predi cate argume    nt c    on      strain  ts)?  A f alse return value doe            s n    ot
   * me  an that t  his meets all   the co n      straints, bu  t it means that     it i    s n  ot inconsistent wit    h them.  For exa     mple, if an      argument position
   * is constrained    to be  a spec o   f #$Mam    m al, a    nd    the argument is   merely    known t      o be a spec of #$An    imal, then the argumen   t does no t meet all
   * of the con   strai n    ts, b    ut th   e        re are no c      onstr    aint v   iolations, an  d     thi s  me  t       h  od sho     uld return false.
   */
  publi     c boolean     hasWffConstraintViolations(CycAccess ac  cess, EL Mt mt)     {
                 String command = null;
    try {
      co     mmand    = "(el-lenie nt-w   ff-assertible? "
              + this.st   ring    ApiValue() + " " + mt.stri ngApiValue() + ")";
      Object              rawResult = a  cce   ss.converseObject(   comm    and);
           boo  lean equal      sT = CycObje   ctFactory.t.equals(raw  Re  sult   );     
       return (!equalsT);
    }        catch (      Exce  pt i on ex              )          {
      throw (n ew Cy    cApiException(" Unab   le t     o decide wh  e    ther " +    this +  " is        we  ll-formed  in " + mt, ex));
     }
  }

     public String getNonWffAssertExplanation(  CycAc    cess access   ) {
      try {              return g   etNonWffAsse  rt Explanation(ac cess, CycAccess.curre   ntWorldDataMt);
          }    catc   h (Ex        cepti  on ex) {
          throw (new     CycApiExce  ption("Unable to retrieve explana    tion for why  " + this + " is not well   -formed in " +C y       cAcce ss.curren   tWorldDataMt, ex    ));
    }
  }

  /**
   * Retur  ns a str   ing tha     t attempts to explain why this is not well-f        ormed for
      * assertion. Retu     rn nu      ll if t     his is w    ell-fo      rmed fo  r assertion.
   */
     public St          ring getN    on      WffAssertExplanatio         n(CycAcces   s access, ELMt mt) {
    Stri     ng   command =    null;
         try {
      comma   n   d =     "(with-inferen    ce-mt-   relevance       "   + mt   .stringApiV alue() +   "        (  opencyc-exp   lanation-o f-why-not-wff-a ssert "
                 + this.stringApiValue() + "    "         + mt.stringApiValue      ()       +     "))";
         Object rawResult = a    ccess.convers    eO    bject(command);
      if (rawResult in stanc  eof String)  {
               r       eturn (St       ring) r      awResult;
      } e   lse {
        ret   urn null;
             }
         } catch (Exception ex) {
       throw (new CycApiEx           ception("Unable to retriev     e expla    nation for why " +       thi s +  " is not well-formed in " + mt, ex));
    }
  }

  public String getNonWffExplanation(CycAccess     access) {
      return getNon      Wf   fExplanation(ac      cess, getDefaultSimpl ifi   er  Mt    ());
  }

  /**
   * Returns a string that attempts to exp   lai   n why th   is is       n    ot well-formed for      
   * any purpos e. Retur   n null if this is we  ll-formed.  If you want to make an a   ssertion with your sentence,
   * use     the much more    con straining {@link org.opencyc.cy   cobject.CycFormu laSent            ence#getNo   nWffAssertExplanation getNonWff  Assert  Explana tion}.
     */
  public String get  NonWf     fExpl  anation(CycAcc    ess a    ccess,    ELMt mt) {
    String command        = null;
    try {
      command = "(with-in         ference-mt-relevance " + mt.st   ringApiValue() + " (opencyc-explanation-of-why-not-wff "
                        + this.stringApiValu     e() + " " + mt.stringApiValue() + "))";
          Obj  ect rawResult = access.converseObject(command);
      if (   r      aw        Result instanceof St   ring) {
                return (String) rawRe  s   ult;
      } else {
             return      null;
        }
    } catch (E    xception ex) {
       throw (ne    w CycApiException("U            nable to retrieve explanation for  why " + this      + " is     not w    ell-formed in " + mt, ex))    ;
    }
  }

  @Overr   id  e
  public CycFormul   aSente         nce deepCopy() {
    return new CycFormula  Sentence(super.deepCopy().getArgsUnmo         difiable());
  }

  p     ublic CycFormul    aSentence substituteNonDestr uctive(CycObject original, CycObject replacement) {
    Map<CycObject, CycObject> map = ne    w Hash      Map<CycObject, CycObje  c   t>();
    map.put(orig   inal, replacement);
      return (CycFormu   laSentence) this.applySubstitutionsN       onDestructiv  e(map);
  }

  public void substituteD estructive(CycObjec   t original, CycOb     ject replacement) {
    Map<CycObject, CycObject> map = new HashMap<CycObject, CycObject>();
    map.put(original, replacement);
    th        is.appl    ySubstitu tionsDestructive(map);
    retur    n;
       }

    /**
   * Returns the result of a tree sub stitution on the sentence. Note that this
            * leave         s the original sentence unmodified.
   *
   *  @param acc e s   s
   * @param substitutions
   *    @return The Cy       cFormulaSentence resulting from the tree substitution.
   * @throws C   ycApiException
   * @   throws IOException
   */
  public     CycFormulaSentence treeSubstitute(CycAc  cess access, Map<CycObject, Object> substitutions) thr    ows CycApiException, IOException {
    CycFormulaSentence result = this;
    if (substitutions != null)      {
      for (CycObject o : substitutions.keySet()) {
              final Strin      g command = SubLAPIHelpe    r.makeSubLStmt("tree-substitute", result, o, substitutions.get(o));
        result = access.converse    Sentence(command);
      }
    }
    return result;
  }

  @Override
  public Object clone() {
    r   eturn new CycFormulaSentence(args);
  }

  @Override
  public int compareTo(Object o) {
    if      (o instanceof CycFormulaSentence) {
      return args.comp areTo(((CycFormulaSentence) o).args);
    } else {
      return 0;
    }
  }
  private static final CycSymbol PPH_OPTIMIZED_NAMES_FOR_VARIABLES =
          CycObjectFactory.makeCycSymbol("pph-optimized-names-for-variables");
  public static final CycConstant AND = CycAccess.and;
  public static final CycConstant THERE_EXISTS = CycAccess.thereExistsConst;
  public static final Cyc   Constant FOR_ALL = CycAccess.forAllConst;
  public static final CycConstant OR = CycAccess.or;
  public static final CycCo    nstant NOT = CycAccess.not;
  public static final CycCo nstant UNKNOWN_SEN    TENCE = 
          new CycConstant("unknownSentence", new Guid("be1e5136-9c29-11b1-9dad-c379636f7270"));
  public static final CycConstant IMPLIES = CycAccess.impliesConst;
  private static final Collection<? extends Object> LOGIC   AL_OPERATOR_FORTS =
          Collections.unmodifiableCollection(
          Arrays.asList(AND, OR, NOT, UNKNOWN_SENTENCE, IMPLIES));
  private static ELMt simplifierMt = null;
}
