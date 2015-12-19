
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

import com.free4lab.monitorproxy.hbasetemp.BeanMem;
import com.free4lab.monitorproxy.hbasetemp.BeanPing;
import com.free4lab.monitorproxy.hbasetemp.test;

@Path("dataPing/")
@Component
public class PingResource extends AbstractResource{
	private Logger logger = LoggerFactory.getLogger(PingResource.class);
	
	@Override
	public Class getEntityClass() {
		return PingResource.class;
	}
	
	@GET
	@Path("Ping")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<BeanPing> getIdByMac(@PathParam("id") String id, @PathParam(BEGIN_TIME) String start, @PathParam(END_TIME) String end) {
		return resolveHbaseResultit(test.getPing(id, start, end));
	}
	
	//重写的返回值需要时一样的,用反射写，简化代码实现。上面的也都尝试使反射来写，简化代码
	public List<BeanPing> resolveHbaseResultit(String result){
		List<BeanPing> returnit = new ArrayList<BeanPing>();
		List<JSONObject>  list = resolveHbaseResult(result);
		for(JSONObject jSONObject : list){
			BeanPing beanPing= null;
			try {
				beanPing = new BeanPing(Integer.getInteger(jSONObject.getString("id")), Date.valueOf(jSONObject.getString("createdTime")),jSONObject.getString("destIp"), Integer.getInteger(jSONObject.getString("loss")), (float)jSONObject.getDouble("transferSpeed"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			returnit.add(beanPing);
		}
		return returnit;
	}
	
}
