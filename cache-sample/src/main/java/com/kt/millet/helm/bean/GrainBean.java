package com.kt.millet.helm.bean;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Getter;

@Getter
@RedisHash(value="grain", timeToLive=60)
public class GrainBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6044595120149367248L;

	@Id
	private long id;
	
	private String type;
	
	private String name;
	
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
