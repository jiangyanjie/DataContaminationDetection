package database;

import   java.sql.Connection;
import java.sql.PreparedStatement;
impo   rt java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import database.bean.Drug;
import database.bean.Employee;
impo       rt database.bean.Patient;
import database.bean.Prescriptio   n;
import database.bean.Schedule;

/**
 * @auth or An   dy H  siang
 */
p ublic class DatabaseProcess    {
	
	private static Conn     ection     conn = ConnectionManager.getInstance().getConnection();
	
	publ   ic static vo       id displayStart(){
		System.out      .println("logged in!");
 	}
	/**
	 * This meth   od calls di           splay method of the table     based on the bean instance
	 * @param bean
	 * @throw  s SQLException   
	 */
	public static void     display(String  tableName){
		
		Statement st    mt = null;
		ResultSet rs = null;
		String sql;
		
		try{
			stmt= conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet       .CONCUR_READ_ONLY    );
			if(tableName.toLowerCas       e().equals("patients")){
				sql = "  SELECT * FROM PATIENTS";
				  rs = stmt.executeQuery(sql);
				displayPatients(rs);;
			}
			else if(tableName.toLowerCase().equa   ls("employees")){
				sql = "SELECT *  FROM EMPLOYEES";
				rs = stmt.exec  uteQuery(sql);
				displayEmployees(rs);
			}
			else                if(tableName.toLowerCas   e().equals("dru   gs")){
				sql = "SELECT * FROM DRUGS";
				r     s = stmt.executeQuery(sql);
    				displayDrugs(rs);
			}
			else if(tableName.toLowerCase().equals("pres   criptions")){
				sql = "SELECT * FROM PRESCRIPTIONS";
				     rs = stmt.executeQuery(sql);
				displayP   rescriptions(rs);
			}
			else if(tableName.toLowerCase      ().equal s("schedules")){
				sql = "SELECT * FROM SCHEDULES";
				rs = st  mt.executeQuer    y(sql);
				displaySchedules(rs);
			}
		}catch(SQLException e){
			System.out.println("sql exception in display()");
			System.out.println(e);
		}finally{
			try{
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.clo   se();	
			}catch(Exception e){
				System.out.println("error    closing   resource within display()"    );
			}
		}
	}
	
	private static void displayPatients(ResultSet rs)thr         ows SQLException{
		
		String     Buffer bf = new StringBuffer();
		while (rs.next()){
			bf.append(rs.getInt(" pid")+": ");
			bf.append(rs.getString("firstname")+" ");
			bf.appen    d(rs.getString("lastname"));
			System.out.println(bf.toString());
			bf.delete (0, bf.length());
		}
      	}
	
	p  rivate st atic void displayEmp    loyees(ResultSet rs)throws SQLException{ 
		
		StringBuffer bf = new StringBuffer();
		while (rs.next()){
			bf.append(rs.getString("name")+" ");
			bf.append(rs.getString("username")+" ");
			bf.ap      pend(rs.getString("pass")+" ");
			bf.append(rs.getD  ate("dob")+" ");
			bf.append(rs.getString("phone"));
 			System.out.prin      tln(bf     .toString());
			bf.delet   e(0, bf.length());
		}
	}
	   
	private static void displayDrugs(ResultSet rs) throws SQLException{

		StringBuffer bf = new StringBuffer();
		while (rs.next()){
			bf.append(rs.getInt("dr   ugid")+": ");
			bf.append(rs.getString("drugname"));
			Syste m.out.println(bf.toString());
	    		bf.delete(0, bf     .length());
		}
	}
	
	private static void displayPrescriptions(ResultSet rs)throws SQL Exception{
		
		StringBuffer bf = new StringBuffer();
		while (rs.next()){
			bf.append(rs.getInt("presc_id")+": ");
			bf.append(rs.getDate("start_date")+", ");
			bf.append(rs.getD       ate("this_day"));
			System.out.println(bf.toString());
			bf.delete(0, bf.length());
		}
	}
	
	pr ivate static void displaySchedules(ResultSe    t rs)throws SQLException{
		
		StringBuffer bf = new StringBuffer();
		while (rs.next()){
			bf.append(rs.getDate("work_day"   )+", ");
			bf.append(rs.getTime("work_from")+", ");
			bf.append(rs.getTime("work_till")+", ");
			bf.append(rs.getBigD   ecimal(    "hourly_ra te")+", ");
			bf.a    ppend(    rs.getInt("vac_days")+", ");
			bf.ap   pend(rs.getString("username"));
			System.out          .println(bf.toString  ());
			bf.delete(0, bf.length());
		}
	} 
	
	/*** This meth   od    checks the bean ob        ject param and c  all the      correct insert method a    ccord    ingly
  	@pre: bean is pre-initialized with all     fields re   quire     d to successfully inse    rt     a row of data into a given table.
	@post: a ro     w has bee   n successfully inserted into the database in a specifi ed table
	 ***/
	public static  boolean  insert(Object bean) {
		
		String sql;
		PreparedS       tatement stmt  =null;
		
		try{
			if(bean instanceof Pat    ient){
				sql      = "IN       SERT into patients ("+Patient.     getTableSchema()+") " +
					"VALUES (?, ?,    ?,     ?, ?, ?   , ?, ?, ?)";
				stmt=conn.prepareStatement(    sql, Statement.RETURN_GENERATED_KEYS);
				re   turn insertPatient((Pati     ent)bean   , stmt)   ;
	    		}
			if(bean insta nceof Employee){
	    			sql = "INSERT into employee  s (       "+Employ    ee.getTableS     chema()+") " +
						"VALUES (?, ?, ?, ?, ?,       ?, ?, ?)";
				stmt=conn.prepareStatement(sq l);
	 			   ret    urn insertEmploye    e((Empl    oyee)bean, stmt  );
			}
			if(   be    an instanceof Drug){
				sql = "INSERT into drugs ("+Drug.getTableSchema  ()+") " +
						"VALUES (?, ?, ?, ?, ?)";
				stmt=conn.prepareStatem   ent(sql, Statement.RETURN_GENERATED_KEYS);
				re  turn insertDrug((Drug)bean, stmt);
			}
			if(bean inst  anceof Prescription){
				sql = "INSERT into prescr   iption    s ("+Prescription.getTableSchema()+") " +
						"VALUES (?, ?,  ?, ?, ?, ?, ?)";
				stmt=   conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				r        etu   rn insertPrescription((Presc  ription) bean, stmt);
			}
			if(bean instanceof Sch  edule){
				sql = "INSERT into Schedules ("      +Schedul   e.getTa       bleSchema() +") "    +
						"VALUES (?, ?, ?, ?, ?,     ?)";
				stmt=conn.pre  pareStatement(sq     l);
				return ins    ertSchedule((Schedule)bean, stmt);
			}
		}catch(SQLException e){
			Sy   stem.out.println("sql except    ion within insert(  )");
			System.out.println(e);
		}finally{
			if(stm  t !=null    )
				try {
					stm  t.close();
		     		} catch (SQLException e) {
					System.out.println("error when closing stmt within insert()");
					e.printStackTrace();
				}
		}
		return false;
	}
	
	/*handle the insert row in on     e of the following insert methods*/
	private    static boolean    insertPatient(Pati  ent bean, PreparedStatement stmt) throws SQLException{

		stmt.setString(1, bean.getFirs     tName());
		stmt.setString(2,   bean.getLastName());
		stmt.setDate(3, bean.ge  tDob());
		stmt.setString(4, bean.getPrimaryDoc());
		stmt.setString(5, bean.getPhone());
		stmt.setS tring(   6, bean.getAddress());
		stmt.setString(7, bean.getCity());
		stmt.setString(8, b  ean.getState());
		stmt.setString(9, bean.    getZip()    );
		
		int affe cted = stmt.executeUpdate();
		if(affected == 1){
			System.out.println("new pat    ien  t added successfully"   );
		}else{
 			System.out.println("error adding patient");
			return fa  l  se;
		}
		return true;
	}
	
	private static boolean insertEmp    loyee(Employ ee bean  , PreparedStatement stmt) throws SQLException{
		
		stmt.s  etString(1, bean.get  Name());
		stmt.setString(2, bean.getUsername());
	    	stmt.setS   tring(3, bean.getPass());  
		stmt.setDate(4, bean.getDob());
		stmt.setString (5, bean.getPhone());
		stmt.setString(6, bean.getAddress());
		stmt.setString(7, bean   .getEmail());
		stmt.setS  tring(8, bean.getPosition());
		
		int affected = stmt.executeUpdate();
		if(affected == 1){
			System.out.println("new employee added successfully");
		}else{
			System.out.println("error adding employee");
			return false;
		}
		return true;
	}
	
	private  static boolea n      insertDrug(Drug  bean, PreparedStatement stmt) throws SQLException{
		
		stmt.setSt      ring(1, bean.getDrugName());
		stmt.setString(2, bean.getDesc   ription());
		stmt.setInt(3, bean.getQuantity());
		stm    t.setBoolean   (4, bean.isControlFlag());
		stmt.setString(5, bean.getSid  eEffect());

		int affect   ed = stm t.executeUp   date();
		if(affected == 1){
			System.out.println(  "new drug added successfully");
		}el    se{    
			System.out .println("error adding drug");
			return false;
		}
		return true;
	}
	
	       private static boolean insertPrescription(Prescription bean, PreparedStatement stmt)   throws SQLException{
		
		stmt.setDate(1, bean.getStartDay());
		stmt.set   Date(2, bean.getThisDay());
		stmt.setString  (3, bean.getDose());
		stmt.setInt(4    , bean.getQuantity(  ));
		stmt.set      Int(5, bean.getRef  ill());
		stmt.setInt(6, bean.getDid());
		stmt.setInt(7, bean.getPid() );

		int        affected = stmt.executeUpdate();
		     if(aff ected == 1){
			System.out.println("new prescription added successfully");
		}else{
			System.out.prin tln("error         adding presc  ription");
			return false;
		}
		return true;
	}
	
	private static b oolean insertSchedu    le(Schedule bean, PreparedStatement stmt) throws SQLExcep      tion{
		
		stmt.setDate(1, bean.ge tWorkDay());
		    stmt.setTime(2, bean.ge    tFrom());
  		stmt.setTime(3, bean.getTo());
		stmt .setB    igDecimal(4, bean.getHourRate());
		stmt.setInt(5, bean.getVacationDays()      );
		stmt.setString(6, bean.getUsername());

		int affected = stmt.executeUp     date  ();
		if(affected == 1){
			S   ystem.out.println("new schedule adde    d successfully");
		}else{
			System.out  .prin tln("erro  r add  ing schedu    le");
			return false;
		}
		ret   urn true;
	}

	/**
	 * This method checks t   he bean i nstance and call t        he corresponding delete method
	 * @param   bean
	 * @return      true if a row has been successfully rem oved from database
	 */
	public static boolean delete (Object bean) {
		   
		Str   ing sql;
		Prepa    redStatement        stmt=null;
		
		try{
			if(bean instanceof Patient){
				sq  l = "DELETE FROM patients WHE  RE pid   = ?";
				stmt   =     conn.prepareStatement(sql);
				return d   eletePatient((Patient)bean, stmt);
			}
		  	if(bean ins    tanceof Employee){
				sql = "DELETE   F  ROM    employees WHERE username = ?";
				stmt = conn.prepareStatement(sql);
				retu   rn deleteEmployee((Employee)bean, stmt);
       			}
			if(bean instan   ceof Drug){
				sql =  "DELETE FROM    Drugs WHERE drugid =   ?";
    				stmt = conn.prepareStatemen     t(sql);
				return deleteDru     g((Drug)bean, stmt);
			}   
			  if(bean instanceof Prescription){
				  sql = "DELETE FRO   M     Prescriptions WHERE pre sc_id = ?";
				stmt = conn.prepareS   tatement(sql);
				return deletePrescription((Prescription) bean,   stmt);
		  	}
			if(bean instanceof Schedule){
				sql = "DELETE FROM schedu1es WHERE work_day =     ? and username = ?";
				stmt = conn    .prepareS     tatemen    t(   sql);
				return del eteSche    dule((Schedule)b  ean, stmt);
			}
		}catch(SQLException e){
			System.out.println("SQLException     within delete()");
			System.out.println(e);
		}finally{
			if(stmt != null)
				   try {
					stmt.close();
		  		} catch (SQLException e) {
					System.out.println("unable to clos  e stmt w  ithin delete()");
					e.printStackTrace();
				}
		}
		return false;
	}

	/*handle th  e delete row in one of the following insert methods*/
	priv  ate st   atic    boolean deletePatient(Patient bean, PreparedStatement stmt) throws       SQLException{
		
		stmt.      setInt(1, bea   n.getPid());
	      	int affected=stmt.executeUpdate();	
		
		if(affe   cted==1){
			Sy  stem.out.println("Patient  with ID "+bean.      getPid     (  )+"        has been Removed from database.");
		} else {
			                   System.out.print  ln("unable to remove patient from database");
			r          eturn false;
		}	
		retur  n true;
	}
	
	    private     static boolean deleteEmployee(Employee be  an, PreparedStatement stmt) throws SQLException     {
		
		stmt.setString(1, bean.g   etUsername());
		int affected=stmt.executeUpda  te();	
		
		if(affected==1){
			Sys  tem.out.   println("Employee with username "+be   an.getUsername()+" has been Removed from database.     ");
		} else {
			System.out.println("unable to    remove emp   loyee from database");
         			return false;
		}	
		return true;
	}
	
	private static boolean deleteDrug(Drug bean, PreparedStatement stmt) th rows SQLException{
		
		stmt.setInt(1,     bean.getDrugId());
		int affected=stmt.executeUpdate();	
		
		if(affe           cted==1){
			System.out.println("Drug with ID "+bean.getDrugId()+" has been Removed from database.");
		} else {
			System.out.println("unable to remove Drug from database");
			return false;
		}	
		return         true;
	}   
	
	priva te stati  c b   oolean deletePrescription(Pre  sc    ript  i on bean, PreparedStatement stmt) throws SQLExc        eption{
	 	
		stmt.setInt(1, bean.getPrescriptionID());
		int affected=stmt.executeUpdate();	
		
		if(affected==1){
			System.out.println("Prescripti  on with ID "+bean.get    P    rescr      iptionID() +" has b een Removed from databas    e.");
		} else {
			System.out.println("unable to remo  ve Prescrip    tion from database");
			return fal    s    e;
		}	    
		return true;
	}
	
	private static bo   olean deleteSchedule(Schedule bea     n, Prepared   Statement stmt) throws SQLExc    eption{
		
		stmt.s    etDate(1, bean.getWorkDay());
		stmt.set String(2, bean.get Username());
		int affected=stmt.executeU      pdat     e(       );	
		
		if(affected==1){
			System.out.println("Emplo    yee with username "+b   ean.getUsern    ame    ()+" is n o  t working on "+bean.getWorkDay()   );
		} e    lse {
			System.out.print ln("unable to delete     schedule from database");
		 	return false;
		}     	
		r eturn  true ;
	}

	/**
	 * This me  thod checks bean instance an   d call     s corresponding get row metho    d
	 * @param bean
	 * @r      etu  rn ret    ri     eved row represented by the bean instance
	 */
      	public stati   c Object g       etRow (Obj     ect bean) {
		int numOfObject=   0;
		String sql;	
		Prep      aredStatement stmt=null;
		ResultSe       t rs =      null;
		Object returnOb   j = null;
		  try{
			if(bean instanceof Patient){
				if(((Patient)    bean).getPid()!=0)
					sql      = "SELECT * FROM patien   ts WHERE pid = ?";
				els   e if(((Patient) bean).getPhone()!=null)
					sql = "SELECT    * FROM patients WHERE fi         rs   tname = ? AND la  stname = ? and phone = ?";
				else
					sql =    "SELECT * FROM pat  ients WH  ERE fi   rstname = ? AND lastname = ?";
				  stmt = conn.prepareState      ment(sql);
				returnObj = getPatient((Pat  ient)b    ean, stmt, rs, numOfObject);
				if(numOfObjec    t>1) returnObj=null;
			}
			if(bean instan    ceof Employee){
				sql = "SELEC T * FROM e  mployees WHERE username = ?";   
				stmt = conn.prepareStatement(sql   );
				returnObj = getEmployee((Employee)b  ean, stmt, rs);
			}
			if(bean instan   ceof Drug){
				if(((Drug) bean).getDrugId()!=0)
					sql = "SEL  ECT * FROM drugs WHERE drugid = ?";
				els  e
					sql = "SELEC    T * FROM drugs W   HERE drugna me = ?";
				stmt = conn.prepareStatement(sql); 
				returnObj = getDrug((Dr  ug)bean, stmt, rs); 
			}
			if(bean instanceof Prescription){
				if(((   Prescript       ion) bean).getPrescriptionID()!  =0)
					sql =   "SELECT *    FROM prescription    s WHERE presc_id = ? ";
				els e
      					sql = "SELE CT * FROM prescriptions WHERE pid = ? and did = ?"     ;
				stmt = co nn.prepareStatement(sql);
				returnObj = getPrescription((Presc ription) bean, stmt, rs);
			}
			if(bean instanceof Schedule){
				sql = "SELECT * FROM schedules WHERE work_day = ? and username = ?";
				stmt =   conn.prepareStatement(sql);  
				retur   nObj = getSchedule((Schedule)bean, stmt, rs);
			}
		}catch(SQLException e){
			System.out.println("sql erro  r withing getRow()");
			System.ou  t.println(e);
		}finally{
			try{
				if(rs!=null)
					rs.c  lose();
				if(stmt!=null)
					stmt.close();
			} catch (SQLException     e) {
				S  ystem.out.println("error closing resource withing getRow()");
				e.printStackTrace();
			}    
		}
		if(returnObj != null)
			retur  n returnObj;
		else
			return null;
	}

	/*handle the get row in one of the following insert methods*/
	private   static Patient getPatient(Patient bean, PreparedStatement stmt, ResultSet rs, int numOfObject) throws SQLException{
		
	    	if(bean.getPi    d()!=0)
			  stmt.setInt(1, bean.     getPid());
		else if(bean.getPhone()!=null){
			stmt.setString    (1, bean.getF   irstName());
			stmt.setString(2, bean.getLastName());
			stmt.setString(3, bean.getPhone())     ;
		}
		else{
			stmt.setString(1, bean.getFirstName());
		  	stmt.setString(2, bean.getLastName()      );
		}
		
		rs = stmt.executeQuery();
		
		if (rs.next()) {
			Patient newBean = new Patient();
			newBean.setPid(rs.getInt("pid"));
			newBean.setFirstName(rs.getString("firstname"));
			newBean.setLastName(rs.getString("l    astname"));
			newBe an.setDob(rs.getDate("dob"));
			newBean.setPrimaryDoc(rs.getString("primarydoc"));
			newBean.setPhone(rs.getString("phone"));
			newBean.setAddr ess(rs.getString("address"))    ;
			n ewBean.setCity(rs.getStri   ng("city"));
			newBean.setState(rs.getString("state"));
			newBean.setZip(rs.getString("zip"));
			numOfObject++;
			return newBean;
		} else {
			System.out.println("unable to retrieve patient info");
			ret urn null;  
		}
	}
	
	private static Employee getEmployee(Employee bean, PreparedStatement stmt, ResultSet rs) t  hrows S      QLException{
		
		stmt.setString(1, bean  .getUsername());
		rs = stmt.executeQuery();
		       
		if (rs.next()) {
    			Employee newBean = new Employee();
			newB      ean.setName(rs.getSt   ring("name"));
			newBean.setUsername(rs.getString("username"));
			newBean.setPass(rs.getString("pass"));
			newBean.setDob(rs.getDate("dob"));
			newBean.setPhone(rs.getString("phone"));
			newBean.setAddress(rs.getString("addr ess"));
			newBean.setEmail(rs.getString("email"));
			newBean      .setPosition(rs.getString("position"));
			return newBean;
		} else {
			System.out.println("unable to retrieve Employee info");
			return null;
		}
	}
    	
	private static Drug getDrug(Drug    bean, Prepar   edStateme  nt stmt, ResultSet rs   ) throws SQLException{
		
		i    f         (bean.getDrugId()!=0)
			stmt.setInt(1, bean.getDrugId());  
		else
			stmt.setString(1, bean.ge   tDrugName());
		
		rs = stmt.      executeQuery();
		if (rs.next()) {
			Drug newBean = new Drug();
			newBean.s  etDrugId(rs.getInt("dru    gid  "));
			newBean.setDrugName(r s.getString("drugn ame"));
			newBean.setDescription(rs.getString("description"));
  			newBean.setQuantity(rs.getInt("quantity"));
			newBean.setControlFlag(rs.getBoolean("controlflag"));
			newBean.setSideEffect(rs.    getString("sideeffect"));
			return newBean;
		} el   se {
			System.out.println("unable to retrieve drug info");
			return null;
  		}
	}
	
	private static Prescription getPrescription(Prescri  ptio       n bean, PreparedStatement stmt, ResultSet rs) throws SQLException{
		
		if(bean.g etPrescriptionID()!=0)
			stmt.setInt(1, bean.getPrescriptionID());    
		else{
			stmt.setInt(1, bean.getPid());
			   stmt.setInt(2, bean.getDid());
		}
		rs = stmt.executeQuery(        );
		if (rs.    next()) {
			Prescription newBean = new Prescription();
			newBean.set    PrescriptionID(rs.getInt("presc_id"));
			newBean.setStartDate(rs      .getDate("start_date"));
			new Bean.setThisDay(rs.getDate("this_day"));
			n  ewBean.setDose(rs.getString("dose"));
			newBean.setQuantity(rs.g  etInt("quan   tity"));
			newBean.setRefill(rs.getInt("refill"));
			newB  ean.se    tDid(rs.getInt("did"));
			newBean.setPid(rs.getInt("pid"));
			return newBean;
		} e  lse {
			System.out.println("unable to retrieve prescription info");
			return null;
		}
	}
	
	private static Schedule getSche      dule(   Schedu le bean, PreparedStatement stmt, ResultSet rs) throws SQLException{
		
		  stmt.setDate(1, bean.getWorkDay());
		stmt.setString(2  , bean.getUsername());
		  
		rs = stmt.executeQuery();
		if (rs.next()) {
			Sch  edule newBean        = new Schedule();
			newBean.setWorkDay(rs.getDate("work_day" ));
			newBean.setFrom(rs.ge    tTime("work_from"));
			newBean.setTo(rs.getTime("work_till"));
			newBea n.setHourR    ate(rs.getBig     Decimal("hourly_rate"));
			newBean.setVacationDays(rs.getInt("vac_day  s"));
			newBean.setUsername(rs.g      etString(      "username"));
			return newBean;
		} els  e {
			System.out.println("unable to retrieve schedule info");
			return  null;
		}
	}

	/**
	 * Thi  s method checks bean insta  nce and calls co rresponding m   od     ify method
	 * @param bean holds 1 row of the given ta  ble
	 * @param fie      ld hold   s column name to be modified
	 * @return true if ro w has been modified su     ccessfully
 	 */
	publ  ic static boolean modifyRow (   Object bea     n, String field) {
		
		PreparedStatement stmt      =    null;
		
		try{
 			if(bean instanceof Patient){
				return modifyPatient((Patient)bean, s  tmt, field);
			}
			if(bean instanceof Employee){
				return modifyEmployee((Employee)bean, stmt, field);
			}
	        		if(bean instanceof Drug){
				retu       r   n modifyDrug((Drug)bean,   stmt, fi     eld);
			}
			if(bean instanceof Pr escription){
				return modifyPrescription    ((Prescription) bean, stmt, f    ield);
			}
			if(bean instanceof Schedule){
				return modifySchedule((Schedu   le)bean, stmt, field);
			}
		 }catch(SQLException e)  {
			System.out.println("SQ    LException within modifyRow()");
			System.out.p    rintln(e);
		}finally{
			i    f(stmt != null)
	    			try {
					stmt.close();
	   			} catch (SQLException   e)  {
					System.out.println("unable to close stmt within modifyRow()");
					e.printStackTrace();
				}
		}
		return false;
	}
	
	/*handle the delete row in o  ne o  f the foll    owing insert methods*/
	private static boolean modifyPatient(Patient bean, Prepa  red   Statement stmt, String field) throws SQLException{
		
		S    tring sql = "   UPDATE patients   SET "+field+"= ? WHERE pid = ?";
		
		stmt = conn.prepareStatement(s  ql);
		
		if(field.toLowerCase().equals("pri     mar ydoc"))
			stmt.setString(1, bean.getPrimaryDoc());
		if(field.toLowerCase().equals("phone"))
	  		stmt.setString(1, bean.getPhone());
 		    if(field.toLowerCase().equals("address"))
			stmt.setString(1, bean.getAddress());
		if(field.toLowerCase().equals("city"))
			stmt.setString(1, bean.getCity());
		if(field.toLowerCase().equals("state"))
			stmt.setS tring(1, bean.getState());
		if(field.toLowe    rCase().equals("zip"))
			stmt.setString(1, bean.getZip());
		stmt.setInt(2, bean.getPid());
			
		int affected = stmt.executeUpdate();
		if (affected == 1) {
			System.out.println("patien    t inf o updated");
			return true;
 		} else {
			System.out.prin        tln("error: no update        applied");
			return false;
		}
	}
	
	private   static boolean modifyEmploye  e(Employee bean, PreparedStatement stmt, String field) throws SQLException{
		
		String sql = "UPDATE employ   ees SET "+fie l        d+"=     ? W    HERE username = ?";
		stmt = conn.prepareState    ment(sql);
		
		if(field.toLowerCase().equals("pass"))
			stmt.setString(1, bean.getPass());
		if(field.toLowerC   ase().equals("phone"))
			stmt.setString(1, bean.getPhone());
		if(field.toLowerCase().equals("address"))
			stmt.setString(1, bean.getAddress());
		if(field.toLowerCase().equals("email"))
			st  mt.setString(1   , bean .get Email   ()) ;
		if(field.toLowerC  ase().equals("positio n"))
			       stmt.setStri  ng(1, bean.getPosition());
		stmt.setStrin     g(2, bea    n.getUsername())          ;
			
		int affect   ed = stmt.executeUpdate();
		if (affected == 1) {
			System.out.println("employee info updated");
			   retu   rn true;
		} else {
			System.out.println("error: no update applied");
			r    eturn false;
		}
	}
	
	private static boolean modifyDrug(Drug bean, P   reparedStatement stmt, String field) throws SQLException{
		
		String     sql = "UPDATE drugs SET "+field+"= ? WHE    RE drugname = ?";
		stmt = conn.prepareStatement(sql)   ;
		
		if(field.toLowerCase().equals("description")) 
			stmt.setString(1, bean.getDescription());
		if(field.toLowerCase().equals("quantity"))
			stmt.setInt(1, bean.getQuantity());
		if(field.toLowerCase().equals("sideefect"));
			stmt.setString(1, bean.getSideEffect());
		stmt.setString(2, bean.getDrugName());
			
		int affected = s tmt.executeUpdate();
		if (affected ==   1) {
			System.out.println("employee info   updated");
			return t   rue;
		} else {
			System.out.println("error: no update applied");
			return false;
		}
	}
	
	private stat    ic boolean modifyPrescription(Pr    escription bean, Prepa   redStatement stmt,    String field) t  hrows SQLException{
		
		String sql =    "UPDATE prescriptio ns SE    T    "+field   +"= ? WHERE presc_id = ?";
		stmt = conn.prepareStatement(sql);
		
		if(field.toLowerCase().equals("dose"))
			stmt.setString(1,     bean.getDose());
		if(field.toLowerCase(     ).equals("quantity"))
			stmt.setInt(1, bean.getQuantity());
		if(fie ld.to  LowerCase().equals("refill"));
			    stmt.setInt(1, bean.getRefill());
		stmt.setInt(2, bean.getPre script   ionID());
			
		int affected = stmt.executeUpdate();
		if (affected == 1) {
			System.out.println("prescription info updated");    
			return true;
		} else {
			System.out.println("error: no update applied");
			return fal   se;
		}
	}
	
	private static boolean modifySchedule(Schedule bean, PreparedStatement stmt, String field) throws SQLExcept    ion{
		
		String   sq  l = "UPDATE prescriptions SET "+field +"= ? WHERE work_day = ? and username = ?" ;
		stmt = conn.prepareStatement(sql);
		
		if(field.toLowerCase().equals("work_from"))
			stmt.setTime(1, bean.getFrom());
		if(field.toLowerCase().equals("work_till"))
			stmt.setTime(1, bea     n .getTo());
		if(field.toLowe   rCase().equals("hourly_rate"));        
			stmt.setBi          gDecimal(1, bean.getHourRate());
		if(field.toLowerCase().equals("vac_day"))
			stmt  .setInt(1, bean    .getVacationDays());
		     
		stmt.setDate(2, bean. getWorkDay());
		stmt.setString(3, bean.getUsername());
			
		int affected = stmt.executeUpdate();
		 if (affected == 1) {
			System.out.println("prescription info updated");
			return tr   ue;
		} else {
			System.out.println("error: no update applied");
			return false;
		}
	}
	
	/**
	 * Th   is method will be called upon exiting the system o    nly
	 * this method should not be ca     lled at all during the execution of the program
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