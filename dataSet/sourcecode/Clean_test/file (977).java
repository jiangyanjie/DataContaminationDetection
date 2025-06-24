/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controladores;
import Datos.DetallePunto;
import Datos.HibernateUtil;
import Datos.Punto;
import Datos.conexion;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author GABRIEL
 */
public class CtrPunto {
    private  conexion cx;

    public CtrPunto() {
        cx=new conexion();
    }
    public boolean InsertarPunto(Punto p){
        if (p!=null) {
            cx.Insertar(p);
            return true;
        }else {
            return false;
        }
    }
    
    public boolean ModificarPunto(Punto p){
        if (p!=null) {
            cx.Modificar(p);
            return true;
        }else {
            return false;
        }
    }
    public boolean EliminarPunto(Punto p){
        if (p!=null) {
            cx.Eliminar(p);
            return true;
        }else {
            return false;
        }
    }
    public List<Punto> ObtenerPunto(String consulta){
        List<Punto> lista=new  ArrayList<Punto>();
        if (!consulta.equals("")) {
            lista=cx.getObjects(consulta);
            
        }else {
           lista=null;
        }
        return lista;
    }
    ////////////DETALLE PUNTO////////////////
      public boolean InsertarDetallePunto(DetallePunto p){
        if (p!=null) {
            cx.Insertar(p);
            return true;
        }else {
            return false;
        }
    }
    
    public boolean ModificarDetallePunto(DetallePunto p){
        if (p!=null) {
            cx.Modificar(p);
            return true;
        }else {
            return false;
        }
    }
    public boolean EliminarDetallePunto(DetallePunto p){
        if (p!=null) {
            cx.Eliminar(p);
            return true;
        }else {
            return false;
        }
    }
    public List<DetallePunto> ObtenerDetallePunto(String consulta){
        List<DetallePunto> lista=new  ArrayList<DetallePunto>();
        if (!consulta.equals("")) {
            lista=cx.getObjects(consulta);
            
        }else {
           lista=null;
        }
        return lista;
    }
}
