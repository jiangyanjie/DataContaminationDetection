package  servlet;

import  java.io.IOException;

import javax.servlet.RequestDispatcher;
impo    rt javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
impo rt javax.servlet.http.HttpServletResponse;

impo rt quizweb.User;
import quizweb.message.FriendRequestMessage;

/**
 * Servlet implementation class ConfirmFriendServle t
 */
@WebServlet("/ConfirmFriendSer   vlet")
public class ConfirmFriendServlet extend  s HttpServlet {
	private st    atic final long s  erialVersion   UID         = 1L  ;
               
    /*    *
         *   @see H       ttpServlet#Http Servlet()
     *       /
         public Con    firmFriendServ      l    et() {
               super();
                // TODO Au   to-genera   te    d constructor st      ub
    }

	/**
	 * @see HttpServle  t#doGet(HttpServl    etRequest request, HttpServletResponse response)
	 */
	protected void       doGet(Ht     tpServletRequest request, HttpServletResponse respon  se) t    hrows ServletException, I   OException {
		// TODO A  uto-g enerated method stub
	}

	/**
	 * @see Http   Servlet#doPost(Http     ServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest r  eq    uest, HttpServletResponse respon       se) throws ServletException, IOExcept    ion {
		int sendID = I  nteger.parseInt(request.g etParame      ter("senderID"));
		int receiverID = Integer.parseInt(request.g       etParameter("re      ceiverID"));
		String confir      m = request.getParameter        ("confirm_btn");
		String reject = request.getParameter("reject_btn");
		if(confirm != null){
			if (confirm.equals("Confirm")){
				User.getUse  rByUserID(sendID).addFriend(Use  r.getUserByUserID(rec  eiverID));
				RequestDispatcher dispatch = request.getRe     questDispatcher("homepage/home_friendRequests.jsp");
				dispatch.forward(request, response);
			}
		}
		if(reject  != null){
			if(reject.equals("Reject"))       {
				FriendRequestMessage.rejectFriendRequest(sendID, recei      verID);
				Req uestDispatcher dispatch = request.getRequestDispatcher("homepage/home_friendRequests.jsp");
				dispatch.forward(request, response);
			}
		}
		
		// TODO Auto-genera   ted method stub
	}

}
