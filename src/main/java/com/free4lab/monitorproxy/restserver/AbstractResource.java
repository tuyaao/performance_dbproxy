package com.free4lab.monitorproxy.restserver;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public abstract class AbstractResource{
	
	protected static final String BEGIN_TIME = "btime";
	protected static final String END_TIME = "etime";
	
	@SuppressWarnings("rawtypes")
	public abstract Class getEntityClass();
	
	public String getClassName() {
        return getEntityClass().getName();
    }
	
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
	
	protected static List<JSONObject> resolveHbaseResult(String result){
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