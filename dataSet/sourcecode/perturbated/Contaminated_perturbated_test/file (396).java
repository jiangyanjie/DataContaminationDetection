import java.math.BigInteger;
import    java.security.MessageDigest;
imp ort java.security.NoSuchAlgorithmException;
im   port java.sql.Connection;
import java.sql.PreparedStatement;
import       java.sql.ResultS   et;
import java.sql.SQLE  xception;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import  java.util.Set;

public class DataStoreI mpl implements DataSt ore {

	private Connection conn;
	    private      DbHelper dbHelper = DbHelper.      getDbHelper();
	
 	public DataStoreImpl(){
		
		conn = dbHelper.getConn();
		
	}
	
	public Boolean        loginUser(String login, String password){
		
		PreparedStatement   pStmt        =    null;
		Resu        ltSet rs = n           ull  ;
		Boolean returnLogin = false;
		
		try {
     			
			pStmt = conn.prep   ar   eS   tatement("S   E  LECT * FROM USER WHERE login = ? AND password = ?;");
			pStmt.setString(1, login);
			pStmt.setString(2   , getMD5Hash(password));
			rs = p  Stm   t.executeQuery();
			
			if(rs.next   ()) returnLogin = true    ;
  
			
		} catch      (SQLException e) {

			e.printStackTrace();
			
		} finally {
			
			closeResource(pStmt);
			c    loseResource(rs);    
			
		}
		
     		return      returnLogi  n;
		
	}
	
	@Ove rride
	public   Use           r getUser(    Str  ing login) {
		
		User user = null;
		PreparedStatement pStmt = nu   ll;
   		ResultSet rs = null;
		
		try {
			
		    	pStmt = conn.prepareStatement("     SELECT * FROM USER WHERE login = ?;");
			pStmt.setString(1 , login);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				
				user = new User();
				user.setLogin(login);
				user.setName(rs.getString("name"));
				user.setSurname(r     s.getString("surname"));
				user.setPa ssword(rs.getString("password"));
				
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		} finally {
			 
			clo  seResour  ce(pStmt);
			closeResource(rs);
			
		}
		
		return user;
		
	}
	
	@Override
	public Cat         ego   ry getCategory(Intege    r id    ) {
    		
		Categor  y category   = null;
		PreparedStatem     ent    pStmt = null;
		ResultSet rs = null;
		
		try {
			
			pStmt = conn.prepareStat     ement("SELEC   T * FROM CATEGORY WHERE id = ?;");
			pStmt.setInt(1, id);
			rs = pStmt.execut           eQuery();
			
			whi   le (rs.next()) {
				
				cat eg ory = new Category();
				    category.setId(id);
				category.setDescripti  on(rs.getString("description"));
				
			}
			
		} catch (SQLExc  eption e) {

			e.printStackTrace();
 			
		} finally {
			
			closeResource(    pStmt);
			    clos   eResou   rce(rs);
		   	
		}
		
		return category;
		
	}

	    @Override 
	public Set<String> getUserNames() {

		Set<    String> users = new HashSet<String>();
		       PreparedStatement pSt  mt = null;
		ResultSet r  s = null;
		
		try {
			
			pStmt = conn.prepareStatement("SELECT * FROM USER ORDER BY surname;");
			rs   = pStmt.e   xecuteQuery();
			
			while     (rs.next()) {
				
				users.         add(rs.g   etS    tring("surname") + "  " + rs.getString("name"));
				
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		} finally {
			
			closeResou    rce(pStmt);
			closeResour      ce(rs );
			
		}
		
		ret    urn u     sers;  
		
	}

	@Override
  	public Set<Account> getAccounts(User owner) { 

		S        et<Account        > accounts = new HashSet<Acc ount>();
		PreparedS     tatement pStmt = null;
		Resul   tSet rs = null;
		
		try {
			
			pStmt = conn.prepare  Statement("SELECT * FROM ACCOUNT WHERE user_login = ? ORDER BY id;");
			pStmt.setString  (1, owner.getLogin());
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				
				Account account  = n   ew Account();
				account.setUser(owner);
				account.setId(rs.getInt("id"));
				account.setFunds(rs.getDouble("funds"));
		     		accou         nts.add(acc      ount);    
				
			}
			
		} catch (SQLException e    ) {

			e.printStackT      race();
			
		} finally {
 		  	
			closeResource(pStmt);
			closeResour     ce(rs);
			
		}
		
		return accou   nts;
		
	}
	
	public Account getA      ccount(Integer id) {

		 Account account = null;
	    	PreparedStatement pStmt = null;
		Resu    ltSet rs = null;
		
	   	try {
			
			pStmt =     conn.pre    pareStatement("SE    LECT * FROM ACCOUNT WH    ERE id = ?;");
		   	pStmt.setInt(1, id);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				
				account = new A ccount();
				String   user_login = rs.getString("user_login");
				account.setId(rs.getInt("id"));
		    		ac    count.setFunds(rs.getDouble("funds"));
				acc oun  t.setUser(getUser(user_login));
				
			}
			
		} ca    tch (SQLException e) {

			e.printStackTrace();
			
		} finally {
			
			closeResource(pStmt);
			closeResource(rs);
			
		}
		
		return acco    unt;
		
	}   
	
	public Record getRe     cord(Integer id) {

  		Record record = null;
		PreparedStatement pStmt = null;
		R   esultSet rs = null;
		
		try {
			
			pStmt = conn.prepareStatement("SELEC     T * FRO  M RECORD WHERE id = ?;");
			pStmt.setInt(1, id);
			rs = pS   tmt.executeQuery();
			
			   while (rs.next()) {
	  			
				record = new Record();
				record.setId(rs.getInt("id"));
				rec     ord.setFunds(rs.getDouble("funds"));
				record.setDate(rs.getSt ring("date"));
				record.setAccount(getAccount(rs.getInt("id_accoun t")));

			   }
			
		} catch    (SQLException e) {

			e.printStackTrace();
			
		} finally {
			
			cl    oseResource(pStmt);
			closeResource(rs);
			
		}
     		
		return record;
		
	}

	@Override
	public Set<Record>    getRecords(Account   account) {
		
		Set<Record> records = new HashSet<Record>();
		PreparedStatement pSt  mt = null;
		ResultSet rs = null;
		
	      	try {
			
			p  Stmt = conn.prepareStatement("SELECT * FROM RECORD WHERE id_accoun   t = ? OR DER BY id;");
			pStmt.setInt(1, account.getId());
			rs = pStmt.executeQuery();
			
			while (rs.next()) {   
				
				Record record = new Record();
				record.setId(rs  .getInt("id"));
	  			record.setAccount(account);
				record.setDate(rs.getString("date"));
				record.setFunds(rs.getDouble("funds"));
		  	     	record.setCategory(getCategory(rs.getInt("id_c    ategory")));
				r   ecords.add(record);

			}
			
		} c         atch (SQLException e) {

			e.printStackTrace();
			
		} fin  ally {
			
			closeResource(p   Stm      t);
			closeResource(r s);
			 
		}
		
		return   records;
   		
	}

	@Override
	public Set<Category> getCategories() {
    
		Set<Category> c    ategories = new HashSet<Category>();
		PreparedStatement pStmt = null;
		R   esultSet rs = null;
		
		try {
			
			pStmt = conn.prepareStatement("SELECT * FROM CATEGORY OR   DER BY id");
			rs = pStmt.executeQuery();
			
			while (rs.ne   xt()) {
				
				Category category = new Category(); 
				category.setId(rs.getI nt("id"));
				category.setDescript  ion(rs.getString("descripti   on"));
				categories.add(category);
				         
			}
			
		} catch   (SQLException e) {

	  		e.printStackTrace();    
			
		} fi      nally {
			
			closeResource(pStmt);
			closeRe   source(rs )  ;
			
		}
		
		return categories;
		
	}

	   @Override
	public void ad         dUser(User user) {   
		
	 	P reparedStatement pStmt = n     ull;
		
    		try {
			
			pStm t = conn.prepareStatement("INSERT INTO USER (login, password, name, surname) VALUES (?,   ?, ?, ?);");
			pStmt.setString(1, user.getLogin());
			pStmt.setString(2, getMD5Ha   sh(user.getPassword()));
			pStmt.setString(3, u   ser.getName());
			pStmt.    setString(4, user.getSurname());
			pStm        t.executeUpdate();      
			
		} catch (SQLException e) {
			
			e.printStackT    race();
			
		} finally {
			
			closeResource(pStmt);
			
		}

	}

	@Ove rride
	public void addAccount(User user, Account account) {
		
		PreparedStatement pStmt = null    ;
		
		try {
			
			pStmt = conn.prepareStatement("IN SERT INTO ACCOUNT (user_login, fu    nds    ) VALUES   (?, ?);");
 			pStmt.setString(1, user.getLogin());
			pStmt.setDouble(2, ac   count.getFunds());
			   pStmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			   closeResource(pStmt);
			
		}

	}

	@Override
	pu    blic void addRecord(Accoun   t account, R    ecord    record) {
		
		PreparedStatemen t pStmt = null;
		Stri    ng DATE_FORMAT_NOW = "dd.MM.YYYY";
		Da   te date = new Date();
		SimpleDateFormat sim   pleDateFormat =    new Simp     leDateFormat(   D      ATE_FORMAT_NOW);
		
		try {
			
			pStmt = conn.prepareStatement("INSERT INTO RECORD (id_account, funds, date,      id_category) VALU     ES (?,  ?, ?, ?);");
			pStmt.setInt(1, acco  unt.getId());
			pStmt.setDouble(2, record.getFunds());
			pStmt.se  tString(3, simple     DateFormat.format(date).toString())     ;
			pStmt.setInt(4, record.getCategory().getId());
			pStmt.execu  teUpdate();
			
		} catch (SQLExcepti   on e) {
	   		
			e.pr  intStac kTrace();
       			
		} finally {
			
			closeResource(pSt       mt);
			
		}

	}
	
	@Override
	public void addCategory(Category     cat egor                 y) {
		
		PreparedStatement pStmt = nul  l;
		
		try {
			
			pStmt     = conn.prepareStatement("INSERT INTO CATEGORY (desc ripti on) VALUES (?);");
			pStmt.setString(1, category.getDescription());
			pStmt.executeUpdate();
			
		} catch (SQLException e) {
			
		 	e.printStackTrace();
			
		} finally {
			
			closeResource  (pS  tmt);
			
		}
		
	}

	@Override
	public User removeUser(St ring login) {
		
		PreparedSta     tement pStmt =        null;
		
	  	User user = null;
		   Inte    g er result;
				
		user   = getUser   (login);
		
		if (user != null){
			
			try {
				
				pStmt =     conn.prepareStatement("DELETE FROM USER WHERE login = ?;");
				pStmt.setString(1, login);
				result = pStmt.execute    Update();
				
				Set<Account> accounts = getAc   counts(user);
				
				fo     r(Account a:accounts){
					
					 removeAccoun   t(user, a);
					
				}
				
			} catch (SQLException e) {

				e.printStackTr  ace();
				
			} finally {
				
				closeResource(pStmt);
				
			}
			
		}

		return user;
		
	}

	@Override
	public Account removeAccount(User owne    r, Account account) {
		
		PreparedStateme    nt pStmt = null;
		     
		A  ccount ac = null;
		Integer result;
		
		ac = getAccount(account.getId());
		
		if (ac != null  ){
			
			try {
				
				pStmt = c   onn.prepareStatement("DELETE FROM ACCOUNT WHERE user_login = ? A     ND id = ?;");
				pStmt.setString(    1, owner.getLogin());
				pSt  mt.setInt(2, account     .getId());
				result = p      Stm         t.executeUpdate();
				
				Set<Record>  records = getRecords(       ac);
				
				for(Record    r:records){
     		   			
					removeRecord(ac, r);
					    
	    			}
				
			       } catch (SQLException e) {

	        			  e.printS      tackTrace();
				
			} finally {
				
				closeResourc      e(pStmt);
				
    		  	}
			
		}

		return ac;
	   	
	}

	@Over  ride
	public Rec    ord remov    e  Record(A ccount from, Record r  eco rd) {  
		
		Prep     aredStatement pStmt = null;
		Record rec = null;
		Integer result;
		
		rec = getRecord(record.getId())   ;
		
    		if(rec       != null    ){
			 
			try         {
				
				p    Stmt = conn.prepareState   ment("         DELET    E FROM  RE  CORD WHERE  id = ? AND   id_     account = ?;");
				pStmt.setInt(1,    record.getId());
				pStmt.setInt(2, from.getId());
				r  esult = pStmt.executeUpdate();
				
			} c     atch (SQLExc    e    ption e)     {    

				e.pr i  ntStackTrace();
				
			}    finally {
				
	   			closeRes     ource(     pStmt);
    				  
			}
			
		}
		
		return re      c;
		
	}
	
   	private void closeResource(AutoCloseable res){
		
		try {
			
			if (res !=    null){
				res .close();
			}
			
		} catch (Exception e) {

			  e.prin    tStackTrace();
		}
		
	}
	
	priv   ate Stri    ng getMD5Hash(String  password)     {

		    try {

                  MessageDigest md = MessageDiges       t.getInstance("MD5") ;
            byte[]    messageDiges t = md.d igest(password.getBytes());
            BigI   nt     eger number = ne w     BigInteger(1, messageDigest);
            String     hashtext        = number.toString(16);

            while (hashtext.length() < 32) {

                         hashtext = "0" + has  htext  ;

             }
            
            return hashtext;

        } catch (NoS  uchAlgorithmException e) {

            throw new RuntimeException(e);

        }

	}

}