package com.sidian.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.sidian.bean.BaseEntity;
import com.sidian.exception.ResponseException;

public class ApiUtil {

	private static Logger logger = LogManager.getLogger(ApiUtil.class);

	public static boolean isEmpty(Object param) {

		if (param == null) {
			return true;
		}

		if (param instanceof Map) {
			return ((Map) param).isEmpty();
		}
		if (param instanceof List) {
			return ((List) param).isEmpty();
		}
		String parameter = param.toString();
		if (parameter.trim().length() == 0) {
			return true;
		}

		if ("null".equalsIgnoreCase(parameter)) {
			return true;
		}

		return false;
	}

	public static Integer getInteger(Object value, int defaultValue, boolean log) {
		Integer result = null;

		if (isEmpty(value)) {
			result = defaultValue;
		} else {
			try {
				result = (int) Float.parseFloat(String.valueOf(value));
			} catch (NumberFormatException e) {
				try {
					result = Integer.parseInt(String.valueOf(value));
				} catch (NumberFormatException e1) {

					if (log) {
						logger.error(String.format("Integer parameter illegal [%s]", value));
					}
				}
			}

		}
		if (result == null)
			result = defaultValue;
		return result;
	}

	public static Float getFloat(Object value, Float defaultValue) {
		Float result = null;

		if (isEmpty(value)) {
			result = defaultValue;
		} else {
			try {
				result = Float.parseFloat(String.valueOf(value));
			} catch (NumberFormatException e) {

				logger.error(String.format("Integer parameter illegal [%s]", value), e);
				throw new ResponseException("ILEGAL_PARAMTERS");

			}

		}
		if (result == null)
			result = defaultValue;
		return result;
	}





	static JsonSerializer<Date> ser = new JsonSerializer<Date>() {
		@Override
		public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
			return src == null ? null : new JsonPrimitive(DateUtil.getDateStringByLong(src.getTime()));
		}
	};

	static JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
		@Override
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			return DateUtil.getDateTime(json.getAsString());
		}
	};

	public static <T extends BaseEntity> BaseEntity toEntity(Map<String, Object> data, Class<T> classzz) {
		String json = new GsonBuilder().registerTypeAdapter(Date.class, ser).setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(data);
		return new GsonBuilder().registerTypeAdapter(Date.class, deser).create().fromJson(json, classzz);

	}

	public static <T extends BaseEntity> BaseEntity toEntity(String data, Class<T> classzz) {
		return new GsonBuilder().registerTypeAdapter(Date.class, deser).create().fromJson(data, classzz);

	}

	public static <T extends BaseEntity> List<T> toJsonList(Object params, Class<T> clz, String key) {

		List<T> results = new ArrayList<T>();
		if (params instanceof Map) {
			Map<String, Object> result = (Map<String, Object>) params;
			if (!ApiUtil.isEmpty(result.get(key))) {

				if (result.get(key) instanceof List) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) result.get(key);

					for (Map<String, Object> obj : list) {
						results.add((T) ApiUtil.toEntity(obj, clz));
					}
				} else if (result.get(key) instanceof String) {

					List<Map<String, Object>> listMap = (List<Map<String, Object>>) new GsonBuilder().registerTypeAdapter(Date.class, deser).create()
					        .fromJson(result.get(key).toString(), List.class);
					for (Map<String, Object> map : listMap) {
						results.add((T) ApiUtil.toEntity(map, clz));
					}
				}

			}
		} else if (params instanceof List) {

			List<Map<String, Object>> dataList = (List<Map<String, Object>>) params;

			for (Map<String, Object> obj : dataList) {
				results.add((T) ApiUtil.toEntity(obj, clz));
			}
		}
		return results;

	}

	public static String toJson(BaseEntity entity) {
		return new GsonBuilder().registerTypeAdapter(Date.class, ser).create().toJson(entity);
	}

	public static String toJson(Object data) {
		return new GsonBuilder().registerTypeAdapter(Date.class, ser).create().toJson(data);
	}

	public static Map<String, Object> toMap(BaseEntity entity) {
		return new Gson().fromJson(entity.toString(), HashMap.class);
	}

	public static Map<String, Object> toMap(String jsonStr) {
		return new Gson().fromJson(jsonStr, HashMap.class);
	}

	public static String toString(Map<String, Object> data) {
		return new Gson().toJson(data);
	}



	public static boolean isValid(Object param) {
		return !isEmpty(param);
	}

	

}
