package com.free4lab.monitorproxy.hbasetemp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.free4lab.monitorproxy.restserver.AbstractResource;
import com.free4lab.utils.log.LogOperation;
import com.free4lab.utils.log.LogOperationImpl;

//为了暂时能用，加了hadoop 的依赖
public class test {
	private static Logger logger = Logger.getLogger(test.class);
	private static final String BEGIN_TIME = "btime";
	private static final String END_TIME = "etime";
	private static final String SIZE = "size";
	private static final String SIZE_NUM = "1000";
	private static final String TIME_ASC = "timeasc";

	public static String getCpu(String id, String Start, String end){
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("id", id);
		filter.put(BEGIN_TIME, Start);
		filter.put(END_TIME, end);
		filter.put(SIZE, SIZE_NUM);
		System.out.println("cpu__"+"id："+id+"--"+BEGIN_TIME+"--"+Start+"--"+END_TIME+"--"+end);
		LogOperation log = new LogOperationImpl();
	    String string  = log.getLog("performance-cpu", filter);
	    return string;
		}
	
	public static String getMem(String id, String Start, String end){
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("id", id);
		filter.put(BEGIN_TIME, Start);
		filter.put(END_TIME, end);
		filter.put(SIZE, SIZE_NUM);
		System.out.println("mem__"+"id："+id+"--"+BEGIN_TIME+"--"+Start+"--"+END_TIME+"--"+end);
		LogOperation log = new LogOperationImpl();
	    String string  = log.getLog("performance-mem", filter);
	    return string;
		}
	
	public static String getIozone(String id, String Start, String end){
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("id", id);
		filter.put(BEGIN_TIME, Start);
		filter.put(END_TIME, end);
		filter.put(SIZE, SIZE_NUM);
		System.out.println("iozone__"+"id："+id+"--"+BEGIN_TIME+"--"+Start+"--"+END_TIME+"--"+end);
		LogOperation log = new LogOperationImpl();
	    String string  = log.getLog("performance-iozone", filter);
	    return string;
		}
	
	public static String getTpcc(String id, String Start, String end){
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("id", id);
		filter.put(BEGIN_TIME, Start);
		filter.put(END_TIME, end);
		filter.put(SIZE, SIZE_NUM);
		System.out.println("tpcc__"+"id："+id+"--"+BEGIN_TIME+"--"+Start+"--"+END_TIME+"--"+end);
		LogOperation log = new LogOperationImpl();
	    String string  = log.getLog("performance-tpcc", filter);
	    return string;
		}
	
	public static String getPing(String id, String Start, String end){
		long projetStart = System.currentTimeMillis();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("id", id);
		filter.put(BEGIN_TIME, Start);
		filter.put(END_TIME, end);
		filter.put(SIZE, SIZE_NUM);
		filter.put(TIME_ASC, "no");
		System.out.println("ping__"+"id："+id+"--"+BEGIN_TIME+"--"+Start+"--"+END_TIME+"--"+end);
		LogOperation log = new LogOperationImpl();
	    String string  = log.getLog("performance-ping", filter);
	    System.out.println("pingtotaltime:"+(System.currentTimeMillis()-projetStart)/1000);
	    return string;
		}
	
	
	public static void main(String[] args) {
		String[] s = new String[10];
		System.out.println(s.length);

		int a =0;
		int c =0;
		do{
			--c;
			a = a-1;
			
		}while(a>0);
		System.out.println(c);
			
		
		test testit = new test();
//		String result = testit.getCpu( "30", "1456300139978", "1456904939978" );
//		logger.error("result:"+result);
//		result = testit.getMem( "30", "1456300139978", "1456904939978" );
//		logger.error("result:"+result);
//		result = testit.getIozone( "30", "1456300139978", "1456904939978" );
//		logger.error("result:"+result);
//		result = testit.getTpcc( "30", "1456300139978", "1456904939978" );
//		logger.error("result:"+result);
		long projetStart = System.currentTimeMillis();
		String result = testit.getPing( "30", "1457401406839", "1458006206839" );
		logger.error("result:"+resolveHbaseResult(result).size());
		logger.error("totaltime:"+(System.currentTimeMillis()-projetStart)/1000);
		projetStart = System.currentTimeMillis();
		result = testit.getMem( "30", "1457401406839", "1458006206839" );
		logger.error("result:"+resolveHbaseResult(result).size());
		logger.error("totaltimeMem:"+(System.currentTimeMillis()-projetStart)/1000);
		
//		JSONObject jSONObject = null;
//		try {
//			jSONObject = new JSONArray(result).getJSONObject(0);
//			JSONArray jSONArray= jSONObject.getJSONArray("result");
//			logger.error("length:"+jSONArray.length());
//			for(int i = 0; i < jSONArray.length(); i++){
//				logger.error(jSONArray.get(i));
//				System.out.println("\n");
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
	}
	
	public static List<JSONObject> resolveHbaseResult(String result){
		if(result == null){
			System.out.println("结果是null");
		}
//		System.out.println("result:"+result);
		List<JSONObject> returnit = new ArrayList<JSONObject>();
		JSONObject jSONObject = null;
		try {
			jSONObject = new JSONArray(result).getJSONObject(0);
			JSONArray jSONArray= jSONObject.getJSONArray("result");
			for(int i = 0; i < jSONArray.length(); i++){
				returnit.add((JSONObject)jSONArray.get(i));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return returnit;
	}
	
}