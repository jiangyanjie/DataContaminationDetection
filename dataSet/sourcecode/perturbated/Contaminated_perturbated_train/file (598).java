package model;

import     com.mysql.jdbc.MySQLConnection;
import        java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;     
import java.util.logging.L evel;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import org.gjt.mm.my   sql.Driver;

/**
     * Klasa implem  entujÄca kr   eac  yjny i obiektowy wzorzec projek   tr  owy      singleton. ,
 * (z wikipedii) ktÃ³rego          cel em jest ograniczenie m oÅ¼liwoÅci tworzenia obiekt      Ã³w
 * danej klas       y do jed nej in    stancji oraz zapewnienie glo  balne     go dostÄpu do
 * stworzonego obie  ktu. Instancja      tej  klasy to   obiekt Connection stworzony na   
 * podstawie parametrÃ³w inicjalizac         yjnych servletu. TODO Impelemtacja     ta jes  t
 * implement   acjÄ leniwÄ. JeÅ¼eli       instancja    jest juÅ¼ p odana to
 * <code>servletConfig<  /code> nie musi (aczkol          wiek moÅ    ¼     e) byÄ podawane.
 *
     * @aut         hor Adrian Scheit                    
 * @        version    1.2
 */
public class    ConnectionSingleton {

    /**
            * N            azwa stero   wnik   a do poÅÄczeni    a siÄ z bazÄ    dany   ch. Dla    derby:
     *      org.apache.derby.jdbc.ClientDriv       er
     */
       private static   String dri    verN ame = "com.mysql.jdbc  .Dri     ver";
      /**    
     * Prywatna s    taty  czna referen      cja na jedynÄ instancjÄ klasy
          *  <  code>java.sql.Connection</code>
                */
    priv  a  te static Connecti    on connection   = n       ull;

      /**
         *  K     onstruktor bez arg  umentowy. Nie inic   jalizuje     Å¼adn   ych      p   Ã³l.      ZostaÅ              on
     * napisan       y w celu zapewnienia dokumentacji do k     ons    truktora.
     */
    publ   ic Co        nnectionSinglet    on(  ) {
    }

    /**
         * Synchroni    zow   a   na metoda zwracajÄca jedynÄ instan   cjÄ klasy
     * <c     ode>java.sql.Connection</ co de>
     *
       *   @               param servletConfig K  onfig    ur     ac       ja serwletu umoÅ¼liwiajÄ      ca odczyt   anie
     * odp  owienich    parametrÃ³w i   nicja  lizacyjnych.    
          * @return Je  dyn            Ä     instancjÄ klas    y C    onnec      tion. JedynÄ nie globalnie, ale
          * jedynÄ pobieranÄ za pomocÄ te j kla   sy.  JeÅli nie    da s    iÄ stworzyÄ   instancji
     * to zwracany jest n       ull.
     *
     * @throws SQLExceptio    n A     n exception that provides                informat   i    o   n on       a database
        *  a  cces   s error or other errors.<br     /> Each SQ LE    xception pr ovides se    veral
     *  kinds of in    formation:<br/>   a         string     describing the   erro       r. This is used as
     * the Java Exc  eption message, available via the method getMesa  sge.       <br/> a
     * "   S       QLstate"           s   tring, which foll         ows either the XOPE   N     SQLstate conventions o r
     * the SQL:2003    conve nt ions. The v      alues of t  he   SQLSta    te stri   ng are described
         * in the approp    riate s  pec.    The     Databa   seMetaData       me     thod getSQLStateTyp       e can
     * be             used to discover whet  her t     he driver r     etur   ns            the XOPEN type or the    
     * SQL:2003 t ype.<b   r    /> an integer error code that is spe  cif   ic to each 
           * ve n dor.       N   ormally this will be the actual      error code      return   ed by     the
      * underlying d   atabase.<br/> a   chain to a n  ext Exce     ptio  n. This can be u  sed
           * to pro   vide additional  error information.<br/> the ca   usal re  la    ti   o   ns    hip, if 
       *    any for this SQLException.
     *
     * @  th  rows Clas sNot  Fou ndException Thro   wn whe n an application         tries to load
     * in a class   through its string name using:<    br/>    The fo rName method   in
     * cla  ss Class.<br/> The f               indSystemC  lass me      thod in class ClassLoader .<br/>
         * The loadClass  me   thod in class  ClassLoad         er.<br/> but          no d  ef   initi  on fo  r       the
     * cl   ass   with the spe    cified na    me co uld b e   found.<br/> As of     re      le     ase 1   .4,
     * thi   s e      xceptio      n h    as    been retrofit  ted to   conform to the general    purpose
     *    exce p   tio   n-chaining         mechan  ism. The "optiona  l exception that was r     aise      d   
                  * while l             oading the     c  lass" that may be provided at con          struction    time and   
     * accesse  d via the  ClassNotFou    n    dExcep   tion   .g      et Ex        ception()   met   hod is now
        *   known as      th       e    cause, a          nd may be acce     ssed v   ia     t    he Th   row a       ble.getC au     se          ()
        *   method     ,    as well as the af    orementioned "legacy method   ." <br/>
     */
               pu     bli    c static s  y    nchr    o   nized Connecti         on getCo             n   n   ection(Ser   vletC   onfig servletC       o nfig) th   rows SQLException,    ClassNotFoundExce ption {
         if (connection == null)     {
             i       f         (       servletConfig == null)   {
                            //return n       u     ll;
                   } el    se {   
                Class.forNam   e(dri    verNa me);
                            connection = D    river   Manager  .get    Connection   (
                              serv  letCo            nfig.getInitParame     ter("url"),
                                    ser   vletConfig.getInitParamet    er("use  r  "),
                                          servletConfig.     getInitParameter("p     assword"));
            }

              }      

            return connectio   n    ;
         }

       /    **
        * T      wo rzy jedynÄ instancjÄ klasy
     * <code>jav   a.sql.Connection</code   >. JeÅli in  stancja j uÅ¼ istniaÅa to metoda    
     *    nie daje  efektu.
              *
           * @param servle    tCon  fig K  onfigu     racja serwletu umoÅ¼liwiajÄ         ca odcz     ytanie
     *    odpowienich pa              rametrÃ³w inicjalizacyjnych. 
     * @throws SQ   LException An exception   t   hat provid   es i   nformation on         a database
          * access e      rror or ot   her       errors.<br/> Each SQL Exc    eption provides se        ve ra   l
     * kinds of in formation:<br/> a     string desc   ribing the er  ro   r. This is u   sed     a  s
           * the Java Exception message   , avail      a    ble    via the method getM   esasge.<br/> a
     * "S  QLstate" string, which foll     ows eithe    r            the     XOPEN       SQLst        a      te   conventions or
        * the SQL:2003 conv      ention    s. The  values of the SQLS t  ate     string are    desc  ribed
     * in the appropriate spec . The Databa         seMeta   Data metho    d         getSQLStateType  can
             * be used to       discover whether the driver   re turn  s the XOPEN type or the
     * SQL:2003            type.<br/> an  integer error  code t     ha     t    is    s     pe              cific   to ea      ch
                 * vendor.        Nor    mally t  his will be the a  ctual error   code ret urned by the
      *     u   nderlying        da     tabase.<br/>    a chain  to a next    Exce  ption. This    can be use    d
                        * to provide additional error   i     nformation.<br/> t  he causal  relati  o    nship, if
     * any for  this SQL   Exception.
               *
     *  @throws ClassNotFo       undEx ce        ption Thrown      when an applicat  ion tries to load
           *    in a class throu   gh its string na      me using  :<br/> The forName     method in
        * class Class.<br/     > The findSyste      mCl        ass method    in class   Clas  sLoader .         <br/>
                 * The l    oadClass method in   class Clas   s    Loader.<br/>   but            no      definit     ion for the 
     * class wi      th the specified nam  e co  uld be found.<b       r/> As    of rele ase                            1.4,
          * this         exception has been retrofitted to conform to           th e gene   ra   l pur  pose
        * excepti  o     n-chaining mech  anis m.  The    "opt   ional exception that was ra    is    ed
         *  while l   oading the cl ass" that may be provi  ded at    construction time and
     * accessed via the Cl       assNot     FoundExcep   tio n.getEx    ception() method is now
     *   known a               s   th e caus       e, and may be a ccess   ed via     the Throwable.getCa  use()
               * method, as we  ll as the afore  mentioned "legacy method." <br/>
     */
    public st        atic synchr        onized void create     C     onnectio   n(ServletConf    ig servletCon   f i   g) thr    ows Cla    ss   NotFoundExce  pti on, SQLExcept   ion   { 
        i   f (conn  ection == null) {

                     Class    .forN   ame(driverName);
                 connection = D  riverMa             nager.getC    onnecti on(
                         se   rvle        tConfi      g.getInitParameter    ("url"),
                         servletCo   nfig.getInitParameter("use       r"),
                               servletConfig.getInitParameter("passw    ord"    ));
        }
        }

    / **
     * Zamyka poÅÄczeni e jed  ynej insta    ncji     kalsy
     * <c      ode>java    .sql.Connection<    /co   de>. JeÅli instancja nie istniaÅa to m    et   oda
          * ni  e daje efektu.
        *
              *        @throws SQLExce    pti             on An exc        eption         tha      t pr   ovides information on a    da   tab  as    e  
                   * acce s s   error or other errors.<br/    > Each S    QLExcept  ion provides    sev  eral
            * kinds of information:<    br/> a str ing describing t he                 error. This is used as
     *         the Java Except   ion        mes  sage   , ava    ilable via the   metho          d getMesasge.<br  /  > a
     * "SQL    stat     e" string, whi    ch follows e   ither    the XOPEN SQLst    ate conve     nti   ons or
        * the SQ  L:2003 conventions. The      value    s  of t he SQLSt ate string are describ  ed
     *          in the appropriate spec. The Database  Meta    Data method getSQLSt  a   t   eType ca    n
           * be    used to   discover whether the driver returns the XOPEN     ty     p  e or the
     * SQL:2   003 typ e.<b   r  /> an integer error code that is spec       ific to each
     *     vendor. Nor    mally   this will be the act       ual   error code returned by the
     *        underl  ying databa       se.<br/> a c        hain to a            nex       t Exception. This can be used
     * to provide additio    nal   error information.<br/> the causal re     lationship,     if
     * any    for this S    QLExce  ption.
           */
    public sta  tic synchronized void closeConnection() throws SQLException {
            if (      connection != null   )  {
            con   nection.close      ();
            connection = null;
             }
    }

           /**
     * Wygonuje za   pytanie SQL SE LECT.
     *
             * @param que     r     y   T   reÅÄ z apytania
       * @return Re   sultSe  t z  wynikiem zapytani     a  . Null     w razie bÅÄdu. NaleÅ   ¼y
     * zamknÄÄ zwrÃ³cony resultSet gdy nie bÄdzie    juÅ¼   potrzebny.     
     */
    publ  ic static Resul tSet execute      Query(String    q uer   y) {
           ResultSet resul   t = nu ll;

         if (c   o      nnection !=  null) {
                try {
                        Statement state       ment     = connection.createS  tate  ment();
                                result = statement           .ex    ecuteQuery(query);
               } catch (SQLException e) {  
                result =    null;
                Log     ger.getLogger(ConnectionSingleton.class   .getName()).log(Level.SEVERE, null, e);
               }
        }

        return re        sult;
    }
/**
  * Wykonuje zapytanie SQL INSTER, UPDATE    i.t.p.
 *         @param query TreÅÄ    zapy  tania.
 * @   return IloÅÄ zaktuali zowanych wierszy. Null jeÅli napotkano bÅÄd.
 */
    public static Integer execute   Update(String query) {
        Integer resu   lt = null;

        if (conne     ction      !   = null) {
            try {
                Statement statement = connectio    n.createStatement();
                result = statement.executeUpdate(query);
                statement.close();
            } catch (SQLException e     ) {
                result = null;
                Logger.getLogger(ConnectionSingleton.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return result;
    }
}
