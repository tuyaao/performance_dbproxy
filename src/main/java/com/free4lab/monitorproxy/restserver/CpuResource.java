
package com.free4lab.monitorproxy.restserver;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
	public List<BeanCpu> getIdByMac(@QueryParam("id") String id, @QueryParam(BEGIN_TIME) String start, @QueryParam(END_TIME) String end) {
		return resolveHbaseResultit(test.getCpu(id, start, end));
	}
	
	//重写的返回值需要时一样的,用反射写，简化代码实现。上面的也都尝试使反射来写，简化代码
	public List<BeanCpu> resolveHbaseResultit(String result){
		List<BeanCpu> returnit = new ArrayList<BeanCpu>();
		List<JSONObject>  list = resolveHbaseResult(result);
		for(JSONObject jSONObject : list){
			BeanCpu beanCpu = null;
			try {
				
				beanCpu = new BeanCpu(Integer.valueOf(jSONObject.getString("id")), new Date(Long.valueOf(jSONObject.getString("createdTime"))), (float)jSONObject.getDouble("totalTime"));
				System.out.println("sever" + beanCpu.toString() );
			} catch (JSONException e) {
				e.printStackTrace();
			}
			returnit.add(beanCpu);
		}
		return returnit;
	}
	
}
