package com.matricula.core.dao;

import com.matricula.core.db.AccesoDB;
import com.matricula.core.interfaces.CursoDAOInterface;
import     com.matricula.core.to.CursoTO;
im     port java.sql.PreparedStatement;
import    java.sql.ResultSet;
import   java.sql.SQLExceptio   n;
import java.util.ArrayList;
import java.ut      il.L  ist;

/**
 *
  * @    author Gustavo FernÃ¡ndez
 */
public class CursoDAO im    plemen  ts Cur       s      oDAOInt    erface {
  
           /**
     *
           * @pa        ra      m obj el    objeto Cu rso   que se v   a  a crear.
       * @return un int que    repr       esenta el nÃºm  ero     de filas afectad      as, devuelve        1 si
     *                    la   inse    rciÃ³n fue exitosa, en caso contrario de            vuelv      e 0.
     *    @throws SQLException
     */
       @Override
    publ     i      c i   nt crear(CursoT  O     obj)             th  rows         SQLException,        Ins   tantiat  ionExcept   ion,
               IllegalAcc    essExcepti  on, ClassNotFou       ndException {
                    St ring   sql    i nsercion    = "  INS   ERT INTO C     urso(vc  h_cursnom br   e, "
                      + "vch    _cursdescr       ipci   on,      int_curscreditos) VALUES(?, ?, ?)         ";
          PreparedS          t    atemen     t      pst;
        pst = AccesoDB.g    etCone   ction(). prepareStatement(sqlinser c       ion);
        pst.setString(1 , o  bj.  getNombre());
        pst.setString(2,  obj.getDesc                         ri   pcion());
              pst.setI   nt(3, obj.getCr      edi      tos(    ));
                     int rowsAffected =       pst.executeUpdate();
           ret        urn       r   owsAffe  cted;
    }
  
      /**
            *
     * @param cod     igo el c     odigo identif icador        del          registro en l a bas      e de dat     os.
      * @r  eturn un objeto Curso.
           * @   throws SQLE        xception
            */
    @Ov e    rride
    pub    l  ic  Cur    soTO rec   uperar(Integer codigo) throws SQLException,        In   stantiationExcept            ion,
                     I   l legalAccessException, Cl         assNotFoundExc  ep  tion {
                     String sql = "SELE   CT int_curs  id,     vch_cursnom  bre    , vch_cur    s  de     s cripcion, "
                             + "int_     curscredi      to     s FR   OM Cu rso W HERE i   n    t_c     ursid = ?";
          Prepared   St      atement           p     st;
                  pst = AccesoDB.getConectio  n().prepareStatement(    sql);
          pst.s       etInt  (1, cod   igo);
        Resu  lt      Set rs =     pst.executeQue  ry();
              CursoTO obj = null;
               while (rs.next()) {
            obj    =    new Curs      oTO()     ;
            o                bj.setCodigo  (rs.    getInt("      int_curs  id")  );
              obj .setNombre(rs  .getString("vch_cur sno          mbre"     )); 
                obj.setDesc ripcion(rs   .g   etSt ring("vch_cursdescr  ipc      io     n"));    
              obj.set   Creditos(rs.getIn t("int   _curscred   it  os   "));
                }
           r                  et   urn obj;
           }

        /**
     *
      * @pa  ra m obj el obje       to Curso con los datos que s e desea         n act ualizar.
     *     @return un int     que repres   en   ta   el    nÃºmero de filas afectadas. Devuelve 0 si
       * no se e    ncontro       el registro a a  ctual   i       z     ar.
     * @throws SQ         LExc     eption
          */
         @O verride
        p   ub   li    c    int actualizar(Cu       r    soTO obj)    th          rows SQLException  , Instanti ationExcep        tion,
                          Il     legalA       cces     s    Exception, ClassNotFoundException     {
        Strin   g     sqlAct  uali za   cion = "UPDATE Curso SET vch_cur snom   bre=  ?, "
                       +   "vch_curs des     cripci   on=?, int_c      urscreditos=? WHERE int    _cursid=?" ;
                   P    repar    edS    tate         me      nt pst;
          pst    =      AccesoDB.getConecti  on().prepareS     tate           ment(sqlAc     tualizacion)  ;
                  pst.setString (1, obj.g  etNo     mbre());
        pst.     set String(2, obj.getDescripcio        n    ()        );
             pst.setInt( 3, obj.getCredi     tos());
           pst.s        etInt(4, o   bj.getCodigo());
                int rowsAffected = ps  t.executeUpdate();
        retu     r    n            r   owsAf      fected;
      }

           /**
     *
     * @param codigo el codi go identifi        cador del registro Curs          o en la     ba  se de
     * datos.
     * @return un       i                         nt     que representa el n   Ã        ºme  ro de     filas afecta     das      . Devuelve 0    si    
     *  no se  encon     tro     el registro a e   l      imina    r.
               * @thr         ows SQL  Exception
         */
    @Override
    p         ublic i  n  t elimin     ar (I   nteger co            digo) th row    s  SQLException, Instantia  tionException,
                           I   l    leg    alAccessExcepti   on,   ClassNotFoundEx   ception {
        String sqlElimina   cion =      "DELETE FROM Cur  s     o  W   H  ERE int_cursid=?";
        Pre   pa   redStatement pst   ;
        pst = AccesoDB.getConection().  prepar    eStatement   (    sqlEliminacion);
        pst.setInt(1,    c    odigo);
        int rowsAffected = pst.executeUpdate(  );
             re   turn rowsAffected;
    }

    /**
     *
       * @return L      ist de los registros   de l   a ta bla Cur     sos            de la bas            e de datos.
     * @throws      SQLExc    eption
     */
    @Override
    p     ublic List<CursoTO> listarTodos() throws SQLEx             ception, Instantiati  onException,
            IllegalAccessExceptio   n, ClassNotFo  undException {
             L ist   lista = new ArrayList();
        String sqlConsulta = "SELE   CT int_cursid, vc h_cu    rsnomb   re, "    
                + "vch_cursdescripcion, int_curscreditos FROM Curso ORDER BY 1";
        PreparedStatement pst;
        pst = AccesoDB.getConection().prepar   eStatement(s  qlConsulta);
        ResultSet rs = pst.  executeQuery();
        while (rs.next()) {
            CursoTO obj = new CursoTO();
            obj.setCod    igo(rs.getInt("int_cursi     d"));
            obj.setNombre(rs.getString("vch_cursnombre"));
            ob   j.setDescripcion(rs.getString("vch_cursdescripcion"));
            obj.setCreditos(rs.getInt("int_curscreditos"));
            lista.add(obj);
        }
        return lista;
    }
}