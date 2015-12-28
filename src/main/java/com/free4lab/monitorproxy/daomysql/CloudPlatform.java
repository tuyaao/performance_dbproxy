package com.free4lab.monitorproxy.daomysql;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;


@Entity
@Table(name = "cloud_platform")
@XmlRootElement(name = "cloudPlatform")

//JAXB中的注解，用来根据java类生成xml内容
public class CloudPlatform implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	/** field */
	private Integer id;
	private String name;
	private String description;
	

	/** default constructor */
	public CloudPlatform() {
	}

	/** minimal constructor */
	public CloudPlatform(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public CloudPlatform(Integer id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;

	}

	/** property */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@XmlElement
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "name")
	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
	@Column(name = "description")
	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
