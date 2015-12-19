
package com.free4lab.monitorproxy.restserver;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.free4lab.monitorproxy.daomysql.VMInstance;
import com.free4lab.monitorproxy.daomysql.VMhardware;
import com.free4lab.monitorproxy.restclient.BeanHostRegister;
import com.free4lab.monitorproxy.servicemysql.CloudPlatformService;
import com.free4lab.monitorproxy.servicemysql.VMInstanceService;
import com.free4lab.monitorproxy.servicemysql.VMhardwareService;

@Path("host/")
@Component
public class HostResource extends AbstractResource{
	private Logger logger = LoggerFactory.getLogger(HostResource.class);
	
	@Autowired
	private CloudPlatformService cloudPlatformService;
	@Autowired
	private VMhardwareService vMhardwareService;
	@Autowired
	private VMInstanceService vMInstanceService;
	
	@Override
	public Class getEntityClass() {
		return HostResource.class;
	}
	
	//这个以后要尝试返回JSONObject or 对象，@Produces参考宋神的代码，不能用MediaType.TEXT_PLAIN这种粗暴的方式
	@GET
	@Path("id/mac/{mac}")
	@Produces({ MediaType.TEXT_PLAIN })
	public String getIdByMac(@PathParam("mac") String mac) {
		logger.error("in getIdByMac");
		Integer id = vMInstanceService.getIdByMac(mac);
		if( null == id ){
			return parseJsonResult("failure", "there is no host's mac is " + mac, null);
		}else{
			JSONObject result = new JSONObject();
			try {
				result.put("id", id);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return parseJsonResult("success", "success", result);
		}
	}
	
	//传入数据注册，注册成功或者已经存在，返回id；失败则返回失败信息
	@POST
	@Path("id/registration")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String addHost(BeanHostRegister beanHostRegister)  {
		//主机已经存在，返回存在
		if( null != vMInstanceService.getIdByMac(beanHostRegister.getMac())){
			logger.error("主机已经存在，返回存在");
			return parseJsonResult("failure", " host existed ", null);
		}
		//公司id不存在
		if( null == cloudPlatformService.getById(beanHostRegister.getCloudPlatformId())){
			logger.error("公司id不存在");
			return parseJsonResult("failure", "there is no CloudPlatform's id is " + beanHostRegister.getCloudPlatformId(), null);
		}
		//主机不存在，其他指标正确，插入数据，返回id
		VMInstance vMInstance = new VMInstance(null, beanHostRegister.getIp(), beanHostRegister.getMac(), 
				beanHostRegister.getCloudPlatformId(), beanHostRegister.getName(), beanHostRegister.getDescription(),
				beanHostRegister.getCreateTime(), beanHostRegister.getUpdateTime(), beanHostRegister.getStatus(),
				beanHostRegister.getOs(), beanHostRegister.getHardware());
		
		Integer id = vMInstanceService.add(vMInstance).getId();
		logger.error("添加instance之后，获取id："+id);
		VMhardware vMhardware = new VMhardware(null, id+"", beanHostRegister.getCpu(), beanHostRegister.getMemory(), beanHostRegister.getDisk(), beanHostRegister.getBandwidth());
	    
		vMhardwareService.add(vMhardware);
		logger.error("添加hardware之后，获取id："+vMhardware.getId());
		
		JSONObject result = new JSONObject();
		try {
			result.put("id", id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return parseJsonResult("success", "add success", result);
		
	}
	
}
