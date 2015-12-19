/**
 * @author TuYaao
 * @param  虚拟机实例
 *
 */

package com.free4lab.monitorproxy.restclient;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "beanHostRegister")//只有这样才回被application/xml序列化，application/xml and application/json 是捆绑在一起使用的
public class BeanHostRegister implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** field */
	private Integer id;
	private String ip;
	private String mac;
	private Integer cloudPlatformId;
	private String name;
	private String description;
	private Timestamp createTime;
	private Timestamp updateTime;
	private String status;
	private String os;
	private Integer hardware;
	private Integer cpu;
	private Integer memory;
	private Integer disk;
	private Integer bandwidth;;

	/** default constructor */
	public BeanHostRegister() {
	}

	/** minimal constructor */
	public BeanHostRegister(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public BeanHostRegister(Integer id, String ip, String mac,
			Integer cloudPlatformId, String name, String description,
			Timestamp createTime, Timestamp updateTime, String status, String os,
			Integer hardware, Integer cpu, Integer memory, Integer disk, Integer bandwidth) {
		this.id = id;
		this.ip = ip;
		this.mac = mac;
		this.cloudPlatformId = cloudPlatformId;
		this.name = name;
		this.description = description;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.status = status;
		this.os = os;
		this.hardware = hardware;
		this.cpu = cpu;
		this.memory = memory;
		this.disk = disk;
		this.bandwidth = bandwidth;

	}

	/** property */
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
    
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Integer getCloudPlatformId() {
		return cloudPlatformId;
	}

	public void setCloudPlatformId(Integer cloudPlatformId) {
		this.cloudPlatformId = cloudPlatformId;
	}
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
	@XmlJavaTypeAdapter(value = TimestampAdapter.class, type = Timestamp.class)
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
    
	@XmlJavaTypeAdapter(value = TimestampAdapter.class, type = Timestamp.class)
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
    
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}
    
	public Integer getHardware() {
		return hardware;
	}

	public void setHardware(Integer hardware) {
		this.hardware = hardware;
	}

	public Integer getCpu() {
		return cpu;
	}

	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}

	public Integer getMemory() {
		return memory;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public Integer getDisk() {
		return disk;
	}

	public void setDisk(Integer disk) {
		this.disk = disk;
	}

	public Integer getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Integer bandwidth) {
		this.bandwidth = bandwidth;
	}
	
}
