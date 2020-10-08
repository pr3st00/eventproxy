package br.com.fernandoalmeida.eventproxy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan
public class ApplicationConfig
{
	@Value("${app.ifttt.url}")
	private String iftttUrl;

	@Value("${app.generic.url}")
	private String genericUrl;

	@Value("${app.default.delay}")
	private Long defaultDelay;

	public String getGenericUrl()
	{
		return genericUrl;
	}

	public void setGenericUrl(String genericUrl)
	{
		this.genericUrl = genericUrl;
	}

	public Long getDefaultDelay()
	{
		return defaultDelay;
	}

	public String getIftttUrl()
	{
		return iftttUrl;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer()
	{
		return new PropertySourcesPlaceholderConfigurer();
	}

}