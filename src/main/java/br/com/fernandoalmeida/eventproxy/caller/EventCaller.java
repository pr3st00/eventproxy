package br.com.fernandoalmeida.eventproxy.caller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Calls an event on IFTTT with a pre-determined delay.
 * 
 * @author falmeida
 */
@Component
public class EventCaller
{
	private final Logger logger = LoggerFactory.getLogger(EventCaller.class);

	/**
	 * Calls an url
	 * 
	 * @param eventName
	 * @param key
	 */
	private void call(String url)
	{
		String response = new RestTemplate().getForObject(url, String.class);
		logger.info("Response is [{}].", response);
	}

	/**
	 * Calls an url with a delay.
	 * 
	 * @param eventName
	 * @param key
	 * @param delay
	 */
	public void callWithDelay(String url, Long delay)
	{
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

		Runnable task = () -> new EventCaller().call(url);
		
		logger.info("Scheduling a call to url [{}]" + " after [{}] second(s).", url, delay);

		executorService.schedule(task, delay, TimeUnit.SECONDS);
	}
}
