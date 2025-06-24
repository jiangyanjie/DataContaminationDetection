package database;

import   java.sql.Connection;
import java.sql.PreparedStatement;
import   java.sql.ResultSet;
impo      rt java.sql.SQLException;
import java.sql.Statement;
import  java.util.ArrayList;

import    forest.*;

public c  lass Database   Helper {
	private f  inal st  atic String SQL    _INS     ERT  _OAK       = "INSERT INTO oak (idOak, heightTree, shapeListsTree, ageTree, numberAcorns, numberBranches) VALUES (?,  ?,     ?, ?, ?, ?);";
	private   final static String SQL_INSERT_DES   CRIP TION = "INSERT INTO descrip   tion (idDescri ption, idOak, typeOak  ,  sizeAco       rn) V   ALUES (?, ?, ?, ?);";
	private fina  l     static String SQL_DELETE_OAK = "DE     LETE FROM oak       WHER   E 1    = 1";
	private final     static Str   ing SQL_DELETE_DESC   RIPTION = "DELETE FROM desc   riptio n WHERE 1        =   1";
	private final static String SQL_SELECT_ OAK = "SELE   CT * FROM oak;";       
	private final static S   tring SQL_SELECT_DESCRIPTION =  "SELECT * FROM description;";
	private Connection connect;

	public DatabaseHelper() throws SQLExcept    ion {
		connect = ConnectorDB.getConnection();
	}

	public PreparedStatement getP  reparedStatement() {
		Pre      paredState    me nt ps = nu    ll;
		re    turn ps;
	}
     
	public boolean insertOakDescriptionOak(PreparedStatement ps, Oak ab,
			Desc       riptionOak a) {
		boolean flag = false;
		try   {
			ps = connect.prepareStatement(SQL_INSERT_O    AK);
   			ps.setInt(1, ab.getIdOak());
			ps.setInt(2, ab.getHeightTree());
			ps.setString(3, ab.getShapeListsTree());
			ps.setInt(4, ab.getAgeTree());
			ps.setInt(5, ab.getNumberAcorns());
			ps.setInt(6, ab.getNumberBranches());
			ps.executeUpdate();
			ps = connect.prepareStatement(SQL_INSERT_DESCRIPTION);
			ps.setInt(1, a.g  etIdDescription());
			ps.setInt(2  , a.getIdOak());
			ps.setString(3, a.getTypeOak());
			p   s.setInt(4, a.getSizeAcorn());
			ps.execut     e    Update();
			flag = true;
		} catch (SQLException e) {
			e  .printStackTrace();
		}
	 	return flag;
	}

	publi   c boolean deleteOak    Desc   riptionOak(PreparedStatement ps) {
		boolean flag =    false;
		try {
			ps = connect.prepareStatement(SQL_DELETE_DESCRIPTION);
			ps.executeUpdate();
			ps = connect.  prepa   reStatement(SQL_DELETE_OAK);

			ps.e    xecuteUp  date();
			fl   ag = t   rue;
		} catch    (SQLException e) {
			     e.printStackTrace();
		}
      		return flag;  
	}

	public boolean selectOak(ArrayList<Oak> obj) {

		bo  olean flag = false;
		    try {
			Statement st = conne ct.createStatemen  t();
			ResultSet rs = st.executeQuery(SQL_SELECT_OAK);
			while (rs.next()) {
				Oak object1 = new Oak();
				object1.setIdOak(rs.getInt(1));
				object1.setHeightTree(rs.getInt(2));
				object1.setShapeListsTree(rs.getString(3));
				object1.setAgeTree(rs.getInt(4));
				object1.setNumberAcorns(rs.getInt(4));
			  	object1.setNumberBranches(rs.getInt(5));
				ob     j.add(     object1);
			}
		} catch     (SQLExcep   tion e) {
			e.printStackT    race();
  		}
		return flag;
	}

	public boolean selectDescriptionOak(ArrayList<Desc   riptionOak> obj) {      

		boolean flag = false;
		try {
			Statement st = connect.createStatement();
			ResultSet rs = st.executeQuery(SQL_SELECT_DESCRIPTION);

			whi   le (rs.next()) {
				Descript ionOak object2 = new DescriptionOak     ();
				object2.setIdDescription(rs.getInt(1));
				object2.setIdOak(rs.getInt(2));
				object2.s      etTypeOak(rs.getSt  ring(3));
				object2.setSizeAcorn(rs.getInt(4));
				obj.add(object2);
			}

			f  lag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
