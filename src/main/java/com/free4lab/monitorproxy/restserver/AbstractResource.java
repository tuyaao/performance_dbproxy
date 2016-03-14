package com.free4lab.monitorproxy.restserver;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.turbine.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;

import com.free4lab.monitorproxy.hbasetemp.BeanCpu;
import com.free4lab.monitorproxy.hbasetemp.BeanIozone;
import com.free4lab.monitorproxy.hbasetemp.BeanMem;
import com.free4lab.monitorproxy.hbasetemp.BeanPing;
import com.free4lab.monitorproxy.hbasetemp.BeanTpcc;

@Scope("prototype")
public abstract class AbstractResource<T>{
	
	protected static final String BEGIN_TIME = "btime";
	protected static final String END_TIME = "etime";
	
	@SuppressWarnings("rawtypes")
	public abstract Class getEntityClass();
	
	public String getClassName() {
        return getEntityClass().getName();
    }
	
	//如果通过反射生成对象，不同对象实例化种类太多，不好做。T只是一个类占位符，没办法查看类名，从而知道是哪个类。只能通过getClassName()类似的方法获得。
	protected List<T> resolveHbaseResultit(String result){
		List<T> returnit = new ArrayList<T>();
		List<JSONObject>  list = resolveHbaseResult(result);
		String[] split = getClassName().split("\\.");
        switch(split[split.length-1]){
        case "CpuResource":
        	for(JSONObject jSONObject : list){
    			BeanCpu beanCpu = null;
    			try {
    				beanCpu = new BeanCpu(Integer.valueOf(jSONObject.getString("id")), new Date(Long.valueOf(jSONObject.getString("createdTime"))), (float)jSONObject.getDouble("totalTime"));
//    				System.out.println("serverbeanCpu" + beanCpu.toString());
    			} catch (JSONException e) {
    				e.printStackTrace();
    			}
    			returnit.add((T)beanCpu);
    		}
        	break;
        case "IozoneResource":
        	for(JSONObject jSONObject : list){
    			BeanIozone beanIozone= null;
    			try {
    				beanIozone = new BeanIozone(Integer.valueOf(jSONObject.getString("id")), new Date(Long.valueOf(jSONObject.getString("createdTime"))), 
    						Integer.valueOf(jSONObject.getString("fileSize")), Integer.valueOf(jSONObject.getString("recordSize")), 
    						Integer.valueOf(jSONObject.getString("write")), Integer.valueOf(jSONObject.getString("rewrite")),
    						Integer.valueOf(jSONObject.getString("read")), Integer.valueOf(jSONObject.getString("reread")),
    						Integer.valueOf(jSONObject.getString("randomRead")), Integer.valueOf(jSONObject.getString("randomWrite")));
    				System.out.println("severbeanIozone" + beanIozone.toString());
    			} catch (JSONException e) {
    				e.printStackTrace();
    			}
    			returnit.add((T)beanIozone);
    		}
        	break;
        case "MemResource":
        	for(JSONObject jSONObject : list){
    			BeanMem beanMem= null;
    			try {
    				beanMem = new BeanMem(Integer.valueOf(jSONObject.getString("id")), new Date(Long.valueOf(jSONObject.getString("createdTime"))), (float)jSONObject.getDouble("transferSpeed"));
//    				System.out.println("severbeanMem" + beanMem.toString());
    			} catch (JSONException e) {
    				e.printStackTrace();
    			}
    			returnit.add((T)beanMem);
    		}
        	break;
        case "PingResource":
        	for(JSONObject jSONObject : list){
    			BeanPing beanPing= null;
    			try {
    				beanPing = new BeanPing(Integer.valueOf(jSONObject.getString("id")), new Date(Long.valueOf(jSONObject.getString("createdTime"))),jSONObject.getString("destIp"), Float.parseFloat(jSONObject.getString("loss")), Float.parseFloat(jSONObject.getString("avg")));
//    				System.out.println("severbeanPing每个" + beanPing.toString());
    			} catch (JSONException e) {
    				e.printStackTrace();
    			}
    			returnit.add((T)beanPing);
    		}
        	break;
        case "TpccResource":
        	for(JSONObject jSONObject : list){
    			BeanTpcc beanTpcc= null;
    			try {
    				beanTpcc = new BeanTpcc(Integer.valueOf(jSONObject.getString("id")), new Date(Long.valueOf(jSONObject.getString("createdTime"))), (float)jSONObject.getDouble("tpmc"));
//    				System.out.println("severbeanTpcc" + beanTpcc.toString());
    			} catch (JSONException e) {
    				e.printStackTrace();
    			}
    			returnit.add((T)beanTpcc);
    		}
        	break;
        }
		return returnit;
	}
	
	//返回结果的粘贴
	protected String parseJsonResult(String success, String message, JSONObject subResult){
		 JSONObject result = new JSONObject(); 
		 try {
			result.put("success", success);
			result.put("message", message);
			result.put("about", getClassName());
			result.put("result", subResult);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		 return result.toString();
	 }
	
	//宋神的接口返回的数据解析
	protected static List<JSONObject> resolveHbaseResult(String result){
		if(result == null){
			System.out.println("结果是null");
		}
		System.out.println("result:"+result);
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