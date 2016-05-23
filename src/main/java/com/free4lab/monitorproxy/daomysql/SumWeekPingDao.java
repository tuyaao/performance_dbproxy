package com.free4lab.monitorproxy.daomysql;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

//@Repository、@Service 和 @Controller 分别对应着经典三层，持久层，业务层，控制层（web层，比如action层）
//当组件不好分类时，用@Component

@Repository

@Scope("prototype")
public class SumWeekPingDao extends AbstractDAO<SumWeekPing> {

	private Logger logger = Logger.getLogger(SumWeekPingDao.class);

	public SumWeekPingDao() {
	}
	
	@Override
	public Class<SumWeekPing> getEntityClass() {
		return SumWeekPing.class;
	}

}
