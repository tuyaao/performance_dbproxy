package com.free4lab.monitorproxy.daomysql;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

//@Repository、@Service 和 @Controller 分别对应着经典三层，持久层，业务层，控制层（web层，比如action层）
//当组件不好分类时，用@Component

@Repository

@Scope("prototype")
public class SumWeekCpuDao extends AbstractDAO<SumWeekCpu> {

	private Logger logger = Logger.getLogger(SumWeekCpuDao.class);

	public SumWeekCpuDao() {
	}
	
	@Override
	public Class<SumWeekCpu> getEntityClass() {
		return SumWeekCpu.class;
	}

}
