/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.BeanProducto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aya
 */
public class DaoProducto {

    private final String consultar = "SELECT * FROM producto";
    private final String insertar = "INSERT INTO producto VALUES(null, ?)";

    public void modificar(int id, String valor) {
        try {
            PreparedStatement ps = MySQL_Connection.getConection()
                    .prepareStatement("UPDATE producto SET nombre = '"
                            + valor + "' WHERE id = " + id);

            ps.executeUpdate();

            ps.close();
        } catch (SQLException ex) {
            System.out.println("modificar");
        }
    }

    public boolean buscar(BeanProducto bean) {
        String buscar = "SELECT id FROM producto WHERE nombre = \""
                + bean.getNombre() + "\"";
        boolean estado = false;

        try {
            PreparedStatement ps = MySQL_Connection.getConection()
                    .prepareStatement(buscar);

            ResultSet rs = ps.executeQuery();

            estado = rs.next();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("productoControl/buscar: "
                    + ex.getMessage());
        }

        return estado;
    }

    public void delete(int id) {
        try {
            PreparedStatement ps = MySQL_Connection.getConection()
                    .prepareStatement("DELETE FROM producto WHERE id = "
                            + id);

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("productoDAO/delete: " + ex.getMessage());
        }
    }

    public List<BeanProducto> getProductos() {
        List<BeanProducto> ls = new ArrayList<BeanProducto>();

        try {
            PreparedStatement ps = MySQL_Connection.getConection()
                    .prepareStatement(consultar);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BeanProducto bean
                        = new BeanProducto(rs.getInt(1), rs.getString(2));
                ls.add(bean);
            }

            rs.close();
        } catch (SQLException ex) {
            System.out.println("productoDAO/getProductos: " + ex.getMessage());
        }

        return ls;
    }

    public boolean insertar(BeanProducto bean) {
        boolean estado = false;

        try {
            PreparedStatement ps = MySQL_Connection.getConection().
                    prepareStatement(insertar);

            ps.setString(1, bean.getNombre());

            estado = ps.executeUpdate() != 0;
            ps.close();
        } catch (SQLException ex) {
            System.out.println("productoControl/insertar: "
                    + ex.getMessage());
        }

        return estado;
    }
}
