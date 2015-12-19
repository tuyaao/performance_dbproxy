
package com.free4lab.monitorproxy.servicemysqlImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.free4lab.monitorproxy.daomysql.CloudPlatform;
import com.free4lab.monitorproxy.daomysql.CloudPlatformDao;
import com.free4lab.monitorproxy.servicemysql.CloudPlatformService;

@Service
public class CloudPlatformServiceImpl implements CloudPlatformService{
	private static Logger logger = LoggerFactory.getLogger(CloudPlatformServiceImpl.class);
	@Autowired
	private CloudPlatformDao cloudPlatformDAO;
	
	public List<CloudPlatform> getAll() {
		try {
			return cloudPlatformDAO.findAll();
		} catch (Exception e) {
			logger.error("Get CloudPlatform error!");
			logger.error(e.getMessage());
		}
		return null;
	}

	public CloudPlatform getById(Integer id) {
		return cloudPlatformDAO.findByPrimaryKey(id);
	}

}
