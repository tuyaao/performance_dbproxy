package com.free4lab.monitorproxy.hbasetemp;


public class HBaseConstants {
	/**
	 * 默认lol 表名，时间戳采用升序设计
	 * |FAMILY_LOG | FAMILY_PROPS |
	 * | 默认属性                 |  自定义属性                    |
	 * 默认属性都放在列族FAMILY_LOG下
	 * 自定义属性放在列族FAMILY_PROPS下
	 * 行健设计：topic+create_time+hash(ip_address+source)+offset 一共48字节
	 */
    public final static String TABLENAME = "lol";
    /**
     * lol表名，时间戳采用降序设计
     * 行健设计：topic+create_time*(-1)+hash(ip_address+source)+offset 一共48字节
     * 时间戳乘以-1，得到最新的日志
     */
    public final static String TABLENAME_TIME_DESC = "lol_time_desc";
    
    
    
    public final static String FAMILY_LOG = "log";
    public final static String FAMILY_PROPS = "props";
    
    public final static String LOG_TOPIC = "topic";
    public final static String LOG_CONTENT = "content";
    public final static String LOG_CTIME = "createdTime";
    public final static String LOG_IPADDRESS = "ipaddress";
    public final static String LOG_SOURCE = "source";
    public final static String LOG_OFFSET = "offset";
    
    
}
