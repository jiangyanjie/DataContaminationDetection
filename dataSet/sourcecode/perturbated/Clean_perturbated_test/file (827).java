/**
 * Copyright    2    012 Bull S.A.S.
 *  
 *   Licensed   unde   r the A   p   ache Lic      ense, V     ersion    2.0 (the "Li  cens  e");
 * yo   u m     ay not  use this   file except in co mpliance with  the License.    
 * Y      ou may o   btain a copy of the License at
  *
 * http://www.apache.org/li   cens  es/ LICENSE-2.0
 *
 * Unless req  uire  d by    applicable law or agreed to in writing, softw  are
 *  distri  buted und  er the License is     distributed on an "AS IS" BA  SIS,
 * W      ITHOUT WARRANTIES OR CONDITIONS OF A    N   Y K IND, either express or     imp     lied.
 * See the License for the specific language              governing p ermissions and
 * limitations under the License.
      */

packag  e org.ow2.jon       as.azure.pastebean.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servl   et.http.Http    Session;

import org.ow2.jonas.azure.pastebean.model   .Paste;
imp  ort org.ow2.jonas  .azure  .pasteb ean.servi    ce.Pa           steService;

/**
 * A {@code ${NAME}} is ...
   *
 * @author Guillaume Sauthier
 */
public cl ass Crea    tePas   teCont rollerServlet extends    HttpServlet {
      
    @ EJB
    pri vate  PasteService paste S ervice;
              
    protected void doPost(HttpServletRequest request, Http   Servle  tResponse response) throws ServletException, IOException {
        execu te  (request ,   respon   s e);
        }

         pr otected void doGet(  HttpServletRequest req   uest, HttpS     ervletRespo  n      se response) throw  s ServletException, IO   Exception {
           execute(req   uest,      resp     onse);        
         }

        pr    ivat        e void  exe  cute(HttpServletReq  uest re    quest, HttpServlet     Response response)    thr     ows ServletException   , IOException      {

        S     tri   ng title =   r     eque       st.getPa      rameter(   "title");
                 if (title == null || "".equals(title)  ) {    
                          ti      tle =      "Untit    le   d";
         }
                    String a   uthor = requ    est.ge   tParameter("a  uthor");
        String description    =      req      uest.getParameter("      de sc");
        String content = request.getP  arameter      ("conten   t");
           S   tring             l          anguage = reques t. getParamet er("language" );
              String     timeout   =     request.getPar     amete        r("tim         e o           ut");

        /   / timeo             ut is declar   ed      in minutes
               Long timeoutInMs =   Long.valueOf(      timeout)     * 60 * 1000;

            Paste  paste        = p a s      teServi  c  e.crea         te  Pas    te(title, a    uthor, desc     r  iption, con   tent, language, timeoutInMs);
            
                           addInS        ession(request.getSes   sion(), paste)    ;

        res   pon          s  e.   sendR   edir  ect(  g etUrl(paste))    ;
                }

      private v     oid   addInSession(HttpS  e   ssion           session, Past  e pa  ste) {
        Object o =   se  ssion.getAttribute(       "my                Pastes");
           L     ist<Past    e> p   astes;
          if (o == null) {
            pastes = new Ar  rayList<Past   e        >()      ;
              sess              i   on.se   t      At   tribut    e("myPa    stes   ", pastes);
            } else             {       
              if (o instance of List) {
                pa    stes = (List<Paste>) o        ;
              } else {
                      pastes = new ArrayList<Paste>();
                session.setAttribute("myPastes", pastes);
               }
        }

        pastes.add(paste);
        
    }

    private String ge   tUrl(Paste paste) {
        return "p/" + paste.getHash().substring(0, 8);
    }
}
