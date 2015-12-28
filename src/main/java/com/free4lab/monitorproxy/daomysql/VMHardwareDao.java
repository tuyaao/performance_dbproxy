package com.free4lab.monitorproxy.daomysql;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository

@Scope("prototype")
public class VMHardwareDao extends AbstractDAO<VMhardware> {
	
	private Logger logger = Logger.getLogger(VMHardwareDao.class);
	
	public VMHardwareDao(){
		
	}
	
	@Override
	public Class<VMhardware> getEntityClass() {
		return VMhardware.class;
	}
	
	
}
