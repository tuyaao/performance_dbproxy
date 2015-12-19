//由于为了那边通用，返回的json,不是宋里面的对象
package com.free4lab.monitorproxy.restclient;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

//名字表示这是对host的操作，String表示返回值为String
public class HostClient extends AbstractClient<String>{
	private Logger logger = LoggerFactory.getLogger(HostClient.class);
	private static final String PATH = "host/";
	@Override
	protected Class<?> getType() {
		return String.class;
	}

//这个本来是用来返回一列对象的，但我们第一用String传值，第二都是返回一个对象，所以用不到
	@Override
	protected GenericType<List<String>> getGenericType() {
		GenericType<List<String>> type = new GenericType<List<String>>() {};
		return type;
	}

	//通过mac查找id
	public String findIdByMac( String mac ) {
		return get(PATH + "id/mac/" + mac);
	}
	
	//通过JSON传入数据注册，注册成功或者已经存在，返回id；失败则返回失败信息
	public String addHost( BeanHostRegister beanHostRegister) {
		return postWithRet(PATH + "id/registration", beanHostRegister);
	}
	
	//通过JSON传入数据修改主机配置，只传入主机id和修改的部分,没完成
	public String modHost( HashMap<String,String> map ) {
		return postWithRet(PATH + "id/mod", map);
	}
	
	//通过JSON传入数据修改主机配置,传入主机对象，没完成
		public String modHost( BeanHostRegister beanHostRegister ) {
			return postWithRet(PATH + "id/mod", beanHostRegister);
		}
	
	//暂时不用
	public List<String> findAll() {
		return getList(PATH, null);
	}
	
	//暂时不用，数据多可以这么传，一个直接用url很方便，这个实际也是放在url里面的
	public List<String> findAppFields(String appName) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("appName", appName);
		
		List<String> fields = null;
		
		fields = getList(PATH, params);
		return fields;
	}
	
	//暂时不用
	public void delAppField(String appName, String fieldName) {
		
	}
	
	//暂时不用
	public void delAppField(Integer fieldId) {
		delete(PATH + "/" + fieldId);
	}
	
	public static void main(String[] args) {
		HostClient hostClient = new HostClient();
		//get 方法
//		System.out.println(hostClient.findIdByMac("FA:16:3E:AD:4F:4B"));
//		System.out.println(hostClient.findIdByMac("lalala"));
		Timestamp createTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
		Timestamp updateTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
		BeanHostRegister beanHostRegiste = new BeanHostRegister(0, "210.14.158.72", "ergaergagag", 2, "jingnan_1c_2g", "jingnan_1c_2g", createTime, updateTime, "ACTIVE", "CentOS_release_6.4_(Final)", null, 1, 1, 1, null);
		hostClient.addHost(beanHostRegiste);
//		System.out.println(hostClient.addHost(beanHostRegiste));
	}
	
	
	
}
