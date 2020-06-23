package br.com.fernandoalmeida.ifttproxy.caller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.fernandoalmeida.ifttproxy.config.ApplicationConfig;

/**
 * Calls an event on IFTTT with a pre-determined delay.
 * 
 * @author falmeida
 */
@Component
public class EventCaller
{
	private final Logger logger = LoggerFactory.getLogger(EventCaller.class);

	@Autowired
	private ApplicationConfig config;

	/**
	 * Calls an event on IFTTT with a delay.
	 * 
	 * @param eventName
	 * @param key
	 * @param delay
	 */
	public void callWithDelay(String eventName, String key, Long delay)
	{
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

		Runnable task = () -> new EventCaller().call(getUrl(eventName, key));

		logger.info("Scheduling a call to event [{}]" + " after [{}] second(s).", eventName, delay);

		executorService.schedule(task, delay, TimeUnit.SECONDS);
	}

	/**
	 * Calls an event on IFTTT
	 * 
	 * @param eventName
	 * @param key
	 */
	public void call(String url)
	{
		String response = new RestTemplate().getForObject(url, String.class);
		logger.info("Response is [{}].", response);
	}

	/**
	 * Builds an ifttt url for an <code> eventName </code> and key
	 * <code> key </code>
	 * 
	 * @param eventName
	 * @param key
	 * @return
	 */
	private String getUrl(String eventName, String key)
	{
		return config.getIftttUrl() + "/trigger/" + eventName + "/with/key/" + key;
	}

}
