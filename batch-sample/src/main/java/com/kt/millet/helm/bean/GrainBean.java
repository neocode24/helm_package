package com.kt.millet.helm.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table
public class GrainBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7602805693233811953L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column
	private String type;
	
	@Column
	private String name;
	
	@Column
	private String description; 
	
	@Builder
	public GrainBean(
			long id, 
			String type, 
			String name, 
			String description) {
		
		this.id = id;
		this.type = type;
		this.name = name;
		this.description = description;
	}

	public GrainBean() {

	}
}
