package com.travelman.action.device;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.travelman.action.LoginAction;
import com.travelman.business.service.device.DeviceBusinesService;
import com.travelman.data.service.DataService;
import com.travelman.domain.User;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;


/**
 * @author Techno life
 *
 */
public class DeleteDeviceAction  extends ActionSupport {
    private Logger log=Logger.getLogger(DeleteDeviceAction.class);


    private long deviceid;
	
     private String currentUserName;
	
      private List<Long> list;

	

	@Override
	public String execute() {
		String actionResult = "";
                ActionContext ctx=ActionContext.getContext();
                HttpServletRequest request=(HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
                Map<String,Object> session = ActionContext.getContext().getSession();
		User user = (User)session.get("USER");
		setCurrentUserName(user.getFname()+" "+user.getLname());               
                               
		DeviceBusinesService deviceBusinesService = new DeviceBusinesService();
		list = new DataService().getDeviceIdForDevice(user.getUserId(),user.getUprofile());
		actionResult  = deviceBusinesService.removeDevice(getDeviceid());
		
                if(actionResult.equals("deviceused")) {
                    request.setAttribute("error", "Vehicle is assigned on this Device Id : "+deviceid+".First Delete Vehicle");
                    request.setAttribute("err", ctx);
               // addFieldError("deviceid", "vehicle exists");
                    log.info("request value -->"+(String)(request.getAttribute("error")));
                actionResult = "input";
                }else{	
		
		if (user.getUprofile() == 1) {
			actionResult = "individual";
			setCurrentUserName(getCurrentUserName()+" [I]");
		} else {
			if (user.getUprofile() == 2) {
				actionResult = "group";
				setCurrentUserName(getCurrentUserName()+" [G]");
			} else {
				if (user.getUprofile() == 3) {
					actionResult = "admin";
					setCurrentUserName(getCurrentUserName()+" [A]");
				}else{
                                  actionResult = "input";
                                }
			}
		}
                }
		return actionResult;
	}


    /**
     * @return the deviceid
     */
    public long getDeviceid() {
        return deviceid;
    }

    /**
     * @param deviceid the deviceid to set
     */
    public void setDeviceid(long deviceid) {
        this.deviceid = deviceid;
    }

    /**
     * @return the currentUserName
     */
    public String getCurrentUserName() {
        return currentUserName;
    }

    /**
     * @param currentUserName the currentUserName to set
     */
    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    /**
     * @return the list
     */
    public List<Long> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<Long> list) {
        this.list = list;
    }


	
}