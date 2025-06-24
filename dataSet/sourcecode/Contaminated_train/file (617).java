import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;

public class ConnectMySQL {
	private static Connection conn = null;
	public static PreparedStatement statement = null;

	// �������ݿ�
	public static void connSQL() {
		String url = "jdbc:mysql://localhost:3306/men?characterEncoding=UTF-8";
		String username = "date";
		String password = "date"; // ���������������������ݿ�
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("���ݿ����ӳɹ�");
		}
		// ����������������쳣
		catch (ClassNotFoundException cnfex) {
			System.err.println("װ�� JDBC/ODBC ��������ʧ�ܡ�");
			cnfex.printStackTrace();
		}
		// �����������ݿ��쳣
		catch (SQLException sqlex) {
			System.err.println("�޷��������ݿ�");
			sqlex.printStackTrace();
		}
	}

	// �Ͽ�����
	public static void deconnSQL() {
		try {
			if (conn != null)
				conn.close();
			System.out.println("���ݿ�رճɹ�");
		} catch (Exception e) {
			System.out.println("�ر����ݿ����� ��");
			e.printStackTrace();
		}
	}

	// ���ؽ����
	public static ResultSet selectSQL(String sql) {
		ResultSet rs = null;
		try {
			statement = conn.prepareStatement(sql);
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// �������ݣ�����Ϊ�ֶ���
	public static boolean insertSQL(String date, String timestart,
			String timeend, String text) {
		String sql = "insert into datetable values('" + date + "','"
				+ timestart + "','" + timeend + "','" + text + "')";
		try {
			statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("�������ݿ�ʱ����");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("����ʱ����");
			e.printStackTrace();
		}
		return false;
	}

	// ɾ������
	public static boolean deleteSQL(String date, String timestart,
			String timeend) {
		String sql = "delete from datetable where date='" + date
				+ "' and timestart='" + timestart + "' and timeend='" + timeend
				+ "'";
		try {
			statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("ɾ�����ݿ�ʱ����");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("ɾ��ʱ����");
			e.printStackTrace();
		}
		return false;
	}

	// ��������,����Ϊ�ֶ���
	public static boolean updateSQL(String date, String timestart,
			String timeend, String text) {
		String sql = "update datetable set text='" + text + "' where date='"
				+ date + "' and timestart='" + timestart + "' and timeend='"
				+ timeend + "'";
		try {
			statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("�������ݿ�ʱ����");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("����ʱ����");
			e.printStackTrace();
		}
		return false;
	}

	public static ResultSet getTodayResultSet() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
		String currentDate = null;
		currentDate = sdfDate.format(Calendar.getInstance().getTime());
		// System.out.println(currentDate);
		ResultSet rs = selectSQL("select * from datetable where date='"
				+ currentDate + "'");
		return rs;
	}

	public static ResultSet getNowResultSet() {
		SimpleDateFormat sdfTime = new SimpleDateFormat("HHmm");
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
		String currentDate = null, currentTime = null;
		currentDate = sdfDate.format(Calendar.getInstance().getTime());
		currentTime = sdfTime.format(Calendar.getInstance().getTime());
		ResultSet rs = selectSQL("select * from datetable where date='"
				+ currentDate + "' and timestart='" + currentTime + "'");
		return rs;
	}

	public static ResultSet getSelectedResultSet(DateChooser dc,
			JSpinner staHourSpinner, JSpinner staMinuteSpinner,
			JSpinner endHourSpinner, JSpinner endMinuteSpinner) {
		String selectDate = dc.sdf2.format(dc.select.getTime());
		String str1 = null, str2 = null, str3 = null, str4 = null;
		if ((Integer) staHourSpinner.getValue() < 10) {
			str1 = '0' + staHourSpinner.getValue().toString();
		} else {
			str1 = staHourSpinner.getValue().toString();
		}
		if ((Integer) staMinuteSpinner.getValue() < 10) {
			str2 = '0' + staMinuteSpinner.getValue().toString();
		} else {
			str2 = staMinuteSpinner.getValue().toString();
		}
		if ((Integer) endHourSpinner.getValue() < 10) {
			str3 = '0' + endHourSpinner.getValue().toString();
		} else {
			str3 = endHourSpinner.getValue().toString();
		}
		if ((Integer) endMinuteSpinner.getValue() < 10) {
			str4 = '0' + endMinuteSpinner.getValue().toString();
		} else {
			str4 = endMinuteSpinner.getValue().toString();
		}
		String selectStaTime = str1 + str2;
		String selectEndTime = str3 + str4;
		ResultSet rs = selectSQL("select * from datetable where date='"
				+ selectDate + "' and timestart='" + selectStaTime
				+ "' and timeend='" + selectEndTime + "'");
		return rs;
	}

}
