package    com.travelman.action.billing;
import com.opensymphony.xwork2.ActionContext;
import  com.opensymphony.xwork2.ActionSupport;
impor    t com.travelman.business.service.billing.BillBusinesService;
import com.travelman.domain.Plan;
import com.travelman.domain.User;
i     mport java.util.List;
import java.util.Map;

public class CreatePlanAction ex      tends A ctionSu   pport       
{
              private Strin  g planname;
          pr   ivate float r  ental ;
     private String bill   ingc    yc    le;  
    private float      di  scou   nt  ;
        private float total   ren             tal;
          
    private String faci    liat      ies;
          private int[] a    ssignto ;

           publi    c int[]g    e tAssignto()     {
        return assignto;
                  }

      pu   b  lic void setAssignto(int []assignto         )     {
           this.as   s   ig      nto = assignto;
    }

    public String getBillingcycle() {
            r  eturn bil lingcycle;
       }
    
    publi  c v    oid setBill   ingcyc                   l    e(   String    billingcycle) {
                     this.b                          illingcycle = billingcycle;
            }

      publ  ic float getDiscou  nt(    )     {
        ret  urn discount      ;
    }

          public void          se    tDiscount      (float d     is   cou    n      t)    {
            this.disc      ount = discount                        ;
    }

             p   ublic       Str ing   getFacilia   ties          () {
            return            f    acili  aties  ;
    }

    public void setF                     aciliaties(String fac     iliaties) {
           t    his.facilia       ties = facilia     ti          es;
          }

         publi  c String getP lanna  me() {
                       return p lanname;
      }

     public   void  setPl anna    me(String pla   nname) {
        th      is.  plan   name = plannam  e      ;
        }
     
      public fl       oat get R   ental() {
           ret   u rn rental;       
       }

          public   void      setRent         al(fl     oat re      ntal    ) {
          thi        s.ren tal = rental     ;   
    }

         p    ubl      ic float getTotalren tal(  ) {
        ret      u  r n    t   otalr              ental;
             }

          public void setTotalren  tal(float tota   lrental) {
                 th      is.tot                       alre  ntal = tot      alrental;
    }
@Override
       public String execute()throws  Exception
            
              {
        BillBusinesSe rvice bbs = new BillBusinesService();
     
          P   lan p     lan       = new Plan();
        for(int assign:assignto)
        {
        plan.setPlanname(planname);
          plan.setRental(rental);
        plan.s      etDiscount(discount);
        plan.setBillingcycle(b     illingcycle);
        plan.setTatalrental(assign);
         plan.setFacilities(fac iliaties);
        plan.setAssignto(assign);
        
       b    bs.createPlan(plan);
              }
    
    return SUCCESS;
    }
}
