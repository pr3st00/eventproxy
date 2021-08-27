package br.com.fernandoalmeida.eventproxy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ApplicationConfig
{
    @Value("${app.ifttt.url}")
    private String iftttUrl;

    @Value("${app.default.delay}")
    private Integer defaultDelay;

    @Autowired
    private Environment env;

    public String getUrlByKey(String key)
    {
        return env.getProperty("app." + key + ".url");
    }

    public Integer getDefaultDelay()
    {
        return defaultDelay;
    }

    public String getIftttUrl()
    {
        return iftttUrl;
    }

}