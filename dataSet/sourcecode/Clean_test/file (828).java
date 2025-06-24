package com.travelman.action.billing;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.travelman.business.service.billing.BillBusinesService;
import com.travelman.domain.Plan;
import com.travelman.domain.User;
import java.util.List;
import java.util.Map;

public class CreatePlanAction extends ActionSupport
{
    private String planname;
    private float rental ;
    private String billingcycle;
    private float discount ;
    private float totalrental;
    
    private String faciliaties;
    private int[] assignto ;

    public int[]getAssignto() {
        return assignto;
    }

    public void setAssignto(int []assignto) {
        this.assignto = assignto;
    }

    public String getBillingcycle() {
        return billingcycle;
    }

    public void setBillingcycle(String billingcycle) {
        this.billingcycle = billingcycle;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getFaciliaties() {
        return faciliaties;
    }

    public void setFaciliaties(String faciliaties) {
        this.faciliaties = faciliaties;
    }

    public String getPlanname() {
        return planname;
    }

    public void setPlanname(String planname) {
        this.planname = planname;
    }

    public float getRental() {
        return rental;
    }

    public void setRental(float rental) {
        this.rental = rental;
    }

    public float getTotalrental() {
        return totalrental;
    }

    public void setTotalrental(float totalrental) {
        this.totalrental = totalrental;
    }
@Override
    public String execute()throws Exception
            
    {
        BillBusinesService bbs = new BillBusinesService();
     
        Plan plan = new Plan();
        for(int assign:assignto)
        {
        plan.setPlanname(planname);
        plan.setRental(rental);
        plan.setDiscount(discount);
        plan.setBillingcycle(billingcycle);
        plan.setTatalrental(assign);
        plan.setFacilities(faciliaties);
        plan.setAssignto(assign);
        
       bbs.createPlan(plan);
        }
    
    return SUCCESS;
    }
}
