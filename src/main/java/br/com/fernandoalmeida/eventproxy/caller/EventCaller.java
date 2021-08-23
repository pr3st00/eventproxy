package br.com.fernandoalmeida.eventproxy.caller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * Calls an event on IFTTT with a pre-determined delay.
 * 
 * @author falmeida
 */
@Component
@Slf4j
public class EventCaller
{
	/**
	 * Calls an url
	 * 
	 * @param eventName
	 * @param key
	 */
	private void call(String url)
	{
		String response = new RestTemplate().getForObject(url, String.class);
		log.info("Response is [{}].", response);
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
		
		log.info("Scheduling a call to url [{}]" + " after [{}] second(s).", url, delay);

		executorService.schedule(task, delay, TimeUnit.SECONDS);
	}
}
