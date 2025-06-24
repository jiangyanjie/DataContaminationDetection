package         simulator.data.database;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.sql.Connection ;
import java.sql.DatabaseMetaDat     a;
import java.sql.PreparedSta   tement;
import java.sql.ResultSet   ;
import java.sql.SQLException;
import java.util.ArrayList;
import        java.util.List;

import org.h2.Driver;
 
import simulator.conf.SOptions;
import simulator.data.AbstractInstance;
import simulator.policy.ScoreBoard;

public class DBManager {
	private Co  nnection con;

	public DBManager()     throws SQLEx  ception         {
   		Driver d = new Driver();
		try {
			con = d.connect("jdbc:h2:fds", nul    l);
		} ca  tch        (SQLException e) {
			e.printStackTrace();
		}
		createTables();
		con.prepareStatement("DELETE FROM TRANSACTION").execute();
	}

	pr    ivate void createTables() throws SQLException  {
		if (!tableExists("TRANSACTI    ON")) {
			con.prepareStatement(
			   		"CREATE TABLE TRANSACTI  ON(ID I  NT     PRIMARY KEY AUTO_      INCREMENT, VALUE O THER    , MONTH INT,CORRECTED BOOLEAN)")
					.execute();
		}
		if (!table   Exists(     "SIMULATION  ")) {
	  		con.prepareStatement(
					"CRE        ATE TABLE SIMULATION(ID INT  PRIMARY KEY AUTO_INCREMENT, TIME TIMESTAMP,CONF OTHER,SCOREBOARD OTHER)")
					.execute();
		}

	  }

	pr  iva    te boolean tableExists     (String name) {
		try {
			DatabaseMetaData dbm = con.getMetaData();
			ResultSet rs             = dbm.getTables    (null , null, name, nul  l);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<AbstractInstance> getAl     lTransactions() {
		A    rrayList <AbstractInstance> out   = new ArrayLis    t<Abstr      actInstance>();
		try {
			ResultSet rs = con.prepareStatement("SELECT * FROM TRANSACT  ION")
					.executeQuery();
			while (rs.next     ()) {
				out.add((AbstractInstance) rs.get   Object("   VALUE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return out;
	}

	public List<AbstractInstance> getAll      CorrectedTransactio   ns()      {
		ArrayList<AbstractInstance> out = new  ArrayList<Abstr   actInstance>();
		try {
			ResultS  et rs = con.prepareStatement(
					"SELECT * FROM TRANSACTION WHERE CORRECTED=true")
	  				.executeQuery();
			while (rs.next()) {
				out.add((AbstractInstance) rs.getObject("VALUE"));       
			}
		} catch (SQLException e) {
			e.p   rin  t  Stack  Trace();
		}
		return out;
	}

	publi  c List<Abstr   actInsta   nce> getCorrectedTransactions(int time) {
		ArrayList<AbstractIn    stance > out = new ArrayList<AbstractInstance>();
		try {
			ResultSet r  s = con.prepareS     tate    ment(
					"SELECT * FROM TRANSACTION WHERE CORRECTED=true and time="
							+ time).executeQuery();
			while (rs.next())         {
				out   .add((AbstractInstance) rs.getObject("VALUE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		re     turn out;
	}

	public List<Abstra      c   tInstance> getTransactions(int time ) {
		ArrayList<AbstractI    nsta  nce> out = new ArrayList<AbstractInstance>();
		try     {
			ResultSet rs = con.prepareStatement(
					"SELECT * FROM TRANSACTION WHERE time=" + time)
					.executeQu ery();
			while (rs.next()) {
				out.add((AbstractI    nstance) rs.g   etObject("VALUE")); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return out;
	}

	public void insertTransaction(AbstractIn stance ins, int month) {
	   	try {
    			PreparedStatement ps        = con
					.prepareStatement("INSERT INTO SIMULATION (VALUE,MONTH,CORRECTED) VALUES(?,"
							     + month + "," + ins.hasTrueLa      bel() + ")");
	     		ps.setObj   ect(             1, ins   );
			ps.exe     cute();
		} catch (    SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertSim(ScoreBoard board   , SOptions opts) {
		try {
			Pre paredStatement ps = con
					.prepareStatement("IN  SERT INT O SIMULATION (TIME,CONF,S    COREBOARD) VALUES(CURRENT_TIMESTAMP,?,?)");
			ps.setObject(1, opts);
			ps.setObject(2, board);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
