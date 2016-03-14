package com.free4lab.monitorproxy.restclient;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.free4lab.monitorproxy.hbasetemp.BeanCpu;
import com.free4lab.monitorproxy.hbasetemp.BeanPing;
import com.free4lab.monitorproxy.hbasetemp.BeanTpcc;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

//名字表示这是对host的操作，String表示返回值为String
public class TpccClient extends AbstractClient<BeanTpcc>{
	
	private static final String BY_IDTIME_PATH = "dataTpcc/Tpcc/";
	
	@Override
	protected Class<?> getType() {
		return BeanTpcc.class;
	}
	
    //这个本来是用来返回一列对象的，但我们第一用String传值，第二都是返回一个对象，所以用不到
	@Override
	protected GenericType<List<BeanTpcc>> getGenericType() {
		GenericType<List<BeanTpcc>> type = new GenericType<List<BeanTpcc>>() {};
		return type;
	}
	
	@Override
	protected String getByIdTimePath() {
		return BY_IDTIME_PATH;
	}
	
}
