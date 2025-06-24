package  com.sidian.service.impl;

import   java.util.ArrayList;
import java.util.HashMap;
import     java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
   import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service  ;

import com.sidian.bean.TDefSk   u;
import com.sidian.bean.TDefS tore;
import com.sidian.bean.TFitting;
import com.sidian.bean.TSysUser;
import com.sidian.cfg.Configuration    Manager;
import com.sidian.dao.IMyBatisDao;
import com.sidian.exception.ResponseException;
import com.sidian.service.IApiService;
import com.sidian.util.ApiUtil;

@Service(value =   "userService")
public   class ApiServiceImpl implements IApiServi  ce {

	privat  e static   Logger logger = LogManager.getLogger(ApiServiceImpl.clas  s);

	@Autowired
	private IMyBatisDao d     ao;

	public void list() {

		List<Map<String, O bject>   > results = dao.listBySql   ("SELECT TOP 1000 [ID],[St  ore],[StoreName] FROM [" + Conf    igurationManager.getDbName() + "].[dbo].[TDefSt    ore]");

		// List<M  ap<String, Object>> results =
		// dao.li   stBySql("SELECT TOP 1000 [U    serName], [Password] FR OM [" + ConfigurationManager.getDbName() + "].[dbo].[TSysUser]");

		System.out.println(re   sults);
	}

	public TSysUser  login(  TSysUs  er user) {
	   	String sql = "SELECT   [UserId],[Store],[User    Nam   e] FROM [" + ConfigurationManager.g    et  DbNa  me() + "].[dbo].[è¯ç ©¿ç¨æ·è¡¨]    AS a WHERE a.UserNam         e  ='" + user.         get U   serName() + "'   and       a.Password='" + user.getPassword()
		         + "' and a.Store='" + user.getStore         () + "';"    ;

		List<Map<String, O  bject>> results = dao.listBySql(sql);
		if (res  ults.size() == 0) {
			throw new ResponseException("ç¨æ·    åæåå¯ç éè     ¯¯");
		}
		re  turn (TSysUser) ApiUtil.toEntity(results.get(0  ),  TSysUser.class);

	}

	public List<TDefStore> listSt ore() {
		List<Map<String, Object>> results = dao.listBySql("SELECT [ID],[Store],[S toreNa me      ]  FROM [" +   Config          urationManage     r.getDbName() + "].[dbo].[T  DefStore] WHER   E CLOSED=0");
		List<TDefStore> storeList = ApiUtil.toJsonList(results, TDefStore.class, null);

		List<Map<String,    Object>> accountResult            = dao.  listBySql("SELECT [Store] , [UserName] FROM [" + ConfigurationManager.getDbName() + "]. [ dbo].[è¯ç©¿ ç      ¨æ·è¡¨]");
		List<TSys    User> accountList = A    piUtil.toJsonList(accountRes    ult, TSysUse    r.class, nu    ll);   

		List<TDefStore> resultsList = new A    rrayList<TDefStore>();
		
		for (TDe   fStore store : store  List) {
			if (store.getStore() != nul  l && store.getStore(). toLowerCase().startsWith("z" )) {
				resultsList.add(store);
			 }
		}
		for     (TSysUser user : acc ountList) {
			  for    (TDefSto   re store : resultsList) {

				i   f (store.  g  etStore() != null && user.getStore() != null && store.getStore().equalsIgnoreCase(user.getStore())) {

					if (store.getSt ore().toLowerCase().startsWith("z")) {
						if (store.getAccount() == null) {
							List<String> accounts = new ArrayList<String>()  ;
  							accounts.a  dd(user.ge  tUse  rName());
							store.setAccount(accounts);
		  				} else {
							store.getAccount().add(user.getUserName());
  					  	}
					}
				}
	 		}

		}

		return resultsList;
	}
	
	public List<TDefSku> listSku(){
		
		List<Map<String, Object>> result      s = dao.listBySql("SELECT [Sku], [PName] FROM [" + ConfigurationManager.getDbName() + "].[dbo].[TDefSku  ]");
	     	return ApiUtil.toJsonList(resu         lt  s, TDefSku.class, null);

	}

    	public TDefSku checkSku(TDefS ku sku) {

		Str        ing sto     re = sku  .getS  tore();
		    String sql = "SELECT a.Sku, a.PName, a.Clr, a.    S   ize, b.Style   , b.Sty  leName, b.Attrib22 as isStarProduct,  b.Att  rib23 as isF  inger, b.SkuPri  ntMou    d as  remark  , b.SafetySort as de  scription FROM [" +    Configurat        i   onManager.getDbName() + "].[dbo].[TDefSku] AS a left join  [" + Confi  gurationManager.getDbName() + "].[dbo].[TDefStyle] AS b ON b.Style=a.Style        WHERE a.Sku=  '"
		        + sku.getSku() + "';";
		System.out.println(sql);
		List<Map<Stri  ng, Object>> resul     ts = dao.listBySql(sql);

		if (results.isEmpty()) {
			throw new ResponseException("æ¡å½¢ç ä¸å­å¨");
		}
		
		
		Map<String, Object> result = results.get(0);
		   if (result.get("isStarProduct") != nul    l    && result.get("isStarProduct").toString().equalsIgnoreCase("1")) {
			result.put("isStarProduct", true);
   		} else {
			  result.p    ut("isSta   rProduct", false);
		}

		if (resu lt.get("i           sFinger") != null && result.get("isFinger").toString().equal sIgnoreCase("1")) {
			re  sult.put("isFinger"    , true);
		} else    {
			result.p  ut("isFinger  ", fa      lse);
		}

		sku = (TDefSku)     ApiUtil.toEntity(resul ts.get(0), TDefSku.class);

		  
		String      sizeSql = "SELECT       a.Sku, a.Size, b.Size    Name FROM [" + ConfigurationManager.getDbName() + "].[dbo].[TDefSku] AS a lef   t join  [" + Configuratio  nManager.get  DbName() + "].[dbo].[T DefSize] AS b ON b.Size=a.Size     WHERE a.Style='"
   		          + sku.get     Sty   le() + "'      and a.Clr ='" + sku.getC    lr() + "'" + ";     ";
		
		List<Map<String, Object>> skuSizeList = dao.listBySql(sizeSql);
		
		System.out.println(sizeSql);
		List<TDefSku> skuList = ApiUtil.toJs  onList    ( skuSizeList,   TDefSku.class, null);

		Map<S  tring, Obje  ct> sizeMap = new HashM    ap<String, Object>()       ;

		   for (TDefSku  defs   ku : skuL ist) {

			String contSql = "SE   LECT a.Qty F    ROM [" + ConfigurationManager.getDbN   am   e() + "].[dbo].[TAccStock] AS a WHERE a.Sku='" + defsku.get   Sku() + "' and a.   Store ='" + store + "  '" + ";";
			List<Map<String, Object>> contSqlResults = dao.listBySql  (co    ntSql);

			if (contSqlResults.size() > 0) {
				sizeMap.put(de  fsku.getSizeName(), ApiUt     il.getInteger(contSq   lResults.get(0)    .get("Qty"), 0, false));
			}else{
				sizeMap.put(defsku.getSizeName(), 0);
			}

		}

		sku.setSizeMap(sizeMap);
		return sku;

	}
	
	publi   c int addFittings(List<TFitting> fittings){
		String sql = "INSERT INTO [" + ConfigurationManager.getDbName() + "].[dbo].[è¯ç  ©¿æ°æ®è¡¨](è¯ç©¿ç¼å·,Store,è¯ç©¿      æ¥æ,è¯ç©¿æ¶é´,å®¢äººç¼å·,å  ¹´é¾,æ¡ç ,æ¯å¦æä   º¤,æè§,éé¡¹1ä»·æ ¼,éé¡¹2é¢è²,éé¡¹3å°ºç ,éé¡¹4æ­é,éé¡¹5æ¬   ¾å¼,éé¡¹6å¶å®,UserName,æ¯å¦æ¨è) VALUES ";
		
		if (Ap        iUtil.isEmpty(fittings)) {
			throw new ResponseExce   ption("è¯è¡£è®°å½ä¸è½ä¸ºç©º");
		}
    		
		List<TFitting> inse  rtFittings = new      ArrayList   <TFitting>();
		List<    TFitting> update  Fittings    = new ArrayList<TFitting>();
		
		for(TFitting fitting: fi    ttings){
			String   selectsql = "SELECT a.è¯ç©¿ç¼å·  FROM [" + ConfigurationManager.g  etDbName() + "].[dbo].[è¯  ç©¿æ°æ®è¡¨    ] AS a WHERE a.è¯ç©¿ç¼å·='" + fitting      .g       etFittingCode()  + "';"     ;
			List<Map<String, Object>> r   esults =   dao.listBySql(sele     ctsql);
			
			if(results.si   ze() > 0){
				    upd   a  teFittin      gs  .add(fitting);
		  	}else{
				insertFittings.add(fit   ting);
			}
			
		}
		
		for(TFitting fitting: insertFittings){

			   String item = "('" + fitting.getFitti   ngCode()           + "'," 
					+  "'" + fitting.    g etStore() + "'," 
		     			+  "'" + f  itting.      getFittingD    ate() + "',"
					+  "'" + fitting.getF  itting Time()  + "',"
					+  "  '" + fitting.getCustomerCode() + "',"    
					+ fitting.getCusto     merAge() + ","
					+  "'" +      fi     tting.getSku   () + "',"
				   	+ fitting.getIsSaled() +    ","
   					+ "'"  + fitting.getFeedBack() + "',"
					+ fitting.getIsPriceOk() + ","
	  				+         fitting.getIsColorOk() + ","
					+ fitting.getI sSizeOk() + ","
					+ fitting.getIsSuitable() + ","
					+ fitti ng.getIsStyleOk() + ","
	    				+ fitting.ge   tIsOther Ok() + ","
					+  "'" + f  itting.getUserName() + "',"
					+ fitting.g        etIsRecommend        () + ")"; 
			
				 	this.dao.insert(sql + item + ";");
		}


		for(TFitting fitting: updateFitt   ings)    {
					
			 Str      ing updaetSql =  "UPDA  TE [" + ConfigurationManager  .getDbName() + "].[dbo].[è¯ç©¿æ°æ®è¡¨]       SET "
					 		+  "è¯ç©¿ç¼å·      ='" + fit   ting.getF  itting    Code() + "'," 
   					   		+  "Store=' " + fitting.getStore()   + "'," 
							+  "è¯ç©¿æ¥æ       ='" + fitting.getFittingDate() + "',"
							+  "è¯ç©¿æ¶é´='" +    fitting.getFittingTime() + "',"
							+  "å®¢äºº  ç¼å·='" + fitting.getCustomerCode() + "',"
			   				+  "å¹´é¾="    + fit    tin   g.getC   ustomerAge() + ","
							+  "æ¡ç ='" + fitting.g    etSku() + "',"
		     					+  "æ¯å¦æäº¤=" + fitting.    getI   sSal  ed() + ","
			   				+  "æè§='" + fitting.getFee    dBack() + "',   "
							+  "éé¡¹1ä»·æ ¼=" + fitting.getIsPriceOk() + ","
							+     "éé¡¹2   é¢è  ²=" + f    ittin g.getIsColorO    k() + ","
							+    "éé¡¹3å°ºç =" + fittin      g.getIsSi  zeOk () + ","
							+  "éé¡¹4æ­é=" + fitting.getIsSuitable()    + ","
							     +  "éé¡¹5æ¬¾å¼   =" + fitting.getIsStyleOk() + ","
							+  "éé¡¹6å¶å®=" + fitting.getIsOtherOk() + ", "
							+  "UserName    =    '" + fitting.getUserName() + "',"
							+  "æ¯å¦æ¨è=" + fitting.getIsRecommend () + ""
      							+ " WHERE è¯ç©¿ç¼å·='" + fitt  ing.getFittingCode()  + "';"; 
			 
			 dao.update(upda  e  t Sql);    
				   	   	
		}
   		
		return 0;
		
	}
	
	    
	public int deleteFittings(List<     TFitting> fittings) {

	                	String sql = "DE  LETE FROM [" + ConfigurationManager.getDbName() + "].[dbo].[è¯ç©¿æ°æ®è¡¨] WHERE è¯ç©¿ç¼å· in ("    ;

		if   (Api  Util.isEmpty(fittings)) {
			throw new    ResponseException("è¯è¡£è®°å½ä¸è½ä¸ºç©º");
		}

		int i = 0;
		String item   s = "";
		for (TFitting fitting : fittings) {
			     String item = "'" + fitting.getFittingCode() + "'";
			i++;
			if (i == fittings.size()) {
				items = items + item + ")";
			} else {
				items = items + item + ",";
			}
		}

		if (fittings.size() > 0) {
			sql = sql + items;
			System.out.println(sql);
			this.dao.delete(sql);
		}

		return 0;

	}
}
