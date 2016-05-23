
package com.free4lab.monitorproxy.restserver;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.free4lab.monitorproxy.daomysql.SumWeekPing;
import com.free4lab.monitorproxy.daomysql.SumWeekPingDao;
import com.free4lab.monitorproxy.hbasetemp.BeanPing;
import com.free4lab.monitorproxy.hbasetemp.test;

@Path("dataPing/")
@Component
public class PingResource extends AbstractResource<BeanPing>{
	private SumWeekPingDao dao = new SumWeekPingDao();
	
	@Override
	public Class getEntityClass() {
		return PingResource.class;
	}
	
	@GET
	@Path("Ping")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<BeanPing> getIdByMac(@QueryParam("id") String id, @QueryParam(BEGIN_TIME) String start, @QueryParam(END_TIME) String end) {
		System.out.println("pingendid:" + id);
		System.out.println("pingstarttime:" + start);
		System.out.println("pingendtime:" + end);
		return resolveHbaseResultit(test.getPing(id, start, end));
	}
	
	@GET
	@Path("SumPing/{id}/{BEGIN_TIME}/{END_TIME}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<SumWeekPing> getSumCpu(@QueryParam("id") String id, @QueryParam(BEGIN_TIME) String start, @QueryParam(END_TIME) String end) {
		return (List<SumWeekPing>)dao.findByPropertyAndTime("uuid", id, "sumTime", stringToTimeStamp(start), stringToTimeStamp(end));
	}
	
	public static void main(String[] args) {
		test testit = new test();
		PingResource pingResource = new PingResource();
		List<BeanPing> list = pingResource.getIdByMac( "34", "1451131259000", "1451822459000" );
		for (BeanPing beanPing : list){
			System.out.println("查看serverping结果" + beanPing.toString());
		}
	}
	
}
