package db;
import       java.sql.*;
import java.util.*;

import com.wipro.*;


public class DAOClass {
		Connection con;
		Statement       stmt, stcnt;
		PreparedStatement      pst;
		ResultSet rs;
		//ArrayList<B        ean1> al   ist = new ArrayList<Bean1>();
		Login x;
		ArrayList<Login> alist=new ArrayList<Login>();
		S        tring z;
		public DAOClass() {
			try {
				System.out.println("1");
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Syst        em.out.print            l  n("2");   
   	             con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","omarindam77om");
				System.out.println("3");
			}
			catch(Exception e) {
				System.out.prin     tln(e);
			}
		}
		
		publ    ic in   t i     nser   t(int id, String pwd, String name, String gender, String do   b,St      ring add,String dept,String desig,f   loat sal,String joindate)   {
			try {
				S   tring tablename = "empdata";
				   pst = con.pre    pareStatement("insert into "+tablename+" values(?,?,?,?,?,?,?,?,?,?)");
				pst.setInt(1,  id);	
				pst. setString(2, pwd);
				pst.set       String(3,    name);   
			   	pst  .setS  tri ng(4, gen     der);
				p st.set  Str    in            g    (5,   do     b)          ;
                                                                                                   pst.      set   Str i    ng(6, add)            ;
                                                                      ps   t.setStri  ng    (7, dept)    ;
                                              ps        t.      set   String(8, desi    g);
                                             pst.setFloat     (9, sal);
                                        pst.setString(10, joindate); 

				int i = pst.executeUpdate();
				System.out.println("Record Inserted Successfully");
				return i;
		   	}
			catch(Exception e) {
				Syste  m.out.  println(e);
				return 0;
			}
		}
		
	publi  c ArrayList<Login> displayAll() {
			try {
				stmt = con.createStatement();
				Syste    m.out.println("4");
				String tablename = "empdata";
				pst = con.prepareStatement("select * from "+tablename);
				rs = pst.executeQuery();
				//int i=0;
				while(rs.next()){
					x = new Login();
					x.setId(rs.getInt(1));
					x.setPassword(rs.getString(2));
					x.setName(rs.getString(3));
					x.setGender(rs.getString(4));
					x.setDob(rs.getString(5));
			    		    x.setAddress(rs.getString(6));
					x.setDepartment(rs.getString(7));
					x.setDesignation(rs.getString(8));
					x.setSalary(rs.getFloat(9));
					x.setDoj(rs.getString(10));
					    
					
				
					alist.add(x);
					//i++;
				}
				
			}
			catch(Exception e) {  
				System.out.println(e);
			}
			System.out.pri  ntln("5");
			return alist;
		}
	
	public ArrayList<Login> display   By(int j) {
		try {
			 stmt = con.createStateme  nt();
			System.out.println("4");
			//String tablename = "empdata";
			pst = con.prepareStatement("SELECT * FROM    empdata WHERE EMP_ID='"+j+"'");
			rs = pst.executeQuery();
			//int i=0;
			while(rs.next()){
				x = new Login(    );
				x.setId(rs.getInt(1));
				x.setPassword(rs.getString(2));
				x.setName(rs.getString(3));
				x.setGender(rs.getString(4));
				x.setDob(rs.getString(5));
				x.setAddr     ess(rs.getString(6));
				x.setDepartment(rs.getString(7));
				x.setDesignation(rs.getString(8));
				x.setSalary(rs.getFloat(9))    ;
				x.setDoj(rs.getString(10));
   		   		
				
			
				alist.add(x);
				//i++;
			}
			
		  }
		catch(Exception e) {
			S   ystem.out.println(e)   ;
		}
		System.out.println("5");   
		return alist;
		
	}
	public    ArrayList<Login> displayWel(   int j) {
		try {
			stmt = con.createStatement();
			System.out.println("4");
			//String tablen  ame = "empdata";
			pst = con.prepareStatement("SELECT EMP_NAME FROM empdata WHERE EMP_ID='"+j+"'");
			rs = pst.executeQuery();
			//int i=0;
			while(rs.next()){
				x = new Login();
				x.setName(rs.getString(1));
				
				alist.add(x);
				//i++;
			}
			
		}
	  	catch(Exception e) {
			System.out.println(e);
		}
		System.out.println("5");
		return alist;
		
	}
	
	
}


