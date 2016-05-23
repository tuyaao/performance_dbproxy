package com.free4lab.monitorproxy.daomysql;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

//@Repository、@Service 和 @Controller 分别对应着经典三层，持久层，业务层，控制层（web层，比如action层）
//当组件不好分类时，用@Component

@Repository

@Scope("prototype")
public class SumWeekTpccDao extends AbstractDAO<SumWeekTpcc> {

	private Logger logger = Logger.getLogger(SumWeekTpccDao.class);

	public SumWeekTpccDao() {
	}
	
	@Override
	public Class<SumWeekTpcc> getEntityClass() {
		return SumWeekTpcc.class;
	}

}
