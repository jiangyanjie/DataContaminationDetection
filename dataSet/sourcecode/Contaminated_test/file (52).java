package com.matricula.core.dao;

import com.matricula.core.db.AccesoDB;
import com.matricula.core.interfaces.CursoDAOInterface;
import com.matricula.core.to.CursoTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo Fernández
 */
public class CursoDAO implements CursoDAOInterface {

    /**
     *
     * @param obj el objeto Curso que se va a crear.
     * @return un int que representa el número de filas afectadas, devuelve 1 si
     * la inserción fue exitosa, en caso contrario devuelve 0.
     * @throws SQLException
     */
    @Override
    public int crear(CursoTO obj) throws SQLException, InstantiationException,
            IllegalAccessException, ClassNotFoundException {
        String sqlinsercion = "INSERT INTO Curso(vch_cursnombre, "
                + "vch_cursdescripcion, int_curscreditos) VALUES(?, ?, ?)";
        PreparedStatement pst;
        pst = AccesoDB.getConection().prepareStatement(sqlinsercion);
        pst.setString(1, obj.getNombre());
        pst.setString(2, obj.getDescripcion());
        pst.setInt(3, obj.getCreditos());
        int rowsAffected = pst.executeUpdate();
        return rowsAffected;
    }

    /**
     *
     * @param codigo el codigo identificador del registro en la base de datos.
     * @return un objeto Curso.
     * @throws SQLException
     */
    @Override
    public CursoTO recuperar(Integer codigo) throws SQLException, InstantiationException,
            IllegalAccessException, ClassNotFoundException {
        String sql = "SELECT int_cursid, vch_cursnombre, vch_cursdescripcion, "
                + "int_curscreditos FROM Curso WHERE int_cursid = ?";
        PreparedStatement pst;
        pst = AccesoDB.getConection().prepareStatement(sql);
        pst.setInt(1, codigo);
        ResultSet rs = pst.executeQuery();
        CursoTO obj = null;
        while (rs.next()) {
            obj = new CursoTO();
            obj.setCodigo(rs.getInt("int_cursid"));
            obj.setNombre(rs.getString("vch_cursnombre"));
            obj.setDescripcion(rs.getString("vch_cursdescripcion"));
            obj.setCreditos(rs.getInt("int_curscreditos"));
        }
        return obj;
    }

    /**
     *
     * @param obj el objeto Curso con los datos que se desean actualizar.
     * @return un int que representa el número de filas afectadas. Devuelve 0 si
     * no se encontro el registro a actualizar.
     * @throws SQLException
     */
    @Override
    public int actualizar(CursoTO obj) throws SQLException, InstantiationException,
            IllegalAccessException, ClassNotFoundException {
        String sqlActualizacion = "UPDATE Curso SET vch_cursnombre=?, "
                + "vch_cursdescripcion=?, int_curscreditos=? WHERE int_cursid=?";
        PreparedStatement pst;
        pst = AccesoDB.getConection().prepareStatement(sqlActualizacion);
        pst.setString(1, obj.getNombre());
        pst.setString(2, obj.getDescripcion());
        pst.setInt(3, obj.getCreditos());
        pst.setInt(4, obj.getCodigo());
        int rowsAffected = pst.executeUpdate();
        return rowsAffected;
    }

    /**
     *
     * @param codigo el codigo identificador del registro Curso en la base de
     * datos.
     * @return un int que representa el número de filas afectadas. Devuelve 0 si
     * no se encontro el registro a eliminar.
     * @throws SQLException
     */
    @Override
    public int eliminar(Integer codigo) throws SQLException, InstantiationException,
            IllegalAccessException, ClassNotFoundException {
        String sqlEliminacion = "DELETE FROM Curso WHERE int_cursid=?";
        PreparedStatement pst;
        pst = AccesoDB.getConection().prepareStatement(sqlEliminacion);
        pst.setInt(1, codigo);
        int rowsAffected = pst.executeUpdate();
        return rowsAffected;
    }

    /**
     *
     * @return List de los registros de la tabla Cursos de la base de datos.
     * @throws SQLException
     */
    @Override
    public List<CursoTO> listarTodos() throws SQLException, InstantiationException,
            IllegalAccessException, ClassNotFoundException {
        List lista = new ArrayList();
        String sqlConsulta = "SELECT int_cursid, vch_cursnombre, "
                + "vch_cursdescripcion, int_curscreditos FROM Curso ORDER BY 1";
        PreparedStatement pst;
        pst = AccesoDB.getConection().prepareStatement(sqlConsulta);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            CursoTO obj = new CursoTO();
            obj.setCodigo(rs.getInt("int_cursid"));
            obj.setNombre(rs.getString("vch_cursnombre"));
            obj.setDescripcion(rs.getString("vch_cursdescripcion"));
            obj.setCreditos(rs.getInt("int_curscreditos"));
            lista.add(obj);
        }
        return lista;
    }
}