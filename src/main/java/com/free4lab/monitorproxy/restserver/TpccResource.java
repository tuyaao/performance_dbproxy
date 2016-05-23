
package com.free4lab.monitorproxy.restserver;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.free4lab.monitorproxy.daomysql.SumWeekTpcc;
import com.free4lab.monitorproxy.daomysql.SumWeekTpccDao;
import com.free4lab.monitorproxy.hbasetemp.BeanTpcc;
import com.free4lab.monitorproxy.hbasetemp.test;

@Path("dataTpcc/")
@Component
public class TpccResource extends AbstractResource<BeanTpcc>{
	private SumWeekTpccDao dao = new SumWeekTpccDao();
	
	@Override
	public Class getEntityClass() {
		return TpccResource.class;
	}
	
	@GET
	@Path("Tpcc")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<BeanTpcc> getIdByMac(@QueryParam("id") String id, @QueryParam(BEGIN_TIME) String start, @QueryParam(END_TIME) String end) {
		return resolveHbaseResultit(test.getTpcc(id, start, end));
	}
	
	@GET
	@Path("SumTpcc/{id}/{BEGIN_TIME}/{END_TIME}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<SumWeekTpcc> getSumCpu(@QueryParam("id") String id, @QueryParam(BEGIN_TIME) String start, @QueryParam(END_TIME) String end) {
		return (List<SumWeekTpcc>)dao.findByPropertyAndTime("uuid", id, "sumTime", stringToTimeStamp(start), stringToTimeStamp(end));
	}
	
}
