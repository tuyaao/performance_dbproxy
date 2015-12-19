package com.free4lab.monitorproxy.hbasetemp;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.free4lab.utils.log.LogOperation;
import com.free4lab.utils.log.LogOperationImpl;

//为了暂时能用，加了hadoop 的依赖
public class test {
	private static Logger logger = Logger.getLogger(test.class);
	private static final String BEGIN_TIME = "btime";
	private static final String END_TIME = "etime";

	public static String getCpu(String id, String Start, String end){
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("id", id);
		System.out.println("id:" + id);
//		filter.put(BEGIN_TIME, Start);
//		filter.put(END_TIME, end);
		LogOperation log = new LogOperationImpl("performance-cpu", "74a6d70b20e448899cf2811e29e0ec13", "JqpaykDFR9xVwBJB8XwwtNvYWZf1xGur");
	    String string  = log.getLog("performance-cpu", filter);
	    System.out.println("string:" + string);
	    return string;
		}
	
	public static String getMem(String id, String Start, String end){
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("id", id);
		filter.put(BEGIN_TIME, Start);
		filter.put(END_TIME, end);
		LogOperation log = new LogOperationImpl("perfomance-mem", "9d649a7d34be4b318ff59e24e889396a", "e6vwjQOweA5T7B4QVJkEIZw21l7ps4wY");
	    String string  = log.getLog("performance-mem", filter);
	    return string;
		}
	
	public static String getIozone(String id, String Start, String end){
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("id", id);
		filter.put(BEGIN_TIME, Start);
		filter.put(END_TIME, end);
		LogOperation log = new LogOperationImpl("performance-iozone", "a9e9862808754d3da1c74616de1852b1", "1coXlQo88hyzlinNaqSEr0ONJMXkW7bX");
	    String string  = log.getLog("performance-iozone", filter);
	    return string;
		}
	
	public static String getTpcc(String id, String Start, String end){
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("id", id);
		filter.put(BEGIN_TIME, Start);
		filter.put(END_TIME, end);
		LogOperation log = new LogOperationImpl("performance-tpcc", "b6e0e7eee096487fb8249d94f3f76639", "zg1ivqqmU5jueO9po8360UoCnUeuBPmE");
	    String string  = log.getLog("performance-tpcc", filter);
	    return string;
		}
	
	public static String getPing(String id, String Start, String end){
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("id", id);
		filter.put(BEGIN_TIME, Start);
		filter.put(END_TIME, end);
		LogOperation log = new LogOperationImpl("performance-ping", "9bb0613fbc094b60a4d5e4810dae0881", "qKKQk4vPFsjc43VmFDjtVh3nvhlg9zzL");
	    String string  = log.getLog("performance-ping", filter);
	    return string;
		}
	
	
	public static void main(String[] args) {
		test testit = new test();
		logger.error("hbase最终时间:"+new Timestamp(Long.valueOf("1447632000000")));
		logger.error("hbase开始时间:"+new Timestamp(Long.valueOf("1447203600000")));
		String result = testit.getPing( "35", "1447203600000", "1447632000000" );
		logger.error("END_TIME:"+result);
		JSONObject jSONObject = null;
		try {
			jSONObject = new JSONArray(result).getJSONObject(0);
			JSONArray jSONArray= jSONObject.getJSONArray("result");
			for(int i = 0; i < jSONArray.length(); i++){
				logger.error(jSONArray.get(i));
				System.out.println("\n");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
}
