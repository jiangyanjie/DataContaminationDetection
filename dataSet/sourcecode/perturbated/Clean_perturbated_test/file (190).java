/*
 * Copyright  (c)   1996, 2013, Oracle and/or its affiliates. All            rights reserved.
 * ORACLE PROPRIETARY/CONFIDENT      IAL.      Use is subj  ect to license         ter  ms.
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


pa  cka     ge java.sql;

/**
 * Comp   r     ehensive  informat      ion about the         dat  abase as a     wh   ole.
  * <P>
 * This interface is implemented   by driver    ven    dors to let users kn ow the capabilities
 * of a   Database Management System (DBMS) in combin  ation with   
 * the driver based on JDBC&trade; technology
 * ("JD     BC driver") th      at is    used    with it.  Different re    lational DBMSs often support
 * different  fea  tur  es,       implement features i       n different ways,     and use different
 * dat   a ty    pes.  I   n ad    ditio     n, a driver may implement a featur e on top of what the
   * DBMS offers.  Information returned by methods in this   i   nt     erfa      ce applies
 * to the capabilities of a particular driver an     d a   particu lar DBMS    wo  r  kin   g
 * together.   Note      that            as used    in this documen      tati  on, the term "database" is
 * used generically to     refer to        both the driver   and DBMS.
 * <P>
 * A user for this interface is commonly a t        ool tha      t ne     eds to disco   ver how to
    * deal with the underlying       DBMS.  This     is especia lly true for ap p lications
 *      that are intended to b e used with more than one DBMS. For       example, a tool might use the metho     d
 *   <code>g   etTypeInfo</code> to     find out what data type    s can be used in a
 * <co       de>CREATE T       ABLE<      /c     ode    > statement     .    Or a user might call the      method
    * <code>supportsCorrelatedSubqueries</code> to see if it is possible t o use
 * a   co   rrel    ated subquery or <cod   e>suppo   rtsBatc  hUpd    ates</code> to see if it   is
 * possible to use batch update      s.
 * <P>
 *  Some    <code>Dat    abaseMet  aData</cod    e> meth  ods return lists of information
 * in the fo    rm of <c  od    e>Res   ultSet< /code> objects.
     * Regula             r <code>ResultSet</code> methods, such as
 * <code>getString</code> a                  nd <   c     ode>    get   Int</code>, can      be u sed
 *         to retrieve the data fr  om these <    code>ResultSet</code> objects.  If
 * a given form of   meta  data is not availa ble, an empty      <code>Re  sultSet</code>
 * will be returned. Additional column     s beyond the columns defin      ed to be
 * returned by   the <code>Resul   tSet</code> object for a given me   thod
 * can be defined   by            the JDBC driver vendor    an    d must be access ed
 * by         their <B>column label</B>.
 * <P>
 * Some     <code>DatabaseMetaData</code>   methods t   ake arguments that ar      e
 * String    patter  ns.  Thes   e argument      s all hav      e name     s such      as fooPattern.  
 * Wi thin a pattern Strin    g, "%  "            m    eans mat    ch   any substring  of 0 or more
 * char  acte    rs, and "_" means match any one chara  cter. O  nly m  e  tada   ta
 *    ent   ries matching the search pattern ar  e returned. I  f a searc    h pa   ttern
 *    a     rgument is set to <code  >null</code>, that arg      um            ent's criterion will
 * be drop  ped from the s  earch.
 *
 */
public i    nterface Dat   aba     seMetaData e       xtends Wrapper {

        //              --------------------------------------  ------------  -----  ---------------
    //  First, a variety of minor     information about t    he      tar     get data ba      se.

    /**
          * Retr   ieves whether the current use    r c an call all the   procedures
             * r eturn        ed by the metho      d              <code>getProcedures</c    ode>.
     *
        * @return <code>tru    e    </cod e   > i   f so; <code>false</co      de> otherw     ise
                  * @exception SQLE xception if a databa se access error oc       curs   
              */   
    boolean allProc  edu         resAreCallable() th    rows SQLException;

      /**
     * Retrieve      s whether the current use   r c    an use all the tabl   es returned
     * by the me    th           od <code> get Tables</  code> in a <code>SELECT</co   de>
               * statement.
     *
           * @return <c                ode>true        </co   de> if so; <  code>false</code> other    wis        e
     * @ex cept    i      on SQLException if a database access erro    r o   ccurs
     */
    boolean all        TablesAre   Select      able() throws SQLEx ception;

             /**
     * Re     trieves the URL    for this DBMS.
     *
       * @return the URL for this DBMS    or <code>null</code  >        if             it   cannot be
        *                    gener   ated
     * @exception SQLException if a database      acc     ess error oc          curs
        */
    String getURL(  ) throw    s  SQ  LE  xceptio  n;

          /**
     *          Retrieves the user name   as known to this data base.
       *
         *       @return  the datab    ase use          r  name
     * @   exceptio    n SQLExc  eptio  n           if a dat  abase access error occurs
                */
      String       getUser     Name()    throws SQ  LEx  ceptio n;

          /**
     * Retrie ves whet  her this database is in read-only mod            e.
     *
     * @return <    code>true</ code> if so; <cod  e>fals   e</code> oth erwis e
             * @exc      eption SQLException if a dat  a   base acc       e   ss error occurs
         *   /
        boolean isReadO      nl   y(   ) throws SQLException;

    /**
        * Retri  e   ve  s whether <co   de>NULL</code> values are    so    rte       d hig h.
      * Sorted high   means t     hat <code   >NULL</code> valu    es
       * sor     t highe      r than any    othe     r v  alue in a         domain.  I    n    an ascending o          rder,
           * if this me        thod ret    u   rns <code>true</      code>,        <cod   e>N  ULL</co  de>     v alue   s
     *          will appear at th     e    end. By cont   rast, the     method
      *    <code>null  sAreSor    tedAtEnd</c  od  e> i      ndi  cates whether    <co    de>NULL</code> values
     * are sorted         a    t the end   regardless    of sort order.
      *       
     * @ret  urn <code>tr   ue<  /code> if so;  <code>f   al  se</c     ode> otherwise
     *    @exception SQLException if a database ac    cess     er    ror occurs
     */
    boolean nullsAr   eSorte   dHi    gh() throws SQLE                 xce    ption;

    /**
         * Retrieves whether   <code>NULL  </code> values are   sorte    d l    ow.
     * Sorted low         me   ans that <co   de>NULL    </cod   e> values     
       * sort low er than any other value in a domain.  In an a  sce     nding order,  
     * if  thi   s method       retu     rns <code>true</code>,  <code>NULL</code> values
          * will appear at the    beginning. By contrast,    the met  ho         d
        * <code>nullsAre So   rtedAtSta                         rt</code> indicates whether <code>NULL </code> va  lues
     * are      s   orte d at the b eginning regardless of sort order.
     *
     *    @return <code>true</code> if s   o; <code>false<  /code>    oth  er    wise
            * @exception SQLExc    eption if   a data base   access e   rror occurs
         */
    bool ean nullsAreSort                   edLow() throws SQL   Exce ption;

    /    **
         * Retrieve    s w   he    ther <code >NU  LL</code> values are   so rted a t t he start regardle         ss
     *    of sort order.
       *
     *       @     return <code>true</code> if      s  o; <code>fals   e  </code> otherwise
            * @exception SQLException if              a database   acce   ss error occurs
        */      
          b     oolean nulls  AreSor ted   AtStar  t ()  throws SQLExcept   ion;

    /**  
     *      Retri   eves w  hethe r <   code>NUL  L     </cod  e> values are    sort     ed  at the  end regardless of
     * sort order.
        *
     * @retur   n <code>t            rue   </code> if  so; <code>false</code> ot herwise
         * @exception SQL   Exception  if a d  atabase access error occurs   
     */
    boolea    n nullsAreSortedA   t                  End()        throws SQLExc     e    ption;

     /**
          * R     e tr    ieves th   e name of this database    product.
            *
     * @return database product name
     * @exception  SQL  Exception   if a    databas  e access error occurs
     */
    String getDatabaseProductName()      throws       SQLExcep   tion;

    /**
       * Retri  e    ve  s the   version n  umber     of this d     at             ab    ase pro      d   uct.
          *
           * @return database version n umbe  r
        * @   exception SQLException if a database a      ccess erro    r  occurs
      */
    St      rin  g ge  t       Databas   eProductVersion(    )     thro  ws    SQLE xcept       ion;

            /*   *
     *  Ret  rieves the name of this JDBC driver   .
         *
     * @return   JDB C             driver name
       *   @exception        SQLException if a database a  ccess     error occur s    
     */    
    String getDriverName() thro  ws SQLException;
     
    /**
              * Retriev   es the   version number   of   thi   s JD  BC driver as a <code         >String</code>.
     *
      * @retu     rn JD BC           driver ver    sio  n
     *       @exception SQLException if a database    access error occurs
            */
    Str  ing getD rive     rVe    rsion() throws          SQLException;

      /**
     * Retrieves    this JDBC     driver's major version number.
          *
         * @   re           turn JDBC drive r m    ajo r version
     */
        int g    et  DriverMajorVers  ion();

    /**
     * Retrieves this JDBC driver'    s minor version num    ber.
     *    
       * @    return J      D       BC driver minor   version number
     */
        int getDriverMinorVe  rsion   ();

    /    **
     * Retrieves whether                 this dat        abase stor       es tables in  a local       file.
     *
     * @return <         code>true</code> if so; <code>false</cod   e> otherwise
        * @exception  SQLExc      eption    i    f a      database a   ccess error occurs
      */
    boo    le  an    us  esLocalFiles()                 t    hrows S    QLExcept   ion;

    /**
     *   Retrieves whether this databas    e uses a f        ile    for each table. 
     * 
         *  @re t     urn <code>true</code> if this dat   abase uses a local file for each ta      ble;
     *                    <code>fa   lse</co de    > otherwise
     * @exception SQLException if a database acc   ess err or      occ  urs
     */
    boolean usesLoca   l FilePe rTa  ble(    ) t   hrows SQLEx  ception;       

      /**
          * Retrieves        whether thi   s database tre        ats mixed case unquoted SQL identifiers as
     * ca se sensitive a  nd as a result st   ores them in      mixed case   .
         *
     * @ret   urn <code>true</code> if     so;   <code>fal  se</code>     otherwise 
     * @exce  ption SQLException if a dat a       ba         se access error occurs
     */
      bool    ean support   sMixedCaseI     dentifiers() throws SQLEx   c     eption;

    /**
     * Retrieves whether thi  s dat     abase trea      ts mixed case un qu     oted SQL identifiers              as
              *  ca   se insensitiv        e and stores them  in up   pe        r case.
           *
     * @return <code      >true</code>      if      so;   <code>fals     e       </code>      o   t  herwi    se
     *     @exception SQLException if                   a database acce   ss error occ urs
     */
           boolean storesUp perCaseIdentifie  rs()  thr  ows   SQLEx cept      ion;

    /**
     *   Retrieves whether this   database treats m ixed case unquoted SQL identi     fiers      as
     *  c   ase insensitive and sto       res t         hem in lower case.
     *     
     * @re   turn    <code>true</code  > if so; <c        od    e>fals   e</code   > oth   erwis   e
     *    @excep                    tion SQLExc           eption     if a database acc   ess error occurs
           */
    boolean storesLo   werCaseIde    ntifier s() throws SQLException;

        /**
             * Re  trieve s whether this d   atab       ase treats mixed case   un quo    ted SQL identifiers       a  s
          *     case i nsensiti   ve and      stores them in mixed cas   e.
        *
     * @return    <code>true</cod      e> if so;             <code>fa      lse</      c   ode> ot    h  erwis e
     * @exception           SQL     Exc         eption if   a database ac  cess error oc  curs
            */
       b    oolean stor   esMixedC         a         seIde    ntifiers()   throws     SQL     Exception      ;

    /**
           * Retrieves whether this da        tabase tr     eats mixed case       quoted SQL iden tifiers     as
     * case sensiti v e and as a re         sult st    ores them in mixed case.
          *
     *      @return <code>true<      /c    ode> if so; <code>false</code> oth erwise
         * @excep   tion SQ        LException if a     da tabase access error occurs
     */
    b     oolean support  sMixe           dCaseQuotedI    dent  if     iers(    ) thr ows SQLExcept        i   on;

    /**           
     * Re    tri     e     ves whethe r thi  s databas  e treats mixed case quoted SQL ident i  fi    ers as
      *   case insensi     tiv   e and stores     them in uppe   r case.
        *
     *     @return <code>true</code> if so; <c     ode>fa  lse</code> ot     herwise
       * @exception SQLExce  ption if      a databas  e access   error occ   urs
        */
      b    oolean store     sUpperCaseQ  u    ot   e    dIdentifiers() throws SQLEx  ception;

    /**
                * Retrieves whether this data         base treats     mixed c ase quoted SQL identi       fiers as
       * case insensitive          and sto   res them in lower case.
         *  
         * @return <code>tru    e</code> if so; <     code>false</code>         o  therwise
        * @exception SQLEx     ception    if     a d    at        abase  a ccess error oc      c       urs   
     */
       boolean     store   sLow     erCase       QuotedId  entifiers() throws SQLExcepti   on;  

          /**
        * Retrieves       wheth       er thi s d a                t          abase tre     ats mixed      case   quo         ted SQL i      den   tifie    rs a   s
      * cas     e      insen  sitive and        stores              them in mi xed case.
         *
     * @retur   n <cod   e>true</code> if so   ; <      code> fals      e< /  code> otherwise
           * @exception SQ     LExcepti   on if a datab a  se   access   err  o  r occurs
     */
    boolea   n storesMixed    C     a   seQ uotedIdentifi       e rs(    ) throws SQLExc      ept ion;

    /**
       * Retri     e     ves       the s    tring used   to quote SQL id entifier   s.
             * This      me     thod returns a spa  c  e "   " if ide    ntif        ier  quotin    g is        not   sup ported.    
         *
         *   @return the quoting   string or a              space if q uoting is    not supported    
     * @exc  ep  tion S    QLException if   a   d   atabase acces       s error occurs
              */
       String getId  entifierQuot    eString(   ) throws SQL      Exception;

        /*     *
                                  * Retrieves a com           ma-separ    at   ed li  st   of all of this database 's SQL keyw    ords
     * t      hat are    N   O           T also SQL:2003 keywords.
        *
                 * @return the list of this databas   e'    s keyword    s t     hat are not   also
              *             SQL:2003 keywords
     * @exce        ption SQLException      if a  databa      se access error occ  urs   
         */
             String getSQLK       eywo      rd s() t hrows    SQLE            x   ception;

    /**
     * Retriev  es a comma-  separated     list of math     functions av    ail  able  with
         * this database.  The       se are th  e Open /Ope    n CLI math f unction n      a   mes used    in
     * the JDBC function escape clause.
              *   
        * @re     turn the list o    f math    functi      ons supp  orted  by this database
     * @exceptio  n SQLE xc     eption if a      database acces       s err   or occurs
           */
    String getNumericF    unc tions() thro      ws   SQLException;

    /**
     * Retrieves a comma-separated list of s   tring   functions availabl   e with
          * this database .  These a   re the  Open    G      ro up CLI st   ring f   unction names us ed
      * in the JDB      C         function esc    ape clause.  
     *
     *  @re    turn t     he lis     t of          string    functions   supported by this dat abase
        * @exce    ption SQ LExc   eption if a dat     ab a               se acces    s er        ror occurs
      */
         String   getS  tringFunctions()     throw   s    S   QL     Exception;

    /**
          *          Retr  ieve  s a comma-separated l  is  t of   system   funct      ions available with
     *        t    his database.  These are the  Open Gro   up    C   LI system fun    ction names us   ed
     * in t      he JDBC fu  nction escape cla             u  s                 e .
     *
     * @return a      list o  f system functions suppor    ted b   y this d atabase
     * @exception SQLE   xception if a d  atabase access err or      oc     curs
     */
     String getSystemFunctions() throws SQLException;

    /**
      * Retri   ev  e     s a comma-sep     arat   ed list of      the tim e and date functions available
     * with this database.
       *
     * @ret    urn th     e list of tim         e    an  d date functions              sup       ported by      this database
     * @exce ption SQLExcepti   o  n if  a dat  a    base access error occurs
     */
    String getTimeDateFunctions() throws SQLException;

    /  **
       * R   etrieves the s   tring that can be used t    o escape w    il           dcard       charac ters.
      * This            is the   stri ng that can b  e u   sed    to escape '_' or '%' i      n
     * the catalo    g search parameters t  h  at are a pattern (and t   her  efore use one
     *     of t he wildcard       cha  racters   ).
     *
          * <      P>The '_'           character represents       any single character;
                *      t  he '%'             cha   racter represents any seque   nce of z ero or
     * more        chara    c t    ers.
     *
     *     @return the string used to            escape wildcard char  acters
     * @exception SQL  Ex   ception if a database access e  rr     or oc  cu   rs
     */
    String getSearchSt      ri     ngEsc ape() th      rows SQL     Exce   ptio     n;

    /**
     *     Re   tri  ev         es all the "e    xtra" charac   ters that can     be used in unquote   d
     * identi           fie     r names (thos   e be  yond     a-z, A-Z,   0-9   and _).
           *
        * @r    eturn the   string containing     the e  xtra characte            rs
       * @exception SQ   LException      if a da    tabase a   cc ess error o   ccurs
        */
        String       getExtraNameCharacters() thro   ws SQLException;

    //  --   --     ----------------    --    ------           ------------------------------------  ----
    //         Function  s describin     g which features          are supported.

    /**
     *         R    etrieves w  het     her this database supports <code >A   LTE R TABLE<     /code>
     * with add    column.
        *
                 * @re turn <code>tr   ue</code> if so;       <code           >false< /        code> other    wise
     * @e    xception SQLException if a database  access error      occurs
     */
    boolean supportsAlt  erTa   bleWi   thAdd      Col    um    n() throws SQLExceptio     n;

    /**
     * Retrieves whet   her      this           database supports <code     >AL       TER TABLE      </code>
        * with dr  op column.
         *
               *   @    retur    n <code>tr ue</code> i   f so; <code>false   </       code> ot her  wis           e
            *      @exc   ep   tion SQLE  xcept   ion if a database access error occur       s
     */
    b o  olean supportsAl  terTa        bleWithDropColumn() throws SQLException;

       /**
             * Re trieves whether this database           suppo r ts colum      n aliasi      ng.
        *
         * < P>If so, the      SQL AS clause     can be used    to provide na      mes for
     * computed c   olumns or       t o provide ali as names for colum   n       s           as
     * requ   ire     d.
     *
        * @return <    co     d     e>true</c        ode> if so; < c     ode>false</c  ode>     oth erwise  
     * @exception SQ    L     Exception i    f  a database a  ccess error occurs
       *       /
    boolean supports ColumnAliasin     g(  ) th  rows      SQLEx       ception;

    /**
     * Retrie        ve   s whether th  is      datab     ase sup      por ts      conca  tenations between
     * <code>NUL   L    </      co     de> an   d non-<c       ode>NULL</code> values be  in   g
     * <code>NULL</code>    .
     *
     * @return <code>t    rue</code> if so; <code>false< /co  de>   oth  erwise
                   * @excepti on           SQLEx  ception if   a       database access err   or occurs
               */
              boolea  n  null PlusNonNullIsNull     () th      r     ows    SQLExc    eption;    

    /**   
     * Retrieves whether       this database supp orts the JDBC sca  l  ar func       tion
     *     <code>CO   NVERT</code> for the conv          ersion of   one JD          BC type t o another.      
         * T  h      e       JDBC    types a  re the generic    SQL data     types defined  
     * in <code >java.sql.Types</code>.
       *
     * @return <co   de   > true       </code> if so  ; <code>false  </code>      otherwise
     * @e   x          cept    io n SQLExc       e   ption if a database access error occu     rs
     */
             bool  e    an s    upportsCon vert() throws SQLExcep   tion;

    /**
     * Retri         eves whether this database        s  upports the JD    BC scalar funct ion
      *   <cod   e>CONVERT</code> for    conversio ns between the JDBC type            s      <i>fromType</i>
     * and <i>t oT       ype</i>.  The JDBC types are the generic SQL dat    a types defined
          * in  <code>java.sq  l.Types   </code>.
     *
         * @param       fromType the type to convert from; one of the type codes from
     *            the class <c  ode>ja  va.     sq    l   .Types</c     ode>
     * @par  am toT   ype the      type to convert to; one of t   he    type code   s from
                   *           th  e class <code>  java.sql.Types</  code>
     * @return <code>true</ code> if             so; <code>false</code>       otherwise
      * @exception SQLExcep   tion if a    databa          se access error occurs
     *    @see Types
       */
        boolean supportsConver      t(int from   Type   , int t    oType) throws SQLExcep      tion;

    / **
     * Retri    eves whether   t   his database supports tab le cor  r el ation n       ames.
     *   
     * @return <code>true</co de> if so; <code>false</code>    otherwise
             * @exc  eption S  QLExcep tio    n  if  a database access    error occurs
      */
    b  oo   lean suppo     rtsT ab   leCorre    lationNa     mes() throws S QL   Exception;

    /**
     * Retr     ieves w     hether, when table corr     elation     names are supported, they
       * are restricted to being d   ifferent from the   names      of the  tables.
     *
     * @return <code>tr  ue    <   /code> if so; <     code>fals   e</code > ot       he   rw ise
     * @ex  ception SQL  Exception if a da    t    abase access e         rror occu   r   s
          */
         boolean supportsDifferentTableCorrelationNames()   thr      ows SQLException;
     
    /**
                * Retrieves   whethe   r thi     s database supports expres       si  ons  in
     * <code>ORDER    BY</     code> list           s.
             *
             *         @ret urn <    code>true</code>    if     so;  <     code>false</code     > other   wis  e    
       * @excep  tion SQLException if  a dat  abase access     error occurs
     */
    boolean suppor      tsEx    p   ressionsInOrderBy() throws SQLE  x      c                 eption;

    /**
     * R    etriev   es whether     this data    bas  e support   s using a column that is
      * not   i  n the <c      ode>SELE   CT<    /code> statement in a  n
         * <code>ORDER  BY</c   ode             > clause.
           *
           * @return <cod           e>tr ue</code> if so; <c  ode>false<  /code> otherwise
     * @exception SQLException i  f a database access error oc    curs
     */        
    bo       ol   ean sup     portsOrderByUn    r elated() t     hrows SQLExceptio  n;

    /**
     * Ret   rieves wh     ether th  is dat abase supports some form of
     * <    c   ode>GROUP BY</code> clause.   
               *
         * @   return <code>true</code> if   so         ;         <code>false</code> otherwise
     * @    exception SQLException if a  d      atabase access error occurs
     */
            boolean suppo      rtsGr oupBy() thro   ws SQLEx   cept  ion;

    /*     *
           * Re tr  ieves whether this       d               a     tabas  e supports using a column that is
     * not in    the <code>SE LECT<     /code> statement in a
     *           <code>GROUP BY<    /code> cl           a    use.
     *
     *                                 @return <code>true</code  > if so; <       cod      e>false</code> otherwise
        * @exc   eption SQLException      if a dat    abase access error occurs
       */
    boolean su      p   portsGroupByUnrelated() throws SQLExceptio     n;
    
      /      **
     *    Retrieves whether thi    s    database suppo   rts      using columns not included in    
         * the <co de   >SELECT  </c   ode> statement in a <code>GROUP BY</     c  ode> clause
     * provided that       all         of the    colu  mns in the <            c    ode>SELECT</code> state           ment
        * are inclu                  ded in  th    e <        code>GROU   P BY</code>          clause.
     *
        * @return <code>true</code> if so; <code>false</code>    oth  erwi     se    
     * @ ex  ceptio n SQLExcepti    on if a da tabase ac         c       ess error occurs
     */
    boolean support sGroupByBeyon  d Select() thr ow   s   SQLExcep          tio        n;      

    /**
     * Retr   ieves wheth      er this d ata  bas e suppo   rts specifying a
                    *    <           code   >LIKE</code> es   cape  clause.
     *
          * @return   <code>true</code> if s   o; <cod   e>false</code> ot    herwise
     * @ex ception SQLException if            a datab   ase ac       ces   s error occurs
     *  /
    bool      e    an supportsLikeEscapeC   lause() throws SQLException;

    /**
           * Retrieves whether this databa  se supports getti   ng mu  ltiple
         * <co    de>ResultS  et</code> ob jects      fro    m  a single call    to the
        * metho  d <code>execute</c    od   e>.
          *
     *   @return <code>true</           cod     e> if so  ; <code>false</c ode>      othe  rwise
       * @exception  S     QL    Exception   if a databa s      e             acc ess erro    r o     ccur    s     
     */
    bool ean sup  portsMu      lt  ipleResultSets() thro  ws S    QLException;

    /**
                 * Retrieves w hether this datab   ase allows h  aving multiple
         * transa     ctions open   at once (on different connections).
     *
     * @  return <code>true</code> if so; <code>false</code> othe  rwise
      * @ex  cep  tio                n SQLExceptio  n if a database acces   s error                occurs
          */
    boolea     n supportsMultipleTransa         ctions() throw   s S    QLEx       ception;

    /**
         * Retr  ieves whether colu  mns in    th  i  s database may be def  ined as non-nullable.
     *
     *       @return <code>true</code> if so     ; <co de>f alse</code> otherwise
     *     @exception SQLExceptio   n if     a d   atabase access error occurs
     */
        boolean su  pport sNon       Nu             ll     ableColumns() throws SQLE    x    ception;

     /**
      * Retrie  v  e     s whether  this databas e      su   pports the ODBC Minimu     m    SQL gr       ammar     .
     *
         * @return      <code>tr u  e</code> i  f so; <cod  e>fals                e</code> otherw ise           
      * @exce     ption SQL     Exception if a     database access er ror occurs
         */
    bo  olea   n supportsMinimumSQLGrammar() throws S    QLException;

    /**
     * Retrie     ves wh      ether this database suppor    t s the ODBC Core SQL   grammar.
     *
     *      @retu    rn <  co    de>true</code> if    so; <cod e>false</code> otherwise
     *   @e xceptio     n SQLExceptio  n if a database       acce     ss error occurs
     */
    boo lean supportsCoreSQLGra  mmar( ) throws     SQLException;

    /**
     * Retrieves whether this database supports t      he     ODBC Extended SQL grammar.
       *
     *      @return <code>true</code> if so; <code>false</code> otherwise
       * @ex    cept   ion SQLExc          ep    tion    if a database      access error occurs
      */
      bool  ean supports      Exten   de     dSQLGrammar() throws SQLExcep  tion;

    /*  *
      * Retr  ieves  whether t          his d          atabase supports the ANSI92 e     ntry level SQL
                 * grammar.
              *
     * @r  eturn <code>    true</code> if so; < code>fal se</co     de> otherwise
     * @    exception SQL   Exc     eption if a dat  abase access erro r   occurs
                         */
      boolean supportsANSI9 2EntryLe   velSQL()    throws SQLE    xception;

    /**
                 * Retrieves    wh  ethe     r this databa   se suppo   rts the ANSI92 i  ntermediate SQL     grammar supported.
        *
          * @retu  rn    <c ode>true< /code > if so;    <cod    e>false</code> otherwise   
         * @ex    ception SQLException     if a database access error occurs
     */
        b oolean su  pport   sANSI92IntermediateS  QL() throw    s SQLException;  

      /**
              *          Re     triev   es whether this     database supports     the ANS     I92 full SQ L gra     mmar   s  u        pported.
     *
     * @return <code>true</code> if so; <c       o de>false</code> oth   erwise
     * @ex  ce    pti   on SQLExcept     ion i   f a databa           s   e      access error o    ccur         s
     *          /
    boolean supportsANSI92Ful   l       SQL() thr     ows SQL  Exception; 

         /**      
       * R     etrieves whet  her this d atabase       support           s the SQL Inte          grity
              * Enhancem   ent Facility.
     *
     * @retur    n <code>true</code> i   f s o  ; <code>false< /code>        other         wise
         *        @exception SQLException if a      da  taba         se access error occurs    
     */     
    boolean  suppo       rtsIntegrity    EnhancementFacility() throws S         QLExcep     tion;

    /**  
     *    Re  trieves    whether this data      base supp    o     r   ts some for       m of  outer join.
     *
     * @return  <code   >true</code> if   so; <c          ode>false</code> otherwise
     *       @ exception   SQLException i f a dat abase acc      ess error o   ccurs
        */
    boolea n suppo       rtsOute  rJo     ins() throws SQLException;

    /   **
     * Retrieves whether th   i    s    datab     ase supports full    nested outer joi    ns. 
          *
       * @return <code>true</cod     e>    if so; <co           d    e>false</code>        otherwise
       * @exception SQLExc       eption     if a database    access error occurs
     */
    boo  lean su   pportsFullOuterJoin s    () thro  ws        SQLException  ;

    /**
     * Retrieves whether t  his databa    se pro  vides limited suppor   t   for outer 
     * joins.  (Thi  s will be             <cod  e>true</code> if the m  ethod
     * <code>suppo   rtsFullOute   rJoi   n   s</code> retu  r   ns <code>true</code>).
     *     
     * @retu rn <code>true</code> if so;   <code>fal se</code> otherwise
     * @exception SQLExcepti on if a database access error occu        rs
     */
    boolean su pport   sLimitedOuterJoins() throws SQLExcepti  on;    

    /**
     * Retrieves the  databa    se vendor's preferred term for      "sc  h     ema"   .
     *       
     * @return the vendor   ter    m     for "schema"
     * @exceptio    n   SQLE xc              eption if a dat   abase access error occurs    
         *                /  
    String getSchem  aTerm () throws SQLExce      ption;

    /**
     *   Re     trieves the database vendor's preferred t  erm       for "pro  c   edur    e".
     *
     *   @return the vend     or term for "procedure"
            *    @exc       eption SQLException i   f a data     base access error occ  urs
     */
    String g   etPr  oc  edureTerm()       throws    SQLEx     ception;

      /*      *
     * Ret   rieves             the database ve                    ndor's preferred t   e   rm  fo         r    "cata      log".
     *
     * @re    turn the v  endo r term for  "catalog"
     * @exception SQLExc  eption if a          database access err   or o  ccurs
     */
     String getCatalogTerm() thro  ws SQLException;    

    / **
     * Ret    rieves whether a     catalog ap    pears at the start of a fully qualified
          *    table     na me.  If       not, the catalog appears at the end.
          *
     * @return <co        de>t    rue</code> if the cata  log name     appears       at the b     eg            inning
     *            o    f a fully qua           lified t able name; <code    >fal se</code> otherwise
           * @  exce     ption S QLExce       ption if a     database access error occurs
       */
     boolean isCa        talogAtSt a   rt() throws   SQLExceptio n;
   
    /**
      * Retrieves        t   he <cod        e>String</code>   t         hat   this        database use   s as the
     * s    eparator between a catalog and         t     able name.
     *
               * @return the s    eparator string
                      * @      exc   eption SQLException   if a databa          se  access erro     r  occur       s
               */  
            String    ge    tCatalogSep arator() throws SQL      Exception;
     
    /**
     * R    etrieves whet her a schema na      me can   be    use    d in a data ma    nipulation    statement.    
     *
     * @r etu    rn <  c    ode>true</code>       if so; <code>false   </code> otherwise
     * @e     xception SQLException i   f a data   base a  ccess error occurs
     */
    boolea    n supportsSchemasInD   ataMan ipu        lation()  throws SQLExc  eptio   n;

    /**
        * Retr  ieves    whethe                r a schema name ca  n    be us  ed   in   a p    r  oc edure         call state   ment.
     *
     * @return       <code>true</code            >        if so; <code>false< /code>    oth   er  wise
     * @e         xception    SQLEx   ception if a database acces     s e     r   ror occurs
     *     /
        boo   lean   supportsS        che    masInProcedureCall                  s() thr  ows SQLExce  ption;

                    /     **
     * Retrieves whether a schem  a name        can   be      used in a   tabl   e   definition    statement.
          *
        * @retu    rn              <code>tr   ue</co   de> if so; <code>fa    lse</  cod   e> otherwise
     * @ex    ception     SQLE   x  ception i         f  a dat              abase access   error occurs
          */
            boolean supportsS c      h    ema      sIn TableDefinition   s() thr    o   ws SQLExcept          ion;

    /**
     * Retrieves whet      her a    sc    h    ema name can be use   d in an index definition statement.
     *
     * @return <code>tr u   e</code> if so;      <   code>fals e</code   > otherwise
        * @exception     SQLExc    eption if a      databa   se acces  s error o  ccurs
       */
      bool                   ean supportsSch    emasInIndexDefinitions(   ) throws SQLException;

    /* *
        *   Retriev             es whe       t her a schema name can be us ed in a privilege definition sta t      ement.
       *
     *    @return <c   od     e>true</code> if so; <    code>     false</code> otherwise
         * @e     xception SQLException if a database    access err   or occurs
     */
        boolean sup          p  ortsS     chemasInPrivilegeDefinitions    () throws SQL Exception;

    /**   
        * Retrieves whethe      r a c  atalog name can be used in a data manipul  ation    s     tatement.
     *
     * @return <      co d     e>true</code> if so; <code>         false</code> othe  rw    ise
       *         @exce        ption SQL  Exception      i   f a database access error occurs
     */
    boole   an supports  Catal   o   gsInDa  taManip   ul      ati     on() throws SQL      Exception;

    /**
     * Retrieve     s whether a catalo    g name can be      used in a procedure call stateme  nt.
          *
     * @return <code>true</code> if so     ; <c   o    de>     fal       se</code>         otherw ise
     * @exceptio   n S    QLEx      ception if a database ac        cess error occurs
     */
    boolean suppo    rtsCa t      alogsInProcedureCall     s() throws       S  QLExc    eption;  

           /**
     * Ret  rieves whether a catalog name can b   e     us ed in a table definiti      on s  tatement.
     *
             *     @return <c  o de>true</code> if so;    <code>f   als e</   co    de> otherwise
     * @exception SQLE xception if           a     dat   abase access error occurs
         */
    b     oole     an    supportsCatalogs    InTa    bleD    efinitions( ) throws SQL     Exception;

          /**
                  * Retrie   ves whether    a catalog name can       be used in   a    n index defi niti       o        n         st             atement.
                *
     *  @return <cod e >true</code>   if  so; <code>false</cod        e> otherwise
     * @e   xception S         QL    Exceptio n if a database access er                 ro  r occurs
        */
    boolean supportsCatalogsIn           IndexDef    init   ions    () throws SQLException;   

    /*     *
     * Re   trieves      whether    a cat     alog name ca        n      be use       d in a      privilege    definition   s  tatement.
     *
       * @ return <code>t  rue</code> if so;      <code>false</code> oth erwise
     * @exception      SQLException if a datab   ase access error    o  ccu               rs
          */ 
         bool      ean   suppor     t   sCatalogsInPrivilegeDefin      itions() thro              ws SQLExcepti on;


    /**  
       * Retrie  ves whether th  is       database s    upports positio   ned <cod e>          DELETE</co    de>
                  * statements .
            *
     * @ return <code      >true</cod e> if so; <code>false</code> otherwise   
         * @e  xc  eption   SQLException if a da                 tab    ase access error  occurs
     */
        boolean suppo rtsPositionedDelete()   thro       w     s S               QLExce p           tion;
       
               /**
         *   Ret  rie ves       whether this dat  abas        e  support    s posit   ioned <       cod    e>UPDATE</code>
         * statements .
     *
         * @return <code>true</co    de> if so;         <code>f        a   lse</code> ot     herwise
            *    @exception SQLException if     a da            tabase access error   oc    curs
       *         /
    boolean supportsPo sitionedUpd   ate  () throws       SQLExcept  ion;

              /*    *      
     * Re   trieves whether this               database su  pports <co  d       e>SELECT FOR UPDATE<    /co     de>
           * s   tat      ements.
     *
         * @return <code>true</cod    e> if so; <cod   e>false     </co     de> otherw   ise
       * @exception SQLExc e      p  tion i     f a database ac       cess er    ror occurs
        */
    boolean suppor          tsSelectFo   rUpd     ate() throws                 SQLException;

      /**
     *      Retrieves whether      t    h   is dat  abase supports stored procedure    calls
        * that       use t  he s      tor   e d    pr         ocedure escape syntax.
     *
     * @  return <co de>true</code> if so; <code>fals e</co     de> otherwise
                 * @ex   ception SQLException   if a databa       se acc  es  s error occur          s
     */
     boole an supportsStoredProce     dures() throws SQLExc eption;

    /**
               * R   etrieves whet    her this database supports subqueries in comp    arison
            *     e          xpressions.
     *
     *    @return <code>true</code> if so;    <   code  >false</code> otherwise
     * @e   xcepti          on SQLExcepti    on if a database acce    ss       erro      r   occurs
         */
    boolean       sup     po    rt       sSubqueriesInCompa     rison   s   () throws           S  QLE    xception;

          /**
     * Retr ieves whe the    r this   databa        se   supports subqueries in
            * <code>EXISTS<  /  code> expre     ssions.
     *
       * @return     <c     ode    >tru e  </code> if so;    <code>false</co       de> otherwise
     * @ exception SQL  Exc      e       p        ti   on if a data    base a   cce  s s error occurs
      */
    bool    ean sup    portsSubqu    eriesInExists() throws SQL    Excepti    on;

         /**
     * Retri    eves     whet         her this database supports subqueries in         
     * <code>    IN<   /code>  ex pressi     ons.  
              *
     * @return <code      >t rue</code> i      f so; <c  ode>f  al  s   e</code> otherw   is      e
     * @exception SQLExc  eption i      f a da       tabas      e     access er       ror occurs           
           *      /
         boo    lean   supp   ortsSu               b            queries     InIns() th   row      s SQL  Exception;
 
        /**
         * Retri  e    ves w he ther t  his  database s  uppor    ts subquer     ies in  quant        ified
       * e    xpressions.
             *
                            * @return <   code    >true</co de> if so; <co de>f alse      </code>        otherwise
     * @exception SQL  Exception if a data  base acces      s error occ  urs
       */
    boolean supportsSubqueriesInQ     uantifi   eds() throws   SQLExc   eption;

       /**
      * Re     t r   ieves    wheth   er t    h   is database s    upports     correl       ated subqueries.
       *
               *   @ret   urn <code>true</code>   if so; <code>false</code> ot h   er     wise
                * @excepti    on S     QLException if a datab    ase ac cess error occu rs
     */
       boolean suppor  t sCo   rrelate dSubqueries() throws SQLEx ception  ;

    /**
            *       Retrieves whether     th          i     s dat abase  support     s S Q L <code>UNION</co       de>.
     *  
        * @return <co           de>true</cod        e> if    so;   <co  de>false</    code                   > oth    erw       i     se
     *  @exce  ption    S   QLException i            f a database access      error occurs
       */
    boo   lean supportsUnion() throws    SQLExc       eption;

    /**
               *  Retrieve  s      whethe      r thi s database sup     ports           SQL <code>UNION ALL</cod  e> .  
                       *
        * @r    et      urn             <code>true   </code> if so   ; <code>false</code> otherwis    e
     * @exception SQLEx  cep tion   if a database access error o    c  curs
     */
    boolean supportsUnionAll() throws SQLExce ption;

    /**
     * Ret     rieves whet    her     this database sup  por   ts keepin    g cursors open
                 * ac     ross com  mit   s.
     *
     *         @r   eturn <code>tru  e</code> if      cursors always  remain       o  pen;
      *            <code>       fal se</code   > if             they    might n         ot remain open
                           * @    exception SQLExc     ept ion if a dat          abase  access error occurs
     */
    boo     lean sup         p or         t      sOpenC ursors AcrossCom     m it()            throws SQL   Exception;

    /**
     *   Retrie  ves whether        th    i s database s   upports  keeping cursors   open
          * across   rollb   acks.
     *
     * @retur n <   code>true<    /code> if    cursors always remai  n     open;
     *              <code> f    alse</c   ode> i      f they might not remain open
     * @exception SQL Except     ion if a database acc  ess e      rror occurs
              */
       boolean suppor    t      sOpenCu   rsor  sAcrossRollback      () throws SQLException;

    /**
           *  R et rieves w  hether this database supports keepi  n      g statements open
           * across commits.
     *
     *   @return <co    de    >true</ code> if state ments always rem  ain  open;
      *       <code>                 fal     se</ c            od  e> if      t  he  y      might not  remain   open
        * @excepti on           SQLEx  ception if    a database acce     ss err  or occ  urs
           */
          boo  lean support    sOpenStatementsAcrossCommit(   ) thr    ows SQLException;

    /**
       * Retrieves wh  eth   er t his database sup  ports keeping statements open
          * across         rollbacks.
           *
     * @return < code>true </code> if  statements alw      ay  s remain open;
     *           <   code>f      alse</code> if        th             ey migh   t      no      t     rem  ain open
        *       @e xception          SQLEx    ception if a database a  c     cess er   ror occurs     
     */
    boole  a   n supportsOpenStatementsAcrossRollback() th    rows SQLException     ;



    //-         -    --            -----         -   -----     -    --------------   ---------------          ---   -----------------  -- ---
    // The fo  llowing group of    methods exposes various       l            imitation    s
    // based on the         target database with the     curren   t driver.
    // Unless other  wise spe cified, a res      ult of zero mean s  there is no
    // limit,                     o   r the limit                    i  s no           t kno       wn   .

    /**
     * Re   trieves th  e   ma    ximum number   of hex characters this database allows      in an
     * inline       b     inar      y literal.
     *
     * @ret   urn    max the m  aximum length (in hex characters) for a bi  nary litera     l;
        *      a re      sult of zero means    that there is   no limit                   or the  limi  t
            *          is not known
        * @exception  SQLException     if    a d     atab  ase acc  ess erro     r o   ccur    s
     */
    int g  etMaxB  inaryLiteralLength      () throws   SQLExcept               i   on;

        /**
     * Retrieves the maximum num   ber of characters this da   tabas   e allows
        * for a chara cter lit  eral.
       *
         * @return the maxim  um number          of    cha  rac  ters   allowed f   or a   cha   racter   literal;
      *       a r esult of zer     o means that  the  re  is no   limit or    the limi     t   i    s
       *      n     ot known
              * @exception SQLException if a dat      aba     s    e access error occurs 
        */
    in       t getM   ax   CharLi    te      ral     Length() throw     s      SQLException;
   
    /**
     * Retrieves th   e maximum number o  f characters this dat  abase   a llows
     * for a  column name.
         *
     * @return     th       e maximum nu mber       of characters al      lowed     for a co      lumn   n  ame;
     *           a r    esult of   z    e        ro me  ans that    there is no limi              t or the l   imit  
      *             is not                known
              *     @exce    ption SQLEx      c    eption              if a database acce   ss error   occurs             
                  */
    i   nt    getMaxColumnNameLength() thro  ws SQLExcept ion;

    / **
     * Retrieves the maxim um nu  mb er of     columns this da tabas   e allo       ws    in a
     * <code>GROUP BY</code> clau    se.
     *
       * @r    et   urn    the maximum number of colu      mns all   owed;
       *           a res          ult  of zero me   ans that t   here    is no li  mit   or the limit
     *                  is      not known
      * @   e      x   cept      ion SQL         Excep   tion    if a da   ta     base access error occurs
     */
     int ge  tMaxColumns InGroup   By() throws SQLException;

        /**     
        * Retrieves the maxim            um numbe   r of colum   ns this dat   aba   s   e allows in an index  .
     *
     * @return the     maximum number      of columns al        low  ed;
     *          a   resul t of       zer  o means th  at there is n  o limit or the limit
           *            is not kn own      
          * @exception SQLException if a d  ata   b        ase acces  s e         r   ror occurs
     *   /
            int ge   tMaxColu     mnsInI ndex() throws SQLException;  

    /**
     * Retrieves  the max imum    number o  f columns thi      s datab                      ase  al  lo  ws    in an   
         *    <code>ORDER BY</cod    e> clau se.
     *  
            *            @retu  rn the maximum number     of c  olumns allowed;
         *      a   resu    lt            of z   ero means that    th         ere is no limi  t or th e limit
     *                  is not known
     * @exceptio  n SQLExc   ep       tion if a databas   e   a     ccess err    or     oc       c   ur s
                */
    int    ge tMaxCo  lumn   sInOrderBy() t   hrow s SQLE   xception;

             /   **
     * Ret  r   i   eves t he    ma     xim   um number of c  olu mns this datab   ase allows i  n a
     * <c   od            e>SELECT</  c        ode> li      st.
        *
         * @return the maximum n           umber  of column       s allowe                  d; 
     *      a result of ze  ro mea    ns tha      t there is no limi    t or the lim   it
     *       is not known
     *      @exception SQL Exception if                a da        tab      ase     acce    ss error occurs
        *     /
          int getMaxColumnsInSelect() throw    s SQ  LExce   pt      ion;

              /**
       *    Retrieve  s the m     a     ximum num   ber of columns th   is           database  allow s in   a ta  ble.
          *
     * @return the     m ax  imum    numb       e       r      of      col umns allowed;
           *      a result   of zero  means that   there is no l   i          mit or th      e li  mit
     *            is   not known
           * @ exception SQLExce  ption if a da        tabase access     err    or occurs  
     */  
    in           t getMaxColumns   InTable    () t       hr  ows S  QLEx    ception;

    /**
     *    Retrieves        the maximum number o  f concurren      t c       on  nections to this
     * database that are pos    sible.
     *
             * @return the maximum n umber           o   f a  ctive      connectio  ns po  ssible at   one time;
     *      a resu  lt of zero mean    s  that there is no     limit      or the         limit
     *                    is not known
               * @exception    SQL   Except  ion if a database access error oc curs
            */
        int getMaxConnecti         on  s() throws       SQLExcept   ion;

    /**
     *      R   etrie    ves the maxi  m        um    number of  characters that this data  base allows in            a
         * cursor name.
     *
        * @return the maxim    um      number of c  har   acters allowed in a cursor name;
           *      a r esult    o   f zero means     that     there is no l     imit     or th  e limit
             *      is not known
     * @exception    SQLException if a database a ccess     erro      r o        ccurs
     */
       int g  etMaxCursorNameLength() thr    ows SQLExc             ept    ion;

    /*    *
          * Retri   eves  the m ax   imum nu mber o    f bytes th     i     s d    a  taba  se allows for an 
     * index, inclu ding all of       the parts of the index.
     *
              * @r etu        rn th  e maxi      mum      numbe  r             of bytes allowe   d    ;    th   is lim   it incl  udes th  e
             *      c     ompos   ite of all the c     onst  itue     nt parts of t  he   index; 
     *      a res    ult o  f zero mea  ns that there is no lim  it or the li    mit
     *      is                not kn      own
     * @e   xcept      ion SQLExce    p   tio      n if a dat   abase acc  ess error occurs
        */
         int ge     tM           a       x   Index    Length() throws SQLExcepti   on;

    /           **
        * Retrieves the max imum number of ch   a racters that this databa  s      e allows in       a
     *            sche  ma name.
     *
         * @return the maximum numb er of ch   aracters    al lo wed in    a schema name;
     *                a  result of zero   means that the   re i    s    no    li      mit or   the        limit
            *             is not        known
     *       @          exc            eption SQLException       i      f    a da       tabase ac    cess    err o  r occurs
     */
              int g     etMaxSchemaNameLength      (    ) throws SQLEx cept ion;

           /**
     * Retrieves the   maximum num ber  of    char   acters that thi         s databa   se all     ows i      n          a
     * procedure    n     a        me.
           *
     *         @return the  m  aximum numb     e  r of      characters allow   ed in      a proc  edure name;
      *        a result o f zero mean  s tha   t      there  is     no limit o         r the   limit
                   *                is n  ot      known
              *    @ex     cept ion SQLE xception        if a database access  error    occurs
     */
    int getMaxProcedureNameLe    ngt      h() throw  s SQ     LEx   c      eption;

    /** 
      * Retrieves the max          imum  num      ber of characters th              at         thi   s da   tabase al    lo          ws in a
     * catalog name.
      *
        * @return  th  e       maxim          um number   of characte      rs allowed in a              catalog name;
      *      a   result of     zero mean  s that there is no limit or the li     mit
     *      is no   t kn own   
        *     @      exception SQ LEx       ception if a data ba    se acc     ess  error     occ       urs
             */
       int getMa xCatalog      NameLeng  th    () throw            s     SQLException  ;    

     /**
        * Retr   ieves the max   i  mu    m nu     mber of bytes t hi s da              ta   ba    se allows     in
     * a        sing le row.
     *
     * @return the maximum num     ber of   bytes allowed for a          ro w; a res  ult of
            *                 zero means th              at there is  no       l  imi   t o r the lim  it is n ot kno  wn
       * @except      ion S   QLException if a database a       ccess     erro   r occu   rs  
        */  
       int getMax Row           Size         () t    hrows SQLExce   ption;

      /**
           * Retrieves whether      th      e ret    ur   n va   lue for the me    th         od
     * <c ode>getMaxRowSiz    e</co  de> include     s the       SQ  L    data types
     * <code>L   ONGVARCHAR</co   d   e>      and <code>LONGVARBINA   RY</code  >   .    
       *     
     * @return <code     > t   rue</co  de> if so  ; <code>false</code                         > otherwise
       * @exc    eption      SQLExce     p                     tion if     a database       ac    cess error    occurs
             */
    bo   olea  n d         oesMaxRow         SizeI  nc  lu     deBlo   bs() throws    SQLExce pti     on;

           /**
      * Ret rie     ves the maximum n   umber       of charact             ers this datab         a        se al  lows i     n
     * an SQ      L statement.
        *
     *      @r   eturn t he maximum        num          ber of c   haracters al   lowed for a   n               SQL statement;
     *      a result of zero means         that         ther      e   i   s no limi   t o  r the limit
       *                    is   not kn  own
         * @excepti     on SQLE  xcep             t   ion if a   dat   a    base ac   ces     s error occurs
     */
    int getMaxStatement     Length() t         hrows SQLExcep   ti    on   ;

    /**
     * Retrieves t    he maximum numb   er of active stateme  nts to this datab  ase
     * that can be open at the same       time.
     *
     *          @retur   n the ma                  ximum nu  mb    er of s                  tatements tha          t        c  a     n be o  pe   n at one     ti         me;
     *          a r  esult of   zer    o me                ans th at th     ere     is no limit   or the limit
          *         is not known  
     * @exception S   QLExceptio        n if a database access error occurs
          */     
    int getMa  xStatements() throw        s SQLExc ept        ion;

      /*  *
        * Ret  rieves the m     aximum number of cha rac  ters this database al      lo  ws in
         * a ta ble      name.
     *
     * @retu rn    the maximu m n        umber of characters allo         wed f     or a tab   le n ame;
          *      a     result of zero means     th                 a  t t  her         e is no limit or the limit
     *            is not known
     * @exce  ption SQLExcepti   on if a database access err  or    o   ccur       s
         */
          int getMaxTabl   eNam    eLe ngth() th rows S QL Exception    ;

     /**    
       * Retrieves the maximum num be   r of tables this d  atabase   allows in a
            * <code>SELE             CT</cod   e  > statement.
          *
     * @return the m  aximum numb      er of tables allowed i     n a <code>     SELEC T</code>    
     *         state  m ent   ; a result of ze  ro means that th ere is no   l       imi     t        or
     *                       the limi  t   is not know      n
     * @ exception SQLExc      eption    if a d       atabase acces  s        err  or o ccurs       
          */
    int getMa   xTablesInSelect(   ) throws SQLExcept          ion;     

    /      **
                   *    Retrieves the maxi  mum number of c ha     r  acters this         database allow            s in      
      * a   us        er name.
     *
     * @ret      urn      the maximum    numb er o   f     c harac       ters all owed     for     a use    r   name ;
     *      a         result of       zero m    ea          ns tha         t   there is no     limit or       th       e li    m           it
          *        is          no         t       known
     * @exception SQ  LEx    ce  ption if a database acce  ss err       o      r occurs
     */
           int getMaxUserNameL      ength()    throws      SQLE         x   ception;
  
       //-----      --------------  -     -------   ------------  ------------------------- ------

                     /*        *
     * Ret           rieves this database's default tra   nsaction isolation le    vel.  The
      * possibl       e v             alues are     defin ed in          <code>jav        a.sql.Connection</code>.
           *
     * @return    the default isolation le   ve   l
                   *   @exception SQLExc  eption if a d  atabase access error o             c  curs
     * @s       ee     Connection
             */
    int     ge    tDefa    u   ltTr ansactionIso   latio  n(    ) throws SQLExcep   tion;

    /**
     * Ret    rieves whether th   is  datab       ase supports tran   sactions. If not           , in      voki ng the
          * method  <code>commit</code> is a noop, and the isolation      l        ev   el is
      * <code>TR   ANS ACTIO       N_NONE      </code> .
     *
          * @retur n <code>true </code> if transact  ions                   are    su  pp       orted;
          *         <cod    e>false</code> otherwi    se
     * @exc    eptio     n        SQLE   xception if a d atabase acc       e    ss e      rror oc   curs
       */
    boolean             supportsTransactions() throws SQ    LEx  ceptio    n;

    /**
         * Retrieves whether   this database         suppo     rts the given transact     ion iso  lation        leve  l.
     *
                          *         @param level    one of th  e    tr    ansaction isola     tion      levels defined in
          *                <cod  e>ja     va.sql.Connec  tion</code>
               * @   return <code>tr                 u  e</code   > if     so; <code    >false</code>          otherwise
     * @e  x  ception       SQLExce      ption  if a d   atabase ac   cess er ror occurs
     * @se e Con nection
     */
    boolean supportsTransactionIsol       ation   L     evel(in  t    level)
        throws S     QLException;

    /**
          * Retrieve   s wh      ether thi s database suppor   ts              bot h data      d     efinition           and
              * data manipulation sta     temen     t s within        a trans    action.
           *
     * @return <cod   e>true< /code>     if so;   <code>false</code>  otherwise
     * @ex     ception SQLExcept   ion if    a database  access error occu rs
     */
    boole   an  supportsDataDefi   nitionAndDataM  anipulationTransactions      ()
         throws SQLExcep          ti    on;
    /**
          * R       et ri  eves whether thi  s database supports on ly data manip    ulatio  n
     * s   tatemen  t s with     in a transaction.
     *
          * @return <code>true</co       d     e> if so; <cod      e>fa    lse</code> otherwis       e   
     *      @exception     SQLExcept ion if a databa se acce    ss e     rror          occurs
     */
            bool   e  an suppor     tsDa      taM  a     nipulat      ionTra             nsactionsOnly(  )
        throws  SQLExcep    tion;

    /**
          *          Retrie ves whether a data definition statement within a   tra     nsaction f       orces
     * the transact   ion to commit.
     *
     *      @return <c ode>t   rue<         /code>      if      so; <cod  e>false</code   > otherwise
       *     @exce  p t   ion     SQLException if a    da        t     abase access er ror occu   rs
     */ 
    boolean   dataDefi       nit  ionC  ausesTran  sactionCommit()
            thro    ws SQL Exc epti    on;

    /**
        * Re   trieves whether thi      s da        t abase ignores     a      data d     efin    i  tion s   tat     em   ent
             *  within a      transac     tion.
                      *
       * @  r     e        turn               <code>true<    /code   > i f so; <code>f   alse</cod       e> otherw   i  se
     * @exce  ption SQLExcept   ion if a database     acce ss e       rror       o    ccurs
       */
     bo  olean    dat    aDefinitio     nIgno    redInTr     an        s    act      ions()
            t           hrows SQLException;
   
    /**
     *   Retri   eves a descri         ption    of           the        stored procedures av        a  ilable in the     given
      * catalog.
     * <   P>
       * O    nly procedure descrip   tions matching the    sche  m   a and
        * procedure     nam  e cri  teria ar  e return  ed.  T      hey    are ordered by
     * <code>PROCE   DURE_CAT</code>,   <code>PRO  C   EDURE _SCHEM</c     ode     >,
     *        <c        o     de>PR   O      C EDURE_NAME</code> a  nd        <c       ode>S  PECIFI      C    _ NAME</code>.
     *
     * <P>Each procedure d  escription h   as the the   fol lowing columns:           
     *  <OL>
     *     <    LI><B  >PR   O     C         EDURE_CAT</B> Strin   g {@cod   e =>} procedure catalo   g (may be <c ode>n   ull</code>)  
          *     <LI>    <B>PRO   CEDU     RE_   SCHEM</     B> String {@ c    ode          =>}   pro  cedure schema (may b  e <code>null</code>)
     *  <LI><B>PROCEDURE_NAME</B>     String {  @code =>       } pr   ocedu   re name
     *  <LI> reserved for future   use
             *      <LI> reserved for fut      ure use
     *     <LI> reserved f               o r fu        ture use
     *  <LI><B>REMARKS</B> String {    @code =    > }       expl     anatory comment o  n    th  e procedure
       *  <LI     ><B>              PROCED   URE_      TYPE</B> short                {@c    o         de     =>}    kind    o f procedure:
                 *         <UL>     
     *          <L      I> procedu   reResultUnknown -   Cannot determ ine i   f  a return value 
     *       will be returned
     *           <LI> p     rocedureNoRes      ult -         Does not return    a     re turn value
     *       <LI    > procedure   Ret  urnsResult     - Return   s a     retu     rn value
     *        </UL>
     *    <LI><B>SPECIFIC_NA  ME</B> String  {  @code =>} The na    m              e  which u      n   iquely i   dentifie    s          th    is
     * procedure  within             its sch       ema.
     *  </OL>
             * <p>
     * A user      may not have pe                      rmissions to execute any of the procedures     that are
     * retur   ned by     <code>      getProcedures</code>
           *
       * @param catal       og a catalog     na me;   must match the                        catalog name as it
     *          is   s   tored in the da   tabase; "" retrieves  those wi t            hou    t a  c         a   talog;
       *              <code>null</code> means that the catalog name should not be used to    narrow
      *        t    he  search
              * @pa   ram schemaP        attern a    schema name pa  tt  ern; m  ust match   the schema name
     *        as i  t is sto         red in the              database; ""   retrie  ves those without   a sche   ma;
     *        <code>       null</code> mea   ns that the schem      a name should  n    ot b     e used t               o      narrow
     *                 th   e search   
                    * @param     procedureNa        mePat    ter n a pr          ocedu  r e name  pattern;   must m  a  tch the
            *           pr      ocedure name        as      it i      s       st     ored in the databa se
              * @re   turn <code>Resu         ltS et</c ode > - each row is   a procedure des    cri       ption
         * @excepti  on SQLException if a data   base    acc ess e         rro  r oc    curs
     * @se     e #g          etSearchStringEscape
     */  
    ResultS             et getProcedur es(S tr  ing catalog, String    schemaPattern,
                                   Strin      g p   rocedureNamePatte     rn) throws SQLException  ;

    /**
      *    I n    dicates that it  is not known    w   hether t      he    pro     cedure returns
     * a result.
         * <P>
     * A p      oss       ible value f  or column <code>P   ROCEDURE_TYPE</code>   in the
     * <c  ode    >Resu   ltSet</code       > object returned by the    m     ethod
     * <code>          getProcedur   es</code>.
        */   
    int procedure      Resu lt  U   nknown          = 0;  

             /**
                      * Indica  tes that      the pro    cedure d   oes not retu        rn a result.
          * <P>
              * A possible         value for c olu  mn <        code     >P            RO     CEDU     RE_T   YP   E</code> in the
              * <     code>ResultSet</code>  object returned by    the met   h     o  d
     * <code>getProcedures<     /code>.
     */
    int                proc    edureNo Result               = 1;

                        /**   
        * Indicate       s that the p  roced  u  r     e returns             a res      ult.  
     * <P>
     * A possible value   f  or co    lumn <code>        P   ROCEDURE_TY   PE</code> in the
         *   <    c ode>ResultSet  </code> object re      turne       d by the method
     * <c     o  d    e>getProc      e dures</cod    e      >.
         */
    int   procedureReturnsResult  = 2;
     
         /**
             *       Retrieves a description      of the gi  ven catalog's stored proc       ed ure p arameter
     *           and result columns.
     *
      * <P>O   nl     y      descr    i  ptions matc        hin   g the        schema, procedure    a    nd
     * parameter na  me    criteria are returned.  They are ord    ered b    y
      * PROCEDURE_CAT, PROC   EDURE_ SC  HEM, PROCEDURE_NAME and SPECIFIC_NAME. W    it     hin this, the return val   ue,
      *     if    a ny, is first. Next are the parame ter descriptions in c       all
     *     orde   r. T    he column descriptions                foll   ow in c             olum n number or  der.
      *   
     * <P>Ea    ch row in the      <code>ResultSet</code  >   is a                        parameter descr    i    ption or
       *    column    des   cription  with the fol    lowing      fields:
     *  <OL>
       *  <LI><B    >PROCED   URE_CAT</B   > String {@     code =>    }       procedure    catalog (may be <code>n  ull</code>)
             *       <L I><B      >PROCEDURE_SCHEM          </B> String {@cod  e =>} procedure    schem          a (   may be <c   ode>   n          u   ll<    /co         de>)     
     *         <LI><B>PROCEDURE_NAME</    B> String {@cod   e =>} proced        ure name
     *  <  LI><B>     COL  UMN_NA ME  </B> Stri   ng {   @code   =>} column   /  parame       ter    name
          *  <LI><B>C      OLUMN_TYP           E</B>  Short {@code =>} k   i         n  d of column/paramet          e     r:
     *                 <UL> 
     *      <L     I> pr    ocedureColumnUnknow   n - nobo     dy knows
        *      <  LI> proc       edureColumnIn -    IN paramet       er
       *      <L  I> procedu  r   eCol   umnInOut      - INO  U       T par  ame       ter
          *        <LI> procedur  eC olumnOut -      OUT pa  rameter
         *         <LI>    procedureCo     lumnRetu   r  n - procedure return value
     *      <LI> procedur     eColumnResult - r     e    sult c olumn      in <code>   Re   sultS    e   t</co   de  >
     *          </U      L>
          *     <  LI><     B>DATA_TYPE<   /B > int    {@cod   e =>} SQL      type     from     java     .sql     . Types
             *  <LI              ><B>TYPE_N    AME<    /B> Stri     ng {@    code =>} SQL type name,       for a U  DT type the  
     *           type n      ame is fully  qualifi       ed
     *     <LI><B>PRECISION</B> int {@code =>} prec     ision
     *  <L    I><B>LENG     TH</B> int { @code      =>}      le     ngth in by               tes of data
                *  <LI><B>SCALE</B> s         ho  rt      {@code =          >} scale   -  null is re   turned for data   types         wh    ere
     * SCAL     E i   s not applicab       le.
        *  <LI><B  >RADI     X</B>  short {@co   de =>} radix         
        *  <LI><B>    NULLABLE         </B>   sho   r t {@    co  de =   >     } can i   t co      ntain NULL.
     *      <UL>
              *      <  LI> procedureN oN   ulls -        does not    allow NULL values
     *      <LI> procedureNul    lable - allows      NULL    va       lu        es
     *           <LI> pr      oce  dureNullableUnknown - nulla   bility unknown   
         *       </UL>    
          *  <LI><B>REMARKS      </  B> String {@code =>} comment describing parameter/c  olumn  
            *  <LI><B>COLUMN_DEF</B>  S     tri  ng {@co        de =>} d    efault value for the colum   n   , which    sho   uld be interpre t  e     d as      a string w       hen the value is en     cl     osed i   n singl     e q   uo   tes (may be <code     >n  u  ll    </code>)
         *                <UL>   
     *               <        LI    > Th  e string     NULL     (not enclosed i  n    quotes) - i    f NULL was   spe    cified a     s the       de   fault value 
     *      <LI>     TRUNC         ATE     (no   t encl                 osed i     n quo   t    es)                               - if the speci          fied de     fault value cannot be   represented    wit h   ou       t trun    c   ation
     *          <LI> NU  LL                                          - if         a defa   ult valu   e was  not   sp         ecified
             *          </U L>            
     *  <    LI><B>S      QL_DAT  A _T     YPE</B> int  {@code =     >} reser  ve    d f or future use
     *      <    L   I><B>SQL_DATE  TIME   _SUB</    B> int  {@code =>} rese   r ved f    o       r  future    use
     *    <LI><B>CHAR_OCTET_LENGTH</B>       int      {     @code  =    >}   th  e maximum len        gth       of          binary and   c  haracter based    colum   n   s.  Fo                r any other da  tatype t  h    e r      eturn     e         d value      is   a
     * NULL
      *  <LI><B>ORD   INAL_POS  ITION</B> int    {@ code    = >} the ordinal p   osition, s  tar     ting f    rom 1, for t        he     inp   ut and output  para          meters for a   p   rocedure. A va lue o      f 0
       *is ret   urned       if this ro      w d   escribes the procedure's return value.  For result set colum     ns, it                 is      the
          *   ord   inal po  sitio  n     of th      e column    in the result se      t  st    arting fr      om  1            .  If    the     re are
          *multiple    result s  ets, the        column    ordi n  al positions are     impl     eme            n     tatio  n
        * d efi   ned.
          *  <LI>   <B>IS_NULLABLE</B>                    String  {@co  de =>}   ISO rules are us  ed to        det      erm    ine the nullability for a column.
     *                 <UL>
             *             <LI> YES                -      --        if  t   h  e   c   o       lumn can include   NULLs
     *                <LI> NO            --- if the co   lumn  ca  nnot include NULLs
     *       <LI> empty string  --- if the nullabi lity for th   e  
     * column is   unknown
     *           </U           L>
     *    <L  I><B>SPECI    FIC_NA     ME     </   B> Stri ng  {@c   ode =>} the nam     e which un      iquel   y identifies        this     procedur  e w   ithin    its schema.
     *  <  /OL>
       *
            * <P><B>Note:</B      > Som     e da  tabases may n        ot      re   turn the      colu mn
     * descri p    tions for a procedure.
        *
          * <    p>The PRECI    SIO  N  column  represents     the   specified    column     size for         the       given colu     mn.
      *     F    or n  ume     ric data    , th     i  s    is  t  h   e maxim     um prec  ision.  For    character data, thi  s is    t   he length in cha   racters.
               * For      datetime datat  ypes, this        is the leng   th in chara cters of the String  represen    t      ation     (assuming the
     * maxim  um allowed pre        ci  sion  of th e fractional seconds c  o    mponent). For binary data, this is th      e length in by     te                  s.  For t     he       R     OWID     dat atype,
         * thi   s    is the length in       bytes. Nu   ll     is re            turned     for data ty  pes where th      e
           * colu            mn size i         s     not applicab                 le.
      * @param c     a  tal og       a   catalog name; must   match the c   atalog   name as it
          *        is s        tored in the databas    e; "" retri   eves             t    hose    without a catalog;
     *                 <code>n        ul    l</co    de> means        that  the catalog name  shoul      d not be used       to     narrow
         *         the search
     * @     pa  ram s            chema   Pattern a sc hema name pattern;     must match t         h e sche     ma name
       *        a s     it is s    tore  d in the          database; "" re     trie  ves those witho  u     t a    schema;
     *        <code>null</code> means that t      he      schema name   should not        be used to   narrow
     *                  the search
            * @param pr    oc        edureNamePattern a pr           ocedure          name pattern   ; m    u    st match the
        *           proce   dure name a    s it is s tore d  in the database
        *         @param columnNamePat   tern a column name p  a       ttern;              must  match th   e   colu  mn name
            *           as it is stored in the database
         *    @return <code>ResultSet</   code> - each row describes      a stored procedure p arameter o              r
     *      col  umn      
     * @exception SQL   Excep tion if a database access  error oc  curs
                * @see #getS      earch   S       trin     gEscape
     */
            ResultSet        getProced            ureCo  lu  mns(St   ring cata  log,
                                                                 S   tr ing sche    maP att   e   rn,
                                                        Str    ing proce  du       reNa mePat tern,
                                                  S         t    ring     col   u  mnName P    attern) thr  ows SQL      Exc eptio     n;

    /**
        *   I  n  dic  ates that t      ype o     f th     e column is unknow  n.
               * <  P>
      * A poss                  ible     value  f  or         the      column
     * <c   ode>COLUMN_TYP E</      code>
     * in the < code>Result    Set</cod   e>
             *          retur      ned by  th      e method  <cod  e>getProcedureColumns</code>.
         */
    int p  r      ocedureColumnUnknown = 0;

          /**
         * Indica       tes    tha t  the col   umn st    ores IN para     meters.
        *         <P>
     *          A po              ssible    value     fo  r the column
        * <      c ode>COLUMN_TYPE<       /code>
     * in the <code>Res         u ltSet</code>
     * returned by the method    <code>getProc  edu     reColumns</code>.
             */
    int procedureColumnIn   = 1; 

    /**
     * Indi     cates that the    c   olumn  st   ore s INOUT pa     rameter           s.
     *     <P>
     * A possible       va     lue for th     e c   olumn
              * <code>COLUMN_TY  PE<   /code>
     * in        t he <code>Res           ultSet</code>
          * returned b y the me   thod <       cod  e>getProc    edureColumns</c     ode>.
                   */
    int   procedureColumnInOut = 2;

    /     **
                       * In  dicates t   hat the c     olumn st          ore    s OUT   para   meters.
           * <P  >
         * A      possible   value fo      r the column
     * <cod  e>COLUM    N_T   YPE<  /code>
      * in th e   <code>Res u      ltSet</cod e>
    * retu    rned by          the method <code>    getProcedureColum              n        s   </co de>.
       */
    int pr          ocedure    Colum     nOut =    4;
    /**
      * Indic                ates that the col   u  m   n stores return values.
            *          <P>
     * A possib  le value   fo   r         the column
       * <code>COLUMN  _T    YPE</code    >
     * in the <code     >  ResultSet</code>
       * returned by      the m         e     t     h  o       d <code>getProcedure   C  olumn      s</code>.
           */
      int   procedureColumnReturn = 5            ;
  
                /  **
                      * Indicates     that the    co lumn stores result         s.
        *   <    P>
        *   A   possible value for   the column
     * <co   de>COLUMN       _TYPE</code>
                *       in    t  he <cod  e>ResultSet</  code>
     * ret       u rned by t he method <code>getProcedureColum    ns</code>.
     */  
    int proce       dureColumnRe    sult =  3;

      /**
       *  Indi    c  ates that <code>N       ULL  </code> values are not allo    wed.
            * <P>
             * A possible value    fo  r the   column
     *    <code  >NULLABLE</code>
       *         i   n th    e <c ode>ResultS  et</  c        ode>      object
     *  re   turn  ed by     the method <c ode>getProcedureCo  lumns</c  ode >.
        */
    int      pro   cedureN      oNulls = 0;

    /**
        * Indic  ates th   at <code>NUL   L</code>                  values are allowed.
     *           <P>
     * A possible v    alue for      the column
             * <code>NULL  ABLE      </   cod       e>
             * i   n th         e <cod    e        >Resul  tSet</c  ode> ob      j   ect
        * returned    by        the method   <code>getProcedureColumns    </code>.
         */
    int procedureNullab  l  e     = 1;
    
    /**
             * Indicates that       whether <code>NULL</co           de> val  ues a   re allowed
     * is unknown.
      * <P>
            * A possible val  ue for        the colum n         
       * <code>NULLABLE</code>
     * in        the <cod e>Resul  tSet</code> object
            * r eturned  by      the method <cod      e>getProcedu    reColumns</c  ode>.
     *  /     
         int procedur eNullableUnknown =    2;


    /**
         * R   etr  ieves    a descr       iption of the     t    ables available in th          e give    n catal o       g.
       * Only t  able     descript     ions matching      t            he catalog, sc     hema, table
                    * nam   e and t       ype criteria are r           eturn   ed .  They are ordered by   
        * <cod      e>TABLE  _   TYPE</        cod    e>, <code>TABLE_CAT   </code>,
        * <c   ode>TABLE_SCHEM</code> a     nd <cod  e>TABLE_NAME<  /code>     .
     * <P>
               * Each table description has th   e f  ollowing co    lumns:
     *     <O  L>
     *  <LI><B>TABLE_C      AT</B     > String {@cod  e =   >   } table catalog (may b e       <   code>null</co  de  >)
      *    <LI><B>TABL E_SCHEM<  /B> Str  ing {@code =>}     table sc     he    ma (may be  <code>nul l</cod      e>)
     *          <    LI><B  >TAB   LE_NAME</B> String {@code =>} ta ble name
         *  <LI><B>T  ABLE      _TYPE<              /B> Str          ing {@cod               e =  >}   table type.  Typ                 ical         types are "TABL   E",
        *                                "VIE   W", "S     YST           EM       TABLE", "GLOBAL TEMPOR        ARY",
     *                     "L  OCAL TEMPORA  RY",       "ALI    AS", "S  YNONY     M".
            *  <LI><B>R  EMARKS<     /B> String {@code =>  }   explanatory comm ent on the     table
              *  <LI><B>TYPE_CAT</B> String {@cod   e =  >  } the types    catalog (may     be <code>n  ull< /    code>    )
              *  <LI><B>TY   PE_SCHEM< /B> Stri    ng         {@code =>} the types sch  ema    (may b     e <code>null    </code>)
      *  <LI   ><B>TYPE_NA    ME</B> String {@code =>} type n  ame   (may be <code>null</    code>)
         *  <  LI><B>S   ELF_REFE  RENCING_C O   L_NAM E</B         > String {@code =>} name of the    designated
         *                                   "identifier" column of a      typed t   ab  le (may b     e <code>null</    code>)
      *     <LI>       <B >REF_    GEN     ERATION</B> String     {@c  ode =>} specifies how values        i    n
     *                           SELF_R        EFE   R     EN                    CING_COL              _NAME a re cre        ated. Values are
     *                  "SYSTEM", "USER", "DERIV E  D".     (may         be    <code>nul  l</code>  )
         *  </OL>  
     *
        * <P><B>Note:</   B > Some databases      may not re  turn information for
     *     al l    tables.
         *
         * @param cata lo  g a          catalog nam        e;  must ma         tch the  catalog nam    e as it
       *           is stor         ed in the data   base; ""     retrieves  th      o               s    e    without a catalog;
     *              <code>null</code>    means that  the ca   tal  og    na       me   should not be             us       ed    to    n             ar    row
     *        the sea       rch
                          * @param     sch emaPatte       rn a schema name p  attern; must    match      the s ch      ema name
     *        as it is stored in the datab         ase; "" retrieves th ose without    a    sche              ma;
     *              <cod      e>null</co                d    e> m         eans that the sche   m     a name sh ould not b      e us    ed   to   narrow
     *          t  h e search
     * @param   tableNam       ePattern a tab  le    name pat    tern; must match the
      *        tabl e na      me as it is sto        red in the database
       * @param types a l      ist of table ty  pes, which must     be from      the   list of             table types
              *             return     e   d fr  om    {@link #ge       tT  ableType       s},to include; <    code>nu      ll</ co    de    > returns    
     * all types
       * @r     et       urn <code>Res     ultSet</code>        - each row is a     table description
     *       @exce    ption       SQLException if a database acce  ss error    oc       curs  
     * @see #getSearchS   tringEscap  e
     */
    ResultS    et ge tTables(String     cat  alog, String sch    emaPat     tern,
                                           Strin     g      t ableNamePattern     , S      t        ring types  []) throws     SQL         Exc   eption;

    /**
        * Retrieves    the sche        ma names availabl                  e in this database.  The result     s
     *           are o   rdered          by <code>TA BLE  _CATALOG</code> and
           *     <code        >T   ABLE           _SCHEM</code>.
            *
     * <P>        The sch      ema columns are:
     *  <OL>
        *  <LI><  B>    TABLE_S   CHEM</B> Strin   g {@code = >} schema name
     *  <LI>        <B>TABLE_CATALOG   </B> Stri   ng {@code =>}    ca     ta         log name (m     a       y be <   code>null  </code>)
        *    </OL>
         *  
              * @   re       turn a <       co    de>ResultSet </code> o  bj    ect in which e   ach row  is a
     *                  schema        de        scription
       * @ excep  t  ion SQ  LExceptio             n if a database access error      occurs
          *
     */
             ResultSet getSch         emas() throws S          QLExce        ption;

         /*    *
     *      R    etrie   ves the catalog names  availab     le in this databa    s   e.  The results
      * a  re ordered  by catalog n   ame. 
                  *
          *             <P>Th    e  c atalog      column is:
     *  <OL>
     *  <LI>    <B>TABLE_CA      T</B> St    ring   {@cod       e =>} ca     t       alog      na      me
          *  </OL>
     *
     * @   return    a <c    o   de>ResultSet</code> object in w    hich each row has a
          *                   single      <code>Str ing</code> colum  n that is a cat    al      og name
         * @exc     eption        SQLException   if a database access e   r  r or o       c          curs
           */
                     R esultSet getCa   talog     s() throws SQLExceptio  n;

    /**
        * Retrie   ve      s the t         able types available in thi s data      base.  The  r esults
                * are orde   red by table type.
     *
     * <P>The ta     b le type is:
       *          <OL>
        *  <LI><B>    TABLE_TYPE</B> String { @co           d  e =>} ta    ble typ      e.  Typical ty  pes are "T         AB     LE ",
     *                        "VIEW", "SYSTE      M    TAB     LE",   "GLO    BAL TEMPORARY",
              *                  "LOCAL    TEMPORAR Y", "ALIAS      ",     "SYNONY   M".    
                    *  < /OL>
        *
     *    @  return a <code>R esu      ltSe t</    code> object   in   which each row    has a      
      *               single <code> String</c   ode>           column  that is a tabl  e type
     * @exception  SQL   Exception  if a datab        ase access  error   o     ccurs
     */
    Res  ultSe   t getTab  leTyp  es()     throw           s SQLExce    ption;

    /*  *
     * Retri     eves              a des cripti   on of table colu   mn  s avail   able i  n
        * the specified  ca   talog.
           *
     * <P>Only co       lumn descript    ions mat ching the catalog        ,  sc      he     ma, table
             * and column n   ame criteri       a are    returned.    They are  or      dere d by
                   * <code>TABLE_CAT   </code>,<code>TABLE_SCHEM</code>,
     * <c             ode>T  A     BLE    _NA    ME</code>, and <cod  e>ORDINA L_POSITIO    N<  /code>.
     *
     * <P>     Each colum   n descript    i     on has th   e fo llo  wing columns:
     *  <OL>
          *              <L        I><B>  TABLE_CAT</B> String {@c    o d       e =>}  table catalog (ma  y be <code>null</cod        e>)
        *  <L      I><B>TABLE     _SCHEM</B>       Stri     ng      {@cod   e    =>} tab  le s      che ma     (may be <cod  e>null</cod   e>)
     *  <LI><B>TABLE_NAME<   /   B> String {@      c   ode    =>} table         na         me
            *  <LI><B>CO LUMN_N AME<    /B> St   ring {@       c          ode   =>} co lumn name
     *  <LI          ><B>DATA_T      YPE</B> int {@code          =>  } SQL type    from java.sql.   Types
     *  <LI><B>TYPE_NAME</    B>  String {@cod e =>} Dat     a source dependent ty  pe nam       e   ,
     *  fo  r a UDT th     e typ   e name is ful    ly qualifie       d     
       *  <LI><B  >CO      LUM N       _S       I   Z          E</B> int {@cod   e =>} colu mn size.
     *      <LI><B>BUFFER_LENGTH</B> is no   t used  .
     *  <LI><B>DE CIMA  L_DI   GITS</B> in   t {   @   code    =>} the  number of fractio      n          al digits. Null is returned for da            ta t    ypes where   
     *   DECIMAL_DIGI           TS is not applica      ble.
      *  <LI><B>NU M_PREC_RAD   IX   </ B> int {@code    =>} Ra   dix ( typ   ically ei   the         r 10 or 2)
     *  <LI><B>NUL     LABLE  < /B> int     {@code =>      }       is     NULL allowed.
         *      <      UL>
           *      <LI>     columnNoNulls     -    mig    ht not allow <   code>NU LL</co     d    e> valu es    
      *      <LI>                      columnN  ullable - defin   itely allows <code >N      ULL</code>   values         
        *      <LI> c     olumnNullab       leU    nknown - nullability unknown
       *                                  < /       UL>
     *  <LI><B>R    EMAR      KS<  /B> String {@c         od     e =>} comm ent     descri   bi ng    column (may be          <cod           e>nu   ll</code>)
     *  <LI><B>COLUMN_DEF</B> String {@co     de =>}          default    value   for the       col   u   mn, whic h sho              uld be in    terpreted as a strin   g when the   v      al  ue is enc    losed in single     quotes (may be       <code>null</c   ode>)
        *  <LI ><B>SQL_DATA_TYPE</B>        int {@code =   >}   unused
     *      < L   I><B>SQL_DATETIME_SUB    </B> int   {@cod    e =>} unused
       *  < LI><B>CHAR_OCTET_LENGTH</B>   int   {@   code =>}   fo  r c   har    ty  pes the
            *              maximum number o   f      by      tes    in               th                e  column
     *  <     LI><B>OR     DINAL_PO  SITION   </B> int {@code     =>} inde x of column in table
                           *         (   starti    ng a  t 1)
           *  <LI><B>IS_        NULLABLE< /B  > String       {@cod e   =>} IS   O r        ules are used to           determi   ne the   nullabilit   y for a column.
     *                        <UL>   
        *         <LI> YES           -- - if the column can in    clud e NULL   s
           *              <LI> NO                 - -- if     the co   lum   n cannot    include NULLs
            *               <LI> e mpty str     ing  ---    if the nullab  ility f  or t   he
            * column is u      nknown
         *       </UL>
      *     <LI><B>SCO      PE_CA            TALOG<    /B> String {@   code =>} catal      og of table   that   is the scope
     *          of  a reference att ribute     (<c    ode       >null</co     de    > if DATA_TYPE isn't REF     )
     *  <    LI><B>SCOPE_SCHE MA</B> String {@code =>} schema of ta     ble that i  s the    scope
      *      of a r           eference          attribute  (<code>null<  /code> i   f   the  D  AT            A_T YPE isn't RE  F)
            *  <LI><B>SCOPE_TABLE  </B>    String {@code =>}      table name that    t       his    the scope   
          *      of a re   ference       attribute (<code>null</cod   e> if the  DATA_TYP E  isn't REF)    
                   *  <LI><    B>SOUR  C          E       _DATA_TY PE<    /B   > short {@code =>} sour ce type of a distinct type        or user    -g   enerat    ed
     *          Ref typ e,               SQL type from java.sql.Types (< code>nu  ll</                c            ode> if     DATA_TYPE      
                     *      isn't DISTINCT o               r user-ge  nerated  RE   F)                         
     *     <L    I      ><B>IS   _AUTOIN   CREME     NT  </         B > String  {@code     =>} I  ndicates whether this  colum n is     auto increme       nted
     *       <     UL>
      *           <LI> YES               --- if the      co          l   umn is au       to incremented
                    *                <LI> NO               -    -- i f the colu            m         n is   not auto  incremented
     *           <LI> e     mpty str             in g            --- i     f it canno   t b    e determined w hether    the    column is auto incremented
     *          </UL>
       *   <  LI       ><B>IS_GENERAT   EDCO LUMN</B> Str   ing  {@code =>              } I   n   di   cat  es whether this i s a genera t ed  column
     *          <U       L>
       *          <LI> YES                       --      -  if t h  is a gen    erated   column
     *           <LI> NO                     --- if th  is not a gene         rated    colum  n
     *            <LI> empty stri    ng      ---        if   it can    not be determined whether this     i    s a generated colum   n
     *         </UL>
     *  </OL  >
       *
     * <            p>The COLUMN_SIZE column spe   c   ifies     the column s           ize   f   or the given c  olumn.
         *   Fo  r numeric data, this      is  the        maximu m prec    ision.    For ch aracter dat    a, this is the  length in c  haracters.
     * For datetime datatypes, thi  s        is the length    in    charac  ters of t   he String re  presen     tation (        as  sum   ing      the
     * maximum allowed precision of the frac  ti      onal sec onds component). For binary data, this i            s t h  e length in b ytes.  For the RO      WID           da t  atyp    e,
       * th    is is     t    he     lengt     h in   bytes.   N     u    ll is r     eturn  ed for data types where the
     *          column size is  not appl  i    cable                        .
        *
     * @param catalog a catalog  name; mu s    t match      th    e catalo  g   name    as i t
           *        is stored i    n th    e database; "     " r    etrieves th    ose   without a catalog;
     *           <c od e>null</code> me ans that   the catalog name s           hou   ld not be u  sed to    n      arro   w
     *        th         e search 
                            * @param   schemaPattern        a schema nam e patte  rn; must match        the s        chem     a name
       *        a             s it is st ored in the  databas   e; "" r     etr   ieves       th      o  se witho                            ut a s   c     hem  a             ;
     *         <c  ode>null</  cod   e> means that     th e schema   name sh     ould not    be used to     n  arrow
      *            the search      
       *      @par  am tableName   Pattern     a table     name      pattern; must        match the
          *                       tab        le name  as             i   t   is stored in t   he database
           * @param column     NamePa tte       rn a colum n name pa    ttern        ; must       m atc   h the colum   n
                     *              name as   it is sto   red in    the datab ase
     * @return <code>ResultSet</code> -  each ro    w is a   column d      escri ption
     * @exc    eption SQLException if a database      acces             s             error occurs
     * @see   #getSearchS    tringEsc    ape
            */
    ResultSet             getColu   mns(       String c atalog, S              tring schemaPatter  n,
                              String     tableNamePa    t     tern,   St    ring column   NamePatte   rn) 
           throws SQLE           xception;

    /**
     * Indicates        that the column might not all o        w <code>NU   LL     </co         de> valu  es.
      * <P>
     * A possible value for the colum     n
     * < code>        NULLABLE</cod   e>
     * in the <c      ode>Resu   ltSet</code    > retur      ned   by    the me     thod
      * <code>get   Columns</c    ode>.
        */ 
    int columnNoNulls = 0;

           /*  *
     *   Indicates that the c  olum n defi     nitely allo   w      s <     co   de>NULL< /co de> values. 
          * <P> 
     * A pos                 sible value f or    the column
          * <code>N     ULLABLE</c      od  e>                 
     *           i   n the <cod    e>ResultSet</code> returned by t   he     method
               *     <code>getColumns  </code>.
     *     /    
       int columnNullable = 1;  

    /**
           * Indicates th  at the      nulla      bi lity     of columns is unknown.
       * <P>
     * A possib  le v     al  ue f  o        r the c      olumn
     *     <code>NU  LLABLE</cod         e>   
         *     in th     e <code>ResultSet</code> re    t  urned by   the m   eth od  
     * <code>getColumns</code>      .
     */
    int columnNu  llab  leUnknown = 2;

    /  **  
             * Retriev     es      a descriptio  n of the ac cess        righ    ts for      a table's c             olumns.
          *
              * <P>Only privil   eges mat             chi           n  g the column    name c     riteria are
     * re  turned.  They ar         e ordere     d b   y C     OLUMN_NAME and PRIVILEGE.
                *   
     * <P>   Each privile   ge descriptio   n has the followin  g columns:
           *  <OL>
       *  <LI><B>T  ABLE_CAT</B> St  ring {@    code         =>}             table   catalo       g     (may be <code>   null</           c       o   de>)
      *    <L        I> <  B>TABL     E_          SCHE      M</B> Strin   g {@co    de =>} table s    chema (       may be <code   >n     ull</code>)
       *  <LI   ><B>TABLE_NAME</  B>             Str   ing {@c   ode =>}      table name
           *  <LI><B>COLUMN_NAME</B> String {@code   =>} co        l  umn name
     *  <LI>      <B>GRANT                 OR</B> S   tring {@cod  e  =>     } g   ra ntor of     access (m       ay be    <code>null</code>        )
      *  <L I    ><  B>   GR  ANTEE</B>   S       tring        { @code =>} grantee    of acces       s
     *  <LI><B       >P    RIVILE    G E</B     > S  tring {@code =>} name of a  ccess (SELECT, 
     *           IN  SERT       ,        UPD       ATE, REFRENCES, ...)
     *  <LI>  <B>IS_GRANTA   BLE</B> St  ring {@code    =>} "YE     S" if grantee is permitted
                    *           to gr          ant   to others; "NO  "       if not;   <code>null  </code>      if unkn    own
                 *   </OL>  
     *
     * @param     c a        t    alog a       catalog name; must match the catalog name   a    s     it
     *        is stored in the    database; "" retrie   ve  s those with           out a     catalog;
         *        <c  ode>null</   code > me     ans th    at the c   atalog     name should   no t be us  ed to nar       row
                       *             the   searc    h
          * @par am schema a                s  chema na      me; m     ust match the sch        ema name as it is    
     *          stored i   n             t     he dat    abase; "" r  etriev     es   those without a sche   ma       ;
         *            <code>       nu   ll</code> means that     the schema      name s          h  o  uld not b      e     used to narrow
     *        the searc      h
     *    @para     m table a table n   ame; mus    t   m   atch the table n  ame as it is
     *        stored in th  e      d  a    tabase
       *  @  param columnNameP     a     ttern a column name patte     rn; must match   the      column
               *        name as it          is store   d in   t       he    datab    ase
     *       @retu        rn <code>  R    esultSet   </code> -   each row   is a   col      umn            privilege  description
     * @exc eption SQ   LExcep        ti o     n i     f a dat  abase access    err   or    occurs  
     * @s ee #  getS earchStringEscape
       */
    ResultS   et getColum nPrivileges(String    catalog, String schema,
                                                                      St  ring t    able, String columnNamePatt      ern) t     hrows S    QLEx    cepti on;
          
    /*         *
      * R     et   r             ieves a des        cription o f the ac     cess rights for e       ach       t   abl   e available
     * in         a    ca    talog.     No te    that   a table     privilege     applies          t    o one or
       *    m ore       columns i    n the table.    It would be wrong to  a   s sume th at
     * thi    s         privilege applies to   all c  olumns    (this may be true  for
                 *                     some systems but is not true     fo  r   al       l.)
          *   
     * <    P>O   nly privilege s     ma          tc      hing the sc  hema and table name
        * c  rit          eria a         re returned.  They   are  ord        e  red by    
     * <code> T  ABLE_CAT</code    >,
     * <      co  de>    TABLE_         SCHEM</     code>, <code   >TAB    LE_NAME</code>,
        * and      <co      de>PR   IVILEGE</code  >.
     *
     * <P   >Each privilege description has the      following    c    olumns:
       *   <OL>
           *      <LI><B>TABLE_  CAT</B>   String {@code  =>   }       table c   atalog (may be <code>nul l<      /code>)
        *  <L   I   ><B>TABLE_SCHEM<  /B> S    trin     g  {@c  ode =>}       table schem           a  (may be  <code>null</code>)
         *  <LI><B>TABLE_NAME</B> String { @    code =>} tab    le name
     *  <L   I><    B>GR      ANTOR</B> St    ri  ng {     @code =>} grantor of acc    ess ( may be <code>nu ll</code>)
     *  <L    I><B>GRAN   TEE</B>    Stri   ng {@code =>  }      grante    e of a c  cess
     *        <LI><B>P RIVIL    E GE</            B> Strin   g {@code =>} name    of access (SELECT,
     *           IN  SE RT, UPDATE, REFRENCES, ..              .)
           *         <LI><B>IS_GRANTABLE    </B      > String {@code =>     } "YES" i   f gran    tee is per        mitte     d  
     *      to grant to others; "NO" if n ot;    <co     de>n ull</c      ode> if     unknown
     *  </OL>
     *
     * @param catalog a catalog name ; must     match the ca                talog   name as it
     *        is stored      in  th   e   dat  aba    se; ""    retrieves those without a   catalog;
               *        <code>null</co    d   e > means that t  h          e catalog           n    ame  should no  t be used to narrow 
        *           the search
        * @para     m schemaPattern   a schema    name pattern  ; must match the         schema         name
     *        as it is s  t  ored in the da    tabase; "  " retrieves tho       se wi     tho ut a     sc   hema; 
        *         <c   ode>null</code> means that      the s chema nam    e    shou      ld not       be  u  sed t   o narrow
      *                         the search
     * @para    m tableNameP  attern a  table name       patte   rn    ; must mat   ch the
               *        table n      a      me as          it is sto   red in     the    database
     * @return <code>ResultSet</code> - ea    ch    r  ow is a ta          ble privilege description 
     * @exception   SQ            LExcep     ti on i  f a       databas   e access e    rror occur    s
     * @see #g etS    ea       rch    StringEscape
          */
       Resul  tSet getT   ablePri      vile     ges(S    tring catalog,   String schemaPattern,
                                                String tab           leNamePattern)  throws SQL Exception;        

    /**
     * Re     trieve   s a descr    iption of a table's     opt   imal set o         f co    lumns tha   t
       *      uniquely identifies   a row. Th       ey      are   ordered by S  CO  PE.
     *
        * <P  >Ea   ch column   description has the foll   owing column   s:
     *          <OL >
            *         <LI><B>SC  OPE<     /B> short {@code =>} actual scope of result
                *      <UL>
     *                 <LI> be  stRowTe   mp   orary - very          temporary, while using row
       *          <LI> be   stR   owTra    ns    a    ction - v alid       for remai   nder of curren       t transaction
     *         <LI  > bestRowSession - val       id for re m  ainder of current session
     *         </UL>
     *  <LI><B >COLUM    N_NAM         E</B> String {@code =>} colu     mn name
     *  <    LI><B>DATA_TYP           E</B> in  t {   @code =>} S      Q L data type f  rom java    .sql.T  y   p  es
             *     <LI><B>TYP   E_NAME   <      /B> String {@code =>} D  at    a so    ur   ce   dependent type name,
     *  f          or a UDT    the type name is f          ully qualified        
            *      <LI><B>                   COLU     MN_SIZE</B> int {@code =>} pre         cision
     *  <LI><B>     B UFF  ER_LENGTH</B> i    nt {@cod    e     =>} not use  d
         *  <LI><B>DEC   IM   AL_DIGITS</B> short  {@code =     >}    scale            - Null        is returned for d  ata types where
     *  DECI                MAL_DIGITS        i s not applic         ab  le.
     *  <LI><B>PSEUD O_COLUMN       </     B> short {@code =>} is thi         s a pseud     o      colu  mn
        *                    like an   Oracle ROWID
     *      <UL>
           *           <LI> be   stRowUnknown - m       ay or may not be pseu   do column
              *          <LI> bes   tRow  NotP  seudo -          is   NOT a pseu    d    o colum        n
     *      <LI> bestRowPseudo - is a pseudo           col   umn
      *                </UL>
         *  </O       L>
            *
     * <p    >The CO   LUMN_SIZE co    lumn    represents t  he spec      ified column size for the given col   umn.
     * F or numeric da t  a, this is th            e   maximum pr ecisio  n.  For charact       er data, this is the l     ength in character    s.
      * For datetim         e data   types, this is    the le   ngth in      characters of the Stri  ng repr             esentation     (assumin         g    the
          * maximum allowed precision        of the fra   ctio      nal s    eco     nds component           ). For  bi  nary data, th is    is  the leng  th in     bytes.  For the ROWID      datatype,
         * th   is     is the length in    bytes.    Null i   s     returned for dat     a typ     e  s where the
        *     column    size is          n     ot applica   ble.
     *
     * @param catalog a c    at alog name;       must match  the catalog n   ame as it
     *        is stored      in the databa   se        ; "" retrie  ves   those without a               cata  log;
             *                      <code>nul      l</code> means that the    ca  talog name should not be used to narrow
     *           the search
     * @    param schema a sche  ma n  ame; must match th    e schem a       name
         *              as it is stored in t  he  database; "" ret  rieves tho    se with  out a schema;
        *        <code>nul     l  </code>    means     that th      e         sch        ema n  ame should no t b e used  to n    a         rrow
     *          the       search
     * @param ta   ble a table nam    e;  must mat    c   h the tab le name as it is stored
              *        in the database
          * @       param    scope    the       scope of interest; u   se same values as SC      OPE
           * @       para   m     nullab     le inc   lude co   lumns that ar   e  nullable.
     * @return <code>Resul   tSe   t</c     ode> - eac    h row is    a column descriptio  n       
               *      @exception S     QLE      xc             eption i                 f       a d    atab      ase access e rror occurs
     */      
    ResultSet getBestRowIdentifier(St ring catalog, St    ri ng schema,
                                              St   ring table, in      t scope, boolean    nullable) throws        SQLException;    

      /**
      * Indicates that the sco pe of the best row      identifier    is
     * very           tempo   rary, la   sting on      ly while th  e
        * r      ow   is being used.
     * <P> 
     *    A possible      value for t  h  e colum  n
     * <code>SCO     PE</code>
      * in the   <code>R   es  ul tSet</c     ode> ob       ject
           * returned   by the meth  od <cod   e>getBestRowIde  nti  fie    r</             co    de>.
     */    
          in t bestR       owTemporary   = 0;

             /**
     * I    ndica  tes t  ha           t t     he s  cope of t          he b   e    st row ide  nt        ifier is
                        *      the remainder o    f the    current transac   tion.
           * <   P>
          * A     possible   value for   the column
     * <code>SC    OP     E</code>
     *        in the <code>ResultSet</code  > obje    ct
     * return  ed by th  e meth    od <cod     e>get  BestRowIdentif       ie r</code>.
         */
     int best    Ro  wTra    ns    action = 1;

    /**
        * I  nd  icat     e      s    that the scope       of the best row i   de   ntifier is     
                 * the remain  der of the  curr en t s   ession.
     * <P>
     *   A possible val   ue for t              he      column
           * <code  >SC   OPE</code>     
           *      in the <cod  e>  R    esul   t   Set</code>        object       
       * returned by   the method <code>getBest RowIdentifier</code>.
     *   /
       int bestRo  wSess ion     = 2;

        /    **
          * Indi   cates tha  t t    he best row     identifier may or may not be a pseudo column.
              * <P>
            * A possible value       for    the colum  n
     * <c      ode    >PSEUDO_      COLUMN</code>
       *  in the <c od  e>    R  esultSet</code> obje        ct
                * returned by the method <code>getBestRowIdentifie  r</co    de>    . 
     */
    int be  stRowUnknown  = 0;      

    /**
                * Indicate  s that        the best       row id    entif      ier is NOT     a pseudo co    lu    mn.
     * <P >  
     * A p   ossib    le value for the col umn
     * <cod   e>      PSEUDO_   C    O       LUMN    </code>
     * in the <code>R  es    ultS  et</code> object
            * returned by the method <code>getBestRowIdentifier<    /c  od  e>.    
     */
                   int bestRowN   otPseu do               = 1;

     /**
     * Indicates that the bes   t row identifi  e   r is      a pseudo column .
     * <P>
     *  A possibl  e va   lu         e for the colu  mn 
             * <code>PSEUDO_COLUMN</code>
     *     in    the <c                o   de>R         e           sultS   et</code>  ob  jec     t
     * returned      by the me   thod <   code>getBest   Ro      wI      dentifier<   /code  >.
     */
    int bestRowPs          eu do   = 2   ;

       /**
     * Retri   e  ves    a   descript    ion of a tabl          e's colum  ns that   are automatically
         *         up dated whe  n        a            ny  value    in a row is updat  e     d.      They a r e
     * unord  e  red. 
     *
     * <  P>Ea        ch colum      n de       sc ri   pti          o   n has the fo        l  low     in    g col   u       mn s:
       *  <OL>
         *  <LI>    <B>SCOPE</B> short {@  code =>} is not used
       *  <LI><B>COLUMN_NAME</B> String {@code =             >} column     nam e
     *  <             LI><B>DATA_TYPE</B> i  n t {@c    ode    =>} SQL dat a type f rom      <code>java.sql.Types</co   de>
                   *  <LI><B>T   YP E   _NAME       </B>    St  ring     {  @code     =>} Dat a source-de   pe  ndent type name
     *  <LI>     <B>COLUMN_              SIZE</B>     i   n       t     {@code =>} precision
        *  <LI><B  >BUFFER_LEN  GTH   </B> in t         {             @code  =    >} length of column value in bytes
     *  <LI>< B>D   ECIMAL_DI     GITS</B> short  {@code =>} scale - Null is retur  ned for d ata types where 
         * DEC             IMAL_DIGITS is not appli   cable.
     *  <LI><B     >PSEUDO_    COLUMN     </B> short {@co   de =>}        whethe r thi    s is pseudo     column
     *              like an   O  racle R  OWID
     *       <UL>
     *       <LI>          versionColumnU   nknown   -      may or m a  y    not be p s   eudo     colu  mn
     *                         <LI>    versionColumnNot     Pseudo        - is           NOT         a      pseudo column
          *      <LI> versionCol    umnPseudo - is a pseudo            colu    mn
     *          </UL>
      *  </ OL>
           *
               * <p>Th     e COLUMN_       SIZE colum     n  repres  ents the      specified column size f         or                    the given column.
     * For numeric data, th  is is the maximum prec      ision.              For character     data,      this is   the len  gth i n characte    rs.       
     * For d   atetime data       t   yp     es   , th  i  s    is the len   gth in         characte   rs of th     e Str  i  ng representat        ion    (assu  ming the
     * m  aximum allowed p      recision          of th   e f      ra c    tion  al second   s c     omponent). For bina            ry da   ta,      this is    the length in bytes.  For the ROWID   datatype,
        * this is    th    e     length in bytes. Null is returned for data          ty  pes       where the     
             *    c   olumn size is not  applicable.
        * @param c   atalog a catalog nam   e;       mu      st    m   atch th e ca     talog n      ame       as it
         *              is stored in   the     databa se; "" re      tri   eve    s those w ith    out a catalog;
         *               <code>null</  code> means tha      t    t   he catalo  g name      s    hould not be used to        narrow
      *        the   search
        *     @param         schem   a a s    chema name;  m u        st        m  atc         h the schema n ame
     *        as    it i   s      stored in the d     at  abase; "" retrie   ves those without a schem  a;      
         *        <c         ode>null</ code> mean       s             that   the schema n ame s       h     ould not be     used to narrow
     *           the    search
     * @p         ara  m t     able a table na    me; must       m   at    ch         the      table     n   a     me as it is store   d
     *        in the d  at     abase
        * @ret   urn a        <cod              e>Re     sult         Set   </code> ob    j  ect  in which each row is a
           *                column      desc         r        ipti   on
       *    @  e   xceptio  n SQL        Exceptio          n if a database    a   cc   es    s   e      rror        occurs
     */
    R     e           sultSe  t getVersionColumns(S       tr  ing catalog             ,     String schema,
                                         String ta        bl  e) th     rows SQ   L           E  xce      ption;
 
    /**   
     * Indicates          that t      his version     column may or   may not be a  pseudo column       .
     * <P>
          * A po ssible value for the col   um   n        
        * <code>PSEUDO_ COLUMN</code>
              * in the < cod    e>Result Set</co   de> object
     *   re turned    by th  e metho      d <  code>getVersionColumns</code>.
     */
    int versionColumnUnknown    = 0;
    
    /**
     * In  dicates    that         this version   column i  s NOT     a ps   eudo col umn.
          * <       P>
        * A possi    ble va lue for   the column
               *    <   code>PSE   UDO_COLUMN</code>
     *   in the <code>ResultSet</code            >           object
             * r    eturned by the meth    od <c          ode>getVersi onColum      ns</code> .
     */
    int versionC   olumnN                 ot    Pseudo     =                1;

      /**
         *       Ind    i    c    a  tes t    hat this version   column         is a    pseudo column.    
                     * <         P>
       *     A possible value fo      r the column    
          * <co    de>PSEUDO_COLUMN</cod      e>     
        * in the <co de>R es   ultS  et</    co     de> object
     * retur       ned               by t  he method <code>getV    ersionColumns</code>.
     */
        int versionColumnPseudo     = 2;

    /**
     *         Retrieves a  de     scription of the      given tab  le's primary key columns.           They
     * are orde    r       ed by COLUMN_NAME.
     *
     * <P   >E ach primary key col     umn description ha   s   the followin    g columns:
            *        <OL>
       *  <LI><B> TABL  E _CAT<      /B > Stri ng {@code =>} table c    ata   log (may be <code>null</code       >)
                *  <LI><B>T   ABLE_SCHEM</B> Strin   g    {@code      =>} table schema (may          be <code>n ull</   code>   )
         *  <LI><B>TABL    E_      NAME</B> String {@    co          d e =>} table nam  e
            *  <LI><B>COLUMN_ NAME</B> St    ri  ng     {@c   ode    =>} column name
     *  <    LI><B>KEY_SEQ<   /B> short {@cod     e =>} seque   nce nu       mber within pr   imary k     e y(    a va lue
         *  of 1 represen       ts    t       he   first    co         lumn     of the    primary key, a value o         f 2 w       ou  ld   
             *  represent      the secon     d   c   olumn with    in the pri      mary ke y   )               .     
     *  <LI><B    >PK _NA ME</B> String {@code       =>} prima            r  y      k   ey name (m   ay be <cod    e>null</co      de>)
           *   < /OL>
                  *
     * @      param c     atalo  g          a c    at   alog name; must ma   tch t  he    catalog name as it
     *            is s        tor   e d in t   he    data              base;  "" retriev  es those    without a catalo    g;
     *        <code>null</co    d        e>  means that t        he   catalog            name       should no   t be used to narrow
     *         th e searc  h  
     * @param s             chema a  schema name; m    ust match the schema name
        *                       as it is st ored in the d   ataba  se; ""   retr    ieves    those without   a sc   hema  ;
        *                            <code>null</code>  means t  hat the                sc   h               ema name  should not be used to narrow
     *            the search
           * @param table a tabl     e name; must match t    he table name   as it is stored
         *        i    n the databas e
        * @retu    rn    <code>ResultSet</code> -       e   ac   h row is a      primary   key column des    cription
     * @excepti        on SQLException if a     database ac        cess error         occurs
         */
       Resul tSet   getPrimaryKeys(   Str      in g catalog,    String sch    e    ma   ,
                                                             String table) thr     ows S         QLException;               

    /**
     *          Retr  ieves a des cription        of the primary    key     colum n   s th        at are
     * referenced   by t he    given table's      fo reig   n key colum   ns (the primary keys
     * imported by a          table).  They ar      e ordere  d     by PKTABLE_CAT,
     * PK    TABLE   _   SC  HEM, PKTABLE_NAME, and KEY_SEQ.  
            *
               * <P>Each pri mary        key colu       mn descr         ipti   on has t     he fo     llowing colum         ns:
     *  <OL>
     *    <LI><B>PKTABLE_CAT</B> String {@code =>  } primary key table catalog
         *       being imported (     may b  e <cod    e>null</co   de>)
           *        <LI><B>PKTAB  LE_SC   HE     M</B> Strin  g {@c  ode =>} pr    imary key tab    le            schema
     *       being import  ed  (may    b   e <co        de>null<         /code>)
          *       <LI><B>PKTABLE_NAME  < /B> String {@code =>} primary   key tab   le name
     *           bei ng import  ed
     *     <L    I><   B>PKCOLUM    N_NAME</B> S    t   ring           {@code =>  } primary key column name
       *               being imported  
       *     <LI><B>FKTAB   LE_CAT</B> String {@c       od              e =  > } fore       ign key tabl       e ca        ta         log (may be <code>null</code>)
     *   <LI><B>FKTABLE_SCHEM</B> St    ring {@co  de =>}  forei  gn key tabl e  schema (may be <code>null</code>)
      *  <LI  ><B>  FKTABLE_   NA       ME      </B> String {@code    =>} f oreig   n k     ey table na me
                     *  <  LI><B>FKCOLUMN_NAME</B> S  tring {@code =>} foreign   key  column name
     *          <LI><B> KEY_S     EQ</B> short {@cod   e =>} sequence number withi    n a foreign ke      y  (    a value
          *  of 1 represents the first column of t               he foreign    ke   y, a valu e of 2 would
        *           represe      n   t the   seco       nd c  olumn within the f ore  ign       key).
            *  <    LI><  B>UPDATE_RULE</B>    short {@co      de =>} What happens t   o a
         *       foreign key when the primary    key is updated:
       *           <UL>
     *      <LI> im     port         edNoAction   -      do   not allow update of primary
     *                       key if it has bee       n imported
     *             <L  I> importedK  eyCa       scad   e    - cha  n    ge importe   d k ey t   o agree
     *               with primary key update     
     *      <LI > imp      ortedKeySetNull - ch      ange imported     key to <     code>NULL</         code>
         *                   if     its primary key has  been update d     
          *         <LI> i   mportedKeySetDefaul    t - chang  e imported key to def    ault    values
     *                      if its          primar   y key has be  en up    dat  ed
         *      <LI> i   m           p    or   te  dKeyRestrict - sa     me a    s import      edK   eyNoAction
     *                                     (fo r OD    BC 2.   x compatibility)
     *      </UL      >
      *  <LI><B  >DELETE_RU    LE</  B> short {@c               ode =>} What happ      ens to  
          *            t   he fo       reign key     when p   rimary i  s deleted.
         *      <UL       >
       *              <LI> importedKeyNo   Action    - do     no    t a llow    delete                   of prim      ary
     *                    ke      y if i    t has been imported
        *          <LI> impo      rtedKeyCascade - del        ete rows tha           t import a delet    ed k ey
          *         <L   I>  importedKeySet      Null - chan    ge    imported key  to  NULL i f
      *                     its primary k     ey has  been de     leted
     *            <LI> importedKeyRestrict - same as      impo    rte     dKeyNoAction
     *                                       (for ODB       C        2.          x compati      bility)
     *      <LI> imported     KeyS   e      tDefault - change imp        orted key t  o def    ault if
              *                      its   primary key has        b   ee   n delet    ed
     *         </UL>
     *  <LI><B>FK_NAM     E </B>      String {@code        =>} foreign key n      ame (may be <code>null</co   de>)
     *        <LI     ><B>PK_N    AME</B> Stri         ng {@code =   >}         primary key name      (ma       y be       <cod       e>nul    l</     code>)
      *  <LI>< B>DEFER       RABILITY</B     > s       h     ort {@code =>} can      the evaluation of foreign key
     *      constra  i       nts be de ferred until com    mit  
       *      <UL>
     *                           <LI> i    mpor   tedKeyInitiallyD  efe r   red  - see       SQL92 for de      fini   tion
     *           <LI> importedKeyInitial     ly Immediate - see SQL92 for defi    nition
     *      <LI> importedKeyNotDeferrabl    e - see SQL92 for def   inition
         *                         </UL> 
     *  </ OL>
          *  
                   * @param catalog a cat  alog name;  mus    t mat    ch the c  atalog       name as it
     *        is        stored in the database; " " r etrieves      those withou           t a catal  og;  
       *           <code>null</code> me        ans that t  he ca  t      alog name shoul     d      not be used to narro w
     *                   the search
            * @param s   chema a schema    name;  must match the   schema nam            e
     *            as it is sto       red in th e database   ; "" retrieves those wit        hout a     schema;
     *         <code>nul l</code> mea ns th       at the schem a name sh    ould      not be u     sed to narrow
              *        the search
     * @pa    ram tab    le a     ta    ble name; must match the  table name as it is st    ored
     *        in t          h    e da     tabase
     * @return <code>Resul     tSet    </code> - e      ach row i         s a primary      key co  lumn de s   cription
     * @exce ption SQLExcep tion if a database    acc ess error occurs
     *     @see #getExp   orte    d   K      eys
     */
          Resul  tSet ge   tImporte dKeys(Strin  g catalo g   , St    r   ing schema,
                                 String table) t       h  rows SQLException;

    /**
     * For th     e col   umn <c  ode>UPDATE_RULE</code>,
       * indicates    that
            * when th    e primary k         e    y is updated, the foreign key      (import     ed key)
     *     is c   h       anged to agree     with it.
     * For the    column <  co  de>DELETE_RULE</code>,
           * it indicates that
                   * when the primary   ke     y is      d  e leted,                r   ows   th  at imp   orted that key
     * a   re de       leted.
     *         <P>
         *    A      po       ssible      value for the columns <c  ode>U   P    DATE_RULE</code>
        * and <code>DELETE_R ULE</code> in the
               * <cod    e>    Res  ultSet</code> objects re     tur       n    e  d by t     he methods
     * <         code>get   Impor  tedKeys</code     >,  <code       >getExportedKeys<      /code>,
       * and             <c         ode   >get  CrossReference</    code    >.
     */
        i        nt im     por       tedKeyCascade         = 0;

       /**
            * For the colum   n <      code>UP   DATE_RULE<    /code>,      indicates that
     * a pri  ma   ry key may n ot     be upd  ate    d if i     t ha   s been impo   rt        ed by
     * another table as a f   o  reign key.
         * For the co  lu  mn <code>DELETE_RULE</code>,  in  dicate  s that   
             * a          primary key may n  ot be deleted if     i      t has         b      een i   mported by
                            * another table as a forei  gn key.  
     * <P>
     * A possible value fo  r the       c  o        l    umns <code>UP D     ATE_RUL  E</code>
     *  a       nd <c   ode>DELETE_RULE</c  ode> in the
       * <c     ode>ResultSet</code> objects  retur   ned by the methods
     * <code>getImportedKe   ys</code>,      <    code> getExportedKeys</code>       ,     
     * and <code>getCrossReference</c                    ode      >    .
         */
       int impo      rtedKe         yRest   rict      = 1;

    /**
     * For  the columns <code>UPDATE_RULE</cod     e>
      * a    nd <co  de>DELETE_RULE</code>, in dicates t  hat
      * when the primary                key is updated    or deleted, t   he fore       ign          key (imported ke   y)
     * is ch   ang      e       d t    o <code >NULL</cod           e>.
        * <P>
     * A possib    le value for the columns <code>UPDATE_RULE</code> 
        * and <co  de>DELETE_RULE<   /code> in    the
     *   <cod  e>ResultSet</code> o   bjects         returned by the methods
     * <code>getImporte    d    Keys</code>,  <c       ode>getExportedKeys</    code >,
                 * and <code>  getCrossRe ferenc    e</code>.
     */ 
    in      t impo     rtedK     eySetNull  = 2;
     
    /**
     *       For th  e column s <code>UPDATE_R ULE</c                   ode  >
        *    a     nd <                co  de>D         ELETE  _RULE<    /code>, indicates that
      *  if the p ri     mary key h       as been imported, it   c         a  nnot be updated or           delet     ed.
        *   <P>
             *   A  possible     value for the column s <     code>U   PD   ATE_RULE</code>
        *      a  nd       <code>DELETE    _RUL       E</cod  e> i n t       he
               *  <c      ode>Result Set</code>         objects returned by the meth  ods
        * <cod      e>getImpor     ted           Keys</co de>,  <code        >get   ExportedKeys</code>,  
             * an     d <c     od   e     >getCrossReference</code>.
                 */  
    int im  por   tedKeyNoAc   tion        = 3;

    /**
           * For the columns <code>UPD AT   E_RU   LE</code>                   
       * and < code>DELETE_RULE</co      de>, indica     te            s that
                   * if          t      h e pr  imar   y key   is updat      ed o      r    delet ed, th        e foreign key    (imported key)
     *       is set t   o the default value.
     *   <P      >
     * A possible value   fo   r the columns <co    de>UPD   ATE_RULE</co   d  e>
     * an d <code>DELET E_RULE</code> in     the
     * <   code>ResultSet</code> objects  re  turned by the methods
               * <c     od    e>getImportedKeys<     /    code>,  <code>getExp  ortedKey  s</co  de>,
     * and <code>getCrossR     eference</code>.
     */
          int     im   portedKeySe    tDefault  = 4;

    /*   *
     *      Indicat  es defe  rrability.  See SQL-9  2 for a definition.
          * <P>
       *       A possi  ble v     alue for t     he column <code>DEFERRABILITY</co   de>
             * in the <c      ode>   ResultSet</co   de>     obje       cts     returned by the metho  d    s
             * <code          >getImportedKeys</code>,  <code>getE      xportedKey   s</code>,
     * a    nd      <c   ode   >getCrossRe ference</code>.
          */
    int importedKeyIniti       allyDeferred  = 5;

    /**
                   *    Indicates        defer  rability.  See S   QL-92 fo  r      a definition.
            * <P> 
          * A      possib     le           va  lue for the column <code>D EFERRABILITY</c    o de>
        *    in     th  e    <code>ResultSet<           /  cod  e> objects r    et     urned by the met       ho     ds
          * <code>getImp  ortedKeys</code>,   <code>getExported      Keys</code>   ,
     * a      nd <code>getCrossRef       eren      ce</code>.
            */
    int import    edKeyInitial    lyImm      ediate      =  6 ;

           /* *    
        * In      di  cates d  eferr        a  bility.   S     ee           SQL-92 for a de   fin ition.
                      *        <P>
            * A    possi     ble value for the co     lumn <   code>DEFERRABILITY</code> 
     * in the <code>ResultSet</code> objects   returned by the m  etho   ds      
         * <cod      e>get   ImportedKeys</code>,  <code >get E  x            portedKeys</cod         e>,
                    * and <co   d e>getCrossRe  ference</code>.
     */
    i nt impo   rt e   d  KeyNotD ef     errable         = 7;

      /   **    
               * Retrieves a        description o   f      the for        eign key      c    olumns     tha                t reference     the          
                     * giv   en  table's p     rimary key c         o     lum   ns (the foreign k   eys ex    ported b      y a
     * table).    They are or   dered  by FKTABLE                  _CAT, FKTABLE_SCHEM   ,
               * FKTABLE_NAME, and KEY_SEQ.
     *
             * <P>Each foreign     key column description has the fo   llowing colum     n            s:
                       *  <OL                     >
     *  <LI  ><B>PKTABL     E_CAT</B> Stri  ng {@code   =>} primary      key t   able catalog  (ma   y be          <code>  null</code>        )
               *      <LI><B  >     PKTABLE_  SCHEM</B> String {@code =>} primary key t         able schema (may be <code>  n   ull  </code>)
         *  <LI><  B>PKTABLE_NA        ME</B> String             {@code   =>   } primary key   table name
     *   <LI> <B>PKCO  LUMN_NA     M E</B> St  ri  ng {@code =>} prim a    ry ke y column name
     *     <LI><B   >FKTABLE_CAT</B> String {  @co            d      e =>} foreign key ta ble catalog (m  ay be <  code>nul  l     </code>)
     *       bei  ng   expor         ted (may be <code>nul          l</code>)
     *  <LI><B>FKTABLE     _SC   HEM</B> S       t ri       ng {@code       =>    }   fore      ign   key table    schema (ma   y be <c     o     de>null</code>)
         *      being     exported   (may be <c      ode>nu     ll</code>)
     *  <LI    ><B            >FKTA     BLE    _NAME</B> Strin    g {   @code =>} f      oreig   n key table na     me
     *              being e   xp   or  ted
     *  <LI><B>FKCO     L    UM    N_NAME</    B     >  String {@code =>} f oreign   ke   y column nam   e
     *                     bei  ng    exported
          *  <L   I><B   >KEY_SEQ</B>    short {@code     =>} se  q  uence n      umber within   fore       ign    key   ( a value
     *  of 1 represen     ts     the first colum   n of t  he foreign key, a va         lue of 2 would
     *  re      pre s ent the s       econd column      with   i   n the foreign      key).    
       *  <L     I><B>UPDATE_RULE<       /    B>   s         ho rt {@co de =>} W    hat happe  ns to
     *       foreign key when   primary i       s   up   dat         ed:
     *              <UL>
     *               <LI> importedNoAction - do no  t           all        o  w       update o      f   pr  imary
     *                   key if i   t has    bee  n imported
     *        <L     I> importe   dKeyCas  cad     e - change  im        ported key to agree
              *                              wi          th pri      mary k       ey u     p     da  te 
     *         <LI> importedKeySetNull - change                    im    ported key      t    o <code>N    ULL<  /code          > if
     *                   its p   rimary key has         been update       d
      *      <LI> impo rtedK            ey     SetDefa   ult      -        change imported     key    to default    valu es
     *                                  if its             primary k   e      y has been updated
            *          <LI> importedKeyRestri        ct - same a    s importedKeyNoAc  tion
     *                                                        (for ODBC 2.x     compatibilit   y)
         *      </UL>
             *  <LI><B>DE           LETE_RULE</B>    sho    rt {@   code =>} What h    appe  ns        to
     *      the               fore  ig   n key w   he    n primary is    deleted.
     *      <UL>
     *                         <LI>  import   edKeyNo          Action - d      o not allow delete of primary
     *                       key if it has      been importe  d
     *       <LI>          importedKe   yCascade - dele            te rows that impor t a  dele  te   d key
        *         <L I >     importedKeySe   tNull - change imported key to <c     ode>NULL</cod              e>   if
     *                         its    primary key has been deleted
       *      <LI> importedKeyRestr ict -             same as import  edKeyNoAct   ion
       *                                                    (for ODBC 2.x compatib  ility)
         *      <LI>      import  edK      eySe  tDe  fault - c   h an   ge impor  ted k ey to default if
        *                                 its  primary key ha                                 s been d ele   ted
     *         </UL>
               *  <L  I>   <B    >FK_NAME</B> S   tri    n     g {@code =>} fo    reign k    ey name (may be <code>null</code>)
        *     <LI><B>PK_NAME</B> S                           tring {@cod e   =>} pr    imary key    name (may be <code    >null<    /code>    )
             *  <L   I><B>   DEFERRABILITY</B> short {@c  ode =>} can th  e evaluation        of foreign ke y
     *         constraint           s    be deferred  until comm  it
     *             <U  L>
         *          <LI> import     edKe  y       Init        iallyDeferred - see     SQ       L92 for def    inition
        *       <LI> importe   dKey InitiallyI  mmediate - see SQL9  2 fo      r defini   tion
            *         <LI> i      mpor       tedKeyNotDeferrable - see    SQL92 f  or definition
       *              </UL>
     *     </O    L       >
     *
       * @ param catalog a catal           og name;       mus t match the        catalog    name as it
     *                  is stored    in this data    bas         e; "" retrieves those   witho   ut a catalo  g;
               *          <c   ode>     null</   code> means  tha     t the c   atalog name    s      hould    not be used   to narrow
     *          the search
     * @par  am schema a schem     a nam  e; mus   t         match the       schema  na  me
         *        as it is store   d in the database; "            "    retrieves those with     out a schema;
     *          <code>              n    u         ll</code> means that the sche         ma name   should not be use  d to n arrow
     *        the search
     * @param ta   ble a ta       ble name; must match the table name as   it    is stor   ed
         *           i n th    is data      base
         * @  retur         n a <code>ResultSet<   /code> object in which each    row is       a
                   *              fore  ign key column descr  iption   
       * @ex     ception S QLException if    a  database access error o   ccur  s
        * @  se   e #getIm    p  ortedK     eys
                *        /
      Resu ltSet     ge    tExportedKeys(String ca tal  og, Str  i        ng schem   a,
                                  String ta      ble)    throw s SQLException;

        /**
      *           Retrieves a descr    i  ption of the foreign  key columns in the    given foreign    k             ey
               * table th   at r      eference the pri mary key or   th         e colu  mn    s repr   esen  tin  g a     u  nique constraint of the  parent      table ( co    uld be the same or a diff  eren    t   t      able). 
       * The number     of columns  returned from  the pa    rent table must match the numb er   of
                   * columns that make up the foreign key.        They
     * are ordered by   FKTABLE_CAT,       FK   TA  BLE_SCHEM, FKTABLE_ NAME, and
        * KEY_SEQ.
     *
      *        <P>E ach foreign key col    u   mn description has     the fo llowing colu     m ns:
     *      <OL>
     *  <LI><B>PKTA   BLE   _CAT</B> String {@cod  e =>} parent key table cat a     log      (may    b  e   <code>null  </code>   )
      *  <LI><   B>PKTABLE_SCH     EM</B> St           ring {@code =>} parent   key table   schema (m        ay be <code>null</cod   e>)
     *  <LI>   <B>PKTABLE_NAME</B> String {@code =>}       parent key table name
           *  <LI>   <B>PKCOLUM   N_NAME </B> String {@co   de =>} parent key column n  ame    
          *  <LI><B>FKTABL             E_CAT<    /B> String {@    co  de =>} foreign              key table ca       talog (may be <code>n    ull</code       >)
            *        bei             ng exported     (may be <code>n       ul   l</c    ode>)
                *  <LI><B>F     KTABLE_           S   CHEM</B> S  t   ring {@cod                 e =>} foreign key tabl   e s        chema (may                  be <code>null</code>)
     *       b       ei   ng export     ed (may be    <   co   de>    null</code>)     
        *  <L I><B>FKTABLE_NAME</  B> String {@      code =   >   } foreign   key tabl    e name
         *                 being e      xport   ed
        *    <       LI><B>FK   COLUMN_NAME</B> String {@cod   e =>}     foreign key c          olumn name
     *       bein     g expo     rted
     *  <L        I><B>KE Y _  SEQ</B>   short    {@c      ode =   >} sequence number within foreign key( a value 
     *      of 1     rep   resents     the first column of th   e fore    ign key, a value o       f 2 would
     *  rep re        sent the seco     nd co    lumn within the foreign key).   
       *  <LI> <B>UP    D  ATE_RULE</B> short {@code =>     }    W    hat happens t  o
     *       for eign key       when pa  rent key is u pdated:
                      *      <UL>
              *      <LI> impor tedNoAc ti       on - do  not        allow update     of parent
                 *                   k    ey if it     h   as b    een    i         mpo      rted
                *          <LI> impor  tedKe    yCasc     a de - change impo   rted key to agree
       *                                       with parent key    up  dat   e
        *              <LI> importedK  eySetNull -     change      imported key t o <code>NULL</code> i     f
                 *                its     parent        key has been updated
     *              <    LI      >           importedKeySetDefault -    change imp           orted ke  y t o default           values
     *                                          if it    s paren          t   key       has been  u p        d      ated
           *      < LI> im          portedKeyRestrict      - s ame as im     po   rte        dKeyNoA      ction 
        *                                                    (for ODBC 2.x compatibility)
      *      </UL>
     *  <LI><B>D   E   LETE_RULE</B> s    hort {@code =>} What       happens to
     *            the foreign         key when pare nt key is deleted.
             *      <UL>
           *      <LI> importedK    eyNoActio       n - do n    ot all   o    w delete o   f pare       nt
     *               key i   f it     has been imported
         *        <LI> importe  dKeyCascade   -    de lete r  ows tha t    im      port a de  leted key
       *              <L I> im        ported    Key   SetN     ull  - change imported k         ey to <code>NULL</code> if
     *                              its     primary key  has bee     n deleted  
           *                                   <LI>      impo    r    tedKeyRestrict       -          same as imported     Key    NoAc       tion
             *                                         (        for O    DBC 2    .x co     mpatibility   )
           *      <LI> importedKey  SetDefault - change impo   rted key to defaul  t if                   
        *                       its parent key has been      d eleted
        *      </UL>
     *  <LI><B>    FK_NAME</B> St  rin  g {@code =>} fore    ign         key n    ame     (may    be <code>null</code>)
     *         <LI><    B>      PK_NAM      E</B     >           S   tr     ing {@code =>} parent key name (ma      y       b       e <code>null</code>)     
               *  <LI        ><      B>DEF  ERRA    BILIT   Y</B> short {@       code =>} c  an the ev aluatio  n of foreign key
                 *             constrai          n       ts be deferred un  til c           ommi t
     *      <UL>
          *       <LI> importedKeyInitiall    yDef   erred -   see    SQL92 for definition
     *      <LI>   imp     ortedKeyI ni ti   allyImm     ediate -   see    SQL9       2 for definitio   n
                                 *      <LI> importedKeyNotDeferrable - see  SQL92         for def      initi   on
        *      </UL     >
     *  </OL   >
        *
      * @param parentCatalog  a c    atalog name; must match the       cata log name
         * a   s it     is     sto  re        d in th     e     database; "" ret     ri   eves those wi        thout           a
          * catal   og; <   code>null</code> means drop   catal    og name from the    select      i     on criteria
              * @p aram p  a rentSchema a schema n  ame; mus    t ma tc       h the    schema name as
     *       it is stored in the d      atabase  ; "" retriev   es    those            wit  hout a     sche  ma;
       * <code>null</code>     me      an  s drop schema na         me from the      se  lection criteria
               *  @param parentTabl    e      the name of     the t able    that exports the ke    y; must match
     *   t    he table name as  it      is st          ored in the database
     * @param foreignCat   a        log a       catal og name; m  ust matc  h the     catalog n     ame     as        
           * it is store        d         in the database; "" retrieves those with      out a
     *    cata  log; <code>nu     ll</code> me   ans drop ca    ta    lo    g name   f  rom the  selection criteria
         *     @param for   eignSch  ema a    schema name;  must match the schema name as it  
     * is stor    e   d in the database; "" r etrieves those without       a sc  hema;
            * <code>n              ull</c    ode> means drop schema name from   t         he selection c    riteria
     * @param   f oreignTable the name of the table    that i mpor   ts th  e key; mus   t   match
     * the tabl    e n  ame as it is    s  tore    d  in  the     databa se
       * @return <code>ResultSe      t</code> - each row  is a f     oreign key column descrip    tio     n
     * @except  ion SQLEx    cep       ti   on i  f    a database acce     ss error occ urs
     * @see #getI     m     portedKeys
     */
    ResultS      et getCrossRefere    nce(
                                          String parentC   a    talog, String parentSchem   a, String parentTable,
                                                          String foreignCatalog, String foreignSchema          , String foreignTable
                                                       ) throws SQ    LException   ;

    /**    
     * R     etrie   ves a descripti  on of all the        d   ata types supp  orted by
     * thi s     dat           abase. The   y        are orde   red by    DATA_TY                       PE and t  hen by ho  w
     * closely the data type m aps to the corre      sp  onding J     DBC     SQL    typ  e.
        *
          *       <P       >If th  e database supports         S       QL          di      stin       ct types,          then   getTypeInf   o()     will re   turn
       * a sing    le r ow with a      TYPE_NAME of DI      STINCT and a DATA      _TYP  E of Types.DI     STIN        CT   .
        * If      the database supports SQL         structured types          , t       hen getTypeInfo() wil   l return
       * a    single row     with a TY    PE_NAME of STRUCT and a DATA_TYPE of T  yp    es.STR      UCT.
     *
     * <P>If      SQ   L d    isti  nct   or structured types are suppo     r  ted, th    en in    fo r           mation on the
     * individual typ      es may   be obtai  ned from the     getUD       T    s() method.
     *
      
     *
                  * <P>    Each type des  cription has     th e fo    llo    wing   columns:
     *  <O L>
     *  <L   I><B>TYPE _NAME     </B> String {@code =>} Type name
     *       <LI>         <B >DATA_TYPE<     /B> in  t {@code =>}    SQL data    type from java.sql.Types
     *  <LI>    <B>        PRECIS    ION<     /B> int {@code =>} m aximum p  recision
         *  <LI><B>LITE RAL_PREF    IX   </B    > String {@c      ode    =>} prefi        x used  to     quo  te a liter       al
     *      (may be <co            de>null</code>)
                    *       <LI><B>LITERAL_SUFFIX   </B> S    tring {@code =       >} suffix used to quote a l  i   teral
            (may be   <code>null    </   c  ode>         )
                  *  <L  I><B>CREATE_PARAMS</B> String {@      c ode =>} paramet      ers used in cr  eat  ing
     *          the type (may be   <code>null</code>)
       *  <      LI><B  >NULLABLE</B> short {@cod      e =>} c     an you u   s    e NULL for this type.
         *      <UL>
        *           <LI> ty   peNoNull         s - does         not    a   llow NULL v   al     ues
        *      <LI> type Nullable - allows NULL values   
     *      <L   I> typeN   ullableUnkno    wn - null  ab   ility unkn     own
     *            </UL>        
       *  <LI><B>CASE_S   ENSITIVE</    B>            boolean   {@cod   e =>} is it    cas           e s   en sitive.
     *  <    LI><B>SE        ARCHABLE</B>   short     {@code =     >} c  an you use "WHERE"          based on this   type:
                *       <        UL>
             *                  <LI>    typePredNone - N   o supp            o  r              t
     *      <LI> typePredChar - Only supporte d   wit           h  WHERE     .. LIKE
                  *           <LI> typePr     edBasic - Sup       po                     rted exc      e  p          t  for WHERE .. LIKE
       *       <LI  > typeSearchab le   - Support     ed for all      WHERE ..
     *           </UL>
     *  <   L  I><B>UNSIG       NED_ATTRIBUTE   </B> boolean {@c       ode =>} is it      un   signe  d.       
            *  <LI><B>FIX       ED_PRE   C_SCALE  </ B> b            oolean {@code =>} can     it be a     mo      ney     v     alue.
        *   <LI><B    >AUTO_INCREMEN      T</B> boo  lean {@co               de      =>     }  c an it be used for    an
         *      auto     -increme   nt value.
         *  <LI><    B>LOCAL_TYP  E_NAME</B>       String {@c ode      =  >   } localized       version of type name
         *                       (may be <code>null</code     > )    
       *  <LI>  <B>MINIMUM_SCALE</B> short {@ c     ode     =>}          minim   um scale supported
          *  <LI            ><B>MAXIM  UM      _  S    CALE</B> short   {@code     =>         }   maximum scale support   ed
      *  <L  I><B   >SQL_    DAT  A_TYPE</   B   > int   {  @   code =>} unused
     *  <L    I><B    >SQL_D  ATETIME_S  UB<  /B> int     {@code =>} unused
     *        <LI><B>N     UM_PREC_R   ADIX<   /B>   int {@c   ode =>} usua  lly 2 or       10   
     *  </OL>
              *
        * <p>The PRECIS   ION column repres  ents the   ma ximum        column size   that the     s  erver su  p   ports  for the given      datatype.
            * For  numer       i   c da          ta,             this is t     he maximu   m pr  ecision.  For charac ter data, th     is  is the length in characters.
       * For dateti me  datatypes,  this is the length in c    haract   ers of the String representation   (assuming the
        *   maximum allowed p   recision of the fraction  al s econds compo  nen t). For binary data, this i  s the le  ngth in bytes.   Fo    r the ROWID datatype,
               * this is the length in byte   s. Null     is return        e                d for data type        s   w here the
     * column size is no        t applic able.
     *
                      * @return a <code>ResultSe    t</c          o  de> object in      whic  h each     row is  an SQL
         *                 t       ype        description
        * @  exception SQLE    xceptio  n if      a database access error occur   s  
           */
       Re     sultSet get          Ty          peInfo() throws SQ LEx         ception;

    /**
     * Ind    icates tha   t a <code >NU L L<    /code> value  is NO        T allowe       d for thi  s
     * data type.
     *    <P>
          * A          p   os     sible value         for       c    o    lumn <cod     e> NUL LABLE</c         ode> in the
              *   <code>ResultSet</code>   objec   t r  et    urne  d by the m                     et  hod
           * <co    d e  >getTypeInfo</c       o     de>.
       */
           i  nt t    ypeNoNu  lls = 0;

               /**
     * Indica    te  s that a <code>N    ULL</code> value    is  allowed fo    r th   is
       *  d ata type.
     * <P>
     * A pos sib   le va     l   ue      for co                   lumn <c    ode>NULLABLE</           code     > in the
     * <co      de>  ResultSet</c  o  de>          object       returned b     y the   met    h     o    d                   
             * <code>  g   e   tT     ypeInfo</co  de>   .
                  */
    in     t     type N   ul  lable = 1;

     /            **
     * Indicates   t hat it     is not known whether a <code   >NULL   </cod  e> val   ue  
       * i        s a   l    lowed for this data ty  pe.
       * <P>
        * A po ssible value for co  lumn <code>NU               L     L       ABLE<      /code> in the
     * <co    de>R  es  ultS   et   </code    > obj        ect  returned by the met    hod
        * <code>    getType Info</code>.
     */
    int typ   eNu      llableU     nknown = 2;

       /**
     * Indicates th     at  <code>WHERE</code> search clauses ar  e  not supported
     * for th   i s     t    ype.
     * <P>
              * A possible value for column <co de>SEARCHABLE</code  > in the
             * <code>ResultSet<    /code> object retu  rned by the metho   d                  
         *  <code>getTypeInfo</code           >.
        */
    int typePre dNon   e = 0;

     /**
     * Indica tes that the   data  typ       e
                  * can be only be used in <code>WHER  E</code> searc  h clauses
          * tha        t  use <c  ode      >LIKE</c   od     e>     predica        tes.
           * <     P>
     *   A possible value  fo      r colu  mn <code>SEAR          CHABLE</code> in th   e
     * <cod    e>R e sultSet</cod         e>     object      retu         rned by the    method
     * <code        >getTypeIn     fo</code>.
     *   /
    int typ    eP    redCh   ar = 1;
  
    /**
     * Indi   cates tha     t the da  ta typ         e c       an  be only be  used in <cod   e>WHE     RE< /co  de>
      *    search c                  lauses
         * t     h  at d     o not use <code>   L    IKE</code> p   redicates.
        * <P  >
      * A possible value for column <code>SEARCHABLE</    code> i       n the
       * <code>Resu  ltSet</cod    e> obj ect retur      ned b      y    th  e method
               * <code>   getTypeIn      f   o</code>.
     *             /
     int typePredB      as    ic = 2;
     
    /**
     * I         n     dicates that al   l <   code      >WHERE</c  ode> search cl   aus     es        can be
     * based o  n thi   s type.
     * <P>
     * A po            ssible valu    e  for   colum   n <code>SEARC        HA     BLE</code> in the
     * <  c   ode>   ResultS    e      t</code> object re turned    by the           method
            * <code          >getTypeInfo</code>.
     */
    in t typeSear ch  a  ble  = 3;

    /*    *
     * Re   triev  e   s a descri ption of   the given ta   ble's i ndices  a    nd statistics. They are
           * ord e   red by NON   _UN    IQU          E, TYP    E,     INDEX_NAME, and O    RDINAL_   POSITION.
     *
     *  <P>Each index col    umn descript    ion ha   s     the following column      s:
     *     <OL>
     *  <LI><B>TABLE_CA       T</B   > St       ring {@cod     e      =  >} table cata  log (may          be <cod   e>          null<        /code      >)
     *  <LI     ><B>TABLE_SCHEM</B>  St ring {   @co      de =>} table  schema (m      ay be  <code>n ull     </   co    de>)
     *  <    LI><B>TABLE_NAME</B>      String    {@code             =>} table name
         *  <LI>   <B>NON_UNIQUE</ B    > b      oolea     n {@code =>} Can index    values  be non-u   nique.
     *         fal   s    e     when TYPE  is tableInd exStatisti   c
         *  <LI>       <B>INDEX    _QUALIFIER</  B> St  ring {@cod  e =>} index    catalog      (may be <code>null</code>);
     *      < cod     e>nul      l</     code>   wh     en         TYPE is     tableIndex     Statistic
       *  <L I><B>IND   EX_NAME</B>               String {@c   od     e =>}       index    name; <code>null</c   ode>   when TYP     E is
      *      tab le     Index     S   tatistic
      *    <        L  I>        <     B>T   YPE</   B> sh       ort {@code =>} index type:
          *      <UL>
        *       <LI> tableIndexSta    tistic - this identifies table statistics   that are
     *           retu rned i   n co     nju   ction with a table's index    desc  ript    ions
     *            <LI> tableI    n   dex  C   lu   stere      d - thi    s is a clustered index
       *               <LI> t   ableIn   dexHashed - this i s   a hashed index
     *      <LI> tableIndexOther - this is          so    me o     ther style of index
       *               </UL>
         *  <LI><    B>ORDI     NA   L_POSITION</B> short          {@code =>}     column sequ     ence number
     *          within index; z        ero when TYP E   i    s tab leIndexS tatisti   c
        *  <     LI><B>COLUMN_NAME</B > String {@code =  >    } column name; <code>null</c     ode> when       TYPE is
          *      tableInd  exS     t     at      is    tic
     *  <   LI><B>    ASC_OR_DESC<  /B> String {@co     de             =     >} column s   ort seq   uence        , "  A" {@cod    e =>} a    scending,
     *      "D" {@code =>} descending, may be   <code>nu   ll</code> if sort sequenc  e is not s    upported;
             *          <code>     null<  /code> when T   YPE       is tabl  eInde   xSt        atistic
     *  <LI><B>CARDIN   ALITY</B >   long    {@code                  => } W  hen TYPE is tab        leIndexStat   istic, then
     *            this is t      h e number of rows in the table;    othe    rwise, it is the
     *      number    of     unique values in       the index.     
     *  <LI><B>PAGES</B> l   ong {@   code         =>} When TYPE i  s      tab    l  eIndexStatisic then
     *           th  is is           the number of p    age     s      used for the  table, othe    rwi     s     e it
     *      is the number of pag  es used fo  r          the current index.
     *  <LI><B   >FILTER_CONDITION</B> String {@code =>}      Filter cond   itio   n, if     a   ny.
     *                  (may b   e <co   de     >     null</cod  e>)
          *  </OL>
         *
      *      @param c      a   talog a ca     talog na     me;       m   u st match       the    catalog name as it
            *        i   s st      ored in this data    base; "  " retrieves those w    ithout a catalog;  
     *        <  code>nu  ll   </code> means that the catalog name should not   be used to narro w
     *                 t    he search
     * @param sche  ma        a sch    em     a name;             must ma  t c     h the schema name
              *                      as it is stored         in this database;   "" retrieves th  ose   with       ou    t a schema;
     *             <code>n  ull</c   ode>    means     that th     e   schem      a name shoul          d   not be us       ed to narrow
       *               the search
     *       @param ta   ble a table name;    must match the table name as it is stored
     *        in this da     tabase
       * @param unique wh en tru    e, return only         indices for unique values  ;
     *     wh     e        n f alse, return indices                 regardless of whether unique or        no         t
      * @p     aram ap  proximate when true, result is allowe d to   re    flect approx imate
                    *     or o  ut of dat    a   val  ues; when false, results are  requested to b    e
     *      accurate
     * @re turn <code>Res   ultSet</cod     e> - each row is an index co lumn d    escripti      on
        * @   exceptio n SQLExc           e ption if     a databa   se acc  ess   error occurs
            */
       ResultSet getIndexInfo   (String           catalog,         Stri   ng schema, St   ring table,
                                boolean u       nique,   boolean approximate)
        throws SQLExce       ption;

     /**
          * Indica      tes that this col           umn contains tab      le statis  ti   cs t     hat
         * are returned in conjunction with a table'  s in       dex d  escrip   t   ions.
     * <P>
                      * A possib          le value          fo    r     col    um     n <c  ode>TYPE<   /code> i n the
       * <code>ResultSet<    /code>        ob     je      ct ret urned by the method
         *   <    code>getIndexInfo</code>.
     */
         short     t   ableI   ndexStatistic =        0      ; 

        /**
                          * Indicat  es that th        is tabl      e ind    ex    is   a c   luster  e  d i   ndex.
     *    <P>
             * A possible v       alue for column    <  code>TYP   E</code>              in the
          *    <code>ResultSet</    code> object return    ed     by the meth     od
                  * <code>ge     tIndexInfo</code>.
     */     
    short     t          able I    ndexCl  ustered = 1;

    /**
           *   Indic    ates that this  table in        dex is a has h               ed index.
                     * <P>  
     * A poss   ible value for colu     mn <co   de>     TYPE</code>    i n th  e
         * <co  de>ResultSet</co  de  >     ob    ject returned by       t   he m    ethod
     *    <code>getIndexInfo</co   de>.
     */
                              sh ort table   Inde   xHash e  d    = 2;    

      /**
      * Indica tes t     ha  t this tabl       e index is  not a cluste  red
     *  i    nd                e x     , a hash  ed index, or        table sta tistics;
                * it      is something o      ther than                            these.
     *          <P>
         * A      possi   ble value for      col       umn <code>     T          YPE</code> in   th   e
           *                   <code>Res   ultSet</code          > object re  turned b  y th    e method
     * <            code>get   IndexInfo</code>       .
           */   
        s hort tableIndexO   t     her     = 3;
        
    //--------------------------JDBC 2      .0         ----------             -------------- -----

      /**
          *     Retriev       es whether this database suppo     rts     the given  r esult set           t          ype.
       *      
     * @p       aram ty   pe defined in <code>java.sql.ResultSet</    code>
     *                    @ret        urn              <code>tr    ue</code> i   f so; <c  ode>        fals    e</c     ode> other      wise
             * @exception SQLException if a da   taba  se access er   ror occurs
     * @see      Connection
        * @since 1.2 
     */
    bool        ean suppor tsResultSetType(int type    ) th  r     ows SQLExcept       ion;
 
                    /**
         * R  et rieves whether this databas   e supports the given  concurrency      ty   pe
         * in combination w     ith t  he given result set t        yp     e.
       *
     * @param type    defin  ed in <code>java.s   ql.Result  Set</   code>
     * @p  ar        am concurrency     type def   in    ed in                 <c  ode>java.sql.ResultSet<      /code>
         * @r   etur      n   <code>t rue</code        > if   s   o; <code>false</c    ode> o  therwise
     * @exce pt  io  n  SQLExcep  tion if a d    atabase ac  cess          erro    r     occurs
               *             @see C       onne    ction
     * @           since 1.2
     *  /
        bo    olean supportsR  esultSe  tConcurre      ncy(int type, int c  oncurren   cy)
           thro    w   s     SQ             LException  ;

      /**
     *
     * Re   tri    eves whether fo    r    th  e given type      of <code>ResultS   et</code> o         bject,        
         * the r esult set's o    wn update  s are visi    bl   e.
       *
     * @         par    am type the <code>Resul    tSet</cod       e> type; one          of
     *                 <c  ode>Re       s    ultSet  .TYPE_     F    ORWA  RD_ONLY</code>        ,
                   *           <code  >ResultSet.TYPE_SCROLL_INSENSITIVE</      code>   , or    
           *           <c  od    e>Res  ultSet.TYPE    _SCRO   LL_SENSITIVE</code>
     * @return <code>true</code > if            upda   tes a  r              e visi   ble for the   given result    set type;
     *                  <code>false</co       de> otherwis       e
       *    @exception    SQ    LException if a d  atabase access error occurs
     *       @     since             1.2
       *              /
      bo   o   le         an ownUp    dates  AreVisi         ble(in    t type) throw  s SQLEx  ception;

    /**
       * Retrieve      s whet   her a resu       lt set  's own deletes are visible.
        *
                * @param type t    he <code>ResultSet<  /code> typ  e; one of  
     *             <code>Res    ultSet.TYPE_FORWARD   _ONLY</code>,
         *               <code>ResultSet.     TYPE_S    CROLL   _INSEN  SITIVE</code>, or
     *              <     c    ode>ResultSe   t.   TYPE_S CROLL_SENSITIV   E</c     ode>
                  * @ret       urn    <   code>true</co  de> if   deletes ar   e  visible f      or the  given resu    lt set type;   
     *          <code>f   alse</code> oth erwise
     * @exception SQLExce     pt   ion if a databa   s  e  acc      ess erro          r occurs
     * @sin   ce 1.2   
          */
      boolean ownDeletesA reVisible(int type) thro  ws SQLException;

    /**
        * Retrieve      s whether   a result set's   own inser    ts are visib        le.
     *
     *   @p        aram t    ype        the      <c  ode> ResultSe    t</c            ode> type; one of
       *        <code>ResultSe   t.TYPE   _ FO   RWARD_ONLY</code>,   
     *             <code  >Resu         ltSet.T YPE_SC                 ROL   L_INSE    NSITIVE</cod  e>, or
     *            <cod               e    >ResultSet.T   Y  PE_SC ROLL      _SE  NSITIVE</code>
     *          @return <co de>true</code>  if    insert   s a         re visible    for the given resul    t      set ty     p e; 
      *        <code >false</co  d e> otherwise
                      * @excep     tion SQLException if a database a          cces  s error occur s
     * @s          i    nce 1.2
     */
    boo      lean    ownIn       sertsAreVisible(int    type) throws SQLE         xcepti    on;

    / *  *
       *     Retr   ieves whether updat    es  ma    d  e by o   thers are visible.
     * 
     *     @p     aram type the <co d   e>ResultSet</code> type; one   of
        *             <c ode>Resul          tSet.TYPE_FORWARD_ONLY</code>,
     *        <code>Re  sultSet.TY  PE_SCROLL_INSENSIT      IVE</co   de>,       or
          *             <code>Resul         tSet.TYPE_SCROLL_  SENSITIVE</c        ode>
       *    @r    etu      r    n <code>true</c ode> if update       s made b y ot  hers
     *        are visib    le fo      r the          given result    set t      y pe;
     *        <cod  e>false</code> otherw    ise
          * @     excep   tion SQLExce    ption  if a database     acc  ess error    occurs
     * @s ince 1    .2
     */
    boolean   othersUpda  tesA  reVisi  ble(     int     type      ) throws SQLExce    ption;

     /**
        * Retrieves wh ethe   r deletes made by othe         rs ar   e vis   ible.
                      *
     *        @param typ      e                 t he <code   >ResultSet</  code> type   ; one o  f
            *         <code>ResultSe t.    TYPE_FORWAR  D_ON LY</code>,
     *           <code   >R  esultSet   .      TYPE_SCROLL_INSEN   SITIVE</code           >, o  r
          *                     <code>ResultSet.T      YPE   _SCROLL_    SENSITIVE   </c ode>
        * @return <cod  e>t rue</cod    e    > if  deletes made by others
     *           are visi        ble for    t    he given result   set type;
     *                        <code>fal  se</code> other  wi se
     * @except   i    on      SQLException if a database  access error oc       curs
     *   @since 1.  2 
     */
    boolean othersDeletesAreVi  sible(int ty    pe) thr        ows SQL  Exception;
   
        /**
     * Retrieves wh   ether inserts made by others ar e vi         sible.
              *
     * @param type    the <c    ode>ResultS  et</code> type; one of
       *         <c       ode>Resul         tSet.TYPE_F   ORWARD_ONLY  </code>,
       *        < code>ResultSe t.TYPE_SCROLL_INSENSI  TIVE</code>, or
     *        <code    >ResultSet.TYP  E_SCROLL_  SE  NSITIVE</              code> 
     * @r        eturn      <code>tru    e</  code> if inserts made b y others
        *         are visible for the     g    i     ven    result s    et type;
     *         <code>false</cod   e> o  therwi    se
      * @exception SQLEx     ce   pt   ion if a data   base           access     err  or occurs
     * @since 1.     2
     */
    boolean othersInsertsAreVisible(int type) throws        SQL    Exception;

       /**
             * Retrieves w    he           ther or  not a  visib le r     ow up  date can b       e detected by
     * calling the        method <code>R    esultSet.rowUpdated</code>.
     *
     * @param type the         <code>R    esult   Set</cod  e> type; one      o      f
     *        <  code      >R    esultSet.TYP    E_  FORWARD_ONL    Y</cod    e>,
              *               <c  ode  >ResultSet.T         YPE_SCROLL_INSE   NSIT     IVE</code    >, or
            *        <c         ode>ResultSet.TYPE_SCROLL_SE  NSI  TIVE</code>
       * @ret    urn <code>tru     e</c o       de> if changes  are detect ed by the result set type;   
     *             <code      >fa lse</code> o therw   ise
     * @exception     SQLExceptio    n       if a dat   abase access error oc cur     s
         * @sin     ce 1.2
           */
    boolean   updatesAreDetected(int      type   )   throws    S  QLExcepti on;

       /**
     * Retrieves   wheth   er or not a vis ible     row delete can be detected by 
               * cal       ling       the method <code>ResultSet.         rowDel   eted</code>.  If the method
     * <c    ode>deletesAreDetected</code> returns <code>fal  s e</code>, it     means        that
       * deleted row    s are removed from th   e resu         lt set.  
                    *
          * @par       am type the <code>Res     ul  tSet</code> type; one of
     *        <   c ode>ResultSet    .TYPE_FORWARD_ONLY</c          o       de>,
     *             <code>ResultSet    .TYPE_SC  ROLL    _I     NSENSITIVE<  /code>, or
     *           <code>Re su     ltSet.T  YP     E_SCROLL      _SENSITIVE</code >
     *  @return <code>true</code> if             deletes are de tected by the gi               ven result set typ  e;
     *         <code>fa lse</code  > othe     rwise
     *      @except     ion SQLExce     pti  o n              if a d  ata base ac    c ess error o                 ccurs
     * @    si  nce     1 .2
      */   
       boolean dele  tesAreDetected(int type)   thr     o   ws SQLEx   ception;

      /**
     *  Ret rieves w     hethe r or not a    visible row insert can        be detected
         * by call     ing     the method <code>ResultS  et.  rowInsert ed</c       ode>.
           *
          * @pa ram type th   e <code>ResultS     et</c       ode> t    ype;  on   e of
          *        <code>ResultSet.TYPE_FORWARD            _ON       LY</code>,
     *        <code>Re  sultSet.T   YPE_SCROLL_I          NSENSITIVE</code>, or
       *        <code>ResultSet.     T  YPE_S CRO   LL_SENSITI  VE    </code>
       * @return <code>t    rue     </co de>         if changes are d etected  by the spe     cified result
     *         set type                  ; <cod    e>false</code> o therwise   
     * @exception SQLException i f a database access error     o   ccurs
     * @sin ce 1.2
     */
    bo   olean in     sertsAreD  etected    (int typ e) th      rows SQLException; 
 
      /**
     * R   etrieves wheth         er th   is     database supp   ort     s b    at ch updates.     
     *
     * @return <code    >true</code> if this da    ta    b          ase supports batch updates;
      *            <code>false</code> ot   herw  ise   
      * @ exception SQLExcep     ti      on if a    database acces  s error occu         rs
        * @since 1.2
     */
               boolean supportsBatchUpdates()  th     rows SQLExc  eption ;
 
      /**
       * Ret  rieves a descrip     ti     on o       f the user-defined ty    pes            (UDTs   ) defined  
       *     in     a particular schema.  Schema-spe   cific UDTs may      have type
     * <cod     e>JAVA_O               BJECT</code>, <co            de>STRUCT</code>,  
      * or <code>DISTINCT<   /code>.
     *  
     * <P>O  nly type  s matching the catalog, sche  ma, t     yp   e   name and    type
        *        c        rit      eria are re    turned.  They are ordered by <code>DATA_TYPE</     code>,
        * <code>T      Y   P  E_CAT</code>, <code>TYPE_SCHEM</code>  and
     * <code>TY  PE_NAME</code >.  The type     n ame     parame   ter may be a fully-qual      i           fied
           * name.  In this      case,    the       catalog and sc        hemaPatt   ern para  meters are
         * i gnored.      
            *
     * <  P>Each type description has th       e following column  s:
     *  <OL>
                *      <L     I><B>T      YPE    _C AT</B> S        tring {@code =>} the type's catalog (may be <code         >null</code  >)
     *  <LI><B           >T YPE_S      CHEM</B>               Stri              ng {@code =>} typ      e's sch    em  a (m   ay be <code>null</code>)
          *     <LI><B>T    YPE_NAME</B> String {@co  de =   >} type na    me
     *  <L   I><B>CLASS_NAME<  /B> String {@code   =>} Java cla ss name
     *  <LI><B>DATA_TYPE</B> int {@code =>} t    ype value    defined        in jav  a.sq           l.Ty    pes.
     *     One of JAV  A     _OBJECT , STRUCT, or       DI     STINCT
      *  <LI     ><B>R EMARKS</B> Stri                   n         g   {@code =>} ex      planat ory   commen     t on the type
        *  <LI><B>BASE_T    YPE</B> sh  ort {    @   code          =      >}   type code o f the source        type of     a
     *         DISTIN  CT type or the type that implements the u   ser-generated
     *     reference type of the SELF_R    EFEREN  CING_    COLUMN of a structured
     *     type as d      efined in java.s    ql.Ty   pes (<code>null</code> if DATA_TYPE is no   t
          *           DISTINCT             or not STRUC   T with      REFERENCE_GE   NERATION =          USER       _DEFINED)
     *  </OL>
     *
     * <P><B>Note:</B> If the driver does not supp  ort UD   Ts,  an empty
       * result set is returned         .
     *
        * @param ca   t  al  og a catalog name ;        m    ust matc     h the catalog          name as it
         *        is stor      ed   in     th  e data  base; "" retrieves those wi  th  out a catalog;
     *        <code>null     </code> means that the catal   og n   ame    should not be use   d       t    o narrow
       *           the se    ar   ch
     * @param sc     hemaPattern a    schema pattern    nam      e; must    match          the     schema         na   me
      *        as it         is stored in th e da   tabase; "" r  etrieve            s those      without a schema;
     *          <code>nul l</     code> means that    the s     chem     a name s  hould n  ot be used           to narrow
     *            the search
     * @param ty    peNamePatte   rn     a type nam   e patt  ern     ; m  ust match th  e type na      me
     *        a       s it is sto re d in the databas          e; may be a fully qualified       name
       * @param types a list          of user-defi                  ned types (JAVA_OBJECT,
           *                     STRUCT, or   D     ISTINCT) to i     nclude;  <code>null</code   > returns all types
     * @return  <code>ResultSet</code> object in which each row  descri   bes a UDT    
     *     @exception     SQL    E   xception if a datab      ase     access error    occurs       
     * @see #getSear    c hStringEscape
     * @  since 1.2
     */
    ResultSe      t getUDT  s(String catalog, String schemaPatte   rn,
                          S    tring type  NamePattern,  int[  ] t    ypes)
        throws     SQLExcept          ion;

    /**
          * Ret   r     ieves the c    o      nnection that pr    oduced th is metada ta object.
            * <P>
           * @return        the connection           that produced this   metadata object
     * @   exception SQLException if a database acces         s error oc  curs
                 * @sin   ce 1.2
     *   /
       Connection get  Connec                    tion() throws SQLException;

     // -------  ------------ JDBC 3.0 -------------------------

           /**
     * Re    tri    eves wh  ether this database supports savepoints       .
       *
       * @return <cod   e  >true</code> if   savepoints are   supported;  
        *                     <code>fa   lse</code> otherwise
         * @exception S       QLExc  ep      tion if a data   base access error occurs
     * @since   1.4
     */
    boolean support   sSavep oint   s() t     hrows SQLExcept   ion;

        /**
     *    Re    t   rieves      whether    this database supports named paramet    e    rs t  o callable
      * statemen   ts.
     *
     * @  return   <code>true</code> if     named parameters are supported;
     *         <    code>false</c       ode>      othe   rwise
     *     @excep  tion    S       QL   Excep     tion if a database acc  ess error   occurs
      * @      since 1.4
     */
     boolea     n s   upp        ort   sNamedParameters()  throw  s SQLException;

    /**
         * Retrieves wh  eth       er it i           s po ssible to         have multiple     <code>   ResultSet</code> objects
       * retu r  ned from a <code>CallableStatement</code>  object     
      * simultaneously.
       *
           *      @  return   <    code>true</co  de> if   a <code>CallableStatement</code>     obje ct
     *                  can return multiple <code>ResultSet</cod    e> ob  je  cts
        *         simultane   ously; <cod e>false</code> o  therwise     
       * @exception SQLEx  cep      tion if a datana  se a     ccess error occu   rs     
              * @since 1.4 
     */
     boolean      support             sMultipleOpenResul  ts() throws SQLException;

    /**
        * R  e     trieves whethe  r auto-generat    ed      keys can be retrieved   after
     * a statemen    t has been executed
          *
     * @return <code>true</code> if auto-  gene  ra        ted k   eys c  an be retrieved
     *                after a stat        ement has executed; <co  de>false</cod    e> otherwise
        *<p>If <code>true</c  ode> is returned, the JDBC driver must      support t   he
     * ret  urning of auto-generated keys for    at le    ast    SQL INSERT statements
      *<p>
     *    @exce     ption SQL    Exception if   a d    a   tabase acces    s error oc       curs
     * @since 1.      4
     */
    boolean supportsGetGen       eratedKeys() th  rows SQLExc     eption   ;

    /**
          * Retrieves   a description of the user-defined type           (UDT) hi  erarchies defined in a
     * particular schema in this datab  ase. Only the immediat e super type/
     * su    b type relationship is modele d.
          *   <P>         
           * Onl       y    supe   rtype info       rmation fo     r UDTs matching the catal  og,
     * schema, and type name is returned. The type name          param               eter
           * m  ay      be      a fully-qualif    ied na me.      Wh   en the UDT n a  me supp lied    is a  
     *        fully-qualified name, the catalog and schemaPat    ter    n parameters are
     * ignored.
     * <   P>
     * If a UDT    does not have a direct super type, it is not listed here.
     *  A ro       w    of th  e <c       ode>  Resul  tSet</code> object returned by  this method
             *   describes the des             ignated UDT and a direct superty  pe. A row has the following
     * columns:
     *  <OL>
     *  <  LI><B>TYPE_CAT</B> String {@code =>} the UDT's cat           al     og (may be <cod     e>null</code>)
          *  <LI><B>TYPE_SCHEM</B> Str ing     {@code =>} UDT's schema (may be <co  de>n   ull   <  /code>)
     *    <LI>       <B>TYPE_NAM       E</B> String {@code =>} type name of the UDT
     *  <LI><B>SUPERTYPE_CAT</B> String {@code =>} the direct supe r      t            ype's catalog
        *                           (may b    e <code>n   ull</code>)
     *  <LI><B>SUPE   RTYPE_SCHEM</B> St   ring {@code =>} the dire   ct su        per     ty  pe'   s schema
     *                                           (may be <code>null</code>)
     *  <L        I><B>SU P  ERTY    P    E_NAME     </B> Stri     ng {@code   =>} the direct super type'   s name
       *   </OL>
     *
            * <P     ><B>No t           e:</B> If t       he driver does not support type hierarchies, an
     * empt  y result set is returned.
     *
           * @param catalo   g a cata    log name; "" retrieves those without a catalog;
     *        <code     >n     ull</code> means drop catalog name    from the selection criteria
     * @p aram schemaPattern   a sc    hema name      pattern; "" retrieves thos     e   
           *        withou    t a schema  
     * @param typ       eNamePat     tern a UDT name  pat   tern; may be       a fully-qualifie    d
         *        name
      *       @return a <cod   e>Resu ltSet<   /code> obje      ct     in which a row give     s informa       tion     
     *         abo ut the designa  ted UDT
     * @throws  SQLExceptio    n if a database acce     ss error oc      curs
     * @see #   g  etSearch   StringEscape
        * @since 1.4
     */
    ResultSet    ge  tSuperTy   pes(String catalog, String schemaPatte    rn,
                                     Strin   g typeNamePattern) thr       ows      SQLExcep   t   ion;
  
    /**
     * Retrieves      a descripti   on of the table hierarchies def          i  ned in a particul ar
     * schema        in this database.
       *
        * <P>On   ly supertable    information for tables matching t     he cat  alog, schema
         * and      table name are returned. Th  e table name param  eter may be a fully-
       * qualif   ied name, in whic     h cas    e,  the    ca      talog and     schemaPattern para      meters
     * are ignored.   If    a table does not have a    super table, it is not lis    ted here.
     * Supertable    s have   to be defined in the same catalog an    d schem     a as the   
     * sub           tables. Therefore, t       he   type descrip    tion do   es no   t nee        d t  o include
     * this inform    ation f     or          the superta    ble.
     *
       * <P>Each type de   scri          ption has the following colum  ns:   
     *  <OL>
     *  <LI><B>TABLE_CAT</B> String {@code =>} th e type's catalo    g (may be <code>null</code>)
     *         <LI><B>TABLE_SCHEM</B> Str     ing {@code =>} type's   s che      ma (m             ay be   <code>  nu  ll</code>   )
                    *    <LI><B>   TABLE_NAME  </B> Str       ing {@code =>}               t ype name
     *  <LI><     B>SUPERTABLE_NA ME</B> String {@code =>}  the direct super type's na  me
     *  </OL    >
        *
     * <P><B>Note:</   B   >           If the driver does not support typ e hierarchies, an
     * empty result set is r  eturned.      
      *
          * @par   am catal   og a catalog name ; "" retrieves     those w   ithout a catalog;
     *            <code>null</code> means drop catal  og name from the select       ion criteria
     * @p  aram schemaPattern a   schema nam       e pattern; "" retrieves thos     e
     *           with     out a schema
     * @param    tableNamePattern a ta      ble name pattern; may be a fully-qualified
       *        name
         * @return a <co      de>ResultSet     </code> object in w   hich each row is a type description
     * @throws SQLException if a     database access error occurs
         * @see #getSearch         StringEscape
     * @    since 1.4
     */
    ResultSet getSuperTabl           es    (String catalog, String schemaPatte rn,
                                          String tableNamePattern) th     rows SQLException;

    /**
     * Indicates tha   t <code>NULL</cod    e> values might not be allo    wed.
      * <P>
     * A possible value for  th   e column
        * <code>NULLABL   E</code> in the <code   >ResultSet</code> object
          * returne d by the met    h      od <code>     getAttrib  utes</code>     .
        */
        short   attributeNoNulls = 0;

    /**
     *   Indicates that    <c    ode>NUL   L</code> values are d  efin      itely a     llowed.
      * <P>
     * A p     ossi    ble value for the column <code>NULLABLE</code>
     * in t    he <code>ResultSet</code> obje   ct
        * retu  rned by the method <code>getAt    tributes</code>       .
                 */ 
    short att    rib     uteNullable = 1;

       /**
     *     Indicate    s that          whether <code>NULL</cod e> values are allowed is not
       * known.
     * <P>
     * A poss   ible va lue f  or the column  <code>NULLABLE</code         >
         *  in the <c     ode>ResultSet</code>   object
     * returne    d by the method <code>getAtt                 ributes</code        >.
       */
    short      att ributeNullableUnknown = 2;

      /**
     * Ret rie    ves a    descripti  on of th       e given   attribute of th   e give  n type
     * for      a user-d    efined type (UDT) that is availabl           e  in                the given s         chema
        * and  catalog.
       * <P>
      * Descriptions are returned o    nly for attributes of U  DTs matching the
     *     catalog, schema, type,           and at  tribute name criteria. They are ordered by
     * <code>TYPE_CAT</c    ode>, <c     ode>T    YPE_SCHEM</    cod           e>,
     * <code>TYPE_NAME</code> and <code>ORDINA   L_POSITION</code>. This description
     * does not con   tain inherited attributes.
     * <P>
     * The <code>R     esultSet</code> object t    hat is returned has the following
     * columns:   
     * <OL>
     *       <   LI><B>TYPE_CAT</B> String {@code =>} type catalog (may be      <code>null       </code>   )
     *  <LI><B>TYPE_SCHEM        </B> Strin   g {@code => } typ       e schema (may be <code>null</co    de>)
     *  <LI><B>  TYPE_N  A  M     E</B> S              tring {@c      o    de =>} type      name
     *  <LI><B>ATTR_NA               ME</B> St ring {  @code =>} attribute nam   e
     *   <LI><B>DATA_TYPE<   /B> int {@code =>} attribute type SQL type from java.s         ql.Types
     *    <LI><B>ATTR_TYPE_NAME</B> String {@code =>} Data source dep                endent type name.
       *  Fo    r   a UDT, the type name is fully qualified .     For a REF, the type n       am    e is
     *  fully qualified      and represents t    he target type     of the     reference ty     pe.
       *  <LI><B>A          TTR_SIZE</B> int {@code =>   } column size.  For cha   r o   r date
     *      types this is th e  maximum number of characters; for numer  ic or
     *              decimal t    yp es this   is precision.
     *  <LI><B>DECIMAL_DIGIT     S</B> int {@code =>   } t         he number of fr  actional digits. N    ull    is returned for     data typ   es   where
          * DEC   IMAL_DIGITS i     s not app    licable.
     *  <LI><B>NUM_PREC_RAD     IX</B    > int {@code =>} Radix (   typically     either 1     0 or 2)
     *  <LI><B>NULLABLE</B> int {@    code =>} whether NULL is         allowed
     *        <UL>
       *      <     LI> at      tributeNoNulls - m      ight   not allow NULL val ues
     *      <LI> attributeNullable - definitely a  llows  NULL values
     *      <LI> attributeNull        ableUnknown - nullabi  lit    y unknown
     *        </UL>
     *  <LI><B>REMARKS</B> S  tri    n      g {@code =>} comment describing column (may be <code>null</code>)
     *  <LI     ><B>ATTR_DEF</B> String        {@cod e =>}   defau  lt value (may be <code>null</code>)
     *  <LI><B >SQL_DATA_TYPE</B> int {@code =>} unused
     *     <LI><B>SQL_DATETIME_   SUB   </B> in   t {@code =>} unus     ed
     *  <LI><B>CHAR_OCTET_LENGTH</B> int   {@code =>} for ch  ar types the
     *           maximum number of byt       es in the     column
     *  <LI><B>ORDINAL_POSITION</B> int {@code =>} index of the    attribute in the UD   T
     *                  (starting at    1)
     *  <LI><B>IS_NULLABLE</B> String  {@cod       e =>} ISO   rules are used to det  ermine
     * the nullability fo    r a attribute.
                 *         <UL>
     *           <L    I> YES             --- if the    attribute can include NUL  Ls
     *            <LI> NO            -     -- i   f the attr   ib  ute cannot i   nclud  e NULLs
     *       <LI> emp       ty string  --- if the nullability for     the
     * at            tribute is unknown
     *          </UL>
          *  <LI><B>SCOPE_CATALOG</B> String {@code =>} catal         og of table t   hat is the
     *      scope of a reference attribute (<code>null</code      > if DATA_TYPE isn't REF)
     *  <LI><B>SC  OPE_SCHEMA</B> String             {@      code =>} schema  of tab     le that i  s          the
       *      scope     of  a referen  ce attribute (<cod e>null</c  ode>   if DATA_T   YPE isn't   REF)
     *  <LI><B>SCO PE    _TABLE</B> String {@code    =>} table name that is the scope of a
     *         refe  rence a ttribute (<code>null           </code> if the DA   TA_TYPE isn't REF    )
       * <LI><B>SOURCE_DATA_TY     PE</B> short {@code        =>} source     type of   a distinct type   or user-  gen erated
     *      Ref type,SQL type f  rom java.s    q    l.Type     s (<code>null</code> if DATA  _TYPE
     *      i      sn't DISTINCT or user-generated R EF) 
     *  </O  L>
     * @param c  atalog a   catalog name  ; must matc   h the catalog name as it
     *         is stored in the da    tabase; "" retriev    es those without a ca     tal   og;
     *           <code>null</code> means tha t the     ca    talog     name      should not be used to narrow
     *          th      e search
     * @param sc    hemaPattern a schem  a name patter   n; must ma    tch the     sche   ma name
     *        as it is stored in the datab             ase; "" retrieves those witho  ut  a     sche    ma;
     *        <co de>    null</code> means th  at    the schem          a name should not be used to narrow
     *        the search
           * @  param typeNamePattern a type na me pattern; must match the
     *        type na   me   as it is stored in the database
     * @param attribut      eNamePattern an at tribute name pattern; must match the attribute
     *        n    ame as it is declared in   the database
       *            @return a <code>ResultSet</code> object in which each    row is an
         *            attribute description
     * @exception SQLException if      a database access err    or occurs
     * @       see #getSearchString  Escape
     * @since 1.     4
     */
       Re      sultSet getAtt   ributes(Str   ing catalog, String schem   aPattern,
                                                 String typeNamePattern,     String attributeNamePattern)
        throws SQL   Exception;

           /**
           * Retri        eves whe  ther this data  base supports the given r    es   ult      set holdability.
              *
     * @param holdability one of th  e following constants:
     *          <code>    ResultSet. HO   LD_CURSORS _OVER_COMMIT</co       de> or
     *          <code>ResultSet.CLOSE_CURSORS_AT_COMMIT</code>
     * @r eturn <code>true</code> if so; <code>false</code> otherwise
     * @exception SQLException if a dat    abas       e access error      occurs
     * @see Connecti    o    n
         * @since 1     .4
          */
    boolean sup   portsResultSetHoldability(int holdability) th     ro    w  s SQ  LException;

          /**     
     * Retrieves this database's default h   oldability for <code>ResultSe  t</code>
     * objects.  
     *
         * @retur n th   e defau           lt holdability       ; either
     *         <code>Res  ultSe    t.HOLD_CURSORS_OVER_COMM        I     T</code> or
     *         <code>ResultSet   .CLO SE_CURSORS_AT_COMMIT</code>
     * @exception          SQ       L Exc  eption if a  database access error occurs
        * @since 1.4
     */
    int getR   esultSetHoldability() throws SQLEx      ception;    
 
    /**
     * Retrieves the ma  jor versi   on number of the underlying database.
      *
     * @return the underlying datab     ase's major versi  on
       * @      exception SQLException if a database access     error occurs
     * @s ince 1.4
          */
    int    g   etDatabaseMajorVersion() throws SQL     Exception;

    /**
     * Re    trieves t    he minor version number of  the underly ing   database.
          *
     * @return underlyi   ng datab          ase's minor    version
               * @exception  SQLE   xception if a datab   ase access error occurs
     * @since 1.4
         */
        int getDatabaseMinorVersion()      thr  ows SQLException;

    /**
     * Retrieves the major JDB C ver   sio    n number for t h   is
     * driver.
     *
     * @return JDBC version major number
     * @exception SQLException if a database    access er      ro    r occu rs
     * @since 1.4
            */
    int  getJDBCMajorVers    ion()     throws SQLException;

    /**
     * Retrieves the minor JDBC ve rsion numb   er f   or this
     * driver.
         *
     * @return JDBC version        minor numb      er
     * @excep   t  ion SQLE  xception if a d    atabase access er    ror occurs
        * @s ince 1.4
     */
    int getJDBCMi  norVersion() throws SQ   LException;

    /**
      *  A possible    return value for the method
     * <code>Database      MetaData.ge tSQLSt  ateTy   pe</code> which is     u  se  d to indicate
     * whether the value returned by the method
     * <code>SQLEx     ception    .getSQLState</code> is an
     * X/Open (     now know as Open Group) SQL CLI SQLSTATE value.
     * <P>
     * @since 1.4
     */
    int sqlStateXOpen =  1;

    /  **
     *  A possible  return value for the method
      * <code>DatabaseMetaData.getSQLStat eType</code> whic   h     is used to i    ndicate 
     * whether the v      alue retur          ned             by the    method  
     * <code> SQLException.getSQLState</code> is an SQLSTATE val ue.
     * <P>
     * @since 1.6
     */
    int sqlStateSQL =   2;

       /**
     *  A    po  ssible return value for the me  th  od
        * <code>DatabaseMetaDa     ta.getSQ         LStateType</code> which is   used to   indicate    
      * whether the value retu  rned by      the method
     * <code>SQLExc    eption .g      etSQLState</code> is an SQL99 SQLSTATE value.      
     *    <P>
     * <b>No   te:             </b>This constant remains only fo        r compatibility reasons.     Develop   ers
     * should use the constant <code>sql     StateSQL</code> instead.
     *
     * @si    nce 1.4
     */
    int sqlStateSQL99 = sqlStateSQL    ;

    /**
     * Indicates whether the SQLS   TATE retur      ned by <code>SQLExcepti  on.    getS      QLState</cod  e>
     * is X/Open (now known as Open Gro   up) SQL CLI or SQL:   2003.
     * @return the type of SQLSTATE; one of:  
     *        sqlStateXOpen or
          *        sqlSta teSQL
     *   @th   rows SQLException if a d    atabase access error occurs
     * @since 1.4
     */
      int getSQLStateType() throws SQLException;

    /**
        * I    ndicates whether updates m ade to a LOB are made  on     a copy or directly
        * to the  LOB.
     * @retur n <    code>true</code> if updates are made to  a copy of the LOB;
     *         <code>false</code> if updates are made dir     ectly to the LOB
     * @throws SQ       LException         if a datab  ase acce  ss error occurs
     * @since 1.4
     */
    bool   ean lo  catorsUpdateCopy() throws SQLException;

    /**
       * Retr ieves whether this database supports statement pooling.
     *
     * @return <c    ode          >true<       /code> if so; <code>false</code> otherwise      
         * @throws S     QLException if a database access error occurs
     * @s      inc  e 1.4
         */
    boolean supportsStatementPooling()   throws SQL  Exception;

    //  ------------------------- JDBC 4.0         ----       ----------------   ----       --    ---------

    /**
     * Indicates whether         or no  t this data source supports the SQL <code>ROWID<     /code> type,
     * and if so  the lifet  ime for which a <c         ode>RowId</code> ob  j ect remains valid.
     * <p>
     * The returned int values have the following relationship:
     *     <pre>{@code
     *     ROWID_UNSUPPORTED < ROWID_VA     LID    _OTHER < ROWID_VALID_TRANSACTION
     *           < ROWID_VALID_SESSION < R    OWID_VAL    ID_FOREVER
     * }     </pre>
     * s  o conditional logic   such as
     * <pre>{@code
       *     if (metadata.  getRowIdLifetime()  > DatabaseMe  taData.ROWID   _VALID_TRANSACT   ION)
     * }</pre>
     * can be   used    .    Valid Forever means valid    across all Sessions,    and valid for
     * a Session me   ans  valid across all its contai   ned Transac   tions.
       *
        * @return the status indicating the lifetime of a <code>RowId</code>
     * @           throws SQLException if  a database ac   cess error occurs
     * @  since     1.6
       */
    RowIdLifetime getRow  IdLifetime() thr     ows SQLException;

    /**
       * Retrieves t   he schema names availab le in    this dat   abase.  The results
     * are ordered by <code>TABLE   _CATA  LOG</code> and
     * <code>TABLE_SCHEM</code>.
     *
     * <P>The schem   a columns are:
     *         <OL>
     *   <LI><     B>TABLE_SCHEM</B> String {@code    =>} schema name
     *  <LI><B>TABLE_CA  TALOG</B> String {@code =>} catalog name (may be <code>null</code>)
          *  </OL>
     *
     *
     * @param ca   talog a cat       al  og name; must mat    ch the catalog name as it is stored
     * in the database;"   " retrieves those wi    thout a       catalog; null mea      ns cata    log
     * name  should not be used to narrow down the search.
     * @par  am s   chemaPattern a schema name; must match the schema name as it i       s
         *   stor        ed in the data   base; null means
           * sche ma name should     not be u    sed to narrow down the   search.
          * @return a <code>ResultSet</code> object     in which each ro    w is a
          *         schema description
     * @exception SQLException if a database access erro   r occu  rs
     * @see #getSearchStringEscap  e
       * @since 1.6
     */
    ResultSet getSchemas(String catalo     g, String schemaPat   tern) throws SQLException;

    /**
     * Retrieves    whethe    r this database supports in  voking us  er-defined or vendor functio   ns
     * using the stored procedure escape syntax.
     *
     * @     return <code>true</code> if so; <code>fals  e</code>  otherwise
     *    @exception SQLException if a database access error occurs
     *    @sin  ce 1.6
     */
    boolean supportsStoredFunctionsUsingCallSyntax() throws SQLExcep     tion;

    /**
        *     Retriev     es whether a <code>SQLExcepti   on</code> while autoCommit is   <c  ode>tr   ue</code> ind   icates
        * that all open ResultSets are   closed, even ones that are holdab    le.  When      a <code>SQLExc eption</code> occurs whi   le
     * autocommit i s <code>true</cod   e>, it is vendor specific whether the JDBC driver res        pond    s with a commit operation, a
     * rollback operation, or by doing neither    a commit nor a rollback.  A potential result of this difference
     * is in whether or not holdab  le Res    ultSet       s are closed.
     *
     * @ret  urn < code>true</      code> if so; <code  >false</code> otherwise
     * @exception SQ   LExcepti    on if a d  at abase  access error occurs
     * @since 1.6
     */
    boolean autoCommit    FailureClosesAllResul  tSets  ( ) throws S    QLException;
        /**
               * Retrieves a list of the cli  ent info properties
                  * that the driver support     s.  The result set contains the following columns
         *
         * <ol>
         * <li><b>NAME</b>   String{@code =>} The name of the client in    fo    property<br>
         * <li><b>MAX_LEN</b> int{@cod  e =>} The   maximum length of the value for t  h     e property<br>   
         * <li><b>DEFAU    LT_VALUE</b> String{@code =>} The default valu  e of the proper   ty<br      >
         * <li><b>DES  CRIPTION</b> String{@code =>} A     description of the property.  This will typically
         *                                              contain information as to where this property is
                    *                                               stored in the datab        ase.
              * </ol>
         * <p>
         * The <code>ResultSet</code> is sorted by the NAME column
            * <p>
                  * @retur n      A <code>ResultSet</code> object; each row is a   supported client info
         * pro   perty
              * <p>
                *  @ex   ception S  QLEx   ception i  f a database access error occurs    
         * <p>
             * @since 1.6
                 */
        ResultSet       getClientInfoPropertie    s()
                    th  rows SQLException;

    /**
     *   Retrieves a description of the  system and user functions available
     * in t      he given cat  alog.
     * <P>
     * Only syste   m an   d user functio   n descriptions mat   ching the sche ma and
     * function name criteria are returned.  They are ordere   d by
        * <code>FUNCTION_CAT</co de>,    <c ode>FUNCTION_SC HEM</code>,
     * <code>FUNCTION_NAME</code> and   
     * <code>SPECI    FIC_ NAME</code>.
         *
     *    <P>Each function d   escription   has the the f o      llowin g columns:
     *  <OL    >
     *  <LI><B>FUNCTION_CAT</ B> S             tring {@code =>} function catalog (   may be <code>null</code>)
     *  <LI><B>FU       NCTION_SCHEM</B> String {@code       =>} function schema (may be <code>   null</code>)
     *     <LI><B>FUNC     TION_ NAME</B> St     ri     n  g {@code =>} fun   ct   ion name.  T   his i  s the name
     * used to invoke the    function
     *       <LI><B>REMARKS</B> String {@code =>} explanatory c omment on the func     tion
     * <LI><B>FUNCTION_TYPE<  /B> short {@code =>} kind of function:
     *         <UL>
     *      <LI>functionResultUnknown - Cannot determine if a return value
     *       or table will be returned     
     *      <L   I> fu   nctionNoTable- Does not return a table
            *      <L I> functionReturnsTab   le - Returns a table
     *      </UL>
     *  <LI><B>SPECIFIC_NAME</B> String  {@code =>} the name which uniquely identifies
     *  this function within its sche    ma.  This is       a user specified, or DBMS
     * generated, name that ma     y be different then the <code>FUNCTI   ON_NAME</cod    e>
        * for example with overload functions
     *  </OL>
         * <p>
     * A user may not have permission to execute any    of the functions that are
     * returned by <code>getFunc tions</code>
     *
     * @param catalog a catalog name; must match the catalog name as it     
     *          is     stored in the database; "" retrieves those without a catalog;
        *        <co  de>null</code> means that the catalog n   am  e should not be used to narrow
     *        the search
         * @param      schemaPattern a schema name pattern; must match the    schema name
     *             as it is s       tored in the data base; "" retrieves those without a schema;
     *        <code>null</code> means that the schema name should not be used to narrow
     *        the search
     * @pa  ram functionNamePattern a function name pattern;    must match the
     *        function name as it is stored in the database
     * @return <code>ResultSet</code> - each row is a functio  n descriptio      n
     * @exception SQLException if a database access error occ    urs
     * @see #get   Sea  rchStringEscape
     * @since 1.6
       */
    Resu  lt     Set getFunctions(S tring catalog, String schemaPattern, 
                            String functionNamePatt      ern)    throws SQLException;
    /**
      * Retrieves a description of the gi      ven ca talog's system or user
     * function parameters and retu rn type.
     *
     * <P> Only de   scr iptions matching the schema    ,  function and
     * parameter name criteria are returned. They are ordered by
     *       <code>FUNCTION_CAT</    code>, <code>FU NCTION_SCHEM</code>,
          * <      code>FUNCTION_NAME</code> an  d
     * <cod    e>S PECIFIC_ NAME</code>. W  i thin this, the return value,  
     * if   any, is first. Next are the parameter descriptions    in call
        *      order. The column de   scriptions   follow in column number order.
     *
     * <P>Each row in the <code>ResultSe    t</code>
     * is a parameter description, column description or
     * return type    description with   the following fields:
     *  <OL>
     *  <LI><B>FUNCTION_CAT</B> String {@code =>} function catalog (may be <code>null</code>)
     *   <LI><B>FUNCTION_SCHEM</B> String {@code =>} function sch     ema (may be <      code>null</code>)
     *  <LI><B>F  UNCTION_NAME</B> String {@code =>} function name.  This is the name
     * used to invo  ke the functio  n
     *  <  LI><B>COLUMN_NAME</B> String {@code =>} column/parameter name
     *  <LI><B>COLUMN    _TYPE</B> Short {@code =>} kind    of column/  paramet er:  
     *      <U    L>
     *      <LI> functionColumnUnknown - nobody knows
     *             <LI> functionColumnIn - IN parameter
     *         <LI> function  ColumnIn Out - INOUT parameter
     *      <LI> functionColumnOut - OUT parameter
     *      <LI> functio nCol umnReturn - function return value
     *      <LI> functionColumnResult - Indicat  es that      the parameter or column
     *  is a column in the <code>ResultSet</code>
     *      </UL>
     *  <LI><B>DATA_TYPE</B> int   {@co   de =>} SQL type from jav a.sql.Types
     *  <LI><B>TYPE_NAME</B> String {@code =>}    SQL type na    me, for a UDT t    ype the
          *  type name is      full y qualified
     *  <LI><B>PRECISION</B> int {@code =>} precision
          *  <L    I><B>LENGTH</B> int {@code =>} length in bytes of data
       *  <LI><B>SCALE</B> short {@code =>} s   cal    e -  null is returned for data types where
     * SCALE is not applicable.
     *  <LI      ><B>RADIX</B> s  hort {@code => } radix
     *  <LI><B>NULLABLE</B> short {@code =>} can it co   ntain NULL.
     *         <UL>
     *      <LI> functionNoNul      l    s - does not all    ow NULL values
     *      <L  I> functionNul    lable -        allows NULL values
       *      <LI> functionNullableUnknow  n - nullability unknown
     *      </UL>
         *    <L  I><B>REMARKS</B> Str   ing {@code =>} comment describing column/parameter
     *  <LI>< B>CHAR_OCTET_LENGT    H</B> in t  {@code =>} t  he maxim   um leng th of b   inary
     * and character base   d parameters or columns.  For  any other datatype the   returned value
     * is a NULL
     *  <LI><B>ORDINAL_POSITION</B> int  {@code =>} the ordinal positio    n, starting
     * from 1,     for the input and output param     ete    rs. A value of 0
     * is returned if this row    describes the function's return value.
         * For result set      columns, it is the
     * ordinal position of the column in the result set starting from 1.
     *  <LI><B>IS_NULLABLE</B> String  {@code =>} ISO rules are used to determine
            *  the nullability for a parameter or column.
     *       <U  L>
     *       <LI > YES           ---   if the para meter or column can include NULLs
     *       <LI>   NO            --- if the parameter or column  cannot include NULLs
     *       <LI> empty str   ing  --- if th    e nullability for the
     * parameter  or col      umn        is unknown
     *          <   /UL>
     *  <LI><B>SPECIFIC_NAME</B> String  {@code =>} the name which uniquely identifies
          * this    function within its schema.  This is a user specified, or DBM S
     * generated, name that may be diff erent then the <code>FUNCTION_NAME</code>
              * for    example with overload functions
     *  </OL>
     *
     * <p>The PRECISION column represents the specified col   umn size for the given
     *       parameter or column.
     *    For n     umeric data,   this is     the maximum precisio    n.  For     character data, this is the length     in characters.
     * F  or datet ime datatypes, th   is is the length in c     haracters of the String representation (a   ss   uming the
     * maximum allowed precision of the fractional second   s componen   t). For binary data, this is the length in bytes.          For the ROWID datatype,
        * this is the lengt       h in bytes. Null is returned for data types where the
     * column size is not applicable.
     * @param catalog a catalog name; must match the catalog name     as    it
     *        is stored in the databas  e; "" retrieves      those without a catalog;
     *        <code>null    </code> means that the catalog       name should not be used to narrow
     *        the search
     * @param schemaPattern a schema name pa    ttern; must match the schema name
     *         as it is stored in the database;   "" retrieves those without a schema;
          *        <code>null</code> means that the schema name should not be used to narrow
     *        the search
         * @param functionName Pattern a procedure name pattern; must match the
     *        function name as it is stored in the database
          * @param columnNamePattern a        parameter name pattern; must match the
     * parameter or column name as it is stored in the database
      * @return <code   >ResultSet</code> - each   row describes a
     * user function parameter, column  or return type
     *
     * @exception SQLException if a database access error occ  urs
     * @see #getSearchStringEscape  
     * @since 1.6
     */
    ResultSet getFunctionColumns(String catalo      g,
                                     String schemaPattern,
                                   String functio  nNam    ePattern,
                                  String columnNamePatte   rn) throws SQLEx    ception;


    /**
     * Indicates that type of the parameter or column is unknown.
     * <P>
     * A possible value f or the column
     *     <code>COLUMN_TYPE</code>
        * in the <code   >ResultSet</code>
     * returned by the method <code>getFunctionColumns</code>.
     */
    int functionColumnUnknown = 0;

    /**
     * Indicates that the parameter or  column is an    IN parameter.
     * <P>
                *  A possible value for the column
     * <code>COLUMN_TYPE</code>
     * in the <code>ResultSet</co   de>
     * returned  by the method <cod e>getFunctionColumns</code>.
     *   @since 1.6
     */
    int functionColumnIn = 1;

    /**
      * Indicates that the parameter or column is an INOUT parame    ter.
     * <P>
     * A possible value for the column
     * <code>COLUMN_TYPE</code>
     * in the <code>ResultSet   </code>
     * returned by    t    he method <code>getFunctionColumns</code>.
     * @since 1.6
     */
    int functionColumnInOut = 2;

    /**
     * Indicat    es that the parame  ter or column is an OUT p   arameter.
      * <P>
     * A possible value for the column
     * <code>COLUMN_TYPE  </code>
     * in the <code>ResultSet</code>
     * returned by the method <code>getFunctionColumns</code>.
     * @since 1.6
     */
      in   t functionColumnOut = 3;
    /**
     * Indicates that the parameter or column is a return value.
     * <P>
     *  A possible value for the column
     * <code>COLUMN_    TYPE</code>
     * in the <code>ResultSet</code>
     * returned by the method <code>getFunctionColumns</code>.
        * @since      1.6
     */
    int fu     nc  tionReturn = 4;

       /**
      * Indicate  s that the paramete  r or column is a column in a result set.
     * <P>
     *  A poss    ible valu  e for the column
     * <code>COLUMN_TYPE</c    ode>
     * in the <code>ResultSet</code>
     * returned by the method <code>getFunctionColumns</code>.
     * @since 1.6
     */
    int functionColumnResult = 5;


     /**
     * Indicates     that <c ode>NULL</code> values are not allowed.
     * <P>
         * A possible value for the column
     * <code>    NULLAB   LE</code>
     * in the <code>ResultSet</code> object
     * returned by the method <code>getFunctionColumns</code>.
     * @since 1.6
     */
    int functionNoNulls = 0;

    /**
     * Indicates that    <code>NULL</c        ode> value   s are allowed.
     * <P>
         * A possible value for the column
     * <code>NULLABLE</code>
     * in the   <code>ResultSet</code> objec     t
     * returned by the metho  d <cod    e>getFunctionColumns</code>    .
     * @since 1.6
     */
    int functionNullable = 1;

    /**
     * Indicates that     whether <code>NULL</code> va   lues are allowed
     * is unknown.
             * <P  >
     * A possible value for the column
     * <co   de>NULLABLE</code>
     * in the <code>R      esultSet</code> object
     * returned by the met  hod <code>getFunctionColumns</code>.
     * @since 1.6
     */
    int functionNullableUnknown = 2;

    /**
     * Indicates that it is n   ot known whether the function returns
     * a result or a table.
       * <P>
     * A possible value for co     lumn <code>FUNCTION_TYPE</code> in the
     * <code>ResultSet</code> object returned by the method
     * <code>getFunctions</code>.
     * @since 1.6
     */
    int functionResultUn    known   = 0;

    /**
     * Indicates that the fun       ction  does not return a table.
          * <P>
     * A possible value for column <code>FUNCTION_TYPE</code> in t     he
     * <co    de>ResultSet</code> object returned by the method
     * <code>getFunctions</code>.
     * @since 1.6
     */
    int functionNo   Table         = 1;

    /**
     * Indicates that the function  returns a table.
     * <P>
        * A possible value for column <code>FUNCTION_TYPE</code> in the
     * <code>ResultSet</code> object    returned by the method
     * <code>getFunctions</code>.
     * @since 1.6
          */
    int functionRetu  rnsTable    = 2;

        //--------------------------JDBC 4.1 ----------------- --   ----------

    /**
     * Retrieves a description of the pseudo or hidden columns available
     * in a given t     able within the specified cata    log and schema.
     * Pseudo or hidden columns may not always be sto    red withi    n
     * a table and are not visible in a Resul    tSet unless they are
     * specified in the query's outermost SEL   ECT list. Pseudo or hid   den
     * columns may not necessarily be able to be mo dified. If there are
     * no pseudo or hidden columns, an empty ResultSet is returned.
     *
     * <P>Only column descriptions matching the catalog, schema, table
     * and column name criteria are  returned.  They are ordered by
     * <code>TABLE_CAT</code>,<code>TABLE_SCHEM</code>, <code>TABLE_NAME</code>
     * and <code>COLUMN_NAME</code     >.
     *
     * <P>Each column description has the following columns:
     *  <OL>
     *  <LI><B>TABLE_CAT</B> String {@code =>} table catalog (may be <code>null</code>)
     *  <LI><B>TABLE_SCHEM</B> String {@code =>} table schema      (may be <code>null</code>)
     *  <LI><B>TABLE_NAME</B> String {@code =>} table name
     *     <LI><B>COLUMN_NAME</B> String {@code      =>} column   name
     *  <LI><B>DATA_TYPE</B> int {@code =>} SQL type from java.sql.Types
     *  <LI><B>COLUMN_SIZE</  B> int {@code =>} column size.
     *  <LI><B>DECIMAL_DIGITS</B> int {@code =>} the number of fractional digits. Null is returned for data types where
     * DECIMAL_DIGITS is not applicable.
     *  <LI><B>NUM_PREC_RADIX</B> int {@code =>} Radix (ty pica   lly either 10 or 2)
     *  <LI><B>COLUMN_USAGE</B> String {@code =>} The allowed usage for the column.  The
     *  value returned will correspond to the enum name returned by {@link PseudoColumnUsage#nam  e PseudoColumnUsage.name()}
     *        <L  I><B>REMARKS</B> String {@code =>} comment describing column (may be <code>null</code>)
     *  <LI><B>CHAR_OCTET_LENGTH</B> int {@code =>} for char types the
     *       maximum number of bytes in the column
     *  <LI><B>IS_NULLABLE</B> String  {@code =>} ISO rules are used to determine the nullability for a column.
     *       <UL>
     *       <LI> YES           --- if the column can include  NULLs
     *       <LI> NO            --- if th e column cannot include NULLs
     *       <LI> empty string  --- if the nullability for the column is unknown
       *       </UL>
     *  </OL>
     *
     * <p>The COLUMN_SIZE column specifies the column size for the given column.
     * F   or numeric data, this is the maximum precision.  For character data, this is the length in characters.
     * For datetime datatypes, this is the length in characters of the String representation (assuming the
     * maximum allowed precision of the fractional seconds component). For binary data, this is the length in bytes.  For the ROWID datatype,
     * this is the length in bytes. Null is returned for dat   a types where the
     * column size is not applicable.
     *
     * @param catalog a catalog name; must match the    catalog name as i    t
     *        is stored in the da     tab    ase; "" retrieves those without a catalog;
     *        <code>null</code> means that the catalog name should not be used to narrow
     *        the search
     * @param schemaPattern a schema name pattern; must match the schema name
     *        as it is stored in the database; "" retrieves those without a schema;
     *        <code>null</code> means that the schema name should not be used to narrow
     *        the search
     * @param tableNam    ePattern a table name pattern   ; must match the
     *        table name as it is stored in the database
     * @param columnNamePattern a co  lumn name pattern; must match the column
     *        name as it is stored in the database
     * @return <code>ResultSet</code> - each row is a column description
     * @exception SQLException if a database access error occurs
     * @see PseudoColumnUsage
     * @since 1.7
     */
    Result    Set getPseudoColumns(String catalog, String schemaPattern,
                         String tableNamePattern, String columnNamePattern)
        throws SQLException;

    /**
     * Retrieves whether a generated key will always be returned if the column
     * name(s) or index(es) specified for the auto generated key column(s)
     * are valid and the statement succeeds.  The key that is returned may or
     * may not be based on the column(s) for the auto generated key.
     * Consult your JDBC driver documentation for additional details.
     * @return <code>true</code> i     f so; <code>false</code> otherwise
     * @exception SQLException if a database access error occurs
     * @since 1.7
     */
    boolean  generatedKeyAlwaysReturned() throws SQLException;

    //--------------------------JDBC 4.2 -----------------------------

    /**
     *
     * Retrieves the maximum number of bytes this database allows for
     * the logical size for a {@code LOB}.
     *<p>
     * The default implementation will return {@code 0}
     *
     * @return the maximum number of bytes allowed; a result of zero
     * means that there is no limit or the limit is not known
     * @exception SQLException if a database access error occurs
     * @since 1.8
     */
    default long getMaxLogicalLobSize() throws SQLException {
        return 0;
    }

    /**
     * Retrieves whether this  database supports REF CURSOR.
     *<p>
     * The default implementation will return {@code false}
     *
     * @return {@code true} if this database supports REF CURSOR;
     *         {@code f    alse} otherwise
     * @exception SQLException if a database access error occurs
     * @since 1.8
     */
    default boolean supportsRefCursors() throws SQLException{
        return false;
    }

}
