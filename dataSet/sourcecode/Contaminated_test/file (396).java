import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DataStoreImpl implements DataStore {

	private Connection conn;
	private DbHelper dbHelper = DbHelper.getDbHelper();
	
	public DataStoreImpl(){
		
		conn = dbHelper.getConn();
		
	}
	
	public Boolean loginUser(String login, String password){
		
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		Boolean returnLogin = false;
		
		try {
			
			pStmt = conn.prepareStatement("SELECT * FROM USER WHERE login = ? AND password = ?;");
			pStmt.setString(1, login);
			pStmt.setString(2, getMD5Hash(password));
			rs = pStmt.executeQuery();
			
			if(rs.next()) returnLogin = true;

			
		} catch (SQLException e) {

			e.printStackTrace();
			
		} finally {
			
			closeResource(pStmt);
			closeResource(rs);
			
		}
		
		return returnLogin;
		
	}
	
	@Override
	public User getUser(String login) {
		
		User user = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		
		try {
			
			pStmt = conn.prepareStatement("SELECT * FROM USER WHERE login = ?;");
			pStmt.setString(1, login);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				
				user = new User();
				user.setLogin(login);
				user.setName(rs.getString("name"));
				user.setSurname(rs.getString("surname"));
				user.setPassword(rs.getString("password"));
				
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		} finally {
			
			closeResource(pStmt);
			closeResource(rs);
			
		}
		
		return user;
		
	}
	
	@Override
	public Category getCategory(Integer id) {
		
		Category category = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		
		try {
			
			pStmt = conn.prepareStatement("SELECT * FROM CATEGORY WHERE id = ?;");
			pStmt.setInt(1, id);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				
				category = new Category();
				category.setId(id);
				category.setDescription(rs.getString("description"));
				
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		} finally {
			
			closeResource(pStmt);
			closeResource(rs);
			
		}
		
		return category;
		
	}

	@Override
	public Set<String> getUserNames() {

		Set<String> users = new HashSet<String>();
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		
		try {
			
			pStmt = conn.prepareStatement("SELECT * FROM USER ORDER BY surname;");
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				
				users.add(rs.getString("surname") + " " + rs.getString("name"));
				
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		} finally {
			
			closeResource(pStmt);
			closeResource(rs);
			
		}
		
		return users;
		
	}

	@Override
	public Set<Account> getAccounts(User owner) {

		Set<Account> accounts = new HashSet<Account>();
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		
		try {
			
			pStmt = conn.prepareStatement("SELECT * FROM ACCOUNT WHERE user_login = ? ORDER BY id;");
			pStmt.setString(1, owner.getLogin());
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				
				Account account = new Account();
				account.setUser(owner);
				account.setId(rs.getInt("id"));
				account.setFunds(rs.getDouble("funds"));
				accounts.add(account);
				
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		} finally {
			
			closeResource(pStmt);
			closeResource(rs);
			
		}
		
		return accounts;
		
	}
	
	public Account getAccount(Integer id) {

		Account account = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		
		try {
			
			pStmt = conn.prepareStatement("SELECT * FROM ACCOUNT WHERE id = ?;");
			pStmt.setInt(1, id);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				
				account = new Account();
				String user_login = rs.getString("user_login");
				account.setId(rs.getInt("id"));
				account.setFunds(rs.getDouble("funds"));
				account.setUser(getUser(user_login));
				
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		} finally {
			
			closeResource(pStmt);
			closeResource(rs);
			
		}
		
		return account;
		
	}
	
	public Record getRecord(Integer id) {

		Record record = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		
		try {
			
			pStmt = conn.prepareStatement("SELECT * FROM RECORD WHERE id = ?;");
			pStmt.setInt(1, id);
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				
				record = new Record();
				record.setId(rs.getInt("id"));
				record.setFunds(rs.getDouble("funds"));
				record.setDate(rs.getString("date"));
				record.setAccount(getAccount(rs.getInt("id_account")));

			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		} finally {
			
			closeResource(pStmt);
			closeResource(rs);
			
		}
		
		return record;
		
	}

	@Override
	public Set<Record> getRecords(Account account) {
		
		Set<Record> records = new HashSet<Record>();
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		
		try {
			
			pStmt = conn.prepareStatement("SELECT * FROM RECORD WHERE id_account = ? ORDER BY id;");
			pStmt.setInt(1, account.getId());
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				
				Record record = new Record();
				record.setId(rs.getInt("id"));
				record.setAccount(account);
				record.setDate(rs.getString("date"));
				record.setFunds(rs.getDouble("funds"));
				record.setCategory(getCategory(rs.getInt("id_category")));
				records.add(record);

			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		} finally {
			
			closeResource(pStmt);
			closeResource(rs);
			
		}
		
		return records;
		
	}

	@Override
	public Set<Category> getCategories() {

		Set<Category> categories = new HashSet<Category>();
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		
		try {
			
			pStmt = conn.prepareStatement("SELECT * FROM CATEGORY ORDER BY id");
			rs = pStmt.executeQuery();
			
			while (rs.next()) {
				
				Category category = new Category(); 
				category.setId(rs.getInt("id"));
				category.setDescription(rs.getString("description"));
				categories.add(category);
				
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		} finally {
			
			closeResource(pStmt);
			closeResource(rs);
			
		}
		
		return categories;
		
	}

	@Override
	public void addUser(User user) {
		
		PreparedStatement pStmt = null;
		
		try {
			
			pStmt = conn.prepareStatement("INSERT INTO USER (login, password, name, surname) VALUES (?, ?, ?, ?);");
			pStmt.setString(1, user.getLogin());
			pStmt.setString(2, getMD5Hash(user.getPassword()));
			pStmt.setString(3, user.getName());
			pStmt.setString(4, user.getSurname());
			pStmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			closeResource(pStmt);
			
		}

	}

	@Override
	public void addAccount(User user, Account account) {
		
		PreparedStatement pStmt = null;
		
		try {
			
			pStmt = conn.prepareStatement("INSERT INTO ACCOUNT (user_login, funds) VALUES (?, ?);");
			pStmt.setString(1, user.getLogin());
			pStmt.setDouble(2, account.getFunds());
			pStmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			closeResource(pStmt);
			
		}

	}

	@Override
	public void addRecord(Account account, Record record) {
		
		PreparedStatement pStmt = null;
		String DATE_FORMAT_NOW = "dd.MM.YYYY";
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_NOW);
		
		try {
			
			pStmt = conn.prepareStatement("INSERT INTO RECORD (id_account, funds, date, id_category) VALUES (?, ?, ?, ?);");
			pStmt.setInt(1, account.getId());
			pStmt.setDouble(2, record.getFunds());
			pStmt.setString(3, simpleDateFormat.format(date).toString());
			pStmt.setInt(4, record.getCategory().getId());
			pStmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			closeResource(pStmt);
			
		}

	}
	
	@Override
	public void addCategory(Category category) {
		
		PreparedStatement pStmt = null;
		
		try {
			
			pStmt = conn.prepareStatement("INSERT INTO CATEGORY (description) VALUES (?);");
			pStmt.setString(1, category.getDescription());
			pStmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			closeResource(pStmt);
			
		}
		
	}

	@Override
	public User removeUser(String login) {
		
		PreparedStatement pStmt = null;
		
		User user = null;
		Integer result;
				
		user = getUser(login);
		
		if (user != null){
			
			try {
				
				pStmt = conn.prepareStatement("DELETE FROM USER WHERE login = ?;");
				pStmt.setString(1, login);
				result = pStmt.executeUpdate();
				
				Set<Account> accounts = getAccounts(user);
				
				for(Account a:accounts){
					
					removeAccount(user, a);
					
				}
				
			} catch (SQLException e) {

				e.printStackTrace();
				
			} finally {
				
				closeResource(pStmt);
				
			}
			
		}

		return user;
		
	}

	@Override
	public Account removeAccount(User owner, Account account) {
		
		PreparedStatement pStmt = null;
		
		Account ac = null;
		Integer result;
		
		ac = getAccount(account.getId());
		
		if (ac != null){
			
			try {
				
				pStmt = conn.prepareStatement("DELETE FROM ACCOUNT WHERE user_login = ? AND id = ?;");
				pStmt.setString(1, owner.getLogin());
				pStmt.setInt(2, account.getId());
				result = pStmt.executeUpdate();
				
				Set<Record> records = getRecords(ac);
				
				for(Record r:records){
					
					removeRecord(ac, r);
					
				}
				
			} catch (SQLException e) {

				e.printStackTrace();
				
			} finally {
				
				closeResource(pStmt);
				
			}
			
		}

		return ac;
		
	}

	@Override
	public Record removeRecord(Account from, Record record) {
		
		PreparedStatement pStmt = null;
		Record rec = null;
		Integer result;
		
		rec = getRecord(record.getId());
		
		if(rec != null){
			
			try {
				
				pStmt = conn.prepareStatement("DELETE FROM RECORD WHERE id = ? AND id_account = ?;");
				pStmt.setInt(1, record.getId());
				pStmt.setInt(2, from.getId());
				result = pStmt.executeUpdate();
				
			} catch (SQLException e) {

				e.printStackTrace();
				
			} finally {
				
				closeResource(pStmt);
				
			}
			
		}
		
		return rec;
		
	}
	
	private void closeResource(AutoCloseable res){
		
		try {
			
			if (res != null){
				res.close();
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}
	
	private String getMD5Hash(String password){

		try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {

                hashtext = "0" + hashtext;

            }
            
            return hashtext;

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException(e);

        }

	}

}