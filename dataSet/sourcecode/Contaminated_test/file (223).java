package db;
import java.sql.*;
import java.util.*;

import com.wipro.*;


public class DAOClass {
		Connection con;
		Statement stmt, stcnt;
		PreparedStatement pst;
		ResultSet rs;
		//ArrayList<Bean1> alist = new ArrayList<Bean1>();
		Login x;
		ArrayList<Login> alist=new ArrayList<Login>();
		String z;
		public DAOClass() {
			try {
				System.out.println("1");
				Class.forName("oracle.jdbc.driver.OracleDriver");
				System.out.println("2");
	             con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","omarindam77om");
				System.out.println("3");
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
		
		public int insert(int id, String pwd, String name, String gender, String dob,String add,String dept,String desig,float sal,String joindate) {
			try {
				String tablename = "empdata";
				pst = con.prepareStatement("insert into "+tablename+" values(?,?,?,?,?,?,?,?,?,?)");
				pst.setInt(1, id);	
				pst.setString(2, pwd);
				pst.setString(3, name);
				pst.setString(4, gender);
				pst.setString(5, dob);
                                pst.setString(6, add);
                                pst.setString(7, dept);
                                pst.setString(8, desig);
                                pst.setFloat(9, sal);
                                pst.setString(10, joindate); 

				int i = pst.executeUpdate();
				System.out.println("Record Inserted Successfully");
				return i;
			}
			catch(Exception e) {
				System.out.println(e);
				return 0;
			}
		}
		
	public ArrayList<Login> displayAll() {
			try {
				stmt = con.createStatement();
				System.out.println("4");
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
			System.out.println("5");
			return alist;
		}
	
	public ArrayList<Login> displayBy(int j) {
		try {
			stmt = con.createStatement();
			System.out.println("4");
			//String tablename = "empdata";
			pst = con.prepareStatement("SELECT * FROM empdata WHERE EMP_ID='"+j+"'");
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
		System.out.println("5");
		return alist;
		
	}
	public ArrayList<Login> displayWel(int j) {
		try {
			stmt = con.createStatement();
			System.out.println("4");
			//String tablename = "empdata";
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


