/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConexionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Clase que consulta equipos y genera una tabla en sintaxis HTML
 * @author jpachecov
 */
@WebServlet(name = "ConsultaEquipo", urlPatterns = {"/ConsultaEquipo"})
public class ConsultaEquipo extends HttpServlet {
    
    
    private ConexionBD bd = new ConexionBD();
    
    String header = "<!DOCTYPE html>\n" +
"<html lang=\"es\">\n" +
"    <head>\n" +
"        <meta charset=\"UTF-8\">\n" +
"        <title>Consulta - Inventario IIB</title>\n" +
"        \n" +
"        <!--Imagen pestaña-->\n" +
"        <link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"img/escudoUnamNegro.png\">\n" +
"\n" +
"        <!--JavaScript-->\n" +
"        <script type=\"text/javascript\" src=\"js/jquery-1.10.1.min.js\"></script>\n" +
"        <script src=\"js/iniciarSesion.js\"></script>\n" +
"\n" +
"        <!--Estilos plantilla-->\n" +
"        <link rel=\"stylesheet\" href=\"css/6cols.css\">\n" +
"        <link rel=\"stylesheet\" href=\"css/4cols.css\">\n" +
"        <link rel=\"stylesheet\" href=\"css/2cols.css\">\n" +
"        <link rel=\"stylesheet\" href=\"css/col.css\">\n" +
"        <link rel=\"stylesheet\" href=\"css/estilosPlantilla.css\">\n" +
"        <link rel=\"stylesheet\" href=\"css/formulario.css\">\n" +
"\n" +
"        <!--Estilo único página-->\n" +
"        <link rel=\"stylesheet\" href=\"css/index.css\">\n" +
"\n" +
"    </head>\n" +
"    <body>\n" +
"        <!--Inicio encabezado-->\n" +
"        <header>\n" +
"            <div class=\"section group\" id=\"encabezado\">\n" +
"                <div class=\"col span_1_of_6\">\n" +
"                    <img src=\"img/escudoUnam.png\" height=\"40%\" width=\"40%\" alt=\"escudo unam\" id=\"imagenUnam\">\n" +
"                </div>\n" +
"                <div class=\"col span_4_of_6\" id=\"nombreInstituto\">\n" +
"                    <span>Instituto de<br>Investigaciones<br>Bibliográficas</span>\n" +
"                </div>\n" +
"                <div class=\"col span_1_of_6\">\n" +
"                    <img src=\"img/logoBiblioteca.png\" height=\"80%\" width=\"80%\" alt=\"escudo biblioteca\" id=\"imagenBiblioteca\">\n" +
"                </div>\n" +
"            </div>\n" +
"            <div class=\"section group\" id=\"barra\">\n" +
"                <div id=\"login\">\n" +
"                   <br>\n" +
"                </div>\n" +
"            </div>\n" +
"        </header>\n"+
         "<div class=\"smart-blue\">\n";
    
    
    String footer = "</div>\n     <footer>\n" +
"            <div class=\"section group\" id=\"pie\">\n" +
"                <p>Hecho en México, todos los derechos reservados 2014. Esta página puede ser reproducida con fines no lucrativos, siempre y cuando no se mutile, se cite la fuente completa y su dirección electrónica. De otra forma requiere permiso previo por escrito de la Institución.</p>\n" +
"            </div>\n" +
"        </footer>\n" +
"        <!--Fin pie-->\n" +
"    </body>\n" +
"</html>\n" +
"";
    
    
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
        try (PrintWriter out = response.getWriter()) {
            out.print(header + hazConsulta(request) + footer);
        }
    }
    /**
     * Metodo que hace una consulta de equipos para la base de datos y 
     * regresa una tabla en sintaxis HTML que contien los equipos devueltos 
     * por la consulta.
     * @param request Objeto que contiene los datos necesarios para consultar
     * en la base de datos.
     * @return Una tabla en sintaxis HTML.
     */
    public String hazConsulta(HttpServletRequest request) {
        String marca = request.getParameter("marca");
        String numero = request.getParameter("numeroSerie");
        String familia = request.getParameter("familia");
        String ubicacion = request.getParameter("ubicacion");
        String responsable = request.getParameter("responsable");
        String tipoEquipo = request.getParameter("tipoEquipo");
        String departamento = request.getParameter("departamento");
        String fechai = request.getParameter("fechaI");
        String fechaf = request.getParameter("fechaF");
        String estado = request.getParameter("estado");

        return generaTabla(bd.reportes(marca, numero, familia, tipoEquipo, fechai, fechaf, departamento, ubicacion, responsable, estado));
        
    }
    /**
     * Genera una tabla en HTML
     * @param equipos Un arreglo de objetos equipo para llenar la tabla.
     * @return  La tabla en HTML lista para incrustarse en la pagina.
     */
    public String generaTabla(ArrayList<Equipo> equipos) {

        String tabla = "<table id='tablaResultado'>\n";
        tabla += "<tr>\n";
        tabla += "<th>Num. Inv. interno</th> <th>Num. Inv. UNAM</th> <th>Marca</th> <th>Modelo</th>"
                + "<th>Serie</th> <th>Familia</th> <th>Tipo</th> <th>Fecha de registro</th>"
                + "<th>Departamento</th> <th>Ubicación</th> <th>Responsable</th>\n";

        if (equipos.size() == 0) {
            return "<label id=\"errorBusqueda\" class=\"errorFormulario\">No se encontraron equipos</label>";
        }
        for (Equipo e : equipos) {
            tabla += "<tr>\n";

            tabla += "<td>" + e.getClave_activo_fijo() + "</td>";
            tabla += "<td>" + e.getNum_inv_unam() + "</td>";
            tabla += "<td>" + e.getClave_marcar() + "</td>";
            tabla += "<td>" + e.getClave_modelo() + "</td>";
            tabla += "<td>" + e.getSerie() + "</td>";
            tabla += "<td>" + e.getClave_familia() + "</td>";
            tabla += "<td>" + e.getClave_tipo() + "</td>";
            tabla += "<td>" + e.getFecha_de_resguardo() + "</td>";
            tabla += "<td>" + e.getClave_institucion() + "</td>";
            tabla += "<td>" + e.getClave_area() + "</td>";
            tabla += "<td>" + e.getResponsable() + "</td>";

            tabla += "</tr>\n";
        }

        tabla += "</tr>\n";
        tabla += "</table>\n";

        return tabla;
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
