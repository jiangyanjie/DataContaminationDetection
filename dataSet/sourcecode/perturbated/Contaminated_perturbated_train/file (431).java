package    org.record.avtice;

import java.lang.reflect.Field;
import     java.lang.reflect.InvocationTargetException;
im     port java.lang.reflect.Metho   d;
import java.sql.Connectio n;
import java.sql.DriverManager;
import java.sql.PreparedStateme     nt;
import java.sql      .ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
im port java.util.Map;
import java.util.Map.Ent  ry;
import java  .util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResp     onse;

import org.record.avtice.i.IDBConn;
import org. record.avtice.i.IRecordActive;
import org.record.avtice.util.BasicType;
import org.record.avtice.util.BeanInjector;
import org.re cord.avtice.util.ClassReflect;
import org.record.avtice.util.DBkit;

import com.jfinal.plugin.activerecord.Page;

/**
 * <b>å¯¹äºç»§æ¿æ­¤ç±»çææå­ç±»</b>
 * <b>è¢«è§ä¸ºè¦æä¹åçå­æ®µå·å¤å¦ä¸ç¹ç¹ï¼</b  >
 * <li/> å¨è¯¥å­ç±»ä¸­å£°ææè¯¥å­æ®µ
 * <li/> å¨è¯¥å­ç±»ä¸­æ   ç¸åº   sett          er/ge   tter  æ¹   æ³                      <br/> <br/>
 * <b>æ»¡è¶³æ­¤æ¡ä»¶çé½å°è¢«è§ä¸ºéè¦æä¹åçå­æ®µ</b>       <br/   >
 * <b>å­æ®µå  ä¸º ID çå­æ®µé»è®¤ä¸ºè¡¨ä¸»é®ï¼ç³»ç»èª             å¢é¿(å¨æä     ¹å bean å¯¹è±¡æ       ¶ï¼ä¸ä¼æä¹åæ­¤å­æ®µ)</   b >    <br/> 
 * 
 * @author searchjack
 *       
 * @param       <T>
  */
pub   lic     class Active       Rec   ord<T>  extends ATable<T> implements      IRecordActive<T>  , IDB    Conn {
	 
	 
	/////////  SQL åæå     ////////
	static final String TABLE =    "{t   able}";
	static   f  inal String S_FIELDS   = "{fields}   ";
	static fin  al Stri  ng S_ VALUES = "{values}";
	static final String D_FIE    LD =  "{field}";
	static  final String D_VALUE  =    "{value}";
	s   tatic String SQL_INSERT;
	stat   ic String SQL_DELETE;
	static String SQL_UPDATE;
	static      String SQL_QUERY;

	publ  ic ActiveRecord() {
		table = getTable    Name();
		fieldNames =  new Has       hSet<String>      ();
		fields = getFi   elds(  );
		
  //		pr();
		
		SQL_INSERT = "INSERT INTO "+ table +"("+   S_FIELDS +") VALUES( "+   S_VALUES +" )";
		SQL_DEL   ETE  = "DELETE F RO  M "+ table +" WHERE "+ D_FIELD +"="  + D_ VAL    UE;
		SQL_UP  DATE = "UPDATE "+ t abl     e +" SE   T              ";
		SQL  _QUERY = "    SELE   CT "+ arryToStr() +" FROM "+ table +   " ";
		
	}
	
/*	priva    te v    oid pr() {
		Sy  stem.out.println("field     Names : ");
		for(String s : fieldNames) {
			System.out.print("    - "+ s);   
		}
		System.out.println("fields     : ");
		Set<Entry<String, String>> entry = field       s.entrySet();
		for(Entry<String, String> e : entry) {
			System.out.println(e.getKey() +" - "+ e.get  Value());
		}
	}*/
	private String getTableName() {
//		a - 97
//		z - 122
//		A - 65
//    		Z - 90
		StringBuffer tab = new StringBuffer();
//		System.ou     t.p   rintln(getShortName(this.getCla  ss().   getName()).toCharAr      ray());
//		char[    ] beanNameArr = getShortName(this.    getClass().getName()).toCharArray  ();    
		    String beanNameArr = ge  tShortName(this.getClass().getName());
		Boo     lean isTheFirstCh = t  rue;
		for(Characte      r c : beanNameArr    .t   oChar  Array()) {   
			if(isTheF  irstCh) {
	  			isTheFirstCh = false;
				tab.ap      pend(Character.toLowerCa    se(c));
			} else {
				int intc = (int      )c;
				if(intc >= 65
						&& int      c <=90) {    // è¥æ­¤å­æ¯æ¯å¤§å
		  			tab.append("_"+ Character.toLowerCase(c));
				} else {
	   	 			tab.append(c);
				}
			}
		}
//		System.out     .println("tab name : "+ tab.toString());
		return tab.toString   ();		
	}
	private S    tring arryToS   tr() {
		String    Buffer   fs = new StringBuffer();
		Inte ger len = fieldNames.size();
		for(String f : fieldNames) {
			fs.append(f);
      			if(--len >= 1) {
				fs.app   end(",");
	    		}
		}
		
		return   fs.toStri   ng();
	}

	


	///////////    ///////////////////////////////////////   /////////////////////
	//                å©ç¨ reflect ä» bean è         ·åè¡¨    åå®¹   --  ä½ æ     éå¨æ
	  ///////////////     /////////////// /////////////////////////////////////////
	/**
	 * è·åå½åç±»çå­ç±»çææå·æ Getter|Setter æ¹æ³äºå­æ®µ 
	 *   /
	@Overrid      e
	public Map<String, String> getFields() {
		Map<Str  ing, String> fields =     new Hash      Map<Strin            g, String>();
		    @SuppressWarnings("rawtypes")
		Class c = this.getClass();
  		Field[           ] df = c.getDecla  redFields();
		for (Field f :    df) {
			if(       e     xistGette  rSetter(f.getN  ame())) {                      //  å­å¨ getter/setter çå­æ®µæ è¢«è§ä½é è¦æ   ä¹åçå­æ®µ
				     fields.put(    f.getNam  e(), getShortName(    f.getType().toStr  ing()));
			       	setFieldNames(f.getNam  e() );   
			}
		}
		return fields;
	}
	static Method[] ms     = null;
	private Boolean      exi   stGetterS    etter(String fi  eld) {
	 	Boolean get = false;
		Boolean set = false;
		Boolean res = false;
		if(ms == null)
			ms = this.getClass().g    e tMethods();
		
//		Sys tem.out.println("    - "+ fie       ld  ) ;
		
		for(Method m : ms) {

//			System.out.println("*** "+ m.getName() +" - "+   "get"+ field);
//			    System.out.println("*** "+ m.getName());
 			
			if(get == false
     					&& m.getName().equalsIgnoreCase("get"+ field)) {
				
// 				System.out.println("=      get");
				
  				get = true;
			}
			if(set == false
					&& m.getName().equalsIgnoreCase("set"+ f ield)) {
				
//				System.out.println("= set");
				
				set = true;
			}
			if(set && get) {
				
//				System.out.println("==");
				
				return true;
			 }
		}
		return  res;
	}
	private void setFieldNames(String fName){
		String fn = ClassReflect.getFieldName(fName);
		fieldNames.add(fn);
	}
	privat   e S      tring getShortName(String longName) {
//		System.out.println("go --- " + longNam   e);   
		Stri  ng[] splitN = longName.s    plit("\\.");
		I   nteger index =    splitN.length   - 1;
		return   spli    tN[index];
	}
	
	/**
	 * è·å<b>æå®   å­æ® µ<b/>å¨å½åç±»çå­ç±»ä¸­ç<b>ç±»å<b/>
	 */
	@Override
	public String getType(String     fi    eldName   ) throws SecurityException,     No    Such   FieldException {
		Fiel    d f    =    this.getClass().g   etDeclaredField(field     Name);
		    return g  etSho  rtName(f.getType()     .toString());
	}

	
	@Over    ride
	publ  ic Object getValue(T bean    , String    field) {
		Object    rtn = null;
	            	Method[] ms = bean.ge tClass().getDeclare dMethods();
		for(Method m   : ms) {
		   	String n      ame = m.getName();
              			Integer params = m.g       etParameterTypes() .length    ;
			if(name.startsWith("     get")
   					&& pa    rams      <= 0) {                                                     /     / if it's getter    method    without parame        ters
				String type = ge   t   ShortName(m.  getRetu  rnTyp   e().toString()    );
	     			  if(Ba      sicType.Con    tain(type)) {                                         // if th  e method   return a basic   type value
		                   			if(    na   me.equalsIgnoreCase    ("    get"+ field)) {                 // if      the the       class conatin the field
						try {
							rtn   = m.invoke(bean);
     						} catch (IllegalArgumentEx    ception e) {
							// TODO Auto-ge   nerated   ca  tch block
							e.printStackTrace();
	      					} catch (Il      le galAccessException e) {
						  	// TODO Auto-generated catch blo       ck
   							e.printStackTrace();
	   					} catch (I nvocationT           argetException e) {
		     					// TODO Auto-generated catch bl ock
						        	e.printStackTra  ce();
						}
					}
				}
			}
		}
		r eturn r   tn;
	}
	@O   verride
	public Map<S  trin  g, Object> get     V    a     lues(T      b  ean) {
		Map<String, Obje       ct> values        = ne w HashMap<String, Objec t  >();
	     	if(ms == null)
			ms = bea    n.getClass().getDe   clare   dMethod   s();
		f  or(Method    m : ms) {
			String mName = m.getN  ame();
			Integer params   = m.getParameter      Types().length;
			if   (mName.startsWith("get")
					&& params <= 0)  {                                      // if it's                    getter method     without para   meters
				
//				S     ys   tem.out.println(" - "+ m.getName() +" - "+ p  arams);
				
				S  tring type = getShortName(m.getReturnType().toSt        ring());
		    		if(BasicType .Contain(type)) {                                       // if the method return a basic type value
					String existFieldNa        me = existGetMethod(mName);
					
//				   	System.out.println(" - "+ existGetterSetter(mName.substring(3)));
		      			
					i     f(existGetterSet te  r(mName.substring(3))
							&&   existFieldName !=    nu   ll  
							&& existFi       eldName.length     ()            > 0) {               // if the the class conatin the field
						
//						Syst  em.out.pri ntln(" - "+ type     );
						
						try {
					  		Obj   ect val = m.invo    ke(  be  an);
							values.put(existFieldName, val);
						} catch (IllegalArgum     entException e) {
	  						// TODO Au    to-generated catch block
							e.printStack     Trace()  ;
						} catch (Ille    galAccessException e) {
							// TODO Auto-gen       erated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Aut o-generated catch block
							e.printStackTrace();
						}
			    		}
				}
			}
		}
		retu   rn values;
	}
	priv     ate St       rin   g existGetMethod(Strin   g met     hod) {
		String field  = null;
		for (Strin     g f : fieldNames) {
//			System.out.println(met    hod.toL   owerCase()    +" -    "+ "get"+ f.toLow    erCase());
			   if(method.toLowerCase().equalsI gnoreCase("get"+ f.toLowerCase())) {			    	
			  	field = f;
				br     eak;
			}
		}
		return field;
	}


	
	    /////////////   /////////    ///////   //////////////////////////////////////////
	/ /             b    elo ng t   o ATable           --  ä½   æ éå¨æ
	/////////////////////////////////////////////   //////////////////////////
	public S    tring ge  tTable() {      // å¯æ   ä¸è¦
		return t  able;
	}
      	
	
	  //////////////////////////////////////////  /   ////////////////////// //////
	//                  belong to IDBconn   --  ä½ æ éå¨æ
	///////////////////      /////////////////////    ///////////////////////////////
	//////////   ////////////////////            ///////   /////////////////////////////   // ///
	//             è·åä¸ä¸ª   java.sql.Connection   --  ä½ æ éå¨æ
	/      //////  //////////////////////      ////////////////////////////////////////    //
	static C   onnection conn = null;     
	public Connection get      Conn() {
		if(conn ==    null)             {
			try {
				Class.forName(DRIVER);
				c  onn = DriverM  a    nager.getConnectio   n("jdbc:mysql://"+ HOSTNAME +":"+ PORT +"/"+ DBNAME, USERNA   ME, USERPWD);
			} catch (ClassNotFoundE xception e) {
				e.printStackTrace();
			} catch (S   QLException e) {
				e.printStackTr          ace(     );
			}
			return conn;
		}
		return conn;
	  	
		// Deploy to Appf  og
//		return DBkit.getConn();

	}
	/**
	 *      å³é­æ°æ®åºè¿æ¥
	 */
	public void closeConn( ) {
		try {
			if(conn != null
					&& !conn.isClosed() ) {
				conn.close();
			}
		      } catc    h (SQLE    xception e) {
			e.print   StackTrace   ();
		}
	}
	
	////////////////////    /////////////////////// ////////////////////////   ////
	//                           C R U D  -- ä½ æéè¦ç
	/////////////////////////////   //////////////////////// ///////////////  ///

	// save
	///////////////     ////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	@Override
	public Integer save() {
		
//  		Syst   em.out.println("b.save();          -- "+this  .get   Cl       ass    ()+" 344 line");
		
//		Map<String     , Object> values = ClassReflect.getValues(this);
//		Object bean = Class  Reflect.setterVa     l   (this.getClass(), values);
//		return save((  T)bean);

		return save((T) this);
	}
	@Override
	public Integer save(T bean) {
		Int    eger rtn = 0;
		Ma    p<String, Objec   t> values = ClassReflect.getValues(bean);   
		
//		System.out.println(ClassR  eflect.getValue(bean, "id"));
/    /		S  ystem.ou   t.println(ClassReflect.    g   etValue(bean, "title"));
//		System.out.println("s : "+ values.size());
		
		Set<Entry<Stri ng, Object>> entry = values.entrySet();
		
		   StringBuffer sql_fields =         new StringBuffer();
		S  tringBuffer sql_values = new StringBuffer();
		I  nteger len  = entry.size();
		for(Entry<String, Object> e : entry) {
		      	
//			System.out.println("field : "+ e.getKey()  +  "  value : "    + e.getValu e()   );
	      		
			if(e.getKey().equalsIgnoreCase("id")) {          // not s     ave or update the 'id' field
				sql_    fields.append("id");
				sql_values.append("null");
				if     (--len >= 1) {
					sql_fields.append(",");
					sql_values.append("     ,");
				}				
			} else {
				sq l_fields.append(e.getKey());
				sql    _values.append("'"+ e.getValue() +"'");
				if(--len >=  1    ) {
					sql_fields.appen  d(",");
					sql_values.append(",");
				}
			}
		}
		
  		String sql = SQL_INSERT.replace(S_VA  LUES, sql      _values.toString()).replace(S_               FIELDS, sql_fields.toString()) + ";";
		showSQL(sql);
		     try         {
			PreparedStat ement pstmt = getConn().prepareStatement(sql);
			rtn = pst   mt.executeUpdate();
       		} catch (SQLException e1) {
			// TODO Au  to-generated catch block 
			e1.printStackTrace();
		}
		return rtn;
	}

	@Override
	public Map<Str    ing, Integer> save(List<T> be     ans) {
		Map<String   , Integer> rtns =    new HashMap<St    ring, Inte  ger>();
		for(T t : beans) {
			Integer rtn = save(t); 
			String id      = getValue(   t, "id").toString();
			rt ns.put(id, rtn);
		}
		return rtns;
	}

	@SuppressWarnings("unchecke    d")
	@Override
	public Integer saveOrUpdate(Class<T> bean) {
		Obje     ct   id     = ClassReflec      t  .getValue(bean, "id");
   		if(       id != null) {
			Integ   er intId = Integer.val ueOf(id .toString());
			if(intId > 1) {             // è¯¥ä ¸»é®å·²ç»å­å¨ï¼    æ    §è¡æ´æ   °æä½
				return update((T) bean)    ;
			} else {                // åå»ºè®°å½
				return save((T) bean);
			}
		}

		return 0;
 	}

	    // delete
	//     /////////////////////////////////////   ////////     ///////////////////// ///

	@   Override
	pu    blic Integer delet e() {
		String id   = null;
		id = ClassReflect.getValue(this, "id").toString();
		re  turn delete(id);
	}
	@Override
	public Integ       er delete(T bean) {
		String val = ClassReflect.getValue(bean, "id").toString();
		Integer rtn = delete(val);
		return rtn;
	}
	@Override
	publi c Inte ger delete(Integer id ) {
		return del ete(id.toString());
	   }
	@Override
	public Integer delete(String id) {
		Integer rtn = 0;
		String sql = S QL_DELETE.repl  ac        e(D _FIELD        , "id   ").replace(  D_        VALUE, id) + "   ;"    ;
		showSQL(sql    );
		
		try {
			rtn = getCo   nn().prepareStatement(sql).executeUpdate();
		} catch (SQLException e) {
			// TODO  Auto-generated catch block
			e.printStac     kTrace();
	  	}
		return rtn;
	}

	@Override
	public Map<S  tring     , Integ           er> delete(List<Integ    er> ids) {
		Map<String, Intege  r   > r t   ns =    new Has   hMap<String,        Integer>();
		for(Integer i : i  ds) {
			Integer rtn = delete(i);
			rtns.put(i.toString(), rtn)   ;
		}
		return rtns;
	}
	@Overr  ide
	public Integer  delete(    String field, O   bject value     )    {
		Integer rtn = 0;
		Stri  ng sql = SQL_DELETE.replace(D_FIELD, field).replace(D_V   ALUE,     value.t   oString())        + ";";
		s   howSQL(sq      l);          
		
		tr   y {
			Pre  paredStatement pstmt = getConn().p repareStatement(sql);
			List<Object> val = new A rrayList<Object>(   );
			val.add(   value);
			DB         kit.pstmtSetValue(pstmt, val);
			rtn = pstmt.executeUpdate()    ;
		}    catch (SQLException e) {
			e.prin   tStackTrace();
		}   
		return rtn;
	}
	@     Override
	public Map   <String, Inte ger> delete(Map<String, String> para   m)   {      
		Map<Str      ing  , Integer> rtns = new     HashMap<String   , Integer>();
		Set  <Entry<String,      String>> e  nt     ry = param.entrySet();
		fo r (Entry<String, String> e : entry) {        
			Integer rtn =   delete(e.getValue     (), e.getKey(   ));
			rtns.put(e.get      Key(), rtn);
	   	}

		return rtns;
	     }
	
	// updat   e
	////////////////////////////   /////////////////      //////////////////////////

	@SuppressWar     nings(  "unchecked")
	@Override
	public In   teger u    pdate() {		
		return update((T) this);
	}  
	@Overr  ide
	p   ubl   ic In          t   eger update(    T bean, String fiel     d    ) {

		Map<String, Object> values = getValues((T)bean);
		In    te   ger rtn = 0;
		String id_val = "";                                                                        //  primary key
		StringBuffer        sql_se    t = new StringBuffer();
		Li   st<Object> sql_key = new ArrayList<O   bject>();
		Set<En  try<String, Object>> ent   ry = val ues.entrySet();
		Integer len = en  try.size()-1;
		for(Entry<String, Object> e :     entry) {
			if(e.getKey().equals   IgnoreCase(  fie     ld)) {
				id_va  l = e  .getValue().toString();
			} else {                                                                 //  ä¸/ä¸è½æ´æ°ä¸»é®
				String k =   e.getK       ey();
				sql   _set.append(k     +"=?");
				sql_key.add  (values.get(k));
				if(--len >= 1) {  
					sql_set.append(", ");
				}
			}
		}
		String sql = SQL_U      PDATE + sql_s     et.toString() + " WHERE "+ field +"='"+ id_   val +"' ;";
		showSQL(sql);
      		
		PreparedStatement pstmt;
		try {
			pstmt = getConn        ( ).prepareStatement(sql);
			
		     	DBkit.pstmtS       etValue      (pstmt, sql   _key);
		    	
	  		rtn = pstmt.executeUpdate();
		} catch (SQLExceptio   n e1) {
			// TODO Aut  o-generated catch bl    ock
			e1.printStackTrace();
		}
		retu  rn rtn;
	
	}

	@Override
	publ   ic Map<String, Integer> update(Li     st<T> bean s, String field) {
		Map<String, Inte ger> rtns = new HashMap<String, Integer>();
		fo    r(T t :      beans) {
			in t rtn = update(t, field);
			rtns.put(ClassReflect.getValue(t, field).toString(), rtn);
		}     
		r   eturn rtns;
  	}

	@Override
	public Integer update(T bean) {
		return update(bean, "id");
	}
    
	@Override
	public Map<String, Integer> update(List<T> beans) {
		Map<String, Integer> rt    ns = new HashMap<String, Integer>();
		  for(T t : beans) {
       			Integer rt   n = update(t);
		    	rtns.put(getValue(t, "id").to      String(), rtn);
		}   

		return rtns;
	}

	// read ( que      ry )
	//////////////////////////////////////////////////     /////////////////////   

	@ O     verride
	public T get(     Integer id) {
		for(T t : get("id", id)) {			
			return t;
		  }
		
		return null;
	}

	@Override
	public List   <T> get(String field, Object value) {
		String sq l = SQ   L_QUERY +" WHERE "+ field +"='"+ v     alue +"' ;"; 
		showSQL(sql);
		
		ResultSet rs = null;
		Map<Stri ng, Obje  ct> va     l = new Hash     Map<String,  Object>();
		List<T>     rt ns = new ArrayList<T>();
   		
		Integer index = 0 ;
		Object[]     arrName       = fieldNa     mes.toArray();
		t           ry {
			rs = getConn().prepareStatement(sql).executeQuery();
			whil    e( rs.next()) {
				Integer count = fiel  dNames.size();
				   for(Inte    ger c=1;  c <= count;  c++) {
					if(index >= count)
				    		ind    ex =   0;
					   val.put(arrName[index++].toString().toLowerCase(), rs.getString(c));
				}
				rtns.add(setterVal(     val));
			}
		} catch (SQLE xception      e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rtns;
   	}

	/**
	 *  æä¼ å¥ç Map è      ½¬æ¢ä¸ºä¸ä¸ª RecordActive å­å¯¹è±¡
	 * @param val   åå«å½åå­ç±»å¯¹è±¡çå±æ§å¼çé     å
	 * @return	          ä¸ä¸ª   å½åå­ç±»å¯¹è±¡å®å
	 */
	private T setterVal(Map<String, Object>     val)     {
		@SuppressWarnings("      unch  ecked"  )
		T bean = (T) ClassRefl      ect.se           tterVal(this.ge  tCl  ass (), val);
		
		return bean;
	}
	    /**
	 * æ    Setter æ¹æ³   æ¥æ¾å¯¹åºå­æ®      µ
	 * 
	 *  @param met          hod
	 * @return
	 */
	     @SuppressWa      rnings("unused")
	private String    exi  stSetMet  hod(S tring method) {		
		 String field = null;
		i     f(method.star  tsWith("set")) {
			for (String f : fieldNa     mes) {
				if(method.toLowerCase().equalsIgnoreCase("set"+ f. toLowerCase())) {				
					fiel d = f;
					break;
				}
			}
		}
		
		return  field;
	}
	 @Override
	public List<T> g   et() {
		L   ist<T> rtns =    new ArrayList<T>();
		String   sql = SQL_QUERY ;
		showSQL(sql);
		
		Res ultSet rs = null;
		HashMap<String, Object> val = null;
		Integer index = 0 ;
		Object[] arrName = fieldNames.toArray();
		try  {
			rs = getCo     nn().pr     epareStatement(sql).executeQ  uery();
			while(rs.next(   )) {
				I        nteg      er count = fieldNames.size();
				val = new HashMa   p<Strin g, Object>();
				for(Integer           c    =1;     c <= count; c++) {
					if(index >= count)
						index = 0;
					val.put    ( arr   Name[index++].   toString().toLower   Ca         se(), rs.getString(c));
				}
			 	rtns    .a  dd(se    tterVal(val));
			}
  		} catch (SQLException e) {
			             // TODO       Auto-genera        ted catch b   lock
			e.printStackTrace();
		}
 
		ret  urn rtn   s;
	}

	@  Override
	p  ublic List<T> get(Map<Stri  ng, String> params) {
		StringB   uff      er      sql_match =   new StringBuff     er();
		
		Set<Entry<    Str     ing, String>> entry = param   s.entrySet(      );
		In    teger   len = pa           rams.size();
		for(Entry<String   , String> e : entry) {
			sql   _match.app end(e.getKey() +"='"+   e.getValue() +"' ");
			if(-  -len >= 1) {
				sql_match  .appen        d(   " AND ");
			}
		}
      		String    sql = SQL_QUERY +" WHERE "+ sql_match.toString() +" ; ";
		showSQL(sql);
	  	
		List<T> rtns = new ArrayList<T >();
		R    es  ul  tSet rs       =       nu  ll;
		HashMap<String  , Object> val = null;
		Integer index = 0 ;
		Object[] ar    rName = fieldNa     me    s.toA     rray();
		try {
			rs = getConn() .prepareSt    atement(sql).ex   ecuteQu    ery();   
			while(rs.next()) {   
				Integer count = fieldNames.size();
				val = new Has    hMap<String, Object>();
      				for(  Integer c=1; c <= co unt; c ++) {
		 			if(ind       ex    >= count)
						index = 0;
		 			val.put(arrName[index++].toString().toLowerCase(), rs.getString(c));
				}
				rtns.add(setterVal(val));
               			}
		}    catc     h (SQLException e)    {    
			// TOD          O Auto-generated catch block
			e.printStackTrace();
		}
	  	return rtns;
	}   

	@Over   ride
	p         ublic List<T>    get(String field, Set<Object> params) {
		      List<T> rtns =    new ArrayList<T>();
//		Set<Entry<String, String>> entry = params .entrySet();
		for(Object e            : params) {
			T rtn = get(field, e.    toString()).get(0);
			     rtns.add(rt   n);
		}
		return rtns;
	}
	
	@Override
	publi    c List<T> get(Integer start, Integer offset) {
		     Strin g sql = SQL_QUER Y +" LIMIT "+ start +","+ offset;
		showSQL(s   ql);
		
    	 	Li  st<T> rtns = new       ArrayList<T>();
		ResultSet rs = null;
		HashMa  p<String, Object> val = null;
		 Inte   ger index = 0 ;
		Object[] arrName = fieldNames.toArray( )        ;
		try {
			r      s = getConn().prepareStatement(sql).e       xe  cuteQuery();
			while(rs.next())  {
				Int      eger count = fieldNames.size();
				val =   new HashMap<  String, Object    >();
				for(Intege    r c=1; c <= count; c++) {
					if(index >= co      unt)
						index = 0;
			  		v   al.put(ar  rName [index++].toString().toLowerCase(),  rs.getSt    ring(c    ));
				}
		   		rtns.add(setterVal(val))     ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.prin tStackTrace ();
		}

		return rtns;
	}

	@Override
	public   Page<T> pa      ger(Integer currPg, Integer sizePg) {
		       Integer totalPage = 0;
		Integer tot    alRow = null;

		Long count = count();                                // åå¾æ»è®°å½æ    ¡æ°
		if(count != null) {  
			total   Row = Integer.valueOf(count.toStr  ing()); // æ»è®°å     ½æ¡  æ°
		} else {
			totalRow = 0;
		}
			      	

		if(si    zePg <=      0) {								  // è®¾ç½®æ»é¡µæ°   
			sizePg = 5;
		}
		totalPage = totalRow / sizePg ;
		if(totalRow % sizePg >= 1) {
			totalPage ++ ;
		}

		if(currPg > totalPage    )                                        // è®¾ç½®å½åé¡µ
			currPg = totalPa       ge;
		
		Integer o   ffset = sizePg;                                           // è®¾ç½®æ¥è¯¢æ°æ®åç§»é
		if(currPg * sizePg > totalRow) {
			offset = totalRow;
		}

		Integer start = nul   l;                                          // è®¾ç½®    èµ·å§è®°å½
		start = currPg * sizePg - sizePg;

		List<T> s = get(start  , offset);
		
		Pa    ge<T> pg = new Page<T>(s, currPg, offset, totalPage, totalRow);

		return pg;
	}


	// other
	///////////////////////////////////////////////////////////////////////

	@O   verride
	public Long count() {
		Long count = null;
		String sql ="SELECT COUNT(*) FROM "+ getTable() +" ; ";
		   showSQL(sql);
		
		try {
			PreparedStatement pstmt = getConn().prepareStatement(sql);
			ResultSet rs =    pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getLong(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T get(HttpServletRequest req, HttpServletResponse res) {
		return (T) BeanInjector.injectActiveRecord(this.getClass(), req, res);
	}
	
	
	/**
	 * æ¾ç¤º SQL è¯­å¥
	 * @param sql
	 */
	private void showSQL(String sql) {
		System.out.println("SQL :-)  "+ sql);
	}


}
