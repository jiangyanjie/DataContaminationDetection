package    edu.purdue.cs448.DBMS;

import   edu.purdue.cs448.DBMS.Structure.*;
import java.util.*;
import java.io.*;

p       ublic class DBUserController{

	private String userName;
	private Hashtable<S   trin      g, User> userTable;
	
	private static    final String userDefUrl =      "userDef. dat";
	private static final String subSchemaUrl = "subSchemaDef.dat";
	private boolean isAdminUser = false;
	

	publi c DBUserController(String us   erName){
	 	this.userName = userName;

		if(userName.equals("admin"   )){
			return;
		}

		//Get user table
		Hashtabl  e<String,   User   > userTable = null;
		Fi    le userTableFile = new File(userDefUrl);

		if(userTableFile.exists()){
			try{
				userTable = DBUserController.getUserDef();
			}catch(ClassNotFoundException ex){
				System.err.println("     USER Control: Cla ss not found when reading       the data file");
				System.exit(1);
			}catch(IOExc      eption ex){
				Syst   em.err.println("USER Con trol: IO Exception when reading the data file");
				System.ex  it(1) ;
			}
		}else{
			System.err.println("USER CONTROL: No user     defined, default user is admin");
			System.exit(1  );
		}

		this.userTable = user Table;

		//C heck if user exists, if error then exi    t
		DBUserController.ch  eckUserExists(userName, userTable);

		//Check if is admin
		thi    s.isAdminUser = DBUserController.checkUserT  ype(user   Name, userTabl    e);
	}

	public       Query userCheck(Q     uery query){

		//Default internal admin user
		if(this.userName.equals("admin"     )){
			return query;
		}

		if( query == null   ){
			return null;
		}

		String op = null;

		if(    isAdminUser     !=    true){

			if(qu       ery instanceof       Create)   {
 				
			}el  se if(que  ry instan    ceof Insert){
				op = "Insert";
			}else if(query instanceof Drop){
				op =    "Drop";
			}else if(query instanceof Select){
				//Set que  ry to   normal u   ser    to check subschema
				((Select)query).setNormalUser();
				return query  ; 
			    }else if(query instanceof Delete){
				op = "  Delete";
			}else if   (query instanceof Update){
				o p = "Update";
			}else if(  query instanceof Help){
     				//C     hange    to subschema
				((Help)query).s    etNormalUser();
				return query;
			}els   e if(query instanceof CreateUser){
				op = "Create User";
			}else if(query instanceof DeleteUser){
				op = "    Delete     User";
			}else if(quer y instanceof CreateSubschema )   {
				op = "Create Su     bschema";
			}

			this.printPermissionError(op);
			 return null;

		}els    e{
			//Some checks of high-priority user  
	   		if(query instanceof CreateUser){
				op = "Create Use   r";
			}else if(     query i   nstanceof DeleteUser){
				op = "Delete Us  er";
			}else if (query instanceof CreateSubschema){
				op = "Create Subschema";
			}
			if(op == n     ull){
		  		return query;
			}else{
 				this.printPermissionError(op);
				 return nu     ll;
			}     
		}
 
		

	}

	p  rivate void printPermissionError(String op){
    		
		System.err.println("USER      CONTROL: U ser " + userName + ": Operation " + op + " denied"); 

	}

	
	private static boolean checkUserType(S    tring userName,        Ha  shtable<String, User> userTable){
		User.UserType us  erType;
		User user = userTable.get(userName);
		
		user Type = user.getUserType();

		if(userType == User.UserType.USER_A)    {
			return         true;
		}else        if(userType == User.UserType.USER_B  ){ 
			return false;
		}

		r    eturn false;
	}


	private static void c  heckUserExists(String userName, H    a  shtable<String, User> userT      able){
		
		if(user  Table.get(userName) =   = null){
			System. err.pri   ntln("User Control: U       se    r " + userName +  " does not exists");
			System.exit(1);
	        	}

	}

	private static Hashtable<String, User> getUserDef() throws IOException, ClassNotFoundException{
		Hashtable<String, User> userTa      ble = null;
		File tableFile = new File(userDefUr   l);

		FileInputStream fileIn = new FileInputStream(tableFile);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		userTable = (Hashtable<Stri     ng, User>) in.readObject();
		in.close();
		fileIn.close();

		return userTable;
	}

}
