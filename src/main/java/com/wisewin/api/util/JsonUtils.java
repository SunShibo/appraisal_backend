package com.wisewin.api.util;

import net.sf.json.*;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;
import net.sf.json.xml.XMLSerializer;

import java.text.ParseException;
import java.util.*;

/**
 * JSON常用方法工具类
 */
@SuppressWarnings("unchecked")
public class JsonUtils {
	public static final String LONG_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 将JAVA对象转化为对象字符串
	 */
	public static String getJsonString4JavaPOJO(Object javaObj) {
		JSONObject json = getJsonObject4JavaPOJO(javaObj);
		return json.toString();
	}

	/**
	 * 将JAVA对象转换成JSON字符串,并设定日期格式 含有时间类型的对象在转换为JSON时候默认将时间转为
	 * {"date":15,"day":2,"hours":13,"minutes":24,"month":0,
	 * "seconds":55,"time":1358256295403,"timezoneOffset":0,"year":113}
	 * 
	 * 该方法可以将时间格式换如：“yyyy-MM-dd HH:mm:ss” 格式
	 * 参考：http://blog.csdn.net/hxx688/article/details/7307154
	 */
	public static String getJsonString4JavaPOJO(Object javaObj,
												String dataFormat) {
		JSONObject json = getJsonObject4JavaPOJO(javaObj, dataFormat);
		return json.toString();
	}

	/**
	 * 将JAVA对象转换成JSON字符串,并设定日期格式
	 * 
	 * @param excludes
	 *            如果对象的某些属性不需要进行转换可在该数组中配置， 数组的值为属性的名称。
	 *            如对象包含（id,name,url)，要是不转换name 在数组中添加name即可。
	 */
	public static String getJsonString4JavaPOJO(Object javaObj,
												String[] excludes, String dataFormat) {
		return getJsonObject4JavaPOJO(javaObj, excludes, dataFormat).toString();
	}

	/**
	 * 将JAVA对象转化为JSON对象 (可用户和其他JSON对象拼接)
	 */
	public static JSONObject getJsonObject4JavaPOJO(Object javaObj) {
		JSONObject json;
		json = JSONObject.fromObject(javaObj);
		return json;
	}

	/**
	 * 将JAVA对象转换成JSON对象,并设定日期格式 参考 getJsonString4JavaPOJO 注释
	 */
	public static JSONObject getJsonObject4JavaPOJO(Object javaObj,
			String dataFormat) {
		JSONObject json;
		JsonConfig jsonConfig = configJson(dataFormat);
		json = JSONObject.fromObject(javaObj, jsonConfig);
		return json;
	}

	/**
	 * 将JAVA对象转换成JSON对象,并设定日期格式 参考 getJsonString4JavaPOJO 注释
	 */
	public static JSONObject getJsonObject4JavaPOJO(Object javaObj,
													String[] excludes, String dataFormat) {
		JSONObject json;
		JsonConfig jsonConfig = configJson(excludes, dataFormat);
		json = JSONObject.fromObject(javaObj, jsonConfig);
		return json;
	}

	/**
	 * 将字符串(JSON)转化为JAVA对象
	 */
	public static Object getObject4JsonString(String jsonString, Class pojoCalss) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;
	}
	
	/**
	 * json对象转换JAVA对象
	 * @param format 时间转换格式
 	 */
	public static Object getObject4JsonString(String jsonString,
											  Class pojoCalss, String format) {
		if(format == null){
			format = LONG_DATE_PATTERN;
		}
		Object pojo;
		JSONUtils.getMorpherRegistry().registerMorpher( new MyDateMorpher(new String[] { format }), true);
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		JSONUtils.getMorpherRegistry().deregisterMorpher(new MyDateMorpher(new String[] { format }));
		return pojo;
	}
	
	/**
	 * 将JSONObject对象转化为JAVA对象
	 * @param jsonObject
	 * @param pojoCalss
	 * @return
	 */
	public static Object getObject4JsonObject(JSONObject jsonObject, Class pojoCalss) {
		return getObject4JsonObject(jsonObject, pojoCalss,null);
	}
	
	/**
	 * 将JSONObject对象转化为JAVA对象
	 * @param jsonObject
	 * @param pojoCalss
	 * @param format
	 * @return
	 */
	public static Object getObject4JsonObject(JSONObject jsonObject, Class pojoCalss, String format) {
		if(null == jsonObject) return null;
		
		if(format == null){
			format = JsonUtils.LONG_DATE_PATTERN;
		}
		
		Object pojo;
		JSONUtils.getMorpherRegistry().registerMorpher( new MyDateMorpher(new String[] { format }), true);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		JSONUtils.getMorpherRegistry().deregisterMorpher( new MyDateMorpher(new String[] { format }));
		return pojo;
	}

	/**
	 * 将字符串(JSON)转化为JAVA对象 <针对于稍微复杂的json对象> Tuangou -->
	 * {"name":"全聚德烤鸭7折优惠","startTime":"2013-01-31",
	 * "end_time":"2013-12-31","shops":[{"name":"全聚德朝阳店","address":"朝阳区"},
	 * {"name":"全聚德丰台店","address":"丰台区"}]}
	 * 
	 * @param jsonString
	 *            json字符串 对应以上整个字符串
	 * @param pojoCalss
	 *            整个字符串对应转换对象
	 * @param classMap
	 *            json中对应对象 classMap.put("shops",Shop.class);
	 * @return
	 */
	public static Object getObject4JsonString(String jsonString,
											  Class pojoCalss, Map classMap) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss, classMap);
		return pojo;
	}
	
	/**
	 * 将List对象转换成JSON字符串
	 */
	public static String getJsonString4JavaList(List list) {
		return getJONSArray4JavaList(list).toString();
	}

	/**
	 * 将List对象转换成JSON字符串   里面含有时间对象Date
	 * 默认为LONG_DATE_PATTERN=yyyy-MM-dd HH:mm:ss
	 */
	public static String getJsonString4JavaListDate(List list){
		return getJsonString4JavaListDate(list, null, null);
	}
	
	public static String getJsonString4JavaListDate(List list, String datePattern){
		return getJsonString4JavaListDate(list, datePattern, null);
	}
	
	public static String getJsonString4JavaListDate(List list, String[] excludes){
		return getJsonString4JavaListDate(list, null, excludes);
	}
	
	/**
	 * 将List对象转换成JSON字符串   里面含有时间对象Date
	 * 需要传入转换的类型，如 LONG_DATE_PATTERN（yyyy-MM-dd HH:mm:ss）
	 * 或者DEFAULT_DATE_PATTERN（yyyy-MM-dd）等等
	 */
	public static String getJsonString4JavaListDate(List list, String datePattern, String[] excludes){
		
		if(null == datePattern || "".equals(datePattern)) {
			datePattern = DateJsonValueProcessor.LONG_DATE_PATTERN;
		}
		
		JsonConfig jsonConfig = configJson(excludes,datePattern);
        JSONArray jo = JSONArray.fromObject(list, jsonConfig);
		return jo.toString();
	}
	
	
	/**
	 * 将List对象转换成JSONArray (可用户和其他JSONArray对象拼接)
	 */
	public static JSONArray getJONSArray4JavaList(List list) {
		JSONArray array = new JSONArray();
		array = JSONArray.fromObject(list);
		return array;
	}	
	
	/**
	 * 将JSONArray 转转为 java LIST
	 * @param ja
	 * @param pojoClass
	 * @return
	 */
	public static List getList4JsonArray(JSONArray ja, Class pojoClass) {
		return getList4JsonArray(ja, pojoClass,null);
	}
	
	/**
	 * 将JSONArray 转转为 java LIST
	 * @param ja
	 * @param pojoClass
	 * @return
	 */
	public static List getList4JsonArray(JSONArray ja, Class pojoClass, String format) {
		
		if(!StringUtils.isEmpty(format)) {
			JSONUtils.getMorpherRegistry().registerMorpher( new MyDateMorpher(new String[] { format }));
		}
		
		List<Object> list = (List<Object>) JSONArray.toCollection(ja, pojoClass);
		
		if(!StringUtils.isEmpty(format)) {
			JSONUtils.getMorpherRegistry().deregisterMorpher( new MyDateMorpher(new String[] { format }));
		}
        return list ; 
	}
	
	/**
	 * 将数组JSON字符串转换为List
	 */
	public static List getList4Json(String jsonString, Class pojoClass) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;

		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);
		}
		return list;
	}
	/**
	 * 将数组JSON字符串转换为List 可以转换日期  默认yyyy-MM-dd HH:mm:ss
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	public static List getListJsonDate(String jsonString, Class pojoClass, String format){
		if(format == null){
			format = LONG_DATE_PATTERN;
		}
		JSONUtils.getMorpherRegistry().registerMorpher( new MyDateMorpher(new String[] { format }), true);
		List<Object> list = (List<Object>) JSONArray.toCollection(JSONArray.fromObject(jsonString), pojoClass);
		JSONUtils.getMorpherRegistry().deregisterMorpher(new MyDateMorpher(new String[] { format }));
        return list ; 
	}
	

	/**
	 * 把XML字符串解析成JSON字符串
	 */
	public static String XmlStrToJsonStr(String xmlStr) {
		String jsonStr = null;
		XMLSerializer xmlSerializer = new XMLSerializer();
		jsonStr = xmlSerializer.read(xmlStr).toString();
		return jsonStr;
	}

	/**
	 * 把JSON字符串解析成XML字符串
	 * 
	 * @param rootNodeName
	 *            根节点名称
	 * @param elementNodeName
	 *            子节点名称
	 */
	public static String JsonStrToXmlStr(String rootNodeName,
										 String elementNodeName, String jsonStr) {
		JSON json = JSONSerializer.toJSON(jsonStr);
		XMLSerializer xmlSerializer = new XMLSerializer();
		xmlSerializer.setRootName(rootNodeName);
		xmlSerializer.setElementName(elementNodeName);
		xmlSerializer.setTypeHintsEnabled(false);
		String xmlStr = xmlSerializer.write(json);
		return xmlStr;
	}

	/**
	 * JSON 时间解析器具 For 格式时间
	 */
	private static JsonConfig configJson(String dataFormat) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "" });
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor(dataFormat));
		return jsonConfig;
	}

	/**
	 * 解析器具
	 * 
	 * @param excludes
	 *            转换是将该数组中的属性排除在外
	 */
	private static JsonConfig configJson(String[] excludes, String dataFormat) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor(dataFormat));

		return jsonConfig;
	}

	/**
	 * 将JSON字符串转换成Map对象
	 */
	public static Map getMap4Json(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();

		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}

		return valueMap;
	}

	/**
	 * 从JSON数组中得到相应java数组
	 * 
	 * @return 数组为JSON字符串类型的对象
	 */
	public static Object[] getObjectArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();
	}

	/**
	 * 从JSON数组中解析出java字符串数组
	 */
	public static String[] getStringArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArray[i] = jsonArray.getString(i);
		}

		return stringArray;
	}

	/**
	 * 从JSON数组中解析出javaLong型对象数组
	 */
	public static Long[] getLongArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Long[] longArray = new Long[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			longArray[i] = jsonArray.getLong(i);
		}
		return longArray;
	}

	/**
	 * 从JSON数组中解析出java Integer型对象数组
	 */
	public static Integer[] getIntegerArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Integer[] integerArray = new Integer[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			integerArray[i] = jsonArray.getInt(i);
		}
		return integerArray;
	}

	/**
	 * 从JSON数组中解析出java Date 型对象数组，使用本方法必须保证
	 */
	public static Date[] getDateArray4Json(String jsonString, String dataFormat)
			throws ParseException {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Date[] dateArray = new Date[jsonArray.size()];
		String dateString;
		Date date;

		for (int i = 0; i < jsonArray.size(); i++) {
			dateString = jsonArray.getString(i);
			date = DateUtils.parseDate(dateString, DateUtils.DATE_PATTERN);
			dateArray[i] = date;
		}
		return dateArray;
	}

	/**
	 * 从JSON数组中解析出java Integer型对象数组
	 */
	public static Double[] getDoubleArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Double[] doubleArray = new Double[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			doubleArray[i] = jsonArray.getDouble(i);
		}
		return doubleArray;
	}

	/**
	 * 将jsonarray字符串转成jsonarray对象
	 * @param jsonArrayStr
	 * @return
     */
	public static JSONArray getJSONArrayByStr(String jsonArrayStr) {
		JSONArray json = JSONArray.fromObject(jsonArrayStr ) ;
		return json ;
	}

	public static JSONObject encapsulationJSON(int code, String msg, String data) {
		if(data == null) {
			data = "";
		}
		if(msg == null) {
			msg = "";
		}
		JSONObject json = new JSONObject();
		json.put("code", Integer.valueOf(code));
		json.put("msg", msg);
		json.put("data", data);
		return json;
	}
}
