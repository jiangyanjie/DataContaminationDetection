/*
    * To change this license    head     er, cho  ose Li  cense      Headers in Project Properties.
 * To    change this templ   ate file,   choos e Tools | Templates
    *    and open   the temp   l at  e in the editor.    
 */
package Controlador;       

import Modelo.ConexionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UR  LEncoder;
import javax.servlet.ServletExceptio   n;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.ser    vlet.http.HttpServletRequest;
import jav  ax.servlet.http.HttpServletResponse;
import javax.servlet.http.  Http Se      ssion;

/      **
   *    Cla  se             que actua  liza un equ ipo en la base de datos y ma nda men saj   es de exito 
 * o fracaso.
 * @author jpachecov   
 */
@ WebServlet(name = "Actual      izaEquipo", urlPatterns   = {"/Ac  tualizaE   qu  ipo"})
pu      blic class Ac  tualizaEqu  ipo extends   Ht tpS er          vle            t {

         ConexionBD bd = new ConexionBD();

    /**   
     * Processe s re     quests fo r both HTTP <code>GE    T</code> and     <  code>POS     T</code>
                    * me  thods.
     *      
        * @p   aram reques     t servlet        req u    est
     * @param    respons   e servl  et resp                                onse
     * @throws ServletException if a       s  e     rvlet-specific er    ror   occurs    
     * @th    rows IO  Ex   ception if an I/O error occurs
     */
    protected   void processRequest(HttpS          ervletRequest reques t, HttpServlet  Response response)
            throws S     erv      letException, I   OExce ption {
              response.setCont      entType("  text /ht ml;charse  t=UTF-8");
          response.se tCharac   te    r  Enc     od  ing("UTF-8")     ;
        request.setCharacterEncoding("UTF-8");
              St   r      ing    msj_  exito = "La             actu  alizaciÃ³n del equipo ha sido exitosa";
        String    msj_error    = "Er    r o              r         al      a  c         tualizar el equipo";
        
        HttpS     ession se       si  on =      re     quest.   get   Sess     ion(true);   
               String tipo_sesio  n  =    (St  ring)ses       ion.    getAt      tribute("identi  da  d");
                           
            if (act        u     alizaE   quipo(re       quest, response)) {
                     
                          response.sendRed  ire    ct   (tipo_sesion+".jsp?me      nsaje=" + URLE  ncoder.encode(msj  _ex        ito     ,"UTF-8") + "&exito=true");
                      
                        } else {
                        
                  r   esponse.sendRed     ir  ect (tipo_sesion+".jsp?mensaj  e=" + URLEn coder.encode(msj_err   or,"UT            F-8") +       "&exito=      false");
               
            }

        
    }
/**             
   *   
 *       @param                request Metod o que contiene los  datos para la so   lcitud    de act     u    ali    zac        io n
   * de equipo.
 * @para m respon   se  
   * @return
 * @throws ServletExcep       tion
 * @t  hr    ows   I  OExceptio   n 
 */
    private boolean actualizaEquipo(Ht    tpServletRequest reques   t, HttpServ    le tResponse response)
              throws       Servle  tException, IOException {
        request.setCharacte     rEncoding  ("UTF-8  ");
         St     ring activo  Fij = request.getParameter("activoFijo");
           Str   i  ng descripcion = requ  est.ge    tP      aramete  r("descripcion");
         String descripcionExt =    reques  t.getParameter("    descripci   onExtendida")   ;
             Str   ing numeroSer =     request.getParam   eter("numeroSerie");
        Strin         g clase =        request       . getParameter("clase");
        Str     ing uso = request.getParameter( "uso");
        String   marca = request.getParame  ter(  "mar         c     a");
        String estado   = r         eque st.getParameter("estad    oFisico");
        String ubicacion =     req   ue             st.getPa     rameter("ubica        cion");
        String fechaRe  s = reque    st.ge     tPar     ameter("fechaRe   sguardo");
        Str    ing    modelo = req   u  est.getParameter        ("modelo");
             String familia = request.getPar    ameter("famil  ia");
                String tipoActi     vo = re quest    .getParameter  ("   t ipoActiv        oFijo  ");
          Stri     ng nivelObs = req       ue    s      t.getParamet   er("nive   lObsolencia");
                S tri   n    g centroCo   s = reques   t.g etParamet   er("centroCos      to");
         String prov   eedor = request.g  etPar  ameter("proveedor     ");
        Stri  ng res  po    n sable = req uest.getParameter("res      ponsab  le"); 

                  return bd.actualiz  aEquipo(BuscaEqui   po.id_equipo  , I    nteger.pa   rs   e       Int  (acti   voFij),
                      Intege r.parseInt(descripcio    n)  , descripcionExt, modelo, marca, nu     m    eroSer, familia, tip        o  Activo,
                        pro   veedor, clase,     uso, niv     elObs, esta     do,  ub          icacion      , cen     troCos,      fe    chaRes,
                       responsable);
  
    }     
    
        
      / / <ed   itor-fold de  faultstate="   coll              apsed"  desc              ="Ht  tpServle       t m  eth   ods. Click o   n the + sign on   th     e left to ed  it th  e code.">
         /**
     * Handles t      he HTTP <code>GET</code > method.
      *
     * @param       request servlet request
     *    @param resp   onse ser        vlet response 
               * @throw  s       ServletExc   eption if a ser    vlet-s    p   ecific error occurs
          * @thr  ows IOException if an I/O e   rror occurs
     */  
              @Override
    prot  ected voi       d doGet(HttpServletRequest reques           t, HttpServletRespon   se response)
              throws ServletEx    ception, I OException {
        processRe  quest(request, response);
    }

    /**
         * Handles  the HTTP <code>POST</code> metho d.
     *      
     * @param        request servlet request
     * @param response servlet     resp  onse
      * @throws ServletException        if a servlet-specific error occurs
     * @throw  s IOException if        an I  /O error occ   urs
     */
    @Override
    protected void   doPo   st(HttpServletRequest req      uest, HttpServletResp   onse response)
               throws Serv    letException, IOException {
        processRequ est(requ   est, response);
    }

          /**
     * Returns a short description of the  servlet.
     *
     * @return a String containi ng servlet description
     */
    @Override
    public String getS  ervlet Info() {
        return "Short description";
    }// </editor-fold>

}
