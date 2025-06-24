package project1;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateTeamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public CreateTeamServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Catching team leader jmbag
		//HttpSession session = request.getSession(true);
		//String JMBAGTeamLeader = session.getAttribute("userid").toString();
		
		
		
		String TeamName = request.getParameter("TeamName");
		String JMBAGTeamLeader = request.getParameter("JMBAGTeamLeader");//replaced with session data
		
		String TeamId = "50";
		
		try {
		
			MySQLcon db = new MySQLcon("jdbc:mysql://localhost:3306/project","a","a");
			
			//****
			ResultSet resCheckTeamName = db.Quer("SELECT team.name FROM team WHERE team.name='" + TeamName + "';");
			ResultSet resCheckLeader = db.Quer("SELECT * FROM users_team WHERE Users_idUsers'" + JMBAGTeamLeader + "' AND Responsibility_idResponsibility=1;");
			
			if (resCheckTeamName.first() && resCheckLeader.first()) {
				RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
				rd.forward(request, response);
			} else {
			//****
			
				db.Upd("INSERT INTO team SET name='" + TeamName + "';");
				
				ResultSet res1 = db.Quer("SELECT team.idteam FROM team WHERE team.name='" + TeamName + "';");
				
				while (res1.next()) {
					TeamId = res1.getString(1);
				}
				
				if (db.Upd("INSERT INTO users_team SET Team_idTeam='" + TeamId + "', Users_idUsers='" + JMBAGTeamLeader + "', Responsibility_idResponsibility='1';")) {
					
					RequestDispatcher rd = request.getRequestDispatcher("SuccessNewTeam.jsp");
					rd.forward(request, response);
	
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
