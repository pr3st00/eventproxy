package br.com.fernandoalmeida.eventproxy.web;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fernandoalmeida.eventproxy.caller.EventCaller;
import br.com.fernandoalmeida.eventproxy.caller.Request;
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
	public void callIftttEvent(@RequestParam("event") @NotBlank String eventName, @RequestParam("key") @NotBlank String key,
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
	@GetMapping(value = "/callEvent")
	public void callEvent(@RequestParam("key") @NotBlank String key, @RequestParam("delay") Optional<Long> delay)
	{
		caller.callWithDelay(config.getUrlByKey(key), delay.isPresent() ? delay.get() : config.getDefaultDelay());
	}
	
	@PostMapping(value = "/callEvent")
	public void callEvent(@RequestBody @Valid Request request)
	{
	    Optional<Integer> delay = request.getDelay();
	    
	    caller.callWithDelay(request.getUrl(), delay.isPresent() ? delay.get().intValue() : config.getDefaultDelay());
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
