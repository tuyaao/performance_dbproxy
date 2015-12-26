
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

import org.json.JSONArray;
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
	public static List<BeanPing> getIdByMac(@QueryParam("id") String id, @QueryParam(BEGIN_TIME) String start, @QueryParam(END_TIME) String end) {
		return resolveHbaseResultit(test.getPing(id, start, end));
	}
	
	//重写的返回值需要时一样的,用反射写，简化代码实现。上面的也都尝试使反射来写，简化代码
	public static List<BeanPing> resolveHbaseResultit(String result){
		List<BeanPing> returnit = new ArrayList<BeanPing>();
		List<JSONObject>  list = resolveHbaseResult(result);
		System.out.println("severbeanPing个数" + list.size());
		for(JSONObject jSONObject : list){
			BeanPing beanPing= null;
			try {
				beanPing = new BeanPing(Integer.valueOf(jSONObject.getString("id")), new Date(Long.valueOf(jSONObject.getString("createdTime"))),jSONObject.getString("destIp"), Float.parseFloat(jSONObject.getString("loss")), Float.parseFloat(jSONObject.getString("avg")));
				System.out.println("severbeanPing" + beanPing.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			returnit.add(beanPing);
		}
		return returnit;
	}
	
	public static void main(String[] args) {
		test testit = new test();
		List<BeanPing> list = getIdByMac( "33", "1448049600000", "1448064000000" );
		for (BeanPing beanPing : list){
			System.out.println("查看serverping结果" + beanPing.toString());
		}
	}
	
}
