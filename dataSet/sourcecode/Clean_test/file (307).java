/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConexionBD;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * Servlet que permite agregar catalogos dentro del sistema.
 * 
 * @author rae
 */
@WebServlet(name = "AgregaCatalogo", urlPatterns = {"/AgregaCatalogo"})
public class AgregaCatalogo extends HttpServlet {

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
        String msj_exito = "Catálogo agregado exitosamente";
        String msj_error = "Error al agregar al catálogo";
        
        HttpSession sesion = request.getSession(true);
        String tipo_sesion = (String)sesion.getAttribute("identidad");
        
        if (agregaCatalogo(request, response)) {
            response.sendRedirect(tipo_sesion + ".jsp?mensaje=" + URLEncoder.encode(msj_exito, "UTF-8") + "&exito=true");
        } else {
            response.sendRedirect(tipo_sesion + ".jsp?mensaje=" + URLEncoder.encode(msj_error, "UTF-8") + "&exito=false");
        }
        
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

    /**
     * 
     * Metodo que obtiene los datos del formulario de agregar catalogo y lo agrega al sistema
     * 
     * @param request servlet request
     * @param response servlet response
     * @return 
     *        true - si el catalogo fue dado de alta exitosamente.
     *        false - si el catalogo no fue dado de alta exitosamente. 
     * 
     * @throws IOException if an I/O error occurs
     */
    
    
    private boolean agregaCatalogo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ConexionBD con = new ConexionBD();
        String descripcion = request.getParameter("descripcion");
        String catalogo = request.getParameter("elegirCatalogo");
        String tipo_familia = request.getParameter("select_catalogo_tipo_equipo");
        
      
        
        if (catalogo.equals("") || descripcion.equals("")) {
           return false;
        } else {
            
            if(catalogo.equals("catalogo_familia")){
                
                if((!tipo_familia.equals("")) && !con.insertaCatalogoFamilia(descripcion, Integer.parseInt(tipo_familia))){
                    return true;
                }else{
                    return false;    
                }     
                
            }else{
                
                if(!con.insertaCatalogo(catalogo, descripcion)){    
                    return true;
                }else{
                    return false;
                }
                
                
            } 
            
        }

    }

}
