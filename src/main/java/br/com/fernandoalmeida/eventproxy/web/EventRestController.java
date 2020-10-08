package br.com.fernandoalmeida.eventproxy.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fernandoalmeida.eventproxy.caller.EventCaller;
import br.com.fernandoalmeida.eventproxy.config.ApplicationConfig;

@RestController
public class EventRestController
{
	@Autowired
	private EventCaller caller;

	@Autowired
	private ApplicationConfig config;

	/**
	 * Calls an event on IFTTT with a delay
	 * 
	 * @param eventName
	 * @param key
	 * @param delay
	 */
	@GetMapping(value = "/callIfttEvent")
	public void callEvent(@RequestParam("event") String eventName, @RequestParam("key") String key,
			@RequestParam("delay") Optional<Long> delay)
	{
		caller.callWithDelay(getEventUrl(eventName, key), delay.isPresent() ? delay.get() : config.getDefaultDelay());
	}

	/**
	 * Calls a generic url with a delay
	 * 
	 * @param eventName
	 * @param key
	 * @param delay
	 */
	@GetMapping(value = "/callGenericUrl")
	public void callGenericUrl(@RequestParam("delay") Optional<Long> delay)
	{
		caller.callWithDelay(config.getGenericUrl(), delay.isPresent() ? delay.get() : config.getDefaultDelay());
	}

	/**
	 * Builds an ifttt url for an <code> eventName </code> and key
	 * <code> key </code>
	 * 
	 * @param eventName
	 * @param key
	 * @return
	 */
	private String getEventUrl(String eventName, String key)
	{
		return config.getIftttUrl() + "/trigger/" + eventName + "/with/key/" + key;
	}

}
