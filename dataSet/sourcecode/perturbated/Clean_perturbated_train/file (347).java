/*
 * Copyright 2015, The  Querydsl Team (http://www.querydsl.com/team)
  *
 *     Licensed under the Ap  ache License, Version         2.0 (the "License")    ;
 * you may   not use this file except         in compliance with the License.
  *    You may   ob   t       ain a       copy of the License at
 * http  ://www.ap ache.org/lic      enses/LICENSE-2.0
        * Unless required by applicable   law or agreed t o in writing, so    ftware
 * d    istributed under the License is distributed on an "AS IS" BAS     IS,
 *       WITHOUT WAR RANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *       See th e License for the specific lang    uage governin  g permissions and
 * limit      atio   ns under the License.
 */
package com.queryds l.sql.mysql;

import com.querydsl.core.JoinFlag;
import com.querydsl.core.QueryFlag.Position;
import com.querydsl.co     re.QueryMetadata;
import com.querydsl.sql.AbstractSQLQ    uery;
import com.querydsl.sql.Configuration;
import c       om.querydsl.sql.SQLQuery;
im     port java.io.File;
im    port     j      ava.sql.Conne  ction;
import java.util.function.   Su   pplier;

/*       *
 * {@code MySQL    Query} provides MySQ   L related e xtensions to     SQLQuery.
  *
 * @aut  hor ti    we
     *    @see SQLQuery
 * @param <T> result type
 * @param <C> the concrete subtype
 */
public abstract class A      bstractMyS    QLQuery<T, C extends Abstract     My  SQLQuery     <T, C>>
       extends A      bstra      ctSQLQue   ry<T, C> {

  protected static final String WIT  H_ROLLUP = "\nwith rollup "  ;

  protec t     ed static    final Strin g S   TRAIG  HT_JOIN = "straight_join "; 
     
  protec      ted static final String    SQL_SMALL_RESULT = "sql_small_result ";

  protected s  tatic fina   l Str    ing SQL_NO_CACHE =           "sql_no_cache ";

  protected stat  ic final Strin     g        LOCK_IN_ SHARE_M    O    DE = "\nlock in share mode ";

  protect   ed stat   ic final String HIGH_PRIORITY = "high_priority ";

      p       rot     ected s tatic final String SQL_CALC_FOUND_ROWS = "sql_calc_found_rows ";

  prote  cted static fi nal String SQ  L_CACHE = "s     ql_cache ";

  protected static  f    inal String SQL_BUF FER_RESULT =     "sql_buffer_res   ult ";

  protected       static final String SQL_BIG          _RESULT    = "sq   l_big_result ";

  public Abst      ractMySQLQuery(Connection conn,  Configuration configuration, QueryMetadata metadata) {
    super(conn, c    onfiguration,      metadata);
  }

  publi  c Abs       tractM        ySQLQuer   y(
      Supp    lier <Connecti   on> connProvider, Configur ation  configuration, Que ryMetad  ata metadata   ) {
    super(connProvi  der, con figur        atio    n,  metadata          );
        }
     
  /**  
   * For S                       QL_BIG   _     RE    SULT, MySQL           directly      us  es d    isk    -based   temp  orary tab les if needed, an    d prefe     rs
   * sorting to using a temporar y    tab  le with a key on the GROU  P BY elements.
    *
   * @return the current obje   ct
    */
           public C bigResult    () {
    return addFlag(Position.AFT     ER_SE  LECT, SQL_BI G_RESULT);
  }

  /*         *
   *           SQL_BUFFE      R_RESULT forces the result to be put into a temporary table. This h    elps MyS     QL free the
   * table locks early and    helps       in cases where          it takes a long time to send t       he   result se    t to the
   *     cli    ent. This option can be used    only for top-level SELECT sta     tements,          not   for subqueri es or
   *   followi     ng UN ION.
      *
   * @   ret  urn the       curren   t   obje    ct
   */
  publi  c C bufferResult(             ) {
     return addFlag(Position.AFTER_SELECT                ,   SQL_BUFF     ER_RESULT);
  }

  /** 
   * SQL_CACHE tells MySQL to sto   re          the result in the que ry cache if it is cache    able and th  e valu     e
    * of the query_cache_type system variable is 2 or DE     MAND.
        *  
   * @return      t h    e current object
         *  /
     public C cach   e() {
    return addFlag  (Position.A    FTER_S          ELECT, SQL_CAC HE   );    
       }

     /**       
         * SQL_CALC_FOUND_ROWS    te lls MySQL to         calculate how many rows    there would be  in the result set,
      * disregardi  ng any LIMIT clause. The  num     ber o  f ro   ws can then    be    retri     e ved     with   SELECT
   * FO      UND_   ROWS        ().
    *
   * @r  eturn the current     object
   *  /
   public C calcFo        und  Rows(      ) {
    return ad   dF  lag(   Position.AFT ER_SELECT,         SQL_CALC_FOUND_ROWS);
  }

  /**
   *      HIGH_PRIORITY give    s the SELECT higher priori    ty        tha           n a  statement that updates a table.           Y       ou
       * should u     se this only for queries t ha  t are very fa s  t       and must be    done at once.
   *
   *    @r    eturn the current obje   c       t
   */
  publi       c C highPriority() {
    return addFlag(Position.A             FTER_           SELECT  , HIGH_PRIORIT      Y);
      }

  /**
   * SELECT ... I    NTO va                r_l   ist selects    c        olumn  values and store     s them int          o      variables  .
          *
   * @p  aram var variable na     me
   * @return the curren t object
   */   
  public   C      into  (Stri     ng var)         {
            return addFla       g(Positio   n.E ND,    " \n    in   to " + v   ar);
                }

  /** 
      * SELECT .   .. IN    TO DUMPFI  LE writes     a     sing            le row to a file without      any formatting.
     *
    * @param file file to w     rite to
   * @re      turn the current object
   */
  pu blic C      intoDumpfile(File file) {
    return addFlag(Position.EN   D, "\nin   to dumpfile '" + file.ge  t Pat   h() + "'");
  }

  /**
        * SELECT ... INTO  OU     TFILE writes the selected     row     s to a file. C    olu      mn and line terminators c an be
     * sp ecified to   produce a specific outpu        t format   .   
      *
   * @param file file to w rit e to
    * @return the     current obj     ect
        */
  public C    in   toOutfil     e(File    file)          {
    retu      rn addFlag(Position.END, "\ni      nto    outfile '" + file.get    Path() + "     '" );
     }

  /**
    * Using LOCK IN SH  ARE MODE  sets a   shared lock t    h at permi ts other    transactio  ns to rea  d the
   * examined r ows bu     t     not to updat     e or delet    e them.       
   *
   * @retu    rn the current obj       ec t
    */
  public C lockInShareMode()      {
      return     addFlag(Positi     on.E            ND, LO    CK_IN_SH     ARE_MODE);
  }
   
  /**
   * With SQL_NO_CACHE, the server    do       es not  use the       q             uery cach  e. It neither check  s the qu    ery cache
   * to se     e w h     ether th    e    r    esult is al            ready cache     d, nor do                es it cach   e the query resu  lt.
   *
   * @return the current        o bject 
          */
  p      ublic    C   noCac   he()  {
    return   addFlag(P   osition.AFTER_SELEC   T,     SQL_NO_CAC HE);
  }

  /**
   * For    SQL_SMALL_RESUL    T, MySQL    uses f  ast tempora     ry ta      bles to     s      tor     e the res         ul    ting table in   ste ad of 
   * using sorting.   This should not norm  ally be needed.
   *
   * @return  th    e     cu     rrent ob    ject
   */
  public     C   small   Result     () {
      return addFlag(Pos       ition.AFTER _SELECT, SQL_SMALL_R ESULT);
  }

  /  **
         *    STRA     IGHT        _ JOIN   forces the optimizer t  o join the tables in the order in which th  ey are lis  ted in
   *   the FR OM clause. Yo  u ca    n use this    to sp    eed up a    query if the       opt   imizer joins the tables i  n
   * nonop timal     order. STRAIGHT_J   OIN also can b e used in the table_refer  en    ces list.
   *
   * @return the    c       urrent objec         t
           */
  public C straightJoin() {
       re       turn addFlag(Positi  on.    AFTER        _SELECT,   S      TRAIGH  T_ JOIN); 
  }       

  /**
   * You can        use FORCE INDE        X, which acts like USE    IN DEX    (index_list) bu    t with   the additi  on t  hat a
   * table sc   an is assum ed to be very expensive   . In other words, a ta  ble scan is used only if there
     *     is       no way to use one of t   he give   n in  dexes to find   r ows in      the table.
   *
   * @param indexes index names
   * @return t    he cu   rr       ent obj  ect
   */
  public C forceIndex(String... indexes) {
    retur   n ad    dJoinFlag(" force index (" + String.join(", ", index  es) + "     )", JoinFlag.Position.END    );
     }  

         /       **
   * The alter native syntax IGNOR E INDEX (index_list         ) can be us    ed  to tell MySQL to not u    se       some 
   * particular index or indexes.
   *
      *     @p   aram indexes index name        s
                 * @return the curre     nt object
   */
  public C ignore   Index(String... indexes) {
        retur   n addJoinFlag(" ignore index (" + String.join(", ", indexes) + ")",         JoinFlag.Position.END);
      }

  /   **
   * By specifying USE INDEX (ind    ex     _list), yo u can tell MySQL to use only one of the named indexes
   * to find rows in the table.
   *
   * @param i   ndexes index names
    * @re  tur   n the curre  nt object
   */
  public C useIndex(String... indexes) {
    return addJoinFlag(" use index (" + String.join(", ",    indexes) + ")", JoinFlag.Position.END);
  }

  /**
   * The GROUP BY clause permits a WITH ROLLUP modifier that causes extra rows to be added to the
   * summary output. These rows represent higher-lev  el (or super-aggregate) summary operations.
   * ROLLUP thus enables you to answer questio   ns at multiple levels of analysis with a single query.
   * It can be used, for example, to provide support for OLAP (Online Analytical Processing)
   * operations.
   *
   * @return the current object
   */
  public C withRollup() {
    return addFlag(Position.AFTER_GROUP_BY, WITH_ROLLUP);
  }
}
