package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import database.bean.Drug;
import database.bean.Employee;
import database.bean.Patient;
import database.bean.Prescription;
import database.bean.Schedule;

/**
 * @author Andy Hsiang
 */
public class DatabaseProcess {
	
	private static Connection conn = ConnectionManager.getInstance().getConnection();
	
	public static void displayStart(){
		System.out.println("logged in!");
	}
	/**
	 * This method calls display method of the table based on the bean instance
	 * @param bean
	 * @throws SQLException 
	 */
	public static void display(String tableName){
		
		Statement stmt = null;
		ResultSet rs = null;
		String sql;
		
		try{
			stmt= conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if(tableName.toLowerCase().equals("patients")){
				sql = "SELECT * FROM PATIENTS";
				rs = stmt.executeQuery(sql);
				displayPatients(rs);;
			}
			else if(tableName.toLowerCase().equals("employees")){
				sql = "SELECT * FROM EMPLOYEES";
				rs = stmt.executeQuery(sql);
				displayEmployees(rs);
			}
			else if(tableName.toLowerCase().equals("drugs")){
				sql = "SELECT * FROM DRUGS";
				rs = stmt.executeQuery(sql);
				displayDrugs(rs);
			}
			else if(tableName.toLowerCase().equals("prescriptions")){
				sql = "SELECT * FROM PRESCRIPTIONS";
				rs = stmt.executeQuery(sql);
				displayPrescriptions(rs);
			}
			else if(tableName.toLowerCase().equals("schedules")){
				sql = "SELECT * FROM SCHEDULES";
				rs = stmt.executeQuery(sql);
				displaySchedules(rs);
			}
		}catch(SQLException e){
			System.out.println("sql exception in display()");
			System.out.println(e);
		}finally{
			try{
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();	
			}catch(Exception e){
				System.out.println("error closing resource within display()");
			}
		}
	}
	
	private static void displayPatients(ResultSet rs)throws SQLException{
		
		StringBuffer bf = new StringBuffer();
		while (rs.next()){
			bf.append(rs.getInt("pid")+": ");
			bf.append(rs.getString("firstname")+" ");
			bf.append(rs.getString("lastname"));
			System.out.println(bf.toString());
			bf.delete(0, bf.length());
		}
	}
	
	private static void displayEmployees(ResultSet rs)throws SQLException{
		
		StringBuffer bf = new StringBuffer();
		while (rs.next()){
			bf.append(rs.getString("name")+" ");
			bf.append(rs.getString("username")+" ");
			bf.append(rs.getString("pass")+" ");
			bf.append(rs.getDate("dob")+" ");
			bf.append(rs.getString("phone"));
			System.out.println(bf.toString());
			bf.delete(0, bf.length());
		}
	}
	
	private static void displayDrugs(ResultSet rs) throws SQLException{

		StringBuffer bf = new StringBuffer();
		while (rs.next()){
			bf.append(rs.getInt("drugid")+": ");
			bf.append(rs.getString("drugname"));
			System.out.println(bf.toString());
			bf.delete(0, bf.length());
		}
	}
	
	private static void displayPrescriptions(ResultSet rs)throws SQLException{
		
		StringBuffer bf = new StringBuffer();
		while (rs.next()){
			bf.append(rs.getInt("presc_id")+": ");
			bf.append(rs.getDate("start_date")+", ");
			bf.append(rs.getDate("this_day"));
			System.out.println(bf.toString());
			bf.delete(0, bf.length());
		}
	}
	
	private static void displaySchedules(ResultSet rs)throws SQLException{
		
		StringBuffer bf = new StringBuffer();
		while (rs.next()){
			bf.append(rs.getDate("work_day")+", ");
			bf.append(rs.getTime("work_from")+", ");
			bf.append(rs.getTime("work_till")+", ");
			bf.append(rs.getBigDecimal("hourly_rate")+", ");
			bf.append(rs.getInt("vac_days")+", ");
			bf.append(rs.getString("username"));
			System.out.println(bf.toString());
			bf.delete(0, bf.length());
		}
	}
	
	/*** This method checks the bean object param and call the correct insert method accordingly
	@pre: bean is pre-initialized with all fields required to successfully insert a row of data into a given table.
	@post: a row has been successfully inserted into the database in a specified table
	 ***/
	public static boolean insert(Object bean) {
		
		String sql;
		PreparedStatement stmt=null;
		
		try{
			if(bean instanceof Patient){
				sql = "INSERT into patients ("+Patient.getTableSchema()+") " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				return insertPatient((Patient)bean, stmt);
			}
			if(bean instanceof Employee){
				sql = "INSERT into employees ("+Employee.getTableSchema()+") " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				stmt=conn.prepareStatement(sql);
				return insertEmployee((Employee)bean, stmt);
			}
			if(bean instanceof Drug){
				sql = "INSERT into drugs ("+Drug.getTableSchema()+") " +
						"VALUES (?, ?, ?, ?, ?)";
				stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				return insertDrug((Drug)bean, stmt);
			}
			if(bean instanceof Prescription){
				sql = "INSERT into prescriptions ("+Prescription.getTableSchema()+") " +
						"VALUES (?, ?, ?, ?, ?, ?, ?)";
				stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				return insertPrescription((Prescription) bean, stmt);
			}
			if(bean instanceof Schedule){
				sql = "INSERT into Schedules ("+Schedule.getTableSchema()+") " +
						"VALUES (?, ?, ?, ?, ?, ?)";
				stmt=conn.prepareStatement(sql);
				return insertSchedule((Schedule)bean, stmt);
			}
		}catch(SQLException e){
			System.out.println("sql exception within insert()");
			System.out.println(e);
		}finally{
			if(stmt !=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					System.out.println("error when closing stmt within insert()");
					e.printStackTrace();
				}
		}
		return false;
	}
	
	/*handle the insert row in one of the following insert methods*/
	private static boolean insertPatient(Patient bean, PreparedStatement stmt) throws SQLException{

		stmt.setString(1, bean.getFirstName());
		stmt.setString(2, bean.getLastName());
		stmt.setDate(3, bean.getDob());
		stmt.setString(4, bean.getPrimaryDoc());
		stmt.setString(5, bean.getPhone());
		stmt.setString(6, bean.getAddress());
		stmt.setString(7, bean.getCity());
		stmt.setString(8, bean.getState());
		stmt.setString(9, bean.getZip());
		
		int affected = stmt.executeUpdate();
		if(affected == 1){
			System.out.println("new patient added successfully");
		}else{
			System.out.println("error adding patient");
			return false;
		}
		return true;
	}
	
	private static boolean insertEmployee(Employee bean, PreparedStatement stmt) throws SQLException{
		
		stmt.setString(1, bean.getName());
		stmt.setString(2, bean.getUsername());
		stmt.setString(3, bean.getPass());
		stmt.setDate(4, bean.getDob());
		stmt.setString(5, bean.getPhone());
		stmt.setString(6, bean.getAddress());
		stmt.setString(7, bean.getEmail());
		stmt.setString(8, bean.getPosition());
		
		int affected = stmt.executeUpdate();
		if(affected == 1){
			System.out.println("new employee added successfully");
		}else{
			System.out.println("error adding employee");
			return false;
		}
		return true;
	}
	
	private static boolean insertDrug(Drug bean, PreparedStatement stmt) throws SQLException{
		
		stmt.setString(1, bean.getDrugName());
		stmt.setString(2, bean.getDescription());
		stmt.setInt(3, bean.getQuantity());
		stmt.setBoolean(4, bean.isControlFlag());
		stmt.setString(5, bean.getSideEffect());

		int affected = stmt.executeUpdate();
		if(affected == 1){
			System.out.println("new drug added successfully");
		}else{
			System.out.println("error adding drug");
			return false;
		}
		return true;
	}
	
	private static boolean insertPrescription(Prescription bean, PreparedStatement stmt) throws SQLException{
		
		stmt.setDate(1, bean.getStartDay());
		stmt.setDate(2, bean.getThisDay());
		stmt.setString(3, bean.getDose());
		stmt.setInt(4, bean.getQuantity());
		stmt.setInt(5, bean.getRefill());
		stmt.setInt(6, bean.getDid());
		stmt.setInt(7, bean.getPid());

		int affected = stmt.executeUpdate();
		if(affected == 1){
			System.out.println("new prescription added successfully");
		}else{
			System.out.println("error adding prescription");
			return false;
		}
		return true;
	}
	
	private static boolean insertSchedule(Schedule bean, PreparedStatement stmt) throws SQLException{
		
		stmt.setDate(1, bean.getWorkDay());
		stmt.setTime(2, bean.getFrom());
		stmt.setTime(3, bean.getTo());
		stmt.setBigDecimal(4, bean.getHourRate());
		stmt.setInt(5, bean.getVacationDays());
		stmt.setString(6, bean.getUsername());

		int affected = stmt.executeUpdate();
		if(affected == 1){
			System.out.println("new schedule added successfully");
		}else{
			System.out.println("error adding schedule");
			return false;
		}
		return true;
	}

	/**
	 * This method checks the bean instance and call the corresponding delete method
	 * @param bean
	 * @return true if a row has been successfully removed from database
	 */
	public static boolean delete (Object bean) {
		
		String sql;
		PreparedStatement stmt=null;
		
		try{
			if(bean instanceof Patient){
				sql = "DELETE FROM patients WHERE pid = ?";
				stmt = conn.prepareStatement(sql);
				return deletePatient((Patient)bean, stmt);
			}
			if(bean instanceof Employee){
				sql = "DELETE FROM employees WHERE username = ?";
				stmt = conn.prepareStatement(sql);
				return deleteEmployee((Employee)bean, stmt);
			}
			if(bean instanceof Drug){
				sql = "DELETE FROM Drugs WHERE drugid = ?";
				stmt = conn.prepareStatement(sql);
				return deleteDrug((Drug)bean, stmt);
			}
			if(bean instanceof Prescription){
				sql = "DELETE FROM Prescriptions WHERE presc_id = ?";
				stmt = conn.prepareStatement(sql);
				return deletePrescription((Prescription) bean, stmt);
			}
			if(bean instanceof Schedule){
				sql = "DELETE FROM schedu1es WHERE work_day = ? and username = ?";
				stmt = conn.prepareStatement(sql);
				return deleteSchedule((Schedule)bean, stmt);
			}
		}catch(SQLException e){
			System.out.println("SQLException within delete()");
			System.out.println(e);
		}finally{
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					System.out.println("unable to close stmt within delete()");
					e.printStackTrace();
				}
		}
		return false;
	}

	/*handle the delete row in one of the following insert methods*/
	private static boolean deletePatient(Patient bean, PreparedStatement stmt) throws SQLException{
		
		stmt.setInt(1, bean.getPid());
		int affected=stmt.executeUpdate();	
		
		if(affected==1){
			System.out.println("Patient with ID "+bean.getPid()+" has been Removed from database.");
		} else {
			System.out.println("unable to remove patient from database");
			return false;
		}	
		return true;
	}
	
	private static boolean deleteEmployee(Employee bean, PreparedStatement stmt) throws SQLException{
		
		stmt.setString(1, bean.getUsername());
		int affected=stmt.executeUpdate();	
		
		if(affected==1){
			System.out.println("Employee with username "+bean.getUsername()+" has been Removed from database.");
		} else {
			System.out.println("unable to remove employee from database");
			return false;
		}	
		return true;
	}
	
	private static boolean deleteDrug(Drug bean, PreparedStatement stmt) throws SQLException{
		
		stmt.setInt(1, bean.getDrugId());
		int affected=stmt.executeUpdate();	
		
		if(affected==1){
			System.out.println("Drug with ID "+bean.getDrugId()+" has been Removed from database.");
		} else {
			System.out.println("unable to remove Drug from database");
			return false;
		}	
		return true;
	}
	
	private static boolean deletePrescription(Prescription bean, PreparedStatement stmt) throws SQLException{
		
		stmt.setInt(1, bean.getPrescriptionID());
		int affected=stmt.executeUpdate();	
		
		if(affected==1){
			System.out.println("Prescription with ID "+bean.getPrescriptionID()+" has been Removed from database.");
		} else {
			System.out.println("unable to remove Prescription from database");
			return false;
		}	
		return true;
	}
	
	private static boolean deleteSchedule(Schedule bean, PreparedStatement stmt) throws SQLException{
		
		stmt.setDate(1, bean.getWorkDay());
		stmt.setString(2, bean.getUsername());
		int affected=stmt.executeUpdate();	
		
		if(affected==1){
			System.out.println("Employee with username "+bean.getUsername()+" is not working on "+bean.getWorkDay());
		} else {
			System.out.println("unable to delete schedule from database");
			return false;
		}	
		return true;
	}

	/**
	 * This method checks bean instance and calls corresponding get row method
	 * @param bean
	 * @return retrieved row represented by the bean instance
	 */
	public static Object getRow (Object bean) {
		int numOfObject=0;
		String sql;	
		PreparedStatement stmt=null;
		ResultSet rs = null;
		Object returnObj = null;
		try{
			if(bean instanceof Patient){
				if(((Patient) bean).getPid()!=0)
					sql = "SELECT * FROM patients WHERE pid = ?";
				else if(((Patient) bean).getPhone()!=null)
					sql = "SELECT * FROM patients WHERE firstname = ? AND lastname = ? and phone = ?";
				else
					sql = "SELECT * FROM patients WHERE firstname = ? AND lastname = ?";
				stmt = conn.prepareStatement(sql);
				returnObj = getPatient((Patient)bean, stmt, rs, numOfObject);
				if(numOfObject>1) returnObj=null;
			}
			if(bean instanceof Employee){
				sql = "SELECT * FROM employees WHERE username = ?";
				stmt = conn.prepareStatement(sql);
				returnObj = getEmployee((Employee)bean, stmt, rs);
			}
			if(bean instanceof Drug){
				if(((Drug) bean).getDrugId()!=0)
					sql = "SELECT * FROM drugs WHERE drugid = ?";
				else
					sql = "SELECT * FROM drugs WHERE drugname = ?";
				stmt = conn.prepareStatement(sql);
				returnObj = getDrug((Drug)bean, stmt, rs);
			}
			if(bean instanceof Prescription){
				if(((Prescription) bean).getPrescriptionID()!=0)
					sql = "SELECT * FROM prescriptions WHERE presc_id = ?";
				else
					sql = "SELECT * FROM prescriptions WHERE pid = ? and did = ?";
				stmt = conn.prepareStatement(sql);
				returnObj = getPrescription((Prescription) bean, stmt, rs);
			}
			if(bean instanceof Schedule){
				sql = "SELECT * FROM schedules WHERE work_day = ? and username = ?";
				stmt = conn.prepareStatement(sql);
				returnObj = getSchedule((Schedule)bean, stmt, rs);
			}
		}catch(SQLException e){
			System.out.println("sql error withing getRow()");
			System.out.println(e);
		}finally{
			try{
				if(rs!=null)
					rs.close();
				if(stmt!=null)
					stmt.close();
			} catch (SQLException e) {
				System.out.println("error closing resource withing getRow()");
				e.printStackTrace();
			}
		}
		if(returnObj != null)
			return returnObj;
		else
			return null;
	}

	/*handle the get row in one of the following insert methods*/
	private static Patient getPatient(Patient bean, PreparedStatement stmt, ResultSet rs, int numOfObject) throws SQLException{
		
		if(bean.getPid()!=0)
			stmt.setInt(1, bean.getPid());
		else if(bean.getPhone()!=null){
			stmt.setString(1, bean.getFirstName());
			stmt.setString(2, bean.getLastName());
			stmt.setString(3, bean.getPhone());
		}
		else{
			stmt.setString(1, bean.getFirstName());
			stmt.setString(2, bean.getLastName());
		}
		
		rs = stmt.executeQuery();
		
		if (rs.next()) {
			Patient newBean = new Patient();
			newBean.setPid(rs.getInt("pid"));
			newBean.setFirstName(rs.getString("firstname"));
			newBean.setLastName(rs.getString("lastname"));
			newBean.setDob(rs.getDate("dob"));
			newBean.setPrimaryDoc(rs.getString("primarydoc"));
			newBean.setPhone(rs.getString("phone"));
			newBean.setAddress(rs.getString("address"));
			newBean.setCity(rs.getString("city"));
			newBean.setState(rs.getString("state"));
			newBean.setZip(rs.getString("zip"));
			numOfObject++;
			return newBean;
		} else {
			System.out.println("unable to retrieve patient info");
			return null;
		}
	}
	
	private static Employee getEmployee(Employee bean, PreparedStatement stmt, ResultSet rs) throws SQLException{
		
		stmt.setString(1, bean.getUsername());
		rs = stmt.executeQuery();
		
		if (rs.next()) {
			Employee newBean = new Employee();
			newBean.setName(rs.getString("name"));
			newBean.setUsername(rs.getString("username"));
			newBean.setPass(rs.getString("pass"));
			newBean.setDob(rs.getDate("dob"));
			newBean.setPhone(rs.getString("phone"));
			newBean.setAddress(rs.getString("address"));
			newBean.setEmail(rs.getString("email"));
			newBean.setPosition(rs.getString("position"));
			return newBean;
		} else {
			System.out.println("unable to retrieve Employee info");
			return null;
		}
	}
	
	private static Drug getDrug(Drug bean, PreparedStatement stmt, ResultSet rs) throws SQLException{
		
		if(bean.getDrugId()!=0)
			stmt.setInt(1, bean.getDrugId());
		else
			stmt.setString(1, bean.getDrugName());
		
		rs = stmt.executeQuery();
		if (rs.next()) {
			Drug newBean = new Drug();
			newBean.setDrugId(rs.getInt("drugid"));
			newBean.setDrugName(rs.getString("drugname"));
			newBean.setDescription(rs.getString("description"));
			newBean.setQuantity(rs.getInt("quantity"));
			newBean.setControlFlag(rs.getBoolean("controlflag"));
			newBean.setSideEffect(rs.getString("sideeffect"));
			return newBean;
		} else {
			System.out.println("unable to retrieve drug info");
			return null;
		}
	}
	
	private static Prescription getPrescription(Prescription bean, PreparedStatement stmt, ResultSet rs) throws SQLException{
		
		if(bean.getPrescriptionID()!=0)
			stmt.setInt(1, bean.getPrescriptionID());
		else{
			stmt.setInt(1, bean.getPid());
			stmt.setInt(2, bean.getDid());
		}
		rs = stmt.executeQuery();
		if (rs.next()) {
			Prescription newBean = new Prescription();
			newBean.setPrescriptionID(rs.getInt("presc_id"));
			newBean.setStartDate(rs.getDate("start_date"));
			newBean.setThisDay(rs.getDate("this_day"));
			newBean.setDose(rs.getString("dose"));
			newBean.setQuantity(rs.getInt("quantity"));
			newBean.setRefill(rs.getInt("refill"));
			newBean.setDid(rs.getInt("did"));
			newBean.setPid(rs.getInt("pid"));
			return newBean;
		} else {
			System.out.println("unable to retrieve prescription info");
			return null;
		}
	}
	
	private static Schedule getSchedule(Schedule bean, PreparedStatement stmt, ResultSet rs) throws SQLException{
		
		stmt.setDate(1, bean.getWorkDay());
		stmt.setString(2, bean.getUsername());
		
		rs = stmt.executeQuery();
		if (rs.next()) {
			Schedule newBean = new Schedule();
			newBean.setWorkDay(rs.getDate("work_day"));
			newBean.setFrom(rs.getTime("work_from"));
			newBean.setTo(rs.getTime("work_till"));
			newBean.setHourRate(rs.getBigDecimal("hourly_rate"));
			newBean.setVacationDays(rs.getInt("vac_days"));
			newBean.setUsername(rs.getString("username"));
			return newBean;
		} else {
			System.out.println("unable to retrieve schedule info");
			return null;
		}
	}

	/**
	 * This method checks bean instance and calls corresponding modify method
	 * @param bean holds 1 row of the given table
	 * @param field holds column name to be modified
	 * @return true if row has been modified successfully
	 */
	public static boolean modifyRow (Object bean, String field) {
		
		PreparedStatement stmt = null;
		
		try{
			if(bean instanceof Patient){
				return modifyPatient((Patient)bean, stmt, field);
			}
			if(bean instanceof Employee){
				return modifyEmployee((Employee)bean, stmt, field);
			}
			if(bean instanceof Drug){
				return modifyDrug((Drug)bean, stmt, field);
			}
			if(bean instanceof Prescription){
				return modifyPrescription((Prescription) bean, stmt, field);
			}
			if(bean instanceof Schedule){
				return modifySchedule((Schedule)bean, stmt, field);
			}
		}catch(SQLException e){
			System.out.println("SQLException within modifyRow()");
			System.out.println(e);
		}finally{
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					System.out.println("unable to close stmt within modifyRow()");
					e.printStackTrace();
				}
		}
		return false;
	}
	
	/*handle the delete row in one of the following insert methods*/
	private static boolean modifyPatient(Patient bean, PreparedStatement stmt, String field) throws SQLException{
		
		String sql = "UPDATE patients SET "+field+"= ? WHERE pid = ?";
		
		stmt = conn.prepareStatement(sql);
		
		if(field.toLowerCase().equals("primarydoc"))
			stmt.setString(1, bean.getPrimaryDoc());
		if(field.toLowerCase().equals("phone"))
			stmt.setString(1, bean.getPhone());
		if(field.toLowerCase().equals("address"))
			stmt.setString(1, bean.getAddress());
		if(field.toLowerCase().equals("city"))
			stmt.setString(1, bean.getCity());
		if(field.toLowerCase().equals("state"))
			stmt.setString(1, bean.getState());
		if(field.toLowerCase().equals("zip"))
			stmt.setString(1, bean.getZip());
		stmt.setInt(2, bean.getPid());
			
		int affected = stmt.executeUpdate();
		if (affected == 1) {
			System.out.println("patient info updated");
			return true;
		} else {
			System.out.println("error: no update applied");
			return false;
		}
	}
	
	private static boolean modifyEmployee(Employee bean, PreparedStatement stmt, String field) throws SQLException{
		
		String sql = "UPDATE employees SET "+field+"= ? WHERE username = ?";
		stmt = conn.prepareStatement(sql);
		
		if(field.toLowerCase().equals("pass"))
			stmt.setString(1, bean.getPass());
		if(field.toLowerCase().equals("phone"))
			stmt.setString(1, bean.getPhone());
		if(field.toLowerCase().equals("address"))
			stmt.setString(1, bean.getAddress());
		if(field.toLowerCase().equals("email"))
			stmt.setString(1, bean.getEmail());
		if(field.toLowerCase().equals("position"))
			stmt.setString(1, bean.getPosition());
		stmt.setString(2, bean.getUsername());
			
		int affected = stmt.executeUpdate();
		if (affected == 1) {
			System.out.println("employee info updated");
			return true;
		} else {
			System.out.println("error: no update applied");
			return false;
		}
	}
	
	private static boolean modifyDrug(Drug bean, PreparedStatement stmt, String field) throws SQLException{
		
		String sql = "UPDATE drugs SET "+field+"= ? WHERE drugname = ?";
		stmt = conn.prepareStatement(sql);
		
		if(field.toLowerCase().equals("description"))
			stmt.setString(1, bean.getDescription());
		if(field.toLowerCase().equals("quantity"))
			stmt.setInt(1, bean.getQuantity());
		if(field.toLowerCase().equals("sideefect"));
			stmt.setString(1, bean.getSideEffect());
		stmt.setString(2, bean.getDrugName());
			
		int affected = stmt.executeUpdate();
		if (affected == 1) {
			System.out.println("employee info updated");
			return true;
		} else {
			System.out.println("error: no update applied");
			return false;
		}
	}
	
	private static boolean modifyPrescription(Prescription bean, PreparedStatement stmt, String field) throws SQLException{
		
		String sql = "UPDATE prescriptions SET "+field+"= ? WHERE presc_id = ?";
		stmt = conn.prepareStatement(sql);
		
		if(field.toLowerCase().equals("dose"))
			stmt.setString(1, bean.getDose());
		if(field.toLowerCase().equals("quantity"))
			stmt.setInt(1, bean.getQuantity());
		if(field.toLowerCase().equals("refill"));
			stmt.setInt(1, bean.getRefill());
		stmt.setInt(2, bean.getPrescriptionID());
			
		int affected = stmt.executeUpdate();
		if (affected == 1) {
			System.out.println("prescription info updated");
			return true;
		} else {
			System.out.println("error: no update applied");
			return false;
		}
	}
	
	private static boolean modifySchedule(Schedule bean, PreparedStatement stmt, String field) throws SQLException{
		
		String sql = "UPDATE prescriptions SET "+field+"= ? WHERE work_day = ? and username = ?";
		stmt = conn.prepareStatement(sql);
		
		if(field.toLowerCase().equals("work_from"))
			stmt.setTime(1, bean.getFrom());
		if(field.toLowerCase().equals("work_till"))
			stmt.setTime(1, bean.getTo());
		if(field.toLowerCase().equals("hourly_rate"));
			stmt.setBigDecimal(1, bean.getHourRate());
		if(field.toLowerCase().equals("vac_day"))
			stmt.setInt(1, bean.getVacationDays());
		
		stmt.setDate(2, bean.getWorkDay());
		stmt.setString(3, bean.getUsername());
			
		int affected = stmt.executeUpdate();
		if (affected == 1) {
			System.out.println("prescription info updated");
			return true;
		} else {
			System.out.println("error: no update applied");
			return false;
		}
	}
	
	/**
	 * This method will be called upon exiting the system only
	 * this method should not be called at all during the execution of the program
	 * because we are using singleton design pattern, once connection is closed, no connection
	 * can be re-established unless completely restart the application
	 */
	public static void closeConnection(){
		
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("error closing connection within process class");
			e.printStackTrace();
		}
	}
}