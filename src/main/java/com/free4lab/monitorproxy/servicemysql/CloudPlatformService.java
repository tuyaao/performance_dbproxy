
package com.free4lab.monitorproxy.servicemysql;

import java.util.List;

import com.free4lab.monitorproxy.daomysql.CloudPlatform;

public interface CloudPlatformService {
	List<CloudPlatform> getAll();
	CloudPlatform getById( Integer id );
}
