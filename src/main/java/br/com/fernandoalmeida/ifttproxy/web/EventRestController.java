package br.com.fernandoalmeida.ifttproxy.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fernandoalmeida.ifttproxy.caller.EventCaller;
import br.com.fernandoalmeida.ifttproxy.config.ApplicationConfig;

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
	@GetMapping(value = "/callEvent")
	public void callEvent(@RequestParam("event") String eventName, @RequestParam("key") String key,
			@RequestParam("delay") Optional<Long> delay)
	{
		caller.callWithDelay(eventName, key, delay.isPresent() ? delay.get() : config.getDefaultDelay());
	}

}
