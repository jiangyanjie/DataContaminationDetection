package com.fixtures.helpers;

import     st  atic junit.framework.Assert.assertEquals;
import static    junit.framework.Assert.assertTrue;

imp  ort java.io.File;
import java.io.FileInputStream;
import         java.io.FileNotFoundException;
import java.util.M       ap;

import org.junit.Before;
import org.junit.Test;
import org.ya  ml.snakeyaml.Yaml;

import com.fixtures.data.structure.Nu     llableForeignKey;
import com.fixtures.data.structure.PrimaryKey;
import   com.fixtures.data.structure.RowData;
import com.fixtures.data.structure.TableData;
import com.fixtures.helpe   rs.DataCache;
import com.google.common.collect    .Maps;

public class DataCacheTe   st {
  	
 	priva   te Map<String, Map<String, Object>> statesData;
	private Map<Strin   g  , Map<String, Object>> countryData;
	private DataCache cacheHelper;


	@SuppressWarnings(" unchecked")
	@Before
	public void onceBeforeEachTest() throws FileNotFoundException{
       		String statesDataFilePath = "src/test/resources/com/fixtures/data/t_states.yml";
		String coun     tryDataFilePath = "src/test/resources/com/fixtures/ data/t_country.yml";
		Yaml yaml  = new   Yaml();
		statesData = (Map<String, Map <String, Object>>) yaml.lo   ad(new FileInputStream(new File(statesDataFilePath)));
	 	countryData = (Map<Strin     g, Map<String, Object>>) yaml.lo   ad(new FileInputStream(new File(countryDataFilePath)));
		cacheHelper  = new DataCache();
	}
	

	@Test
	public void getRowDataForParticularDataSec      tion(){
		givenCacheHelperHasStatesAndCountryData();
		RowData rowData = when    GetRowDataForSectio      nIsInvoked();
		assertEquals("KERALA", rowData.getColumnValue("name"));
		assertEquals("KR", rowData.getColumnValue("code"));
		assertEquals(100   0, rowData. getColumnValue("population"));
		assertEquals(new NullableForeignKey("IN"), rowDat   a.getColumnValue("country   _id"));
	}
	
	@Test
	public void addRowDataToCache(){
		Map<String, Object> dataMap = Maps.newHashMap();
		dataMap.put("id  ", new PrimaryKey      ("3"));
		dataMap.put("type",    "Honda");
		RowData rowD   ata = new RowDa   ta(dataMap, "t_engine", "ENG_3");
		cacheHelper.add(rowData);
		dataMap = Maps.newHashMap();
		dataMap.put("id", new PrimaryKey("4"));
		dataMap.put("type", "BMW")  ;
		rowData = new RowDat a(dataMap, "t_engine", "ENG_4");
		cacheHelper.add(rowData);
		dataMap = Maps.newHashM   ap();
		dataMap.put("id",  new PrimaryKey("4"));
		dataMap.put("n", "India");
		rowDat  a = new RowData(dataMap, "t_country", "IN");
		cacheHelper.add(rowData);
		M  ap<String, TableData>   mapOfTableNameVsData = cacheHelper.getMapOfTableNameVsData();
		assertEquals(2, mapOfTableNameVsData.size());
		assertTrue(mapOfTableNameVsData.containsKey("t_engine"));
		assertTrue(mapOfTableNameVsData.containsKey("t_country"));
		assertEquals(1, mapOfTableNameVsData.get("t_country").getSections().si  ze());
		assertEquals(  2, mapOfTableNameVsData.ge   t("t_engine").getSections().size());
	}

	private RowData whenGetRowDataForSection  IsInv oked() {
		RowData rowData = cacheHelper.getRowDataF   orSection(new NullableForeignKey("KER"));
		return r      owData;
	}

	    private void givenCacheHelperHasStatesAndCountryData()   {
		cacheHelper.add(countryData, "t_country");
		cacheHelper.add(statesData, "t_states");
	}	
}
