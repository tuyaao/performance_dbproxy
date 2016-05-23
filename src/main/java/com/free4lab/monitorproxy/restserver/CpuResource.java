
package com.free4lab.monitorproxy.restserver;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.free4lab.monitorproxy.daomysql.SumWeekCpu;
import com.free4lab.monitorproxy.daomysql.SumWeekCpuDao;
import com.free4lab.monitorproxy.hbasetemp.BeanCpu;
import com.free4lab.monitorproxy.hbasetemp.test;

//迟早要改为data的统一格式，看宋神那边是否统一格式可以
//这个在了解注解之后都可以统一在AbstractResource里面，同时要修改test
@Path("dataCpu/")
@Component
public class CpuResource extends AbstractResource<BeanCpu>{
	private SumWeekCpuDao dao = new SumWeekCpuDao();
	 
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
	
	@GET
	@Path("SumCpu/{id}/{BEGIN_TIME}/{END_TIME}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<SumWeekCpu> getSumCpu(@QueryParam("id") String id, @QueryParam(BEGIN_TIME) String start, @QueryParam(END_TIME) String end) {
		return (List<SumWeekCpu>)dao.findByPropertyAndTime("uuid", id, "sumTime", stringToTimeStamp(start), stringToTimeStamp(end));
	}
	
}
