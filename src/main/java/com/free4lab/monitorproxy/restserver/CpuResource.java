
package com.free4lab.monitorproxy.restserver;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.free4lab.monitorproxy.hbasetemp.BeanCpu;
import com.free4lab.monitorproxy.hbasetemp.test;

//迟早要改为data的统一格式，看宋神那边是否统一格式可以
@Path("dataCpu/")
@Component
public class CpuResource extends AbstractResource{
	private Logger logger = LoggerFactory.getLogger(CpuResource.class);
	
	@Override
	public Class getEntityClass() {
		return CpuResource.class;
	}
	
	@GET
	@Path("Cpu")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<BeanCpu> getIdByMac(@PathParam("id") String id, @PathParam(BEGIN_TIME) String start, @PathParam(END_TIME) String end) {
		logger.error("do here_getIdByMac");
		return resolveHbaseResultit(test.getCpu(id, start, end));
	}
	
	//重写的返回值需要时一样的,用反射写，简化代码实现。上面的也都尝试使反射来写，简化代码
	public List<BeanCpu> resolveHbaseResultit(String result){
		System.out.println("初始返回result" + result );
		List<BeanCpu> returnit = new ArrayList<BeanCpu>();
		List<JSONObject>  list = resolveHbaseResult(result);
		for(JSONObject jSONObject : list){
			BeanCpu beanCpu = null;
			try {
				beanCpu = new BeanCpu(Integer.getInteger(jSONObject.getString("id")), Date.valueOf(jSONObject.getString("createdTime")), (float)jSONObject.getDouble("totalTime"));
				System.out.println("sever" + beanCpu.toString() );
			} catch (JSONException e) {
				e.printStackTrace();
			}
			returnit.add(beanCpu);
		}
		return returnit;
	}
	
}
