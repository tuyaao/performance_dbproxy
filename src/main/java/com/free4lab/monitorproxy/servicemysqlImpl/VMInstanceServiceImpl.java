package com.free4lab.monitorproxy.servicemysqlImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.free4lab.monitorproxy.daomysql.VMInstance;
import com.free4lab.monitorproxy.daomysql.VMInstanceDao;
import com.free4lab.monitorproxy.servicemysql.VMInstanceService;

@Service
public class VMInstanceServiceImpl implements VMInstanceService {
	private static Logger logger = LoggerFactory.getLogger(VMhardwareServiceImpl.class);
	@Autowired
	private VMInstanceDao vMInstanceDao;
	
	public List<VMInstance> getAll() {
		try {
			return vMInstanceDao.findAll();
		} catch (Exception e) {
			logger.error("Get VMInstances error!");
			logger.error(e.getMessage());
		}
		return null;
	}

	public Integer getIdByMac(String mac) {
		List<VMInstance> list = vMInstanceDao.findByProperty("mac", mac);
		if( list.size() >= 1){
			return list.get(0).getId();
		}
		return null;
	}

	public VMInstance add(VMInstance vMInstance) {
		vMInstanceDao.save(vMInstance);
		return vMInstance;
	}
	
}