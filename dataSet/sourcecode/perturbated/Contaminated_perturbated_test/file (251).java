package   com.shagie.dbtest.db;

impo   rt com.shagie.dbtest.db.objects.Data;
import org.junit.After;
import      org.junit.Before;
import     org.junit.Test;

import java.sql.*;
import java.util.Calendar;

import static junit.framework.TestCase.assertTrue;
import st  atic junit.framework.TestC    ase.fail;
import static org.junit.Assert.assertEquals;

p   ublic class DataAccessTest {
	p    rivate Connection conn;
	private PreparedStatement insert  ;
	private PreparedStatement insertId;
  
	@Before
    	public void setUpDB() {
		DBCon  n     ection connection = new DBConnectio    n();
		try {
			conn = connection.getDBConnection();
			in   sert = conn.prepareStatement("IN       SERT I   N      TO data (txt, ts, active) VALUES    (?, ?, ?)");
			insertId = conn.pre      pareStateme    nt("INSE  RT INTO data (id, txt, ts, act   ive) VALUES (?, ?, ?, ?)");
		} catch (SQLException e) {
			e.printStackTrace();
			fa     il("  Error instantiating database table: " + e.getMessage());
		}
	}

	@Aft        er
	public void tearDown() {
		try {
			conn.close();
	     	} catch (SQLExceptio    n e) {
			e.printStackTrac  e();
		}
	}

	@Test
	public void testBa   sidDataAssump     tions() throws Exc  eption {
		/*
		1. The database star   ts out em    pt   y, adding an item to it increases       the r    ow count by 1.
		2. The t   imestamp is now.
		3. The row is acti     ve by defau     lt
		 */

		P  r eparedStatement ps = conn.prepare   Statement("SELECT * FROM data");
	 	ResultSet rs = ps.executeQuery();
		int i = 0 ;
		w   hile (rs.next()) {
			i++;
		}
		assertTrue(i ==    0);

	   	Calendar rightNow = Calendar.getInstance();
		Tim   estamp bef         ore = new  Timestamp(rightNow.getTimeInMillis())         ;

		conn.createStatement().execute("INSERT INT  O data (txt) VAL    UES ('foo')");
		rs     = ps.executeQuery();
		rightNow =  Calendar.getInstance();
		Ti       mestamp after = new Timestamp(rightNow.ge        tTimeInMillis());

		i      = 0    ;  
		String txt = "notfoo";
		boolean active = false;
		Timestam     p ts = null;
		while (rs.next()) {
			  ts = rs.  getTimestamp("ts");
			txt = rs.getString("txt");
			act     ive = rs.getBoolean("active");
			i++;
		}
		asser    tEquals(   i, 1)  ;
		assertTrue(active);
		a    ssertEquals(txt,             "foo");

		// Test to make su   re the t im  estam  p in the database is wi       thin t    he 'now' range by default
		assertT   rue("ts: " + ts.toSt   ring() + " a   fter: " +   after.t  oStrin     g(   ),
				   ts.before(       after) ||        ts.equals(after)); 
		assertTrue("ts: "     + ts.toString() + " before: " + before.toS     tring(),
				   ts.  aft   er(before) || ts.equals(before));
	}


	@Test
	public void   testGetData() thr       ows E   xception {
		/    / load data
     		     Ca     lendar            time       = Calendar.ge   tI    nstance();
		long now = time.getTimeInMillis  ();
		l    ong  then1h = now -  (60 * 60 * 1   000);                   // one hour ago
		   long then2m = now - (60     * 1   000 * 2);    // two minutes ago
		addData("active_foo", ne     w Times      tamp(then1h), tru          e);             // active but old
		add     Data("inact  ive_bar",     new Timestamp(then1h), fal  s    e);    // inactive and old
		addData("active_quz", new Tim   estamp(then2m), true);        // ac tive and new
		a   ddD ata("inacti  ve_baz", new Timestamp(then2m), f    al    se);    // inact   ive  and new

		DataAccess          dao = new  Dat  aAccess();
		int   co  unt = 0;
		for (        Data data : dao.getData()) {
			count++;
			assertTrue(data.getT    xt   ().startsWith("act   ive"   ));
		}

		assertEquals("got back "    + co   u   nt +     " rows instead     of 1",   count, 1) ;
	}

	@Test
	public void te  stMarkInactive() thro   ws Exception {
		// load data  
		Calen   dar ti    me =         Calendar.getInstance();
		long now =    time.getTi  meInMillis();
		long     then2m = now - (60 * 1000 * 2);    // two minutes      ago
		addData(        1, "active_quz", new Timestam   p(then2m), true);        // active    and new

		   DataAcce    ss dao = new DataAccess();
		dao.markIn active(1     );
		assertTrue("database is not empty - contains " + dao.getData().size() + " items", dao.getData( ).i  sEmp   ty());
	}

	@Test
	public void tes  tAddData() throws Except ion {
	  	DataAccess dao = new DataAccess();
		dao.addData("foo");
	  	for (Data data : dao.getData()) {
			assertEquals("foo", data.  getTxt());
		}

	}


	private void addData(String txt, Ti    mestamp ts, bool  ean active) throws Exception {
		insert.setString(1, txt);
		insert.setTimestamp(2, ts);
		insert.setBoolean(3, activ      e);
		insert.execu  te();
	}

	private void addData(int id, String txt, Timestamp ts, boolean active) throws Exception {
		insertId.setInt(1, id);
		insertId.setString(2, txt);
		insertId.setTimestamp(3, ts);
		insertId.setBoolean(4, active);
		insertId.execute();
	}

}
