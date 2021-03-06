package com.free4lab.monitorproxy.daomysql;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository

@Scope("prototype")
public class VMInstanceDao extends AbstractDAO<VMInstance> {
	
	private Logger logger = Logger.getLogger(VMInstanceDao.class);
	
	public VMInstanceDao(){
		
	}
	
	@Override
	public Class<VMInstance> getEntityClass() {
		return VMInstance.class;
	}
	
	
}
