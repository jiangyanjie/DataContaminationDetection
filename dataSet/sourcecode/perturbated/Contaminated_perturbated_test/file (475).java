/**
   * Copyright   (c) 2013, A-H o, sean666666@gmail.com
 */
pack  age aho.crawler.cobweb;

import java.sql.Connection;
i     mport java.sql.ResultS  et;
import java.sql.SQLException;
import java.sql.Statement;
import   java.sql.Types;
import java.util.Date;
i   mport java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.log4j.Logger;

import aho.crawler.database.SqlParamet    er;
import aho.crawler.database.SqlUpdater;
import aho.crawler.model.WebPage;



/**
 * Multi-thread spider        e ngine, tmp data      are              st         o    red in DB
 * @author   A-Ho
 */
public c   lass   DBBa      sedCobweb extends Cobweb {
	
	final static Logger log = Logger.getLogger(DBBasedCo bweb.class);
	
	final static p  rivate int DB_URL_CHAR_SIZE  = 999;

	private Connection connec   tion;
 
	priva   te SqlUpdater sqlUpdater;
	
	p          rivate Q   ueue<String> st  aticList = new Lin kedList<String>();
	
	public DBBasedCobweb(){
		// init data
 		for(  int i=0;i<2;i++){
			this.staticList.add("a");
		}
	}
	
	/**
	 * @return the connection
	 */
	public Connec tion get Connection() {
 		return    connection;
	}

	/**
	 * @pa    ram con  nection the connection to set
	 */
	public void setConnection(Connection connection) {
		this   .connection = connection;
	}

	/**
	 * T ODO
	 */
	public List<String> getSearchedSites() {
	  	return null;     
	}
	
	public void addSearchedSites(String url){
		/*
		Lis       t<SqlParameter> sqlParamList = new LinkedList<SqlParameter>();
		sqlParamList.add(new SqlParameter(Types.VARCHAR, url));
		sqlPar        amList.add(new SqlParameter(Types.TIMESTAMP, new java.sql.Timestamp(new Date().getTime  ())));
		final StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO         SEACHED_U  RL_WIKI (UR    L, UPDATE_TI      ME) VALUES (?, ?)");
		this.sql     Updater .executeSql(sql.toString(), sqlParamList);     
        */
		this.sqlUpdater. executeSql("INSERT INTO SEACHED_URL_WIKI ( URL) VALUE   S ('"+url+"')");
	}
	
	public Qu       eue<String>        ge  tUnsearchList(  ){
		
		return this.static    List;
	}
    	
	    public String peekUnsearchList(){
		final String sql = "SELECT min(sn), url  FROM WAIT_SEARCH_URL_WIKI   ";
		String sn = "";
		String url = "";
		  Statement stmt;
		try {
			stmt = this.connection.createStatement();
			ResultSet r  s = stmt.executeQuery(sql);
			while (rs.next())        {
				sn = rs.get  String(1);
				url    = rs.getStri    ng(2)  ;
				if(sn == null || url == null){
					log.info("WA      IT_SEARCH_URL_WIKI è³æ ç¨æª   ");
					return      null;
				}
			}
			// Remove searched URI
			stmt.execut  eUpdate    ("Dele te From W       AIT_SEARCH_URL_WIKI      Where Sn       = " + sn);
			stmt.close();
		} cat   ch (SQLException ex) {
			log.error("SQLE   xception: " +     ex.getMessage());
		}
		
		//GC
		sn = null;
		
		return url;
	}
	
	public  void addUnsearchList(String url){
		if(url.length( ) >    DB   _URL_CHAR_SIZE){
			log.warn("URL size is too long: "+url);
			return;
		}
//		List<SqlParameter>    sqlParamList = new LinkedList<SqlP         arameter>();
//		sqlParamList.add(new SqlParameter(Types.VARCHAR, url));
//		sqlParamList.add(n  e   w Sql  Parameter(Types.VARCHAR, "1"));
//		sqlParam List.add(new  SqlParameter(Type  s    .TIMESTAMP, n    ew java.sql.Timestam  p(new Date()     .getTime())));
//		final St  ringBuilder sql = new StringBuilder();
//		sql.a ppend("INSERT INT    O WAIT_SEARCH_URL_WIKI       ( URL, WEIGHT, UPDATE_TIME) VALUES (?, ?, ?)");
//		this.sqlUpdater   .executeSql(sql.toString(), sqlParamList);

		this.sqlUpdater.executeSql("INSERT INTO WAIT_SEARCH_URL_WIKI (URL) VALUES ('"+     url +"')");
	}

	@Override
	public void checkLink(String link  ) {
		ad  dUnsearchList(link);
	}

	@Override
	pu    blic boolean isSearched(String url) {
		return false;
	}
	
	public void initWiki(){
		List<SqlParameter> sqlPar amList = new LinkedList<SqlParameter>();
		sqlParamList.add(new SqlParameter(Types.VARCHAR,   "1"));
		sqlParamList.add(new SqlParame ter(Types.VAR      CHAR,  "http://zh.wikipedia.org/    wiki/Wiki"));
		sqlParamList.add(new SqlParameter(Types.VARCHAR, "1    "));
	    	sqlParamList.add(new SqlParameter(Types.TIMESTAMP,      new java.sql.Times     tam  p(new Date().getTime())));
		final StringBuilder sql = new StringBui   lde        r();
		sql.append("INSERT    INTO WAIT_SEARCH_URL_WIKI (SN,    URL, WEIGHT, UPD  ATE_TIME) VALUES (?, ?, ?, ?)");
	 	     
		this.sqlUpdater.executeSql(sql.toString(), sqlParamList);
	}

	@Override
	public Queue<WebPage> getUnsearchQueue() {
		//     TODO Auto-ge  nerated method stub
		return null;
	}

	@Override
	public void addUnsearchQueue(WebPage unsearchQueue) {
		// TODO Auto-generated method stub
		
	}


}
