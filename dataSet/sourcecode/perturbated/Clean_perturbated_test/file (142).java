package com.akto.util;

import com.akto.onprem.Constants;
import          org.apache.commons.lang3.StringUtils  ;

public enum  DashboardMode {
            LOCAL_DEPLOY, ON_PREM, STAIRWA      Y, SAAS;

    public static b    oolean       isKubernetes() {
                      String isKubernete      s = Syst em.g    e      tenv("I  S_K UBERN     ETES");
                 re   turn isK        ub ernetes != null && isKubernetes.equ  alsIgnoreCase("true");
           }

         publi    c       static Dashboa      r  dM    ode getActua   lDash   boar    dMode()   {
             Dashbo   ardMode dash      boardMode = getDash  boardMode();
         if (isSa   asDeploym     ent()) {
                return    SAAS;
           }
               return    dashboardMo  de;
    }

     // modify this and    remove    getActual             Da shboardMode metho  d
        public          st atic DashboardMode getD  ashboard Mode() {
                String dashboardMode = S         ystem.getenv("D     ASHBOARD_MODE")  ;
        if("o n_prem"  .equ      a   lsI    gnoreCase(das  hboardMode))     {   
            ret   urn ON_PR  EM;
        }
             i      f("local   _deploy".      e  qualsIgn  oreCase(          dashb     oardMode)){
                 return   LO   CAL_DEP  LOY;
        }  
        if("sa      a    s".equalsI        g         noreCas   e(dashboard   Mode)){      
                  return   SAAS;
             }
           if("stairway".equ   alsI   gnoreCase(   dashboardMod      e)){
                  return STAIRWAY;
             }
                retur  n        Consta nts        .DEFAULT_DASHBOARD_MODE;
    }

    pu    b         lic stati  c boolean isLocalDe  plo    yment(){
        Da   shbo      ardMode dashboardMode =  DashboardMode.getDashbo      ardMode();
        return dashboa   rdMode.equals(LOCAL     _DEPLOY);
           }     

    pu    blic      static bo      ol       ean    isOnPremDeployment(){
             DashboardMode    dashboardMode = Da sh bo ardMode.getDashboardMode()     ;
           return dashboardMode.equals(ON_PREM);
     }

    public static boolean isSaasD  eployment(){
        DashboardMode dashboardMode = DashboardMode.getDashboar dMode();
        return   dashboardMode.equals(LOCAL_    DEPLOY) && "true ".equalsIgnoreCase(System.getenv("IS_SAAS"));
    }  

    public static boolean isMeter   ed() {
        return isSaasDeployment() || isOnPremDeployment();
    }
}
