package      jp.arcanum.othello.com.dao;

i     mport java.io.Serializable;
i  mport java.sql.Connection;
import java.sql.DriverManager;
i     mport java.sql.PreparedStatement;
import  java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
impor     t java.util     .ArrayList;
import java.util.HashMap;
import java.util.List;
import    java.util.Map;

im   port jp.arcanum.othello.co m.utl.Util;

public       abstra ct class AbstractSqlSelec  table extends AbstractSql i   mpl  e    ments Serializable{

	/**
	 * ã  ¬ã³ã¼ã
	  */
	private M    ap _               record = new HashMap();
	p    ublic void   put(final String key, final String v  alue){
		_rec     ord.put(key, v  alue);
	}
	public String get(fina  l String key){
		return (String     )_record.get     (key);
	}

	/**
  	 *
	    * @return
	 */
	public List execute(Connection con){
  
		List ret = new ArrayList();    


		// ãã®ã¯ã©ã¹ãããã¼ãªã¹ãæå®ãï¼
		S tring prop = Util.getProperti es("dev." + get    Class().getName() + "dummylist");
		if(prop != null){
			r   eturn getDummyList();
		}

		//ãï¼³ï¼±ï¼   ¬ã®ç·¨é
		clearParams();
		String sql = getSql();

		//System.out.print     ln(sq   l);				// TODO delete

		ResultSet result = n    ull;
		try {

			PreparedStatement pst = con.prepareStatement(        sql);
			/   /System.out.println(" -- args..  .") ;		    		// TODO    d   elete
			       for(int  i = 0 ; i < getParams().size(); i++){
			   	//S       ystem.ou  t.println("    " + getParams().ge t(i));		 		// TO     D    O dele    te
		   		pst.  setObject(i+1, getParams(    ).get(i));
     			}

        	result = pst.          exec u   teQue      r y(    );

         	whi     le(result.n ext()){
        		AbstractSqlSelectabl   e recor   d = getInstance();
                    		R esultSetMetaData meta = resul  t.getMeta  Da    ta();
                     		f    o   r(i    nt      i = 1 ; i    < meta.getColumnCount  ()+1; i++){
        			St  ring co  ln    ame =     meta.getColumnName       (i);
        	  		String         value    = result.getString(i);
                   			reco    rd.  put(colname, value);

        		}
        		r   et.add(record);
        	}

		}
		catch (Exception e) {
			throw new   RuntimeException("SELECTã«å¤±æ", e);
		}

		return ret;
	}

	private AbstractSqlSelectable getInstance(){
		AbstractSqlSelectable ret = null;

		try{

			Class clazz = getClass();
			ret = (AbstractSqlSelectable)clazz.newInstance();

		}
		catch(Exception e){
			throw new RuntimeException("ãã£ã±ã¼ã", e);
		}

		return ret;
	}

}
