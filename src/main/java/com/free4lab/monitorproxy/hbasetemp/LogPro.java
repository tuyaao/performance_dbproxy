package com.free4lab.monitorproxy.hbasetemp;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * 日志属性
 * 
 * @author huahui
 */
public class LogPro {
	private static Logger logger = Logger.getLogger(LogPro.class);

	// 日志的topic，标识不同第三方的日志(required) 最多24个英文字符
	public final static String APP_NAME = "appName";
	public final static String TOPIC = "topic";
	
	// 日志创建的时间
	public final static String CREATE_TIME = "createdTime";

	// 日志产生的客户端ip地址，缺省为un_known
	public final static String IP_ADDRESS = "ipaddress";

	// 日志的来源（文件名，端口，应用类名），缺省为un_know
	public final static String SOURCE = "source";

	// 日志在本source中的偏移位置，缺省为系统的一个long自增值
	public final static String OFFSET = "offset";

	// 日志的主体内容
	public final static String CONTENT = "content";

	// 未知的属性标识为un_known
	public final static String UN_KNOWN = "un_known";
	
	
	// 日志的其他属性，这部分属性第三方可以定制<topic,pros>形式
	private static Object topicMutex = new Object();
	private static Map<String, HashSet<String>> TOPIC_PROS = new HashMap<String, HashSet<String>>();

	
	//标识应用信息的一个16位字符串，
	public final static String APP_KEY = "appKey";

	//日志发送时间
	public final static String TC = "tc";
		
	//验证请求合法性的签名信息
	public final static String SIGN = "sign";
	
	/**
	 * load the third-party customized properties
	 */
	private final static String EXTRA_PROS_CONF_FILE = "extra-topic-pros.properties";
	static {
		getServerLog();
	}
	
	public static class GetServerLog implements Runnable{
		public void run() {
		}
	}
	
	public static void getServerLog(){
		
	}
	
	private static void loadLocalLog() {
		Properties p = new Properties();
		InputStream is = LogPro.class.getClassLoader().getResourceAsStream(
				EXTRA_PROS_CONF_FILE);

		try {
			p.load(is);
		} catch (Exception e) {
			logger.warn("Invalid" + EXTRA_PROS_CONF_FILE + "found", e);
		}
		Map<String, HashSet<String>> topicPros = new HashMap<String, HashSet<String>>();
		final String stringSep = ",";
		for (Object at : p.keySet()) {
			if (topicPros.containsKey(at.toString())) {
				HashSet<String> updateSet = topicPros.get(at);
				updateSet.addAll(StringUtil.String2HashSet(p.get(at).toString(), stringSep));
				topicPros.put(at.toString(), updateSet);
			} else {
				HashSet<String> newSet = StringUtil.String2HashSet(p.get(at).toString(), stringSep);
				topicPros.put(at.toString(), newSet);
			}
		}
		
		synchronized (topicMutex) {
			TOPIC_PROS.clear();
			TOPIC_PROS.putAll(topicPros);
		}
		logger.info("load " + EXTRA_PROS_CONF_FILE + ":" + TOPIC_PROS.toString());
	}
	/**
	 * get the map: topic->parameters
	 * @return
	 */
	public static Map<String, HashSet<String>> getTopicPros() {
		synchronized (topicMutex) {
			return TOPIC_PROS;
		}
	}
	
	/**
	 * basic log properties
	 * @param value
	 * @return
	 */
	public static boolean isInDefaultPros(String value) {
		if(StringUtil.isBlank(value)) {
			return false;
		}
		if(value.equals(APP_NAME) || value.equals(CREATE_TIME)|| value.equals(IP_ADDRESS)
				|| value.equals(SOURCE)|| value.equals(OFFSET)|| value.equals(CONTENT)) {
			return true;
		}
		return false;
	}
	

	public static void main(String[] args) {
	}
}