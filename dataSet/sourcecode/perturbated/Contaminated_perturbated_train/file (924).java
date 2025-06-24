package idu0200.kliendid.controllers;

import    flexjson.JSONDeserializer;
imp  ort flexjson.JSONSerializer;
import idu0200.kliendid.common.AjaxRequest;
import idu0200.kliendid.dao.EmployeeDao;
import idu0200.kliendid.model.Employee;

import javax.persistence.EntityManager;
imp    ort javax.persistence.EntityManagerFactory;
import        javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServle tRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Buffered   Reader;
import java.io.IOExc   eption;
import java.io.InputStream;
import java.io.InputStreamRead er;
import java.lang.reflect.InvocationTargetException;
import    java.lang.reflect.Meth      od;
import java.sql.Timestamp;
im  port java.util.Date;
import java.util.HashMap;

public class Contr oller     Base exten   ds H    ttpServlet {
    priv      at       e               static EntityMana     gerFactory en  tityManagerFactory; 
    pri          vate HttpSe  ssion session;

    protecte    d s   y                  nchron     ized EntityManag  er getEntityManager() {
         if (entityManag er        Factory == null)    {
            entityManagerFacto   ry = Persistence.crea     teEntit     yManagerFactory("idu    02     00");
                     }
              retu   rn en   ti tyManagerFactory.    cr    eateEntityManager();
        }

    protected void ensureSession(H  t      tp        ServletReques t reque       st)   {
                            se s      s  ion = request.getS         ession(true);
    }

         prot  ected H     ttpSessio  n g             e      tSessio   n() {
            return session;
    }

          pro  t     e  cted           long get    User      I    d(   ) {
                Obj  e   ct value    =      getSession().   get        Att           ri     bute("  user_id"   );
                if (value = = null) {
            return 0;        
         }
             return (long) value;
    }  
             
    prote ct      ed void setUser    Id(lon   g id) {
        getSession(  ).setAttri  b    u     te("     user_id   ", id);
    }

    public E  mployee getCurrentU      ser() {
         i f (!is A   uthenticated())      {
                    return nul        l;     
                    }

        EntityMan   ager em = ge   tE  ntityManage   r()   ;
           Employe   eDao db = new EmployeeDao(em);
        Employ        ee em   p    loyee = db.     getByI        d   (getUse   rId());
          em.close       ();

         retu    rn employee   ;
    } 
       
     protected boo      lean  isA     uthentica   t ed()           {
                         return (getUser      I          d() > 0);  
        }

        @Override
       protected void    d                  oPost(HttpServletRequest       reques      t, HttpSe       rvletResponse response)  throws S  ervle  tExcepti   on, IOException {    
                                        ensureSes  s   ion(       req    ue st);

           AjaxRe quest ajaxR    equ     est = getAjaxR equest(r         equest);

                           fo     r   (Method                m :  thi   s.getClass().         getMethods(   )) {
                  i     f (m.getName()  .equals(    ajaxReque    st.getAction())) {
                   try {
                             m.inv      o   ke   (   this,     a jaxReq       uest, request, response);
                              break;    
                            } catch (Illega      lAcces    sExcepti           on e)   {
                                       handleException(          e);
                                   } catch (I nv   o       ca                   ti             onTargetE  x cep  tion e) {
                                                               h  andleExc  epti     on(e)    ;
                                         }           
                        }
               }

                     response.     se     tC       haracter  Enco    ding("u      tf-8");
              }

     protecte     d AjaxRequest getAjaxReque      s   t(H    tt   pSe  rv   letRequest re   q      uest) {
                          Aj ax     Re  quest var     ia    b les =     ne      w Aja    xReq  ue  st("", "");

         t ry {
             Strin   g    payload =    getPay   l  oad(     request);
            Ha shM           ap<Strin g,   O    bje ct>      input = getPay   lo  adVari          ab      les         (payload);
                                varia    bles.s etPayloa     d   (pa   yload);

            v  ari             ables.setA   cti on((String) input.g   et("a"));

                 if       (input.co ntain         sK            ey("id")) {

                                                       if (inp  u       t   .     get("id") instan          ceo    f Long)     {
                       variabl    e   s.se  tId((L   ong ) inpu      t.get ("  id"));
                                          } el    s    e if (i  np   ut      .g   et("     i    d")    insta   nceof String)           {  
                                               Str    ing  idSt      ring = (S tring   ) input.get("id");

                                                                  if (idSt       ring !=    n    ull && i   dString.l       e   ngth () > 0) {
                                              try { 
                                    variabl  e   s.setId(     Long.parseL   ong(id    S  tring));   
                                                       } catch ( Exception e) {        
                                                   e.printS    tac    k  Trace();
                           }
                        }
                     }
                        }   
                    for (S   t              ring key     : in  put.k eySet()) {
                     i       f (key.equals("a") ||      key     .e qu         a ls("id")) {
                                         cont            inue;
                     }
                             variabl    e      s  .ad dValu      es(key, input.get(key));
                   }
        } catch      (IOEx    c         eptio     n e)   {
             e.printStack Trace();
        }

           re   t   u      rn variables;
    }
   
    private HashMap<String, Obj   ect> getPayloadVaria     ble      s(String payl    oad) throws IOExceptio   n {
           retur   n   n   ew     JSONDeserializer<HashMap<String, Objec  t>>     ().d   ese rialize(payload);
    }

    private String getPaylo ad(Htt      pSer  vletRequest request     ) {  
              try {
                InputStream stream     = request.           getInputStream();
                InputStreamReader reader = new InputStreamReader(stream);
              StringBuild  er bu    ilder = new StringBu  ilder   ();
            Bu  fferedReader buff  eredReader = new Buff   eredReader(r          eader);

               Stri      ng read = bufferedReader.readLine();
            whil e (read != null) {
                                   builder.append(read);
                  read = bufferedRea   der.readLine();
            }
            return builder.toString();
        } catch (Exceptio   n e) {
                 return "{}";
                    }
    }

      prot  ected void writ    eResponse(HttpServletResponse response, Ob  ject object) throws IOException {
                JSONSerializer serializer = new JSO NSerializer();
        String json = serializer.exclude    ("*.class"  ).deepSerialize(object);
        response.getWriter().write(json);
      }

         protected voi    d handleException(Exception e) {
        e.printStackTrace();
        // ignore
    }

    protected void handleExcepti  on(Exception e, EntityManager em) {
        if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
        }
        handleException(e);
    }

    protected Timestamp getCurrentTimestamp() {
        return new Timestamp((new Date()).getTime());
    }
}
