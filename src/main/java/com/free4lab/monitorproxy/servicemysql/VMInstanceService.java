
package com.free4lab.monitorproxy.servicemysql;

import java.util.List;

import com.free4lab.monitorproxy.daomysql.VMInstance;

public interface VMInstanceService {
	List<VMInstance> getAll();
	Integer getIdByMac( String mac );
	VMInstance add( VMInstance vMInstance );
}
