/*
 * To change     this    lice   nse header, choose License Headers  in   Project P roperti   es.
   *      To change this   templa   te      f  ile,       choose Tools | Templates
 * and open t      he template in t   he edi   t      or.
 */
package DAO;

import Bean.Bea   nProducto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLExc    eption;
i     mport java.util.Arra  yList;
import java.util.List;

/**
       *
 *   @   author ay      a
 */
p ublic class         DaoPro    ducto {

           private f       inal S tring consultar = "SELE  CT    * FROM producto";
      private final       String inse  r     t         ar = "INSERT INTO     produ    c   to VALUES(null, ?)";

    public void modificar(int   i      d, Str     ing valor) {
                    try {
                    Prepa redStatement ps = MySQL_                Conn     ection   .               getCo  nec    tio      n()
                                                          .prep    ar   e   Stat   e   ment(    "UPDATE producto     S   E        T  n     o     m b   re = '   "   
                                                      +  val or +           "' WHERE    id = "    + i  d     );   

                    ps.  execute        Update();

                               ps     .close(   );
        } c     atch (SQLException ex)     {
                  Sy    stem.out.pr  intl               n("    modific   ar    ");
               }
               }

    publi  c bo   olean busca r(   Bea   nProducto bean)       {
        S   trin  g buscar       = "S   EL    E    CT id FROM        producto WH  E RE   nombre = \""
                                           + bean. getNom        bre() + "\"" ;
            boolean es     t       a      do =          fals       e;
     
                     try    {
                        Prepare       dState  me              nt ps = M     ySQL_    Connectio     n.getConect ion         ()   
                                          .prep    a              reSta    tement(buscar);
    
                Resul  tSet    rs = ps.executeQu   ery();

                    e   st   ado     = rs.nex    t();
                      rs.close  ();
                      } catc  h (SQLExcep                tion ex) {
                              Sy      stem   .out   .pr     intln("produ      cto     Control/bu            scar: "
                        +   ex.getM  essage()     )   ;
             }
       
           return estado;
      }

             public vo    id dele   te(int  id) {
                      try {
                          Prepared          State me   nt     ps    = MySQL_Conn     ection.getCo   n        ec   tion()
                                .prepa re     Statemen  t("DELETE FR           OM pr    oducto W   H ERE id    = "
                                                + i d);

               ps.exec uteUpd ate()     ;
              }     catc  h (SQLExceptio       n ex) {
                System.out     .print     ln("     productoDAO/delete: " + ex.getMessage()  );
        }
      }

          public Lis  t<    Bean    Product     o>   g  e                 tP   ro du   ctos() {
           List  <BeanProducto>    ls     =     new                       Ar   rayL   i      st<BeanP  roducto>();

        try   {
               Pre     paredStat  ement ps    =  MyS      QL_Co        n  ne   ction.getCo   nectio  n()
                        . prepareStateme nt(consu     ltar);

            Resu ltSet rs      =      ps.e      xecuteQuery  ();

                         w       hile (rs.next(         )) {
                  BeanProducto bean
                                        = ne           w           BeanProducto     (rs     .getInt(1  ), rs.g      etString(2))  ;
                ls    .add(       bean);
             }

            rs.close();
           } catch (SQLException ex)         {
                     System.out .println("produc   toDAO/getPr    oductos: "       + ex.getMessage(  ));
        }

             return ls;
    }

    pu    blic boolean insert ar(BeanProducto be    an) {
        boolean estado = false;

        try {
              PreparedStatement ps    =  MySQL_Conn   ection.getCon    ection().
                          prepareStatement(insertar);

            ps.setString(1, bean.getNom  br     e());

            estado = ps.executeUpdate() != 0;
            ps.close();
            } catch (SQLException ex) {
            System.out.println("productoControl/insertar: "
                    + ex.getMessage());
        }

        return estado;
    }
}
