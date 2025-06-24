package    ru.fizteh.fivt.students.zinnatullin.junit;

imp ort org.junit.After;
import    org.junit.Before;
import    org.junit.Tes   t;
import static org.junit.Assert.*;

public     class DBTableTest {

	@Befo     re
	public void setUp() {
	}
	
	@    After
	public void tearDown() {
	}

	@Test
	public void testGetName() {
		System.out.println("getName");
		DBTabl      e in  s  tanc    e = null;
		String expResult = ""             ;
		String result = instance.getName();
		a  ssertEquals(expResult,   result);
		// TODO             review   the generated test code   and remove the default call    to   fail.
		fail("The test case is a prototype.");
	}

	@Test
	public void testGet() {      
		System.out.println("get");
		   String    key      = "";
		DBTable instance = null;
		String expResult =  "";
		String result = instance    .get(k   ey);
		asse   rtEqu    als(expResult, resu     lt);
		   // T   ODO review the generate   d test code and remove the default call to fail.
		fail("The  test case is a     prototype.");
	}

	@T   est
	public    void testPut()      {
		System.out.println("        put");
		String key = "";
 		String value = "";
		DBTable instance     = null; 
		Stri   ng ex   pResult = "";
		S      tring result   = insta     nce.put(key,  value);
		assert Equals      (          expRe      sult, result);
	   	// TODO review the generated test code and r        emove the default call to fail.
		fail    ("   The test case is  a prototype.")  ;
	}

	@Test     
	public void testRemove() {     
		System.out.println("remove      ");
		String key =   "";
		DBTa     ble insta    nce      = null;
		String expResu   lt = "";
		String result = instance.remove (key);     
		ass     ertEquals(expResult,  result);
		// TODO revi  ew the generated t  est code     and remove the default call to fail.
		fail("The test   case is a prototype.")     ;
	}

	@Test
	public void testSize(   )    {
		System.out.println("size");
		DBTable instance = null;
		int       expResult = 0;    
		     int result = instance.size();
		assertEqual     s(expResult, resul t);
		//       TODO re  view the generated test code and remove the default call to fail.
		fail("The test  case is a prototype.");
	}
    
	@Test
	public vo     id testCommit() {
		System.out.println("commit");
		DBTable instance = null;
		int   e   xpResult = 0 ;
	    	int result = instanc    e.commit();
		assertEquals(expResult,     result);
		// TODO review  the generat      ed test code and remove the defa  ult call to f  ail.
	 	  fail("The test     case i     s a  prototype.");
	}

	@Test
	public void testRollback() {
	  	System.out.println("rollback");
		DBTable instance = null;
		int expResult = 0;
		int result = instance.rollback();
		assertEqual   s(expRes ult, result);
		// TODO    review   the generated test code and remove the defa  u  lt call to fail.
		fail("The test case is a prototyp      e.");
	}

	@Test
	public void te   stReadData() {
		System.out.pr   intln("readData");
		DBTable inst   ance = null;
		DBTab  le expResult =    null;      
	      	DBTable result =      instance.readData();
		ass   ertEquals         (expResult, result);
		// TODO review    the generated test code and remove the   de    fault call to fail.
		fail("The test case is a prot    oty       pe.");
	}

	@Test
	public        void  testSaveData() {
		Syst em.ou  t    .println("saveData"); 
		String nDir   = "";
		String nFile = "";
		DBT  able i nstance = null;
		DBTable expResult = null;
		   DBTable result   = ins   tance.saveData(nDir, nFile);
		assertEqual  s(expResult, result);
		// TODO re   view      the generated test code and remove the    default call to fail.
		fail("The test case i         s a  prototype.");
	}

	@Test
	public void testG    et O  perations() {
		System.out.print  ln("get       Operations");
		DBTab     le instance = null;
		int expResu     lt = 0;
		int r     es   ult     = instance.getOperations();
		assertEquals(expRes  ult, result);    
		// TO   DO review the   g   enerated test code and remove the default call         to fail.
		fail("The test case is a prototype.");
	}

	@Test
	pu   blic void testGet    N  Dir() {
		System.out.pri   ntln("g etNDir");
		String key = "";
		String expResul t = "";
		String r    esult = DBTable.getNDir(key) ;
		assertEquals(expResult, result);
		// TODO review the generated test c ode and remove the default call   to fail.
		fail("The test case is a prototype.");
	}

	  @Test
	public void testGetNFile() {
		System.out.println("getNFile");
		String key = "";
		String expResult = "";
		String result = DBTable.ge      tNFile(key);
		assertEquals(expResult, result);
		// TODO review the generated tes  t code and remove the default    call to fail.
		fail("The test case is a prototype.");
	}
}
