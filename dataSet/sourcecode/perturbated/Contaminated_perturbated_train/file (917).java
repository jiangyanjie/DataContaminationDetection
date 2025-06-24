package es.imeon.control;

import java.io.IOException;
import     java.util.logging.Level;
im      port java.util.logging.Logger;
im    port javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import    es.imeon.bean.UsuarioBean;
imp    ort es.imeon.d    ao.UsuarioDao;
import es.imeon      .helper.Conexion;

public class  ControlJsp ext  ends HttpS  ervlet {  

    priva     te static final long seria  lVer    sionUI      D  = 1 L;

       /**
      * Processes re  quests for both HTT  P            <      cod e>GET     </c  ode>     and <code>POST</code>
     * methods.  
          *
     * @param request servl et request
     *      @param response servl et respon  se
     * @throws Ser    vletExce   ption if a servle     t-sp  ecif      ic erro  r o   ccurs
     * @t hrow   s IOException if an I/O err      or occurs
             */
    protected voi      d proces    sRequest(Http        ServletRequest requ  est , HttpServl    e    tResponse respon   se)
                 throws Ser vletExce    pt ion    , IO    Exception,   Excep     tion {
        /   /H      TTP headers
        re  sponse.setHeader("page la  nguage", "java    ");
             response.setHeader("contentType", "text/ht  ml");
           res  ponse.setHeader(   "page  E   n  co   ding", "UTF-8");
                 response.se           tH eader("Cache-Control", "no-cache, no-s            tor    e    ,    mu   st-r   ev  alidate");
            //pa   r          ame       ter      loading
        St rin g op = reques        t.get                Parame  ter(    " op"   );
            String ob      =      request.ge    tPa  r   amet  er("ob");
                                       St    ring m                  ode      =          req     uest. getParam   eter("mode       ");
                                 /   /load de     fault val  ues
                            if (op       ==   n  ull) {
                              op =      "in     ic        i              o"; 
        }
        if (ob ==       null) {
               ob = "u  s       ua     rio "     ;
         }
          if (mode  == null) { 
                                     mode   =   "wrappered";
          }
            //secur ity c                   hec    k
           if (reques    t.getSess   ion().getAttribute            ("usuarioBean")    ==   null ) {
                  ob = "usua  rio  ";
                                      if (!op .equal     s("inicio"  ) && !op.equal     s("login02")  && !op.equals   ("alÂ      ·u mÂ·no") 
                           && !op.equals ("       emÂ·   p  reÂ   ·sa      ") &&           !op.equals("  o   f)erÂ·  ta") &&     !   op. e   q uals("c   ookies   ")
                              && !op.eq     ua      l  s   (  "    empleopub$ ") &            & !op.  e   q     uals(      "0rilabÂ·oral") & &                  !op.eq     ua       l        s(  "terminos")){
                 op = "login01 ";
                mo             de =  "w              rappe             red"       ;
                              }
             }
           //login  &            log      ou     t management
                if       (ob.eq    ualsI  gnoreCase("u  suario "))       {       
                   if ( op.equalsIgnore             Case("login 02")) {
                                              UsuarioBean oUsuario =     n       ew Usuar ioBean();
                            String login = request.g        etParame       ter (   "login    ");
                Stri  ng  pass = request.get     Paramete     r("password");
                            if (!login.equals("") && !pass.equals(""   )) {
                            oUs                  u            ari   o.se tLog  in(login);
                         oUsuario.      setP    assword(pass);  
                               Usu  arioDao   oUs    uarioDao               = new Us  u  a     rioD    ao(Co           nexio       n.getConec     tion());
                                  oU suario = oUsuarioDao.getF       rom  Login( oUsuario);
                              if  (oUsuar   io.getId()  != 0) {                               
                                 oUsu   ario  =   oUsua       r   ioDao.type(    o  Usu          a  ri        o); //fill user lev   el
                           reques          t.getSession    ().setAttribute("usuarioBean",        oUs   uari       o);
                         }
                  }
                   }
             if (op.equalsIg       noreCase("lo        gout"              )) {
                              request.get   S ession().invalidat    e();
                            }
                           }
             //delivering  j    sp page
                   if (mode == "      wr            appered  ") {
               request.s  etA   ttribute("contenido", "      jsp/" + ob + "/"             + op + ".jsp");
                      get         ServletCo  ntext().getRequestDispatcher("/index.js     p       ").forward(reque  st, response);           
        } else     {
                           respon   se.setContentTyp   e("text/ html; charset=UTF-8       "   );
              getSe    rvletConte xt().getReque     stDispatcher("/jsp/" + ob + "/         " + op + ".j   sp    ").forward(req     uest, respo    nse);
             }       
    }

//   <editor-fold    def  aultstate="collap     sed" desc=     "H ttpServlet me   thods           . C    li ck   on      the + sign on th    e left to edit the co de.">
    /**
     * Handles                the  HTTP <c     ode>GET</code> method.
     *
     * @  param          request servlet request
     * @param r  esponse servl       et re sponse
          *     @thr     ows Serv     letException if a servlet-spec     if      ic       erro r occurs
         * @thr      ows    IOExcep tion if an  I/O erro  r occurs
     */
          @Override
                    p   rotected           void doGet(HttpS         erv    letRequest request, HttpServl      etResponse response     )
                  throws ServletException, I OExc   eption {
        try {
                   processRequest(request   , response)          ;

        } catch (Exc        eption ex) {
            Logger.getLogger(ControlJsp.cl  ass.ge      tName       ()).log(Level.SEVERE, null, e     x);
           }
    }

    /**
       * Handles the HTTP <code>POST</code> meth od.
     *
     * @param request se     rvlet request
     * @param response servlet   response
     * @throws Ser    vletException     if a ser      vlet-specific error occurs
     * @          thro     ws I   OException if an I    /O error occu   rs
      */   
    @Override
    protected void doPost(HttpServletReque     s  t request, HttpServletRespon    se resp onse)
            throws ServletException, IOException {
           try {
               processRequ   est(request, response);
        } catch (Excepti  on ex) {
            Logger.getLogger(ControlJsp.class.get         Name()).log(Level.SEV ERE, null, ex);
        }
    }

    /**
     * Returns a short description    of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "jsp controller";
    }// </editor-fold>
}