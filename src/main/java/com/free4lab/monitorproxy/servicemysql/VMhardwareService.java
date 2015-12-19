package com.free4lab.monitorproxy.servicemysql;

import java.util.List;

import com.free4lab.monitorproxy.daomysql.VMhardware;

public interface VMhardwareService {
	List<VMhardware> getAll();
	VMhardware add( VMhardware vMhardware );
}
