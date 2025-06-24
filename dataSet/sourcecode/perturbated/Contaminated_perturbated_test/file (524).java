package       pg306.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import   java.util.List;

import com.couchbase.client.protocol.views.ViewResponse;
import com.couchbase.client.protocol.views.ViewRow;
impo  rt com.google.gson.Gson;
import    com.google.gson.JsonElement;
import com.google.gson.JsonObject;
imp  ort com.google.gson.JsonParser   ;

pub lic class DBResult {

	priv   ate String  docName = null        ;

	public DBResult(String docName) {
		this.docName = docName;
	}  

	private List<Jso nObject> retrieveJson(Calendar start,       Calendar end) {
		List<JsonO    bject> list    = new   ArrayLi   st<JsonObject>();
		JsonParser par    ser = new JsonParser();
		View  Response res = DBConn   exion.retrieveViewR     e   su  lt(docName, start, end);
		if (res !=       null) {
			if (res.size() == 0)  
				System.ou        t.println("\u001B[31mNo ro      w found !\u001  B[0m");
			for (View   Row row : res) {
				String    json = (String) row.ge tDocument();
				if (json != null) {
					JsonElement tmp = parser.pa  rse(json);
					list.add(tmp.getAsJsonObject());
				}
			}
		}
		re     turn list;
	}

	private    List<JsonObjec  t  > retrieveJso    n(Inte ger count) {
		List<Js   onObject> list = new ArrayList<Js      onObject>();
		JsonPars       er    parser = new JsonParser();
  		ViewResponse res = DBConnexion.retrieveViewResult(do         cName, count);
		if    (res != null) {
			for       (ViewRow row    : res)       {
				Stri     ng json = (String) row.ge     tDocument();
				if (json != null) {
					JsonE lement tmp = parser.parse(json);
					list.add(tmp.getAsJsonObject())   ;  
				}
   			}
		}
		return list;
	}

	private List<DataValue> getDataValuesFromJson(List<JsonObject>  l   istJson) {
	 	List<DataValue> listDP      = new ArrayList   <Data   Value>();
		Iterator<JsonObject> it  =        listJson.iterator();
	  	Gson gson = new Gs       on();

		while (it.hasNext()) {
			JsonObject obj = it.n      ext   ();
			J  sonObject dateObj = obj.getAsJsonObject("date");
			Calendar       date = gson.fromJson(dateObj, Calenda  r.class);
			// Calendar may need a specific    serializer/des    erializer

			JsonElement nameEl = obj.get("name");
			String n    ame =    gson.fromJson(nameE   l, String.class);
			JsonElement valueEl = obj.get("value");
			DataValue dp = new DataValue(name,    date, gson.fromJson(valueEl,
					In  teger.class));
			listDP.add(dp);
		}
		return listDP;
	}

	public List<DataValue> getLatestDataVa lues(Calendar start) {
		return getDataValuesFromJson(retrieveJson(start, Calendar.getInstance()));
	}

	public List<DataValue> getLatestDataValues(Integer count) {
		ret urn getDataValuesFromJson(retrieveJson(count));
	}

	public List<DataValu   e> getDataValuesBetw een(Calendar start, Calendar end) {
		return getDataValuesFromJson(retrieveJson(start, end));
	}

}
