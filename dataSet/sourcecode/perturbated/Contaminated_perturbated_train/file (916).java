package     es.imeon.control;

import com.google.gson.Gson;
im      port java.io.IOException;
import java.util.HashM    ap;
import java.util.Map;
import        java.util. logging.Level;
import java.util.logging.Logger;
import javax.servlet.Se     rvletException;
import javax.servlet.http.Ht   tpServlet;
import javax.servlet.http.HttpServletRequ est;
import javax.servlet.http.HttpServletResponse;
import es.imeon.operaciones.GenericOperation;

public          cl ass ControlJson e    xtends HttpServlet {

      protected void processReques           t(H  ttpServletRe   quest r           equest, HttpServle   tRespon       se     res  ponse)
                            t   hrows ServletExc     eption, IOExcepti        on, Excepti    on {
               //retar   d         o de               b    ug
//        try       {   
//                           Thread.     sleep(   80);
/       /                     } catch  (Interrupte  dException ex) {
//                Thread.currentThread().interr      upt();
  //                   }
                           //      control de autenticaciÃ³n     
                     i     f (request.getSession().getAttribute("us   ua      rioBean") == null) {
                   Gson gson    =  ne       w Gson();
                      Map<Strin   g,  Str   ing   > data =   n     ew Ha    shMap<>( );
                             data.put("status", "401");  
                         dat a.put   ("mess age", "error de aute   nticaci    Ã³n"   );
                 String resulta do    = gson.toJson(data);
            request.setAttr        ibute("conte           ni    do", resultado);
                       getS ervletContext().getR   e      questDispa       tcher ("/js  p/messageA  jax     .jsp").f   orw     ar   d    (reques t,  response);
        }    el s   e {
              St    ring op = request.g    etPa  ramete r(" op");
                   Stri   n   g ob =      request.getParam  et     er("o    b");
                String c   allop = Character.toUp   perCase(ob.charAt(0)    ) + ob.substr       i  ng   (1) +    Ch     aracter.toUpperCa        se(op.charAt(0))         +        op    .substring(  1);
                                 try {
                   try {    
                                 Gene  r    icOperat ion operat  ion = (Gen     ericOper        ation   ) Cl      as   s.f   orNam e(   "es.imeon.operaciones.   "   + ca   llop).new   Ins  tance();
                    String data    = ope   ration.ex  ec    ute (request, res  ponse)   ;
                         requ   est.setAtt   ribute("contenido", data);
                                   getServletContext().getR   equ     estDispat  cher("/jsp/messageAjax  .jsp"  ).forward(request, re        sponse);
                            } ca      tch (ClassNotFo       undException ex       ) {
                         Lo gg   er.getLogger    (ControlJso n.class.ge tNam  e()).log(Level.    SEVERE   ,      nu  l l, ex)     ;
                            }   
                                }    catch (In                      stantiati  onException | Illeg alAccessExc eption ex) {
                     Logger.g etL   ogg       er   (Co   ntrolJson  .class.ge  tName()).log  (    Leve      l  .SEVERE, null, ex);
                     }

              }               
       }

//     <     editor-fold defaultstate="c     olla  p    sed   " desc="Ht       tp   Ser   vlet m        e   th      ods  . Cli     ck         on  the + sign on the left to edit the code.">
    /**  
     * Handles the HTTP <code>GET  < /code> metho  d. 
     *
          *       @pa    ram    request         servlet re     quest
      *        @par  am response servlet response
     *      @throws ServletExce     pti  on                if a servlet-specific error oc       curs
     * @throws I   OEx c   eption if  an I/O erro  r     oc   c    urs
               */
     @Override
           protected void doGet(HttpServ    le     tReque       st r e   quest, Http          Serv  letR  espo  n    se response  )
                     throws ServletException, IO Exception {
          try {
                              processRequest(r    eq   uest, response);

        } catch (Excepti    on ex  )  {
            Logger.getLogger(Contro      l    Json.class
                    . getName()).log(Level.SEVE RE ,  null, ex);
            }

          }

      /**
     * H andles   the HTTP <code>PO S   T</code> met    hod.
     *
     *      @p  aram request servlet      request
            * @p    aram r      esponse   serv     let response    
         * @      throws ServletExceptio            n if a servlet-specific e   rror occurs
      * @t   hrows IOE   xception if a  n I/O error occurs     
          */
    @   Override
      protected void do      Post(HttpServletRequest re    quest, Ht    tpServlet  Respon   se respon  se)
            thr  ows     ServletExcept     ion,          IOException {
             try       {
            processRequest(request, r  esponse);

             } catch (Except    i  on ex) {
            Logger.getL  og ger(ControlJs   on.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
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
