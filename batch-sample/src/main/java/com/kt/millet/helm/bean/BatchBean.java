package com.kt.millet.helm.bean;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class BatchBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2553521609879327235L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column
	private LocalDateTime createDate;
	
	@Column
	private long count;
	
	@Builder
	public BatchBean(
			long id,
			long count) {
		
		this.id = id;
		this.count = count;
		

		this.createDate = LocalDateTime.now();
	}
	
	public BatchBean() {
		
	}
}
