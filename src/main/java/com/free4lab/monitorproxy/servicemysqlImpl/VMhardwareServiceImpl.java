package com.free4lab.monitorproxy.servicemysqlImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.free4lab.monitorproxy.daomysql.VMHardwareDao;
import com.free4lab.monitorproxy.daomysql.VMhardware;
import com.free4lab.monitorproxy.servicemysql.VMhardwareService;

@Service
public class VMhardwareServiceImpl implements VMhardwareService {
	private static Logger logger = LoggerFactory.getLogger(VMhardwareServiceImpl.class);
	@Autowired
	private VMHardwareDao vMhardwareDao;

	public List<VMhardware> getAll() {
		try {
			return vMhardwareDao.findAll();
		} catch (Exception e) {
			logger.error("Find all VMhardwares error!");
			logger.error(e.getMessage());
		}
		return null;
	}

	public VMhardware add(VMhardware vMhardware) {
		vMhardwareDao.save(vMhardware);
		return vMhardware;
	}

}
