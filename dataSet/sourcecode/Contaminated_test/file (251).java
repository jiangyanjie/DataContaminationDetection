package com.shagie.dbtest.db;

import com.shagie.dbtest.db.objects.Data;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Calendar;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class DataAccessTest {
	private Connection conn;
	private PreparedStatement insert;
	private PreparedStatement insertId;

	@Before
	public void setUpDB() {
		DBConnection connection = new DBConnection();
		try {
			conn = connection.getDBConnection();
			insert = conn.prepareStatement("INSERT INTO data (txt, ts, active) VALUES (?, ?, ?)");
			insertId = conn.prepareStatement("INSERT INTO data (id, txt, ts, active) VALUES (?, ?, ?, ?)");
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Error instantiating database table: " + e.getMessage());
		}
	}

	@After
	public void tearDown() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testBasidDataAssumptions() throws Exception {
		/*
		1. The database starts out empty, adding an item to it increases the row count by 1.
		2. The timestamp is now.
		3. The row is active by default
		*/

		PreparedStatement ps = conn.prepareStatement("SELECT * FROM data");
		ResultSet rs = ps.executeQuery();
		int i = 0;
		while (rs.next()) {
			i++;
		}
		assertTrue(i == 0);

		Calendar rightNow = Calendar.getInstance();
		Timestamp before = new Timestamp(rightNow.getTimeInMillis());

		conn.createStatement().execute("INSERT INTO data (txt) VALUES ('foo')");
		rs = ps.executeQuery();
		rightNow = Calendar.getInstance();
		Timestamp after = new Timestamp(rightNow.getTimeInMillis());

		i = 0;
		String txt = "notfoo";
		boolean active = false;
		Timestamp ts = null;
		while (rs.next()) {
			ts = rs.getTimestamp("ts");
			txt = rs.getString("txt");
			active = rs.getBoolean("active");
			i++;
		}
		assertEquals(i, 1);
		assertTrue(active);
		assertEquals(txt, "foo");

		// Test to make sure the timestamp in the database is within the 'now' range by default
		assertTrue("ts: " + ts.toString() + " after: " + after.toString(),
				   ts.before(after) || ts.equals(after));
		assertTrue("ts: " + ts.toString() + " before: " + before.toString(),
				   ts.after(before) || ts.equals(before));
	}


	@Test
	public void testGetData() throws Exception {
		// load data
		Calendar time = Calendar.getInstance();
		long now = time.getTimeInMillis();
		long then1h = now - (60 * 60 * 1000);    // one hour ago
		long then2m = now - (60 * 1000 * 2);    // two minutes ago
		addData("active_foo", new Timestamp(then1h), true);        // active but old
		addData("inactive_bar", new Timestamp(then1h), false);    // inactive and old
		addData("active_quz", new Timestamp(then2m), true);        // active and new
		addData("inactive_baz", new Timestamp(then2m), false);    // inactive and new

		DataAccess dao = new DataAccess();
		int count = 0;
		for (Data data : dao.getData()) {
			count++;
			assertTrue(data.getTxt().startsWith("active"));
		}

		assertEquals("got back " + count + " rows instead of 1", count, 1);
	}

	@Test
	public void testMarkInactive() throws Exception {
		// load data
		Calendar time = Calendar.getInstance();
		long now = time.getTimeInMillis();
		long then2m = now - (60 * 1000 * 2);    // two minutes ago
		addData(1, "active_quz", new Timestamp(then2m), true);        // active and new

		DataAccess dao = new DataAccess();
		dao.markInactive(1);
		assertTrue("database is not empty - contains " + dao.getData().size() + " items", dao.getData().isEmpty());
	}

	@Test
	public void testAddData() throws Exception {
		DataAccess dao = new DataAccess();
		dao.addData("foo");
		for (Data data : dao.getData()) {
			assertEquals("foo", data.getTxt());
		}

	}


	private void addData(String txt, Timestamp ts, boolean active) throws Exception {
		insert.setString(1, txt);
		insert.setTimestamp(2, ts);
		insert.setBoolean(3, active);
		insert.execute();
	}

	private void addData(int id, String txt, Timestamp ts, boolean active) throws Exception {
		insertId.setInt(1, id);
		insertId.setString(2, txt);
		insertId.setTimestamp(3, ts);
		insertId.setBoolean(4, active);
		insertId.execute();
	}

}
