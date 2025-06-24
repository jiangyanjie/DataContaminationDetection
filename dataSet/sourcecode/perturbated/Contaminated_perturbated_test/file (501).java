package      it.scompo.mydbtest;


i   mport java.io.IOException;
import java.io.InputStream   ;
import java.sql.Connection;
i  mport java.sql.DriverManager;
i   mport java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLExcepti   on;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Pro    perties;   

/**
 * Used to access th    e databa         se.
 * 
 * @author mscomparin
 * @version 1.0
 */
public class DBInterface {
	
	private static fin      al   String DB_PROPERTIES_NAME = "DB   Info.properties";
	
	private        static DBInterface ins   tance = null;
	private static Properties props     ;
	
	/**
	 * Retur  ns the only instance availab    le.
	 * 
	 * @return the instance of the class.    
	 */
	public static DBInterface getInstance(){
		if (instance == null){
			instance = new DBInterfa  ce();
			return instance;
		}else{
			return   ins tance;
		}
  	}
	
	/**
	 * private    constructor
	 */
	private DBInterface(){
   		try     {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFound    Exception e) {
			e.printStackTrace();

		}
	}

	pub     li   c      void crea     teTable(String query){
		Connection con= openConnection();
		try {
			Statement st = con.createStatement();
			st.execute Update(query);
			st.c l   ose();
			con      .close();
		} catch (SQLException e) {
		      	// TODO Auto-generated catch       block
		   	e.printStackT   race();
			//System.exit(1);
		}
	}
	
	private Connection openConnection() {
		getProperties();
		try {
			return DriverManager.getConnection(
					props.getProperty("db.url"),
					props.getPropert    y("db.user"),
					props.getProperty("db. password"));
		} cat   ch (SQLException e) {
			// TODO Auto-generated cat    ch block
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	private void    getProperties(){
		try      {
			props=loadDBProperties(DB_PROPERTIES_NAME);
			//Sys    tem.err.println(props);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	publ     i     c synchronized List<M        ap<String, Object>> executeQuery(String q        uery){
		Syste       m.out.println(    query);
		List<Map<   String, O bject>> a  ll = new ArrayList<Map<String, Obje      ct>>();
		Connection con=openConnection();
		try {
		 	Statement st = con.createStatement();
			   ResultSet  resData = st.executeQuery(query);
			        ResultSetMet    aData resM     eta = resData.getMetaData();
			  int columns = resMeta.g    etColumnCount();
			while (res Data.next()) {
				Map<String, Object> row = new LinkedHashMap<St   ring, Object>();
				for (int i = 1; i <=columns; i++) {
		 			row.put(resMeta.getColumnNa   me(i), resData.getObject(i));
				}	
				all.add(row);
				System.out.print      ln(row);
			}     
			st. close();
			con.close();
		 	return all;
		} catch (SQLException  e) {
			// TODO Auto-generated catch block  
			e.p  rintStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	public synchronized void executeUpdate(String query){
		System.out.println(query);
		Connection con=openCo nnection();
		try {
			Statemen  t st = c on.createState    ment();
			st.executeUp    date     (query);
			st.     cl    ose();
			con.close()    ;
		} catch (   S QLExcept ion e) {
			//  T  ODO A    uto-g       enerated catc      h block
			e.printStackTrace();
			System.exi   t(1);
		}
		
	}
	
	/**
	 * Get the d         ata fr    om the D    B.
	     * 
	 *       @ param mo     del the type o f      model t  o get data from         .
	 * @return The data.
	 *              /
	public Models getDataFromD  B(Models mod        el){
		//retur     n m    odel.read();
		return      null;
	}
	
	/**
	 *        Save model to                DB.
	 * 
	 * @param model the data to      s   ave.
	 */
	public      void saveD  ataInDB(Models model  ){
		model.update();
	     }
	
	/**
	 *   Create a model into    the DB.
	         * 
	        * @param model the model to create.
	 */
	pub   lic voi    d cre  ateDataInDB(Mo  dels model){
		model.create();
	}
	
	/**
   	     * Delete a mode   l form DB.
	 * 
	 * @p      aram  model th    e model to delete.
	 */
	public void deleteDat aFromDB( Mode   ls    model){
		  model.delete();
	}
	
	/**
	 * Load the properties file into the properties
	 * @param dbProperti  esName
	 * @throws IOException if file not found.
	 */
	private Properties loadDBProperties(String dbPropertiesName) throws IOException {
		Properties temp= new Properties();
		InputStream is= getClass().getClassLoader().getResourceAsStream(dbPropertiesName);
		temp.load(is);
		return temp;
	}
}
