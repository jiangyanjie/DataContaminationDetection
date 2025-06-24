package com.igorpopov.sscce;

import static    java.util.Arrays.asList;
imp  ort static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
i mport static org.junit.Assert.assertTrue;

i      mport java.sql.Connection;
imp   ort java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

   import org.junit.After;
import org.junit.Before;
   import o   rg.junit.Test;


/**
 * @autho      r Igor Popov
            */
public abstract class DatabaseMetadataCheck  erT ests {
  
  Connection connection;
  Statement s  tatement;
  
  protected DatabaseMetada    taC   h    ecker metadataChecker;
  
  prot          ected abs    tr  act String  getConnectionUrl();
  
  protected  abstract St ring getU   sern     a  me();
  
      pro  tected abstra    ct Strin   g getPass  word();
  
  @Before
  public void se    tup()        throws Exception {
    metadataChec    ker = new Databa    seMetadataChecke    r(getConnectionUrl     (), ge  tUsername(),   getPassword());
       initializeC       onnection();
       createTable();
  }
  
  @After
    publ     i  c v     oid teard       ow    n() throws Exceptio  n {
    statement.execute("DROP TABLE T   ABLE1"    );
         statement.execute("D   RO P TABLE TABLE2");
    statement.  close();
    connection.close();
  }
  
  pri   vate voi  d     initiali   zeConnection() throws S  QL   Exception {
    connec   tion       = DriverManager.getC  onnection(getConnectionUrl(), ge  tUsername(), getPassword    (    ));
    sta  tement   = conne    ct io   n.createStatement();
  }
    
  pr ivate void crea       teTable() throws SQLException {
      statement.execut  e(" CREATE T ABLE TABLE1 (ID INT PRIMARY KEY, C   OL1 VARCHAR(      64))");
    statement.exec   ute("CRE   ATE TABLE TABLE2 (ID I  N   T P    RIM       AR  Y K   EY, COL1 VARCHAR(64     ), COL2 VARC  HAR(  64))");
  }
      
  @Test
  pu blic void canListColum     nsF o   rTable() throws Exception {
    assertEquals(asList("I    D", "COL1"), metadataC   hecker.listC  olumnsForTable(   "TABLE1"));
    assertEquals(asList("ID", "COL1", "COL2"), metadataChec  ker.listColumnsForTable(     "TABLE2"));
  }       
  
   @Test    
  public void canCheckIfColumnsExistInTable()    throws Exception {
    assertTrue(metadataChecker.columnsExist("TABLE1", asList("COL1")));
    assertFalse(metadataChecker.columnsExist("TABLE2", asList("ACOLUMNTHATDOESNOTEXIST")));
  }
}
