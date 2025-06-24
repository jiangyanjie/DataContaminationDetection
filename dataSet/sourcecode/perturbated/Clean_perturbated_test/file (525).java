package      es.imeon.control;

i  mport com.google.gson.Gson;
i  mport java.io.IOException;     
import java.util.HashMap;
import java.util.Map;   
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import  javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse  ;
import es.imeon.operaciones.GenericOper   ation;

publ    ic    class Contr      olJson ex    tends HttpS  ervlet {

    pro tected void processRequest(Htt   pServletRequest requ   est, Htt      p      ServletResponse       response   )  
            throws ServletException   , IOException, E      xceptio                   n {    
                    //r etardo d ebug
//           try {  
//              Threa         d.sleep(80)     ;
//            } catch (Int     errup    tedException ex)     {
//            Thread.      c    ur          re    ntTh re        ad().interrupt();
//                }
            /   /c         ontrol    de au         tenticaciÃ³n     
             i  f (request.getSession().getAttribute("usuarioBean"                 ) == n    ull)  {
                Gson     gson  =   new     Gson();
                             Map<String, String> data =    new Has      h    Map   <>();
            data.put("status", "401");   
            data.put("m      ess   age  ", "e    rr or de auten        t  icaciÃ³n");
                     Stri   ng     resultado = gson.toJson(data);
              reque    s      t.setAtt        ribute("contenido", resultado);
                      getServletContext               ().getR       e    questDispatc          her(     "/jsp/mes   sageAjax.js    p").forw        ar    d(r      equest, respo       nse);
        } else {
                         S   tring op = r   equ    est. getParame ter("op  ");
              St     ring o    b = request       .getParameter("o b               ");
            S   tring     call  o    p = Character.t oU   pperCase    (o     b.charAt(0)) + ob.sub   s     tring(1) +   Character.toU   pp         erCase(op    .charAt(0))  +   op.subst ring(1);
                       try {
                                  tr  y {
                      Gener icOperation   operation = (GenericO    pe  ration) Class.forNa me(      "es.imeon.opera     ciones."              +    ca    llop).    newIns t  anc   e(       );
                         String data = operation.execute(request, response);
                                  request.setAttrib      ute("c  onteni     d   o", da  ta);
                              getSer  vletContex          t(  ).getR   eq   uestDis  patcher("/jsp  /  messa        geAjax.jsp").   forwar d(request, respo   n     se);
                             } catch (Cl  assNot    Fo    undE   xception ex) {
                                         Logger.get  Lo    gger(C      on  tro    lJson.class.getName()).l              o                  g(L   evel.SEVE   RE  , null,        ex);
                }
                                 } catch (Instantia  t   ionExce    p tion | IllegalAcces   s  Exception ex) {
                                   Logger.  getLogg   er(Contro      lJson.class.getName())  .log(Level.S   EVERE, null, ex);
                    }

        }  
           }

/           / <editor-fold default  state="collapsed  "     de   sc="HttpServle        t methods. Clic    k on the +   sign on the   lef    t to edit  the code.">
    /        **
             * Handles the HTT P <co    de>   GET</c  ode> method.
       *
     * @pa    ram request servle  t request
     * @p  aram res      ponse servlet response
     * @throws S   ervle tExcepti      on if       a s     er     vle   t-specific error o    ccurs
     * @throws   IOException if            a  n I/O error occurs
        */   
    @Overrid   e
                protected void doGet(HttpServletRe  qu   est    req     uest ,  HttpSer   vle   tRe        sponse res   pon    se    )
                        throws Servl    etException,  IOExc epti on {
           try {
                 proces       sReques     t(request   ,      re     sp    onse);

        } c        atch (Exception      ex)    {
                    Logger     .g  e  tLog  ger(C           ontrolJ   son .class
                                           .getName()).log(   Level.SEVERE, null, ex) ;
         }

    }

    /** 
     * Ha    ndle    s the H   TT   P <code> POST</code> method.
     *
         * @param request servlet request
     * @pa ram response servlet  response
     * @throws Servlet  E      xcepti          on if a servlet-specific      error occurs
     * @throws IOException if an I/O error occur    s
     */
         @Override
    p       rotected void doPost(HttpServletRequest request, HttpServletResponse response)
            throw     s ServletException, IOExc   eption {
        try {
                 pro   c         essRequest(re  que    st, r      esp   o       nse);

        } catch (Exception     ex) {
              Lo gger.getLogger(ControlJson.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the se           rvlet.
     *
     * @re turn a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
