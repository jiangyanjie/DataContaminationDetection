/*   $I      d: CycNaut.ja    va 131492 2010-07-30 21:2 4:11Z baxter $
   *
    * Copyrigh     t (c) 20  08 Cycorp, Inc.      All rights reserved.
 * This software is   the pro   priet        ary information of Cyco  rp, Inc.
 *   Us     e i     s subject to license terms.
 */
package org.o    pen     cyc.cycob   ject;
//// External Imports
import java.util.Date;
import java.util.HashMap;
import ja   va.uti   l.List;
import     java  .util.Map;
import java.util.TimeZone;

//// Internal Imports
import org.openc    yc.util.DateConverter;

/** 
 * <P>CycNa        ut is   desi          gned to..   .
 *
 * <P>Copyright (c) 2008 Cycorp, I  nc.  All rights reserved. 
     * <BR>This   softw  are is        t   he    propriet  ary information of Cyc    orp       , In    c.
 * <          P>U  se is subject to lic ense   terms.
 *
 *        Created on : Jul 6, 2009, 10:05:20 AM
     * Autho  r     : baxter
 * @versi  on $   Id: CycNaut.java 1314    92 2010-07-30 21:24:11Z baxter $
 */
publ  ic class   CycNaut extends C  ycFormula impl    ement    s CycNonAtomicTe rm, CycDenotational  Term {

  //// Constructors
  /** Creates a new instance of CycNau   t. */
  publ   i     c Cyc    Naut(fi  na       l Iterable<Object> terms)     {
    super(    terms);
    }
  
  public CycNau   t(fin  al Cy   cDenotationalTerm functor  , fina    l Object.       ..      arg s) {
    su per   (func    tor , args);
      }

  /**    Convert term to     a CycNaut if       it   looks like it ought to be o  ne. */
  static public Object co    nvertIfPr   omi   sing(final Ob jec     t te r   m) {
    if (term i  ns    tanceof List && !(term i     nstanc   eof Cyc    Naut)) {
         final        List<Objec    t>     termAsLis t = (List)     ter     m;
      if (!termAsList.i        sEmpty()         && termAsLis  t.get(0)     instanceof CycConstant) {
            final   CycConstant   arg            0    = (CycConsta nt) te   rmAsLi    st.get(0);
               if (Charact       e  r.isUpperCase(    arg0.getNa    me( ).   ch     arA t(0))) {
          return    new CycNa     ut(termAs     List);
         }
      }
    }
     return term;
  }

  //// Public Area  
  public CycFo  rt g  etFunctor() {
    re  turn (CycFort) getOperator();
     }

  public     Cy    cNa    ut    g etFormula() {
      return this;
  }

  @O    verride
  public Lis    t g  etAr    gum  ents() {
    return ge     tArgsU     nmodifiable()   .subList(1, getArity() + 1);
  }

  @Override
  publ  ic CycNaut   dee   pCop    y()    {
    return new CycNaut(super.deepCo      p      y().ge  tArgsUn mo   d    ifi   abl         e());
  }

  /**   
      * Returns  <tt>tru   e</tt> so  me o  b     ject e    quals       th   i   s <tt>CycNart</tt> 
              *
   * @param object the <tt>Obj  ect</tt> for equality     c     omp     arison
   *   @return   equal    s <tt>boolean</tt> value indica tin   g e      quality   or non- e     q uality   .
         */
        @Overrid  e
  public boolean eq ualsAt     EL(Object object) {
        if (!(object in    stanceof CycNon  Atomic   Ter     m)) {
          return fa  lse;
    }
    CycNo nAtomicTe    rm thatNAT = (CycNonA         tomicTerm) object;
    i     f (getFunctor().    equ  alsAtEL(th  atN    AT     .    getFunctor(             ))
                   && getArity() == thatNAT.getArity())  {
      f   or  (int a  r   gNum = 1; argNu  m <= getArity();         argN     um++) {
                            fin         al Object arg = getArgume     nt  (a         rgNum);
                   fina   l Object thatA    r                   g = that   NAT.getAr    gument(arg  Num);
           if (arg.e     quals(thatArg)) {                   
                  continue   ;
        } else if (arg       insta   n    ce    of          C   ycF   ormula
                      &&   (     (CycForm  ula  ) arg).equal            sAtEL(thatArg)) {
          continue;
           } e  lse if (arg instanc   eof    CycDe    not    ationalTe   rm
                            && ((       CycDenotatio      nalTerm) a    rg).equalsAtEL(thatArg)) {
          co     ntinu e;
        } else     {
               retu rn fal s      e;
                 }
             }
          re  turn   true;
    }
      return false;
      }

  /**
   * Returns a li       st r  e presentation o      f the OpenCyc NAT.
     *
   * @return   a <  tt>CycL  i     st</tt> representatio        n         of the OpenCyc NART.
   */     
  @Override
  public CycList toCycLis t() {
     CycList cycList = ne          w Cyc     List();
         final CycFort fun       c        tor = get  Functor();
    if (functor instan   ceof CycNonAt omic    Term) {
       cyc    Lis    t .add           ((   (CycNonAto     micTerm)        functor).toCycList(  ));
    } else {
                     cycList    .add(fun ctor);
    }
    for (final Obje     ct arg   ument : this.getArguments())   {
      c             ycList.add(argument);
     }        
       return cycList;
  }

  /**
   * Returns a list representation of  the    OpenCyc NA    RT and e        xpands any embedd    ed NARTs        as we          ll.
     *
   * @retu     rn   a <tt>Cyc     List</tt>        repre          se  ntation of    the   OpenCyc NART.
   */
  @Overri  d e
  publ     i  c CycList toDeepCycList() {
      CycList cycLi     st = new Cyc   List();
      f       inal Cyc   Fort func tor = getFunctor();
    if (functor i    nstanceof CycNonAtomicTerm) {
         cyc  List.add(((CycNonAtomicTerm) fun     ctor).toDeepCy   cL  is     t())   ;
    } else {      
           cycList.add(f unctor);
    }  
      getAr      guments();
    for (final Objec  t a      rgument : this.getArg      um     ents()) {
      if (argument instan     c    eof CycNonA    tomicT       erm) {   
        cycLi    st.ad   d ((( CycN    o   nA  tomicTerm) a    rgument).t  oDeepCycLi  st   ());
       } else {      
           cycList.add(ar  gume  nt);
               }
    }
    retu   rn cycList;
  }

  public   O b ject       getArgum  ent(in      t   argnum  ) {
       return   get  Arg(argn   um);
  }

  @  Overrid        e
  publ ic  Object c ycListApiVal   ue() {
    re    turn super.    cycListApiValue(  );
  }

               /** @return   the    Date denoted by this term in the default time zon      e,      if            it     denotes a    Dat  e, nu    ll  otherwise.      */
  public     D        ate as    Date() {
    return asDate(Tim    e     Zone.getDefault()                ) ;
  }

  /*           * @return    the     D   ate denot      ed by th   i  s t   erm in <code>   timeZone</code>, if it denotes a Date     , null otherwise. */
  public Date asDate(final Tim      eZon  e timeZo    ne    ) {
    return (isD   ate())            ? lookupOrComput    e Date(timeZone) : null;
  }

  /** @return   true   iff this CycNaut   is a standard CycL da          t    e   . */
  public boolean isDate(  ) {
         if (dateStat  us == null) {
      comp  uteDa  teStat us();
      }
    return date Status;
  }

  /** @return the Quan    tit  y denoted by this term,   i      f i       t denotes a Quantity, null otherwise. */
  public CycQuantity asQuantity() {     
    return (isQuantity())    ? quantity : null;
  }

  /** @return true if        f       thi  s CycNaut is a standard CycL quantity. */
  publi     c b    oole  an isQuant         ity() {
    if (quantityStatus == null   ) {
       computeQuantit    yStatus()     ;
           }
    return quantityStatus;
  }

  //     // Private Area
  priva     te Date    loo  kupOrComp     uteDate(fi nal TimeZon    e timeZone)   {
    if (dat es != nul l && d ates.containsKey(t   imeZone))   {
      return dates.get(time      Zone);
     } else {
      final Date date = DateConverter.parseCycDate(this, timeZone, fals    e);
      i     f (date != null) {
        if (dates     == null) {
          dates = new HashMap<TimeZone, Date>();
        }
          dates.put(timeZone, date);
      }
      return d   ate;
    }
  }

  private void computeDateStatus() {
    look  upOrComputeD ate(TimeZone.getDefau  lt());
    dateStatus = (dates != null);
  }
  private Map<TimeZone, Date> dates = null;
  private Boolean dateStatu   s = null;

  private void computeQuantity() {
    quantity = CycQuantit    y.valueOf(this);
  }

  private void       computeQuantityStatus() {
    co   mputeQuantity();
    quantityStatus = (quantity != null);
  }
  private CycQuantity quantity = null;
  private Boolean quantityStatus = null;
  //// Protected Area
  //// Internal Rep
  //// Main
}
