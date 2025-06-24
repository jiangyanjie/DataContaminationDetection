
import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class Acc_Test {
	
	private static Cmd_UI ui = new Cmd_UI();

	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		test_Connect();
		test_init();
		test_logout("admin");
		test_signUp_login_logout();
		test_create_view_subForum();
	}
	
	/*
	 * Check that the methods responsible for creating and displaying a sub-forum are working. This test important because 
	 * those are relatively complex methods.
	 */
	private static void test_create_view_subForum() throws UnsupportedEncodingException, IOException {
		Com.sendMessage(Protocol.logIn_request("admin", "pass"));
		String outcome = Com.recieveMessage(); // Receive confirmation about log in as administrator 
		test_create_subForum();
		test_view_subForum();
	}

	/*
	 * Check that the method responsible for displaying a sub-forum is working with no problems. This test important because 
	 * this is a relatively complex method. 
	 */
	private static void test_view_subForum() throws UnsupportedEncodingException, IOException {
		Com.sendMessage(Protocol.viewSubForum_request("0"));          
		String outcome = Com.recieveMessage();
		if(outcome.equals("cars" + ":\n"))
			System.out.println("ViewSubForum-Test: OK!");
		else
			System.out.println("ViewSubForum-Test: FAILED!");
	}
	
	/*
	 * Check that the method responsible for creating a sub-forum is working with no problems. This test important because 
	 * this is a relatively complex method. 
	 */
	private static void test_create_subForum() throws UnsupportedEncodingException, IOException {
		Com.sendMessage(Protocol.createSubForum_request("cars", "good cars"));
		String outcome = Com.recieveMessage();
		if(outcome.equals("Sub Forum: cars with description: good cars\n\n\tCreated Succesfully.\n"))
			System.out.println("create_subForum-Test: OK!");
		else
			System.out.println("create_subForum-Test: FAILED!");
	}

	/*
	 * Check a sign up of a new user and its ability to log in and log out from the system. This test important because 
	 * this is a relatively complex test that check multiple different functionalities of the system.
	 */
	private static void test_signUp_login_logout() throws UnsupportedEncodingException, IOException {
		test_signUp();
		test_logout("user1");
		test_login();
		test_logout("user1");
	}

	/*
	 * Check that a user can log out from the forum with no problems. It`s very basic and important method and hence it`s critical
	 * to check this functionality.
	 */ 
	private static void test_logout(String user) throws UnsupportedEncodingException, IOException {
		Com.sendMessage(Protocol.logOut_request());
		String outcome = Com.recieveMessage();
		if(outcome.equals("Logged out from: " + user))
			System.out.println("logOut-Test: OK!");
		else
			System.out.println("logOut-Test: FAILED!");
	}

	/*
	 * Check the initialization of a forum. It`s very basic and important method and hence it`s critical
	 * to check this functionality.
	 */ 
	private static void test_init() throws UnsupportedEncodingException, IOException {
		//ui.init_request();
		Com.sendMessage(Protocol.init_request("admin", "pass"));
		String outcome = Com.recieveMessage();
		if(outcome.equals("Forum created. Admin: admin"))
			System.out.println("Init-Test: OK!");
		else
			System.out.println("Init-Test: FAILED!");
	}

	/*
	 * Check that a new user can sign up to the system with no problems.It`s very basic and important method and hence it`s critical
	 * to check this functionality.
	 */
	private static void test_signUp() throws UnsupportedEncodingException, IOException {
		Com.sendMessage(Protocol.signUp_request("user1", "pass1", "name1", "20"));          
		String outcome = Com.recieveMessage();
		if(outcome.equals("Signup successful. \nUsername: " + "user1" + "\n"+ "Shemer Forum" + ":\n"))
			System.out.println("SignUp-Test: OK!");
		else
			System.out.println("SignUp-Test: FAILED!");
	}

	/*
	 * Check the start of the connection between the client and the server. It`s very basic and important method and hence it`s critical
	 * to check this functionality.
	 */
	private static void test_Connect() throws UnsupportedEncodingException, IOException {
		Com.Conncect();
		String outcome = Com.recieveMessage();
		if(outcome.equals("Server says: Connected!"))
			System.out.println("Connection-Test: OK!");
		else
			System.out.println("Connection-Test: FAILED!");
	}
	
	/*
	 * Check that a user can log in to the system with no problems. It`s important to check
	 * this method mainly because it will be activated many times.
	 */
	private static void test_login() throws UnsupportedEncodingException, IOException {
		Com.sendMessage(Protocol.logIn_request("user1", "pass1"));                       
		String outcome = Com.recieveMessage();
		if(outcome.equals("Successfully connected as: " + "user1" + "\n"))
			System.out.println("LogIn-Test: OK!");
		else
			System.out.println("LogIn-Test: FAILED!");
	}

}
