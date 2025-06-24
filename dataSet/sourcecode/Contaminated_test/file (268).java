package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import forest.*;

public class DatabaseHelper {
	private final static String SQL_INSERT_OAK = "INSERT INTO oak (idOak, heightTree, shapeListsTree, ageTree, numberAcorns, numberBranches) VALUES (?, ?, ?, ?, ?, ?);";
	private final static String SQL_INSERT_DESCRIPTION = "INSERT INTO description (idDescription, idOak, typeOak, sizeAcorn) VALUES (?, ?, ?, ?);";
	private final static String SQL_DELETE_OAK = "DELETE FROM oak WHERE 1 = 1";
	private final static String SQL_DELETE_DESCRIPTION = "DELETE FROM description WHERE 1 = 1";
	private final static String SQL_SELECT_OAK = "SELECT * FROM oak;";
	private final static String SQL_SELECT_DESCRIPTION = "SELECT * FROM description;";
	private Connection connect;

	public DatabaseHelper() throws SQLException {
		connect = ConnectorDB.getConnection();
	}

	public PreparedStatement getPreparedStatement() {
		PreparedStatement ps = null;
		return ps;
	}

	public boolean insertOakDescriptionOak(PreparedStatement ps, Oak ab,
			DescriptionOak a) {
		boolean flag = false;
		try {
			ps = connect.prepareStatement(SQL_INSERT_OAK);
			ps.setInt(1, ab.getIdOak());
			ps.setInt(2, ab.getHeightTree());
			ps.setString(3, ab.getShapeListsTree());
			ps.setInt(4, ab.getAgeTree());
			ps.setInt(5, ab.getNumberAcorns());
			ps.setInt(6, ab.getNumberBranches());
			ps.executeUpdate();
			ps = connect.prepareStatement(SQL_INSERT_DESCRIPTION);
			ps.setInt(1, a.getIdDescription());
			ps.setInt(2, a.getIdOak());
			ps.setString(3, a.getTypeOak());
			ps.setInt(4, a.getSizeAcorn());
			ps.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteOakDescriptionOak(PreparedStatement ps) {
		boolean flag = false;
		try {
			ps = connect.prepareStatement(SQL_DELETE_DESCRIPTION);
			ps.executeUpdate();
			ps = connect.prepareStatement(SQL_DELETE_OAK);

			ps.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean selectOak(ArrayList<Oak> obj) {

		boolean flag = false;
		try {
			Statement st = connect.createStatement();
			ResultSet rs = st.executeQuery(SQL_SELECT_OAK);
			while (rs.next()) {
				Oak object1 = new Oak();
				object1.setIdOak(rs.getInt(1));
				object1.setHeightTree(rs.getInt(2));
				object1.setShapeListsTree(rs.getString(3));
				object1.setAgeTree(rs.getInt(4));
				object1.setNumberAcorns(rs.getInt(4));
				object1.setNumberBranches(rs.getInt(5));
				obj.add(object1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean selectDescriptionOak(ArrayList<DescriptionOak> obj) {

		boolean flag = false;
		try {
			Statement st = connect.createStatement();
			ResultSet rs = st.executeQuery(SQL_SELECT_DESCRIPTION);

			while (rs.next()) {
				DescriptionOak object2 = new DescriptionOak();
				object2.setIdDescription(rs.getInt(1));
				object2.setIdOak(rs.getInt(2));
				object2.setTypeOak(rs.getString(3));
				object2.setSizeAcorn(rs.getInt(4));
				obj.add(object2);
			}

			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
