package com.free4lab.monitorproxy.daomysql;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

//@Repository、@Service 和 @Controller 分别对应着经典三层，持久层，业务层，控制层（web层，比如action层）
//当组件不好分类时，用@Component

@Repository
public class CloudPlatformDao extends AbstractDAO<CloudPlatform> {

	private Logger logger = Logger.getLogger(CloudPlatformDao.class);

	public CloudPlatformDao() {
	}
	
	@Override
	public Class<CloudPlatform> getEntityClass() {
		return CloudPlatform.class;
	}

}
