
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

import com.free4lab.monitorproxy.hbasetemp.BeanIozone;
import com.free4lab.monitorproxy.hbasetemp.BeanMem;
import com.free4lab.monitorproxy.hbasetemp.BeanPing;
import com.free4lab.monitorproxy.hbasetemp.test;

@Path("dataIo")
@Component
public class IozoneResource extends AbstractResource{
	private Logger logger = LoggerFactory.getLogger(IozoneResource.class);
	
	@Override
	public Class getEntityClass() {
		return IozoneResource.class;
	}
	
	@GET
	@Path("Io")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<BeanIozone> getIdByMac(@QueryParam("id") String id, @QueryParam(BEGIN_TIME) String start, @QueryParam(END_TIME) String end) {
		return resolveHbaseResultit(test.getIozone(id, start, end));
	}
	
	//重写的返回值需要时一样的,用反射写，简化代码实现。上面的也都尝试使反射来写，简化代码
	public List<BeanIozone> resolveHbaseResultit(String result){
		List<BeanIozone> returnit = new ArrayList<BeanIozone>();
		List<JSONObject>  list = resolveHbaseResult(result);
		for(JSONObject jSONObject : list){
			BeanIozone beanIozone= null;
			try {
				beanIozone = new BeanIozone(Integer.valueOf(jSONObject.getString("id")), new Date(Long.valueOf(jSONObject.getString("createdTime"))), 
						Integer.valueOf(jSONObject.getString("fileSize")), Integer.valueOf(jSONObject.getString("recordSize")), 
						Integer.valueOf(jSONObject.getString("write")), Integer.valueOf(jSONObject.getString("rewrite")),
						Integer.valueOf(jSONObject.getString("read")), Integer.valueOf(jSONObject.getString("reread")),
						Integer.valueOf(jSONObject.getString("randomRead")), Integer.valueOf(jSONObject.getString("randomWrite")));
				System.out.println("severbeanIozone" + beanIozone.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			returnit.add(beanIozone);
		}
		return returnit;
	}
	
}
