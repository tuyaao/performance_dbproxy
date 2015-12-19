package com.free4lab.monitorproxy.hbasetemp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;

public class HBaseConfigurationSingleton {
	
	private static class SingletonHolder {
        static Configuration conf;
        /*static HConnection  conn;*/
        static {
        	conf = HBaseConfiguration.create();
        	conf.addResource("hbase-site.xml");
        	/*try {
				conn = HConnectionManager.createConnection(conf);
			} catch (ZooKeeperConnectionException e) {
				e.printStackTrace();
			}*/
        }
    }


	public static Configuration getConf() {
		return SingletonHolder.conf;
	}

	/*public static HConnection getHConnection() {
		return SingletonHolder.conn;
	}*/
}
