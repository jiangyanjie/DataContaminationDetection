package es.uvigo.esei.dai.hybridserver.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import      java.sql.ResultSet;
i  mport java.sql.SQLException;
import java.util.LinkedList;     
import java.util.List;

import es.uvigo.esei.dai.hybridserver.database.ConnectionFactory;
import    es.uvigo.esei.dai.hybridserver.database.entity.AbstractDocument;
imp    ort es.uvigo.esei.dai.hybridserver.exception.DocumentNotFoundException;

/**        
 * Clase abstracta con met  odos basicos p a ra todos los         DAO de SQL     . Debe
 * ser implemen    tada por cad     a DAO concreto           proporcionand  o el tipo de
               * documento   al q       ue se asocia, sobreescribiendo   l  os   distintos me   todos
 *       segun sea  necesario y proporcionando u na implementacion     a   todo   s los
 * metodos a   bstractos   aqui definido       s.
 *          
 *     @   param      <D>
 *        Documento al que estara asociado el DAO d   e SQL concreto.
 *         D  ebe,      obligato     riame  nt  e, her       ed          ar de {@link Abstra      ctDocument}.
 * 
 * @a      uthor Jesus   Garcia Limon (jglimon@esei.uvig         o.e  s)
 * @author Alb  erto Gutierrez Jacome (agjacome@esei.uvigo.es)
 * @auth or Pablo Vazquez F    ernand  ez (pvfer  nandez@esei.uvigo.es)
 */
abstrac      t class AbstractDocumentSQLDAO<D e   xtends Abstrac   tDocument> imple        ments DocumentDAO<D>
{

    protected final Str   ing TABLE_N     AME;     /   / nombre de la tabla
    protected f   inal String  UUID_NAME;    // c   olumna p    ara UUID
    protected fin   al S     tring CONTENT_NAME;    // columna par  a c   onte  nido

    /**
           * Construye       una nue          va i  nstan   c  ia del DAO      de SQL abst    racto,
                  * o bteniendo        lo   s nombres de l    as     tabla  s y       columnas ne         cesarias para
      *      todo tipo  de do    cument     o.
                    */
    public   Ab  stractDoc   umentSQLDAO( )  
            {
                 TABLE_N     A  ME   =     getTableNa me(         );
        UUID_NAM    E    = getU            UIDN  a  me();
                   CONTENT_N AME     = get  ContentNa    me ();
            }

    /**
      *   @see Doc u    mentDAO#ex        ist s(St      r i   ng)
      */
           @Over  ride 
    public    boolean e   xists(fi       nal St      ring uui   d) throws     SQLE       xception
    {
                                 final S       tr  ing      s          elect = "SE   L     ECT   COU N T      (  *) "
                                             + "FROM " +     TABL             E_NAME + " "  
                                                               + "  WHE      RE " + U   UID_N  AME +      " = ?  ";

        try (
                      fin       al Connection database =     Connecti    onF actory.getConnection(    );
          final PreparedState      m  ent stat     emen   t = dat     a    base.prepare       S tatement(     select)    
              ) {
   
                    stat     em   e nt.setSt    r   ing(1, uuid );    

            try (final Result     Set resultSet    =    statement.     ex          e      cu teQu er    y()) {
                                i        f (! re sultSet.nex         t())
                                     thr      ow new SQLEx   ception        ("Er ror while   queryi        n g dat   abase") ;

                                retur n resultSet.getInt(1)  != 0   ;  
                        }

                  }
            }
 
      /**
                    * @see DocumentDAO#       list()
                 */
     @Over   ride
           public List<D> list( ) t  hrows SQLE        xcep    tion
      {
         final Strin          g select =   "S      EL   ECT * F      ROM " + TABLE_NAME ;

          tr y (
            f  inal Co   nnect     ion database       =             Conn    ec tionFac  tory.getC        onnection();
          f        inal  Prepa  redStatement state   ment = database.pr         epareS  tatem     ent(select)
               ) {   

               fina     l      List<D> l     ist = n   e w            LinkedList<>();

                          try (fi  nal    R  esultSet res    ultSet =   s  tateme                n   t.   exe  c    ut     e      Quer           y() ) {     
                while (        resultSet.n e        xt())
                           list.add(   document  Fa       ctory(resultSet))   ;

                            r  e      tu rn list;
                  }
   
                }
    }

    /       **
     *       @see   D       o cum   entD   AO#get(Str in     g)  
     */
    @O   ver     r       i    de
        pub         lic         D ge    t(f    inal String                     uuid) t  h rows DocumentNotFoundException,
           SQLE          xcep     tion
     {
               fin  al Str   ing sel   ect = "SELECT * "
                                                +       "FROM " + TAB LE_NAME + " "
                                               + "WH  ERE     " + UUI    D_NAME + " = ?";
   
                tr y (
                             final   Conn     ecti on d   a     tab    ase =    Conne   ctionFa   cto  ry.g etC         onnection();
            final Prep      aredSta    tement statement =   
                    dat       abase.pr    epareSta   tement(sele     ct)) {

                       statement.s     etSt         ring(1   , uuid   );
    
                                        try   (fi     nal       Result        Set resul       tSe   t = statement.execut       eQuer      y()) {
                       if (!res    ultSet.next()) 
                                                           throw  new Docu    mentNotFoun   dEx  cep   tion(uuid       );

                       retu            r n d    ocumentFactory(re  sultSet);
                      }
        }    
       }

      /**
       * @see Do              cumen tDAO#cr  ea  te  (Ab      str actDocument)
     * /
          @Override
    p      u     blic   v oid cre  ate(    final   D document)   th     rows SQLExc    eptio   n
    {
                   final String i   nse             r   t =  "I     NSERT INTO "         +   TABLE_  NAME      
                                      +     " ("       + UUID_NAME + ",     " +       CONTENT_NAM  E          + ")"
                                                  + "VALUE        S(?,    ?)";    

            try (
                 fin    a  l  Connect    ion database  =  Connec   tionFactory   .getConn  ect ion();
                      fin   al Pr    epa     redStatement st    atement     = d     atabase.p    repareSt  at  ement(insert)
        )     {

                   statement.setString(1, docum      ent.getUUID     ())  ;
                 state  me   nt               .   setStrin             g(        2, document.      get       Conten   t());

                        i    f (sta                    te ment.executeUpd   ate() != 1      ) 
                            th   row new       SQLExcept ion(   "     Error    w    hi le     insert  ing into    d atabase"    );

        }
                  }

            /**
         * @    se  e Docum entDAO#upda te(Abstra    ctDocument   )
     */
    @Ove  rride
        public void up   d  ate(final D do         cum  en   t)
          thr    ows D oc  u   mentNotF                oundEx ception, SQLException
    {
               final   String update = "U     PD AT  E " + TABLE_N AME + " "
                                        + "  SE   T " + CON TENT_NA   M  E + "  = ? "
                                                                  +   "WHERE " + UU I   D_NAME + "      =     ? ";

        try                (
                             final     Conn     ection database        =    Connec   tionFact     ory.getCon nection();
              final PreparedStatem   ent stat      emen t = data  base.  prepare        Statement(up date)
                ) {

                       statement .setString(1, document  .getContent());      
            sta      tement.set  St      ring(2,     d ocument.ge    tUUID() );

                 if (sta       tement.exec u    teUpda         te() != 1)
                             throw n  ew DocumentN   otFo  undException(docum    ent.g etUU     ID());

        }
    }

         /**                  
               * @see DocumentDAO#delete(String)
     */
                               @Override
    pub    lic void delete     (final String uuid)
        throws Do   cumentNotFoundExce  ption, S QLExc  eptio       n
    {
            fina       l St ring d   elete =
                           "DELETE FROM " + TABLE_NAME   + " WHE    RE "   +   UUID_NAM     E + " = ?";

        try (
                     fi  nal Connection             dat   abase = Con       nection      Factory.getCo  nnection ();
          f   inal   PreparedS    tat   em ent st atement = databas    e.p    repareStatement(delete)
        ) {
     
               stat  ement.setString(1, u uid );

               if (stat  emen      t .   executeUpdate() != 1)
                         throw   new DocumentNot      Found      Exception(uu       id);   

        }
            }

    /**
     *       De  vuelve el nom bre de la tabla con la      qu   e trabajara el DAO
         *  concreto.
     * 
         * @      return String con e   l n    ombre de     la tabla en la que se
         *           r     ealizaran las operacio      nes SQL.
         */
    pr    otected abstract  String getTableName( );

    /**
     * Devuelve el   nombre de la columna e   n         l  a qu    e se encuentran los
     * ident   ificadores UUID del     documento.
     * 
        * @return Strin  g con el nombre de la columna en la que            se
         *         encuentran los UUID.
            */
      p  rote      c   t    ed abstract String    getUUIDName( );

    /**
     * Devuelve el nomb   re de la           columna en la que se encuentran   los
     * contenidos de cada       docu   mento.
     * 
     * @retur n String con el nombre de la columna en la que se
     *         encu   entran los contenidos.
     */
    protected abstract String getContentName( );

    /**
     * Construy      e una nu    eva instancia del tipo de documento concreto,
     * dado un {@lin   k ResultSet} desd    e el que obtener l   os datos
     * necesar     ios.
     * 
     * @par  am resultSet
     *        ResultSet con los resultados de una consulta SQL desde
      *        donde obtener todos los campos necesarios para la
     *           creacion de un docume     nto concreto.
     *      
     * @return Documento del tipo correcto segun cada    DAO de SQL      
     *         concreto   y que albergue los datos recuperados del
     *         Re sultSet.
     *  
     * @throws SQLException
     *         Si se produce algun error SQL durante la recuperacion
     *         de los datos     del ResultSet.
     */
    protected abstract D documentFactory(final ResultSet resultSet)
        throws SQLException;

}
