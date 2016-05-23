
package com.free4lab.monitorproxy.restserver;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.free4lab.monitorproxy.daomysql.SumWeekIozone;
import com.free4lab.monitorproxy.daomysql.SumWeekIozoneDao;
import com.free4lab.monitorproxy.hbasetemp.BeanIozone;
import com.free4lab.monitorproxy.hbasetemp.test;

@Path("dataIo")
@Component
public class IozoneResource extends AbstractResource<BeanIozone>{
	private SumWeekIozoneDao dao = new SumWeekIozoneDao();
	
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
	
	@GET
	@Path("SumIo/{id}/{BEGIN_TIME}/{END_TIME}")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<SumWeekIozone> getSumCpu(@QueryParam("id") String id, @QueryParam(BEGIN_TIME) String start, @QueryParam(END_TIME) String end) {
		return (List<SumWeekIozone>)dao.findByPropertyAndTime("uuid", id, "sumTime", stringToTimeStamp(start), stringToTimeStamp(end));
	}
	
}
