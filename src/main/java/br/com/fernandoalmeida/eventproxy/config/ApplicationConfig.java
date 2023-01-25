package br.com.fernandoalmeida.eventproxy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("app")
public class ApplicationConfig {
	@Getter
	@Setter
	private String iftttUrl;
	@Getter
	@Setter
	private Integer defaultDelay;

	@Autowired
	private Environment env;

	public String getUrlByKey(String key) {
		return env.getProperty("app." + key + ".url");
	}

}