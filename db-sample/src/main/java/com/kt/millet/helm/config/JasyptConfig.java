package com.kt.millet.helm.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class JasyptConfig{
	
	private static final String JASYPT_KEY 	= "millet";
	private static final String ALGORITHM 	= "PBEWithMD5AndDES";
	
	@Bean("jasyptStringEncrptor")
	public StandardPBEStringEncryptor StringEnc() {
		 StandardPBEStringEncryptor enc = new StandardPBEStringEncryptor();
		 EnvironmentPBEConfig conf = new EnvironmentPBEConfig();
		 conf.setPassword(JASYPT_KEY);
		 conf.setAlgorithm(ALGORITHM);
		 enc.setConfig(conf);
		 return enc;
	 }
	
	public static void main(String[] args) {
		StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
		pbeEnc.setAlgorithm(JasyptConfig.ALGORITHM);
		pbeEnc.setPassword(JasyptConfig.JASYPT_KEY);
		
		String username = pbeEnc.encrypt("helm_user");
		String password = pbeEnc.encrypt("new1234!");
				
		log.debug("username:" + username);
		log.debug("password:" + password);
		
		log.debug(pbeEnc.decrypt(username));
		log.debug(pbeEnc.decrypt(password));
	
	}
}

