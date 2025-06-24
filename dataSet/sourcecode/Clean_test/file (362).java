package com.sidian.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sidian.bean.TDefSku;
import com.sidian.bean.TDefStore;
import com.sidian.bean.TFitting;
import com.sidian.bean.TSysUser;
import com.sidian.cfg.ConfigurationManager;
import com.sidian.dao.IMyBatisDao;
import com.sidian.exception.ResponseException;
import com.sidian.service.IApiService;
import com.sidian.util.ApiUtil;

@Service(value = "userService")
public class ApiServiceImpl implements IApiService {

	private static Logger logger = LogManager.getLogger(ApiServiceImpl.class);

	@Autowired
	private IMyBatisDao dao;

	public void list() {

		List<Map<String, Object>> results = dao.listBySql("SELECT TOP 1000 [ID],[Store],[StoreName] FROM [" + ConfigurationManager.getDbName() + "].[dbo].[TDefStore]");

		// List<Map<String, Object>> results =
		// dao.listBySql("SELECT TOP 1000 [UserName], [Password] FROM [" + ConfigurationManager.getDbName() + "].[dbo].[TSysUser]");

		System.out.println(results);
	}

	public TSysUser login(TSysUser user) {
		String sql = "SELECT [UserId],[Store],[UserName] FROM [" + ConfigurationManager.getDbName() + "].[dbo].[试穿用户表] AS a WHERE a.UserName='" + user.getUserName() + "' and a.Password='" + user.getPassword()
		        + "' and a.Store='" + user.getStore() + "';";

		List<Map<String, Object>> results = dao.listBySql(sql);
		if (results.size() == 0) {
			throw new ResponseException("用户名或则密码错误");
		}
		return (TSysUser) ApiUtil.toEntity(results.get(0), TSysUser.class);

	}

	public List<TDefStore> listStore() {
		List<Map<String, Object>> results = dao.listBySql("SELECT [ID],[Store],[StoreName] FROM [" + ConfigurationManager.getDbName() + "].[dbo].[TDefStore] WHERE CLOSED=0");
		List<TDefStore> storeList = ApiUtil.toJsonList(results, TDefStore.class, null);

		List<Map<String, Object>> accountResult = dao.listBySql("SELECT [Store],[UserName] FROM [" + ConfigurationManager.getDbName() + "].[dbo].[试穿用户表]");
		List<TSysUser> accountList = ApiUtil.toJsonList(accountResult, TSysUser.class, null);

		List<TDefStore> resultsList = new ArrayList<TDefStore>();
		
		for (TDefStore store : storeList) {
			if (store.getStore() != null && store.getStore().toLowerCase().startsWith("z")) {
				resultsList.add(store);
			}
		}
		for (TSysUser user : accountList) {
			for (TDefStore store : resultsList) {

				if (store.getStore() != null && user.getStore() != null && store.getStore().equalsIgnoreCase(user.getStore())) {

					if (store.getStore().toLowerCase().startsWith("z")) {
						if (store.getAccount() == null) {
							List<String> accounts = new ArrayList<String>();
							accounts.add(user.getUserName());
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
		
		List<Map<String, Object>> results = dao.listBySql("SELECT [Sku], [PName] FROM [" + ConfigurationManager.getDbName() + "].[dbo].[TDefSku]");
		return ApiUtil.toJsonList(results, TDefSku.class, null);

	}

	public TDefSku checkSku(TDefSku sku) {

		String store = sku.getStore();
		String sql = "SELECT a.Sku, a.PName, a.Clr, a.Size, b.Style, b.StyleName, b.Attrib22 as isStarProduct,  b.Attrib23 as isFinger, b.SkuPrintMoud as remark, b.SafetySort as description FROM [" + ConfigurationManager.getDbName() + "].[dbo].[TDefSku] AS a left join  [" + ConfigurationManager.getDbName() + "].[dbo].[TDefStyle] AS b ON b.Style=a.Style WHERE a.Sku='"
		        + sku.getSku() + "';";
		System.out.println(sql);
		List<Map<String, Object>> results = dao.listBySql(sql);

		if (results.isEmpty()) {
			throw new ResponseException("条形码不存在");
		}
		
		
		Map<String, Object> result = results.get(0);
		if (result.get("isStarProduct") != null && result.get("isStarProduct").toString().equalsIgnoreCase("1")) {
			result.put("isStarProduct", true);
		} else {
			result.put("isStarProduct", false);
		}

		if (result.get("isFinger") != null && result.get("isFinger").toString().equalsIgnoreCase("1")) {
			result.put("isFinger", true);
		} else {
			result.put("isFinger", false);
		}

		sku = (TDefSku) ApiUtil.toEntity(results.get(0), TDefSku.class);

		
		String sizeSql = "SELECT a.Sku, a.Size, b.SizeName FROM [" + ConfigurationManager.getDbName() + "].[dbo].[TDefSku] AS a left join  [" + ConfigurationManager.getDbName() + "].[dbo].[TDefSize] AS b ON b.Size=a.Size WHERE a.Style='"
		        + sku.getStyle() + "' and a.Clr ='" + sku.getClr() + "'" + ";";
		
		List<Map<String, Object>> skuSizeList = dao.listBySql(sizeSql);
		
		System.out.println(sizeSql);
		List<TDefSku> skuList = ApiUtil.toJsonList(skuSizeList, TDefSku.class, null);

		Map<String, Object> sizeMap = new HashMap<String, Object>();

		for (TDefSku defsku : skuList) {

			String contSql = "SELECT a.Qty FROM [" + ConfigurationManager.getDbName() + "].[dbo].[TAccStock] AS a WHERE a.Sku='" + defsku.getSku() + "' and a.Store ='" + store + "'" + ";";
			List<Map<String, Object>> contSqlResults = dao.listBySql(contSql);

			if (contSqlResults.size() > 0) {
				sizeMap.put(defsku.getSizeName(), ApiUtil.getInteger(contSqlResults.get(0).get("Qty"), 0, false));
			}else{
				sizeMap.put(defsku.getSizeName(), 0);
			}

		}

		sku.setSizeMap(sizeMap);
		return sku;

	}
	
	public int addFittings(List<TFitting> fittings){
		String sql = "INSERT INTO [" + ConfigurationManager.getDbName() + "].[dbo].[试穿数据表](试穿编号,Store,试穿日期,试穿时间,客人编号,年龄,条码,是否成交,意见,选项1价格,选项2颜色,选项3尺码,选项4搭配,选项5款式,选项6其它,UserName,是否推荐) VALUES ";
		
		if (ApiUtil.isEmpty(fittings)) {
			throw new ResponseException("试衣记录不能为空");
		}
		
		List<TFitting> insertFittings = new ArrayList<TFitting>();
		List<TFitting> updateFittings = new ArrayList<TFitting>();
		
		for(TFitting fitting: fittings){
			String selectsql = "SELECT a.试穿编号  FROM [" + ConfigurationManager.getDbName() + "].[dbo].[试穿数据表] AS a WHERE a.试穿编号='" + fitting.getFittingCode()  + "';";
			List<Map<String, Object>> results = dao.listBySql(selectsql);
			
			if(results.size() > 0){
				updateFittings.add(fitting);
			}else{
				insertFittings.add(fitting);
			}
			
		}
		
		for(TFitting fitting: insertFittings){

			String item = "('" + fitting.getFittingCode() + "'," 
					+  "'" + fitting.getStore() + "'," 
					+  "'" + fitting.getFittingDate() + "',"
					+  "'" + fitting.getFittingTime() + "',"
					+  "'" + fitting.getCustomerCode() + "',"
					+ fitting.getCustomerAge() + ","
					+  "'" + fitting.getSku() + "',"
					+ fitting.getIsSaled() + ","
					+ "'"  + fitting.getFeedBack() + "',"
					+ fitting.getIsPriceOk() + ","
					+ fitting.getIsColorOk() + ","
					+ fitting.getIsSizeOk() + ","
					+ fitting.getIsSuitable() + ","
					+ fitting.getIsStyleOk() + ","
					+ fitting.getIsOtherOk() + ","
					+  "'" + fitting.getUserName() + "',"
					+ fitting.getIsRecommend() + ")"; 
			
					this.dao.insert(sql + item + ";");
		}


		for(TFitting fitting: updateFittings){
					
			 String updaetSql =  "UPDATE [" + ConfigurationManager.getDbName() + "].[dbo].[试穿数据表] SET "
					 		+  "试穿编号='" + fitting.getFittingCode() + "'," 
							+  "Store='" + fitting.getStore() + "'," 
							+  "试穿日期='" + fitting.getFittingDate() + "',"
							+  "试穿时间='" + fitting.getFittingTime() + "',"
							+  "客人编号='" + fitting.getCustomerCode() + "',"
							+  "年龄=" + fitting.getCustomerAge() + ","
							+  "条码='" + fitting.getSku() + "',"
							+  "是否成交=" + fitting.getIsSaled() + ","
							+  "意见='" + fitting.getFeedBack() + "',"
							+  "选项1价格=" + fitting.getIsPriceOk() + ","
							+  "选项2颜色=" + fitting.getIsColorOk() + ","
							+  "选项3尺码=" + fitting.getIsSizeOk() + ","
							+  "选项4搭配=" + fitting.getIsSuitable() + ","
							+  "选项5款式=" + fitting.getIsStyleOk() + ","
							+  "选项6其它=" + fitting.getIsOtherOk() + ","
							+  "UserName='" + fitting.getUserName() + "',"
							+  "是否推荐=" + fitting.getIsRecommend() + ""
							+ " WHERE 试穿编号='" + fitting.getFittingCode()  + "';"; 
			 
			 dao.update(updaetSql);
						
		}
		
		return 0;
		
	}
	
	
	public int deleteFittings(List<TFitting> fittings) {

		String sql = "DELETE FROM [" + ConfigurationManager.getDbName() + "].[dbo].[试穿数据表] WHERE 试穿编号 in (";

		if (ApiUtil.isEmpty(fittings)) {
			throw new ResponseException("试衣记录不能为空");
		}

		int i = 0;
		String items = "";
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
