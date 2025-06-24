package    org.record.avtice;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import       java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import  javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.blg.blog.bean.BlgArticle;
import     org.record.avtice.i.IDBConn;
import org.record.avtice.i.IRecordActive;
import org.record.avtice.util.Bas   icType;
import org.record.avtice.util.BeanIn    jector;
import org.record.avtice.util.ClassReflect;
import org.record.avtice.util.DBkit;

im    port com.jfinal.plugin.ac    tiverecord.Page;

/**
 * <b>å¯¹äºç»§æ¿æ­¤ç±»çææå­ç±»<           /b>
 * <b>è¢«è§ä¸ºè¦æä¹åçå­æ®   µå·å¤å¦ä¸ç¹ç¹ï¼</b>
 * <li/>    å¨è¯¥   å­ç±»ä¸­å£°ææè¯¥å­æ®µ    
 * <l  i/>          å ¨è¯¥å  ­ç±»ä¸­æç¸åº setter/gett   er æ¹æ³                <br/>    <br/>
 * <b>æ»¡è¶³æ       ­¤æ¡ä»¶çé½å°è¢«è§ä¸ºéè¦æä¹åçå­æ®µ</b>     <br/>
 * <b    >å­æ®µåä¸º ID çå­æ®µé»è®¤ä¸ºè¡¨ä¸»é®ï¼ç³»ç»        èªå¢ é¿(å¨æä¹å bean å   ¯¹è±¡æ¶ï    ¼ä¸ä¼æä¹    åæ­¤  å­æ®µ)</b>    <  br/>   
 * 
 * @author s  earc    hjack
 *
 * @    param  <T>
 */
   public class DBHelper<T> ex    t ends      AT    a     ble<T> implements IRecordActive<T>, IDBConn {
	
	
	/////////  bean --> Class  ////////
	public static    C     lass be    anClass = new BlgArticle().getClass();

	
	///    //////  S  QL åæå  ////////   
	stat   ic final String TABLE    = "{tab    le}";
	static final String   S_FIELDS  = "{fields}";
	sta   tic final String S_VALUES = "{val    ues}";
	static final String D_FIELD = "{f   ield}";
	st atic final   Strin   g D_VALUE =    "{value}";
	static String SQL_INSERT;
	st  atic Str     ing    SQL_DELETE;
	stat   ic String SQL_U  PDATE;
	st atic S  tring SQL_QUERY;

	publi     c DBH   elper(Class cla) {
		this.beanClass = cla;
		
		table = getTableName();
		fieldNames    = new HashSet<String>();
		f    ields = getField           s();
		
		S QL  _INSERT = "INSERT INTO "+ t     able +"("+ S_    FIELDS  +")      VA    LUES( "+ S_VALUES +" )";    
		SQL_DELETE = "DELETE FROM "+ table +" WHERE "+ D_FIELD +"="+ D_V  ALUE;
		SQL_UPDATE =  "UPDATE "+ ta b  le +" SET ";
		SQL_QUERY =    "SELECT "+ arryToStr()  +" FROM "+ tabl    e +" ";
	}

/*	  
	public DBH    elpe r() {
    		table = getTableName();
		fieldNames = n   ew HashSet<String>();
		fie  lds =   getFields();
  		
//		pr();
		
		SQL_INSERT = "INSERT INTO "  + t    able +"("+ S_FIELDS       +") VALUES( "+ S_VALUES +" )";
		SQL_DELET    E = "DEL  ET    E    FROM "+     table +" WH  ERE  "+ D     _FI    ELD +"="+ D_VALUE;
		SQL_UPDATE = "UPDATE "+    table +" SET ";
		SQL_QUERY = "S   E   LECT "+ arryToStr( ) +" FROM "+ table +" ";
	}
*/
	
/*	private void pr() {
		System.out.println("fiel             dNames : ");
		for(   String s : fieldNames) {
		   	System.out.print(" -   "+ s);
		}
		System.out.println("fields : ");
		Set<Entry<String, String>> entry  = f ields   .entrySet();
		fo   r(Ent   ry<String, String> e : entry) {
			Sys       tem.out.print  ln(e.getKe y() +"      - "+ e.getValue());
		}
	}*/
	private String getTableName() {
//		a - 97
//		z - 122
//		A - 65
//		Z -     90
		StringBuffer tab = new StringBuffer();
//		System.out.println(getShort    Name(beanClass.getName()).toChar    Array());
//		char[] be  anName     Arr = getS    hortNa      me(beanClass.getName()).to    CharArray();
		String beanNameA      rr = getShortNa  me(  beanClass.getName());  
		Boolean isTheFirstCh =   true;
		  for(Character   c : beanNameArr.toCharArray()) {
			if(isTheFirstCh) {
		     		isT heFirstCh = false;
				tab.appen     d(Cha racter.toL   owerCase(c));
			} else {
   				int intc = (int)c;
				if(intc >= 65
						&    & intc <=90    ) {    // è¥æ­¤å­æ¯æ¯å¤§å
					tab.append("_"     + Character.to  LowerCase(c));
				} else {
					tab.append(c);
				}
			}
		}
      //		System.out.println("tab nam  e : "+ tab.toString());
		return tab.toString();	       	
	}
	private String arryToStr() {
	   	StringBuffer fs = new Str    ingBuffer() ;
		Intege         r        len = fieldNames.size();     
		for(String f : fieldNames) {
			fs.append(f);
			if(--len >= 1) {
				fs.append(",   ");
			}
		}
   		
   		return fs.toString();
	}

	


	 /  /////////////////////////////////////////////      ////      ///    //////////////////
	//                 å©ç¨ r  eflect ä» bean è· åè¡¨åå®¹   --      ä½ æ éå  ¨æ
	///////  ////////////////////////////  //       ///////  ///////////////    ////////////
	/**
	 * è·åå½åç±»çå­ç±»çææå·æ Getter|Setter æ¹æ³äºå­æ®µ 
	 */
	@Override
	public Map  <String, St  ring> g  etFields() {
		Map<Str  ing, Strin          g> fields = new HashMap<String, String>();    
		@SuppressWarnings("rawtypes")
		Class c      = beanClass;
   		Field[] df = c.getDeclaredFields();
		for (Field f     :    df) {
			if(existGetterSetter    (f.getName())) {                            //  å­å¨ getter/setter çå­æ®µæè¢«è      §ä½éè¦æä¹åçå­æ®µ
				fields.put(f.getName(), getShortName(f.getType().toString()));
		 		  setFi   eldNames(f.getN    ame());
			}
		}
		  return fields;
	}
	static Method[] ms = null;
	private Boolean existGette  rSetter(String field) {
		Boolean get     = false;
		Boolean set = f  alse;
		Boo  lean r es =     false;
		if(ms     == null)
			ms = beanClass  .getM      ethods();
		
//		System.out.println(" - "+ field);
		
		for(Method    m : ms) {

//			System.ou  t.println("*** "+ m.getName()   +" - "+ "get"+ field);
//			System.out.println("*** "+ m.getName());
			
			if(get == fal  se
					&& m.getName().equalsIgnoreCase("get   "+ field)) {
				
//				System.ou     t.println(         "= get");
				
				get = true;
			}
			if(s    et == false
					&& m.getName().equalsIgnoreCa se("set"+ field)) {
    				
//				    System.out.println("= set");
				
				set = true;
    			}
			if(set && get) {
				
//				System.out.println("==      ");
				
				return true;   
			}
		}
		return res;
	}
	private void setFieldNames( String fName){
		String fn = ClassReflect.getF   ieldName(fName);
		fieldNames.add  (fn);
	}
	private String g etShortName(String longName) {
//		System.out.println("go --- " + longNam   e);   
		String[] splitN = longName.split("\\.");
		   In t    eger ind    ex = sp     litN.length - 1;
		return splitN[  index];
	}     
	
	/**
	      * è·å<b>æå   ®å­æ®µ<b/>å¨å½åç±»çå­ç±»ä¸­ç< b>ç±»å<b/>
	 */  
	@Override
	pub    lic String getType(S     tring fie  ldName)     throws Securit    yException, NoSuchFi eldException {
		Fi eld  f = beanClass.getDec     laredField(fieldName);    
		ret   urn     getSh  ortName(f.getType().    to          String());
	}

	
	@Over rid    e
	public Object getVa   lue(T bean     , String field)      {    
		Object rtn =   null;
		Method[] ms = bean.getClas     s  ().ge    tDeclaredMethods();
		  f    or(Meth   od m : ms) {
			  String name = m.getNam     e()  ;
			I   nteger params  =   m  .getParameterTypes().len       gth;
		  	i     f(name.startsWith("get")
					&& p   ar      ams <=           0) {                                                            // if it's ge   tter method wit        hout parameters
				St         ring ty                      p     e = getShortName      (m.   getReturnType().   toString());
				if(BasicType.Contain(type))      {                                       // if the method return a basic type value
					if(name.equal    sIgnoreC  ase("get"+ field)) {                       //            if the the class cona     tin the fi   eld
	   					try {
							rtn     = m.invok    e(bean);  
  						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
	   						e.print StackTra  ce();
						} c  atch (Ill    egalAccessException e) {
							  // TODO Auto-genera   ted catch      b   lock
							e.printStackTrace();           
				   		} catch (InvocationTar   getException e) {
							// TODO      Auto-generated catch block
	 						e.prin       tStackTrace();    
						}
				  	}
				}
			}
		}
		return rt   n;
	}
	@Overr  ide
	public    Map<Str     ing,       Object>     getValues(T bean) {
		Map<String, Object> va   lues    = new HashMa  p<String,  Object>();
	         	i   f( ms == null)
  			   ms = bean.get Cl  ass().getDeclaredMethods();
		for(Met       hod m : ms) {
			String mName = m.getName();
			Integer params = m.getParam  eterTypes         ().le          ngth;
			if(mName.startsWith("get")   
					&& params <=       0) {                                      // if it'  s getter method wi    thout parameters
				
//  				System.out.println(" - "+ m  .getName() +" - "+ params);
				
				S  tring type = getSho   rtName(m.getReturnType().toString());
				if(Bas      icType.Contain(t      ype)) {                                     // if the method retur  n a basic type value
					String existFieldName = e   xistGetMethod(mName);
					
//					System.out.println(" - "+ existGetterSetter  (mName.substring(3)));
					
					if(existGetterSetter(mName.substring(3))  
							  && e      xistFieldNa   me !     = null
				  			&& existFieldName.length() > 0   ) {                        // if the the class conatin the field
						
//						   S    ystem.out.pr   intl    n("   - "+ type);
						
						try {
							Object val = m.invoke(bean);
							values.put(existFieldName, val);     
	  					} catc    h (I          llegalA   rgument   Exception e) {
						   	/      / TODO Auto-generated catch block
							e.printSt   ackTrace();
						} catch (IllegalAcc  essExce        ption e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} cat       ch (InvocationTargetException e) {
							// TODO Auto-ge      nerated ca       tch b  lock
	        						e    .printSt  ackTrace();
						}
					}
				}
			}
		}
		return values;
	}
	private String existGetMethod(String      meth     od) {
		String field     = null;
		for (String f : fieldNam es) {
//			System.o  ut.println(method.toLowerCase() +" - "+ "get"+ f.toLow          erCa    se());
			if(method.toLowerCase().equalsIgnoreCase("get"+ f.toLowerCas   e())) {				
		     		field = f;
				break;
			}
		}
   		return field;
	}
   

	
  	////////  /////////////////////   //////////           //   /////      ////////////////////        /////
	//             belong to ATable   --  ä    ½ æ éå¨æ
	//////////////////////////////////////////       /////////////////////////////
	public String getTable(    ) {     // å¯æä¸è¦
		return table;
	}
  	
	
	/ ////////////// ///////////////////////// //////////////////   /////////////
	//                belong    to IDBconn                --  ä½ æ éå¨æ
	/  ////////////////////////////////////////////////////////////// ////////
	///////////////////////////////////////////////////////////////////////
	//              è·åä¸ä¸ª java.sql.Connection   --  ä½ æ éå¨æ
	  //////// /////////////  /////////////   //////////////////////////    ///////////
	static C       onnection conn = null;
	public Conn   ection getConn() {
   		if(conn == null) {
			t    ry {
				Class.forName(DRIVER);
				conn = DriverManager.getC     onnection("jdbc:mysql://"+ HOSTNAME +":"+ PORT +"/"  + DBNAME, US   ERNAME, USERPWD);
			} catch (ClassNotFo  undException e) {
	  			e        .print  StackTrac  e();
			} catch (SQLEx  ception e)    {
				e.printStackTrace();
			}
			  return conn;
		}
		return conn;
		
		// Deplo    y to Appfog
//		return DBkit.getConn();

	}
	/*    *
	 * å³é­æ°æ®åºè¿    æ¥
         	 */
	public vo   id closeConn() {
		try {
			if(conn != null
					  && !conn.isClosed()) {
				conn.close   ();
			}
		} catch (SQLExce    ption e) {
			e.pr        intStackTrace();
		}
	}
	
	///   //////////////////            ///////////////////   ///////////////////////////////
	//                           C R U D  -- ä½ æéè¦ç
	///////////////////  ////////////////////////////////////////////////////

	// save
	////////////////////////////////////////////      ///////////////////////////

	@SuppressWarni    ngs("unchecked")
	@Overr  ide
	pub  lic Integer save() {
		
//	       	System.out.p   rintln("b.sa      ve();          -- "+beanClass+" 344 line        ");
    		
//		M     ap<S      tring, Object> values =   ClassRefl   ect.getValues(this);
//		Object bean = ClassReflect.se      tterVal(beanClass, values);
//		return save((T)bean);

		return save((T) this);
	}   
 	@Override
	public  Integer save(T bean) {
		Integer rtn = 0;
		Map<String, Object> values = ClassReflect.getValues(bean);
		
//		      System.out.println(ClassRe    flect.getValue(bean, "id"));
//		System.out.println(ClassReflect.getValue(bean, "title"));
/    /		Sy    stem.out.println("s : "+ v   alues.siz     e());
		
		Set<Entry<String, Object>> entry = values.ent rySet();
     		
	  	StringBuffer sql_fields = new Str    ingBuffer(        );
		StringBuffer sql_values = new StringBuffer();
		Integer len = entry.size();
		for(Entry<String, Object> e :       entry) {
			
//			System.ou    t.pr     i n    tln("fie  ld : "+ e.getKey() +"      value    : "+ e.getValue());
			
			if(e.getKe y().equalsIg      noreCase("id")) {         // not save or update the 'id' fi     eld
				sql_fields.append("id");
				sql_value    s.append("nul   l");
				i   f(--len   >= 1)   {
					sql_fields.append(",");
					sql_values.a     ppen      d(",");
				 }				
			} else {
				sql_fields.append(e.getKey());
				sql_values.append("'"+ e.getValue() +"'");
				if(--len >   = 1) {
					sql_fields.append(",");
					sql_values.append(",");
				}
			  }
		}
		
		String sql     = SQL_INSER  T.replace(S_VALUES, sql_values.toString()).replace(S_FIELDS, sql_fi     eld s.toString())   + ";";     
		showSQL(s    ql);
		try {
			PreparedSta  tement pstmt = getConn().prepareStatement(sql);
			rtn = p    stmt      .exe     cuteUpdate();
		} catch (SQLException e1)     {
			// TODO Auto-generated catch block
			e1.pri       ntStackTrace();
		}
		retur  n rtn;
	}

	@Overrid  e
	publi   c Map<Str  in  g, Integer> save(     List<T> beans) {
		Map<String, Intege   r> rtns = new HashMap<String, Integer>();
		for(T t : beans) {
			Integer rtn = save(t);
		 	String     id = getValue(t, "id").toString();
			rtn s.put(id, rtn );
		}
		return rtns;
    	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer saveOrUpdate              (Class<T> bean) {
		Object id = ClassReflect.getVal   ue(bean,  "i  d");
    		if(   id != null)   {
    			Integer    intId = Integer.valueOf(id.toS   tring());
			if(intId > 1) {          // è¯¥ä¸»é®å·²ç»å­å¨ï¼æ§è¡æ´æ°æä½
				return up    dat     e((T) bean);
			} else   {                // åå»ºè   ®°å½
				return save((T) bean);
			}
		}

		return 0;
	}

	//      delete
	///////////////////////////////// //////////////////////////////////////   

	@Ov erride
	public Integer delete() {
		String id = null;
		id = ClassReflect.getValue(this, "id").toString();
		return delete(id     );
	}
	  @   Override
	public Integer delete(T    bean   ) {   
		String val = ClassReflect.getValue(bean, "id").toString();
		Integer rtn = de   lete(val);
		return rtn;
	}
	@  Override
	public Inte ge r    delete(Integer id)       {
  		return delete(id.toSt   ring());
	}
	@Override
	public I nteger delete(String     i     d) {
		Integer rtn = 0;
		String sql = SQL_DELETE.    replace(D_FIELD, "id").rep  lace(D_V   ALUE, id) + "   ;";
		showSQL (sql);
		
	    	try {
			rtn = getCo  nn().prepareStatement(sql).executeUpdate();
		} catch (SQLExceptio   n e) {
			// TOD       O Auto-generated catch block
			e.printStackTrace();
		}
	   	return rtn;
	}

	@   Override
	public Map<String, Integer> delete(List<I   nteger> ids) {
		Map<String, Integer> rtns = new HashMap<Strin     g,      Integer>();
     		for(Integer i : ids) {
			Integ er rt n = delete(i);
			rtns.put(i.toString(), rtn);
		}
		return rtn      s;
	}
	@Override
	public Integer delete(Str      ing f   ield, Object v alue) {
		Integer    rtn = 0;
		Str   ing sql = SQL_DELETE.re     place(D_FIELD, field).replace(D _VALUE, value.  toString()) + ";";
		showSQL(sql)  ;
		
		try    {
			Pre   par    edStatement pstmt = getCon    n(). prepare    Statement(sql);
			List<Object> val = ne  w Array   List<Objec t      >();
			val.add(       val     ue);   
			DBkit.pstm   tSetValue(pstm   t, va   l);      
     			r    tn = pstmt.executeUp     date();
		} cat   ch (SQLExc  eption e) {
			e.printStackTrace();
		}   
		retur   n rtn;
	 }
	@Over    rid  e
	public     Map<String  , Integer> delete(Map<St   ring, String> param) {
		Map<    String, Integer> rtns = new HashMap<St       ring, Integer>();
		S   et<Entry  <Str     in  g, String>     > entry = para       m.entrySet();
		for (Entry  <String, String> e :     entr    y) {
			Integer rtn = delete(e.g            etValue(      )  , e.getKey());
			rtns.put( e.getKey(), rt       n);
		}
 
		return rtns;
	}
	  
	// update
	/////////////////    ////////////////////////////////////    //  ////////////////
     
	@SuppressWarnings("u       nc   hecked")
	@Override
	public  Integer u  pdate() {     		
		return                  update((T) this);
	}
	@Override
	public     Integer update(T bean,  String field) {

		M   ap<String, Object> values =   getValues((T)bean);
		Integ   er   rtn = 0;
		String id_val = "";                                                            //  primary ke  y     
		StringBuff   er sql_set = new StringBuffer();
		List<O   bje  ct> sql_key =      new ArrayLis     t<Object>();  
	 	Set<Entry<String, Object>> entry = values.entrySet()   ;
		Integer len = entry .size()-1;  
		for(Entry<String, Object> e : entry) {   
			if(e.getKey().equalsIgn    oreCase(field)) {
				id_val = e.getValue  ().toString();
			} else {                                                 //    ä¸/ä¸è½æ´æ°ä¸»é®
				String k   =      e .getKey(  );
				sql_set.ap   pend(k +"   =?");
				sql_k  e y.add(values.get(k));
		      		if(--len >= 1) {
					sql_set.append(", ");
				}
			}
		   }
		String sql = SQL_UPDATE + sql_set.toSt    ring() + " WHERE "+ field +"='"+ id_val +"' ;";
		showSQL(sql);
		
     		 PreparedStatement pstmt;
		try {
			pstmt = getConn().prepar    eStatement(sql);
			
			DBkit.  pstmtSetValue(pstmt, sql_key);
			
		  	rtn = pstmt.executeUpdate();
		} catch (SQLEx     cept    ion e1) {
			// TODO Auto-ge    nerated c   atch block
			e1.printStackTrace();
		}
		return r tn    ;
	
	}

 	@Override
	public    Map<   Str       ing, Integer> update(List<T> bea ns, String field) {
		Map<String, Integer> rtns = ne w HashMap<String, Integer>();          
		for(T t : beans) {
			int rtn = update(t, field);
			rtns.put(ClassReflect.getValue(t, field   ).toString(), rtn);
		}
		return r tns;
	}

	@   Override
	public Integer update(T bean)   {
		return upda  te(bean, "    id");
	}

	@Override
	public Map<String,     Int   eger> update(List<T> beans) {
		Map<String, Integer> rtns    = new HashMap<String, Integer      >();
		for(T t : beans) {
			Integ    er rtn = update(t);
			rtns.put(  getValue(t, "id").toString(), rtn);
		}

		return    rtns;
  	}

	// rea   d ( query )
	////////////////////////////////////////////////       ///////////////////////

	@Override  
	public T     get(Integer id) {
		fo      r   (T t    : get("id", id)) {			
			return t;
		}
		
		ret urn null;
	}

	@Override
	publ        ic List<T> get(String field,    Object value) {
		String sql = SQL_QUERY +" WHERE "+ field +    "='"+ va   lue +"' ;"; 
		showS      QL(sql);
		
		Resul  tSet rs = null;
		Map <String, Objec   t> val = new HashMap<String, Obje   ct>();
		List<T>      rtns    =   new ArrayList<T>();
		
     		Integer index = 0 ;
		Object[] arrName =            fieldNames.toArray();
		try {
			rs = getConn().p repareStateme          nt(    sql).executeQuery();
			while(rs.next()) {
				Integer count = fieldNames.size();
				for(Integer c=1; c <= count; c++) {
					if(i   n     dex >= count)
				     		     i  ndex       = 0;
		   			val.p      ut     (arrName[ind    ex++].toSt   ring()   .toLowerCase(), rs    .getString(c));
				}
				rtns.add(setterVal(val   ));
			 }
		} catch (  SQ       LExceptio n      e) {
			// TODO Auto-generated catch     block
			e.    printStackTrace();   
		}

   		return rtns;
	}

	/**
	 *  æ     ä¼ å¥ç Map   è½¬æ¢ä ¸ºä¸ä¸ª Reco     rdActive  å­å¯¹è±¡
	 * @param val   åå«å½åå­ç±»å¯¹è±¡ç å±æ§å¼çéå
	 * @return	          ä¸ä¸ªå½åå­   ç±»å¯¹è±¡å®å
	 */
	private T setterVal(Map<String, Object> val) {
		@SuppressWarnings("unche  c   ked")
		T b  ean = (T) ClassRefle        ct  .setterVal(beanClass, val);
		
		return bean;
	}
	/**
	 * æ Se  tter æ¹æ³æ¥æ¾å¯¹åºå­   æ®µ
	 * 
	 * @param method
	 * @return
	 */   
	@SuppressWarnings   ("unused")
	priva  te String existSetMethod(String m ethod) {		  
		String field = null;
		if(method.startsWith("set"))      {
			for (String f : fieldNames) {   
				if(method.toLowerCase().equalsIgnor   eCase("set"+ f.toLowerCase())) {				
					     field = f;   
					break;
				}
			}
		}
		
		return field;
	}
	@Override
	public List<T> get() {
  		List<T> rtns = new ArrayList<T>();
		String sql = SQL_QUERY ;
		showSQL(sql);
		
		ResultSet r     s = null;
		HashMap<String, Object> v   al = n   ull;
		Integer index = 0 ;
		Object  [] arrName = fieldNames.toArray();
		tr   y {  
			rs = getConn().prepareStatement(sq        l).ex       ecuteQuery();
			w       hile(rs.next()) {
				Integer count = fieldNames.size();
				val = n   ew HashMap<String, Object>();
		   		for(Integ       er c=1; c <= count; c++) {
		 			if(index >= count)
		    				index = 0;
					val.put(arrName[ind ex  ++].toString().toLowerCase(), rs.getString(c));
				  }
				rtns.add    (se  tterVal(val));
   			}
		} catch (SQ   LException e)   {
		       	// T    ODO Auto-ge   nerated catch block
			e.printStackTrace();
		}
 
		return    rtns;
	}

	   @Override
	public List<T> get(Map<      S  tring, Strin g> par ams) {
		StringBuffer sql_match = new StringB      uffer();
		
		Se            t<Entry<Strin              g,     Strin g>> entry = params.entrySet();
		Integer len = params.size();
		for(Entry<String, St       ring> e : entry) {
			     sql_            match.append(  e.getKey() +"='"+ e.getValue() +"' ");
			if(--len >= 1) {
				sql_m        atch.append(" AND ");
		  	  }
		}
		String sql = SQL_QUERY +" WHERE "+ sql_match.    t    oString() +" ;    ";
		showSQL(sql);
		
		List<T> rtns = new ArrayLis  t<T>();
		ResultSet rs = nul l;
		HashMap<String, Obj      ect> val = null;
		Integer index = 0 ;     
	  	Object    [] arrN ame = fieldNames.toArray();
		try {
			rs = getConn().prepareStat  ement(sql).exec      uteQuery();
         			while(rs.next())      {
				Integer count = fieldName  s.si   ze();
				val =     new HashMap<String, Object>();
				f   or(Integer c=1; c <=   count    ; c++)      {
	    				   if(index >= count)
						index = 0;
					val.put(arrName[index++].toString().toLowerCase(), rs.getString(c));
		      		}
				rtns     .add(  se            tterVal(val));
    	      		}
		} catch (SQLException e) {
			// TODO Aut      o-genera   ted catch            block
			e.printStackTr     ace(      );
		}
		r     eturn rtns     ;
	}

	@ O        ve     rride
	public List<T> get(Stri ng field, Set     <O   bject> pa             rams) {
    		List<T> rtns = new ArrayLis   t<T  >();
//		Set <Entry<String, String>>  entry =      params.   en  trySet();
		    for(Object e : pa r    am      s)  {
			T r     tn = get(field    , e.toStrin g()).get(0);
			rtns.add(   rtn);
		}
		return rtns;
	}
	
  	@Override
	public List<T> get(I  nteger start, Integer offset     ) {        
	  	String sql      =    SQ   L_QUERY +" LIMIT "+ s   tart +","+ offset;
	    	    showSQL(sql);
		
		List<    T> rtns   = new ArrayLi   st<T>();
		ResultSet rs = null    ;     
		Ha   shMap<Stri  ng, Objec  t> v   al = null;
		Integer index = 0 ;
		Object[] arrName = fiel  dName      s.toArray();
		try {
			r    s =     getConn    ().prepareStatement(sql).execute    Query();
			while(rs.next()) {
				Integer count = fieldNames.size(  );
		  		val = new HashMap<String, Object>();
				for(Integ   er c=1; c <= count; c++) {
					if(index >= count)
						index = 0;
					val.pu   t(arrName[index++ ].toString().toLowerCase(), rs.ge    tStri  ng(c));
				}
	     			rtns .add(setterVal(val    ));
			}
		} catch    (SQLException e) {
			//  TODO Auto-gener    a  ted c  atc    h block
			e.printStackTrace();
		}

		return rtns;
	}

	@Override
	public Page<T> pager(Integer cur rPg, Integer sizePg)    {
		Integer    totalPage = 0;
		Integer totalRow =     null;
 
		Long count    = count ();                             // åå¾æ»è     ®°å½   æ¡æ°
		if(count !=          null) {
			totalRow = Integer.valueOf(count.toString()); // æ»è®°å½æ¡æ°  
		} else {         
			totalRow = 0;
		}     
				

		if(sizePg <= 0) {								  // è®¾ç½®æ   »é¡µæ°
			    sizePg = 5;
		}
		totalPag  e = totalRow / siz  ePg ;
		if(totalRow % sizePg >= 1) {
			totalP  age ++ ;
		}

		if(currPg > t  otalPage)                                        // è®¾ç½®å½åé¡µ
			currPg = totalPage;
		
		Integer of fset =      sizePg;                                              // è®¾ç½®æ¥è¯¢æ°æ®åç§»é
		if(currPg * sizePg > totalRow) {
			offset = totalRow;
		}

		Integer   start = null;                                         // è®¾ç½®èµ·å§è®°å½
		start = currPg * sizePg - sizePg;

		List<T> s = get(start, offset);
		
		Page<T> pg = new Page<T>(s, currP    g, of    fset, totalPage, totalRow);

		return pg;
	}


	// other
	///////////////////////////////////////////////////////////////////////

	@Override
	public Long count() {
		L    ong count = null;  
		String sql ="SELECT COUNT(*) FROM "+ getTable() +" ; ";
		showSQL(sql);
		
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs = pstmt    .executeQuery();
			if (rs.next()) {
				count = rs.getLong(1);
			}
		} catch (SQLException e) {
			// TO  DO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T get(HttpServletRequest req, HttpServletResponse res) {
		return (T) BeanInjector.injectActiveRecord(beanClass, req, res);
	}
	
	
	/**
	 * æ¾ç¤º SQL è¯­å¥
	 * @param sql
	 */
	private void showSQL(String sql) {
		System.out.println("SQL :-)  "+ sql);
	}


}
