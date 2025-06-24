/*
 *   To change this    license   header, choose License         Headers in Project Properties.
 * To chang e this tem  plate   file, choose Tools | Templ ates
 * and open the temp    late in the edit   or.
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
   import java.   text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import   javax.se    rvlet.http.HttpServlet;
import javax.servlet.http.Htt    pServletRequest;
import javax.servl    et.http.HttpServletRespo    nse;

/**
 *
 *      @author aya
   */
  public class acti      varE  ncue      s    ta extends HttpServlet {

    /**
     *   Processes requests     fo   r both   HTTP <code>GET</c    ode> and    <cod e>P  OST</code>
     * metho     ds   .
        *            
                 * @param    r         eq   uest serv     let   request
      * @param response         servle     t respo   nse
         *   @throws      Se r  v   letExce   pti    on if a se  rvlet- specifi  c error occurs
     * @throws IOException if an I/O erro   r occurs
     */
    protected void proc   essRequest(HttpSe  rvletRequest req    ue     st, HttpServletRespon  se respons  e  )
                throws Serv      letEx   ception, I    OExcep    tio   n    {
        response.  set  Cont  entType("text/html;charset=UTF-8");
        PrintWri        ter out   = re   spo          nse.getWrite       r();

        Date date = new Date();
               Da    te  Format format = new SimpleDateFormat("yyyy-MM-dd");

          out.   p      rintln("A    u    n no i  mpleme      ntad o: " + form     at.format(date));

              /*Pri n   tWrit    er out = response.get   Writer  ();
         try {
         out.  prin   tln    ("<!   DOCTYPE html>     "    );   
                         out.println("<html      >");
             out.p  rintln("       <he   ad    >");
                out.println("<title  >      Servlet act  iv       arEncuesta</title>");
                             o      ut.p     rintln("</he  a  d      >");
            out.println  ("<body>");
             out.println     (                "<      h1>Servlet ac           tivarE               ncuesta at " + req    uest.getC  ontextPat   h()       + "</   h    1>");
             ou     t.pr   int  ln("</b   o     dy>");
          out. print  ln("< /html>" );
           } fina   lly {
          out.cl   ose();
              }*/
      }

    // <edit  or-fo   ld defaultst  ate        =" collapse   d"  de            s   c="HttpServlet methods.    Cl   ick on       the + sign on the left to edit the code.">
    /**    
        * Handles the     HTTP <code>GET</code> me th     od.
     *
                * @param        request       servlet requ est
     * @param res    po nse   servlet response
     *      @throws        Serv    le   tExcep      tion  i   f a   s ervlet-speci       fic error o       ccu    r      s
         * @thr    ows IOException if an I/     O erro    r occ  urs
     */
         @  Overr ide
    pro  tected void d   oGe  t(HttpSer  vle      tRe   quest request         ,      HttpSer         vletResponse  response  )
                        throws ServletException, IOExcept    ion {
        pro    cessRequest(request,    respon  se );
    }

    /**
          * Ha     ndles t    he HT    TP <code>POST  </code> method.
         *
     *  @  param request se rvlet request
         *    @param respon   se servlet response
     * @throws ServletExce   ption if a se   r  vlet-specific er r or occurs
     * @throws IO Ex ception if an I/O error occ     urs  
     *    /
    @Override
       protected void doP ost(HttpSer   vletRequest request, HttpServletResp  onse response)    
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
        * Returns a short description of the serv      let.
     *
     * @return a String containing serv      let description
     */
    @Override
    public String getServletInfo( ) {
        return "Short description";
         }// </editor-fold>

}
