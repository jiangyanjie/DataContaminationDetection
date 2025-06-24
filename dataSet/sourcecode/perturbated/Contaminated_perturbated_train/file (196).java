  /     * <p>Copyright     2010 Cyc     orp, Inc.,    license is  ope    n sourc    e GNU LGPL.
 * <p><a href="http://www.opencyc.org/license. txt       ">the license</a>
 * <p><a href="http://www.opencyc.org">www.opencyc.org</a>    
 * <p><a href="http://www.sourceforge.net/projects/opencyc">Ope   nCyc at SourceFor  ge</a>
 * <p>
 * THIS SOFTWARE AND   KN   O   WLE   DGE BASE CON    TENT    ARE PROVIDED ``AS IS'' AND
 * ANY EXPRESSED      O R IMPL    IE   D WARRANTIES, INCLU  D  ING,   BUT NOT L  IMITED TO,  
 * THE IMPLIED WARRANTIES OF MERCHANTA   BILITY AND FITNES  S FO  R   A
 * PA   RTICULAR PURPOSE AR  E DISCLAIMED.  IN      NO E    VENT    SHAL     L TH    E OP  ENCYC
                 * ORGANIZATI      ON OR ITS  CONTRIBUTORS BE LIABLE           FOR ANY      DIRECT,
 * INDIRE    CT, I   NC   IDENTAL,      SPECIAL, EXEMPLARY, OR C   ONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT N     OT LIMI   TE   D TO,          PROCUREM  ENT OF     SUBSTITUTE      GOO  DS OR
 * SERVICES; L       OSS  OF USE, DATA, OR P     ROFITS; OR BUSI       NESS INTERRUPTION)
 * HOWEVER     C    AUSED AND ON     ANY THEORY      OF LIABI     LI   TY, WHETHER IN CONTRACT,    
 * STRICT LIAB  IL    IT  Y, OR TORT (INCLUDI     NG NEG         LIGENCE OR OT  HERWISE)
 * ARISI    NG IN ANY WAY OUT OF THE USE OF THIS SO   FTWARE AN     D KNOWLEDGE
 * BASE CON   TENT,    EVEN        IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package or   g.opencyc.     inference   ;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
im    port java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.   NClob;
import java.sql.Ref;
im   port java.sql.ResultSet;
import jav    a         .sql.ResultSetMetaData;
im  port java.s  ql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
impo     rt java.sql.SQLXM   L;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
im    port java.util.List;
import java.util.Map;

//// OpenCyc Imports
import org.ope ncyc.api.CycApiException;
import org.opencyc.cycobject.CycConstant;
import org.opencyc.cycobject.CycDenotationalTerm;
import org.opencyc.cycobject.CycFort;
import org.opencyc.cycob ject.CycList;
import   org.ope   ncyc.cycobject.CycNart;
import or      g.opencyc.cycobje    ct    .Cy  cNaut;
import    org.opencyc.cycobject  .CycObject;
import org.opencyc.cycobjec t.CycSymbol;
imp   ort org.opencyc.cycobject.CycVariable;
impor  t org.opencyc.uti      l.D   ateCo nverte   r;

/**   
 * <P>AbstractResultSet provides a cl     ass for easy access and  manipulation
 * of inference re  sults       .    It tries to closely mim  ic t    he   java.sql.Resul   tSet      clas     s.
 * E  xampl  e:
 *
 * <code><pre>
 *  System.out.println("Sta     rting");
 *  CycAccess access = null        ;
 *     try {
    *    access = new CycAc  ce         s       s("l   ocalho   st", 36       6    0);
 *    ELMt infere  ncePSC = access.makeELMt  ("#$Infe   re   ncePSC");
     *          String que     ry = "(#  $isa ?X #  $Do  g)";
 *    InferenceWork erSynch worker = new Def      ault    In    ference      WorkerSync    h(     que ry,
 *      inferencePSC,     nu      ll,      access, 5  00 000);
   *    AbstractResultSet     rs = wor   ker.executeQuery();
 *     try     {
       *      int indexOfX       = rs.findC  olumn("?X    ");
 *                 while (rs.next()) {
 *          CycOb           ject curDog = rs.  getCy      cObject(index    OfX);
 *        System.out.pr  intln      ("Got dog:            " + curDog. cycl           ify());
 *      }
 *         } f  i  nall      y {
 *        rs.close();
 *    }
 *           } catch        (Exception e) {
 *    e.prin       tStackTrace();
 *  } fin  ally {
 *      if (access ! = nul    l)     {
 *      access.clo  se();
 *    }
 *  }
 *  System.ou t.  println("Finished");
 *  Sys  tem.o          ut    .flush();
 * </pre></code>
 *
 * <p>      Copyrig  ht   2010 Cycorp, Inc     ., license is open source GNU LGPL.
 * <p><a href="ht    tp      :  // www.opencyc.org/license.txt">the lic   ense</a>
 * <p><  a    href="http://w        ww.ope n      cyc     .org">www.o     pencyc.org</a     >
 * <p><a href="http://www.sou       rc    e   forge.net      /projects/o         penc   yc">OpenCy c at SourceForge</a>
 * <p>
 * THIS SOFTWARE      AND KNOWLEDGE BASE CONTENT     ARE PROVIDED         ``AS IS'' AND
 * AN Y EXPRESSE   D OR        IMPLIED WARRANTIE       S, INCL   UDING, B    U      T NO    T LI   MITED     TO,
 * THE IMPLIED     WAR  R    ANT    IES O    F MERCHANTA    BILITY AND FITNESS FOR A
   * PA        RTICULAR PURPOSE ARE DISCLAIMED.    IN NO EVENT SHALL THE O    PENCYC
 * ORGANIZATION   OR ITS CO    NTRI  BUTORS BE LIABLE FOR ANY DI        RECT,
  *       INDIRECT, I    NCIDENTA  L, SPECIAL, EXEMPLARY, OR CO  NSEQUENTI     AL    D           AMA    GES       
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *     SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INT  ERRUPTI  ON)
 * HO    WEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABI LITY,   OR TORT (IN     CLUDING NEGL        IGEN  CE OR OTHERWISE)
 * ARISING I    N ANY WAY  OUT OF THE     USE OF THIS   SOFTWARE AND KNOWLEDGE
 * BA     SE CONTENT  , EVEN IF ADV   ISE     D OF    THE POSSI   BILITY      OF SUC   H  DAMAGE.
 *
 * @a  uth or tbrussea     
 * @date     M  a   r 22, 2010, 11  :55 AM
 * @version $Id:           Abs           t  ractResultS et.java 135677 2011-09-01 0 9:06:49Z    daves $
 */
p     ublic abstract class AbstractResultSet     implements Res ultSet           {

  //// Public Are     a
  /**     
              * Releases this <code>AbstractResult  Set</code> obj e   ct's       server side r  e    s    ources   .    
     * Fai   lure to      close a re   sult set ma     y  leave significa   nt
     * resources hanging   around the server until the        client object i  s closed.
   * <P>
     * Calling    the method <code>close</co  de> on   a   <code>Ab   str     actResultSet</code>
   * obj        ect that is     already            closed is a no-op .
   */
  @Ove    rride
  p            ubl                  ic sy       nchronized void               close() {    
    i    f (  isClosed) {
      ret      urn;
        }
    isClosed = true;
  }

  /**
   * Retrieves whether this  <code>AbstractResultSet</    code> obje  ct h   as       been    closed.
   * A <code>AbstractResultS    et</c    ode> i     s clo      sed if the method clo   se has been
   * ca lled on it.
            *
    * @return true if this <code>AbstractR esul   tSet</code> object is closed;
   *           false if     it is still o pe n  
   */
       @Override      
    public                      boole   an isClosed() {
      retu          rn isC    losed;
  }

  /**
     *  Retrieves whether the          cursor is before the first       row    in
   *     this <code>AbstractResultSe  t    </code> object.
   *
   * @return <cod    e >true</code> if t     h     e cursor is before the first row;
         *     <code>false</code> if th  e cursor is at any             other position or the
   * r       esult set contains n      o row  s
   */
  @O   verride
  publi         c boolean isBeforeFirst()    {
         ensureOpen("isBefo   reFirst"        )     ;      
    return (cu  rsor == -1);
  }

  /**     
   *  Retrieves whether the cursor   is after the l ast   row i  n
       * this <code>A  bstrac tResultSet</c     ode> object.
      * <p/>
   * <strong>Note:  </st   rong> Calling the method <code>i       sLast</code> may be  expens     ive
   * because i  f the inference is         asynchronous, it first needs to wait un  til       th    e inference
   * is complete.
   *
   * @ret     urn <code   >true</code> if the    cursor is after the last     row;
   * <code>false</       code> if the cursor is at any other position or t     he
       * result set contain   s   no rows
     */
  @O     verrid  e
  pub     lic boole   an isAfterLast(  ) {
    ensureOpen("isAfterLast");
    wai    tTillProce ss   ingDone(); 
    retu     rn (cursor == get    CurrentRowCount());
  }

  /*      *
     * Retrieves whether the cursor            is    o  n       the first row of
   * this <       code>AbstractResultS    e   t</code> object.
   *
        * @return <code>true</code> if the c   ursor     is  on t        he first row;     
   * <code>false</code> other          wi s  e
     */
  @Override
  publ  ic bo    olean isFi     rst() {
    ensureOpen("isFirst");
    return (cursor == 0);
    }

  /*   *      
      * Retrieves whether th     e cursor is on    t       he l  as           t ro        w    of
   * this          <code>AbstractResultSet</cod e> object.
     * <p/>
      * <str ong>     Note:</strong> Calli      ng the me th            od <code>isLast</code>   m   ay be expens     ive
   * because if the    inferen    ce i       s async  hronous     , it firs  t needs    to     wait u      ntil the inference
   * is  complete.
   *
            * @return <code>true</co       de>            i   f the cur       sor is     on the last row;
   * <c     ode>fals     e</code> otherwi   se               
   */
   @Override
  public boolea n is          Last() {
    ensureOpe    n("i            sL   ast") ;    
    waitTillProcessingDone();
         return (    cursor = = (getCurrentRowCount() -     1));
   }

  /**
   * Moves th    e cursor t   o th     e front of
            * this <code>AbstractResultSet     </cod   e> object, jus   t before the
   * first row. This method ha s   no eff      ect if    the result s   et contains no rows.
       *
   */
  @Override
            pub   lic void beforeFirst   () {
    ensure        Open(   "       bef        oreFirst");
    cursor = -1;
    curRow  = nul     l              ;
  }
       
  /**
      * Moves the  cursor to the end of
   * th is <code>Abstract    Re   s ultSet</code> object, just after the
   * last row. This method has no effect    if the result          set       cont   ains  no     r   ows   .
     * <p/>
   * <strong>Note:</strong>     Calling the meth  od <code>is  Last  </code     > may be e   x   pensive
    * b    ecaus       e   if the inference is asynchronou     s    , it first needs t     o wait until t   he inference
    * is c       om      plete .
   *    
   */
  @Override
  public v  o  id   afterLas t() {
    ens    ure   O   p   en("a    fterLa     st");
      waitTillProc        essingDone();
          cursor = getCu    rrentRowCount();
      curRow =        null;
  }

  /**
   *           R           etri e       ves the cur   rent row nu   mb     er.     The firs   t row is nu mber      1, the          
   *  second numbe   r 2, a       nd so on.
   * @return the c  ur    ren     t row number;     <code>0</code   > i   f there  is no curre   nt row
   */
  @Ove         rr    ide
  public int getRow() {
           ens      ureOpen   ("getRow");
       //return cursor        ;
    return (cursor <         0) ? 0 : cursor + 1;
     }

  /**
        * Move  s the cursor to j   ust     be   fore the   given row number in    this
   *   <code>AbstractR     esultSet</code>  object.
   *
   * <p    />If the row number i    s positive,  the     cu            rsor moves to     
      * th   e     gi  ven ro   w     nu    mber w     ith respect to the beg innin g of t    he result s   et.
       *    The first r    ow is         row 1,  the second is row 2,     and so  on    .
        *
       * <  p      />If th   e    giv    en row numb   er i                 s negat  i   ve,   the c  ur      sor moves to
   * a   n absolute ro  w positi   on  with  respect to
            * the en   d of the       result set.  For example  , calling     the method
          * <code>absolute(-1)</co  de> positions the
   * cursor on th    e last     row; calling the met    hod <cod    e>absolute(-2)</co      de>
   * moves the cursor to t      he next    -to-l     ast row, and so   on.
   *
    * <p/>An attempt to position the  cursor beyond the    firs          t/last r  ow     in    
                *     the re     sult set    lea    ves    the        cursor before the first  row or       after
           * the last r ow       .
   *
   * <p/><B>Note:</B> C         alling <code>absolute(1)</cod  e>     i  s the same
   * a  s       calling <c      ode>       first()</code>.    Calling   <code   >ab    so   lut   e(-1 )</code>
     *    is th e same as calling <code>last()</cod  e>.
   *
      * @param r    ow the n umber of the ro  w to       whi ch the cursor shoul  d move.
      *           A positive      number indica   tes the row number cou   nting from the
   *           beginning  of the resu         l     t    set; a   negativ e number      indicates the
   *        row nu   mber     co unting from the    end of the r   esu lt set
   * @retur n <code>tr        ue</code> if the cu rsor         is moved to   a posit i    on in   thi   s
           * <code>AbstractResult      Set</c    ode> object   ; <code>false</code>              if the cursor
      * is before the        fi     rst row or   aft       er   t        he
        * last row
   */
  @Over   rid    e
      publi  c boolean absolute(int row) {
    e          nsureOpen("   abs  olute");
          if ((r    ow < 1)   |     | (row   > rs.size()))    {
               waitTil   lP                     roc essingDone();
              if (row <       0) {
              ro w = rs.size()      + row +            1;
                 }     
    }
    if (row < 0) {
      row = 0;
         }
    if (row > rs.size  ()) {
      row       = rs.size() + 1;
      cursor = rs   .size();
      cu  rRow = null;
           ret      urn false    ;    
            }
       cursor = row - 1;
    curRow    = rs.get(cursor);
       return tr  ue;
  }

  /**
   * Moves th     e cursor a relative number of rows, either   posi tive or       nega tive.
   *     At   tempting t     o move   beyond the first/l ast r    ow   in the
     * re   su    lt set   posi      ti       ons  th     e      cursor bef    ore/after the
   * the first/last row. Cal  ling <code>    relat       ive(0      )</c    ode > i  s valid, but does
   * not change the cursor position.
            *
   * <p>Note: Calling the metho      d            <code>relative(1)</co            de>
   * is identical to calli  ng     the   method <code>n  ext()</code> an        d
          * c     alling t   he method <code>r  el ative(-1)</code>    is identical
   * to calling the      method <code   >previo   us(  )<      /code>.
   *
   *    <P/>
   *  <stron    g>Note:</strong> Calling the   meth     od <code>re     lat    ive</code> may be expensive
           * becau     se if the inference is asynchronous, it f     irst needs to wait un  til the inference   
       * i         s complete.
      *
     * @   param row
   * @return <code>true</co  de>            if         the cursor is o    n a row;    
   *           <co     d        e>false</c    ode> otherw         ise
   */
  @Override
       pub      lic boo       lean relative(   int row) {
    ensureOpen("rel       ative");
    r    eturn abso     lu   te(getRow() +      row);
             }

  /**
   * M   ov es      the cur  sor to the     f irst row in
       * this <code>       AbstractResultS    et  </co        de>    o      bject.
   *
   * @re     tu rn <code> tr   ue</code>   if the c       ursor is on a val  id row  ;
   * <code>fal    se</code>   if the           r        e are   no rows in the    result set
   */
  @     O   verride
      public bool    ea    n    first() {       
    ensur    eOpen(                  "fir      st");
    return absolute(1);
  }

  /**
   * Moves the cursor to      the la    st    row in
   * this <code>AbstractResul   t        Set</code> object.
     *
   * <P/>
   *    <strong   >  Note:</strong>            Calling the   method   < c od    e>last</code> may be expensiv e
   * because if the inference is a  synchronous, it  first needs to wait  until th     e      inference
   *  is complete  .
    *
   * @retur  n <cod  e>true </co      de> if the cursor is on a valid row; 
   * <co     de>false</code> if         there are no rows       in  the    result   set
       */
        @Override
  p ubl     ic boolean last()  {
    ensureOpen("last");
    w    ait Til      lProc essingDone();
    return absolute(getC      urrentRowCo unt() - 1);
  }

  /**
   * Moves the c    urso  r to    the pre   viou    s row in this
      * <    code>Abstr     actResu         ltSet< /code> object.
   *<p>
   * When a call to          the <code>previous   </       code> method     returns <code       >fal   se</code>,
   *       the cu     rsor is p ositioned before the firs            t row.  Any invocat      ion of a
   * <code>AbstractResult      Set</code>        method   which  requires a current row  will re     sult    in  a
   * <code>SQLE xception</co   de> b    eing throw   n.
     *<p> 
   * If an input st ream is open for the current row, a call to the me thod
   * <code>previous</code> will implicitly close it    .  A <code>AbstractRe     sultSet</code>  
   *      object's warning change is cleared when     a ne   w row is read  .
   *  <p   >
   *
   * @return <code>tr ue</c ode> if  the cursor is   now posi   t  ioned on    a valid row;
                * <code>false</code   > if      th   e       cursor is  positioned before th    e firs  t row
   *    @si   nce 1.   2
        *  /
        @O  verri   d    e
  public boolean previous() {
    ensureOpen("pre   vious");
    return relat  ive(-1);
      }

  /**
    * Moves t  he cu    rsor forward o  ne row from its current position.
   * A <code>Abs    tractResultSet     </code> cursor is i     nitia      lly positioned
   * before    the first row; the first call to th  e method 
   *  <code>next</ c o de>    makes the first row the current row;    the
          * second call makes   the s   ec  ond          row t     he current ro      w, and so on.
       *
    * @retu   rn <code>true</code> if the new current   ro   w is valid;
   * <code>false</     code> if ther  e a    re       no more r  ows
        */
  @ Override
  pub     lic bool  ean next() {
    ensur   eOpen("next"  );
    return rela       tive(1);
   }

  /**
           * R   etur  ns, as an <code>Obj ec t</code>     , the value at the current row and at the 
   * column identifie     d by <code>col</ code>. Returns    </code>null<code> 
        * if   no value is set for      the c       urrent row an   d given       column.
   *
   * <p/><stro  ng>Note:</strong> Use     the metho     d <code>   int co    lIin     dex = find           Column(col)<code> once
   *  and the versi  on       of t     his method     that takes an integer for     ma   ximum    pe    rformanc   e.
   *
   * @par     am col the name of the variable that repr       esents the column    of interest
   *  @return the va lue, a   s an <cod  e>   Object</code>, at the cu  rrent row and 
      * at the column identified by the <code>col   </code>. Return    s </co  de>null<      cod e >,
      * if no value is set for  the curren   t row and gi       ven column.
   * @exceptio     n     Ille     galArgumen           tException   if <code>col<  /code> is      not   valid
   * @excep   tion ArrayInd   exOutOfBoundsException if   the current curso    r is not   on a valid ro   w
   */
  @Override
  public       Object g                etObject(String    col)
          throws IllegalArgumentException,      Array  IndexOutOfBo  undsException {
    r eturn getObject(f     indColumnStrict(col  ))    ;
  }

     /**
   * Returns, as an <     code>Object</code>, the value at the current row a    nd at   th     e 
   * column identi    fied by  <code>colIndex</code    >. Returns         </code>nu  ll<code> 
   * i f no     value is set for t        he curren   t row and given column.

   * @param colIndex the co  lumn   index of interest (one  -based)
   * @   return the value,  as a  n <code>Object</code>, at the current row       and 
   * at the c  olu  mn id      entified by <code>col  Ind ex   <   /code>. Returns 
   * </c  ode>nul  l<     code>    ,     if no value is s     et for the    curre  nt    row       and given column.
   *    @  exception IllegalArgumentException if <code         >co   lIn        dex </code> is not valid
   * @except  ion ArrayIndexOutOfBoundsException if    the cu   rrent cur    sor is not       o n a   valid ro    w
   */
       @Override
  public       Obj  ect getObje  ct(  int colI          ndex)
            throws Illega   lArg umentException, ArrayIndexOutOfB    oundsException   {
    ensureOpen("getObject");
    if ((colIndex <= 0)   || (colIndex >  getMaxCo  lumns(            )))      {
      throw new IllegalArgume     ntException("Inval   id column in              dex: "       +          colIndex);      
    }
    return             curRow.  get(colIndex - 1);
  }

  /**
   * Returns, as a <code>CycDenotationa    lTerm          </code>,   t   h        e value at the c                 ur  rent r  ow and   at the
     * colum  n identified by        <code>     col<   /code >. Ret u    rns </code>nu  l  l     <c   ode> 
   * if no val        ue  is set   for the c urrent row and given colu   mn.
   *
    * <p/><strong>Note:</strong> Us  e the    method           <     code>int colIindex             = findColumn(                       c    ol)<code>  once
   * and the versi     on of this metho   d that  takes      an integer for maximum perf   ormance.
   *
     * @param col the name of the variable that    represents the    column of i     nterest
   * @   return the valu     e,   as a <code>Cy    cDenot            ation   alTerm<  /c  ode>, at the current row and 
   * at the     c  olumn identified by the     <code>c       ol</code>. Re    turns </c   ode>null<co       de>,
   * if no valu   e       is set for the curre    nt row and given         col     umn.
   *      @exception IllegalArgumentExce    ption if <code        >col<   /code>    is n   ot   valid
   *  @ex   cepti    on ArrayIndexOutOfBou nd      sExceptio  n if the cu  rrent cursor is not on a valid row
   * @     exception ClassCastExce           ption i  f t     he  value is n    ot con   vertib    l    e        to a
   * <code>CycDenotat iona     lTerm</code>
        */
  public   Cy c   DenotationalTerm getDenotationalTerm( String col)
                 throws IllegalArgumentEx ception, A    r rayInd            exOutO fBoundsExcep  tion, ClassCastEx  ception {
    r                etu      rn getDenot    ationalTerm(find   ColumnSt r  ic  t(col));
     }

    /**  
   * Returns, as a <code>C   ycDeno   tationalTerm</cod  e>, the valu      e    at the c          urrent row and at the
   * column  identified by <code    >c    olIndex</c      ode>.         Returns </code>null<code> 
   * if no value is set for the cur rent row and  gi  ven column.

   * @param    colIndex the    column index   of    interest (  one-based)
   * @return the value, as a <code>      CycDenotati  ona   lTerm</  code>,  a     t       the current row and
   * at the column identif  ied by <code>colIndex</c     ode>     . Retur       ns 
   * </code>null<code>, if n      o v alue is set     for the current row and given column.
   * @exception   IllegalAr     gum     entException             if <code>colIndex</code> is not valid
   * @exc                 e    ption ArrayIn   dexO      utOfBou     ndsExcept  ion if the current   cursor is     not on a vali   d  row
             * @exception Cl     assCastExce    ption i  f the      value is not convertibl e t    o a
   * <code>CycDenotationalTerm</code>
           */
  public CycDenotatio nalTe        rm  getDenotationalT  erm(int colInde   x)
                    throws IllegalArgumentE     xception, ArrayIndexOutOfBoundsEx  ceptio   n, ClassCastExcept   ion {
    ensureOpen("getD  enotati      onal     Term");

                 return (CycDe  notationalTe   rm) getObject(colIndex);   
       }

  /**
   * R    e        turns, as a <code>String</code>, the value   at the cur   rent row and at th  e
      * column identified by <code   >col</    code  >. R       eturns </code>null<code>
   * i   f no value i          s     set for  the current row and   given column.
     *
   *    <p/><strong>Note:<  /strong> Use t  he method <code>int colIindex = fi   n  dC    olum n(col)<c ode> once
   * a     nd th e ve    rsion o    f this  method that t akes an       integer for maximum perform   ance.
   *
   * @par   am co  l the name     of the variable that represents the column of interest
        * @r  eturn the value, as a <code>String</code>,    at th   e current       row and   
   * at the column ident   ified             by the <code>co       l</code>. Returns    </c ode>    null<code>,
   * if no value is     set for th e curren  t row an  d given column.
   * @exception          IllegalArgument   Exception if <code       >col</code> is not valid
   * @exception Arra yIndexOutOf  BoundsException i          f the current curs      or is  not o      n a vali  d row
      *  @exception Cl      assCastEx    ception if the valu  e is not convertible  to a
   * <code>String</code>
   */
  @Overr     ide
          publi    c St   ring getS tring(String col)        
                     thro ws IllegalArgum   entE   xception, ArrayInde x   OutOfBoundsException, Cl  assCastException   {
    return      getString(findCo      lumnStrict(col));
  }

  /**
   *            Returns, as a <code>String</code> , the     value at the cu  r          rent  row and at        the
   * column identified by <  code>colIndex</c  ode>. Retur   ns   </code>null<   co    de>
   * if n  o va     lue is se t for the current row and given column.

   * @param colIndex the       column   index              of      int   erest (one   -  based) 
   * @return the  value, as a       <code>String</code>, at the    curre    nt row and
   * at   the column id    entifie   d    by <code>   colIndex   </code>. Returns
   * </code>null<co   de     > , if no value is se   t     f or th     e current row and g   iven column.
   * @exception IllegalA rgumentException if    <code>colIn  dex</code> is   not vali  d
   * @exception ArrayIndexOutOfBoundsException if the current    cursor   is not      on   a valid row
   * @exception ClassCastException if the value is not convertible to a
     *        <code>String<   /cod e>
     */
  @  Ov          erride       
  public Strin          g getString(int colIndex)
                       throws IllegalArgumentException,    ArrayIndexOutOfBoundsExce  pt      ion, ClassCastExcept       ion {
    ensureOpen("ge    tString            ");
    ret urn (String) getObject(colIndex);
  }

  /**
   * Returns    , as a <c ode>long<    /code>      , the  va    lue at     the c urr   ent   r   o w and at the 
   * column id   en  ti fied by            <cod      e>col  </code>. Returns </c   ode>nu    ll<c      ode>
   * if no     value is set for the current r       ow and   given c   olumn.
    *
   * <p/><strong>No  te:</s      trong> Use the meth  od <code>int col    Iin    dex = findColumn(       co l)<code> once
   * an  d th e version of thi  s meth    od       that takes an integer for maximum    per      for     man ce.
              *
   * @p aram col   the nam       e o f the variable t  hat     represents     the column    of interest    
   * @return the   value, a s a <    code>lo     ng</code>,      at the c urrent row and
     * at the column   identi   fied by the <code>col</code>. Returns    </code>null<code>,  
   * if no value is    set for t  he     current row and given column.
   * @exception IllegalArgumentExceptio   n if <code>col</code> is not valid
   * @                ex        ception ArrayIndex   OutOf    BoundsException  if the current cursor is not on a valid row
   *  @exception Cla  s  sCastExc    ept             ion if the value   is not convertible to a
   * <code>long</  code>
   */
  @Override
         publi c long ge    tLon    g(Strin  g col)
             throws IllegalArgumentException, ArrayIndexO    utO  fBoundsExcepti  on,      ClassCastExcepti   on {
    return getLong(findColumn   S  tric  t(col));
  }

   /   **
   * Ret   ur     ns, as a <cod e>long</       code>, t    he value at the current   r  ow    a    nd at the
   * c  olumn identified by <code >colIndex</code>. Returns <   /code>null<code>
        * if no value is set      for the current row  and given column.

       * @p aram    colIndex the column index of interest (one        -bas             ed)
   * @return the value, as a <code>long</code>, at the current row a    nd
   * at the column identified b y <code>c      olInd      ex</code>. Returns
            * </code>nu   ll<     code>, if no value i   s se  t   for the current row and given column.
   * @e   x  ception IllegalArgumentExce          ptio  n if <code    >colIndex</code> is not valid
     * @exc          eption ArrayIndexOutOfBoundsExc    e         ption           i   f     the current cursor is not on a valid row
   * @exception      ClassCastException    if the value is not conver  tible to a     
   * <     code>long</code>
   */
  @Override
  pu    blic long ge tLo   ng(i  nt   colIndex)
               th    rows IllegalAr       gumentException, Array   IndexOu     t      OfBoundsExcep   tion, Cla       ssCastExcep        tion {
    ensureOpen("getLong")  ;
    return ((N     umber) getObject(colIndex))    .longValue();
  }

      /**
           * Returns, as a <code>int</code>, the        v   alue at     the curre  nt row a  nd at th   e
         * co    lumn identifi   ed by <code>c ol</c ode>. R    eturns </code>null<cod   e     >
   * if no value is set for the current row and g    ive  n colum  n.
   *
   * <p/><strong>N      ote:<   /stron g       > Use     the method <c     ode>int colIindex        = findC olumn(col)<code> once
   *    and the ver   sion of  this method that  takes   an    int   eger for maximum p       erforman     ce.
      *
   *   @param     col    t     he name o    f the variable that represents the column      of int    erest
   *  @return th    e value, as a <code>int</cod       e>, at t        he current row and
   * at      the column iden     tified by the <code>co   l</    code>y.     Returns </code>null  <c od e>,
    * if n o value is set for the c  u r    r   ent row     and       given column.
   * @exception      Ill     egalArgumentEx   ception if <code>col</co  de> is not valid
      * @ex       ception ArrayIndexOutO  fBoundsException  if the    c    urren         t cursor  is not on a valid row
   * @e       xception Cl  assCastException i    f the value  is not conve      rtibl       e to    a
   * <code>int</code>
   */
  @Over ri      de
  p  ub     lic int get    Int     (Strin   g col)
          throws      Illeg    alA rgumentExceptio n,    A  rrayIndexOutO  fBoundsException, ClassCastExc      eption {
       retur        n getInt(fin  dColumnStrict(col));
  }

  /**
   * Re  turns   ,  as a <code>int</co         de>, the   value    at the current    ro    w and at the
   * col      umn   id entified     by <code>  colIndex</code>.              Returns </code >null<code>
   * if no value is set for the curren        t row and given   column.

   * @param colIndex t he   column index of interest   (one-based)
   * @return the val  ue, as a  <code>int</code>, at   the current row and
   *     at the column identified by <    cod         e>colIndex</code>. Returns
   * </code>nul  l<    code>, if no value is set      for the current row and g    iven column.
   * @exc    eption Illegal   A    rgumentExc   eption if <code>colIndex</code>     is     not         va        lid
   * @exception ArrayIndexOutOfBou   ndsException if t     he current     cursor is not on a vali         d ro w
       * @except  ion         ClassCastEx cep  tion if the va   lue is not convertible to a
           * <code>int</code>
           */
  @Override
  public int      getInt(int    c olInd e  x)
                        throws         Illeg   alA rgumen    tException, ArrayIn dexOutOfBoundsException, ClassCast  Except   io n {
                   ensureOpen("getInt");
       return ((Number)   getObject(colIndex)).int    Value();
    }

  /**
     * Retur    n  s, a     s a <co          de>double<   /c   o    de>, the value at        the current row a    nd at the
   * co lumn identified by <c   ode>col</code           >. Returns </code>null<code>
   * if no valu          e is set for the current row and given column.     
   *    
       *       <p/>       <stro ng>Note:</st          rong>   Use the method <code>int colIindex = findCo lumn(col)<code> once
   * and the version of t           his metho  d that takes an integer for maximum   perf   ormance.
   *
   * @param col th   e name of the variable that rep r    esents the colum  n of inte    rest
       * @return the value, as a <code>dou               ble</code>, at t   he current row and
   * at the column identified by the    <code>col</code>. Returns    </co de>null<code>,
   * if n     o value is set for the   current row and given column.
   * @e  xception IllegalArgumentException    i              f     <code>col</code> is not valid
   * @except    ion ArrayInd    e    xOutO    fBoun          dsException if the c      urrent cursor   is n   ot on a val id row   
   * @ex   cepti on ClassCastException if the  val   ue is not conv  ertible to a
   *       <code>double</code>
   */
  @Overr   ide
  public double      getDo u ble(String col)
          throw     s IllegalArgumentExce ption, ArrayIndex   OutOfBoundsException, Class  CastException {
    return getDouble(findColumnStrict(col));
  }

  /**
        *  Ret      urns,          as a   <cod e>double</code>,   the v  alu    e at th e    curre    nt row and at the
      * column    i     den     tified by <cod     e>colIndex</code> . Returns <   /code>n    ul    l<code>
   * if    no value is set for t     he current row and given column.

   * @p  aram colIndex  the colum  n ind   ex  of interest (one-based)
   * @re  turn t        he value, as a   <code>double</code   >, at the current r      o  w and
   * at the column identified  by          <code>colInd     ex</co     de>. Returns
   * </code>null< c      ode    >,     i            f no va          lue is set for th              e c    urrent   row and g  iven column.
   * @e    xceptio  n Il             l egalArgument   Exception if <c   ode>col     Index</code> i  s not valid
   * @exception ArrayIndexOutOfBoundsException if the current curs   or is     not on a v     alid   r ow
    * @exception ClassCastException if the value is not convertible to a
   * <code>double</code>
   */
  @Overrid                  e
         public do  uble getDouble(int   colIn  dex)
          t     h  rows Illeg    alArgumentException, ArrayIndexOutOfBounds     Ex         ception, ClassCastEx   ception {
    ensureOpen("ge   tDouble"         );
    r     eturn ((Number) getObject  (colIndex)).doubleValue()     ;
  }

  /**
          * Retu  rns,       as  a <code>float</c ode>, the   value at t   he current row and at    the
       * colum n    ide ntified by <cod  e>col</ code>. Returns </code>n   u    ll<code>
     * if   no value    is set for the current row and gi     ven   column.
   *
   * <p/><strong>Note:</strong      > Use the method <code>i    n      t colIindex  = findColum     n(col)<   code> once
   * and the ve   rsion o        f thi     s meth   od that takes an       intege      r for maximum performance.
        *
   * @  param col the name of t  he variab   le that represe    nts the col  umn of in   terest
             * @r  eturn the v    a lue, as a <cod     e      >f          loat</ co  d      e>, at t he cur       r   en    t row   a   nd
   * at the column identifie   d by the <    code>col</code  >. Re turns </code>null<code>,
   * if no valu   e is set fo        r   the current                 ro   w and given column.
   * @e  xception I     llegalArgumentException if     <code>   col</code> i     s    not valid
   *      @exception ArrayIndexO   utOfBoundsException if      the current cu        rsor is not on a valid row
       * @exception Cl assCastEx  cept   ion if the       value is   not convert  ible to a
   * <code  >   flo   at</code>    
      */
  @Override
  public       f     loat      getF   loat(String col)
          t          hrow   s Illega   l Arg   umentException, ArrayIndexOutOfBou   ndsException,     C  lassCastException {
      return getFloa   t(findColumnStrict(col));
    }

    /**
   * Returns, as a <code>float</code>, the value at the current row           and at the
   *   column ident    if ied by <code>colI                 ndex</code>. Re    turns </code>null    <cod          e>   
   * i f no value is      s      et for the curren   t r      ow and given column.

   * @param colIn     dex the column index of interest (one   -ba  s          ed)
   * @return the value, as a <cod   e>float</code>        , at the current    row and
   * at the column identified b     y <cod   e     >colIndex</code>. Return  s
   * </code>null<code>    , if no   value i     s set for    the                current row and given       column.
   *      @   exception IllegalArgumentEx ception if <code>colIndex<  /c   ode> is not valid
       * @exception ArrayIndexOu   t OfBou   ndsExceptio  n if the cu rren  t cursor i     s   not on a valid row
   * @ex ception C   lassCastExceptio      n if the value is not convertible to a
        * <       code>float</c   ode>
   */
      @Override
       public floa    t getFloat          (int colI ndex )
           throws Illeg  alA     rgumentException, Arr ayIndexOu tOfBoundsException             , Class  CastExcep  tion {
      ensureOp    en("getFloat")  ;
         return ((Number) getOb  ject(col  Index))    .  f   loatValue();      
  }

  /*      *
            * Return s, as a <cod  e>boolean</    code>, the va       lue at t   he curren       t ro      w and at the
   * column identified by <code>col</code>. Re  turn     s <    /code>null<code     >
   * if no value is s  et for the curr   ent row and given column.
         *
      * <     p/      ><        strong>Note:</strong> Use the method <code>int colIi  ndex = find  Column(col)   <code> once
        * and the versi   on of this m  ethod that takes      an integer for maximum performance.
   *
                 * @param col th  e na      me of the variable that represents the co  lumn of interest
   * @ret  urn        the va  lue,  as a <   co        d     e>boolean</code>, at the curre   nt row and
   * at the column i    dentified b  y the <code>c  ol</c  ode>. Returns </code>null<       code>,
    * if no value is set  f   or th       e current     row and given col           umn.
   * @exception IllegalArgume      ntEx  cep  tio  n i   f <code>col</code> is not valid
   * @excep   tion ArrayIn    dexOutOfB oundsException  if   the        current cursor is not on a  valid row
   * @exce ption Class      CastExcept    ion if the value is n             ot con    ve   rtib  le t    o a
   *       <co    de>boolean</cod  e>
                    */
  @Override
  public bo   olean getBo     olean(Stri        ng co l)
          thro    ws IllegalArgumentException, ArrayIndexOutOfB     ounds      Exception, ClassCastException {
          return     getBo      olean(fi      ndColumnStrict(col));
  }

  /**
   * Returns, as a <code>boolean</   code>, the value at the cu   rrent row and at the
   * column identified by <code>colIndex</code>   . Returns </c    ode>null<code>
   * if no value         is set f  or the curr ent row and given column.

   * @param colIndex t       he column index of in      teres  t  (one-based)
         * @return the value, as a     <co de>boolean</co   de>, at the current row and
   * at the column identified b  y     <c   ode>co   lIndex</code>. Return   s
   * </code     >   null<cod  e>, if n  o value is set for   the curre    nt row        and gi   ven co lumn.
   * @exception IllegalArgumentExcept   ion if <cod   e>colIndex</c      ode> is not     valid
   * @exception ArrayIndexOutOfBoundsE      xcept        ion if  the current cursor is not on a     valid row
   * @exc   eption Cl    assCastException if the value is      not   convertible to a
       * <   code>boolean</code>
   */
  publi       c boolean    getBoo     lean(int colIndex)
             throws Ille  ga   lArgumentExcept    ion, ArrayIndexOutOfBoundsEx  ception, ClassCastException     {
       ensureOpen("getBool   ean");
     Obje  ct val = g    etOb    j       ect(colIndex);
    if (!(val  instanceo f CycSymbol   )) {
      re    tu  rn    false;
    }
    return    !((CycS    ymbol) val).getSymbolName().equa   lsIgnoreCase("    nil")  ;  
  }
    
  /    **
   * Returns, as a  <code>CycConstant</code>, the value  at the current row and at the
   *    column identified by <code>col   </code>. Re turns </code>null<code>
   * if no     value is set for the cur   rent row and  given column.
   *
   * <p/><   strong>Note :</strong> Use the method <code>int colIinde  x   = findColumn(col)<code> once
   * and th           e     v  ersion of this  method that     takes an intege     r for maximum   performanc     e.
   *
   *  @param col the name of the variable that repres    ent   s     the column of interest
     * @r      eturn the value, as a <c ode>C         ycConstant<   /code>       , at    the current row and
   * at the column   identified by the <code>col</code>. R      eturn s </code>null<code>,
   * if n   o value is set for the current row a      nd given    column.
   * @exception I  llegalArgumentExce  ption if <code        >col</code> is not valid
                      * @e  xception   A   rrayIndexOutOfB    oundsException if the current cur   sor is not on a val    id row
   * @exception Clas     sCastE    xception if                t   he va lue is not convertible to a
   * <c    ode>CycCo nstant</code>
   *         /
     publ ic CycConstant   getConstant(String  col)
               thr ows Il l    egalArgumentException, ArrayInde     xOutOfB oundsExcept        ion, ClassCastEx    ception {
    return getConstant(findC   olumnStrict   (col)  )   ;
  }

  /           **
   * Return  s, as a <code>  Cyc C o     nstant</   code>, the value at    the current row and at t    he
   * column identified   by <code>colInd          ex</code>. Retur    ns </code>null<code>
   * i f no val ue is set for the     c    urrent row and gi    ven column.

   * @para  m co lIn       dex the column index of         i nterest (on  e-based)
   * @re  turn the value, as a <code>CycConstant</code> , at the current row        and
   * at the column identified by <c  ode >c        olIndex</code>. Returns
   *   <  /cod         e>null<code>,            if no value is set f      o   r t he cu    r   re   nt row and g            iv      en      column.
        * @      except ion Ill egalArgu me      ntException if <co     de>colIndex</code> is not v     alid
      * @exceptio     n Arr     ay       IndexOutO  fBoundsException if the      current        cursor is     not on a     valid  r   ow
      * @ex  ception ClassCastExc          eption if t    he value is not convertible t  o a
   * <code>CycConstant</code>
    */
  pu   blic Cyc     C  onstant getConstan      t   (int colIndex)
               t     hrows Ill  egalA rgume   ntException,    ArrayIndexOutOf        Boun   dsException, ClassCa   stException   {
    ensureOpen("getConsta nt");
    return (CycConstan      t)    getObject(  col In      dex);
  }

        /**
   * Returns, as a <code>Cyc     Obje  ct</   code>, t   he    value at    the    current row and  at       t   he   
   * column identi              fied by <code>col</code>. Re     turns </co   de>null<code>
   * if no value is set for th     e curren t row a        nd gi v en        c    ol    umn.
   *
         * <p/><strong>Note:</strong> Use the me    thod <code           >  int       colIindex = fi  ndColu     mn(col)<co   de> once
   * and the v   ersion of this method that takes an integer for  maximum perform  ance.
        *
   *   @param col the name of the variable that represents the column  of   intere st
   * @r   eturn the valu    e, a  s a <code>CycObject</code>, at the cur  rent row and
   * at the col  umn identified     by the <code>c   o  l</code>. Returns </code>null<code>,
   *  if no va    lue is    set for the current   ro      w and given colu  m    n.
   *  @excep     tion IllegalArgumentE      xc         eption if <code>  col<    /co de> is n      ot v   alid
      * @exceptio   n ArrayIndexOutOfBoundsExc   eption if the current cursor   is not on a vali    d row
   * @exception ClassCastException if the value is not convert     ible     to a
     * <cod   e>CycObject</code>
   */
     publi     c CycObject getC  ycObject(String col)
               throws Ille     galAr   g   umentExcep   tion,     ArrayIndexOutOfBou     ndsExce  ption, ClassCastException {
      return getC        ycObject(findColumnStrict(col));
  }

  /**
   * Returns,      as a <code>   CycO     bje   ct</code>, the value at     the curre nt  row and  at the
   * col   umn identified by <code>colIn  dex</code>. Returns </cod  e>null<c   ode>
   * if no va   lue is  set f or the current row and given c        o    lumn. Note: literal
   * strings and numbers are not CycObjects a nd can p   ossibly  b     e retur   ned by
   *     some inferences. Only c   al       l this m      ethod if   you ar  e sur       e th   at literals
       * are not a vali       d return result for the give  n <code>  colI  nd  ex<  /code> or
   * a      ClassCastException will be thrown when a li    teral is    encountered. 
   * @param colIndex th    e colum   n index of interest (one-b    ased)
   * @retu       rn    the value, a    s a <code>CycObject<   /cod    e>, a   t the current   r  ow and
   * at       t   he column identified by <code>col   In   de   x</code>    . Returns
   * <    /code>nu   ll<cod   e>  , if     no value   is          set for the current row and given column       .
   * @exception    Ille       galA   rgumentException if <code>colIndex<  /code> is      not valid
        *   @exception Ar rayIndexOutOfB  ound           sE   x  ce    ptio   n if the current cursor is not   on a valid row
   * @except   ion C    lass     CastException if    the value         is not convertible to a
   * <cod e>CycObject</code>
   */
  public CycObject   getCyc   O   bject(int colIndex)
             throws   IllegalArgumentExc           eption, ArrayIndex   OutOfBounds   Exception,     Cl  assCastException {
    ensu    reOpe n    ("getCycObject");
         return (CycObject    ) getObject(colIndex);
  }

  /**
        *   Returns, as a <code>CycFort</code    >, the value at the cur   rent row and at the
   * column identi   fied by <code>  col</code>.     Returns < /c   ode>nu    ll<c      o  de     >
   *    if no value is set for the curre   nt row and given co lumn.
   *
           * <p/><strong>Note:</strong> Use the    method <code>int   col     Iindex = findColumn    (col)<code> once
   *   and      the versio    n of th    is method tha       t takes an integer fo  r maxim          um performance     .
       *    
   *    @     param col the nam  e of the       variab   le that represe  nt    s t        he column of    inter  est
      * @return the value, as  a <code>CycFort</code>,            at the current   row and
   * at the colu   mn identified by the <code>col</code>. Returns </    code>       n        ull<code>,
   * i     f no valu    e is set for the cu      rrent    row and   given      colu mn.
       * @e          xce         ption IllegalArgumentEx      ception if <code>co      l</code> is not      valid
   * @exception    A   rray      IndexOu  tOfBoundsEx cepti o  n if the curren     t cu    rsor   is not on a valid row    
   *  @exception ClassCastE    x ception      if the value  is no    t co  nvertible to  a
   *    <code>CycFort</co de> 
   */
   p   ublic Cy     cFort ge   tFort(String    col)      
          throws IllegalArg umentException, Arra    yInde       xOutOfBound  sExcept     ion,    Class     CastException {
    r          eturn get    Fort(findCol    umnStrict(col));   
    }

  /**
   * Returns, as a <c    ode>    Cy    cFort</code>, the value    a  t     the current row and     at the
   * column identi  fied by <code>colIndex</code   >. Returns         </code>null<code>
        *     if no       value is set fo    r the cu    rrent row an      d g   iven col   u       mn.

   *   @par        am c olInde  x t     he column index  of int      erest (one-ba  sed)
   * @return the value, as a <     code>CycFort        <   /code>, at the current row   and
   * at the column ident      ified    by <code>colIndex</code >. Returns
   * </code  >null<c    ode>,     if n  o value is set for the current  r      ow a   nd given c olumn.    
   * @exception Illega   lArgumentExcept  i      on if <code>co    l   Index</code       > is no  t val  id
   * @exception ArrayIn  dexOutOfBoundsExc        eption if           the current c  ur  sor is not on a valid row    
   *   @exception ClassCas t         Exception if the value is no   t con    vertible to a
   * <code>Cyc   F ort</code>      
   */
  public CycFort g   et         Fort(int     colInd       ex)
           throws IllegalArgu     mentExc eption, ArrayInd       exOutOfBoundsEx   ception, ClassCastException {
      ensure  Open    ("getFort")  ;
    return (CycFort) getObjec            t(colIndex);      
  }
       
  /*     *  
   * Return   s , as a <code>Cyc L ist</code>     , the value at the  cur    rent     row and at the
   * c   olu mn identified by <code>col</code>. Returns   </cod    e>        null<code>
   * if no value is set for the cur     rent row       and giv  en column.
      *
   * <   p/><     strong>Not e:</str   ong> Use the method <c  ode>int   c         olIindex = fin     dColu m  n(   col)<code> once
    * an  d the version of      this  method that   ta  kes an          in    tege      r fo r maxim    um pe  rformance.
   *
   * @para      m col the name o   f the variable that rep   res  ents the column of interest
   * @return           the   value    , as a <co    de>CycList</     code   >, at the current row and
         * at the   colu  mn identifie     d      by the <code>c           o  l</code>. Returns </code         >n  ull<code>,
        * if    n   o val  ue is set for the current   r ow a              nd  give    n column.
        * @ex     cept    ion Illega  lA          rgumentExc  eptio    n if <code>co  l</co  de> i      s               not valid
   *       @exception Array  In  dex   O   utOfBo          undsExcept ion     if the curre        n  t       cu  r    sor is not on a va lid       row
   *   @exception ClassCastException if the value is not convertible   to a
   *        <code    >CycList</code     >
   */
  public CycLi   st<O   bject> get  List(String col)   
                   th   rows       IllegalArgumentException, Arra    yIndexOut       OfBoundsException, C   lassCastException {
      retu  rn    getList(f indColumnStrict(col));
  }

      /*  *
   * R   eturns, as a <code>Cyc     List         </c   ode                 >, the value  at the      current row           and a     t t  he
       * column ident   ified by <cod     e>colIndex</c    ode>. Returns </co  de>null<code  >
   * if no     value is       set for the   current row and given co    lumn.

   *     @param colIndex th e column index      of interest (o  n    e-based )
   * @r       eturn the value, as a <code>CycLis    t</code>   , at the current row an   d
   *   at the column ide   ntified   by <code>colIn  d ex  < /code>. Returns
   *  </code>null<code>, if no value is set for the current row and given column.
   * @exception IllegalArgumentException if <code>colI     n dex</code> i    s n    ot valid 
   *     @exception ArrayI        ndexOutOfB oundsEx        cept  ion i     f the current cursor is not on a v  alid row  
   * @exc   eptio       n ClassCastExcep  tion i              f the value is not convertibl    e to a
     * <cod           e>CycList</      code>
   */
  @ Su   ppres sWa    rnings("uncheck     ed")
  public CycList<Object> ge     tList(int colIndex)
               t hrows I        llegalArgumentException,  A    rr     ayIndex OutOfBoundsExce     p   tion, ClassCastExce    ption {
      ensureOpen("   getList");
         return  (   CycList<Object>) g   etObjec    t(col  Index);
  }

  /**
   * R          etu   rns, as a <                c   o    de>CycNart</code>,  the       value at the current row      and at th e
           * col    umn iden    tified by <cod    e>col</code>. Returns </code>       null<co   de>        
    *     if   n     o value is set for t  he    current row and given column.
   *
               * <p/><   strong   >  Note:</st  rong> U   se the method    <code>int     colI         index = findColumn(col)<code> once
   * an  d th     e version of    this meth     od       th    at     takes an inte     ger       for maximum p     erfo        rmance.
    *
    * @param col th   e name o f the va    riable that re    presents the column of int  erest
   *  @r     e   turn the   value,           as a <code>CycNart</code>, at the current   row and
   * at the column      i   dentif  ied by the <  cod  e>  col</code>. Returns </code>null<      code>,
   * i  f no value is set for the curr   ent    row and     given      column.
       * @exception I   llegalArg        umentException if <code>col<   /code> i   s not valid
   *   @except     ion Ar     rayIndexOutOfBound      sException if the curren  t curso    r          is     not on a    va     lid row
   * @exc   eption ClassC  astException if the va  lue is not convertibl    e to a
   * <code>Cyc    Nart</code>    
   */
  public  CycNar        t     getNa    rt(String col)
            throws IllegalArgum   entException, ArrayIn    dexOut     OfBoundsExc eption, ClassCas   tExce ption {
        return getNart  ( fin  dColumnStrict(col));
       }

  /**
   *      Returns, as   a <code>CycNart</code>, the val   ue at t   h    e current row   and at   t  he      
        * column iden       ti    fied by <code>colIndex</cod     e>. Return s </   code>nu     ll<code>
   * if no value is se t for the   c  u    rr      ent ro    w and     given column.

   *   @param colIndex     the c olu       mn  index of i     nterest (one-base   d)
   * @r   eturn the valu   e, as a <code>CycNart</       code>,           at th       e current row and
   * at the column identifie    d by <c od  e>colIndex</code>. Returns
   * </co     de>nu       ll<code   >, if no v   alu       e is set for the c   urrent r  ow           and given column.
   * @e  xc     eption Illegal    ArgumentExceptio   n if <co  d  e>colIndex</cod   e> is not v        alid
   * @e                  xception ArrayIndexOutOfBoundsException if t    he cu     rrent curso  r is not on   a vali  d row
       *    @exception ClassCastExc   ep    t    ion if the va     lue is not c      onvertible t o a
   * <code>CycNart</code>
   */
  pub    lic CycNa  rt ge tNart(int colInd  ex)
            throws Illegal Arg    umentExcep      tion, ArrayI     ndexOutOfBoundsE xception, ClassCastExceptio  n {
    ensureOpen("getNart");
         ret    urn (CycNart) getObje   ct(colIn dex);
  }

                           /**
   * Retur  ns, as a <co d  e>C  ycNa        ut</code>, the value     at the   current r ow and a    t   the
   * col umn identified   by <code>col</code>. Re     turns </co   de>null<code>       
   * i        f no value      is set for the    current row            and give      n colu    mn.
   *   
       * <p/><s     trong>N   ote:</strong>   Us   e the me      thod    <code     >in t colIindex = findColumn(col)  <code> on  ce
   * and the version o   f th i    s met  hod that takes an integer for      m     aximum     performance.
   *
       * @param col the nam   e       of      the variable tha t represents     th  e col   umn of i  n    terest
   *          @return   t         he    value, as     a <code>CycNaut<  /code>, at the cu   rrent row and     
   * at     the    column identified by the <code>col<  /code>. Returns </code>null<co  de>,
        *       if no value is s     et for the current ro w and given colu  mn.
         * @except  io    n IllegalArgumentExcep t       ion if <code>col</code>   is not    vali     d
   * @exception Ar  rayInde  xOutOf  BoundsException if the    curr           ent cursor is not on a valid    row
    * @exceptio    n ClassCastException if the value is not co    nvertible to a
   * <code>CycN    aut</   cod   e>
      */
  publi      c CycNaut  g      etNaut(String col)
          throws I  llegalArgumentException, ArrayIndex   OutOfBoundsException, ClassCastException    {
    return getNaut(findColumnS   tr ict(c      ol));
  }

  /        **
   * Returns , as a <code>    CycNaut</co de>, the val         ue      at the current row and     at the
   * colu  mn id    ent   ified by  <code>colIndex</code>. Returns </co     de>  null<cod    e>      
   * if no value is set for th      e current  row and g          iven column.

     * @    param colI         ndex     t    he co  lumn inde    x of i  nteres    t     (one-based)
   * @return the va  lue, as a <co    de>CycNaut</code>     , at the current row and
      * at th    e colu       mn identified by <code      >colInde       x</cod    e>. Returns
   * </code>null<   code>, if no value      is set for the cu  rrent row and given column.
   * @exception Ille     galAr  gum    en   tE  xception if <code>colIndex</code> is not valid
   * @exceptio    n ArrayIndexOutOfBoundsEx    ception if the current cursor is   not on a valid row
   * @e   xcept   ion ClassCastExce    ption if the value is not convertible to a
   * <code>CycN    aut   </code              >
   */
  publ  ic   CycNaut getNaut(int   colIndex)
          throws IllegalArgumentException, ArrayI  ndexOutOfBounds   Exception, Cla    ssCastException {  
    ensu   r  e     Open    ("get CycNaut");
        return (CycNaut) getObjec          t(colIndex);
  }

     
  /**
   * Returns, as a <code>java.sql.D    a    te</cod         e     >    object , the value a t the current r   ow and at the
   * column identifi    ed    by <code>col</code> which should     be a
      * <cod      e>Cy cVariable</code>    in    the original query. Returns </cod   e>n   ull< code>
   * if no value is set for t       he current row    and given c   olumn.
   *
   * @param colIndex         the column index of inter    est (one-based)
   * @re    t   urn the value,    as a <code >java.sql.Date<    /cod e>, at the curr    ent ro    w and
         * at the column i   de   ntified by the <cod      e>col</code> which is a
   * <code>CycVariable  </cod    e> in  the ori ginal query.   Retur    ns </code>null<c         ode>,
     * if no value is set for the current row and given     colu mn.
       * @note Fai   ls o  n dates that     are not CycNauts using #$YearFn     (i.e. it    will not
   * work on skolemi  zed     dates, or ot  her forms of date  s that don't u  se t   he #$YearFn          vocabu lary).
         *    @exception CycApiException if   called on a clo  sed result set
   * @excepti         on IllegalArgument  Except    ion if <code>col</code> is not valid
   *       @exception Ar   rayInde x    OutOfBoundsE    xception if    t  h    e current cursor is not on a va  l  i  d row
   * @exception C        lassCastE     xception if the value      is not conver      tible to a
   * <co de>jav a.util.Date</c   ode>
   */
  public Dat  e getDate(int colIn dex)
         throws CycApiException, IllegalArg      umentExc    eption,   Ar  rayIndexOutOfBoundsException, Cl assCastExceptio     n {
        Object obj = get Object(   colIndex);
    return ne   w Date(DateConverter.pa rseCycDate(((CycNaut)CycNaut.convertIfPromising(obj))).  getTime());
  }
  
    /   **
   *  Returns, as a <code>jav  a.    sq   l.  Date</code> object, the    v   alue at the curre      nt             row and at the
   * column identified    by <code>col</code> which should be a
   * <code>CycVariable</code  > in the original q  uery. Returns     </code>n   ull       <code>
   * if n o value is set     for t   he curre     nt row and give   n column.
   *
   * @param   col the   name of the va riabl e that represents the column      of interest
   * @ret urn the val  ue, as a <cod e>java.sql.Date</code>      , at the current row    and
   * at     t    he  column ide    n  tified    by the <code>c  o   l</code> w    hich is     a
   * <code>CycV   a riable</code> in the original query. Returns </c  ode>null<code>,     
       * if  no value i          s set          for       the current row an           d g        iven column.
   * @note Fails   on dat  es th at are not Cyc  N    auts using #$YearFn (          i.e. it will not
   * work on skole   mized dates, or other forms of date   s          that    don't use      the #$YearFn vocabulary).
   * @exception  CycApiException if called on a    clos          ed result set
      * @exception IllegalArgumentException if <code>col</co      de> is       not  va    lid
   * @e   xc  epti    o      n ArrayIndexOutOfBoundsException if the curr        ent     cursor is not      on a valid  row     
   *  @exception       ClassCastExce ption if the value is no t convertible to a
   * <code>jav a.util.Date</cod   e>
     */
  @O   ve   rride
          public       Date getD    ate(String columnLa  be l) throws SQLExc   epti   on {
    return getDate(findCo            lumnStri   ct(columnLab       el));
  }

  
  /**
       * Returns the column index for <code>col</code>.
   *
   * @param col the colu  mn name to l     ook up
   * @return    the column ind  ex for the given <code        >col</code> name.  
   * @exception IllegalArgumentE    xce  ption if called with an         i   nvalid   <code>col</code>
     */
  @O    verride
  public int f         in    dColumn(S    tr         ing col) th       ro  w   s I   llegalArgumentExceptio       n {
          ensureOpen("findColum             n");
    int col  Index = columnNames.indexOf(col);
    if (colIn   dex < 0) {
            throw new IllegalArgumentException("Unab              le to find colu  mn: "      + col);
    }
    return colIndex  + 1;
  }

     /**
        *    Returns the m  axim  um nu     mber of c   olumns.
      *
   * @return        th    e maxi   mum number of columns   .
   */
     pub   lic i   nt getMaxColumns() {
           wai   t    TillP  roc    essingDon  e();
            return co l umnNam     es.si   ze();
    }

       /**
   *      Returns          a list    (cl oned) of     column names that   are available.
   *
   *  @r        eturn a list (cloned) of c      olumn nam       es t     hat are available.
        */
  public List<String> getColumnNames    () { 
        List<Str  ing>   results = new Arr   ayList<String>(co    lumnNames.size());
        results.addAll(c olumnNames);
       return   results;
  }

     /**
   * Returns the number o    f rows    currently in the result set.           
   *
   * @ return    the        number of rows  currentl  y in the result set
   */
          public i     nt ge   tC          urren      tRowCount() {
       return rs.si  ze();
           }

         /**
   * Retu   rns a String representation for this object.
   *      
   *   @return     a String represent    ation for this object   .
   */
  @Override
       public S  tring toS    tring() {
    if (hasTruthValue()) {
      return      ""    + getTruthValue();
    }
    StringBuffer buf = n   ew St    ringBuffer("(");
    int count = 0;
    if (rs != null) {
      for (List<O        bject> row : rs) {
         if (count++      > 15) {
              buf.ap  pend    ("...");
          break;
        }   
             if (count > 1) {
          buf.ap pend(", ");
                       }
              buf.append("[row:" + (co         unt         - 1)   + " ");
        int col         = 0;
        for   (Object val : row) {    
            if (col > 15) {
            b   uf.append("...");
            break;
                  }
          if (col > 0) {
                                   buf.app     end(",    ");
             }
          buf.append("{");
                     buf.  append(columnNames.get(col+    +         ))     ;
                       buf.append("->").append(val);
             buf.append("}");
        } 
        buf.append("]    ");
      }
    }
    buf.app      end(")");
    r  etur    n buf.toString();
  }  

  /**
       * Returns whether the query is a  simple tr ut     h query       (no open variables).
   *
   * @retur   n whether the query is a si       mple truth q    uer  y (no open variables).
   */
  public b       oolean hasT   ruthVa    lue() { 
    return (  truthValue != nul  l);
  }   

  /       **
   *    Returns the truth value for th     is query, or <code>null</code>      if the query has open variables.
   *
   * @return the truth value for this query, or <co    de>null</code> if the query has open variables.
   */
      public boolean getTruthValue() {
    if (!hasTruthValue()) {     
      throw new RuntimeExc ep   tion("Attempt to get   th e tr     uth val           ue f   or a result s    et with non-tr    uth value        data."   )     ;
    }
    return truthValue;
  }

  @Override
  public boolean wasNull() throws SQLException {
    throw new Unsu pportedOperationExcepti on("Not supported yet.");
  }

  @Override
  pub      lic    byte getByt        e(int columnIn    dex) throws SQLException {
    throw new U nsupport     edO       perationException("Not supported yet.");
        }

  @Override    
  pu     blic short getShort(int   columnIndex) thro   ws S QLExce  ption {
    throw new Unsup  portedOperationException("Not supported yet.");
    }
    
  @Override
  @SuppressWarnings("  deprecation"  )
  public BigDecimal getBigDecimal(int colum       nIndex,    int s         cal e) throws SQLException {
    th  row new UnsupportedO perationException("Not supported yet."); 
  }    

  @Override
  public byte[] getBytes(int columnIndex) throws S  QLE   xception {
    throw new Unsup portedOperatio   nException("Not suppor       ted yet.     ");
  }


  @Override
  public Time ge   tTime(int columnIndex) throws SQLException {
    t   hrow new UnsupportedOpera   tionExcep    tio  n("N ot supported yet.");
  }
  
  @Ove     r       r   ide
  public Timestamp     getTi  mesta      mp(i nt columnIndex) th rows    SQLExc     ept   ion {
    throw new Unsuppo  rtedOperationException("  Not supported  yet.         ");
  }

  @Override   
  public InputStream ge    tAsciiStream(int colum   nIndex) throws SQLExceptio  n {
    t         hrow new UnsupportedOperationException("Not supported y    et.");
  }

  @Ov    err   ide
     @Suppre        ssWarnings("deprecation      ")
      publ  ic InputStream getUnicodeStr   e am(int columnIndex) throws SQLEx  ception {
    t   hrow new Unsup   portedOperationException("   Not su       pporte   d yet.");
           }

  @Override
  public InputStream getBinaryStr       ea   m(int      columnIndex) throws  SQLEx             ception {
          throw new UnsupportedOper     ati       onException("Not suppor   ted yet.");
  }

  @Ov  erride
  publ  ic   byte    getByte(String   columnLabel) throws SQLEx     ception {
          throw new Unsup    por     tedO        perationException("Not supported yet.");
  }

  @Override
    pu  blic short getShort(Str      ing columnLabel) th     rows SQLE   xception {
    throw new UnsupportedOperationExcept   ion("No  t supported y et.");
  }

  @    Override
  @SuppressWarnings("deprecation")
  public Bi gDecimal getBig Decimal(S   tring     columnLabel, in   t scale) throws SQLExcepti   on {
    thr   ow   new U     nsupportedOperationException("Not sup ported yet.   ");
  } 

  @Override
  public byte[] getBytes(String columnLabel) throws SQLException    {
      throw new UnsupportedOperat   ionException("Not supp orted yet.")   ;
  }

  @Ove   r   r     ide
  publi        c Time       getT  ime(Str  ing columnLabel) throws SQLExceptio   n {
           throw new UnsupportedOperatio     nException("Not supported yet.");
  }

  @Ove     rride
  public Timestam    p getT       imestamp(String  columnLabel) throws SQLException {
    throw       new UnsupportedOperationException("Not supported yet.");
  }

  @Override
      p      ublic InputStream getAsciiStream(String columnLab     el) throws SQLException {
        throw new Uns      upportedOperationException("Not supported   yet       .");
  }

  @Ov  erride
  @Sup  pressWarnin gs("de   precation")
  public InputStream getUnicodeStream(Str   ing columnLabel) throws SQ   LE xception {
     throw new UnsupportedOperationException("Not supported yet.");
         }

         @Override
  public Input   Str     eam getBina     rySt    ream(String columnLabel) throws SQLException {
    throw new UnsupportedOperationException("Not supp   orted yet      .");
  }

  @Override
  public SQLWarning     getWarnings() throws SQ  LException {
          throw new UnsupportedOperationException("Not      supported yet.");   
  }

  @Overrid   e
  public void clearWarnings() throws SQLException {
    throw new UnsupportedOper  ationException("Not supported yet.");
  }

  @Override
  public String getCursorNa    me() thr     ows S  QLExce ption {
    th row ne     w Unsupported Operatio nException("Not supported y e   t.");
  }  

  @Override
  public     ResultSetMetaData            getMetaData()      t    hrows    SQLException     {
    throw new UnsupportedOperationException("Not supported yet.");
  }

            @Override
  public Reader getCharacte    rStream(int c olum      nI   nd    ex) throws SQLException {   
    throw new UnsupportedOperationExceptio n("Not supported yet   .");
  }
 
                 @Override
  public Re ader getCharacterStream(String columnLabe   l) throws SQLException {
    thr      ow new Un  supportedOperationException("Not supported yet.");    
  }

  @Overrid   e
  pub   lic B     igDecimal getBigDecimal(int columnInd e    x) throws SQLExc  ept  ion {
       throw new UnsupportedOperationException("Not s upported yet.");
  }

  @Over  ride
    public BigDecimal getBigDeci        mal(Stri    ng columnLabel) th    rows SQLException {
    thr  ow n ew Unsupporte   dOperation   Exception("   Not supported yet.");
  }

  @Overri    de
  public void setF etchDirection(int direct   ion    ) throws SQLException        {
    throw new Unsupporte      dOperationEx    cept ion("Not supported       y  et.");
       }

  @Override
  pu    blic int   getFetchDirecti    on() throws SQLException {  
     throw new Unsuppo   rtedOperationException("Not supporte   d yet.")   ;
  }

  @Override
  public v  oid setFetchSize(int rows) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet.    ");
  }

  @Override
  public  int g etFetchSiz  e   () throws SQL   Exc      eption   {
    throw new Unsuppor t edOpera             tionException("N ot support        ed yet.");
  }

  @Override
  public int getType(      ) throws SQLExcep    tio      n   {
    throw new  UnsupportedO           perationException            ("Not supported yet.");
       }

  @Override
  pub       lic int getConcurrency() throws SQLException {
    throw new Unsu pported      OperationException("Not su    pported y et.");
  }

  @Override
  public boole    a    n rowUpdated() throws SQLException {
       throw new  UnsupportedOperationException("  Not support ed yet.      ");
  }

  @Override    
  public boolean rowInserted() throws S    QLException {
      throw new Unsupp     ortedOperationException("      Not su pported    yet.");
     }

  @Over  ride
  pu     bl        ic boolean rowDeleted() throws SQLException {
        throw new Uns   upportedOper    ationException(" Not supported yet   .");
   }

      @Override
  p    ubl  ic v       oid upda    teNull    (int col    umnIndex) throws SQL      Exception {
    throw    new UnsupportedOperationExcepti      on(       "Not suppor  te   d yet.");
     }

  @Overrid   e
   public void updateBoolean(int c           olum    nIndex,         boolean x)     thr      ows SQLExce  ption {
    throw new UnsupportedOperationException("Not support ed yet.");
       }

  @Ove  rride
      public void   updateByte(int columnIndex   , byte x) throws SQL Excepti on {
    thro    w new Unsupp  ort  edOperationEx    ception ("    Not supported yet.");
  }

  @Override
  public voi   d updateShort(int co        lumnIn   dex, short x) throws   SQL         Excepti        on {
             t hrow new UnsupportedOperationException("Not suppo     rted yet.");
  }

   @Override
  public      void updateInt(int    c  olum   nIndex, int x) throws SQL   Exception {
    throw        new UnsupportedOperationException("Not  su    pp   orted ye   t.");
  }

  @Override
  public void updateLong(        in t      co      lumnIn dex, long x) throws           SQLException        {
    throw new   U     nsup     portedOper  ati  onEx     cep  tion("Not s  upported yet.") ;
   }

  @Override
    public void updateFloat(int columnIndex, floa  t x)      th           r     ows SQLExcepti  on {     
           th row new Unsupp   ortedOperationException("Not  suppo  rted yet.");
      }

  @Overrid e
  pu   blic   void up    d   ateDouble (int columnIndex, double x) throws SQLE   xception {
                 throw new Unsupp    ortedOp    erationExcept     io   n("Not supported ye  t.");
  }

  @Override
  public void updateBigDecimal    (int columnInd        ex, BigDecima     l x) throws SQLException {
      throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
       public v  oid updateStri ng(int co  lumnIndex, String      x) throws      SQLExcepti   on {
    throw new UnsupportedOperat    ion Exception("Not suppor     ted yet.");
  }

  @Override
  public void updateBytes(int columnIndex, byte[]    x) thr ows SQL                  Exception { 
         throw  new UnsupportedOperationException("Not supported yet.") ;
        }

  @Overri    de
  public void updateDate(int colum    nInde  x, Date    x) throws SQLException     {
    throw new Unsup  portedOperat i      onException("Not     supported yet.");
  }

  @Overr   ide
  public void updateTime(int column Index, Time x) thro   ws SQLException {
    throw new         Unsuppor  t     edO   p    erationException("Not supported yet.");
  }

  @Override
  publ    ic void updateTimestamp(int   columnIndex, Timestamp x) th rows SQLExce                  ption    {
    th    ro         w new UnsupportedOperationException("N     ot supported yet.");
  }

  @Override
  public void updateAsciiStre   am(int columnIndex,     InputStream x  , int length) thro       ws SQL    Exception {
    t   hrow new UnsupportedOperationException(    "Not supported yet.");
  }

      @Overr    ide
  public voi      d u   pdateBin     aryStream(int c   olumnIndex, InputStream x, int length)           thro    ws SQL    Exceptio   n {
    throw     ne   w UnsupportedOper      ationExcept   ion("Not suppo  rted yet.");
  }

  @Override
  public vo   id update    Charac   terStrea     m(int columnIndex, Reader x, int le  ngth) throws SQL Excep tion {
    throw  new UnsupportedOperationException("Not supp orted yet.");
        }

  @Overri  de
  public void updateObject(int columnI ndex, Object x, int scaleOrLength) throws SQLExc   eption     {
    t hrow new Unsu   pportedOperationException("Not supported yet.");
  }

  @Overr   ide
  public void upd ateObject(            int            columnIndex, Object  x) throws SQLException {
         throw new     U      n  supportedOperationEx ception("   N     o t suppo            rted yet.");
  }

     @Override
  public void updateNull(String columnLabel) t   h  r      ows SQLExcep  tion      {
    throw    new U nsupportedOperationException("Not suppo    rted   yet.");
  }
  
         @O   verride
  publ  ic void updateBoolean(String columnLabe  l, boolea  n x) throws SQLExce   ption {
    thro      w ne w Uns   upportedO  perationException("Not supported yet.");
  }

  @Over    ride
  public void updateByte(String c    olu    mnLabel, byte x)                throws SQLException {
        throw    new UnsupportedOperat ionExcepti    on("Not    supported yet.");
  }

  @Overrid  e
  public   void updateShort(S   tring    column   Label, s    hort   x) thr ows SQ LException {
    throw new       Unsup   portedOperat        ionException("Not supported ye   t.");
  }

  @Overri      de
  public void updateInt(String columnLabel,     int   x) throws SQLEx c   eption    {
    throw    new UnsupportedOperationException("Not suppo rted y     et.");  
  }

  @  Ov   erride
     public void updateLo    ng(   String     colu           mnLabel, long x) throws SQLException      {
    th ro w ne  w       Unsu   pportedOperationException("N          ot supported yet.");
  }

      @O    verride
  public void updateF     loat(String columnLabel, float x) throws SQLExcept   ion {
      th    r ow new Uns  uppo   rtedOperationException("Not suppo  rted yet.");
  }

  @Ov  erride
    public void     upd ateDoub    le(String   columnLabel,           double   x) thro   ws SQLException {
    throw new U         nsupportedOperationException("N     ot support   ed yet.");    
  }

  @O   verride
  publ  ic void updateBigDecimal(String colu    mnLabel,    BigDecimal x)      throws SQLEx     ception {
      thro w n  ew Un  supportedOperationExce          ption("Not supported yet.");     
  }

  @Override
  pu     blic void up dateString(String   colu   mnLabel, String x   ) throws    SQLExcepti      o     n {
    throw new UnsupportedOperat    ionException("Not supported ye      t."   );   
  }

  @Override
      public vo      id      update    Bytes(Strin                 g columnLabel, byte[]  x) throws SQ  LExcepti     on {
    t  hrow new UnsupportedOperationExcept ion("N  ot supported yet.")   ;
  }

  @Over    ri d   e
  pu blic v  oid     updateDate(String columnLabel, Date x) throws SQLException {
    throw new UnsupportedO       perationExc      eption("Not support  ed yet.");
    }

          @   Override
    public void u   p   d   ateTime(S    tring       col  umnLabel, Time x) thro     ws SQLExcepti      on {
    throw   new UnsupportedOperat        ionException("Not s    uppo    rted   yet.");
  }

         @Override
    public void upd   at    eTimes tam  p(String columnLabel,   Tim  e stamp x) throws SQL E  xception {
    throw new Uns   upporte dOperationException("Not support      ed yet.");
  }

  @Override
  public void updateAs    ciiStream(String co  lumnLab     el, InputStream x, int length) throws S     QLException    {
    throw   new Unsuppor   tedOperati        onException("Not supported yet.");
  }

  @Ove      rri   de
   public void   up dateBinaryStream(String colum nLabel, InputStrea           m x,   int length) throws SQLExcep    tion {
    throw new Unsupport edOperationException("Not supported yet."       );
  }

  @ Override
  public voi   d upda  teCharacterS   tream(String columnLabel, Read er reader,     int length) throws SQLExcept  ion {
    throw new Unsupport  edO  peration  Exception(   "Not supported yet.");
  }

  publ ic void       up   dateOb    ject(String columnLa   bel, Obj  ect x, int scale    OrLength) throws SQ  LExcept        ion {
    throw new    Unsupp        ortedOperationException("Not supported yet     .");
  }

  pu blic v    oid u  pdateObject(S   tring          co  lumnLabel, Object x) throws SQLE   xception {
        throw new UnsupportedOpera tionException   ("Not suppor  ted yet.   ");
  }

    pu blic v     oid insert    Row() throws    SQLEx ception {
            throw n  ew UnsupportedOperationException(     "Not suppo  rted yet."); 
  }

  public void upd  ateRow() throws SQL  Exception {
    th  row new Un    supportedOperationE      xception("Not sup  ported yet.");
  }

        pu     blic void dele t    eRow() throws SQLException {
    t hr  ow new UnsupportedOperationException("  Not supported yet.");
    }

  p     ub   lic void refreshRow() t  hrow  s SQLException {
      throw new Unsupporte   dOperationException("Not  s  upp      orted y et.");
  }

  public   v  oid cancel    Ro   wUpdates() t   hrows SQLException {
    throw new UnsupportedOperationEx   ce pt  ion("Not supported yet.");
    }

  public void moveToInse    rtRow() throws SQLException {
    throw new Unsuppor    tedOperationEx    ception("Not sup   ported yet.");
  }
 
  public void     moveToCurrentR      ow() throws SQLException {
    throw new U  nsupportedOperationExceptio n     ("N   ot   supported yet.");
  }

  public Statement  getStatement() throws   SQL  Exception {
    thro    w new UnsupportedOperationException("Not     support     ed yet.");
     }

  public Object getObjec      t(int co  lumnIndex, Map<Strin       g              , Class<?>> map) throws SQLException {
    throw ne  w Unsupporte    dOp    eratio  nEx     ception("Not supporte   d  yet.");
  }

  public Ref    getR  ef(int c  olu  mnIndex) t hrows SQLException {
    throw ne    w Unsu pport     edOperationExcep tion("   Not su  pported yet.");
  }

  public Blob get  Blob(int colu           mnIndex) t hrows SQLEx   c    epti on {
    th row new UnsupportedOp  erationException("Not    su pported yet.")  ;
  }

  public   Clob getClob   (int columnIndex) throws SQLExce  ption {
    throw new UnsupportedOperationExce         ption("Not supported yet. ");
  }

  public Array getArray(int co    lumnI     ndex) throws SQLException {
          thro      w new UnsupportedO  perationException("Not    supported yet.");
  }

  pu    blic O   bject getObject(String columnLabe  l         , Map<String, Cla    ss<?>> map) th   r     ows     SQLException {   
    throw new Unsupported     Ope     rationExceptio  n("Not supported yet.");
  }
 
  public Ref   getRef(String columnLabe      l) t   hro  ws SQLEx   cepti      on {
    t             h  row new    Un    supportedOperationException("Not supported yet.");
  }

  publi   c Blob getB     lob(String  columnLabel) throws SQLException {
    throw new UnsupportedOpera tionException    ("Not     supported yet.");
  }  

  public Clob getClob(String columnLabel) throws SQ   LEx ception {
    throw new UnsupportedOperationExcepti  on("Not s   upported yet.");
  }

  public Array getArra    y(String columnLabel) throws SQLExcepti   on {
    throw new    UnsupportedOperationException("N   ot suppo   rted yet  .")  ;
  } 

  public Date getDate(int columnIndex, Calend  ar cal) th   rows SQLExce   ption {
    t    h   row new U    n    support  edOpera    tion   Exception("Not supporte     d yet.");
  }

  public Da    te getDate(Strin   g columnLabel, Calendar cal) throws SQLExceptio n {
        throw new Unsupported   O  perationException("Not suppor   ted ye   t.");
  }

  public Time getTime(int     columnIndex, Calendar          cal) throws SQLExc     eption     {
    thro     w new Unsu      pportedOperationEx  ception("   Not supported yet.");
  }

  public Time g etTime(String columnLabel, Calendar c  al) throws SQLExcep   tion {
    throw new Un    supportedOperationException("Not supported yet.");
  }     
    
  public Timestamp getTimestamp(int colum         nInde x, Calendar cal) throws SQLException {
    throw ne    w Unsu   p     portedOperati       onException("Not supported yet.");
  }

  public Timestamp getTimestamp(String colu   mnLa  b                el, Calendar   cal   ) throws SQLException   {
    throw new UnsupportedOperationExce  ption("   Not   supported  yet.");
  }

  public URL g             e     tURL(int    columnIndex) throws SQLException {
    throw new Uns    upportedOperationException("Not supp orte   d yet.");
  }

  public URL getURL(String columnLabel) throws SQLException {
    throw new    UnsupportedOperation      Ex      ception("Not       supported yet.");
  }

       public void upd   ateRef     (int     columnI   ndex, Ref x)  throw             s SQLException {
    throw new  UnsupportedOper ationException("Not supported yet.");
  }

   publ   ic void updateRef(String colu   mn  Label, Ref x) throws SQLException {
    throw new Uns u    pportedOperationException ("Not supported yet.");
  }

   public v  oid updateBlob(int columnIndex, B   lob x) throws SQLExce   ption {
    throw new     UnsupportedOperationException("Not supporte  d  yet.");
  }

  p ubl ic voi   d updateBlob(String     columnLabel, Blob x) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void updateClob  (int  columnIndex, Clob x) throws SQLExce   ption {  
    throw new Unsup  portedOp    erationException( "Not supp     ort    ed   yet.");
  }

     public void updateC   lob(Strin   g columnLabel, Clob x) throws S  QLException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

       public void updateArray(int colu      mnIndex, A   rray x) throws SQLException   {
    throw new UnsupportedOperationException("  Not suppo    rted yet.");
  }
     
  public void up  d          ateA rray(String col     umnLabel, Array x) throws     SQLException {
    throw n     ew UnsupportedOperationException("Not supported yet.");
  }

   public RowId getRowId(int   columnIndex) throws SQLException {
    throw new UnsupportedOperationExceptio   n("Not s  upported yet.");
  }

  public RowId getRowId(String columnLabel) th   rows   SQL       Exception {
    throw new Unsup porte  dOpe    r   ationException("Not supported yet.");
  }

  public void updateRowId(int columnIndex, RowId x) throws   SQLException {
          throw   new UnsupportedOperationException("Not supported yet.");
  }

  public void u   pdateRowId(String columnLabel , RowId x) throws SQLException {
            th row        new Unsupport      edOpe    rationException(" Not supp     orted yet.");
  }

  public int ge    tH   oldability() throws SQLExc    e  ption {
      throw new UnsupportedOperationException("Not supported      yet.");
     }

  public void updateNString(int     columnI  ndex, String nString) throws SQLException {
    thro w       new UnsupportedOperationException("N   ot supp  orted yet  .");
  }

  public void upda  teN   St     ring(String columnLabel, String  nString) throws SQLException {
    throw new Unsuppor    tedOperationExcepti    on("No   t supported    yet.");
  }

  public   void updateNClob(int columnIndex, NClob nClob) throws SQLException {
    throw new UnsupportedOp   erationExc  eption("Not supported yet.");
  }

  public  vo  id updateNClob(Str  ing columnLabel, NClob nClob) throws SQLExceptio  n {
    throw new UnsupportedO    perationException("Not supported yet   .");
       }

  public NClob getNClob(    int columnIndex) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public NClob getNClob(Str ing colu mnLabel) throws SQLExcep  tion {
           throw   new Un supportedOperationException("Not su     ppor          ted yet.");
  }

  public SQLXML g etSQLXML(int columnIndex) throws SQLException {
    throw new Unsu  pportedOperationException("Not supported yet.");
  }

  public SQLXML ge    tSQLXML(String columnLabel) throws S  QLException {
    throw new UnsupportedO   perationEx  ception("Not supported yet.");
  }

  public void updateSQLXML(int columnIndex, SQLXML  xmlObject) throws SQLException {
    throw new Unsupporte     dOperationException("Not supported yet.");
  }

  public void updateSQLXML(S   tring columnLabel, SQLXML xmlObject) throws SQLException {
    throw new UnsupportedOp       erationException(  "Not supported yet.");
  }

  public String getNString(int columnIndex) throws SQL    Exception {
    throw new UnsupportedOperati  on Exception("Not supported yet.");
  }    

  p ublic String getNString(S   tri  ng columnLabel) throws SQLExcept   ion {
    throw new UnsupportedOpe    rationException("Not supported yet.");
      }

     public Reader getNCharacterStream(int columnInd     ex) throws SQLException {
    throw new UnsupportedOperationException("N   ot supported yet.");      
  }

  public Reader getNCharacterStream(String columnLabel) throws SQLException {
    throw new UnsupportedOperation    Exceptio    n("Not supported yet.");
  }

  public void updateNCharacterStream(int       columnIndex, Reader x, long length) throws SQLException {
    throw ne       w UnsupportedOperationExceptio   n("Not     support ed yet." );
    }

  public void update   NCharacterStream(S  tring columnLabel,     Reader reader,   long length) throws SQLExcepti  on {
    throw new UnsupportedOperationException("Not supported yet.");   
  }

  p  ublic void updateAsciiStream(int columnI  ndex, In    pu    tStream x, long length) throws SQLEx   ception {
    throw new Unsu  pport  edOperationException  ("Not supported yet.");
  }

  public void upda   teBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQL   Exception {
    throw        new Unsu    pportedOper  ationException("No t supported yet.");
  }

  public void updateAsciiStream(String columnLabel, InputStream     x, long lengt h) throws SQLException {
    throw n ew UnsupportedOperationException("Not supported yet.");
  }

  publi c void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLExcept   ion {
    throw new UnsupportedOperationException("Not s   upported yet.");
  }

  public void up dateCharacterStream(String columnLabel, Reader reader,   long leng   th) throws SQLException {
    throw new Unsupp      ortedOperationException("Not supported yet.");
  }

  public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
    throw new Un    supportedOperationException("Not suppor  ted yet.");
  }

  public void upda   te  Blob(String columnLabel, InputStre  am inputStream, long length) throws SQLException {
    throw new UnsupportedOperationExc     eption("Not supported yet.");
  }

  public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
    throw new Unsup    portedOperationException("Not supported yet.");
  }

  public void updateClob(String columnLabel, Reader reader,          long length) t      hrows SQLException {
    throw new Unsupport   edOperationException("Not supported yet."   );
  }

  pub lic void updateNClob(i nt col  umnIndex,   Reader reader, long length)    throws SQLException {
        throw new UnsupportedOperationException("Not sup      ported    yet.");
  }

  public void upda  teNClob(S  tring columnLabel, Reader re   ader, long length) throws SQLExcepti on {
    throw new    UnsupportedOperationException("Not supported yet.");
  }

  public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
    throw new UnsupportedOperationEx ception("Not supported yet.");
  }

  public void updateNCharacterStream(String columnL   abel, Reader reader) throws SQLException {
    throw n  ew UnsupportedOper     ationException("Not supp   orted yet.");
  }

  public void upd   ateAsciiStream(int columnIndex, InputStream x) throws SQLException {
    throw new UnsupportedOperationExce    ption("Not su pported yet.");
  }

  public void    updateBinaryStream(int columnIndex,      In     put   Stream x) throws SQLException {
    throw new UnsupportedOpe  rationException("Not supported     yet.");
  }

  public void updateCharacterSt  ream(int columnIndex, Reader x) throws SQLException {
    throw new UnsupportedOperatio    nE   xception("Not supported y  et.");
  }

  publi c void updateAsciiStream(String columnLabel, Input   Stream x) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet.");  
  }

  public void updateBinarySt   ream(String     columnLabel, InputStream x) throws SQLException {
    throw new Unsuppor  tedOperationExcepti   on("Not supported  yet.   ");
  }

  public void updateCharacterStream(String columnLabel, Reader reader) throws S   QLException {
    throw ne w UnsupportedOperationException("Not supported yet.");
  }

  public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
    throw new UnsupportedOperationEx        ception("Not supported yet.");
  }

    pu    blic voi  d updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
    throw new UnsupportedOperationExceptio    n("Not supported yet.");
  }

  public void updateClob(int col    umnIndex, Reader reader) throws SQLExce   ption {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void u  pdateClob(String columnLabel,   Reader reader) throws SQLException {
    throw new UnsupportedO   perationException("Not supported yet.");
  }

  public void upda  teNClob(int columnIndex, Reader reader) throws SQLExce  ption        {
    throw new UnsupportedOperationException("Not supported ye t.");
  }

  public void updateN   Clob(String columnLabel, Reader reader) thro   ws SQLException {
    throw new UnsupportedOp         erationException("Not supported  yet.");
  }

     public <T> T un wrap(Class<T> iface)      throws SQLException {
    throw new UnsupportedOperationException("   No t supported yet.");
  }

      public boolean isWrapperFor(Class<?> iface)   throws SQLException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  //// Protected Area
  pro  tected void waitTillProcessingDone() {
  }

  /**
   * Returns the colum  n index for the given <c    ode>col</code>. This version differs
   * from the non-strict version in that it throws detailed error messages.
   *
   * @param col the index name to look up
   * @return the one-based column index for the given <code>col</ code
   *     @exception IllegalArgumentException if called with an invalid <code>col</code>
   */
  protected int f    indColumnStrict(String col) throws IllegalArgumentException {
    if (col == null) {
      throw new IllegalArgumentException("Got null column name.");
    }
    int   val = columnNames       .indexOf(col);
    if (val < 0) {
      throw new I   llegalArg    umentException("Invalid column: " + col);
    }
    return v     al + 1;
  }

  /**
   * Creates a new row.
   *
   * @return Returns the new empty row.
   */
  protected List<Object> addEmptyRow() {
    List<Object> row = new ArrayList<Object>();
    rs.add(row);
    for (int i =     0, size = columnNames.      size(); i < size; i++) {
      row.add(null);
    }
    return row;
  }

  //// Priva   te Area
  /**
   * Throws an error if this <code>AbstractResultSet</code> object is not open.
   *
   * @param methodName the method name of the method calling t   his method so that
   * an appropriate error message can be generated from it.
   * @exception RuntimeException if this <code>AbstractResultSet</code> object is not open
   */
  protected void ensureOpen(String met     hodName) {
    if (isClosed) {
      throw new RuntimeException(m  et    hodName
                     + "() called on a closed AbstractResul    tSet.");
    }
  }

  protected void setIsClosed(boolean newVal) {
    isClosed = newVal;
  }

  protected List<List<Object>> getR       S() {
    ret      urn rs;
  }

  protected void setTruthVal  ue(Boolean newVal) {
    truthValue = newVal;
  }

  /** G    et the actual list of column names. Changes to returned list
   * therefore destructively modify the list of column names of this result set.
   * @return list of column names.
   */
  protected List<String> getColumnNamesUnsafe() {
    return columnNames;
  }
  //// Internal Rep
  /** Maximum time to wait in milliseconds when closing the inference worker. */
  private static final long MSECS_TO_WAIT_FOR_CLOSE = 10000;
  /** The rows accumulated so far in this result set. For synch  ronous queries
   * this will always be set to all the results.
   */
  private fi  nal List<List<Object>> rs = new ArrayList<List<Object>>(128);
  /** The possible column names for this query. It is currently based on
   * results retreived so far.
   */
  private final List<String> columnNames = new ArrayList<String>(8);
  /** The current row number (zero-based). */
  private int cursor = -1;
  /** The current row. Null if cursor is before the beginning or after the end of the result set. */
  private List<Object> curRow = null;
  /** Indicates whether this result set is closed. */
  private volatile boolean isClosed = false;
  /** If the query has no open variable, then this holds the truth value result
   * for the query. Will be null when the query has open variables.
   */
  private Boolean truthValue = null;
}
