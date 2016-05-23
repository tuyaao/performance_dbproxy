
package com.free4lab.monitorproxy.restserver;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.free4lab.monitorproxy.daomysql.SumWeekMem;
import com.free4lab.monitorproxy.daomysql.SumWeekMemDao;
import com.free4lab.monitorproxy.hbasetemp.BeanMem;
import com.free4lab.monitorproxy.hbasetemp.test;

@Path("dataMem/")
@Component
public class MemResource extends AbstractResource<BeanMem>{
	private SumWeekMemDao dao = new SumWeekMemDao();
	
	@Override
	public Class getEntityClass() {
		return MemResource.class;
	}
	
	@GET
	@Path("Mem")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<BeanMem> getIdByMac(@QueryParam("id") String id, @QueryParam(BEGIN_TIME) String start, @QueryParam(END_TIME) String end) {
		return resolveHbaseResultit(test.getMem(id, start, end));
	}
	
	@GET
	@Path("SumMem/{id}/{BEGIN_TIME}/{END_TIME}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<SumWeekMem> getSumCpu(@QueryParam("id") String id, @QueryParam(BEGIN_TIME) String start, @QueryParam(END_TIME) String end) {
		return (List<SumWeekMem>)dao.findByPropertyAndTime("uuid", id, "sumTime", stringToTimeStamp(start), stringToTimeStamp(end));
	}
	
}
