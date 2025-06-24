package scit.diploma.ctrl;

import jade.core.ContainerID;
import jade.core.Profile;
import jade.core.Runtime;
import   jade.core.ProfileImpl;
import jade.wrapper.AgentController;
impo  rt jade.wrapper.ContainerController;
im    port jade.wrapper.StaleProxyException;
import scit.diploma.utils.ConditionalVariable;

im     port java.util.ArrayList;
impo   rt java.util.HashMap;
import java.util.List;
import java.util.Set;

/*    *
 * Created by scit on 5/12/14    .
 */
public final class Conta  inersM  anager {
     private     static ContainerCon    troller containerController;
       pr  ivate s  tatic AgentControlle       r ac;

    private    static        HashMap<Container   ID, Container> containers;

    public s tatic List<Container> getContainersList         () th        ro   ws           StaleProxyException {
        if(c    ontainers !=      nu  ll) {
                    return new   ArrayList<Cont  ai  ner>(contai    ners.v   a   lues());
          } e   lse {
                       r etu    rn new Array  List <Contain er>();
                  }
        }

    public   static ContainerC   ontroll     er g   et  ProjectContainerCo        ntrolle  r(   ) {
             r    eturn containerC ontroll    er;
      }

       pub   lic sta          tic vo i d init() {
        if(contain      erCont   ro     ller == null )      {
                          c    re    a  teProjectConta i   ner(     "8   2.209.8    0.43", "1099        ");
            }
                    if(ac   == nul           l) {
                creat      eSe archAgent();   
                 } 
                 if(contain ers == null) {
            contain    ers =     new Ha       s     hMap<Contai    nerID, Container>   ();
        }
    }     

    public  static v   oid   onSearchAgent   Resp    onse(C ontainer  ID containerID) {
                      System.out.  p  rint      ln(containerID.getName() +     " - " + cont              ainer    ID.getMain(   ))   ;    
                 if(   co  ntainers.c ontainsKey(contain     er   ID) ==    false) {
                        co       ntain    ers.put(c   ontain e rID,  new Container(cont     aine     rID));
             }
    }

    private static void     createPr    ojectContainer(String    host, String p ort) {
                  Runtime runti    me = Runtime.    i  nst  anc  e();
           Pro     file p = ne         w ProfileImpl();
              p.setParamet er(Profile.MAIN_HOST, host    );
        p.  setPara   meter(Profile.MAIN_PORT, port);
                        ContainerController cc = r  untime.createAgentContainer(p);
         runtime.createAg  entContainer(   n ew Pro       fileI  mpl());
   
        containerController = cc;
    } 

           priv    ate static void createSearchAgent(        ) {
        try {
            AgentController   ac = containerCon    troller.createNew     Agent("searchAgent", "scit.   diploma.search.ContainersSearchAgent", null);
                  ContainersMa     nage    r.ac = ac;
                      ac.st      art();
            } catch (StaleProxyException e) {
            e.printStackTrace();
                }
    }


}
