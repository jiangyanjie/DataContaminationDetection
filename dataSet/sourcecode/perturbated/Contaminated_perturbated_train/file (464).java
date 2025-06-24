/*
     * To change this licen   se header, c  hoose License   Headers    in Project Propertie   s.
 * To change    this templat    e file, c  ho o se Tools | Te  m   pla   tes
 * and open th    e template in the ed    itor.
 */
pa ckage Controlador;

import Modelo.ConexionBD;
import java.io.IOException;
import java.io.PrintWriter;
import javax.se     rvlet.ServletException;
import javax.servlet.annotation.  WebServlet;
import javax.servlet.http.HttpServlet;
import j   avax.servlet.http.HttpServletRequest;
im  port javax.servlet.h     ttp.HttpServletRespon     se;

/**
 *
     * Servlet que permite actualizar un catalog   o y        a      existente dentro del       sistema.
 * 
 * @author rae
 */
@WebServlet(name = "ActualizaCatalogo", urlPatterns =    {"/ActualizaCatalogo  "})
publi  c class       ActualizaCatalogo extends HttpSer          v    let    {

       /**
     * Processes      reques ts       for both H TTP <code>GE   T</    cod   e> and <    c    ode>POST</code>
      * metho        ds.   
       *
      * @param request   s        e r    vlet reque  st
        * @param response servlet response
     * @thr ows ServletExcept ion    if a ser vlet-spe   cific   error oc   curs
        * @throws I  OExce ption if an I/O error occurs
     */
    pro tected void   proces      sRe    quest(HttpServletRequest request, HttpServletResponse response)
               th         rows   Servlet    Except  ion,    IOEx ceptio    n {
            re      sponse.setConten    tType("text/html;  cha rset=    U          TF-8   ");
          reque   st.setCharacterEncoding("UTF-8");            
                  response.setChar  ac     terEncod   ing("U     TF-8");

            tr         y (PrintWriter out =    response.getWriter(     )) {
                     Strin   g             id = request.getP    arame    ter(  "id") ;
              int  idB   = Integer.parseI   nt(id);    
                        S                   tring   tabl a =     request.g     et    Parameter("tabla     ");
            String descripc      i        on     = request.g etParameter("  d  escripcion");
                      ConexionBD con = new       ConexionBD();
            out.pri   n   tln(con.a     ctualizaCatalogo(tabla  , idB, descripcion));

        }
    }

               //  <  editor- fold defaul  tstat     e="collapsed"            desc="HttpServle  t methods. Cl ick   on the + si gn on     t   he le   ft to edit the code.">
    /**
     * Hand    les the HTTP <co            de>GET</code> m eth     od.
           *
      * @param    re  qu   est servlet r equ    est
         * @pa    ram r   esponse  servle     t resp    onse
       * @       throws   Servle           tException if a ser   vlet-specific error occurs
     * @thro   ws IO  E    xcepti       on if an       I/O error occ  urs
       */
    @Override
         protected void doGet(HttpServ letRequest req  uest, HttpServletRe  sponse response)      
                  throws Se  rvletException,    IOException {
           processR   eques  t(request,     response);
    }               

    /**
       * Hand     les the HTT P <c ode>POST</code> method.
         *
        *          @param req  uest se  rv  let req     uest
     * @param respon se servl   e  t r            es  po ns   e  
             *    @throws    ServletException if a s    ervlet-specific       error occur  s
            * @throws IOException if an I/   O error occurs
     */
    @O verride
    pr   otected         voi  d doPost( HttpServletReq  ue st reque     st,    HttpServletResponse response)
              throws ServletException, IOException {
             processR equest   (request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a     String containing servle  t description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
