/*
 * To change        this  licens    e header, choos   e License    Header  s in P roject Propertie      s.
 * T   o change   this template file  , choose Tools | Templates
      * a     nd open the    template       in the editor.
 */
package Controlador;

imp   ort Modelo.ConexionBD;
import java.      io.IOException;
import java.io.PrintWriter;
import java .util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ht tp.Http   Servlet    Re  s    ponse;

/**
 * Clase que con sulta equipos y gen              era una tabla en sintaxis HTML
 * @author   jpachecov
 */
@WebServlet(name = "ConsultaEqui  po", urlPatterns = {"/Consu     lt  aE   quipo "})
pub        lic class ConsultaEq uipo       extends HttpS      ervl  et {
    
    
    privat    e C onexionBD b  d =  n    ew         Conexion     BD()       ;  
       
         St     ring header =    "    <!DOC      TYPE   html>\n" +
"<htm      l lang=\    "es\">\n" +
"    < head> \n" +
"             <meta charset=\" UTF-           8\"  >\n" +
"        <title>Consult     a  - I nventa   rio IIB</title>\      n" +    
"        \n" +    
"        <!--Imag        en pestaÃ±a-->     \n" +
"        <link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"img/escudoUn  am  Negro.png\">\n" +       
"\n"     +
"          <!--Java  Script-->\n"   +
"             <script type=\"text/javascript\" src=\"js/jquery-1  .      1  0.1.min.js   \"></script>     \n" +
"            <script s  rc=\"js   /ini  c    iarSesio  n.js\    "  >  </sc  ript>\n" +
"\n" +
"        <!--Estilos plantilla-->\n" +
"          <l     ink   rel=\"s   tylesheet\" href=\"css/6cols.css\">       \n" +
"        <link rel=\"s    tyl  esheet\" href=\"css/4cols .css\">\n" +
"                    <link rel=         \"stylesheet\" href=\"css  /2cols.css\">\n" +
"        <link rel=\"sty  lesheet\" hre              f=\"     css/c   o  l.css\">\n" +
"                 <link rel=\"stylesheet\  " hre     f=\  "css /es  tilosPlantilla.css\">\n" +
"         <link   rel=\"stylesheet\" href=\"c   ss/formu          lario.css\">\n     " +
"\n" +
"                        <!--Est   ilo Ãºnico      pÃ¡gi     na-->\n"                   +
"          <lin   k rel=\"sty     lesheet\" href=\"css/index.  css\">\n" +  
"\n" +
"        </head>\n" +
"    <body>\n" +
"            <! --Inici    o en   cabezado  -->  \n" +
"             <h  eader>\n" +
"                               <div cl   ass=\"se  cti   on gr      ou    p\  " id=\"enc  a       bez     ado\">\n" +
"                       <div c  lass=\"col span         _1   _     of_6\">\n" +
"                                 <img src=\"i   m     g/esc     udoUnam.  png\       " h eight=  \"40%\  " width=\"40     %    \" alt=\"escudo     unam\"      id=\"imagenUna   m\">\n" +
"                       </    div>     \n" +
"                     <d        iv class=\"c            ol sp an_4      _of   _6\" i d=\      "nombreInstituto\">\n"       +
"                                  <  span>Instituto de<br>       In       ves    tig   ac     iones<br>Bib    l iog  r      Ã¡ficas</   span    >  \n       " + 
"                          </div> \n" +
"                                 <div class=\"col sp   an       _   1_of_6  \">\n" +
"                                      <   img sr   c=\"    img/l      ogoBibliot      eca.png\" height=\"80%\" width=\"   80%    \" a     lt =\"escudo    bib      lioteca\"              id=\  "imagenBiblio teca\   ">\n" +
"                                     </div  >\n"     +
"                </   div>     \n" +
   "              <div class=\"  sec    tion group\" id=\     "barra\">\n" +     
"                                 <div         id=\"            login\">\n"  +
"                                             <br>\n" +
"                    </div>     \n"      +
"            </div>\n" +
"               </header>\n"+
            "<   div class=\"     s    mart-bl    ue\"       >\n";
          
       
    String foot   e         r = "    </d       iv>\n     <foo    ter>\n" +
"                             <div class=\"section  group    \" id=\"p    ie\">\n" +
"                <   p>He  cho      en     MÃ©xico,   todos    l      os     derechos r eservados 2014. Esta pÃ¡gin   a pu ed              e ser reproduc   i   da con fines         no l         ucrat         ivo  s, siem   pre y cuando no se     mut          ile,     se cite la f   u   en te   completa y su    direcciÃ³n ele   ctrÃ         ³    nica. De    otra forma requiere perm   iso prev     io por escrito de la Insti    tuciÃ³n.</p>\n" +
"                </div>\n"        +
"                             </   footer>\n" +
"                 <    !--Fin pie-->\n" +
"           <  /b    ody>\n" +
"</html>\n"    +
"";
             
      
    /**
     * Proces  ses requests for    both HT    TP <co    de          >GET</code> and <code       >POST    </code>
     * methods    .
     *
      * @param request servlet request  
     * @param resp   onse s     ervlet     r esponse
                   * @t      hrows      ServletExce     ption if    a     servlet-spec    ific error occu    rs
     * @throws IO Exception if an I/  O error occurs
     */
    prote    ct    e   d void proc  essRe    quest(Ht  tpServle    tRequest request, HttpSer   vletRespons  e respo  n   se)
            throws Se      rvletExce  ption,       IOEx ception {
        response.setContentType       ("text/html;charset=UTF-8")       ;
                response.setCharacterEnco  ding("U   T  F-8");   
        request.setChar   acterEncoding(   "UTF-8");
               try (Pri  ntWriter out = response.getWriter()) {
                 out.pr     in      t(header         + h  azConsulta(re   q   uest) + footer);
        }
        }
    /*  *
     * M    etodo que hace una con   su     lta     de equipos para  la     base de    da   tos  y      
     * r egres   a una t  abla         en  si       ntaxis HTML que contien      los e quipo s de    vue        ltos 
               * por l    a consu    lta       .     
     * @param reques   t Obje            to que conti    ene los    da              tos necesarios para c       onsultar
      * en l   a      base de da      tos.
         * @return Una tabla en sin  taxis HTML.
     */
    public Stri  ng hazCo       n             sulta(HttpServ   letReque     st reques     t) {
             St    r  in     g mar    ca =     re  qu  est.ge   tParameter("ma     r  ca");
            S  tring   nu            mero = request .getParameter("numer  oSer     ie");
          Strin     g f       amilia = req     uest    .getParameter("familia");
             Str        ing ubica    cion = request.getParamet          er("      ubicacion"     );
            String     respons       a ble     =      request.getParam            ete   r("r       esponsa      ble");
          String tipoEquipo = reque  st.      getPara    meter("tipoEquipo")    ;
         Stri  ng de  part    a   mento = request     .  getPa    r  ameter("departamento"   );
                Stri     ng fechai = request     .getParamete           r("fechaI");  
           String fecha  f               =  re   quest.getParameter("fe    cha  F");
         St    ring esta               do = req  uest.getP ara       meter("estado");

        retu  rn ge   ne   ra  Tabla(bd.reportes(marca,    num er   o, familia, tipoEquipo, fechai, fechaf, depar   tamento,            ubi    cacion, respons  able, es   tado));
        
    }
              /**
     *   Genera  una  tabla        e   n HTML
     * @  para   m eq u    ipos Un arreglo de       o   bjetos equi  po para          ll    enar      la tabla.    
     * @r  et      ur  n  La    t  abla   en HTML lista para incrustarse   e   n     la  pa          g    ina.
     */
               pub lic    Str   ing ge   ne   ra   Tabla(ArrayList<Equipo>       equip      os ) {

        String    tab la = "<table id=  '   ta               blaResult   ad       o'>       \n";
             tab    la += "<         tr>\n";
           tabla += "<th>Num. Inv. interno<  /th> <th>Nu       m.  Inv. UN    AM<      /th> <th>Marc      a<  /th> <t   h>     Modelo</th>"
                                       + "<th>S    erie  </th  > <    th >Familia  </th> <th>Tipo</th> <  th>F   e cha de registro</  th>    "
                  +                 "<t   h>Depart  amento<  /th> <t h>U       bicaciÃ     ³n  </    th    > <th>Responsable</th   >\n    ";

        if (equipos.size() ==     0    ) {
            r eturn      "<label id=\"errorBu       sque     d  a\" class=\"errorFormulario\">No s e e  ncontraron equipos</label>";
                 }
        for (E   quipo e :   equipos) {
             tabla   += "<tr>\n";   
      
             tabla += " <td>" + e.getClave _activo_fijo()   + "</     td>";
                 t     abla += "   <td>" + e.getNum_inv_u    nam() + "</td>";      
                t ab    la += "  <td>" +    e.getClav    e_marcar() + "</t         d>     ";
              tabla += "<td>" + e.ge     tClave_modelo() + "    </td>   ";
             t   abla +   = "<td>" + e.getS   erie()      + "</td>";
                    tabla += "<td>    " + e  .getClave_familia() + "</     td>"   ;
            tabla += "      <td>" + e.getClave_ tip     o  () +    "</td>";
             t  abla += "<td>" + e.getFecha    _de_    resg  ua rdo() + "</td>";
                    tabla += "<td>" + e.g      etClave_ins  tituc   ion() + "</td>";
            tabla += "<t    d>" + e.getCl  ave_area(   ) + "</t        d>";
              tabla += "<td>" + e.getResponsable() + "</t   d>";

            tabla += "<   /tr>\n";
           }

         tabla += "</tr>\n";
             tabla += "</tab   le>\n";

        return tabla;  
    }

    // <editor-fo ld defaultstate="collapsed   " desc="HttpServlet m     ethods. Click on the + sign on the left to edit the c    ode     .">
    /**
     * Handles the HTTP <code>GET  </  code> method.
      *
           *  @param requ  est serv   let request
     * @param respon   se servlet response
     * @throws ServletE    xcept     ion if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
       */
        @Override
    protected void doGet(Htt pServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }   

    /**
     * Handles t        he H    TTP <code>    POST</code> method.
     *
     * @param req  uest servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error   occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPo  st(HttpServletRequ    est request, Ht   tp   ServletResponse response     )
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * R  eturns a short description of the servlet.
     *
      * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
