/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConexionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Clase que actualiza un equipo en la base de datos y manda mensajes de exito 
 * o fracaso.
 * @author jpachecov
 */
@WebServlet(name = "ActualizaEquipo", urlPatterns = {"/ActualizaEquipo"})
public class ActualizaEquipo extends HttpServlet {

    ConexionBD bd = new ConexionBD();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String msj_exito = "La actualización del equipo ha sido exitosa";
        String msj_error = "Error al actualizar el equipo";
        
        HttpSession sesion = request.getSession(true);
        String tipo_sesion = (String)sesion.getAttribute("identidad");
        
            if (actualizaEquipo(request, response)) {
                
               response.sendRedirect(tipo_sesion+".jsp?mensaje=" + URLEncoder.encode(msj_exito,"UTF-8") + "&exito=true");
            
            } else {
                
               response.sendRedirect(tipo_sesion+".jsp?mensaje=" + URLEncoder.encode(msj_error,"UTF-8") + "&exito=false");
            
            }

        
    }
/**
 * 
 * @param request Metodo que contiene los datos para la solcitud de actualizacion
 * de equipo.
 * @param response
 * @return
 * @throws ServletException
 * @throws IOException 
 */
    private boolean actualizaEquipo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String activoFij = request.getParameter("activoFijo");
        String descripcion = request.getParameter("descripcion");
        String descripcionExt = request.getParameter("descripcionExtendida");
        String numeroSer = request.getParameter("numeroSerie");
        String clase = request.getParameter("clase");
        String uso = request.getParameter("uso");
        String marca = request.getParameter("marca");
        String estado = request.getParameter("estadoFisico");
        String ubicacion = request.getParameter("ubicacion");
        String fechaRes = request.getParameter("fechaResguardo");
        String modelo = request.getParameter("modelo");
        String familia = request.getParameter("familia");
        String tipoActivo = request.getParameter("tipoActivoFijo");
        String nivelObs = request.getParameter("nivelObsolencia");
        String centroCos = request.getParameter("centroCosto");
        String proveedor = request.getParameter("proveedor");
        String responsable = request.getParameter("responsable");

        return bd.actualizaEquipo(BuscaEquipo.id_equipo, Integer.parseInt(activoFij),
                Integer.parseInt(descripcion), descripcionExt, modelo, marca, numeroSer, familia, tipoActivo,
                proveedor, clase, uso, nivelObs, estado, ubicacion, centroCos, fechaRes,
                responsable);

    }

    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
