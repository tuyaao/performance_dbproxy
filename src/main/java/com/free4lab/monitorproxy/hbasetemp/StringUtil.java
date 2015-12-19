package com.free4lab.monitorproxy.hbasetemp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class StringUtil {
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	public static HashSet<String> String2HashSet(String value, String StringSep) {
		HashSet<String> result = new HashSet<String>();
		if(null == value || "".equals(value)) {
			return result;
		}
		String array[] = value.split(StringSep);
		for(String at:array) {
			if(null == at || "".equals(at)) {
				continue;
			}
			result.add(at);
		}
		return result;
 	}
	
	/**
	 * convert the request parameters string to hash map
	 * @param value
	 * @param sep1 default: "&"
	 * @param sep2 default: "="
	 * @return
	 */
	public static Map<String, String> String2HashMap(String value, String sep1, String sep2) {
		Map<String, String> result = new HashMap<String, String>();
		if(isBlank(value) || isBlank(sep1) || isBlank(sep2)) {
			logger.warn("The params you passed to the String2HashMap is empty!");
			return result;
		}
		
		logger.debug("The paramters are value=" + value + ",sep1=" + sep1 + ",sep2=" + sep2);
		String array[] = value.split(sep1);
		for(String at:array) {
			if(isBlank(at)) {
				continue;
			}
			String keyVal[] = at.split(sep2);
			if (isBlank(keyVal[0])  || isBlank(keyVal[1])) {
				logger.warn("Bad http request parameters pattern!");
				return result;
			}
			result.put(keyVal[0], keyVal[1]);
		}
		
		logger.debug("we have convert " + value + " to " + result);
		return result;
 	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isBlank(String value) {
		if (null == value || "".equals(value)){
			return true;
		}
		return false;
	}
	
	
	/**
	 * @param batchlogs
	 * @param size
	 * @return
	 */
	public static Stack<String> fetchSubStack(Stack<String> batchlogs, int size) {
		size = batchlogs.size() > size ? size : batchlogs.size();
		Stack<String> result = new Stack<String>();
		while (size-- > 0) {
			result.add(batchlogs.pop());
		}
		return result;
	}
	
	public static String wrapStringHbase(String value) {
		if(null == value || "".equals(value)) {
			value = LogPro.UN_KNOWN;
		}
		return value;
	}
	
	/**
	 * fetch all the digit from the String s and gather them to be an string, then return it
	 * @param s
	 * @return
	 */
	public static String fetchAllDigit(String s) {
		String regEx="[^0-9]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(s);   
		return m.replaceAll("").trim();
	}
}
